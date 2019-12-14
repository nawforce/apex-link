
name := "ApexAssist"

version := "0.1"

scalaVersion := "2.12.3"

enablePlugins(ScalaJSBundlerPlugin)
scalacOptions += "-P:scalajs:sjsDefinedByDefault"
webpackBundlingMode := BundlingMode.LibraryOnly()
npmDependencies in Compile += "java" -> "0.11.1"

npmDependencies in Test += "fs-monkey" -> "0.3.3"
npmDependencies in Test += "memfs" -> "3.0.1"

webpackConfigFile in fastOptJS := Some(baseDirectory.value / "custom.webpack.config.js")

resolvers += Resolver.sonatypeRepo("public")
libraryDependencies += "io.scalajs" %%% "nodejs" % "0.4.2"
libraryDependencies += "org.scalaz" %%% "scalaz-core" % "7.2.8"
libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.0" % "test"
