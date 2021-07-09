/*
 Copyright (c) 2021 Kevin Jones & FinancialForce, All rights reserved.
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

package com.nawforce.apexlink.types.schema

import com.nawforce.apexlink.cst.VerifyContext
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.core.{MethodDeclaration, TypeDeclaration}
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.names.{Name, TypeName}

trait SObjectMethods {
  this: TypeDeclaration =>

  def defaultFindMethod(name: Name,
                        params: Array[TypeName],
                        staticContext: Option[Boolean],
                        verifyContext: VerifyContext): Option[MethodDeclaration] = {
    cloneMethods
      .get((name, params.length, staticContext.contains(true)))
      .orElse(PlatformTypes.sObjectType.findMethod(name, params, staticContext, verifyContext))
  }

  private lazy val cloneMethods: Map[(Name, Int, Boolean), MethodDeclaration] = {
    val preserveId = CustomParameterDeclaration(Name("preserveId"), TypeNames.Boolean)
    val isDeepClone = CustomParameterDeclaration(Name("isDeepClone"), TypeNames.Boolean)
    val preserveReadOnlyTimestamps =
      CustomParameterDeclaration(Name("preserveReadOnlyTimestamps"), TypeNames.Boolean)
    val preserveAutonumber =
      CustomParameterDeclaration(Name("preserveAutonumber"), TypeNames.Boolean)
    Seq(CustomMethodDeclaration(Location.empty, Name("clone"), typeName, Array()),
        CustomMethodDeclaration(Location.empty, Name("clone"), typeName, Array(preserveId)),
        CustomMethodDeclaration(Location.empty, Name("clone"), typeName, Array(preserveId, isDeepClone)),
        CustomMethodDeclaration(Location.empty,
                                Name("clone"),
                                typeName,
                                Array(preserveId, isDeepClone, preserveReadOnlyTimestamps)),
        CustomMethodDeclaration(Location.empty,
                                Name("clone"),
                                typeName,
                                Array(preserveId, isDeepClone, preserveReadOnlyTimestamps, preserveAutonumber)))
      .map(m => ((m.name, m.parameters.length, m.isStatic), m))
      .toMap
  }
}
