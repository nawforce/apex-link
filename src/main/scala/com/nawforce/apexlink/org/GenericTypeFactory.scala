/*
 Copyright (c) 2021 Kevin Jones & FinancialForce, All rights reserved.
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
package com.nawforce.apexlink.org

import com.nawforce.apexlink.cst.ClassDeclaration
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.apexlink.names.{TypeNames, XNames}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.extended.GenericTypeDeclaration
import com.nawforce.pkgforce.names.{Name, Names, TypeName}

import scala.collection.mutable

trait GenericTypeFactory {
  this: Module =>

  private val genericTypes = mutable.Map[TypeName, Option[TypeDeclaration]]()

  def getOrCreateExtendedGeneric(typeName: TypeName, genericBase: ClassDeclaration): Option[TypeDeclaration] = {
    genericTypes.getOrElseUpdate(typeName, {
      val typeArgs = typeName.name.value.split('_').map(Name(_))
      constructTypeName(typeArgs).flatMap(decoded => {
        // We must consume all args and end up with same as the number of args as the base class
        if (decoded._1.isEmpty && decoded._2.params.length == genericBase.typeArguments.length) {
          Some(new GenericTypeDeclaration(this, decoded._2, genericBase))
        } else {
          None
        }
      })
    })
  }

  /** Construct a TypeName from some args using a rather specific model for resolving. */
  private def constructTypeName(typeArgs: Array[Name]): Option[(Array[Name], TypeName)] = {
    if (typeArgs.nonEmpty) {
      return constructNoneGeneric(typeArgs)
        .orElse(constructExtendedGeneric(typeArgs))
        .orElse(constructPlatformGeneric(typeArgs))
    }
    None
  }

  private def constructNoneGeneric(typeArgs: Array[Name]): Option[(Array[Name], TypeName)] = {
    val typeName = constructTypeName(typeArgs.head)
    if (typeName.outer.isEmpty && namespace.nonEmpty) {
      val td = TypeResolver(typeName.withNamespace(namespace), this).toOption
      if (td.nonEmpty && asCompanion(td.get).isEmpty)
        return Some((typeArgs.tail, td.get.typeName))
    }

    val td = TypeResolver(typeName, this).toOption
    if (td.nonEmpty && asCompanion(td.get).isEmpty)
      return Some((typeArgs.tail, td.get.typeName))

    None
  }

  private def constructExtendedGeneric(typeArgs: Array[Name]): Option[(Array[Name], TypeName)] = {
    var typeName = constructTypeName(typeArgs.head)
    if (typeName.outer.isEmpty) {
      // Add package namespace if not explicit
      typeName = typeName.withNamespace(namespace)
    }

    val td = findPackageType(typeName)
    val companion = td.flatMap(asCompanion)
    if (td.nonEmpty && companion.nonEmpty) {
      val requiredArgs = companion.get.typeArguments.length
      val params = constructTypeNames(typeArgs.tail, requiredArgs)
      if (params._2.length == requiredArgs)
        return Some((params._1, td.get.typeName.withParams(params._2.reverse)))
    }
    None
  }

  private def constructPlatformGeneric(typeArgs: Array[Name]): Option[(Array[Name], TypeName)] = {

    var typeName = constructTypeName(typeArgs.head)
    if (typeName.outer.isEmpty) {
      // Non-namespace can only be for a System generic
      typeName = typeName.withOuter(Some(TypeNames.System))
    }

    GenericTypeFactory.platformGenerics.get(typeName.asDotName.names).flatMap(requiredArgs => {
      val params = constructTypeNames(typeArgs.tail, requiredArgs)
      if (params._2.length == requiredArgs)
        Some((params._1, typeName.withParams(params._2.reverse)))
      else
        None
    })
  }

  /** Construct a set number of TypeNames form some args, may not be possible, returns any unused type args. */
  private def constructTypeNames(typeArgs: Array[Name], count: Int): (Array[Name], List[TypeName]) = {
    if (count == 0) {
      (typeArgs, List())
    } else {
      constructTypeName(typeArgs) match {
        case Some((residual, typeName)) =>
          val rest = constructTypeNames(residual, count - 1)
          (rest._1, typeName :: rest._2)
        case None =>
          (typeArgs, List())
      }
    }
  }

  private def constructTypeName(typeArg: Name): TypeName = {
    namespaces.find(ns => typeArg.value.matches(s"(?i)${ns.value}.+"))
      .map(ns => TypeName(Name(typeArg.value.substring(ns.value.length)), Seq(), Some(TypeName(ns))))
      .getOrElse(TypeName(typeArg))
  }

  /** Are we dealing with an Extended Apex companion type declaration. */
  private def asCompanion(td: TypeDeclaration): Option[ClassDeclaration] = {
    td match {
      case cd: ClassDeclaration if cd.typeArguments.nonEmpty => Some(cd)
      case _ => None
    }
  }
}

object GenericTypeFactory {
  val platformGenerics = Map(
    Seq(Names.System, XNames.List$) -> 1,
    Seq(Names.System, XNames.Iterator) -> 1,
    Seq(Names.System, XNames.Map$) -> 2,
    Seq(Names.System, XNames.Set$) -> 1,
    Seq(Names.System, XNames.Iterable) -> 1,
    Seq(Names.Database, XNames.Batchable) -> 1,
  )
}
