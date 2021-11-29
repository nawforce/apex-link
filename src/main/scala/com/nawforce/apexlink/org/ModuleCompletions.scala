package com.nawforce.apexlink.org

import com.nawforce.apexlink.org.TextOps.TestOpsUtils
import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.types.apex.ApexClassDeclaration
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.names.EncodedName
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE}

import scala.collection.compat.immutable.ArraySeq

/** Matchers to support code completion */
trait ModuleCompletions {
  this: Module =>

  def matchTypeName(content: String, offset: Int): Array[CompletionItemLink] = {
    val prefix = content.findLimit(new IdentifierLimiter, forward = false, offset - 1)
      .map(start => content.substring(start, offset))

    prefix.map(prefix => {
      val parts = prefix.split('.')
      if (parts.head == prefix) {
        // Match on first character, being careful about namespace handling
        val typeName = EncodedName(parts.head).defaultNamespace(namespace).asTypeName
        types
          .filter(kv => kv._1.name.value.take(1).equalsIgnoreCase(prefix) && kv._1.outer == typeName.outer)
          .collect { case (_, td: ApexClassDeclaration) => td }
          .flatMap(toCompletionItem)
          .toArray
      } else if (parts.length == 1 || (parts.length == 2 && prefix.last != '.')) {
        // Match on first character of inner type, if we can find the outer
        val typeName = EncodedName(parts.head).defaultNamespace(namespace).asTypeName
        types.get(typeName)
          .collect { case td: ApexClassDeclaration => td }
          .map(td => {
            td.nestedTypes.filter(itd => parts.length == 1 || parts(1).isEmpty ||
              itd.typeName.name.value.take(1).equalsIgnoreCase(parts(1).take(1)))
          }).getOrElse(ArraySeq.empty)
          .flatMap(toCompletionItem)
          .toArray
      } else {
        Array[CompletionItemLink]()
      }
    }).getOrElse {
      // Return all classes in module when no prefix
      types.collect { case (_, td: ApexClassDeclaration) => td }
        .flatMap(toCompletionItem).toArray
    }
  }

  def toCompletionItem(td: TypeDeclaration): Option[CompletionItemLink] = {
    td.nature match {
      case CLASS_NATURE => Some(CompletionItemLink(td.typeName.name.value, "Class"))
      case INTERFACE_NATURE => Some(CompletionItemLink(td.typeName.name.value, "Interface"))
      case ENUM_NATURE => Some(CompletionItemLink(td.typeName.name.value, "Enum"))
      case _ => None
    }
  }
}
