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

import com.nawforce.common.api.{IssueOptions, Org, Package, ServerOps}
import com.nawforce.common.deps.{DependencyNode, DownWalker}
import com.nawforce.common.org.OrgImpl
import com.nawforce.pkgforce.diagnostics.Issue
import com.nawforce.pkgforce.path.PathFactory

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

trait APIRequest {
  def process(org: OrgQueue): Unit
}

class OrgQueue(quiet: Boolean, path: String) { self =>
  val org: Org = Org.newOrg(path)
  var packages: List[Package] = Nil

  private val queue = new LinkedBlockingQueue[APIRequest]()
  private val dispatcher = new APIRequestDispatcher()

  class APIRequestDispatcher() extends Runnable {

    override def run(): Unit = {
      while (true) {
        val request = queue.take()

        while (org.isDirty()) Thread.sleep(50)

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

  def refresh(path: String): Unit = {
    packages.headOption.foreach(pkg => pkg.refresh(path))
  }
}

case class OpenRequest(promise: Promise[OpenResult]) extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    promise.success(try {
      val namespaces = queue.org.getPackages().flatMap(_.getNamespaces(false))
      OpenResult(None, namespaces)
    } catch {
      case ex: IllegalArgumentException => OpenResult(Some(APIError(ex.getMessage)), Array())
      case ex: Throwable                => OpenResult(Some(APIError(ex)), Array())
    })
  }
}

object OpenRequest {
  def apply(queue: OrgQueue): Future[OpenResult] = {
    val promise = Promise[OpenResult]()
    queue.add(new OpenRequest(promise))
    promise.future
  }
}

case class GetIssues(promise: Promise[GetIssuesResult],
                     includeWarnings: Boolean,
                     includeZombies: Boolean)
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {

    val buffer = new ArrayBuffer[Issue]()
    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {
      val issues = orgImpl
        .reportableIssues(new IssueOptions {
          includeWarnings = includeWarnings; includeZombies = includeZombies
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
  def apply(queue: OrgQueue,
            includeWarnings: Boolean,
            includeZombies: Boolean): Future[GetIssuesResult] = {
    val promise = Promise[GetIssuesResult]()
    queue.add(new GetIssues(promise, includeWarnings, includeZombies))
    promise.future
  }
}

case class TypeIdentifiers(promise: Promise[GetTypeIdentifiersResult]) extends APIRequest {
  override def process(queue: OrgQueue): Unit = {

    val buffer = new mutable.HashSet[String]()
    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {
      orgImpl.packages
        .filterNot(_.isGhosted)
        .foreach(pkg => buffer.addAll(pkg.orderedModules.head.getApexTypeIdentifiers))
      promise.success(GetTypeIdentifiersResult(buffer.toArray.sorted))
    }
  }
}

object TypeIdentifiers {
  def apply(queue: OrgQueue): Future[GetTypeIdentifiersResult] = {
    val promise = Promise[GetTypeIdentifiersResult]()
    queue.add(new TypeIdentifiers(promise))
    promise.future
  }
}

case class DependencyGraphRequest(promise: Promise[DependencyGraphResult],
                                  typeName: String,
                                  depth: Int)
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {

    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {

      orgImpl
        .getIdentifier(typeName)
        .foreach(id => {
          val depWalker = new DownWalker(queue.org)
          val nodeData = depWalker
            .walk(id, depth)
            .map(n => {
              NodeData(n.id.typeName.toString(),
                       nodeFileSize(queue.org, n),
                       n.nature,
                       n.transitiveCount,
                       n.extending.map(_.typeName.toString()),
                       n.implementing.map(_.typeName.toString()),
                       n.using.map(_.typeName.toString()))
            })

          val nodeIndex = nodeData.map(_.name).zipWithIndex.toMap

          val linkData = new ArrayBuffer[LinkData]()
          nodeData.foreach(n => {
            val source = nodeIndex(n.name)
            def safeLink(nature: String)(name: String): Unit = {
              nodeIndex
                .get(name)
                .foreach(target =>
                  if (source != target) linkData += LinkData(source, target, nature))
            }

            n.extending.foreach(safeLink("extends"))
            n.implementing.foreach(safeLink("implements"))
            n.using.foreach(safeLink("uses"))
          })

          promise.success(DependencyGraphResult(nodeData, linkData.toArray))
        })
    }
  }

  private def nodeFileSize(org: Org, n: DependencyNode): Int = {
    Option(org.getIdentifierLocation(n.id.typeName.toString()))
      .map(location => PathFactory(location.path).size.toInt)
      .getOrElse(0)
  }
}

object DependencyGraphRequest {
  def apply(queue: OrgQueue, path: String, depth: Int): Future[DependencyGraphResult] = {
    val promise = Promise[DependencyGraphResult]()
    queue.add(new DependencyGraphRequest(promise, path, depth))
    promise.future
  }
}

case class IdentifierLocation(promise: Promise[IdentifierLocationResult], identifier: String)
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    promise.success(IdentifierLocationResult(queue.org.getIdentifierLocation(identifier)))
  }
}

object IdentifierLocation {
  def apply(queue: OrgQueue, identifier: String): Future[IdentifierLocationResult] = {
    val promise = Promise[IdentifierLocationResult]()
    queue.add(new IdentifierLocation(promise, identifier))
    promise.future
  }
}

case class IdentifierForPath(promise: Promise[Option[String]], path: String) extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {
      val types = orgImpl.packagesByNamespace.values.flatMap(pkg => Option(pkg.getTypeOfPath(path)))
      promise.success(types.headOption.map(_.typeName.toString()))
    }
  }
}

object IdentifierForPath {
  def apply(queue: OrgQueue, identifier: String): Future[Option[String]] = {
    val promise = Promise[Option[String]]()
    queue.add(new IdentifierForPath(promise, identifier))
    promise.future
  }
}

object OrgQueue {
  private var _instance: Option[OrgQueue] = None

  def open(quiet: Boolean, path: String): OrgQueue = {
    synchronized {
      _instance = Some(new OrgQueue(quiet, path))
      _instance.get
    }
  }

  def instance(): OrgQueue = {
    synchronized {
      _instance.get
    }
  }

  def reset(): Unit = {
    synchronized {
      _instance = None
    }
  }
}

class OrgAPIImpl extends OrgAPI {
  override def identifier(): Future[String] = {
    Future(classOf[OrgAPIImpl].getProtectionDomain.getCodeSource.getLocation.getPath)
  }

  override def reset(): Future[Unit] = {
    Future(OrgQueue.reset())
  }

  override def open(directory: String): Future[OpenResult] = {
    OrgQueue.open(quiet = true, directory)
    OpenRequest(OrgQueue.instance())
  }

  override def getIssues(includeWarnings: Boolean,
                         includeZombies: Boolean): Future[GetIssuesResult] = {
    GetIssues(OrgQueue.instance(), includeWarnings, includeZombies)
  }

  override def refresh(path: String): Future[Unit] = {
    Future(OrgQueue.instance().refresh(path))
  }

  override def typeIdentifiers(): Future[GetTypeIdentifiersResult] = {
    TypeIdentifiers(OrgQueue.instance())
  }

  override def dependencyGraph(path: String, depth: Int): Future[DependencyGraphResult] = {
    DependencyGraphRequest(OrgQueue.instance(), path, depth)
  }

  override def identifierLocation(identifier: String): Future[IdentifierLocationResult] = {
    IdentifierLocation(OrgQueue.instance(), identifier)
  }

  override def identifierForPath(path: String): Future[Option[String]] = {
    IdentifierForPath(OrgQueue.instance(), path)
  }
}
