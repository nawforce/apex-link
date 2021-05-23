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
public enum InvocationContext {
  ASSET_TOKEN,
  OAUTH1,
  OAUTH2_JWT_BEARER_TOKEN,
  OAUTH2_SAML_ASSERTION,
  OAUTH2_SAML_BEARER_ASSERTION,
  OAUTH2_USERNAME_PASSWORD,
  OAUTH2_USER_AGENT_ID_TOKEN,
  OAUTH2_USER_AGENT_TOKEN,
  OAUTH2_WEB_SERVER,
  OPENIDCONNECT,
  REFRESH_TOKEN,
  SAML_ASSERTION,
  UNKNOWN,
  USERID_ENDPOINT
}
