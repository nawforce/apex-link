/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.types.platform

import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.finding.{MissingType, WrongTypeArguments}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.schema.SObjectFieldFinder
import com.nawforce.apexlink.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.pkgforce.modifiers.{Modifier, PUBLIC_MODIFIER}
import com.nawforce.pkgforce.names.{DotName, Name, Names, TypeName}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE, Nature}
import com.nawforce.pkgforce.path.{Location, PathLike, PathLocation}
import com.nawforce.runforce.Internal.Object$

import java.nio.file.{FileSystemNotFoundException, FileSystems, Files, Paths}
import java.util
import scala.collection.immutable.{ArraySeq, HashMap}
import scala.collection.mutable
import scala.jdk.CollectionConverters._

/* Platform type declaration, a wrapper around a com.nawforce.platform Java classes */
class PlatformTypeDeclaration(val native: Any, val outer: Option[PlatformTypeDeclaration])
    extends TypeDeclaration
    with SObjectFieldFinder {

  val cls: java.lang.Class[_] = native.asInstanceOf[java.lang.Class[_]]

  override def paths: ArraySeq[PathLike]              = PathLike.emptyPaths
  override lazy val moduleDeclaration: Option[Module] = None

  override lazy val name: Name                      = typeName.name
  override lazy val typeName: TypeName              = PlatformTypeDeclaration.typeNameFromClass(cls, cls)
  override lazy val outerTypeName: Option[TypeName] = outer.map(_.typeName)
  override lazy val nature: Nature = {
    (cls.isEnum, cls.isInterface) match {
      case (true, _) => ENUM_NATURE
      case (_, true) => INTERFACE_NATURE
      case _         => CLASS_NATURE
    }
  }
  override val isComplete: Boolean = true

  override lazy val superClass: Option[TypeName] = getSuperClass

  protected def getSuperClass: Option[TypeName] = {
    if (cls.getSuperclass != null) {
      cls.getSuperclass.getCanonicalName match {
        case "java.lang.Object" => None
        case "java.lang.Enum"   => None
        case _                  => PlatformTypeDeclaration.typeNameOptional(cls.getSuperclass, cls)
      }
    } else {
      None
    }
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => PlatformTypes.get(sc, None).toOption)
  }

  override lazy val interfaces: ArraySeq[TypeName] = getInterfaces

  override lazy val interfaceDeclarations: ArraySeq[TypeDeclaration] = {
    getInterfaces.flatMap(id => PlatformTypes.get(id, None).toOption)
  }

  protected def getInterfaces: ArraySeq[TypeName] =
    ArraySeq.unsafeWrapArray(
      cls.getGenericInterfaces.map(i => PlatformTypeDeclaration.typeNameFromType(i, cls))
    )

  override lazy val modifiers: ArraySeq[Modifier] =
    PlatformModifiers.typeModifiers(cls.getModifiers, nature)

  override lazy val constructors: ArraySeq[PlatformConstructor] = {
    ArraySeq
      .unsafeWrapArray(cls.getConstructors)
      .filterNot(_.isSynthetic)
      .map(c => new PlatformConstructor(c, this))
  }

  override lazy val nestedTypes: ArraySeq[TypeDeclaration] = {
    // JVM12 is adding a nested class to enums so we restrict nesting just to classes
    if (nature == CLASS_NATURE) {
      ArraySeq.unsafeWrapArray(
        cls.getClasses.map(nested => new PlatformTypeDeclaration(nested, Some(this)))
      )
    } else {
      TypeDeclaration.emptyTypeDeclarations
    }
  }

  override lazy val blocks: ArraySeq[BlockDeclaration] = BlockDeclaration.emptyBlockDeclarations

  override lazy val fields: ArraySeq[FieldDeclaration] = getFields

  protected def getFields: ArraySeq[PlatformField] =
    ArraySeq.unsafeWrapArray(collectFields(cls).values.toArray)

  private def collectFields(
    cls: Class[_],
    accum: mutable.Map[Name, PlatformField] = mutable.Map()
  ): mutable.Map[Name, PlatformField] = {
    if (cls.getCanonicalName.startsWith(PlatformTypeDeclaration.platformPackage)) {
      cls.getDeclaredFields
        .filterNot(_.isSynthetic)
        .foreach(f => {
          val name = Name(f.getName)
          if (!accum.contains(name))
            accum.put(name, new PlatformField(f))
        })
      Option(cls.getSuperclass).foreach(superClass => collectFields(superClass, accum))
    }
    accum
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (isSObject) {
      findFieldSObject(name, staticContext)
    } else {
      super.findField(name, staticContext)
    }
  }

  override lazy val methods: ArraySeq[MethodDeclaration] = {
    nature match {
      case ENUM_NATURE => PlatformTypeDeclaration.enumMethods(typeName)
      case _           => getMethods
    }
  }

  protected def getMethods: ArraySeq[PlatformMethod] = {
    val localMethods =
      cls.getMethods
        .filter(
          _.getDeclaringClass.getCanonicalName.startsWith(PlatformTypeDeclaration.platformPackage)
        )
        .filterNot(_.isSynthetic)
    nature match {
      case ENUM_NATURE =>
        assert(
          localMethods.forall(m => m.getName == "values" || m.getName == "valueOf"),
          s"Enum $name has locally defined methods which are not supported in platform types"
        )
        ArraySeq[PlatformMethod]()
      case _ =>
        ArraySeq.unsafeWrapArray(localMethods.map(m => new PlatformMethod(m, this)))
    }
  }

  override def validate(): Unit = {
    // Always valid because javac said so
  }
}

class PlatformField(val field: java.lang.reflect.Field) extends FieldDeclaration {
  override def location: PathLocation = null
  override lazy val name: Name        = Name(decodeName(field.getName))
  override lazy val typeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(field.getGenericType, field.getDeclaringClass)
  override lazy val modifiers: ArraySeq[Modifier] =
    PlatformModifiers.fieldOrMethodModifiers(field.getModifiers)
  override lazy val readAccess: Modifier       = PUBLIC_MODIFIER
  override lazy val writeAccess: Modifier      = PUBLIC_MODIFIER
  override lazy val idTarget: Option[TypeName] = None

  def getGenericTypeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(field.getGenericType, field.getDeclaringClass)

  private def decodeName(name: String): String = {
    if (name.endsWith("$"))
      name.substring(0, name.length - 1)
    else
      name
  }
}

class PlatformParameter(val parameter: java.lang.reflect.Parameter, val declaringClass: Class[_])
    extends ParameterDeclaration {
  override lazy val name: Name = Name(parameter.getName)
  override lazy val typeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(parameter.getParameterizedType, declaringClass)

  def getGenericTypeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(parameter.getParameterizedType, declaringClass)

  override def toString: String = typeName.toString + " " + name.toString
}

class PlatformConstructor(
  ctor: java.lang.reflect.Constructor[_],
  typeDeclaration: PlatformTypeDeclaration
) extends ConstructorDeclaration {
  lazy val modifiers: ArraySeq[Modifier] =
    PlatformModifiers.ctorModifiers(ctor.getModifiers)
  lazy val parameters: ArraySeq[ParameterDeclaration] =
    ArraySeq.unsafeWrapArray(
      ctor.getParameters.map(p => new PlatformParameter(p, ctor.getDeclaringClass))
    )

  def getDeclaringClass: Class[_] = ctor.getDeclaringClass

  override def toString: String =
    modifiers.map(_.toString).mkString(" ") + " " + typeDeclaration.typeName.toString + "(" +
      parameters.map(_.toString).mkString(", ") + ")"
}

class PlatformMethod(
  val method: java.lang.reflect.Method,
  val typeDeclaration: PlatformTypeDeclaration
) extends MethodDeclaration {
  lazy val name: Name = Name(decodeName(method.getName))
  lazy val typeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(method.getGenericReturnType, method.getDeclaringClass)
  lazy val modifiers: ArraySeq[Modifier] =
    PlatformModifiers.methodModifiers(method.getModifiers, typeDeclaration.nature)
  lazy val parameters: ArraySeq[ParameterDeclaration] = getParameters
  override val hasBlock: Boolean                      = false

  def getParameters: ArraySeq[ParameterDeclaration] =
    ArraySeq.unsafeWrapArray(
      method.getParameters.map(p => new PlatformParameter(p, method.getDeclaringClass))
    )

  def getGenericTypeName: TypeName =
    PlatformTypeDeclaration.typeNameFromType(method.getGenericReturnType, method.getDeclaringClass)

  private def decodeName(name: String): String = {
    if (name.endsWith("$"))
      name.substring(0, name.length - 1)
    else
      name
  }
}

object PlatformTypeDeclaration {
  /* Java package prefix for platform types */
  private val platformPackage = "com.nawforce.runforce"

  /* Cache of loaded platform declarations */
  private val declarationCache = mutable.Map[DotName, Option[PlatformTypeDeclaration]]()

  /* Get a Path that leads to platform classes */
  lazy val platformPackagePath: java.nio.file.Path = {
    val path = "/" + platformPackage.replaceAll("\\.", "/")
    val uri  = classOf[Object$].getResource(path).toURI
    if (uri.getScheme.equalsIgnoreCase("file")) {
      Paths.get(uri)
    } else {
      try {
        FileSystems.getFileSystem(uri).getPath(path)
      } catch {
        case _: FileSystemNotFoundException =>
          FileSystems.newFileSystem(uri, new util.HashMap[String, String]).getPath(path)
      }
    }
  }

  /* Get a type, in general don't call this direct, use TypeRequest which will delegate here. If needed this will
   * construct a GenericPlatformTypeDeclaration to specialise a PlatformTypeDeclaration but you must provide from
   * for that to be possible as this allows discovery of the correct type arguments.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeResponse = {
    val tdOption = getDeclaration(typeName.asDotName)
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
    declarationCache.getOrElseUpdate(
      name, {
        val matched = classNameMap.get(name)
        assert(matched.size < 2, s"Found multiple platform type matches for $name")
        matched.map(
          name =>
            new PlatformTypeDeclaration(
              classOf[PlatformTypeDeclaration].getClassLoader
                .loadClass(platformPackage + "." + name),
              None
            )
        )
      }
    )
  }

  /* Valid platform class names */
  lazy val classNames: Iterable[DotName] = classNameMap.keys

  /* All the namespaces - excluding our special ones! */
  lazy val namespaces: Set[Name] = classNameMap.keys
    .filter(_.isCompound)
    .map(_.firstName)
    .filterNot(name => name == Names.SObjects || name == Names.Internal)
    .toSet

  /* Map of class names, it's a map just to allow easy recovery of the original case by looking at value */
  private lazy val classNameMap: HashMap[DotName, DotName] = {
    val names = mutable.HashMap[DotName, DotName]()
    indexDir(platformPackagePath, DotName(Seq()), names)
    HashMap[DotName, DotName]() ++ names
  }

  /* Index .class files, we have to index to make sure we get natural case sensitive names, but also used
   * to re-map SObject so they appear in Schema namespace.
   */
  private def indexDir(
    path: java.nio.file.Path,
    prefix: DotName,
    accum: mutable.HashMap[DotName, DotName]
  ): Unit = {
    Files
      .list(path)
      .iterator
      .asScala
      .foreach(entry => {
        val filename = entry.getFileName.toString
        if (
          Files.isRegularFile(entry) && filename.endsWith(".class") &&
          (filename.endsWith("$.class") || !filename.contains('$'))
        ) {
          val dotName = prefix.append(Name(filename.dropRight(".class".length)))
          if (dotName.names.head == Names.SObjects) {
            accum.put(DotName(Names.Schema +: dotName.names.tail), dotName)
          } else {
            accum.put(dotName, dotName)
          }
        } else if (Files.isDirectory(entry)) {
          val safeFilename = filename.replace("/", "").replace("\\", "")
          indexDir(entry, prefix.append(Name(safeFilename)), accum)
        }
      })
  }

  /* Create a TypeName from a Java class with null checking */
  private def typeNameOptional(
    cls: java.lang.Class[_],
    contextCls: java.lang.Class[_]
  ): Option[TypeName] = {
    cls match {
      case null => None
      case _    => Some(typeNameFromClass(cls, contextCls))
    }
  }

  /* Create a TypeName from a Java Type, handles type variables as well as classes */
  def typeNameFromType(paramTypeNative: Any, contextClsNative: Any): TypeName = {
    val paramType  = paramTypeNative.asInstanceOf[java.lang.reflect.Type]
    val contextCls = contextClsNative.asInstanceOf[java.lang.Class[_]]

    paramType match {
      case cls: Class[_]                         => PlatformTypeDeclaration.typeNameFromClass(cls, contextCls)
      case tv: java.lang.reflect.TypeVariable[_] => TypeName(Name(tv.getName))
      case pt: java.lang.reflect.ParameterizedType =>
        val cname = pt.getRawType.getTypeName
        assert(
          cname.startsWith(platformPackage),
          s"Reference to non-platform type $cname in ${contextCls.getCanonicalName}"
        )
        val names  = cname.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
        val params = pt.getActualTypeArguments.map(ta => typeNameFromType(ta, contextCls))
        TypeName(ArraySeq.unsafeWrapArray(names)).withParams(ArraySeq.unsafeWrapArray(params))
    }
  }

  /* Create a TypeName from a Java class */
  def typeNameFromClass(cls: java.lang.Class[_], contextCls: java.lang.Class[_]): TypeName = {
    val cname = if (cls.isArray) cls.getComponentType.getCanonicalName else cls.getCanonicalName
    val typeName =
      if (cname == "java.lang.Object") {
        TypeNames.InternalObject
      } else if (cname == "void") {
        TypeNames.Void
      } else if (cname.startsWith(platformPackage + ".SObjects")) {
        val names  = cname.drop(platformPackage.length + 10).split('.').map(n => Name(n)).reverse
        val params = cls.getTypeParameters.map(tp => Name(tp.getName))
        TypeName(ArraySeq.unsafeWrapArray(names :+ Names.Schema))
          .withParams(params.toSeq.map(TypeName(_)))
      } else {
        assert(
          cname.startsWith(platformPackage),
          s"Reference to non-platform type $cname in ${contextCls.getCanonicalName}"
        )
        val names  = cname.drop(platformPackage.length + 1).split('.').map(n => Name(n)).reverse
        val params = cls.getTypeParameters.map(tp => Name(tp.getName))
        TypeName(ArraySeq.unsafeWrapArray(names)).withParams(params.toSeq.map(TypeName(_)))
      }
    if (cls.isArray)
      TypeNames.listOf(typeName)
    else
      typeName
  }

  /* Standard methods to be exposed on enums */
  private def enumMethods(typeName: TypeName): ArraySeq[MethodDeclaration] =
    ArraySeq(
      CustomMethodDeclaration(
        Location.empty,
        Name("name"),
        TypeNames.String,
        CustomMethodDeclaration.emptyParameters
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("ordinal"),
        TypeNames.Integer,
        CustomMethodDeclaration.emptyParameters
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("values"),
        TypeNames.listOf(typeName),
        CustomMethodDeclaration.emptyParameters,
        asStatic = true
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("valueOf"),
        typeName,
        ArraySeq(CustomParameterDeclaration(Name("name"), TypeNames.String)),
        asStatic = true
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("equals"),
        TypeNames.Boolean,
        ArraySeq(CustomParameterDeclaration(Name("other"), TypeNames.InternalObject))
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("hashCode"),
        TypeNames.Integer,
        CustomMethodDeclaration.emptyParameters
      )
    )
}
