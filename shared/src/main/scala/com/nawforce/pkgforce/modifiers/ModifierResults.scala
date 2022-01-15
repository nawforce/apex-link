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

package com.nawforce.pkgforce.modifiers

import com.nawforce.pkgforce.diagnostics.Issue
import com.nawforce.pkgforce.memory.InternCache

import scala.collection.compat.immutable.ArraySeq

/** Results from modifier analysis.
  *
  * Modifiers are examined before the CST is constructed to make things a bit simpler. The results of the analysis
  * are returned via this type. Interning is supported to reduce memory use.
  */
final case class ModifierResults(modifiers: ArraySeq[Modifier], issues: ArraySeq[Issue]) {

  override val hashCode: Int = modifiers.hashCode()

  def intern: ModifierResults = ModifierResults.intern(this)

  override def equals(that: Any): Boolean = {
    that match {
      case other: ModifierResults =>
        other.canEqual(this) && doesEqual(other)
      case _ => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[ModifierResults]

  private def doesEqual(other: ModifierResults): Boolean = {
    this.modifiers == other.modifiers && this.issues == other.issues
  }

  def methodOwnerNature: MethodOwnerNature = {
    if (modifiers.contains(ABSTRACT_MODIFIER)) ABSTRACT_METHOD_NATURE
    else if (modifiers.contains(VIRTUAL_MODIFIER)) VIRTUAL_METHOD_NATURE
    else FINAL_METHOD_NATURE
  }

}

object ModifierResults extends InternCache[ModifierResults] {
  val empty: ModifierResults = ModifierResults(ArraySeq.empty, ArraySeq.empty)
}
