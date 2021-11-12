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
package com.nawforce.pkgforce.sfdx

sealed trait BuildNumber
case object NextBuild        extends BuildNumber
case object LatestBuild      extends BuildNumber
case class Build(build: Int) extends BuildNumber

case class VersionNumber(major: Int, minor: Int, patch: Int, build: BuildNumber) {
  def isCompatible(other: VersionNumber): Boolean = {
    if (major != other.major || minor != other.minor || patch != other.patch)
      return false

    (build, other.build) match {
      case (Build(x), Build(y)) => x == y
      case (LatestBuild, _)     => true
      case _                    => false
    }
  }

  override def toString: String = {
    s"$major.$minor.$patch." + (build match {
      case Build(n)    => s"$n"
      case NextBuild   => "NEXT"
      case LatestBuild => "LATEST"
    })
  }
}
