## pkgforce

master: [![Master Build Status](https://travis-ci.org/nawforce/pkgforce.svg?branch=master)](https://travis-ci.org/nawforce/pkgforce)

Provides utility functions for the handling collections of Salesforce metadata. The library is implemented in Scala
in a style that allows it to be compiled for either use on both JVM or node. This library was originally part of  
[ApexLink](https://github.com/nawforce/ApexLink) which can perform static analysis of Apex code and support 
Salesforce IDEs. It has been spun out to allow it to be used for other tasks.

### API Model

TODO
  
## NPM Module

To use the npm module

    npm install pkgforce       
   
### Java Distribution via Maven

To use in a JVM project

    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>pkgforce</artifactId>
        <version>1.0.0</version>
    </dependency>

### Building

The dual build (Java & JS) is handled by [sbt](https://www.scala-sbt.org/): 

    sbt build
   
To run tests:

    sbt test   

### Source & Licenses

All the source code included uses a 3-clause BSD license. The only third-party component included is the Apex Antlr4 
grammar originally from [Tooling-force.com](https://github.com/neowit/tooling-force.com), although the version used is
now markedly different from the original.  
