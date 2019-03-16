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

import com.nawforce.utils.{DotName, Name}
import scalaz.Memo

import scala.collection.immutable.HashMap
import scala.collection.mutable

/** Platform type declaration, a wrapper around a com.nawforce.platform Java classes */
case class PlatformTypeDeclaration(cls: java.lang.Class[_]) extends TypeDeclaration {
  lazy val name: Name = typeName.name
  lazy val typeName: TypeName = PlatformTypeDeclaration.typeName(cls)
  lazy val nature: Nature = {
    (cls.isEnum, cls.isInterface) match {
      case (true, _) => ENUM
      case (_, true) => INTERFACE
      case _ => CLASS
    }
  }

  lazy val superClass: Option[TypeName] = {
    if (cls.getSuperclass != null) {
      cls.getSuperclass.getCanonicalName match {
        case "java.lang.Object" => None
        case "java.lang.Enum" => None
        case _ => PlatformTypeDeclaration.typeNameOptional(cls.getSuperclass)
      }
    } else {
      None
    }
  }
  lazy val interfaces: Seq[TypeName] =  cls.getInterfaces.map(PlatformTypeDeclaration.typeName)
}

object PlatformTypeDeclaration {
  val platformPackage = "com.nawforce.platform"

  /** Get a Path that leads to platform classes */
  lazy val platformPackagePath: Path = {
    val path = "/" + platformPackage.replaceAll("\\.", "/")
    val uri = classOf[PlatformTypeDeclaration].getResource(path).toURI
    if (uri.getScheme.equalsIgnoreCase("file")) {
      Paths.get(uri)
    } else {
      FileSystems.newFileSystem(uri, new util.HashMap[String, String]).getPath(path)
    }
  }

  /* Get a declaration for a class from a name, if one exists, searching of inner classes is not supported here */
  def get(name: DotName): Option[PlatformTypeDeclaration] = {
    declarationCache(name)
  }

  private val declarationCache: DotName => Option[PlatformTypeDeclaration] =
    Memo.immutableHashMapMemo { name: DotName => find(name) }

  private def find(name: DotName): Option[PlatformTypeDeclaration] = {
    val matched: Option[DotName] = classNameMap.get(name)
    if (matched.size == 1)
      Some(PlatformTypeDeclaration(
        classOf[PlatformTypeDeclaration].getClassLoader.loadClass(platformPackage + "." + matched.head)
      ))
    else
      None
  }

  /* Valid platform class names */
  lazy val classNames: Iterable[DotName] = classNameMap.values

  /** Map of class names, it's a map just to allow easy recovery of the original case by looking at value */
  private lazy val classNameMap: HashMap[DotName, DotName] = {
    val names = mutable.HashMap[DotName, DotName]()
    indexDir(platformPackagePath.toFile, DotName(Seq()), names)
    HashMap[DotName, DotName]() ++ names
  }

  /* Index .class files in a path, we have to index to make sure we get natural case sensitive names */
  private def indexDir(file: File, prefix: DotName, accum: mutable.HashMap[DotName, DotName]): Unit = {
    val path = file.toPath
    file.list().foreach(name => {
      path.resolve(name).toFile match {
        case f: File if f.isDirectory =>
          indexDir(f, prefix.append(Name(name)), accum)
        case f: File if f.isFile && name.endsWith(".class") && !name.contains('$') =>
          val dotName = prefix.append(Name(name.dropRight(".class".length)))
          accum.put(dotName, dotName)
        case _ => ()
      }
    })
  }

  /** Create a TypeName from a Java class with null checking */
  private def typeNameOptional(cls: java.lang.Class[_]): Option[TypeName] = {
    cls match {
      case null => None
      case _ => Some(typeName(cls))
    }
  }

  /** Create a TypeName from a Java class */
  private def typeName(cls: java.lang.Class[_]): TypeName = {
    val cname = cls.getCanonicalName
    assert(cname.startsWith(platformPackage))

    val names = cls.getCanonicalName.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
    val params = cls.getTypeParameters.map(tp => Name(tp.getName))
    TypeName(names).withParams(params.toSeq)
  }
}


