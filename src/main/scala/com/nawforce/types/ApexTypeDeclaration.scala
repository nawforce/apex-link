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

import java.io.InputStream
import java.nio.file.Path

import com.nawforce.api.Org
import com.nawforce.cst._
import com.nawforce.documents.LineLocation
import com.nawforce.parsers.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.parsers.{ApexLexer, ApexParser, CaseInsensitiveInputStream}
import com.nawforce.utils._
import org.antlr.v4.runtime.CommonTokenStream

import scala.collection.JavaConverters._

/** Apex type declaration, a wrapper around the Apex parser output. This is the base for classes, interfaces & enums*/
abstract class ApexTypeDeclaration(val id: Id, val outerContext: Either[Name, TypeName],
                                   _modifiers: Seq[Modifier], val superClass: Option[TypeName],
                                   val interfaces: Seq[TypeName], val bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ClassBodyDeclaration(_modifiers) with TypeDeclaration {

  override def children(): List[CST] = bodyDeclarations.toList

  override val name: Name = id.name
  override val path: Path = Org.current.value.issues.context.value
  override val typeName: TypeName = {
    outerContext match {
      case Left(ns) if ns == Name.Empty => TypeName(name)
      case Left(ns) => TypeName(name).withOuter(Some(TypeName(ns)))
      case Right(outer) => TypeName(name).withOuter(Some(outer))
    }
  }
  override val outerTypeName: Option[TypeName] = {
    outerContext match {
      case Left(_) => None
      case Right(outer) => Some(outer)
    }
  }

  override val nature: Nature

  private var superTypeDeclaration: Option[TypeDeclaration] = None
  private var isComplete = false

  override val nestedTypes: Seq[ApexTypeDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ApexTypeDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val blocks: Seq[BlockDeclaration] = {
    bodyDeclarations.flatMap {
      case x: BlockDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val fields: Seq[FieldDeclaration] = {
    Org.current.value.issues.context.withValue(path) {

      val fields = bodyDeclarations.flatMap {
        case x: FieldDeclaration => Some(x)
        case _ => None
      }
      val allFields = superTypeDeclaration.map(_.fields).getOrElse(Seq()) ++ fields.groupBy(f => f.name).collect {
        case (_, y :: Nil) => y
        case (_, duplicates) =>
          duplicates.tail.foreach(d => {
            Org.logMessage(d.textRange, s"Duplicate field/property: '${d.name}'")
          })
          duplicates.head
      }.toSeq

      allFields.map(f => (f.name, f)).toMap.values.toSeq
    }
  }

  override lazy val constructors: Seq[ConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ConstructorDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val methods: Seq[MethodDeclaration] = {
    bodyDeclarations.flatMap {
      case x: MethodDeclaration => Some(x)
      case _ => None
    }
  }

  override def validate(): Unit = {
    val context = new TypeVerifyContext(None, this)
    if (depends.isEmpty) {
      verify(context)
      depends = Some(context.dependencies)
    }
  }

  protected def verify(context: TypeVerifyContext): Unit = {
    if (depends.nonEmpty)
      return

    superTypeDeclaration = superClass.flatMap(superType => context.getTypeAndAddDependency(superType))
    if (superClass.nonEmpty) {
      if (superTypeDeclaration.isEmpty) {
        Org.logMessage(id.textRange, s"No type declaration found for '${superClass.get.asDotName}'")
      } else if (superTypeDeclaration.get.nature != CLASS_NATURE) {
        Org.logMessage(id.textRange, s"Parent type '${superClass.get.asDotName}' must be a class")
      } else if (superTypeDeclaration.get.modifiers.intersect(Seq(VIRTUAL_MODIFIER, ABSTRACT_MODIFIER)).isEmpty) {
        Org.logMessage(id.textRange, s"Parent class '${superClass.get.asDotName}' must be declared virtual or abstract")
      }
    }
    isComplete = superTypeDeclaration.isDefined || superClass.isEmpty

    val duplicateNestedType = (this +: nestedTypes).groupBy(_.name).collect { case (_, List(_, y, _*)) => y }
    duplicateNestedType.foreach(td =>
      Org.logMessage(td.id.textRange, s"Duplicate type name '${td.name.toString}'"))

    // TODO: Check for interfaces
    interfaces.foreach(context.getTypeAndAddDependency)
    bodyDeclarations.foreach(bd => bd.validate(new BodyDeclarationVerifyContext(context, bd)))

    depends = Some(context.dependencies)
  }

  def resolve(index: CSTIndex)
}

object ApexTypeDeclaration {
  def create(namespace: Name, path: Path, data: InputStream): Option[ApexTypeDeclaration] = {
    Org.current.value.issues.context.withValue(path) {
      try {
        val listener = new ThrowingErrorListener
        val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(data)
        val lexer: ApexLexer = new ApexLexer(cis)
        lexer.removeErrorListeners()
        lexer.addErrorListener(listener)

        val tokens: CommonTokenStream = new CommonTokenStream(lexer)
        tokens.fill()

        val parser: ApexParser = new ApexParser(tokens)
        parser.removeErrorListeners()
        parser.setTrace(false)
        parser.addErrorListener(listener)

        Some(CompilationUnit.construct(namespace, path, parser.compilationUnit(), new ConstructContext()).typeDeclaration())
      }
      catch
      {
        case se: SyntaxException =>
          Org.logMessage(LineLocation(path, se.line), se.msg)
          None
      }
    }
  }

  def construct(outerContext: Either[Name, TypeName], typeDecl: TypeDeclarationContext, context: ConstructContext)
  : ApexTypeDeclaration = {

    val modifiers: Seq[ModifierContext] = typeDecl.modifier().asScala
    val isOuter = outerContext.isLeft
    val cst =
      if (typeDecl.classDeclaration() != null) {
        ClassDeclaration.construct(
          outerContext,
          ApexModifiers.classModifiers(modifiers, context, outer = isOuter, typeDecl.classDeclaration().id()),
          typeDecl.classDeclaration(), context)
      } else if (typeDecl.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(
          outerContext,
          ApexModifiers.interfaceModifiers(modifiers, context, outer = isOuter, typeDecl.interfaceDeclaration().id()),
          typeDecl.interfaceDeclaration(), context)
      } else {
        assert(typeDecl.enumDeclaration() != null)
        EnumDeclaration.construct(
          outerContext,
          ApexModifiers.enumModifiers(modifiers, context, outer = isOuter, typeDecl.enumDeclaration().id()),
          typeDecl.enumDeclaration(), context)
      }
    cst.withContext(typeDecl, context)
  }

  def parseBlock(path: Path, data: InputStream): Option[ApexParser.BlockContext] = {
    Org.current.value.issues.context.withValue(path) {
      try {

        val listener = new ThrowingErrorListener
        val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(data)
        val lexer: ApexLexer = new ApexLexer(cis)
        lexer.removeErrorListeners()
        lexer.addErrorListener(listener)

        val tokens: CommonTokenStream = new CommonTokenStream(lexer)
        tokens.fill()

        val parser: ApexParser = new ApexParser(tokens)
        parser.removeErrorListeners()
        parser.setTrace(false)
        parser.addErrorListener(listener)
        Some(parser.block())
      } catch {
        case se: SyntaxException =>
          Org.logMessage(LineLocation(path, se.line), se.msg)
          None
      }
    }
  }
}
