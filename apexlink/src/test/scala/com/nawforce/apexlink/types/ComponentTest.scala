/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class ComponentTest extends AnyFunSuite with TestHelper {

  test("Missing root element") {
    FileSystemHelper.run(Map("Test.component" -> "")) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Test.component")) ==
          "Syntax: line 1: mismatched input '<EOF>' expecting {COMMENT, PI_START, '<', '<script', WS_NL}\n"
      )
    }
  }

  test("Bad root element") {
    FileSystemHelper.run(Map("Test.component" -> "<foo/>")) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Test.component")) ==
          "Error: line 1 at 0-11: Root element must be 'apex:component'\n"
      )
    }
  }

  test("Custom component (MDAPI)") {
    FileSystemHelper.run(
      Map(
        "Test.component" -> "<apex:component/>",
        "Dummy.cls"      -> "public class Dummy { {Component.Test;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Missing component") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy { {Component.Test;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(
          getMessages(Path("/Dummy.cls")) ==
            "Missing: line 1 at 22-36: Unknown field or type 'Test' on 'Component'\n"
        )
    }
  }

  test("Create component") {
    FileSystemHelper.run(
      Map(
        "Test.component" -> "<apex:component/>",
        "Dummy.cls"      -> "public class Dummy { {Component.Test c = new Component.Test();} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Create component (c namespace)") {
    FileSystemHelper.run(
      Map(
        "Test.component" -> "<apex:component/>",
        "Dummy.cls"      -> "public class Dummy { {Component.Test c = new Component.c.Test();} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Create component (namespaced)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Test.component" -> "<apex:component/>",
        "pkg/Dummy.cls"      -> "public class Dummy { {Component.Test c = new Component.pkg.Test();} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Create component (namespaced but without namespace)") {
    FileSystemHelper.run(
      Map(
        "Test.component" -> "<apex:component/>",
        "Dummy.cls"      -> "public class Dummy { {Component.Test c = new Component.Test();} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Create component (ghosted)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Component.ghosted.Test c = new Component.ghosted.Test();} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Create component (packaged)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/Test.component" -> "<apex:component/>",
        "pkg2/Dummy.cls"      -> "public class Dummy { {Component.pkg1.Test c = new Component.pkg1.Test();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(!hasIssues)

      val pkg1           = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2           = org.packagesByNamespace(Some(Name("pkg2")))
      val componentType1 = TypeIdentifier.fromJava(Name("pkg1"), TypeNames.Component)
      val componentType2 = TypeIdentifier.fromJava(Name("pkg2"), TypeNames.Component)
      assert(
        pkg2
          .getDependencies(componentType2, outerInheritanceOnly = false, apexOnly = false)
          .sameElements(Array(componentType1))
      )
      assert(
        pkg1
          .getDependencyHolders(componentType1, apexOnly = false)
          .sameElements(Array(componentType2))
      )

      val dummyType =
        pkg2.getTypeOfPathInternal(root.join("pkg2").join("Dummy.cls")).get.asTypeIdentifier
      assert(
        pkg2
          .getDependencies(dummyType, outerInheritanceOnly = false, apexOnly = false)
          .sameElements(Array(componentType2))
      )
      assert(
        pkg2.getDependencyHolders(componentType2, apexOnly = false).sameElements(Array(dummyType))
      )
    }
  }

  test("Single attribute") {
    FileSystemHelper.run(
      Map(
        "Test.component" ->
          """<apex:component>
          |  <apex:attribute name="test" type="String"/>
          |</apex:component>
          |""".stripMargin,
        "Dummy.cls" ->
          """public class Dummy {
          |  {
          |    Component.Test c = new Component.Test();
          |    c.test = 'Hello';
          |  }
          |}
          |""".stripMargin
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Multiple attributes") {
    FileSystemHelper.run(
      Map(
        "Test.component" ->
          """<apex:component>
            |  <apex:attribute name="test" type="String"/>
            |  <apex:attribute name="test2" type="String"/>|
            |</apex:component>
            |""".stripMargin,
        "Dummy.cls" ->
          """public class Dummy {
            |  {
            |    Component.Test c = new Component.Test();
            |    c.test2 = c.test;
            |  }
            |}
            |""".stripMargin
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("Missing controller") {
    FileSystemHelper.run(Map("Test.component" -> "<apex:component controller='Dummy'/>")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(
          getMessages(root.join("Test.component")) ==
            "Missing: line 1 at 16-34: No type declaration found for 'Dummy'\n"
        )
    }
  }

  test("Valid controller") {
    FileSystemHelper.run(
      Map(
        "Test.component" -> "<apex:component controller='Controller'/>",
        "Controller.cls" -> "public class Controller {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      val testComponentTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Test.component")).get.asTypeIdentifier
      assert(testComponentTypeId.toString == "Component.Test")
      assert(
        org.unmanaged.getPathsOfType(testComponentTypeId).sameElements(Array("/Test.component"))
      )

      val componentTypeId = TypeIdentifier(None, TypeName(Name("Component")))
      val controllerTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Controller.cls")).get.asTypeIdentifier

      assert(
        org.unmanaged
          .getDependencies(
            componentTypeId,
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
          .sameElements(Array(componentTypeId))
      )
    }
  }
}
