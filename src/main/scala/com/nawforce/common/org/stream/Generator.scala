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
import com.nawforce.common.documents.{DocumentIndex, LineLocationImpl, MetadataDocumentType}
import com.nawforce.common.names.Name

import scala.collection.immutable.Queue

trait Generator {

  protected def queue(metadataType: Name, logger: IssueLogger, index: DocumentIndex, queue: Queue[PackageEvent])
  : Queue[PackageEvent] = {
    index.getByExtension(metadataType).foldRight(queue)((d, q) => {
      queueFromDocument(logger, q, d)
    })
  }

  private def queueFromDocument(logger: IssueLogger, queue: Queue[PackageEvent],
                            documentType: MetadataDocumentType): Queue[PackageEvent] = {
    if (!documentType.path.isFile) {
      logger.logError(LineLocationImpl(documentType.path.toString, 0), s"Expecting metadata to be in a regular file")
      return queue
    }

    queue ++ getMetadata(logger, documentType).toSeq
  }

  protected def getMetadata(logger: IssueLogger, metadata: MetadataDocumentType): Option[PackageEvent]
}
