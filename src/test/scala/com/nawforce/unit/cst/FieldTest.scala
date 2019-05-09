/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.unit.cst

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.types._
import com.nawforce.utils.{IssueLog, Name}
import org.scalatest.FunSuite

class FieldTest extends FunSuite {
  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)

  def typeDeclaration(clsText: String, hasMessages: Boolean = false): TypeDeclaration = {
    IssueLog.clear()
    val td = ApexTypeDeclaration.create(defaultPath, new ByteArrayInputStream(clsText.getBytes()))
    if (td.isEmpty)
      IssueLog.dumpMessages(json=false)
    assert(IssueLog.hasMessages == hasMessages)
    td.get
  }

  test("Empty class has no fields") {
    assert(typeDeclaration("public class Dummy {}").fields.isEmpty)
  }

  test("Field visible") {
    val field = typeDeclaration("public class Dummy {String foo;}").fields.head
    assert(field.name == Name("foo"))
    assert(field.readAccess == Seq(PRIVATE_MODIFIER))
    assert(field.writeAccess == Seq(PRIVATE_MODIFIER))
  }

  test("Multiple fields visible") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer bar;}").fields
    assert(fields.map(_.name).toSet == Set(Name("foo"), Name("bar")))
  }

  test("Duplicate field reports error on duplicate") {
    val fields = typeDeclaration("public class Dummy {String foo; String foo;}", hasMessages = true).fields
    assert(fields.size == 1)
    assert(fields.head.name == Name("foo"))
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 32-43: Duplicate field: 'foo'\n")
  }

  test("More than on duplicate field reports error on duplicates") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer foo; String foo;}",
      hasMessages = true).fields
    assert(fields.size == 1)
    assert(fields.head.name == Name("foo"))
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 32-44: Duplicate field: 'foo'\nline 1 at 45-56: Duplicate field: 'foo'\n")
  }

  test("Default field access private" ) {
    val field = typeDeclaration("public class Dummy {String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Private field access" ) {
    val field = typeDeclaration("public class Dummy {private String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Protected field access" ) {
    val field = typeDeclaration("public class Dummy {protected String foo;}").fields.head
    assert(field.readAccess == Seq(PROTECTED_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Public field access" ) {
    val field = typeDeclaration("public class Dummy {public String foo;}").fields.head
    assert(field.readAccess == Seq(PUBLIC_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Global field access" ) {
    val field = typeDeclaration("public class Dummy {global String foo;}", hasMessages = true).fields.head
    assert(field.readAccess == Seq(GLOBAL_MODIFIER))
    assert(field.writeAccess == field.readAccess)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals must also be declared global\n")
  }

  test("Static field" ) {
    val field = typeDeclaration("public class Dummy {static String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Final field" ) {
    val field = typeDeclaration("public class Dummy {final String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, FINAL_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Many modifiers field" ) {
    val field = typeDeclaration("public class Dummy {protected transient final static String foo;}").fields.head
    assert(field.readAccess == Seq(PROTECTED_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER, STATIC_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("Duplicate modifiers field" ) {
    val field = typeDeclaration("public class Dummy {protected protected String foo;}",
      hasMessages = true).fields.head
    assert(field.readAccess == Seq(PROTECTED_MODIFIER))
    assert(field.writeAccess == field.readAccess)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 47-50: Modifier 'protected' is used more than once\n")
  }

  test("Mixed access field" ) {
    val field = typeDeclaration("public class Dummy {global protected String foo;}",
      hasMessages = true).fields.head
    assert(field.readAccess == Seq(PUBLIC_MODIFIER))
    assert(field.writeAccess == field.readAccess)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 44-47: Only one visibility modifier from 'global', 'public', 'protected' & 'private' may be used on fields\n")
  }

  test("Global field access in global class" ) {
    val field = typeDeclaration("global class Dummy {global String foo;}").fields.head
    assert(field.readAccess == Seq(GLOBAL_MODIFIER))
    assert(field.writeAccess == field.readAccess)
  }

  test("AuraEnabled field" ) {
    val field = typeDeclaration("public class Dummy {@AuraEnabled String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION))
    assert(field.writeAccess == field.readAccess)
  }

  test("Deprecated field" ) {
    val field = typeDeclaration("public class Dummy {@Deprecated String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION))
    assert(field.writeAccess == field.readAccess)
  }

  test("InvocableVariable field" ) {
    val field = typeDeclaration("public class Dummy {@InvocableVariable String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION))
    assert(field.writeAccess == field.readAccess)
  }

  test("TestVisible field" ) {
    val field = typeDeclaration("public class Dummy {@TestVisible String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(field.writeAccess == field.readAccess)
  }

  test("SuppressWarnings field" ) {
    val field = typeDeclaration("public class Dummy {@SuppressWarnings String foo;}").fields.head
    assert(field.readAccess == Seq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION))
    assert(field.writeAccess == field.readAccess)
  }

  test("Bad annotation field" ) {
    val field = typeDeclaration("public class Dummy {@TestSetup String foo;}", hasMessages = true).fields.head
    assert(field.readAccess == Seq())
    assert(field.writeAccess == field.readAccess)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 20-30: Unexpected annotation 'TestSetup' on field declaration\n")
  }

  test("Duplciate annotation field" ) {
    val field = typeDeclaration("public class Dummy {@TestVisible @TestVisible String foo;}", hasMessages = true).fields.head
    assert(field.readAccess == Seq(TEST_VISIBLE_ANNOTATION))
    assert(field.writeAccess == field.readAccess)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 53-56: Modifier '@TestVisible' is used more than once\n")
    IssueLog.clear()
  }
}
