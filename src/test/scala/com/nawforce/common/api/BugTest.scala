/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.common.api

import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class BugTest extends AnyFunSuite {

  test("Enum scoping") {
    FileSystemHelper.run(Map(
      "Wrapper.cls" -> "public virtual class Wrapper {public enum MyEnum {A, B, C}}",
      "Dummy.cls" ->
        s"""
           |public class Dummy {
           |  private enum MyEnum {
           |    D
           |  }
           |  private class Inner extends Wrapper {
           |    Object e = MyEnum.D;
           |  }
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Bad generics") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        "public class Dummy {{ DescribeFieldResult a; List<Schema.PicklistEntry> b = a.getPicklistValues();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Bad generics (without generic)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {{ DescribeFieldResult a; Integer b = a.getByteLength();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Duplicate SObject/Type name resolves as Type") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        "public class Dummy {{String b = Site.getName();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Duplicate SObject/Type name resolves as SObject") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {{Site a; SObjectType b = a.getSObjectType();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("SObject static use of getSobjectType") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {{SObjectType b = Account.getSobjectType();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Clone apex type") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {{Dummy a,b; b = a.clone();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("System type name clash with field") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {String Matcher; {Matcher.capitalize();}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Static method of super class of outer") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy extends SuperClass {class Inner {public void ifunc(){ func(); } }}",
      "Super.cls" -> "public virtual class SuperClass {public static void func() {}}"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      org.issues.dumpMessages(false)
      assert(!org.issues.hasMessages)
    }
  }

  test("Static name clash") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           |public class Dummy {
           |  public static void pop() {}
           |  public interface API { void pop(); }
           |  private class Impl implements API { public void pop() {}}
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Type name clash") {
    FileSystemHelper.run(Map(
      "Other.cls" -> "public class Other {public class Inner{ public String a; } }",
      "Dummy.cls" ->
        s"""
           |public class Dummy {
           |  public class Inner{}
           |  public class Other {
           |    void something(Other.Inner x) { x.a = 'Hello';}
           |  }
           |}
           |""".stripMargin
    )) {root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Platform enum equals") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           |public class Dummy {
           |  {DisplayType a,b; Boolean c = a.equals(b);}
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      
      assert(!org.issues.hasMessages)
    }
  }

  test("CreateBy.Id access") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           |public class Dummy {
           |  {SObjectField f = Account.CreatedBy.Id;}
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Static call via an interface") {
    FileSystemHelper.run(Map(
      "Other.cls" -> "public class Other {public static void func() {} public interface MyInterface{ } }",
      "Dummy.cls" ->
        s"""
           |public class Dummy {
           |  {Other.MyInterface.func();}
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Add Double onto Decimal") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Decimal a; Double b; a+=b; } }"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("max(decimal, double) is a Decimal") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Decimal a; Double b; Math.max(a * b, b).round(System.RoundingMode.DOWN); } }"
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("SObject erasure via an interface") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           | public class Dummy implements MyInterface{
           |  public void func(List<Account> a) {}
           |  public interface MyInterface {
           |    void func(List<SObject> a);
           |  }
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Contact AccountId describe") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           | public class Dummy {
           |  public void func(List<Account> a) {Object o = Contact.SObjectType.AccountId.getDescribe();}
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Opportunity SObjectType with shadowing field") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           | public class Dummy {
           |  public static Schema.SObjectType OPP_SOBJECT_TYPE = Opportunity.SObjectType;
           |  public Opportunity opportunity {get; set;}
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Double SObjectType reference") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           | public class Dummy {
           |  public static Object a = Account.SObjectType.SObjectType.newSObject();
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Ternary decimal type") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        s"""
           | public class Dummy {
           |  public static Decimal a =  (true ? 0 : 0.1).setScale(2);
           |}
           |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Schema RowClause") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        """
          | public class Dummy {
          |  public static String a = AccountShare.RowCause.Manual;
          |}
          |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Database RaisesPlatformEvents") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        """
          | public class Dummy implements Database.RaisesPlatformEvents {
          |}
          |""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }
}
