package com.nawforce.common.cst

import com.nawforce.common.api.{Name, ServerOps}
import com.nawforce.common.documents.ApexClassDocument
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.FullDeclaration
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.runtime.parsers.SourceData
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SafeNavigationTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private val defaultDoc = ApexClassDocument(defaultPath, Name("Dummy"))
  private var defaultOrg: OrgImpl = _

  def typeDeclaration(clsText: String): Option[TypeDeclaration] = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultDoc, SourceData(clsText))
      td.foreach(td => {
        defaultOrg.unmanaged.upsertMetadata(td)
        td.validate()
      })
      td
    }
  }

  before {
    ServerOps.setAutoFlush(false)
    defaultOrg = new OrgImpl
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Field Reference") {
    typeDeclaration("public class Dummy {String a; { String b = this?.a; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Method Reference") {
    typeDeclaration("public class Dummy {String func(){} { String b = this?.func(); }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Field nested reference") {
    typeDeclaration("public class Dummy {Dummy a; { Dummy b = this?.a?.a; }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Method nexted reference") {
    typeDeclaration("public class Dummy {Dummy func(){} { Dummy b = this?.func()?.func(); }}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Static field reference") {
    typeDeclaration("public class Dummy {static String a; { String b = Dummy?.a; }}")
    assert(
      defaultOrg.issues
        .getMessages(defaultPath.toString) == "Error: line 1 at 50-58: Safe navigation operator (?.) can not be used on static references\n")
  }

  test("Static method reference") {
    typeDeclaration("public class Dummy {static String func(){} { String b = Dummy?.func(); }}")
    assert(
      defaultOrg.issues
        .getMessages(defaultPath.toString) == "Error: line 1 at 56-69: Safe navigation operator (?.) can not be used on static references\n")
  }


}
