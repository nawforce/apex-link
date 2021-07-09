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

import com.nawforce.apexlink.cst.ClassDeclaration
import com.nawforce.apexlink.rpc.LocationLink
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.path.PathLike

import java.io.{BufferedReader, StringReader}
import scala.jdk.CollectionConverters._
import scala.util.{Success, Try, Using}

trait DefinitionProvider {
  this: PackageImpl =>

  def getDefinition(path: PathLike, line: Int, offset: Int): Option[LocationLink] = {
    extractExpression(path, line, offset).flatMap(exprAndLocation => {
      TypeName(exprAndLocation._1).toOption match {
        case Some(typeName: TypeName) =>
          orderedModules.view.flatMap(_.packageType(typeName)).headOption match {
            case Some(ad: ClassDeclaration) =>
              Some(LocationLink(exprAndLocation._2, ad.path.toString, ad.location.location, ad.nameLocation.location))
            case _ =>
              None
          }
        case _ => None
      }
    })
  }

  private def extractExpression(path: PathLike, lineNumber: Int, offset: Int): Option[(String, Location)] = {
    val line = path.read().toOption.map(contents => getLine(contents, lineNumber - 1)).flatMap {
      case Success(Some(expr)) => Some(expr)
      case _                   => None
    }

    line.flatMap(line => {
      if (offset >= 0 && offset < line.length) {
        val start = findLimit(forward = false, line, offset)
        val end = findLimit(forward = true, line, offset)
        if (start != end && start.nonEmpty && end.nonEmpty)
          Some((line.substring(start.get, end.get + 1), Location(lineNumber, start.get, lineNumber, end.get+1)))
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
