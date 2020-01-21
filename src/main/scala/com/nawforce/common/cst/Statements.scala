/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.common.cst

import com.nawforce.common.api.Org
import com.nawforce.common.documents.LineLocation
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.types.{ApexModifiers, ENUM_NATURE, Modifier}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.{ClippedText, CodeParser}

import scala.ref.WeakReference

trait Statement extends CST {
  def verify(context: BlockVerifyContext): Unit
}

// Treat Block as Statement for blocks in blocks
final case class LazyBlock(clippedText: ClippedText, var blockContextRef: WeakReference[BlockContext], isStatic: Boolean)
  extends CST with Statement {
  private var statementsRef: WeakReference[Seq[Statement]] = WeakReference(null)
  private var reParsed = false

  override def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    statements().foreach(s => s.verify(blockContext))
  }

  def statements(): Seq[Statement] = {
    var statements = statementsRef.get
    if (statements.isEmpty) {
      var statementContext = blockContextRef.get
      if (statementContext.isEmpty) {
        CodeParser.parseBlock(clippedText.path, clippedText.text) match {
          case Left(err) =>
            Org.logMessage(LineLocation(clippedText.path, err.line), err.message)
            return Nil
          case Right(c) =>
            statementContext = Some(c)
            blockContextRef = WeakReference(statementContext.get)
            reParsed = true

        }
      }

      var rangeAdjust = CST.rangeAdjust.value
      assert(rangeAdjust._1 == 0 && rangeAdjust._2 == 0)
      if (reParsed)
        rangeAdjust = (clippedText.line, clippedText.column)
      CST.rangeAdjust.withValue(rangeAdjust) {
        val statementContexts: Seq[StatementContext] = CodeParser.toScala(statementContext.get.statement())
        statements = Some(Statement.construct(statementContexts, new ConstructContext))
        statementsRef = WeakReference(statements.get)
      }
    }
    statements.get
  }
}

final case class Block(statements: Seq[Statement])
  extends CST with Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    statements.foreach(s => s.verify(blockContext))
  }
}

object Block {
  def constructLazy(blockContext: BlockContext, context: ConstructContext, isStatic: Boolean): LazyBlock = {
    LazyBlock(CodeParser.clipText(blockContext), WeakReference(blockContext), isStatic)
  }

  def construct(blockContext: BlockContext, context: ConstructContext): Block = {
    Block(Statement.construct(CodeParser.toScala(blockContext.statement()), context)).withContext(blockContext, context)
  }

  def constructOption(blockContext: Option[BlockContext], context: ConstructContext): Option[Block] = {
    blockContext.map(bc => construct(bc, context))
  }
}

final case class LocalVariableDeclarationStatement(localVariableDeclaration: LocalVariableDeclaration)
  extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    localVariableDeclaration.verify(context)
  }
}

object LocalVariableDeclarationStatement {
  def construct(from: LocalVariableDeclarationStatementContext,
                context: ConstructContext): LocalVariableDeclarationStatement = {
    LocalVariableDeclarationStatement(
      LocalVariableDeclaration.construct(from.localVariableDeclaration(), context)).withContext(from, context)
  }
}

final case class IfStatement(expression: Expression, statements: Seq[Statement]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)

    // This is replicating a feature where non-block statements can pass declarations forward
    var stmtContext = new InnerBlockVerifyContext(context)
    statements.foreach(stmt => {
      if (stmt.isInstanceOf[Block]) {
        stmtContext = new InnerBlockVerifyContext(stmtContext)
      }
      stmt.verify(stmtContext)
    })
  }
}

object IfStatement {
  def construct(ifStatement: IfStatementContext, context: ConstructContext): IfStatement = {
    val statements: Seq[StatementContext] = CodeParser.toScala(ifStatement.statement())
    IfStatement(Expression.construct(ifStatement.parExpression().expression(), context),
      Statement.construct(statements.toList, context)).withContext(ifStatement, context)
  }
}

sealed abstract class WhenLiteral extends CST

final class WhenNullLiteral extends WhenLiteral
final case class WhenIdLiteral(id: Id) extends WhenLiteral
final case class WhenStringLiteral(value: String) extends WhenLiteral
final case class WhenIntegerLiteral(negate: Boolean, value: String) extends WhenLiteral

object WhenLiteral {
  def construct(literal: WhenLiteralContext, context: ConstructContext): WhenLiteral = {
    val whenLiteral = CodeParser.toScala(literal.NULL())
      .map(_ => new WhenNullLiteral())
    .orElse(CodeParser.toScala(literal.IntegerLiteral())
      .map(l => new WhenIntegerLiteral(CodeParser.toScala(literal.SUB()).nonEmpty, CodeParser.getText(l))))
    .orElse(CodeParser.toScala(literal.StringLiteral())
      .map(l => new WhenStringLiteral(CodeParser.getText(l))))
    .orElse(CodeParser.toScala(literal.id())
      .map(l => new WhenIdLiteral(Id.construct(l, context))))

    if (whenLiteral.isEmpty)
      throw new CSTException()
    else
      whenLiteral.get.withContext(literal, context)
  }
}

sealed abstract class WhenValue extends CST

final class WhenElseValue extends WhenValue
final case class WhenLiteralsValue(literals: Seq[WhenLiteral]) extends WhenValue
final case class WhenIdsValue(ids: Seq[Id]) extends WhenValue

object WhenValue {
  def construct(value: WhenValueContext, context: ConstructContext): WhenValue = {
    if (CodeParser.toScala(value.ELSE()).nonEmpty)
      new WhenElseValue()
    else {
      val literals = CodeParser.toScala(value.whenLiteral()).map(l => WhenLiteral.construct(l, context))
      if (literals.nonEmpty)
        WhenLiteralsValue(literals)
      else
        WhenIdsValue(CodeParser.toScala(value.id()).map(id => Id.construct(id, context)))
    }
  }
}

final case class WhenControl(whenValues: WhenValue, block: Block) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    // TODO: whenValue handling
    block.verify(new InnerBlockVerifyContext(context))
  }
}

object WhenControl {
  def construct(whenControl: WhenControlContext, context: ConstructContext): WhenControl = {
    WhenControl(
      CodeParser.toScala(whenControl.whenValue()).map(v => WhenValue.construct(v, context)).get,
      Block.construct(whenControl.block(), context))
  }
}

final case class SwitchStatement(expression: Expression, whenControls: List[WhenControl]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    val result = expression.verify(context)
    if (result.isDefined) {
      result.typeName match {
        case TypeName.Integer | TypeName.Long | TypeName.String => ()
        case _ if result.typeDeclaration.nature == ENUM_NATURE => ()
        case _ if result.typeDeclaration.isSObject => ()
        case _ =>
          Org.logMessage(location,
            s"Switch expression must be a Integer, Long, String, SObject record or enum value, not '${result.typeName}'")
          return;
      }
      whenControls.foreach(_.verify(context))
    }
  }
}

object SwitchStatement {
  def construct(switchStatement: SwitchStatementContext, context: ConstructContext): SwitchStatement = {
    SwitchStatement(
      Expression.construct(switchStatement.expression(), context),
      CodeParser.toScala(switchStatement.whenControl()).map(WhenControl.construct(_, context)).toList,
    )
  }
}

final case class ForStatement(control: ForControl, statement: Statement) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    control.verify(context)

    val loopContext = new InnerBlockVerifyContext(context)
    control.addVars(loopContext)
    statement.verify(loopContext)
  }
}

object ForStatement {
  def construct(statement: ForStatementContext, context: ConstructContext): ForStatement = {
    ForStatement(ForControl.construct(statement.forControl(), context),
      Statement.construct(statement.statement(), context)).withContext(statement, context)
  }
}

sealed abstract class ForControl extends CST {
  def verify(context: BlockVerifyContext): Unit
  def addVars(context: BlockVerifyContext): Unit
}

object ForControl {
  def construct(from: ForControlContext, context: ConstructContext): ForControl = {
    val cst =
      CodeParser.toScala(from.enhancedForControl())
        .map(efc => EnhancedForControl.construct(efc, context))
        .getOrElse(BasicForControl.construct(from, context))
    cst.withContext(from, context)
  }
}

final case class EnhancedForControl(modifiers: Seq[Modifier], typeName: TypeName,
                                    id: Id, expression: Expression) extends ForControl {
  override def verify(context: BlockVerifyContext): Unit = {
    val forType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (forType.isEmpty)
      context.missingType(id.location, typeName)
    expression.verify(context)
  }

  def addVars(context: BlockVerifyContext): Unit = {
    context.addVar(id.name, location, typeName)
  }
}

object EnhancedForControl {
  def construct(from: EnhancedForControlContext, context: ConstructContext): EnhancedForControl = {
    EnhancedForControl(
      ApexModifiers.construct(CodeParser.toScala(from.modifier()), context),
      TypeRef.construct(from.typeRef()),
      Id.construct(from.id(), context),
      Expression.construct(from.expression(), context).withContext(from, context)
    )
  }
}

final case class BasicForControl(forInit: Option[ForInit], expression: Option[Expression],
                                 forUpdate: Option[ForUpdate]) extends ForControl {
  override def verify(context: BlockVerifyContext): Unit = {
    forInit.foreach(_.verify(context))
    expression.foreach(_.verify(context))
    forUpdate.foreach(_.verify(context))
  }

  def addVars(context: BlockVerifyContext): Unit = {
    // Not needed, handled by forInit verify
  }
}

object BasicForControl {
  def construct(from: ForControlContext, context: ConstructContext): BasicForControl = {
    val forInit =
      CodeParser.toScala(from.forInit())
        .map(fi => ForInit.construct(fi, context))
    val expression =
      CodeParser.toScala(from.expression())
        .map(e => Expression.construct(e, context))
    val forUpdate =
      CodeParser.toScala(from.forUpdate())
        .map(u => ForUpdate.construct(u, context))
    BasicForControl(forInit, expression, forUpdate).withContext(from, context)
  }
}

sealed abstract class ForInit extends CST {
  def verify(context: BlockVerifyContext): Unit
  def addVars(context: BlockVerifyContext): Unit
}

final case class LocalVariableForInit(variable: LocalVariableDeclaration) extends ForInit {
  override def verify(context: BlockVerifyContext): Unit = {
    variable.verify(context)
  }

  override def addVars(context: BlockVerifyContext): Unit = {
    variable.addVars(context)
  }
}

final case class ExpressionListForInit(expressions: Seq[Expression]) extends ForInit {
  override def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
  }

  override def addVars(context: BlockVerifyContext): Unit = {
  }
}

object ForInit {
  def construct(from: ForInitContext, context: ConstructContext): ForInit = {
    CodeParser.toScala(from.localVariableDeclaration())
      .map(lvd => LocalVariableForInit(LocalVariableDeclaration.construct(lvd, context)))
      .getOrElse({
        val expressions: Seq[ExpressionContext] = CodeParser.toScala(
          CodeParser.toScala(from.expressionList()).get.expression())
        ExpressionListForInit(Expression.construct(expressions, context))
      })
      .withContext(from, context)
  }
}

final case class ForUpdate(expressions: Seq[Expression]) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
  }
}

object ForUpdate {
  def construct(from: ForUpdateContext, context: ConstructContext): ForUpdate = {
    val expressions: Seq[ExpressionContext] = CodeParser.toScala(from.expressionList().expression())
    ForUpdate(Expression.construct(expressions.toList, context)).withContext(from, context)
  }
}

final case class WhileStatement(expression: Expression, statement: Statement) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    statement.verify(context)
  }
}

object WhileStatement {
  def construct(statement: WhileStatementContext, context: ConstructContext): WhileStatement = {
    WhileStatement(Expression.construct(statement.parExpression().expression(), context),
      Statement.construct(statement.statement(), context)).withContext(statement, context)
  }
}

final case class DoWhileStatement(statement: Statement, expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    statement.verify(context)
  }
}

object DoWhileStatement {
  def construct(statement: DoWhileStatementContext, context: ConstructContext): DoWhileStatement = {
    DoWhileStatement(Statement.construct(statement.statement(), context),
      Expression.construct(statement.parExpression().expression(), context)
    ).withContext(statement, context)
  }
}

final case class TryStatement(block: Block, catches: Seq[CatchClause], finallyBlock: Option[Block]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    block.verify(context)
    catches.foreach(_.verify(context))
    finallyBlock.foreach(_.verify(context))
  }
}

object TryStatement {
  def construct(from: TryStatementContext, context: ConstructContext): TryStatement = {
    val catches: Seq[CatchClauseContext] = CodeParser.toScala(from.catchClause())
    val finallyBlock = CodeParser.toScala(from.finallyBlock()).map(fb => Block.construct(fb.block(), context))
    TryStatement(Block.construct(from.block(), context),
      CatchClause.construct(catches, context),
      finallyBlock).withContext(from, context)
  }
}

final case class CatchClause(modifiers: Seq[Modifier], qname: QualifiedName, id: String, block: Block) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    blockContext.addVar(Name(id), qname.location, qname.asTypeName())
    block.verify(blockContext)
  }
}

object CatchClause {
  def construct(aList: Seq[CatchClauseContext], context: ConstructContext): Seq[CatchClause] = {
    if (aList != null)
      aList.map(x => CatchClause.construct(x, context))
    else
      List()
  }

  def construct(from: CatchClauseContext, context: ConstructContext): CatchClause = {
    CatchClause(
      ApexModifiers.construct(CodeParser.toScala(from.modifier()), context),
      QualifiedName.construct(from.qualifiedName(), context),
      CodeParser.getText(from.id()),
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }
}

final case class ReturnStatement(expression: Option[Expression]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.foreach(_.verify(context))
  }
}

object ReturnStatement {
  def construct(statement: ReturnStatementContext, context: ConstructContext): ReturnStatement = {
    ReturnStatement(
      CodeParser.toScala(statement.expression())
        .map(e => Expression.construct(e, context)))
      .withContext(statement, context)
  }
}

final case class ThrowStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object ThrowStatement {
  def construct(statement: ThrowStatementContext, context: ConstructContext): ThrowStatement = {
    ThrowStatement(Expression.construct(statement.expression(), context))
      .withContext(statement, context)
  }
}

final case class BreakStatement() extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {}
}

object BreakStatement {
  def construct(statement: BreakStatementContext, context: ConstructContext): BreakStatement = {
    BreakStatement().withContext(statement, context)
  }
}

final case class ContinueStatement() extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {}
}

object ContinueStatement {
  def construct(statement: ContinueStatementContext, context: ConstructContext): ContinueStatement = {
    ContinueStatement().withContext(statement, context)
  }
}

final case class InsertStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object InsertStatement {
  def construct(statement: InsertStatementContext, context: ConstructContext): InsertStatement = {
    InsertStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class UpdateStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object UpdateStatement {
  def construct(statement: UpdateStatementContext, context: ConstructContext): UpdateStatement = {
    UpdateStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class DeleteStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object DeleteStatement {
  def construct(statement: DeleteStatementContext, context: ConstructContext): DeleteStatement = {
    DeleteStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class UndeleteStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object UndeleteStatement {
  def construct(statement: UndeleteStatementContext, context: ConstructContext): UndeleteStatement = {
    UndeleteStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class UpsertStatement(expression: Expression, field: Option[QualifiedName]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    // Future: Verify Field
  }
}

object UpsertStatement {
  def construct(statement: UpsertStatementContext, context: ConstructContext): UpsertStatement = {
    val expression = Expression.construct(statement.expression(), context)
    val qualifiedName =
      CodeParser.toScala(statement.qualifiedName())
      .map(qn => QualifiedName.construct(qn, context))
    UpsertStatement(expression, qualifiedName).withContext(statement, context)
  }
}

final case class MergeStatement(expression1: Expression, expression2: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression1.verify(context)
    expression2.verify(context)
  }
}

object MergeStatement {
  def construct(statement: MergeStatementContext, context: ConstructContext): MergeStatement = {
    val expressions = CodeParser.toScala(statement.expression())
    MergeStatement(Expression.construct(expressions.head, context),
      Expression.construct(expressions(1), context)).withContext(statement, context)
  }
}

final case class RunAsStatement(expressions: Seq[Expression], block: Option[Block]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
    block.foreach(_.verify(context))
  }
}

object RunAsStatement {
  def construct(statement: RunAsStatementContext, context: ConstructContext): RunAsStatement = {
    val expressions =
      CodeParser.toScala(statement.expressionList())
        .map(el => Expression.construct(CodeParser.toScala(el.expression()), context))
        .getOrElse(Seq())
    val block =
      CodeParser.toScala(statement.block())
        .map(b => Block.construct(b, context))
    RunAsStatement(expressions, block).withContext(statement, context)
  }
}

final case class ExpressionStatement(var expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    // Future: What causes 'expression can not be a statement' error
    expression.verify(context)
  }
}

object ExpressionStatement {
  def construct(statement: ExpressionStatementContext, context: ConstructContext): ExpressionStatement = {
    ExpressionStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

object Statement {
  def construct(statements: Seq[StatementContext], context: ConstructContext): Seq[Statement] = {
    statements.map(s => Statement.construct(s, context))
  }

  def construct(statement: StatementContext, context: ConstructContext): Statement = {
    val cst = CodeParser.toScala(statement.block())
      .map(x => Block.construct(x, context))
    .orElse(CodeParser.toScala(statement.localVariableDeclarationStatement())
      .map(x => LocalVariableDeclarationStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.ifStatement())
      .map(x => IfStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.switchStatement())
        .map(x => SwitchStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.forStatement())
      .map(x => ForStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.whileStatement())
      .map(x => WhileStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.doWhileStatement())
      .map(x => DoWhileStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.tryStatement())
      .map(x => TryStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.returnStatement())
      .map(x => ReturnStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.throwStatement())
      .map(x => ThrowStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.breakStatement())
      .map(x => BreakStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.continueStatement())
      .map(x => ContinueStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.insertStatement())
      .map(x => InsertStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.updateStatement())
      .map(x => UpdateStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.deleteStatement())
      .map(x => DeleteStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.undeleteStatement())
      .map(x =>UndeleteStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.upsertStatement())
      .map(x =>UpsertStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.mergeStatement())
      .map(x =>MergeStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.runAsStatement())
      .map(x =>RunAsStatement.construct(x, context)))
    .orElse(CodeParser.toScala(statement.expressionStatement())
      .map(x =>ExpressionStatement.construct(x, context)))

    if (cst.isEmpty)
      throw new CSTException()
    else
      cst.get.withContext(statement, context)
  }
}
