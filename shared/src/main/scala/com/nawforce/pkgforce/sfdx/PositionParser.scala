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

package com.nawforce.pkgforce.sfdx

import com.nawforce.pkgforce.memory.IdentityBox
import ujson.{AstTransformer, Value}
import upickle.core.{Util, Visitor}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class PositionParser extends AstTransformer[Value] {

  val positions: mutable.Map[IdentityBox[Value], Int] = mutable.Map[IdentityBox[Value], Int]()

  def transform[T](j: Value, f: Visitor[_, T]): T = {
    j match {
      case ujson.Null => f.visitNull(-1)
      case ujson.True => f.visitTrue(-1)
      case ujson.False => f.visitFalse(-1)
      case ujson.Str(s) => f.visitString(s, -1)
      case ujson.Num(d) => f.visitFloat64(d, -1)
      case ujson.Arr(items) => transformArray(f, items)
      case ujson.Obj(items) => transformObject(f, items)
    }
  }

  def visitArray(length: Int, index: Int) = new AstArrVisitor[ArrayBuffer](xs => indexPosition(ujson.Arr(xs), index))

  def visitObject(length: Int, index: Int) = new AstObjVisitor[mutable.LinkedHashMap[String, Value]](xs => indexPosition(ujson.Obj(xs), index))

  def visitNull(index: Int): Value = indexPosition(ujson.Null, index)

  def visitFalse(index: Int): Value = indexPosition(ujson.False, index)

  def visitTrue(index: Int): Value = indexPosition(ujson.True, index)

  override def visitFloat64StringParts(s: CharSequence, decIndex: Int, expIndex: Int, index: Int): Value = {
    indexPosition(ujson.Num(
      if (decIndex != -1 || expIndex != -1) s.toString.toDouble
      else Util.parseIntegralNum(s, decIndex, expIndex, index).toDouble
    ), index)
  }

  override def visitFloat64(d: Double, index: Int): Value = indexPosition(ujson.Num(d), index)

  def visitString(s: CharSequence, index: Int): Value = indexPosition(ujson.Str(s.toString), index)

  private def indexPosition(value: Value, index: Int): Value = {
    positions.put(new IdentityBox(value), index)
    value
  }
}

class ValueWithPositions(val root: Value, val positions: Map[IdentityBox[ujson.Value], Int], data: String) {
  def positionOf(value: Value): Option[Int] = {
    positions.get(new IdentityBox(value))
  }

  def lineAndOffsetOf(value: Value): Option[(Int, Int)] = {
    positionOf(value).map(position => {
      var line = 1
      var offset = 0
      var at = 0
      while (at < position) {
        data(at) match {
          case '\n' =>
            line += 1
            offset = 0
          case _ =>
            offset += 1
        }
        at += 1
      }
      (line, offset)
    })
  }
}

object PositionParser {
  def parse(data: String): ValueWithPositions = {
    val visitor = new PositionParser
    new ValueWithPositions(ujson.transform(ujson.Readable.fromString(data), visitor), visitor.positions.toMap, data)
  }
}
