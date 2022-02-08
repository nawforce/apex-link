/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink

import com.nawforce.apexlink.api.{Org, ServerOps, TypeSummary}
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexlink.plugins.{PluginsManager, UnusedPlugin}
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, ApexFullDeclaration, FullDeclaration}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.schema.SObjectDeclaration
import com.nawforce.pkgforce.diagnostics.UNUSED_CATEGORY
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.PathLike

trait TestHelper {

  private var defaultOrg: OrgImpl = _
  private var root: PathLike      = _

  def createOrg(path: PathLike): OrgImpl = {
    val plugins = PluginsManager.overridePlugins(Seq())
    try {
      defaultOrg = Org.newOrg(path).asInstanceOf[OrgImpl]
      defaultOrg
    } finally {
      PluginsManager.overridePlugins(plugins)
    }
  }

  def createOrgWithUnused(path: PathLike): OrgImpl = {
    val plugins = PluginsManager.overridePlugins(Seq(classOf[UnusedPlugin]))
    try {
      defaultOrg = Org.newOrg(path).asInstanceOf[OrgImpl]
      defaultOrg
    } finally {
      PluginsManager.overridePlugins(plugins)
    }
  }

  def createHappyOrg(path: PathLike): OrgImpl = {
    createOrg(path)
    assert(!hasIssues)
    defaultOrg
  }

  def emptyOrg(): OrgImpl = {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      this.root = root
      createOrg(root)
    }
  }

  def withOrg[T](op: OrgImpl => T): T = {
    OrgImpl.current.withValue(defaultOrg) {
      op(defaultOrg)
    }
  }

  def withManualFlush[T](op: => T): T = {
    val current = ServerOps.setAutoFlush(false)
    try {
      op
    } finally {
      ServerOps.setAutoFlush(current)
    }
  }

  def withEmptyOrg[T](op: OrgImpl => T): T = {
    val org = emptyOrg()
    OrgImpl.current.withValue(org) {
      op(org)
    }
  }

  def typeDeclarations(classes: Map[String, String]): Seq[ApexClassDeclaration] = {
    try {
      ServerOps.setAutoFlush(false)
      FileSystemHelper.run(classes) { root: PathLike =>
        this.root = root
        createOrg(root)
        classes.keys
          .map(cls => unmanagedClass(cls.replace(".cls", "")).get)
          .toSeq
      }
    } finally {
      ServerOps.setAutoFlush(true)
    }
  }

  def typeDeclaration(clsText: String): ApexClassDeclaration = {
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
        this.root = root
        createOrg(root)
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

  def unmanagedClass(name: String): Option[ApexClassDeclaration] = {
    unmanagedType(TypeName(Name(name))).map(_.asInstanceOf[ApexClassDeclaration])
  }

  def unmanagedSObject(name: String): Option[SObjectDeclaration] = {
    unmanagedType(TypeName(Name(name), Nil, Some(TypeName(Names.Schema))))
      .map(_.asInstanceOf[SObjectDeclaration])
  }

  def packagedType(namespace: Some[Name], typeName: TypeName): Option[TypeDeclaration] = {
    defaultOrg.packagesByNamespace(namespace).orderedModules.head.findModuleType(typeName)
  }

  def packagedType(namespace: Name, typeName: TypeName): Option[TypeDeclaration] = {
    packagedType(Some(namespace), typeName)
  }

  def packagedCustomType(namespace: String, name: String): Option[TypeDeclaration] = {
    packagedType(
      Some(Name(namespace)),
      TypeName(Name(name), Seq(), Some(TypeName(Name(namespace))))
    )
  }

  def packagedClass(namespace: String, name: String): Option[ApexClassDeclaration] = {
    packagedCustomType(namespace, name).map(_.asInstanceOf[ApexClassDeclaration])
  }

  def packagedSObject(namespace: String, name: String): Option[SObjectDeclaration] = {
    packagedType(Name(namespace), TypeName(Name(name), Nil, Some(TypeName(Names.Schema))))
      .map(_.asInstanceOf[SObjectDeclaration])
  }

  def hasIssues: Boolean = defaultOrg.issues.nonEmpty

  def getMessages(org: OrgImpl = defaultOrg): String = {
    val messages = org.issues
      .issuesForFilesInternal(paths = null, includeWarnings = true, maxIssuesPerFile = 10)
      .mkString("\n")
    // For backward compatability with earlier behaviour
    if (messages.nonEmpty) messages + "\n" else ""
  }

  def getMessages(path: PathLike): String = {
    val messages = defaultOrg.issues
      .issuesForFileInternal(path)
      .map(_.asString())
      .mkString("\n")
    // For backward compatibility with earlier behaviour
    if (messages.nonEmpty) messages + "\n" else ""
  }

  def dummyIssues: String = getMessages(root.join("Dummy.cls"))

  def customObject(
    label: String,
    fields: Seq[(String, Option[String], Option[String])],
    fieldSets: Set[String] = Set(),
    sharingReason: Set[String] = Set(),
    sharingModel: String = "",
    extending: Boolean = false
  ): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        ${if (field._2.nonEmpty) s"<type>${field._2.get}</type>" else ""}
         |        ${if (field._3.nonEmpty) s"<referenceTo>${field._3.get}</referenceTo>" else ""}
         |        ${if (field._3.nonEmpty)
        s"<relationshipName>${field._1.replaceAll("__c$", "")}</relationshipName>"
      else ""}
         |    </fields>
         |""".stripMargin
    })

    val fieldSetMetadata = fieldSets.map(fieldSet => {
      s"""
         |    <fieldSets>
         |        <fullName>$fieldSet</fullName>
         |    </fieldSets>
         |""".stripMargin
    })

    val sharingReasonMetadata = sharingReason.map(sharingReason => {
      s"""
         |    <sharingReasons>
         |        <fullName>$sharingReason</fullName>
         |    </sharingReasons>
         |""".stripMargin
    })

    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$label</fullName>
       |    ${if (!extending) "<label/>" else ""}
       |    ${if (!extending) "<pluralLabel/>" else ""}
       |    ${if (!extending) "<nameField/>" else ""}
       |    ${if (!extending) "<deploymentStatus/>" else ""}
       |    ${if (sharingModel.nonEmpty) s"<sharingModel>$sharingModel</sharingModel>" else ""}
       |    $fieldMetadata
       |    $fieldSetMetadata
       |    $sharingReasonMetadata
       |</CustomObject>
       |""".stripMargin
  }

  def customField(name: String, fieldType: String, relationshipName: Option[String]): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomField xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |    <type>$fieldType</type>
       |    ${if (relationshipName.nonEmpty) s"<referenceTo>${relationshipName.get}</referenceTo>"
    else ""}
       |    ${if (relationshipName.nonEmpty)
      s"<relationshipName>${name.replaceAll("__c$", "")}</relationshipName>"
    else ""}
       |</CustomField>
       |""".stripMargin
  }

  def customFieldSet(name: String): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<FieldSet xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |</FieldSet>
       |""".stripMargin
  }

  def customSharingReason(name: String): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<SharingReason xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |</SharingReason>
       |""".stripMargin
  }
}
