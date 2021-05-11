package com.nawforce.common.types.schema

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.cst.VerifyContext
import com.nawforce.common.modifiers.{GLOBAL_MODIFIER, Modifier}
import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.core.{BasicTypeDeclaration, FieldDeclaration, MethodDeclaration, TypeDeclaration}
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}

final case class GhostSObjectDeclaration( pkg: PackageImpl,
                                    _typeName: TypeName)
  extends BasicTypeDeclaration(Array(), pkg, _typeName) {

  override val isComplete: Boolean = false

  override val modifiers: Array[Modifier] = Array(GLOBAL_MODIFIER)

  override val superClass: Option[TypeName] = {
    Some(TypeNames.SObject)
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    pkg.getTypeFor(superClass.get, this)
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    None
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    if (staticContext.contains(true)) {
      val customMethod = SObjectDeclaration.sObjectMethodMap.get((name, params.length))
      if (customMethod.nonEmpty)
        return customMethod.toArray
    }
    defaultFindMethod(name, params, staticContext, verifyContext)
  }

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
