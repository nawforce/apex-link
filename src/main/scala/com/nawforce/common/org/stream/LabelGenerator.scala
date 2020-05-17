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

import com.nawforce.common.api.Name
import com.nawforce.common.diagnostics.IssueLogger
import com.nawforce.common.documents._
import com.nawforce.common.xml.XMLException
import com.nawforce.runtime.xml.XMLDocument

import scala.collection.immutable.Queue

case class LabelFileEvent(sourceInfo: SourceInfo) extends PackageEvent
case class LabelEvent(location: LocationImpl, name: Name, isProtected: Boolean) extends PackageEvent

object LabelGenerator extends Generator {

  def queue(logger: IssueLogger, provider: MetadataProvider, queue: Queue[PackageEvent])
    : Queue[PackageEvent] = {
    super.queue(DocumentType.labelsExt, logger, provider, queue)
  }

  protected override def getMetadata(logger: IssueLogger, metadata: MetadataDocumentWithData): Seq[PackageEvent] = {

    val path = metadata.docType.path
    val parseResult = XMLDocument(path, metadata.data)
    if (parseResult.isLeft) {
      logger.logError(parseResult.left.get._1, parseResult.left.get._2)
      return Seq.empty
    }
    val rootElement = parseResult.right.get.rootElement

    try {
      rootElement.assertIs("CustomLabels")
    } catch {
      case e: XMLException =>
        logger.logError(RangeLocationImpl(path, e.where), e.msg)
        return Seq.empty
    }

    val labels = rootElement.getChildren("labels")
      .flatMap(c => {
        try {
          val fullName: String = c.getSingleChildAsString("fullName")
          val protect: Boolean = c.getSingleChildAsBoolean("protected")
          Some(LabelEvent(RangeLocationImpl(path, TextRange(c.line)), Name(fullName), protect))
        } catch {
          case e: XMLException =>
            logger.logError(RangeLocationImpl(path, e.where), e.msg)
            None
        }
      })
    labels :+ LabelFileEvent(SourceInfo(path, metadata.data))
  }
}
