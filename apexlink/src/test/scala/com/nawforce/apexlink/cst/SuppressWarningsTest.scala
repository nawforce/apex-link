/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class SuppressWarningsTest extends AnyFunSuite with TestHelper {

  test("Suppress disabled") {
    typeDeclaration("public class Dummy {class Inner {Integer b; List<Inner> a; {b = a[null].b;}}}")
    assert(dummyIssues == "Error: line 1 at 66-70: Array indexes must be Integers, found 'null'\n")
  }

  test("Outer Suppress") {
    typeDeclaration(
      "@SuppressWarnings public class Dummy {class Inner {Integer b; List<Inner> a; {Integer b = a[null].b;}}}"
    )
    assert(hasIssues)
  }

  test("Outer Suppress (PMD)") {
    typeDeclaration(
      "@SuppressWarnings('PMD') public class Dummy {class Inner {Integer b; List<Inner> a; {Integer b = a[null].b;}}}"
    )
    assert(!hasIssues)
  }

  test("Inner Suppress") {
    typeDeclaration(
      "public class Dummy {@SuppressWarnings class Inner {Integer b; List<Inner> a; {Integer b = a[null].b;}}}"
    )
    assert(hasIssues)
  }

  test("Inner Suppress (PMD)") {
    typeDeclaration(
      "public class Dummy {@SuppressWarnings('PMD') class Inner {Integer b; List<Inner> a; {Integer b = a[null].b;}}}"
    )
    assert(!hasIssues)
  }

  test("Method Suppress") {
    typeDeclaration(
      "public class Dummy {class Inner {Integer b; List<Inner> a; @SuppressWarnings void foo(){ Integer b = a[null].b;}}}"
    )
    assert(hasIssues)
  }

  test("Method Suppress (PMD)") {
    typeDeclaration(
      "public class Dummy {class Inner {Integer b; List<Inner> a; @SuppressWarnings('PMD') void foo(){ Integer b = a[null].b;}}}"
    )
    assert(!hasIssues)
  }

  test("Field Suppress") {
    typeDeclaration(
      "public class Dummy {class Inner {Integer b; List<Inner> a; @SuppressWarnings Integer b = a[null].b;}}"
    )
    assert(hasIssues)
  }

  test("Field Suppress (PMD)") {
    typeDeclaration(
      "public class Dummy {class Inner {Integer b; List<Inner> a; @SuppressWarnings('PMD') Integer b = a[null].b;}}"
    )
    assert(!hasIssues)
  }

  test("Unused method suppress") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "@SuppressWarnings('Unused') public class Dummy {void foo() {}}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused method suppress (on method)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@SuppressWarnings('Unused') void foo() {}}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused field suppress") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "@SuppressWarnings('Unused') public class Dummy {String foo;}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused field suppress (on field)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@SuppressWarnings('Unused') String foo;}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused inner class suppress") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@SuppressWarnings('Unused') class Inner {} }",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused outer class suppress") {
    FileSystemHelper.run(Map("Dummy.cls" -> "@SuppressWarnings('Unused') public class Dummy {}")) {
      root: PathLike =>
        createOrg(root)
        withOrg(org => {
          assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
        })
    }
  }

  test("Unused method suppress PMD") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "@SuppressWarnings('PMD') public class Dummy {void foo() {}}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused method suppress (on method) PMD") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@SuppressWarnings('PMD') void foo() {}}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused field suppress PMD") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "@SuppressWarnings('PMD') public class Dummy {String foo;}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused field suppress (on field) PMD") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@SuppressWarnings('PMD') String foo;}",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused inner class suppress PMD") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@SuppressWarnings('PMD') class Inner {} }",
        "Foo.cls"   -> "public class Foo { {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      withOrg(org => {
        assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
      })
    }
  }

  test("Unused outer class suppress PMD") {
    FileSystemHelper.run(Map("Dummy.cls" -> "@SuppressWarnings('PMD') public class Dummy {}")) {
      root: PathLike =>
        createOrg(root)
        withOrg(org => {
          assert(org.issueManager.issuesForFile(root.join("Dummy.cls").toString).isEmpty)
        })
    }
  }

}
