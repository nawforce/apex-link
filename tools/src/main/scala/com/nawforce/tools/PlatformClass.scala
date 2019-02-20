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
package com.nawforce.tools

import java.io.{BufferedWriter, StringWriter, Writer}

import net.liftweb.json._

case class PlatformClass(namespace: String, json: JField) {
  val name: String = json.name
  val isGeneric: Boolean = PlatformClass.genericClasses.contains(name.toLowerCase())

  lazy val properties: List[String] = (json.value \ "properties").asInstanceOf[JArray].arr.map(p => {
    (p \ "name").asInstanceOf[JString].s
  })

  lazy val constructors: List[JsonAST.JValue] = (json.value \ "constructors").asInstanceOf[JArray].arr

  lazy val instanceMethods: List[JsonAST.JValue] = methods.filterNot(m => (m \ "isStatic").asInstanceOf[JBool].value)
  lazy val staticMethods: List[JsonAST.JValue] = methods.filter(m => (m \ "isStatic").asInstanceOf[JBool].value)
  lazy val methods: List[JsonAST.JValue] = (json.value \ "methods").asInstanceOf[JArray].arr
    .filterNot(m => (m \ "name").asInstanceOf[JString].s.equalsIgnoreCase("clone"))

  lazy val asJava: String = {
    val stringWriter = new StringWriter()
    val writer = new BufferedWriter(stringWriter)

    CopyrightWriter.write(writer)
    writer.write(
      s"""package com.nawforce.platform.$namespace;
         |
         |import com.nawforce.platform.System.*;
         |import com.nawforce.platform.System.String;
         |import com.nawforce.platform.System.Boolean;
         |import com.nawforce.platform.System.Integer;
         |import com.nawforce.platform.Schema;
         |import com.nawforce.platform.DatabaseImpl;
         |import com.nawforce.platform.Namespaces.Dom;
         |
         |@SuppressWarnings("unused")
         |""".stripMargin)
    writer.write(s"public class $name {\n")

    properties.foreach(p => {
      val getter = methods.filter(m => (m \ "name").asInstanceOf[JString].s.equalsIgnoreCase("get"+p))
      if (getter.length == 1) {
        val returnType = getPlatformType((getter.head \ "returnType").asInstanceOf[JString].s)
        writer.write(s"\tpublic $returnType ${(getter.head \ "name").asInstanceOf[JString].s.drop(3)};\n")
      } else {
        val getterIs = methods.filter(m => (m \ "name").asInstanceOf[JString].s.equalsIgnoreCase("is"+p))
        if (getterIs.length == 1) {
          val returnType = getPlatformType((getterIs.head \ "returnType").asInstanceOf[JString].s)
          writer.write(s"\tpublic $returnType ${(getterIs.head \ "name").asInstanceOf[JString].s.drop(2)};\n")
        } else {
          writer.write(s"\tpublic $p;\n")
        }
      }
    })
    writer.write(s"\n")

    constructors.foreach(c => {
      writer.write(s"\tpublic $name(")
      writeMethodArguments(writer, c)
      writer.write(s") {throw new java.lang.UnsupportedOperationException();}\n")
    })
    writer.write(s"\n")

    instanceMethods.foreach(m => {
      val methodName = getMethodName((m \ "name").asInstanceOf[JString].s)
      val returnType = getPlatformType((m \ "returnType").asInstanceOf[JString].s)
      writer.write(s"\tpublic $returnType $methodName(")
      writeMethodArguments(writer, m)
      writer.write(s") {throw new java.lang.UnsupportedOperationException();}\n")
    })
    writer.write(s"\n")

    staticMethods.foreach(m => {
      val methodName = getMethodName((m \ "name").asInstanceOf[JString].s)
      val returnType = getPlatformType((m \ "returnType").asInstanceOf[JString].s)
      writer.write(s"\tpublic static $returnType $methodName(")
      writeMethodArguments(writer, m)
      writer.write(s") {throw new java.lang.UnsupportedOperationException();}\n")
    })
    writer.write(s"\n")

    writer.write(s"}\n")
    writer.close()
    stringWriter.toString
  }

  private def writeMethodArguments(writer: Writer, method: JValue): Unit = {
    writer.write(
      (method \ "parameters").asInstanceOf[JArray].arr.map(arg => {
        s"${getPlatformType((arg \ "type").asInstanceOf[JString].s)} " +
        s"${(arg \ "name").asInstanceOf[JString].s}"
      }).mkString(", ")
    )
  }

  private def getMethodName(name:String) : String = {
    name match {
      case "assert" => "Assert"
      case "equals" => "equals$"
      case "hashCode" => "hashCode$"
      case "toString" => "toString$"
      case _ => name
    }
 }

  private def getPlatformType(argType: String): String = {
    argType match {
      case "ANY" => "Object"
      case "APEX_OBJECT" => "Object"
      case t => t
        .replace("dom.", "Dom.")
        .replace(namespace + ".", "")
        .replace(name + ".", "")
    }
  }
}

object PlatformClass {
  private val genericClasses = Set(
    "iterable",
    "iterator",
    "list",
    "map",
    "set"
  )
}

