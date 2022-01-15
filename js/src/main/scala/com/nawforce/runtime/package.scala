/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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
package com.nawforce

import java.nio.charset.StandardCharsets

/** Platform specific handling for Node execution.
  *
  * Contains a number of abstractions for handling the differences between JVM & Node execution. This is just the JVM
  * implementation, the node version of the same abstraction is not in this project. See [[com.nawforce.pkgforce]] for
  * the analysis code.
  */
package object runtime {
  type SourceBlob = Array[Byte]

  object SourceBlob {
    def apply(value: String): SourceBlob = {
      value.getBytes(StandardCharsets.UTF_8)
    }
  }
}
