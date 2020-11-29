import sbt.Keys.libraryDependencies
import sbtcrossproject.CrossPlugin.autoImport.crossProject

lazy val build = taskKey[Unit]("Build artifacts")

ThisBuild / scalaVersion := "2.13.3"

lazy val pkgforce = project.in(file(".")).
  aggregate(cross.js, cross.jvm).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val buildNPM = Def.task {

  // Update NPM module with latest compile
  import java.nio.file.StandardCopyOption.REPLACE_EXISTING
  import java.nio.file.Files.copy

  (Compile / fastOptJS).value
  (Compile / fullOptJS).value

  val targetDir = file("js/target/scala-2.13").toPath
  val npmDir = file("js/npm").toPath
  val outputFiles = Seq("pkgforce-fastopt.js", "pkgforce-opt.js", "pkgforce-fastopt.js.map", "pkgforce-opt.js.map")

  outputFiles.foreach(name => copy(targetDir.resolve(name), npmDir.resolve(name), REPLACE_EXISTING))

  // Install modules in NPM
  import scala.language.postfixOps
  import scala.sys.process._

  Process("npm i --production", npmDir.toFile) !

  // Update target with NPM modules (for testing)
  copy(npmDir.resolve("package.json"), targetDir.resolve("package.json"), REPLACE_EXISTING)
  Process("npm i", targetDir.toFile) !
}

lazy val buildJVM = Def.task {
  (Compile / compile).value
}

lazy val cross = crossProject(JSPlatform, JVMPlatform).in(file(".")).
  settings(
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "1.2.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.0" % Test,
  ).
  jvmSettings(
    name := "pkgforce",
    version := "1.2.0-SNAPSHOT",
    build := buildJVM.value,
    scalacOptions += "-deprecation",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
    libraryDependencies += "org.antlr" % "antlr4-runtime" % "4.8-1",
    libraryDependencies += "com.google.jimfs" % "jimfs" % "1.1" % Test
  ).
  jsSettings(
    name := "pkgforce",
    version := "1.2.0-SNAPSHOT",
    build := buildNPM.value,
    scalacOptions += "-deprecation",
    libraryDependencies += "net.exoego" %%% "scala-js-nodejs-v14" % "0.12.0",
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
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
ThisBuild / isSnapshot := true
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
