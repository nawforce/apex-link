## apex-link

This is a static analysis library for Salesforce metadata. It mostly focuses on Apex classes but also handles other types of metadata if they are relevant to understanding Apex code, such as custom objects. The library is used by the VSCode extension [apex-assist](https://github.com/nawforce/apex-assist) & the SFDX CLI plugin [apex-link-sfdx-cli](https://github.com/nawforce/apex-link-sfdx-cli). Metadata support utilities such Apex & Visualforce parsing are provided by [pkgforce](https://github.com/nawforce/pkgforce). If you are interested in Apex or Visualforce parsing on node for your own tools see [apex-parser](https://github.com/nawforce/apex-parser) and [vf-parser](https://github.com/nawforce/vf-parser). 

The library is named after the 'link' programs used in other languages to construct a single executable from a number of object files. The library does not construct an executable but does create a detailed relationship graph between the metadata that it loads which allows for 'whole-program' analyses to be implemented. In constructing this graph it validates the metadata to ensure correctness and so can also be used to validate metadata without ever deploying it to a Salesforce Org. This makes the library particularly useful when performing code refactoring, as it can give near instant feedback on the state of a codebase as you change it.

### Current Capabilities
Currently the main aim of the Apex validation is to ensure that code is not referencing any types, fields, methods etc that are not available, this feature is largely complete at this point. The main validation holes are in statement validation which have not yet been implemented beyond some simple cases, e.g. comparing the wrong data types in if statements will not yet cause an error.

The underlying handling of metadata currently uses a 1GP like package model. The library can perform analysis involving multiple packages but they must have unique namespaces. Work is planned to expand this model to include SFDX style multi-package directories and ultimately 2GP packages sharing a namespace (see Roadmap below).

### Testing
The library comes with a set of unit tests to limit regressions. We also use a set of around 100 of the most popular Salesforce projects on github for testing. These are made available in this repo via git modules (see samples/README.md) and can be run using the 'Test' github action. If you have a particular interest in seeing a project regularly tested for compatibility please feel free to raise a PR to add it.

### API Access
The main API for the library is based around the concept of a virtual (in-memory) Salesforce Org. Using this model makes it a little easier to reason about how the API should work. You can find docs for the Org abstraction at [javadoc.io](https://javadoc.io/doc/com.github.nawforce/apexlink/latest/com/nawforce/common/api/Org.html). Many of the internal classes of the library are available for use but only classes in the com.nawforce.apexlink.api package are considered stable.

The [apex-assist](https://github.com/nawforce/apex-assist) VSCode extension makes use of a JSON-RPC server that is built into the library. The [API](https://javadoc.io/doc/com.github.nawforce/apexlink/latest/com/nawforce/common/rpc/OrgAPI.html) for this is not considered stable, but it may be more convenient for some use cases.

### Roadmap
Building this library has been a multi-year effort, but there are still plenty of improvements to be made. Work is currently focused on a 2.0.0 release which has breaking API changes. This mainly adds the ability to layer metadata internally & support sharing of namespaces for 1GP MPD and 2GP support. Following this we expect to work on the following:
 
* Add ability to import metadata into a layer from external sources, such as directly from an org
* Extended validations (this can be done incrementally with each release)
* Provide a means to perform call graph analysis
* Provide an API to search/access the internal syntax trees
     
### Maven
To use the jar in a maven project add the following to your pom.xml

    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>apexlink</artifactId>
        <version>1.4.6</version>
    </dependency>

or for the latest unstable release

    <dependency>
        <groupId>com.github.nawforce</groupId>
        <artifactId>apexlink</artifactId>
        <version>2.0.0-rc2</version>
    </dependency>

### Source & Licenses
The core of apex-link is written in Scala but should run on any fairly recent JVM and is known to cross compile to Javascript via scala.js. Please let me know if you have trouble building or running it. All the source code included uses a 3-clause BSD license. The only third-party component included is the Apex Antlr4 grammar originally from [Tooling-force.com](https://github.com/neowit/tooling-force.com), although the version used is now markedly different 
from the original.  
