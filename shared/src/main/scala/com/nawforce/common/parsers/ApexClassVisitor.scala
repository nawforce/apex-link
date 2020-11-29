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
package com.nawforce.common.parsers

import com.nawforce.common.modifiers.{ApexModifiers, ModifierResults}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.parsers.{CodeParser, TreeVisitor}

import scala.collection.compat.immutable.ArraySeq

class ApexClassVisitor(parser: CodeParser) extends TreeVisitor[ApexNode] {
  private var typeDepth = 0

  def typeWrap[T]()(op: => T): T = {
    typeDepth += 1
    try {
      op
    } finally {
      typeDepth -= 1
    }
  }

  override def classDeclaration(ctx: ClassDeclarationContext,
                                visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    typeWrap() {
      val modifierContext = getModifierContext(parentContext(ctx), typeDepth == 1)
      val modifiers =
        ApexModifiers.classModifiers(parser,
                                     modifierContext.get.modifiers,
                                     typeDepth == 1,
                                     ctx.id())
      val description =
        s"${modifiers.modifiers.mkString(" ")} class ${CodeParser.getText(ctx.id())}"

      ArraySeq(
        ApexClassNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                      IdAndRange(parser, ctx.id()),
                      visitChildren(ctx),
                      modifiers,
                      description))
    }
  }

  override def interfaceDeclaration(ctx: InterfaceDeclarationContext,
                                    visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    typeWrap() {
      val modifierContext = getModifierContext(parentContext(ctx), typeDepth == 1)
      val modifiers =
        ApexModifiers.interfaceModifiers(parser,
                                         modifierContext.get.modifiers,
                                         typeDepth == 1,
                                         ctx.id())
      val description =
        s"${modifiers.modifiers.mkString(" ")} interface ${CodeParser.getText(ctx.id())}"
      ArraySeq(
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                        ApexInterfaceType,
                        IdAndRange(parser, ctx.id()),
                        visitChildren(ctx),
                        modifiers,
                        description))
    }
  }

  override def enumDeclaration(ctx: EnumDeclarationContext,
                               visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    typeWrap() {
      val modifierContext = getModifierContext(parentContext(ctx), typeDepth == 1)
      val modifiers =
        ApexModifiers.enumModifiers(parser, modifierContext.get.modifiers, typeDepth == 1, ctx.id())
      val description = s"${modifiers.modifiers.mkString(" ")} enum ${CodeParser.getText(ctx.id())}"
      ArraySeq(
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                        ApexEnumType,
                        IdAndRange(parser, ctx.id()),
                        visitChildren(ctx),
                        modifiers,
                        description))
    }
  }

  override def constructorDeclaration(ctx: ConstructorDeclarationContext,
                                      visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers =
      ApexModifiers.constructorModifiers(parser, modifierContext.get.modifiers, ctx.qualifiedName())
    val params = formatFormalParameters(ctx.formalParameters())
    val description =
      s"${appendSpace(modifiers.modifiers.mkString(" "))}${CodeParser.getText(ctx.qualifiedName())}($params)"
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                      ApexConstructorType,
                      IdAndRange(parser, ctx.qualifiedName()),
                      ArraySeq(),
                      modifiers,
                      description))
  }

  override def methodDeclaration(ctx: MethodDeclarationContext,
                                 visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers = ApexModifiers.methodModifiers(parser, modifierContext.get.modifiers, ctx.id())
    val returnType = CodeParser.toScala(ctx.typeRef()).map(CodeParser.getText).getOrElse("void")
    val params = formatFormalParameters(ctx.formalParameters())
    val description =
      s"${appendSpace(modifiers.modifiers.mkString(" "))}$returnType ${CodeParser.getText(ctx.id())}($params)"
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                      ApexMethodType,
                      IdAndRange(parser, ctx.id()),
                      ArraySeq(),
                      modifiers,
                      description))
  }

  override def interfaceMethodDeclaration(ctx: InterfaceMethodDeclarationContext,
                                          visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifiers =
      ApexModifiers.interfaceMethodModifiers(parser, CodeParser.toScala(ctx.modifier()), ctx.id())
    val returnType = CodeParser.toScala(ctx.typeRef()).map(CodeParser.getText).getOrElse("void")
    val params = formatFormalParameters(ctx.formalParameters())
    val description =
      s"${appendSpace(modifiers.modifiers.mkString(" "))}$returnType ${CodeParser.getText(ctx.id())}($params)"
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(ctx)._2,
                      ApexMethodType,
                      IdAndRange(parser, ctx.id()),
                      ArraySeq(),
                      modifiers,
                      description))
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
    val modifiers = ApexModifiers.fieldModifiers(parser,
                                                 modifierContext.get.modifiers,
                                                 typeDepth == 1,
                                                 variableDeclarators.head.id())
    if (variableDeclarators.size == 1) {
      val vd = variableDeclarators.head
      val description =
        s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser.getText(vd.id())}"
      ArraySeq(
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                        ApexFieldType,
                        IdAndRange(parser, vd.id()),
                        ArraySeq(),
                        modifiers,
                        description))
    } else {
      ArraySeq(variableDeclarators.map(vd => {
        val description =
          s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser.getText(vd.id())}"
        ApexGenericNode(parser.source.path,
                        parser.getPathAndLocation(vd)._2,
                        ApexFieldType,
                        IdAndRange(parser, vd.id()),
                        ArraySeq(),
                        modifiers,
                        description)
      }): _*)
    }
  }

  override def propertyDeclaration(ctx: PropertyDeclarationContext,
                                   visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers =
      ApexModifiers.fieldModifiers(parser, modifierContext.get.modifiers, typeDepth == 1, ctx.id())
    val fieldType = CodeParser.getText(ctx.typeRef())
    val description =
      s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser.getText(ctx.id())}"
    ArraySeq(
      ApexGenericNode(parser.source.path,
                      parser.getPathAndLocation(modifierContext.get.enclosing)._2,
                      ApexPropertyType,
                      IdAndRange(parser, ctx.id()),
                      ArraySeq(),
                      modifiers,
                      description))
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
                          constantName)
        }): _*)
  }

  private def parentContext(ctx: ParserRuleContext): ParserRuleContext = {
    ctx.parent.asInstanceOf[ParserRuleContext]
  }

  case class ModifierContextDetails(enclosing: ParserRuleContext, modifiers: Seq[ModifierContext])

  private def getModifierContext(ctx: ParserRuleContext,
                                 isOuter: Boolean): Option[ModifierContextDetails] = {
    if (isOuter) {
      ctx match {
        case typeDeclarationContext: TypeDeclarationContext =>
          Some(
            ModifierContextDetails(typeDeclarationContext,
                                   CodeParser.toScala(typeDeclarationContext.modifier())))
        case _ => None
      }
    } else {
      classBodyModifierContext(parentContext(ctx))
    }
  }

  private def classBodyModifierContext(ctx: ParserRuleContext): Option[ModifierContextDetails] = {
    ctx match {
      case classBodyDeclarationContext: ClassBodyDeclarationContext =>
        Some(
          ModifierContextDetails(classBodyDeclarationContext,
                                 CodeParser.toScala(classBodyDeclarationContext.modifier())))
      case _ => None
    }
  }

  private def appendSpace(str: String): String = {
    if (str.nonEmpty)
      str + " "
    else
      str
  }
}
