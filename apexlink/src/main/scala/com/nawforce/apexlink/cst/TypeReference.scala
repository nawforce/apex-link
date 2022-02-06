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

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.apexparser.ApexParser.{
  TypeArgumentsContext,
  TypeListContext,
  TypeNameContext,
  TypeRefContext
}
import com.nawforce.pkgforce.names.{EncodedName, Name, Names, TypeName}
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.immutable.ArraySeq

object TypeReference {
  def construct(typeRefs: List[TypeRefContext]): List[TypeName] = {
    typeRefs.map(x => TypeReference.construct(x))
  }

  def construct(typeRef: TypeRefContext): TypeName = {
    CodeParser
      .toScala(typeRef)
      .map(typeRef => {
        val arraySubs = CodeParser.getText(typeRef.arraySubscripts()).count(_ == '[')
        val names     = CodeParser.toScala(typeRef.typeName())

        // Only decode head as rest can't legally be in EncodedName format
        createTypeName(decodeName(names.head), names.tail).withArraySubscripts(arraySubs)
      })
      .getOrElse(TypeNames.Void)
  }

  private def getName(name: TypeNameContext): Name = {
    if (CodeParser.toScala(name.LIST()).nonEmpty) Names.ListName
    else if (CodeParser.toScala(name.SET()).nonEmpty) Names.SetName
    else if (CodeParser.toScala(name.MAP()).nonEmpty) Names.MapName
    else Option(name.id).map(id => Names(CodeParser.getText(id))).getOrElse(Names.Empty)
  }

  private def decodeName(name: TypeNameContext): TypeName = {
    val params   = createTypeParams(CodeParser.toScala(name.typeArguments()))
    val typeName = getName(name)
    val encType  = EncodedName(typeName)
    if (encType.ext.nonEmpty)
      TypeName(encType.fullName, params, Some(TypeNames.Schema)).intern
    else
      TypeName(typeName, params, None).intern
  }

  @scala.annotation.tailrec
  private def createTypeName(outer: TypeName, names: Seq[TypeNameContext]): TypeName = {
    names match {
      case Nil => outer
      case hd +: tl =>
        createTypeName(
          TypeName(
            getName(hd),
            createTypeParams(CodeParser.toScala(hd.typeArguments())),
            Some(outer)
          ).intern,
          tl
        )
    }
  }

  private def createTypeParams(typeArguments: Option[TypeArgumentsContext]): Seq[TypeName] = {
    typeArguments
      .map(a => CodeParser.toScala(a.typeList().typeRef()))
      .getOrElse(Seq())
      .map(param => TypeReference.construct(param))
  }
}

object TypeList {
  def construct(typeList: TypeListContext): ArraySeq[TypeName] = {
    val types = CodeParser.toScala(typeList.typeRef())
    types.map(t => TypeReference.construct(t))
  }
}
