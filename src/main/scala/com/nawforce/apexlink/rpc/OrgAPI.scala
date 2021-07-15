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

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import io.github.shogowada.scala.jsonrpc.api
import io.github.shogowada.scala.jsonrpc.serializers.JSONRPCPickler.{macroRW, ReadWriter => RW}

import scala.concurrent.Future

case class APIError(message: String, stack: Array[String])

object APIError {
  implicit val rw: RW[APIError] = macroRW

  def apply(ex: Throwable): APIError = {
    new APIError(ex.getMessage, ex.getStackTrace.map(_.toString))
  }

  def apply(message: String): APIError = {
    new APIError(message, Array())
  }
}

case class OpenResult(error: Option[APIError], namespaces: Array[String])

object OpenResult {
  implicit val rw: RW[OpenResult] = macroRW
}

case class GetIssuesResult(issues: Array[Issue])

object GetIssuesResult {
  implicit val rw: RW[GetIssuesResult] = macroRW
  implicit val rwIssue: RW[Issue] = macroRW
  implicit val rwDiagnostic: RW[Diagnostic] = macroRW
  implicit val rwDiagnosticCategory: RW[DiagnosticCategory] = macroRW
  implicit val rwLocation: RW[Location] = macroRW
}

case class GetTypeIdentifiersResult(identifiers: Array[TypeIdentifier])

object GetTypeIdentifiersResult {
  implicit val rw: RW[GetTypeIdentifiersResult] = macroRW
  implicit val rwTypeIdentifier: RW[TypeIdentifier] = macroRW
  implicit val rwTypeName: RW[TypeName] = macroRW
  implicit val rwName: RW[Name] = macroRW
}

case class IdentifierLocationResult(pathLocation: PathLocation)

object IdentifierLocationResult {
  implicit val rw: RW[IdentifierLocationResult] = macroRW
  implicit val rwPathLocation: RW[PathLocation] = macroRW
  implicit val rwLocation: RW[Location] = macroRW
}

case class IdentifierRequest(identifier: TypeIdentifier)

object IdentifierRequest {
  implicit val rw: RW[IdentifierRequest] = macroRW
  implicit val rwTypeIdentifier: RW[TypeIdentifier] = macroRW
  implicit val rwTypeName: RW[TypeName] = macroRW
  implicit val rwName: RW[Name] = macroRW
}

case class IdentifierForPathResult(identifier: Option[TypeIdentifier])

object IdentifierForPathResult {
  implicit val rw: RW[IdentifierForPathResult] = macroRW
  implicit val rwTypeIdentifier: RW[TypeIdentifier] = macroRW
  implicit val rwTypeName: RW[TypeName] = macroRW
  implicit val rwName: RW[Name] = macroRW
}

trait OrgAPI {
  @api.JSONRPCMethod(name = "identifier")
  def identifier(): Future[String]

  @api.JSONRPCMethod(name = "reset")
  def reset(): Future[Unit]

  @api.JSONRPCMethod(name = "open")
  def open(directory: String): Future[OpenResult]

  @api.JSONRPCMethod(name = "getIssues")
  def getIssues(includeWarnings: Boolean, includeZombies: Boolean): Future[GetIssuesResult]

  @api.JSONRPCMethod(name = "refresh")
  def refresh(path: String): Future[Unit]

  @api.JSONRPCMethod(name = "getTypeIdentifiers")
  def typeIdentifiers(apexOnly: Boolean): Future[GetTypeIdentifiersResult]

  @api.JSONRPCMethod(name = "dependencyGraph")
  def dependencyGraph(identifier: IdentifierRequest, depth: Int): Future[DependencyGraph]

  @api.JSONRPCMethod(name = "identifierLocation")
  def identifierLocation(identifier: IdentifierRequest): Future[IdentifierLocationResult]

  @api.JSONRPCMethod(name = "identifierForPath")
  def identifierForPath(path: String): Future[IdentifierForPathResult]
}

object OrgAPI {
  // Just a test entry point
  def apply(): OrgAPI = new OrgAPIImpl(true)
}
