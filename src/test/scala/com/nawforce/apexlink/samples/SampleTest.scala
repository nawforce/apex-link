/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
 */
package com.nawforce.apexlink.samples

import com.nawforce.apexlink.TestHelper
import com.nawforce.apexlink.api.IssueOptions
import com.nawforce.pkgforce.path.PathFactory
import org.scalatest.funsuite.AnyFunSuite

class SampleTest extends AnyFunSuite with TestHelper {

  private def sample(path: String): Unit = {
    withManualFlush {
      val org = createOrg(PathFactory(path))
      val options = new IssueOptions
      options.includeWarnings = false
      val issues = org.getIssues(options)
      if (issues.nonEmpty) {
        println(issues)
        assert(false)
      }
    }
  }

  test("forcedotcom-enterprise-architecture") {
    sample("samples/forcedotcom-enterprise-architecture/src")
  }

  test("chatter=game") {
    sample("samples/forcedotcomlabs/chatter-game/src")
  }

  test("Cumulus") {
    sample("samples/SalesforceFoundation/Cumulus")
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
}
