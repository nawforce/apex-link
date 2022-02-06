/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.api.ServerOps
import com.nawforce.apexlink.cst.stmts.SwitchStatement
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.modifiers.{ApexModifiers, ModifierResults}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.runtime.parsers.{CodeParser, Source}

import java.lang.ref.WeakReference
import scala.collection.immutable.ArraySeq

trait Statement {
  def verify(context: BlockVerifyContext): Unit
}

// Treat Block as Statement for blocks in blocks
trait Block extends Statement

// Standard eager block
final case class EagerBlock(statements: Seq[Statement]) extends Block {
  override def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    statements.foreach(s => s.verify(blockContext))
  }
}

object EagerBlock {
  val empty = new EagerBlock(Seq())
}

// Lazy block, will re-parse when needed
final case class LazyBlock(
  source: Source,
  var blockContextRef: WeakReference[BlockContext],
  isTrigger: Boolean
) extends Block {
  private var statementsRef: WeakReference[Seq[Statement]] = _
  private var reParsed                                     = false

  override def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    statements().foreach(s => s.verify(blockContext))
    context.typePlugin.onBlockValidated(this, context.isStatic, blockContext)
  }

  def statements(): Seq[Statement] = {
    var statements = Option(statementsRef).map(_.get).orNull

    // If the statement WeakRef has gone stale we need to re-build them
    if (statements == null) {
      // If the block AST WeakRef has gone stale as well we need to re-parse first
      var statementContext = blockContextRef.get
      if (statementContext == null) {
        val parser = new CodeParser(source)
        val result = parser.parseBlock()
        result.issues.foreach(OrgImpl.log)
        statementContext = result.value
        blockContextRef = new WeakReference(statementContext)
        reParsed = true
      }

      // Now rebuild, making sure we put correct source in scope for CST to use
      val parsedSource = if (reParsed) source else source.outer.get
      CST.sourceContext.withValue(Some(parsedSource)) {
        val parser = new CodeParser(parsedSource)
        statementsRef = createStatements(statementContext, parser, isTrigger)
        statements = statementsRef.get
      }
    }
    statements
  }

  // Construct statements from AST
  private def createStatements(
    context: BlockContext,
    parser: CodeParser,
    isTrigger: Boolean
  ): WeakReference[Seq[Statement]] = {
    val statementContexts = CodeParser.toScala(context.statement())
    val statements        = Some(Statement.construct(parser, statementContexts, isTrigger))
    new WeakReference(statements.get)
  }
}

object Block {
  def constructLazy(
    parser: CodeParser,
    blockContext: BlockContext,
    isTrigger: Boolean = false
  ): Block = {
    if (ServerOps.getLazyBlocks) {
      LazyBlock(parser.extractSource(blockContext), new WeakReference(blockContext), isTrigger)
    } else {
      construct(parser, blockContext, isTrigger)
    }
  }

  def construct(parser: CodeParser, blockContext: BlockContext, isTrigger: Boolean): Block = {
    EagerBlock(Statement.construct(parser, CodeParser.toScala(blockContext.statement()), isTrigger))
  }

  def constructOption(parser: CodeParser, blockContext: Option[BlockContext]): Option[Block] = {
    blockContext.map(bc => constructLazy(parser, bc))
  }
}

final case class LocalVariableDeclarationStatement(
  localVariableDeclaration: LocalVariableDeclaration
) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    localVariableDeclaration.verify(context)
  }
}

object LocalVariableDeclarationStatement {
  def construct(
    parser: CodeParser,
    from: LocalVariableDeclarationStatementContext,
    isTrigger: Boolean
  ): LocalVariableDeclarationStatement = {
    LocalVariableDeclarationStatement(
      LocalVariableDeclaration.construct(parser, from.localVariableDeclaration(), isTrigger)
    )
  }
}

final case class IfStatement(expression: Expression, statements: Seq[Statement]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)

    // This is replicating a feature where non-block statements can pass declarations forward
    var stmtContext = new InnerBlockVerifyContext(context)
    statements.foreach(stmt => {
      val isBlock = stmt.isInstanceOf[Block]
      if (isBlock) {
        stmtContext = new InnerBlockVerifyContext(stmtContext)
      }
      stmt.verify(stmtContext)
      if (isBlock)
        context.typePlugin.onBlockValidated(stmt.asInstanceOf[Block], context.isStatic, stmtContext)
    })
  }
}

object IfStatement {
  def construct(parser: CodeParser, ifStatement: IfStatementContext): IfStatement = {
    val statements = CodeParser.toScala(ifStatement.statement())
    IfStatement(
      Expression.construct(ifStatement.parExpression().expression()),
      Statement.construct(parser, statements.toList, isTrigger = false)
    )
  }
}

final case class ForStatement(control: Option[ForControl], statement: Option[Statement])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    control.foreach(control => {
      val forContext = new InnerBlockVerifyContext(context)
      control.verify(forContext)

      val loopContext = new InnerBlockVerifyContext(forContext)
      control.addVars(loopContext)
      statement.foreach(_.verify(loopContext))
    })
  }
}

object ForStatement {
  def construct(parser: CodeParser, statement: ForStatementContext): ForStatement = {
    ForStatement(
      CodeParser.toScala(statement.forControl()).map(fc => ForControl.construct(parser, fc)),
      CodeParser
        .toScala(statement.statement())
        .flatMap(stmt => Statement.construct(parser, stmt, isTrigger = false))
    )
  }
}

sealed abstract class ForControl extends CST {
  def verify(context: BlockVerifyContext): Unit
  def addVars(context: BlockVerifyContext): Unit
}

object ForControl {
  def construct(parser: CodeParser, from: ForControlContext): ForControl = {
    val cst =
      CodeParser
        .toScala(from.enhancedForControl())
        .map(efc => EnhancedForControl.construct(efc))
        .getOrElse(BasicForControl.construct(parser, from))
    cst.withContext(from)
  }
}

final case class EnhancedForControl(typeName: TypeName, id: Id, expression: Expression)
    extends ForControl {
  override def verify(context: BlockVerifyContext): Unit = {
    id.validate()
    val forType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (forType.isEmpty)
      context.missingType(id.location, typeName)
    expression.verify(context)
  }

  def addVars(context: BlockVerifyContext): Unit = {
    context.addVar(id.name, this, typeName)
  }
}

object EnhancedForControl {
  def construct(from: EnhancedForControlContext): EnhancedForControl = {
    EnhancedForControl(
      TypeReference.construct(from.typeRef()),
      Id.construct(from.id()),
      Expression.construct(from.expression()).withContext(from)
    )
  }
}

final case class BasicForControl(
  forInit: Option[ForInit],
  expression: Option[Expression],
  forUpdate: Option[ForUpdate]
) extends ForControl {
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
  def construct(parser: CodeParser, from: ForControlContext): BasicForControl = {
    val forInit =
      CodeParser
        .toScala(from.forInit())
        .map(fi => ForInit.construct(parser, fi))
    val expression =
      CodeParser
        .toScala(from.expression())
        .map(e => Expression.construct(e))
    val forUpdate =
      CodeParser
        .toScala(from.forUpdate())
        .map(u => ForUpdate.construct(u))
    BasicForControl(forInit, expression, forUpdate).withContext(from)
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

final case class ExpressionListForInit(expressions: ArraySeq[Expression]) extends ForInit {
  override def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
  }

  override def addVars(context: BlockVerifyContext): Unit = {}
}

object ForInit {
  def construct(parser: CodeParser, from: ForInitContext): ForInit = {
    CodeParser
      .toScala(from.localVariableDeclaration())
      .map(
        lvd =>
          LocalVariableForInit(LocalVariableDeclaration.construct(parser, lvd, isTrigger = false))
      )
      .getOrElse({
        val expressions =
          CodeParser.toScala(CodeParser.toScala(from.expressionList()).get.expression())
        ExpressionListForInit(Expression.construct(expressions))
      })
      .withContext(from)
  }
}

final case class ForUpdate(expressions: ArraySeq[Expression]) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
  }
}

object ForUpdate {
  def construct(from: ForUpdateContext): ForUpdate = {
    val expressions = CodeParser.toScala(from.expressionList().expression())
    ForUpdate(Expression.construct(expressions)).withContext(from)
  }
}

final case class WhileStatement(expression: Expression, statement: Option[Statement])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    statement.foreach(_.verify(context))
  }
}

object WhileStatement {
  def construct(parser: CodeParser, statement: WhileStatementContext): WhileStatement = {
    WhileStatement(
      Expression.construct(statement.parExpression().expression()),
      Statement.construct(parser, statement.statement(), isTrigger = false)
    )
  }
}

final case class DoWhileStatement(statement: Option[Statement], expression: Option[Expression])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.foreach(_.verify(context))
    statement.foreach(_.verify(context))
  }
}

object DoWhileStatement {
  def construct(parser: CodeParser, statement: DoWhileStatementContext): DoWhileStatement = {
    DoWhileStatement(
      Statement.construct(parser, statement.statement(), isTrigger = false),
      Option(statement.parExpression())
        .map(parExpression => Expression.construct(parExpression.expression()))
    )
  }
}

final case class TryStatement(block: Block, catches: Seq[CatchClause], finallyBlock: Option[Block])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    block.verify(context)
    catches.foreach(_.verify(context))
    finallyBlock.foreach(_.verify(context))
  }
}

object TryStatement {
  def construct(parser: CodeParser, from: TryStatementContext): TryStatement = {
    val catches = CodeParser.toScala(from.catchClause())
    val finallyBlock =
      CodeParser
        .toScala(from.finallyBlock())
        .map(fb => Block.construct(parser, fb.block(), isTrigger = false))
    TryStatement(
      Block.construct(parser, from.block(), isTrigger = false),
      CatchClause.construct(parser, catches),
      finallyBlock
    )
  }
}

final case class CatchClause(
  modifiers: ModifierResults,
  qname: QualifiedName,
  id: String,
  block: Option[Block]
) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    modifiers.issues.foreach(context.log)

    block.foreach(block => {
      val blockContext      = new InnerBlockVerifyContext(context)
      val exceptionTypeName = qname.asTypeName()
      blockContext.getTypeAndAddDependency(exceptionTypeName, context.thisType) match {
        case Left(_) =>
          context.missingType(qname.location, exceptionTypeName)
        case Right(exceptionType) =>
          blockContext.addVar(Name(id), None, exceptionType)
          block.verify(blockContext)
      }
      context.typePlugin.onBlockValidated(block, context.isStatic, blockContext)
    })
  }
}

object CatchClause {
  def construct(parser: CodeParser, clauses: Seq[CatchClauseContext]): Seq[CatchClause] = {
    if (clauses != null)
      clauses.flatMap(x => CatchClause.construct(parser, x))
    else
      Seq()
  }

  def construct(parser: CodeParser, from: CatchClauseContext): Option[CatchClause] = {
    QualifiedName
      .construct(from.qualifiedName())
      .map(qualifiedName => {
        CatchClause(
          ApexModifiers.catchModifiers(parser, CodeParser.toScala(from.modifier()), from),
          qualifiedName,
          CodeParser.getText(from.id()),
          CodeParser
            .toScala(from.block())
            .map(block => Block.construct(parser, block, isTrigger = false))
        ).withContext(from)
      })
  }
}

final case class ReturnStatement(expression: Option[Expression]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.foreach(_.verify(context))
  }
}

object ReturnStatement {
  def construct(statement: ReturnStatementContext): ReturnStatement = {
    ReturnStatement(
      CodeParser
        .toScala(statement.expression())
        .map(e => Expression.construct(e))
    )
  }
}

final case class ThrowStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object ThrowStatement {
  def construct(statement: ThrowStatementContext): ThrowStatement = {
    ThrowStatement(Expression.construct(statement.expression()))
  }
}

final case class BreakStatement() extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {}
}

object BreakStatement {
  def construct(statement: BreakStatementContext): BreakStatement = {
    BreakStatement()
  }
}

final case class ContinueStatement() extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {}
}

object ContinueStatement {
  def construct(statement: ContinueStatementContext): ContinueStatement = {
    ContinueStatement()
  }
}

final case class InsertStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object InsertStatement {
  def construct(statement: InsertStatementContext): InsertStatement = {
    InsertStatement(Expression.construct(statement.expression()))
  }
}

final case class UpdateStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object UpdateStatement {
  def construct(statement: UpdateStatementContext): UpdateStatement = {
    UpdateStatement(Expression.construct(statement.expression()))
  }
}

final case class DeleteStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object DeleteStatement {
  def construct(statement: DeleteStatementContext): DeleteStatement = {
    DeleteStatement(Expression.construct(statement.expression()))
  }
}

final case class UndeleteStatement(expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
  }
}

object UndeleteStatement {
  def construct(statement: UndeleteStatementContext): UndeleteStatement = {
    UndeleteStatement(Expression.construct(statement.expression()))
  }
}

final case class UpsertStatement(expression: Expression, field: Option[QualifiedName])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression.verify(context)
    // Future: Verify Field
  }
}

object UpsertStatement {
  def construct(statement: UpsertStatementContext): Option[UpsertStatement] = {
    val expression = Expression.construct(statement.expression())
    CodeParser
      .toScala(statement.qualifiedName())
      .map(qualifiedName => QualifiedName.construct(qualifiedName))
      .map(qualifiedName => UpsertStatement(expression, qualifiedName))
  }
}

final case class MergeStatement(expression1: Expression, expression2: Expression)
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expression1.verify(context)
    expression2.verify(context)
  }
}

object MergeStatement {
  def construct(statement: MergeStatementContext): MergeStatement = {
    val expressions = CodeParser.toScala(statement.expression())
    MergeStatement(Expression.construct(expressions.head), Expression.construct(expressions(1)))
  }
}

final case class RunAsStatement(expressions: ArraySeq[Expression], block: Option[Block])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
    block.foreach(_.verify(context))
  }
}

object RunAsStatement {
  def construct(parser: CodeParser, statement: RunAsStatementContext): RunAsStatement = {
    val expressions: ArraySeq[Expression] =
      CodeParser
        .toScala(statement.expressionList())
        .map(el => Expression.construct(CodeParser.toScala(el.expression())))
        .getOrElse(Expression.emptyExpressions)
    val block =
      CodeParser
        .toScala(statement.block())
        .map(b => Block.construct(parser, b, isTrigger = false))
    RunAsStatement(expressions, block)
  }
}

final case class ExpressionStatement(var expression: Expression) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    // Future: What causes 'expression can not be a statement' error
    expression.verify(context)
  }
}

object ExpressionStatement {
  def construct(statement: ExpressionStatementContext): ExpressionStatement = {
    ExpressionStatement(Expression.construct(statement.expression()))
  }
}

object Statement {
  def construct(
    parser: CodeParser,
    statements: Seq[StatementContext],
    isTrigger: Boolean
  ): Seq[Statement] = {
    statements.flatMap(s => Statement.construct(parser, s, isTrigger))
  }

  def construct(
    parser: CodeParser,
    statement: StatementContext,
    isTrigger: Boolean
  ): Option[Statement] = {
    CodeParser
      .toScala(statement.block())
      .map(x => Block.construct(parser, x, isTrigger = false))
      .orElse(
        CodeParser
          .toScala(statement.localVariableDeclarationStatement())
          .map(x => LocalVariableDeclarationStatement.construct(parser, x, isTrigger))
      )
      .orElse(
        CodeParser
          .toScala(statement.ifStatement())
          .map(x => IfStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.switchStatement())
          .map(x => SwitchStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.forStatement())
          .map(x => ForStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.whileStatement())
          .map(x => WhileStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.doWhileStatement())
          .map(x => DoWhileStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.tryStatement())
          .map(x => TryStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.returnStatement())
          .map(x => ReturnStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.throwStatement())
          .map(x => ThrowStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.breakStatement())
          .map(x => BreakStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.continueStatement())
          .map(x => ContinueStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.insertStatement())
          .map(x => InsertStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.updateStatement())
          .map(x => UpdateStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.deleteStatement())
          .map(x => DeleteStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.undeleteStatement())
          .map(x => UndeleteStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.upsertStatement())
          .flatMap(x => UpsertStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.mergeStatement())
          .map(x => MergeStatement.construct(x))
      )
      .orElse(
        CodeParser
          .toScala(statement.runAsStatement())
          .map(x => RunAsStatement.construct(parser, x))
      )
      .orElse(
        CodeParser
          .toScala(statement.expressionStatement())
          .map(x => ExpressionStatement.construct(x))
      )
      .orElse({
        // Parsing failed
        None
      })
  }
}
