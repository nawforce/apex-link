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
package com.nawforce.types

import java.lang.reflect.{Modifier => JavaModifier}

import com.nawforce.cst.ConstructContext
import com.nawforce.parsers.ApexParser.ModifierContext

sealed abstract class Modifier(name: String) {
  override def toString: String = name
}

case object GLOBAL extends Modifier("global")
case object PUBLIC extends Modifier("public")
case object PROTECTED extends Modifier("protected")
case object PRIVATE extends Modifier("private")

case object STATIC extends Modifier("static")
case object ABSTRACT extends Modifier("abstract")
case object FINAL extends Modifier("final")
case object OVERRIDE extends Modifier("override")
case object VIRTUAL extends Modifier("virtual")

case object WEBSERVICE extends Modifier("webservice")

case object TEST_METHOD extends Modifier("@isTest")
case object TEST_VISIBLE extends Modifier("@TestVisible")

case object WITH_SHARING extends Modifier("with sharing")
case object WITHOUT_SHARING extends Modifier("without sharing")
case object INHERITED_SHARING extends Modifier("inherited sharing")

object ApexModifiers {
  def construct(modifiers: Seq[ModifierContext], context: ConstructContext): Seq[Modifier] = {
    modifiers.map(_.getText).flatMap {
      case "public" => Some(PUBLIC)
      case _ => None
    }
  }
}


object PlatformModifiers {
  def typeModifiers(javaBits: Int, nature: Nature): Seq[Modifier] = {
    assert(JavaModifier.isPublic(javaBits))
    if (nature == CLASS) assert(!JavaModifier.isAbstract(javaBits))
    if (nature != ENUM) assert(!JavaModifier.isFinal(javaBits))
    assert(!JavaModifier.isTransient(javaBits))
    assert(!JavaModifier.isVolatile(javaBits))
    assert(!JavaModifier.isSynchronized(javaBits))
    assert(!JavaModifier.isNative(javaBits))
    assert(!JavaModifier.isStrict(javaBits))

    if (JavaModifier.isStatic(javaBits))
      Seq(PUBLIC, STATIC)
    else
      Seq(PUBLIC)
  }

  def fieldOrMethodModifiers(javaBits: Int): Seq[Modifier] = {
    assert(JavaModifier.isPublic(javaBits))
    assert(!JavaModifier.isAbstract(javaBits))
    assert(!JavaModifier.isFinal(javaBits))
    assert(!JavaModifier.isTransient(javaBits))
    assert(!JavaModifier.isVolatile(javaBits))
    assert(!JavaModifier.isSynchronized(javaBits))
    assert(!JavaModifier.isNative(javaBits))
    assert(!JavaModifier.isStrict(javaBits))

    if (JavaModifier.isStatic(javaBits))
      Seq(PUBLIC, STATIC)
    else
      Seq(PUBLIC)
  }

  def methodModifiers(javaBits: Int, nature: Nature): Seq[Modifier] = {
    assert(JavaModifier.isPublic(javaBits))
    if (nature == INTERFACE)
      assert(JavaModifier.isAbstract(javaBits))
    else
      assert(!JavaModifier.isAbstract(javaBits))
    assert(!JavaModifier.isFinal(javaBits))
    assert(!JavaModifier.isTransient(javaBits))
    assert(!JavaModifier.isVolatile(javaBits))
    assert(!JavaModifier.isSynchronized(javaBits))
    assert(!JavaModifier.isNative(javaBits))
    assert(!JavaModifier.isStrict(javaBits))

    if (JavaModifier.isStatic(javaBits))
      Seq(PUBLIC, STATIC)
    else
      Seq(PUBLIC)
  }

}
