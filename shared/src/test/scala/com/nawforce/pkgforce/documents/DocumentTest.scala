/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class DocumentTest extends AnyFunSuite {

  private val root: PathLike = Path("pkg")

  test("cls file") {
    MetadataDocument(root.join("Foo.cls")) match {
      case Some(ApexClassDocument(path, Name("Foo"))) if path == root.join("Foo.cls") => ()
      case x                                                                          => assert(false, x)
    }
  }

  test("component file") {
    MetadataDocument(root.join("Foo.component")) match {
      case Some(ComponentDocument(path, Name("Foo"))) if path == root.join("Foo.component") => ()
      case x                                                                                => assert(false, x)
    }
  }

  test("object file") {
    MetadataDocument(root.join("Foo.object")) match {
      case Some(SObjectDocument(path, Name("Foo"))) if path == root.join("Foo.object") => ()
      case x                                                                           => assert(false, x)
    }
  }

  test("object file (sfdx)") {
    MetadataDocument(root.join("Foo.object-meta.xml")) match {
      case Some(SObjectDocument(path, Name("Foo"))) if path == root.join("Foo.object-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("custom object file") {
    MetadataDocument(root.join("Foo__c.object")) match {
      case Some(SObjectDocument(path, Name("Foo__c"))) if path == root.join("Foo__c.object") => ()
      case x                                                                                 => assert(false, x)
    }
  }

  test("custom object file (sfdx)") {
    MetadataDocument(root.join("Foo__c.object-meta.xml")) match {
      case Some(SObjectDocument(path, Name("Foo__c")))
          if path == root.join("Foo__c.object-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("custom metadata file") {
    MetadataDocument(root.join("Foo__mdt.object")) match {
      case Some(CustomMetadataDocument(path, Name("Foo__mdt")))
          if path == root.join("Foo__mdt.object") =>
        ()
      case x => assert(false, x)
    }
  }

  test("custom metadata file (sfdx)") {
    MetadataDocument(root.join("Foo__mdt.object-meta.xml")) match {
      case Some(CustomMetadataDocument(path, Name("Foo__mdt")))
          if path == root.join("Foo__mdt.object-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("big object file") {
    MetadataDocument(root.join("Foo__b.object")) match {
      case Some(BigObjectDocument(path, Name("Foo__b"))) if path == root.join("Foo__b.object") =>
        ()
      case x => assert(false, x)
    }
  }

  test("big object file (sfdx)") {
    MetadataDocument(root.join("Foo__b.object-meta.xml")) match {
      case Some(BigObjectDocument(path, Name("Foo__b")))
          if path == root.join("Foo__b.object-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("platform event file") {
    MetadataDocument(root.join("Foo__e.object")) match {
      case Some(PlatformEventDocument(path, Name("Foo__e")))
          if path == root.join("Foo__e.object") =>
        ()
      case x => assert(false, x)
    }
  }

  test("platform event file (sfdx)") {
    MetadataDocument(root.join("Foo__e.object-meta.xml")) match {
      case Some(PlatformEventDocument(path, Name("Foo__e")))
          if path == root.join("Foo__e.object-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("field file (sfdx)") {
    MetadataDocument(root.join("object/fields/Foo.field-meta.xml")) match {
      case Some(SObjectFieldDocument(path, Name("Foo")))
          if path == root.join("object/fields/Foo.field-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("fieldset file (sfdx)") {
    MetadataDocument(root.join("object/fieldSets/Foo.fieldSet-meta.xml")) match {
      case Some(SObjectFieldSetDocument(path, Name("Foo")))
          if path == root.join("object/fieldSets/Foo.fieldSet-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

  test("labels file") {
    MetadataDocument(root.join("Foo.labels")) match {
      case Some(LabelsDocument(path, Name("Foo"))) if path == root.join("Foo.labels") => ()
      case x                                                                          => assert(false, x)
    }
  }

  test("labels file (sfdx)") {
    MetadataDocument(root.join("Foo.labels-meta.xml")) match {
      case Some(LabelsDocument(path, Name("Foo"))) if path == root.join("Foo.labels-meta.xml") => ()
      case x                                                                                   => assert(false, x)
    }
  }

  test("page file") {
    MetadataDocument(root.join("Foo.page")) match {
      case Some(PageDocument(path, Name("Foo"))) if path == root.join("Foo.page") => ()
      case x                                                                      => assert(false, x)
    }
  }

  test("extra dots") {
    MetadataDocument(root.join("gantt_v13.1.labels-meta.xml")) match {
      case Some(LabelsDocument(path, Name("gantt_v13.1")))
          if path == root.join("gantt_v13.1.labels-meta.xml") =>
        ()
      case x => assert(false, x)
    }
  }

}
