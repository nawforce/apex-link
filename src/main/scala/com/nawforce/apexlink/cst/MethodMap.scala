/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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

import com.nawforce.apexlink.cst.AssignableSupport.isAssignable
import com.nawforce.apexlink.names.{TypeNames, XNames}
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, ApexMethodLike}
import com.nawforce.apexlink.types.core.{MethodDeclaration, TypeDeclaration}
import com.nawforce.apexlink.types.platform.{GenericPlatformMethod, PlatformMethod}
import com.nawforce.apexlink.types.synthetic.CustomMethodDeclaration
import com.nawforce.pkgforce.diagnostics.Duplicates.IterableOps
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.modifiers.{ABSTRACT_MODIFIER, ISTEST_ANNOTATION, PRIVATE_MODIFIER}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, INTERFACE_NATURE}
import com.nawforce.pkgforce.path.{Location, PathLocation}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

final case class MethodMap(deepHash: Int, methodsByName: Map[(Name, Int), Array[MethodDeclaration]], errors: List[Issue]) {

  /** Return all available methods */
  def allMethods: Array[MethodDeclaration] = {
    val buffer = new mutable.ArrayBuffer[MethodDeclaration]()
    methodsByName.values.foreach(methods => buffer.addAll(methods))
    buffer.toArray
  }

  /** Find a method, without concern for the calling context. */
  def findMethod(name: Name, params: ArraySeq[TypeName]): Option[MethodDeclaration] = {
    methodsByName.getOrElse((name, params.length), Array()).find(method =>
      method.parameters.map(_.typeName) == params)
  }

  /** Find a method, suitable for use from the given context */
  def findMethod(name: Name, params: ArraySeq[TypeName], staticContext: Option[Boolean],
                 context: VerifyContext): Option[MethodDeclaration] = {
    val matches = methodsByName.getOrElse((name, params.length), Array())
    val filteredMatches = staticContext match {
      case None => matches
      case Some(x) => matches.filter(m => m.isStatic == x)
    }

    val exactMatches = filteredMatches.filter(_.hasParameters(params))
    if (exactMatches.nonEmpty)
      return Some(exactMatches.head)

    val erasedMatches = filteredMatches.filter(_.hasCallErasedParameters(context.module, params))
    if (erasedMatches.nonEmpty)
      return Some(erasedMatches.head)

    val assignableMatches = filteredMatches.map(m => {
      val argZip = m.parameters.map(_.typeName).zip(params)
      (argZip.forall(argPair => isAssignable(argPair._1, argPair._2, context)),
        argZip.count(argPair => argPair._1 == argPair._2),
        m)
    }).filter(_._1).map(m => (m._2, m._3))

    if (assignableMatches.nonEmpty) {
      val maxIdentical = assignableMatches.map(_._1).max
      assignableMatches.filter(_._1 == maxIdentical).map(_._2).headOption
    } else {
      None
    }
  }
}

object MethodMap {
  type WorkingMap = mutable.Map[(Name, Int), Array[MethodDeclaration]]

  private val specialOverrideMethodSignatures = Set[String] (
    "system.boolean equals(object)",
    "system.integer hashcode()",
    "system.string tostring(),"
  )

  private val batchOverrideMethodSignature = "database.querylocator start(database.batchablecontext)"

  def empty(): MethodMap = {
    new MethodMap(0, Map(), Nil)
  }

  def apply(td: TypeDeclaration, location: Option[PathLocation], superClassMap: MethodMap,
            newMethods: Array[MethodDeclaration], outerStaticMethods: Array[MethodDeclaration],
            interfaces: ArraySeq[TypeDeclaration]): MethodMap = {

    val workingMap = collection.mutable.Map[(Name, Int), Array[MethodDeclaration]]() ++= superClassMap.methodsByName
    val errors = mutable.Buffer[Issue]()
    val localMethodsByType = newMethods.groupBy(_.isStatic)

    // Add instance methods first with validation checks
    val isTest = td.outermostTypeDeclaration.modifiers.contains(ISTEST_ANNOTATION)
    val isComplete = td.isComplete
    localMethodsByType.get(false).foreach(methods =>
      methods.foreach(method => applyInstanceMethod(workingMap, method, isTest, isComplete, errors)))

    // For interfaces make sure we have all methods
    if (td.nature == INTERFACE_NATURE) {
      mergeInterfaces(workingMap, interfaces)
    }

    // Add outer statics
    outerStaticMethods.foreach(method => applyStaticMethod(workingMap, method))

    // Add local statics, de-duped
    val ignorableStatics = mutable.Set[MethodDeclaration]()
    localMethodsByType.get(true)
      .foreach(methods => methods.toIterable.duplicates(_.nameAndParameterTypes.toLowerCase).foreach(duplicates => {
        duplicates._2.foreach(duplicate => {
          ignorableStatics.add(duplicate)
          setMethodError(duplicate, s"Method '${duplicate.name}' is a duplicate of an existing method in this class", errors)
        })
      }))
    localMethodsByType.get(true).foreach(methods =>
      methods.filterNot(ignorableStatics.contains).foreach(method => applyStaticMethod(workingMap, method)))

    // Validate any interface use in classes
    if (td.nature == CLASS_NATURE && td.moduleDeclaration.nonEmpty) {
      workingMap.put((Names.Clone, 0), Array(CustomMethodDeclaration(Location.empty, Names.Clone, td.typeName, CustomMethodDeclaration.emptyParameters)))
      checkInterfaces(td.moduleDeclaration.get, location, td.isAbstract, workingMap, interfaces, errors)
    }

    // Only Apex class types are replaceable and hence have deep hashes
    td match {
      case td: ApexClassDeclaration => new MethodMap(td.deepHash, workingMap.toMap, errors.toList)
      case _: TypeDeclaration => new MethodMap(0, workingMap.toMap, errors.toList)
    }
  }

  private def mergeInterfaces(workingMap: WorkingMap, interfaces: ArraySeq[TypeDeclaration]): Unit = {
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

  private def checkInterfaces(module: Module, location: Option[PathLocation], isAbstract: Boolean,
                              workingMap: WorkingMap, interfaces: ArraySeq[TypeDeclaration], errors: mutable.Buffer[Issue]): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        checkInterface(module, location, isAbstract, workingMap, i, errors)
      case _ => ()
    })
  }

  private def checkInterface(module: Module, location: Option[PathLocation], isAbstract: Boolean,
                             workingMap: WorkingMap, interface: TypeDeclaration, errors: mutable.Buffer[Issue]): Unit = {
    if (interface.isInstanceOf[ApexClassDeclaration] && interface.nature == INTERFACE_NATURE)
      checkInterfaces(module, location, isAbstract, workingMap, interface.interfaceDeclarations, errors)

    interface.methods
      .filterNot(_.isStatic)
      .foreach(method => {
      val key = (method.name, method.parameters.length)
      val methods = workingMap.getOrElse(key, Array())

      var matched = methods.find(m => m.hasSameParameters(method))
      if (matched.isEmpty)
        matched = methods.find(m => m.hasSameErasedParameters(module, method))

      if (matched.isEmpty) {
        lazy val hasGhostedMethods =
          methods.exists(method => module.isGhostedType(method.typeName) ||
            methods.exists(method => method.parameters.map(_.typeName).exists(module.isGhostedType)))

        if (isAbstract) {
          workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
        } else if (!hasGhostedMethods) {
          location.foreach(l => errors.append(new Issue(l.path, Diagnostic(ERROR_CATEGORY, l.location,
            s"Method '${method.signature}' from interface '${interface.typeName}' must be implemented"))))
        }
      } else {
        matched.get match {
          case am: ApexMethodLike => am.addShadow(method)
          case _ => ()
        }
      }
      })
  }

  private def applyStaticMethod(workingMap: WorkingMap, method: MethodDeclaration): Unit = {
    val key = (method.name, method.parameters.length)
    val methods = workingMap.getOrElse(key, Array())
    val matched = methods.find(m => m.hasSameParameters(method))
    if (matched.isEmpty)
      workingMap.put(key, method +: methods)
    else if (matched.get.isStatic)
      workingMap.put(key, method +: methods.filterNot(_.hasSameSignature(method)))
  }

  private def applyInstanceMethod(workingMap: WorkingMap, method: MethodDeclaration, isTest: Boolean,
                                  isComplete: Boolean, errors: mutable.Buffer[Issue]): Unit = {
    val key = (method.name, method.parameters.length)
    val methods = workingMap.getOrElse(key, Array())

    // Find a match, FUTURE: the use of isTest is over general for allowing private matches but there is a problem with
    // using @TestVisible instead with Cumulus codebase that I don't yet understand.
    val matched = methods.find(_.hasSameParameters(method)) match {
      case Some(am: MethodDeclaration)
        if am.visibility != PRIVATE_MODIFIER || sameFile(method, am) || isTest => Some(am)
      case _ => None
    }

    if (matched.nonEmpty) {
      val matchedMethod = matched.get
      lazy val isSpecial = {
        val matchedSignature = matchedMethod.signature.toLowerCase()
        specialOverrideMethodSignatures.contains(matchedSignature) ||
          (matchedSignature == batchOverrideMethodSignature &&
            method.typeName.outer.contains(TypeNames.System) && method.typeName.name == XNames.Iterable)
      }

      lazy val isPlatformMethod =
        matchedMethod.isInstanceOf[PlatformMethod] || matchedMethod.isInstanceOf[GenericPlatformMethod]

      lazy val isInterfaceMethod =
        !matchedMethod.hasBlock && !matchedMethod.modifiers.contains(ABSTRACT_MODIFIER)

      if (isDuplicate(matchedMethod, method)) {
        setMethodError(method,
          s"Method '${method.name}' is a duplicate of an existing method in this class", errors)
      }
      else if (matchedMethod.typeName != method.typeName && !isSpecial) {
          setMethodError(method,
            s"Method '${method.name}' has wrong return type to override, should be '${matched.get.typeName}'",
            errors, isWarning = true)
      } else if (!matchedMethod.isVirtualOrAbstract) {
        setMethodError(method, s"Method '${method.name}' can not override non-virtual method", errors)
      } else if (!method.isVirtualOrOverride && !isInterfaceMethod && !isSpecial && !isTest && !isPlatformMethod) {
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
      case am: ApexMethodLike if !isWarning => errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case am: ApexMethodLike => errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case _ => ()
    }
  }

  private def sameFile(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.location.path == am2.location.path
      case _ => false
    }
  }

  private def isDuplicate(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.outerTypeId == am2.outerTypeId
      case _ => false
    }
  }

}
