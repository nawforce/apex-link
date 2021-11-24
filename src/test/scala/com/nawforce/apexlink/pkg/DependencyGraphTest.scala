package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.rpc.{DependencyLink, DependencyNode}
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class DependencyGraphTest extends AnyFunSuite with TestHelper {

  test("Correct dependencies returned at depth 1") {
    FileSystemHelper.run(
      Map(
        "A.cls" -> "public class A extends B { C c;}",
        "B.cls" -> "public class B {C c;}",
        "C.cls" -> "public class C {D d;}",
        "D.cls" -> "public class D {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val result = org.getDependencyGraph(
        Array(TypeIdentifier(None, TypeName(Name("A")))),
        1,
        apexOnly = true,
        Array()
      )
      assert(
        result.nodeData sameElements Array(
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("A"))),
            32,
            "class",
            3,
            Array(TypeIdentifier(None, TypeName(Name("B")))),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("C"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("B"))),
            21,
            "class",
            2,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("C"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("C"))),
            21,
            "class",
            1,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("D"))))
          )
        )
      )
      assert(
        result.linkData sameElements Array(
          DependencyLink(0, 1, "extends"),
          DependencyLink(0, 2, "uses"),
          DependencyLink(1, 2, "uses")
        )
      )
    }
  }

  test("Correct dependencies returned at depth 2") {
    FileSystemHelper.run(
      Map(
        "A.cls" -> "public class A extends B { C c;}",
        "B.cls" -> "public class B {C c;}",
        "C.cls" -> "public class C {D d;}",
        "D.cls" -> "public class D {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val result = org.getDependencyGraph(
        Array(TypeIdentifier(None, TypeName(Name("A")))),
        2,
        apexOnly = true,
        Array()
      )
      assert(
        result.nodeData sameElements Array(
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("A"))),
            32,
            "class",
            3,
            Array(TypeIdentifier(None, TypeName(Name("B")))),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("C"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("B"))),
            21,
            "class",
            2,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("C"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("C"))),
            21,
            "class",
            1,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("D"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("D"))),
            17,
            "class",
            0,
            Array(),
            Array(),
            Array()
          )
        )
      )
      assert(
        result.linkData sameElements Array(
          DependencyLink(0, 1, "extends"),
          DependencyLink(0, 2, "uses"),
          DependencyLink(1, 2, "uses"),
          DependencyLink(2, 3, "uses")
        )
      )
    }
  }

  test("Correct dependencies returned at depth 2 with ignored") {
    FileSystemHelper.run(
      Map(
        "A.cls" -> "public class A extends B { C c;}",
        "B.cls" -> "public class B {C c;}",
        "C.cls" -> "public class C {D d;}",
        "D.cls" -> "public class D {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val result = org.getDependencyGraph(
        Array(TypeIdentifier(None, TypeName(Name("A")))),
        2,
        apexOnly = true,
        Array(TypeIdentifier(None, TypeName(Name("B"))))
      )
      assert(
        result.nodeData sameElements Array(
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("A"))),
            32,
            "class",
            2,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("C"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("C"))),
            21,
            "class",
            1,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("D"))))
          ),
          DependencyNode(
            TypeIdentifier(None, TypeName(Name("D"))),
            17,
            "class",
            0,
            Array(),
            Array(),
            Array()
          )
        )
      )
      assert(
        result.linkData sameElements Array(
          DependencyLink(0, 1, "uses"),
          DependencyLink(1, 2, "uses")
        )
      )
    }
  }
}
