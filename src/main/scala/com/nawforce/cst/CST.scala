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
package com.nawforce.cst

import com.nawforce.parsers.ApexParser
import com.nawforce.parsers.ApexParser._
import com.nawforce.utils.CSTException
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConverters._
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

  def withContext(context: ParserRuleContext, constructContext: ConstructContext): this.type = {
    startPos = context.start.getStartIndex()
    endPos = context.stop.getStopIndex()

    this
  }

  def location(): String = {
    "From " + start() + " to " + end() + " length " + (end() - start())
  }

  def start(): Long = startPos

  def end(): Long = endPos

  def findExpressions(onlyOuter: Boolean): List[Expression] = {
    this match {
      case e: Expression => List(e) ++ (if (!onlyOuter) this.children().flatMap(_.findExpressions(onlyOuter)) else Nil)
      case _ => this.children().flatMap(_.findExpressions(onlyOuter))
    }
  }

  def findStatements(onlyOuter: Boolean): List[Statement] = {
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

  def get(name: String): mutable.Set[CST] = {
    val nodes: Option[mutable.Set[CST]] = cstIndex.get(name)
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
  def construct(primitiveType: PrimitiveTypeContext, arraySubs: Integer, context: ConstructContext): PrimitiveType = {
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
    cst.withContext(primitiveType, context)
  }
}

object Type {
  def construct(t: TypeRefContext, context: ConstructContext): Type = {
    if (t.primitiveType() != null) {
      PrimitiveType.construct(t.primitiveType(), 0, context).withContext(t, context)
    } else if (t.classOrInterfaceType() != null) {
      ClassOrInterfaceType.construct(t.classOrInterfaceType(), context).withContext(t, context)
    } else {
      throw new CSTException()
    }
  }
}

final case class TypeList(types: List[Type])

object TypeList {
  def construct(typeList: TypeListContext, context: ConstructContext): TypeList = {
    val types: Seq[TypeRefContext] = typeList.typeRef().asScala
    TypeList(types.toList.map(t => Type.construct(t, context).withContext(t, context)))
  }

  def empty(): TypeList = {
    TypeList(List())
  }
}

final case class ClassOrInterfaceTypePart(name: String, typeList: TypeList)

final case class ClassOrInterfaceType(name: List[ClassOrInterfaceTypePart]) extends Type

object ClassOrInterfaceType {
  def construct(classOrInterfaceType: ClassOrInterfaceTypeContext, context: ConstructContext): ClassOrInterfaceType = {
    val ids: Seq[IdContext] = classOrInterfaceType.id().asScala
    val typeArgs: Seq[TypeArgumentsContext] = classOrInterfaceType.typeArguments().asScala
    var i = 0
    val parts = for (id <- ids) yield {
      val hasTypes = typeArgs.length > i
      i += 1
      if (hasTypes)
        ClassOrInterfaceTypePart(id.getText, TypeList.construct(typeArgs(i - 1).typeList(), context))
      else
        ClassOrInterfaceTypePart(id.getText, TypeList.empty())
    }
    ClassOrInterfaceType(parts.toList).withContext(classOrInterfaceType, context)
  }
}

final case class ClassType(typeRef: TypeRef) extends Type

final case class VoidClassType() extends Type

final case class QualifiedName(names: List[String]) extends CST {
  override def children(): List[CST] = Nil
}

object QualifiedName {
  def construct(aList: List[QualifiedNameContext], context: ConstructContext): List[QualifiedName] = {
    aList.map(n => QualifiedName.construct(n, context))
  }

  def construct(qualifiedName: QualifiedNameContext, context: ConstructContext): QualifiedName = {
    val ids: Seq[IdContext] = qualifiedName.id().asScala
    QualifiedName(ids.toList.map(id => id.getText)).withContext(qualifiedName, context)
  }
}

final case class Annotation(name: QualifiedName, elementValuePairs: List[ElementValuePair], elementValue: Option[ElementValue]) extends CST {
  override def children(): List[CST] = List[CST]() ++ elementValuePairs ++ elementValue
}

object Annotation {
  def construct(annotation: AnnotationContext, context: ConstructContext): Annotation = {
    val elementValue =
      if (annotation.elementValue() != null) {
        Some(ElementValue.construct(annotation.elementValue(), context))
      } else {
        None
      }
    Annotation(QualifiedName.construct(annotation.qualifiedName(), context),
      ElementValuePairs.construct(annotation.elementValuePairs(), context),
      elementValue).withContext(annotation, context)
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
  def construct(aList: List[ElementValueContext], context: ConstructContext): List[ElementValue] = {
    aList.map(x => ElementValue.construct(x, context))
  }

  def construct(elementValue: ElementValueContext, context: ConstructContext): ElementValue = {
    if (elementValue.expression() != null) {
      ExpressionElementValue(Expression.construct(elementValue.expression(), context)).withContext(elementValue, context)
    } else if (elementValue.annotation() != null) {
      AnnotationElementValue(Annotation.construct(elementValue.annotation(), context)).withContext(elementValue, context)
    } else if (elementValue.elementValueArrayInitializer() != null) {
      ArrayInitializerElementValue(ElementValueArrayInitializer.construct(
        elementValue.elementValueArrayInitializer(), context)).withContext(elementValue, context)
    } else {
      throw new CSTException()
    }
  }
}

final case class ElementValueArrayInitializer(elementValues: List[ElementValue]) extends CST {
  override def children(): List[CST] = elementValues
}

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext, context: ConstructContext): ElementValueArrayInitializer = {
    val elements: Seq[ElementValueContext] = from.elementValue().asScala
    ElementValueArrayInitializer(elements.toList.map(x => ElementValue.construct(x, context))).withContext(from, context)
  }
}

final case class ElementValuePair(id: String, elementValue: ElementValue) extends CST {
  override def children(): List[CST] = elementValue :: Nil
}

object ElementValuePair {
  def construct(aList: List[ElementValuePairContext], context: ConstructContext): List[ElementValuePair] = {
    aList.map(x => ElementValuePair.construct(x, context))
  }

  def construct(from: ElementValuePairContext, context: ConstructContext): ElementValuePair = {
    ElementValuePair(from.id().getText, ElementValue.construct(from.elementValue(), context)).withContext(from, context)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext, context: ConstructContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs: Seq[ElementValuePairContext] = from.elementValuePair().asScala
      pairs.toList.map(x => ElementValuePair.construct(x, context))
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

  def construct(typeModifiers: List[ClassOrInterfaceModifierContext], context: ConstructContext): List[TypeModifier] = {
    typeModifiers.map(x => TypeModifier.construct(x, context))
  }

  def construct(typeModifier: ClassOrInterfaceModifierContext, context: ConstructContext): TypeModifier = {
    val cst =
      if (typeModifier.annotation() != null) {
        AnnotationModifier(Annotation.construct(typeModifier.annotation(), context))
      } else if (typeModifier.withSharing() != null) {
        WithSharingModifier()
      } else if (typeModifier.withoutSharing() != null) {
        WithoutSharingModifier()
      } else {
        construct(typeModifier.getText, context)
      }
    cst.withContext(typeModifier, context)
  }

  def construct(typeModifier: String, context: ConstructContext): TypeModifier = {
    typeModifier.toLowerCase match {
      case "public" => PublicModifier()
      case "protected" => ProtectedModifier()
      case "private" => PrivateModifier()
      case "static" => StaticModifier()
      case "abstract" => AbstractModifier()
      case "final" => FinalModifier()
      case "global" => GlobalModifier()
      case "webservice" => WebserviceModifier()
      case "override" => OverrideModifier()
      case "virtual" => VirtualModifier()
      case "testmethod" => TestMethodModifier()
      case _ => UnknownModifier(typeModifier)
    }
  }
}

sealed abstract class Modifier() extends TypeModifier

object Modifier {
  def construct(typeModifiers: List[ModifierContext], context: ConstructContext): List[TypeModifier] = {
    typeModifiers.map(x => Modifier.construct(x, context))
  }

  def construct(modifier: ModifierContext, context: ConstructContext): TypeModifier = {
    val cst =
      if (modifier.classOrInterfaceModifier() != null) {
        TypeModifier.construct(modifier.classOrInterfaceModifier(), context)
      } else {
        modifier.getText.toLowerCase() match {
          case "native" => NativeModifier()
          case "synchronized" => SynchronizedModifier()
          case "transient" => TransientModifier()
          case _ => throw new CSTException()
        }
      }
    cst.withContext(modifier, context)
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
  def construct(compilationUnit: CompilationUnitContext, context: ConstructContext): CompilationUnit = {
    val typeDecls: Seq[TypeDeclarationContext] = compilationUnit.typeDeclaration().asScala
    CompilationUnit(TypeDeclaration.construct(typeDecls.toList, context)).withContext(compilationUnit, context)
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

  def construct(typeDecls: List[TypeDeclarationContext], context: ConstructContext): List[TypeDeclaration] = {
    typeDecls.map(t => TypeDeclaration.construct(t, context))
  }

  def construct(typeDecl: TypeDeclarationContext, context: ConstructContext): TypeDeclaration = {
    val modifiers: Seq[ClassOrInterfaceModifierContext] = typeDecl.classOrInterfaceModifier().asScala
    val cst =
      if (typeDecl.classDeclaration() != null) {
        ClassDeclaration.construct(TypeModifier.construct(modifiers.toList, context), typeDecl.classDeclaration(), context)
      } else if (typeDecl.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(TypeModifier.construct(modifiers.toList, context), typeDecl.interfaceDeclaration(), context)
      } else if (typeDecl.enumDeclaration() != null) {
        EnumDeclaration.construct(TypeModifier.construct(modifiers.toList, context), typeDecl.enumDeclaration(), context)
      } else {
        // TODO: Empty type declaration?
        throw new CSTException()
      }
    cst.withContext(typeDecl, context)
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
  def construct(typeModifiers: List[TypeModifier], classDeclaration: ClassDeclarationContext, context: ConstructContext): ClassDeclaration = {
    val extendType =
      if (classDeclaration.typeRef() != null)
        Some(Type.construct(classDeclaration.typeRef(), context))
      else
        None
    val implementsType =
      if (classDeclaration.typeList() != null)
        TypeList.construct(classDeclaration.typeList(), context)
      else
        TypeList.empty()

    val classBody = classDeclaration.classBody()
    val classBodyDeclarations: Seq[ClassBodyDeclarationContext] = classBody.classBodyDeclaration().asScala
    val bodyDeclarations: List[ClassBodyDeclaration] =
      if (classBodyDeclarations != null) {
        classBodyDeclarations.toList.map(cbd =>

          if (cbd.block() != null) {
            StaticBlock.construct(cbd.block(), context)
          } else if (cbd.memberDeclaration() != null) {
            val modifiers: Seq[ModifierContext] = cbd.modifier().asScala
            ClassBodyDeclaration.construct(modifiers.toList, cbd.memberDeclaration(), context)
          } else {
            throw new CSTException()
          }
        )
      } else {
        List()
      }

    ClassDeclaration(classDeclaration.id().getText, typeModifiers, extendType,
      implementsType, bodyDeclarations).withContext(classDeclaration, context)
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
  def construct(typeModifiers: List[TypeModifier], interfaceDeclaration: ApexParser.InterfaceDeclarationContext, context: ConstructContext): InterfaceDeclaration = {
    val implementsType =
      if (interfaceDeclaration.typeList() != null)
        TypeList.construct(interfaceDeclaration.typeList(), context)
      else
        TypeList.empty()

    InterfaceDeclaration(interfaceDeclaration.id().getText, typeModifiers, implementsType).withContext(interfaceDeclaration, context)
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
  def construct(typeModifiers: List[TypeModifier], enumDeclaration: ApexParser.EnumDeclarationContext, context: ConstructContext): EnumDeclaration = {
    val implementsType =
      if (enumDeclaration.typeList() != null)
        TypeList.construct(enumDeclaration.typeList(), context)
      else
        TypeList.empty()
    EnumDeclaration(enumDeclaration.id().getText, typeModifiers, implementsType).withContext(enumDeclaration, context)
  }
}

trait ClassBodyDeclaration extends CST {
  def resolve(index: CSTIndex): Unit = {
    // Default to ignore
  }
}

object ClassBodyDeclaration {
  def construct(modifiers: List[ModifierContext], memberDeclarationContext: MemberDeclarationContext, context: ConstructContext): ClassBodyDeclaration = {
    val m = Modifier.construct(modifiers, context)
    val cst: ClassBodyDeclaration =
      if (memberDeclarationContext.methodDeclaration() != null) {
        MethodDeclaration.construct(m, memberDeclarationContext.methodDeclaration(), context)
      } else if (memberDeclarationContext.fieldDeclaration() != null) {
        FieldDeclaration.construct(m, memberDeclarationContext.fieldDeclaration(), context)
      } else if (memberDeclarationContext.constructorDeclaration() != null) {
        ConstructorDeclaration.construct(m, memberDeclarationContext.constructorDeclaration(), context)
      } else if (memberDeclarationContext.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(m, memberDeclarationContext.interfaceDeclaration(), context)
      } else if (memberDeclarationContext.enumDeclaration() != null) {
        EnumDeclaration.construct(m, memberDeclarationContext.enumDeclaration(), context)
      } else if (memberDeclarationContext.propertyDeclaration() != null) {
        PropertyDeclaration.construct(m, memberDeclarationContext.propertyDeclaration(), context)
      } else if (memberDeclarationContext.classDeclaration() != null) {
        ClassDeclaration.construct(m, memberDeclarationContext.classDeclaration(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(memberDeclarationContext, context)
  }

}

final case class StaticBlock(block: Block) extends ClassBodyDeclaration {
  override def children(): List[CST] = block.children()

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object StaticBlock {
  def construct(block: BlockContext, context: ConstructContext): StaticBlock = {
    StaticBlock(Block.construct(block, context))
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
  def construct(modifiers: List[TypeModifier], from: MethodDeclarationContext, context: ConstructContext): MethodDeclaration = {
    val typeRef = if (from.typeRef() != null) Some(TypeRef.construct(from.typeRef(), context)) else None
    val block = if (from.block != null) Some(Block.construct(from.block, context)) else None

    MethodDeclaration(modifiers,
      typeRef,
      from.id.getText,
      FormalParameters.construct(from.formalParameters(), context),
      block
    ).withContext(from, context)
  }
}

final case class FieldDeclaration(modifiers: List[TypeModifier], typeRef: TypeRef, variableDeclarators: VariableDeclarators) extends ClassBodyDeclaration {
  override def children(): List[CST] = typeRef :: variableDeclarators :: modifiers
}

object FieldDeclaration {
  def construct(modifiers: List[TypeModifier], fieldDeclaration: FieldDeclarationContext, context: ConstructContext): FieldDeclaration = {
    FieldDeclaration(modifiers, TypeRef.construct(fieldDeclaration.typeRef(), context),
      VariableDeclarators.construct(fieldDeclaration.variableDeclarators(), context)).withContext(fieldDeclaration, context)
  }
}

final case class ConstructorDeclaration(modifiers: List[TypeModifier], qualifiedName: QualifiedName,
                                        formalParameters: List[FormalParameter],
                                        block: Block) extends ClassBodyDeclaration {
  override def children(): List[CST] = modifiers ++ formalParameters ++ List(block)
}

object ConstructorDeclaration {
  def construct(modifiers: List[TypeModifier], from: ConstructorDeclarationContext, context: ConstructContext): ConstructorDeclaration = {
    ConstructorDeclaration(modifiers,
      QualifiedName.construct(from.qualifiedName(), context),
      FormalParameters.construct(from.formalParameters(), context),
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }
}

final case class PropertyDeclaration(modifiers: List[TypeModifier], typeRef: TypeRef, variableDeclarators: VariableDeclarators, propertyDeclaration: PropertyBodyDeclaration) extends ClassBodyDeclaration {
  override def children(): List[CST] = typeRef :: variableDeclarators :: propertyDeclaration :: modifiers
}

object PropertyDeclaration {
  def construct(modifiers: List[TypeModifier], propertyDeclaration: PropertyDeclarationContext, context: ConstructContext): PropertyDeclaration = {
    PropertyDeclaration(modifiers, TypeRef.construct(propertyDeclaration.typeRef(), context),
      VariableDeclarators.construct(propertyDeclaration.variableDeclarators(), context),
      PropertyBodyDeclaration.construct(propertyDeclaration.propertyBodyDeclaration(), context)).withContext(propertyDeclaration, context)
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
  def constructBlocks(blockContexts: List[BlockContext], context: ConstructContext): List[Block] = {
    blockContexts.map(bc => construct(bc, context))
  }

  def construct(blockContext: BlockContext, context: ConstructContext): Block = {
    val statements: Seq[StatementContext] = blockContext.statement().asScala
    construct(statements.toList, context)
  }

  def construct(statements: List[StatementContext], context: ConstructContext): Block = {
    Block(Statement.construct(statements, context))
  }

  def constructOption(blockContext: BlockContext, context: ConstructContext): Option[Block] = {
    if (blockContext != null)
      Some(construct(blockContext, context))
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

final case class EnhancedForControl(modifiers: List[VariableModifier], typeRef: TypeRef,
                                    id: Identifier, expression: Expression) extends ForControl with VarIntroducer {
  override def children(): List[CST] = typeRef :: id :: expression :: modifiers

  def resolve(context: ResolveStmtContext): Unit = {
    expression.resolve(new ResolveExprContext(context))
    context.addVarDeclaration(VarDeclaration(id, typeRef, this))
  }
}

object EnhancedForControl {
  def construct(from: EnhancedForControlContext, context: ConstructContext): EnhancedForControl = {
    val variableModifiers: Seq[VariableModifierContext] =
      if (from.variableModifier() != null) {
        from.variableModifier().asScala
      } else {
        Seq()
      }

    EnhancedForControl(
      VariableModifier.construct(variableModifiers.toList, context),
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

final case class CatchClause(modifiers: List[VariableModifier], catchType: CatchType, id: String, block: Block) extends CST {
  override def children(): List[CST] = modifiers ++ List(catchType) ++ List(block)

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
    val modifiers: Seq[VariableModifierContext] = from.variableModifier().asScala
    CatchClause(
      VariableModifier.construct(modifiers.toList, context),
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

final case class UpsertStatement(expression: Expression, id: String) extends Statement {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveStmtContext): Unit = expression.resolve(new ResolveExprContext(context))
}

object UpsertStatement {
  def construct(statement: UpsertStatementContext, context: ConstructContext): UpsertStatement = {
    val expression = Expression.construct(statement.expression(), context)
    if (statement.id() != null)
      UpsertStatement(expression, statement.id.getText).withContext(statement, context)
    else
      UpsertStatement(expression, null).withContext(statement, context)
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

sealed abstract class VariableModifier extends CST

final case class FinalVariableModifier() extends VariableModifier {
  override def children(): List[CST] = Nil
}

final case class AnnotationVariableModifier(annotation: Annotation) extends VariableModifier {
  override def children(): List[CST] = annotation :: Nil
}

object VariableModifier {
  def construct(variableModifiers: List[VariableModifierContext], context: ConstructContext): List[VariableModifier] = {
    variableModifiers.map(x => VariableModifier.construct(x, context))
  }

  def construct(variableModifierContext: VariableModifierContext, context: ConstructContext): VariableModifier = {
    if (variableModifierContext.annotation() != null) {
      AnnotationVariableModifier(Annotation.construct(variableModifierContext.annotation(), context)).withContext(variableModifierContext, context)
    } else {
      FinalVariableModifier().withContext(variableModifierContext, context)
    }
  }
}

sealed abstract class VariableInitializer() extends CST

object VariableInitializer {
  def construct(variableInitializers: List[VariableInitializerContext], context: ConstructContext): List[VariableInitializer] = {
    variableInitializers.map(x => VariableInitializer.construct(x, context))
  }

  def construct(variableInitializer: VariableInitializerContext, context: ConstructContext): VariableInitializer = {
    val cst =
      if (variableInitializer.expression() != null) {
        ExpressionVariableInitializer(Expression.construct(variableInitializer.expression(), context))
      } else if (variableInitializer.arrayInitializer() != null) {
        ArrayVariableInitializer.construct(variableInitializer.arrayInitializer(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(variableInitializer, context)
  }
}

final case class ArrayVariableInitializer(variableInitializers: List[VariableInitializer]) extends VariableInitializer {
  override def children(): List[CST] = variableInitializers
}

final case class ExpressionVariableInitializer(expression: Expression) extends VariableInitializer {
  override def children(): List[CST] = expression :: Nil
}

object ArrayVariableInitializer {
  def construct(arrayInitializer: ArrayInitializerContext, context: ConstructContext): ArrayVariableInitializer = {
    val variableInitializers: Seq[VariableInitializerContext] = arrayInitializer.variableInitializer().asScala
    ArrayVariableInitializer(variableInitializers.toList.map(x =>
      VariableInitializer.construct(x, context)
    )).withContext(arrayInitializer, context)
  }
}

final case class VariableDeclarator(id: Identifier, init: Option[VariableInitializer]) extends CST with VarIntroducer {
  override def children(): List[CST] = List[CST](id) ++ init
}

object VariableDeclarator {
  def construct(variableDeclarator: VariableDeclaratorContext, context: ConstructContext): VariableDeclarator = {
    val init =
      if (variableDeclarator.variableInitializer() != null) {
        Some(VariableInitializer.construct(variableDeclarator.variableInitializer(), context))
      } else {
        None
      }
    VariableDeclarator(Identifier(variableDeclarator.id().getText), init).withContext(variableDeclarator, context)
  }
}

final case class VariableDeclarators(declarators: List[VariableDeclarator]) extends CST {
  override def children(): List[CST] = declarators

  def resolve(typeRef: TypeRef, context: ResolveStmtContext): Unit = {
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
  def construct(variableDeclaratorsContext: VariableDeclaratorsContext, context: ConstructContext): VariableDeclarators = {
    val variableDeclarators: Seq[VariableDeclaratorContext] = variableDeclaratorsContext.variableDeclarator().asScala
    VariableDeclarators(variableDeclarators.toList.map(x => VariableDeclarator.construct(x, context))).withContext(variableDeclaratorsContext, context)
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
  def construct(aList: List[TypeRefContext], context: ConstructContext): List[TypeRef] = {
    aList.map(x => TypeRef.construct(x, context))
  }

  def construct(typeRef: TypeRefContext, context: ConstructContext): TypeRef = {
    val arraySubs = typeRef.arraySubscripts().getText.count(_ == '[')
    val cst =
      if (typeRef.classOrInterfaceType() != null) {
        ClassOrInterfaceTypeRef(ClassOrInterfaceType.construct(typeRef.classOrInterfaceType(), context), arraySubs)
      } else if (typeRef.primitiveType() != null) {
        PrimitiveTypeRef(PrimitiveType.construct(typeRef.primitiveType(), arraySubs, context))
      } else {
        throw new CSTException()
      }
    cst.withContext(typeRef, context)
  }
}

final case class LocalVariableDeclaration(modifiers: List[VariableModifier], typeRef: TypeRef, variableDeclarators: VariableDeclarators) extends CST {
  override def children(): List[CST] = modifiers ::: (typeRef :: variableDeclarators :: Nil)

  def resolve(context: ResolveStmtContext): Unit = variableDeclarators.resolve(typeRef, context)
}

object LocalVariableDeclaration {
  def construct(localVariableDeclaration: LocalVariableDeclarationContext, context: ConstructContext): LocalVariableDeclaration = {
    val modifiers =
      if (localVariableDeclaration.variableModifier() != null) {
        val v: Seq[VariableModifierContext] = localVariableDeclaration.variableModifier().asScala
        VariableModifier.construct(v.toList, context)
      } else {
        List()
      }
    LocalVariableDeclaration(modifiers, TypeRef.construct(localVariableDeclaration.typeRef(), context),
      VariableDeclarators.construct(localVariableDeclaration.variableDeclarators(), context)).withContext(localVariableDeclaration, context)
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
  def construct(propertyBlockContext: PropertyBlockContext, context: ConstructContext): PropertyBlock = {
    val modifiers: Seq[ModifierContext] = propertyBlockContext.modifier().asScala
    val cst =
      if (propertyBlockContext.getter() != null) {
        GetterPropertyBlock(Modifier.construct(modifiers.toList, context), Block.constructOption(propertyBlockContext.getter().block(), context))
      } else if (propertyBlockContext.setter() != null) {
        SetterPropertyBlock(Modifier.construct(modifiers.toList, context), Block.constructOption(propertyBlockContext.setter().block(), context))
      } else {
        throw new CSTException()
      }
    cst.withContext(propertyBlockContext, context)
  }
}

final case class PropertyBodyDeclaration(propertyBlocks: List[PropertyBlock]) extends CST {
  override def children(): List[CST] = propertyBlocks
}

object PropertyBodyDeclaration {
  def construct(propertyBodyDeclarationContext: PropertyBodyDeclarationContext, context: ConstructContext): PropertyBodyDeclaration = {
    val blocks: Seq[PropertyBlockContext] = propertyBodyDeclarationContext.propertyBlock().asScala
    PropertyBodyDeclaration(blocks.toList.map(x => PropertyBlock.construct(x, context))).withContext(propertyBodyDeclarationContext, context)
  }
}

final case class FormalParameter(modifiers: List[VariableModifier], typeRef: TypeRef, id: Identifier) extends CST {
  override def children(): List[CST] = modifiers ::: (typeRef :: id :: Nil)
}

object FormalParameter {
  def construct(aList: List[FormalParameterContext], context: ConstructContext): List[FormalParameter] = {
    aList.map(x => FormalParameter.construct(x, context))
  }

  def construct(from: FormalParameterContext, context: ConstructContext): FormalParameter = {
    val modifiers: List[VariableModifier] =
      if (from.variableModifier() != null) {
        val m: Seq[VariableModifierContext] = from.variableModifier().asScala
        VariableModifier.construct(m.toList, context)
      } else {
        List()
      }
    FormalParameter(modifiers, TypeRef.construct(from.typeRef(), context), Identifier(from.id.getText)).withContext(from, context)
  }
}

object FormalParameterList {
  def construct(from: FormalParameterListContext, context: ConstructContext): List[FormalParameter] = {
    if (from.formalParameter() != null) {
      val m: Seq[FormalParameterContext] = from.formalParameter().asScala
      FormalParameter.construct(m.toList, context)
    } else {
      List()
    }
  }
}

object FormalParameters {
  def construct(from: FormalParametersContext, context: ConstructContext): List[FormalParameter] = {
    if (from.formalParameterList() != null) {
      FormalParameterList.construct(from.formalParameterList(), context)
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

  override def resolve(context: ResolveExprContext): Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class QName(ids: List[String]) extends Expression {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class FunctionCall(callee: Expression, arguments: List[Expression]) extends Expression {
  override def children(): List[CST] = callee :: arguments

  override def resolve(context: ResolveExprContext): Unit = {
    callee.resolve(context)
    arguments.foreach(_.resolve(context))
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def children(): List[CST] = creator :: Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class TypeExpression(typeRef: TypeRef, expression: Expression) extends Expression {
  override def children(): List[CST] = typeRef :: expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class PostOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class PreOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  def resolve(context: ResolveExprContext): Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class InstanceOfExpression(expression: Expression, typeRef: TypeRef) extends Expression {
  override def children(): List[CST] = expression :: typeRef :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def children(): List[CST] = query :: lhs :: rhs :: Nil

  def resolve(context: ResolveExprContext): Unit = {
    query.resolve(context)
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def children(): List[CST] = primary :: Nil

  def resolve(context: ResolveExprContext): Unit = {
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

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSThis() extends ExpressionRHS {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSNew(nonWildcardTypeArguments: Option[NonWildcardTypeArguments], innerCreator: InnerCreator) extends ExpressionRHS {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArguments ++ List(innerCreator)

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSSuper(superSuffix: SuperSuffix) extends ExpressionRHS {
  override def children(): List[CST] = superSuffix :: Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSExplicitGenericInvocation(explicitGenericInvocation: ExplicitGenericInvocation) extends ExpressionRHS {
  override def children(): List[CST] = explicitGenericInvocation :: Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSArrayExpression(expression: Expression) extends ExpressionRHS {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

object Expression {
  def construct(from: ExpressionContext, context: ConstructContext): Expression = {
    val cst =
      from match {
        case alt1: Alt1ExpressionContext =>
          val lHSExpression = Expression.construct(alt1.expression(), context)
          lHSExpression match {
            case PrimaryExpression(Identifier(id)) =>
              QName(id :: alt1.id().getText :: Nil)
            case QName(ids) =>
              QName(ids ::: (alt1.id().getText :: Nil))
            case _ =>
              LHSExpression(lHSExpression, RHSId(alt1.id().getText))
          }
        case alt2: Alt2ExpressionContext =>
          LHSExpression(Expression.construct(alt2.expression(), context), RHSThis())
        case alt3: Alt3ExpressionContext =>
          LHSExpression(Expression.construct(alt3.expression(), context), RHSNew(
            if (alt3.nonWildcardTypeArguments() != null)
              Some(NonWildcardTypeArguments.construct(alt3.nonWildcardTypeArguments(), context))
            else
              None,
            InnerCreator.construct(alt3.innerCreator(), context)
          ))
        case alt4: Alt4ExpressionContext =>
          LHSExpression(Expression.construct(alt4.expression(), context), RHSSuper(
            SuperSuffix.construct(alt4.superSuffix(), context)
          ))
        case alt5: Alt5ExpressionContext =>
          LHSExpression(Expression.construct(alt5.expression(), context), RHSExplicitGenericInvocation(
            ExplicitGenericInvocation.construct(alt5.explicitGenericInvocation(), context)
          ))
        case alt6: Alt6ExpressionContext =>
          LHSExpression(Expression.construct(alt6.expression(0), context), RHSArrayExpression(
            Expression.construct(alt6.expression(1), context)
          ))
        case alt7: FunctionCallExpressionContext =>
          FunctionCall(Expression.construct(alt7.expression, context),
            if (alt7.expressionList() != null) {
              val expression: Seq[ExpressionContext] = alt7.expressionList().expression().asScala
              Expression.construct(expression.toList, context)
            } else {
              List()
            }
          )
        case alt8: Alt8ExpressionContext =>
          NewExpression(Creator.construct(alt8.creator(), context))
        case alt9: Alt9ExpressionContext =>
          TypeExpression(TypeRef.construct(alt9.typeRef(), context), Expression.construct(alt9.expression(), context))
        case alt10: Alt10ExpressionContext =>
          PostOpExpression(Expression.construct(alt10.expression(), context), alt10.getChild(1).getText)
        case alt11: Alt11ExpressionContext =>
          PreOpExpression(Expression.construct(alt11.expression(), context), alt11.getChild(0).getText)
        case alt12: Alt12ExpressionContext =>
          PostOpExpression(Expression.construct(alt12.expression(), context), alt12.getChild(0).getText)
        case alt13: Alt13ExpressionContext =>
          BinaryExpression(Expression.construct(alt13.expression(0), context), Expression.construct(alt13.expression(1), context), alt13.getChild(1).getText)
        case alt14: Alt14ExpressionContext =>
          BinaryExpression(Expression.construct(alt14.expression(0), context), Expression.construct(alt14.expression(1), context), alt14.getChild(1).getText)
        case alt15: Alt15ExpressionContext =>
          BinaryExpression(Expression.construct(alt15.expression(0), context), Expression.construct(alt15.expression(1), context), alt15.getChild(1).getText)
        case alt16: Alt16ExpressionContext =>
          BinaryExpression(Expression.construct(alt16.expression(0), context), Expression.construct(alt16.expression(1), context), alt16.getChild(1).getText)
        case alt17: Alt17ExpressionContext =>
          InstanceOfExpression(Expression.construct(alt17.expression(), context), TypeRef.construct(alt17.typeRef(), context))
        case alt18: Alt18ExpressionContext =>
          BinaryExpression(Expression.construct(alt18.expression(0), context), Expression.construct(alt18.expression(1), context), alt18.getChild(1).getText)
        case alt19: Alt19ExpressionContext =>
          BinaryExpression(Expression.construct(alt19.expression(0), context), Expression.construct(alt19.expression(1), context), alt19.getChild(1).getText)
        case alt20: Alt20ExpressionContext =>
          BinaryExpression(Expression.construct(alt20.expression(0), context), Expression.construct(alt20.expression(1), context), alt20.getChild(1).getText)
        case alt21: Alt21ExpressionContext =>
          BinaryExpression(Expression.construct(alt21.expression(0), context), Expression.construct(alt21.expression(1), context), alt21.getChild(1).getText)
        case alt22: Alt22ExpressionContext =>
          BinaryExpression(Expression.construct(alt22.expression(0), context), Expression.construct(alt22.expression(1), context), alt22.getChild(1).getText)
        case alt23: Alt23ExpressionContext =>
          BinaryExpression(Expression.construct(alt23.expression(0), context), Expression.construct(alt23.expression(1), context), alt23.getChild(1).getText)
        case alt24: Alt24ExpressionContext =>
          QueryExpression(Expression.construct(alt24.expression(0), context), Expression.construct(alt24.expression(1), context), Expression.construct(alt24.expression(2), context))
        case alt25: Alt25ExpressionContext =>
          BinaryExpression(Expression.construct(alt25.expression(0), context), Expression.construct(alt25.expression(1), context), alt25.getChild(1).getText)
        case alt26: Alt26ExpressionContext =>
          PrimaryExpression(Primary.construct(alt26.primary(), context))
      }
    cst.withContext(from, context)
  }

  def construct(expression: List[ExpressionContext], context: ConstructContext): List[Expression] = {
    expression.map(x => Expression.construct(x, context))
  }
}

final case class NonWildcardTypeArguments(typeList: TypeList) extends CST {
  override def children(): List[CST] = typeList.types
}

object NonWildcardTypeArguments {
  def construct(from: NonWildcardTypeArgumentsContext, context: ConstructContext): NonWildcardTypeArguments = {
    NonWildcardTypeArguments(TypeList.construct(from.typeList(), context)).withContext(from, context)
  }
}

final case class NonWildcardTypeArgumentsOrDiamond(nonWildcardTypeArguments: Option[NonWildcardTypeArguments]) extends CST {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArguments
}

object NonWildcardTypeArgumentsOrDiamond {
  def construct(from: NonWildcardTypeArgumentsOrDiamondContext, context: ConstructContext): NonWildcardTypeArgumentsOrDiamond = {
    NonWildcardTypeArgumentsOrDiamond(
      if (from.nonWildcardTypeArguments() != null)
        Some(NonWildcardTypeArguments.construct(from.nonWildcardTypeArguments(), context))
      else None).withContext(from, context)
  }
}

final case class TypeArgumentsOrDiamond(typeArguments: Option[TypeArguments]) extends CST {
  override def children(): List[CST] = List[CST]() ++ typeArguments
}

object TypeArgumentsOrDiamond {
  def construct(from: TypeArgumentsOrDiamondContext, context: ConstructContext): TypeArgumentsOrDiamond = {
    TypeArgumentsOrDiamond(
      if (from.typeArguments() != null)
        Some(TypeArguments.construct(from.typeArguments(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class TypeArguments(typeList: List[TypeRef]) extends CST {
  override def children(): List[CST] = typeList
}

object TypeArguments {
  def construct(from: TypeArgumentsContext, context: ConstructContext): TypeArguments = {
    val types: Seq[TypeRefContext] = from.typeList().typeRef().asScala
    TypeArguments(TypeRef.construct(types.toList, context)).withContext(from, context)
  }
}

final case class ClassCreatorRest(arguments: List[Expression], expressionList: List[Expression]) extends CST {
  override def children(): List[CST] = arguments ::: expressionList
}

object ClassCreatorRest {
  def construct(from: ClassCreatorRestContext, context: ConstructContext): ClassCreatorRest = {
    ClassCreatorRest(
      if (from.arguments() != null)
        Arguments.construct(from.arguments(), context)
      else
        List(),
      if (from.expressionList() != null) {
        val expressions: Seq[ExpressionContext] = from.expressionList().expression().asScala
        Expression.construct(expressions.toList, context)
      } else {
        List()
      }
    ).withContext(from, context)
  }
}

final case class ArrayCreatorRest(arraySubs: Int, arrayInitializer: Option[ArrayInitializer],
                                  expressions: List[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ arrayInitializer ++ expressions
}

object ArrayCreatorRest {
  def construct(from: ArrayCreatorRestContext, context: ConstructContext): ArrayCreatorRest = {
    if (from.arrayInitializer() != null) {
      val arraySubs = 1 + from.arraySubscripts().getText.count(_ == '[')
      ArrayCreatorRest(arraySubs, Some(ArrayInitializer.construct(from.arrayInitializer(), context)), List()).withContext(from, context)
    } else {
      val arraySubs = from.arraySubscripts().getText.count(_ == '[')
      val expressions: Seq[ExpressionContext] = from.expression().asScala
      ArrayCreatorRest(arraySubs, None, Expression.construct(expressions.toList, context)).withContext(from, context)
    }
  }
}

final case class ArrayInitializer(variableInitializers: List[VariableInitializer]) extends CST {
  override def children(): List[CST] = variableInitializers
}

object ArrayInitializer {
  def construct(from: ArrayInitializerContext, context: ConstructContext): ArrayInitializer = {
    val initializers: Seq[VariableInitializerContext] = from.variableInitializer().asScala
    ArrayInitializer(VariableInitializer.construct(initializers.toList, context)).withContext(from, context)
  }
}

object Arguments {
  def construct(from: ArgumentsContext, context: ConstructContext): List[Expression] = {
    if (from.expressionList() != null) {
      val expressions: Seq[ExpressionContext] = from.expressionList().expression().asScala
      Expression.construct(expressions.toList, context)
    } else {
      List()
    }
  }
}

final case class MapCreatorRest(pairs: List[MapCreatorRestPair]) extends CST {
  override def children(): List[CST] = pairs
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext, context: ConstructContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = from.mapCreatorRestPair().asScala
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRestPair(from: IdOrExpression, to: LiteralOrExpression) extends CST {
  override def children(): List[CST] = from :: to :: Nil
}

object MapCreatorRestPair {
  def construct(aList: List[MapCreatorRestPairContext], context: ConstructContext): List[MapCreatorRestPair] = {
    aList.map(x => MapCreatorRestPair.construct(x, context))
  }

  def construct(from: MapCreatorRestPairContext, context: ConstructContext): MapCreatorRestPair = {
    MapCreatorRestPair(
      IdOrExpression.construct(from.idOrExpression(), context),
      LiteralOrExpression.construct(from.literalOrExpression(), context)
    ).withContext(from, context)
  }
}

final case class SetCreatorRest(parts: List[LiteralOrExpression]) extends CST {
  override def children(): List[CST] = parts
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext, context: ConstructContext): SetCreatorRest = {
    val parts: Seq[LiteralOrExpressionContext] = from.literalOrExpression().asScala
    SetCreatorRest(LiteralOrExpression.construct(parts.toList, context)).withContext(from, context)
  }
}

final case class IdOrExpression(id: Option[String], expression: Option[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ expression
}

object IdOrExpression {
  def construct(from: IdOrExpressionContext, context: ConstructContext): IdOrExpression = {
    IdOrExpression(
      if (from.id() != null)
        Some(from.id().getText)
      else
        None,
      if (from.expression() != null)
        Some(Expression.construct(from.expression(), context))
      else
        None
    ).withContext(from, context)
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
  def construct(aList: List[LiteralOrExpressionContext], context: ConstructContext): List[LiteralOrExpression] = {
    aList.map(x => LiteralOrExpression.construct(x, context))
  }

  def construct(from: LiteralOrExpressionContext, context: ConstructContext): LiteralOrExpression = {
    LiteralOrExpression(
      if (from.literal() != null)
        Some(Literal.construct(from.literal(), context))
      else
        None,
      if (from.expression() != null)
        Some(Expression.construct(from.expression(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class InnerCreator(nonWildcardTypeArgumentsOrDiamond: Option[NonWildcardTypeArgumentsOrDiamond],
                              classCreatorRest: ClassCreatorRest) extends CST {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArgumentsOrDiamond ++ List(classCreatorRest)
}

object InnerCreator {
  def construct(from: InnerCreatorContext, context: ConstructContext): InnerCreator = {
    InnerCreator(
      if (from.nonWildcardTypeArgumentsOrDiamond() != null)
        Some(NonWildcardTypeArgumentsOrDiamond.construct(from.nonWildcardTypeArgumentsOrDiamond(), context))
      else
        None,
      ClassCreatorRest.construct(from.classCreatorRest(), context)
    ).withContext(from, context)
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
  def construct(from: CreatedNameContext, context: ConstructContext): CreatedName = {
    if (from.primitiveType() != null)
      PrimitiveCreatedName(PrimitiveType.construct(from.primitiveType(), 0, context)).withContext(from, context)
    else {
      val pairs: Seq[IdCreatedNamePairContext] = from.idCreatedNamePair().asScala
      IdCreatedName(IdCreatedNamePair.construct(pairs.toList, context)).withContext(from, context)
    }
  }
}

final case class IdCreatedNamePair(id: String, typeArgumentsOrDiamond: Option[TypeArgumentsOrDiamond]) extends CST {
  override def children(): List[CST] = List[CST]() ++ typeArgumentsOrDiamond
}

object IdCreatedNamePair {
  def construct(aList: List[IdCreatedNamePairContext], context: ConstructContext): List[IdCreatedNamePair] = {
    aList.map(x => IdCreatedNamePair.construct(x, context))
  }

  def construct(from: IdCreatedNamePairContext, context: ConstructContext): IdCreatedNamePair = {
    IdCreatedNamePair(from.id.getText,
      if (from.typeArgumentsOrDiamond() != null)
        Some(TypeArgumentsOrDiamond.construct(from.typeArgumentsOrDiamond(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class SuperSuffix(id: Option[String], arguments: List[Expression]) extends CST {
  override def children(): List[CST] = arguments
}

object SuperSuffix {
  def construct(from: SuperSuffixContext, context: ConstructContext): SuperSuffix = {
    SuperSuffix(
      if (from.id() != null)
        Some(from.id.getText)
      else
        None,
      if (from.arguments() != null)
        Arguments.construct(from.arguments(), context)
      else
        List()
    ).withContext(from, context)
  }
}

final case class ExplicitGenericInvocationSuffix(supperSuffix: Option[SuperSuffix], id: Option[String], arguments: List[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ supperSuffix ++ arguments
}

object ExplicitGenericInvocationSuffix {
  def construct(from: ExplicitGenericInvocationSuffixContext, context: ConstructContext): ExplicitGenericInvocationSuffix = {
    if (from.superSuffix() != null)
      ExplicitGenericInvocationSuffix(Some(SuperSuffix.construct(from.superSuffix(), context)), None, List()).withContext(from, context)
    else
      ExplicitGenericInvocationSuffix(None, Some(from.id.getText), Arguments.construct(from.arguments(), context)).withContext(from, context)
  }
}

final case class ExplicitGenericInvocation(nonWildcardTypeArguments: NonWildcardTypeArguments,
                                           explicitGenericInvocationSuffix: ExplicitGenericInvocationSuffix) extends CST {
  override def children(): List[CST] = nonWildcardTypeArguments :: explicitGenericInvocationSuffix :: Nil
}

object ExplicitGenericInvocation {
  def construct(from: ExplicitGenericInvocationContext, context: ConstructContext): ExplicitGenericInvocation = {
    ExplicitGenericInvocation(
      NonWildcardTypeArguments.construct(from.nonWildcardTypeArguments(), context),
      ExplicitGenericInvocationSuffix.construct(from.explicitGenericInvocationSuffix(), context)
    ).withContext(from, context)
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
  def construct(from: CreatorContext, context: ConstructContext): Creator = {
    from match {
      case alt1: Alt1CreatorContext =>
        Alt1Creator(
          NonWildcardTypeArguments.construct(alt1.nonWildcardTypeArguments(), context),
          CreatedName.construct(alt1.createdName(), context),
          ClassCreatorRest.construct(alt1.classCreatorRest(), context)
        ).withContext(from, context)
      case alt2: Alt2CreatorContext =>
        Alt2Creator(
          CreatedName.construct(alt2.createdName(), context),
          if (alt2.arrayCreatorRest() != null) Some(ArrayCreatorRest.construct(alt2.arrayCreatorRest(), context)) else None,
          if (alt2.classCreatorRest() != null) Some(ClassCreatorRest.construct(alt2.classCreatorRest(), context)) else None,
          if (alt2.mapCreatorRest() != null) Some(MapCreatorRest.construct(alt2.mapCreatorRest(), context)) else None,
          if (alt2.setCreatorRest() != null) Some(SetCreatorRest.construct(alt2.setCreatorRest(), context)) else None
        ).withContext(from, context)
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
  def construct(from: LiteralContext, context: ConstructContext): Literal = {
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
    cst.withContext(from, context)
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
  def construct(from: PrimaryContext, context: ConstructContext): Primary = {
    val cst =
      from match {
        case alt1: Alt1PrimaryContext =>
          ExpressionPrimary(Expression.construct(alt1.expression(), context))
        case _: Alt2PrimaryContext =>
          ThisPrimary()
        case _: Alt3PrimaryContext =>
          SuperPrimary()
        case alt4: Alt4PrimaryContext =>
          LiteralPrimary(Literal.construct(alt4.literal(), context))
        case alt5: Alt5PrimaryContext =>
          Identifier(alt5.id().getText)
        case alt6: Alt6PrimaryContext =>
          TypeRefClassPrimary(TypeRef.construct(alt6.typeRef(), context))
        case _: Alt7PrimaryContext =>
          VoidClassPrimary()
        case alt8: Alt8PrimaryContext =>
          MethodPrimary(
            NonWildcardTypeArguments.construct(alt8.nonWildcardTypeArguments(), context),
            if (alt8.explicitGenericInvocationSuffix() != null)
              Some(ExplicitGenericInvocationSuffix.construct(alt8.explicitGenericInvocationSuffix(), context))
            else
              None,
            if (alt8.arguments() != null)
              Arguments.construct(alt8.arguments(), context)
            else
              List()
          )
        case alt9: Alt9PrimaryContext =>
          SOQL(alt9.soqlLiteral().getText)
      }
    cst.withContext(from, context)
  }
}
