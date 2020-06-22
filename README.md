## ApexLink

ApexLink is a SFDX CLI plugin & Java library for static analysis of Salesforce Apex code aimed at improving developer 
productivity. The core library is useful for any number of analysis problems while the CLI plugin acts as a demo of 
current capability.
 
### SFDX CLI

To install the CLI plugin (from npm)

    sfdx plugins:install apexlink

Check the installation was successful with

    sfdx plugins
     
This should show apexlink in the plugin list.      

To perform a simple validity check use:

    sfdx apexlink:check <directory>

This parses and performs semantic checks on the code and reports any errors, such as types not being found. The library
contains a pretty comprehensive set of platform types that it validates against.

More complex validations can be performed that support namespaced packages and multiple source directories, see the 
command help for more details. This command does not require an sfdx project, if you omit the directory it will search 
the current directory for metadata.  

### Unused fields, properties & methods

You can use the check command to report on unused fields, properties and methods of Apex classes. 

    sfdx apexlink:check --zombies <directory>

This analysis currently may return false positives for:
*  Fields & Properties only bound to SOQL queries
*  Properties only used by Visualforce Pages
*  Fields, properties & methods only referenced from triggers.   

### Class dependencies

The check command can also report Apex class dependencies with:

    sfdx apexlink:check --depends --json <directory>

If you omit the --json the dependency default format is CSV. Understanding dependencies is useful when analysing 
[cold start behaviours](https://nawforce.blog/2019/02/25/apex-cold-starts-and-class-caching-misses/) but it also 
provides the support for identifying unused methods and fields. 

### Building

The master branch is under active development, so it's generally best to build from the last released version.

To create a jar use:

    mvn package
    
To create the SFDX CLI package use:
    
    cd cli
    sh package.sh
    yarn    
     
### Maven

To use the jar in a maven project add the following to your pom.xml

    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>apexlink</artifactId>
        <version>0.9.3</version>
    </dependency>

### Source & Licenses

The core of ApexLink is written in Scala but should run on any fairly recent JVM and is known to cross compile to 
Javascript via scala.js. Please let me know if you have trouble building or running it. All the source code included 
uses a 3-clause BSD license. The only third-party component included is the Apex Antlr4 grammar originally from 
[Tooling-force.com](https://github.com/neowit/tooling-force.com), although the version used is now markedly different 
from the original.  
