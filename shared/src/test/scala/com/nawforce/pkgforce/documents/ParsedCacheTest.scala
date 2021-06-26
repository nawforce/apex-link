/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

package com.nawforce.pkgforce.documents

import com.nawforce.runtime.platform.Environment
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class ParsedCacheTest extends AnyFunSuite with BeforeAndAfter {

  private val emptyPackageContext = PackageContext(None, Array(), Array())

  before {
    ParsedCache.clear()
  }

  after {
    ParsedCache.clear()
  }

  test("homedir is available") {
    assert(Environment.homedir.nonEmpty)
  }

  test("variable can be set") {
    assert(Environment.setVariable("TEST", "value"))
    assert(Environment.variable("TEST").contains("value"))
  }

  test("default uses homedir") {
    val cache = ParsedCache.create(1)
    assert(cache.isRight)
    assert(
      cache.getOrElse(throw new NoSuchElementException()).path == Environment.homedir.get
        .join(ParsedCache.CACHE_DIR))
  }

  test("custom path used") {
    try {
      val testPath = Environment.homedir.get.join(".apexlink_cache_test")
      assert(Environment.setVariable("APEXLINK_CACHE_DIR", testPath.toString))
      ParsedCache.clear()

      val cache = ParsedCache.create(1)
      assert(cache.isRight)
      assert(cache.getOrElse(throw new NoSuchElementException()).path == testPath)
      assert(testPath.delete().isEmpty)
    } finally {
      Environment.setVariable("APEXLINK_CACHE_DIR", null)
    }
  }

  test("empty key insert/recover") {
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(emptyPackageContext, "", Array(), "Hello".getBytes())
    assert(cache.get(emptyPackageContext, "", Array()).get.sameElements("Hello".getBytes()))
    assert(cache.get(emptyPackageContext, "Foo", Array()).isEmpty)
    assert(cache.get(emptyPackageContext, "", "Foo".getBytes).isEmpty)
  }

  test("key insert/recover on name") {
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(emptyPackageContext, "Foo", Array(), "Hello".getBytes())
    assert(cache.get(emptyPackageContext, "", Array()).isEmpty)
    assert(cache.get(emptyPackageContext, "Foo", Array()).get.sameElements("Hello".getBytes()))
  }

  test("key insert/recover on content") {
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(emptyPackageContext, "", "Foo".getBytes(), "Hello".getBytes())
    assert(cache.get(emptyPackageContext, "", Array()).isEmpty)
    assert(
      cache.get(emptyPackageContext, "", "Foo".getBytes()).get.sameElements("Hello".getBytes()))
  }

  test("overwrite entry") {
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(emptyPackageContext, "Foo", Array(), "Hello".getBytes())
    assert(cache.get(emptyPackageContext, "Foo", Array()).get.sameElements("Hello".getBytes()))
    cache.upsert(emptyPackageContext, "Foo", Array(), "Goodbye".getBytes())
    assert(cache.get(emptyPackageContext, "Foo", Array()).get.sameElements("Goodbye".getBytes()))
  }

  test("key insert/recover wrong packageContext") {
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(emptyPackageContext, "Foo", Array(), "Hello".getBytes())
    assert(cache.get(PackageContext(Some(""), Array(), Array()), "Foo", Array()).isEmpty)
    assert(cache.get(PackageContext(Some("Foo"), Array(), Array()), "Foo", Array()).isEmpty)
  }

  test("key insert/recover with namespaced packageContext") {
    val packageContext = PackageContext(Some("test"), Array(), Array())
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(packageContext, "Foo", Array(), "Hello".getBytes())
    assert(cache.get(packageContext, "", Array()).isEmpty)
    assert(cache.get(packageContext, "Foo", Array()).get.sameElements("Hello".getBytes()))
  }

  test("key insert/recover with bad packageContext") {
    val packageContext =
      PackageContext(Some("test"), Array("ghosted1", "ghosted2"), Array("analysed1", "analysed2"))
    val cache = ParsedCache.create(1).getOrElse(throw new NoSuchElementException())
    cache.upsert(packageContext, "Foo", Array(), "Hello".getBytes())
    assert(cache.get(packageContext, "Foo", Array()).get.sameElements("Hello".getBytes()))
    assert(
      cache
        .get(PackageContext(Some("test"), Array("ghosted1"), Array("analysed1", "analysed2")),
             "Foo",
             Array())
        .isEmpty)
    assert(
      cache
        .get(PackageContext(Some("test"),
                            Array("ghosted2", "ghosted1"),
                            Array("analysed1", "analysed2")),
             "Foo",
             Array())
        .isEmpty)
    assert(
      cache
        .get(PackageContext(Some("test"), Array("ghosted2", "ghosted1"), Array("analysed2")),
             "Foo",
             Array())
        .isEmpty)
    assert(
      cache
        .get(PackageContext(Some("test"),
                            Array("ghosted1", "analysed1"),
                            Array("ghosted1", "analysed2")),
             "Foo",
             Array())
        .isEmpty)
    assert(cache.get(PackageContext(Some("test"), Array(), Array()), "Foo", Array()).isEmpty)
  }
}
