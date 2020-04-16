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

import com.nawforce.common.names.Name
import com.nawforce.common.path.PathFactory
import org.scalatest.funsuite.AnyFunSuite

class DocumentTest extends AnyFunSuite {

  test("cls file") {
    DocumentType(PathFactory("/pkg/Foo.cls")) match {
      case Some(ApexClassDocument(PathFactory("/pkg/Foo.cls"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("component file") {
    DocumentType(PathFactory("/pkg/Foo.component")) match {
      case Some(ComponentDocument(PathFactory("/pkg/Foo.component"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("object file") {
    DocumentType(PathFactory("/pkg/Foo.object")) match {
      case Some(SObjectDocument(PathFactory("/pkg/Foo.object"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("object file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo.object-meta.xml")) match {
      case Some(SObjectDocument(PathFactory("/pkg/Foo.object-meta.xml"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("custom object file") {
    DocumentType(PathFactory("/pkg/Foo__c.object")) match {
      case Some(SObjectDocument(PathFactory("/pkg/Foo__c.object"), Name("Foo__c"))) => ()
      case x => assert(false, x)
    }
  }

  test("custom object file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo__c.object-meta.xml")) match {
      case Some(SObjectDocument(PathFactory("/pkg/Foo__c.object-meta.xml"), Name("Foo__c"))) => ()
      case x => assert(false, x)
    }
  }

  test("custom metadata file") {
    DocumentType(PathFactory("/pkg/Foo__mdt.object")) match {
      case Some(CustomMetadataDocument(PathFactory("/pkg/Foo__mdt.object"), Name("Foo__mdt"))) => ()
      case x => assert(false, x)
    }
  }

  test("custom metadata file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo__mdt.object-meta.xml")) match {
      case Some(CustomMetadataDocument(PathFactory("/pkg/Foo__mdt.object-meta.xml"), Name("Foo__mdt"))) => ()
      case x => assert(false, x)
    }
  }

  test("platform event file") {
    DocumentType(PathFactory("/pkg/Foo__e.object")) match {
      case Some(PlatformEventDocument(PathFactory("/pkg/Foo__e.object"), Name("Foo__e"))) => ()
      case x => assert(false, x)
    }
  }

  test("platform event file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo__e.object-meta.xml")) match {
      case Some(PlatformEventDocument(PathFactory("/pkg/Foo__e.object-meta.xml"), Name("Foo__e"))) => ()
      case x => assert(false, x)
    }
  }

  test("field file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo.field-meta.xml")) match {
      case Some(SObjectFieldDocument(PathFactory("/pkg/Foo.field-meta.xml"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("fieldset file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo.fieldset-meta.xml")) match {
      case Some(SObjectFieldSetDocument(PathFactory("/pkg/Foo.fieldset-meta.xml"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("labels file") {
    DocumentType(PathFactory("/pkg/Foo.labels")) match {
      case Some(LabelsDocument(PathFactory("/pkg/Foo.labels"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("labels file (sfdx)") {
    DocumentType(PathFactory("/pkg/Foo.labels-meta.xml")) match {
      case Some(LabelsDocument(PathFactory("/pkg/Foo.labels-meta.xml"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }

  test("page file") {
    DocumentType(PathFactory("/pkg/Foo.page")) match {
      case Some(PageDocument(PathFactory("/pkg/Foo.page"), Name("Foo"))) => ()
      case x => assert(false, x)
    }
  }
}
