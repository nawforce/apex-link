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
package com.nawforce.api

import com.nawforce.cache.CacheProxy
import com.nawforce.imports.Java
import com.nawforce.server.OrgProxy
import io.scalajs.nodejs.fs.Fs

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}
import scala.util.Try

trait MetadataProxy extends js.Object {
  def activated(): Unit
}

@JSExportTopLevel("Server") @JSExportAll
class Server {
  // Currently only support single org model
  private lazy val org = new Org(OrgProxy())

  def setLogging(flags: js.Array[String]): Unit = {
    Java.callStaticMethodSync("com.nawforce.api.ServerOps", "setLogging", flags)
  }

  def getOrg(properties: js.Dynamic): Org = org
}

@JSExportTopLevel("ServerOps") @JSExportAll
object ServerOps {
  private var _jarFile: Option[String] = None
  private var _server: Option[Server] = None

  def setJarFile(jarFile: String): Boolean = {
    if (!Fs.existsSync(jarFile))
      return false
    _jarFile = Some(jarFile)
    true
  }

  def getServer(): js.Promise[Server] = {
    if (_server.nonEmpty)
      js.Promise.resolve[Server](_server.get)
    else if (_jarFile.isEmpty)
      js.Promise.reject(js.JavaScriptException("Location of ApexlLnk jar file must be set"))
    else
      launch().future.toJSPromise
  }

  private def launch(): Promise[Server] = {

    Java.options.push("-XX:+UseG1GC")
    Java.classpath.push(_jarFile.get)

    val promise = Promise[Server]()
    Java.ensureJvm(_ => {
      if (!Java.isJvmCreated()) {
        promise.failure(js.JavaScriptException(s"JVM Startup failed"))
      } else {
        _server = Some(new Server())
        setMetadataProxy(new CacheProxy)
        promise.complete(Try(_server.get))
      }
    })
    promise
  }

  private def setMetadataProxy(metadataProxy: MetadataProxy): Unit = {
    val proxy: js.Dynamic = Java.newProxy("com.nawforce.api.MetadataProxy", metadataProxy)
    Java.callStaticMethodSync("com.nawforce.api.ServerOps", "setMetadataProxy", proxy)
  }
}


