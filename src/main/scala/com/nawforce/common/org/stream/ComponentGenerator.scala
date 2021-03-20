/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

package com.nawforce.common.org.stream

import com.nawforce.common.api.{Location, Name, PathLocation}
import com.nawforce.common.diagnostics.IssueLogger
import com.nawforce.common.documents._
import com.nawforce.runtime.parsers.{PageParser, VFParser}

import scala.collection.immutable.Queue

case class ComponentEvent(sourceInfo: SourceInfo,
                          location: PathLocation,
                          name: Name,
                          attributes: Array[Name])
    extends PackageEvent

/** Convert component documents into PackageEvents */
object ComponentGenerator extends Generator {

  def queue(logger: IssueLogger,
            provider: MetadataProvider,
            queue: Queue[PackageEvent]): Queue[PackageEvent] = {
    super.queue(MetadataDocument.componentExt, logger, provider, queue)
  }

  override def getMetadata(logger: IssueLogger,
                           metadata: MetadataDocumentWithData): Seq[PackageEvent] = {

    val path = metadata.docType.path
    val parser = PageParser(path, metadata.source)
    val attributes = parser.parsePage() match {
      case Left(issues)     => issues.foreach(logger.log); Array[Name]()
      case Right(component) => extractAttributes(parser, logger, component)
    }

    Seq(
      ComponentEvent(SourceInfo(metadata.docType.path, metadata.source.asString),
                     PathLocation(metadata.docType.path.toString, Location.empty),
                     metadata.docType.name,
                     attributes))
  }

  private def extractAttributes(parser: PageParser,
                                logger: IssueLogger,
                                component: VFParser.VfUnitContext): Array[Name] = {
    val root = component.element()
    if (!PageParser.getText(root.Name(0)).equalsIgnoreCase("apex:component")) {
      val location = parser.getPathAndLocation(component)
      logger.logError(location._1, location._2, "Root element must be 'apex:component'")
    }

    Option(root.content())
      .map(
        content =>
          PageParser
            .toScala(content.element())
            .filter(el =>
              Option(el.Name(0)).exists(name =>
                PageParser.getText(name).equalsIgnoreCase("apex:attribute")))
            .flatMap(attribute => {
              val name = PageParser
                .toScala(attribute.attribute())
                .find(a => PageParser.getText(a.attributeName()).equalsIgnoreCase("name"))
              if (name.isEmpty) {
                val location = parser.getPathAndLocation(component)
                logger.logError(location._1,
                                location._2,
                                "apex:attribute is missing 'name' attribute")
                None
              } else {
                Some(Name(
                  PageParser.toScala(name.get.attributeValues()).map(PageParser.getText).mkString))
              }
            }))
      .getOrElse(Seq())
      .toArray
  }
}
