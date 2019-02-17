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
package io.github.nawforce.tools

import java.io.{BufferedWriter, StringWriter, Writer}

import com.sforce.soap.partner.{DescribeSObjectResult, Field}

case class DescribedSObject(describe: DescribeSObjectResult) {

  val name: String = describe.getName

  lazy val fieldsAndTypes: Array[(String, String)] =
    describe.getFields.map(df => (df.getName, DescribedSObject.getPlatformType(df)))
      .filterNot(fp => DescribedSObject.IgnorableFieldsAndRelationships.contains(fp._1))

  lazy val relationshipsAndTypes: Array[(String, String)] =
    describe.getChildRelationships.map(cr => (cr.getRelationshipName, cr.getChildSObject))
      .filterNot(cr => cr._1 == null)
      .filterNot(cr => DescribedSObject.IgnorableFieldsAndRelationships.contains(cr._1))

  lazy val asJava: String = {
    val stringWriter = new StringWriter()
    val writer = new BufferedWriter(stringWriter)

    CopyrightWriter.write(writer)
    writer.write(
      s"""package io.github.nawforce.platform.SObjects;
         |
         |import io.github.nawforce.platform.System.*;
         |import io.github.nawforce.platform.System.String;
         |import io.github.nawforce.platform.System.Boolean;
         |import io.github.nawforce.platform.System.Integer;
         |import io.github.nawforce.platform.Schema;
         |import io.github.nawforce.platform.DatabaseImpl;
         |import io.github.nawforce.platform.Namespaces.Dom;
         |
         |@SuppressWarnings("unused")
         |""".stripMargin)

    writer.write(s"public class $name extends SObject {\n")

    fieldsAndTypes.sortBy(_._1).foreach(fd => writeField(fd._1, fd._2, writer))
    if (relationshipsAndTypes.nonEmpty)
      writer.write(s"\n")
    relationshipsAndTypes.sortBy(_._1).foreach(rd =>
      writer.write(s"\tpublic ${rd._2} ${rd._1};\n")
    )
    writer.write(s"}\n")

    writer.close()
    stringWriter.toString
  }

  private def writeField(name: String, fieldType: String, writer: Writer): Unit = {
    if (name != "Id" && name.endsWith("Id") && fieldType.startsWith("SObject.")) {
      writer.write(s"\tpublic Id $name;\n")
      writer.write(s"\tpublic ${fieldType.drop("SObject.".length)} ${name.dropRight(2)};\n")
    }
    else
      writer.write(s"\tpublic $fieldType $name;\n")
  }
}

object DescribedSObject {
  private val IgnorableFieldsAndRelationships = Set(
    "CreatedBy",
    "CreatedById",
    "CreatedDate",
    "Id",
    "IsDeleted",
    "LastModifiedBy",
    "LastModifiedById",
    "LastModifiedDate",
    "RecordTypeId",
    "RecordType",
    "SystemModstamp"
  )

  private def getPlatformType(field: Field): String = {
    field.getType.toString match {
      case "reference" => "SObject."+field.getReferenceTo.headOption.getOrElse("SObject")
      case "address" => "Address"
      case "anyType" => "Object"
      case "base64" => "Blob"
      case "boolean" => "Boolean"
      case "combobox" => "String"
      case "complexvalue" => "Object"
      case "currency" => "Decimal"
      case "date" => "Date"
      case "datetime" => "Datetime"
      case "double" => "Decimal"
      case "email" => "String"
      case "encryptedstring" => "Blob"
      case "id" => "Id"
      case "int" => "Integer"
      case "location" => "Location"
      case "long" => "Long"
      case "multipicklist" => "String"
      case "percent" => "Decimal"
      case "phone" => "String"
      case "picklist" => "String"
      case "string" => "String"
      case "textarea" => "String"
      case "time" => "Time"
      case "url" => "String"
    }
  }

  def apply(describe: DescribeSObjectResult): DescribedSObject = new DescribedSObject(describe)
}
