package io.github.nahforce.apexlink

import org.junit.{Assert, Test}

class SampleTest {

  def sample(path : String) : Unit = {
    Assert.assertEquals(0, Link.link(Array(path)))
  }

  @Test def sample1 : Unit = {
    sample("samples/forcedotcom-enterprise-architecture/src")
  }

  @Test def sample2 : Unit = {
    sample("samples/forcedotcomlabs/chatter-game/src")
  }

  @Test def sample3 : Unit = {
    sample("samples/SalesforceFoundation/Cumulus/src")
  }

}
