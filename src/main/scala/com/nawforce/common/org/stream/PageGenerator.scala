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

import scala.collection.immutable.Queue

case class PageEvent(location: LocationImpl, name: Name) extends PackageEvent

object PageGenerator {

  def queue(logger: IssueLogger, index: DocumentIndex, queue: Queue[PackageEvent])
    : Queue[PackageEvent] = {
    index.getByExtension(Name("page")).foldRight(queue)((f, q) => queueFromPath(logger, q, f.path))
  }

  private def queueFromPath(logger: IssueLogger, queue: Queue[PackageEvent], path: PathLike): Queue[PackageEvent] = {
    if (!path.isFile) {
      logger.logError(LineLocationImpl(path.toString, 0), s"Expecting page to be in a regular file")
      return queue
    }

    DocumentType(path) match {
      case Some(page: PageDocument) =>
        queue :+ PageEvent(LineLocationImpl(page.path.toString, 0), page.name)
      case _ =>
        queue
    }
  }
}
