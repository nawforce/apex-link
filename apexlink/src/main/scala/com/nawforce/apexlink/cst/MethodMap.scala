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
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.names.{TypeNames, XNames}
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, ApexDeclaration, ApexMethodLike}
import com.nawforce.apexlink.types.core.MethodDeclaration.{
  emptyMethodDeclarations,
  emptyMethodDeclarationsSet
}
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

final case class MethodMap(
  td: Option[ApexClassDeclaration],
  methodsByName: Map[(Name, Int), Array[MethodDeclaration]],
  testVisiblePrivateMethods: Set[MethodDeclaration],
  errors: List[Issue]
) {
  val deepHash: Int = td.map(_.deepHash).getOrElse(0)

  /** Return all available methods */
  def allMethods: ArraySeq[MethodDeclaration] = {
    val buffer = new mutable.ArrayBuffer[MethodDeclaration]()
    methodsByName.values.foreach(methods => buffer.addAll(methods))
    ArraySeq.unsafeWrapArray(buffer.toArray)
  }

  /** Find a method, without concern for the calling context so must be an exact match. */
  def findMethod(name: Name, params: ArraySeq[TypeName]): Option[MethodDeclaration] = {
    methodsByName
      .getOrElse((name, params.length), Array())
      .find(method => method.parameters.map(_.typeName) == params)
  }

  /** Find a method using full rules for disambiguation */
  def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    context: VerifyContext
  ): Either[String, MethodDeclaration] = {
    findMethodCall(name, params, staticContext, context) match {
      case Left(err)     => Left(err.value)
      case Right(method) => Right(method)
    }
  }

  private def findMethodCall(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    context: VerifyContext
  ): Either[MethodCallError, MethodDeclaration] = {

    val matches = methodsByName.getOrElse((name, params.length), Array())

    // Filter for right static context
    val staticContextMatches = staticContext match {
      case None    => matches
      case Some(x) => matches.filter(m => m.isStatic == x)
    }

    val testMatches =
      if (!context.thisType.inTest && testVisiblePrivateMethods.nonEmpty) {
        // If not in test code, filter out inherited private test visible methods that we allowed in
        staticContextMatches.filterNot(testVisiblePrivateMethods.contains)
      } else {
        staticContextMatches
      }

    // Try for an exact match first
    val exactMatches =
      testMatches.filter(_.hasParameters(params, allowPlatformGenericEquivalence = true))
    if (exactMatches.length == 1)
      return Right(exactMatches.head)
    else if (exactMatches.length > 1)
      return Left(AMBIGUOUS_ERROR)

    // If no exact we need to search for possible in two stages, either using strict assignment or lax if
    // we are still short of a possible match
    var found =
      mostSpecificMatch(strict = true, testMatches, params, context)
        .getOrElse(
          mostSpecificMatch(strict = false, testMatches, params, context)
            .getOrElse(Left(NO_MATCH_ERROR))
        )

    // If not found locally search super & outer for statics
    if (found == Left(NO_MATCH_ERROR) && !staticContext.contains(false)) {
      found = findStaticMethodOn(td.flatMap(_.superClassDeclaration), name, params, context)
      if (found == Left(NO_MATCH_ERROR)) {
        found = findStaticMethodOn(td.flatMap(_.outerTypeDeclaration), name, params, context)
      }
    }

    found
  }

  /** Helper for finding static methods on related type declarations */
  private def findStaticMethodOn(
    td: Option[TypeDeclaration],
    name: Name,
    params: ArraySeq[TypeName],
    context: VerifyContext
  ): Either[MethodCallError, MethodDeclaration] = {
    td match {
      case Some(td: ApexClassDeclaration) =>
        td.methodMap.findMethodCall(name, params, Some(true), context)
      case _ =>
        Left(NO_MATCH_ERROR)
    }
  }

  /** Find the most specific method call match. This uses a most-specific selection model similar to that
    * outlined in JLS6 15.12.2.5.
    */
  private def mostSpecificMatch(
    strict: Boolean,
    matches: Array[MethodDeclaration],
    params: ArraySeq[TypeName],
    context: VerifyContext
  ): Option[Either[MethodCallError, MethodDeclaration]] = {

    val assignable = matches.filter(m => {
      val argZip = m.parameters.map(_.typeName).zip(params)
      argZip.forall(argPair => isAssignable(argPair._1, argPair._2, strict, context))
    })

    if (assignable.isEmpty)
      None
    else if (assignable.length == 1)
      Some(Right(assignable.head))
    else {
      Some(
        assignable
          .find(
            method =>
              assignable.forall(
                m => m == method || method.isMoreSpecific(m, params, context).contains(true)
              )
          )
          .map(Right(_))
          .getOrElse(Left(AMBIGUOUS_ERROR))
      )
    }
  }
}

object MethodMap {
  type WorkingMap = mutable.HashMap[(Name, Int), List[MethodDeclaration]]

  private val specialOverrideMethodSignatures = Set[String](
    "system.boolean equals(object)",
    "system.integer hashcode()",
    "system.string tostring(),"
  )

  private val batchOverrideMethodSignature =
    "database.querylocator start(database.batchablecontext)"

  def empty(): MethodMap = {
    new MethodMap(None, Map(), Set(), Nil)
  }

  private def toMap(workingMap: WorkingMap): Map[(Name, Int), Array[MethodDeclaration]] = {
    workingMap.map(kv => (kv._1, kv._2.toArray)).toMap
  }

  /** Construct for an arbitrary type declaration, for Apex classes used the other apply function which builds
    * up a proper MethodMap. This is just for other type declarations with non-complex needs.
    */
  def apply(td: TypeDeclaration): MethodMap = {
    val workingMap = new WorkingMap()
    td.methods.foreach(method => {
      val key = (method.name, method.parameters.length)
      workingMap.put(key, method :: workingMap.getOrElse(key, Nil))
    })
    new MethodMap(None, toMap(workingMap), Set(), Nil)
  }

  def apply(
    td: TypeDeclaration,
    location: Option[PathLocation],
    superClassMap: MethodMap,
    newMethods: ArraySeq[MethodDeclaration],
    outerStaticMethods: ArraySeq[MethodDeclaration],
    interfaces: ArraySeq[TypeDeclaration]
  ): MethodMap = {

    // Create a starting working map from super class map, just removing statics initially
    var workingMap         = new WorkingMap()
    val testVisiblePrivate = mutable.Set[MethodDeclaration]()
    superClassMap.methodsByName.foreach(superMethodGroup => {
      val superMethods = superMethodGroup._2.filterNot(_.isStatic)
      workingMap.put(superMethodGroup._1, superMethods.toList)
    })
    workingMap = workingMap.filterNot(_._2.isEmpty)

    // Now build the rest of the map with the new methods
    val errors                         = mutable.Buffer[Issue]()
    val (staticLocals, instanceLocals) = newMethods.partition(_.isStatic)

    // Add instance methods first with validation checks
    instanceLocals.foreach {
      case am: ApexMethodLike => am.resetShadows()
      case _                  =>
    }
    instanceLocals.foreach(
      method => applyInstanceMethod(workingMap, method, td.inTest, td.isComplete, errors)
    )

    // Now strip out none test visible/abstract inherited privates excluding when a super class is in the same file as
    // td, in that case the private methods are visible. Yeah, this is very odd behaviour, but might be related to how
    // Java compiles inner classes as outers.
    val sameFileSuperclassPrivateMethods = findSameFileSuperclassPrivateMethods(td)
    workingMap.foreach(keyAndMethodGroup => {
      val methods = keyAndMethodGroup._2.filterNot(method => {
        method.visibility == PRIVATE_MODIFIER && !method.isAbstract &&
          !(method.isTestVisible || isApexLocalMethod(
            td,
            method
          ) || sameFileSuperclassPrivateMethods.contains(method))
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
    staticLocals
      .duplicates(_.nameAndParameterTypes.toLowerCase)
      .foreach(duplicates => {
        duplicates._2.foreach(duplicate => {
          ignorableStatics.add(duplicate)
          setMethodError(
            duplicate,
            s"Method '${duplicate.name}' is a duplicate of an existing method",
            errors
          )
        })
      })
    staticLocals
      .filterNot(ignorableStatics.contains)
      .foreach(method => applyStaticMethod(workingMap, method))

    // Validate any interface use in classes
    if (td.nature == CLASS_NATURE && td.moduleDeclaration.nonEmpty) {
      workingMap.put(
        (Names.Clone, 0),
        List(
          CustomMethodDeclaration(
            Location.empty,
            Names.Clone,
            td.typeName,
            CustomMethodDeclaration.emptyParameters
          )
        )
      )
      checkInterfaces(td, location, td.isAbstract, workingMap, interfaces, errors)
    }

    // Only Apex class types are replaceable and hence have deep hashes
    val testVisiblePrivateSet =
      if (testVisiblePrivate.isEmpty) emptyMethodDeclarationsSet else testVisiblePrivate.toSet
    td match {
      case td: ApexClassDeclaration =>
        new MethodMap(Some(td), toMap(workingMap), testVisiblePrivateSet, errors.toList)
      case _: TypeDeclaration =>
        new MethodMap(None, toMap(workingMap), testVisiblePrivateSet, errors.toList)
    }
  }

  private def isApexLocalMethod(td: TypeDeclaration, method: MethodDeclaration): Boolean = {
    (td, method) match {
      case (td: ApexClassDeclaration, method: ApexMethodLike) =>
        td.typeId == method.outerTypeId
      case _ =>
        false
    }
  }

  private def findSameFileSuperclassPrivateMethods(
    td: TypeDeclaration
  ): ArraySeq[MethodDeclaration] = {
    if (td.superClass.nonEmpty) {
      val superClass = td.superClassDeclaration
      if (superClass.nonEmpty && superClass.get.paths == td.paths) {
        return superClass.get.methods.filter(
          method => method.visibility == PRIVATE_MODIFIER && !method.isStatic
        )
      }
    }
    emptyMethodDeclarations
  }

  private def mergeInterfaces(
    workingMap: WorkingMap,
    interfaces: ArraySeq[TypeDeclaration]
  ): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        mergeInterface(workingMap, i)
      case _ => ()
    })
  }

  private def mergeInterface(workingMap: WorkingMap, interface: TypeDeclaration): Unit = {
    if (interface.isInstanceOf[ApexClassDeclaration] && interface.nature == INTERFACE_NATURE)
      mergeInterfaces(workingMap, interface.interfaceDeclarations)

    interface.methods
      .filterNot(_.isStatic)
      .foreach(method => {
        val key     = (method.name, method.parameters.length)
        val methods = workingMap.getOrElse(key, Nil)

        val matched = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))
        if (matched.isEmpty) {
          workingMap.put(key, method :: methods)
        } else {
          matched.get match {
            case am: ApexMethodLike => am.addShadow(method)
            case _                  => ()
          }
        }
      })
  }

  private def checkInterfaces(
    from: TypeDeclaration,
    location: Option[PathLocation],
    isAbstract: Boolean,
    workingMap: WorkingMap,
    interfaces: ArraySeq[TypeDeclaration],
    errors: mutable.Buffer[Issue]
  ): Unit = {
    interfaces.foreach({
      case i: TypeDeclaration if i.nature == INTERFACE_NATURE =>
        checkInterface(from, location, isAbstract, workingMap, i, errors)
      case _ => ()
    })
  }

  private def checkInterface(
    from: TypeDeclaration,
    location: Option[PathLocation],
    isAbstract: Boolean,
    workingMap: WorkingMap,
    interface: TypeDeclaration,
    errors: mutable.Buffer[Issue]
  ): Unit = {
    if (interface.isInstanceOf[ApexClassDeclaration] && interface.nature == INTERFACE_NATURE)
      checkInterfaces(
        from,
        location,
        isAbstract,
        workingMap,
        interface.interfaceDeclarations,
        errors
      )

    interface.methods
      .filterNot(_.isStatic)
      .foreach(method => {
        val key     = (method.name, method.parameters.length)
        val methods = workingMap.getOrElse(key, Nil)
        val matched = methods.find(mapMethod => isInterfaceMethod(from, method, mapMethod))

        if (matched.isEmpty) {
          val module = from.moduleDeclaration.get
          lazy val hasGhostedMethods =
            methods.exists(
              method =>
                module.isGhostedType(method.typeName) ||
                  methods.exists(
                    method => method.parameters.map(_.typeName).exists(module.isGhostedType)
                  )
            )

          if (isAbstract) {
            workingMap.put(
              key,
              method :: methods
                .filterNot(_.hasSameSignature(method, allowPlatformGenericEquivalence = true))
            )
          } else if (!hasGhostedMethods) {
            location.foreach(
              l =>
                errors.append(
                  new Issue(
                    l.path,
                    Diagnostic(
                      ERROR_CATEGORY,
                      l.location,
                      s"Method '${method.signature}' from interface '${interface.typeName}' must be implemented"
                    )
                  )
                )
            )
          }
        } else {
          matched.get match {
            case am: ApexMethodLike => am.addShadow(method)
            case _                  => ()
          }
        }
      })
  }

  private def applyStaticMethod(workingMap: WorkingMap, method: MethodDeclaration): Unit = {
    val key     = (method.name, method.parameters.length)
    val methods = workingMap.getOrElse(key, Nil)
    val matched = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))
    if (matched.isEmpty)
      workingMap.put(key, method :: methods)
    else if (matched.get.isStatic)
      workingMap.put(key, method :: methods.filterNot(_ eq matched.get))
  }

  /** Add an instance method into the working map. The is littered with very horrible conditional logic based on
    * what we have been able to work out from testing.
    */
  private def applyInstanceMethod(
    workingMap: WorkingMap,
    method: MethodDeclaration,
    isTest: Boolean,
    isComplete: Boolean,
    errors: mutable.Buffer[Issue]
  ): Unit = {
    val errorCount = errors.length
    val key        = (method.name, method.parameters.length)
    val methods    = workingMap.getOrElse(key, Nil)
    val matched    = methods.find(mapMethod => areSameMethodsIgnoringReturn(mapMethod, method))

    if (matched.nonEmpty) {
      val matchedMethod = matched.get
      lazy val isSpecial = {
        val matchedSignature = matchedMethod.signature.toLowerCase()
        specialOverrideMethodSignatures.contains(matchedSignature) ||
        (matchedSignature == batchOverrideMethodSignature &&
        method.typeName.outer.contains(TypeNames.System) && method.typeName.name == XNames.Iterable)
      }

      lazy val isPlatformMethod =
        matchedMethod.isInstanceOf[PlatformMethod] || matchedMethod
          .isInstanceOf[GenericPlatformMethod]

      lazy val isInterfaceMethod =
        !matchedMethod.hasBlock && !matchedMethod.modifiers.contains(ABSTRACT_MODIFIER)

      lazy val reallyPrivateMethod =
        matchedMethod.visibility == PRIVATE_MODIFIER && !areInSameApexFile(method, matchedMethod)

      if (areInSameApexClass(matchedMethod, method)) {
        matchedMethod match {
          case matchedMethod: ApexMethodLike =>
            if (method.hasSameParameters(matchedMethod, allowPlatformGenericEquivalence = false))
              setMethodError(
                method,
                s"Method '${method.name}' is a duplicate of an existing method at ${matchedMethod.idLocation
                  .displayPosition()}",
                errors
              )
            else
              setMethodError(
                method,
                s"Method '${method.name}' can not use same platform generic interface as existing method at ${matchedMethod.idLocation
                  .displayPosition()}",
                errors
              )
          case _ => ()
        }
      } else if (matchedMethod.typeName != method.typeName && !reallyPrivateMethod && !isSpecial) {
        setMethodError(
          method,
          s"Method '${method.name}' has wrong return type to override, should be '${matched.get.typeName}'",
          errors,
          isWarning = true
        )
      } else if (!matchedMethod.isVirtualOrAbstract && !reallyPrivateMethod) {
        setMethodError(
          method,
          s"Method '${method.name}' can not override non-virtual method",
          errors
        )
      } else if (
        !method.isVirtualOrOverride && !reallyPrivateMethod && !matchedMethod.isAbstract &&
        !isInterfaceMethod && !isSpecial && !isTest && !isPlatformMethod
      ) {
        setMethodError(method, s"Method '${method.name}' must use override keyword", errors)
      } else if (
        method.visibility.methodOrder < matchedMethod.visibility.methodOrder && !isSpecial
      ) {
        setMethodError(
          method,
          s"Method '${method.name}' can not reduce visibility in override",
          errors
        )
      } else if (
        method.isOverride && matchedMethod.isVirtualOrAbstract && matchedMethod.visibility == PRIVATE_MODIFIER
      ) {
        // Some escapes from this being bad, don't ask why, know one knows :-(
        if (
          !areInSameApexFile(
            method,
            matchedMethod
          ) && !(method.inTest && matchedMethod.isTestVisible)
        )
          setMethodError(
            method,
            s"Method '${method.name}' can not override a private method",
            errors
          )
      }
    } else if (method.isOverride && isComplete) {
      setMethodError(
        method,
        s"Method '${method.name}' does not override a virtual or abstract method",
        errors
      )
    }

    // Shadow if all looks OK
    if (errors.length == errorCount) {
      method match {
        case am: ApexMethodLike => matched.foreach(am.addShadow)
        case _                  => ()
      }
    }

    // Update workingMap with new methods, regardless of if we error on it as probably was meant to be
    matched match {
      case None          => workingMap.put(key, method :: methods)
      case Some(matched) => workingMap.put(key, method :: methods.filterNot(_ eq matched))
    }
  }

  private def setMethodError(
    method: MethodDeclaration,
    error: String,
    errors: mutable.Buffer[Issue],
    isWarning: Boolean = false
  ): Unit = {
    method match {
      case am: ApexMethodLike if !isWarning =>
        errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case am: ApexMethodLike =>
        errors.append(new Issue(am.location.path, Diagnostic(ERROR_CATEGORY, am.idLocation, error)))
      case _ => ()
    }
  }

  /** Determine if two Apex defined methods are declared in the same Apex file. */
  private def areInSameApexFile(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.location.path == am2.location.path
      case _                                          => false
    }
  }

  /** Determine if two methods are declared in the same Apex class. The implementation is a bit awkward due to
    * how apex defined and platform methods diff in representation.
    */
  private def areInSameApexClass(m1: MethodDeclaration, m2: MethodDeclaration): Boolean = {
    (m1, m2) match {
      case (am1: ApexMethodLike, am2: ApexMethodLike) => am1.outerTypeId == am2.outerTypeId
      case (pm1: PlatformMethod, pm2: PlatformMethod) => pm1.typeDeclaration eq pm2.typeDeclaration
      case _                                          => false
    }
  }

  /** Determine if two methods are considered the same without looking at the return type. For 'equals' we
    * consider them the same if they both have a single parameter even if that parameter differs. This is
    * because defining equals in a class will hide the Object equals method if the arguments don't match.
    */
  private def areSameMethodsIgnoringReturn(
    method: MethodDeclaration,
    other: MethodDeclaration
  ): Boolean = {
    if (
      method.name == other.name &&
      method.hasSameParameters(other, allowPlatformGenericEquivalence = true)
    )
      true
    else if (isEqualsLike(method) && isEqualsLike(other))
      true
    else
      false
  }

  /** Check if the implMethod fulfills the contract of the interfaceMethod. As usual there are plenty of oddities
    * to handle to determine this.
    */
  private def isInterfaceMethod(
    from: TypeDeclaration,
    interfaceMethod: MethodDeclaration,
    implMethod: MethodDeclaration
  ): Boolean = {
    if (
      implMethod.name == interfaceMethod.name &&
      canAssign(interfaceMethod.typeName, implMethod.typeName, from) &&
      interfaceMethod.fulfillsInterfaceMethodParams(from, implMethod.parameters.map(_.typeName))
    )
      true
    else if (isEqualsLike(interfaceMethod) && isEqualsLike(implMethod))
      true
    else if (isDatabaseBatchableStart(interfaceMethod) && isDatabaseBatchableIterable(implMethod))
      true
    else
      false
  }

  /** A helper to invoke isAssignable which need a VerifyContext */
  private def canAssign(toType: TypeName, fromType: TypeName, from: TypeDeclaration): Boolean = {
    if (toType == fromType)
      true
    else {
      from match {
        case ad: ApexDeclaration =>
          val context = new TypeVerifyContext(None, ad, None)
          isAssignable(toType, fromType, strict = false, context)
        case _ =>
          false
      }
    }
  }

  private def isEqualsLike(method: MethodDeclaration): Boolean = {
    method.name == XNames.Equals &&
    method.typeName == TypeNames.Boolean &&
    !method.isStatic &&
    method.parameters.length == 1
  }

  private def isDatabaseBatchableStart(method: MethodDeclaration): Boolean = {
    method.name == XNames.Start &&
    method.typeName == TypeNames.QueryLocator &&
    !method.isStatic &&
    method.parameters.length == 1 && method.parameters.head.typeName == TypeNames.BatchableContext
  }

  private def isDatabaseBatchableIterable(method: MethodDeclaration): Boolean = {
    method.name == XNames.Start &&
    (method.typeName.isIterable || method.typeName.isList) &&
    !method.isStatic &&
    method.parameters.length == 1 && method.parameters.head.typeName == TypeNames.BatchableContext
  }
}
