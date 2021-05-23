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

package com.nawforce.platform.dom;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class XmlNode {
	public XmlNode addChildElement(String name, String namespace, String prefix) {throw new java.lang.UnsupportedOperationException();}
	public XmlNode addCommentNode(String text) {throw new java.lang.UnsupportedOperationException();}
	public XmlNode addTextNode(String text) {throw new java.lang.UnsupportedOperationException();}
	public String getAttribute(String key, String keyNamespace) {throw new java.lang.UnsupportedOperationException();}
	public Integer getAttributeCount() {throw new java.lang.UnsupportedOperationException();}
	public String getAttributeKeyAt(Integer index) {throw new java.lang.UnsupportedOperationException();}
	public String getAttributeKeyNsAt(Integer index) {throw new java.lang.UnsupportedOperationException();}
	public String getAttributeValue(String key, String keyNamespace) {throw new java.lang.UnsupportedOperationException();}
	public String getAttributeValueNs(String key, String keyNamespace) {throw new java.lang.UnsupportedOperationException();}
	public XmlNode getChildElement(String name, String namespace) {throw new java.lang.UnsupportedOperationException();}
	public List<XmlNode> getChildElements() {throw new java.lang.UnsupportedOperationException();}
	public List<XmlNode> getChildren() {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public String getNamespace() {throw new java.lang.UnsupportedOperationException();}
	public String getNamespaceFor(String prefix) {throw new java.lang.UnsupportedOperationException();}
	public XmlNodeType getNodeType() {throw new java.lang.UnsupportedOperationException();}
	public XmlNode getParent() {throw new java.lang.UnsupportedOperationException();}
	public String getPrefixFor(String namespace) {throw new java.lang.UnsupportedOperationException();}
	public String getText() {throw new java.lang.UnsupportedOperationException();}
	public XmlNode insertBefore(Object newChild, Object refChild) {throw new java.lang.UnsupportedOperationException();}
	public Boolean removeAttribute(String key, String keyNamespace) {throw new java.lang.UnsupportedOperationException();}
	public Boolean removeChild(Object child) {throw new java.lang.UnsupportedOperationException();}
	public void setAttribute(String key, String value) {throw new java.lang.UnsupportedOperationException();}
	public void setAttributeNs(String key, String value, String keyNamespace, String valueNamespace) {throw new java.lang.UnsupportedOperationException();}
	public void setNamespace(String prefix, String namespace) {throw new java.lang.UnsupportedOperationException();}
}
