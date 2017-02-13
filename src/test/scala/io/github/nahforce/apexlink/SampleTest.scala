package io.github.nahforce.apexlink

import org.junit.{Assert, Test}

class SampleTest {

  def sample(path : String) : Unit = {
    Assert.assertEquals(0, Link.link(Array(path)))
  }

  @Test def sample1() : Unit = {
    sample("samples/forcedotcom-enterprise-architecture/src")
  }

  @Test def sample2() : Unit = {
    sample("samples/forcedotcomlabs/chatter-game/src")
  }

  @Test def sample3() : Unit = {
    sample("samples/SalesforceFoundation/Cumulus/src")
  }

  @Test def sample4() : Unit = {
    sample("samples/SalesforceFoundation/HEDAP/src")
  }

  @Test def sample5() : Unit = {
    sample("samples/SalesforceFoundation/CampaignTools/src")
  }

  @Test def sample6() : Unit = {
    sample("samples/SalesforceFoundation/Volunteers-for-Salesforce/src")
  }

  @Test def sample7() : Unit = {
    sample("samples/SalesforceFoundation/Relationships/src")
  }

  @Test def sample8() : Unit = {
    sample("samples/SalesforceFoundation/Households/src")
  }

  @Test def sample9() : Unit = {
    sample("samples/SalesforceFoundation/Recurring_Donations/src")
  }

  @Test def sample10() : Unit = {
    sample("samples/SalesforceFoundation/Contacts_and_Organizations/src")
  }

  @Test def sample11() : Unit = {
    sample("samples/SalesforceFoundation/Affiliations/src")
  }
}
