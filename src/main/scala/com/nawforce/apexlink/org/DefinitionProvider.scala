/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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

import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.rpc.LocationLink
import com.nawforce.apexlink.types.apex.{ApexDeclaration, FullDeclaration}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.documents.{ApexClassDocument, MetadataDocument}
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.ByteArraySourceData

import java.io.{BufferedReader, StringReader}
import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters._
import scala.util.{Success, Try, Using}

trait DefinitionProvider {
  this: PackageImpl =>

  def getDefinition(path: PathLike, line: Int, offset: Int, content: Option[String]): Option[LocationLink] = {
    val source = content.orElse(path.read().toOption)
    if (source.isEmpty)
      return None

    extractExpression(source.get, line, offset).flatMap(exprAndLocation => {
      TypeName(exprAndLocation._1).toOption match {
        case Some(typeName: TypeName) =>
          findType(typeName, path, source.get, line, offset) match {
            case Some(ad: ApexDeclaration) =>
              Some(LocationLink(exprAndLocation._2, ad.path.toString, ad.fullLocation, ad.nameLocation))
            case _ =>
              None
          }
        case _ => None
      }
    })
  }

  private def findType(typeName: TypeName,
                       path: PathLike,
                       source: String,
                       line: Int,
                       offset: Int): Option[TypeDeclaration] = {
    loadClass(path, source)
      .flatMap(td =>
        findEnclosingClass(td, line, offset).flatMap(td => {
          TypeResolver(typeName, td).toOption
        }))
      .orElse(findType(typeName))
  }

  private def findEnclosingClass(td: FullDeclaration, line: Int, offset: Int): Option[FullDeclaration] = {
    td.nestedTypes
      .collect { case nested: FullDeclaration => nested }
      .find(_.containsPosition(line, offset))
      .orElse({
        if (td.containsPosition(line, offset))
          Some(td)
        else None
      })
  }

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

  /* This is does simple outer/inner lookup, no parsing needed ! */
  private def findType(typeName: TypeName): Option[TypeDeclaration] = {
    orderedModules.view
      .flatMap(_.packageType(typeName.withNamespace(namespace)))
      .headOption
      .orElse({
        orderedModules.view.flatMap(_.packageType(typeName)).headOption
      })
  }

  private def extractExpression(source: String, lineNumber: Int, offset: Int): Option[(String, Location)] = {
    val line: Option[String] = getLine(source, lineNumber - 1) match {
      case Success(Some(expr)) => Some(expr)
      case _                   => None
    }

    line.flatMap(line => {
      if (offset >= 0 && offset < line.length) {
        val start = findLimit(forward = false, line, offset)
        val end = findLimit(forward = true, line, offset)
        if (start != end && start.nonEmpty && end.nonEmpty)
          Some((line.substring(start.get, end.get + 1), Location(lineNumber, start.get, lineNumber, end.get + 1)))
        else None
      } else {
        None
      }
    })
  }

  private def findLimit(forward: Boolean, content: String, offset: Int): Option[Int] = {
    val ch = "" + content(offset)
    if (!ch.matches("[0-9a-zA-Z_\\.]")) {
      None
    } else {
      val nextOffset = if (forward) offset + 1 else offset - 1
      if (nextOffset == -1 || nextOffset == content.length)
        Some(offset)
      else
        Some(findLimit(forward, content, nextOffset).getOrElse(offset))
    }
  }

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
