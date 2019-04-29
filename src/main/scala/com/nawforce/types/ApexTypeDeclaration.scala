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

import com.nawforce.cst._
import com.nawforce.documents.LineLocation
import com.nawforce.parsers.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.parsers.{ApexLexer, ApexParser, CaseInsensitiveInputStream}
import com.nawforce.utils._
import org.antlr.v4.runtime.CommonTokenStream

import scala.collection.JavaConverters._

/** Apex type declaration, a wrapper around the Apex parser output */
abstract class ApexTypeDeclaration(val id: Id, val outerTypeName: Option[TypeName], val modifiers: Seq[Modifier],
                                   val bodyDeclarations: Seq[ClassBodyDeclaration])
  extends CST with ClassBodyDeclaration with TypeDeclaration {

  val name: Name = id.name
  val typeName: TypeName = ApexTypeDeclaration.typeName(name).withOuter(outerTypeName)
  val nature: Nature

  // TODO:
  val superClass: Option[TypeName] = None
  val interfaces: Seq[TypeName] = Seq()
  lazy val nestedTypes: Seq[TypeDeclaration] = {
    bodyDeclarations.flatMap {
      case x: TypeDeclaration => Some(x)
      case _ => None
    }
  }

  val fields: Seq[FieldDeclaration] = Seq()
  val methods: Seq[MethodDeclaration] = Seq()

  def verify()
  def resolve(index: CSTIndex)
}

object ApexTypeDeclaration {
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

        val cu = CompilationUnit.construct(path, parser.compilationUnit(), new ConstructContext())
        cu.verify()
        Some(cu.typeDeclaration)
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
      } else if (typeDecl.enumDeclaration() != null) {
        EnumDeclaration.construct(
          outerTypeName,
          ApexModifiers.construct(modifiers, context), typeDecl.enumDeclaration(), context)
      } else {
        // TODO: Empty type declaration?
        throw new CSTException()
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
}
