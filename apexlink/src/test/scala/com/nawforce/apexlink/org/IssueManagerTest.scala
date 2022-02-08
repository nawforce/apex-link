/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.org

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.diagnostics.{Issue, SYNTAX_CATEGORY}
import com.nawforce.pkgforce.path.{Location, PathLike}
import org.scalatest.funsuite.AnyFunSuite

class IssueManagerTest extends AnyFunSuite with TestHelper {

  test("Empty org") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)

        assert(org.issues.hasUpdatedIssues.isEmpty)
        assert(
          org.issues
            .issuesForFiles(paths = null, includeWarnings = true, maxIssuesPerFile = 100)
            .isEmpty
        )
      }
    }
  }

  test("Issue on loading") {
    withManualFlush {
      FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy")) { root: PathLike =>
        val org = createOrg(root)

        assert(org.issues.hasUpdatedIssues sameElements Array("/Dummy.cls"))
        org.issues.ignoreUpdatedIssuesInternal(root.join("Dummy.cls"))
        assert(org.issues.hasUpdatedIssues.isEmpty)

        val expectedIssue = Issue(
          root.join("Dummy.cls"),
          SYNTAX_CATEGORY,
          Location(1, 18),
          "mismatched input '<EOF>' expecting {'extends', 'implements', '{'}"
        )

        val dummyPath = root.join("Dummy.cls")
        assert(org.issues.issuesForFileInternal(dummyPath) sameElements Array(expectedIssue))
        assert(org.issues.issuesForFileLocationInternal(dummyPath, Location(1, 17)).isEmpty)
        assert(org.issues.issuesForFileLocationInternal(dummyPath, Location(1, 19)).isEmpty)
        assert(
          org.issues.issuesForFileLocationInternal(dummyPath, Location(1, 18)) sameElements Array(
            expectedIssue
          )
        )
        assert(
          org.issues.issuesForFileLocationInternal(
            dummyPath,
            Location(1, 17, 1, 19)
          ) sameElements Array(expectedIssue)
        )
        assert(
          org.issues.issuesForFileLocationInternal(
            dummyPath,
            Location(1, 18, 2, 0)
          ) sameElements Array(expectedIssue)
        )

        assert(
          org.issues.issuesForFilesInternal(
            paths = null,
            includeWarnings = true,
            maxIssuesPerFile = 100
          ) sameElements
            Array(expectedIssue)
        )
        assert(
          org.issues.issuesForFilesInternal(
            Array(dummyPath),
            includeWarnings = true,
            maxIssuesPerFile = 100
          ) sameElements
            Array(expectedIssue)
        )
        assert(
          org.issues.issuesForFilesInternal(
            Array(dummyPath),
            includeWarnings = false,
            maxIssuesPerFile = 0
          ) sameElements
            Array(expectedIssue)
        )
      }
    }
  }

  test("Issue from refresh") {
    withManualFlush {
      FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
        val org = createOrg(root)

        assert(org.issues.hasUpdatedIssues.isEmpty)

        val path = root.join("/Dummy.cls")
        path.write("public class Dummy")
        org.unmanaged.refresh(path)
        org.flush()

        assert(org.issues.hasUpdatedIssues sameElements Array("/Dummy.cls"))

        val expectedIssues = Array(
          Issue(
            root.join("Dummy.cls"),
            SYNTAX_CATEGORY,
            Location(1, 18),
            "mismatched input '<EOF>' expecting {'extends', 'implements', '{'}"
          )
        )

        val dummyPath = root.join("Dummy.cls")
        assert(org.issues.issuesForFileInternal(dummyPath) sameElements expectedIssues)
        assert(org.issues.hasUpdatedIssues.isEmpty)
        assert(org.issues.issuesForFileLocationInternal(dummyPath, Location(1, 17)).isEmpty)
        assert(org.issues.issuesForFileLocationInternal(dummyPath, Location(1, 19)).isEmpty)
        assert(
          org.issues.issuesForFileLocationInternal(dummyPath, Location(1, 18)) sameElements Array(
            expectedIssues.head
          )
        )
        assert(
          org.issues.issuesForFileLocationInternal(
            dummyPath,
            Location(1, 17, 1, 19)
          ) sameElements Array(expectedIssues.head)
        )
        assert(
          org.issues.issuesForFileLocationInternal(
            dummyPath,
            Location(1, 18, 2, 0)
          ) sameElements Array(expectedIssues.head)
        )

        assert(
          org.issues.issuesForFilesInternal(
            paths = null,
            includeWarnings = true,
            maxIssuesPerFile = 100
          ) sameElements
            expectedIssues
        )
        assert(
          org.issues.issuesForFilesInternal(
            Array(dummyPath),
            includeWarnings = true,
            maxIssuesPerFile = 100
          ) sameElements
            expectedIssues
        )
        assert(
          org.issues.issuesForFilesInternal(
            Array(dummyPath),
            includeWarnings = true,
            maxIssuesPerFile = 1
          ) sameElements
            Array(expectedIssues.head)
        )
        assert(
          org.issues.issuesForFilesInternal(
            Array(dummyPath),
            includeWarnings = false,
            maxIssuesPerFile = 0
          ) sameElements
            Array(expectedIssues.head)
        )
      }
    }
  }

  test("Multiple issues") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Dummy.cls" -> "public class Dummy {String a = 1; void func() {Integer a;} }")
      ) { root: PathLike =>
        val org = createOrg(root)

        assert(org.issues.hasUpdatedIssues sameElements Array("/Dummy.cls"))

        assert(org.issues.issuesForFileInternal(root.join("Dummy.cls")).length == 2)
        assert(
          org.issues
            .issuesForFiles(paths = null, includeWarnings = true, maxIssuesPerFile = 100)
            .length == 2
        )
        assert(
          org.issues
            .issuesForFiles(paths = null, includeWarnings = false, maxIssuesPerFile = 100)
            .length == 1
        )
        assert(
          org.issues
            .issuesForFiles(paths = null, includeWarnings = true, maxIssuesPerFile = 1)
            .length == 1
        )
        assert(
          org.issues
            .issuesForFiles(paths = null, includeWarnings = true, maxIssuesPerFile = 2)
            .length == 2
        )
        assert(
          org.issues
            .issuesForFiles(paths = null, includeWarnings = false, maxIssuesPerFile = 2)
            .length == 1
        )
      }
    }
  }
}
