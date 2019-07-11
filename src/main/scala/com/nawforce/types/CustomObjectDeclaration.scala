package com.nawforce.types

import java.io.InputStream
import java.nio.file.Path

import com.nawforce.documents.DocumentType
import com.nawforce.utils.Name

final case class CustomObjectDeclaration(path: Path, typeName: TypeName) extends TypeDeclaration {
  val name: Name = typeName.name
  val outerTypeName: Option[TypeName] = None
  val nature: Nature = CLASS_NATURE
  val modifiers: Seq[Modifier] = Seq.empty

  val superClass: Option[TypeName] = Some(TypeName.SObject)
  val interfaces: Seq[TypeName] = Seq.empty
  val nestedTypes: Seq[TypeDeclaration] = Seq.empty

  val blocks: Seq[BlockDeclaration] = Seq.empty
  val fields: Seq[FieldDeclaration]= Seq.empty
  val constructors: Seq[ConstructorDeclaration] = Seq.empty
  val methods: Seq[MethodDeclaration]= Seq.empty

  def validate(): Unit = {}
  def dependencies(): Set[DependencyDeclaration] = Set.empty
}

object CustomObjectDeclaration {
  def create(namespace: Name, path: Path, data: InputStream): Option[CustomObjectDeclaration] = {
    val name = DocumentType.apply(path).get.name
    Some(new CustomObjectDeclaration(path, TypeName(name, Nil,
      if (namespace.value.isEmpty) None else Some(TypeName(namespace)))))
  }
}