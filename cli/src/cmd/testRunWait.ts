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

import CommandRunner, { CommandStatus } from "./commandRunner";

export default class TestRunWait {
  private testRunId: string;

  constructor(testRunId: string) {
    this.testRunId = testRunId;
  }

  public async execute(): Promise<CommandStatus> {
    return new Promise((resolve, reject) => {
      new CommandRunner("sfdx", [
        "force:apex:test:report",
        "--testrunid",
        this.testRunId,
        "--wait",
        "120",
        "--json"
      ])
        .execute()
        .then((status: CommandStatus) => {
          resolve(status);
        })
        .catch((status: CommandStatus) => {
          if (status.statusCode == 100) {
            // 100 is a success code as well
            resolve(status);
          } else if (status.stderr.search(/reason: URI too long/) !== -1) {
            // Just too many tests for report to handle
            resolve(status);
          } else if (status.stderr.search(/ENOTFOUND/) !== -1) {
            // It failed for unknown reasons, try again...
            this.execute().then(
              status => resolve(status),
              status => reject(status)
            );
          } else {
            reject(status);
          }
        });
    });
  }
}
