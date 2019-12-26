/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.runtime.types

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.common.api.Org
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathFactory
import org.scalatest.funsuite.AnyFunSuite

class PageTest extends AnyFunSuite {

  test("Valid page") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("TestPage.page"), "".getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {PageReference a = Page.TestPage;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(com.nawforce.runtime.path.Path(fs.getPath("/"))), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Valid page (case insensitive)") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("TestPage.page"), "".getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {PageReference a = Page.tesTPage;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(com.nawforce.runtime.path.Path(fs.getPath("/"))), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Missing page") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("TestPage.page"), "".getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {PageReference a = Page.AnotherPage;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(com.nawforce.runtime.path.Path(fs.getPath("/"))), Seq())
    pkg.deployAll()
    assert(org.issues.getMessages(PathFactory("/work/Dummy.cls")) ==
      "Error: line 1 at 40-56: Unknown field or type 'AnotherPage' on 'Page'\n")
  }

  test("Base package page") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.createDirectory(fs.getPath("pkg1"))
    Files.write(fs.getPath("pkg1/TestPage.page"), "".getBytes())
    Files.createDirectory(fs.getPath("pkg2"))
    Files.write(fs.getPath("pkg2/Dummy.cls"),"public class Dummy { {PageReference a = Page.pkg1__TestPage;} }".getBytes())

    val org = new Org()
    val pkg1 = org.addPackageInternal(Some(Name("pkg1")), Seq(com.nawforce.runtime.path.Path(fs.getPath("/work/pkg1"))), Seq())
    val pkg2 = org.addPackageInternal(Some(Name("pkg2")), Seq(com.nawforce.runtime.path.Path(fs.getPath("/work/pkg2"))), Seq(pkg1))
    pkg2.deployAll()
    assert(!org.issues.hasMessages)
  }
}
