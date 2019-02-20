/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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

package com.nawforce.apexlink.utils

import com.sforce.soap.metadata.MetadataConnection
import com.sforce.soap.partner.PartnerConnection
import com.sforce.soap.tooling.ToolingConnection
import com.sforce.ws.{ConnectionException, ConnectorConfig}

case class SFConnection(url: String, username: String, connection: PartnerConnection, metadataURL: String, serverURL: String) {
  def metadataConnection(): MetadataConnection = {
    val config = new ConnectorConfig
    config.setServiceEndpoint(metadataURL)
    config.setSessionId(connection.getConfig.getSessionId)
    new MetadataConnection(config)
  }

  def toolingConnection(): ToolingConnection = {
    val config = new ConnectorConfig
    config.setServiceEndpoint(serverURL.replace("/u/","/T/"))
    config.setSessionId(connection.getConfig.getSessionId)
    new ToolingConnection(config)
  }
}

object SFConnection {

  def login(url: String, username: String, passwordAndToken: String): Either[String, SFConnection] = {
    val config = new ConnectorConfig
    config.setAuthEndpoint(url)
    config.setServiceEndpoint(url)
    config.setManualLogin(true)

    try {
      val connection = new PartnerConnection(config)
      val result = connection.login(username, passwordAndToken)

      val newConfig = new ConnectorConfig
      newConfig.setServiceEndpoint(result.getServerUrl)
      newConfig.setSessionId(result.getSessionId)
      Right(SFConnection(url, username, new PartnerConnection(newConfig), result.getMetadataServerUrl, result.getServerUrl))
    } catch {
      case e: ConnectionException => Left(e.toString)
    }
  }
}
