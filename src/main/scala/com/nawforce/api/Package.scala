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

import java.nio.file.{Path, Paths}

import com.nawforce.types.PackageDeclaration
import com.nawforce.utils.Name
import com.typesafe.scalalogging.LazyLogging

class Package(val org: Org, _namespace: Name, _paths: Seq[Path])
  extends PackageDeclaration(_namespace, _paths) with LazyLogging {

  lazy val classCount: Int = documents.getByExtension(Name("cls")).size

  def deployAll(): Unit = {
    val components = documents.getByExtension(Name("component"))
    logger.debug(s"Found ${components.size} components to parse")
    org.deployMetadata(this, components)

    val objects = documents.getByExtension(Name("object"))
    logger.debug(s"Found ${objects.size} custom objects to parse")
    org.deployMetadata(this, objects)

    val classes = documents.getByExtension(Name("cls"))
    logger.debug(s"Found ${classes.size} classes to parse")
    org.deployMetadata(this, classes)
  }
}

object Package {
  def apply(org: Org, namespace: Name, directories: Seq[String]): Package = {
    val paths = directories.filterNot(_.isEmpty).map(directory => Paths.get(directory))
    paths.foreach(path => {
      if (!path.toFile.isDirectory)
        throw new IllegalArgumentException(s"Package root '${path.toString}' must be a directory")
    })
    new Package(org, namespace, paths)
  }
}
