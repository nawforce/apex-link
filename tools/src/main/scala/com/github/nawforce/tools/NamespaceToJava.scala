/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.github.nawforce.tools

import java.io.{BufferedWriter, ByteArrayOutputStream, FileWriter}
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

import io.github.nawforce.apexlink.utils.SFConnection
import net.liftweb.json._
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

case class AbortException(msg: String)  extends Exception(msg)

object NamespaceToJava {
  def loginURL = "https://login.salesforce.com/services/Soap/u/45.0"

  def main(args: Array[String]): Unit = {

    args.length match {
      case 3 => ()
      case _ => println("Usage: NamespaceToJava <username> <password> <namespace>"); return
    }

    val connectionResult = SFConnection.login(loginURL, args(0), args(1))
    if (connectionResult.isLeft) {
      println(connectionResult.left.get)
      return
    }

    val serverURL = new URL(connectionResult.right.get.connection.getConfig.getServiceEndpoint)
    val completions = getCompletions(
      serverURL.toString.dropRight(serverURL.getPath.length),
      connectionResult.right.get.connection.getConfig.getSessionId
    )

    (completions \ args(2)).asInstanceOf[JObject].obj.foreach(json => {
      val cls = PlatformClass(args(2), json)

      val output = Paths.get(cls.name+".java").toFile
      val writer = new BufferedWriter(new FileWriter(output))
      writer.write(cls.asJava)
      writer.flush()
      writer.close()
    })
  }

  private def getCompletions(endpoint: String, sessionId: String): JObject = {
    val client = HttpClientBuilder.create.build()
    val request = new HttpGet(endpoint + "/services/data/v45.0/tooling/completions/?type=apex")
    request.addHeader("Authorization", "Bearer " + sessionId)
    request.addHeader("Content-Type", "application/json")
    request.addHeader("Accept", "application/json")

    val response = client.execute(request)
    if (response.getStatusLine.getStatusCode != 200) {
      throw AbortException(response.getStatusLine.toString)
    }

    val responseStream = response.getEntity.getContent
    val result = new ByteArrayOutputStream
    val buffer = new Array[Byte](1024)
    var length = 0
    while ({length = responseStream.read(buffer); length != -1})
      result.write(buffer, 0, length)
    (parse(result.toString(StandardCharsets.UTF_8.name())) \ "publicDeclarations").asInstanceOf[JObject]
  }
}
