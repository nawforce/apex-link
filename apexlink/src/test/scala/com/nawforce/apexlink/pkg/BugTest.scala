/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class BugTest extends AnyFunSuite with TestHelper {

  test("Enum scoping") {
    FileSystemHelper.run(
      Map(
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
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Bad generics") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy {{ DescribeFieldResult a; List<Schema.PicklistEntry> b = a.getPicklistValues();}}"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Bad generics (without generic)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {{ DescribeFieldResult a; Integer b = a.getByteLength();}}"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Duplicate SObject/Type name resolves as Type") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy {{String b = Site.getName();}}"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Duplicate SObject/Type name resolves as SObject") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {{Site a; SObjectType b = a.getSObjectType();}}")
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("SObject static use of getSobjectType") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {{SObjectType b = Account.getSobjectType();}}")
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Clone apex type") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{Dummy a,b; b = a.clone();}}")) {
      root: PathLike =>
        createHappyOrg(root)
    }
  }

  test("System type name clash with field") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {String Matcher; {Matcher.capitalize();}}")
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Static method of super class of outer") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls"      -> "public class Dummy extends SuperClass {class Inner {public void func(){ func(); } }}",
        "SuperClass.cls" -> "public virtual class SuperClass {public static void func() {}}"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Static name clash") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           |public class Dummy {
           |  public static void pop() {}
           |  public interface API { void pop(); }
           |  private class Impl implements API { public void pop() {}}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Type name clash") {
    FileSystemHelper.run(
      Map(
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
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Platform enum equals") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           |public class Dummy {
           |  {DisplayType a,b; Boolean c = a.equals(b);}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("CreateBy.Id access") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           |public class Dummy {
           |  {SObjectField f = Account.CreatedBy.Id;}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Static call via an interface") {
    FileSystemHelper.run(
      Map(
        "Other.cls" -> "public class Other {public static void func() {} public interface MyInterface{ } }",
        "Dummy.cls" ->
          s"""
           |public class Dummy {
           |  {Other.MyInterface.func();}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Add Double onto Decimal") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { {Decimal a; Double b; a+=b; } }")
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("max(decimal, double) is a Decimal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {Decimal a; Double b; Math.max(a * b, b).round(System.RoundingMode.DOWN); } }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("SObject erasure via an interface") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           | public class Dummy implements MyInterface{
           |  public void func(List<Account> a) {}
           |  public interface MyInterface {
           |    void func(List<SObject> a);
           |  }
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Contact AccountId describe") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           | public class Dummy {
           |  public void func(List<Account> a) {Object o = Contact.SObjectType.AccountId.getDescribe();}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Opportunity SObjectType with shadowing field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           | public class Dummy {
           |  public static Schema.SObjectType OPP_SOBJECT_TYPE = Opportunity.SObjectType;
           |  public Opportunity opportunity {get; set;}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Double SObjectType reference") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           | public class Dummy {
           |  public static Object a = Account.SObjectType.SObjectType.newSObject();
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Ternary decimal type") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           | public class Dummy {
           |  public static Decimal a =  (true ? 0 : 0.1).setScale(2);
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Database RaisesPlatformEvents") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          """
          | public class Dummy implements Database.RaisesPlatformEvents {
          |}
          |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("@testSetup is entry") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           |public class Dummy {
           |@testSetup
           |static void setup() {
           |  Account a = new Account(Name='foo');
           |  insert a;
           |}
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Interface missing formal argument") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public interface Dummy {void foo(Bar a);}")) {
      root: PathLike =>
        createOrg(root)
        assert(
          getMessages() == "/Dummy.cls: Missing: line 1 at 37-38: No type declaration found for 'Bar'\n"
        )
    }
  }

  test("Interface equals") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls"       -> "public class Dummy {{MyInterface a; Boolean b = a.equals(a);}} ",
        "MyInterface.cls" -> "public interface MyInterface {void foo(String a);}"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Outer static field") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {public final static String a;} ")
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Quiddity") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""
           |public class Dummy {
           |  public static Quiddity demonstrateGetQuiddity() {
           |    return Request.getCurrent().getQuiddity();
           |  }
           |}
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Generic assignment") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          s"""public class Dummy { { Iterable<Id> a; a = new List<Id>(); } }
           |""".stripMargin
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

}
