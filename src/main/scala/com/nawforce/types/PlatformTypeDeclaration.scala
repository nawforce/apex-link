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
case class PlatformTypeDeclaration(cls: java.lang.Class[_], parent: Option[PlatformTypeDeclaration])
  extends TypeDeclaration {

  lazy val name: Name = typeName.name
  lazy val typeName: TypeName = PlatformTypeDeclaration.typeName(cls, cls)
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
        case _ => PlatformTypeDeclaration.typeNameOptional(cls.getSuperclass, cls)
      }
    } else {
      None
    }
  }
  lazy val interfaces: Seq[TypeName] = cls.getInterfaces.map(i => PlatformTypeDeclaration.typeName(i, cls))

  lazy val modifiers: Seq[Modifier] = Modifiers.typeModifiers(cls.getModifiers, nature)

  lazy val nestedClasses: Seq[PlatformTypeDeclaration] =
    cls.getClasses.map(nested => PlatformTypeDeclaration(nested, Some(this)))

  case class Field(field: java.lang.reflect.Field) extends FieldDeclaration {
    lazy val name: Name = Name(field.getName)
    lazy val typeName: TypeName = PlatformTypeDeclaration.typeName(field.getType, field.getDeclaringClass)
    lazy val modifiers: Seq[Modifier] = Modifiers.fieldOrMethodModifiers(field.getModifiers)
  }

  lazy val fields: Seq[FieldDeclaration] = cls.getFields.map(f => Field(f))

  case class Parameter(parameter: java.lang.reflect.Parameter, method: Method) extends ParameterDeclaration {
    lazy val name: Name = Name(parameter.getName)
    lazy val typeName: TypeName = PlatformTypeDeclaration.typeName(parameter.getType, method.getDeclaringClass)

    override def toString: String = typeName.toString + " " + name.toString
  }

  case class Method(method: java.lang.reflect.Method, typeDeclaration: PlatformTypeDeclaration) extends MethodDeclaration {
    lazy val name: Name = Name(method.getName)
    lazy val typeName: TypeName = PlatformTypeDeclaration.typeName(method.getReturnType, method.getDeclaringClass)
    lazy val modifiers: Seq[Modifier] = Modifiers.methodModifiers(method.getModifiers, typeDeclaration.nature)
    lazy val parameters: Seq[Parameter] = method.getParameters.map(p => Parameter(p, this))
    def getDeclaringClass: Class[_] =  method.getDeclaringClass

    override def toString: String =
      modifiers.map(_.toString).mkString(" ") + " " + typeName.toString + " " + name.toString + "(" +
        parameters.map(_.toString).mkString(", ") + ")"
  }

  lazy val methods: Seq[MethodDeclaration] = {
    val localMethods = cls.getMethods.filter(_.getDeclaringClass eq cls)
    nature match {
      case ENUM =>
        assert(localMethods.forall(m => m.getName == "values" || m.getName == "valueOf"),
          s"Enum $name has locally defined methods which are not supported in platform types")
        Seq()
      case _ =>
        localMethods.map(m => Method(m, this))
    }
  }
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
    val matched = classNameMap.get(name)
    assert(matched.size < 2, s"Found multiple platform type matches for $name")
    matched.map(name => PlatformTypeDeclaration(
      classOf[PlatformTypeDeclaration].getClassLoader.loadClass(platformPackage + "." + name),
      None))
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
  private def typeNameOptional(cls: java.lang.Class[_], contextCls: java.lang.Class[_]): Option[TypeName] = {
    cls match {
      case null => None
      case _ => Some(typeName(cls, contextCls))
    }
  }

  /** Create a TypeName from a Java class */
  private def typeName(cls: java.lang.Class[_], contextCls: java.lang.Class[_]): TypeName = {
    val cname = cls.getCanonicalName
    if (cname == "java.lang.Object") {
      TypeName.Object
    } else if (cname == "void") {
      TypeName.Void
    } else {
      if (!cname.startsWith(platformPackage))
        println("")
      assert(cname.startsWith(platformPackage), s"Reference to non-platform type $cname in ${contextCls.getCanonicalName}")
      val names = cls.getCanonicalName.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
      val params = cls.getTypeParameters.map(tp => Name(tp.getName))
      TypeName(names).withParams(params.toSeq)
    }
  }
}




