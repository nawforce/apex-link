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

package com.nawforce.pkgforce.stream

import com.nawforce.pkgforce.diagnostics.{CatchingLogger, LocationAnd, PathLocation}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.Name
import com.nawforce.runtime.parsers.PageParser

final case class PageEvent(sourceInfo: SourceInfo,
                           _controllers: Array[LocationAnd[Name]],
                           _expressions: Array[LocationAnd[String]])
    extends VFEvent(_controllers, _expressions)

/** Convert page documents into PackageEvents */
object PageGenerator {

  def iterator(index: DocumentIndex): Iterator[PackageEvent] =
    index.get(PageNature).flatMap(toEvents)

  private def toEvents(document: MetadataDocument): Iterator[PackageEvent] = {
    val source = document.source
    source.value
      .map(source => {
        val parser: PageParser = PageParser(document.path, source)
        val result = parser.parsePage()
        if (result.issues.nonEmpty) {
          IssuesEvent.iterator(result.issues)
        } else {
          val location = parser.getPathAndLocation(result.value)
          val logger = new CatchingLogger
          Iterator(PageEvent(SourceInfo(PathLocation(location._1.toString, location._2), source),
                             VFEvent.extractControllers(parser.source, result.value, isPage = true),
                             VFEvent.extractExpressions(parser.source, result.value))) ++
            IssuesEvent.iterator(logger.issues)
        }
      })
      .getOrElse(Iterator.empty) ++ IssuesEvent.iterator(source.issues)
  }
}
