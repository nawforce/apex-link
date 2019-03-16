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
package com.nawforce.apexlink.behaviour.types

import com.nawforce.types.{CLASS, ENUM, INTERFACE, PlatformTypeDeclaration}
import org.scalatest.FunSuite

class PlatformTypesValid extends FunSuite {

  test("Right number of types (should exclude inners)") {
    assert(PlatformTypeDeclaration.classNames.size == 1295)
  }

  test("All types are valid") {
    PlatformTypeDeclaration.classNames.foreach(cn => {
      val tdOpt = PlatformTypeDeclaration.get(cn)
      assert(tdOpt.nonEmpty)
      val td = tdOpt.get

      // name & typeName are valid
      assert(td.name.toString == cn.lastName.toString)
      cn.toString match {
        case "System.List" => assert(td.typeName.toString == "System.List<T>")
        case "System.Iterator" => assert(td.typeName.toString == "System.Iterator<T>")
        case "System.Map" => assert(td.typeName.toString == "System.Map<K, V>")
        case "System.Set" => assert(td.typeName.toString == "System.Set<T>")
        case "System.Iterable" => assert(td.typeName.toString == "System.Iterable<T>")
        case _ => assert(td.typeName.toString == cn.toString)
      }

      // superClass & interfaces reference platform types
      if (td.superClass.nonEmpty)
        assert(PlatformTypeDeclaration.get(td.superClass.get.asDotName).nonEmpty)
      td.interfaces.foreach(tn => PlatformTypeDeclaration.get(tn.asDotName))

      // nature valid and superClass & interfaces are valid for it
      td.nature match {
        case INTERFACE =>
          assert(td.superClass.isEmpty)
        case ENUM =>
          assert(td.superClass.isEmpty)
          assert(td.interfaces.isEmpty)
        case CLASS => ()
      }
    })
  }
}
