/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import { SfdxCommand, flags } from "@salesforce/command";
import { Messages, SfdxError } from "@salesforce/core";
import { AnyJson } from "@salesforce/ts-types";
import Server from "../../api/server";
import Org from "../../api/org";
import { InfoMessages, MessageWriter } from "../../api/messages";
import * as fs from "fs";

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file. Messages from @salesforce/command, @salesforce/core,
// or any library that is using the messages framework can also be loaded this way.
const messages = Messages.loadMessages("apexlink", "check");

export default class Check extends SfdxCommand {
  public static description = messages.getMessage("commandDescription");

  public static examples = [
    `$ sfdx apexlink:check`,
    `$ sfdx apexlink:check --verbose projects/myproject`,
    `$ sfdx apexlink:check --json myns=projects/base projects/extension`
  ];

  public static args = [
    {
      name: "directories",
      description:
        "list of directories to search for Apex class files, defaults to current directory\n" +
        "use [<namespace>=]directory for multiple packages, packages are loaded in first seen order"
    }
  ];

  protected static flagsConfig = {
    json: flags.boolean({
      description: "show output in json format (disables --verbose)"
    }),
    verbose: flags.builtin({ description: "show progress messages" })
  };

  protected static requiresUsername = false;
  protected static supportsDevhubUsername = false;
  protected static requiresProject = false;
  public static strict = false;

  public async run(): Promise<AnyJson> {
    let verbose = this.flags.verbose || false;
    let json = this.flags.json || false;

    let server = await Server.getInstance();
    server.setLoggingLevel(verbose && !json);

    const org = this.createOrg(server, this.directoryArgs());
    const results = org.issues();

    if (json) {
      return JSON.parse(results);
    } else {
      let writer = new MessageWriter();
      writer.writeMessages(JSON.parse(results) as InfoMessages);
      this.ux.log(writer.output());
      return {};
    }
  }

  private createOrg(server: Server, directories: [string, string][]): Org {
    const org = server.createOrg();
    const namespaces = new Set<string>();

    for (let directory of directories) {
      if (!namespaces.has(directory[0])) {
        namespaces.add(directory[0]);
        const pkg = org.addPackage(
          directory[0],
          directories
            .filter(d => {
              return d[0] == directory[0];
            })
            .map(d => d[1])
        );
        pkg.deployAll();
      }
    }

    return org;
  }

  private directoryArgs(): [string, string][] {
    const flagTypes = this.flagTypes();
    let directoryArgs = [];
    let skipArgs = 0;

    for (let arg of this.argv) {
      if (skipArgs > 0) {
        skipArgs -= 1;
      } else {
        skipArgs = flagTypes.get(arg);
        if (skipArgs == undefined) {
          if (arg.startsWith("--")) {
            throw new SfdxError(
              messages.getMessage("expectingDirectory", [arg])
            );
          }
          directoryArgs.push(arg);
          skipArgs = 0;
        }
      }
    }

    if (directoryArgs.length > 0) return this.collectNamespaces(directoryArgs);
    else return [["", process.cwd()]];
  }

  private collectNamespaces(directoryArgs: string[]): [string, string][] {
    let namespaceDirectories = [];

    for (let arg of directoryArgs) {
      let parts = arg.split("=");
      if (parts.length == 1) {
        this.checkDirectoryExists(arg);
        namespaceDirectories.push(["", arg]);
      } else if (parts.length == 2) {
        this.checkDirectoryExists(parts[1]);
        namespaceDirectories.push([parts[0], parts[1]]);
      } else {
        throw new SfdxError(messages.getMessage("badNamespace", [arg]));
      }
    }
    return namespaceDirectories;
  }

  private checkDirectoryExists(directory: string) {
    if (!fs.existsSync(directory) || !fs.lstatSync(directory).isDirectory()) {
      throw new SfdxError(messages.getMessage("errorNotDir", [directory]));
    }
  }

  private flagTypes(): Map<string, number> {
    let flagTypes = new Map<string, number>();
    flagTypes.set("--verbose", 0);
    flagTypes.set("--json", 0);
    flagTypes.set("--loglevel", 1);
    return flagTypes;
  }
}
