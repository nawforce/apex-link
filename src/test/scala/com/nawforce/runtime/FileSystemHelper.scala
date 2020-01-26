package com.nawforce.runtime

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.os.Path

object FileSystemHelper {

  // Abstract virtual filesystem for testing
  def run[T](files: Map[String, String])(verify: PathLike => T): T = {
    val config = Configuration.unix().toBuilder
      .setWorkingDirectory("/")
      .build()
    val fs = Jimfs.newFileSystem(config)
    val rootDir = fs.getRootDirectories.iterator().next()
    files.foreach(kv => {
      val path = rootDir.resolve(kv._1)
      Files.createDirectories(path.getParent)
      Files.write(path, kv._2.getBytes())
    })
    verify(Path(rootDir))
  }
}
