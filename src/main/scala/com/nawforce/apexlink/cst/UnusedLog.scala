/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.cst

import com.nawforce.apexlink.types.apex.ApexClassDeclaration
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.IssueLog

class UnusedLog(types: Iterable[TypeDeclaration]) extends IssueLog {

  collectUnused()

  private def collectUnused(): Unit = {
    types.foreach {
      //case labels: LabelDeclaration => labels.unused().foreach(add)
      case apexType: ApexClassDeclaration => apexType.unused().foreach(add)
      case _                              => ()
    }
  }
}
