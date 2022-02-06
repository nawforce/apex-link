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

package com.nawforce.apexlink.finding

import com.nawforce.pkgforce.diagnostics.{Diagnostic, Issue, MISSING_CATEGORY}
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.path.PathLocation

/** Collection of error types returned from type requests */
sealed abstract class TypeError(val typeName: TypeName) {
  def asIssue(location: PathLocation): Issue

  // Protect against old way of using this
  override def toString: String = throw new IllegalArgumentException
}

final case class MissingType(_typeName: TypeName) extends TypeError(_typeName) {
  def asIssue(location: PathLocation): Issue = {
    new Issue(
      location.path,
      Diagnostic(MISSING_CATEGORY, location.location, s"No type declaration found for '$typeName'")
    )
  }

  // Protect against old way of using this
  override def toString: String = throw new IllegalArgumentException
}

final case class WrongTypeArguments(_typeName: TypeName, expected: Integer)
    extends TypeError(_typeName) {
  def asIssue(location: PathLocation): Issue = {
    new Issue(
      location.path,
      Diagnostic(
        MISSING_CATEGORY,
        location.location,
        s"Wrong number of type arguments for '$typeName', expected $expected"
      )
    )
  }

  // Protect against old way of using this
  override def toString: String = throw new IllegalArgumentException
}
