## Abandoned?

This code was rolled into a project for my employeers some time ago so has not been updated here for some time. 
It's likely that will be released to the community at some point but leaving this older code available until 
that happens.

## ApexLink v0.1-SNANPSHOT

ApexLink is an offline validation and transformation toolkit for Salesforce packages. It reads and attempts to 
validate the usual package metadata structure and optionally you can use it to perform transforms over the Apex code
within the package. It is currently a little limited in what it can do but is fairly easy to extend.
 
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
syntactical problems in Apex code and provide a basis for writing some simple transformation tools of that code.

To run just a linker pass use:

     java -jar target/uber-apexlink-0.1-SNAPSHOT.jar <packagage directory>
     
If any errors are found these will be reported back to the console.
     
     
### Basic Transformations

After linking you can optionally run a set of transformations that analyse the Apex code in the package. A simple
example is provided by 'make-istests' which replaces the use of testmethod by @isTest. To run
this try:

     java -jar target/uber-apexlink-0.1-SNAPSHOT.jar -transform make-istests <packagage directory>

The output from the command is a patch file which you apply to your package Apex code in the usual ways. Automatic 
patching is on the TODO list.

### Bang Comments Transform

The 'bang-comments' transform gives you a simple way to selectively include code into a deployment perhaps for 
implementing developer asserts or additional debug information. To use this place '//!' before any code you 
wish to be optional, e.g.

    public count(List<String> aList) {
        //! Utils.assert(aList != null);
        return aList.size();
    }
  
When you run the 'bang-comments' transform the leading '//!' will be removed. The transform is restricted to 
only enable bang-comments in places where statements are allowed in Apex. This is deliberate to avoid them been
placed in obscure locations that might lead to hard to locate bugs.

### Label Sorting Transform

The 'sort-labels' transform does what it says on the tin, it re-orders the labels in your CustomLabels.labels file
so they match the order that you would get back from retrieving your package from Salesforce. This is handy if you
want to compare your source file in git with one deployed.

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
you have trouble building or running it. All the source code included uses a 3-clause BSD license. There are two 
third-party component included, the [Scala Patience Diff](https://github.com/owst/Scala-Patience-Diff/tree/master/OwenDiff) 
library and Apex Antlr4 grammar from [Tooling-force.com](https://github.com/neowit/tooling-force.com). Note both of 
these have been customised for use in ApexLink, in particularly the Antlr4 grammar is now markedly different from the 
original.  

If you want to write your own front end rather than use the built-in main class take a look at the 
io.github.nawforce.Apexlink source for an example of how to do this.
