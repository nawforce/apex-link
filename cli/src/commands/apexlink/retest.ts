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
import { AnyJson, JsonMap, JsonArray } from "@salesforce/ts-types";
import GitStatus, { ChangedFile } from "../../cmd/gitStatus";
import { CommandStatus } from "../../cmd/commandRunner";
import ForceIgnore from "../../cmd/forceIgnore";
import * as path from "path";

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file. Messages from @salesforce/command, @salesforce/core,
// or any library that is using the messages framework can also be loaded this way.
const messages = Messages.loadMessages("apexlink", "retest");

const includedExtensions = [".cls", ".trigger"];

export default class ReTest extends SfdxCommand {
  public static description = messages.getMessage("commandDescription");

  public static examples = [
    `$ sfdx apexlink:retest`,
    `$ sfdx apexlink:retest --all --verbose`
  ];

  protected static flagsConfig = {
    all: flags.boolean({ description: "run all local tests" }),
    json: flags.boolean({
      description: "show output in json format (disables --verbose)"
    }),
    verbose: flags.builtin({ description: "show progress messages" })
  };

  protected static requiresUsername = false;
  protected static supportsDevhubUsername = false;
  protected static requiresProject = true;

  public async run(): Promise<AnyJson> {
    const projectConfig = await this.project.resolveProjectConfig();
    const forceIgnore = new ForceIgnore(this.project.getPath(), projectConfig);

    return new GitStatus()
      .execute()
      .then((changedFiles: ChangedFile[]) => {
        changedFiles
          .filter(fileStatus => {
            return (
              forceIgnore.isValid(fileStatus.path) &&
              includedExtensions.filter(ext => fileStatus.path.endsWith(ext))
                .length > 0
            );
          })
          .forEach(fileStatus => {
            console.log(fileStatus);
          });
        return {};
      })
      .catch((status: CommandStatus) => {
        if (status.abortError)
          throw new SfdxError(
            messages.getMessage("errorNoStatus", [status.abortError])
          );
        else
          throw new SfdxError(
            messages.getMessage("errorBadStatus", [status.statusCode])
          );
      });
  }
}
