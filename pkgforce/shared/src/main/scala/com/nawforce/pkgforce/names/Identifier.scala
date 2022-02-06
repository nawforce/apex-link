/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

object Identifier {

  /** Check is name is a legal identifier, None if OK or error message string. */
  def isLegalIdentifier(name: Name): Option[String] = {
    val value = name.value
    assert(value.nonEmpty)

    if (value.startsWith("__sfdc_trigger")) {
      if (!value.matches("^((__sfdc_trigger)[/][0-9a-zA-Z_]+)([/][0-9a-zA-Z_]+)?$")) {
        return Some(
          "can only be in the format '__sfdc_trigger/namespace/name' or '__sfdc_trigger/name'"
        )
      }
      None
    } else if (!value.matches("^[0-9a-zA-Z_]*$")) {
      Some("can only use characters A-Z, a-z, 0-9 or _")
    } else if (value(0) >= '0' && value(0) <= '9') {
      Some("can not start with a digit")
    } else if (value.head == '_' || value.last == '_') {
      Some("can not start or end with '_'")
    } else if (value.contains("__")) {
      Some("can not use '__'")
    } else {
      None
    }
  }

  /** Check is name is a reserved identifier, None if OK or error message string. */
  def isReservedIdentifier(name: Name): Boolean = {
    allReservedIdentifiers.contains(name)
  }

  // This is the official reserved keyword list, not all are actually reserved, some are for "future" use, I have
  // removed those known not to be enforced
  lazy val reservedIdentifiers: Set[Name] = Set(
    Name("abstract"),
    Name("activate"),
    Name("and"),
    Name("any"),
    Name("array"),
    Name("as"),
    Name("asc"),
    Name("autonomous"),
    Name("begin"),
    Name("bigdecimal"),
    Name("blob"),
    Name("break"),
    Name("bulk"),
    Name("by"),
    Name("byte"),
    Name("case"),
    Name("cast"),
    Name("catch"),
    Name("char"),
    Name("class"),
    Name("collect"),
    Name("commit"),
    Name("const"),
    Name("continue"),
    Name("decimal"),
    Name("default"),
    Name("delete"),
    Name("desc"),
    Name("do"),
    Name("else"),
    Name("end"),
    Name("enum"),
    Name("exception"),
    Name("exit"),
    Name("export"),
    Name("extends"),
    Name("false"),
    Name("final"),
    Name("finally"),
    Name("float"),
    Name("for"),
    Name("from"),
    Name("global"),
    Name("goto"),
    Name("group"),
    Name("having"),
    Name("hint"),
    Name("if"),
    Name("implements"),
    Name("import"),
    Name("in"),
    Name("inner"),
    Name("insert"),
    Name("instanceof"),
    Name("interface"),
    Name("into"),
    Name("int"),
    Name("join"),
    Name("like"),
    Name("limit"),
    Name("list"),
    Name("long"),
    Name("loop"),
    Name("map"),
    Name("merge"),
    Name("new"),
    Name("not"),
    Name("null"),
    Name("nulls"),
    Name("number"),
    Name("object"),
    Name("of"),
    Name("on"),
    Name("or"),
    Name("outer"),
    Name("override"),
    Name("package"),
    Name("parallel"),
    Name("pragma"),
    Name("private"),
    Name("protected"),
    Name("public"),
    Name("retrieve"),
    Name("return"),
    Name("rollback"),
    Name("select"),
    Name("set"),
    Name("short"),
    Name("sort"),
    Name("static"),
    Name("super"),
    Name("switch"),
    Name("synchronized"),
    Name("system"),
    Name("testmethod"),
    Name("then"),
    Name("this"),
    Name("throw"),
    Name("transaction"),
    Name("trigger"),
    Name("true"),
    Name("try"),
    Name("undelete"),
    Name("update"),
    Name("upsert"),
    Name("using"),
    Name("virtual"),
    Name("webservice"),
    Name("when"),
    Name("where"),
    Name("while")
  )

  // These are identifiers that don't work but are not reserved, yeah go figure
  lazy val badIdentifiers: Set[Name] = Set(
    Name("boolean"),
    Name("currency"),
    Name("date"),
    Name("datetime"),
    Name("double"),
    Name("integer"),
    Name("string"),
    Name("time")
  )

  lazy val allReservedIdentifiers: Set[Name] = reservedIdentifiers ++ badIdentifiers
}
