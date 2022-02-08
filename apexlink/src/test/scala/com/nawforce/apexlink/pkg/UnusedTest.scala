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
package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.{OrgImpl, PackageImpl}
import com.nawforce.apexlink.plugins.UnusedPlugin.onlyTestCodeReferenceText
import com.nawforce.apexlink.types.apex.{FullDeclaration, SummaryDeclaration}
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class UnusedTest extends AnyFunSuite with TestHelper {

  def orgIssuesFor(org: OrgImpl, path: PathLike): String = {
    val messages = org.issueManager.issuesForFileInternal(path).map(_.asString()).mkString("\n")
    if (messages.nonEmpty) messages + "\n" else ""
  }

  test("Unused method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public void foo() {}}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == "Unused: line 1 at 32-35: Unused method 'void foo()'\n"
      )
    }
  }

  test("Unused global method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "global class Dummy {global void foo() {}}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused @AuraEnabled method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@AuraEnabled public void foo() {}}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Nested unused method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class Inner {public void foo() {}} }",
        "Foo.cls"   -> "public class Foo{ {Type t1 = Dummy.class; Type t2 = Dummy.Inner.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls")) ==
          "Unused: line 1 at 52-55: Unused method 'void foo()'\n"
      )
    }
  }

  test("Nested global unused method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "global class Dummy {global class Inner {global void foo() {}} }",
        "Foo.cls"   -> "public class Foo{ {Type t1 = Dummy.class; Type t2 = Dummy.Inner.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Nested @AuraEnabled method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class Inner {public @AuraEnabled void foo() {}} }",
        "Foo.cls"   -> "public class Foo{ {Type t1 = Dummy.class; Type t2 = Dummy.Inner.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Method used from same method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public void foo() {foo();}}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Method used from block") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {{foo();} public void foo() {}}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {Object a;}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls")) ==
          "Unused: line 1 at 27-28: Unused field 'a'\n"
      )
    }
  }

  test("Unused global field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "global class Dummy {global Object a;}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused @AuraEnabled field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@AuraEnabled Object a;}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Nested unused field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class Inner {Object a;} }",
        "Foo.cls"   -> "public class Foo{ {Type t1 = Dummy.class; Type t2 = Dummy.Inner.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls")) ==
          "Unused: line 1 at 47-48: Unused field 'a'\n"
      )
    }
  }

  test("Nested global unused field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "global class Dummy {global class Inner {global Object a;} }",
        "Foo.cls"   -> "public class Foo{ {Type t1 = Dummy.class; Type t2 = Dummy.Inner.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Nested @AuraEnabled unused field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class Inner {public @AuraEnabled Object a;} }",
        "Foo.cls"   -> "public class Foo{ {Type t1 = Dummy.class; Type t2 = Dummy.Inner.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Field used from method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {Object a; void foo(){foo(); a = null;}}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Field used from block") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {{a = null;} Object a;}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused class") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {Object a; void func() {}}")) {
      root: PathLike =>
        val org = createOrgWithUnused(root)
        assert(
          orgIssuesFor(org, root.join("Dummy.cls")) ==
            "Unused: line 1 at 13-18: Unused class 'Dummy'\n"
        )
    }
  }

  test("Nested unused class") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class Inner {Object a; void func() {}} }",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls")) ==
          "Unused: line 1 at 33-38: Unused class 'Dummy.Inner'\n"
      )
    }
  }

  test("Unused catch") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {{ try {} catch(Exception e) {} }}",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused this call argument") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public Dummy(Object a) {this(a, null);} public Dummy(Object a, Object b) {a = b;} }",
        "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused super call argument") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy extends Foo {public Dummy(Object a) {super(a, null);}  }",
        "Foo.cls"   -> "virtual public class Foo{ {Type t = Dummy.class;} public Foo(Object a, Object b) {a = b;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  def assertIsFullDeclaration(
    pkg: PackageImpl,
    name: String,
    namespace: Option[Name] = None
  ): Unit = {
    assert(
      pkg.orderedModules.head
        .findModuleType(TypeName(Name(name)).withNamespace(namespace))
        .head
        .isInstanceOf[FullDeclaration]
    )
  }

  def assertIsSummaryDeclaration(
    pkg: PackageImpl,
    name: String,
    namespace: Option[Name] = None
  ): Unit = {
    assert(
      pkg.orderedModules.head
        .findModuleType(TypeName(Name(name)).withNamespace(namespace))
        .head
        .isInstanceOf[SummaryDeclaration]
    )
  }

  test("Used method on summary type") {
    withManualFlush {
      FileSystemHelper.run(
        Map(
          "Dummy.cls"  -> "public class Dummy {public static void foo() {}}",
          "Caller.cls" -> "public class Caller {{Dummy.Foo();}}"
        )
      ) { root: PathLike =>
        // Setup as cached
        val org = createOrgWithUnused(root)
        val pkg = org.unmanaged
        assertIsFullDeclaration(pkg, "Dummy")
        assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
        org.flush()

        val org2 = createOrgWithUnused(root)
        val pkg2 = org2.unmanaged
        assertIsSummaryDeclaration(pkg2, "Dummy")
        OrgImpl.current.withValue(org2) {
          assert(orgIssuesFor(org2, root.join("Dummy.cls")).isEmpty)
        }
      }
    }
  }

  test("Unused method on summary type") {
    withManualFlush {
      FileSystemHelper.run(
        Map(
          "Dummy.cls" -> "public class Dummy {public void foo() {}}",
          "Foo.cls"   -> "public class Foo{ {Type t = Dummy.class;} }"
        )
      ) { root: PathLike =>
        // Setup as cached
        val org = createOrgWithUnused(root)
        val pkg = org.unmanaged
        assertIsFullDeclaration(pkg, "Dummy")
        assert(
          orgIssuesFor(org, root.join("Dummy.cls")) ==
            "Unused: line 1 at 32-35: Unused method 'void foo()'\n"
        )
        org.flush()

        val org2 = createOrgWithUnused(root)
        val pkg2 = org2.unmanaged
        assertIsSummaryDeclaration(pkg2, "Dummy")
        OrgImpl.current.withValue(org2) {
          assert(
            orgIssuesFor(org2, root.join("Dummy.cls")) ==
              "Unused: line 1 at 32-35: Unused method 'void foo()'\n"
          )
        }
      }
    }
  }

  test("Trigger referencing class") {
    FileSystemHelper.run(
      Map(
        "Foo.cls" -> "public class Foo {public static String bar;}",
        "Dummy.trigger" ->
          """trigger Dummy on Account (before insert) {
            |  System.debug(Foo.bar);
            |}""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(org.issues.isEmpty)

      OrgImpl.current.withValue(org) {
        assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
      }
    }
  }

  test("Method referenced from external function call ") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ext"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy {public static void foo() {}}",
        "pkg/Other.cls" -> "public class Other {public void bar() {ext.func(Dummy.foo());}}"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Unused local var") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy { {Object a;} }")) {
      root: PathLike =>
        createOrgWithUnused(root)
        assert(
          getMessages(root.join("Dummy.cls")) ==
            "Unused: line 1 at 13-18: Unused class 'Dummy'\nUnused: line 1 at 29-30: Unused local variable 'a'\n"
        )
    }
  }

  test("Unused local var assignment") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy { {Object a; a=null;} }")) {
      root: PathLike =>
        createOrgWithUnused(root)
        assert(
          getMessages(root.join("Dummy.cls")) ==
            "Unused: line 1 at 13-18: Unused class 'Dummy'\n"
        )
    }
  }

  test("Page controller & extension is used") {
    FileSystemHelper.run(
      Map(
        "Test.page"      -> "<apex:page controller='Controller' extensions='Extension'/>",
        "Controller.cls" -> "public class Controller { }",
        "Extension.cls"  -> "public class Extension { }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(org.issues.isEmpty)

      OrgImpl.current.withValue(org) {
        assert(orgIssuesFor(org, root.join("Controller.cls")).isEmpty)
        assert(orgIssuesFor(org, root.join("Extension.cls")).isEmpty)
      }
    }
  }

  test("Component controller is used") {
    FileSystemHelper.run(
      Map(
        "Test.component" -> "<apex:component controller='Controller'/>",
        "Controller.cls" -> "public class Controller { }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(org.issues.isEmpty)

      OrgImpl.current.withValue(org) {
        assert(orgIssuesFor(org, root.join("Controller.cls")).isEmpty)
      }
    }
  }

  test("Queueable via abstract") {
    FileSystemHelper.run(
      Map(
        "Base.cls"  -> "public abstract class Base implements Queueable { {Type t = Dummy.class;} }",
        "Dummy.cls" -> "public class Dummy extends Base {public void execute(QueueableContext context) {} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      OrgImpl.current.withValue(org) {
        assert(getMessages(root.join("Dummy.cls")).isEmpty)
      }
    }
  }

  test("Unused method only referenced in test class") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public static void foo() {}}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }",
        "Foo.cls"   -> "@isTest public class Foo{ {Dummy.foo();} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == s"Unused: line 1 at 39-42: Unused method 'void foo()', $onlyTestCodeReferenceText\n"
      )
    }
  }

  test("Unused method suppress by @TestVisible") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@TestVisible public static void foo() {}}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")) == "")
    }
  }

  test("Unused field only referenced in test class") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public static String foo;}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }",
        "Foo.cls"   -> "@isTest public class Foo{ {String b = Dummy.foo;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == s"Unused: line 1 at 41-44: Unused field 'foo', $onlyTestCodeReferenceText\n"
      )
    }
  }

  test("Unused field suppress by @TestVisible") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@TestVisible public static String foo;}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")) == "")
    }
  }

  test("Unused inner class only referenced in test class") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class foo {}}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }",
        "Foo.cls"   -> "@isTest public class Foo{ {Type t = Dummy.foo;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == "Unused: line 1 at 33-36: Unused class 'Dummy.foo'\n"
      )
    }
  }

  test("Unused inner class suppress by @TestVisible") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {@TestVisible public class foo {}}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")) == "")
    }
  }

  test("Unused method only referenced in test class rolls up") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public static void foo() {}}",
        "Foo.cls"   -> "@isTest public class Foo{ {Dummy.foo();} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == s"Unused: line 1 at 13-18: Unused class 'Dummy', $onlyTestCodeReferenceText\n"
      )
    }
  }

  test("Unused field only referenced in test class rolls up") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public static String foo;}",
        "Foo.cls"   -> "@isTest public class Foo{ {String b = Dummy.foo;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == s"Unused: line 1 at 13-18: Unused class 'Dummy', $onlyTestCodeReferenceText\n"
      )
    }
  }

  test("Unused inner class only referenced in test class rolls up") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public class foo {}}",
        "Foo.cls"   -> "@isTest public class Foo{ {Type t = Dummy.foo;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(
        orgIssuesFor(org, root.join("Dummy.cls"))
          == s"Unused: line 1 at 13-18: Unused class 'Dummy', $onlyTestCodeReferenceText\n"
      )
    }
  }

  test("Unused enum constant bug") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public enum A { B } {switch on a { when B {}} }}",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      val org = createOrgWithUnused(root)
      assert(orgIssuesFor(org, root.join("Dummy.cls")) == "")
    }
  }

  test("Local var not unused when bound in string literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {Object a; System.debug(':a');} }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Instance field not unused when bound in string literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {Object a; { System.debug(':a');} }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Static field not unused when bound in string literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {static Object a; { System.debug(':a');} }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Outer static field not unused when bound in string literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {static Object a; class Foo { { System.debug(':a');} } }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.Foo.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Local var not unused when bound in SOQL literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {Object a; System.debug([Select Id from Account where Id=:a]); } }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Instance field not unused when bound in SOQL literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {Object a; { System.debug([Select Id from Account where Id=:a]);} }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Static field not unused when bound in SOQL literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {static Object a; { System.debug([Select Id from Account where Id=:a]);} }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

  test("Outer static field not unused when bound in SOQL literal") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {static Object a; class Foo { { System.debug([Select Id from Account where Id=:a]);} } }",
        "Bar.cls"   -> "public class Bar{ {Type t = Dummy.Foo.class;} }"
      )
    ) { root: PathLike =>
      createOrgWithUnused(root)
      assert(getMessages(root.join("Dummy.cls")).isEmpty)
    }
  }

}
