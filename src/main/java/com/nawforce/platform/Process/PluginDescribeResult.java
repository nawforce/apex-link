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

package com.nawforce.platform.Process;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class PluginDescribeResult {
	public enum ParameterType
	{
		BOOLEAN,
		DATE,
		DATETIME,
		DECIMAL,
		DOUBLE,
		FLOAT,
		ID,
		INTEGER,
		LONG,
		STRING
	}

  public static class InputParameter {
    public String description;
    public String name;
    public PluginDescribeResult.ParameterType parameterType;
    public Boolean required;

    public InputParameter(String name, PluginDescribeResult.ParameterType parameterType, Boolean required) {throw new java.lang.UnsupportedOperationException();}
    public InputParameter(String name, String description, PluginDescribeResult.ParameterType parameterType, Boolean required) {throw new java.lang.UnsupportedOperationException();}
  }

  public static class OutputParameter {
    public String description;
    public String name;
    public PluginDescribeResult.ParameterType parameterType;

    public OutputParameter(String name, PluginDescribeResult.ParameterType parameterType) {throw new java.lang.UnsupportedOperationException();}
    public OutputParameter(String name, String description, PluginDescribeResult.ParameterType parameterType) {throw new java.lang.UnsupportedOperationException();}
  }

	public String description;
	public List<InputParameter> inputParameters;
	public String name;
	public List<OutputParameter> outputParameters;
	public String tag;

	public PluginDescribeResult() {throw new java.lang.UnsupportedOperationException();}
}
