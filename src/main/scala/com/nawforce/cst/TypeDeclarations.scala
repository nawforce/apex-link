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

import java.nio.file.Path

import com.nawforce.parsers.ApexParser
import com.nawforce.parsers.ApexParser._
import com.nawforce.types.{ApexTypeDeclaration, CLASS_NATURE, ENUM_NATURE, GLOBAL_MODIFIER, INTERFACE_NATURE, Modifier, Nature, TypeName}
import com.nawforce.utils.{IssueLog, Name}

import scala.collection.JavaConverters._

final case class CompilationUnit(path: Path, typeDeclaration: ApexTypeDeclaration) extends CST {
  def children(): List[CST] = List(typeDeclaration)

  def verify(): Unit = typeDeclaration.verify()

  def resolve(index: CSTIndex): Unit = {
    typeDeclaration.resolve(index)
  }
}

object CompilationUnit {
  def construct(path: Path, compilationUnit: CompilationUnitContext, context: ConstructContext): CompilationUnit = {
    CompilationUnit(path,
      ApexTypeDeclaration.construct(None, compilationUnit.typeDeclaration(), context))
      .withContext(compilationUnit, context)
  }
}

final case class ClassDeclaration(_id: Id, _outerTypeName: Option[TypeName], _modifiers: Seq[Modifier],
                                  _extendsType: Option[TypeName], _implementsTypes: Seq[TypeName],
                                  _bodyDeclarations: Seq[ClassBodyDeclaration]) extends
  ApexTypeDeclaration(_id, _outerTypeName, _modifiers, _extendsType, _implementsTypes, _bodyDeclarations) {

  override val nature: Nature = CLASS_NATURE

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)

  override def verify(): Unit = {
    if (bodyDeclarations.exists(_.isGlobal) && !modifiers.contains(GLOBAL_MODIFIER)) {
      IssueLog.logMessage(id.textRange, "Classes enclosing globals must also be declared global")
    }
  }

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
    bodyDeclarations.foreach(_.resolve(index))
  }
}

object ClassDeclaration {
  def construct(outerTypeName: Option[TypeName], modifiers: Seq[Modifier], classDeclaration: ClassDeclarationContext,
                context: ConstructContext): ClassDeclaration = {

    val thisType = TypeName(Name(classDeclaration.id().getText)).withOuter(outerTypeName)
    val extendType =
      if (classDeclaration.typeRef() != null)
        Some(TypeRef.construct(classDeclaration.typeRef(), context))
      else
        None
    val implementsType =
      if (classDeclaration.typeList() != null)
        TypeList.construct(classDeclaration.typeList(), context)
      else
        Seq()

    val classBody = classDeclaration.classBody()
    val classBodyDeclarations: Seq[ClassBodyDeclarationContext] = classBody.classBodyDeclaration().asScala
    val bodyDeclarations: List[ClassBodyDeclaration] =
      if (classBodyDeclarations != null) {
        classBodyDeclarations.toList.map(cbd =>

          if (cbd.block() != null) {
            StaticBlock.construct(cbd.block(), context)
          } else if (cbd.memberDeclaration() != null) {
            val modifiers: Seq[ModifierContext] = cbd.modifier().asScala
            ClassBodyDeclaration.construct(Some(thisType), modifiers.toList, cbd.memberDeclaration(), context)
          } else {
            throw new CSTException()
          }
        )
      } else {
        List()
      }

    ClassDeclaration(Id.construct(classDeclaration.id(), context), outerTypeName, modifiers, extendType,
      implementsType, bodyDeclarations).withContext(classDeclaration, context)
  }
}

final case class InterfaceDeclaration(_id: Id, _outerTypeName: Option[TypeName], _modifiers: Seq[Modifier],
                                      implementsTypes: Seq[TypeName], _bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ApexTypeDeclaration(_id, _outerTypeName, _modifiers, None, implementsTypes, _bodyDeclarations) {

  override val nature: Nature = INTERFACE_NATURE

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)

  override def verify(): Unit = {}

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object InterfaceDeclaration {
  def construct(outerTypeName: Option[TypeName], modifiers: Seq[Modifier], interfaceDeclaration: ApexParser.InterfaceDeclarationContext, context: ConstructContext): InterfaceDeclaration = {
    val implementsType =
      if (interfaceDeclaration.typeList() != null)
        TypeList.construct(interfaceDeclaration.typeList(), context)
      else
        Seq()

    // TODO: Handle body
    InterfaceDeclaration(Id.construct(interfaceDeclaration.id(), context), outerTypeName, modifiers,
      implementsType, Seq())
      .withContext(interfaceDeclaration, context)
  }
}

final case class EnumDeclaration(_id: Id, _outerTypeName: Option[TypeName], _modifiers: Seq[Modifier],
                                 _bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ApexTypeDeclaration(_id, _outerTypeName, _modifiers, None, Seq(), _bodyDeclarations) {

  override val nature: Nature = ENUM_NATURE

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)

  override def verify(): Unit = {}

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object EnumDeclaration {
  def construct(outerTypeName: Option[TypeName], typeModifiers: Seq[Modifier],
                enumDeclaration: ApexParser.EnumDeclarationContext, context: ConstructContext): EnumDeclaration = {
    // TODO: Handle body
    EnumDeclaration(Id.construct(enumDeclaration.id(), context), outerTypeName, typeModifiers, Seq())
      .withContext(enumDeclaration, context)
  }
}
