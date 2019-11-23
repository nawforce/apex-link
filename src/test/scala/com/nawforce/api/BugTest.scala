package com.nawforce.api

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import org.scalatest.FunSuite

class BugTest extends FunSuite {

  test("Enum scoping") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Wrapper.cls"), "public virtual class Wrapper {public enum MyEnum {A, B, C}}".getBytes())
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         |public class Dummy {
         |  private enum MyEnum {
         |    D
         |  }
         |  private class Inner extends Wrapper {
         |    Object e = MyEnum.D;
         |  }
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Bad generics") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {{ DescribeFieldResult a; List<Schema.PicklistEntry> b = a.getPicklistValues();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Bad generics (without generic)") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {{ DescribeFieldResult a; Integer b = a.getByteLength();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Duplicate SObject/Type name resolves as Type") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {{String b = Site.getName();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Duplicate SObject/Type name resolves as SObject") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {{Site a; SObjectType b = a.getSObjectType();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("SObject static use of getSobjectType") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {{SObjectType b = Account.getSobjectType();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Clone apex type") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {{Dummy a,b; b = a.clone();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("System type name clash with field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy {String Matcher; {Matcher.capitalize();}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Static method of super class of outer") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy extends SuperClass {class Inner {public void ifunc(){ func(); } }}".getBytes())
    Files.write(fs.getPath("Super.cls"),
      "public virtual class SuperClass {public static void func() {}}".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    org.issues.dumpMessages(false)
    assert(!org.issues.hasMessages)
  }

  test("Static name clash") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         |public class Dummy {
         |  public static void pop() {}
         |  public interface API { void pop(); }
         |  private class Impl implements API { public void pop() {}}
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Type name clash") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Other.cls"),
      "public class Other {public class Inner{ public String a; } }".getBytes())
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         |public class Dummy {
         |  public class Inner{}
         |  public class Other {
         |    void something(Other.Inner x) { x.a = 'Hello';}
         |  }
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Platform enum equals") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         |public class Dummy {
         |  {DisplayType a,b; Boolean c = a.equals(b);}
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("CreateBy.Id access") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         |public class Dummy {
         |  {SObjectField f = Account.CreatedBy.Id;}
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Static call via an interface") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Other.cls"),
      "public class Other {public static void func() {} public interface MyInterface{ } }".getBytes())
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         |public class Dummy {
         |  {Other.MyInterface.func();}
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Add Double onto Decimal") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"), "public class Dummy { {Decimal a; Double b; a+=b; } }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("max(decimal, double) is a Decimal") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      "public class Dummy { {Decimal a; Double b; Math.max(a * b, b).round(System.RoundingMode.DOWN); } }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("SObject erasure via an interface") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         | public class Dummy implements MyInterface{
         |  public void func(List<Account> a) {}
         |  public interface MyInterface {
         |    void func(List<SObject> a);
         |  }
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Contact AccountId describe") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         | public class Dummy {
         |  public void func(List<Account> a) {Object o = Contact.SObjectType.AccountId.getDescribe();}
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Opportunity SObjectType with shadowing field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         | public class Dummy {
         |  public static Schema.SObjectType OPP_SOBJECT_TYPE = Opportunity.SObjectType;
         |  public Opportunity opportunity {get; set;}
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Double SObjectType reference") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         | public class Dummy {
         |  public static Object a = Account.SObjectType.SObjectType.newSObject();
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Ternary decimal type") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),
      s"""
         | public class Dummy {
         |  public static Decimal a =  (true ? 0 : 0.1).setScale(2);
         |}
         |""".stripMargin.getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    org.issues.dumpMessages(false)
    assert(!org.issues.hasMessages)
  }


}
