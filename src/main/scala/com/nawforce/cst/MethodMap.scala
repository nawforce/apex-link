/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.cst

import com.nawforce.documents.Location
import com.nawforce.names.Name
import com.nawforce.types.MethodDeclaration

import scala.collection.mutable

final case class MethodMap(methodsByName: Map[(Name, Int), Seq[MethodDeclaration]], errors: Map[Location, String]) {

  lazy val externalMethods: Iterable[MethodDeclaration] = methodsByName.values.flatMap(_.filter(_.isGlobalOrPublic))
  val allMethods: Iterable[MethodDeclaration] = methodsByName.values.flatten

  def findMethod(name: Name, paramCount: Int, staticContext: Option[Boolean]): Option[MethodDeclaration] = {
    val matches = methodsByName.getOrElse((name, paramCount),Seq())
    staticContext match {
      case None => matches.headOption
      case Some(x) => matches.find(m => m.isStatic == x)
    }
  }
}

object MethodMap {
  type WorkingMap = mutable.Map[(Name, Int), Seq[MethodDeclaration]]
  type ErrorMap = mutable.Map[Location, String]

  def empty(): MethodMap = {
    new MethodMap(Map(), Map())
  }

  def apply(superClassMap: MethodMap, localMethods: Seq[MethodDeclaration],
            interfaces: Seq[InterfaceDeclaration]): MethodMap = {

    val workingMap = collection.mutable.Map[(Name, Int), Seq[MethodDeclaration]]() ++= superClassMap.methodsByName
    val errors = mutable.Map[Location, String]()

    interfaces.foreach(interface => applyInterface(workingMap, interface, errors))
    localMethods.foreach(method => applyMethod(workingMap, method, errors))

    new MethodMap(workingMap.toMap, errors.toMap)
  }

  private def applyInterface(workingMap: WorkingMap, interface: InterfaceDeclaration, errors: ErrorMap): Unit = {
    interface.methods.foreach(method => {
      if (findMatches(workingMap, method).isEmpty) {
        addMethod(workingMap, method)
      }
    })
  }

  private def applyMethod(workingMap: WorkingMap, method: MethodDeclaration, errors: ErrorMap): Unit = {
    // TODO: Validate upsert OK
    upsertMethod(workingMap, method)
  }

  private def findMatches(workingMap: WorkingMap, method: MethodDeclaration): Seq[MethodDeclaration] = {
    workingMap.getOrElse((method.name, method.parameters.size), Seq())
      .filter(m => {
          // TODO: This is an exact match, should be assignable match
          m.signature == method.signature
      })
  }

  private def addMethod(workingMap: WorkingMap, method: MethodDeclaration): Unit = {
    val key = (method.name, method.parameters.size)
    val methods = workingMap.getOrElse(key, Seq())
    workingMap.put(key, method +: methods)
  }

  private def upsertMethod(workingMap: WorkingMap, method: MethodDeclaration): Unit = {
    val key = (method.name, method.parameters.size)
    val methods = workingMap.getOrElse(key, Seq())
    // TODO: This is an exact match, should be assignable match
    workingMap.put(key, method +: methods.filterNot(_.signature == method.signature))
  }
}
