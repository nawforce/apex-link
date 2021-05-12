package com.nawforce.common.types.schema

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.cst.VerifyContext
import com.nawforce.common.modifiers.{GLOBAL_MODIFIER, Modifier}
import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.core.{BasicTypeDeclaration, FieldDeclaration, MethodDeclaration, TypeDeclaration}
import com.nawforce.common.types.platform.PlatformTypes

final case class GhostSObjectDeclaration( pkg: PackageImpl,
                                    _typeName: TypeName)
  extends BasicTypeDeclaration(Array(), pkg, _typeName) with SObjectMethods {

  override val isComplete: Boolean = false

  override val modifiers: Array[Modifier] = Array(GLOBAL_MODIFIER)

  override val superClass: Option[TypeName] = {
    Some(TypeNames.SObject)
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    pkg.getTypeFor(superClass.get, this)
  }

  override val fields: Array[FieldDeclaration] = {
    PlatformTypes.customSObject.fields.map(f => (f.name, f)).toMap.values.toArray
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    super
      .findFieldSObject(name, staticContext)
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
}
