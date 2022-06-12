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
package com.nawforce.apexparser

import com.nawforce.runtime.parsers.antlr.CharStream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("apex-parser", "CaseInsensitiveInputStream")
class CaseInsensitiveInputStream(src: CharStream) extends CharStream {
}
