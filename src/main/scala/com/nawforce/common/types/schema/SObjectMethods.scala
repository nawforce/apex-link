package com.nawforce.common.types.schema

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.cst.VerifyContext
import com.nawforce.common.names.TypeNames
import com.nawforce.common.types.core.{MethodDeclaration, TypeDeclaration}
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}

trait SObjectMethods {
  this: TypeDeclaration =>

  def defaultFindMethod(name: Name,
                        params: Array[TypeName],
                        staticContext: Option[Boolean],
                        verifyContext: VerifyContext): Array[MethodDeclaration] = {
    val clone = cloneMethods.get((name, params.length, staticContext.contains(true)))
    if (clone.nonEmpty)
      clone.toArray
    else
      PlatformTypes.sObjectType.findMethod(name, params, staticContext, verifyContext)
  }

  private lazy val cloneMethods: Map[(Name, Int, Boolean), MethodDeclaration] = {
    val preserveId = CustomParameterDeclaration(Name("preserveId"), TypeNames.Boolean)
    val isDeepClone = CustomParameterDeclaration(Name("isDeepClone"), TypeNames.Boolean)
    val preserveReadOnlyTimestamps =
      CustomParameterDeclaration(Name("preserveReadOnlyTimestamps"), TypeNames.Boolean)
    val preserveAutonumber =
      CustomParameterDeclaration(Name("preserveAutonumber"), TypeNames.Boolean)
    Seq(CustomMethodDeclaration(None, Name("clone"), typeName, Array()),
      CustomMethodDeclaration(None, Name("clone"), typeName, Array(preserveId)),
      CustomMethodDeclaration(None, Name("clone"), typeName, Array(preserveId, isDeepClone)),
      CustomMethodDeclaration(None,
        Name("clone"),
        typeName,
        Array(preserveId, isDeepClone, preserveReadOnlyTimestamps)),
      CustomMethodDeclaration(
        None,
        Name("clone"),
        typeName,
        Array(preserveId, isDeepClone, preserveReadOnlyTimestamps, preserveAutonumber)))
      .map(m => ((m.name, m.parameters.length, m.isStatic), m))
      .toMap
  }
}
