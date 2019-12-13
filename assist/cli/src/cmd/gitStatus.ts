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

import * as path from "path";
import CommandRunner, {CommandStatus} from "./commandRunner";

export interface ChangedFile {
  status: string;
  path: string;
}

export default class GitStatus {
  public async execute(): Promise<ChangedFile[]> {
    const gitRoot = await this.getGitRoot();
    return new Promise((resolve, reject) => {
      new CommandRunner("git", ["status", "--porcelain", "-z", "."])
        .execute()
        .then((status: CommandStatus) => {
          resolve(this.parseFiles(gitRoot, status));
        })
        .catch((status: CommandStatus) => {
          reject(status);
        });
    });
  }

  private async getGitRoot(): Promise<string> {
    return new Promise((resolve, reject) => {
      new CommandRunner("git", ["rev-parse", "--show-toplevel"])
        .execute()
        .then((status: CommandStatus) => {
          resolve(status.stdout.split("\n")[0]);
        })
        .catch((status: CommandStatus) => {
          reject(status);
        });
    });
  }

  private parseFiles(gitRoot: string, status: CommandStatus): ChangedFile[] {
    const fileEntries = status.stdout.split("\0");
    if (fileEntries.length > 0) fileEntries.pop();
    return fileEntries.map(entry => {
      return {
        status: entry.substr(0, 1),
        path: path.join(gitRoot, entry.substr(3))
      };
    });
  }
}
