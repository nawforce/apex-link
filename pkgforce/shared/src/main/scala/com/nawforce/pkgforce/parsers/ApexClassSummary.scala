/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.parsers

import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.diagnostics.Duplicates.IterableOps
import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue}
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier, WEBSERVICE_MODIFIER}
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{IdLocatable, Location, PathLocation}
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.compat.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer

sealed abstract class Nature(val value: String)
case object CLASS_NATURE         extends Nature("class")
case object INTERFACE_NATURE     extends Nature("interface")
case object ENUM_NATURE          extends Nature("enum")
case object TRIGGER_NATURE       extends Nature(value = "trigger")
case object CONSTRUCTOR_NATURE   extends Nature(value = "constructor")
case object METHOD_NATURE        extends Nature(value = "method")
case object FIELD_NATURE         extends Nature(value = "field")
case object PROPERTY_NATURE      extends Nature(value = "property")
case object ENUM_CONSTANT_NATURE extends Nature(value = "enum constant")
case object INIT_NATURE          extends Nature(value = "<init>")

object Nature {
  def forType(value: String): Nature = {
    value match {
      case CLASS_NATURE.value     => CLASS_NATURE
      case INTERFACE_NATURE.value => INTERFACE_NATURE
      case ENUM_NATURE.value      => ENUM_NATURE
      case TRIGGER_NATURE.value   => TRIGGER_NATURE
    }
  }
}

trait ApexNode extends IdLocatable {
  val nature: Nature
  val name: Name
  val children: ArraySeq[ApexNode]
  val modifiers: ArraySeq[Modifier]
  val parseIssues: ArraySeq[Issue]
  val signature: String
  val description: String

  def collectIssues(): ArraySeq[Issue] = {
    val issues = new ArrayBuffer[Issue]()
    collectIssues(issues)
    ArraySeq.unsafeWrapArray(issues.toArray)
  }

  protected def collectIssues(issues: ArrayBuffer[Issue]): Unit = {
    issues.addAll(parseIssues)
    localIssues.foreach(issue => issues.append(issue))
    children.foreach(_.collectIssues(issues))
  }

  def localIssues: Seq[Issue] = {
    if (nature == CLASS_NATURE) {
      checkNeedsGlobalOrWebService() ++
        checkMisnamedConstructors() ++
        checkDuplicateConstructors() ++
        checkDuplicateMethods()
    } else {
      Seq.empty
    }
  }

  private def checkNeedsGlobalOrWebService(): Seq[Issue] = {
    if (!modifiers.contains(GLOBAL_MODIFIER)) {
      children
        .filter(_.modifiers.intersect(Seq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER)).nonEmpty)
        .map(
          child =>
            new Issue(
              location.path,
              Diagnostic(
                ERROR_CATEGORY,
                child.idLocation,
                "Enclosing class must be declared global to use global or webservice modifiers"
              )
            )
        )
    } else {
      Seq.empty
    }
  }

  private def checkMisnamedConstructors(): Seq[Issue] = {
    children
      .filter(child => child.nature == CONSTRUCTOR_NATURE && child.name != name)
      .map(misnamed => {
        new Issue(
          location.path,
          Diagnostic(
            ERROR_CATEGORY,
            misnamed.idLocation,
            s"Constructors should have same name as the class, maybe method return type is missing?"
          )
        )
      })
  }

  // This is a weak test as types have not been normalised
  private def checkDuplicateConstructors(): Seq[Issue] = {
    children
      .collect { case n: ApexConstructorNode => n }
      .duplicates(_.compareString)
      .flatMap(duplicates => {
        duplicates._2.map(dup => {
          new Issue(
            location.path,
            Diagnostic(
              ERROR_CATEGORY,
              dup.idLocation,
              s"Constructor is a duplicate of an earlier constructor at ${duplicates._1.idLocation.displayPosition}"
            )
          )
        })
      })
      .toSeq
  }

  private def checkDuplicateMethods(): Seq[Issue] = {
    children
      .collect { case n: ApexMethodNode => n }
      .duplicates(_.compareString)
      .flatMap(duplicates => {
        duplicates._2.map(dup => {
          new Issue(
            location.path,
            Diagnostic(
              ERROR_CATEGORY,
              dup.idLocation,
              s"Method is a duplicate of an earlier method at ${duplicates._1.idLocation.displayPosition}"
            )
          )
        })
      })
      .toSeq
  }
}

object ApexNode {
  def apply(parser: CodeParser, ctx: CompilationUnitContext): Option[ApexNode] = {
    val visitor = new ApexClassVisitor(parser)
    visitor.visit(ctx).headOption
  }

  def apply(parser: CodeParser, ctx: TriggerUnitContext): Option[ApexNode] = {
    val visitor = new ApexClassVisitor(parser)
    visitor.visit(ctx).headOption
  }

  def appendSpace(str: String): String = {
    if (str.nonEmpty)
      str + " "
    else
      str
  }
}

class ApexLightNode(
  val location: PathLocation,
  val nature: Nature,
  val name: Name,
  val idLocation: Location,
  val children: ArraySeq[ApexNode],
  val modifiers: ArraySeq[Modifier],
  override val signature: String,
  override val description: String,
  val parseIssues: ArraySeq[Issue]
) extends ApexNode {}

case class ApexFormalParameter(
  modifiers: ArraySeq[Modifier],
  typeName: String,
  name: String,
  parseIssues: ArraySeq[Issue]
) {
  def toStringNoName: String =
    s"${ApexNode.appendSpace(modifiers.map(_.name).sorted.mkString(" "))}$typeName"

  override def toString: String = s"$toStringNoName $name"
}

case class ApexConstructorNode(
  location: PathLocation,
  name: Name,
  idLocation: Location,
  children: ArraySeq[ApexLightNode],
  modifiers: ArraySeq[Modifier],
  parseIssues: ArraySeq[Issue],
  params: ArraySeq[ApexFormalParameter]
) extends ApexNode {

  override val nature: Nature = CONSTRUCTOR_NATURE
  override lazy val signature: String =
    s"${ApexNode.appendSpace(modifiers.mkString(" "))}$name(${params.mkString(", ")})"
  override lazy val description: String = s"(${params.mkString(", ")}) ${modifiers.mkString(" ")}"
  lazy val compareString: String =
    s"$name(${params.map(_.toStringNoName).mkString(", ")}".toLowerCase()
}

case class ApexMethodNode(
  location: PathLocation,
  name: Name,
  idLocation: Location,
  children: ArraySeq[ApexLightNode],
  modifiers: ArraySeq[Modifier],
  parseIssues: ArraySeq[Issue],
  returnType: String,
  params: ArraySeq[ApexFormalParameter]
) extends ApexNode {

  override val nature: Nature = METHOD_NATURE
  override lazy val signature: String =
    s"${ApexNode.appendSpace(modifiers.mkString(" "))}$returnType $name(${params.mkString(", ")})"
  override lazy val description: String =
    s"$returnType (${params.mkString(", ")}) ${modifiers.mkString(" ")}"
  lazy val compareString: String =
    s"${ApexNode.appendSpace(modifiers.map(_.name).sorted.mkString(" "))} " +
      s"$returnType $name(${params.map(_.toStringNoName).mkString(", ")}".toLowerCase()
}
