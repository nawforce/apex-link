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
package com.nawforce.pkgforce.diagnostics

import com.nawforce.pkgforce.path.Location
import upickle.default.{macroRW, ReadWriter => RW}

@upickle.implicits.key("DiagnosticCategory")
sealed abstract class DiagnosticCategory(val value: String)

case object SYNTAX_CATEGORY  extends DiagnosticCategory("Syntax")
case object ERROR_CATEGORY   extends DiagnosticCategory("Error")
case object MISSING_CATEGORY extends DiagnosticCategory("Missing")
case object WARNING_CATEGORY extends DiagnosticCategory("Warning")
case object UNUSED_CATEGORY  extends DiagnosticCategory("Unused")

object DiagnosticCategory {
  def apply(value: String): DiagnosticCategory = {
    value match {
      case SYNTAX_CATEGORY.value  => SYNTAX_CATEGORY
      case ERROR_CATEGORY.value   => ERROR_CATEGORY
      case MISSING_CATEGORY.value => MISSING_CATEGORY
      case WARNING_CATEGORY.value => WARNING_CATEGORY
      case UNUSED_CATEGORY.value  => UNUSED_CATEGORY
    }
  }

  def isErrorType(category: DiagnosticCategory): Boolean = {
    category match {
      case SYNTAX_CATEGORY  => true
      case ERROR_CATEGORY   => true
      case MISSING_CATEGORY => true
      case _                => false
    }
  }

  implicit val rw: RW[DiagnosticCategory] = macroRW
}

/** A diagnostic message, category tells us what type of diagnostic this is while location and messages provide details */
@upickle.implicits.key("Diagnostic")
case class Diagnostic(category: DiagnosticCategory, location: Location, message: String)

object Diagnostic {
  implicit val rw: RW[Diagnostic] = macroRW

  def apply(category: String, location: Location, message: String): Diagnostic = {
    new Diagnostic(DiagnosticCategory(category), location, message)
  }
}
