/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.parsers

import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.Name
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.parsers.{CodeParser, TreeVisitor}

import scala.collection.immutable.ArraySeq
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

  override def classDeclaration(
    ctx: ClassDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val isOuter         = ownerNatureStack.isEmpty
    val modifierContext = getModifierContext(parentContext(ctx))
    val classModifiers =
      ApexModifiers.classModifiers(parser, modifierContext.modifiers, isOuter, ctx.id())

    typeWrap(classModifiers.methodOwnerNature) {
      ArraySeq(
        new ApexLightNode(
          parser.getPathLocation(parentContext(ctx)),
          CLASS_NATURE,
          Name(CodeParser.getText(ctx.id())),
          parser.getPathLocation(ctx.id()).location,
          visitChildren(ctx),
          classModifiers.modifiers,
          s"${classModifiers.modifiers.mkString(" ")} class ${CodeParser.getText(ctx.id())}",
          classModifiers.modifiers.mkString(" "),
          classModifiers.issues
        )
      )
    }
  }

  override def triggerDeclaration(ctx: TriggerUnitContext, visitChildren: VisitChildren): ArraySeq[ApexNode] = {
    val ids = CodeParser.toScala(ctx.id())
    ArraySeq(
      new ApexLightNode(
        parser.getPathLocation(ctx),
        TRIGGER_NATURE,
        Name(CodeParser.getText(ids.head)),
        parser.getPathLocation(ids.head).location,
        visitChildren(ctx),
        ArraySeq(),
        s"trigger ${CodeParser.getText(ids.head)} on ${CodeParser.getText(ids.tail.head)}",
        "",
        ArraySeq()
      )
    )
  }

  override def interfaceDeclaration(
    ctx: InterfaceDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val isOuter = ownerNatureStack.isEmpty

    typeWrap(INTERFACE_METHOD_NATURE) {
      val modifierContext = getModifierContext(parentContext(ctx))
      val modifiers =
        ApexModifiers.interfaceModifiers(parser, modifierContext.modifiers, isOuter, ctx.id())
      ArraySeq(
        new ApexLightNode(
          parser.getPathLocation(parentContext(ctx)),
          INTERFACE_NATURE,
          Name(CodeParser.getText(ctx.id())),
          parser.getPathLocation(ctx.id()).location,
          visitChildren(ctx),
          modifiers.modifiers,
          s"${modifiers.modifiers.mkString(" ")} interface ${CodeParser.getText(ctx.id())}",
          modifiers.modifiers.mkString(" "),
          modifiers.issues
        )
      )
    }
  }

  override def enumDeclaration(
    ctx: EnumDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val isOuter = ownerNatureStack.isEmpty

    typeWrap(ENUM_METHOD_NATURE) {
      val modifierContext = getModifierContext(parentContext(ctx))
      val modifiers =
        ApexModifiers.enumModifiers(parser, modifierContext.modifiers, isOuter, ctx.id())
      ArraySeq(
        new ApexLightNode(
          parser.getPathLocation(parentContext(ctx)),
          ENUM_NATURE,
          Name(CodeParser.getText(ctx.id())),
          parser.getPathLocation(ctx.id()).location,
          visitChildren(ctx),
          modifiers.modifiers,
          s"${modifiers.modifiers.mkString(" ")} enum ${CodeParser.getText(ctx.id())}",
          modifiers.modifiers.mkString(" "),
          modifiers.issues
        )
      )
    }
  }

  override def constructorDeclaration(
    ctx: ConstructorDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers =
      ApexModifiers.constructorModifiers(parser, modifierContext.modifiers, ctx.qualifiedName())
    ArraySeq(
      ApexConstructorNode(
        parser.getPathLocation(parentContext(parentContext(ctx))),
        Name(CodeParser.getText(ctx.qualifiedName())),
        parser.getPathLocation(ctx.qualifiedName()).location,
        ArraySeq(),
        modifiers.modifiers,
        modifiers.issues,
        formatFormalParameters(ctx.formalParameters())
      )
    )
  }

  override def methodDeclaration(
    ctx: MethodDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers = MethodModifiers.classMethodModifiers(
      parser,
      modifierContext.modifiers,
      ctx.id(),
      ownerNatureStack.head,
      ownerNatureStack.size == 1
    )
    ArraySeq(
      ApexMethodNode(
        parser.getPathLocation(parentContext(parentContext(ctx))),
        Name(CodeParser.getText(ctx.id())),
        parser.getPathLocation(ctx.id()).location,
        ArraySeq(),
        modifiers.modifiers,
        modifiers.issues,
        CodeParser.toScala(ctx.typeRef()).map(CodeParser.getText).getOrElse("void"),
        formatFormalParameters(ctx.formalParameters())
      )
    )
  }

  override def interfaceMethodDeclaration(
    ctx: InterfaceMethodDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val modifiers =
      MethodModifiers.interfaceMethodModifiers(
        parser,
        ArraySeq.from(CodeParser.toScala(ctx.modifier())),
        ctx.id(),
        ownerNatureStack.size == 1
      )

    ArraySeq(
      ApexMethodNode(
        parser.getPathLocation(ctx),
        Name(CodeParser.getText(ctx.id())),
        parser.getPathLocation(ctx.id()).location,
        ArraySeq(),
        modifiers.modifiers,
        modifiers.issues,
        CodeParser.toScala(ctx.typeRef()).map(CodeParser.getText).getOrElse("void"),
        formatFormalParameters(ctx.formalParameters())
      )
    )
  }

  private def formatFormalParameters(
    ctx: FormalParametersContext
  ): ArraySeq[ApexFormalParameter] = {
    val params = CodeParser
      .toScala(ctx.formalParameterList())
      .toSeq
      .flatMap(f => CodeParser.toScala(f.formalParameter()))
    ArraySeq.unsafeWrapArray(
      params
        .map(fp => {
          val modifiers = ApexModifiers.parameterModifiers(
            parser,
            ArraySeq.unsafeWrapArray(CodeParser.toScala(fp.modifier()).toArray),
            fp
          )
          ApexFormalParameter(
            modifiers.modifiers,
            CodeParser.getText(fp.typeRef()),
            CodeParser.getText(fp.id()),
            modifiers.issues
          )
        })
        .toArray
    )
  }

  override def fieldDeclaration(
    ctx: FieldDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val modifierContext     = classBodyModifierContext(parentContext(parentContext(ctx)))
    val fieldType           = CodeParser.getText(ctx.typeRef())
    val variableDeclarators = CodeParser.toScala(ctx.variableDeclarators().variableDeclarator())
    val modifiers = FieldModifiers.fieldModifiers(
      parser,
      modifierContext.modifiers,
      ownerNatureStack.size == 1,
      variableDeclarators.head.id()
    )
    if (variableDeclarators.size == 1) {
      val vd = variableDeclarators.head
      ArraySeq(
        new ApexLightNode(
          parser.getPathLocation(modifierContext.enclosing),
          FIELD_NATURE,
          Name(CodeParser.getText(vd.id())),
          parser.getPathLocation(vd.id()).location,
          ArraySeq(),
          modifiers.modifiers,
          s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser
            .getText(vd.id())}",
          s"$fieldType ${modifiers.modifiers.mkString(" ")}",
          modifiers.issues
        )
      )
    } else {
      ArraySeq.from(variableDeclarators.map(vd => {
        new ApexLightNode(
          parser.getPathLocation(vd),
          FIELD_NATURE,
          Name(CodeParser.getText(vd.id())),
          parser.getPathLocation(vd.id()).location,
          ArraySeq(),
          modifiers.modifiers,
          s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser
            .getText(vd.id())}",
          s"$fieldType ${modifiers.modifiers.mkString(" ")}",
          modifiers.issues
        )
      }))
    }
  }

  override def propertyDeclaration(
    ctx: PropertyDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    val modifierContext = classBodyModifierContext(parentContext(parentContext(ctx)))
    val modifiers =
      FieldModifiers.fieldModifiers(
        parser,
        modifierContext.modifiers,
        ownerNatureStack.size == 1,
        ctx.id()
      )
    val fieldType = CodeParser.getText(ctx.typeRef())
    ArraySeq(
      new ApexLightNode(
        parser.getPathLocation(modifierContext.enclosing),
        PROPERTY_NATURE,
        Name(CodeParser.getText(ctx.id())),
        parser.getPathLocation(ctx.id()).location,
        ArraySeq(),
        modifiers.modifiers,
        s"${appendSpace(modifiers.modifiers.mkString(" "))}$fieldType ${CodeParser
          .getText(ctx.id())}",
        s"$fieldType ${modifiers.modifiers.mkString(" ")}",
        modifiers.issues
      )
    )
  }

  override def enumConstants(
    ctx: EnumConstantsContext,
    visitChildren: VisitChildren
  ): ArraySeq[ApexNode] = {
    ArraySeq.from(
      CodeParser
        .toScala(ctx.id())
        .map(id => {
          val constantName = CodeParser.getText(id)
          new ApexLightNode(
            parser.getPathLocation(id),
            ENUM_CONSTANT_NATURE,
            Name(CodeParser.getText(id)),
            parser.getPathLocation(id).location,
            ArraySeq.empty,
            ArraySeq.empty,
            constantName,
            constantName,
            ArraySeq.empty
          )
        })
    )
  }

  private def parentContext[T](ctx: ParserRuleContext): T = {
    ctx.parent.asInstanceOf[T]
  }

  case class ModifierContextDetails(
    enclosing: ParserRuleContext,
    modifiers: ArraySeq[ModifierContext]
  )

  private def getModifierContext(ctx: ParserRuleContext): ModifierContextDetails = {
    ctx match {
      case td: TypeDeclarationContext =>
        ModifierContextDetails(td, ArraySeq.from(CodeParser.toScala(td.modifier())))
      case _ =>
        classBodyModifierContext(parentContext(ctx))
    }
  }

  private def classBodyModifierContext(ctx: ClassBodyDeclarationContext): ModifierContextDetails = {
    ModifierContextDetails(ctx, ArraySeq.from(CodeParser.toScala(ctx.modifier())))
  }

  private def appendSpace(str: String): String = {
    if (str.nonEmpty)
      str + " "
    else
      str
  }
}
