package com.nawforce.server

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
trait NodeJavaAPI extends js.Object {
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
  def newProxy(interfaceName: String, functions: ProxyFunctions): js.Dynamic = js.native
}
