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

import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.common.types.apex.FullDeclaration
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class OperationsTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls").toString
  private var defaultOrg: OrgImpl = new OrgImpl

  def typeDeclaration(clsText: String): TypeDeclaration = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, PathFactory("Dummy.cls"), clsText).head
      defaultOrg.unmanaged.upsertMetadata(td)
      td.validate()
      td
    }
  }

  before {
    defaultOrg = new OrgImpl
  }

  test("String prefix bug on SObject") {
    typeDeclaration("public class Dummy {{Object a;  String b = '' + + a;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Date addition") {
    typeDeclaration("public class Dummy {{Date a;  Date b = a + 1;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Date subtraction") {
    typeDeclaration("public class Dummy {{Date a;  Date b = a - 12l;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Datetime addition") {
    typeDeclaration("public class Dummy {{Datetime a;  Datetime b = a + 1;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Datetime subtraction") {
    typeDeclaration("public class Dummy {{Datetime a;  Datetime b = a - 12l;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Time addition") {
    typeDeclaration("public class Dummy {{Time a;  Time b = a + 1;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Time subtraction") {
    typeDeclaration("public class Dummy {{Time a;  Time b = a - 12l;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("List assignment to Object") {
    typeDeclaration("public class Dummy {{Object a; a = new List<String>{''}; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("sObject List to Specific Object List") {
    typeDeclaration("public class Dummy {{List<Account> a; a = new List<sObject>(); }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Alternative not equal") {
    typeDeclaration("public class Dummy {{Boolean a; a = 1 <> 2; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Split comparator") {
    typeDeclaration("public class Dummy {{Boolean a; a = 1 < = 2; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Bitwise Integer to Integer") {
    typeDeclaration("public class Dummy {{Integer a; a = a & 2; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Bitwise Long to Integer") {
    typeDeclaration("public class Dummy {{Long a; a = a | 2; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Bitwise Boolean to Boolean") {
    typeDeclaration("public class Dummy {{Boolean a; a = a ^ false; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Bitwise Assigment Integer to Integer") {
    typeDeclaration("public class Dummy {{Integer a; a ^= a; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Bitwise Assigment Integer to Long") {
    typeDeclaration("public class Dummy {{Long a; a &= 2; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Bitwise Assigment Long to Integer") {
    typeDeclaration("public class Dummy {{Integer a; a |= 22l; }}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 32-40: Bitwise operation only allowed between Integer, Long & Boolean types, not 'System.Integer' and 'System.Long'\n")
  }

  test("Bitwise Shift in Array Index") {
    typeDeclaration("public class Dummy {{List<String> a; Integer b; String c = a[b << 2];}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("String concat assignment") {
    typeDeclaration("public class Dummy {{String a; a += 12;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Enum value assignment") {
    typeDeclaration("public class Dummy {enum MyEnum{A} String A; {MyEnum a; a = Dummy.MyEnum.A;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Nested ternary") {
    typeDeclaration("public class Dummy {{ String a; a = (1==0) ? 'a' : (1==-1) ? null : 'b';}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Ternary common base") {
    typeDeclaration("public virtual class Dummy {class A extends Dummy {} class B extends Dummy {} { A a; B b; Object a = 2>0 ? a : b;}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Ternary SObjectType") {
    typeDeclaration("public virtual class Dummy {{ SObjectType a = 2>0 ? Account.SObjectType : Contact.SObjectType;}}")
    assert(!defaultOrg.issues.hasMessages)
  }
}
