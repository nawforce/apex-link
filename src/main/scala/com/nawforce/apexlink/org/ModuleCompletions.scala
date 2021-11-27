package com.nawforce.apexlink.org

import com.nawforce.apexlink.types.apex.ApexClassDeclaration

/** Matchers to support code completion */
trait ModuleCompletions {
  this: Module =>

  def matchTypeName(pattern: String): Array[String] = {
    types
      .filter(_._1.name.value.head == pattern.head)
      .collect{ case (name, _:ApexClassDeclaration) => name}
      .map(_.name.toString())
      .toArray
  }

}
