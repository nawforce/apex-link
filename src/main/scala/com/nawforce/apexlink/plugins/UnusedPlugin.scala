/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.plugins

import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.types.core.DependentType
import com.nawforce.pkgforce.diagnostics.{Diagnostic, Issue, UNUSED_CATEGORY}

class UnusedPlugin(td: DependentType) extends Plugin(td) {

  override def onClassValidated(td: ClassDeclaration): Unit = {
    if (td.outerTypeName.isEmpty)
      td.unused().foreach(td.module.pkg.org.issues.add)
  }

  override def onEnumValidated(td: EnumDeclaration): Unit = {
    if (td.outerTypeName.isEmpty)
      td.unused().foreach(td.module.pkg.org.issues.add)
  }

  override def onInterfaceValidated(td: InterfaceDeclaration): Unit = {
    if (td.outerTypeName.isEmpty)
      td.unused().foreach(td.module.pkg.org.issues.add)
  }

  override def onBlockValidated(block: Block, isStatic: Boolean, context: BlockVerifyContext): Unit = {
    context.declaredVars
      .filter(v => !context.referencedVars.contains(v._1) && v._2.definition.nonEmpty)
      .foreach(v => {
        val definition = v._2.definition.get
        context.log(
          new Issue(definition.location.path,
            Diagnostic(UNUSED_CATEGORY, definition.location.location, s"Unused local variable '${v._1}'")))
      })
  }
}
