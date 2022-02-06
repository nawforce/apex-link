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
import com.nawforce.apexlink.types.apex.{SummaryDeclaration, TriggerDeclaration}
import com.nawforce.apexlink.types.core.DependentType

class Plugin(td: DependentType) {

  def onTypeValidated(): Unit = {
    td match {
      case td: ClassDeclaration     => onClassValidated(td)
      case td: InterfaceDeclaration => onInterfaceValidated(td)
      case td: EnumDeclaration      => onEnumValidated(td)
      case td: TriggerDeclaration   => onTriggerValidated(td)
      case td: SummaryDeclaration   => onSummaryValidated(td)
    }
  }

  def onClassValidated(td: ClassDeclaration): Unit = {}

  def onInterfaceValidated(td: InterfaceDeclaration): Unit = {}

  def onEnumValidated(td: EnumDeclaration): Unit = {}

  def onTriggerValidated(td: TriggerDeclaration): Unit = {}

  def onSummaryValidated(td: SummaryDeclaration): Unit = {}

  def onBlockValidated(block: Block, isStatic: Boolean, context: BlockVerifyContext): Unit = {}
}
