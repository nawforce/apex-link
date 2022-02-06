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
package com.nawforce.apexlink.types

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class PageTest extends AnyFunSuite with TestHelper {

  test("Valid page") {
    FileSystemHelper.run(
      Map(
        "TestPage.page" -> "<apex:page/>",
        "Dummy.cls"     -> "public class Dummy { {PageReference a = Page.TestPage;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Valid page (case insensitive)") {
    FileSystemHelper.run(
      Map(
        "TestPage.page" -> "<apex:page/>",
        "Dummy.cls"     -> "public class Dummy { {PageReference a = Page.tesTPage;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Missing page") {
    FileSystemHelper.run(
      Map(
        "TestPage.page" -> "",
        "Dummy.cls"     -> "public class Dummy { {PageReference a = Page.AnotherPage;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 40-56: Unknown field or type 'AnotherPage' on 'Page'\n"
      )
    }
  }

  test("Base package page") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/TestPage.page" -> "<apex:page/>",
        "pkg2/Dummy.cls"     -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Ghost package page") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1"}]}
          |}""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Missing controller") {
    FileSystemHelper.run(Map("Test.page" -> "<apex:page controller='Dummy'/>")) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Test.page")) ==
          "Missing: line 1 at 11-29: No type declaration found for 'Dummy'\n"
      )
    }
  }

  test("Missing extension") {
    FileSystemHelper.run(
      Map(
        "Test.page" -> "<apex:page controller='Dummy' extensions='Extension'/>",
        "Dummy.cls" -> "public class Dummy {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Test.page")) ==
          "Missing: line 1 at 30-52: No type declaration found for 'Extension'\n"
      )
    }
  }

  test("Valid controller") {
    FileSystemHelper.run(
      Map(
        "Test.page"      -> "<apex:page controller='Controller'/>",
        "Controller.cls" -> "public class Controller {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      val testPageTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Test.page")).get.asTypeIdentifier
      assert(testPageTypeId.toString == "Page.Test")
      assert(org.unmanaged.getPathsOfType(testPageTypeId).sameElements(Array("/Test.page")))

      val pageTypeId = TypeIdentifier(None, TypeName(Name("Page")))
      val controllerTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Controller.cls")).get.asTypeIdentifier

      assert(
        org.unmanaged
          .getDependencies(
            pageTypeId,
            outerInheritanceOnly = false,
            apexOnly = false
          ) sameElements Array(controllerTypeId)
      )

      assert(
        org.unmanaged
          .getDependencies(controllerTypeId, outerInheritanceOnly = false, apexOnly = false)
          .isEmpty
      )
      assert(
        org.unmanaged
          .getDependencyHolders(controllerTypeId, apexOnly = false)
          .sameElements(Array(pageTypeId))
      )
    }
  }

  test("Valid controller & extension") {
    FileSystemHelper.run(
      Map(
        "Test.page"      -> "<apex:page controller='Controller' extensions='Extension'/>",
        "Controller.cls" -> "public class Controller {}",
        "Extension.cls"  -> "public class Extension {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      val testPageTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Test.page")).get.asTypeIdentifier
      assert(testPageTypeId.toString == "Page.Test")
      assert(org.unmanaged.getPathsOfType(testPageTypeId).sameElements(Array("/Test.page")))

      val pageTypeId = TypeIdentifier(None, TypeName(Name("Page")))
      val controllerTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Controller.cls")).get.asTypeIdentifier
      val extensionTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Extension.cls")).get.asTypeIdentifier

      assert(
        org.unmanaged
          .getDependencies(
            pageTypeId,
            outerInheritanceOnly = false,
            apexOnly = false
          ) sameElements Array(extensionTypeId, controllerTypeId)
      )

      assert(
        org.unmanaged
          .getDependencies(controllerTypeId, outerInheritanceOnly = false, apexOnly = false)
          .isEmpty
      )
      assert(
        org.unmanaged
          .getDependencies(extensionTypeId, outerInheritanceOnly = false, apexOnly = false)
          .isEmpty
      )

      assert(
        org.unmanaged
          .getDependencyHolders(controllerTypeId, apexOnly = false)
          .sameElements(Array(pageTypeId))
      )
      assert(
        org.unmanaged
          .getDependencyHolders(extensionTypeId, apexOnly = false)
          .sameElements(Array(pageTypeId))
      )
    }
  }

}
