/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.pkgforce.stream

import com.nawforce.pkgforce.diagnostics.{CatchingLogger, IssueLogger}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{LocationAnd, PathLocation}
import com.nawforce.runtime.parsers.{PageParser, VFParser}

import scala.collection.compat.immutable.ArraySeq

final case class ComponentEvent(
  sourceInfo: SourceInfo,
  attributes: ArraySeq[Name],
  _controllers: ArraySeq[LocationAnd[Name]],
  _expressions: ArraySeq[LocationAnd[String]]
) extends VFEvent(_controllers, _expressions)

/** Convert component documents into PackageEvents */
object ComponentGenerator {

  def iterator(index: DocumentIndex): Iterator[PackageEvent] =
    index.get(ComponentNature).flatMap(toEvents)

  private def toEvents(document: MetadataDocument): Iterator[PackageEvent] = {
    val source = document.source
    source.value
      .map(source => {
        val parser: PageParser = PageParser(document.path, source)
        val result             = parser.parsePage()
        if (result.issues.nonEmpty) {
          IssuesEvent.iterator(result.issues)
        } else {
          val location   = parser.getPathLocation(result.value)
          val logger     = new CatchingLogger
          val attributes = extractAttributes(parser, logger, result.value)
          (if (logger.issues.isEmpty)
             Iterator(
               ComponentEvent(
                 SourceInfo(PathLocation(location.path, location.location), source),
                 attributes,
                 VFEvent.extractControllers(parser.source, result.value, isPage = false),
                 VFEvent.extractExpressions(parser.source, result.value)
               )
             )
           else
             Iterator()) ++ IssuesEvent.iterator(logger.issues)
        }
      })
      .getOrElse(Iterator.empty) ++ IssuesEvent.iterator(source.issues)
  }

  private def extractAttributes(
    parser: PageParser,
    logger: IssueLogger,
    component: VFParser.VfUnitContext
  ): ArraySeq[Name] = {
    val root: VFParser.ElementContext = component.element()
    if (!PageParser.getText(root.Name(0)).equalsIgnoreCase("apex:component")) {
      val location = parser.getPathLocation(component)
      logger.logError(location.path, location.location, "Root element must be 'apex:component'")
      return ArraySeq()
    }

    ArraySeq.unsafeWrapArray(
      PageParser
        .toScala(root.content())
        .map(
          content =>
            PageParser
              .toScala(content.element())
              .filter(
                el =>
                  Option(el.Name(0))
                    .exists(name => PageParser.getText(name).equalsIgnoreCase("apex:attribute"))
              )
              .flatMap(attribute => {
                val name = PageParser
                  .toScala(attribute.attribute())
                  .find(a => PageParser.getText(a.attributeName()).equalsIgnoreCase("name"))
                if (name.isEmpty) {
                  val location = parser.getPathLocation(component)
                  logger.logError(
                    location.path,
                    location.location,
                    "apex:attribute is missing 'name' attribute"
                  )
                  None
                } else {
                  Some(
                    Name(
                      PageParser
                        .toScala(name.get.attributeValues())
                        .map(PageParser.getText)
                        .mkString
                    )
                  )
                }
              })
        )
        .getOrElse(Seq())
        .toArray
    )
  }
}
