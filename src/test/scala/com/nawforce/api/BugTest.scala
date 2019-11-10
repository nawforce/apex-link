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
}