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

import com.nawforce.common.api.{LoggerOps, Org, ServerOps}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

trait APIRequest {
  def process(): Unit
}

case class AddPackageRequest(org: Org, directory: String, promise: Promise[AddPackageResult]) extends APIRequest {
  override def process(): Unit = {
    promise.success(
      try {
        LoggerOps.debug(LoggerOps.Trace, "Adding package")
        val pkg = org.newSFDXPackage(directory)
        AddPackageResult(None, pkg.getNamespaces(withDependents = true))
      } catch {
        case ex: IllegalArgumentException => AddPackageResult(Some(APIError(ex.getMessage)), Array())
        case ex: Throwable => AddPackageResult(Some(APIError(ex)), Array())
      }
    )
  }
}

object AddPackageRequest {
  def apply(queue: LinkedBlockingQueue[APIRequest], org: Org, directory: String): Future[AddPackageResult] = {
    val promise = Promise[AddPackageResult]()
    queue.add(new AddPackageRequest(org, directory, promise))
    promise.future
  }
}

class OrgAPIImpl extends OrgAPI {
  private val org = Org.newOrg()
  private val queue = new LinkedBlockingQueue[APIRequest]()
  private val dispatcher = new APIRequestDispatcher()

  ServerOps.setDebugLogging(Array("ALL"))
  new Thread(dispatcher).start()

  class APIRequestDispatcher extends Runnable {

    override def run(): Unit = {
      while (true) {
        val request = queue.take()

        while (!org.isFlushed())
          Thread.sleep(50)

        request.process()
      }
    }
  }

  override def identifier(): Future[String] = {
    Future(classOf[OrgAPIImpl].getProtectionDomain.getCodeSource.getLocation.getPath)
  }

  override def addPackage(directory: String): Future[AddPackageResult] = {
    AddPackageRequest(queue, org, directory)
  }

  def refresh(path: String, contents: Option[String]): Future[Unit] = {
    synchronized {
      Future( () )
    }
  }
}
