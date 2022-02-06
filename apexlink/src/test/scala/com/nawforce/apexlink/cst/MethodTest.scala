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

  test("Method call with ambiguous target") {
    typeDeclaration("public class Dummy { {EventBus.publish(null); } }")
    assert(
      dummyIssues == "Error: line 1 at 22-44: Ambiguous method call for 'publish' on 'System.EventBus' taking arguments 'null'\n"
    )
  }

  test("Method call ambiguous target strict match") {
    typeDeclaration("public class Dummy { {Decimal.valueOf(1); } }")
    assert(dummyIssues.isEmpty)
  }

  test("Method call with non-ambiguous target") {
    FileSystemHelper.run(
      Map(
        "A.cls"     -> "public virtual class A {}",
        "B.cls"     -> "public virtual class B extends A {}",
        "Dummy.cls" -> "public class Dummy extends B { {Dummy d; d.func(d);} void func(A a) {} void func(B b) {} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(getMessages(root.join("Dummy.cls")) == "")
    }
  }

  test("Platform generic interface params duplicate") {
    typeDeclarations(
      Map(
        "Dummy.cls" ->
          "public class Dummy { void run(Database.Batchable<SObject> arg) {} void run(Database.Batchable<String> arg) {} }"
      )
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 71-74: Method 'run' can not use same platform generic interface as existing method at line 1 at 26-29\n"
    )
  }

  test("Method call using platform generic interface with wrong type argument type") {
    typeDeclarations(
      Map(
        "Dummy.cls" ->
          "public class Dummy { void run(Database.Batchable<SObject> arg) {} {Database.Batchable<Object> a; run(a);} }"
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("SObject generic params duplicate") {
    typeDeclarations(
      Map(
        "Dummy.cls" ->
          "public class Dummy { void run(List<SObject> arg) {} void run(List<Account> arg) {} }"
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("SObject generic params fulfills interface") {
    typeDeclarations(
      Map(
        "Dummy.cls" ->
          "public class Dummy implements API { public interface API {void run(List<SObject> arg);} void run(List<Account> arg) {} }"
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("String params fulfills Id interface") {
    typeDeclarations(
      Map(
        "Dummy.cls" ->
          "public class Dummy implements API { public interface API {void run(Id arg);} void run(String arg) {} }"
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Id params fulfills String interface") {
    typeDeclarations(
      Map(
        "Dummy.cls" ->
          "public class Dummy implements API { public interface API {void run(String arg);} void run(Id arg) {} }"
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Method call for possible synthetic platform method") {
    typeDeclaration("public class Dummy { {Database.QueryLocatorIterator it; it.next(); } }")
    assert(dummyIssues.isEmpty)
  }

  test("Method call basic database query") {
    typeDeclaration("public class Dummy { {List<SObject> a = Database.query(''); } }")
    assert(dummyIssues.isEmpty)
  }

  test("Method call for possible ambiguous database query") {
    typeDeclaration(
      "public class Dummy {void f1(List<SObject> a){} void f1(SObject a) {} {f1(Database.query('')); } }"
    )
    assert(dummyIssues.isEmpty)
  }

  test("Method call custom equals (bad param type)") {
    typeDeclarations(
      Map(
        "MyInterface.cls" -> "public interface MyInterface {}",
        "Dummy.cls"       -> "public class Dummy implements MyInterface { public Boolean equals(MyInterface other) {return true;} {Boolean b = equals(this);} }"
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Static method private override different return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"   -> "public virtual class Base { static Base getInstance() {return null;} }",
        "Extend.cls" -> "public class Extend extends Base { static Extend getInstance() {return null;} { getInstance();} }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Static method protected override different return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"  -> "public virtual class Base { static protected Base getInstance() {return null;} }",
        "Dummy.cls" -> "public class Dummy extends Base { static protected Dummy getInstance() {return null;} { getInstance();} }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Static method public override different return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"  -> "public virtual class Base { static public Base getInstance() {return null;} }",
        "Dummy.cls" -> "public class Dummy extends Base { static public Dummy getInstance() {return null;} { getInstance();} }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Instance method private none-override different return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"  -> "public virtual class Base { Base getInstance() {return null;} }",
        "Dummy.cls" -> "public class Dummy extends Base { Dummy getInstance() {return null;} { this.getInstance();} }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Instance method private none-override same return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"  -> "public virtual class Base { void getInstance() {} }",
        "Dummy.cls" -> "public class Dummy extends Base { void getInstance() {return null;} { this.getInstance();} }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Instance method protected override different return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"   -> "public virtual class Base { protected virtual Base getInstance() {return null;} }",
        "Extend.cls" -> "public class Extend extends Base { protected override Extend getInstance() {return null;} { this.getInstance();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Extend.cls")) ==
          "Error: line 1 at 61-72: Method 'getInstance' has wrong return type to override, should be 'Base'\n"
      )
    }
  }

  test("Instance method public override different return") {
    FileSystemHelper.run(
      Map(
        "Base.cls"   -> "public virtual class Base { public virtual Base getInstance() {return null;} }",
        "Extend.cls" -> "public class Extend extends Base { public override Extend getInstance() {return null;} { this.getInstance();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Extend.cls")) ==
          "Error: line 1 at 58-69: Method 'getInstance' has wrong return type to override, should be 'Base'\n"
      )
    }
  }

  test("Method call with ghosted type") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"packageDirectories": [{"path": "force-app"}],
          |"plugins": {"dependencies": [{"namespace": "ext"}]}
          |}""".stripMargin,
        "force-app/Dummy.cls" -> "public class Dummy { {ext.Something a; String.escapeSingleQuotes(a); } }"
      )
    ) { root: PathLike =>
      createHappyOrg(root)
    }
  }

  test("Ambiguous Method call with ghosted type") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"packageDirectories": [{"path": "force-app"}],
          |"plugins": {"dependencies": [{"namespace": "ext"}]}
          |}""".stripMargin,
        "force-app/Dummy.cls" -> "public class Dummy { {ext.Something a; EventBus.publish(a); } }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("force-app").join("Dummy.cls")) ==
          "Warning: line 1 at 39-58: Ambiguous method call for 'publish' on 'System.EventBus' taking arguments 'any', likely due to unknown type\n"
      )
    }
  }
}
