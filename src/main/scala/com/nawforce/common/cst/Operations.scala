/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. the name of the author may not be used to endorse or promote products
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

import com.nawforce.common.api.TypeName
import com.nawforce.common.names.{TypeNames, _}
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.common.types.platform.PlatformTypes

trait AssignableSupport {
  def isAssignable(toType: TypeName, fromType: TypeDeclaration, context: VerifyContext): Boolean = {
    if (fromType.typeName == TypeNames.Null ||
      (fromType.typeName == toType) ||
      (toType == TypeNames.InternalObject) ||
      context.pkg.isGhostedType(toType)) {
      true
    } else if (fromType.typeName.isRecordSet) {
      isRecordSetAssignable(toType, context)
    } else if (toType.params.nonEmpty || fromType.typeName.params.nonEmpty) {
      isAssignableGeneric(toType, fromType, context)
    } else {
      AssignableSupport.baseAssignable.contains(toType, fromType.typeName) ||
        fromType.extendsOrImplements(toType)
    }
  }

  def isAssignable(toType: TypeName, fromType: TypeName, context: VerifyContext): Boolean = {
    context.getTypeFor(fromType, context.thisType) match {
      case Left(_) => false
      case Right(fromDeclaration) => isAssignable(toType, fromDeclaration, context)
    }
  }

  private def isAssignableGeneric(toType: TypeName, fromType: TypeDeclaration, context: VerifyContext): Boolean = {
    if (toType == fromType.typeName) {
      true
    } else if (toType.params.size == fromType.typeName.params.size) {
      isSObjectListAssignment(toType, fromType, context) || {
        // Future: This is over general, not supported on Set & Map, doh
        val sameParams = toType.withParams(fromType.typeName.params)
        (fromType.typeName == sameParams || fromType.extendsOrImplements(sameParams)) &&
          toType.params.zip(fromType.typeName.params).map(p => isAssignable(p._1, p._2, context)).forall(b =>b)
      }
    } else if (toType.params.isEmpty || fromType.typeName.params.isEmpty) {
      fromType.extendsOrImplements(toType)
    } else {
      false
    }
  }

  private def isSObjectListAssignment(toType: TypeName, fromType: TypeDeclaration, context: VerifyContext): Boolean = {
    if (toType.isList && fromType.typeName.isList &&
      fromType.typeName.params.head == TypeNames.SObject &&
      toType.params.head != TypeNames.SObject) {
      context.getTypeFor(toType.params.head, context.thisType) match {
        case Left(_) => false
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
        case Left(_) => false
        case Right(toDeclaration) => toDeclaration.isSObject
      }
    }
  }


  def couldBeEqual(toType: TypeDeclaration, fromType: TypeDeclaration, context: VerifyContext): Boolean = {
    isAssignable(toType.typeName, fromType, context) || isAssignable(fromType.typeName, toType, context)
  }
}

object AssignableSupport {
  private lazy val baseAssignable: Set[(TypeName, TypeName)] = Set(
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

abstract class Operation extends AssignableSupport {
  def verify(leftType: ExprContext, rightContext: ExprContext, op: String,
             context: VerifyContext): Either[String, ExprContext]

  def getCommonBase(toType: TypeDeclaration, fromType: TypeDeclaration, context: VerifyContext): Option[TypeDeclaration] = {
    val toSupertypes = toType.superTypes()
    val fromSupertypes = fromType.superTypes()
    val common = toSupertypes.intersect(fromSupertypes)
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

  def isNonReferenceKind(typeName: TypeName): Boolean = {
    Operation.nonReferenceTypes.contains(typeName)
  }

  def getArithmeticResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.arithmeticOps.get((leftType, rightType))
  }

  def getArithmeticAddSubtractAssigmentResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.arithmeticAddSubtractAssigmentOps.get((leftType, rightType))
  }

  def getArithmeticMultiplyDivideAssigmentResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.arithmeticMultiplyDivideAssigmentOps.get((leftType, rightType))
  }

  def getBitwiseResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.bitwiseOps.get((leftType, rightType))
  }

  def getBitwiseAssignmentResult(leftType: TypeName, rightType: TypeName): Option[TypeDeclaration] = {
    Operation.bitwiseAssignmentOps.get((leftType, rightType))
  }
}

object Operation {
  private lazy val nonReferenceTypes: Set[TypeName] = Set (
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
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Integer, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Integer, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Integer, TypeNames.Double) -> PlatformTypes.doubleType,
    (TypeNames.Long, TypeNames.Integer) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Long, TypeNames.Double) -> PlatformTypes.doubleType,
    (TypeNames.Decimal, TypeNames.Integer) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Long) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Double) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Integer) -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Long) -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Double) -> PlatformTypes.doubleType,
    (TypeNames.Date, TypeNames.Integer) -> PlatformTypes.dateType,
    (TypeNames.Date, TypeNames.Long) -> PlatformTypes.dateType,
    (TypeNames.Datetime, TypeNames.Integer) -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Long) -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Decimal) -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Double) -> PlatformTypes.datetimeType,
    (TypeNames.Time, TypeNames.Integer) -> PlatformTypes.timeType,
    (TypeNames.Time, TypeNames.Long) -> PlatformTypes.timeType,
  )

  private lazy val arithmeticAddSubtractAssigmentOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Long, TypeNames.Integer) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Decimal, TypeNames.Integer) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Long) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Double) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Integer) -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Long) -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Double) -> PlatformTypes.doubleType,
    (TypeNames.Date, TypeNames.Integer) -> PlatformTypes.dateType,
    (TypeNames.Date, TypeNames.Long) -> PlatformTypes.dateType,
    (TypeNames.Datetime, TypeNames.Integer) -> PlatformTypes.datetimeType,
    (TypeNames.Datetime, TypeNames.Long) -> PlatformTypes.datetimeType,
    (TypeNames.Time, TypeNames.Integer) -> PlatformTypes.timeType,
    (TypeNames.Time, TypeNames.Long) -> PlatformTypes.timeType,
  )

  private lazy val arithmeticMultiplyDivideAssigmentOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Long, TypeNames.Integer) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Decimal, TypeNames.Integer) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Long) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Double) -> PlatformTypes.decimalType,
    (TypeNames.Decimal, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Integer) -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Long) -> PlatformTypes.doubleType,
    (TypeNames.Double, TypeNames.Decimal) -> PlatformTypes.decimalType,
    (TypeNames.Double, TypeNames.Double) -> PlatformTypes.doubleType,
  )

  private lazy val bitwiseOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Integer, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Integer) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Boolean, TypeNames.Boolean) -> PlatformTypes.booleanType,
  )

  private lazy val bitwiseAssignmentOps: Map[(TypeName, TypeName), TypeDeclaration] = Map(
    (TypeNames.Integer, TypeNames.Integer) -> PlatformTypes.integerType,
    (TypeNames.Long, TypeNames.Integer) -> PlatformTypes.longType,
    (TypeNames.Long, TypeNames.Long) -> PlatformTypes.longType,
    (TypeNames.Boolean, TypeNames.Boolean) -> PlatformTypes.longType,
  )
}

case object AssignmentOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    if (rightContext.typeName == TypeNames.Null) {
      Right(leftContext)
    } else if (isAssignable(leftContext.typeName, rightContext.typeDeclaration, context)) {
      Right(leftContext)
    } else {
      Left(s"Incompatible types in assignment, from '${rightContext.typeName}' to '${leftContext.typeName}'")
    }
  }
}

case object LogicalOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    if (!isAssignable(TypeNames.Boolean, rightContext.typeDeclaration, context)) {
      Left(s"Right expression of logical $op must a boolean, not '${rightContext.typeName}'")
    } else if (!isAssignable(TypeNames.Boolean, leftContext.typeDeclaration, context)) {
      Left(s"Left expression of logical $op must a boolean, not '${leftContext.typeName}'")
    } else {
      Right(leftContext)
    }
  }
}

case object CompareOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {

    if (isNumericKind(leftContext.typeName)) {
      if (!isNumericKind(rightContext.typeName)) {
        return Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
      }
    } else if (isStringKind(leftContext.typeName)) {
      if (!isStringKind(rightContext.typeName))
        return Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
    } else if (isDateKind(leftContext.typeName)) {
      if (!isDateKind(rightContext.typeName)) {
        return Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
      }
    } else {
      return Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
    }
    Right(ExprContext(isStatic = Some(false), PlatformTypes.booleanType))
  }
}

case object ExactEqualityOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {

    if (isNonReferenceKind(leftContext.typeName)) {
      Left(s"Exact equality/inequality requires is not supported on non-reference types '${leftContext.typeName}'")
    } else if (isNonReferenceKind(rightContext.typeName)) {
      Left(s"Exact equality/inequality requires is not supported on non-reference types '${rightContext.typeName}'")
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
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    if (couldBeEqual(leftContext.typeDeclaration, rightContext.typeDeclaration, context))
      Right(ExprContext(isStatic = Some(false), PlatformTypes.booleanType))
    else
      Left(s"Comparing incompatible types '${leftContext.typeName}' and '${rightContext.typeName}'")
  }
}

case object PlusOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    if (leftContext.typeName == TypeNames.String || rightContext.typeName == TypeNames.String) {
      Right(ExprContext(isStatic = Some(false), PlatformTypes.stringType))
    }
    else {
      val td = getArithmeticResult(leftContext.typeName, rightContext.typeName)
      if (td.isEmpty) {
        return Left(s"Addition operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'")
      }
      Right(ExprContext(isStatic = Some(false), td.get))
    }
  }
}

case object ArithmeticOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    val td = getArithmeticResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(s"Arithmetic operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'")
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object ArithmeticAddSubtractAssignmentOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    if (leftContext.typeName == TypeNames.String && op == "+=") {
      Right(ExprContext(isStatic = Some(false), PlatformTypes.stringType))
    } else {
      val td = getArithmeticAddSubtractAssigmentResult(leftContext.typeName, rightContext.typeName)
      if (td.isEmpty) {
        return Left(s"Arithmetic operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'")
      }
      Right(ExprContext(isStatic = Some(false), td.get))
    }
  }
}

case object ArithmeticMultiplyDivideAssignmentOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    val td = getArithmeticMultiplyDivideAssigmentResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(s"Arithmetic operation not allowed between types '${leftContext.typeName}' and '${rightContext.typeName}'")
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object BitwiseOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    val td = getBitwiseResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(s"Bitwise operation only allowed between Integer, Long & Boolean types, not '${leftContext.typeName}' and '${rightContext.typeName}'")
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object BitwiseAssignmentOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {
    val td = getBitwiseAssignmentResult(leftContext.typeName, rightContext.typeName)
    if (td.isEmpty) {
      return Left(s"Bitwise operation only allowed between Integer, Long & Boolean types, not '${leftContext.typeName}' and '${rightContext.typeName}'")
    }
    Right(ExprContext(isStatic = Some(false), td.get))
  }
}

case object ConditionalOperation extends Operation {
  override def verify(leftContext: ExprContext, rightContext: ExprContext,
                      op: String, context: VerifyContext): Either[String, ExprContext] = {

    // Future: How does this really function, Java mechanics are very complex
    if (isAssignable(leftContext.typeName, rightContext.typeDeclaration, context)) {
      Right(leftContext)
    } else if (isAssignable(rightContext.typeName, leftContext.typeDeclaration, context)) {
      Right(rightContext)
    } else {
      getCommonBase(leftContext.typeDeclaration, rightContext.typeDeclaration, context)
          .map(td => Right(ExprContext(isStatic = Some(false), td)))
          .getOrElse({
            Left(s"Incompatible types in ternary operation '${leftContext.typeName}' and '${rightContext.typeName}'")
          })
    }
  }
}
