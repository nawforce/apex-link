/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.org

import com.nawforce.apexlink.memory.Monitor
import com.nawforce.pkgforce.documents.ParsedCache
import com.nawforce.pkgforce.memory.Cleanable
import com.nawforce.pkgforce.path.PathLike

import java.util.concurrent.locks.ReentrantLock
import scala.collection.mutable

case class RefreshRequest(pkg: PackageImpl, path: PathLike)

class Flusher(org: OrgImpl, parsedCache: Option[ParsedCache]) {
  protected val lock         = new ReentrantLock(true)
  protected val refreshQueue = new mutable.Queue[RefreshRequest]()
  private var expired        = false

  def isDirty: Boolean = {
    lock.synchronized { refreshQueue.nonEmpty }
  }

  def queue(request: RefreshRequest): Unit = {
    lock.synchronized {
      refreshQueue.enqueue(request)
    }
  }

  def refreshAndFlush(): Boolean = {
    OrgImpl.current.withValue(org) {
      lock.synchronized {
        val packages = org.packages

        val refreshed = packages
          .map(pkg => {
            pkg.refreshBatched(refreshQueue.filter(_.pkg == pkg).toSeq)
          })
          .foldLeft(false) {
            _ || _
          }

        parsedCache.foreach(pc => {
          packages.foreach(pkg => {
            pkg.flush(pc)
          })
          if (!expired) {
            pc.expire()
            expired = true
          }
        })

        Monitor.reportDuplicateTypes()
        Cleanable.clean()
        refreshQueue.clear()
        refreshed
      }
    }
  }

}

class CacheFlusher(org: OrgImpl, parsedCache: Option[ParsedCache])
    extends Flusher(org, parsedCache)
    with Runnable {

  private val t = new Thread(this)
  t.setDaemon(true)
  t.setName("apex-link cache flusher")
  t.start()

  override def run(): Unit = {
    def queueSize: Int = lock.synchronized { refreshQueue.size }

    while (true) {
      // Wait for non-zero queue to be stable
      var stable = false
      while (!stable) {
        val start = queueSize
        Thread.sleep(250)
        val end = queueSize
        stable = start > 0 && start == end
      }

      // Process refresh requests & flush
      refreshAndFlush()
    }
  }
}
