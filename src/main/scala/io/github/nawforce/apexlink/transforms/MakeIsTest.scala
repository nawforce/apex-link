/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package io.github.nawforce.apexlink.transforms

import io.github.nawforce.apexlink.cst._
import io.github.nawforce.apexlink.diff.FileChanger
import io.github.nawforce.apexlink.metadata.{ApexClass, SymbolReaderContext}

class MakeIsTest {

  def exec(ctx: SymbolReaderContext, fileChanger: FileChanger): Unit = {
    ctx.getClasses.values.foreach((apexClass: ApexClass) => {
      apexClass.methodDeclarations.foreach((method: MethodDeclaration) => {

        // Remove any testmethod modifiers
        val testMethods = method.modifiers.filter(m => MakeIsTest.isTestMethodModifier(m))
        testMethods.foreach(a => fileChanger.addChange(apexClass.location.filepath, a.start(), a.end() + 1, None))

        // If we had some but no @isTest add an @isTest
        if (testMethods.nonEmpty) {
          if (!method.modifiers.exists(m => MakeIsTest.isTestAnnotationModifier(m))) {
            fileChanger.addChange(apexClass.location.filepath, method.modifiers.head.start(), -1, Some("@isTest\n"))
          }
        }

      })
    })
  }
}

object MakeIsTest {
  def isTestAnnotationModifier(m: TypeModifier): Boolean = {
    m match {
      case am: AnnotationModifier => am.annotation.name == new QualifiedName("isTest" :: Nil)
      case _ => false
    }
  }

  def isTestMethodModifier(m: TypeModifier): Boolean = {
    m match {
      case _: TestMethodModifier => true
      case _ => false
    }
  }
}
