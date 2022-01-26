## pkgforce

Provides utility functions for handling collections of Salesforce metadata. The library is implemented in Scala in a 
style that allows it to be compiled for either use on both JVM or node. 

### NPM API

The library is mostly written in Scala to support dual building for Java & Node. This model works well when you are 
also using Scala and is usable from Java but is more awkward for Node clients. To overcome this a small part of the 
library for resolving type names to paths is exposed in a Node friendly NPM module. 

To use this, first create a workspace:

    const workspace = Workspaces.get("mydirectory")     // Will throw on errors

Call findType on the workspace:

    const fooPath = workspace.findType("ns001.Foo")     // Returns null if type is unknown

A workspace here is simply the directory containing Salesforce metadata, typically it's the directory in which 
sfdx-project.json resides.

### Java Distribution via Maven

To use in a JVM project

    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>pkgforce</artifactId>
        <version>2.3.1/version>
    </dependency>

### Scala/Scala.js 

For scala.js:

    libraryDependencies += "com.github.nawforce" %%% "pkgforce" % "2.3.1"

For scala:

    libraryDependencies += "com.github.nawforce" % "pkgforce" % "2.3.1"

### Building

The dual build (Java & JS) is handled by [sbt](https://www.scala-sbt.org/): 

    sbt build
   
To run tests:

    sbt test

A maven pom.xml is also provided to aid compatibility with IntelliJ. See top level README.md for details on use.    
