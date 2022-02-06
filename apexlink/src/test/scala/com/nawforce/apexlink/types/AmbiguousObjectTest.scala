/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.types

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class AmbiguousObjectTest extends AnyFunSuite with TestHelper {
  test("BusinessHours field reference") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectField a = BusinessHours.FridayEndTime;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("BusinessHours").get
        )
      )
    }
  }

  test("Site field reference") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectField a = Site.AnalyticsTrackingCode;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Site").get
        )
      )
    }
  }

  test("BusinessHours static method call") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Datetime a = BusinessHours.add(null, null, null);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("Site static method call") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Id a = Site.getSiteId();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("Location static method call") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Double a = Location.getDistance(null, null, null);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("Approval static method call") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Boolean a = Approval.isLocked((Id)null);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("BusinessHours SObjectType") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectType a = BusinessHours.SObjectType;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("BusinessHours").get
        )
      )
    }
  }

  test("Site SObjectType") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectType a = Site.SObjectType;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Site").get
        )
      )
    }
  }

  test("Location SObjectType") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectType a = Location.SObjectType;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Dummy.cls")) ==
          "Missing: line 1 at 38-58: Unknown field or type 'SObjectType' on 'System.Location'\n"
      )
    }
  }

  test("Approval SObjectType") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectType a = Approval.SObjectType;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Dummy.cls")) ==
          "Missing: line 1 at 38-58: Unknown field or type 'SObjectType' on 'System.Approval'\n"
      )
    }
  }

  test("Address SObjectType") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectType a = Address.SObjectType;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Dummy.cls")) ==
          "Missing: line 1 at 38-57: Unknown field or type 'SObjectType' on 'System.Address'\n"
      )
    }
  }

  test("BusinessHours Id field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {BusinessHours a; System.debug(a.Id);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("BusinessHours").get
        )
      )
    }
  }

  test("Site Id field") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Site a; System.debug(a.Id);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Site").get
        )
      )
    }
  }

  test("Location instance method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Location a; System.debug(a.getLatitude());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("Address instance method") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Address a; System.debug(a.getCity());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }
}
