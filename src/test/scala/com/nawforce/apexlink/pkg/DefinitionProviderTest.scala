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

import com.nawforce.apexlink.rpc.LocationLink
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class DefinitionProviderTest extends AnyFunSuite with TestHelper {

  test("Outer class text match") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createHappyOrg(root)
      val path = root.join("Dummy.cls")
      assert(
        org.unmanaged
          .getDefinition(path, line = 1, offset = 14)
          .contains(LocationLink(Location(1, 13, 1, 18), path.toString, Location(1, 0, 1, 21), Location(1, 13, 1, 18))))
    }
  }

  test("Inner class text match") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {class Inner{} }", "Match.txt" -> "Dummy.Inner")) {
      root: PathLike =>
        val org = createHappyOrg(root)
        assert(
          org.unmanaged
            .getDefinition(root.join("Match.txt"), line = 1, offset = 1)
            .contains(
              LocationLink(Location(1, 0, 1, 11),
                           root.join("Dummy.cls").toString,
                           Location(1, 20, 1, 33),
                           Location(1, 26, 1, 31))))
    }
  }

}
