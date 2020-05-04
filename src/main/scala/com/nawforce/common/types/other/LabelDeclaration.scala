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
package com.nawforce.common.types.other

import com.nawforce.common.cst.{GLOBAL_MODIFIER, Modifier, PRIVATE_MODIFIER, STATIC_MODIFIER}
import com.nawforce.common.diagnostics.{Issue, UNUSED_CATEGORY}
import com.nawforce.common.documents._
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types._
import com.nawforce.common.xml.XMLElementLike

/** A individual Label being represented as a static field. */
case class Label(location: LocationImpl, name: Name, isProtected: Boolean) extends FieldDeclaration {
  override lazy val modifiers: Seq[Modifier] = Seq(STATIC_MODIFIER, GLOBAL_MODIFIER)
  override lazy val typeName: TypeName = TypeName.String
  override lazy val readAccess: Modifier = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
  override val idTarget: Option[TypeName] = None
}

object Label {
  /** Construct a label from a parsed XML element */
  def apply(path: PathLike, element: XMLElementLike): Label = {
    val fullName: String = element.getSingleChildAsString("fullName")
    val protect: Boolean = element.getSingleChildAsBoolean( "protected")
    Label(RangeLocationImpl(path, TextRange(element.line)), Name(fullName), protect)
  }
}

/** System.Label implementation. Provides access to labels in the package as well as labels that are accessible in
  * base packages via the Label.namespace.name format. */
final class LabelDeclaration(paths: Seq[PathLike], pkg: PackageImpl, labels: Seq[Label], packageLabels: Seq[TypeDeclaration])
  extends BasicTypeDeclaration(paths, pkg, TypeName.Label) {

  // Set individual labels to use this as the controller
  labels.foreach(_.setController(Some(this)))

  override val nestedTypes: Seq[TypeDeclaration] = packageLabels
  override val fields: Seq[FieldDeclaration] = labels

  /** Create new labels from merging those in the provided stream */
  def merge(stream: PackageStream): LabelDeclaration = {
    val newLabels = labels ++ stream.labels.map(le => Label(le.location, le.name, le.isProtected))
    val paths = stream.labelsFiles.map(l => PathFactory(l.location.path)).distinct
    new LabelDeclaration(paths, pkg, newLabels, packageLabels)
  }

  // Report on unused labels
  def unused(): Seq[Issue] = {
    labels.filterNot(_.hasHolders)
      .map(label => new Issue(UNUSED_CATEGORY, label.location, s"Label '$typeName.${label.name}'"))
  }
}

/** System.Label.ns implementation for exposing labels from dependent packages. Only public labels are visible through
  * this. As the exposed labels are owned elsewhere (by the passed LabelDeclaration) there is no need to set a
  * controller here.
  */
final class PackageLabels(pkg: PackageImpl, labelDeclaration: LabelDeclaration)
  extends InnerBasicTypeDeclaration(Seq.empty, pkg,
    TypeName(labelDeclaration.packageDeclaration.get.namespace.get, Nil, Some(TypeName.Label))) {

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    labelDeclaration.findField(name, staticContext) match {
      case Some(label: Label) if !label.isProtected => Some(label)
      case _ => None
    }
  }
}

/** System.Label.ns implementation for ghosted packages. This simulates the existence of any label you ask for. */
final class GhostedLabels(pkg: PackageImpl, ghostedNamespace: Name)
  extends InnerBasicTypeDeclaration(Seq(), pkg, TypeName(ghostedNamespace, Nil, Some(TypeName.Label))) {

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (staticContext.contains(true)) {
      Some(Label(LineLocationImpl(PathFactory(s"$name.labels").toString, 0), name, isProtected = false))
    } else {
      None
    }
  }
}

object LabelDeclaration {
  /** Construct System.Label for a package. */
  def apply(pkg: PackageImpl): LabelDeclaration = {
    new LabelDeclaration(Seq(), pkg, Seq(), createPackageLabels(pkg))
  }

  // Create labels declarations for each base package
  private def createPackageLabels(pkg: PackageImpl): Seq[TypeDeclaration] = {
    pkg.transitiveBasePackages.map(basePkg => {
      if (basePkg.isGhosted) {
        new GhostedLabels(pkg, basePkg.namespace.get)
      } else {
        new PackageLabels(pkg, basePkg.labels)
      }
    }).toSeq
  }
}
