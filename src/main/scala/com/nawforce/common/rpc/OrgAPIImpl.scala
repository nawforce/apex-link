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
package com.nawforce.common.rpc

import java.util.concurrent.LinkedBlockingQueue

import com.nawforce.common.api.{IssueOptions, LoggerOps, Org, Package, ServerOps}
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.org.OrgImpl
import com.nawforce.runtime.SourceBlob

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

trait APIRequest {
  def process(org: OrgQueue): Unit
}

class OrgQueue(quiet: Boolean) { self =>
  val org: Org = Org.newOrg()
  var packages: List[Package] = Nil

  private val queue = new LinkedBlockingQueue[APIRequest]()
  private val dispatcher = new APIRequestDispatcher()

  class APIRequestDispatcher() extends Runnable {

    override def run(): Unit = {
      while (true) {
        val request = queue.take()

        while (!org.isFlushed()) Thread.sleep(50)

        request.process(self)
      }
    }
  }

  if (!quiet)
    ServerOps.setDebugLogging(Array("ALL"))
  new Thread(dispatcher).start()

  def add(request: APIRequest): Unit = {
    queue.add(request)
  }

  def refresh(path: String, contents: Option[String]): Unit = {
    packages.headOption.foreach(pkg => pkg.refresh(path, contents.map(c => SourceBlob(c)).orNull))
  }

}

case class AddPackageRequest(directory: String, promise: Promise[AddPackageResult])
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    promise.success(try {
      LoggerOps.debug(LoggerOps.Trace, "Adding package")
      val pkg = queue.org.newSFDXPackage(directory)
      queue.packages = pkg :: queue.packages
      AddPackageResult(None, pkg.getNamespaces(withDependents = true))
    } catch {
      case ex: IllegalArgumentException => AddPackageResult(Some(APIError(ex.getMessage)), Array())
      case ex: Throwable                => AddPackageResult(Some(APIError(ex)), Array())
    })
  }
}

object AddPackageRequest {
  def apply(queue: OrgQueue, directory: String): Future[AddPackageResult] = {
    val promise = Promise[AddPackageResult]()
    queue.add(new AddPackageRequest(directory, promise))
    promise.future
  }
}

case class GetIssues(promise: Promise[GetIssuesResult]) extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    LoggerOps.debug(LoggerOps.Trace, "Getting issues")

    val buffer = new ArrayBuffer[Issue]()
    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {
      val issues = orgImpl
        .reportableIssues(new IssueOptions {
          includeWarnings = true; includeZombies = true
        })
        .getIssues
      issues.keys.foreach(key => {
        buffer.addAll(issues(key).take(10))
      })
      promise.success(GetIssuesResult(buffer.toArray))
    }
  }
}

object GetIssues {
  def apply(queue: OrgQueue): Future[GetIssuesResult] = {
    val promise = Promise[GetIssuesResult]()
    queue.add(new GetIssues(promise))
    promise.future
  }
}

object OrgQueue {
  private var _instance: OrgQueue = _

  def instance(quiet: Boolean): OrgQueue = {
    synchronized {
      if (_instance == null)
        _instance = new OrgQueue(quiet)
      _instance
    }
  }

  def reset(): Unit = {
    synchronized {
      _instance = null
    }
  }
}

class OrgAPIImpl(quiet: Boolean) extends OrgAPI {
  override def identifier(): Future[String] = {
    Future(classOf[OrgAPIImpl].getProtectionDomain.getCodeSource.getLocation.getPath)
  }

  override def reset(): Future[Unit] = {
    Future(OrgQueue.reset())
  }

  override def addPackage(directory: String): Future[AddPackageResult] = {
    AddPackageRequest(OrgQueue.instance(quiet), directory)
  }

  override def getIssues(): Future[GetIssuesResult] = {
    GetIssues(OrgQueue.instance(quiet))
  }

  override def refresh(path: String, contents: Option[String]): Future[Unit] = {
    Future(OrgQueue.instance(quiet).refresh(path, contents))
  }
}
