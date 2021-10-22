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

import java.io.{BufferedReader, InputStreamReader, PrintStream}

import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer
import io.github.shogowada.scala.jsonrpc.server.JSONRPCServer
import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

// In case IntelliJ messes up the imports again stick this back in
// import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer._

class RPCTerminatedException(msg: String) extends Exception(msg)

class RPCServer {
  private val serializer = new UpickleJSONSerializer()
  private val server = JSONRPCServer(serializer)
  server.bindAPI[OrgAPI](new OrgAPIImpl(quiet=false))

  def run(): Unit = {
    val input = new BufferedReader(new InputStreamReader(System.in))
    var message = new StringBuilder
    val block = new Array[Char](1024)
    while (true) {
      val read = input.read(block)
      if (read == -1)
        throw new RPCTerminatedException("Read -1 from stdin")

      val existingLength = message.length()
      message.append(new String(block.slice(0, read)))
      var terminator = message.indexOf('\u0000', existingLength)
      while (terminator != -1) {
        val msg = message.slice(0, terminator).mkString
        handleMessage(msg, System.out)
        message = message.slice(terminator + 1, message.length)
        terminator = message.indexOf('\u0000')
      }
    }
  }

  def handleMessage(message: String, stream: PrintStream): Unit = {
    server.receive(message).onComplete {
      case Success(Some(response: String)) =>
        synchronized {
          stream.print(response)
          stream.print('\u0000')
        }
      case Success(None) =>
        throw new RPCTerminatedException(s"No response: $message")
      case Failure(ex: Throwable) =>
        throw ex
    }
  }
}

