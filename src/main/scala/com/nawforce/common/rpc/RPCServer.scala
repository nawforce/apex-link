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

import java.io.{BufferedReader, InputStreamReader, PrintStream}

import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer
import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer._
import io.github.shogowada.scala.jsonrpc.server.JSONRPCServer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class RPCTerminatedException(msg: String) extends Exception(msg)

class RPCServer {
  private val serializer = new UpickleJSONSerializer()
  private val server = JSONRPCServer(serializer)
  server.bindAPI[OrgAPI](new OrgAPIImpl())

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
      var terminator = message.indexOf("\n\n", existingLength)
      while (terminator != -1) {
        val msg = message.slice(0, terminator + 1).mkString
        handleMessage(msg, System.out)
        message = message.slice(terminator + 2, message.length)
        terminator = message.indexOf("\n\n")
      }
    }
  }

  def handleMessage(message: String, stream: PrintStream): Unit = {
    server.receive(message).onComplete {
      case Success(Some(response: String)) =>
        stream.print(encodeJSON(response))
        stream.print("\n\n")
      case Success(None) =>
        throw new RPCTerminatedException(s"No response: $message")
      case Failure(ex: Throwable) =>
        throw ex
    }
  }

  private def encodeJSON(json: String): String = {
    // New lines are used as message terminator so we best remove any used in formatting
    if (json.indexOf('\n') == 1)
      json
    else
      json.replace("\\n", "")
  }
}
