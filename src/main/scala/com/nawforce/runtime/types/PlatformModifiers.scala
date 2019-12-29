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
package com.nawforce.runtime.types

import java.lang.reflect.{Modifier => JavaModifier}

import com.nawforce.common.types._

object PlatformModifiers {
  def typeModifiers(javaBits: Int, nature: Nature): Seq[Modifier] = {
    assert(JavaModifier.isPublic(javaBits))
    if (nature == CLASS_NATURE) assert(!JavaModifier.isAbstract(javaBits))
    if (nature != ENUM_NATURE) assert(!JavaModifier.isFinal(javaBits))
    assert(!JavaModifier.isTransient(javaBits))
    assert(!JavaModifier.isVolatile(javaBits))
    assert(!JavaModifier.isSynchronized(javaBits))
    assert(!JavaModifier.isNative(javaBits))
    assert(!JavaModifier.isStrict(javaBits))

    Seq(PUBLIC_MODIFIER) ++
      (if (nature == CLASS_NATURE) Seq(VIRTUAL_MODIFIER) else Seq()) ++
      (if (JavaModifier.isStatic(javaBits)) Seq(STATIC_MODIFIER) else Seq())
  }

  def fieldOrMethodModifiers(javaBits: Int): Seq[Modifier] = {
    assert(JavaModifier.isPublic(javaBits))
    assert(!JavaModifier.isAbstract(javaBits))
    assert(!JavaModifier.isTransient(javaBits))
    assert(!JavaModifier.isVolatile(javaBits))
    assert(!JavaModifier.isSynchronized(javaBits))
    assert(!JavaModifier.isNative(javaBits))
    assert(!JavaModifier.isStrict(javaBits))

    Seq(PUBLIC_MODIFIER) ++
      (if (JavaModifier.isStatic(javaBits)) Seq(STATIC_MODIFIER) else Seq()) ++
      (if (JavaModifier.isFinal(javaBits)) Seq(FINAL_MODIFIER) else Seq())
  }

  def methodModifiers(javaBits: Int, nature: Nature): Seq[Modifier] = {
    assert(JavaModifier.isPublic(javaBits))
    if (nature == INTERFACE_NATURE)
      assert(JavaModifier.isAbstract(javaBits))
    else
      assert(!JavaModifier.isAbstract(javaBits))
    assert(!JavaModifier.isFinal(javaBits))
    assert(!JavaModifier.isTransient(javaBits))
    assert(!JavaModifier.isVolatile(javaBits))
    assert(!JavaModifier.isSynchronized(javaBits))
    assert(!JavaModifier.isNative(javaBits))
    assert(!JavaModifier.isStrict(javaBits))

    Seq(PUBLIC_MODIFIER) ++
      (if (JavaModifier.isStatic(javaBits)) Seq(STATIC_MODIFIER) else Seq())

  }
}
