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

import * as fs from "fs";
import * as tmp from "tmp";
import * as crossspawn from "cross-spawn";

export interface CommandStatus {
  abortError: any;
  statusCode: number;
  stdout: string;
  stderr: string;
}

export default class CommandRunner {
  private cwd: string;
  private cmd: string;
  private args: Array<String>;

  public constructor(cmd: string, args: Array<String>, cwd?: string) {
    this.cmd = cmd;
    this.args = args;
    this.cwd = cwd ? cwd : process.cwd();
  }

  public async execute(): Promise<CommandStatus> {
    const stdoutFile = tmp.tmpNameSync();
    const stderrFile = tmp.tmpNameSync();
    const captureOutput = (abortError:any, statusCode: number): CommandStatus => {
      return {
        abortError: abortError,
        statusCode: statusCode,
        stdout: fs.readFileSync(stdoutFile, "utf-8"),
        stderr: fs.readFileSync(stderrFile, "utf-8")
      };
    }

    return new Promise((resolve, reject) => {
      const child = crossspawn.spawn(this.cmd, this.args, {
        cwd: this.cwd,
        stdio: [
          "ignore",
          fs.openSync(stdoutFile, "a"),
          fs.openSync(stderrFile, "a")
        ]
      });
      child.on("error", (abortError: any) => {
        reject(captureOutput(abortError, null))
      })
      child.on("close", (statusCode: number) => {
        if (statusCode == 0) {
          resolve(captureOutput(null, statusCode));
        } else {
          reject(captureOutput(null, statusCode));
        }
      });
    });
  }
}
