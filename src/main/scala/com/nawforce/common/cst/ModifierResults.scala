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
 3. The name of the author may not be used to endorse or promote products
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

import java.lang.ref.WeakReference

import com.nawforce.common.diagnostics.Issue

import scala.collection.mutable

/** Results from modifier analysis.
  *
  * Modifiers are examined before the CST is constructed to make things a bit simpler. The results of the analysis
  * are returned via this type. Interning is supported to reduce memory use.
  **/
case class ModifierResults(modifiers: Array[Modifier], issues: Array[Issue]) {

  override val hashCode: Int = modifiers.toSeq.hashCode()

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
    this.modifiers.sameElements(other.modifiers) &&
      this.issues.sameElements(other.issues)
  }
}

object ModifierResults {
  private var cache = mutable.WeakHashMap[ModifierResults, WeakReference[ModifierResults]]()

  def intern(modifierResults: ModifierResults): ModifierResults = {
    cache.getOrElseUpdate(modifierResults, new WeakReference[ModifierResults](modifierResults)).get
  }

  def clearCache(): Unit = {
    cache = new mutable.WeakHashMap[ModifierResults, WeakReference[ModifierResults]]()
  }
}
