/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.pkgforce.parsers

import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.modifiers._
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.parsers.{CodeParser, TreeVisitor}

import scala.collection.compat.immutable.ArraySeq
import scala.collection.mutable

class ApexClassVisitor(parser: CodeParser) extends TreeVisitor[ApexNode] {
  private val ownerNatureStack = mutable.Stack[MethodOwnerNature]()

  def typeWrap[T](ownerNature: MethodOwnerNature)(op: => T): T = {
    ownerNatureStack.push(ownerNature)
    try {
      op
    } finally {
      ownerNatureStack.pop()
    }
  }

  override def classDeclaration(ctx: ClassDeclarationContext,
                                visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val isOuter = ownerNatureStack.isEmpty
    val modifierContext = getModifierContext(parentContext(ctx))
    val classModifiers =
      ApexModifiers.classModifiers(parser, modifierContext.modifiers, isOuter, ctx.id())

    typeWrap(classModifiers.methodOwnerNature) {

      ArraySeq(
        ApexClassNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.enclosing)._2,
                      IdAndRange(parser, ctx.id()),
                      visitChildren(ctx),
                      classModifiers,
                      s"${classModifiers.modifiers.mkString(" ")} class ${CodeParser.getText(ctx.id())}",
                      classModifiers.modifiers.mkString(" ")))
    }
  }

  override def interfaceDeclaration(ctx: InterfaceDeclarationContext,
                                    visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val isOuter = ownerNatureStack.isEmpty

    typeWrap(INTERFACE_METHOD_NATURE) {
      val modifierContext =
        getModifierContext(parentContext(ctx))
      val modifiers =
        ApexModifiers.interfaceModifiers(parser, modifierContext.modifiers, isOuter, ctx.id())
      ArraySeq(
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(modifierContext.enclosing)._2,
                        ApexInterfaceType,
                        IdAndRange(parser, ctx.id()),
                        visitChildren(ctx),
                        modifiers,
                        s"${modifiers.modifiers.mkString(" ")} interface ${CodeParser.getText(ctx.id())}",
                        modifiers.modifiers.mkString(" ")))
    }
  }

  override def enumDeclaration(ctx: EnumDeclarationContext,
                               visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val isOuter = ownerNatureStack.isEmpty

    typeWrap(ENUM_METHOD_NATURE) {
      val modifierContext =
        getModifierContext(parentContext(ctx))
      val modifiers =
        ApexModifiers.enumModifiers(parser, modifierContext.modifiers, isOuter, ctx.id())
      ArraySeq(
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(modifierContext.enclosing)._2,
                        ApexEnumType,
                        IdAndRange(parser, ctx.id()),
                        visitChildren(ctx),
                        modifiers,
                        s"${modifiers.modifiers.mkString(" ")} enum ${CodeParser.getText(ctx.id())}",
                        modifiers.modifiers.mkString(" ")))
    }
  }

  override def constructorDeclaration(ctx: ConstructorDeclarationContext,
                                      visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers =
      ApexModifiers.constructorModifiers(parser, modifierContext.modifiers, ctx.qualifiedName())
    val params = formatFormalParameters(ctx.formalParameters())
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.enclosing)._2,
                      ApexConstructorType,
                      IdAndRange(parser, ctx.qualifiedName()),
                      ArraySeq(),
                      modifiers,
                      s"${appendSpace(modifiers.modifiers.mkString(" "))}${CodeParser.getText(
                        ctx.qualifiedName())}($params)",
                      s"($params) ${modifiers.modifiers.mkString(" ")}"))
  }

  override def methodDeclaration(ctx: MethodDeclarationContext,
                                 visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers = MethodModifiers.classMethodModifiers(parser,
                                                         modifierContext.modifiers,
                                                         ctx.id(),
                                                         ownerNatureStack.head,
                                                         ownerNatureStack.size == 1)
    val returnType = CodeParser.toScala(ctx.typeRef()).map(CodeParser.getText).getOrElse("void")
    val params = formatFormalParameters(ctx.formalParameters())
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.enclosing)._2,
                      ApexMethodType,
                      IdAndRange(parser, ctx.id()),
                      ArraySeq(),
                      modifiers,
                      s"${appendSpace(modifiers.modifiers.mkString(" "))}$returnType ${CodeParser
                        .getText(ctx.id())}($params)",
                      s"$returnType ($params) ${modifiers.modifiers.mkString(" ")}"))
  }

  override def interfaceMethodDeclaration(ctx: InterfaceMethodDeclarationContext,
                                          visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifiers =
      MethodModifiers.interfaceMethodModifiers(parser,
                                               CodeParser.toScala(ctx.modifier()),
                                               ctx.id(),
                                               ownerNatureStack.size == 1)

    val returnType = CodeParser.toScala(ctx.typeRef()).map(CodeParser.getText).getOrElse("void")
    val params = formatFormalParameters(ctx.formalParameters())
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(ctx)._2,
                      ApexMethodType,
                      IdAndRange(parser, ctx.id()),
                      ArraySeq(),
                      modifiers,
                      s"${appendSpace(modifiers.modifiers.mkString(" "))}$returnType ${CodeParser
                        .getText(ctx.id())}($params)",
                      s"$returnType ($params) ${modifiers.modifiers.mkString(" ")}"))
  }

  private def formatFormalParameters(ctx: FormalParametersContext): String = {
    val params = CodeParser
      .toScala(ctx.formalParameterList())
      .toSeq
      .flatMap(f => CodeParser.toScala(f.formalParameter()))
    params
      .map(fp => {
        val modifiers = CodeParser.toScala(fp.modifier()).map(CodeParser.getText).mkString(" ")
        val typeName = CodeParser.getText(fp.typeRef())
        val name = CodeParser.getText(fp.id())
        s"""${appendSpace(modifiers)}$typeName $name"""
      })
      .mkString(", ")
  }

  override def fieldDeclaration(ctx: FieldDeclarationContext,
                                visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val fieldType = CodeParser.getText(ctx.typeRef())
    val variableDeclarators = CodeParser.toScala(ctx.variableDeclarators().variableDeclarator())
    val modifiers = FieldModifiers.fieldModifiers(parser,
                                                  modifierContext.modifiers,
                                                  ownerNatureStack.size == 1,
                                                  variableDeclarators.head.id())
    if (variableDeclarators.size == 1) {
      val vd = variableDeclarators.head
      ArraySeq(
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(modifierContext.enclosing)._2,
                        ApexFieldType,
                        IdAndRange(parser, vd.id()),
                        ArraySeq(),
                        modifiers,
                        s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser
                          .getText(vd.id())}",
                        s"$fieldType ${modifiers.modifiers.mkString(" ")}"))
    } else {
      ArraySeq(variableDeclarators.map(vd => {
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(vd)._2,
                        ApexFieldType,
                        IdAndRange(parser, vd.id()),
                        ArraySeq(),
                        modifiers,
                        s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser
                          .getText(vd.id())}",
                        s"$fieldType ${modifiers.modifiers.mkString(" ")}")
      }): _*)
    }
  }

  override def propertyDeclaration(ctx: PropertyDeclarationContext,
                                   visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers =
      FieldModifiers.fieldModifiers(parser,
                                    modifierContext.modifiers,
                                    ownerNatureStack.size == 1,
                                    ctx.id())
    val fieldType = CodeParser.getText(ctx.typeRef())
    val description =
      s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser.getText(ctx.id())}"
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.enclosing)._2,
                      ApexPropertyType,
                      IdAndRange(parser, ctx.id()),
                      ArraySeq(),
                      modifiers,
                      s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser
                        .getText(ctx.id())}",
                      s"$fieldType ${modifiers.modifiers.mkString(" ")}"))
  }

  override def enumConstants(ctx: EnumConstantsContext,
                             visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    ArraySeq(
      CodeParser
        .toScala(ctx.id())
        .map(id => {
          val constantName = CodeParser.getText(id)
          ApexGenericNode(parser.source.path,
                          parser.getPathAndLocation(id)._2,
                          ApexEnumConstantType,
                          IdAndRange(parser, id),
                          ArraySeq(),
                          ModifierResults.empty,
                          constantName,
                          constantName)
        }): _*)
  }

  private def parentContext[T](ctx: ParserRuleContext): T = {
    ctx.parent.asInstanceOf[T]
  }

  case class ModifierContextDetails(enclosing: ParserRuleContext, modifiers: Seq[ModifierContext])

  private def getModifierContext(ctx: ParserRuleContext): ModifierContextDetails = {
    ctx match {
      case td: TypeDeclarationContext =>
        ModifierContextDetails(td, CodeParser.toScala(td.modifier()))
      case _ =>
        classBodyModifierContext(parentContext(ctx))
    }
  }

  private def classBodyModifierContext(ctx: ClassBodyDeclarationContext): ModifierContextDetails = {
    ModifierContextDetails(ctx, CodeParser.toScala(ctx.modifier()))
  }

  private def appendSpace(str: String): String = {
    if (str.nonEmpty)
      str + " "
    else
      str
  }
}
