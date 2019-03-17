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

import com.nawforce.types._
import com.nawforce.utils.DotName
import org.scalatest.FunSuite

class PlatformTypesValid extends FunSuite {

  test("Right number of types (should exclude inners)") {
    assert(PlatformTypeDeclaration.classNames.size == 1295)
  }

  test("All outer types are valid") {
    PlatformTypeDeclaration.classNames.foreach(className => {
      val typeDeclaration = PlatformTypeDeclaration.get(className)
      assert(typeDeclaration.nonEmpty)
      validateTypeDeclaration(className, typeDeclaration.get)
    })
  }

  def validateTypeDeclaration(className: DotName, typeDeclaration: PlatformTypeDeclaration): Unit = {
    // name & typeName are valid
    assert(typeDeclaration.name.toString == className.lastName.toString)
    className.toString match {
      case "System.List" => assert(typeDeclaration.typeName.toString == "System.List<T>")
      case "System.Iterator" => assert(typeDeclaration.typeName.toString == "System.Iterator<T>")
      case "System.Map" => assert(typeDeclaration.typeName.toString == "System.Map<K, V>")
      case "System.Set" => assert(typeDeclaration.typeName.toString == "System.Set<T>")
      case "System.Iterable" => assert(typeDeclaration.typeName.toString == "System.Iterable<T>")
      case _ => assert(typeDeclaration.typeName.toString == className.toString)
    }

    // superClass & interfaces reference platform types
    if (typeDeclaration.superClass.nonEmpty)
      assert(PlatformTypeDeclaration.get(typeDeclaration.superClass.get.asDotName).nonEmpty)
    typeDeclaration.interfaces.foreach(tn => PlatformTypeDeclaration.get(tn.asDotName))

    // nature valid and superClass & interfaces are valid for it
    typeDeclaration.nature match {
      case INTERFACE =>
        assert(typeDeclaration.superClass.isEmpty)
      case ENUM =>
        assert(typeDeclaration.superClass.isEmpty)
        assert(typeDeclaration.interfaces.isEmpty)
      case CLASS => ()
    }

    // Modifiers, always public for outer platform classes
    if (typeDeclaration.parent.isEmpty)
      assert(typeDeclaration.modifiers == Seq(PUBLIC))

    // Nested classes
    typeDeclaration.nature match {
      case INTERFACE =>
        assert(typeDeclaration.nestedClasses.isEmpty)
      case ENUM =>
        assert(typeDeclaration.nestedClasses.isEmpty)
      case CLASS =>
        typeDeclaration.nestedClasses.foreach(nested => validateTypeDeclaration(className.append(nested.name), nested))
    }
  }
}
