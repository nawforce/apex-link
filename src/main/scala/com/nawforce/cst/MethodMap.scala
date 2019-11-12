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
import com.nawforce.names.{Name, TypeName}
import com.nawforce.types.{CLASS_NATURE, CustomMethodDeclaration, INTERFACE_NATURE, MethodDeclaration, Nature, TypeDeclaration}

import scala.collection.mutable

final case class MethodMap(methodsByName: Map[(Name, Int), Seq[MethodDeclaration]], errors: Map[Location, String])
  extends AssignableSupport {

  lazy val externalMethods: Iterable[MethodDeclaration] = methodsByName.values.flatMap(_.filter(_.isGlobalOrPublic))
  val allMethods: Iterable[MethodDeclaration] = methodsByName.values.flatten

  def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                 context: VerifyContext): Seq[MethodDeclaration] = {
    val matches = methodsByName.getOrElse((name, params.size),Seq())
    val filteredMatches = staticContext match {
      case None => matches
      case Some(x) => matches.filter(m => m.isStatic == x)
    }
    val exactMatches = filteredMatches.filter(_.hasParameters(params))
    if (exactMatches.nonEmpty)
      Seq(exactMatches.head)

    val assignableMatches = filteredMatches.map(m => {
      val argZip = m.parameters.map(_.typeName).zip(params)
      (argZip.forall(argPair => isAssignable(argPair._1, argPair._2, context)),
        argZip.count(argPair => argPair._1 == argPair._2),
        m)
    }).filter(_._1).map(m => (m._2, m._3))

    if (assignableMatches.nonEmpty) {
      val maxIdentical = assignableMatches.map(_._1).max
      assignableMatches.filter(_._1 == maxIdentical).map(_._2)
    } else {
      Seq()
    }
  }
}

object MethodMap {
  type WorkingMap = mutable.Map[(Name, Int), Seq[MethodDeclaration]]
  type ErrorMap = mutable.Map[Location, String]

  def empty(): MethodMap = {
    new MethodMap(Map(), Map())
  }

  def apply(location: Option[Location], nature: Nature, typeName: TypeName, superClassMap: MethodMap,
            localMethods: Seq[MethodDeclaration], interfaces: Seq[TypeDeclaration]): MethodMap = {

    val workingMap = collection.mutable.Map[(Name, Int), Seq[MethodDeclaration]]() ++= superClassMap.methodsByName
    val errors = mutable.Map[Location, String]()

    localMethods.foreach(method => applyMethod(workingMap, method, errors))
    if (nature == CLASS_NATURE) {
      workingMap.put((Name.Clone, 0), Seq(CustomMethodDeclaration(Name.Clone, typeName, Seq())))
      checkInterfaces(location, workingMap, interfaces, errors)
    } else if (nature == INTERFACE_NATURE) {
      mergeInterfaces(workingMap, interfaces)
    }

    new MethodMap(workingMap.toMap, errors.toMap)
  }

  private def mergeInterfaces(workingMap: WorkingMap, interfaces: Seq[TypeDeclaration]): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        mergeInterface(workingMap, i)
      case _ => ()
    })
  }

  private def mergeInterface(workingMap: WorkingMap, interface: TypeDeclaration): Unit = {
    if (interface.isInstanceOf[InterfaceDeclaration])
      mergeInterfaces(workingMap, interface.interfaceDeclarations)

    interface.methods.filterNot(_.isStatic).foreach(method => {
      val key = (method.name, method.parameters.size)
      val methods = workingMap.getOrElse(key, Seq())

      val matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty) {
        workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
      } else {
        matched.get match {
          case am: ApexMethodDeclaration => am.shadows(method)
          case _ => ()
        }
      }
    })
  }

  private def checkInterfaces(location: Option[Location], workingMap: WorkingMap, interfaces: Seq[TypeDeclaration], errors: ErrorMap): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        checkInterface(location, workingMap, i, errors)
      case _ => ()
    })
  }

  private def checkInterface(location: Option[Location], workingMap: WorkingMap, interface: TypeDeclaration, errors: ErrorMap): Unit = {
    if (interface.isInstanceOf[InterfaceDeclaration])
      checkInterfaces(location, workingMap, interface.interfaceDeclarations, errors)

    interface.methods.filterNot(_.isStatic).foreach(method => {
      val key = (method.name, method.parameters.size)
      val methods = workingMap.getOrElse(key, Seq())

      var matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty && interface.typeName.isBatchable)
        matched = methods.find(isBatchableExecute)

      if (matched.isEmpty) {
        location.foreach(errors.put(_, s"Method '${method.signature}' from interface '${interface.typeName}' must be implemented"))
      } else {
        matched.get match {
          case am: ApexMethodDeclaration => am.shadows.add(method)
          case _ => ()
        }
      }
    })
  }

  private def isBatchableExecute(method: MethodDeclaration): Boolean = {
    method.name == Name.Execute &&
      method.typeName == TypeName.Void &&
      method.parameters.size == 2 &&
      method.parameters(0).typeName == TypeName.BatchableContext &&
      method.parameters(1).typeName.isList
  }

  private def applyMethod(workingMap: WorkingMap, method: MethodDeclaration, errors: ErrorMap): Unit = {
    val key = (method.name, method.parameters.size)
    val methods = workingMap.getOrElse(key, Seq())

    if (!method.isStatic) {
      val matched = methods.find(_.hasSameParameters(method))

      if (matched.nonEmpty && (matched.get.typeName != method.typeName)) {
        setMethodError(method, s"Method '${method.name}' has wrong return type to override, should be '${matched.get.typeName}'", errors)
      } else if (matched.nonEmpty && matched.get.isInstanceOf[ApexMethodDeclaration]) {
        if (!matched.get.isAbstract) {
          if (!matched.get.isVirtualOrOverride) {
            setMethodError(method, s"Method '${method.name}' can not override non-virtual method", errors)
          } else if (!method.isVirtualOrOverride) {
            setMethodError(method, s"Method '${method.name}' must use override or virtual keyword", errors)
          }
        }
      }
      method match {
        case am: ApexMethodDeclaration => matched.foreach(am.shadows.add)
        case _ => ()
      }
    }

    workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
  }

  private def setMethodError(method: MethodDeclaration, error: String, errors: ErrorMap): Unit = {
    method match {
      case am: ApexMethodDeclaration => errors.put(am.id.location, error)
      case _ => ()
    }
  }
}
