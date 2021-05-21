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
package com.nawforce.common.documents

import com.nawforce.common.diagnostics
import com.nawforce.common.diagnostics._
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.PathLike
import com.nawforce.common.sfdx.ForceIgnore

import scala.collection.mutable

/** Metadata document index, maintains information on available metadata within a project/package.
  *
  * Duplicate detection is based on the relevant MetadataDocumentType(s) being able to generate an accurate TypeName
  * for the metadata. Where a document defined a unique type duplicates are reported as errors and then ignored.
  *
  * During an upsert/deletion of new types the index will also need to be updated so that it maintains an accurate
  * view of the metadata files being used.
  */
final class DocumentIndex(val namespace: Option[Name],
                    val path: PathLike,
                    private val ignore: Option[ForceIgnore],
                    private val collection: MetadataCollection) {

  /** Number of documents. */
  val size: Int = collection.size

  /** Get documents of some nature. */
  def get(nature: MetadataNature): Iterator[MetadataDocument] = collection.get(nature)

  /** Get all documents of some nature that contribute to a type  */
  def get(nature: MetadataNature, typeName: TypeName): Set[MetadataDocument] =
    collection.get(nature, typeName)

  /** Get all documents that contribute to a type */
  def get(typeName: TypeName): Set[MetadataDocument] =
    collection.get(typeName)

  /** Add a new document, for non-partial documents this will replace any existing document in the store that provides
    * the same type. Duplicate detection is left to the caller. For partial types, the document will always be added
    * to the store if not already present. */
  def add(logger: IssueLogger, document: MetadataDocument): Unit = {
    if (isVisibleFile(document.path))
      collection.add(logger, document)
  }

  /** Remove a document, does not error if the document is not in the index. */
  def remove(document: MetadataDocument): Unit = {
    collection.remove(document)
  }

  /** Upsert a document. Document defining new or existing types return true, if the document would create a duplicate
    * type it is not added to the store and false is returned.
    */
  def upsert(logger: IssueLogger, document: MetadataDocument): Unit = {
    if (isVisibleFile(document.path))
      collection.upsert(logger, document)
  }

  /** Check a file path would be included in index. */
  def isVisibleFile(path: PathLike): Boolean = {
    ignore.forall(_.includeFile(path)) && isVisiblePath(path.parent)
  }

  /** Check a directory path would be included in index. */
  @scala.annotation.tailrec
  private def isVisiblePath(path: PathLike): Boolean = {
    if (this.path == path) return true
    if (!ignore.forall(_.includeDirectory(path))) return false

    val parent = path.parent
    if (parent != path)
      isVisiblePath(parent)
    else
      false
  }
}

/** A DocumentStore specialised for duplicate detection and other metadata shenanigans. */
final private class MetadataCollection(val namespace: Option[Name]) extends DocumentStore(namespace) {

  override def add(logger: IssueLogger, document: MetadataDocument): Unit = {
    // Reject if document may be ignored
    if (document.ignorable)
      return

    // Duplicate detect for documents that define a complete type
    val typeName = document.typeName(namespace)
    if (!document.nature.partialType) {
      val existing = get(document.nature, typeName)
      if (existing.nonEmpty) {
        logger.log(
          Issue(document.path.toString,
                diagnostics.Diagnostic(
                  ERROR_CATEGORY,
                  Location(0),
                  s"File creates duplicate type '$typeName' as '${existing.head.path}', ignoring")))
        return
      }
    }

    // If we find a field or fieldSet without a SObject metadata, fake it exists to make later processing easier
    if (document.nature == FieldNature || document.nature == FieldSetNature) {
      val objectDir = document.path.parent.parent
      val metaFile = objectDir.join(objectDir.basename + ".object-meta.xml")
      val docType = SObjectDocument(metaFile, Name(objectDir.basename))
      if (get(SObjectNature, docType.typeName(namespace)).isEmpty)
        super.add(logger, docType)
    }

    super.add(logger, document)
  }

  def upsert(logger: IssueLogger, document: MetadataDocument): Boolean = {
    // Partial can always be upserted
    if (document.nature.partialType) {
      super.add(logger, document)
      return true
    }

    // As can metadata defining a new type
    val typeName = document.typeName(namespace)
    val existing = get(document.nature, typeName)
    if (existing.isEmpty) {
      super.add(logger, document)
      return true
    }

    // Or existing documents
    if (existing.contains(document))
      return true

    // Otherwise we should ignore it, sad but true
    logger.log(
      Issue(
        document.path.toString,
        Diagnostic(ERROR_CATEGORY,
                   Location(0),
                   s"Duplicate type '$typeName' found in '${document.path}', ignoring this file")))
    false
  }
}

/** Basic mutable store of documents partitioned by nature & type. To limit memory use, metadata that defines a unique
  * type is stored separately from partial metadata that contributes to a type. This make a bit of a mess of the
  * code but I care more about the reduced memory. */
private class DocumentStore(namespace: Option[Name]) {

  /** Store for 'partial type' metadata, i.e. a set of documents defines a type */
  private val partialTypeDocuments =
    new mutable.HashMap[MetadataNature, mutable.HashMap[TypeName, Set[MetadataDocument]]]()

  /** Store for 'full type' metadata, i.e. each document defines a type */
  private val fullTypeDocuments =
    new mutable.HashMap[MetadataNature, mutable.HashMap[TypeName, MetadataDocument]]()

  def size: Int = {
    partialTypeDocuments.values.map(_.values.size).sum + fullTypeDocuments.values.size
  }

  def get(nature: MetadataNature): Iterator[MetadataDocument] = {
    if (nature.partialType) {
      partialTypeDocuments.get(nature) match {
        case Some(byTypeName) => byTypeName.valuesIterator.flatten
        case None             => Iterator.empty
      }
    } else {
      fullTypeDocuments.get(nature) match {
        case Some(byTypeName) => byTypeName.valuesIterator
        case None             => Iterator.empty
      }
    }
  }

  def get(nature: MetadataNature, typeName: TypeName): Set[MetadataDocument] = {
    if (nature.partialType) {
      partialTypeDocuments.get(nature) match {
        case Some(byTypeName) => byTypeName.getOrElse(typeName, Set.empty)
        case None             => Set.empty
      }
    } else {
      fullTypeDocuments.get(nature) match {
        case Some(byTypeName) => byTypeName.get(typeName).toSet
        case None             => Set.empty
      }
    }
  }

  def get(typeName: TypeName): Set[MetadataDocument] = {
    (partialTypeDocuments.values.flatMap(_.get(typeName)).flatten ++
      fullTypeDocuments.values.flatMap(_.get(typeName))).toSet
  }

  def add(logger: IssueLogger, document: MetadataDocument): Unit = {
    if (document.nature.partialType) {
      val docMap = safePartialDocumentMap(document.nature)
      val typeName = document.typeName(namespace)
      docMap.put(typeName, docMap.getOrElse(typeName, Set()) + document)
    } else {
      val docMap = safeFullDocumentMap(document.nature)
      val typeName = document.typeName(namespace)
      docMap.put(typeName, document)
    }
  }

  /** Remove a document from the store. */
  def remove(document: MetadataDocument): Unit = {
    if (document.nature.partialType) {
      val docMap = safePartialDocumentMap(document.nature)
      val typeName = document.typeName(namespace)
      if (docMap.get(typeName).nonEmpty)
        docMap.put(typeName, docMap(typeName).filterNot(_ == document))
    } else {
      val docMap = safeFullDocumentMap(document.nature)
      val typeName = document.typeName(namespace)
      if (docMap.get(typeName).contains(document))
        docMap.remove(typeName)
    }
  }

  private def safePartialDocumentMap(
    nature: MetadataNature): mutable.HashMap[TypeName, Set[MetadataDocument]] = {
    partialTypeDocuments.getOrElseUpdate(nature, {
      mutable.HashMap[TypeName, Set[MetadataDocument]]()
    })
  }

  private def safeFullDocumentMap(
    nature: MetadataNature): mutable.HashMap[TypeName, MetadataDocument] = {
    fullTypeDocuments.getOrElseUpdate(nature, { mutable.HashMap[TypeName, MetadataDocument]() })
  }

}

object DocumentIndex {
  /** Construct a new DocumentIndex from a recursive descent scan of the passed path. */
  def apply(logger: IssueLogger,
            namespace: Option[Name],
            projectPath: PathLike,
            path: PathLike): DocumentIndex = {
    val ignore = logger.logAndGet(ForceIgnore(projectPath.join(".forceignore")))
    val collection = new MetadataCollection(namespace)
    new DirectoryIndexer(logger, path, ignore, collection)
    new DocumentIndex(namespace, path, ignore, collection)
  }

  /** Simplified construction for MDAPI only projects where projectDir = module path. */
  def apply(logger: IssueLogger, namespace: Option[Name], path: PathLike): DocumentIndex = {
    DocumentIndex(logger, namespace, path, path)
  }
}

/** Directory indexer, somewhat optimised to minimise scan time */
final class DirectoryIndexer(logger: IssueLogger,
                             path: PathLike,
                             forceIgnore: Option[ForceIgnore],
                             collection: MetadataCollection) {

  LoggerOps.debugTime(s"Indexed ${path.toString}") {
    indexPath(path)
  }

  private def indexPath(path: PathLike): Unit = {
    if (isExcluded(path))
      return

    if (path.isDirectory) {
      if (forceIgnore.forall(_.includeDirectory(path))) {
        val entries = path.splitDirectoryEntries()
        // Enforce top-down handling
        entries._1.foreach(addPath)
        entries._2.foreach(indexPath)
      } else {
        LoggerOps.debug(LoggerOps.Trace, s"Ignoring directory $path")
      }
    } else {
      addPath(path)
    }
  }

  private def addPath(path: PathLike): Unit = {
    // Not testing if this is a regular file to improve scan performance, will fail later on read
    if (forceIgnore.forall(_.includeFile(path))) {
      val dt = MetadataDocument(path)
      dt.foreach(dt => collection.add(logger, dt))
    } else {
      LoggerOps.debug(LoggerOps.Trace, s"Ignoring file $path")
    }
  }

  /** Exclude some paths that we would waste time searching. */
  def isExcluded(path: PathLike): Boolean = {
    val basename = path.basename
    if (basename.startsWith(".")) return true
    if (basename == "node_modules") return true
    false
  }
}
