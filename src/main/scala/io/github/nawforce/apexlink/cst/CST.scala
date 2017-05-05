/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package io.github.nawforce.apexlink.cst

import io.github.nawforce.apexlink.antlr.ApexParser
import io.github.nawforce.apexlink.antlr.ApexParser._
import io.github.nawforce.apexlink.utils.CSTException
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConversions._
import scala.collection.mutable

sealed abstract class CST {

  private var startPos: Long = -1
  private var endPos: Long = -1

  // TODO: Not all CST produce types
  def getType(ctx: TypeContext): Type = {
    throw new CSTException
  }

  def children(): List[CST]

  def descendants(cst: CST): List[CST] = {
    List(cst) ++ cst.children().flatMap(x => descendants(x))
  }

  def withContext(context: ParserRuleContext): this.type = {
    startPos = context.start.getStartIndex()
    endPos = context.stop.getStopIndex()
    this
  }

  def location(): String = {
    "From " + start() + " to " + end() + " length " + (end() - start())
  }

  def start(): Long = startPos

  def end(): Long = endPos

  def findExpressions(onlyOuter : Boolean) : List[Expression] = {
    this match {
      case e: Expression => List(e) ++ (if (!onlyOuter) this.children().flatMap(_.findExpressions(onlyOuter)) else Nil)
      case _ => this.children().flatMap(_.findExpressions(onlyOuter))
    }
  }

  def findStatements(onlyOuter : Boolean) : List[Statement] = {
    this match {
      case e: Statement => List(e) ++ (if (!onlyOuter) this.children().flatMap(_.findStatements(onlyOuter)) else Nil)
      case _ => this.children().flatMap(_.findStatements(onlyOuter))
    }
  }
}

class CSTIndex {
  private val cstIndex = new mutable.HashMap[String, mutable.Set[CST]] with mutable.MultiMap[String, CST]

  def add(cst: CST): Unit = {
    cstIndex.addBinding(cst.getClass.getSimpleName, cst)
  }

  def get(name: String) : mutable.Set[CST] = {
    val nodes : Option[mutable.Set[CST]] = cstIndex.get(name)
    if (nodes.isDefined)
      nodes.get
    else
      mutable.Set[CST]()
  }
}

abstract class TypeContext {
  def thisType: Type

  def superType: Type

  def getIdentifierType(id: String): Type
}

sealed abstract class Type extends CST {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): Type = {
    this
  }
}

sealed abstract class PrimitiveType extends Type

final case class BlobType(arraySubs: Integer) extends PrimitiveType

final case class BooleanType(arraySubs: Integer) extends PrimitiveType

final case class DateType(arraySubs: Integer) extends PrimitiveType

final case class DatetimeType(arraySubs: Integer) extends PrimitiveType

final case class DecimalType(arraySubs: Integer) extends PrimitiveType

final case class DoubleType(arraySubs: Integer) extends PrimitiveType

final case class IdType(arraySubs: Integer) extends PrimitiveType

final case class IntegerType(arraySubs: Integer) extends PrimitiveType

final case class LongType(arraySubs: Integer) extends PrimitiveType

final case class ObjectType(arraySubs: Integer) extends PrimitiveType

final case class StringType(arraySubs: Integer) extends PrimitiveType

final case class TimeType(arraySubs: Integer) extends PrimitiveType

final case class NullType() extends PrimitiveType

object PrimitiveType {
  def construct(primitiveType: PrimitiveTypeContext, arraySubs: Integer = 0): PrimitiveType = {
    val cst = primitiveType.getText.toLowerCase match {
      case "blob" => BlobType(arraySubs)
      case "boolean" => BooleanType(arraySubs)
      case "date" => DateType(arraySubs)
      case "datetime" => DatetimeType(arraySubs)
      case "decimal" => DecimalType(arraySubs)
      case "double" => DoubleType(arraySubs)
      case "id" => IdType(arraySubs)
      case "integer" => IntegerType(arraySubs)
      case "long" => LongType(arraySubs)
      case "object" => ObjectType(arraySubs)
      case "string" => StringType(arraySubs)
      case "time" => TimeType(arraySubs)
    }
    cst.withContext(primitiveType)
  }
}

object Type {
  def construct(t: TypeRefContext): Type = {
    if (t.primitiveType() != null) {
      PrimitiveType.construct(t.primitiveType()).withContext(t)
    } else if (t.classOrInterfaceType() != null) {
      ClassOrInterfaceType.construct(t.classOrInterfaceType()).withContext(t)
    } else {
      throw new CSTException()
    }
  }
}

final case class TypeList(types: List[Type])

object TypeList {
  def construct(typeList: TypeListContext): TypeList = {
    val types: Seq[TypeRefContext] = typeList.typeRef()
    TypeList(types.toList.map(t => Type.construct(t).withContext(t)))
  }

  def empty(): TypeList = {
    TypeList(List())
  }
}

final case class ClassOrInterfaceTypePart(name: String, typeList: TypeList)

final case class ClassOrInterfaceType(name: List[ClassOrInterfaceTypePart]) extends Type

object ClassOrInterfaceType {
  def construct(classOrInterfaceType: ClassOrInterfaceTypeContext): ClassOrInterfaceType = {
    val ids: Seq[IdContext] = classOrInterfaceType.id()
    val typeArgs: Seq[TypeArgumentsContext] = classOrInterfaceType.typeArguments()
    var i = 0
    val parts = for (id <- ids) yield {
      val hasTypes = typeArgs.length > i
      i += 1
      if (hasTypes)
        ClassOrInterfaceTypePart(id.getText, TypeList.construct(typeArgs.get(i - 1).typeList()))
      else
        ClassOrInterfaceTypePart(id.getText, TypeList.empty())
    }
    ClassOrInterfaceType(parts.toList).withContext(classOrInterfaceType)
  }
}

final case class ClassType(typeRef: TypeRef) extends Type

final case class VoidClassType() extends Type

final case class QualifiedName(names: List[String]) extends CST {
  override def children(): List[CST] = Nil
}

object QualifiedName {
  def construct(qualifiedName: QualifiedNameContext): QualifiedName = {
    val ids: Seq[IdContext] = qualifiedName.id()
    QualifiedName(ids.toList.map(id => id.getText)).withContext(qualifiedName)
  }

  def construct(aList: List[QualifiedNameContext]): List[QualifiedName] = {
    aList.map(QualifiedName.construct)
  }
}

final case class Annotation(name: QualifiedName, elementValuePairs: List[ElementValuePair], elementValue: Option[ElementValue]) extends CST {
  override def children(): List[CST] = List[CST]() ++ elementValuePairs ++ elementValue
}

object Annotation {
  def construct(annotation: AnnotationContext): Annotation = {
    val elementValue =
      if (annotation.elementValue() != null) {
        Some(ElementValue.construct(annotation.elementValue()))
      } else {
        None
      }
    Annotation(QualifiedName.construct(annotation.qualifiedName()),
      ElementValuePairs.construct(annotation.elementValuePairs()),
      elementValue).withContext(annotation)
  }
}

sealed abstract class ElementValue() extends CST

final case class ExpressionElementValue(expression: Expression) extends ElementValue {
  override def children(): List[CST] = expression :: Nil
}

final case class AnnotationElementValue(annotation: Annotation) extends ElementValue {
  override def children(): List[CST] = annotation :: Nil
}

final case class ArrayInitializerElementValue(arrayInitializer: ElementValueArrayInitializer) extends ElementValue {
  override def children(): List[CST] = arrayInitializer :: Nil
}

object ElementValue {
  def construct(elementValue: ElementValueContext): ElementValue = {
    if (elementValue.expression() != null) {
      ExpressionElementValue(Expression.construct(elementValue.expression())).withContext(elementValue)
    } else if (elementValue.annotation() != null) {
      AnnotationElementValue(Annotation.construct(elementValue.annotation())).withContext(elementValue)
    } else if (elementValue.elementValueArrayInitializer() != null) {
      ArrayInitializerElementValue(ElementValueArrayInitializer.construct(
        elementValue.elementValueArrayInitializer())).withContext(elementValue)
    } else {
      throw new CSTException()
    }
  }

  def construct(aList: List[ElementValueContext]): List[ElementValue] = {
    aList.map(ElementValue.construct)
  }
}

final case class ElementValueArrayInitializer(elementValues: List[ElementValue]) extends CST {
  override def children(): List[CST] = elementValues
}

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext): ElementValueArrayInitializer = {
    val elements: Seq[ElementValueContext] = from.elementValue()
    ElementValueArrayInitializer(elements.toList.map(ElementValue.construct)).withContext(from)
  }
}

final case class ElementValuePair(id: String, elementValue: ElementValue) extends CST {
  override def children(): List[CST] = elementValue :: Nil
}

object ElementValuePair {
  def construct(from: ElementValuePairContext): ElementValuePair = {
    ElementValuePair(from.id().getText, ElementValue.construct(from.elementValue())).withContext(from)
  }

  def construct(aList: List[ElementValuePairContext]): List[ElementValuePair] = {
    aList.map(ElementValuePair.construct)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs: Seq[ElementValuePairContext] = from.elementValuePair()
      pairs.toList.map(ElementValuePair.construct)
    } else {
      List()
    }
  }
}

sealed abstract class TypeModifier() extends CST {
  override def children(): List[CST] = Nil
}

// TODO: How do we remove this
final case class UnknownModifier(value: String) extends TypeModifier

final case class AnnotationModifier(annotation: Annotation) extends TypeModifier {
  override def children(): List[CST] = annotation :: Nil
}

final case class PublicModifier() extends TypeModifier

final case class ProtectedModifier() extends TypeModifier

final case class PrivateModifier() extends TypeModifier

final case class StaticModifier() extends TypeModifier

final case class AbstractModifier() extends TypeModifier

final case class FinalModifier() extends TypeModifier

final case class GlobalModifier() extends TypeModifier

final case class WebserviceModifier() extends TypeModifier

final case class OverrideModifier() extends TypeModifier

final case class VirtualModifier() extends TypeModifier

final case class TestMethodModifier() extends TypeModifier

final case class WithSharingModifier() extends TypeModifier

final case class WithoutSharingModifier() extends TypeModifier

object TypeModifier {

  def construct(typeModifier: String): TypeModifier = {
    typeModifier.toLowerCase match {
      case "public" => PublicModifier()
      case "protected" => ProtectedModifier()
      case "private" => PrivateModifier()
      case "static" => PrivateModifier()
      case "abstract" => PrivateModifier()
      case "final" => PrivateModifier()
      case "global" => PrivateModifier()
      case "webservice" => PrivateModifier()
      case "override" => PrivateModifier()
      case "virtual" => PrivateModifier()
      case "testmethod" => PrivateModifier()
      case _ => UnknownModifier(typeModifier)
    }
  }

  def construct(typeModifier: ClassOrInterfaceModifierContext): TypeModifier = {
    val cst =
      if (typeModifier.annotation() != null) {
        AnnotationModifier(Annotation.construct(typeModifier.annotation()))
      } else if (typeModifier.withSharing() != null) {
        WithSharingModifier()
      } else if (typeModifier.withoutSharing() != null) {
        WithoutSharingModifier()
      } else {
        construct(typeModifier.getText)
      }
    cst.withContext(typeModifier)
  }

  def construct(typeModifiers: List[ClassOrInterfaceModifierContext]): List[TypeModifier] = {
    typeModifiers.map(TypeModifier.construct)
  }
}

sealed abstract class Modifier() extends TypeModifier

object Modifier {
  def construct(modifier: ModifierContext): TypeModifier = {
    val cst =
      if (modifier.classOrInterfaceModifier() != null) {
        TypeModifier.construct(modifier.classOrInterfaceModifier())
      } else {
        modifier.getText.toLowerCase() match {
          case "native" => NativeModifier()
          case "synchronized" => SynchronizedModifier()
          case "transient" => TransientModifier()
          case _ => throw new CSTException()
        }
      }
    cst.withContext(modifier)
  }

  def construct(typeModifiers: List[ModifierContext]): List[TypeModifier] = {
    typeModifiers.map(Modifier.construct)
  }
}

final case class NativeModifier() extends Modifier

final case class SynchronizedModifier() extends Modifier

final case class TransientModifier() extends Modifier

final case class CompilationUnit(types: List[TypeDeclaration]) extends CST {
  def children(): List[CST] = types

  def resolve(index: CSTIndex): Unit = {
    types.foreach(_.resolve(index))
  }
}

object CompilationUnit {
  def construct(compilationUnit: CompilationUnitContext): CompilationUnit = {
    val typeDecls: Seq[TypeDeclarationContext] = compilationUnit.typeDeclaration()
    CompilationUnit(TypeDeclaration.construct(typeDecls.toList)).withContext(compilationUnit)
  }
}

// Treat TypeDeclarations as ClassBodyDeclarations for inner class, interface & enum
sealed abstract class TypeDeclaration extends CST with ClassBodyDeclaration {
  def resolve(index: CSTIndex)
}

final case class EmptyTypeDeclaration() extends TypeDeclaration {
  override def children(): List[CST] = Nil

  override def resolve(index: CSTIndex) {}
}

object TypeDeclaration {

  def construct(typeDecl: TypeDeclarationContext): TypeDeclaration = {
    val modifiers: Seq[ClassOrInterfaceModifierContext] = typeDecl.classOrInterfaceModifier()
    val cst =
      if (typeDecl.classDeclaration() != null) {
        ClassDeclaration.construct(TypeModifier.construct(modifiers.toList), typeDecl.classDeclaration())
      } else if (typeDecl.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(TypeModifier.construct(modifiers.toList), typeDecl.interfaceDeclaration())
      } else if (typeDecl.enumDeclaration() != null) {
        EnumDeclaration.construct(TypeModifier.construct(modifiers.toList), typeDecl.enumDeclaration())
      } else {
        // TODO: Empty type declaration?
        throw new CSTException()
      }
    cst.withContext(typeDecl)
  }

  def construct(typeDecls: List[TypeDeclarationContext]): List[TypeDeclaration] = {
    typeDecls.map(TypeDeclaration.construct)
  }
}

final case class ClassDeclaration(name: String, typeModifiers: List[TypeModifier],
                                  extendsType: Option[Type], implementsTypes: TypeList,
                                  bodyDeclarations: List[ClassBodyDeclaration]) extends TypeDeclaration {
  override def children(): List[CST] = {
    typeModifiers ++ extendsType ++ implementsTypes.types ++ bodyDeclarations
  }

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
    bodyDeclarations.foreach(_.resolve(index))
  }
}

object ClassDeclaration {
  def construct(typeModifiers: List[TypeModifier], classDeclaration: ClassDeclarationContext): ClassDeclaration = {
    val extendType =
      if (classDeclaration.typeRef() != null)
        Some(Type.construct(classDeclaration.typeRef()))
      else
        None
    val implementsType =
      if (classDeclaration.typeList() != null)
        TypeList.construct(classDeclaration.typeList())
      else
        TypeList.empty()

    val classBody = classDeclaration.classBody()
    val classBodyDeclarations: Seq[ClassBodyDeclarationContext] = classBody.classBodyDeclaration()
    val bodyDeclarations: List[ClassBodyDeclaration] =
      if (classBodyDeclarations != null) {
        classBodyDeclarations.toList.map(cbd =>

          if (cbd.block() != null) {
            StaticBlock.construct(cbd.block())
          } else if (cbd.memberDeclaration() != null) {
            val modifiers: Seq[ModifierContext] = cbd.modifier()
            ClassBodyDeclaration.construct(modifiers.toList, cbd.memberDeclaration())
          } else {
            throw new CSTException()
          }
        )
      } else {
        List()
      }

    ClassDeclaration(classDeclaration.id().getText, typeModifiers, extendType,
      implementsType, bodyDeclarations).withContext(classDeclaration)
  }
}

// TODO: Handle body
final case class InterfaceDeclaration(name: String, typeModifiers: List[TypeModifier], implementsTypes: TypeList) extends TypeDeclaration {
  override def children(): List[CST] = implementsTypes.types ::: typeModifiers

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object InterfaceDeclaration {
  def construct(typeModifiers: List[TypeModifier], interfaceDeclaration: ApexParser.InterfaceDeclarationContext): InterfaceDeclaration = {
    val implementsType =
      if (interfaceDeclaration.typeList() != null)
        TypeList.construct(interfaceDeclaration.typeList())
      else
        TypeList.empty()

    InterfaceDeclaration(interfaceDeclaration.id().getText, typeModifiers, implementsType).withContext(interfaceDeclaration)
  }
}

// TODO: Handle body
final case class EnumDeclaration(name: String, typeModifiers: List[TypeModifier], implementsTypes: TypeList) extends TypeDeclaration {
  override def children(): List[CST] = typeModifiers ::: implementsTypes.types

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object EnumDeclaration {
  def construct(typeModifiers: List[TypeModifier], enumDeclaration: ApexParser.EnumDeclarationContext): EnumDeclaration = {
    val implementsType =
      if (enumDeclaration.typeList() != null)
        TypeList.construct(enumDeclaration.typeList())
      else
        TypeList.empty()
    EnumDeclaration(enumDeclaration.id().getText, typeModifiers, implementsType).withContext(enumDeclaration)
  }
}

trait ClassBodyDeclaration extends CST {
  def resolve(index: CSTIndex): Unit = {
    // Default to ignore
  }
}

object ClassBodyDeclaration {
  def construct(modifiers: List[ModifierContext], memberDeclarationContext: MemberDeclarationContext): ClassBodyDeclaration = {
    val m = Modifier.construct(modifiers)
    val cst: ClassBodyDeclaration =
      if (memberDeclarationContext.methodDeclaration() != null) {
        MethodDeclaration.construct(m, memberDeclarationContext.methodDeclaration())
      } else if (memberDeclarationContext.fieldDeclaration() != null) {
        FieldDeclaration.construct(m, memberDeclarationContext.fieldDeclaration())
      } else if (memberDeclarationContext.constructorDeclaration() != null) {
        ConstructorDeclaration.construct(m, memberDeclarationContext.constructorDeclaration())
      } else if (memberDeclarationContext.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(m, memberDeclarationContext.interfaceDeclaration())
      } else if (memberDeclarationContext.enumDeclaration() != null) {
        EnumDeclaration.construct(m, memberDeclarationContext.enumDeclaration())
      } else if (memberDeclarationContext.propertyDeclaration() != null) {
        PropertyDeclaration.construct(m, memberDeclarationContext.propertyDeclaration())
      } else if (memberDeclarationContext.classDeclaration() != null) {
        ClassDeclaration.construct(m, memberDeclarationContext.classDeclaration())
      } else {
        throw new CSTException()
      }
    cst.withContext(memberDeclarationContext)
  }

}

final case class StaticBlock(block: List[Statement]) extends ClassBodyDeclaration {
  override def children(): List[CST] = block
}

object StaticBlock {
  def construct(block: BlockContext): StaticBlock = {
    val statements: Seq[StatementContext] = block.statement()
    StaticBlock(Statement.construct(statements.toList))
  }
}

final case class MethodDeclaration(modifiers: List[TypeModifier], typeRef: Option[TypeRef], id: String,
                                   formalParameters: List[FormalParameter], block: Option[Block]) extends ClassBodyDeclaration {

  override def children(): List[CST] = modifiers ++ typeRef ++ formalParameters ++ block

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
    val rc = new ResolveStmtContext(index)
    block.foreach(b => b.resolve(rc))
    rc.complete()
  }
}

object MethodDeclaration {
  def construct(modifiers: List[TypeModifier], from: MethodDeclarationContext): MethodDeclaration = {
    val typeRef = if (from.typeRef() != null) Some(TypeRef.construct(from.typeRef())) else None
    val block = if (from.block != null) Some(Block.construct(from.block)) else None

    MethodDeclaration(modifiers,
      typeRef,
      from.id.getText,
      FormalParameters.construct(from.formalParameters()),
      block
    ).withContext(from)
  }
}

final case class FieldDeclaration(modifiers: List[TypeModifier], typeRef: TypeRef, variableDeclarators: VariableDeclarators) extends ClassBodyDeclaration {
  override def children(): List[CST] = typeRef :: variableDeclarators :: modifiers
}

object FieldDeclaration {
  def construct(modifiers: List[TypeModifier], fieldDeclaration: FieldDeclarationContext): FieldDeclaration = {
    FieldDeclaration(modifiers, TypeRef.construct(fieldDeclaration.typeRef()),
      VariableDeclarators.construct(fieldDeclaration.variableDeclarators())).withContext(fieldDeclaration)
  }
}

final case class ConstructorDeclaration(modifiers: List[TypeModifier], id: String, formalParameters: List[FormalParameter],
                                         block: Block) extends ClassBodyDeclaration {
  override def children(): List[CST] = modifiers ++ formalParameters ++ List(block)
}

object ConstructorDeclaration {
  def construct(modifiers: List[TypeModifier], from: ConstructorDeclarationContext): ConstructorDeclaration = {
    ConstructorDeclaration(modifiers,
      from.id().getText,
      FormalParameters.construct(from.formalParameters()),
      Block.construct(from.block())
    ).withContext(from)
  }
}

final case class PropertyDeclaration(modifiers: List[TypeModifier], typeRef: TypeRef, variableDeclarators: VariableDeclarators, propertyDeclaration: PropertyBodyDeclaration) extends ClassBodyDeclaration {
  override def children(): List[CST] = typeRef :: variableDeclarators :: propertyDeclaration :: modifiers
}

object PropertyDeclaration {
  def construct(modifiers: List[TypeModifier], propertyDeclaration: PropertyDeclarationContext): PropertyDeclaration = {
    PropertyDeclaration(modifiers, TypeRef.construct(propertyDeclaration.typeRef()),
      VariableDeclarators.construct(propertyDeclaration.variableDeclarators()),
      PropertyBodyDeclaration.construct(propertyDeclaration.propertyBodyDeclaration())).withContext(propertyDeclaration)
  }
}

// Treat Block as Statement for blocks in blocks
final case class Block(statements: List[Statement]) extends CST with Statement {
  override def children(): List[CST] = statements

  override def resolve(context: ResolveStmtContext): Unit = {
    context.pushBlock()
    statements.foreach(_.resolve(context))
    context.popBlock()
  }
}

object Block {
  def construct(statements: List[StatementContext]): Block = {
    Block(Statement.construct(statements))
  }

  def construct(blockContext: BlockContext): Block = {
    val statements: Seq[StatementContext] = blockContext.statement()
    construct(statements.toList)
  }

  def constructBlocks(blockContexts: List[BlockContext]): List[Block] = {
    blockContexts.map(construct)
  }

  def constructOption(blockContext: BlockContext): Option[Block] = {
    if (blockContext != null)
      Some(construct(blockContext))
    else
      None
  }
}

trait Statement extends CST {
  def resolve(context: ResolveStmtContext)
}

final case class LocalVariableDeclarationStatement(localVariableDeclaration: LocalVariableDeclaration) extends Statement {
  override def children(): List[CST] = localVariableDeclaration :: Nil
  override def resolve(context: ResolveStmtContext): Unit = localVariableDeclaration.resolve(context)
}

object LocalVariableDeclarationStatement {
  def construct(from: LocalVariableDeclarationStatementContext): LocalVariableDeclarationStatement = {
    LocalVariableDeclarationStatement(LocalVariableDeclaration.construct(from.localVariableDeclaration())).withContext(from)
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
  def construct(ifStatement: IfStatementContext): IfStatement = {
    val statements: Seq[StatementContext] = ifStatement.statement()
    IfStatement(Expression.construct(ifStatement.parExpression().expression()),
      Statement.construct(statements.toList)).withContext(ifStatement)
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
  def construct(statement: ForStatementContext): ForStatement = {
    ForStatement(ForControl.construct(statement.forControl()), Statement.construct(statement.statement())).withContext(statement)
  }
}

sealed abstract class ForControl extends CST {
  def resolve(context: ResolveStmtContext): Unit
}

object ForControl {
  def construct(from: ForControlContext): ForControl = {
    val cst =
      if (from.enhancedForControl() != null) {
        EnhancedForControl.construct(from.enhancedForControl())
      } else {
        BasicForControl.construct(from)
      }
    cst.withContext(from)
  }
}

final case class EnhancedForControl(modifiers: List[VariableModifier], typeRef: TypeRef,
                                    id: Identifier, expression: Expression) extends ForControl with VarIntroducer {
  override def children(): List[CST] = typeRef :: id :: expression :: modifiers
  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    context.addVarDeclaration(VarDeclaration(id, typeRef, this))
  }
}

object EnhancedForControl {
  def construct(from: EnhancedForControlContext): EnhancedForControl = {
    val variableModifiers: Seq[VariableModifierContext] =
      if (from.variableModifier() != null) {
        from.variableModifier()
      } else {
        Seq()
      }

    EnhancedForControl(
      VariableModifier.construct(variableModifiers.toList),
      TypeRef.construct(from.typeRef()),
      Identifier(from.id().getText),
      Expression.construct(from.expression()).withContext(from)
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
  def construct(from: ForControlContext): BasicForControl = {
    val forInit =
      if (from.forInit() != null) {
        Some(ForInit.construct(from.forInit()))
      } else {
        None
      }
    val expression =
      if (from.expression() != null) {
        Some(Expression.construct(from.expression()))
      } else {
        None
      }
    val forUpdate =
      if (from.forUpdate() != null) {
        Some(ForUpdate.construct(from.forUpdate()))
      } else {
        None
      }
    BasicForControl(forInit, expression, forUpdate).withContext(from)
  }
}

sealed abstract class ForInit extends CST {
  def resolve(context: ResolveStmtContext) : Unit
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
  def construct(from: ForInitContext): ForInit = {
    val cst =
      if (from.localVariableDeclaration() != null) {
        LocalVariableForInit(LocalVariableDeclaration.construct(from.localVariableDeclaration()))
      } else if (from.expressionList() != null) {
        val expressions: Seq[ExpressionContext] = from.expressionList().expression()
        ExpressionListForInit(Expression.construct(expressions.toList))
      } else {
        throw new CSTException
      }
    cst.withContext(from)
  }
}

final case class ForUpdate(expressions: List[Expression]) extends CST {
  override def children(): List[CST] = expressions
  def resolve(context: ResolveStmtContext): Unit = expressions.foreach(_.resolve(new ResolveExprContext(context)))
}

object ForUpdate {
  def construct(from: ForUpdateContext): ForUpdate = {
    val expressions: Seq[ExpressionContext] = from.expressionList().expression()
    ForUpdate(Expression.construct(expressions.toList)).withContext(from)
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
  def construct(statement: WhileStatementContext): WhileStatement = {
    WhileStatement(Expression.construct(statement.parExpression().expression()),
      Statement.construct(statement.statement())).withContext(statement)
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
  def construct(statement: DoWhileStatementContext): DoWhileStatement = {
    DoWhileStatement(Statement.construct(statement.statement()),
      Expression.construct(statement.parExpression().expression())
    ).withContext(statement)
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
  def construct(from: TryStatementContext): TryStatement = {
    val catches: List[CatchClauseContext] = if (from.catchClause() != null) from.catchClause().toList else List()
    TryStatement(Block.construct(from.block()), CatchClause.construct(catches),
      FinallyBlock.construct(from.finallyBlock())).withContext(from)
  }
}

object FinallyBlock {
  def construct(from: FinallyBlockContext): Option[Block] = {
    if (from != null) {
      Some(Block.construct(from.block()))
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
  def construct(from: CatchTypeContext): CatchType = {
    val names: Seq[QualifiedNameContext] = from.qualifiedName()
    CatchType(QualifiedName.construct(names.toList)).withContext(from)
  }
}

final case class CatchClause(modifiers: List[VariableModifier], catchType: CatchType, id: String, block: Block) extends CST {
  override def children(): List[CST] = modifiers ++ List(catchType) ++ List(block)
  def resolve(context: ResolveStmtContext): Unit = {
    block.resolve(context)
  }
}

object CatchClause {
  def construct(from: CatchClauseContext): CatchClause = {
    val modifiers: Seq[VariableModifierContext] = from.variableModifier()
    CatchClause(
      VariableModifier.construct(modifiers.toList),
      CatchType.construct(from.catchType()),
      from.id().getText,
      Block.construct(from.block())
    ).withContext(from)
  }

  def construct(aList: List[CatchClauseContext]): List[CatchClause] = {
    if (aList != null)
      aList.map(CatchClause.construct)
    else
      List()
  }

}

final case class ReturnStatement(expression: Option[Expression]) extends Statement {
  override def children(): List[CST] = List() ++ expression
  def resolve(context: ResolveStmtContext): Unit = expression.foreach(_.resolve(new ResolveExprContext(context)))
}

object ReturnStatement {
  def construct(statement: ReturnStatementContext): ReturnStatement = {
    val cst =
      if (statement.expression() != null) {
        ReturnStatement(Some(Expression.construct(statement.expression())))
      } else {
        ReturnStatement(None)
      }
    cst.withContext(statement)
  }
}

final case class ThrowStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object ThrowStatement {
  def construct(statement: ThrowStatementContext): ThrowStatement = {
    ThrowStatement(Expression.construct(statement.expression())).withContext(statement)
  }
}

final case class BreakStatement(id: String) extends Statement {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object BreakStatement {
  def construct(statement: BreakStatementContext): BreakStatement = {
    val cst =
      if (statement.id() != null) {
        BreakStatement(statement.id.getText)
      } else {
        BreakStatement(null)
      }
    cst.withContext(statement)
  }
}

final case class ContinueStatement(id: String) extends Statement {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object ContinueStatement {
  def construct(statement: ContinueStatementContext): ContinueStatement = {
    val cst =
      if (statement.id() != null) {
        ContinueStatement(statement.id.getText)
      } else {
        ContinueStatement(null)
      }
    cst.withContext(statement)
  }
}

final case class InsertStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object InsertStatement {
  def construct(statement: InsertStatementContext): InsertStatement = {
    InsertStatement(Expression.construct(statement.expression())).withContext(statement)
  }
}

final case class UpdateStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object UpdateStatement {
  def construct(statement: UpdateStatementContext): UpdateStatement = {
    UpdateStatement(Expression.construct(statement.expression())).withContext(statement)
  }
}

final case class DeleteStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object DeleteStatement {
  def construct(statement: DeleteStatementContext): DeleteStatement = {
    DeleteStatement(Expression.construct(statement.expression())).withContext(statement)
  }
}

final case class UndeleteStatement(expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object UndeleteStatement {
  def construct(statement: UndeleteStatementContext): UndeleteStatement = {
    UndeleteStatement(Expression.construct(statement.expression())).withContext(statement)
  }
}

final case class UpsertStatement(expression: Expression, id: String) extends Statement {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object UpsertStatement {
  def construct(statement: UpsertStatementContext): UpsertStatement = {
    val expression = Expression.construct(statement.expression())
    if (statement.id() != null)
      UpsertStatement(expression, statement.id.getText).withContext(statement)
    else
      UpsertStatement(expression, null).withContext(statement)
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
  def construct(statement: MergeStatementContext): MergeStatement = {
    MergeStatement(Expression.construct(statement.expression(0)), Expression.construct(statement.expression(1))).withContext(statement)
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
  def construct(statement: RunAsStatementContext): RunAsStatement = {
    val expressions =
      if (statement.expressionList() != null) {
        val e: Seq[ExpressionContext] = statement.expressionList().expression()
        Expression.construct(e.toList)
      } else {
        List()
      }
    val block = if (statement.block() != null) Some(Block.construct(statement.block())) else None
    RunAsStatement(expressions, block).withContext(statement)
  }
}

final case class EmptyStatement() extends Statement {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object EmptyStatement {
  def construct(statement: EmptyStatementContext): EmptyStatement = {
    EmptyStatement().withContext(statement)
  }
}

final case class ExpressionStatement(var expression: Expression) extends Statement {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))

    // Link var assignment to declaration
    expression match {
      case BinaryExpression(PrimaryExpression(VarRef(decl)),rhs,"=") => decl.addAssign(rhs)
      case _ =>
    }
  }
}

object ExpressionStatement {
  def construct(statement: ExpressionStatementContext): ExpressionStatement = {
    ExpressionStatement(Expression.construct(statement.expression())).withContext(statement)
  }
}

final case class IdStatement(id: String, statement: Statement) extends Statement {
  override def children(): List[CST] = statement :: Nil
  def resolve(context: ResolveStmtContext): Unit = {} // Nothing to do
}

object IdStatement {
  def construct(statement: IdStatementContext): IdStatement = {
    IdStatement(statement.id().getText, Statement.construct(statement.statement())).withContext(statement)
  }
}

object Statement {
  def construct(statement: StatementContext): Statement = {
    val cst =
      if (statement.block() != null) {
        Block.construct(statement.block())
      } else if (statement.localVariableDeclarationStatement() != null) {
        LocalVariableDeclarationStatement.construct(statement.localVariableDeclarationStatement())
      } else if (statement.ifStatement() != null) {
        IfStatement.construct(statement.ifStatement())
      } else if (statement.forStatement() != null) {
        ForStatement.construct(statement.forStatement())
      } else if (statement.whileStatement() != null) {
        WhileStatement.construct(statement.whileStatement())
      } else if (statement.doWhileStatement() != null) {
        DoWhileStatement.construct(statement.doWhileStatement())
      } else if (statement.tryStatement() != null) {
        TryStatement.construct(statement.tryStatement())
      } else if (statement.returnStatement() != null) {
        ReturnStatement.construct(statement.returnStatement())
      } else if (statement.throwStatement() != null) {
        ThrowStatement.construct(statement.throwStatement())
      } else if (statement.breakStatement() != null) {
        BreakStatement.construct(statement.breakStatement())
      } else if (statement.continueStatement() != null) {
        ContinueStatement.construct(statement.continueStatement())
      } else if (statement.insertStatement() != null) {
        InsertStatement.construct(statement.insertStatement())
      } else if (statement.updateStatement() != null) {
        UpdateStatement.construct(statement.updateStatement())
      } else if (statement.deleteStatement() != null) {
        DeleteStatement.construct(statement.deleteStatement())
      } else if (statement.undeleteStatement() != null) {
        UndeleteStatement.construct(statement.undeleteStatement())
      } else if (statement.upsertStatement() != null) {
        UpsertStatement.construct(statement.upsertStatement())
      } else if (statement.mergeStatement() != null) {
        MergeStatement.construct(statement.mergeStatement())
      } else if (statement.runAsStatement() != null) {
        RunAsStatement.construct(statement.runAsStatement())
      } else if (statement.emptyStatement() != null) {
        EmptyStatement.construct(statement.emptyStatement())
      } else if (statement.expressionStatement() != null) {
        ExpressionStatement.construct(statement.expressionStatement())
      } else if (statement.idStatement() != null) {
        IdStatement.construct(statement.idStatement())
      } else {
        throw new CSTException()
      }
    cst.withContext(statement)
  }

  def construct(statements: List[StatementContext]): List[Statement] = {
    statements.map(Statement.construct)
  }
}

sealed abstract class VariableModifier extends CST

final case class FinalVariableModifier() extends VariableModifier {
  override def children(): List[CST] = Nil
}

final case class AnnotationVariableModifier(annotation: Annotation) extends VariableModifier {
  override def children(): List[CST] = annotation :: Nil
}

object VariableModifier {
  def construct(variableModifierContext: VariableModifierContext): VariableModifier = {
    if (variableModifierContext.annotation() != null) {
      AnnotationVariableModifier(Annotation.construct(variableModifierContext.annotation())).withContext(variableModifierContext)
    } else {
      FinalVariableModifier().withContext(variableModifierContext)
    }
  }

  def construct(variableModifiers: List[VariableModifierContext]): List[VariableModifier] = {
    variableModifiers.map(VariableModifier.construct)
  }
}

sealed abstract class VariableInitializer() extends CST

object VariableInitializer {
  def construct(variableInitializer: VariableInitializerContext): VariableInitializer = {
    val cst =
      if (variableInitializer.expression() != null) {
        ExpressionVariableInitializer(Expression.construct(variableInitializer.expression()))
      } else if (variableInitializer.arrayInitializer() != null) {
        ArrayVariableInitializer.construct(variableInitializer.arrayInitializer())
      } else {
        throw new CSTException()
      }
    cst.withContext(variableInitializer)
  }

  def construct(variableInitializers: List[VariableInitializerContext]): List[VariableInitializer] = {
    variableInitializers.map(VariableInitializer.construct)
  }
}

final case class ArrayVariableInitializer(variableInitializers: List[VariableInitializer]) extends VariableInitializer {
  override def children(): List[CST] = variableInitializers
}

final case class ExpressionVariableInitializer(expression: Expression) extends VariableInitializer {
  override def children(): List[CST] = expression :: Nil
}

object ArrayVariableInitializer {
  def construct(arrayInitializer: ArrayInitializerContext): ArrayVariableInitializer = {
    val variableInitializers: Seq[VariableInitializerContext] = arrayInitializer.variableInitializer()
    ArrayVariableInitializer(variableInitializers.toList.map(
      VariableInitializer.construct
    )).withContext(arrayInitializer)
  }
}

final case class VariableDeclarator(id: Identifier, init: Option[VariableInitializer]) extends CST with VarIntroducer {
  override def children(): List[CST] = List[CST](id) ++ init
}

object VariableDeclarator {
  def construct(variableDeclarator: VariableDeclaratorContext): VariableDeclarator = {
    val init =
      if (variableDeclarator.variableInitializer() != null) {
        Some(VariableInitializer.construct(variableDeclarator.variableInitializer()))
      } else {
        None
      }
    VariableDeclarator(Identifier(variableDeclarator.id().getText), init).withContext(variableDeclarator)
  }
}

final case class VariableDeclarators(declarators: List[VariableDeclarator]) extends CST {
  override def children(): List[CST] = declarators
  def resolve(typeRef : TypeRef, context: ResolveStmtContext) : Unit = {
    declarators.foreach(x => {
      context.addVarDeclaration(VarDeclaration(x.id, typeRef, x))
      x.init.foreach {
        case ExpressionVariableInitializer(expression) => x.addAssign(expression)
        case _ =>
      }
    })
  }
}

object VariableDeclarators {
  def construct(variableDeclaratorsContext: VariableDeclaratorsContext): VariableDeclarators = {
    val variableDeclarators: Seq[VariableDeclaratorContext] = variableDeclaratorsContext.variableDeclarator()
    VariableDeclarators(variableDeclarators.toList.map(VariableDeclarator.construct)).withContext(variableDeclaratorsContext)
  }
}

sealed abstract class TypeRef extends CST

final case class ClassOrInterfaceTypeRef(classOrInterfaceType: ClassOrInterfaceType, arraySubs: Int) extends TypeRef {
  override def children(): List[CST] = classOrInterfaceType :: Nil
}

final case class PrimitiveTypeRef(primitiveType: PrimitiveType) extends TypeRef {
  override def children(): List[CST] = primitiveType :: Nil
}

object TypeRef {
  def construct(typeRef: TypeRefContext): TypeRef = {
    val arraySubs = typeRef.arraySubscripts().getText.count(_ == '[')
    val cst =
      if (typeRef.classOrInterfaceType() != null) {
        ClassOrInterfaceTypeRef(ClassOrInterfaceType.construct(typeRef.classOrInterfaceType()), arraySubs)
      } else if (typeRef.primitiveType() != null) {
        PrimitiveTypeRef(PrimitiveType.construct(typeRef.primitiveType(), arraySubs))
      } else {
        throw new CSTException()
      }
    cst.withContext(typeRef)
  }

  def construct(aList: List[TypeRefContext]): List[TypeRef] = {
    aList.map(TypeRef.construct)
  }
}

final case class LocalVariableDeclaration(modifiers: List[VariableModifier], typeRef: TypeRef, variableDeclarators: VariableDeclarators) extends CST {
  override def children(): List[CST] = modifiers ::: (typeRef :: variableDeclarators :: Nil)
  def resolve(context : ResolveStmtContext) : Unit = variableDeclarators.resolve(typeRef, context)
}

object LocalVariableDeclaration {
  def construct(localVariableDeclaration: LocalVariableDeclarationContext): LocalVariableDeclaration = {
    val modifiers =
      if (localVariableDeclaration.variableModifier() != null) {
        val v: Seq[VariableModifierContext] = localVariableDeclaration.variableModifier()
        VariableModifier.construct(v.toList)
      } else {
        List()
      }
    LocalVariableDeclaration(modifiers, TypeRef.construct(localVariableDeclaration.typeRef()),
      VariableDeclarators.construct(localVariableDeclaration.variableDeclarators())).withContext(localVariableDeclaration)
  }
}

sealed abstract class PropertyBlock extends CST

final case class GetterPropertyBlock(modifiers: List[TypeModifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = modifiers ++ block
}

final case class SetterPropertyBlock(modifiers: List[TypeModifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = modifiers ++ block
}

object PropertyBlock {
  def construct(propertyBlockContext: PropertyBlockContext): PropertyBlock = {
    val modifiers: Seq[ModifierContext] = propertyBlockContext.modifier()
    val cst =
      if (propertyBlockContext.getter() != null) {
        GetterPropertyBlock(Modifier.construct(modifiers.toList), Block.constructOption(propertyBlockContext.getter().block()))
      } else if (propertyBlockContext.setter() != null) {
        SetterPropertyBlock(Modifier.construct(modifiers.toList), Block.constructOption(propertyBlockContext.setter().block()))
      } else {
        throw new CSTException()
      }
    cst.withContext(propertyBlockContext)
  }
}

final case class PropertyBodyDeclaration(propertyBlocks: List[PropertyBlock]) extends CST {
  override def children(): List[CST] = propertyBlocks
}

object PropertyBodyDeclaration {
  def construct(propertyBodyDeclarationContext: PropertyBodyDeclarationContext): PropertyBodyDeclaration = {
    val blocks: Seq[PropertyBlockContext] = propertyBodyDeclarationContext.propertyBlock()
    PropertyBodyDeclaration(blocks.toList.map(PropertyBlock.construct)).withContext(propertyBodyDeclarationContext)
  }
}

final case class FormalParameter(modifiers: List[VariableModifier], typeRef: TypeRef, id: Identifier) extends CST {
  override def children(): List[CST] = modifiers ::: (typeRef :: id :: Nil)
}

object FormalParameter {
  def construct(from: FormalParameterContext): FormalParameter = {
    val modifiers: List[VariableModifier] =
      if (from.variableModifier() != null) {
        val m: Seq[VariableModifierContext] = from.variableModifier()
        VariableModifier.construct(m.toList)
      } else {
        List()
      }
    FormalParameter(modifiers, TypeRef.construct(from.typeRef()), Identifier(from.id.getText)).withContext(from)
  }

  def construct(aList: List[FormalParameterContext]): List[FormalParameter] = {
    aList.map(FormalParameter.construct)
  }
}

object FormalParameterList {
  def construct(from: FormalParameterListContext): List[FormalParameter] = {
    if (from.formalParameter() != null) {
      val m: Seq[FormalParameterContext] = from.formalParameter()
      FormalParameter.construct(m.toList)
    } else {
      List()
    }
  }
}

object FormalParameters {
  def construct(from: FormalParametersContext): List[FormalParameter] = {
    if (from.formalParameterList() != null) {
      FormalParameterList.construct(from.formalParameterList())
    } else {
      List()
    }
  }
}

trait ExpressionRHS extends CST {
  def resolve(context: ResolveExprContext)
}

sealed abstract class Expression extends CST {
  def resolve(context: ResolveExprContext)
}

final case class LHSExpression(lhs: Expression, rhs: ExpressionRHS) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil
  override def resolve(context: ResolveExprContext) : Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class QName(ids: List[String]) extends Expression {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class FunctionCall(callee: Expression, arguments: List[Expression]) extends Expression {
  override def children(): List[CST] = callee :: arguments
  override def resolve(context: ResolveExprContext) : Unit = {
    callee.resolve(context)
    arguments.foreach(_.resolve(context))
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def children(): List[CST] = creator :: Nil
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class TypeExpression(typeRef: TypeRef, expression: Expression) extends Expression {
  override def children(): List[CST] = typeRef :: expression :: Nil
  def resolve(context: ResolveExprContext) : Unit = expression.resolve(context)
}

final case class PostOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveExprContext) : Unit = expression.resolve(context)
}

final case class PreOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveExprContext) : Unit = expression.resolve(context)
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil
  def resolve(context: ResolveExprContext) : Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class InstanceOfExpression(expression: Expression, typeRef: TypeRef) extends Expression {
  override def children(): List[CST] = expression :: typeRef :: Nil
  def resolve(context: ResolveExprContext) : Unit = expression.resolve(context)
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def children(): List[CST] = query :: lhs :: rhs :: Nil
  def resolve(context: ResolveExprContext) : Unit = {
    query.resolve(context)
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def children(): List[CST] = primary :: Nil
  def resolve(context: ResolveExprContext) : Unit = {
    // Link variable reference
    primary match {
      case Identifier(name) =>
        context.getVarDeclaration(name).foreach(declaration => {
          primary = VarRef(declaration)
        })
      case _ =>
    }
  }
}

final case class RHSId(id: String) extends ExpressionRHS {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class RHSThis() extends ExpressionRHS {
  override def children(): List[CST] = Nil
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class RHSNew(nonWildcardTypeArguments: Option[NonWildcardTypeArguments], innerCreator: InnerCreator) extends ExpressionRHS {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArguments ++ List(innerCreator)
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class RHSSuper(superSuffix: SuperSuffix) extends ExpressionRHS {
  override def children(): List[CST] = superSuffix :: Nil
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class RHSExplicitGenericInvocation(explicitGenericInvocation: ExplicitGenericInvocation) extends ExpressionRHS {
  override def children(): List[CST] = explicitGenericInvocation :: Nil
  def resolve(context: ResolveExprContext) : Unit = {}
}

final case class RHSArrayExpression(expression: Expression) extends ExpressionRHS {
  override def children(): List[CST] = expression :: Nil
  def resolve(context: ResolveExprContext) : Unit = expression.resolve(context)
}

object Expression {
  def construct(from: ExpressionContext): Expression = {
    val cst =
      from match {
        case alt1: Alt1ExpressionContext =>
          val lHSExpression = Expression.construct(alt1.expression())
          lHSExpression match {
            case PrimaryExpression(Identifier(id)) =>
              QName(id :: alt1.id().getText :: Nil)
            case QName(ids) =>
              QName(ids ::: (alt1.id().getText :: Nil))
            case _ =>
              LHSExpression(lHSExpression, RHSId(alt1.id().getText))
          }
        case alt2: Alt2ExpressionContext =>
          LHSExpression(Expression.construct(alt2.expression()), RHSThis())
        case alt3: Alt3ExpressionContext =>
          LHSExpression(Expression.construct(alt3.expression()), RHSNew(
            if (alt3.nonWildcardTypeArguments() != null)
              Some(NonWildcardTypeArguments.construct(alt3.nonWildcardTypeArguments()))
            else
              None,
            InnerCreator.construct(alt3.innerCreator())
          ))
        case alt4: Alt4ExpressionContext =>
          LHSExpression(Expression.construct(alt4.expression()), RHSSuper(
            SuperSuffix.construct(alt4.superSuffix())
          ))
        case alt5: Alt5ExpressionContext =>
          LHSExpression(Expression.construct(alt5.expression()), RHSExplicitGenericInvocation(
            ExplicitGenericInvocation.construct(alt5.explicitGenericInvocation())
          ))
        case alt6: Alt6ExpressionContext =>
          LHSExpression(Expression.construct(alt6.expression(0)), RHSArrayExpression(
            Expression.construct(alt6.expression(1))
          ))
        case alt7: FunctionCallExpressionContext =>
          FunctionCall(Expression.construct(alt7.expression),
            if (alt7.expressionList() != null) {
              val expression: Seq[ExpressionContext] = alt7.expressionList().expression()
              Expression.construct(expression.toList)
            } else {
              List()
            }
          )
        case alt8: Alt8ExpressionContext =>
          NewExpression(Creator.construct(alt8.creator()))
        case alt9: Alt9ExpressionContext =>
          TypeExpression(TypeRef.construct(alt9.typeRef()), Expression.construct(alt9.expression()))
        case alt10: Alt10ExpressionContext =>
          PostOpExpression(Expression.construct(alt10.expression()), alt10.getChild(1).getText)
        case alt11: Alt11ExpressionContext =>
          PreOpExpression(Expression.construct(alt11.expression()), alt11.getChild(0).getText)
        case alt12: Alt12ExpressionContext =>
          PostOpExpression(Expression.construct(alt12.expression()), alt12.getChild(0).getText)
        case alt13: Alt13ExpressionContext =>
          BinaryExpression(Expression.construct(alt13.expression(0)), Expression.construct(alt13.expression(1)), alt13.getChild(1).getText)
        case alt14: Alt14ExpressionContext =>
          BinaryExpression(Expression.construct(alt14.expression(0)), Expression.construct(alt14.expression(1)), alt14.getChild(1).getText)
        case alt15: Alt15ExpressionContext =>
          BinaryExpression(Expression.construct(alt15.expression(0)), Expression.construct(alt15.expression(1)), alt15.getChild(1).getText)
        case alt16: Alt16ExpressionContext =>
          BinaryExpression(Expression.construct(alt16.expression(0)), Expression.construct(alt16.expression(1)), alt16.getChild(1).getText)
        case alt17: Alt17ExpressionContext =>
          InstanceOfExpression(Expression.construct(alt17.expression()), TypeRef.construct(alt17.typeRef()))
        case alt18: Alt18ExpressionContext =>
          BinaryExpression(Expression.construct(alt18.expression(0)), Expression.construct(alt18.expression(1)), alt18.getChild(1).getText)
        case alt19: Alt19ExpressionContext =>
          BinaryExpression(Expression.construct(alt19.expression(0)), Expression.construct(alt19.expression(1)), alt19.getChild(1).getText)
        case alt20: Alt20ExpressionContext =>
          BinaryExpression(Expression.construct(alt20.expression(0)), Expression.construct(alt20.expression(1)), alt20.getChild(1).getText)
        case alt21: Alt21ExpressionContext =>
          BinaryExpression(Expression.construct(alt21.expression(0)), Expression.construct(alt21.expression(1)), alt21.getChild(1).getText)
        case alt22: Alt22ExpressionContext =>
          BinaryExpression(Expression.construct(alt22.expression(0)), Expression.construct(alt22.expression(1)), alt22.getChild(1).getText)
        case alt23: Alt23ExpressionContext =>
          BinaryExpression(Expression.construct(alt23.expression(0)), Expression.construct(alt23.expression(1)), alt23.getChild(1).getText)
        case alt24: Alt24ExpressionContext =>
          QueryExpression(Expression.construct(alt24.expression(0)), Expression.construct(alt24.expression(1)), Expression.construct(alt24.expression(2)))
        case alt25: Alt25ExpressionContext =>
          BinaryExpression(Expression.construct(alt25.expression(0)), Expression.construct(alt25.expression(1)), alt25.getChild(1).getText)
        case alt26: Alt26ExpressionContext =>
          PrimaryExpression(Primary.construct(alt26.primary()))
      }
    cst.withContext(from)
  }

  def construct(expression: List[ExpressionContext]): List[Expression] = {
    expression.map(Expression.construct)
  }
}

final case class NonWildcardTypeArguments(typeList: TypeList) extends CST {
  override def children(): List[CST] = typeList.types
}

object NonWildcardTypeArguments {
  def construct(from: NonWildcardTypeArgumentsContext): NonWildcardTypeArguments = {
    NonWildcardTypeArguments(TypeList.construct(from.typeList())).withContext(from)
  }
}

final case class NonWildcardTypeArgumentsOrDiamond(nonWildcardTypeArguments: Option[NonWildcardTypeArguments]) extends CST {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArguments
}

object NonWildcardTypeArgumentsOrDiamond {
  def construct(from: NonWildcardTypeArgumentsOrDiamondContext): NonWildcardTypeArgumentsOrDiamond = {
    NonWildcardTypeArgumentsOrDiamond(
      if (from.nonWildcardTypeArguments() != null)
        Some(NonWildcardTypeArguments.construct(from.nonWildcardTypeArguments()))
      else None).withContext(from)
  }
}

final case class TypeArgumentsOrDiamond(typeArguments: Option[TypeArguments]) extends CST {
  override def children(): List[CST] = List[CST]() ++ typeArguments
}

object TypeArgumentsOrDiamond {
  def construct(from: TypeArgumentsOrDiamondContext): TypeArgumentsOrDiamond = {
    TypeArgumentsOrDiamond(
      if (from.typeArguments() != null)
        Some(TypeArguments.construct(from.typeArguments()))
      else
        None
    ).withContext(from)
  }
}

final case class TypeArguments(typeList: List[TypeRef]) extends CST {
  override def children(): List[CST] = typeList
}

object TypeArguments {
  def construct(from: TypeArgumentsContext): TypeArguments = {
    val types: Seq[TypeRefContext] = from.typeList().typeRef()
    TypeArguments(TypeRef.construct(types.toList)).withContext(from)
  }
}

final case class ClassCreatorRest(arguments: List[Expression], expressionList: List[Expression]) extends CST {
  override def children(): List[CST] = arguments ::: expressionList
}

object ClassCreatorRest {
  def construct(from: ClassCreatorRestContext): ClassCreatorRest = {
    ClassCreatorRest(
      if (from.arguments() != null)
        Arguments.construct(from.arguments())
      else
        List(),
      if (from.expressionList() != null) {
        val expressions: Seq[ExpressionContext] = from.expressionList().expression()
        Expression.construct(expressions.toList)
      } else {
        List()
      }
    ).withContext(from)
  }
}

final case class ArrayCreatorRest(arraySubs: Int, arrayInitializer: Option[ArrayInitializer],
                                  expressions: List[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ arrayInitializer ++ expressions
}

object ArrayCreatorRest {
  def construct(from: ArrayCreatorRestContext): ArrayCreatorRest = {
    if (from.arrayInitializer() != null) {
      val arraySubs = 1 + from.arraySubscripts().getText.count(_ == '[')
      ArrayCreatorRest(arraySubs, Some(ArrayInitializer.construct(from.arrayInitializer())), List()).withContext(from)
    } else {
      val arraySubs = from.arraySubscripts().getText.count(_ == '[')
      val expressions: Seq[ExpressionContext] = from.expression()
      ArrayCreatorRest(arraySubs, None, Expression.construct(expressions.toList)).withContext(from)
    }
  }
}

final case class ArrayInitializer(variableInitializers: List[VariableInitializer]) extends CST {
  override def children(): List[CST] = variableInitializers
}

object ArrayInitializer {
  def construct(from: ArrayInitializerContext): ArrayInitializer = {
    val initializers: Seq[VariableInitializerContext] = from.variableInitializer()
    ArrayInitializer(VariableInitializer.construct(initializers.toList)).withContext(from)
  }
}

object Arguments {
  def construct(from: ArgumentsContext): List[Expression] = {
    if (from.expressionList() != null) {
      val expressions: Seq[ExpressionContext] = from.expressionList().expression()
      Expression.construct(expressions.toList)
    } else {
      List()
    }
  }
}

final case class MapCreatorRest(pairs: List[MapCreatorRestPair]) extends CST {
  override def children(): List[CST] = pairs
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = from.mapCreatorRestPair()
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList)).withContext(from)
  }
}

final case class MapCreatorRestPair(from: IdOrExpression, to: LiteralOrExpression) extends CST {
  override def children(): List[CST] = from :: to :: Nil
}

object MapCreatorRestPair {
  def construct(from: MapCreatorRestPairContext): MapCreatorRestPair = {
    MapCreatorRestPair(
      IdOrExpression.construct(from.idOrExpression()),
      LiteralOrExpression.construct(from.literalOrExpression())
    ).withContext(from)
  }

  def construct(aList: List[MapCreatorRestPairContext]): List[MapCreatorRestPair] = {
    aList.map(MapCreatorRestPair.construct)
  }
}

final case class SetCreatorRest(parts: List[LiteralOrExpression]) extends CST {
  override def children(): List[CST] = parts
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext): SetCreatorRest = {
    val parts: Seq[LiteralOrExpressionContext] = from.literalOrExpression()
    SetCreatorRest(LiteralOrExpression.construct(parts.toList)).withContext(from)
  }
}

final case class IdOrExpression(id: Option[String], expression: Option[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ expression
}

object IdOrExpression {
  def construct(from: IdOrExpressionContext): IdOrExpression = {
    IdOrExpression(
      if (from.id() != null)
        Some(from.id().getText)
      else
        None,
      if (from.expression() != null)
        Some(Expression.construct(from.expression()))
      else
        None
    ).withContext(from)
  }
}

final case class LiteralOrExpression(literal: Option[Literal], expression: Option[Expression]) extends CST {
  override def children(): List[CST] = literal match {
    case Some(x) => x :: Nil
    case None => expression match {
      case Some(x) => x :: Nil
      case None => Nil
    }
  }
}

object LiteralOrExpression {
  def construct(from: LiteralOrExpressionContext): LiteralOrExpression = {
    LiteralOrExpression(
      if (from.literal() != null)
        Some(Literal.construct(from.literal()))
      else
        None,
      if (from.expression() != null)
        Some(Expression.construct(from.expression()))
      else
        None
    ).withContext(from)
  }

  def construct(aList: List[LiteralOrExpressionContext]): List[LiteralOrExpression] = {
    aList.map(LiteralOrExpression.construct)
  }
}

final case class InnerCreator(nonWildcardTypeArgumentsOrDiamond: Option[NonWildcardTypeArgumentsOrDiamond],
                              classCreatorRest: ClassCreatorRest) extends CST {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArgumentsOrDiamond ++ List(classCreatorRest)
}

object InnerCreator {
  def construct(from: InnerCreatorContext): InnerCreator = {
    InnerCreator(
      if (from.nonWildcardTypeArgumentsOrDiamond() != null)
        Some(NonWildcardTypeArgumentsOrDiamond.construct(from.nonWildcardTypeArgumentsOrDiamond()))
      else
        None,
      ClassCreatorRest.construct(from.classCreatorRest())
    ).withContext(from)
  }
}

sealed abstract class CreatedName extends CST

final case class IdCreatedName(idPairs: List[IdCreatedNamePair]) extends CreatedName {
  override def children(): List[CST] = idPairs
}

final case class PrimitiveCreatedName(primitiveType: PrimitiveType) extends CreatedName {
  override def children(): List[CST] = primitiveType :: Nil
}

object CreatedName {
  def construct(from: CreatedNameContext): CreatedName = {
    if (from.primitiveType() != null)
      PrimitiveCreatedName(PrimitiveType.construct(from.primitiveType())).withContext(from)
    else {
      val pairs: Seq[IdCreatedNamePairContext] = from.idCreatedNamePair()
      IdCreatedName(IdCreatedNamePair.construct(pairs.toList)).withContext(from)
    }
  }
}

final case class IdCreatedNamePair(id: String, typeArgumentsOrDiamond: Option[TypeArgumentsOrDiamond]) extends CST {
  override def children(): List[CST] = List[CST]() ++ typeArgumentsOrDiamond
}

object IdCreatedNamePair {
  def construct(from: IdCreatedNamePairContext): IdCreatedNamePair = {
    IdCreatedNamePair(from.id.getText,
      if (from.typeArgumentsOrDiamond() != null)
        Some(TypeArgumentsOrDiamond.construct(from.typeArgumentsOrDiamond()))
      else
        None
    ).withContext(from)
  }

  def construct(aList: List[IdCreatedNamePairContext]): List[IdCreatedNamePair] = {
    aList.map(IdCreatedNamePair.construct)
  }
}

final case class SuperSuffix(id: Option[String], arguments: List[Expression]) extends CST {
  override def children(): List[CST] = arguments
}

object SuperSuffix {
  def construct(from: SuperSuffixContext): SuperSuffix = {
    SuperSuffix(
      if (from.id() != null)
        Some(from.id.getText)
      else
        None,
      if (from.arguments() != null)
        Arguments.construct(from.arguments())
      else
        List()
    ).withContext(from)
  }
}

final case class ExplicitGenericInvocationSuffix(supperSuffix: Option[SuperSuffix], id: Option[String], arguments: List[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ supperSuffix ++ arguments
}

object ExplicitGenericInvocationSuffix {
  def construct(from: ExplicitGenericInvocationSuffixContext): ExplicitGenericInvocationSuffix = {
    if (from.superSuffix() != null)
      ExplicitGenericInvocationSuffix(Some(SuperSuffix.construct(from.superSuffix())), None, List()).withContext(from)
    else
      ExplicitGenericInvocationSuffix(None, Some(from.id.getText), Arguments.construct(from.arguments())).withContext(from)
  }
}

final case class ExplicitGenericInvocation(nonWildcardTypeArguments: NonWildcardTypeArguments,
                                           explicitGenericInvocationSuffix: ExplicitGenericInvocationSuffix) extends CST {
  override def children(): List[CST] = nonWildcardTypeArguments :: explicitGenericInvocationSuffix :: Nil
}

object ExplicitGenericInvocation {
  def construct(from: ExplicitGenericInvocationContext): ExplicitGenericInvocation = {
    ExplicitGenericInvocation(
      NonWildcardTypeArguments.construct(from.nonWildcardTypeArguments()),
      ExplicitGenericInvocationSuffix.construct(from.explicitGenericInvocationSuffix())
    ).withContext(from)
  }
}

sealed abstract class Creator extends CST

final case class Alt1Creator(nonWildcardTypeArguments: NonWildcardTypeArguments, createdName: CreatedName,
                             classCreatorRest: ClassCreatorRest) extends Creator {
  override def children(): List[CST] = nonWildcardTypeArguments :: createdName :: classCreatorRest :: Nil
}

final case class Alt2Creator(createdName: CreatedName, arrayCreatorRest: Option[ArrayCreatorRest], classCreatorRest: Option[ClassCreatorRest],
                             mapCreatorRest: Option[MapCreatorRest], setCreatorRest: Option[SetCreatorRest]) extends Creator {
  override def children(): List[CST] = List[CST](createdName) ++ arrayCreatorRest ++ classCreatorRest ++ mapCreatorRest ++ setCreatorRest
}

object Creator {
  def construct(from: CreatorContext): Creator = {
    from match {
      case alt1: Alt1CreatorContext =>
        Alt1Creator(
          NonWildcardTypeArguments.construct(alt1.nonWildcardTypeArguments()),
          CreatedName.construct(alt1.createdName()),
          ClassCreatorRest.construct(alt1.classCreatorRest())
        ).withContext(from)
      case alt2: Alt2CreatorContext =>
        Alt2Creator(
          CreatedName.construct(alt2.createdName()),
          if (alt2.arrayCreatorRest() != null) Some(ArrayCreatorRest.construct(alt2.arrayCreatorRest())) else None,
          if (alt2.classCreatorRest() != null) Some(ClassCreatorRest.construct(alt2.classCreatorRest())) else None,
          if (alt2.mapCreatorRest() != null) Some(MapCreatorRest.construct(alt2.mapCreatorRest())) else None,
          if (alt2.setCreatorRest() != null) Some(SetCreatorRest.construct(alt2.setCreatorRest())) else None
        ).withContext(from)
    }
  }
}

sealed abstract class Literal() extends CST {
  override def children(): List[CST] = Nil
}

final case class IntegerLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): Type =
    if (value.endsWith("l") || value.endsWith("L"))
      LongType(0)
    else
      IntegerType(0)
}

final case class NumberLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): Type =
    if (value.length() > 50)
      DoubleType(0)
    else
      DecimalType(0)
}

final case class StringLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): Type = StringType(0)
}

final case class BooleanLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): Type = BooleanType(0)
}

final case class NullLit() extends Literal {
  override def getType(ctx: TypeContext): Type = NullType()
}

object Literal {
  def construct(from: LiteralContext): Literal = {
    val cst =
      if (from.IntegerLiteral() != null)
        IntegerLit(from.IntegerLiteral().getText)
      else if (from.NumberLiteral() != null)
        NumberLit(from.NumberLiteral().getText)
      else if (from.StringLiteral() != null)
        StringLit(from.StringLiteral().getText)
      else if (from.BooleanLiteral() != null)
        BooleanLit(from.BooleanLiteral().getText)
      else
        NullLit()
    cst.withContext(from)
  }
}

sealed abstract class Primary extends CST

final case class ExpressionPrimary(expression: Expression) extends Primary {
  override def children(): List[CST] = expression :: Nil

  override def getType(ctx: TypeContext): Type = expression.getType(ctx)
}

final case class ThisPrimary() extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): Type = ctx.thisType
}

final case class SuperPrimary() extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): Type = ctx.superType
}

final case class LiteralPrimary(literal: Literal) extends Primary {
  override def children(): List[CST] = literal :: Nil

  override def getType(ctx: TypeContext): Type = literal.getType(ctx)
}

final case class Identifier(text: String) extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): Type = ctx.getIdentifierType(text)
}

final case class TypeRefClassPrimary(typeRef: TypeRef) extends Primary {
  override def children(): List[CST] = typeRef :: Nil

  override def getType(ctx: TypeContext): Type = ClassType(typeRef)
}

final case class VoidClassPrimary() extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): Type = VoidClassType()
}

// Fake node for linking variable refs to their declarations
final case class VarRef(varDeclaration: VarDeclaration) extends Primary {
  override def children(): List[CST] = List(varDeclaration.name)
}

final case class MethodPrimary(nonWildcardTypeArguments: NonWildcardTypeArguments,
                               explicitGenericInvocationSuffix: Option[ExplicitGenericInvocationSuffix], arguments: List[Expression]) extends Primary {
  override def children(): List[CST] = List[CST](nonWildcardTypeArguments) ++ explicitGenericInvocationSuffix ++ arguments
}

final case class SOQL(soql: String) extends Primary {
  override def children(): List[CST] = Nil
}

object Primary {
  def construct(from: PrimaryContext): Primary = {
    val cst =
      from match {
        case alt1: Alt1PrimaryContext =>
          ExpressionPrimary(Expression.construct(alt1.expression()))
        case _: Alt2PrimaryContext =>
          ThisPrimary()
        case _: Alt3PrimaryContext =>
          SuperPrimary()
        case alt4: Alt4PrimaryContext =>
          LiteralPrimary(Literal.construct(alt4.literal()))
        case alt5: Alt5PrimaryContext =>
          Identifier(alt5.id().getText)
        case alt6: Alt6PrimaryContext =>
          TypeRefClassPrimary(TypeRef.construct(alt6.typeRef()))
        case _: Alt7PrimaryContext =>
          VoidClassPrimary()
        case alt8: Alt8PrimaryContext =>
          MethodPrimary(
            NonWildcardTypeArguments.construct(alt8.nonWildcardTypeArguments()),
            if (alt8.explicitGenericInvocationSuffix() != null)
              Some(ExplicitGenericInvocationSuffix.construct(alt8.explicitGenericInvocationSuffix()))
            else
              None,
            if (alt8.arguments() != null)
              Arguments.construct(alt8.arguments())
            else
              List()
          )
        case alt9: Alt9PrimaryContext =>
          SOQL(alt9.soqlLiteral().getText)
      }
    cst.withContext(from)
  }
}
