/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.apex.{FullDeclaration, ThisType}
import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.diagnostics.Duplicates.IterableOps
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers._
import com.nawforce.runtime.parsers.CodeParser.TerminalNode
import com.nawforce.runtime.parsers.{CodeParser, Source}

import scala.collection.immutable.ArraySeq

class CompilationUnit(val typeDeclaration: FullDeclaration) extends CST

object CompilationUnit {
  def construct(
    parser: CodeParser,
    module: Module,
    name: Name,
    compilationUnit: CompilationUnitContext
  ): Option[CompilationUnit] = {
    CST.sourceContext.withValue(Some(parser.source)) {
      FullDeclaration
        .construct(parser, module, name, compilationUnit.typeDeclaration())
        .map(fd => new CompilationUnit(fd).withContext(compilationUnit))
    }
  }
}

final case class ClassDeclaration(
  _source: Source,
  _module: Module,
  _typeContext: RelativeTypeContext,
  _typeName: TypeName,
  _outerTypeName: Option[TypeName],
  _id: Id,
  _modifiers: ModifierResults,
  _inTest: Boolean,
  _extendsType: Option[TypeName],
  _implementsTypes: ArraySeq[TypeName],
  _bodyDeclarations: ArraySeq[ClassBodyDeclaration]
) extends FullDeclaration(
      _source,
      _module,
      _typeContext,
      _typeName,
      _outerTypeName,
      _id,
      _modifiers,
      _inTest,
      _extendsType,
      _implementsTypes,
      _bodyDeclarations
    )
    with ApexNode {

  override val nature: Nature = CLASS_NATURE

  override def verify(context: TypeVerifyContext): Unit = {
    verifyCommon(context)
    super.verify(context)
  }

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    verifyCommon(context)
    super.verify(new TypeVerifyContext(Some(context), this, None))
  }

  private def verifyCommon(context: VerifyContext): Unit = {
    localIssues.foreach(context.log)

    // This should likely be handled by method mapping, but constructors are not currently methods
    constructors
      .duplicates(_.parameters.map(_.typeName.toString()).mkString(","))
      .foreach(duplicates => {
        duplicates._2.map(dup => {
          context.logError(
            dup.idPathLocation,
            s"Constructor is a duplicate of an earlier constructor at ${duplicates._1.idLocation.displayPosition}"
          )
        })
      })

    // FUTURE: Eval method map for error handling side-effects
    methods
  }
}

object ClassDeclaration {
  val staticModifier: ArraySeq[Modifier] = ArraySeq(STATIC_MODIFIER)

  def constructInner(
    parser: CodeParser,
    outerType: ThisType,
    modifiers: ModifierResults,
    classDeclaration: ClassDeclarationContext
  ): ClassDeclaration = {
    val thisType = outerType.asInner(CodeParser.getText(classDeclaration.id()))
    construct(parser, thisType, Some(outerType.typeName), modifiers, classDeclaration)
  }

  def construct(
    parser: CodeParser,
    thisType: ThisType,
    outerTypeName: Option[TypeName],
    modifiers: ModifierResults,
    classDeclaration: ClassDeclarationContext
  ): ClassDeclaration = {

    val extendType =
      CodeParser
        .toScala(classDeclaration.typeRef())
        .map(tr => TypeReference.construct(tr))
        .getOrElse(TypeNames.InternalObject)
    val implementsType =
      CodeParser
        .toScala(classDeclaration.typeList())
        .map(tl => TypeList.construct(tl))
        .getOrElse(TypeNames.emptyTypeNames)

    val classBodyDeclarations = CodeParser
      .toScala(classDeclaration.classBody())
      .map(cb => CodeParser.toScala(cb.classBodyDeclaration()))
      .getOrElse(ArraySeq())
    val typeContext = new RelativeTypeContext

    val bodyDeclarations =
      classBodyDeclarations
        .flatMap(
          cbd =>
            CodeParser
              .toScala(cbd.block())
              .map(
                x =>
                  ArraySeq(
                    ApexInitializerBlock.construct(
                      parser,
                      thisType,
                      ModifierResults(getModifiers(CodeParser.toScala(cbd.STATIC())), ArraySeq()),
                      x
                    )
                  )
              )
              .orElse(
                CodeParser
                  .toScala(cbd.memberDeclaration())
                  .map(
                    x =>
                      ClassBodyDeclaration.construct(
                        parser,
                        thisType,
                        typeContext,
                        modifiers.methodOwnerNature,
                        outerTypeName.isEmpty,
                        CodeParser.toScala(cbd.modifier()),
                        x
                      )
                  )
              )
        )
        .flatten

    val td = ClassDeclaration(
      parser.source,
      thisType.module,
      typeContext,
      thisType.typeName,
      outerTypeName,
      Id.construct(classDeclaration.id()),
      modifiers,
      thisType.inTest,
      Some(extendType),
      implementsType,
      bodyDeclarations
    ).withContext(classDeclaration)
    typeContext.freeze(td)
    td
  }

  private def getModifiers(isStatic: Option[TerminalNode]): ArraySeq[Modifier] = {
    isStatic.map(_ => staticModifier).getOrElse(ArraySeq.empty)
  }
}

final case class InterfaceDeclaration(
  _source: Source,
  _module: Module,
  _typeContext: RelativeTypeContext,
  _typeName: TypeName,
  _outerTypeName: Option[TypeName],
  _id: Id,
  _modifiers: ModifierResults,
  _inTest: Boolean,
  _implementsTypes: ArraySeq[TypeName],
  _bodyDeclarations: ArraySeq[ClassBodyDeclaration]
) extends FullDeclaration(
      _source,
      _module,
      _typeContext,
      _typeName,
      _outerTypeName,
      _id,
      _modifiers,
      _inTest,
      None,
      _implementsTypes,
      _bodyDeclarations
    ) {

  override val nature: Nature = INTERFACE_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this, None))
  }
}

object InterfaceDeclaration {
  def constructInner(
    parser: CodeParser,
    outerType: ThisType,
    modifiers: ModifierResults,
    interfaceDeclaration: InterfaceDeclarationContext
  ): InterfaceDeclaration = {
    val thisType = outerType.asInner(CodeParser.getText(interfaceDeclaration.id()))
    construct(parser, thisType, Some(outerType.typeName), modifiers, interfaceDeclaration)
  }

  def construct(
    parser: CodeParser,
    thisType: ThisType,
    outerTypeName: Option[TypeName],
    modifiers: ModifierResults,
    interfaceDeclaration: InterfaceDeclarationContext
  ): InterfaceDeclaration = {

    val implementsType =
      CodeParser
        .toScala(interfaceDeclaration.typeList())
        .map(x => TypeList.construct(x))
        .getOrElse(ArraySeq(TypeNames.InternalInterface))

    val typeContext = new RelativeTypeContext()

    val methods =
      CodeParser
        .toScala(interfaceDeclaration.interfaceBody())
        .map(interfaceBody => CodeParser.toScala(interfaceBody.interfaceMethodDeclaration()))
        .map(methods => {
          methods.map(method => {
            ApexMethodDeclaration.construct(
              parser,
              thisType,
              typeContext,
              MethodModifiers.interfaceMethodModifiers(
                parser,
                CodeParser.toScala(method.modifier()),
                method.id(),
                outerTypeName.isEmpty
              ),
              method
            )
          })
        })
        .getOrElse(ArraySeq[ApexMethodDeclaration]())

    val td = InterfaceDeclaration(
      parser.source,
      thisType.module,
      typeContext,
      thisType.typeName,
      outerTypeName,
      Id.construct(interfaceDeclaration.id()),
      modifiers,
      thisType.inTest,
      implementsType,
      methods
    ).withContext(interfaceDeclaration)
    typeContext.freeze(td)
    td
  }
}

final case class EnumDeclaration(
  _source: Source,
  _module: Module,
  _typeContext: RelativeTypeContext,
  _typeName: TypeName,
  _outerTypeName: Option[TypeName],
  _id: Id,
  _modifiers: ModifierResults,
  _inTest: Boolean,
  _bodyDeclarations: ArraySeq[ClassBodyDeclaration]
) extends FullDeclaration(
      _source,
      _module,
      _typeContext,
      _typeName,
      _outerTypeName,
      _id,
      _modifiers,
      _inTest,
      None,
      ArraySeq(),
      _bodyDeclarations
    ) {

  override val nature: Nature = ENUM_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this, None))
  }

  override lazy val localMethods: ArraySeq[ApexMethodDeclaration] = {
    val location = id.location

    def fakeId(name: String): Id = Id(Name(name)).withLocation(location)

    val thisType        = ThisType(module, typeName, inTest)
    val modifiers       = ModifierResults(ArraySeq(PUBLIC_MODIFIER), ArraySeq())
    val staticModifiers = ModifierResults(ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER), ArraySeq())
    ArraySeq(
      new ApexMethodDeclaration(
        thisType,
        modifiers,
        RelativeTypeName(typeContext, TypeNames.String),
        fakeId("name"),
        FormalParameters.empty,
        Some(EagerBlock.empty)
      ).withLocation(location),
      new ApexMethodDeclaration(
        thisType,
        modifiers,
        RelativeTypeName(typeContext, TypeNames.Integer),
        fakeId("ordinal"),
        FormalParameters.empty,
        Some(EagerBlock.empty)
      ).withLocation(location),
      new ApexMethodDeclaration(
        thisType,
        staticModifiers,
        RelativeTypeName(typeContext, TypeNames.listOf(typeName)),
        fakeId("values"),
        FormalParameters.empty,
        Some(EagerBlock.empty)
      ).withLocation(location),
      new ApexMethodDeclaration(
        thisType,
        staticModifiers,
        RelativeTypeName(typeContext, typeName),
        fakeId("valueOf"),
        ArraySeq(
          FormalParameter(
            ModifierResults.empty,
            RelativeTypeName(typeContext, TypeNames.String),
            fakeId("name")
          )
        ),
        Some(EagerBlock.empty)
      ).withLocation(location),
      new ApexMethodDeclaration(
        thisType,
        modifiers,
        RelativeTypeName(typeContext, TypeNames.Boolean),
        fakeId("equals"),
        ArraySeq(
          FormalParameter(
            ModifierResults.empty,
            RelativeTypeName(typeContext, TypeNames.InternalObject),
            fakeId("other")
          )
        ),
        Some(EagerBlock.empty)
      ).withLocation(location),
      new ApexMethodDeclaration(
        thisType,
        modifiers,
        RelativeTypeName(typeContext, TypeNames.Integer),
        fakeId("hashCode"),
        FormalParameters.empty,
        Some(EagerBlock.empty)
      ).withLocation(location)
    )
  }
}

object EnumDeclaration {

  def constructInner(
    parser: CodeParser,
    outerType: ThisType,
    modifiers: ModifierResults,
    enumDeclaration: EnumDeclarationContext
  ): EnumDeclaration = {
    val thisType = outerType.asInner(CodeParser.getText(enumDeclaration.id()))
    construct(parser, thisType, Some(outerType.typeName), modifiers, enumDeclaration)
  }

  def construct(
    parser: CodeParser,
    thisType: ThisType,
    outerTypeName: Option[TypeName],
    typeModifiers: ModifierResults,
    enumDeclaration: EnumDeclarationContext
  ): EnumDeclaration = {

    val id = Id.construct(enumDeclaration.id())
    val constants = CodeParser
      .toScala(enumDeclaration.enumConstants())
      .map(ec => CodeParser.toScala(ec.id()))
      .getOrElse(ArraySeq())
    val fields = constants.map(constant => {
      ApexFieldDeclaration(
        thisType,
        ModifierResults(ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER), ArraySeq()),
        thisType.typeName,
        VariableDeclarator(thisType.typeName, Id.construct(constant), None).withContext(constant)
      ).withContext(constant)
    })

    val typeContext = new RelativeTypeContext()
    val td = EnumDeclaration(
      parser.source,
      thisType.module,
      typeContext,
      thisType.typeName,
      outerTypeName,
      id,
      typeModifiers,
      thisType.inTest,
      fields
    ).withContext(enumDeclaration)
    typeContext.freeze(td)
    td
  }
}
