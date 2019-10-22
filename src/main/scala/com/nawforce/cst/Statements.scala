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
package com.nawforce.cst

import java.io.ByteArrayInputStream
import java.nio.file.Path

import com.nawforce.names.{Name, TypeName}
import com.nawforce.parsers.ApexParser._
import com.nawforce.parsers.CaseInsensitiveInputStream
import com.nawforce.types.{ApexModifiers, ApexTypeDeclaration, Modifier}
import org.antlr.v4.runtime.misc.Interval

import scala.collection.JavaConverters._
import scala.ref.WeakReference

trait Statement extends CST {
  def verify(context: BlockVerifyContext): Unit
}

// Treat Block as Statement for blocks in blocks
final case class LazyBlock(path: Path, bytes: Array[Byte], lineAdjust: Int, positionAdjust: Int,
                       var blockContextRef: WeakReference[BlockContext], isStatic: Boolean)
  extends CST with Statement {
  private var statementsRef: WeakReference[List[Statement]] = WeakReference(null)
  private var reParsed = false

  override def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    statements().foreach(s => s.verify(blockContext))
  }

  def statements(): List[Statement] = {
    var statements = statementsRef.get
    if (statements.isEmpty) {
      var statementContext = blockContextRef.get
      if (statementContext.isEmpty) {
        statementContext = ApexTypeDeclaration.parseBlock(path, new ByteArrayInputStream(bytes))
        blockContextRef = WeakReference(statementContext.get)
        reParsed = true
      }

      var rangeAdjust = CST.rangeAdjust.value
      assert(rangeAdjust._1 == 0 && rangeAdjust._2 == 0)
      if (reParsed)
        rangeAdjust = (lineAdjust, positionAdjust)
      CST.rangeAdjust.withValue(rangeAdjust) {
        val statementContexts: Seq[StatementContext] = statementContext.get.statement().asScala
        statements = Some(Statement.construct(statementContexts.toList, new ConstructContext))
        statementsRef = WeakReference(statements.get)
      }
    }
    statements.get
  }

  override def children(): List[CST] = statements()
}

final case class Block(statements: List[Statement])
  extends CST with Statement {

  override def children(): List[CST] = statements

  override def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    statements.foreach(s => s.verify(blockContext))
  }
}

object Block {
  def constructLazy(blockContext: BlockContext, context: ConstructContext, isStatic: Boolean): LazyBlock = {
    val is = blockContext.start.getInputStream
    val text = is.getText(new Interval(blockContext.start.getStartIndex, blockContext.stop.getStopIndex))
    val path = blockContext.start.getInputStream.asInstanceOf[CaseInsensitiveInputStream].path
    LazyBlock(path, text.getBytes(), blockContext.start.getLine-1, blockContext.start.getCharPositionInLine,
      WeakReference(blockContext), isStatic)
  }

  def construct(blockContext: BlockContext, context: ConstructContext): Block = {
    Block(Statement.construct(blockContext.statement().asScala.toList, context)).withContext(blockContext, context)
  }

  def constructOption(blockContext: BlockContext, context: ConstructContext): Option[Block] = {
    if (blockContext != null)
      Some(construct(blockContext, context))
    else
      None
  }
}

final case class LocalVariableDeclarationStatement(localVariableDeclaration: LocalVariableDeclaration) extends Statement {
  override def children(): List[CST] = localVariableDeclaration :: Nil

  override def verify(context: BlockVerifyContext): Unit = {
    localVariableDeclaration.verify(context)
  }
}

object LocalVariableDeclarationStatement {
  def construct(from: LocalVariableDeclarationStatementContext, context: ConstructContext): LocalVariableDeclarationStatement = {
    LocalVariableDeclarationStatement(LocalVariableDeclaration.construct(from.localVariableDeclaration(), context)).withContext(from, context)
  }
}

final case class IfStatement(expression: Expression, statements: List[Statement]) extends Statement {
  override def children(): List[CST] = expression :: statements

  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)

    // This is replicating a feature where non-block statements can pass declarations forward
    var stmtContext = new InnerBlockVerifyContext(context)
    statements.foreach(stmt => {
      if (stmt.isInstanceOf[Block]) {
        stmtContext = new InnerBlockVerifyContext(context)
      }
      stmt.verify(stmtContext)
    })
  }
}

object IfStatement {
  def construct(ifStatement: IfStatementContext, context: ConstructContext): IfStatement = {
    val statements: Seq[StatementContext] = ifStatement.statement().asScala
    IfStatement(Expression.construct(ifStatement.parExpression().expression(), context),
      Statement.construct(statements.toList, context)).withContext(ifStatement, context)
  }
}

final case class WhenControl(expressions: List[Expression], block: Block) extends CST {
  override def children(): List[CST] = expressions ++ List(block)

  def verify(context: BlockVerifyContext): Unit = {
    // TYPING: This requires type knowledge when used with enum, disable for now
    // expressions.foreach(_.verify(context))
    block.verify(new InnerBlockVerifyContext(context))
  }
}

object WhenControl {
  def construct(whenControl: WhenControlContext, context: ConstructContext): WhenControl = {
    val exprs =
      if (whenControl.expressionList() != null) {
        whenControl.expressionList().expression().asScala.map(e => Expression.construct(e, context))
      } else {
        Seq()
      }
    WhenControl(exprs.toList, Block.construct(whenControl.block(), context))
  }
}

final case class SwitchStatement(expression: Expression, whenControls: List[WhenControl]) extends Statement {
  override def children(): List[CST] = expression :: whenControls

  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    whenControls.foreach(_.verify(context))
  }
}

object SwitchStatement {
  def construct(switchStatement: SwitchStatementContext, context: ConstructContext): SwitchStatement = {
    SwitchStatement(
      Expression.construct(switchStatement.expression(), context),
      switchStatement.whenControl().asScala.map(WhenControl.construct(_, context)).toList,
    )
  }
}

final case class ForStatement(control: ForControl, statement: Statement) extends Statement {
  override def children(): List[CST] = {
    control :: statement :: Nil
  }

  override def verify(context: BlockVerifyContext): Unit = {
    control.verify(context)

    val loopContext = new InnerBlockVerifyContext(context)
    control.addVars(loopContext)
    statement.verify(loopContext)
  }
}

object ForStatement {
  def construct(statement: ForStatementContext, context: ConstructContext): ForStatement = {
    ForStatement(ForControl.construct(statement.forControl(), context), Statement.construct(statement.statement(), context)).withContext(statement, context)
  }
}

sealed abstract class ForControl extends CST {
  def verify(context: BlockVerifyContext): Unit
  def addVars(context: BlockVerifyContext): Unit
}

object ForControl {
  def construct(from: ForControlContext, context: ConstructContext): ForControl = {
    val cst =
      if (from.enhancedForControl() != null) {
        EnhancedForControl.construct(from.enhancedForControl(), context)
      } else {
        BasicForControl.construct(from, context)
      }
    cst.withContext(from, context)
  }
}

final case class EnhancedForControl(modifiers: Seq[Modifier], typeName: TypeName,
                                    id: Id, expression: Expression) extends ForControl {
  override def children(): List[CST] = id :: expression :: Nil

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
      ApexModifiers.construct(from.modifier().asScala, context),
      TypeRef.construct(from.typeRef()),
      Id.construct(from.id(), context),
      Expression.construct(from.expression(), context).withContext(from, context)
    )
  }
}

final case class BasicForControl(forInit: Option[ForInit], expression: Option[Expression], forUpdate: Option[ForUpdate]) extends ForControl {
  override def children(): List[CST] = List[CST]() ++ forInit ++ expression ++ forUpdate

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
      if (from.forInit() != null) {
        Some(ForInit.construct(from.forInit(), context))
      } else {
        None
      }
    val expression =
      if (from.expression() != null) {
        Some(Expression.construct(from.expression(), context))
      } else {
        None
      }
    val forUpdate =
      if (from.forUpdate() != null) {
        Some(ForUpdate.construct(from.forUpdate(), context))
      } else {
        None
      }
    BasicForControl(forInit, expression, forUpdate).withContext(from, context)
  }
}

sealed abstract class ForInit extends CST {
  def verify(context: BlockVerifyContext): Unit
  def addVars(context: BlockVerifyContext): Unit
}

final case class LocalVariableForInit(variable: LocalVariableDeclaration) extends ForInit {
  override def children(): List[CST] = variable :: Nil

  override def verify(context: BlockVerifyContext): Unit = {
    variable.verify(context)
  }

  override def addVars(context: BlockVerifyContext): Unit = {
    variable.addVars(context)
  }
}

final case class ExpressionListForInit(expressions: List[Expression]) extends ForInit {
  override def children(): List[CST] = expressions

  override def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
  }

  override def addVars(context: BlockVerifyContext): Unit = {
  }
}

object ForInit {
  def construct(from: ForInitContext, context: ConstructContext): ForInit = {
    val cst =
      if (from.localVariableDeclaration() != null) {
        LocalVariableForInit(LocalVariableDeclaration.construct(from.localVariableDeclaration(), context))
      } else if (from.expressionList() != null) {
        val expressions: Seq[ExpressionContext] = from.expressionList().expression().asScala
        ExpressionListForInit(Expression.construct(expressions.toList, context))
      } else {
        throw new CSTException
      }
    cst.withContext(from, context)
  }
}

final case class ForUpdate(expressions: List[Expression]) extends CST {
  override def children(): List[CST] = expressions

  def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
  }
}

object ForUpdate {
  def construct(from: ForUpdateContext, context: ConstructContext): ForUpdate = {
    val expressions: Seq[ExpressionContext] = from.expressionList().expression().asScala
    ForUpdate(Expression.construct(expressions.toList, context)).withContext(from, context)
  }
}

final case class WhileStatement(expression: Expression, statement: Statement) extends Statement {
  override def children(): List[CST] = {
    expression :: statement :: Nil
  }

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
  override def children(): List[CST] = {
    expression :: statement :: Nil
  }

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

final case class TryStatement(block: Block, catches: List[CatchClause], finallyBlock: Option[Block]) extends Statement {
  override def children(): List[CST] = List(block) ++ catches ++ finallyBlock

  override def verify(context: BlockVerifyContext): Unit = {
    block.verify(context)
    catches.foreach(_.verify(context))
    finallyBlock.foreach(_.verify(context))
  }
}

object TryStatement {
  def construct(from: TryStatementContext, context: ConstructContext): TryStatement = {
    val catches: List[CatchClauseContext] = if (from.catchClause() != null) from.catchClause().asScala.toList else List()
    TryStatement(Block.construct(from.block(), context), CatchClause.construct(catches, context),
      FinallyBlock.construct(from.finallyBlock(), context)).withContext(from, context)
  }
}

object FinallyBlock {
  def construct(from: FinallyBlockContext, context: ConstructContext): Option[Block] = {
    if (from != null) {
      Some(Block.construct(from.block(), context))
    } else {
      None
    }
  }
}

final case class CatchClause(modifiers: Seq[Modifier], qname: QualifiedName, id: String, block: Block) extends CST {
  override def children(): List[CST] = qname :: List(block)

  def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    blockContext.addVar(Name(id), qname.location, qname.asTypeName())
    block.verify(blockContext)
  }
}

object CatchClause {
  def construct(aList: List[CatchClauseContext], context: ConstructContext): List[CatchClause] = {
    if (aList != null)
      aList.map(x => CatchClause.construct(x, context))
    else
      List()
  }

  def construct(from: CatchClauseContext, context: ConstructContext): CatchClause = {
    CatchClause(
      ApexModifiers.construct(from.modifier().asScala, context),
      QualifiedName.construct(from.qualifiedName(), context),
      from.id().getText,
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }
}

final case class ReturnStatement(expression: Option[Expression]) extends Statement {
  override def children(): List[CST] = List() ++ expression

  override def verify(context: BlockVerifyContext): Unit = {
    expression.foreach(_.verify(context))
  }
}

object ReturnStatement {
  def construct(statement: ReturnStatementContext, context: ConstructContext): ReturnStatement = {
    val cst =
      if (statement.expression() != null) {
        ReturnStatement(Some(Expression.construct(statement.expression(), context)))
      } else {
        ReturnStatement(None)
      }
    cst.withContext(statement, context)
  }
}

final case class ThrowStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = Nil

  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object ThrowStatement {
  def construct(statement: ThrowStatementContext, context: ConstructContext): ThrowStatement = {
    ThrowStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class BreakStatement() extends Statement {
  override def children(): List[CST] = Nil

  override def verify(context: BlockVerifyContext): Unit = {}
}

object BreakStatement {
  def construct(statement: BreakStatementContext, context: ConstructContext): BreakStatement = {
    BreakStatement().withContext(statement, context)
  }
}

final case class ContinueStatement() extends Statement {
  override def children(): List[CST] = Nil

  override def verify(context: BlockVerifyContext): Unit = {}
}

object ContinueStatement {
  def construct(statement: ContinueStatementContext, context: ConstructContext): ContinueStatement = {
    ContinueStatement().withContext(statement, context)
  }
}

final case class InsertStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

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
  override def children(): List[CST] = expression :: Nil

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
  override def children(): List[CST] = expression :: Nil

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
  override def children(): List[CST] = expression :: Nil

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
  override def children(): List[CST] = expression :: Nil

  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    // Future: Verify Field
  }
}

object UpsertStatement {
  def construct(statement: UpsertStatementContext, context: ConstructContext): UpsertStatement = {
    val expression = Expression.construct(statement.expression(), context)
    val qualifiedName =
      if (statement.qualifiedName()==null)
        None
      else
        Some(QualifiedName.construct(statement.qualifiedName(), context))
    UpsertStatement(expression, qualifiedName).withContext(statement, context)
  }
}

final case class MergeStatement(expression1: Expression, expression2: Expression) extends Statement {
  override def children(): List[CST] = expression1 :: expression2 :: Nil

  override def verify(context: BlockVerifyContext): Unit = {
    expression1.verify(context)
    expression2.verify(context)
  }
}

object MergeStatement {
  def construct(statement: MergeStatementContext, context: ConstructContext): MergeStatement = {
    MergeStatement(Expression.construct(statement.expression(0), context), Expression.construct(statement.expression(1), context)).withContext(statement, context)
  }
}

final case class RunAsStatement(expressions: List[Expression], block: Option[Block]) extends Statement {
  override def children(): List[CST] = expressions ++ block

  override def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
    block.foreach(_.verify(context))
  }
}

object RunAsStatement {
  def construct(statement: RunAsStatementContext, context: ConstructContext): RunAsStatement = {
    val expressions =
      if (statement.expressionList() != null) {
        val e: Seq[ExpressionContext] = statement.expressionList().expression().asScala
        Expression.construct(e.toList, context)
      } else {
        List()
      }
    val block = if (statement.block() != null) Some(Block.construct(statement.block(), context)) else None
    RunAsStatement(expressions, block).withContext(statement, context)
  }
}

final case class ExpressionStatement(var expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

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
  def construct(statements: List[StatementContext], context: ConstructContext): List[Statement] = {
    statements.map(s => Statement.construct(s, context))
  }

  def construct(statement: StatementContext, context: ConstructContext): Statement = {
    val cst =
      if (statement.block() != null) {
        Block.construct(statement.block(), context)
      } else if (statement.localVariableDeclarationStatement() != null) {
        LocalVariableDeclarationStatement.construct(statement.localVariableDeclarationStatement(), context)
      } else if (statement.ifStatement() != null) {
        IfStatement.construct(statement.ifStatement(), context)
      } else if (statement.switchStatement() != null) {
        SwitchStatement.construct(statement.switchStatement(), context)
      } else if (statement.forStatement() != null) {
        ForStatement.construct(statement.forStatement(), context)
      } else if (statement.whileStatement() != null) {
        WhileStatement.construct(statement.whileStatement(), context)
      } else if (statement.doWhileStatement() != null) {
        DoWhileStatement.construct(statement.doWhileStatement(), context)
      } else if (statement.tryStatement() != null) {
        TryStatement.construct(statement.tryStatement(), context)
      } else if (statement.returnStatement() != null) {
        ReturnStatement.construct(statement.returnStatement(), context)
      } else if (statement.throwStatement() != null) {
        ThrowStatement.construct(statement.throwStatement(), context)
      } else if (statement.breakStatement() != null) {
        BreakStatement.construct(statement.breakStatement(), context)
      } else if (statement.continueStatement() != null) {
        ContinueStatement.construct(statement.continueStatement(), context)
      } else if (statement.insertStatement() != null) {
        InsertStatement.construct(statement.insertStatement(), context)
      } else if (statement.updateStatement() != null) {
        UpdateStatement.construct(statement.updateStatement(), context)
      } else if (statement.deleteStatement() != null) {
        DeleteStatement.construct(statement.deleteStatement(), context)
      } else if (statement.undeleteStatement() != null) {
        UndeleteStatement.construct(statement.undeleteStatement(), context)
      } else if (statement.upsertStatement() != null) {
        UpsertStatement.construct(statement.upsertStatement(), context)
      } else if (statement.mergeStatement() != null) {
        MergeStatement.construct(statement.mergeStatement(), context)
      } else if (statement.runAsStatement() != null) {
        RunAsStatement.construct(statement.runAsStatement(), context)
      } else if (statement.expressionStatement() != null) {
        ExpressionStatement.construct(statement.expressionStatement(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(statement, context)
  }
}
