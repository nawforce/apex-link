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

import java.nio.file.{FileSystems, Files, Path, Paths}
import java.util

import com.nawforce.finding.TypeRequest.TypeRequest
import com.nawforce.finding.{MissingType, TypeError, WrongTypeArguments}
import com.nawforce.names.{DotName, Name, TypeName}
import scalaz._

import scala.collection.JavaConverters._
import scala.collection.immutable.HashMap
import scala.collection.mutable

/* Platform type declaration, a wrapper around a com.nawforce.platform Java classes */
case class PlatformTypeDeclaration(cls: java.lang.Class[_], outer: Option[PlatformTypeDeclaration])
  extends TypeDeclaration {

  override lazy val packageDeclaration: Option[PackageDeclaration] = None
  override lazy val name: Name = typeName.name
  override lazy val typeName: TypeName = PlatformTypeDeclaration.typeNameFromClass(cls, cls)
  override lazy val outerTypeName: Option[TypeName] = outer.map(_.typeName)
  override lazy val nature: Nature = {
    (cls.isEnum, cls.isInterface) match {
      case (true, _) => ENUM_NATURE
      case (_, true) => INTERFACE_NATURE
      case _ => CLASS_NATURE
    }
  }
  override val isComplete: Boolean = true
  override val isExternallyVisible: Boolean = true

  override lazy val superClass: Option[TypeName] = getSuperClass

  protected def getSuperClass: Option[TypeName] = {
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

  override def superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => PlatformTypes.get(sc, None).toOption)
  }

  override lazy val interfaces: Seq[TypeName] = getInterfaces

  protected def getInterfaces: Seq[TypeName] = cls.getInterfaces.map(i => PlatformTypeDeclaration.typeNameFromClass(i, cls))

  override lazy val modifiers: Seq[Modifier] = PlatformModifiers.typeModifiers(cls.getModifiers, nature)

  override lazy val constructors: Seq[PlatformConstructor] = {
    cls.getConstructors.map(c => new PlatformConstructor(c, this))
  }

  override lazy val nestedTypes: Seq[PlatformTypeDeclaration] =
    cls.getClasses.map(nested => PlatformTypeDeclaration(nested, Some(this)))

  override lazy val blocks: Seq[BlockDeclaration] = Seq.empty

  override lazy val fields: Seq[FieldDeclaration] = cls.getFields.filter(
    _.getDeclaringClass.getCanonicalName.startsWith(PlatformTypeDeclaration.platformPackage))
    .map(f => new PlatformField(f))

  override def findField(name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    if (isSObject) {
      super.findFieldSObject(name, staticOnly)
    } else {
      super.findField(name, staticOnly)
    }
  }

  override lazy val methods: Seq[MethodDeclaration] = getMethods

  protected def getMethods: Seq[PlatformMethod] = {
    val localMethods = cls.getMethods.filter(
      _.getDeclaringClass.getCanonicalName.startsWith(PlatformTypeDeclaration.platformPackage))
    nature match {
      case ENUM_NATURE =>
        assert(localMethods.forall(m => m.getName == "values" || m.getName == "valueOf"),
          s"Enum $name has locally defined methods which are not supported in platform types")
        Seq()
      case _ =>
        localMethods.map(m => new PlatformMethod(m, this))
    }
  }

  override def validate(): Unit = {
    // Always valid because javac said so
  }

  override def dependencies(): Set[Dependant] = {
    // Not important what these are currently
    Set.empty
  }

  override def collectDependencies(dependencies: mutable.Set[Dependant]): Unit = {}
}

class PlatformField(field: java.lang.reflect.Field) extends FieldDeclaration {
  lazy val name: Name = Name(decodeName(field.getName))
  lazy val typeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(field.getGenericType, field.getDeclaringClass)
  lazy val modifiers: Seq[Modifier] = PlatformModifiers.fieldOrMethodModifiers(field.getModifiers)
  lazy val readAccess: Modifier = PUBLIC_MODIFIER
  lazy val writeAccess: Modifier = PUBLIC_MODIFIER

  private def decodeName(name: String): String = {
    if (name.endsWith("$"))
      name.substring(0, name.length-1)
    else
      name
  }
}

class PlatformParameter(val parameter: java.lang.reflect.Parameter, val declaringClass: Class[_]) extends ParameterDeclaration {
  override lazy val name: Name = Name(parameter.getName)
  override lazy val typeName: TypeName = PlatformTypeDeclaration.typeNameFromType(parameter.getParameterizedType, declaringClass)

  override def toString: String = typeName.toString + " " + name.toString
}

class PlatformConstructor(ctor: java.lang.reflect.Constructor[_], typeDeclaration: PlatformTypeDeclaration)
  extends ConstructorDeclaration {
  lazy val modifiers: Seq[Modifier] = PlatformModifiers.methodModifiers(ctor.getModifiers, typeDeclaration.nature)
  lazy val parameters: Seq[PlatformParameter] = ctor.getParameters.map(p => new PlatformParameter(p, ctor.getDeclaringClass))
  def getDeclaringClass: Class[_] =  ctor.getDeclaringClass

  override def toString: String =
    modifiers.map(_.toString).mkString(" ") + " " + typeDeclaration.typeName.toString + "(" +
      parameters.map(_.toString).mkString(", ") + ")"
}

class PlatformMethod(val method: java.lang.reflect.Method, val typeDeclaration: PlatformTypeDeclaration)
  extends MethodDeclaration {
  lazy val name: Name = Name(decodeName(method.getName))
  lazy val typeName: TypeName = PlatformTypeDeclaration.typeNameFromClass(method.getReturnType, method.getDeclaringClass)
  lazy val modifiers: Seq[Modifier] = PlatformModifiers.methodModifiers(method.getModifiers, typeDeclaration.nature)
  lazy val parameters: Seq[ParameterDeclaration] = getParameters
  def getDeclaringClass: Class[_] =  method.getDeclaringClass

  def getParameters: Seq[PlatformParameter] = method.getParameters.map(p => new PlatformParameter(p, method.getDeclaringClass))

  override def toString: String =
    modifiers.map(_.toString).mkString(" ") + " " + typeName.toString + " " + name.toString + "(" +
      parameters.map(_.toString).mkString(", ") + ")"

  private def decodeName(name: String): String = {
    if (name.endsWith("$"))
      name.substring(0, name.length-1)
    else
      name
  }
}

object PlatformTypeDeclaration {
  val platformPackage = "com.nawforce.platform"

  /* Get a Path that leads to platform classes */
  lazy val platformPackagePath: Path = {
    val path = "/" + platformPackage.replaceAll("\\.", "/")
    val uri = classOf[PlatformTypeDeclaration].getResource(path).toURI
    if (uri.getScheme.equalsIgnoreCase("file")) {
      Paths.get(uri)
    } else {
      FileSystems.newFileSystem(uri, new util.HashMap[String, String]).getPath(path)
    }
  }

  /* Get a type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. If needed this will construct a GenericPlatformTypeDeclaration to specialise a
   * PlatformTypeDeclaration but it does not handle nested classes, see PlatformTypes for that.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {
    val tdOption = declarationCache(typeName.asDotName)
    if (tdOption.isEmpty)
      return Left(MissingType(typeName))

    // Quick fail on wrong number of type variables
    val td = tdOption.get
    if (td.typeName.params.size != typeName.params.size)
      return Left(WrongTypeArguments(typeName, td.typeName.params.size))

    if (td.typeName.params.nonEmpty)
      GenericPlatformTypeDeclaration.get(typeName, from)
    else
      Right(td)
  }

  /* Get a declaration for a class from a DotName, in general don't call this direct, use TypeRequest which will
   * delegate here if needed. This does not handle generics or inner classes
   */
  def getDeclaration(name: DotName): Option[PlatformTypeDeclaration] = {
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
  lazy val classNames: Iterable[DotName] = classNameMap.keys

  /* All the namespaces - excluding our special ones! */
  lazy val namespaces: Set[Name] = classNameMap.keys.filter(_.isCompound).map(_.firstName)
    .filterNot(name => name == Name.SObjects || name == Name.Internal).toSet

  /* Map of class names, it's a map just to allow easy recovery of the original case by looking at value */
  private lazy val classNameMap: HashMap[DotName, DotName] = {
    val names = mutable.HashMap[DotName, DotName]()
    indexDir(platformPackagePath, DotName(Seq()), names)
    HashMap[DotName, DotName]() ++ names
  }

  /* Index .class files, we have to index to make sure we get natural case sensitive names, but also used
   * to re-map SObject so they appear in root of platform namespace.
   */
  private def indexDir(path: Path, prefix: DotName, accum: mutable.HashMap[DotName, DotName]): Unit = {
    Files.list(path).iterator.asScala.foreach(entry => {
      val filename = entry.getFileName.toString
      if (Files.isRegularFile(entry) && filename.endsWith(".class") &&
        (filename.endsWith("$.class") || !filename.contains('$'))) {
        val dotName = prefix.append(Name(filename.dropRight(".class".length)))
        if (dotName.names.head == Name.SObjects)
          accum.put(DotName(dotName.names.tail), dotName)
        else
          accum.put(dotName, dotName)
      }
      else if (Files.isDirectory(entry)) {
        val safeFilename = filename.replace("/", "").replace("\\", "")
        indexDir(entry, prefix.append(Name(safeFilename)), accum)
      }
    })
  }

  /* Create a TypeName from a Java class with null checking */
  private def typeNameOptional(cls: java.lang.Class[_], contextCls: java.lang.Class[_]): Option[TypeName] = {
    cls match {
      case null => None
      case _ => Some(typeNameFromClass(cls, contextCls))
    }
  }

  /* Create a TypeName from a Java Type, handles type variables as well as classes */
  def typeNameFromType(paramType: java.lang.reflect.Type, contextCls: java.lang.Class[_]): TypeName = {
    paramType match {
      case cls: Class[_] => PlatformTypeDeclaration.typeNameFromClass(cls, contextCls)
      case tv: java.lang.reflect.TypeVariable[_] => TypeName(Name(tv.getName))
      case pt: java.lang.reflect.ParameterizedType =>
        val cname = pt.getRawType.getTypeName
        assert(cname.startsWith(platformPackage), s"Reference to non-platform type $cname in ${contextCls.getCanonicalName}")
        val names = cname.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
        val params = pt.getActualTypeArguments.map(ta => typeNameFromType(ta, contextCls))
        TypeName(names).withParams(params)
    }
  }

  /* Create a TypeName from a Java class */
  def typeNameFromClass(cls: java.lang.Class[_], contextCls: java.lang.Class[_]): TypeName = {
    val cname = if (cls.isArray) cls.getComponentType.getCanonicalName else cls.getCanonicalName
    val typeName =
      if (cname == "java.lang.Object") {
        TypeName.Object
      } else if (cname == "void") {
        TypeName.Void
      } else if (cname.startsWith(platformPackage+".SObjects")) {
        val names = cname.drop(platformPackage.length + 10).split('.').map(n => Name(n)).reverse
        val params = cls.getTypeParameters.map(tp => Name(tp.getName))
        TypeName(names).withParams(params.toSeq.map(TypeName(_)))
      } else {
        assert(cname.startsWith(platformPackage), s"Reference to non-platform type $cname in ${contextCls.getCanonicalName}")
        val names = cname.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
        val params = cls.getTypeParameters.map(tp => Name(tp.getName))
        TypeName(names).withParams(params.toSeq.map(TypeName(_)))
      }
    if (cls.isArray)
      TypeName.listOf(typeName)
    else
      typeName
  }
}



