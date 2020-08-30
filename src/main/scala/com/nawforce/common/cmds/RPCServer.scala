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
package com.nawforce.common.cmds

import java.io.{BufferedReader, InputStreamReader}

import io.github.shogowada.scala.jsonrpc.Models.JSONRPCRequest
import io.github.shogowada.scala.jsonrpc.api
import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer
import io.github.shogowada.scala.jsonrpc.serializers.UpickleJSONSerializer._
import io.github.shogowada.scala.jsonrpc.server.JSONRPCServer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait TestAPI {
  @api.JSONRPCMethod(name = "foo")
  def something(msg: String): Future[String]
}

class Test extends TestAPI {
  override def something(msg: String): Future[String] = {
    Future(msg)
  }
}

class RPCTerminatedException(msg: String) extends Exception(msg)

object RPCServer {

  def main(args: Array[String]): Unit = {
    val serializer = new UpickleJSONSerializer()
    val server = JSONRPCServer(serializer)
    val testRPC = new Test
    server.bindAPI[TestAPI](testRPC)

    val request: JSONRPCRequest[Tuple1[String]] = JSONRPCRequest(
      jsonrpc = "2.0",
      id = Left("id"),
      method = "foo",
      params = Tuple1("bar")
    )
    val requestJSON: String = serializer.serialize(request).get
    println(requestJSON)
    handleMessage(server, requestJSON)

    val input = new BufferedReader(new InputStreamReader(System.in))
    var message = new StringBuilder
    val block = new Array[Char](1024)
    while (true) {
      val read = input.read(block)
      if (read == -1)
        throw new RPCTerminatedException("Read -1 from stdin")

      message.append(new String(block.slice(0, read)))
      val terminator = message.indexOf("\n\n")
      if (terminator != -1) {
        handleMessage(server, message.slice(0, terminator+1).mkString)
        message = message.slice(terminator+2, message.length)
      }
    }
  }

  private def handleMessage(server: JSONRPCServer[UpickleJSONSerializer], message: String): Unit = {
    server.receive(message).onComplete {
      case Success(Some(response: String)) =>
        println(response)
      case Success(None) =>
        System.err.println(s"No response for msg")
      case Failure(ex: Throwable) =>
        ex.printStackTrace(System.err)
    }
  }

}
