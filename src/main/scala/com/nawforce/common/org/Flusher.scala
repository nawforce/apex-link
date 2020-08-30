/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.common.org

import java.util.concurrent.locks.ReentrantLock

import com.nawforce.common.api.ServerOps
import com.nawforce.common.documents.ParsedCache
import com.nawforce.common.memory.{Cleanable, Monitor}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.SourceBlob

import scala.collection.mutable

case class RefreshRequest(pkg: PackageImpl, path: PathLike, source: Option[SourceBlob])

class Flusher(org: OrgImpl) extends Runnable {
  private val lock = new ReentrantLock(true)
  private val refreshQueue = new mutable.Queue[RefreshRequest]()

  def queue(request: RefreshRequest): Unit = {
    lock.synchronized {
      refreshQueue.enqueue(request)
    }
  }

  def isFlushed: Boolean = lock.synchronized {refreshQueue.isEmpty}

  override def run(): Unit = {
      def queueSize: Int = lock.synchronized {refreshQueue.size}

      while (true) {
        // Wait for non-zero queue to be stable
        var stable = false
        while (!stable) {
          val start = queueSize
          Thread.sleep(500)
          val end = queueSize
          stable = start > 0 && start == end
        }

        // Process refresh requests & flush
        refreshAndFlush()
      }
  }

  def refreshAndFlush(): Boolean = {
    OrgImpl.current.withValue(org) {
      lock.synchronized {
        val postMessage = if (Monitor.size > 0) s", ${Monitor.size} full types" else ""
        val refreshed = ServerOps.debugTime("Org flushed", show = true, postMessage) {
          ParsedCache.create match {
            case Left(err) =>
              ServerOps.error(err); false
            case Right(pc) =>
              org.orderedPackages.map(pkg => {
                val refreshed = pkg.refreshBatched(refreshQueue.filter(_.pkg == pkg).toSeq)
                pkg.flush(pc)
                refreshed
              }).foldLeft(false) {
                _ || _
              }
          }
        }
        Monitor.reportDuplicateTypes()
        Cleanable.clean()
        refreshQueue.clear()
        refreshed
      }
    }
  }
}



