/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.names

import upickle.default.{macroRW, ReadWriter => RW}

/** Identifier for a specific type within an Org.
  *
  * The provided namespace is used to locate a package which qualifies the meaning of the type name. This is useful
  * when the type name may not contain a namespace or it may be visible across package such as with global classes.
  */
final case class TypeIdentifier(namespace: Option[Name], typeName: TypeName) {

  override def toString: String = {
    var typeNameAsString = typeName.toString
    if (namespace.nonEmpty && typeNameAsString.startsWith(namespace.get.value + '.')) {
      typeNameAsString =
        typeNameAsString.takeRight(typeNameAsString.length - namespace.get.value.length - 1)
      typeNameAsString + namespace.map(n => s" (${n.toString})").getOrElse("")
    } else {
      typeNameAsString + namespace.map(n => s" [${n.toString}]").getOrElse("")
    }
  }
}

object TypeIdentifier {
  implicit val rw: RW[TypeIdentifier] = macroRW

  /** Helper for construction from Java, namespace may be null to indicate unmanaged package reference */
  def fromJava(namespace: Name, typeName: TypeName): TypeIdentifier = {
    new TypeIdentifier(Option(namespace), typeName)
  }

  def apply(identifier: String): Either[String, TypeIdentifier] = {
    val parts = identifier.split(" ", 2)
    TypeName(parts.head) match {
      case Left(err)                            => Left(err)
      case Right(typeName) if parts.length == 1 => Right(TypeIdentifier(None, typeName))
      case Right(typeName) =>
        if (
          parts(1).length < 3 ||
          (parts(1).head != '(' && parts(1).head != '[') ||
          (parts(1).last != ')' && parts(1).last != ']')
        )
          Left(s"Expecting brackets around namespace in '$identifier'")
        else {
          val namespace = Name(parts(1).substring(1, parts(1).length - 1))
          val namespacedTypeName =
            if (parts(1).head == '(')
              typeName.withTail(TypeName(namespace))
            else
              typeName
          Identifier
            .isLegalIdentifier(namespace)
            .map(error => Left(s"Illegal namespace '$namespace': $error"))
            .getOrElse(Right(TypeIdentifier(Some(namespace), namespacedTypeName)))
        }
    }
  }
}
