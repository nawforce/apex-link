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
package com.nawforce.apexlink.names

import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.pkgforce.names.{Names, TypeIdentifier}

object TypeIdentifiers {
  implicit class TypeIdentifierUtils(typeIdentifier: TypeIdentifier) {

    def intern: TypeIdentifier =
      TypeIdentifier(typeIdentifier.namespace.map(Names(_)), typeIdentifier.typeName.intern)

  }
}
