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
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.extended.GenericTypeDeclaration
import com.nawforce.pkgforce.names.{Name, TypeName}

import scala.collection.mutable

trait GenericTypeFactory {
  this: Module =>

  private val genericTypes = mutable.Map[TypeName, Option[TypeDeclaration]]()

  def getOrCreateExtendedGeneric(typeName: TypeName, declaration: ClassDeclaration): Option[TypeDeclaration] = {
    genericTypes.getOrElseUpdate(typeName, {
      val typeArgs = typeName.name.value.split('_').tail.map(Name(_))
      consumeTypeArgs(typeArgs, declaration).filter(_._1 == typeArgs.length).map(_._2)
    })
  }

  /** Find a declaration from type args returning number consumed. */
  private def consumeTypeArgs(typeArgs: Array[Name], declaration: ClassDeclaration): Option[(Integer, TypeDeclaration)] = {
    val typeArgDeclarations = mutable.ArrayBuffer[TypeDeclaration]()
    var remainingArgs = typeArgs.clone()

    // For each arg we need to consume some typeArgs, question is how many
    for (arg <- declaration.typeArguments) {
      // Consume enough to identify the TypeDeclaration to use
      val used = consumeTypeArgs(remainingArgs)
      if (used.isEmpty)
        return None
      remainingArgs = remainingArgs.takeRight(remainingArgs.length - used.get._1)

      // Handle the TypeDeclaration being itself generic
      used.get._2 match {
        case cd: ClassDeclaration if cd.extendedApex && cd.typeArguments.nonEmpty =>
          val consumed = consumeTypeArgs(remainingArgs, cd)
          if (consumed.isEmpty)
            return None

          remainingArgs = remainingArgs.takeRight(remainingArgs.length - consumed.get._1)
          typeArgDeclarations.append(consumed.get._2)

        case td => ()
          typeArgDeclarations.append(td)
      }
    }

    val typeName = declaration.typeName.withParams(typeArgDeclarations.map(_.typeName).toSeq)
    val td = new GenericTypeDeclaration(this, typeName, declaration)
    Some(typeArgs.length - remainingArgs.length, td)
  }

  /** Find a declaration from type args returning number consumed, maybe > 1 if namespace required. */
  private def consumeTypeArgs(typeArgs: Array[Name]): Option[(Integer, TypeDeclaration)] = {

    if (typeArgs.nonEmpty) {
      val td = TypeResolver(TypeName(typeArgs.head), this).toOption
      if (td.nonEmpty)
        return Some(1, td.get)

      if (typeArgs.length > 1) {
        val tdWithNamespace = findPackageType(TypeName(typeArgs(1), Seq(), Some(TypeName(typeArgs.head))))
        if (tdWithNamespace.nonEmpty)
          return Some(2, tdWithNamespace.get)
      }
    }

    None
  }
}
