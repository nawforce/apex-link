import java.nio.file.Path

name := "apexlink-js"
version := "0.5.0"
scalaVersion := "2.12.3"

unmanagedSourceDirectories in Compile += baseDirectory.value / "common"
unmanagedSourceDirectories in Test += baseDirectory.value / "common-test"

enablePlugins(ScalaJSBundlerPlugin)
scalacOptions += "-P:scalajs:sjsDefinedByDefault"
webpackBundlingMode := BundlingMode.Application

npmDependencies in Compile += "java" -> "0.11.1"
npmDependencies in Compile += "xmldom" -> "0.1.27"
npmDependencies in Compile += "antlr4ts" -> "0.5.0-alpha.3"
npmDependencies in Test += "fs-monkey" -> "0.3.3"
npmDependencies in Test += "memfs" -> "3.0.1"

webpackConfigFile in fastOptJS := Some(baseDirectory.value / "custom.webpack.config.js")
webpackConfigFile in fullOptJS := Some(baseDirectory.value / "custom.webpack.config.js")

resolvers += Resolver.sonatypeRepo("public")
libraryDependencies += "io.scalajs" %%% "nodejs" % "0.4.2"
libraryDependencies += "org.scalaz" %%% "scalaz-core" % "7.2.8"
libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.0" % "test"

val npmTargetDir = s"target/npm/" // where to generate npm
val npmConf = "npm_config" // directory with static files for NPM package
val npmTask = taskKey[Unit](s"Create npm package in $npmTargetDir")

npmTask := {
  // JS libraries must first be generated
  (Compile / fastOptJS).value
  (Compile / fullOptJS).value

  import java.nio.file.StandardCopyOption.REPLACE_EXISTING
  import java.nio.file.Files.copy
  import java.nio.file.Paths.get
  import java.nio.file.{Paths, Files}

  import java.nio.charset.StandardCharsets

  implicit def toPath (filename: String): Path = get(filename)

  def copyToDir(filePathName:String, dirName:String) = {
    val fileName = new File(filePathName).getName
    copy (s"$filePathName", s"$dirName/$fileName", REPLACE_EXISTING)
  }

  val libName = name.value.toLowerCase()

  val inputDir = "target/scala-2.12/scalajs-bundler/main"
  val targetDir = s"$npmTargetDir/$libName"
  val sourceDir = "source/js"
  val distDir = "dist/js"
  val jarDir = "jars"

  // Create module directory structure
  new File(targetDir).mkdirs()
  List(distDir, sourceDir, jarDir).foreach(d => new File(s"$targetDir/$d").mkdirs())

  // copy static files
  copyToDir(s"../LICENSE", targetDir)
  copyToDir(s"../README.md", targetDir)

  // copy optimized js library
  val fileDist = List(s"$libName-opt.js", s"$libName-opt.js.map")
  for(file <- fileDist) {
    println(s"copy file $inputDir/$file")
    copy(s"$inputDir/$file", s"$targetDir/$distDir/$file", REPLACE_EXISTING)
  }

  // copy non optimized js library (for debug purpose)
  val fileSource = List(s"$libName-fastopt.js", s"$libName-fastopt.js.map")
  for(file <- fileSource) {
    println(s"copy file $inputDir/$file")
    copy(s"$inputDir/$file", s"$targetDir/$sourceDir/$file", REPLACE_EXISTING)
  }

  // copy jars directory
  val fileJars = new File(jarDir).listFiles().filter(_.name.endsWith(".jar")).map(_.name)
  for(file <- fileJars) {
    println(s"copy file $jarDir/$file")
    copy(s"$jarDir/$file", s"$targetDir/$jarDir/$file", REPLACE_EXISTING)
  }

  val packageJson = s"""{
  "name": "$libName",
  "version": "${version.value.toString}",
  "description": "${description.value.toString}",
  "main": "$distDir/$libName-opt.js",
  "repository": "nawforce/apexlink",
  "author": "Kevin Jones <nawforce@gmail.com> (https://github.com/nawforce)",
  "license": "BSD-3-Clause",
  "bugs": "https://github.com/nawforce/apexlink/issues",
  "dependencies": {
    "java": "0.11.1",
    "xmldom": "0.1.27",
    "antlr4ts": "0.5.0-alpha.3",
    "apex-parser": "1.0.0"
  }
}"""

  println(packageJson)
  Files.write(Paths.get(s"$targetDir/package.json"), packageJson.getBytes(StandardCharsets.UTF_8))

  println(s"NPM package created in $npmTargetDir")
}
