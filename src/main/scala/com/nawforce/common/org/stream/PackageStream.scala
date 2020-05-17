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
import com.nawforce.common.documents.DocumentIndex

import scala.collection.immutable.Queue

trait PackageEvent

class PackageStream(val namespace: Option[Name], val events: Seq[PackageEvent]) {
  def labelsFiles: Seq[LabelFileEvent] = events.collect{case e: LabelFileEvent => e}
  def labels: Seq[LabelEvent] = events.collect{case e: LabelEvent => e}
  def pages: Seq[PageEvent] = events.collect{case e: PageEvent => e}
  def flows: Seq[FlowEvent] = events.collect{case e: FlowEvent => e}
  def components: Seq[ComponentEvent] = events.collect{case e: ComponentEvent => e}
}

object PackageStream {
  def apply(logger: IssueLogger, namespace: Option[Name], index: DocumentIndex): PackageStream = {
    val provider = new DocumentIndexMetadataProvider(index)
    var queue = Queue[PackageEvent]()
    queue = LabelGenerator.queue(logger, provider, queue)
    queue = PageGenerator.queue(logger, provider, queue)
    queue = FlowGenerator.queue(logger, provider, queue)
    queue = ComponentGenerator.queue(logger, provider, queue)
    new PackageStream(namespace, queue)
  }
}
