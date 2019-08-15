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
package com.nawforce.types

import java.nio.file.{Files, Path}

import com.nawforce.api.Org
import com.nawforce.documents._
import com.nawforce.utils.{DotName, Issue, Name}
import com.nawforce.xml.XMLUtils.getLine
import com.nawforce.xml.{XMLException, XMLLineLoader, XMLUtils}

import scala.collection.mutable
import scala.xml.{Elem, SAXParseException}

final case class LabelDeclaration(pkg: PackageDeclaration) extends TypeDeclaration {
  private val labelNamespaces = mutable.Map[Name, LabelDeclaration]()
  private val labelFields = mutable.Map[Name, Label]()

  load()

  override val name: Name = Name.Label
  override val typeName: TypeName = TypeName.Label
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Seq.empty
  override val isComplete: Boolean = true
  override val isExternallyVisible: Boolean = true

  override val superClass: Option[TypeName] = Some(TypeName.SObject)
  override val interfaces: Seq[TypeName] = Seq.empty
  override val nestedTypes: Seq[TypeDeclaration] = labelNamespaces.values.toSeq

  override val blocks: Seq[BlockDeclaration] = Seq.empty
  override val fields: Seq[FieldDeclaration] = labelFields.values.toSeq
  override val constructors: Seq[ConstructorDeclaration] = Seq.empty
  override val methods: Seq[MethodDeclaration]= Seq.empty

  override def validate(): Unit = {}
  override def dependencies(): Set[TypeDeclaration] = Set.empty
  override def collectDependencies(dependencies: mutable.Set[TypeDeclaration]): Unit = {}

  override def validateReference(location: Location, dotName: DotName): Unit = {
    var position = 0
    var allowProtected = true
    var labelDeclaration = if (dotName.names.size > 1) labelNamespaces.get(dotName.names.head) else None
    if (labelDeclaration.isEmpty) {
      labelDeclaration = Some(this)
    } else {
      position = 1
      allowProtected = false
    }

    if (!labelDeclaration.get.pkg.isGhosted) {
      val label = labelDeclaration.get.labelFields.get(dotName.names(position))
      if (label.isEmpty)
        Org.logMessage(location, s"Label '$dotName' not found")
      else if (label.get.isProtected && !allowProtected)
        Org.logMessage(location, s"Label '$dotName' is protected so can not be accessed externally")
    }
  }

  def unused(): Seq[Issue] = Seq()

  private def load(): Unit = {
    pkg.documentsByExtension(Name("labels")).foreach(labelFile =>
      parseLabels(labelFile).foreach(label => labelFields.put(label.name, label))
    )
    pkg.basePackage().foreach(pkg => {
      labelNamespaces.put(pkg.namespace, pkg.labels())
    })
  }

  private def parseLabels(path: Path): Seq[Label] = {
    if (!Files.isRegularFile(path)) {
      Org.logMessage(LineLocation(path, 0), s"Expecting labels to be in a normal file")
      Seq()
    } else if (!Files.isReadable(path)) {
      Org.logMessage(LineLocation(path, 0), s"Labels file is not readable")
      Seq()
    } else {
      try {
        val root = XMLLineLoader.load(StreamProxy.getInputStream(path))
        XMLUtils.assertIs(root, "CustomLabels")

        root.child.flatMap {
          case elem: Elem =>
            XMLUtils.assertIs(elem, "labels")
            Some(Label(path, elem))
          case _ => None
        }

      } catch {
        case e: XMLException => Org.logMessage(RangeLocation(path, e.where), e.msg); Seq()
        case e: SAXParseException => Org.logMessage(PointLocation(path,
          Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage); Seq()
      }
    }
  }
}

case class Label(location: Location, fullName: String, isProtected: Boolean) extends FieldDeclaration {
  override val name: Name = Name(fullName)
  override lazy val modifiers: Seq[Modifier] = Seq(readAccess)
  override lazy val typeName: TypeName = TypeName.String
  override lazy val readAccess: Modifier = if (isProtected) PUBLIC_MODIFIER else GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
  override def dependencies(): Set[TypeDeclaration] = Set()
}

object Label {
  def apply(path: Path, element: Elem): Label = {

    val fullName: Option[String] = XMLUtils.getSingleChildAsString(element, "fullName")
    val protect: Option[Boolean] = XMLUtils.getSingleChildAsBoolean(element, "protected")

    /* Not needed so save some time parsing for now
    val language: Option[String] = XMLUtils.getSingleChildAsString(element, "language")
    val shortDescription: Option[String] = XMLUtils.getSingleChildAsString(element, "shortDescription")
    val value: Option[String] = XMLUtils.getSingleChildAsString(element, "value")
    val categories: Option[String] = XMLUtils.getOptionalSingleChildAsString(element, "categories")
     */

    Label(RangeLocation(path, TextRange(getLine(element))), fullName.get, protect.get)
  }
}


