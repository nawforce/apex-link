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

import com.nawforce.api.Org
import com.nawforce.types._
import com.nawforce.utils.Name
import org.scalatest.FunSuite

class PropertyTest extends FunSuite {
  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private val defaultOrg: Org = new Org

  def typeDeclaration(clsText: String, hasMessages: Boolean = false): TypeDeclaration = {
    Org.current.withValue(defaultOrg) {
      defaultOrg.issues.clear()
      val td = ApexTypeDeclaration.create(Name.Empty, defaultPath, new ByteArrayInputStream(clsText.getBytes()))
      if (td.isEmpty)
        defaultOrg.issues.dumpMessages(json = false)
      else {
        Org.current.value.issues.context.withValue(defaultPath) {
          td.get.validate()
        }
        td.get.fields
      }
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.get
    }
  }

  test("Empty class has no properties") {
    assert(typeDeclaration("public class Dummy {}").fields.isEmpty)
  }

  test("Property visible") {
    val property = typeDeclaration("public class Dummy {String foo{get; set;} }").fields.head
    assert(property.name == Name("foo"))
  }

  test("Multiple properties visible") {
    val fields = typeDeclaration("public class Dummy {String foo{get; set;} Integer bar{get; set;}}").fields
    assert(fields.map(_.name).toSet == Set(Name("foo"), Name("bar")))
  }

  test("Duplicate property reports error on duplicate") {
    val fields = typeDeclaration("public class Dummy {String foo{get; set;} String foo{get; set;}}", hasMessages = true).fields
    assert(fields.size == 1)
    assert(fields.head.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 42-63: Duplicate field/property: 'foo'\n")
  }

  test("Property without blocks") {
    val property = typeDeclaration("public class Dummy {String foo{} }", hasMessages = true).fields.head
    assert(property.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-32: Properties must have either a single 'get' and/or a single 'set' block\n")
  }

  test("Property with dual set") {
    val property = typeDeclaration("public class Dummy {String foo{set; set;} }", hasMessages = true).fields.head
    assert(property.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-41: Properties must have either a single 'get' and/or a single 'set' block\n")
  }

  test("Property with dual get & a set") {
    val property = typeDeclaration("public class Dummy {String foo{get; set; get;} }", hasMessages = true).fields.head
    assert(property.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-46: Properties must have either a single 'get' and/or a single 'set' block\n")
  }

  test("More than one duplicate property reports error on duplicates") {
    val fields = typeDeclaration("public class Dummy {String foo{get; set;} Integer foo{get; set;} String foo{get; set;}}",
      hasMessages = true).fields
    assert(fields.size == 1)
    assert(fields.head.name == Name("foo"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 42-64: Duplicate field/property: 'foo'\nline 1 at 65-86: Duplicate field/property: 'foo'\n")
  }

  test("Default property access private" ) {
    val property = typeDeclaration("public class Dummy {String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Private property access" ) {
    val property = typeDeclaration("public class Dummy {private String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Protected property access" ) {
    val property = typeDeclaration("public class Dummy {protected String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PROTECTED_MODIFIER))
    assert(property.readAccess == PROTECTED_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Public property access" ) {
    val property = typeDeclaration("public class Dummy {public String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PUBLIC_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Global property access" ) {
    val property = typeDeclaration("public class Dummy {global String foo{get; set;}}", hasMessages = true).fields.head
    assert(property.modifiers == Seq(GLOBAL_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Global property access in global class" ) {
    val property = typeDeclaration("global class Dummy {global String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(GLOBAL_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Global property access with get modifier in global class" ) {
    val property = typeDeclaration("global class Dummy {global String foo{global get; public set;}}").fields.head
    assert(property.modifiers == Seq(GLOBAL_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
  }

  test("Webservice property access" ) {
    val property = typeDeclaration("public class Dummy {webservice String foo{get; set;}}", hasMessages = true).fields.head
    assert(property.modifiers == Seq(WEBSERVICE_MODIFIER))
    assert(property.readAccess == WEBSERVICE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Webservice property access with get/set modifiers" ) {
    val property = typeDeclaration("global class Dummy {webservice String foo{global get; public set;}}").fields.head
    assert(property.modifiers == Seq(WEBSERVICE_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
  }

  test("Webservice property access in global class" ) {
    val property = typeDeclaration("global class Dummy {webservice String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(WEBSERVICE_MODIFIER))
    assert(property.readAccess == WEBSERVICE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Property setter lower visibility" ) {
    val property = typeDeclaration("public class Dummy {public String foo{get; private set;}}").fields.head
    assert(property.modifiers == Seq(PUBLIC_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == PRIVATE_MODIFIER)
  }

  test("Property setter higher visibility" ) {
    val property = typeDeclaration("public class Dummy {private String foo{get; public set;}}", hasMessages = true).fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 28-56: Setter visibility must be same or less than property\n")
  }

  test("Property getter lower visibility" ) {
    val property = typeDeclaration("public class Dummy {public String foo{private get; set;}}").fields.head
    assert(property.modifiers == Seq(PUBLIC_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
  }

  test("Property getter higher visibility" ) {
    val property = typeDeclaration("public class Dummy {private String foo{public get; set;}}", hasMessages = true).fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == PRIVATE_MODIFIER)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 28-56: Getter visibility must be same or less than property\n")
  }

  test("Static property" ) {
    val property = typeDeclaration("public class Dummy {static String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Final property" ) {
    val property = typeDeclaration("public class Dummy {final String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, FINAL_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Many modifiers property" ) {
    val property = typeDeclaration("public class Dummy {protected transient final static String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PROTECTED_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER, STATIC_MODIFIER))
    assert(property.readAccess == PROTECTED_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Duplicate modifiers property" ) {
    val property = typeDeclaration("public class Dummy {protected protected String foo{get; set;}}",
      hasMessages = true).fields.head
    assert(property.modifiers == Seq(PROTECTED_MODIFIER))
    assert(property.readAccess == PROTECTED_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 47-50: Modifier 'protected' is used more than once\n")
  }

  test("Mixed access property" ) {
    val property = typeDeclaration("public class Dummy {global webservice String foo{get; set;}}",
      hasMessages = true).fields.head
    assert(property.modifiers == Seq(PUBLIC_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 45-48: Only one visibility modifier from 'webservice', 'global', 'public', 'protected' & 'private' may be used on fields\n")
  }

  test("AuraEnabled property" ) {
    val property = typeDeclaration("public class Dummy {@AuraEnabled String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Deprecated property" ) {
    val property = typeDeclaration("public class Dummy {@Deprecated String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("InvocableVariable property" ) {
    val property = typeDeclaration("public class Dummy {@InvocableVariable String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("TestVisible property" ) {
    val property = typeDeclaration("public class Dummy {@TestVisible String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("SuppressWarnings property" ) {
    val property = typeDeclaration("public class Dummy {@SuppressWarnings String foo{get; set;}}").fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
  }

  test("Bad annotation property" ) {
    val property = typeDeclaration("public class Dummy {@TestSetup String foo{get; set;}}", hasMessages = true).fields.head
    assert(property.modifiers == Seq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-30: Unexpected annotation 'TestSetup' on field/property declaration\n")
  }

  test("Duplicate annotation property" ) {
    val property = typeDeclaration("public class Dummy {@TestVisible @TestVisible String foo{get; set;}}", hasMessages = true).fields.head
    assert(property.modifiers == Seq(TEST_VISIBLE_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 53-56: Modifier '@TestVisible' is used more than once\n")
  }
}
