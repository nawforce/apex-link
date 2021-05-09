package com.nawforce.common.cst

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.api.{Org, ServerOps, TypeSummary}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.apex.{ApexFullDeclaration, FullDeclaration}
import com.nawforce.common.types.core.TypeDeclaration

trait CSTTestHelper {

  private var defaultOrg: OrgImpl = _

  def createOrg(path: PathLike): OrgImpl = {
    defaultOrg = Org.newOrg(path).asInstanceOf[OrgImpl]
    defaultOrg
  }

  def withOrg[T](op: OrgImpl => T): T = {
    OrgImpl.current.withValue(defaultOrg) {
      op(defaultOrg)
    }
  }

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    try {
      ServerOps.setAutoFlush(false)
      FileSystemHelper.run(classes) { root: PathLike =>
        createOrg(root)
        classes.keys
          .map(cls => unmanagedType(TypeName(Name(cls.replace(".cls", "")))).get)
          .toSeq
      }
    } finally {
      ServerOps.setAutoFlush(true)
    }
  }

  def typeDeclaration(clsText: String): TypeDeclaration = {
    typeDeclarations(Map("Dummy.cls" -> clsText)).head
  }

  def typeDeclarationInner(clsText: String): TypeDeclaration = {
    typeDeclaration(clsText).nestedTypes.head
  }

  def classSummary(text: String, hasMessages: Boolean = false): TypeSummary = {
    val td = typeDeclaration(text)
    assert(hasIssues == hasMessages)
    td.asInstanceOf[FullDeclaration].summary
  }

  def triggerDeclaration(text: String): TypeDeclaration = {
    try {
      ServerOps.setAutoFlush(false)
      FileSystemHelper.run(Map("Dummy.trigger" -> text)) { root: PathLike =>
        defaultOrg = Org.newOrg(root).asInstanceOf[OrgImpl]
        unmanagedType(TypeName(Name("__sfdc_trigger/Dummy"))).get
      }
    } finally {
      ServerOps.setAutoFlush(true)
    }
  }

  def triggerSummary(text: String, hasMessages: Boolean = false): TypeSummary = {
    val td = triggerDeclaration(text)
    assert(hasIssues == hasMessages)
    td.asInstanceOf[ApexFullDeclaration].summary
  }

  def unmanagedType(typeName: TypeName): Option[TypeDeclaration] = {
    defaultOrg.unmanaged.orderedModules.head.findModuleType(typeName)
  }

  def packagedType(namespace: Some[Name], typeName: TypeName): Option[TypeDeclaration] = {
    defaultOrg.packagesByNamespace(namespace).orderedModules.head.findModuleType(typeName)
  }

  def packagedType(namespace: Name, typeName: TypeName): Option[TypeDeclaration] = {
    packagedType(Some(namespace), typeName)
  }

  def hasIssues: Boolean = defaultOrg.issues.hasMessages

  def dummyIssues: String = defaultOrg.issues.getMessages("/Dummy.cls")
}
