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
package com.nawforce.names

/**
  * A name with optional namespace prefixing & type suffix such as foo__field__c
  */
case class EncodedName(name: Name, ext: Option[Name], namespace: Option[Name]) {
  lazy val localName: Name = Name(name.value + ext.map("__"+ _.value).getOrElse(""))
  lazy val fullName: Name = Name(namespace.map(_.value+"__").getOrElse("") + localName.value)

  def defaultNamespace(ns: Option[Name]): EncodedName = {
    if (ext.nonEmpty && namespace.isEmpty)
      EncodedName(name, ext, ns)
    else
      this
  }

  def asTypeName: TypeName = {
    TypeName(localName, Nil, namespace.map(TypeName(_)))
  }
}

object EncodedName {
  def apply(name: Name): EncodedName = {
    apply(name.value)
  }

  def apply(name: String): EncodedName = {
    val parts = threeSplit(name)
    parts.size match {
      case 3 if parts.head.isEmpty => EncodedName(Name("__"+parts(1)), Some(Name(parts(2))), None)
      case 3 => EncodedName(Name(parts(1)), Some(Name(parts(2))), Some(Name(parts.head)))
      case 2 => EncodedName(Name(parts.head), Some(Name(parts(1))), None)
      case 1 => EncodedName(Name(parts.head), None, None)
    }
  }

  private def threeSplit(name: String): Seq[String] = {
    var parts = name.split("__")

    // Fold subfields to avoid field name looking like a namespace
    if (parts.length > 1 && parts.last == "s")
      parts = parts.take(parts.length-2) :+ parts.takeRight(2).mkString("__")

    if (parts.length > 2)
      Seq(parts.head, parts.tail.take(parts.length-2).mkString("__"), parts.last)
    else if (parts.length == 2)
      Seq(parts.head, parts.last)
    else
      Seq(name)
  }
}
