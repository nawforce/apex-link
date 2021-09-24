/*
 Copyright (c) 2021 Kevin Jones & FinancialForce, All rights reserved.
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
package com.nawforce.apexlink.org

import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.types.apex.{ApexDeclaration, FullDeclaration}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.documents.{ApexClassDocument, MetadataDocument}
import com.nawforce.pkgforce.modifiers.{PRIVATE_MODIFIER, PUBLIC_MODIFIER}
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.ByteArraySourceData

import java.io.{BufferedReader, StringReader}
import java.nio.charset.StandardCharsets
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.util.{Success, Try, Using}

trait CompletionProvider {
  this: PackageImpl =>

  def getCompletionItems(path: PathLike, line: Int, offset: Int, content: Option[String]): Array[CompletionItemLink] = {
    val sourceAndType = loadSourceAndType(path, content)
    if (sourceAndType.isEmpty) {
      return Array.empty
    }

    completionsFromValidation(sourceAndType.get._1, sourceAndType.get._2, line, offset)
  }

  private def loadSourceAndType(path: PathLike, content: Option[String]): Option[(String, FullDeclaration)] = {
    // We need source code no matter what
    val sourceOpt = content.orElse(path.read().toOption)
    if (sourceOpt.isEmpty)
      return None

    // If we don't have new source we can assume the loaded type is current, but it could be a summary
    if (content.isEmpty) {
      MetadataDocument(path)
        .collect { case doc: ApexClassDocument => doc }
        .flatMap(doc => orderedModules.view.flatMap(_.moduleType(doc.typeName(namespace))).headOption)
        .collect { case td: FullDeclaration => td }
        .orElse({
          // If not try and load a temp version to work with, this is expensive
          loadClass(path, sourceOpt.get)
        })
        .map(td => (sourceOpt.get, td))
    } else {
      // No option but to load it as content is being provided
      loadClass(path, sourceOpt.get).map(td => (sourceOpt.get, td))
    }
  }

  /** Extract a location link from an expression at the passed location */
  private def completionsFromValidation(source: String,
                                        td: FullDeclaration,
                                        line: Int,
                                        offset: Int): Array[CompletionItemLink] = {
    getExpressionFromValidation(source, td, line, offset) match {
      case Right(exprContext) =>
        exprContext._1.declaration match {
          //TODO: Fix modifier issues and add support for protected modifier.
          // For now we assume we have access to private items only when we fall back to using current declaration.
          case Some(typeDec) => return getAllCompletionItems(typeDec, exprContext._1.isStatic, exprContext._2)
          case _             => return getAllCompletionItems(td, exprContext._1.isStatic, exprContext._2, hasPrivateAccess = true)
        }
      case Left(result) =>
        //We only have access to the search term at this point and no context
        result match {
          case Some(searchTerm) => return getCompletionsFromTypeLookup(searchTerm, td)
          case None             =>
        }
    }
    Array.empty
  }

  private def getCompletionsFromTypeLookup(searchTermResult: (String, Location, String),
                                           fd: FullDeclaration): Array[CompletionItemLink] = {
    //TODO: Fix for Variables and other context. This only works when referring to type in a static context
    // as we cant get the type name from the search term.
    // The search term could be a variable in which case we need resolve the type name and the below wont work

    //Always assume a static context for now when we don't have an expression or context to work with
    locateFromTypeLookup(searchTermResult._1, searchTermResult._2, fd) match {
      case Some(ad) => getAllCompletionItems(ad, Some(true), searchTermResult._3)
      case None     => Array.empty
    }
  }

  private def getAllCompletionItems(td: TypeDeclaration,
                                    isStatic: Option[Boolean],
                                    filterBy: String,
                                    hasPrivateAccess: Boolean = false): Array[CompletionItemLink] = {
    var items = Array[CompletionItemLink]()
    items = items ++ td.methods
      .filter(_.isStatic == isStatic.getOrElse(false))
      .filter(_.modifiers.contains(if (hasPrivateAccess) PRIVATE_MODIFIER else PUBLIC_MODIFIER))
      .map(x =>
        new CompletionItemLink(x.name.toString + "(" + x.parameters.map(_.name.toString()).mkString(", ") + ")",
                               "Method"))
    items = items ++ td.fields
      .filter(_.isStatic == isStatic.getOrElse(false))
      .filter(_.modifiers.contains(if (hasPrivateAccess) PRIVATE_MODIFIER else PUBLIC_MODIFIER))
      .map(x => new CompletionItemLink(x.name.toString, "Field"))

    items = items ++ td.constructors.map(_ => new CompletionItemLink(td.name.value, "Constructor"))
    items = items ++ td.nestedTypes.map(x => new CompletionItemLink(x.name.value, "TypeParameter"))
    items.filter(x => x.label.toLowerCase().startsWith(filterBy.toLowerCase()))
  }

  private def getExpressionFromValidation(
    source: String,
    td: FullDeclaration,
    line: Int,
    offset: Int): Either[Option[(String, Location, String)], (ExprContext, String)] = {
    getTypeBodyDeclaration(td, line, offset)
      .flatMap(typeAndBody => {
        // Validate the body declaration for the side-effect of being able to collect a map of expression results
        val typeContext = new TypeVerifyContext(None, typeAndBody._1, None)
        val resultMap = mutable.Map[Location, (CST, ExprContext)]()
        val context = new BodyDeclarationVerifyContext(typeContext, typeAndBody._2, Some(resultMap))

        context.disableIssueReporting() {
          typeAndBody._2.validate(context)
        }
        extractSearchTerm(source, line, offset) match {
          case Some(searchTerm) =>
            val exprLocations = resultMap.keys.filter(_.contains(searchTerm._2.startLine, searchTerm._2.startPosition))
            exprLocations
              .find(exprLocation => exprLocations.forall(_.contains(exprLocation))) match {
              case Some(loc) =>
                return Right((resultMap(loc)._2, searchTerm._3))
              case None =>
                return Left(Some(searchTerm))
            }
          case _ =>
            return Left(None)
        }
      })
      .getOrElse(Left(None))
  }

  private def getTypeBodyDeclaration(typeDeclaration: TypeDeclaration,
                                     line: Int,
                                     offset: Int): Option[(FullDeclaration, ClassBodyDeclaration)] = {
    typeDeclaration match {
      case td: FullDeclaration =>
        td.nestedTypes.view
          .flatMap(td => getTypeBodyDeclaration(td, line, offset))
          .headOption
          .orElse({
            td.bodyDeclarations.find(_.location.location.contains(line, offset)).map((td, _))
          })
    }
  }

  private def locateFromTypeLookup(searchTerm: String,
                                   location: Location,
                                   from: FullDeclaration): Option[ApexDeclaration] = {
    TypeName(searchTerm).toOption match {
      case Some(typeName: TypeName) =>
        resolveTypeName(typeName, location, from)
      case _ => None
    }
  }

  /** Locate the ApexDeclaration for the passed typeName that was extracted from 'location' within 'from' */
  private def resolveTypeName(typeName: TypeName,
                              location: Location,
                              from: FullDeclaration): Option[ApexDeclaration] = {
    findEnclosingClass(from, location.startLine, location.startPosition).flatMap(td => {
      TypeResolver(typeName, td).toOption.collect { case td: ApexDeclaration => td }
    })
  }

  /** Find the outer or inner class that contains the passed cursor position */
  private def findEnclosingClass(td: FullDeclaration, line: Int, offset: Int): Option[FullDeclaration] = {
    td.nestedTypes
      .collect { case nested: FullDeclaration => nested }
      .find(_.location.location.contains(line, offset))
      .orElse({
        if (td.location.location.contains(line, offset))
          Some(td)
        else None
      })
  }

  /** Load a class to obtain it's FullDeclaration, issues are not updated, this is just a temporary version so
    * that can be inspected in case different contents are provided from the version saved on disk. */
  private def loadClass(path: PathLike, source: String): Option[FullDeclaration] = {
    MetadataDocument(path) match {
      case Some(doc: ApexClassDocument) =>
        getPackageModule(path).flatMap(module => {
          val existingIssues = org.issues.pop(path.toString)
          try {
            val asBytes = source.getBytes(StandardCharsets.UTF_8)
            FullDeclaration
              .create(module,
                      doc,
                      ByteArraySourceData(asBytes, 0, asBytes.length),
                      extendedApex = false,
                      forceConstruct = true)
          } catch {
            case _: Exception => None
          } finally {
            org.issues.push(path.toString, existingIssues)
          }
        })
      case _ => None
    }
  }

  /** Extract what to search for from source code given line & offset of cursor */
  private def extractSearchTerm(source: String, line: Int, offset: Int): Option[(String, Location, String)] = {
    val lineText: Option[String] = getLine(source, line - 1) match {
      case Success(Some(expr)) => Some(expr)
      case _                   => None
    }

    lineText.flatMap(lineText => {
      // Search backwards from -1 as selection cursor position is on next character which is possibly not legal
      findLimit(forward = false, lineText, offset - 1).flatMap(start => {
        findLimit(forward = true, lineText, start).map(end => {
          // Split & rebuild so not so sensitive to cursor being close to a "."
          val searchTerm = new mutable.StringBuilder()
          val parts = lineText.substring(start, end + 1).split('.')
          val dotOffset = if (parts.length > 1) 1 else -1
          var canAppendNext = true
          parts.foreach(part => {
            canAppendNext = start + searchTerm.length + part.length + dotOffset < offset
            if (canAppendNext) {
              if (searchTerm.nonEmpty)
                searchTerm.append(".")
              searchTerm.append(part)
            }
          })
          (searchTerm.toString(), Location(line, start, line, start + searchTerm.length()), parts.last)
        })
      })
    })
  }

  /** Search for limit of a search term either forwards or backwards */
  private def findLimit(forward: Boolean, content: String, offset: Int): Option[Int] = {
    if (offset < 0 || offset >= content.length) {
      None
    } else {
      val ch = "" + content(offset)
      if (!ch.matches("[0-9a-zA-Z_\\.]()")) {
        None
      } else {
        val nextOffset = if (forward) offset + 1 else offset - 1
        Some(findLimit(forward, content, nextOffset).getOrElse(offset))
      }
    }
  }

  /** Find a specific line in source contents */
  private def getLine(contents: String, line: Int): Try[Option[String]] = {
    Using(new BufferedReader(new StringReader(contents))) { reader =>
      val lines = reader.lines().iterator().asScala.toArray
      if (line >= 0 && line < lines.length)
        Some(lines(line))
      else
        None
    }
  }
}
