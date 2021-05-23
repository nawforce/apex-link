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
package com.nawforce.apexlink.parsers

import com.nawforce.apexlink.FileSystemHelper
import com.nawforce.apexlink.api.{Org, ServerOps}
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class CodeParserTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Good class") {
    val parser = CodeParser(PathFactory("Hello.cls"), SourceData("public class Hello {}"))
    parser.parseClass() match {
      case Left(_)   => assert(false)
      case Right(cu) => assert(cu != null)
    }
  }

  test("Broken class") {
    val parser = CodeParser(PathFactory("Hello.cls"), SourceData("public class Hello {"))
    parser.parseClass() match {
      case Left(_)  => ()
      case Right(_) => assert(false)
    }
  }

  test("UTF-8 class") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{String a = 'Kimi Räikkönen';}}")) {
      root: PathLike =>
        val org = Org.newOrg(root).asInstanceOf[OrgImpl]
        assert(!org.issues.hasMessages)
    }
  }
}
