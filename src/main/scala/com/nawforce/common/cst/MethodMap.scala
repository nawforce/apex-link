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

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.diagnostics.{ERROR_CATEGORY, Issue}
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.Names
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.apex.{ApexClassDeclaration, ApexMethodLike}
import com.nawforce.common.types.core.{CLASS_NATURE, INTERFACE_NATURE, MethodDeclaration, TypeDeclaration}
import com.nawforce.common.types.synthetic.CustomMethodDeclaration

import scala.collection.mutable

final case class MethodMap(methodsByName: Map[(Name, Int), Seq[MethodDeclaration]], errors: List[Issue])
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
      return Seq(exactMatches.head)

    val erasedMatches = filteredMatches.filter(_.hasCallErasedParameters(context.pkg, params))
    if (erasedMatches.nonEmpty)
      return Seq(erasedMatches.head)

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

  def empty(): MethodMap = {
    new MethodMap(Map(), Nil)
  }

  def apply(td: TypeDeclaration, location: Option[LocationImpl],
            superClassMap: MethodMap, localMethods: Seq[MethodDeclaration], interfaces: Seq[TypeDeclaration]): MethodMap = {

    val workingMap = collection.mutable.Map[(Name, Int), Seq[MethodDeclaration]]() ++= superClassMap.methodsByName
    val errors = mutable.Buffer[Issue]()

    // Add instance methods first with validation checks
    localMethods.filterNot(_.isStatic).foreach(method => applyInstanceMethod(workingMap, method, errors))

    // For interfaces make sure we have all methods
    if (td.nature == INTERFACE_NATURE) {
      mergeInterfaces(workingMap, interfaces)
    }

    // Add statics is they are not being shadowed by an instance method
    localMethods.filter(_.isStatic).foreach(method => {
      val key = (method.name, method.parameters.size)
      val methods = workingMap.getOrElse(key, Seq())
      val matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty)
        workingMap.put(key, method +: methods)
      else if (matched.get.isStatic)
        workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
    })

    // Validate any interface use in classes
    if (td.nature == CLASS_NATURE) {
      workingMap.put((Names.Clone, 0), Seq(CustomMethodDeclaration(location, Names.Clone, td.typeName, Seq())))
      checkInterfaces(td.packageDeclaration, location, td.isAbstract, workingMap, interfaces, errors)
    }

    new MethodMap(workingMap.toMap, errors.toList)
  }

  private def mergeInterfaces(workingMap: WorkingMap, interfaces: Seq[TypeDeclaration]): Unit = {
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
      val key = (method.name, method.parameters.size)
      val methods = workingMap.getOrElse(key, Seq())

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

  private def checkInterfaces(pkg: Option[PackageImpl], location: Option[LocationImpl], isAbstract: Boolean,
                              workingMap: WorkingMap, interfaces: Seq[TypeDeclaration], errors: mutable.Buffer[Issue]): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        checkInterface(pkg, location, isAbstract, workingMap, i, errors)
      case _ => ()
    })
  }

  private def checkInterface(pkg: Option[PackageImpl], location: Option[LocationImpl], isAbstract: Boolean,
                             workingMap: WorkingMap, interface: TypeDeclaration, errors: mutable.Buffer[Issue]): Unit = {
    if (interface.isInstanceOf[ApexClassDeclaration] && interface.nature == INTERFACE_NATURE)
      checkInterfaces(pkg, location, isAbstract, workingMap, interface.interfaceDeclarations, errors)

    interface.methods
      .filterNot(_.isStatic)
      .foreach(method => {
      val key = (method.name, method.parameters.size)
      val methods = workingMap.getOrElse(key, Seq())

      var matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty)
        matched = methods.find(m => m.hasSameErasedParameters(pkg, method))

      if (matched.isEmpty) {
        lazy val hasGhostedMethods =
          methods.exists(method => pkg.exists(_.isGhostedType(method.typeName)) ||
          methods.exists(method => pkg.exists(p => method.parameters.map(_.typeName).exists(p.isGhostedType))))

        if (!isAbstract && !hasGhostedMethods)
          location.foreach(l => errors.append(new Issue(ERROR_CATEGORY, l,
            s"Method '${method.signature}' from interface '${interface.typeName}' must be implemented")))
      } else {
        matched.get match {
          case am: ApexMethodLike => am.addShadow(method)
          case _ => ()
        }
      }
    })
  }

  private def applyInstanceMethod(workingMap: WorkingMap, method: MethodDeclaration, errors: mutable.Buffer[Issue]): Unit = {
    assert(!method.isStatic)

    val key = (method.name, method.parameters.size)
    val methods = workingMap.getOrElse(key, Seq())

    // Only consider matches against Apex defined methods, overriding platform methods such a hashCode is different
    val matched = methods.find(_.hasSameParameters(method)) match {
      // TOOD: THis should be a ApexMethodLike but we can't currently tell if these are abstract
      case Some(am: ApexMethodDeclaration) => Some(am)
      case _ => None
    }

    if (matched.nonEmpty) {
      val matchedMethod = matched.get
      if (matchedMethod.typeName != method.typeName) {
        setMethodError(method,
          s"Method '${method.name}' has wrong return type to override, should be '${matched.get.typeName}'",
          errors, isWarning = true)
      } else {
        if (matchedMethod.block.nonEmpty) {
          if (!matchedMethod.isVirtualOrOverride) {
            setMethodError(method, s"Method '${method.name}' can not override non-virtual method", errors)
          } else if (!method.isVirtualOrOverride) {
            setMethodError(method, s"Method '${method.name}' must use override or virtual keyword", errors)
          }
        }
      }
    }
    method match {
      case am: ApexMethodLike => matched.foreach(am.addShadow)
      case _ => ()
    }

    workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
  }

  private def setMethodError(method: MethodDeclaration, error: String, errors: mutable.Buffer[Issue], isWarning: Boolean=false): Unit = {
    method match {
      case am: ApexMethodLike if !isWarning => errors.append(new Issue(ERROR_CATEGORY, am.nameRange, error))
      case am: ApexMethodLike => errors.append(new Issue(ERROR_CATEGORY, am.nameRange, error))
      case _ => ()
    }
  }
}
