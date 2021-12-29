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
import com.nawforce.pkgforce.modifiers.{ABSTRACT_MODIFIER, PRIVATE_MODIFIER}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, INTERFACE_NATURE}
import com.nawforce.pkgforce.path.{Location, PathLocation}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

final case class MethodMap(td: Option[ApexClassDeclaration],
                           methodsByName: Map[(Name, Int), Array[MethodDeclaration]], errors: List[Issue]) {
  val deepHash: Int = td.map(_.deepHash).getOrElse(0)

  // Cache private methods that are always accessible, we need this when filtering out inherited test visible privates
  private lazy val allowablePrivateMethods =
    (td.map(_.localMethods).getOrElse(ArraySeq()).filter(_.visibility == PRIVATE_MODIFIER) ++
      td.map(_.outerStaticMethods).getOrElse(ArraySeq()).filter(_.visibility == PRIVATE_MODIFIER)).toSet

  /** Return all available methods */
  def allMethods: ArraySeq[MethodDeclaration] = {
    val buffer = new mutable.ArrayBuffer[MethodDeclaration]()
    methodsByName.values.foreach(methods => buffer.addAll(methods))
    ArraySeq.unsafeWrapArray(buffer.toArray)
  }

  /** Find a method, without concern for the calling context. */
  def findMethod(name: Name, params: ArraySeq[TypeName]): Option[MethodDeclaration] = {
    methodsByName.getOrElse((name, params.length), Array()).find(method =>
      method.parameters.map(_.typeName) == params)
  }

  /** Find a method, suitable for use from the given context */
  def findMethod(name: Name, params: ArraySeq[TypeName], staticContext: Option[Boolean],
                 context: VerifyContext): Either[String, MethodDeclaration] = {

    val matches = methodsByName.getOrElse((name, params.length), Array())

    // Filter for right static context
    val staticContextMatches = staticContext match {
      case None => matches
      case Some(x) => matches.filter(m => m.isStatic == x)
    }

    val testMatches =
      if (context.thisType.inTest) {
        staticContextMatches
      } else {
        // If not in test code, filter out inherited private test visible methods that we allowed in
        staticContextMatches.filterNot(method =>
          method.visibility == PRIVATE_MODIFIER && !allowablePrivateMethods.contains(method))
      }

    val exactMatches = testMatches.filter(_.hasParameters(params))
    if (exactMatches.length == 1)
      return Right(exactMatches.head)
    else if (exactMatches.length > 1)
      return Left("Ambiguous method call")

    // TODO: Explain what this is doing
    val erasedMatches = testMatches.filter(_.hasCallErasedParameters(context.module, params))
    if (erasedMatches.length == 1)
      return Right(erasedMatches.head)
    else if (erasedMatches.length > 1)
      return Left("Ambiguous method call")

    assignableMatch(strict = true, testMatches, params, context)
      .getOrElse(
        assignableMatch(strict = false, testMatches, params, context)
          .getOrElse(Left("No matching method found"))
      )
  }

  private def assignableMatch(strict: Boolean, matches: Array[MethodDeclaration], params: ArraySeq[TypeName],
                              context: VerifyContext): Option[Either[String, MethodDeclaration]] = {
    val assignableMatches = matches.map(m => {
      val argZip = m.parameters.map(_.typeName).zip(params)
      (argZip.forall(argPair => isAssignable(argPair._1, argPair._2, strict, context)),
        argZip.count(argPair => argPair._1 == argPair._2),
        m)
    }).filter(_._1).map(m => (m._2, m._3))

    if (assignableMatches.nonEmpty) {
      val maxIdentical = assignableMatches.map(_._1).max
      val priorityMatches = assignableMatches.filter(_._1 == maxIdentical).map(_._2)
      if (priorityMatches.length == 1)
        Some(Right(priorityMatches.head))
      else
        Some(Left("Ambiguous method call"))
    } else {
      None
    }
  }
}

object MethodMap {
  type WorkingMap = mutable.HashMap[(Name, Int), Array[MethodDeclaration]]

  private val specialOverrideMethodSignatures = Set[String] (
    "system.boolean equals(object)",
    "system.integer hashcode()",
    "system.string tostring(),"
  )

  private val batchOverrideMethodSignature = "database.querylocator start(database.batchablecontext)"

  def empty(): MethodMap = {
    new MethodMap(None, Map(), Nil)
  }

  def apply(td: TypeDeclaration, location: Option[PathLocation], superClassMap: MethodMap,
            newMethods: ArraySeq[MethodDeclaration], outerStaticMethods: ArraySeq[MethodDeclaration],
            interfaces: ArraySeq[TypeDeclaration]): MethodMap = {

    // Create a starting working map from super class map
    val workingMap = new WorkingMap()
    superClassMap.methodsByName.foreach(superMethodGroup => {
      val superMethods = superMethodGroup._2.filter(
        // Allow test visible as you can override them from a test
        method => method.visibility != PRIVATE_MODIFIER || method.isTestVisible
      )
      if (superMethods.nonEmpty)
        workingMap.put(superMethodGroup._1, superMethods)
    })
    val errors = mutable.Buffer[Issue]()
    val (staticLocals, instanceLocals) = newMethods.partition(_.isStatic)

    // Add instance methods first with validation checks
    instanceLocals.foreach {
      case am: ApexMethodLike => am.resetShadows()
      case _ =>
    }
    instanceLocals.foreach(method =>
      applyInstanceMethod(workingMap, method, td.inTest, td.isComplete, errors)
    )

    // For interfaces make sure we have all methods
    if (td.nature == INTERFACE_NATURE) {
      mergeInterfaces(workingMap, interfaces)
    }

    // Add outer statics
    outerStaticMethods.foreach(method => applyStaticMethod(workingMap, method))

    // Add local statics, de-duped
    val ignorableStatics = mutable.Set[MethodDeclaration]()
    staticLocals.duplicates(_.nameAndParameterTypes.toLowerCase).foreach(duplicates => {
      duplicates._2.foreach(duplicate => {
        ignorableStatics.add(duplicate)
        setMethodError(duplicate, s"Method '${duplicate.name}' is a duplicate of an existing method in this class", errors)
      })
    })
    staticLocals
      .filterNot(ignorableStatics.contains)
      .foreach(method => applyStaticMethod(workingMap, method))

    // Validate any interface use in classes
    if (td.nature == CLASS_NATURE && td.moduleDeclaration.nonEmpty) {
      workingMap.put((Names.Clone, 0),
        Array(CustomMethodDeclaration(Location.empty, Names.Clone, td.typeName, CustomMethodDeclaration.emptyParameters)))
      checkInterfaces(td.moduleDeclaration.get, location, td.isAbstract, workingMap, interfaces, errors)
    }

    // Only Apex class types are replaceable and hence have deep hashes
    td match {
      case td: ApexClassDeclaration => new MethodMap(Some(td), workingMap.toMap, errors.toList)
      case _: TypeDeclaration => new MethodMap(None, workingMap.toMap, errors.toList)
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
      workingMap.put(key, method +: methods.filterNot(_.hasSameParameters(method)))
  }

  private def applyInstanceMethod(workingMap: WorkingMap, method: MethodDeclaration, isTest: Boolean,
                                  isComplete: Boolean, errors: mutable.Buffer[Issue]): Unit = {
    val key = (method.name, method.parameters.length)
    val methods = workingMap.getOrElse(key, Array())

    val matched = methods.find(_.hasSameParameters(method))
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

    workingMap.put(key, method +: methods.filterNot(_.hasSameParameters(method)))
  }

  private def setMethodError(method: MethodDeclaration, error: String, errors: mutable.Buffer[Issue], isWarning: Boolean=false): Unit = {
    method match {
      case am: ApexMethodLike if !isWarning => errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case am: ApexMethodLike => errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case _ => ()
    }
  }

  private def isDuplicate(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.outerTypeId == am2.outerTypeId
      case _ => false
    }
  }

}
