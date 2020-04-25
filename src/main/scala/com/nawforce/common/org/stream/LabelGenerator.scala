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

import com.nawforce.common.diagnostics.IssueLogger
import com.nawforce.common.documents._
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import com.nawforce.common.xml.{XMLException, XMLFactory}

import scala.collection.immutable.Queue

case class LabelEvent(location: LocationImpl, name: Name, isProtected: Boolean) extends PackageEvent

object LabelGenerator {

  def queue(logger: IssueLogger, index: DocumentIndex, queue: Queue[PackageEvent])
    : Queue[PackageEvent] = {
    index.getByExtension(Name("labels")).foldRight(queue)((f, q) => queueFromPath(logger, q, f.path))
  }

  private def queueFromPath(logger: IssueLogger, queue: Queue[PackageEvent], path: PathLike): Queue[PackageEvent] = {
    if (!path.isFile) {
      logger.logError(LineLocationImpl(path.toString, 0), s"Expecting labels to be in a normal file")
      return queue
    }

    val data = path.read()
    if (data.isLeft) {
      logger.logError(LineLocationImpl(path.toString, 0), s"Could not read file: ${data.right.get}")
      return queue
    }

    val parseResult = XMLFactory.parse(path)
    if (parseResult.isLeft) {
      logger.logError(parseResult.left.get._1, parseResult.left.get._2)
      return queue
    }
    val rootElement = parseResult.right.get.rootElement

    try {
      rootElement.assertIs("CustomLabels")
    } catch {
      case e: XMLException =>
        logger.logError(RangeLocationImpl(path, e.where), e.msg)
        return queue
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
      queue ++ labels
  }
}
