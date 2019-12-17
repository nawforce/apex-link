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

import com.nawforce.api.Org
import com.nawforce.diagnostics.{Issue, UNUSED_CATEGORY}
import com.nawforce.documents._
import com.nawforce.names.{Name, TypeName}
import com.nawforce.path.{FILE, PathFactory, PathLike}
import com.nawforce.runtime.Path
import com.nawforce.xml.XMLUtils.getLine
import com.nawforce.xml.{XMLException, XMLLineLoader, XMLUtils}

import scala.collection.mutable
import scala.xml.{Elem, SAXParseException}

final case class LabelDeclaration(pkg: PackageDeclaration, name: Name, labelFields: Seq[Label], labelNamespaces: Seq[TypeDeclaration])
  extends TypeDeclaration {

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override val typeName: TypeName =
    if (name == Name.Label) TypeName.Label else TypeName(name, Nil, Some(TypeName.Label))
  override val outerTypeName: Option[TypeName] = typeName.outer

  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Seq.empty
  override val isComplete: Boolean = true
  override val isExternallyVisible: Boolean = true

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Seq.empty
  override val nestedTypes: Seq[TypeDeclaration] = labelNamespaces

  override val blocks: Seq[BlockDeclaration] = Seq.empty
  override val fields: Seq[FieldDeclaration] = labelFields
  override val constructors: Seq[ConstructorDeclaration] = Seq.empty
  override val methods: Seq[MethodDeclaration]= Seq.empty

  override def validate(): Unit = {}
  override def collectDependencies(dependencies: mutable.Set[Dependant]): Unit = {}

  def unused(): Seq[Issue] = {
    labelFields.filterNot(_.hasHolders)
      .map(label => Issue(UNUSED_CATEGORY, label.location, s"Label '$typeName.${label.name}'"))
  }
}

final case class GhostedLabelDeclaration(pkg: PackageDeclaration, name: Name)
  extends TypeDeclaration {

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override val typeName: TypeName = TypeName(name, Nil, Some(TypeName.Label))
  override val outerTypeName: Option[TypeName] = typeName.outer

  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Seq.empty
  override val isComplete: Boolean = false
  override val isExternallyVisible: Boolean = true

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Seq.empty
  override val nestedTypes: Seq[TypeDeclaration] = Seq.empty

  override val blocks: Seq[BlockDeclaration] = Seq.empty
  override val fields: Seq[FieldDeclaration] = Seq.empty
  override val constructors: Seq[ConstructorDeclaration] = Seq.empty
  override val methods: Seq[MethodDeclaration]= Seq.empty

  override def validate(): Unit = {}
  override def collectDependencies(dependencies: mutable.Set[Dependant]): Unit = {}

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (staticContext.contains(true)) {
      Some(Label(LineLocation(PathFactory(s"$name.labels"), 0), name, isProtected = false))
    } else {
      None
    }
  }
}

object LabelDeclaration {
  def apply(pkg: PackageDeclaration): LabelDeclaration = {
    val labels = pkg.documentsByExtension(Name("labels"))
      .flatMap(labelFile => parseLabels(labelFile.path))
    val baseLabels = collectBaseLabels(pkg)
    LabelDeclaration(pkg, Name.Label, labels, baseLabels.values.toSeq)
  }

  private def collectBaseLabels(pkg: PackageDeclaration, collected: mutable.Map[Name, TypeDeclaration]=mutable.Map())
    : mutable.Map[Name, TypeDeclaration] = {
    pkg.basePackages.foreach(basePkg => {
      val ns = basePkg.namespace.get
      if (!collected.contains(ns)) {
        if (basePkg.isGhosted) {
          collected.put(ns, GhostedLabelDeclaration(pkg, ns))
        } else {
          val labels = LabelDeclaration(pkg, ns,
            basePkg.labels().labelFields.filterNot(label => label.isProtected),
            Seq()
          )
          collected.put(ns, labels)
          collectBaseLabels(basePkg, collected)
        }
      }
    })
    collected
  }

  private def parseLabels(path: PathLike): Seq[Label] = {
    if (!path.nature.isInstanceOf[FILE]) {
      Org.logMessage(LineLocation(path, 0), s"Expecting labels to be in a normal file")
      Seq()
    } else {
      try {
        val root = XMLLineLoader.load(StreamProxy.getInputStream(path))
        XMLUtils.assertIs(root, "CustomLabels")

        root.child.flatMap {
          case elem: Elem =>
            XMLUtils.assertIs(elem, "labels")
            Some(Label(path.native.asInstanceOf[java.nio.file.Path], elem))
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

case class Label(location: Location, name: Name, isProtected: Boolean) extends FieldDeclaration {
  override lazy val modifiers: Seq[Modifier] = Seq(STATIC_MODIFIER, GLOBAL_MODIFIER)
  override lazy val typeName: TypeName = TypeName.String
  override lazy val readAccess: Modifier = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
}

object Label {
  def apply(path: java.nio.file.Path, element: Elem): Label = {

    val fullName: String = XMLUtils.getSingleChildAsString(element, "fullName")
    val protect: Boolean = XMLUtils.getSingleChildAsBoolean(element, "protected")

    /* Not needed so save some time parsing for now
    val language: Option[String] = XMLUtils.getSingleChildAsString(element, "language")
    val shortDescription: Option[String] = XMLUtils.getSingleChildAsString(element, "shortDescription")
    val value: Option[String] = XMLUtils.getSingleChildAsString(element, "value")
    val categories: Option[String] = XMLUtils.getOptionalSingleChildAsString(element, "categories")
     */

    Label(RangeLocation(Path(path), TextRange(getLine(element))), Name(fullName), protect)
  }
}
