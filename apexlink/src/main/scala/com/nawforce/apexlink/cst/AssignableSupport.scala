package com.nawforce.apexlink.cst

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.names.TypeName

object AssignableSupport {
  def isAssignable(
    toType: TypeName,
    fromType: TypeDeclaration,
    strict: Boolean,
    context: VerifyContext
  ): Boolean = {
    if (
      fromType.typeName == TypeNames.Null ||
      fromType.typeName == TypeNames.Any ||
      fromType.typeName == toType ||
      (!strict && toType == TypeNames.InternalObject) ||
      context.module.isGhostedType(toType)
    ) {
      true
    } else if (!strict && fromType.typeName.isRecordSet) {
      isRecordSetAssignable(toType, context)
    } else if (toType.params.nonEmpty || fromType.typeName.params.nonEmpty) {
      isAssignableGeneric(toType, fromType, context)
    } else {
      (if (strict)
         strictAssignable.contains(toType, fromType.typeName)
       else
         looseAssignable.contains(toType, fromType.typeName)) ||
      fromType.extendsOrImplements(toType)
    }
  }

  def isAssignable(
    toType: TypeName,
    fromType: TypeName,
    strict: Boolean,
    context: VerifyContext
  ): Boolean = {
    context.getTypeFor(fromType, context.thisType) match {
      case Left(_) =>
        // Allow some ghosted assignments to support Lists
        (toType == TypeNames.SObject && context.module.isGhostedType(fromType) && fromType.outer
          .contains(TypeNames.Schema)) ||
          (toType == TypeNames.InternalObject && context.module.isGhostedType(fromType))
      case Right(fromDeclaration) =>
        isAssignable(toType, fromDeclaration, strict, context)
    }
  }

  def couldBeEqual(
    toType: TypeDeclaration,
    fromType: TypeDeclaration,
    context: VerifyContext
  ): Boolean = {
    isAssignable(toType.typeName, fromType, strict = false, context) ||
    isAssignable(fromType.typeName, toType, strict = false, context)
  }

  private def isAssignableGeneric(
    toType: TypeName,
    fromType: TypeDeclaration,
    context: VerifyContext
  ): Boolean = {
    if (toType == fromType.typeName) {
      true
    } else if (toType.params.size == fromType.typeName.params.size) {
      isSObjectListAssignment(toType, fromType, context) || {
        // Future: This is over general, not supported on Set & Map, doh
        val sameParams = toType.withParams(fromType.typeName.params)
        (fromType.typeName == sameParams || fromType.extendsOrImplements(sameParams)) &&
        toType.params
          .zip(fromType.typeName.params)
          .map(p => isAssignable(p._1, p._2, strict = false, context))
          .forall(b => b)
      }
    } else if (toType.params.isEmpty || fromType.typeName.params.isEmpty) {
      fromType.extendsOrImplements(toType)
    } else {
      false
    }
  }

  private def isSObjectListAssignment(
    toType: TypeName,
    fromType: TypeDeclaration,
    context: VerifyContext
  ): Boolean = {
    if (
      toType.isList && fromType.typeName.isList &&
      fromType.typeName.params.head == TypeNames.SObject &&
      toType.params.head != TypeNames.SObject
    ) {
      context.getTypeFor(toType.params.head, context.thisType) match {
        case Left(_)              => false
        case Right(toDeclaration) => toDeclaration.isSObject
      }
    } else {
      false
    }
  }

  @scala.annotation.tailrec
  private def isRecordSetAssignable(toType: TypeName, context: VerifyContext): Boolean = {
    if (toType == TypeNames.SObject || toType.isSObjectList || toType.isObjectList) {
      true
    } else if (toType.isList) {
      isRecordSetAssignable(toType.params.head, context)
    } else {
      context.getTypeFor(toType, context.thisType) match {
        case Left(_)              => false
        case Right(toDeclaration) => toDeclaration.isSObject
      }
    }
  }

  private lazy val strictAssignable: Set[(TypeName, TypeName)] =
    Set(
      (TypeNames.Long, TypeNames.Integer),
      (TypeNames.Decimal, TypeNames.Integer),
      (TypeNames.Decimal, TypeNames.Long),
      (TypeNames.String, TypeNames.IdType),
      (TypeNames.Datetime, TypeNames.Date)
    )

  private lazy val looseAssignable: Set[(TypeName, TypeName)] =
    Set(
      (TypeNames.Long, TypeNames.Integer),
      (TypeNames.Decimal, TypeNames.Integer),
      (TypeNames.Double, TypeNames.Integer),
      (TypeNames.Decimal, TypeNames.Long),
      (TypeNames.Double, TypeNames.Long),
      (TypeNames.Double, TypeNames.Decimal),
      (TypeNames.Decimal, TypeNames.Double),
      (TypeNames.IdType, TypeNames.String),
      (TypeNames.String, TypeNames.IdType),
      (TypeNames.Datetime, TypeNames.Date)
    )
}
