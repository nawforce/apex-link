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

import { SfdxCommand, flags } from '@salesforce/command';
import { Messages, SfdxError } from '@salesforce/core';
import { AnyJson } from '@salesforce/ts-types';
import Server from '../../api/server';
import {InfoMessages, MessageWriter} from '../../api/messages';
import * as fs from 'fs'

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file. Messages from @salesforce/command, @salesforce/core,
// or any library that is using the messages framework can also be loaded this way.
const messages = Messages.loadMessages('apexlink', 'check');

export default class Check extends SfdxCommand {

  public static description = messages.getMessage('commandDescription');

  public static examples = [
    `$ sfdx apexlink:check`,
    `$ sfdx apexlink:check --verbose projects/myproject`
  ];

  public static args = [
    { name: 'directory', description: 'directory to search for Apex class files, defaults to current directory' }
  ];

  protected static flagsConfig = {
    json: flags.boolean({ description: 'show output in json format (disables --verbose)' }),
    verbose: flags.builtin({ description: 'show progress messages' })
  };

  protected static requiresUsername = false;
  protected static supportsDevhubUsername = false;
  protected static requiresProject = false;

  public async run(): Promise<AnyJson> {
    let verbose = this.flags.verbose || false
    let json = this.flags.json || false

    const directory = this.args.directory || process.cwd()
    if (!fs.existsSync(directory) || !fs.lstatSync(directory).isDirectory()) {
      throw new SfdxError(messages.getMessage('errorNotDir', [directory]));
    }

    let server = await Server.getInstance();
    server.setLoggingLevel(verbose && !json)

    const org = server.createOrg();
    const pkg = org.addPackage("", [directory])
    const results = pkg.deployAll()

    if (json) {
      return JSON.parse(results)
    } else {
      let writer = new MessageWriter();
      writer.writeMessages(JSON.parse(results) as InfoMessages)
      this.ux.log(writer.output())
      return {}
    }
  }
}
