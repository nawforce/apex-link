package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class CompletionProviderTest extends AnyFunSuite with TestHelper {

  test("Outer class match") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createHappyOrg(root)
      val path = root.join("Dummy.cls")
      assert(
        org.unmanaged
          .getCompletions(path, line = 1, offset = 14, None)
          .isEmpty)
    }
  }

}
