import sbt.Keys.libraryDependencies
import sbt.url
import sbtcrossproject.CrossPlugin.autoImport.crossProject

ThisBuild / version := "2.3.1"
ThisBuild / isSnapshot := true

ThisBuild / scalaVersion := "2.13.3"
ThisBuild / parallelExecution := false
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
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / resolvers += Resolver.mavenLocal

lazy val buildNPM = Def.task {

  // Update NPM module with latest compile
  import java.nio.file.StandardCopyOption.REPLACE_EXISTING
  import java.nio.file.Files.copy

  (Compile / fastOptJS).value
  (Compile / fullOptJS).value

  val jsDir = file("js")
  val targetDir = jsDir / "target" / "scala-2.13"
  val optSource = targetDir / "pkgforce-opt.js"
  val optTarget = jsDir / "npm" / "src" / "pkgforce.js"
  copy(optSource.toPath, optTarget.toPath, REPLACE_EXISTING)

  // Install modules in NPM
  import scala.language.postfixOps
  import scala.sys.process._

  val npmDir = jsDir / "npm"
  val shell: Seq[String] = if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
  Process(shell :+ "npm i --production", npmDir) !

  // Update NPM README.md
  val readMeTarget = npmDir / "README.md"
  copy(file("README.md").toPath, readMeTarget.toPath, REPLACE_EXISTING)

  // Update target with NPM modules (for testing)
  val packageJSONSource = npmDir / "package.json"
  val packageJSONTarget = targetDir / "package.json"
  copy(packageJSONSource.toPath, packageJSONTarget.toPath, REPLACE_EXISTING)
  Process(shell :+ "npm i", targetDir) !
}

lazy val buildJVM = Def.task {
  (Compile / compile).value
}

lazy val build = taskKey[Unit]("Build artifacts")

lazy val root = project.in(file(".")).
  aggregate(pkgforce.js, pkgforce.jvm).
  settings(
    publish := {},
    publishLocal := {},
    publishM2 := {}
  )

lazy val pkgforce = crossProject(JSPlatform, JVMPlatform).in(file(".")).
  settings(
    scalacOptions += "-deprecation",
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "1.2.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.0" % Test,
  ).
  jvmSettings(
    build := buildJVM.value,
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0",
    libraryDependencies += "com.github.nawforce" % "apex-parser" % "2.12.1",
    libraryDependencies += "org.antlr" % "antlr4-runtime" % "4.8-1",
    libraryDependencies += "com.google.jimfs" % "jimfs" % "1.1" % Test
  ).
  jsSettings(
    build := buildNPM.value,
    libraryDependencies += "net.exoego" %%% "scala-js-nodejs-v14" % "0.12.0",
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
  )
