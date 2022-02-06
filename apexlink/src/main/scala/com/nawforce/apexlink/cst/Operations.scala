/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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

import com.nawforce.apexlink.cst.AssignableSupport.{couldBeEqual, isAssignable}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.pkgforce.names.TypeName

abstract class Operation {
  def verify(
    leftType: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext]

  def getCommonBase(
    toType: TypeDeclaration,
    fromType: TypeDeclaration,
    context: VerifyContext
  ): Option[TypeDeclaration] = {
    val toSupertypes   = toType.superTypes()
    val fromSupertypes = fromType.superTypes()
    val common         = toSupertypes.intersect(fromSupertypes)
    common.headOption.flatMap(context.getTypeFor(_, context.thisType).toOption)
  }

  def isNumericKind(typeName: TypeName): Boolean = {
    typeName == TypeNames.Integer ||
    typeName == TypeNames.Long ||
    typeName == TypeNames.Decimal ||
    typeName == TypeNames.Double
  }

  def isStringKind(typeName: TypeName): Boolean = {
    typeName == TypeNames.String ||
    typeName == TypeNames.IdType
  }

  def isDateKind(typeName: TypeName): Boolean = {
    typeName == TypeNames.Date ||
    typeName == TypeNames.Datetime
  }

  def isTimeKind(typeName: TypeName): Boolean = {
    typeName == TypeNames.Time
  }

  def isNonReferenceKind(typeName: TypeName): Boolean = {
    Operation.nonReferenceTypes.contains(typeName)
  }

  def getArithmeticResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.arithmeticOps.get((leftType, rightType))
  }

  def getArithmeticAddSubtractAssigmentResult(
    leftType: TypeName,
    rightType: TypeName
  ): Option[TypeDeclaration] = {
    Operation.arithmeticAddSubtractAssigmentOps.get((leftType, rightType))
  }

  def getArithmeticMultiplyDivideAssigmentResult(
    leftType: TypeName,
    rightType: TypeName
  ): Option[TypeDeclaration] = {
    Operation.arithmeticMultiplyDivideAssigmentOps.get((leftType, rightType))
  }

  def getBitwiseResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.bitwiseOps.get((leftType, rightType))
  }

  def getBitwiseAssignmentResult(
    leftType: TypeName,
    rightType: TypeName
  ): Option[TypeDeclaration] = {
    Operation.bitwiseAssignmentOps.get((leftType, rightType))
  }
}

object Operation {
  private lazy val nonReferenceTypes: Set[TypeName] = Set(
    TypeNames.Integer,
    TypeNames.Long,
    TypeNames.Decimal,
    TypeNames.Double,
    TypeNames.String,
    TypeNames.Date,
    TypeNames.Datetime,
    TypeNames.Time,
    TypeNames.Blob,
    TypeNames.IdType
  )

  private lazy val arithmeticOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer)  -> PlatformTypes.integerType,
    (TypeNames.Integer, TypeNames.Long)     -> PlatformTypes.longType,
    (TypeNames.Integer, TypeNames.Decimal)  -> PlatformTypes.decimalType,
    (TypeNames.Integer, TypeNames.Double)   -> PlatformTypes.doubleType,
    (TypeNames.Long, TypeNames.Integer)     -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long)        -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Decimal)     -> PlatformTypes.decimalType,
    (TypeNames.Long, TypeNames.Double)      -> PlatformTypes.doubleType,
    (TypeNames.Decimal, TypeNames.Integer)  -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Long)     -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Decimal)  -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Double)   -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Integer)   -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Long)      -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Decimal)   -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Double)    -> PlatformTypes.doubleType,
    (TypeNames.Date, TypeNames.Integer)     -> PlatformTypes.dateType,
    (TypeNames.Date, TypeNames.Long)        -> PlatformTypes.dateType,
    (TypeNames.Datetime, TypeNames.Integer) -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Long)    -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Decimal) -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Double)  -> PlatformTypes.datetimeType,
    (TypeNames.Time, TypeNames.Integer)     -> PlatformTypes.timeType,
    (TypeNames.Time, TypeNames.Long)        -> PlatformTypes.timeType
  )

  private lazy val arithmeticAddSubtractAssigmentOps: Map[(TypeName, TypeName), TypeDeclaration] =
    Map(
      (TypeNames.Integer, TypeNames.Integer)  -> PlatformTypes.integerType,
      (TypeNames.Long, TypeNames.Integer)     -> PlatformTypes.longType,
      (TypeNames.Long, TypeNames.Long)        -> PlatformTypes.longType,
      (TypeNames.Decimal, TypeNames.Integer)  -> PlatformTypes.decimalType,
      (TypeNames.Decimal, TypeNames.Long)     -> PlatformTypes.decimalType,
      (TypeNames.Decimal, TypeNames.Double)   -> PlatformTypes.decimalType,
      (TypeNames.Decimal, TypeNames.Decimal)  -> PlatformTypes.decimalType,
      (TypeNames.Double, TypeNames.Integer)   -> PlatformTypes.doubleType,
      (TypeNames.Double, TypeNames.Long)      -> PlatformTypes.doubleType,
      (TypeNames.Double, TypeNames.Decimal)   -> PlatformTypes.decimalType,
      (TypeNames.Double, TypeNames.Double)    -> PlatformTypes.doubleType,
      (TypeNames.Date, TypeNames.Integer)     -> PlatformTypes.dateType,
      (TypeNames.Date, TypeNames.Long)        -> PlatformTypes.dateType,
      (TypeNames.Datetime, TypeNames.Integer) -> PlatformTypes.datetimeType,
      (TypeNames.Datetime, TypeNames.Long)    -> PlatformTypes.datetimeType,
      (TypeNames.Time, TypeNames.Integer)     -> PlatformTypes.timeType,
      (TypeNames.Time, TypeNames.Long)        -> PlatformTypes.timeType
    )

  private lazy val arithmeticMultiplyDivideAssigmentOps
    : Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Long, TypeNames.Integer)    -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long)       -> PlatformTypes.longType,
    (TypeNames.Decimal, TypeNames.Integer) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Long)    -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Double)  -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Integer)  -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Long)     -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Decimal)  -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Double)   -> PlatformTypes.doubleType
  )

  private lazy val bitwiseOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Integer, TypeNames.Long)    -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Integer)    -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long)       -> PlatformTypes.longType,
    (TypeNames.Boolean, TypeNames.Boolean) -> PlatformTypes.booleanType
  )

  private lazy val bitwiseAssignmentOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Long, TypeNames.Integer)    -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long)       -> PlatformTypes.longType,
    (TypeNames.Boolean, TypeNames.Boolean) -> PlatformTypes.longType
  )
}

case object AssignmentOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    if (rightContext.typeName == TypeNames.Null) {
      Right(leftContext)
    } else if (
      isAssignable(leftContext.typeName, rightContext.typeDeclaration, strict = false, context)
    ) {
      Right(leftContext)
    } else {
      Left(
        s"Incompatible types in assignment, from '${rightContext.typeName}' to '${leftContext.typeName}'"
      )
    }
  }
}

case object LogicalOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    if (!isAssignable(TypeNames.Boolean, rightContext.typeDeclaration, strict = false, context)) {
      Left(s"Right expression of logical $op must a boolean, not '${rightContext.typeName}'")
    } else if (
      !isAssignable(TypeNames.Boolean, leftContext.typeDeclaration, strict = false, context)
    ) {
      Left(s"Left expression of logical $op must a boolean, not '${leftContext.typeName}'")
    } else {
      Right(leftContext)
    }
  }
}

case object CompareOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {

    if (isNumericKind(leftContext.typeName)) {
      if (!isNumericKind(rightContext.typeName)) {
        return Left(
          s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'"
        )
      }
    } else if (isStringKind(leftContext.typeName)) {
      if (!isStringKind(rightContext.typeName))
        return Left(
          s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'"
        )
    } else if (isDateKind(leftContext.typeName)) {
      if (!isDateKind(rightContext.typeName)) {
        return Left(
          s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'"
        )
      }
    } else if (isTimeKind(leftContext.typeName)) {
      if (!isTimeKind(rightContext.typeName)) {
        return Left(
          s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'"
        )
      }
    } else {
      return Left(
        s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'"
      )
    }
    Right(ExprContext(isStatic = Some(false), PlatformTypes.booleanType))
  }
}

case object ExactEqualityOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {

    if (isNonReferenceKind(leftContext.typeName)) {
      Left(
        s"Exact equality/inequality requires is not supported on non-reference types '${leftContext.typeName}'"
      )
    } else if (isNonReferenceKind(rightContext.typeName)) {
      Left(
        s"Exact equality/inequality requires is not supported on non-reference types '${rightContext.typeName}'"
      )
    } else if (
      leftContext.typeName == rightContext.typeName ||
      leftContext.typeName == TypeNames.InternalObject ||
      rightContext.typeName == TypeNames.InternalObject ||
      leftContext.typeDeclaration.extendsOrImplements(rightContext.typeName) ||
      rightContext.typeDeclaration.extendsOrImplements(leftContext.typeName)
    )
      Right(ExprContext(isStatic = Some(false), PlatformTypes.booleanType))
    else
      Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
  }
}

case object EqualityOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    if (couldBeEqual(leftContext.typeDeclaration, rightContext.typeDeclaration, context))
      Right(ExprContext(isStatic = Some(false), PlatformTypes.booleanType))
    else
      Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
  }
}

case object PlusOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    if (leftContext.typeName == TypeNames.String || rightContext.typeName == TypeNames.String) {
      Right(ExprContext(isStatic = Some(false), PlatformTypes.stringType))
    } else {
      val td = getArithmeticResult(leftContext.typeName, rightContext.typeName)
      if (td.isEmpty) {
        return Left(
          s"Addition operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'"
        )
      }
      Right(ExprContext(isStatic = Some(false), td.get))
    }
  }
}

case object ArithmeticOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    val td = getArithmeticResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(
        s"Arithmetic operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'"
      )
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object ArithmeticAddSubtractAssignmentOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    if (leftContext.typeName == TypeNames.String && op == "+=") {
      Right(ExprContext(isStatic = Some(false), PlatformTypes.stringType))
    } else {
      val td = getArithmeticAddSubtractAssigmentResult(leftContext.typeName, rightContext.typeName)
      if (td.isEmpty) {
        return Left(
          s"Arithmetic operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'"
        )
      }
      Right(ExprContext(isStatic = Some(false), td.get))
    }
  }
}

case object ArithmeticMultiplyDivideAssignmentOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    val td = getArithmeticMultiplyDivideAssigmentResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(
        s"Arithmetic operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'"
      )
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object BitwiseOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    val td = getBitwiseResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(
        s"Bitwise operation only allowed between Integer, Long & Boolean types, not '${leftContext.typeName}' and '${rightContext.typeName}'"
      )
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object BitwiseAssignmentOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {
    val td = getBitwiseAssignmentResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(
        s"Bitwise operation only allowed between Integer, Long & Boolean types, not '${leftContext.typeName}' and '${rightContext.typeName}'"
      )
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object ConditionalOperation extends Operation {
  override def verify(
    leftContext: ExprContext,
    rightContext: ExprContext,
    op: String,
    context: VerifyContext
  ): Either[String, ExprContext] = {

    // Future: How does this really function, Java mechanics are very complex
    if (isAssignable(leftContext.typeName, rightContext.typeDeclaration, strict = false, context)) {
      Right(leftContext)
    } else if (
      isAssignable(rightContext.typeName, leftContext.typeDeclaration, strict = false, context)
    ) {
      Right(rightContext)
    } else {
      getCommonBase(leftContext.typeDeclaration, rightContext.typeDeclaration, context)
        .map(td => Right(ExprContext(isStatic = Some(false), td)))
        .getOrElse({
          Left(
            s"Incompatible types in ternary operation '${leftContext.typeName}' and '${rightContext.typeName}'"
          )
        })
    }
  }
}
