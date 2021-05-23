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

import com.nawforce.platform.System.Map;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class UserData {
  public UserData(String identifier, String firstName, String lastName, String fullName, String email, String link, String userName, String locale, String provider, String siteLoginUrl, Map<String,String> attributeMap)  {throw new java.lang.UnsupportedOperationException();}

  public String identifier;
  public String firstName;
  public String lastName;
  public String fullName;
  public String email;
  public String link;
  public String username;
  public String locale;
  public String provider;
  public String siteLoginUrl;
  public Map<String, String> attributeMap;
}
