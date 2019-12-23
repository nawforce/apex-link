/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.common.cst

import com.nawforce.common.names.{EncodedName, Name, TypeName}
import com.nawforce.common.parsers.ApexParser.{TypeArgumentsContext, TypeListContext, TypeNameContext, TypeRefContext}

import scala.collection.JavaConverters._

object TypeRef {
  def construct(aList: List[TypeRefContext]): List[TypeName] = {
    aList.map(x => TypeRef.construct(x))
  }

  def construct(typeRef: TypeRefContext): TypeName = {
    val arraySubs = typeRef.arraySubscripts().getText.count(_ == '[')
    val names = typeRef.typeName().asScala

    // Only decode head as rest can't legally be in EncodedName format
    createTypeName(
      decodeName(names.head), names.tail).withArraySubscripts(arraySubs)
  }

  private def decodeName(name: TypeNameContext): TypeName = {
    val params = createTypeParams(name.typeArguments())
    val encType = EncodedName(Name(name.id().getText))
    if (encType.ext.nonEmpty)
      TypeName(encType.fullName, params, Some(TypeName.Schema))
    else
      TypeName(Name(name.id().getText), params, None)
  }

  @scala.annotation.tailrec
  private def createTypeName(outer: TypeName, names: Seq[TypeNameContext])
  : TypeName = {
    names match {
      case Nil => outer
      case hd +: tl =>
        createTypeName(
          TypeName(Name(hd.id().getText), createTypeParams(hd.typeArguments()), Some(outer)),
          tl
        )
    }
  }

  private def createTypeParams(typeArguments: TypeArgumentsContext): Seq[TypeName] = {
    Option(typeArguments)
      .map(_.typeList().typeRef().asScala.toSeq)
      .getOrElse(Seq())
      .map(param => TypeRef.construct(param))
  }
}

object TypeList {
  def construct(typeList: TypeListContext): Seq[TypeName] = {
    val types: Seq[TypeRefContext] = typeList.typeRef().asScala
    types.toList.map(t => TypeRef.construct(t))
  }
}

