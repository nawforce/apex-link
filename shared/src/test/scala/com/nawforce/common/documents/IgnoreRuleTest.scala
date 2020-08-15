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
package com.nawforce.common.documents

import org.scalatest.funsuite.AnyFunSuite

class IgnoreRuleTest extends AnyFunSuite {

  test("Character escapes") {
    assert(IgnoreRule.escape('/') == "\\/")
    assert(IgnoreRule.escape('\\') == "\\\\")
    assert(IgnoreRule.escape('+') == "\\+")
    assert(IgnoreRule.escape('#') == "\\#")
    assert(IgnoreRule.escape('a') == "a")
    assert(IgnoreRule.escape('0') == "0")
  }

  test("Empty rules") {
    assert(IgnoreRule.read("").isEmpty)
  }

  test("Whitespace rule") {
    assert(IgnoreRule.read(" ").isEmpty)
  }

  test("Comment rule") {
    assert(IgnoreRule.read("# A comment").isEmpty)
  }

  test("Two many *") {
    assert(IgnoreRule.read("a/***/b").isEmpty)
  }

  test("** at front") {
    assert(IgnoreRule.read("**/b").isEmpty)
  }

  test("** at back") {
    assert(IgnoreRule.read("a/**").isEmpty)
  }

  test("Legal use of **") {
    assert(IgnoreRule.read("a/**/b").size == 1)
  }

  test("Just a /") {
    assert(IgnoreRule.read("/").isEmpty)
  }

  test("A name") {
    assert(IgnoreRule.read("foo") == Seq(IgnoreRule(dirOnly = false, negation = false, "foo")))
  }

  test("An anchored name") {
    assert(IgnoreRule.read("foo/") == Seq(IgnoreRule(dirOnly = true, negation = false, "foo")))
  }

  test("An negated name") {
    assert(IgnoreRule.read("!foo") == Seq(IgnoreRule(dirOnly = false, negation = true, "foo")))
  }

  test("An anchored & negated name") {
    assert(IgnoreRule.read("!foo/") == Seq(IgnoreRule(dirOnly = true, negation = true, "foo")))
  }

  test("A sub-path") {
    assert(IgnoreRule.read("foo/bar") == Seq(IgnoreRule(dirOnly = false, negation = false, "foo/bar")))
  }

  test("A sub-path with wildcard") {
    assert(IgnoreRule.read("foo/*/bar") == Seq(IgnoreRule(dirOnly = false, negation = false, "foo/*/bar")))
  }

  test("A sub-path with double wildcard") {
    assert(IgnoreRule.read("foo/**/bar") == Seq(IgnoreRule(dirOnly = false, negation = false, "foo/**/bar")))
  }

  test("Leading /") {
    assert(IgnoreRule.read("/foo") == Seq(IgnoreRule(dirOnly = false, negation = false, "foo")))
  }

  test("Leading /**/") {
    assert(IgnoreRule.read("/**/foo") == Seq(IgnoreRule(dirOnly = false, negation = false, "foo")))
  }

  test("A name regex") {
    assert(IgnoreRule.read("foo").head.regex == "foo$")
  }

  test("A name with question mark regex") {
    val re = IgnoreRule.read("f?o").head.regex
    assert(re == "f[^\\/]o$" || re == "f[^\\\\]o$")
  }

  test("A name with wildcard regex") {
    val re = IgnoreRule.read("f*o").head.regex
    assert(re == "f[^\\/]*o$" || re == "f[^\\\\]*o$" )
  }

  test("A name with range regex") {
    assert(IgnoreRule.read("f[a-Z]o").head.regex == "f[a-Z]o$")
  }

  test("A sub-path regex") {
    val re = IgnoreRule.read("foo/bar").head.regex
    assert(re == "foo\\/bar$" || re == "foo\\\\bar$")
  }

  test("A sub-path with wildcard regex") {
    val re = IgnoreRule.read("foo/*/bar").head.regex
    assert(re == "foo\\/[^\\/]*\\/bar$" || re == "foo\\\\[^\\\\]*\\\\bar$")
  }

  test("A sub-path with double wildcard regex") {
    val re = IgnoreRule.read("foo/**/bar").head.regex
    assert(re == "foo\\/.*\\/?bar$" || re == "foo\\\\.*\\\\?bar$")
  }

  test("Leading / regex") {
    assert(IgnoreRule.read("/foo").head.regex == "foo$")
  }

  test("Leading /**/ regex") {
    assert(IgnoreRule.read("/**/foo").head.regex == "foo$")
  }
}
