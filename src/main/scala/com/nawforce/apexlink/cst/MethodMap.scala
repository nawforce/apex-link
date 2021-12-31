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
import com.nawforce.apexlink.types.core.MethodDeclaration.{emptyMethodDeclarations, emptyMethodDeclarationsSet}
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

sealed abstract class MethodCallError(final val value: String)

case object NO_MATCH_ERROR extends MethodCallError("No matching method found")

case object AMBIGUOUS_ERROR extends MethodCallError("Ambiguous method call")

final case class MethodMap(td: Option[ApexClassDeclaration],
                           methodsByName: Map[(Name, Int), Array[MethodDeclaration]],
                           testVisiblePrivateMethods: Set[MethodDeclaration],
                           errors: List[Issue]) {
  val deepHash: Int = td.map(_.deepHash).getOrElse(0)

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
    findMethodCall(name, params, staticContext, context) match {
      case Left(err) => Left(err.value)
      case Right(method) => Right(method)
    }
  }

  private def findMethodCall(name: Name, params: ArraySeq[TypeName], staticContext: Option[Boolean],
                             context: VerifyContext): Either[MethodCallError, MethodDeclaration] = {

    val matches = methodsByName.getOrElse((name, params.length), Array())

    // Filter for right static context
    val staticContextMatches = staticContext match {
      case None => matches
      case Some(x) => matches.filter(m => m.isStatic == x)
    }

    val testMatches =
      if (!context.thisType.inTest && testVisiblePrivateMethods.nonEmpty) {
        // If not in test code, filter out inherited private test visible methods that we allowed in
        staticContextMatches.filterNot(testVisiblePrivateMethods.contains)
      } else {
        staticContextMatches
      }

    val exactMatches = testMatches.filter(_.hasParameters(params))
    if (exactMatches.length == 1)
      return Right(exactMatches.head)
    else if (exactMatches.length > 1)
      return Left(AMBIGUOUS_ERROR)

    // TODO: Explain what this is doing
    val erasedMatches = testMatches.filter(_.hasCallErasedParameters(context.module, params))
    if (erasedMatches.length == 1)
      return Right(erasedMatches.head)
    else if (erasedMatches.length > 1)
      return Left(AMBIGUOUS_ERROR)

    var found =
      assignableMatch(strict = true, testMatches, params, context)
        .getOrElse(
          assignableMatch(strict = false, testMatches, params, context)
            .getOrElse(Left(NO_MATCH_ERROR))
        )

    // If not found locally search super & outer for statics
    if (found == Left(NO_MATCH_ERROR) && !staticContext.contains(false)) {
      found = findMethodOn(td.flatMap(_.superClassDeclaration), name, params, staticContext, context)
      if (found == Left(NO_MATCH_ERROR)) {
        found = findMethodOn(td.flatMap(_.outerTypeDeclaration), name, params, staticContext, context)
      }
    }

    found
  }

  private def findMethodOn(td: Option[TypeDeclaration], name: Name, params: ArraySeq[TypeName], staticContext: Option[Boolean],
                           context: VerifyContext): Either[MethodCallError, MethodDeclaration] = {
    td match {
      case Some(td: ApexClassDeclaration) =>
        td.methodMap.findMethodCall(name, params, staticContext, context)
      case _ =>
        Left(NO_MATCH_ERROR)
    }
  }

  private def assignableMatch(strict: Boolean, matches: Array[MethodDeclaration], params: ArraySeq[TypeName],
                              context: VerifyContext): Option[Either[MethodCallError, MethodDeclaration]] = {
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
        Some(Left(AMBIGUOUS_ERROR))
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
    new MethodMap(None, Map(), Set(), Nil)
  }

  def apply(td: TypeDeclaration, location: Option[PathLocation], superClassMap: MethodMap,
            newMethods: ArraySeq[MethodDeclaration], outerStaticMethods: ArraySeq[MethodDeclaration],
            interfaces: ArraySeq[TypeDeclaration]): MethodMap = {

    // Find private methods to include to workaround a bug where one Inner class that extends another Inner with
    // the same outer can access the private methods of the base class. Yeah, well screwed up stuff.
    val innerPeerSuperclassPrivateMethods = findInnerPeerSuperclassPrivateMethods(td)

    // Create a starting working map from super class map, just removing statics initially
    var workingMap = new WorkingMap()
    val testVisiblePrivate = mutable.Set[MethodDeclaration]()
    superClassMap.methodsByName.foreach(superMethodGroup => {
      val superMethods = superMethodGroup._2.filterNot(_.isStatic)
      workingMap.put(superMethodGroup._1, superMethods)
    })
    workingMap = workingMap.filterNot(_._2.isEmpty)

    // Now build the rest of the map with the new methods
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

    // Now strip out inherited privates that are neither test visible or impacted by the bug (see above)
    workingMap.foreach(keyAndMethodGroup => {
      val methods = keyAndMethodGroup._2.filterNot(method => {
        method.visibility == PRIVATE_MODIFIER &&
          !(
            method.isTestVisible || isApexLocalMethod(td, method) ||
              innerPeerSuperclassPrivateMethods.contains(method)
            )
      })
      workingMap.put(keyAndMethodGroup._1, methods)
    })
    workingMap = workingMap.filterNot(_._2.isEmpty)

    // For interfaces make sure we have all methods
    if (td.nature == INTERFACE_NATURE) {
      mergeInterfaces(workingMap, interfaces)
    }

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
    val testVisiblePrivateSet = if (testVisiblePrivate.isEmpty) emptyMethodDeclarationsSet else testVisiblePrivate.toSet
    td match {
      case td: ApexClassDeclaration =>
        new MethodMap(Some(td), workingMap.toMap, testVisiblePrivateSet, errors.toList)
      case _: TypeDeclaration =>
        new MethodMap(None, workingMap.toMap, testVisiblePrivateSet, errors.toList)
    }
  }

  private def isApexLocalMethod(td: TypeDeclaration, method: MethodDeclaration): Boolean = {
    (td, method) match {
      case (td: ApexClassDeclaration, method: ApexMethodLike) =>
        method.outerTypeId == td.typeId
      case _ =>
        false
    }
  }

  private def findInnerPeerSuperclassPrivateMethods(td: TypeDeclaration): ArraySeq[MethodDeclaration] = {
    lazy val superClass = td.superClassDeclaration
    if (td.outerTypeName.nonEmpty && td.superClass.nonEmpty &&
      superClass.nonEmpty && superClass.get.outerTypeName == td.outerTypeName) {
      superClass.get.methods.filter(method => method.visibility == PRIVATE_MODIFIER && !method.isStatic)
    } else {
      emptyMethodDeclarations
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

      val matched = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))
      if (matched.isEmpty) {
        workingMap.put(key, method +: methods)
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
      var matched = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))

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
    val matched = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))
    if (matched.isEmpty)
      workingMap.put(key, method +: methods)
    else if (matched.get.isStatic)
      workingMap.put(key, method +: methods.filterNot(_ eq matched.get))
  }

  /** Add an instance method into the working map. The is littered with very horrible conditional logic based on
    * what we have been able to work out from testing. */
  private def applyInstanceMethod(workingMap: WorkingMap, method: MethodDeclaration, isTest: Boolean,
                                  isComplete: Boolean, errors: mutable.Buffer[Issue]): Unit = {
    val errorCount = errors.length
    val key = (method.name, method.parameters.length)
    val methods = workingMap.getOrElse(key, Array())
    val matched = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))

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

      lazy val reallyPrivateMethod =
        matchedMethod.visibility == PRIVATE_MODIFIER && !areInSameApexFile(method, matchedMethod)

      if (areInSameApexClass(matchedMethod, method)) {
        setMethodError(method,
          s"Method '${method.name}' is a duplicate of an existing method in this class", errors)
      }
      else if (matchedMethod.typeName != method.typeName && !reallyPrivateMethod && !isSpecial) {
        setMethodError(method,
          s"Method '${method.name}' has wrong return type to override, should be '${matched.get.typeName}'",
          errors, isWarning = true)
      } else if (!matchedMethod.isVirtualOrAbstract && !reallyPrivateMethod) {
        setMethodError(method, s"Method '${method.name}' can not override non-virtual method", errors)
      } else if (!method.isVirtualOrOverride && !reallyPrivateMethod && !matchedMethod.isAbstract &&
        !isInterfaceMethod && !isSpecial && !isTest && !isPlatformMethod) {
        setMethodError(method, s"Method '${method.name}' must use override keyword", errors)
      } else if (method.visibility.methodOrder < matchedMethod.visibility.methodOrder && !isSpecial) {
        setMethodError(method, s"Method '${method.name}' can not reduce visibility in override", errors)
      } else if (method.isOverride && matchedMethod.isVirtualOrAbstract && matchedMethod.visibility == PRIVATE_MODIFIER) {
        // Some escapes from this being bad, don't ask why, know one knows :-(
        if (!areInSameApexFile(method, matchedMethod) && !(method.inTest && matchedMethod.isTestVisible))
          setMethodError(method, s"Method '${method.name}' can not override a private method", errors)
      }
    } else if (method.isOverride && isComplete) {
      setMethodError(method, s"Method '${method.name}' does not override a virtual or abstract method", errors)
    }

    // Shadow if all looks OK
    if (errors.length == errorCount) {
      method match {
        case am: ApexMethodLike => matched.foreach(am.addShadow)
        case _ => ()
      }
    }

    // Update workingMap with new methods, regardless of if we error on it as probably was meant to be
    matched match {
      case None => workingMap.put(key, method +: methods)
      case Some(matched) => workingMap.put(key, method +: methods.filterNot(_ eq matched))
    }
  }

  private def setMethodError(method: MethodDeclaration, error: String, errors: mutable.Buffer[Issue], isWarning: Boolean = false): Unit = {
    method match {
      case am: ApexMethodLike if !isWarning => errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case am: ApexMethodLike => errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case _ => ()
    }
  }

  /** Determine if two Apex defined methods are declared in the same Apex file. */
  private def areInSameApexFile(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.location.path == am2.location.path
      case _ => false
    }
  }

  /** Determine if two methods are declared in the same Apex class. The implementation is a bit awkward due to
    * how apex defined and platform methods diff in representation. */
  private def areInSameApexClass(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.outerTypeId == am2.outerTypeId
      case (pm1: PlatformMethod, pm2: PlatformMethod) => pm1.typeDeclaration eq pm2.typeDeclaration
      case _ => false
    }
  }

  /** Determine if two methods are considered the same without looking at the return type. For 'equals' we
    * consider them the same if they both have a single parameter even if that parameter differs. This is
    * because defining equals in a class will hide the Object equals method if the arguments don't match. */
  private def areSameMethodsIgnoringReturn(method: MethodDeclaration, other: MethodDeclaration): Boolean = {
    method.name == other.name &&
      (if (method.name == XNames.Equals && !method.isStatic && !other.isStatic) {
        method.parameters.length == 1 && other.parameters.length == 1
      } else {
        method.hasSameParameters(other)
      })
  }
}
