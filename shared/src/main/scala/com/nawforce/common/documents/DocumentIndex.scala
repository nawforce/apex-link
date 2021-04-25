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

/** Metadata workspace, maintains information on available metadata within a project/package.
  *
  * Duplicate detection is based on the relevant MetadataDocumentType(s) being able to generate an accurate TypeName
  * for the metadata. Where multiple metadata items may contribute to a type, e.g. labels, make sure that
  * duplicatesAllowed is set which will bypass the duplicate detection. Duplicates are reported as errors and then
  * ignored.
  *
  * During an upsert/deletion of new types the index will also need to be updated so that it maintains an accurate
  * view of the metadata files being used.
  */
class DocumentIndex(namespace: Option[Name], path: PathLike, collection: DocumentCollection) {

  /** Issues detected in workspace, typically duplicate types */
  private val logger = new CatchingLogger()

  /** Issues found in workspace */
  val issues: List[Issue] = logger.issues

  /** Number of types found*/
  val typeCount: Int = collection.documents.values.map(_.size).sum

  /** Get index'd metadata by declared extension */
  def getByExtension(ext: Name): Iterable[MetadataDocument] = {
    if (!collection.documents.contains(ext)) return Set.empty
    collection.documents(ext).values.flatten
  }

  /* Find a class or trigger by its typename */
  def getByType(typeName: TypeName): Option[MetadataDocument] = {
    collection.documents
      .get(MetadataDocument.clsExt)
      .flatMap(_.get(typeName))
      .orElse(collection.documents.get(MetadataDocument.triggerExt).flatMap(_.get(typeName)))
      .map(_.head)
  }

  /** Upsert a metadata document with duplicate detection */
  /* TODO
  def upsert(metadata: MetadataDocument): Boolean = {
    // Duplicates always good
    if (metadata.duplicatesAllowed) {
      addDocument(metadata)
      return true
    }

    // Label replacement OK
    val typeName = metadata.typeName(namespace)
    if (typeName == TypeNames.Label)
      return true

    // New is OK
    if (!typeNames.contains(typeName)) {
      addDocument(metadata)
      return true
    }

    // Existing with same path OK, but beware some files may have been deleted without notification
    val knownDocs = documents
      .get(metadata.extension)
      .flatMap(_.get(typeName))
      .getOrElse(Nil)
    val docs = knownDocs.filter(_.path.exists)
    if (docs.size != knownDocs.size)
      documents(metadata.extension).put(typeName, docs)

    if (docs.isEmpty || docs.contains(metadata)) {
      return true
    }

    docs.foreach(doc => {
      logger.log(
        Issue(doc.path.toString,
              Diagnostic(
                ERROR_CATEGORY,
                Location(0),
                s"Duplicate type '$typeName' found in '${metadata.path}', ignoring this file")))
    })
    false
  }

  /** Remove a metadata document from the index */
  def remove(metadataDocumentType: MetadataDocument): Unit = {
    documents
      .get(metadataDocumentType.extension)
      .foreach(docs => {
        val typeName = metadataDocumentType.typeName(namespace)
        if (!metadataDocumentType.duplicatesAllowed) {
          docs.remove(typeName)
          typeNames.remove(typeName)
        } else {
          val filtered =
            docs.getOrElse(typeName, Nil).filterNot(_.path == metadataDocumentType.path)
          docs.put(typeName, filtered)
          typeNames.remove(typeName)
        }
      })
  }
 */
}

private class DocumentCollection(val namespace: Option[Name]) {

  /** All documents partitioned by declared extension */
  val documents =
    new mutable.HashMap[Name, mutable.HashMap[TypeName, List[MetadataDocument]]]()

  /** The typeNames that may be exclusively generated by the documents, for duplicate detection */
  val typeNames = new mutable.HashSet[TypeName]()

  def getByExtension(ext: Name): Iterable[MetadataDocument] = {
    if (!documents.contains(ext)) return Set.empty
    documents(ext).values.flatten
  }

  def insertDocument(logger: IssueLogger, documentType: MetadataDocument): Unit = {
    if (documentType.ignorable)
      return

    if (documentType.duplicatesAllowed) {
      addDocument(documentType)
    } else {
      // Duplicate detect based on type that will be generated
      val typeName = documentType.typeName(namespace)
      if (typeNames.contains(typeName)) {
        val duplicate = documents(documentType.extension).get(typeName)
        logger.log(
          Issue(documentType.path.toString,
                diagnostics.Diagnostic(
                  ERROR_CATEGORY,
                  Location(0),
                  s"File creates duplicate type '$typeName' as '${duplicate.get.head.path}', ignoring")))
      } else {
        typeNames.add(typeName)
        addDocument(documentType)
      }
    }
  }

  private def addDocument(docType: MetadataDocument): Unit = {
    val extMap = documents.getOrElseUpdate(docType.extension, {
      mutable.HashMap[TypeName, List[MetadataDocument]]()
    })
    val typeName = docType.typeName(namespace)
    extMap.put(typeName, docType :: extMap.getOrElse(typeName, Nil))
  }

  /** Hack to deal with missing .object-meta.xml files in SFDX */
  // TODO: This should be handled by automatically
  def createGhostSObjectFiles(name: Name, forceIgnore: Option[ForceIgnore]): Unit = {
    getByExtension(name).foreach(docType => {
      val objectDir = docType.path.parent.parent
      val metaFile = objectDir.join(objectDir.basename + ".object-meta.xml")
      if (!metaFile.isFile) {
        if (forceIgnore.forall(_.includeDirectory(metaFile.parent))) {
          val objectExt = MetadataDocument.objectExt
          val docType = SObjectDocument(metaFile, Name(objectDir.basename))
          if (!documents.contains(objectExt) || !documents(objectExt).contains(
            docType.typeName(namespace))) {
            addDocument(docType)
          }
        }
      }
    })
  }
}

object DocumentIndex {

  def apply(logger: IssueLogger, namespace: Option[Name], path: PathLike): DocumentIndex = {
    val ignore = logger.logAndGet(ForceIgnore(path))
    val collection = new DocumentCollection(namespace)
    index(logger, path, ignore, collection)
    new DocumentIndex(namespace, path, collection)
  }

  private def index(logger: IssueLogger,
                    path: PathLike,
                    ignore: Option[ForceIgnore],
                    collection: DocumentCollection): Unit = {
    if (path.isDirectory) {
      LoggerOps.debugTime(s"Indexed ${path.toString}") {
        indexPath(logger, path, ignore, collection)
      }
    }
    // TODO: Remove this hack
    collection.createGhostSObjectFiles(Name("field"), ignore)
    collection.createGhostSObjectFiles(Name("fieldSet"), ignore)
  }


  private def indexPath(logger: IssueLogger,
                        path: PathLike,
                        forceIgnore: Option[ForceIgnore],
                        collection: DocumentCollection): Unit = {
    if (DocumentIndex.isExcluded(path))
      return

    if (path.isDirectory) {
      if (forceIgnore.forall(_.includeDirectory(path))) {
        path.directoryList() match {
          case Left(err) => LoggerOps.error(err)
          case Right(parts) =>
            parts.foreach(part => indexPath(logger, path.join(part), forceIgnore, collection))
        }
      } else {
        LoggerOps.debug(LoggerOps.Trace, s"Ignoring directory $path")
      }
    } else {
      // Not testing if this is a regular file to improve scan performance, will fail later on read
      if (forceIgnore.forall(_.includeFile(path))) {
        val dt = MetadataDocument(path)
        dt.foreach(dt => collection.insertDocument(logger, dt))
      } else {
        LoggerOps.debug(LoggerOps.Trace, s"Ignoring file $path")
      }
    }
  }

  /** Exclude some paths that we would waste time searching */
  def isExcluded(path: PathLike): Boolean = {
    val basename = path.basename
    if (basename.startsWith(".")) return true
    if (basename == "node_modules") return true
    false
  }
}
