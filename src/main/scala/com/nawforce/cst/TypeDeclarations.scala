package com.nawforce.cst

import com.nawforce.parsers.ApexParser
import com.nawforce.parsers.ApexParser._
import com.nawforce.types.{ApexModifiers, Modifier}
import com.nawforce.utils.CSTException

import scala.collection.JavaConverters._

final case class CompilationUnit(typeDeclaration: TypeDeclaration) extends CST {
  def children(): List[CST] = List(typeDeclaration)

  def resolve(index: CSTIndex): Unit = {
    typeDeclaration.resolve(index)
  }
}

object CompilationUnit {
  def construct(compilationUnit: CompilationUnitContext, context: ConstructContext): CompilationUnit = {
    CompilationUnit(TypeDeclaration.construct(compilationUnit.typeDeclaration(), context)).withContext(compilationUnit, context)
  }
}

// Treat TypeDeclarations as ClassBodyDeclarations for inner class, interface & enum
sealed abstract class TypeDeclaration(val modifiers: Seq[Modifier]) extends CST with ClassBodyDeclaration {
  def resolve(index: CSTIndex)
}

object TypeDeclaration {

  def construct(typeDecl: TypeDeclarationContext, context: ConstructContext): TypeDeclaration = {
    val modifiers: Seq[ModifierContext] = typeDecl.modifier().asScala
    val cst =
      if (typeDecl.classDeclaration() != null) {
        ClassDeclaration.construct(ApexModifiers.construct(modifiers, context), typeDecl.classDeclaration(), context)
      } else if (typeDecl.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(ApexModifiers.construct(modifiers, context), typeDecl.interfaceDeclaration(), context)
      } else if (typeDecl.enumDeclaration() != null) {
        EnumDeclaration.construct(ApexModifiers.construct(modifiers, context), typeDecl.enumDeclaration(), context)
      } else {
        // TODO: Empty type declaration?
        throw new CSTException()
      }
    cst.withContext(typeDecl, context)
  }
}

final case class ClassDeclaration(name: String, _modifiers: Seq[Modifier],
                                  extendsType: Option[Type], implementsTypes: TypeList,
                                  bodyDeclarations: List[ClassBodyDeclaration]) extends
  TypeDeclaration(_modifiers) {

  override def children(): List[CST] = {
    List() ++ extendsType ++ implementsTypes.types ++ bodyDeclarations
  }

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
    bodyDeclarations.foreach(_.resolve(index))
  }
}

object ClassDeclaration {
  def construct(modifiers: Seq[Modifier], classDeclaration: ClassDeclarationContext, context: ConstructContext): ClassDeclaration = {
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

    ClassDeclaration(classDeclaration.id().getText, modifiers, extendType,
      implementsType, bodyDeclarations).withContext(classDeclaration, context)
  }
}

// TODO: Handle body
final case class InterfaceDeclaration(name: String, _modifiers: Seq[Modifier], implementsTypes: TypeList)
  extends TypeDeclaration(_modifiers) {

  override def children(): List[CST] = implementsTypes.types

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object InterfaceDeclaration {
  def construct(modifiers: Seq[Modifier], interfaceDeclaration: ApexParser.InterfaceDeclarationContext, context: ConstructContext): InterfaceDeclaration = {
    val implementsType =
      if (interfaceDeclaration.typeList() != null)
        TypeList.construct(interfaceDeclaration.typeList(), context)
      else
        TypeList.empty()

    InterfaceDeclaration(interfaceDeclaration.id().getText, modifiers, implementsType).withContext(interfaceDeclaration, context)
  }
}

// TODO: Handle body
final case class EnumDeclaration(name: String, _modifiers: Seq[Modifier], implementsTypes: TypeList)
  extends TypeDeclaration(_modifiers) {

  override def children(): List[CST] = implementsTypes.types

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object EnumDeclaration {
  def construct(typeModifiers: Seq[Modifier], enumDeclaration: ApexParser.EnumDeclarationContext, context: ConstructContext): EnumDeclaration = {
    val implementsType =
      if (enumDeclaration.typeList() != null)
        TypeList.construct(enumDeclaration.typeList(), context)
      else
        TypeList.empty()
    EnumDeclaration(enumDeclaration.id().getText, typeModifiers, implementsType).withContext(enumDeclaration, context)
  }
}
