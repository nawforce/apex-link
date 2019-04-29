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
package com.nawforce.api


import java.util.concurrent.ConcurrentHashMap

import com.nawforce.types.{TypeDeclaration, TypeName, TypeStore}
import com.nawforce.utils.DotName
import com.typesafe.scalalogging.LazyLogging

class Org extends TypeStore with LazyLogging {
  private var packages: List[Package] = Nil
  private val types = new ConcurrentHashMap[DotName, TypeDeclaration]()

  def addPackage(directories: Array[String]): Package = {
    packages = Package(this, directories) :: packages
    packages.head
  }

  override def getType(typeName: TypeName): Option[TypeDeclaration] = {
    val dotName = typeName.asDotName
    val declaration = getType(dotName)
    if (declaration.isEmpty)
        super.getType(typeName)
    else
      declaration
  }

  private def getType(name: DotName): Option[TypeDeclaration] = {
    val declaration = types.get(name)
    if (declaration==null && name.isCompound)
      getType(name.headNames).flatMap(_.nestedTypes.find(td => td.name == name.lastName))
    else
      Option(declaration)
  }

  def replaceType(typeDeclaration: TypeDeclaration): Unit = {
    types.put(typeDeclaration.typeName.asDotName, typeDeclaration)
  }
}
