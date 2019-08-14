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
package com.nawforce.types

import java.io.InputStream
import java.nio.file.Path

import com.nawforce.documents.DocumentType
import com.nawforce.utils.DotName

final case class CustomObjectDeclaration(_typeName: TypeName)
  extends NamedTypeDeclaration(_typeName) {
}

object CustomObjectDeclaration {
  def create(pkg: PackageDeclaration, path: Path, data: InputStream): Seq[CustomObjectDeclaration] = {
    val name = DotName(DocumentType.apply(path).get.name).demangled
    val ns = if (pkg.namespace.value.isEmpty) None else Some(TypeName(pkg.namespace))
    val typeName =
      if (!name.isCompound)
        TypeName(name.firstName, Nil, ns)
      else
        TypeName(name.names(1), Nil, Some(TypeName(name.firstName)))
    Seq(
      new CustomObjectDeclaration(typeName),
      new CustomObjectDeclaration(typeName.withNameReplace("__c$", "__Share")),
      new CustomObjectDeclaration(typeName.withNameReplace("__c$", "__Feed")),
      new CustomObjectDeclaration(typeName.withNameReplace("__c$", "__History"))
    )
  }
}