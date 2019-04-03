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
import com.nawforce.documents.TextRange
import com.nawforce.parsers.ApexParser.{IdContext, ModifierContext}
import com.nawforce.utils.IssueLog

sealed abstract class Modifier(name: String) {
  override def toString: String = name
}

case object GLOBAL_MODIFIER extends Modifier("global")
case object PUBLIC_MODIFIER extends Modifier("public")
case object PROTECTED_MODIFIER extends Modifier("protected")
case object PRIVATE_MODIFIER extends Modifier("private")
case object TEST_CLASS_MODIFIER extends Modifier("@isTest")

case object STATIC_MODIFIER extends Modifier("static")
case object ABSTRACT_MODIFIER extends Modifier("abstract")
case object FINAL_MODIFIER extends Modifier("final")
case object OVERRIDE_MODIFIER extends Modifier("override")
case object VIRTUAL_MODIFIER extends Modifier("virtual")

case object WEBSERVICE_MODIFIER extends Modifier("webservice")

case object TEST_METHOD_MODIFIER extends Modifier("@isTest")
case object TEST_VISIBLE_MODIFIER extends Modifier("@TestVisible")

case object WITH_SHARING_MODIFIER extends Modifier("with sharing")
case object WITHOUT_SHARING_MODIFIER extends Modifier("without sharing")
case object INHERITED_SHARING_MODIFIER extends Modifier("inherited sharing")

object ApexModifiers {
  def classModifiers(modifierContexts: Seq[ModifierContext], context: ConstructContext,
                     outer: Boolean, idContext: IdContext)
  : Seq[Modifier] = {

    val mods = modifierContexts.flatMap(modifierContext =>
      modifierContext.getText match {
        case "global" => Some(GLOBAL_MODIFIER)
        case "public" => Some(PUBLIC_MODIFIER)
        case "private" if !outer => Some(PRIVATE_MODIFIER)
        case "withsharing" => Some(WITH_SHARING_MODIFIER)
        case "withoutsharing" => Some(WITHOUT_SHARING_MODIFIER)
        case "inheritedsharing" => Some(INHERITED_SHARING_MODIFIER)
        case _ =>
          val modifierText = modifierContext.getText
          if (modifierText == "private")
            IssueLog.logMessage(TextRange(modifierContext),
              s"Modifier '${modifierContext.getText}' is not supported on ${if (outer) "outer" else ""} classes")
          else
            IssueLog.logMessage(TextRange(modifierContext),
              s"Modifier '${modifierContext.getText}' is not supported on classes")
          None
      })

    if (mods.size == modifierContexts.size) {
      val duplicates = mods.groupBy(identity).collect { case (_, List(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        IssueLog.logMessage(TextRange(idContext), s"Modifier '${duplicates.head.toString}' is used more than once")
      } else if (outer && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
        IssueLog.logMessage(TextRange(idContext), s"Outer classes must be declared either 'global' or 'public'")
      } else if (mods.intersect(Seq(PUBLIC_MODIFIER, PROTECTED_MODIFIER, PRIVATE_MODIFIER)).size > 1) {
        IssueLog.logMessage(TextRange(idContext),
          s"Only one visibility modifier from 'global', 'public' & 'private' may be used on classes")
      } else if (mods.intersect(Seq(WITH_SHARING_MODIFIER, WITHOUT_SHARING_MODIFIER, INHERITED_SHARING_MODIFIER)).size > 1) {
        IssueLog.logMessage(TextRange(idContext),
          s"Only one sharing modifier from 'with sharing', 'without sharing' & 'inherited sharing' may be used on classes")
      }
    }
    mods
  }

  def construct(modifiers: Seq[ModifierContext], context: ConstructContext): Seq[Modifier] = {
    modifiers.map(_.getText).flatMap {
      case "public" => Some(PUBLIC_MODIFIER)
      case _ => None
    }
  }
}

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

    if (JavaModifier.isStatic(javaBits))
      Seq(PUBLIC_MODIFIER, STATIC_MODIFIER)
    else
      Seq(PUBLIC_MODIFIER)
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
      Seq(PUBLIC_MODIFIER, STATIC_MODIFIER)
    else
      Seq(PUBLIC_MODIFIER)
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

    if (JavaModifier.isStatic(javaBits))
      Seq(PUBLIC_MODIFIER, STATIC_MODIFIER)
    else
      Seq(PUBLIC_MODIFIER)
  }

}
