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

package com.nawforce.platform.Component;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.PageReference;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Flow {
  public static class Interview {
    public Boolean AllowShowPause;
    public String ButtonLocation;
    public String ButtonStyle;
    public PageReference FinishLocation;
    public String Id;
    public com.nawforce.platform.Flow.Interview Interview;
    public String Name;
    public String PausedInterviewId;
    public Boolean Rendered;
    public Object Rerender;
    public Boolean ShowHelp;
  }
}
