package com.nawforce.runtime

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.pkgforce.documents.ParsedCache
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path

object FileSystemHelper {

  // Abstract virtual filesystem for testing
  def run[T](files: Map[String, String], setupCache: Boolean = false)(verify: PathLike => T): T = {

    val os = System.getProperty("os.name")
    val config =
      if (os.contains("Windows")) {
        val b = Configuration.windows().toBuilder
        b.setWorkingDirectory("C:\\")
        b.build()
      } else if (os.contains("OS X")) {
        val b = Configuration.osX().toBuilder
        b.setWorkingDirectory("/")
        b.build()
      } else {
        val b = Configuration.unix().toBuilder
        b.setWorkingDirectory("/")
        b.build()
      }

    val fs      = Jimfs.newFileSystem(config)
    val rootDir = fs.getRootDirectories.iterator().next()
    files.foreach(kv => {
      // Allow UNIX style for test files on Windows
      var newPath = kv._1.split('/').mkString(Path.separator)
      if (newPath.head.toString == Path.separator)
        newPath = newPath.tail
      val path = rootDir.resolve(newPath)

      // Workaround Jimfs not supporting absolute without a drive
      //if (os.contains("Windows") && !path.toString.startsWith("C:"))
      //  path = rootDir.resolve("C:" + path)
      Files.createDirectories(path.getParent)
      Files.write(path, kv._2.getBytes())
    })

    // Make sure cache is empty if we are going to use it
    if (setupCache)
      ParsedCache.clear()

    verify(new Path(rootDir))
  }

  // Temp directory based model
  def runTempDir[T](files: Map[String, String], setupCache: Boolean = false)(
    verify: PathLike => T
  ): T = {
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
      verify(new Path(tempDir))
    } finally {
      files.foreach(kv => {
        val path = tempDir.resolve(kv._1)
        path.toFile.delete()
      })
      tempDir.toFile.delete()
    }
  }

}
