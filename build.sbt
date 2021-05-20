import sbt.Keys.libraryDependencies
import sbtcrossproject.CrossPlugin.autoImport.crossProject

lazy val build = taskKey[Unit]("Build artifacts")

ThisBuild / scalaVersion := "2.13.3"
ThisBuild / parallelExecution := false

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
  val srcDir = file("js/npm/src").toPath
  copy(targetDir.resolve("pkgforce-opt.js"), srcDir.resolve("pkgforce.js"), REPLACE_EXISTING)

  // Install modules in NPM
  import scala.language.postfixOps
  import scala.sys.process._

  val npmDir = file("js/npm").toPath
  Process("npm i --production", npmDir.toFile) !

  // Update NPM README.md
  copy(file("README.md").toPath, file("js/npm/README.md").toPath, REPLACE_EXISTING)

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
    version := "2.0.0",
    build := buildJVM.value,
    scalacOptions += "-deprecation",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0",
    libraryDependencies += "org.antlr" % "antlr4-runtime" % "4.8-1",
    libraryDependencies += "com.google.jimfs" % "jimfs" % "1.1" % Test
  ).
  jsSettings(
    name := "pkgforce",
    version := "2.0.0",
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
ThisBuild / isSnapshot := false
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
