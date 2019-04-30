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
package com.nawforce.api

import java.io.FileInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.documents.{ApexDocument, DocumentLoader, DocumentType}
import com.nawforce.types.ApexTypeDeclaration
import com.nawforce.utils.{IssueLog, Name}
import com.typesafe.scalalogging.LazyLogging

class Package(org: Org, paths: Seq[Path]) extends LazyLogging {
  private val documents = new DocumentLoader(paths)

  lazy val classCount: Int = documents.getByExtension(Name("cls")).size

  def deployAll(): String = {
    val classes = documents.getByExtension(Name("cls"))
    logger.debug(s"Found ${classes.size} classes to parse")

    val newDeclarations = classes.par.flatMap(path => {
      DocumentType(path) match {
        case docType: ApexDocument =>
          val start = System.currentTimeMillis()
          val typeDeclaration = ApexTypeDeclaration.create(docType.path, new FileInputStream(docType.path.toFile))
          val end = System.currentTimeMillis()
          logger.debug(s"Parsed ${docType.path.toString} in ${end-start}ms")
          typeDeclaration
      }
    })
    org.replaceTypes(newDeclarations.seq)
    IssueLog.asJSON(100)
  }
}

object Package {
  def apply(org: Org, directories: Seq[String]): Package = {
    val paths = directories.map(directory => Paths.get(directory))
    paths.foreach(path => {
      if (!path.toFile.isDirectory)
        throw new IllegalArgumentException(s"Package root '${path.toString}' must be a directory")
    })
    new Package(org, paths)
  }
}
