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

import { JsonArray, JsonMap } from "@salesforce/ts-types";
import * as fs from "fs";
import * as GitIgnoreParser from "gitignore-parser";
import * as path from "path";

const excludedFiles = ["package2-descriptor.json", "package2-manifest.json"];

export default class ForceIgnore {
  private projectPath: string;
  private projectConfig: JsonMap;
  private packageDirectories: string[];
  private parser: GitIgnoreParser = null;

  constructor(projectPath: string, projectConfig: JsonMap) {
    this.projectPath = projectPath;
    this.projectConfig = projectConfig;
    this.packageDirectories = this.getPackageDirectories();
    const forceIgnoreFile = path.join(this.projectPath, ".forceignore");
    if (fs.existsSync(forceIgnoreFile)) {
      const contents = fs.readFileSync(forceIgnoreFile, "utf-8");
      this.parser = GitIgnoreParser.compile(this.normaliseContent(contents));
    }
  }

  public exists(): boolean {
    return this.parser != null;
  }

  public isValid(fullPath: string): boolean {
    const dirname = path.dirname(fullPath);
    const basename = path.basename(fullPath);
    if (basename.startsWith(".") || basename.startsWith(".dot")) return false;
    if (
      this.packageDirectories.filter(packageDirectory => {
        return dirname.startsWith(packageDirectory);
      }).length === 0
    ) {
      return false;
    }
    if (excludedFiles.includes(basename)) return false;
    return this.accepts(fullPath);
  }

  private accepts(fullPath: string): boolean {
    if (this.parser) {
      return this.parser.accepts(path.relative(this.projectPath, fullPath));
    } else {
      return true;
    }
  }

  private normaliseContent(content: string): string {
    return content
      .split("\n")
      .map(line => line.trim())
      .map(line => line.replace(/[\\\/]/g, path.sep))
      .map(line => line.replace(/^\\/, ""))
      .join("\n");
  }

  private getPackageDirectories(): string[] {
    if (this.packageDirectories) return this.packageDirectories;

    const packageDirectoryEntries: JsonArray = (this.projectConfig[
      "packageDirectories"
    ] || []) as JsonArray;
    this.packageDirectories = packageDirectoryEntries.map(packageDirectory => {
      return path.join(this.projectPath, packageDirectory["path"]);
    });
    return this.packageDirectories;
  }
}
