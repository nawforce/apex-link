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
package com.nawforce.pkgforce.names

/** A name with optional namespace prefixing & optional type suffix such as foo__field__c. Only a small set of
  * suffixes are supported. It's not clear what the full list used by Salesforce is. This class is safe to use on
  * non-encoded names which don't contain \_\_, although you can not default the namespace on them.
  *
  * Where all of namespace, name and suffix are provided the code will assert on a bad suffix. Where we only have two
  * parts of the name then if the last parts is not a valid suffix the handling will assume the first part is a
  * namespace and the second the name, as in Page.pkg1\_\_TestPage.
  *
  * The code deals with a few exception cases where splitting on \_\_ would gives either a wrong name or ext part.
  * For subfields the subfield name is combined with the extension. For supporting SObjects such as MyObject\_\_Feed
  * the 'Feed' is considered an extension in the same way that 'c' would be.
  */
final case class EncodedName(name: Name, ext: Option[Name], namespace: Option[Name]) {

  /** The name & suffix excluding any namespace. */
  lazy val localName: Name = Name(name.value + ext.map("__" + _.value).getOrElse(""))

  /** The name & suffix including any namespace. */
  lazy val fullName: Name = Name(namespace.map(_.value + "__").getOrElse("") + localName.value)

  /** An encoded name using the provided namespace if one was not provided. */
  def defaultNamespace(ns: Option[Name]): EncodedName = {
    if (ext.nonEmpty && namespace.isEmpty)
      EncodedName(name, ext, ns)
    else
      this
  }

  def asTypeName: TypeName = {
    TypeName(localName, Nil, namespace.map(TypeName(_)))
  }
}

object EncodedName {
  private final val extensions = Set("c", "r", "e", "b", "mdt", "share", "history", "feed")

  private final val underscoreSplit = "__".r

  def apply(name: Name): EncodedName = {
    apply(name.value)
  }

  def apply(name: String): EncodedName = {
    val parts = nameSplit(name)
    parts.size match {
      case 3 =>
        val normalExt = parts(2).toLowerCase
        if (extensions.contains(normalExt) || normalExt.endsWith("__s")) {
          EncodedName(Name(parts(1)), Some(Name(parts(2))), Some(Name(parts.head)))
        } else {
          // If extension is not recognised don't split
          EncodedName(Name(name), None, None)
        }
      case 2 =>
        val normalExt = parts(1).toLowerCase
        if (extensions.contains(normalExt) || normalExt.endsWith("__s"))
          EncodedName(Name(parts.head), Some(Name(parts(1))), None)
        else
          EncodedName(Name(parts(1)), None, Some(Name(parts.head)))
      case 1 => EncodedName(Name(parts.head), None, None)
    }
  }

  private def nameSplit(name: String): Seq[String] = {
    var parts = underscoreSplit.split(name)

    // Collapse subfield name into the ext
    if (parts.length > 1 && parts.last.equalsIgnoreCase("s"))
      parts = parts.take(parts.length - 2) :+ parts.takeRight(2).mkString("__")

    if (parts.exists(_.isEmpty))
      Seq(name)
    else if (parts.length > 2)
      Seq(parts.head, parts.tail.take(parts.length - 2).mkString("__"), parts.last)
    else if (parts.length == 2)
      Seq(parts.head, parts.last)
    else
      Seq(name)
  }
}
