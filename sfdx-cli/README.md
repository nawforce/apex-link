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

### Usage

<!-- toc -->

<!-- tocstop -->
<!-- install -->
<!-- usage -->
```sh-session
$ npm install -g apexlink
$ sfdx COMMAND
running command...
$ sfdx (-v|--version|version)
apexlink/1.3.2 darwin-x64 node-v12.16.2
$ sfdx --help [COMMAND]
USAGE
  $ sfdx COMMAND
...
```
<!-- usagestop -->
<!-- commands -->
* [`sfdx apexlink:check [--zombie] [--depends] [--namespaces <string>] [--verbose] [--json] [--loglevel trace|debug|info|warn|error|fatal|TRACE|DEBUG|INFO|WARN|ERROR|FATAL]`](#sfdx-apexlinkcheck---zombie---depends---namespaces-string---verbose---json---loglevel-tracedebuginfowarnerrorfataltracedebuginfowarnerrorfatal)

## `sfdx apexlink:check [--zombie] [--depends] [--namespaces <string>] [--verbose] [--json] [--loglevel trace|debug|info|warn|error|fatal|TRACE|DEBUG|INFO|WARN|ERROR|FATAL]`

Validate Apex code in current or passed directories

```
USAGE
  $ sfdx apexlink:check [--zombie] [--depends] [--namespaces <string>] [--verbose] [--json] [--loglevel 
  trace|debug|info|warn|error|fatal|TRACE|DEBUG|INFO|WARN|ERROR|FATAL]

ARGUMENTS
  DIRECTORY  directory to search for metadata files, defaults to current directory

OPTIONS
  --depends                                                                         output map of type dependencies
                                                                                    rather than issues, CSV or JSON
                                                                                    format

  --json                                                                            show output in json format (disables
                                                                                    --verbose)

  --loglevel=(trace|debug|info|warn|error|fatal|TRACE|DEBUG|INFO|WARN|ERROR|FATAL)  [default: warn] logging level for
                                                                                    this command invocation

  --namespaces=namespaces                                                           comma separated list of dependent
                                                                                    package namespaces (without spaces)

  --verbose                                                                         show progress messages

  --zombie                                                                          show warnings for unused fields &
                                                                                    methods

EXAMPLES
  $ sfdx apexlink:check
  $ sfdx apexlink:check --verbose $HOME/myproject
  $ sfdx apexlink:check --zombie --namespaces ns1,ns2 $HOME/myproject
```

_See code: [src/commands/apexlink/check.ts](https://github.com/nawforce/apexlink/blob/v1.3.2/src/commands/apexlink/check.ts)_
<!-- commandsstop -->

[![Version](https://img.shields.io/npm/v/apexlink.svg)](https://npmjs.org/package/apexlink)
[![License](https://img.shields.io/npm/l/apexlink.svg)](https://github.com/nawforce/apexlink/blob/master/package.json)
[![Known Vulnerabilities](https://snyk.io/test/github/nawforce/apexlink/badge.svg)](https://snyk.io/test/github/nawforce/apexlink)
