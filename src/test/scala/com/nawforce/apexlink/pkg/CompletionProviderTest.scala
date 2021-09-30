package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class CompletionProviderTest extends AnyFunSuite with TestHelper {

  test("Method Completions") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {public String methodA(){} public String methodB(String a, String b){} }")) {
      root: PathLike =>
        val org = createOrg(root)
        val path = root.join("Completion.cls")
        val content = "public class Completion { public Completion() {String a = new Dummy().me"
        assert(
          org
            .getCompletionItems(path.toString, line = 1, offset = content.length, content)
            .sameElements(
              Array(CompletionItemLink("methodB(a, b)", "Method"), CompletionItemLink("methodA()", "Method"))))
    }
  }

  test("Instance Field completions") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { public static String FieldS = ''; public string fieldA = '';}")) {
      root: PathLike =>
        val org = createOrg(root)
        val path = root.join("Completion.cls")
        val content = "public class Completion { public Completion() {String a = new Dummy().fi"
        assert(
          org
            .getCompletionItems(path.toString, line = 1, offset = content.length, content)
            .sameElements(Array(CompletionItemLink("fieldA", "Field"))))
    }
  }

  test("Static Field completions") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { public static String fieldS = ''; public string fieldA = '';}")) {
      root: PathLike =>
        val org = createOrg(root)
        val path = root.join("Completion.cls")
        val content = "public class Completion { public Completion() {String a = Dummy.fi"
        assert(
          org
            .getCompletionItems(path.toString, line = 1, offset = content.length, content)
            .sameElements(Array(CompletionItemLink("fieldS", "Field") )))
    }
  }

  test("Inner class completion") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {class Inner{} }")) { root: PathLike =>
      val org = createOrg(root)
      val path = root.join("Completion.cls")
      val content = "public class Completion { public Completion() {Dummy.Inn"
      assert(
        org
          .getCompletionItems(path.toString, line = 1, offset = content.length, content)
          .sameElements(Array(CompletionItemLink("Inner", "TypeParameter"))))
    }
  }

  /* TODO: fix this for expressions
  test("Instance method from static") {

    FileSystemHelper.run(
      Map("Foo.cls" -> "public class Foo { public String method(){}}",
          "Baz.cls" -> "public class Baz { public static Foo bar = new Foo();}")) { root: PathLike =>
      val org = createOrg(root)
      val path = root.join("Dummy.cls")
      val content = "public class Dummy { public String method(){Baz.bar.me"
      assert(
        org
          .getCompletionItems(path.toString, line = 1, offset = content.length, content)
          .sameElements(Array(CompletionItemLink("method()", "Method"))))
    }
  }*/

}
