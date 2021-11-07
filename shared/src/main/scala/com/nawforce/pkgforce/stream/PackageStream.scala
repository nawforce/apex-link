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

import com.nawforce.pkgforce.diagnostics.Issue
import com.nawforce.pkgforce.documents._

import scala.collection.compat.immutable.ArraySeq

trait PackageEvent

final case class IssuesEvent(issues: ArraySeq[Issue]) extends PackageEvent

object IssuesEvent {
  def apply(issues: Issue*): IssuesEvent = {
    new IssuesEvent(issues.to(ArraySeq))
  }

  def iterator(issues: ArraySeq[Issue]): Iterator[IssuesEvent] = {
    if (issues.nonEmpty)
      Iterator(new IssuesEvent(issues.to(ArraySeq)))
    else
      Iterator.empty
  }
}

class PackageStream(val events: ArraySeq[PackageEvent]) {
  def issues: ArraySeq[IssuesEvent] = events.collect { case e: IssuesEvent => e }

  def labelsFiles: ArraySeq[LabelFileEvent] = events.collect { case e: LabelFileEvent => e }

  def labels: ArraySeq[LabelEvent] = events.collect { case e: LabelEvent => e }

  def pages: ArraySeq[PageEvent] = events.collect { case e: PageEvent => e }

  def flows: ArraySeq[FlowEvent] = events.collect { case e: FlowEvent => e }

  def components: ArraySeq[ComponentEvent] = events.collect { case e: ComponentEvent => e }
}

object PackageStream {
  def apply(index: DocumentIndex): PackageStream = {
    new PackageStream(ArraySeq.unsafeWrapArray(eventStream(index).toArray))
  }

  def eventStream(index: DocumentIndex): Iterator[PackageEvent] = {
    LabelGenerator.iterator(index) ++
      ComponentGenerator.iterator(index) ++
      PageGenerator.iterator(index) ++
      FlowGenerator.iterator(index) ++
      SObjectGenerator.iterator(index) ++
      ExtendedApexGenerator.iterator(index) ++
      ApexGenerator.iterator(index) ++
      TriggerGenerator.iterator(index)
  }
}
