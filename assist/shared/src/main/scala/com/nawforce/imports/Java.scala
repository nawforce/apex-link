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
package com.nawforce.imports

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.|

@js.native
trait Callback[T] extends js.Object {
  def apply(err: String, result: T): Unit = js.native
}

@js.native
trait Promisify extends js.Object {
  def apply(funct: js.Function, receiver: js.Any): js.Function = js.native
}

@js.native
trait AsyncOptions extends js.Object {
  var syncSuffix: String = js.native
  var asyncSuffix: js.UndefOr[String] = js.native
  var promiseSuffix: js.UndefOr[String] = js.native
  var promisify: js.UndefOr[Promisify] = js.native
}

@js.native
trait ProxyFunctions extends js.Object {
  @JSBracketAccess
  def apply(index: String): js.Function = js.native
  @JSBracketAccess
  def update(index: String, v: js.Function): Unit = js.native
}

@js.native
trait Java extends js.Object {
  var classpath: js.Array[String] = js.native
  var options: js.Array[String] = js.native
  var asyncOptions: AsyncOptions = js.native
  var nativeBindingLocation: String = js.native
  def callMethod(instance: js.Any, methodName: String, args: js.Array[js.Any], callback: Callback[js.Any]): Unit = js.native
  def callMethodSync(instance: js.Any, methodName: String, args: js.Any*): js.Dynamic = js.native
  def callStaticMethod(className: String, methodName: String, args: js.Any | Callback[js.Any]*): Unit = js.native
  def callStaticMethodSync(className: String, methodName: String, args: js.Any*): js.Dynamic = js.native
  def getStaticFieldValue(className: String, fieldName: String): js.Dynamic = js.native
  def setStaticFieldValue(className: String, fieldName: String, newValue: js.Any): Unit = js.native
  def instanceOf(javaObject: js.Any, className: String): Boolean = js.native
  def registerClient(before: js.Function1[Callback[Unit], Unit], after: js.Function1[Callback[Unit], Unit] = ???): Unit = js.native
  def ensureJvm(done: js.Function1[String, Unit]): Unit = js.native
  def isJvmCreated(): Boolean = js.native
  def newByte(`val`: Double): js.Dynamic = js.native
  def newChar(`val`: String | Double): js.Dynamic = js.native
  def newDouble(`val`: Double): js.Dynamic = js.native
  def newShort(`val`: Double): js.Dynamic = js.native
  def newLong(`val`: Double): js.Dynamic = js.native
  def newFloat(`val`: Double): js.Dynamic = js.native
  def `import`(className: String): js.Dynamic = js.native
  def newInstance(className: String, args: js.Any*): Unit = js.native
  def newInstanceSync(className: String, args: js.Any*): js.Dynamic = js.native
  def newArray[T](className: String, arg: js.Array[js.Any]): js.Dynamic = js.native
  def getClassLoader(): js.Dynamic = js.native
  //def newProxy(interfaceName: String, functions: ProxyFunctions): js.Dynamic = js.native
  def newProxy(interfaceName: String, functions: js.Object): js.Dynamic = js.native
}

@js.native
@JSImport("java", JSImport.Namespace)
object Java extends Java

