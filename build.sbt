import org.scalajs.core.tools.linker.backend.ModuleKind.CommonJSModule
import sbt.Keys.libraryDependencies
import sbtcrossproject.CrossPlugin.autoImport.crossProject

ThisBuild / scalaVersion := "2.12.11"

lazy val all = project.in(file(".")).
  aggregate(pkgforce.js, pkgforce.jvm).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val pkgforce = crossProject(JSPlatform, JVMPlatform).in(file(".")).
  settings(
    name := "pkgforce",
    version := "1.0-SNAPSHOT",
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "1.2.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.0" % Test
  ).
  jvmSettings(
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
    libraryDependencies += "org.antlr" % "antlr4-runtime" % "4.8-1",
    libraryDependencies += "com.google.jimfs" % "jimfs" % "1.1" % Test,
  ).
  jsSettings(
    publish := {},
    libraryDependencies += "io.scalajs" %%% "nodejs" % "0.4.2",
    scalaJSModuleKind := CommonJSModule,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault"
  )


// To publish use: pkgforceJVM / publishSigned

ThisBuild / organization := "com.github.nawforce"
ThisBuild / organizationHomepage := Some(url("https://github.com/nawforce/pkgforce"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/nawforce/pkgforce"),
    "git@github.com:nawforce/pkgforce.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "nawforce",
    name = "Kevin Jones",
    email = "nawforce@gmail.com",
    url = url("https://github.com/nawforce"
    )
  )
)

ThisBuild / description := "Salesforce Metadata Management Utility Library"
ThisBuild / licenses := List("BSD-3-Clause" -> new URL("https://opensource.org/licenses/BSD-3-Clause"))
ThisBuild / homepage := Some(url("https://github.com/nawforce/pkgforce"))

ThisBuild / credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")

ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
