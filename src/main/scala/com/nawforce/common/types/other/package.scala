package com.nawforce.common.types

import com.nawforce.common.documents._
import com.nawforce.common.org.Module
import com.nawforce.common.stream.PackageStream
import com.nawforce.common.types.core.DependentType

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
