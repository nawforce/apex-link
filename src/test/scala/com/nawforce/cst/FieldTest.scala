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
package com.nawforce.cst

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.api.Org
import com.nawforce.types._
import com.nawforce.utils.Name
import org.scalatest.FunSuite

class FieldTest extends FunSuite {
  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private val defaultOrg: Org = new Org

  def typeDeclaration(clsText: String, hasMessages: Boolean = false): TypeDeclaration = {
    Org.current.withValue(defaultOrg) {
      defaultOrg.clear()
      val td = ApexTypeDeclaration.create(Name.Empty, defaultPath, new ByteArrayInputStream(clsText.getBytes()))
      if (td.isEmpty)
        defaultOrg.issues.dumpMessages(json = false)
      else {
        Org.current.value.issues.context.withValue(defaultPath) {
          td.head.validate()
        }
        td.head.fields
      }
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.head
    }
  }

  test("Empty class has no fields") {
    assert(typeDeclaration("public class Dummy {}").fields.isEmpty)
  }

  test("Field visible") {
    val field = typeDeclaration("public class Dummy {String foo;}").fields.head
    assert(field.name == Name("foo"))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == PRIVATE_MODIFIER)
  }

  test("Multiple fields visible") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer bar;}").fields
    assert(fields.map(_.name).toSet == Set(Name("foo"), Name("bar")))
  }

  test("Duplicate field reports error on duplicate") {
    val fields = typeDeclaration("public class Dummy {String foo; String foo;}", hasMessages = true).fields
    assert(fields.size == 1)
    assert(fields.head.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 32-43: Duplicate field/property: 'foo'\n")
  }

  test("More than one duplicate field reports error on duplicates") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer foo; String foo;}",
      hasMessages = true).fields
    assert(fields.size == 1)
    assert(fields.head.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 32-44: Duplicate field/property: 'foo'\nline 1 at 45-56: Duplicate field/property: 'foo'\n")
  }

  test("Default field access private" ) {
    val field = typeDeclaration("public class Dummy {String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Private field access" ) {
    val field = typeDeclaration("public class Dummy {private String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Protected field access" ) {
    val field = typeDeclaration("public class Dummy {protected String foo;}").fields.head
    assert(field.modifiers == Seq(PROTECTED_MODIFIER))
    assert(field.readAccess == PROTECTED_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Public field access" ) {
    val field = typeDeclaration("public class Dummy {public String foo;}").fields.head
    assert(field.modifiers == Seq(PUBLIC_MODIFIER))
    assert(field.readAccess == PUBLIC_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Global field access" ) {
    val field = typeDeclaration("public class Dummy {global String foo;}", hasMessages = true).fields.head
    assert(field.modifiers == Seq(GLOBAL_MODIFIER))
    assert(field.readAccess == GLOBAL_MODIFIER)
    assert(field.writeAccess == field.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Global field access in global class" ) {
    val field = typeDeclaration("global class Dummy {global String foo;}").fields.head
    assert(field.modifiers == Seq(GLOBAL_MODIFIER))
    assert(field.readAccess == GLOBAL_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Webservice field access" ) {
    val field = typeDeclaration("public class Dummy {webservice String foo;}", hasMessages = true).fields.head
    assert(field.modifiers == Seq(WEBSERVICE_MODIFIER))
    assert(field.readAccess == WEBSERVICE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Webservice field access in global class" ) {
    val field = typeDeclaration("global class Dummy {webservice String foo;}").fields.head
    assert(field.modifiers == Seq(WEBSERVICE_MODIFIER))
    assert(field.readAccess == WEBSERVICE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Static field" ) {
    val field = typeDeclaration("public class Dummy {static String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Final field" ) {
    val field = typeDeclaration("public class Dummy {final String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, FINAL_MODIFIER))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Many modifiers field" ) {
    val field = typeDeclaration("public class Dummy {protected transient final static String foo;}").fields.head
    assert(field.modifiers == Seq(PROTECTED_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER, STATIC_MODIFIER))
    assert(field.readAccess == PROTECTED_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Duplicate modifiers field" ) {
    val field = typeDeclaration("public class Dummy {protected protected String foo;}",
      hasMessages = true).fields.head
    assert(field.modifiers == Seq(PROTECTED_MODIFIER))
    assert(field.readAccess == PROTECTED_MODIFIER)
    assert(field.writeAccess == field.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 47-50: Modifier 'protected' is used more than once\n")
  }

  test("Mixed access field" ) {
    val field = typeDeclaration("public class Dummy {global webservice String foo;}",
      hasMessages = true).fields.head
    assert(field.modifiers == Seq(PUBLIC_MODIFIER))
    assert(field.readAccess == PUBLIC_MODIFIER)
    assert(field.writeAccess == field.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 45-48: Only one visibility modifier from 'webservice', 'global', 'public', 'protected' & 'private' may be used on fields\n")
  }

  test("AuraEnabled field" ) {
    val field = typeDeclaration("public class Dummy {@AuraEnabled String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Deprecated field" ) {
    val field = typeDeclaration("public class Dummy {@Deprecated String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("InvocableVariable field" ) {
    val field = typeDeclaration("public class Dummy {@InvocableVariable String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("TestVisible field" ) {
    val field = typeDeclaration("public class Dummy {@TestVisible String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("SuppressWarnings field" ) {
    val field = typeDeclaration("public class Dummy {@SuppressWarnings String foo;}").fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
  }

  test("Bad annotation field" ) {
    val field = typeDeclaration("public class Dummy {@TestSetup String foo;}", hasMessages = true).fields.head
    assert(field.modifiers == Seq(PRIVATE_MODIFIER))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-30: Unexpected annotation 'TestSetup' on field/property declaration\n")
  }

  test("Duplicate annotation field" ) {
    val field = typeDeclaration("public class Dummy {@TestVisible @TestVisible String foo;}", hasMessages = true).fields.head
    assert(field.modifiers == Seq(TEST_VISIBLE_ANNOTATION))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == field.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 53-56: Modifier '@TestVisible' is used more than once\n")
  }
}
