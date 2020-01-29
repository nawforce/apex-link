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
package com.nawforce.common.api

import org.scalatest.funsuite.AnyFunSuite

class SampleTest extends AnyFunSuite {

  private val npExternalNamespaces = Set("npe01", "npo02", "npe03", "npe4", "npe5")

  private def sample(path: String, namespace: String = "", externalNamespaces: Set[String] = Set(),
                     expectedCount: Int = 0): Unit = {
    val org = new Org()
    externalNamespaces.foreach(ens => org.newPackage(ens, Array(), Array()))
    org.newPackage(namespace, Array[String](path), externalNamespaces.toArray)

    if (org.issues.logCount != expectedCount) {
      org.issues.dumpMessages(false)
      assert(false)
    }
  }

  test("forcedotcom-enterprise-architecture") {
    sample("samples/forcedotcom-enterprise-architecture/src")
  }

  test("chatter=game") {
    sample("samples/forcedotcomlabs/chatter-game/src")
  }

  test("Cumulus") {
    sample("samples/SalesforceFoundation/Cumulus/src", namespace = "", npExternalNamespaces)
  }

  test("HEDAP") {
    sample("samples/SalesforceFoundation/HEDAP/src")
  }

  test("CampaignTools") {
    sample("samples/SalesforceFoundation/CampaignTools/src")
  }

  /* TODO: Re-enable after rollup fix
  test("Volunteers-for-Salesforce") {
    sample("samples/SalesforceFoundation/Volunteers-for-Salesforce/src")
  }*/

  test("Relationships") {
    sample("samples/SalesforceFoundation/Relationships/src")
  }

  test("Households") {
    sample("samples/SalesforceFoundation/Households/src")
  }

  test("Recurring_Donations") {
    sample("samples/SalesforceFoundation/Recurring_Donations/src")
  }

  test("Contacts_and_Organizations") {
    sample("samples/SalesforceFoundation/Contacts_and_Organizations/src")
  }

  test("Affiliations") {
    sample("samples/SalesforceFoundation/Affiliations/src")
  }

  test("forcedotcom-enterprise-architecture packaged") {
    sample("samples/forcedotcom-enterprise-architecture/src", "namespace")
  }

  test("chatter=game packaged") {
    sample("samples/forcedotcomlabs/chatter-game/src", "namespace")
  }

  test("Cumulus packaged") {
    sample("samples/SalesforceFoundation/Cumulus/src", "namespace", npExternalNamespaces)
  }

  test("HEDAP packaged") {
    sample("samples/SalesforceFoundation/HEDAP/src", "namespace")
  }

  test("CampaignTools packaged") {
    sample("samples/SalesforceFoundation/CampaignTools/src", "namespace")
  }

  /* TODO: Re-enable after rollup fix
  test("Volunteers-for-Salesforce packaged") {
    sample("samples/SalesforceFoundation/Volunteers-for-Salesforce/src", "namespace")
  } */

  test("Relationships packaged") {
    sample("samples/SalesforceFoundation/Relationships/src", "namespace")
  }

  test("Households packaged") {
    sample("samples/SalesforceFoundation/Households/src", "namespace")
  }

  test("Recurring_Donations packaged") {
    sample("samples/SalesforceFoundation/Recurring_Donations/src", "namespace")
  }

  test("Contacts_and_Organizations packaged") {
    sample("samples/SalesforceFoundation/Contacts_and_Organizations/src", "namespace")
  }

  test("Affiliations packaged") {
    sample("samples/SalesforceFoundation/Affiliations/src", "namespace")
  }
}
