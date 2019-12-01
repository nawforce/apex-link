package com.nawforce.server

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("java", JSImport.Namespace)
@js.native
object JavaImport extends NodeJavaAPI {
}

@JSImport("fs", JSImport.Namespace)
@js.native
object FSImport extends js.Object {
  def existsSync(name: String): Boolean = js.native
}


