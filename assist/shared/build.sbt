
name := "ApexAssist"

version := "0.1"

scalaVersion := "2.13.1"

enablePlugins(ScalaJSBundlerPlugin)
webpackBundlingMode := BundlingMode.LibraryOnly()
npmDependencies in Compile += "java" -> "0.11.1"

webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack.config.js")
