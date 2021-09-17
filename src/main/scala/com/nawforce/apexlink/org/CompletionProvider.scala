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
import com.nawforce.apexlink.rpc.LocationLink
import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.types.apex.{ApexDeclaration, FullDeclaration, IdLocatable}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.documents.{ApexClassDocument, MetadataDocument}
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.{ByteArraySourceData, Locatable, UnsafeLocatable}

import java.io.{BufferedReader, StringReader}
import java.nio.charset.StandardCharsets
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.util.{Success, Try, Using}

trait CompletionProvider {
  this: PackageImpl =>

  def getCompletions(path: PathLike, line: Int, offset: Int, content: Option[String]): Array[CompletionItemLink] = {

    val sourceAndType = loadSourceAndType(path, content)
    if (sourceAndType.isEmpty)
      return Array.empty

    completionsFromValidation(sourceAndType.get._2, line, offset)
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
  private def completionsFromValidation(td: FullDeclaration, line: Int, offset: Int): Array[CompletionItemLink] = {
    getTypeBodyDeclaration(td, line, offset).foreach(typeAndBody => {

      // Validate the body declaration for the side-effect of being able to collect a map of expression results
      val typeContext = new TypeVerifyContext(None, typeAndBody._1, None)
      val resultMap = mutable.Map[Location, (CST, ExprContext)]()
      val context = new BodyDeclarationVerifyContext(typeContext, typeAndBody._2, Some(resultMap))
      context.disableIssueReporting() {
        typeAndBody._2.validate(context)
      }

      // Find the inner-most expression containing location from those that do
      val exprLocations = resultMap.keys.filter(_.contains(line, offset))
      val target = exprLocations.find(exprLocation => exprLocations.forall(_.contains(exprLocation)))

      Array()
    })
    Array()
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
  private def extractSearchTerm(source: String, line: Int, offset: Int): Option[(String, Location)] = {
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
          var canAppend = true
          parts.foreach(part => {
            if (canAppend) {
              if (searchTerm.nonEmpty)
                searchTerm.append(".")
              searchTerm.append(part)
              canAppend = start + searchTerm.length < offset
            }
          })
          (searchTerm.toString(), Location(line, start, line, start + searchTerm.length()))
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
      if (!ch.matches("[0-9a-zA-Z_\\.]")) {
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
