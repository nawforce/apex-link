package com.nawforce.common.types

import com.nawforce.common.documents._
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.types.core.DependentType

package object other {

  trait OtherTypeDeclaration extends DependentType {
    def merge(stream: PackageStream): OtherTypeDeclaration
  }

  object Other {
    def apply(dt: UpdatableMetadata, pkg: PackageImpl): OtherTypeDeclaration = {
      dt match {
        case _: LabelsDocument => LabelDeclaration(pkg)
        case _: PageDocument => PageDeclaration(pkg)
        case _: ComponentDocument => ComponentDeclaration(pkg)
        case _: FlowDocument => InterviewDeclaration(pkg)
      }
    }
  }

}
