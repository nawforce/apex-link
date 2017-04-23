ApexLink v0.1-SNANPSHOT
=======================

ApexLink is an offline validation and transformation toolkit for Salesforce packages. It reads and attempts to 
validate the usual package metadata structure and optionally you can use it perform transforms over the Apex code
within the package. It is currently a little limited in what it can do but is fairly easy to extend.
 
There are a few publicly available tools available for handling packaged metadata offline, notably Salesforce's own 
Eclipse plugin provides Apex parsing and the derived PMD plugin utilises this but neither can offer full source code
access at present, besides this project you may want to take a look at Andrey Gavrikov's 
[Tooling-force.com](https://github.com/neowit/tooling-force.com) project which includes Apex & SOQL parsers.   

Building
--------

To create a jar use:

    mvn package


Linking
-------

In spirit ApexLink attempts to reproduce the functionality provided by traditional toolchain linkers in that it reads 
the package and tries to resolve all symbols to ensure they exist in the correct form. To do this it reads and parses
the metadata in the package. In practice it's quite a long way from being useful as a linker but can report some simple
syntactical problems in Apex code and provide a basis for writing some simple transformation tools of that code.

To run just a linker pass use:

     java -jar target/uber-apexlink-0.1-SNAPSHOT.jar <packagage directory>
     
If any errors are found these will be reported back to the console.
     
     
Transformation
--------------

After linking your can optionally run a set of transformations that analyse the Apex code in the package. A simple
example is provided by 'assert-delete' which removes all calls to the System.assert function from the code. To run
this:

     java -jar target/uber-apexlink-0.1-SNAPSHOT.jar -transform assert-delete <packagage directory>

The output from the command is a patch file which you apply to your package Apex code in the usual ways. Automatic 
patching is on the TODO list.

Source & Licenses
-----------------

ApexLink is written in a combination of Java and Scala but should run on any fairly recent JVM. Please let me know if 
you have trouble building or running it. All the source code included uses a 3-clause BSD license. There are two 
third-party component included, the [Scala Patience Diff](https://github.com/owst/Scala-Patience-Diff/tree/master/OwenDiff) 
library and Apex Antlr4 grammar from [Tooling-force.com](https://github.com/neowit/tooling-force.com). Note both of 
these have been customised for use in ApexLink, in particularly the Antlr4 grammar is now markedly different from the 
original.  