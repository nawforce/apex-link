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
package com.nawforce.apexlink.types

import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.DependentType
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.stream.PackageStream

package object other {

  trait OtherTypeDeclaration extends DependentType {
    def merge(stream: PackageStream): OtherTypeDeclaration
  }

  object Other {
    def apply(dt: UpdatableMetadata, module: Module): OtherTypeDeclaration = {
      dt match {
        case _: LabelsDocument    => LabelDeclaration(module)
        case _: PageDocument      => PageDeclaration(module)
        case _: ComponentDocument => ComponentDeclaration(module)
        case _: FlowDocument      => InterviewDeclaration(module)
      }
    }
  }

}
