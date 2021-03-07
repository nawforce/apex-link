## pkgforce

Provides utility functions for handling collections of Salesforce metadata. The library is implemented in Scala in a 
style that allows it to be compiled for either use on both JVM or node. 

This code was originally part of [ApexLink](https://github.com/nawforce/ApexLink) which can perform static analysis of 
Apex code and support Salesforce IDEs. It has been spun out to allow it to be used for other tasks.

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
        <version>1.3.2</version>
    </dependency>

### Scala/Scala.js 

For scala.js:

    libraryDependencies += "com.github.nawforce" %%% "pkgforce" % "1.3.2"

For scala:

    libraryDependencies += "com.github.nawforce" % "pkgforce" % "1.3.2"

### Building

The dual build (Java & JS) is handled by [sbt](https://www.scala-sbt.org/): 

    sbt build
   
To run tests:

    sbt test   

### Source & Licenses

All the source code included uses a 3-clause BSD license. The only third-party component included is the Apex Antlr4 
grammar originally from [Tooling-force.com](https://github.com/neowit/tooling-force.com), although the version used is
now markedly different from the original.  
