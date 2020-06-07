package com.nawforce.runtime

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.common.documents.ParsedCache
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.os.Path

object FileSystemHelper {

  // Abstract virtual filesystem for testing
  def run[T](files: Map[String, String], setupCache: Boolean = false)(verify: PathLike => T): T = {
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

    // Make sure cache is empty if we are going to use it
    if (setupCache)
      ParsedCache.clear()

    verify(Path(rootDir))
  }

  // Temp directory based model
  def runTempDir[T](files: Map[String, String], setupCache: Boolean = false)(verify: PathLike => T): T = {
    val tempDir = Files.createTempDirectory("apexlinktest")
    files.foreach(kv => {
      val path = tempDir.resolve(kv._1)
      Files.createDirectories(path.getParent)
      Files.write(path, kv._2.getBytes())
    })

    // Make sure cache is empty if we are going to use it
    if (setupCache)
      ParsedCache.clear()
    
    try {
      verify(Path(tempDir))
    } finally {
      files.foreach(kv => {
        val path = tempDir.resolve(kv._1)
        path.toFile.delete()
      })
      tempDir.toFile.delete()
    }
  }

}
