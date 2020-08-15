## pkgforce

Provides utility functions for the handling collections of Salesforce metadata. The library is implemented in Scala
in a style that allows it to be compiled for either use on both JVM or node. This library has been spun out of 
[ApexLink](https://github.com/nawforce/ApexLink) which can performed static analysis of Apex code and support 
Salesforce IDEs.

### API Model

TODO

### Building

The dual build (Java & JS) is handled by [sbt](https://www.scala-sbt.org/): 

    sbt all
   
   
### Maven

To use the jar in a maven project add the following to your pom.xml

    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>pkgforce</artifactId>
        <version>1.0.0</version>
    </dependency>

### Source & Licenses

All the source code included uses a 3-clause BSD license. The only third-party component included is the Apex Antlr4 
grammar originally from [Tooling-force.com](https://github.com/neowit/tooling-force.com), although the version used is
now markedly different from the original.  
