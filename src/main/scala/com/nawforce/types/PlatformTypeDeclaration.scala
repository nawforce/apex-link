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
package com.nawforce.types

import java.io.File
import java.nio.file.{FileSystems, Path, Paths}
import java.util

import com.nawforce.utils.{Name, QName}
import scalaz.Memo

case class PlatformTypeDeclaration(cls: java.lang.Class[_]) extends TypeDeclaration {
  lazy val typeName: TypeName = PlatformTypeDeclaration.typeName(cls)

  def name: Name = typeName.name

  //  def extendsQName: QName
  //  def implementsQNames: Seq[QName]

}

object PlatformTypeDeclaration {
  val platformPackage = "com.nawforce.platform"

  lazy val platformPackagePath: Path = {
    val path = "/" + platformPackage.replaceAll("\\.", "/")
    val uri = classOf[PlatformTypeDeclaration].getResource(path).toURI
    if (uri.getScheme.equalsIgnoreCase("file")) {
      Paths.get(uri)
    } else {
      FileSystems.newFileSystem(uri, new util.HashMap[String, String]).getPath(path)
    }
  }

  lazy val platformPackagePathPrefix: String = platformPackagePath.toString

  def get(name: QName): Option[PlatformTypeDeclaration] = {
    declarationCache(name)
  }

  private val declarationCache: QName => Option[PlatformTypeDeclaration] =
    Memo.immutableHashMapMemo { name: QName => find(name) }

  private def find(name: QName, path: Path = platformPackagePath): Option[PlatformTypeDeclaration] = {
    matchFile(name.name.toString, path) match {
      case Some(f: File) if f.isFile =>
        Some(PlatformTypeDeclaration(classOf[PlatformTypeDeclaration].getClassLoader.loadClass(classNameOf(f))))
      case Some(f: File) if f.isDirectory && name.outer.isDefined =>
        find(name.outer.get, f.toPath)
      case _ => None
    }
  }

  private def matchFile(name: String, path: Path): Option[File] = {
    val testFile = path.resolve(name).toFile
    val testFileClass = path.resolve(name + ".class").toFile

    if (testFile.isFile || testFile.isDirectory) {
      Some(testFile)
    } else if (testFileClass.isFile || testFileClass.isDirectory) {
      Some(testFileClass)
    } else {
      val matches = path.toFile.list().flatMap(n => {
        if (n.equalsIgnoreCase(testFile.getName) || n.equalsIgnoreCase(testFileClass.getName))
          Some(path.resolve(n))
        else
          None
      })
      if (matches.length == 1)
        Some(path.resolve(matches.head).toFile)
      else
        None
    }
  }

  private def classNameOf(file: File): String = {
    assert(file.toString.startsWith(platformPackagePathPrefix))
    platformPackage + "." + file.toString.drop(platformPackagePathPrefix.length + 1)
      .replace(File.separatorChar, '.')
      .dropRight(".class".length)
  }

  private def typeName(cls: java.lang.Class[_]): TypeName = {
    val cname = cls.getCanonicalName
    assert(cname.startsWith(platformPackage))

    val names = cls.getCanonicalName.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
    val params = cls.getTypeParameters.map(tp => {
      assert(tp.getAnnotatedBounds.isEmpty)
      assert(tp.getBounds.isEmpty)
      Name(tp.getName)
    })
    TypeName(names).withParams(params.toSeq)
  }
}


