package com.nawforce.apexlink.types

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class ExtendedTest extends AnyFunSuite with TestHelper {

  test("No type arguments on standard class") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy<T> { }")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Error: line 1 at 19-20: Class type arguments can only by used by 'Extended' Apex classes\n")
    }
  }

  test("Mismatched file and class names") {
    FileSystemHelper.run(
      Map("Foo.xcls" -> "public class Dummy<T> { }")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages("/Foo.xcls") ==
        "Error: line 1 at 13-18: Type name 'Dummy' does not match file name 'Foo.xcls'\n")
    }
  }

  test("Mismatched file and class names (case insensitive)") {
    FileSystemHelper.run(
      Map("dummy.xcls" -> "public class DuMmy<T> { }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Type arguments allowed on extended class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<T> { }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Multiple arguments allowed on extended class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<T, S, R> { }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Duplicate arguments on extended class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<T, S, t> { }")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages("/Dummy.xcls") ==
        "Error: line 1 at 25-26: Duplicate type argument for 'T'\n")
    }
  }

  test("Extended class reference in Apex class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy { }",
        "Foo.cls" -> "public class Foo { {Dummy a;} }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Extended class reference in Apex class (xcls layer)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"packageDirectories\":[{\"path\":\"pkg\"}],\"plugins\":{\"xcls\":{\"path\":\"xcls\"}}}",
        "xcls/Dummy.xcls" -> "public class Dummy { }",
        "pkg/Foo.cls" -> "public class Foo { {Dummy a;} }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Generic Extended class reference in Apex class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X> { }",
        "Foo.cls" -> "public class Foo { {Dummy_String a;} }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Generic Extended class bad reference in Apex class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X> { }",
        "Foo.cls" -> "public class Foo { {Dummy_Bar a;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages("/Foo.cls") ==
        "Missing: line 1 at 30-31: No type declaration found for 'Dummy_Bar'\n")
    }
  }

  test("Generic Extended class nested reference in Apex class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X> { }",
        "Foo.cls" -> "public class Foo { {Dummy_Dummy_String a;} }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Generic Extended class dual reference in Apex class") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X> { }",
        "Foo.cls" -> "public class Foo { {Dummy_Dummy_String a;} }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Generic Extended class without type argument") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X> { }",
        "Foo.cls" -> "public class Foo { {Dummy a;} }")) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Generic Extended class too many type arguments") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X> { }",
        "Foo.cls" -> "public class Foo { {Dummy_String_String a;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages("/Foo.cls") ==
        "Missing: line 1 at 40-41: No type declaration found for 'Dummy_String_String'\n")
    }
  }

  test("Generic Extended class missing type argument") {
    FileSystemHelper.run(
      Map("Dummy.xcls" -> "public class Dummy<X, Y> { }",
        "Foo.cls" -> "public class Foo { {Dummy_String a;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages("/Foo.cls") ==
        "Missing: line 1 at 33-34: No type declaration found for 'Dummy_String'\n")
    }
  }
}
