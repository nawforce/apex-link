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

import com.nawforce.parsers.ApexParser._
import com.nawforce.types.{ApexModifiers, ApexTypeDeclaration, Modifier, TypeName}
import com.nawforce.utils.IssueLog
import org.antlr.v4.runtime.misc.Interval

import scala.collection.JavaConverters._
import scala.ref.WeakReference

trait Statement extends CST {
  def resolve(context: ResolveStmtContext)
}

// Treat Block as Statement for blocks in blocks
final case class Block(path: Path, bytes: Array[Byte]) extends CST with Statement {
  private var statementsRef: WeakReference[List[Statement]] = WeakReference(null)

  def statements(): List[Statement] = {
    if (statementsRef.get.isEmpty) {
      val blockContext = ApexTypeDeclaration.parseBlock(path, new ByteArrayInputStream(bytes))
      if (blockContext.nonEmpty) {
        val statementContexts: Seq[StatementContext] = blockContext.get.statement().asScala
        statementsRef = WeakReference(Statement.construct(statementContexts.toList, new ConstructContext))
      }
    }
    statementsRef.get.getOrElse(List())
  }

  override def children(): List[CST] = statements()

  override def resolve(context: ResolveStmtContext): Unit = {
    context.pushBlock()
    statements().foreach(_.resolve(context))
    context.popBlock()
  }
}

object Block {
  def construct(blockContext: BlockContext, context: ConstructContext): Block = {
    val is = blockContext.start.getInputStream
    val text = is.getText(new Interval(blockContext.start.getStartIndex, blockContext.stop.getStopIndex))
    Block(IssueLog.context.value, text.getBytes())
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

  override def resolve(context: ResolveStmtContext): Unit = localVariableDeclaration.resolve(context)
}

object LocalVariableDeclarationStatement {
  def construct(from: LocalVariableDeclarationStatementContext, context: ConstructContext): LocalVariableDeclarationStatement = {
    LocalVariableDeclarationStatement(LocalVariableDeclaration.construct(from.localVariableDeclaration(), context)).withContext(from, context)
  }
}

final case class IfStatement(expression: Expression, statements: List[Statement]) extends Statement {
  override def children(): List[CST] = expression :: statements

  override def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    context.pushBlock()
    statements.foreach(_.resolve(context))
    context.popBlock()
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

  def resolve(context: ResolveStmtContext): Unit = {
    val erc = new ResolveExprContext(context)
    expressions.foreach(_.resolve(erc))
    block.resolve(context)
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

  override def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    whenControls.foreach(_.resolve(context))
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

  override def resolve(context: ResolveStmtContext): Unit = {
    context.pushBlock()
    control.resolve(context)
    statement.resolve(context)
    context.popBlock()
  }
}

object ForStatement {
  def construct(statement: ForStatementContext, context: ConstructContext): ForStatement = {
    ForStatement(ForControl.construct(statement.forControl(), context), Statement.construct(statement.statement(), context)).withContext(statement, context)
  }
}

sealed abstract class ForControl extends CST {
  def resolve(context: ResolveStmtContext): Unit
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

final case class EnhancedForControl(modifiers: Seq[Modifier], typeRef: TypeName,
                                    id: Identifier, expression: Expression) extends ForControl with VarIntroducer {
  override def children(): List[CST] = id :: expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    context.addVarDeclaration(VarDeclaration(id, typeRef, this))
  }
}

object EnhancedForControl {
  def construct(from: EnhancedForControlContext, context: ConstructContext): EnhancedForControl = {
    EnhancedForControl(
      ApexModifiers.construct(from.modifier().asScala, context),
      TypeRef.construct(from.typeRef(), context),
      Identifier(from.id().getText),
      Expression.construct(from.expression(), context).withContext(from, context)
    )
  }
}

final case class BasicForControl(forInit: Option[ForInit], expression: Option[Expression], forUpdate: Option[ForUpdate]) extends ForControl {
  override def children(): List[CST] = List[CST]() ++ forInit ++ expression ++ forUpdate

  def resolve(context: ResolveStmtContext): Unit = {
    forInit.foreach(_.resolve(context))
    expression.foreach(_.resolve(new ResolveExprContext(context)))
    forUpdate.foreach(_.resolve(context))
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
  def resolve(context: ResolveStmtContext): Unit
}

final case class LocalVariableForInit(variable: LocalVariableDeclaration) extends ForInit {
  override def children(): List[CST] = variable :: Nil

  def resolve(context: ResolveStmtContext): Unit = variable.resolve(context)
}

final case class ExpressionListForInit(expressions: List[Expression]) extends ForInit {
  override def children(): List[CST] = expressions

  def resolve(context: ResolveStmtContext): Unit = expressions.foreach(_.resolve(new ResolveExprContext(context)))
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

  def resolve(context: ResolveStmtContext): Unit = expressions.foreach(_.resolve(new ResolveExprContext(context)))
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

  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    statement.resolve(context)
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

  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    statement.resolve(context)
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

  def resolve(context: ResolveStmtContext): Unit = {
    block.resolve(context)
    catches.foreach(_.resolve(context))
    finallyBlock.foreach(_.resolve(context))
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

final case class CatchType(names: List[QualifiedName]) extends CST {
  override def children(): List[CST] = {
    names
  }
}

object CatchType {
  def construct(from: CatchTypeContext, context: ConstructContext): CatchType = {
    val names: Seq[QualifiedNameContext] = from.qualifiedName().asScala
    CatchType(QualifiedName.construct(names.toList, context)).withContext(from, context)
  }
}

final case class CatchClause(modifiers: Seq[Modifier], catchType: CatchType, id: String, block: Block) extends CST {
  override def children(): List[CST] = List(catchType) ++ List(block)

  def resolve(context: ResolveStmtContext): Unit = {
    block.resolve(context)
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
      CatchType.construct(from.catchType(), context),
      from.id().getText,
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }

}

final case class ReturnStatement(expression: Option[Expression]) extends Statement {
  override def children(): List[CST] = List() ++ expression

  def resolve(context: ResolveStmtContext): Unit = expression.foreach(_.resolve(new ResolveExprContext(context)))
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

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object ThrowStatement {
  def construct(statement: ThrowStatementContext, context: ConstructContext): ThrowStatement = {
    ThrowStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class BreakStatement(id: String) extends Statement {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object BreakStatement {
  def construct(statement: BreakStatementContext, context: ConstructContext): BreakStatement = {
    val cst =
      if (statement.id() != null) {
        BreakStatement(statement.id.getText)
      } else {
        BreakStatement(null)
      }
    cst.withContext(statement, context)
  }
}

final case class ContinueStatement(id: String) extends Statement {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object ContinueStatement {
  def construct(statement: ContinueStatementContext, context: ConstructContext): ContinueStatement = {
    val cst =
      if (statement.id() != null) {
        ContinueStatement(statement.id.getText)
      } else {
        ContinueStatement(null)
      }
    cst.withContext(statement, context)
  }
}

final case class InsertStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object InsertStatement {
  def construct(statement: InsertStatementContext, context: ConstructContext): InsertStatement = {
    InsertStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class UpdateStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object UpdateStatement {
  def construct(statement: UpdateStatementContext, context: ConstructContext): UpdateStatement = {
    UpdateStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class DeleteStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object DeleteStatement {
  def construct(statement: DeleteStatementContext, context: ConstructContext): DeleteStatement = {
    DeleteStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class UndeleteStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object UndeleteStatement {
  def construct(statement: UndeleteStatementContext, context: ConstructContext): UndeleteStatement = {
    UndeleteStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class UpsertStatement(expression: Expression, field: Option[QualifiedName]) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
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

  def resolve(context: ResolveStmtContext): Unit = {
    expression1.resolve(new ResolveExprContext(context))
    expression2.resolve(new ResolveExprContext(context))
  }
}

object MergeStatement {
  def construct(statement: MergeStatementContext, context: ConstructContext): MergeStatement = {
    MergeStatement(Expression.construct(statement.expression(0), context), Expression.construct(statement.expression(1), context)).withContext(statement, context)
  }
}

final case class RunAsStatement(expressions: List[Expression], block: Option[Block]) extends Statement {
  override def children(): List[CST] = expressions ++ block

  def resolve(context: ResolveStmtContext): Unit = {
    expressions.foreach(_.resolve(new ResolveExprContext(context)))
    block.foreach(_.resolve(context))
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

final case class EmptyStatement() extends Statement {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object EmptyStatement {
  def construct(statement: EmptyStatementContext, context: ConstructContext): EmptyStatement = {
    EmptyStatement().withContext(statement, context)
  }
}

final case class ExpressionStatement(var expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))

    // Link var assignment to declaration
    expression match {
      case BinaryExpression(PrimaryExpression(VarRef(decl)), rhs, "=") => decl.addAssign(rhs)
      case _ =>
    }
  }
}

object ExpressionStatement {
  def construct(statement: ExpressionStatementContext, context: ConstructContext): ExpressionStatement = {
    ExpressionStatement(Expression.construct(statement.expression(), context)).withContext(statement, context)
  }
}

final case class IdStatement(id: String, statement: Statement) extends Statement {
  override def children(): List[CST] = statement :: Nil

  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object IdStatement {
  def construct(statement: IdStatementContext, context: ConstructContext): IdStatement = {
    IdStatement(statement.id().getText, Statement.construct(statement.statement(), context)).withContext(statement, context)
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
      } else if (statement.emptyStatement() != null) {
        EmptyStatement.construct(statement.emptyStatement(), context)
      } else if (statement.expressionStatement() != null) {
        ExpressionStatement.construct(statement.expressionStatement(), context)
      } else if (statement.idStatement() != null) {
        IdStatement.construct(statement.idStatement(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(statement, context)
  }
}
