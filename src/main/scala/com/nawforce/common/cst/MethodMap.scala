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
package com.nawforce.common.cst

import com.nawforce.common.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue, PathLocation}
import com.nawforce.common.modifiers.{ISTEST_ANNOTATION, PRIVATE_MODIFIER}
import com.nawforce.common.names.{Names, _}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.apex.{ApexClassDeclaration, ApexMethodLike}
import com.nawforce.common.types.core.{CLASS_NATURE, INTERFACE_NATURE, MethodDeclaration, TypeDeclaration}
import com.nawforce.common.types.synthetic.CustomMethodDeclaration

import scala.collection.mutable

final case class MethodMap(methodsByName: Map[(Name, Int), Array[MethodDeclaration]], errors: List[Issue])
  extends AssignableSupport {

  def allMethods: Array[MethodDeclaration] = methodsByName.values.flatten.toArray

  def findMethod(name: Name, params: Array[TypeName], staticContext: Option[Boolean],
                 context: VerifyContext): Array[MethodDeclaration] = {
    val matches = methodsByName.getOrElse((name, params.length),Array())
    val filteredMatches = staticContext match {
      case None => matches
      case Some(x) => matches.filter(m => m.isStatic == x)
    }

    val exactMatches = filteredMatches.filter(_.hasParameters(params))
    if (exactMatches.nonEmpty)
      return Array(exactMatches.head)

    val erasedMatches = filteredMatches.filter(_.hasCallErasedParameters(context.pkg, params))
    if (erasedMatches.nonEmpty)
      return Array(erasedMatches.head)

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
      Array()
    }
  }
}

object MethodMap {
  type WorkingMap = mutable.Map[(Name, Int), Array[MethodDeclaration]]

  private val specialMethodSignature = Set[String] (
    "system.boolean equals(object)",
    "system.integer hashcode()",
    "system.string tostring()")

  def empty(): MethodMap = {
    new MethodMap(Map(), Nil)
  }

  def apply(td: TypeDeclaration, location: Option[PathLocation],
            superClassMap: MethodMap, localMethods: Array[MethodDeclaration], interfaces: Array[TypeDeclaration]): MethodMap = {

    val workingMap = collection.mutable.Map[(Name, Int), Array[MethodDeclaration]]() ++= superClassMap.methodsByName
    val errors = mutable.Buffer[Issue]()

    // Add instance methods first with validation checks
    val isTest = td.outermostTypeDeclaration.modifiers.contains(ISTEST_ANNOTATION)
    val isComplete = td.isComplete
    localMethods.filterNot(_.isStatic)
      .foreach(method => applyInstanceMethod(workingMap, method, isTest, isComplete, errors))

    // For interfaces make sure we have all methods
    if (td.nature == INTERFACE_NATURE) {
      mergeInterfaces(workingMap, interfaces)
    }

    // Add statics is they are not being shadowed by an instance method
    localMethods.filter(_.isStatic).foreach(method => {
      val key = (method.name, method.parameters.length)
      val methods = workingMap.getOrElse(key, Array())
      val matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty)
        workingMap.put(key, method +: methods)
      else if (matched.get.isStatic)
        workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
    })

    // Validate any interface use in classes
    if (td.nature == CLASS_NATURE) {
      workingMap.put((Names.Clone, 0), Array(CustomMethodDeclaration(location, Names.Clone, td.typeName, Array())))
      checkInterfaces(td.packageDeclaration, location, td.isAbstract, workingMap, interfaces, errors)
    }

    new MethodMap(workingMap.toMap, errors.toList)
  }

  private def mergeInterfaces(workingMap: WorkingMap, interfaces: Array[TypeDeclaration]): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        mergeInterface(workingMap, i)
      case _ => ()
    })
  }

  private def mergeInterface(workingMap: WorkingMap, interface: TypeDeclaration): Unit = {
    if (interface.isInstanceOf[ApexClassDeclaration] && interface.nature == INTERFACE_NATURE)
      mergeInterfaces(workingMap, interface.interfaceDeclarations)

    interface.methods.filterNot(_.isStatic).foreach(method => {
      val key = (method.name, method.parameters.length)
      val methods = workingMap.getOrElse(key, Array())

      val matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty) {
        workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
      } else {
        matched.get match {
          case am: ApexMethodLike => am.addShadow(method)
          case _ => ()
        }
      }
    })
  }

  private def checkInterfaces(pkg: Option[PackageImpl], location: Option[PathLocation], isAbstract: Boolean,
                              workingMap: WorkingMap, interfaces: Array[TypeDeclaration], errors: mutable.Buffer[Issue]): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        checkInterface(pkg, location, isAbstract, workingMap, i, errors)
      case _ => ()
    })
  }

  private def checkInterface(pkg: Option[PackageImpl], location: Option[PathLocation], isAbstract: Boolean,
                             workingMap: WorkingMap, interface: TypeDeclaration, errors: mutable.Buffer[Issue]): Unit = {
    if (interface.isInstanceOf[ApexClassDeclaration] && interface.nature == INTERFACE_NATURE)
      checkInterfaces(pkg, location, isAbstract, workingMap, interface.interfaceDeclarations, errors)

    interface.methods
      .filterNot(_.isStatic)
      .foreach(method => {
      val key = (method.name, method.parameters.length)
      val methods = workingMap.getOrElse(key, Array())

      var matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty)
        matched = methods.find(m => m.hasSameErasedParameters(pkg, method))

      if (matched.isEmpty) {
        lazy val hasGhostedMethods =
          methods.exists(method => pkg.exists(_.isGhostedType(method.typeName)) ||
          methods.exists(method => pkg.exists(p => method.parameters.map(_.typeName).exists(p.isGhostedType))))

        if (!isAbstract && !hasGhostedMethods)
          location.foreach(l => errors.append(new Issue(l.path, Diagnostic(ERROR_CATEGORY, l.location,
            s"Method '${method.signature}' from interface '${interface.typeName}' must be implemented"))))
      } else {
        matched.get match {
          case am: ApexMethodLike => am.addShadow(method)
          case _ => ()
        }
      }
    })
  }

  private def applyInstanceMethod(workingMap: WorkingMap, method: MethodDeclaration, isTest: Boolean,
                                  isComplete: Boolean, errors: mutable.Buffer[Issue]): Unit = {
    assert(!method.isStatic)

    val key = (method.name, method.parameters.length)
    val methods = workingMap.getOrElse(key, Array())

    // Find a match, FUTURE: the use isTest is over general for allowing private matches but there is a problem with
    // using @TestVisible instead with Cumulus codebase that I don't yet understand.
    val matched = methods.find(_.hasSameParameters(method)) match {
      case Some(am: MethodDeclaration)
        if am.visibility != PRIVATE_MODIFIER || sameFile(method, am) || isTest => Some(am)
      case _ => None
    }

    if (matched.nonEmpty) {
      val matchedMethod = matched.get
      lazy val isSpecial = specialMethodSignature.contains(matchedMethod.signature.toLowerCase())
      if (matchedMethod.typeName != method.typeName && !isSpecial) {
          setMethodError(method,
            s"Method '${method.name}' has wrong return type to override, should be '${matched.get.typeName}'",
            errors, isWarning = true)
      } else if (!matchedMethod.isVirtualOrAbstract) {
        setMethodError(method, s"Method '${method.name}' can not override non-virtual method", errors)
      } else if (!method.isVirtualOrOverride && !isSpecial && !isTest) {
        setMethodError(method, s"Method '${method.name}' must use override keyword", errors)
      } else if (method.visibility.methodOrder < matchedMethod.visibility.methodOrder && !isSpecial) {
        setMethodError(method, s"Method '${method.name}' can not reduce visibility in override", errors)
      }
    } else if (method.isOverride && isComplete) {
      setMethodError(method, s"Method '${method.name}' does not override a virtual or abstract method", errors)
    }
    method match {
      case am: ApexMethodLike => matched.foreach(am.addShadow)
      case _ => ()
    }

    workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
  }

  private def setMethodError(method: MethodDeclaration, error: String, errors: mutable.Buffer[Issue], isWarning: Boolean=false): Unit = {
    method match {
      case am: ApexMethodLike if !isWarning => errors.append(new Issue(am.nameRange.path, Diagnostic(ERROR_CATEGORY, am.nameRange.location, error)))
      case am: ApexMethodLike => errors.append(new Issue(am.nameRange.path, Diagnostic(ERROR_CATEGORY, am.nameRange.location, error)))
      case _ => ()
    }
  }

  private def sameFile(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.nameRange.path == am2.nameRange.path
      case _ => false
    }
  }
}
