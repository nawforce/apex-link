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

import * as java from "java";
import Package from "./package";
import TypeInfo from "./typeInfo";

interface DependencyInfo {
  name: string;
  dependencies: string[];
}

export default class Org {
  private org: any;
  private unmanaged: Package;

  constructor() {
    this.org = java.newInstanceSync("com.nawforce.api.Org");
  }

  public issues(): string {
    return this.org.issuesAsJSONSync();
  }

  public unmanagedPackage(): Package {
    if (this.unmanaged === null) {
      this.unmanaged = new Package(
        this.org.getUnmanagedPackageSync()
      )
    }
    return this.unmanaged;
  }

  public addPackage(namespace: string, directories: string[], basePackages: string[]): Package {
    return new Package(
      this.org.addPackageSync(
        namespace,
        java.newArray("java.lang.String", directories),
        java.newArray("java.lang.String", basePackages)
      )
    );
  }

  public getApexDependencies(): DependencyInfo[] {
    const results = [];
    const names = this.getApexTypeNames();
    for (const name of names) {
      const ti = this.getTypeInfo(name);
      if (ti != null) {
        results.push({ name, dependencies: ti.getDependsOn() });
      }
    }
    return results;
  }

  private getApexTypeNames(): string[] {
    const nameList = this.org.getApexTypeNamesSync();
    const results = [];
    for (let i = 0; i < nameList.sizeSync(); i++) {
      results.push(nameList.getSync(i));
    }
    return results;
  }

  private getTypeInfo(name: string): TypeInfo {
    const ti = this.org.getTypeInfoSync(name);
    if (ti != null) return new TypeInfo(ti);
    else return null;
  }
}
