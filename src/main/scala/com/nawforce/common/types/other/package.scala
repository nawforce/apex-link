package com.nawforce.common.types

import com.nawforce.common.org.Module
import com.nawforce.common.types.core.DependentType
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
