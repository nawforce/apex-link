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

import com.nawforce.pkgforce.diagnostics.{ERROR_CATEGORY, Issue, IssuesAnd, Location, PathLocation}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.xml.XMLException
import com.nawforce.runtime.xml.XMLDocument

final case class LabelFileEvent(sourceInfo: SourceInfo) extends PackageEvent
final case class LabelEvent(location: PathLocation, name: Name, isProtected: Boolean) extends PackageEvent

object LabelGenerator {

  def iterator(index: DocumentIndex): Iterator[PackageEvent] =
    index.get(LabelNature).flatMap(toEvents)

  private def toEvents(document: MetadataDocument): Iterator[PackageEvent] = {
    val source = document.source
    source.value
      .map(source => {
        XMLDocument(document.path, source) match {
          case IssuesAnd(issues, doc) if doc.isEmpty => IssuesEvent.iterator(issues)
          case IssuesAnd(_, doc) =>
            val document = doc.get
            val rootElement = document.rootElement
            try {
              rootElement.checkIsOrThrow("CustomLabels")
              val labels = rootElement
                .getChildren("labels")
                .iterator
                .flatMap(c => {
                  try {
                    val fullName: String = c.getSingleChildAsString("fullName")
                    val protect: Boolean = c.getOptionalSingleChildAsBoolean("protected").getOrElse(true)
                    Some(
                      LabelEvent(PathLocation(document.path.toString, Location(c.line)),
                        Name(fullName),
                        protect))
                  } catch {
                    case e: XMLException =>
                      Iterator(IssuesEvent(Issue(document.path, ERROR_CATEGORY, e.where, e.msg)))
                  }
                })
              labels ++ Iterator(LabelFileEvent(SourceInfo(document.path, source)))
            } catch {
              case e: XMLException =>
                Iterator(IssuesEvent(Issue(document.path, ERROR_CATEGORY, e.where, e.msg)))
            }
        }
      })
      .getOrElse(Iterator.empty) ++ IssuesEvent.iterator(source.issues)
  }
}