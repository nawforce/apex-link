apexlink
========

[![Version](https://img.shields.io/npm/v/apexlink.svg)](https://npmjs.org/package/apexlink)
[![License](https://img.shields.io/npm/l/apexlink.svg)](https://github.com/nawforce/apexlink/blob/master/package.json)
[![Known Vulnerabilities](https://snyk.io/test/github/nawforce/apexlink/badge.svg)](https://snyk.io/test/github/nawforce/apexlink)

<!-- toc -->

<!-- tocstop -->
<!-- install -->
<!-- usage -->
```sh-session
$ npm install -g apexlink
$ sfdx COMMAND
running command...
$ sfdx (-v|--version|version)
apexlink/0.2.0 darwin-x64 node-v8.14.0
$ sfdx --help [COMMAND]
USAGE
  $ sfdx COMMAND
...
```
<!-- usagestop -->
<!-- commands -->
* [`sfdx apexlink:check [--verbose] [--json] [--loglevel trace|debug|info|warn|error|fatal]`](#sfdx-apexlinkcheck---verbose---json---loglevel-tracedebuginfowarnerrorfatal)

## `sfdx apexlink:check [--verbose] [--json] [--loglevel trace|debug|info|warn|error|fatal]`

Validate Apex code in current or passed directories

```
USAGE
  $ sfdx apexlink:check [--verbose] [--json] [--loglevel trace|debug|info|warn|error|fatal]

ARGUMENTS
  DIRECTORY  directory to search for Apex class files, defaults to current directory

OPTIONS
  --json                                          show output in json format (disables --verbose)
  --loglevel=(trace|debug|info|warn|error|fatal)  [default: warn] logging level for this command invocation
  --verbose                                       show progress messages

EXAMPLES
  $ sfdx apexlink:check
  $ sfdx apexlink:check -verbose $HOME/myproject
```

_See code: [src/commands/apexlink/check.ts](https://github.com/nawforce/apexlink/blob/v0.2.0/src/commands/apexlink/check.ts)_
<!-- commandsstop -->
