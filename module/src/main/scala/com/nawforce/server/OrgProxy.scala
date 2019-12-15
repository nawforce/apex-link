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
package com.nawforce.server

import com.nawforce.imports.Java

import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.Any

class OrgProxy(proxy: js.Dynamic) {

  lazy val unmanagedPackage: PackageProxy = {
    new PackageProxy(Java.callMethodSync(proxy, "getUnmanagedPackage"))
  }

  def issues(warnings: Boolean, zombies: Boolean): String = {
    Java.callMethodSync(proxy, "issuesAsJSON", warnings, zombies).asInstanceOf[String]
  }

  def addPackage(namespace: String, directories: Array[String], basePackages: Array[String]): PackageProxy = {
    addPackage(namespace, directories.toJSArray, basePackages.toJSArray)
  }

  def addPackage(namespace: String, directories: js.Array[String], basePackages: js.Array[String]): PackageProxy = {
    new PackageProxy(
      Java.callMethodSync(proxy, "addPackage", namespace,
        Java.newArray("java.lang.String", directories.map(Any.fromString)),
        Java.newArray("java.lang.String", basePackages.map(Any.fromString)),
      )
    )
  }

  /*
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

   */

}

object OrgProxy {
  def apply(): OrgProxy = {
    new OrgProxy(Java.newInstanceSync("com.nawforce.api.Org"))
  }
}
