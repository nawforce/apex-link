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

import scala.collection.immutable.Queue

/** Package stream generator, assists queuing package stream events. */
trait Generator {

  protected def queue(metadataType: Name,
                      logger: IssueLogger,
                      provider: MetadataProvider,
                      queue: Queue[PackageEvent]): Queue[PackageEvent] = {
    provider
      .retrieve(metadataType)
      .foldRight(queue)((d, q) => {
        queueFromDocument(logger, q, d)
      })
  }

  private def queueFromDocument(logger: IssueLogger,
                                queue: Queue[PackageEvent],
                                metadata: MetadataDocumentWithData): Queue[PackageEvent] = {
    queue ++ getMetadata(logger, metadata)
  }

  protected def getMetadata(logger: IssueLogger,
                            metadata: MetadataDocumentWithData): Seq[PackageEvent]
}

object Generator {
  def queue(dt: UpdatableMetadata,
            logger: IssueLogger,
            provider: MetadataProvider): Queue[PackageEvent] = {
    dt match {
      case _: LabelsDocument    => LabelGenerator.queue(logger, provider, Queue[PackageEvent]())
      case _: PageDocument      => PageGenerator.queue(logger, provider, Queue[PackageEvent]())
      case _: ComponentDocument => ComponentGenerator.queue(logger, provider, Queue[PackageEvent]())
      case _: FlowDocument      => FlowGenerator.queue(logger, provider, Queue[PackageEvent]())
    }
  }
}
