ApexLink v0.01
==============

ApexLink is an offline validation and transformation toolkit for Salesforce packages. It reads and attempts to 
 validate the usual package metadata structure and optionally you can use it perform transforms over the Apex code
 within the package.
 
There are few publicly available tools available for handling packaged metadata offline, notably Salesforce's own Eclipse 
plugin and the derived PMD plugin but nothing that as yet offers full source code. ApexLink is an attempt to supply that
but currently it has fairly limited functionality.

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

     java -jar target/uber-apexlink-0.01.jar <packagage directory>
     
If any errors are found these will be reported back to the console.
     
     
Transformation
--------------

After linking your can optionally run a set of transformations that analyse the Apex code in the package. A simple
example is provided by 'assert-delete' which removes all calls to the System.assert function from the code. To run
this:

     java -jar target/uber-apexlink-0.01.jar -transform assert-delete <packagage directory>

The output from the command is a patch file which you apply to your package Apex code in the usual ways. Automatic 
patching is on the TODO list.

Source & Licenses
-----------------

ApexLink is written in a combination of Java and Scala but should run on any modern VM. Please let me know if you have
trouble running. There are a few different copyrights in the code that means you probably don't have rights to
redistribute in a binary format. I will attempt to tidy the license conditions in a future version.     