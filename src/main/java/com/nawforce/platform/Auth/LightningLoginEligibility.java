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

package com.nawforce.platform.Auth;

@SuppressWarnings("unused")
public enum LightningLoginEligibility {
  ELIGIBLE,
  ORG_AUTHENTICATOR_NOT_ENABLED,
  ORG_PREF_NOT_ENABLED,
  USER_AUTHENTICATOR_NOT_CONNECTED,
  USER_NOT_ALLOWED,
  USER_NOT_ENROLLED,
  USER_PERM_NOT_ENABLED
}
