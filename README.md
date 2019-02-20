
## ApexLink v0.1-SNAPSHOT

ApexLink is a library for supporting offline validation of Salesforce package Apex code. It's currently WIP but
contains a half decent parser for Apex alongside definitions for common platform classes and SObjects. 
 
There are a few publicly available tools available for handling packaged metadata offline, notably Salesforce's own 
Eclipse plugin provides Apex parsing and the derived PMD plugin utilises this but neither can offer full source code
access at present, besides this project you may want to take a look at Andrey Gavrikov's 
[Tooling-force.com](https://github.com/neowit/tooling-force.com) project which includes Apex & SOQL parsers.   

### Building

To create a jar use:

    mvn package

### Linking

In spirit ApexLink attempts to reproduce the functionality provided by traditional toolchain linkers in that it reads 
the package and tries to resolve all symbols to ensure they exist in the correct form. To do this it reads and parses
the metadata in the package. In practice it's quite a long way from being useful as a linker but can report some simple
syntactical problems in Apex code.

To run just a linker pass use:

     java -jar target/uber-apexlink-0.1-SNAPSHOT.jar <packagage directory>
     
If any errors are found these will be reported back to the console.
     
### Maven

To use in a maven project add the following to your pom.xml

    <repositories>
        <repository>
            <id>oss-sonatype</id>
            <name>oss-sonatype</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>


    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>apexlink</artifactId>
        <version>0.1-SNAPSHOT</version>
    </dependency>

### Source & Licenses

ApexLink is written in a combination of Java and Scala but should run on any fairly recent JVM. Please let me know if 
you have trouble building or running it. All the source code included uses a 3-clause BSD license. The only third-party
component included is the Apex Antlr4 grammar from [Tooling-force.com](https://github.com/neowit/tooling-force.com),
but this is now markedly different from the original.  

