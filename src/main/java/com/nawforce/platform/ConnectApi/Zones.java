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

package com.nawforce.platform.ConnectApi;

import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Zones {
	public static Zone getZone(String communityId, String zoneId) {throw new java.lang.UnsupportedOperationException();}
	public static ZonePage getZones(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ZonePage getZones(String communityId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ZoneSearchPage searchInZone(String communityId, String zoneId, String q, ZoneSearchResultType filter) {throw new java.lang.UnsupportedOperationException();}
	public static ZoneSearchPage searchInZone(String communityId, String zoneId, String q, ZoneSearchResultType filter, String language) {throw new java.lang.UnsupportedOperationException();}
	public static ZoneSearchPage searchInZone(String communityId, String zoneId, String q, ZoneSearchResultType filter, String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchInZone(String communityId, String zoneId, String q, ZoneSearchResultType filter, ZoneSearchPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchInZone(String communityId, String zoneId, String q, ZoneSearchResultType filter, String language, ZoneSearchPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchInZone(String communityId, String zoneId, String q, ZoneSearchResultType filter, String pageParam, Integer pageSize, ZoneSearchPage result) {throw new java.lang.UnsupportedOperationException();}
}
