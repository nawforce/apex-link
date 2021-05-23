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

import com.nawforce.apexlink.TestHelper
import org.scalatest.funsuite.AnyFunSuite

class SuppressWarningsTest extends AnyFunSuite with TestHelper {

  test("Suppress disabled") {
    typeDeclaration("public class Dummy {class Inner {Integer b; List<Inner> a; {b = a[null].b;}}}")
    assert(dummyIssues == "Error: line 1 at 66-70: Array indexes must be Integers, found 'null'\n")
  }

  test("Outer Suppress") {
    typeDeclaration(
      "@SuppressWarnings public class Dummy {class Inner {Integer b; List<Inner> a; {Integer b = a[null].b;}}}")
    assert(!hasIssues)
  }

  test("Inner Suppress") {
    typeDeclaration(
      "public class Dummy {@SuppressWarnings class Inner {Integer b; List<Inner> a; {Integer b = a[null].b;}}}")
    assert(!hasIssues)
  }

  // TODO: This should work
  /*
  test("Method Suppress") {
    typeDeclaration("public class Dummy {class Inner {Integer b; List<Inner> a; @SuppressWarnings void foo(){ Integer b = a[null].b;}}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }*/

}
