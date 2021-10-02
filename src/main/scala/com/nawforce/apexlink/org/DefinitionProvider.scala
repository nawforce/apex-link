/*
 Copyright (c) 2021 Kevin Jones & FinancialForce, All rights reserved.
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
import com.nawforce.apexlink.rpc.LocationLink
import com.nawforce.apexlink.types.apex.{FullDeclaration, IdLocatable}
import com.nawforce.pkgforce.documents.{ApexClassDocument, MetadataDocument}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.{Locatable, UnsafeLocatable}

trait DefinitionProvider {
  this: PackageImpl =>

  def getDefinition(path: PathLike, line: Int, offset: Int, content: Option[String]): Array[LocationLink] = {
    // Make sure we have access to source code and a type to resolve things against
    val sourceAndType = loadSourceAndType(path, content)
    if (sourceAndType.isEmpty)
      return Array.empty

    locateFromValidation(sourceAndType.get._2, line, offset).orElse({

      val source = sourceAndType.get._1
      val sourceTD = sourceAndType.get._2
      val searchTermAndLocation = source.extractDotTerm(DefinitionProvider.allowedCharacters, line, offset, inclusive = true)
      if (searchTermAndLocation.isEmpty)
        return Array.empty
      val searchTerm = searchTermAndLocation.get._1
      val sourceLocation = searchTermAndLocation.get._2

      sourceTD.findDeclarationFromSourceReference(searchTerm, sourceLocation)
        .map(ad => {
          LocationLink(sourceLocation, ad.location.path, ad.location.location, ad.idLocation)
        })
    }).toArray
  }

  private def loadSourceAndType(path: PathLike, content: Option[String]): Option[(String, FullDeclaration)] = {
    // We need source code no matter what
    val sourceOpt = content.orElse(path.read().toOption)
    if (sourceOpt.isEmpty)
      return None

    // If we don't have new source we can assume the loaded type is current, but it could be a summary
    if (content.isEmpty) {
      MetadataDocument(path)
        .collect { case doc: ApexClassDocument => doc }
        .flatMap(doc => orderedModules.view.flatMap(_.moduleType(doc.typeName(namespace))).headOption)
        .collect { case td: FullDeclaration => td }
        .orElse({
          // If not try and load a temp version to work with, this is expensive
          loadClass(path, sourceOpt.get)
        })
        .map(td => (sourceOpt.get, td))
    } else {
      // No option but to load it as content is being provided
      loadClass(path, sourceOpt.get).map(td => (sourceOpt.get, td))
    }
  }

  /** Extract a location link from an expression at the passed location */
  private def locateFromValidation(td: FullDeclaration, line: Int, offset: Int): Option[LocationLink] = {
    val resultMap = td.getBodyDeclarationValidationMap(line, offset)

    // Find the inner-most expression containing location from those that do
    val exprLocations = resultMap.keys.filter(_.contains(line, offset))
    val innerExprLocation = resultMap.keys.filter(_.contains(line, offset))
      .find(exprLocation => exprLocations.forall(_.contains(exprLocation)))

    innerExprLocation
      .flatMap(loc => {
        // If the result has a locatable we can use that as the target, beware the order here matters due
        // to both inheritance and some objects supporting multiple Locatable traits
        resultMap(loc)._2.locatable match {
          case Some(l: IdLocatable) =>
            Some(LocationLink(loc, l.location.path, l.location.location, l.idLocation))
          case Some(l: UnsafeLocatable) =>
            Option(l.location).map(l => LocationLink(loc, l.path, l.location, l.location))
          case Some(l: Locatable) =>
            Some(LocationLink(loc, l.location.path, l.location.location, l.location.location))
          case _ =>
            None
        }
      })
  }
}

object DefinitionProvider {
  val allowedCharacters: Set[Char] = (('0' to '9') ++ ('a' to 'z') ++ ('A' to 'Z') ++ Seq('_', '.')).toSet
}
