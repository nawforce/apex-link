/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.org

import com.nawforce.apexlink.org.TextOps.TestOpsUtils
import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.types.apex.ApexClassDeclaration
import com.nawforce.pkgforce.names.{Name, TypeName}

import scala.collection.compat.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer

/** Matchers to support code completion */
trait ModuleCompletions {
  this: Module =>

  def matchTypeName(content: String, offset: Int): Array[CompletionItemLink] = {
    val prefix = content
      .findLimit(new IdentifierLimiter, forward = false, offset - 1)
      .map(start => content.substring(start, offset))

    // If we lead with our namespace, throw it away
    val searchTerm = prefix.map(prefix => {
      if (
        namespace.nonEmpty && prefix.toLowerCase
          .startsWith(namespace.get.value.toLowerCase + ".")
      ) {
        prefix.substring(namespace.get.value.length + 1)
      } else {
        prefix
      }
    })

    // Collect over the modules (aka same namespace)
    val accum = ArrayBuffer[CompletionItemLink]()
    accum.addAll(matchTypeNameForModule(this, searchTerm))
    baseModules.foreach(module => accum.addAll(matchTypeNameForModule(module, searchTerm)))
    accum.toArray
  }

  def matchTypeNameForModule(
    module: Module,
    searchTerm: Option[String]
  ): Array[CompletionItemLink] = {

    searchTerm
      .map(searchTerm => {

        val parts = searchTerm.split('.')
        val partCount =
          parts.length + (if (searchTerm.length > 1 && searchTerm.endsWith(".")) 1 else 0)

        if (partCount == 1) {
          // Match on first character of only part against any class name
          module.types
            .filter(
              kv =>
                parts.head.isEmpty || kv._1.name.value.take(1).equalsIgnoreCase(parts.head.take(1))
            )
            .collect { case (_, td: ApexClassDeclaration) => td }
            .flatMap(td => CompletionItemLink(td))
            .toArray
        } else if (partCount == 2) {
          // Match on first character of inner type, if we can find the outer
          val typeName = TypeName(Name(parts.head), Seq(), namespace.map(ns => TypeName(ns)))
          module.types
            .get(typeName)
            .collect { case td: ApexClassDeclaration => td }
            .map(td => {
              val innerName = if (parts.length == 2) parts(1) else ""
              td.nestedTypes.filter(
                itd =>
                  innerName.isEmpty || itd.name.value.take(1).equalsIgnoreCase(innerName.take(1))
              )
            })
            .getOrElse(ArraySeq.empty)
            .flatMap(td => CompletionItemLink(td))
            .toArray
        } else {
          Array[CompletionItemLink]()
        }
      })
      .getOrElse {
        // Return all classes in module when no prefix
        module.types
          .collect { case (_, td: ApexClassDeclaration) => td }
          .flatMap(td => CompletionItemLink(td))
          .toArray
      }
  }
}
