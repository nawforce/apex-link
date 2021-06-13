/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.apexlink.rpc

import java.util.concurrent.LinkedBlockingQueue

import com.nawforce.apexlink.api.{IssueOptions, Org, Package, ServerOps}
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.pkgforce.diagnostics.Issue
import com.nawforce.pkgforce.names.TypeIdentifier

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

trait APIRequest {
  def process(org: OrgQueue): Unit
}

class OrgQueue(quiet: Boolean, path: String) { self =>
  val org: Org = Org.newOrg(path)
  val packages: List[Package] = Nil

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
    val orgImpl = queue.org.asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(orgImpl) {
      promise.success(GetTypeIdentifiersResult(orgImpl.getTypeIdentifiers))
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

case class DependencyGraphRequest(promise: Promise[DependencyGraph],
                                  identifier: TypeIdentifier,
                                  depth: Int)
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    promise.success(queue.org.getDependencyGraph(identifier, depth))
  }
}

object DependencyGraphRequest {
  def apply(queue: OrgQueue, identifier: TypeIdentifier, depth: Int): Future[DependencyGraph] = {
    val promise = Promise[DependencyGraph]()
    queue.add(new DependencyGraphRequest(promise, identifier, depth))
    promise.future
  }
}

case class IdentifierLocation(promise: Promise[IdentifierLocationResult],
                              identifier: TypeIdentifier)
    extends APIRequest {
  override def process(queue: OrgQueue): Unit = {
    promise.success(IdentifierLocationResult(queue.org.getIdentifierLocation(identifier)))
  }
}

object IdentifierLocation {
  def apply(queue: OrgQueue, identifier: TypeIdentifier): Future[IdentifierLocationResult] = {
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

  override def dependencyGraph(identifier: IdentifierRequest, depth: Int): Future[DependencyGraph] = {
    DependencyGraphRequest(OrgQueue.instance(), identifier.identifier, depth)
  }

  override def identifierLocation(
    request: IdentifierRequest): Future[IdentifierLocationResult] = {
    IdentifierLocation(OrgQueue.instance(), request.identifier)
  }

  override def identifierForPath(path: String): Future[Option[String]] = {
    IdentifierForPath(OrgQueue.instance(), path)
  }
}
