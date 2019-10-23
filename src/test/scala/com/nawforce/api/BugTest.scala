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
    org.issues.dumpMessages(false)
    assert(!org.issues.hasMessages)
  }
}
