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
    libraryDependencies += "io.scalajs" %%% "nodejs" % "0.4.2",
    scalaJSModuleKind := CommonJSModule,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault"
  )
