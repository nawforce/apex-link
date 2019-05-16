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

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.file.{Path, Paths}

import com.nawforce.cst._
import com.nawforce.documents.LineLocation
import com.nawforce.parsers.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.parsers.{ApexLexer, ApexParser, CaseInsensitiveInputStream}
import com.nawforce.utils._
import org.antlr.v4.runtime.CommonTokenStream

import scala.collection.JavaConverters._
import scala.collection.mutable

/** Apex type declaration, a wrapper around the Apex parser output. This is the base for classes, interfaces & enums*/
abstract class ApexTypeDeclaration(val id: Id, val outerTypeName: Option[TypeName], _modifiers: Seq[Modifier],
                                   val superClass: Option[TypeName], val interfaces: Seq[TypeName],
                                   val bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ClassBodyDeclaration(_modifiers) with TypeDeclaration {

  override def children(): List[CST] = bodyDeclarations.toList

  override val name: Name = id.name
  override val typeName: TypeName = ApexTypeDeclaration.typeName(name).withOuter(outerTypeName)
  override val nature: Nature

  override lazy val nestedTypes: Seq[TypeDeclaration] = {
    bodyDeclarations.flatMap {
      case x: TypeDeclaration => Some(x)
      case _ => None
    }
  }

  override val fields: Seq[FieldDeclaration] = {
    val fields = bodyDeclarations.flatMap {
      case x: FieldDeclaration => Some(x)
      case _ => None
    }
    fields.groupBy(f => f.name).collect {
      case (_, y :: Nil) => y
      case (_, duplicates) =>
        duplicates.tail.foreach(d => {
          IssueLog.logMessage(d.textRange, s"Duplicate field/property: '${d.name}'")
        })
        duplicates.head
    }.toSeq
  }

  override val constructors: Seq[ConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ConstructorDeclaration => Some(x)
      case _ => None
    }
  }

  override val methods: Seq[MethodDeclaration] = {
    bodyDeclarations.flatMap {
      case x: MethodDeclaration => Some(x)
      case _ => None
    }
  }

  protected def verify(imports: mutable.Set[TypeName]): Unit = {
    superClass.foreach(tn => imports.add(tn))
    interfaces.foreach(tn => imports.add(tn))
    bodyDeclarations.foreach(bd => imports ++= bd.imports)
    imports.diff(TypeName.ApexTypes).toSet
  }

  def resolve(index: CSTIndex)
}

object ApexTypeDeclaration {
  def clearCache(): Unit = {
    create(Paths.get("Dummy"), new ByteArrayInputStream("public class Dummy {}".getBytes()))
    System.gc()
  }

  def create(path: Path, data: InputStream): Option[ApexTypeDeclaration] = {
    try {
      IssueLog.context.withValue(path) {

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

        Some(CompilationUnit.construct(path, parser.compilationUnit(), new ConstructContext()).typeDeclaration)
      }
    } catch {
      case se: SyntaxException =>
        IssueLog.logMessage(LineLocation(path, se.line), se.msg)
        None
    }
  }

  def construct(outerTypeName: Option[TypeName], typeDecl: TypeDeclarationContext, context: ConstructContext)
  : ApexTypeDeclaration = {

    val modifiers: Seq[ModifierContext] = typeDecl.modifier().asScala
    val cst =
      if (typeDecl.classDeclaration() != null) {
        ClassDeclaration.construct(
          outerTypeName,
          ApexModifiers.classModifiers(modifiers, context, outer = outerTypeName.isEmpty, typeDecl.classDeclaration().id()),
          typeDecl.classDeclaration(), context)
      } else if (typeDecl.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(
          outerTypeName,
          ApexModifiers.construct(modifiers, context), typeDecl.interfaceDeclaration(), context)
      } else {
        assert(typeDecl.enumDeclaration() != null)
        EnumDeclaration.construct(
          outerTypeName,
          ApexModifiers.construct(modifiers, context), typeDecl.enumDeclaration(), context)
      }
    cst.withContext(typeDecl, context)
  }

  /** Create a TypeName from a name */
  private def typeName(name: Name): TypeName = {
    if (name == Name.Void) {
      TypeName.Void
    } else {
      TypeName(name)
    }
  }

  def parseBlock(path: Path, data: InputStream): Option[ApexParser.BlockContext] = {
    try {
      IssueLog.context.withValue(path) {

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
      }
    } catch {
      case se: SyntaxException =>
        IssueLog.logMessage(LineLocation(path, se.line), se.msg)
        None
    }
  }
}
