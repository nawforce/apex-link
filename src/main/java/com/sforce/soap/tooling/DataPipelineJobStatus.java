package com.sforce.soap.tooling;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public enum DataPipelineJobStatus {


  
	/**
	 * Enumeration  : Scheduled
	 */
	Scheduled("Scheduled"),

  
	/**
	 * Enumeration  : Running
	 */
	Running("Running"),

  
	/**
	 * Enumeration  : Success
	 */
	Success("Success"),

  
	/**
	 * Enumeration  : Failed
	 */
	Failed("Failed"),

  
	/**
	 * Enumeration  : Killed
	 */
	Killed("Killed"),

  
	/**
	 * Enumeration  : Load
	 */
	Load("Load"),

  
	/**
	 * Enumeration  : Process
	 */
	Process("Process"),

  
	/**
	 * Enumeration  : Store
	 */
	Store("Store"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (DataPipelineJobStatus e : EnumSet.allOf(DataPipelineJobStatus.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private DataPipelineJobStatus(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
