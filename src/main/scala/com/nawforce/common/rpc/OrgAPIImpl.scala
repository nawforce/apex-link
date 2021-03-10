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

import com.nawforce.common.api.{IssueOptions, Org, Package, ServerOps, TypeIdentifier}
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.runtime.SourceBlob

import scala.collection.mutable
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
      orgImpl.packagesByNamespace.values.foreach(pkg => buffer.addAll(pkg.getApexTypeIdentifiers))
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

case class DependencyGraphRequest(promise: Promise[DependencyGraphResult], id: String, depth: Int)
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {

    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {

      val nodeDataMap = mutable.HashMap[String, Int]()
      nodeDataMap.put(id, 0)
      val linkDataArray = ArrayBuffer[LinkData]()
      collectDependencies(orgImpl, depth, processed = 0, nodeDataMap, linkDataArray)

      val nodeData = toNodeData(orgImpl, nodeDataMap.toSeq.sortBy(_._2).map(_._1)).toArray
      val linkData = linkDataArray.toArray

      promise.success(DependencyGraphResult(nodeData, linkData))
    }
  }

  private def toNodeData(org: OrgImpl, nodes: Seq[String]): Seq[NodeData] = {
    nodes.map(id => {
      Option(org.getIdentifierLocation(id)).map(location => {
        NodeData(id, PathFactory(location.path).size)
      }).getOrElse(NodeData(id, 0))
    })
  }

  @scala.annotation.tailrec
  private def collectDependencies(org: OrgImpl,
                                  depth: Int,
                                  processed: Int,
                                  nodeData: mutable.Map[String, Int],
                                  linkData: ArrayBuffer[LinkData]): Unit = {
    if (depth == 0) return

    val unhandled = nodeData.filter(kv => kv._2 >= processed)
    unhandled.foreach(source => {

      val dependencies: Array[TypeIdentifier] = org
        .getIdentifier(source._1)
        .map(tid => {
          org
            .getPackage(tid.namespace)
            .map(pkg => {
              pkg.getDependencies(tid, inheritanceOnly = false)
            })
            .getOrElse(Array[TypeIdentifier]())
        })
        .getOrElse(Array[TypeIdentifier]())

      dependencies
        .map(_.typeName.toString())
        .filterNot(_ == source._1)
        .foreach(targetName => {
          val index: Int =
            if (nodeData.contains(targetName)) {
              nodeData(targetName)
            } else {
              val i = nodeData.size
              nodeData.put(targetName, i)
              i
            }
          linkData.addOne(LinkData(source._2, index))
        })
    })

    collectDependencies(org, depth - 1, processed + unhandled.size, nodeData, linkData)
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

  override def getIssues(includeWarnings: Boolean,
                         includeZombies: Boolean): Future[GetIssuesResult] = {
    GetIssues(OrgQueue.instance(quiet), includeWarnings, includeZombies)
  }

  override def refresh(path: String, contents: Option[String]): Future[Unit] = {
    Future(OrgQueue.instance(quiet).refresh(path, contents))
  }

  override def typeIdentifiers(): Future[GetTypeIdentifiersResult] = {
    TypeIdentifiers(OrgQueue.instance(quiet))
  }

  override def dependencyGraph(path: String, depth: Int): Future[DependencyGraphResult] = {
    DependencyGraphRequest(OrgQueue.instance(quiet), path, depth)
  }

  override def identifierLocation(identifier: String): Future[IdentifierLocationResult] = {
    IdentifierLocation(OrgQueue.instance(quiet), identifier)
  }

  override def identifierForPath(path: String): Future[Option[String]] = {
    IdentifierForPath(OrgQueue.instance(quiet), path)
  }
}
