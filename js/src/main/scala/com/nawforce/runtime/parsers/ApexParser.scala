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
package com.nawforce.runtime.parsers

import com.nawforce.runtime.parsers.ApexParser.{BlockContext, CompilationUnitContext, LiteralContext, TriggerUnitContext, TypeRefContext}
import com.nawforce.runtime.parsers.antlr.{CommonTokenStream, ParserRuleContext, TerminalNode}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("apex-parser", "ApexParser")
class ApexParser(tokens: CommonTokenStream) extends js.Object {

  def removeErrorListeners(): Unit = js.native
  def addErrorListener(listener: ThrowingErrorListener): Unit = js.native

  def compilationUnit(): CompilationUnitContext = js.native
  def triggerUnit(): TriggerUnitContext = js.native
  def block(): BlockContext = js.native
  def typeRef(): TypeRefContext = js.native

  def literal(): LiteralContext = js.native
}

object ApexParser {

  @js.native
  @JSImport("apex-parser", "TriggerUnitContext")
  class TriggerUnitContext extends ParserRuleContext {
    def id(): js.Array[IdContext] = js.native
    def triggerCase(): js.Array[TriggerCaseContext] = js.native
    def block(): BlockContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "TriggerCaseContext")
  class TriggerCaseContext extends ParserRuleContext {
    def BEFORE(): js.UndefOr[TerminalNode] = js.native
    def AFTER(): js.UndefOr[TerminalNode] = js.native
    def INSERT(): js.UndefOr[TerminalNode] = js.native
    def UPDATE(): js.UndefOr[TerminalNode] = js.native
    def DELETE(): js.UndefOr[TerminalNode] = js.native
    def UNDELETE(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "CompilationUnitContext")
  class CompilationUnitContext extends ParserRuleContext {
    def typeDeclaration(): TypeDeclarationContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "IdContext")
  class IdContext extends ParserRuleContext

  @js.native
  @JSImport("apex-parser", "AnyIdContext")
  class AnyIdContext extends ParserRuleContext

  @js.native
  @JSImport("apex-parser", "BlockContext")
  class BlockContext extends ParserRuleContext {
    def statement(): js.Array[StatementContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "QualifiedNameContext")
  class QualifiedNameContext extends ParserRuleContext {
    def id(): js.Array[IdContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "AnnotationContext")
  class AnnotationContext extends ParserRuleContext {
    def elementValue(): js.UndefOr[ElementValueContext] = js.native
    def elementValuePairs(): js.UndefOr[ElementValuePairsContext] = js.native
    def qualifiedName(): QualifiedNameContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ElementValueContext")
  class ElementValueContext extends ParserRuleContext {
    def expression(): js.UndefOr[ExpressionContext] = js.native
    def annotation(): js.UndefOr[AnnotationContext] = js.native
    def elementValueArrayInitializer(): js.UndefOr[ElementValueArrayInitializerContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ElementValuePairsContext")
  class ElementValuePairsContext extends ParserRuleContext {
    def elementValuePair(): js.Array[ElementValuePairContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ExpressionContext")
  class ExpressionContext extends ParserRuleContext {
  }

  @js.native
  @JSImport("apex-parser", "ElementValueArrayInitializerContext")
  class ElementValueArrayInitializerContext extends ParserRuleContext {
    def elementValue(): js.Array[ElementValueContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ElementValuePairContext")
  class ElementValuePairContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def elementValue(): ElementValueContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "LiteralContext")
  class LiteralContext extends ParserRuleContext {
    def IntegerLiteral(): js.UndefOr[TerminalNode] = js.native
    def NumberLiteral(): js.UndefOr[TerminalNode] = js.native
    def StringLiteral(): js.UndefOr[TerminalNode] = js.native
    def BooleanLiteral(): js.UndefOr[TerminalNode] = js.native
    def NULL(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ModifierContext")
  class ModifierContext extends ParserRuleContext {
    def annotation(): js.UndefOr[AnnotationContext] = js.native
    def GLOBAL(): js.UndefOr[TerminalNode] = js.native
    def PUBLIC(): js.UndefOr[TerminalNode] = js.native
    def PROTECTED(): js.UndefOr[TerminalNode] = js.native
    def PRIVATE(): js.UndefOr[TerminalNode] = js.native
    def TRANSIENT(): js.UndefOr[TerminalNode] = js.native
    def STATIC(): js.UndefOr[TerminalNode] = js.native
    def ABSTRACT(): js.UndefOr[TerminalNode] = js.native
    def FINAL(): js.UndefOr[TerminalNode] = js.native
    def WEBSERVICE(): js.UndefOr[TerminalNode] = js.native
    def OVERRIDE(): js.UndefOr[TerminalNode] = js.native
    def VIRTUAL(): js.UndefOr[TerminalNode] = js.native
    def TESTMETHOD(): js.UndefOr[TerminalNode] = js.native
    def WITH(): js.UndefOr[TerminalNode] = js.native
    def SHARING(): js.UndefOr[TerminalNode] = js.native
    def WITHOUT(): js.UndefOr[TerminalNode] = js.native
    def INHERITED(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "PrimaryContext")
  class PrimaryContext extends ParserRuleContext {
  }

  @js.native
  @JSImport("apex-parser", "SubPrimaryContext")
  class SubPrimaryContext extends PrimaryContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ThisPrimaryContext")
  class ThisPrimaryContext extends PrimaryContext {
  }

  @js.native
  @JSImport("apex-parser", "SuperPrimaryContext")
  class SuperPrimaryContext extends PrimaryContext {
  }

  @js.native
  @JSImport("apex-parser", "LiteralPrimaryContext")
  class LiteralPrimaryContext extends PrimaryContext {
    def literal(): LiteralContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "TypeRefPrimaryContext")
  class TypeRefPrimaryContext extends PrimaryContext {
    def typeRef(): TypeRefContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "IdPrimaryContext")
  class IdPrimaryContext extends PrimaryContext {
    def id(): IdContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "SoqlPrimaryContext")
  class SoqlPrimaryContext extends PrimaryContext {
  }

  @js.native
  @JSImport("apex-parser", "TypeRefContext")
  class TypeRefContext extends PrimaryContext {
    def arraySubscripts(): ArraySubscriptsContext = js.native
    def typeName(): js.Array[TypeNameContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "TypeArgumentsContext")
  class TypeArgumentsContext extends PrimaryContext {
    def typeList(): TypeListContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "TypeListContext")
  class TypeListContext extends PrimaryContext {
    def typeRef(): js.Array[TypeRefContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "TypeNameContext")
  class TypeNameContext extends PrimaryContext {
    def id(): IdContext = js.native
    def LIST(): js.UndefOr[TerminalNode] = js.native
    def SET(): js.UndefOr[TerminalNode] = js.native
    def MAP(): js.UndefOr[TerminalNode] = js.native
    def typeArguments(): js.UndefOr[TypeArgumentsContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ArraySubscriptsContext")
  class ArraySubscriptsContext extends PrimaryContext {
  }

  @js.native
  @JSImport("apex-parser", "PropertyDeclarationContext")
  class PropertyDeclarationContext extends PrimaryContext {
    def typeRef(): TypeRefContext = js.native
    def id(): IdContext = js.native
    def propertyBlock(): js.Array[PropertyBlockContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "PropertyBlockContext")
  class PropertyBlockContext extends ParserRuleContext {
    def getter(): js.UndefOr[GetterContext] = js.native
    def setter(): js.UndefOr[SetterContext] = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "GetterContext")
  class GetterContext extends ParserRuleContext {
    def block(): js.UndefOr[BlockContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "GetterContext")
  class SetterContext extends ParserRuleContext {
    def block(): js.UndefOr[BlockContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "CreatedNameContext")
  class CreatedNameContext extends ParserRuleContext {
    def idCreatedNamePair(): js.Array[IdCreatedNamePairContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "IdCreatedNamePairContext")
  class IdCreatedNamePairContext extends ParserRuleContext {
    def anyId(): AnyIdContext = js.native
    def typeList(): js.UndefOr[TypeListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "CreatorContext")
  class CreatorContext extends ParserRuleContext {
    def createdName(): CreatedNameContext = js.native
    def noRest(): js.UndefOr[NoRestContext] = js.native
    def classCreatorRest(): js.UndefOr[ClassCreatorRestContext] = js.native
    def arrayCreatorRest(): js.UndefOr[ArrayCreatorRestContext] = js.native
    def mapCreatorRest(): js.UndefOr[MapCreatorRestContext] = js.native
    def setCreatorRest(): js.UndefOr[SetCreatorRestContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "NoRestContext")
  class NoRestContext extends ParserRuleContext {
  }

  @js.native
  @JSImport("apex-parser", "ClassCreatorRestContext")
  class ClassCreatorRestContext extends ParserRuleContext {
    def arguments(): ArgumentsContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ArrayCreatorRestContext")
  class ArrayCreatorRestContext extends ParserRuleContext {
    def expression(): js.UndefOr[ExpressionContext] = js.native
    def arrayInitializer(): js.UndefOr[ArrayInitializerContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ArrayInitializerContext")
  class ArrayInitializerContext extends ParserRuleContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MapCreatorRestContext")
  class MapCreatorRestContext extends ParserRuleContext {
    def mapCreatorRestPair(): js.Array[MapCreatorRestPairContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MapCreatorRestPairContext")
  class MapCreatorRestPairContext extends ParserRuleContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "SetCreatorRestContext")
  class SetCreatorRestContext extends ParserRuleContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ArgumentsContext")
  class ArgumentsContext extends ParserRuleContext {
    def expressionList(): js.UndefOr[ExpressionListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "VariableDeclaratorsContext")
  class VariableDeclaratorsContext extends ParserRuleContext {
    def variableDeclarator(): js.Array[VariableDeclaratorContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "VariableDeclaratorContext")
  class VariableDeclaratorContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def expression(): js.UndefOr[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "LocalVariableDeclarationContext")
  class LocalVariableDeclarationContext extends ParserRuleContext {
    def typeRef(): TypeRefContext = js.native
    def variableDeclarators(): VariableDeclaratorsContext = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MethodCallContext")
  class MethodCallContext extends ExpressionContext {
    def id(): js.UndefOr[IdContext] = js.native
    def THIS(): js.UndefOr[TerminalNode] = js.native
    def expressionList(): js.UndefOr[ExpressionListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MethodCallContext")
  class DotMethodCallContext extends ExpressionContext {
    def anyId(): AnyIdContext = js.native
    def expressionList(): js.UndefOr[ExpressionListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ExpressionListContext")
  class ExpressionListContext extends ParserRuleContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "DotExpressionContext")
  class DotExpressionContext extends ExpressionContext {
    def expression(): ExpressionContext = js.native
    def anyId(): js.UndefOr[AnyIdContext] = js.native
    def dotMethodCall(): js.UndefOr[DotMethodCallContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MethodCallExpressionContext")
  class MethodCallExpressionContext extends ExpressionContext {
    def methodCall(): MethodCallContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "NewExpressionContext")
  class NewExpressionContext extends ExpressionContext {
    def creator(): CreatorContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ArrayExpressionContext")
  class ArrayExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "CastExpressionContext")
  class CastExpressionContext extends ExpressionContext {
    def typeRef(): TypeRefContext = js.native
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "PostOpExpressionContext")
  class PostOpExpressionContext extends ExpressionContext {
    def expression(): ExpressionContext = js.native
    def INC(): js.UndefOr[TerminalNode] = js.native
    def DEC(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "PreOpExpressionContext")
  class PreOpExpressionContext extends ExpressionContext {
    def expression(): ExpressionContext = js.native
    def ADD(): js.UndefOr[TerminalNode] = js.native
    def DEC(): js.UndefOr[TerminalNode] = js.native
    def INC(): js.UndefOr[TerminalNode] = js.native
    def SUB(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "NegExpressionContext")
  class NegExpressionContext extends ExpressionContext {
    def expression(): ExpressionContext = js.native
    def BANG(): js.UndefOr[TerminalNode] = js.native
    def TILDE(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "Arth1ExpressionContext")
  class Arth1ExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
    def DIV(): js.UndefOr[TerminalNode] = js.native
    def MOD(): js.UndefOr[TerminalNode] = js.native
    def MUL(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "Arth2ExpressionContext")
  class Arth2ExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
    def ADD(): js.UndefOr[TerminalNode] = js.native
    def SUB(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "BitExpressionContext")
  class BitExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
    def GT(): js.Array[TerminalNode] = js.native
    def LT(): js.Array[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "CmpExpressionContext")
  class CmpExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
    def GT(): js.UndefOr[TerminalNode] = js.native
    def LT(): js.UndefOr[TerminalNode] = js.native
    def ASSIGN(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "InstanceOfExpressionContext")
  class InstanceOfExpressionContext extends ExpressionContext {
    def expression(): ExpressionContext = js.native
    def typeRef(): TypeRefContext  = js.native
  }

  @js.native
  @JSImport("apex-parser", "EqualityExpressionContext")
  class EqualityExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
    def EQUAL(): js.UndefOr[TerminalNode] = js.native
    def LESSANDGREATER(): js.UndefOr[TerminalNode] = js.native
    def NOTEQUAL(): js.UndefOr[TerminalNode] = js.native
    def TRIPLEEQUAL(): js.UndefOr[TerminalNode] = js.native
    def TRIPLENOTEQUAL(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "BitAndExpressionContext")
  class BitAndExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "BitNotExpressionContext")
  class BitNotExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "BitOrExpressionContext")
  class BitOrExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "LogAndExpressionContext")
  class LogAndExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "LogOrExpressionContext")
  class LogOrExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "CondExpressionContext")
  class CondExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "AssignExpressionContext")
  class AssignExpressionContext extends ExpressionContext {
    def expression(): js.Array[ExpressionContext] = js.native
    def ADD_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def AND_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def DIV_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def LSHIFT_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def MOD_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def MUL_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def OR_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def RSHIFT_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def SUB_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def URSHIFT_ASSIGN(): js.UndefOr[TerminalNode] = js.native
    def XOR_ASSIGN(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "PrimaryExpressionContext")
  class PrimaryExpressionContext extends ExpressionContext {
    def primary(): PrimaryContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "StatementContext")
  class StatementContext extends ParserRuleContext {
    def block(): js.UndefOr[BlockContext] = js.native
    def ifStatement(): js.UndefOr[IfStatementContext] = js.native
    def switchStatement(): js.UndefOr[SwitchStatementContext] = js.native
    def forStatement(): js.UndefOr[ForStatementContext] = js.native
    def whileStatement(): js.UndefOr[WhileStatementContext] = js.native
    def doWhileStatement(): js.UndefOr[DoWhileStatementContext] = js.native
    def tryStatement(): js.UndefOr[TryStatementContext] = js.native
    def returnStatement(): js.UndefOr[ReturnStatementContext] = js.native
    def throwStatement(): js.UndefOr[ThrowStatementContext] = js.native
    def breakStatement(): js.UndefOr[BreakStatementContext] = js.native
    def continueStatement(): js.UndefOr[ContinueStatementContext] = js.native
    def insertStatement(): js.UndefOr[InsertStatementContext] = js.native
    def updateStatement(): js.UndefOr[UpdateStatementContext] = js.native
    def deleteStatement(): js.UndefOr[DeleteStatementContext]  = js.native
    def undeleteStatement(): js.UndefOr[UndeleteStatementContext] = js.native
    def upsertStatement(): js.UndefOr[UpsertStatementContext] = js.native
    def mergeStatement(): js.UndefOr[MergeStatementContext] = js.native
    def runAsStatement(): js.UndefOr[RunAsStatementContext] = js.native
    def localVariableDeclarationStatement(): js.UndefOr[LocalVariableDeclarationStatementContext] = js.native
    def expressionStatement(): js.UndefOr[ExpressionStatementContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "IfStatementContext")
  class IfStatementContext extends ParserRuleContext {
    def parExpression(): ParExpressionContext = js.native
    def statement(): js.Array[StatementContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ParExpressionContext")
  class ParExpressionContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "WhenControlContext")
  class WhenControlContext extends ParserRuleContext {
    def whenValue(): js.UndefOr[WhenValueContext] = js.native
    def block(): BlockContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "WhenLiteralContext")
  class WhenLiteralContext extends ParserRuleContext {
    def id(): js.UndefOr[IdContext] = js.native
    def StringLiteral(): js.UndefOr[TerminalNode] = js.native
    def IntegerLiteral(): js.UndefOr[TerminalNode] = js.native
    def SUB(): js.UndefOr[TerminalNode] = js.native
    def NULL(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "WhenValueContext")
  class WhenValueContext extends ParserRuleContext {
    def whenLiteral(): js.Array[WhenLiteralContext] = js.native
    def id(): js.Array[IdContext] = js.native
    def ELSE(): js.UndefOr[TerminalNode] = js.native
  }

  @js.native
  @JSImport("apex-parser", "SwitchStatementContext")
  class SwitchStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
    def whenControl(): js.Array[WhenControlContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ForStatementContext")
  class ForStatementContext extends ParserRuleContext {
    def forControl(): ForControlContext = js.native
    def statement(): StatementContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ForControlContext")
  class ForControlContext extends ParserRuleContext {
    def enhancedForControl(): js.UndefOr[EnhancedForControlContext] = js.native
    def forInit(): js.UndefOr[ForInitContext] = js.native
    def expression(): js.UndefOr[ExpressionContext] = js.native
    def forUpdate(): js.UndefOr[ForUpdateContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "EnhancedForControlContext")
  class EnhancedForControlContext extends ParserRuleContext {
    def typeRef(): TypeRefContext = js.native
    def id(): IdContext = js.native
    def expression(): ExpressionContext = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ForInitContext")
  class ForInitContext extends ParserRuleContext {
    def localVariableDeclaration(): js.UndefOr[LocalVariableDeclarationContext] = js.native
    def expressionList(): js.UndefOr[ExpressionListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ForUpdateContext")
  class ForUpdateContext extends ParserRuleContext {
    def expressionList(): ExpressionListContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "WhileStatementContext")
  class WhileStatementContext extends ParserRuleContext {
    def parExpression(): ParExpressionContext = js.native
    def statement(): StatementContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "DoWhileStatementContext")
  class DoWhileStatementContext extends ParserRuleContext {
    def statement(): StatementContext = js.native
    def parExpression(): ParExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "TryStatementContext")
  class TryStatementContext extends ParserRuleContext {
    def block(): BlockContext = js.native
    def finallyBlock(): js.UndefOr[FinallyBlockContext] = js.native
    def catchClause(): js.Array[CatchClauseContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "FinallyBlockContext")
  class FinallyBlockContext extends ParserRuleContext {
    def block(): BlockContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "CatchClauseContext")
  class CatchClauseContext extends ParserRuleContext {
    def qualifiedName(): QualifiedNameContext= js.native
    def id(): IdContext = js.native
    def block(): BlockContext = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ReturnStatementContext")
  class ReturnStatementContext extends ParserRuleContext {
    def expression(): js.UndefOr[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ThrowStatementContext")
  class ThrowStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ContinueStatementContext")
  class ContinueStatementContext extends ParserRuleContext {
  }

  @js.native
  @JSImport("apex-parser", "BreakStatementContext")
  class BreakStatementContext extends ParserRuleContext {
  }

  @js.native
  @JSImport("apex-parser", "InsertStatementContext")
  class InsertStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "UpdateStatementContext")
  class UpdateStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "DeleteStatementContext")
  class DeleteStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "UndeleteStatementContext")
  class UndeleteStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "UpsertStatementContext")
  class UpsertStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
    def qualifiedName(): js.UndefOr[QualifiedNameContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MergeStatementContext")
  class MergeStatementContext extends ParserRuleContext {
    def expression(): js.Array[ExpressionContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "RunAsStatementContext")
  class RunAsStatementContext extends ParserRuleContext {
    def expressionList(): js.UndefOr[ExpressionListContext] = js.native
    def block(): js.UndefOr[BlockContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ExpressionStatementContext")
  class ExpressionStatementContext extends ParserRuleContext {
    def expression(): ExpressionContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "LocalVariableDeclarationStatementContext")
  class LocalVariableDeclarationStatementContext extends ParserRuleContext {
    def localVariableDeclaration(): LocalVariableDeclarationContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "MemberDeclarationContext")
  class MemberDeclarationContext extends ParserRuleContext {
    def methodDeclaration(): js.UndefOr[MethodDeclarationContext] = js.native
    def fieldDeclaration(): js.UndefOr[FieldDeclarationContext] = js.native
    def constructorDeclaration(): js.UndefOr[ConstructorDeclarationContext] = js.native
    def interfaceDeclaration(): js.UndefOr[InterfaceDeclarationContext] = js.native
    def classDeclaration(): js.UndefOr[ClassDeclarationContext] = js.native
    def enumDeclaration(): js.UndefOr[EnumDeclarationContext] = js.native
    def propertyDeclaration(): js.UndefOr[PropertyDeclarationContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "InterfaceDeclarationContext")
  class InterfaceDeclarationContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def interfaceBody(): InterfaceBodyContext = js.native
    def typeList(): js.UndefOr[TypeListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ClassDeclarationContext")
  class ClassDeclarationContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def classBody(): ClassBodyContext = js.native
    def typeRef(): js.UndefOr[TypeRefContext] = js.native
    def typeList(): js.UndefOr[TypeListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "EnumDeclarationContext")
  class EnumDeclarationContext extends ParserRuleContext {
    def id(): IdContext= js.native
    def enumConstants(): js.UndefOr[EnumConstantsContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "MethodDeclarationContext")
  class MethodDeclarationContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def formalParameters(): FormalParametersContext= js.native
    def typeRef(): js.UndefOr[TypeRefContext] = js.native
    def block(): js.UndefOr[BlockContext] = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "InterfaceMethodDeclarationContext")
  class InterfaceMethodDeclarationContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def formalParameters(): FormalParametersContext = js.native
    def typeRef(): js.UndefOr[TypeRefContext] = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "FieldDeclarationContext")
  class FieldDeclarationContext extends ParserRuleContext {
    def typeRef(): TypeRefContext= js.native
    def variableDeclarators(): VariableDeclaratorsContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ConstructorDeclarationContext")
  class ConstructorDeclarationContext extends ParserRuleContext {
    def qualifiedName(): QualifiedNameContext = js.native
    def formalParameters(): FormalParametersContext = js.native
    def block(): BlockContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "FormalParameterListContext")
  class FormalParameterListContext extends ParserRuleContext {
    def formalParameter(): js.Array[FormalParameterContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "FormalParameterContext")
  class FormalParameterContext extends ParserRuleContext {
    def typeRef(): TypeRefContext = js.native
    def id(): IdContext = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "FormalParametersContext")
  class FormalParametersContext extends ParserRuleContext {
    def formalParameterList(): js.UndefOr[FormalParameterListContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "InterfaceBodyContext")
  class InterfaceBodyContext extends ParserRuleContext {
    def interfaceMethodDeclaration(): js.Array[InterfaceMethodDeclarationContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ClassBodyContext")
  class ClassBodyContext extends ParserRuleContext {
    def classBodyDeclaration(): js.Array[ClassBodyDeclarationContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ClassBodyDeclarationContext")
  class ClassBodyDeclarationContext extends ParserRuleContext {
    def block(): js.UndefOr[BlockContext] = js.native
    def STATIC(): js.UndefOr[TerminalNode] = js.native
    def memberDeclaration(): js.UndefOr[MemberDeclarationContext] = js.native
    def modifier(): js.Array[ModifierContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "EnumConstantsContext")
  class EnumConstantsContext extends ParserRuleContext {
    def id(): js.Array[IdContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "TypeDeclarationContext")
  class TypeDeclarationContext extends ParserRuleContext {
    def classDeclaration(): js.UndefOr[ClassDeclarationContext] = js.native
    def modifier(): js.Array[ModifierContext] = js.native
    def enumDeclaration(): js.UndefOr[EnumDeclarationContext] = js.native
    def interfaceDeclaration(): js.UndefOr[InterfaceDeclarationContext] = js.native
  }
}
