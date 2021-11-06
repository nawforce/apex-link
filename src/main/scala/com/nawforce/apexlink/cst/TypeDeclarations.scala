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

import com.nawforce.apexlink.finding.RelativeTypeContext
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.apex.{ApexVisibleMethodLike, FullDeclaration}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.diagnostics.Duplicates.IterableOps
import com.nawforce.pkgforce.diagnostics.Issue
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.parsers._
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.CodeParser.TerminalNode
import com.nawforce.runtime.parsers.{CodeParser, Source}

import scala.collection.immutable.ArraySeq

class CompilationUnit(val typeDeclaration: FullDeclaration, val extendedApex: Boolean) extends CST

object CompilationUnit {
  def construct(parser: CodeParser, module: Module, name: Name, extendedApex: Boolean, compilationUnit: CompilationUnitContext): Option[CompilationUnit] = {
    CST.sourceContext.withValue(Some(parser.source)) {
      FullDeclaration.construct(parser, module, name, extendedApex, compilationUnit.typeDeclaration())
        .map(fd => new CompilationUnit(fd, extendedApex).withContext(compilationUnit))
    }
  }
}

final case class TypeArgumentProxy(_paths: Array[PathLike], _module: Module, _typeName: TypeName)
  extends BasicTypeDeclaration(_paths, _module, _typeName) {
  override lazy val isComplete: Boolean = false
}

final case class ClassDeclaration(_source: Source, _module: Module, _typeContext: RelativeTypeContext, _typeName: TypeName,
                                  _outerTypeName: Option[TypeName], _id: Id, extendedApex: Boolean,
                                  _modifiers: ModifierResults, typeArguments: Seq[Id], _extendsType: Option[TypeName],
                                  _implementsTypes: Array[TypeName], _bodyDeclarations: ArraySeq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _module, _typeContext, _typeName, _outerTypeName, _id, _modifiers, _extendsType, _implementsTypes,
    _bodyDeclarations) with ApexNode {

  override val nature: Nature = CLASS_NATURE

  override lazy val nestedTypes: Array[TypeDeclaration] = {
    typeArguments
      .groupBy(_.name)
      .map(_._2.head)
      .map(typeArg => TypeArgumentProxy(Array(source.path), module, TypeName(typeArg.name, Nil, Some(typeName)))).toArray ++ super.nestedTypes
  }

  override def unused(): ArraySeq[Issue] = {
    if (extendedApex && typeArguments.nonEmpty)
      ArraySeq()
    else
      super.unused()
  }

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

    if (!extendedApex) {
      if (typeArguments.nonEmpty)
        context.logError(typeArguments.head.location, "Class type arguments can only by used by 'Extended' Apex classes")
    } else {
      if (typeArguments.nonEmpty && outerTypeName.nonEmpty)
        context.logError(typeArguments.head.location, "Class type arguments can only by used by outer classes")
    }

    typeArguments
      .groupBy(_.name)
      .foreach {
        case (_, single) if single.length == 1 => ()
        case (_, duplicates) =>
          duplicates.tail.foreach(dup => {
            context.logError(dup.location, s"Duplicate type argument for '${duplicates.head.name.toString()}'")
          })
      }

    // This should likely be handled by method mapping, but constructors are not currently methods
    constructors.duplicates(_.formalParameters.map(_.typeName.toString()).mkString(","))
      .foreach(duplicates => {
        duplicates._2.map(dup => {
          context.logError(dup.idPathLocation,
            s"Constructor is a duplicate of an earlier constructor at ${duplicates._1.idLocation.displayPosition}")
        })
      })

    // FUTURE: Eval method map for error handling side-effects
    methods
  }
}

object ClassDeclaration {
  val staticModifier: ArraySeq[Modifier] = ArraySeq(STATIC_MODIFIER)

  def constructInner(parser: CodeParser, module: Module, outerType: TypeName, extendedApex: Boolean, modifiers: ModifierResults,
                     classDeclaration: ClassDeclarationContext): ClassDeclaration = {
    val thisType = TypeName(Names(CodeParser.getText(classDeclaration.id())), Nil, Some(outerType))
    construct(parser, module, thisType, Some(outerType), extendedApex, modifiers, classDeclaration)
  }

  def construct(parser: CodeParser, module: Module, thisType: TypeName, outerTypeName: Option[TypeName], extendedApex: Boolean,
                modifiers: ModifierResults, classDeclaration: ClassDeclarationContext): ClassDeclaration = {

    val extendType =
      CodeParser.toScala(classDeclaration.typeRef())
        .map(tr => TypeReference.construct(tr))
        .getOrElse(TypeNames.InternalObject)
    val implementsType =
      CodeParser.toScala(classDeclaration.typeList())
        .map(tl => TypeList.construct(tl))
        .getOrElse(TypeName.emptyTypeName)
    val typeArguments: Seq[Id] =
      CodeParser.toScala(classDeclaration.typeParameters())
        .map(args => ArraySeq.unsafeWrapArray(CodeParser.toScala(args.id()).toArray).map(Id.construct))
        .getOrElse(Seq.empty)

    val classBodyDeclarations = CodeParser.toScala(classDeclaration.classBody())
      .map(cb => CodeParser.toScala(cb.classBodyDeclaration()))
      .getOrElse(Seq())
    val typeContext = new RelativeTypeContext

    val bodyDeclarations = ArraySeq.unsafeWrapArray(
      classBodyDeclarations.flatMap(cbd =>
        CodeParser.toScala(cbd.block())
          .map(x => Seq(ApexInitializerBlock.construct(parser,
            ModifierResults(getModifiers(CodeParser.toScala(cbd.STATIC())), ArraySeq()), x)))
          .orElse(CodeParser.toScala(cbd.memberDeclaration())
            .map(x => ClassBodyDeclaration.construct(parser, typeContext, module, modifiers.methodOwnerNature,
              outerTypeName.isEmpty, thisType, extendedApex, ArraySeq.unsafeWrapArray(CodeParser.toScala(cbd.modifier()).toArray), x))
          )
      ).flatten.toArray)

    val td = ClassDeclaration(parser.source, module, typeContext, thisType, outerTypeName,
      Id.construct(classDeclaration.id()), extendedApex, modifiers, typeArguments,
      Some(extendType), implementsType, bodyDeclarations).withContext(classDeclaration)
    typeContext.freeze(td)
    td
  }

  private def getModifiers(isStatic: Option[TerminalNode]): ArraySeq[Modifier] = {
    isStatic.map(_ => staticModifier).getOrElse(ArraySeq.empty)
  }

}

final case class InterfaceDeclaration(_source: Source, _module: Module, _typeContext: RelativeTypeContext, _typeName: TypeName,
                                      _outerTypeName: Option[TypeName], _id: Id, _modifiers: ModifierResults,
                                      _implementsTypes: Array[TypeName], _bodyDeclarations: ArraySeq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _module, _typeContext, _typeName, _outerTypeName, _id, _modifiers, None, _implementsTypes,
    _bodyDeclarations) {

  override val nature: Nature = INTERFACE_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this, None))
  }
}

object InterfaceDeclaration {
  def constructInner(parser: CodeParser, module: Module, outerType: TypeName, modifiers: ModifierResults,
                interfaceDeclaration: InterfaceDeclarationContext): InterfaceDeclaration = {
    val thisType = TypeName(Names(CodeParser.getText(interfaceDeclaration.id())), Nil, Some(outerType))
    construct(parser, module, thisType, Some(outerType), modifiers, interfaceDeclaration)
  }

  def construct(parser: CodeParser, module: Module, thisType: TypeName, outerTypeName: Option[TypeName],
                modifiers: ModifierResults, interfaceDeclaration: InterfaceDeclarationContext)
  : InterfaceDeclaration = {

    val implementsType =
      CodeParser.toScala(interfaceDeclaration.typeList())
        .map(x => TypeList.construct(x))
        .getOrElse(Array(TypeNames.InternalInterface))

    val typeContext = new RelativeTypeContext()

    val methods =
      CodeParser.toScala(interfaceDeclaration.interfaceBody())
        .map(interfaceBody => CodeParser.toScala(interfaceBody.interfaceMethodDeclaration()))
        .map(methods => {
          ArraySeq.unsafeWrapArray(methods.map(method => {
            ApexMethodDeclaration.construct(parser, typeContext, module, TypeId(module, thisType),
              MethodModifiers.interfaceMethodModifiers(parser,
                ArraySeq.unsafeWrapArray(CodeParser.toScala(method.modifier()).toArray), method.id(), outerTypeName.isEmpty),
              method)
          }).toArray)
        }).getOrElse(ArraySeq[ApexMethodDeclaration]())

    val td = InterfaceDeclaration(parser.source, module, typeContext, thisType, outerTypeName,
      Id.construct(interfaceDeclaration.id()), modifiers, implementsType, methods).withContext(interfaceDeclaration)
    typeContext.freeze(td)
    td
  }
}

final case class EnumDeclaration(_source: Source, _module: Module, _typeContext: RelativeTypeContext, _typeName: TypeName,
                                 _outerTypeName: Option[TypeName], _id: Id,
                                 _modifiers: ModifierResults, _bodyDeclarations: ArraySeq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _module, _typeContext, _typeName, _outerTypeName, _id, _modifiers, None, TypeName.emptyTypeName,
    _bodyDeclarations) {

  override val nature: Nature = ENUM_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this, None))
  }

  override lazy val _localMethods: Array[ApexVisibleMethodLike] =
    Array(
      CustomMethodDeclaration(id.location.location, Name("name"), TypeNames.String, CustomMethodDeclaration.emptyParameters),
      CustomMethodDeclaration(id.location.location, Name("ordinal"), TypeNames.Integer, CustomMethodDeclaration.emptyParameters),
      CustomMethodDeclaration(id.location.location, Name("values"), TypeNames.listOf(typeName), CustomMethodDeclaration.emptyParameters, asStatic = true),
      CustomMethodDeclaration(id.location.location, Name("equals"), TypeNames.Boolean, ArraySeq(
        CustomParameterDeclaration(Name("other"), TypeNames.InternalObject))),
      CustomMethodDeclaration(id.location.location, Name("hashCode"), TypeNames.Integer, CustomMethodDeclaration.emptyParameters)
    )
}

object EnumDeclaration {

  def constructInner(parser: CodeParser, module: Module, outerType: TypeName, modifiers: ModifierResults,
                     enumDeclaration: EnumDeclarationContext): EnumDeclaration = {
    val thisType = TypeName(Names(CodeParser.getText(enumDeclaration.id())), Nil, Some(outerType))
    construct(parser, module, thisType, Some(outerType), modifiers, enumDeclaration)
  }

  def construct(parser: CodeParser, module: Module, thisType: TypeName, outerTypeName: Option[TypeName],
                typeModifiers: ModifierResults, enumDeclaration: EnumDeclarationContext): EnumDeclaration = {

    // FUTURE: Add standard enum methods
    val id = Id.construct(enumDeclaration.id())
    val constants = CodeParser.toScala(enumDeclaration.enumConstants())
      .map(ec => CodeParser.toScala(ec.id())).getOrElse(Seq())
    val fields = ArraySeq.unsafeWrapArray(constants.map(constant => {
      ApexFieldDeclaration(TypeId(module, thisType), ModifierResults(ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER), ArraySeq()), thisType,
        VariableDeclarator(
          thisType,
          Id.construct(constant),
          None
        ).withContext(constant)
      ).withContext(constant)
    }).toArray)

    EnumDeclaration(parser.source, module, new RelativeTypeContext() ,thisType, outerTypeName, id, typeModifiers, fields).withContext(enumDeclaration)
  }
}
