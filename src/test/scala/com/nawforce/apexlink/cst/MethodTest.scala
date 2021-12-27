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

class MethodTest extends AnyFunSuite with TestHelper {

  test("Method call with ghosted type") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" ->
        """{
          |"packageDirectories": [{"path": "force-app"}],
          |"plugins": {"dependencies": [{"namespace": "ext"}]}
          |}""".stripMargin,
      "force-app/Dummy.cls" -> "public class Dummy { {ext.Something a; String.escapeSingleQuotes(a); } }"
    )) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Method call with ambiguous target") {
    typeDeclaration("public class Dummy { {EventBus.publish(null); } }")
    assert(dummyIssues == "Error: line 1 at 22-44: Ambiguous method call for 'publish' on 'System.EventBus' taking arguments 'null'\n")
  }
}
