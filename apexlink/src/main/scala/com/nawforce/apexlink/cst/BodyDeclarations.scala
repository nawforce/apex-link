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

import com.nawforce.apexlink.finding.{RelativeTypeContext, RelativeTypeName}
import com.nawforce.apexlink.memory.SkinnySet
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.apex.{
  ApexBlockLike,
  ApexConstructorLike,
  ApexFieldLike,
  ApexMethodLike,
  ThisType
}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.diagnostics.Issue
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers._
import com.nawforce.pkgforce.path.Location
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

abstract class ClassBodyDeclaration(modifierResults: ModifierResults)
    extends CST
    with DependencyHolder
    with ApexNode {

  val modifiers: ArraySeq[Modifier] = modifierResults.modifiers

  def modifierIssues: ArraySeq[Issue] = modifierResults.issues

  // For ApexNode, not used yet used in CST
  override val parseIssues: ArraySeq[Issue] = null
  override lazy val signature: String       = null
  override val description: String          = null

  lazy val isGlobal: Boolean =
    modifiers.contains(GLOBAL_MODIFIER) || modifiers.contains(WEBSERVICE_MODIFIER)

  protected var depends: Option[SkinnySet[Dependent]] = None

  override def dependencies(): Iterable[Dependent] = {
    depends.map(_.toIterable).getOrElse(Array().toIterable)
  }

  def setDepends(dependencies: SkinnySet[Dependent]): Unit = {
    if (dependencies.isEmpty)
      depends = None
    else
      depends = Some(dependencies)
  }

  def collectDependencies(dependsOn: mutable.Set[Dependent]): Unit = {
    dependencies().foreach(dependsOn.add)
  }

  def validate(context: BodyDeclarationVerifyContext): Unit = {
    modifierIssues.foreach(context.log)
    verify(context)
  }

  protected def verify(context: BodyDeclarationVerifyContext): Unit
}

object ClassBodyDeclaration {
  def construct(
    parser: CodeParser,
    thisType: ThisType,
    typeContext: RelativeTypeContext,
    methodOwnerNature: MethodOwnerNature,
    isOuter: Boolean,
    modifiers: ArraySeq[ModifierContext],
    memberDeclarationContext: MemberDeclarationContext
  ): Seq[ClassBodyDeclaration] = {

    val declarations: Seq[ClassBodyDeclaration] =
      CodeParser
        .toScala(memberDeclarationContext.methodDeclaration())
        .map(
          x =>
            Seq(
              ApexMethodDeclaration.construct(
                parser,
                thisType,
                typeContext,
                MethodModifiers
                  .classMethodModifiers(parser, modifiers, x.id(), methodOwnerNature, isOuter),
                x
              )
            )
        )
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.fieldDeclaration())
            .map(
              x =>
                ApexFieldDeclaration.construct(
                  thisType,
                  FieldModifiers.fieldModifiers(
                    parser,
                    modifiers,
                    isOuter,
                    CodeParser.toScala(x.variableDeclarators().variableDeclarator()).head.id()
                  ),
                  x
                )
            )
        )
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.constructorDeclaration())
            .map(
              x =>
                ApexConstructorDeclaration
                  .construct(
                    parser,
                    thisType,
                    typeContext,
                    ApexModifiers.constructorModifiers(parser, modifiers, x),
                    x
                  )
                  .toSeq
            )
        )
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.interfaceDeclaration())
            .map(
              x =>
                Seq(
                  InterfaceDeclaration.constructInner(
                    parser,
                    thisType,
                    ApexModifiers.interfaceModifiers(parser, modifiers, outer = false, x.id()),
                    x
                  )
                )
            )
        )
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.enumDeclaration())
            .map(
              x =>
                Seq(
                  EnumDeclaration.constructInner(
                    parser,
                    thisType,
                    ApexModifiers.enumModifiers(parser, modifiers, outer = false, x.id()),
                    x
                  )
                )
            )
        )
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.propertyDeclaration())
            .map(
              x =>
                Seq(
                  ApexPropertyDeclaration
                    .construct(
                      parser,
                      thisType,
                      FieldModifiers.fieldModifiers(parser, modifiers, isOuter, x.id()),
                      x
                    )
                )
            )
        )
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.classDeclaration())
            .map(
              x =>
                Seq(
                  ClassDeclaration.constructInner(
                    parser,
                    thisType,
                    ApexModifiers.classModifiers(parser, modifiers, outer = false, x.id()),
                    x
                  )
                )
            )
        )
        .getOrElse(Seq())

    declarations.map(_.withContext(memberDeclarationContext))
  }
}

final case class ApexInitializerBlock(_modifiers: ModifierResults, block: Block, _inTest: Boolean)
    extends ClassBodyDeclaration(_modifiers)
    with ApexBlockLike {

  override def idLocation: Location = location.location

  override val isStatic: Boolean            = modifiers.contains(STATIC_MODIFIER)
  override val nature: Nature               = INIT_NATURE
  override val children: ArraySeq[ApexNode] = ArraySeq.empty
  override val name: Name                   = Name.empty
  override val inTest: Boolean              = _inTest

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val blockContext = new OuterBlockVerifyContext(context, isStatic)
    block.verify(blockContext)
    context.typePlugin.onBlockValidated(block, isStatic, blockContext)

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexInitializerBlock {
  def construct(
    parser: CodeParser,
    thisType: ThisType,
    modifiers: ModifierResults,
    block: BlockContext
  ): ApexInitializerBlock = {
    ApexInitializerBlock(modifiers, Block.constructLazy(parser, block), thisType.inTest)
      .withContext(block)
  }
}

class ApexMethodDeclaration(
  thisType: ThisType,
  _modifiers: ModifierResults,
  returnTypeName: RelativeTypeName,
  id: Id,
  override val parameters: ArraySeq[FormalParameter],
  val block: Option[Block]
) extends ClassBodyDeclaration(_modifiers)
    with ApexMethodLike {

  override def idLocation: Location = id.location.location

  override val name: Name                   = id.name
  override val outerTypeId: TypeId          = thisType.typeId
  override val hasBlock: Boolean            = block.nonEmpty
  override lazy val typeName: TypeName      = returnTypeName.typeName
  override val nature: Nature               = METHOD_NATURE
  override val children: ArraySeq[ApexNode] = ArraySeq.empty
  override lazy val signature: String       = super[ApexMethodLike].signature
  override val inTest: Boolean              = thisType.inTest

  // If using a fake block then consider synthetic, we need to use a block to avoid confusion with abstract
  override def isSynthetic: Boolean = block.contains(EagerBlock.empty)

  override def verify(context: BodyDeclarationVerifyContext): Unit = {

    if (returnTypeName.outerNature == CLASS_NATURE) {
      if (isAbstract && block.nonEmpty)
        context.logError(id.location, "Abstract methods can not have an implementation")
      else if (!isAbstract && block.isEmpty)
        context.logError(id.location, "Method must have an implementations or be marked abstract")
      else if (isAbstract && isVirtual)
        context.logError(id.location, "Abstract methods do not need virtual keyword")
    }

    returnTypeName.dependOn(id.location, context)
    parameters.foreach(_.verify(context))

    val blockContext = new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER))
    parameters.foreach(param => param.addVar(blockContext))
    block.foreach(block => {
      block.verify(blockContext)
    })

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexMethodDeclaration {
  def construct(
    parser: CodeParser,
    thisType: ThisType,
    typeContext: RelativeTypeContext,
    modifiers: ModifierResults,
    from: MethodDeclarationContext
  ): ApexMethodDeclaration = {
    val block = CodeParser
      .toScala(from.block())
      .map(b => Block.constructLazy(parser, b))

    new ApexMethodDeclaration(
      thisType,
      modifiers,
      RelativeTypeName(typeContext, TypeReference.construct(from.typeRef())),
      Id.construct(from.id()),
      FormalParameters.construct(parser, typeContext, from.formalParameters()),
      block
    ).withContext(from)
  }

  def construct(
    parser: CodeParser,
    thisType: ThisType,
    typeContext: RelativeTypeContext,
    modifiers: ModifierResults,
    from: InterfaceMethodDeclarationContext
  ): ApexMethodDeclaration = {
    val typeName = CodeParser
      .toScala(from.typeRef())
      .map(tr => TypeReference.construct(tr))
      .getOrElse(TypeNames.Void)
    new ApexMethodDeclaration(
      thisType,
      modifiers,
      RelativeTypeName(typeContext, typeName),
      Id.construct(from.id()),
      FormalParameters.construct(parser, typeContext, from.formalParameters()),
      None
    ).withContext(from)
  }
}

final case class ApexFieldDeclaration(
  thisType: ThisType,
  _modifiers: ModifierResults,
  typeName: TypeName,
  variableDeclarator: VariableDeclarator
) extends ClassBodyDeclaration(_modifiers)
    with ApexFieldLike {

  def id: Id = variableDeclarator.id

  override def idLocation: Location = id.location.location

  override val name: Name = id.name
  private val visibility: Option[Modifier] =
    _modifiers.modifiers.find(ApexModifiers.visibilityModifiers.contains)
  override val readAccess: Modifier         = visibility.getOrElse(PRIVATE_MODIFIER)
  override val writeAccess: Modifier        = readAccess
  override val children: ArraySeq[ApexNode] = ArraySeq.empty
  override val nature: Nature               = FIELD_NATURE
  override val outerTypeId: TypeId          = thisType.typeId
  override val inTest: Boolean              = thisType.inTest

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val staticContext = if (isStatic) Some(true) else None

    variableDeclarator.verify(
      ExprContext(staticContext, context.thisType),
      new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER))
    )
    setDepends(context.dependencies)

    context.propagateDependencies()
  }
}

object ApexFieldDeclaration {
  def construct(
    thisType: ThisType,
    modifiers: ModifierResults,
    fieldDeclaration: FieldDeclarationContext
  ): Seq[ApexFieldDeclaration] = {
    val typeName = TypeReference.construct(fieldDeclaration.typeRef())
    VariableDeclarators
      .construct(typeName, fieldDeclaration.variableDeclarators())
      .declarators
      .map(vd => {
        ApexFieldDeclaration(thisType, modifiers, typeName, vd).withContext(fieldDeclaration)
      })
  }
}

final case class ApexConstructorDeclaration(
  _modifiers: ModifierResults,
  qualifiedName: QualifiedName,
  parameters: ArraySeq[FormalParameter],
  _inTest: Boolean,
  block: Block
) extends ClassBodyDeclaration(_modifiers)
    with ApexConstructorLike {

  override def idLocation: Location = qualifiedName.location.location

  override val name: Name                   = Name(qualifiedName.names.mkString("."))
  override val children: ArraySeq[ApexNode] = ArraySeq.empty
  override val nature: Nature               = CONSTRUCTOR_NATURE
  override val inTest: Boolean              = _inTest

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    parameters.foreach(_.verify(context))

    val blockContext = new OuterBlockVerifyContext(context, isStaticContext = false)
    parameters.foreach(param => blockContext.addVar(param.name, param.id, param.typeName))
    block.verify(blockContext)
    context.typePlugin.onBlockValidated(block, isStatic = false, blockContext)

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexConstructorDeclaration {
  def construct(
    parser: CodeParser,
    thisType: ThisType,
    typeContext: RelativeTypeContext,
    modifiers: ModifierResults,
    from: ConstructorDeclarationContext
  ): Option[ApexConstructorDeclaration] = {

    QualifiedName
      .construct(from.qualifiedName())
      .map(qname => {
        ApexConstructorDeclaration(
          modifiers,
          qname,
          FormalParameters.construct(parser, typeContext, from.formalParameters()),
          thisType.inTest,
          Block.constructLazy(parser, from.block())
        ).withContext(from)
      })
  }
}

final case class FormalParameter(
  modifiers: ModifierResults,
  relativeTypeName: RelativeTypeName,
  id: Id
) extends ParameterDeclaration {

  override val name: Name = id.name

  override def typeName: TypeName = relativeTypeName.typeName

  def addVar(context: BlockVerifyContext): Unit = {
    relativeTypeName.addVar(id, id.name, context)
  }

  def verify(context: BodyDeclarationVerifyContext): Unit = {
    modifiers.issues.foreach(context.log)
    // This is validated when made available to a Block
  }
}

object FormalParameter {
  def construct(
    parser: CodeParser,
    typeContext: RelativeTypeContext,
    items: ArraySeq[FormalParameterContext]
  ): ArraySeq[FormalParameter] = {
    items.map(x => FormalParameter.construct(parser, typeContext, x))
  }

  def construct(
    parser: CodeParser,
    typeContext: RelativeTypeContext,
    from: FormalParameterContext
  ): FormalParameter = {
    FormalParameter(
      ApexModifiers.parameterModifiers(parser, CodeParser.toScala(from.modifier()), from),
      RelativeTypeName(typeContext, TypeReference.construct(from.typeRef)),
      Id.construct(from.id())
    )
  }
}

object FormalParameterList {
  val noParams: ArraySeq[FormalParameter] = ArraySeq()

  def construct(
    parser: CodeParser,
    typeContext: RelativeTypeContext,
    from: FormalParameterListContext
  ): ArraySeq[FormalParameter] = {
    if (from.formalParameter() != null) {
      FormalParameter.construct(parser, typeContext, CodeParser.toScala(from.formalParameter()))
    } else {
      noParams
    }
  }
}

object FormalParameters {
  val empty: ArraySeq[FormalParameter] = ArraySeq()

  def construct(
    parser: CodeParser,
    typeContext: RelativeTypeContext,
    from: FormalParametersContext
  ): ArraySeq[FormalParameter] = {
    Option(from)
      .map(from => {
        CodeParser
          .toScala(from.formalParameterList())
          .map(x => FormalParameterList.construct(parser, typeContext, x))
          .getOrElse(empty)
      })
      .getOrElse(empty)
  }
}
