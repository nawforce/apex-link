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

  test("Outer class match") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createHappyOrg(root)
      val path = root.join("Dummy.cls")
      assert(
        org.unmanaged
          .getDefinition(path, line = 1, offset = 14, None)
          .contains(LocationLink(Location(1, 13, 1, 18), path.toString, Location(1, 0, 1, 21), Location(1, 13, 1, 18))))
    }
  }

  test("Outer class match (direct content)") {
    FileSystemHelper.run(Map()) { root: PathLike =>
      val org = createHappyOrg(root)
      val path = root.join("Dummy.cls")
      assert(
        org.unmanaged
          .getDefinition(path, line = 1, offset = 14, Some("public class Dummy {}"))
          .contains(LocationLink(Location(1, 13, 1, 18), path.toString, Location(1, 0, 1, 21), Location(1, 13, 1, 18))))
    }
  }

  test("Outer class match (start)") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createHappyOrg(root)
      val path = root.join("Dummy.cls")
      assert(
        org.unmanaged
          .getDefinition(path, line = 1, offset = 13, None)
          .contains(LocationLink(Location(1, 13, 1, 18), path.toString, Location(1, 0, 1, 21), Location(1, 13, 1, 18))))
    }
  }

  test("Outer class match (end)") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createHappyOrg(root)
      val path = root.join("Dummy.cls")
      assert(
        org.unmanaged
          .getDefinition(path, line = 1, offset = 17, None)
          .contains(LocationLink(Location(1, 13, 1, 18), path.toString, Location(1, 0, 1, 21), Location(1, 13, 1, 18))))
    }
  }

  test("Outer class match (external)") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {/* Foo */}", "Foo.cls" -> "public class Foo {}")) {
      root: PathLike =>
        val org = createHappyOrg(root)
        val path = root.join("Dummy.cls")
        assert(
          org.unmanaged
            .getDefinition(path, line = 1, offset = 23, None)
            .contains(
              LocationLink(Location(1, 23, 1, 26),
                           root.join("Foo.cls").toString,
                           Location(1, 0, 1, 19),
                           Location(1, 13, 1, 16))))
    }
  }

  test("Inner class match") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {class Inner{} }", "Match.txt" -> "Dummy.Inner")) {
      root: PathLike =>
        val org = createHappyOrg(root)
        assert(
          org.unmanaged
            .getDefinition(root.join("Match.txt"), line = 1, offset = 1, None)
            .contains(
              LocationLink(Location(1, 0, 1, 11),
                           root.join("Dummy.cls").toString,
                           Location(1, 20, 1, 33),
                           Location(1, 26, 1, 31))))
    }
  }

  test("Inner class match (relative external)") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy /* Inner */ {class Inner{} }")) { root: PathLike =>
      val org = createHappyOrg(root)
      assert(
        org.unmanaged
          .getDefinition(root.join("Dummy.cls"), line = 1, offset = 22, None)
          .contains(
            LocationLink(Location(1, 22, 1, 27),
                         root.join("Dummy.cls").toString,
                         Location(1, 32, 1, 45),
                         Location(1, 38, 1, 43))))
    }
  }

  test("Inner class match (relative within)") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {class Inner{/* Inner */} }")) { root: PathLike =>
      val org = createHappyOrg(root)
      assert(
        org.unmanaged
          .getDefinition(root.join("Dummy.cls"), line = 1, offset = 35, None)
          .contains(
            LocationLink(Location(1, 35, 1, 40),
                         root.join("Dummy.cls").toString,
                         Location(1, 20, 1, 44),
                         Location(1, 26, 1, 31))))
    }
  }

  test("Inner class match (relative inner)") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {class Inner{/* Other */} class Other {}}")) {
      root: PathLike =>
        val org = createHappyOrg(root)
        assert(
          org.unmanaged
            .getDefinition(root.join("Dummy.cls"), line = 1, offset = 35, None)
            .contains(
              LocationLink(Location(1, 35, 1, 40),
                           root.join("Dummy.cls").toString,
                           Location(1, 45, 1, 59),
                           Location(1, 51, 1, 56))))
    }
  }

  test("Inner class match (external)") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {/* Foo.Other */}", "Foo.cls" -> "public class Foo {class Other {}}")) {
      root: PathLike =>
        val org = createHappyOrg(root)
        assert(
          org.unmanaged
            .getDefinition(root.join("Dummy.cls"), line = 1, offset = 23, None)
            .contains(
              LocationLink(Location(1, 23, 1, 32),
                           root.join("Foo.cls").toString,
                           Location(1, 18, 1, 32),
                           Location(1, 24, 1, 29))))
    }
  }

  test("Outer class with syntax error") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {")) { root: PathLike =>
      createOrg(root)
      val path = root.join("Dummy.cls")
      withOrg { org =>
        assert(org.unmanaged
          .getDefinition(path, line = 1, offset = 14, None)
          .contains(LocationLink(Location(1, 13, 1, 18), path.toString, Location(1, 0, 1, 20), Location(1, 13, 1, 18))))
      }
    }
  }

  test("Inner class with syntax error") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {/*Inner*/ class Inner{ /* }}")) {
      root: PathLike =>
        createOrg(root)
        withOrg(org => {
          assert(
            org.unmanaged
              .getDefinition(root.join("Dummy.cls"), line = 1, offset = 22, None)
              .contains(
                LocationLink(Location(1, 22, 1, 27),
                             root.join("Dummy.cls").toString,
                             Location(1, 30, 1, 45),
                             Location(1, 36, 1, 41))))
        })
    }
  }
}
