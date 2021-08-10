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
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.apex.{ApexBlockLike, ApexConstructorLike, ApexFieldLike, ApexMethodLike}
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.diagnostics.{Issue, PathLocation}
import com.nawforce.pkgforce.modifiers.{MethodModifiers, _}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.apexparser.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.mutable

abstract class ClassBodyDeclaration(modifierResults: ModifierResults) extends CST with DependencyHolder {

  def idLocation: Option[PathLocation]
  val modifiers: Array[Modifier] = modifierResults.modifiers
  def modifierIssues: Array[Issue] = modifierResults.issues
  lazy val isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER) || modifiers.contains(WEBSERVICE_MODIFIER)

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
  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                methodOwnerNature: MethodOwnerNature,
                isOuter: Boolean,
                typeName: TypeName,
                modifiers: Seq[ModifierContext],
                memberDeclarationContext: MemberDeclarationContext): Seq[ClassBodyDeclaration] = {

    val outerTypeId = TypeId(module, typeName)
    val declarations: Option[Seq[ClassBodyDeclaration]] =
      CodeParser
        .toScala(memberDeclarationContext.methodDeclaration())
        .map(
          x =>
            Seq(
              ApexMethodDeclaration.construct(
                parser,
                typeContext,
                module,
                outerTypeId,
                MethodModifiers
                  .classMethodModifiers(parser, modifiers, x.id(), methodOwnerNature, isOuter),
                x)))
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.fieldDeclaration())
            .map(
              x =>
                ApexFieldDeclaration.construct(
                  outerTypeId,
                  FieldModifiers.fieldModifiers(
                    parser,
                    modifiers,
                    isOuter,
                    CodeParser.toScala(x.variableDeclarators().variableDeclarator()).head.id()),
                  x)))
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.constructorDeclaration())
            .map(
              x =>
                Seq(
                  ApexConstructorDeclaration.construct(parser,
                                                       typeContext,
                                                       module,
                                                       typeName,
                                                       ApexModifiers.constructorModifiers(parser, modifiers, x),
                                                       x))))
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.interfaceDeclaration())
            .map(
              x =>
                Seq(
                  InterfaceDeclaration.constructInner(
                    parser,
                    module,
                    typeName,
                    ApexModifiers.interfaceModifiers(parser, modifiers, outer = false, x.id()),
                    x))))
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.enumDeclaration())
            .map(
              x =>
                Seq(
                  EnumDeclaration.constructInner(parser,
                                                 module,
                                                 typeName,
                                                 ApexModifiers.enumModifiers(parser, modifiers, outer = false, x.id()),
                                                 x))))
        .orElse(CodeParser
          .toScala(memberDeclarationContext.propertyDeclaration())
          .map(x =>
            Seq(ApexPropertyDeclaration
              .construct(parser, outerTypeId, FieldModifiers.fieldModifiers(parser, modifiers, isOuter, x.id()), x))))
        .orElse(
          CodeParser
            .toScala(memberDeclarationContext.classDeclaration())
            .map(x =>
              Seq(
                ClassDeclaration.constructInner(parser,
                                                module,
                                                typeName,
                                                ApexModifiers.classModifiers(parser, modifiers, outer = false, x.id()),
                                                x))))

    if (declarations.isEmpty)
      throw new CSTException()
    else
      declarations.get.map(_.withContext(memberDeclarationContext))
  }
}

final case class ApexInitializerBlock(_modifiers: ModifierResults, block: Block)
    extends ClassBodyDeclaration(_modifiers)
    with ApexBlockLike {

  override val idLocation: Option[PathLocation] = None
  override val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    context.withOuterBlockVerifyContext(isStatic) { blockContext =>
      block.verify(blockContext)
    }
    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexInitializerBlock {
  def construct(parser: CodeParser, modifiers: ModifierResults, block: BlockContext): ApexInitializerBlock = {
    ApexInitializerBlock(modifiers, Block.constructLazy(parser, block))
  }
}

final class ApexMethodDeclaration(override val outerTypeId: TypeId,
                                  _modifiers: ModifierResults,
                                  returnTypeName: RelativeTypeName,
                                  id: Id,
                                  override val parameters: Array[ParameterDeclaration],
                                  val block: Option[Block])
    extends ClassBodyDeclaration(_modifiers)
    with ApexMethodLike {

  override def idLocation: Option[PathLocation] = Some(id.location)
  override def nameLocation: PathLocation = id.location
  override def fullLocation: PathLocation = location
  override val name: Name = id.name
  override def hasBlock: Boolean = block.nonEmpty

  override lazy val typeName: TypeName = returnTypeName.typeName

  /* All parameters are FormalParameters but we need to bypass Array being invariant */
  def formalParameters: Array[FormalParameter] = parameters.collect { case p: FormalParameter => p }

  override def verify(context: BodyDeclarationVerifyContext): Unit = {

    if (returnTypeName.outerNature == CLASS_NATURE) {
      if (isAbstract && block.nonEmpty)
        context.logError(id.location, "Abstract methods can not have an implementation")
      else if (!isAbstract && block.isEmpty)
        context.logError(id.location, "Method must have an implementations or be marked abstract")
      else if (isAbstract && isVirtual)
        context.logError(id.location, "Abstract methods do not need virtual keyword")
    } else if (returnTypeName.outerNature == INTERFACE_NATURE) {
      if (modifiers.nonEmpty)
        context.logError(id.location, s"Modifier '${modifiers.head.name}' is not supported on interface methods")
    }

    returnTypeName.dependOn(id.location, context)
    formalParameters.foreach(_.verify(context))

    context.withOuterBlockVerifyContext(modifiers.contains(STATIC_MODIFIER), noUnused = true) { blockContext =>
      formalParameters.foreach(param => param.addVar(blockContext))
      block.foreach(_.verify(blockContext))
    }

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexMethodDeclaration {
  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeId: TypeId,
                modifiers: ModifierResults,
                from: MethodDeclarationContext): ApexMethodDeclaration = {
    val typeName = CodeParser
      .toScala(from.typeRef())
      .map(tr => TypeReference.construct(tr))
      .getOrElse(TypeNames.Void)
    val block = CodeParser
      .toScala(from.block())
      .map(b => Block.constructLazy(parser, b))

    new ApexMethodDeclaration(outerTypeId,
                              modifiers,
                              RelativeTypeName(typeContext, typeName),
                              Id.construct(from.id()),
                              FormalParameters.construct(parser,
                                                         typeContext,
                                                         module,
                                                         outerTypeId.typeName,
                                                         from.formalParameters()),
                              block).withContext(from)
  }

  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeId: TypeId,
                modifiers: ModifierResults,
                from: InterfaceMethodDeclarationContext): ApexMethodDeclaration = {
    val typeName = CodeParser
      .toScala(from.typeRef())
      .map(tr => TypeReference.construct(tr))
      .getOrElse(TypeNames.Void)
    new ApexMethodDeclaration(outerTypeId,
                              modifiers,
                              RelativeTypeName(typeContext, typeName),
                              Id.construct(from.id()),
                              FormalParameters.construct(parser,
                                                         typeContext,
                                                         module,
                                                         outerTypeId.typeName,
                                                         from.formalParameters()),
                              None).withContext(from)
  }
}

final case class ApexFieldDeclaration(outerTypeId: TypeId,
                                      _modifiers: ModifierResults,
                                      typeName: TypeName,
                                      variableDeclarator: VariableDeclarator)
    extends ClassBodyDeclaration(_modifiers)
    with ApexFieldLike {

  val id: Id = variableDeclarator.id
  override val idLocation: Option[PathLocation] = Some(id.location)
  override val nameLocation: PathLocation = id.location
  override def fullLocation: PathLocation = location
  override val name: Name = id.name
  private val visibility: Option[Modifier] =
    _modifiers.modifiers.find(m => ApexModifiers.visibilityModifiers.contains(m))
  override val readAccess: Modifier = visibility.getOrElse(PRIVATE_MODIFIER)
  override val writeAccess: Modifier = readAccess

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val staticContext = if (isStatic) Some(true) else None

    variableDeclarator.verify(ExprContext(staticContext, context.thisType),
      new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER)))
    setDepends(context.dependencies)

    context.propagateDependencies()
  }
}

object ApexFieldDeclaration {
  def construct(outerTypeId: TypeId,
                modifiers: ModifierResults,
                fieldDeclaration: FieldDeclarationContext): Seq[ApexFieldDeclaration] = {
    val typeName = TypeReference.construct(fieldDeclaration.typeRef())
    VariableDeclarators
      .construct(typeName, fieldDeclaration.variableDeclarators())
      .declarators
      .map(vd => {
        ApexFieldDeclaration(outerTypeId, modifiers, typeName, vd).withContext(fieldDeclaration)
      })
  }
}

final case class ApexConstructorDeclaration(_modifiers: ModifierResults,
                                            qualifiedName: QualifiedName,
                                            parameters: Array[ParameterDeclaration],
                                            block: Block)
    extends ClassBodyDeclaration(_modifiers)
    with ApexConstructorLike {

  override val idLocation: Option[PathLocation] = Some(qualifiedName.location)
  override val nameLocation: PathLocation = qualifiedName.location
  override def fullLocation: PathLocation = location

  /* All parameters are FormalParameters but we need to bypass Array being invariant */
  def formalParameters: Array[FormalParameter] = parameters.collect { case p: FormalParameter => p }

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    formalParameters.foreach(_.verify(context))

    context.withOuterBlockVerifyContext(isStatic = false, noUnused = true) { blockContext =>
      formalParameters.foreach(param => blockContext.addVar(param.name, param.id.location, param.typeName))
      block.verify(blockContext)
    }

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexConstructorDeclaration {
  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeName: TypeName,
                modifiers: ModifierResults,
                from: ConstructorDeclarationContext): ApexConstructorDeclaration = {
    ApexConstructorDeclaration(modifiers,
                               QualifiedName.construct(from.qualifiedName()),
                               FormalParameters.construct(parser,
                                                          typeContext,
                                                          module,
                                                          outerTypeName,
                                                          from.formalParameters()),
                               Block.constructLazy(parser, from.block())).withContext(from)
  }
}

final case class FormalParameter(module: Module,
                                 outerTypeName: TypeName,
                                 modifiers: ModifierResults,
                                 relativeTypeName: RelativeTypeName,
                                 id: Id)
    extends ParameterDeclaration {

  override val name: Name = id.name

  override def typeName: TypeName = relativeTypeName.typeName

  def addVar(context: BlockVerifyContext): Unit = {
    relativeTypeName.addVar(id.location, id.name, context)
  }

  def verify(context: BodyDeclarationVerifyContext): Unit = {
    modifiers.issues.foreach(context.log)
    // This is validated when made available to a Block
  }
}

object FormalParameter {
  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeName: TypeName,
                items: Array[FormalParameterContext]): Array[ParameterDeclaration] = {
    items.map(x => FormalParameter.construct(parser, typeContext, module, outerTypeName, x))
  }

  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeName: TypeName,
                from: FormalParameterContext): FormalParameter = {
    FormalParameter(module,
                    outerTypeName,
                    ApexModifiers.parameterModifiers(parser, CodeParser.toScala(from.modifier()), from),
                    RelativeTypeName(typeContext, TypeReference.construct(from.typeRef())),
                    Id.construct(from.id()))
  }
}

object FormalParameterList {
  val noParams: Array[ParameterDeclaration] = Array()

  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeName: TypeName,
                from: FormalParameterListContext): Array[ParameterDeclaration] = {
    if (from.formalParameter() != null) {
      FormalParameter.construct(parser,
                                typeContext,
                                module,
                                outerTypeName,
                                CodeParser.toScala(from.formalParameter()).toArray)
    } else {
      noParams
    }
  }
}

object FormalParameters {
  val noParams: Array[ParameterDeclaration] = Array()

  def construct(parser: CodeParser,
                typeContext: RelativeTypeContext,
                module: Module,
                outerTypeName: TypeName,
                from: FormalParametersContext): Array[ParameterDeclaration] = {
    CodeParser
      .toScala(from.formalParameterList())
      .map(x => FormalParameterList.construct(parser, typeContext, module, outerTypeName, x))
      .getOrElse(noParams)
  }
}
