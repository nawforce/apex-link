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

import com.nawforce.platform.System.Exception;
import com.nawforce.platform.System.HttpResponse;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class JWTBearerTokenExchange {
  public JWTBearerTokenExchange(String tokenEndpoint, JWS jws) {throw new java.lang.UnsupportedOperationException();}

  public Object clone() {throw new java.lang.UnsupportedOperationException();}
  public String getAccessToken() {throw new java.lang.UnsupportedOperationException();}
  public String getGrantType() {throw new java.lang.UnsupportedOperationException();}
  public HttpResponse getHttpResponse() {throw new java.lang.UnsupportedOperationException();}
  public JWS getJWS() {throw new java.lang.UnsupportedOperationException();}
  public String getTokenEndpoint() {throw new java.lang.UnsupportedOperationException();}
  public void setGrantType(String grantType) {throw new java.lang.UnsupportedOperationException();}
  public void setJWS(JWS jws) {throw new java.lang.UnsupportedOperationException();}
  public void setTokenEndpoint(String tokenEndpoint) {throw new java.lang.UnsupportedOperationException();}

  public static class JWTBearerTokenExchangeException extends Exception {
    public JWTBearerTokenExchangeException() {throw new java.lang.UnsupportedOperationException();}
    public JWTBearerTokenExchangeException(Exception param1) {throw new java.lang.UnsupportedOperationException();}
    public JWTBearerTokenExchangeException(String param1) {throw new java.lang.UnsupportedOperationException();}
    public JWTBearerTokenExchangeException(String param1, Exception param2) {throw new java.lang.UnsupportedOperationException();}
  }
}
