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
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.rpc.LocationLink
import com.nawforce.apexlink.types.apex.{ApexDeclaration, ApexFieldLike, ApexMethodLike, FullDeclaration}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.{Location, LoggerOps}
import com.nawforce.pkgforce.documents.{ApexClassDocument, MetadataDocument}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.ByteArraySourceData

import java.io.{BufferedReader, StringReader}
import java.nio.charset.StandardCharsets
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.util.{Success, Try, Using}

trait DefinitionProvider {
  this: PackageImpl =>

  def getDefinition(path: PathLike, line: Int, offset: Int, content: Option[String]): Array[LocationLink] = {
    // Make sure we have access to source code and a type to resolve things against
    val sourceAndType = loadSourceAndType(path, content)
    if (sourceAndType.isEmpty)
      return Array.empty

    // Extract something to search for from the source
    val source = sourceAndType.get._1
    val sourceTD = sourceAndType.get._2
    val searchTermAndLocation = extractSearchTerm(source, line, offset)
    if (searchTermAndLocation.isEmpty)
      return Array.empty
    val searchTerm = searchTermAndLocation.get._1
    val sourceLocation = searchTermAndLocation.get._2

    locateFromTypeLookup(searchTerm, sourceLocation, sourceTD)
      .getOrElse(locateFromValidation(sourceTD, sourceLocation))

    /*
    val exprLocationAndType = fullExprAndLocation
      .flatMap(expr => findTypeInExpression(path, line, offset, source, expr))

    if (fullExprAndLocation.isEmpty || exprLocationAndType.isEmpty)
      return Array.empty

    val loc = locationForStaticMethodOrField(exprLocationAndType.get._1,
                                             fullExprAndLocation.get._2,
                                             exprLocationAndType.get._3,
                                             fullExprAndLocation.get._1)
    if (loc.isEmpty) {
      exprLocationAndType match {
        case Some((_: String, srcLocation: Location, ad: ApexDeclaration)) =>
          Array(LocationLink(srcLocation, ad.path.toString, ad.fullLocation, ad.nameLocation))
        case _ => Array.empty
      }
    } else loc
   */
  }

  private def locateFromTypeLookup(searchTerm: String,
                                   location: Location,
                                   from: FullDeclaration): Option[Array[LocationLink]] = {
    TypeName(searchTerm).toOption match {
      case Some(typeName: TypeName) =>
        resolveTypeName(typeName, location, from)
          .map(ad => {
            Array(LocationLink(location, ad.location.path, ad.location.location, ad.idLocation))
          })
      case _ => None
    }
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
  private def locateFromValidation(td: FullDeclaration, location: Location): Array[LocationLink] = {
    LoggerOps.debugTime("locateFromValidation") {
      getTypeBodyDeclaration(td, location.startLine, location.endPosition).foreach(typeAndBody => {
        val typeContext = new TypeVerifyContext(None, typeAndBody._1, None)
        val exprMap = mutable.Map[Location, (Expression, ExprContext)]()
        val context = new BodyDeclarationVerifyContext(typeContext, typeAndBody._2, Some(exprMap))
        context.disableIssueReporting() {
          typeAndBody._2.validate(context)
        }
        exprMap.keys
          .find(_.contains(location.startLine, location.endPosition))
          .foreach(loc => {
            exprMap(loc)._2.definition.foreach(definition => {
              val definitionLocation = definition.location
              return Array(
                LocationLink(location,
                             definitionLocation.path,
                             definitionLocation.location,
                             definitionLocation.location))
            })
          })
      })
      Array()
    }
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

  /*
  private def findBodyDeclaration(path: PathLike,
                                  line: Int,
                                  offset: Int,
                                  source: String,
                                  exprAndLocation: (String, Location)): Option[(String, Location, ApexDeclaration)] = {

    def findTypeForExpression(path: PathLike,
                              line: Int,
                              offset: Int,
                              source: String,
                              exprAndLocation: (String, Location)): Option[TypeDeclaration] = {
      TypeName(exprAndLocation._1).toOption match {
        case Some(typeName: TypeName) =>
          resolveTypeName(typeName, path, source, line, offset)
        case _ => None
      }
    }

    findTypeForExpression(path, line, offset, source, exprAndLocation) match {
      case Some(ad: ApexDeclaration) => Some((exprAndLocation._1, exprAndLocation._2, ad))
      case Some(_) => None
      case None =>
        lessSpecificExpression(exprAndLocation) match {
          case Some(expr) => findTypeInExpression(path, line, offset, source, expr)
          case None => None
        }
    }
  }
   */

  private def locationForStaticMethodOrField(expr: String,
                                             srcLocation: Location,
                                             td: ApexDeclaration,
                                             fullExpr: String): Array[LocationLink] = {

    def staticMethod(name: String): Array[LocationLink] = {
      val methods = td.methods.filter(m => m.name == Name(name))
      methods.flatMap {
        case method: ApexMethodLike =>
          Some(LocationLink(srcLocation, td.location.path, method.location.location, method.idLocation))
        case _ => None
      }
    }

    def staticField(name: String): Array[LocationLink] = {
      val fields = td.fields.filter(f => f.name == Name(name))
      fields.flatMap {
        case field: ApexFieldLike =>
          Some(LocationLink(srcLocation, td.location.toString, field.location.location, field.idLocation))
        case _ => None
      }
    }

    if (expr != fullExpr)
      staticMethod(fullExpr.substring(expr.length + 1)) ++ staticField(fullExpr.substring(expr.length + 1))
    else
      Array.empty
  }

  /*
  @tailrec
  private def findTypeInExpression(path: PathLike,
                                   line: Int,
                                   offset: Int,
                                   source: String,
                                   exprAndLocation: (String, Location)): Option[(String, Location, ApexDeclaration)] = {

    def findTypeForExpression(path: PathLike,
                              line: Int,
                              offset: Int,
                              source: String,
                              exprAndLocation: (String, Location)): Option[TypeDeclaration] = {
      TypeName(exprAndLocation._1).toOption match {
        case Some(typeName: TypeName) =>
          resolveTypeName(typeName, path, source, line, offset)
        case _ => None
      }
    }

    findTypeForExpression(path, line, offset, source, exprAndLocation) match {
      case Some(ad: ApexDeclaration) => Some((exprAndLocation._1, exprAndLocation._2, ad))
      case Some(_)                   => None
      case None =>
        lessSpecificExpression(exprAndLocation) match {
          case Some(expr) => findTypeInExpression(path, line, offset, source, expr)
          case None       => None
        }
    }
  }*/

  def lessSpecificExpression(exprAndLocation: (String, Location)): Option[(String, Location)] = {
    def shrinkLocation(src: Location, difference: Int): Location = {
      src.copy(endPosition = src.endPosition - difference)
    }

    val index = exprAndLocation._1.lastIndexOf(".")
    if (index == -1) return None
    Some(
      (exprAndLocation._1.substring(0, index), shrinkLocation(exprAndLocation._2, exprAndLocation._1.length - index)))
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

  /* Find a type in this package, with or without a namespace prefix on it. */
  private def findType(typeName: TypeName): Option[TypeDeclaration] = {
    orderedModules.view
      .flatMap(_.moduleType(typeName.withNamespace(namespace)))
      .headOption
      .orElse({
        orderedModules.view.flatMap(_.moduleType(typeName)).headOption
      })
  }

  /** Extract what to search for from source code given line & offset of cursor */
  private def extractSearchTerm(source: String, line: Int, offset: Int): Option[(String, Location)] = {
    val lineText: Option[String] = getLine(source, line - 1) match {
      case Success(Some(expr)) => Some(expr)
      case _                   => None
    }

    lineText.flatMap(lineText => {
      if (offset >= 0 && offset < lineText.length) {
        val start = findLimit(forward = false, lineText, offset)
        val end = findLimit(forward = true, lineText, offset)
        if (start != end && start.nonEmpty && end.nonEmpty)
          Some((lineText.substring(start.get, end.get + 1), Location(line, start.get, line, end.get + 1)))
        else None
      } else {
        None
      }
    })
  }

  /** Search for limit of a search term either forwards or backwards */
  private def findLimit(forward: Boolean, content: String, offset: Int): Option[Int] = {
    val regex = if (forward) "[0-9a-zA-Z_]" else "[0-9a-zA-Z_\\.]"
    val ch = "" + content(offset)
    if (!ch.matches(regex)) {
      None
    } else {
      val nextOffset = if (forward) offset + 1 else offset - 1
      if (nextOffset == -1 || nextOffset == content.length)
        Some(offset)
      else
        Some(findLimit(forward, content, nextOffset).getOrElse(offset))
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
