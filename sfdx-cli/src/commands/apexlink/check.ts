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
import { Messages, SfdxError} from '@salesforce/core';
import { AnyJson } from '@salesforce/ts-types';
import LocateJavaHome from 'locate-java-home'
import { IJavaHomeInfo } from 'locate-java-home/js/es5/lib/interfaces';
import * as fs from 'fs'
import * as path from 'path'
import * as crossspawn from 'cross-spawn';

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file. Messages from @salesforce/command, @salesforce/core,
// or any library that is using the messages framework can also be loaded this way.
const messages = Messages.loadMessages('apexlink', 'check');

export default class Check extends SfdxCommand {

  public static description = messages.getMessage('commandDescription');

  public static examples = [
  `$ sfdx apexlink:check`,
  `$ sfdx apexlink:check --verbose $HOME/myproject`,
  `$ sfdx apexlink:check --zombie --namespaces ns1,ns2 $HOME/myproject`
  ];

  public static args = [
    {name: 'directory', description: 'directory to search for metadata files, defaults to current directory'}
  ];

  protected static flagsConfig = {
    zombie: flags.boolean({description: 'show warnings for unused fields & methods'}),
    depends: flags.boolean({description: 'output map of type dependencies rather than issues, CSV or JSON format'}),
    namespaces: flags.string({description: 'comma separated list of dependent package namespaces (without spaces)'}),
    verbose: flags.builtin({description: 'show progress messages'}),
    json: flags.boolean({description: 'show output in json format (disables --verbose)'})
  };

  protected static requiresUsername = false;
  protected static supportsDevhubUsername = false;
  protected static requiresProject = false;

  public async run(): Promise<AnyJson> {

    const jarFile = path.join(__dirname, '..', '..', '..', 'jars', 'apexlink-1.1.0.jar')
    if (!fs.existsSync(jarFile) || !fs.lstatSync(jarFile).isFile()) {
      throw new SfdxError(messages.getMessage('errorNoJarFile', [jarFile]));
    }

    const jvms = await this.getJavaHome();
    if (jvms.length == 0) {
      throw new SfdxError(messages.getMessage('errorNoJVM'));
    }

    const javaExecutable = jvms[0].executables.java
    if (jvms.length > 1 && this.flags.verbose) {
      this.ux.log(messages.getMessage('errorManyJVM', [javaExecutable]));
    }

    const directory = this.args.directory || process.cwd()
    if (!fs.existsSync(directory) || !fs.lstatSync(directory).isDirectory()) {
      throw new SfdxError(messages.getMessage('errorNotDir', [directory]));
    }

    const namespaces: String[] = (this.flags.namespaces || "").split(",")

    let execArgs = ['-Dfile.encoding=UTF-8', '-jar', jarFile]
    if (this.flags.verbose) execArgs.push('-verbose')
    if (this.flags.json) execArgs.push('-json')
    if (this.flags.zombie) execArgs.push('-zombie')
    if (this.flags.depends) execArgs.push('-depends')
    namespaces.forEach(namespace => {execArgs.push(namespace+"=")})
    execArgs.push(directory)
    return this.execute(javaExecutable, execArgs, this.flags.json)
  }

  private getJavaHome(): Promise<IJavaHomeInfo[]> {
    return new Promise<IJavaHomeInfo[]>(function(resolve, reject) {
      LocateJavaHome({version: ">=1.8", mustBe64Bit: true}, function(error, javaHomes) {
        if (error != null) reject(error)
        else resolve(javaHomes)
      });
    });
  }

  private execute(javaCmd: string, args: string[], json: boolean) {
    let jsonData = ''
    return new Promise<AnyJson>(function(resolve, reject) {
      try {
          const child = crossspawn(javaCmd, args, json ? {} : {stdio: 'inherit'});
          if (json) {
            child.stdout.on('data', (data) => {
              jsonData += data.toString('utf8')
            });
          }
          child.on('close', (code) => {
            process.exitCode = code
            if (json)
              resolve(JSON.parse(jsonData))
            else
              resolve({})
          });
      } catch (e) {
          reject(e)
      }
    })
  }
}
