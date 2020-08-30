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
package com.nawforce.common.cmds

import com.nawforce.common.api.{Org, ServerOps}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.types.apex.SummaryDeclaration

object ViewBench {

  def main(args: Array[String]): Unit = {
    // Double run check to get cached org
    runCheck(args)
    val org = runCheck(args).asInstanceOf[OrgImpl]

    org.packagesByNamespace.foreach(pkgPair => {
      ServerOps.debugTime(s"Checking namespace ${pkgPair._1.getOrElse("unmanaged")}") {
        pkgPair._2.getTypes.foreach {
          case sd: SummaryDeclaration =>
            val viewInfo = pkgPair._2.getViewOfType(sd.path, None)
            if (!viewInfo.hasType || !viewInfo.diagnostics.forall(_.category=="Warning")) {
              println(s"Problem found for ${sd.typeName}")
              System.exit(-1)
            }
          case _ => ()
        }
      }
    })
  }

  private def runCheck(args: Array[String]): Org = {
    val org = Org.newOrg()
    val status = Check.main("ViewBench", args, org)
    if (status != 0)
      System.exit(status)
    org
  }
}
