package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class PathAssistant extends com.sforce.soap.tooling.metadata.Metadata {

    /**
     * Constructor
     */
    public PathAssistant() {}

    /* Cache the typeInfo instead of declaring static fields throughout*/
    private transient java.util.Map<String, com.sforce.ws.bind.TypeInfo> typeInfoCache = new java.util.HashMap<String, com.sforce.ws.bind.TypeInfo>();
    private com.sforce.ws.bind.TypeInfo _lookupTypeInfo(String fieldName, String namespace, String name, String typeNS, String type, int minOcc, int maxOcc, boolean elementForm) {
      com.sforce.ws.bind.TypeInfo typeInfo = typeInfoCache.get(fieldName);
      if (typeInfo == null) {
        typeInfo = new com.sforce.ws.bind.TypeInfo(namespace, name, typeNS, type, minOcc, maxOcc, elementForm);
        typeInfoCache.put(fieldName, typeInfo);
      }
      return typeInfo;
    }

    /**
     * element : active of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean active__is_set = false;

    private boolean active;

    public boolean getActive() {
      return active;
    }

    public boolean isActive() {
      return active;
    }

    public void setActive(boolean active) {
      this.active = active;
      active__is_set = true;
    }

    protected void setActive(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("active", "urn:metadata.tooling.soap.sforce.com","active","http://www.w3.org/2001/XMLSchema","boolean",1,1,true))) {
        setActive(__typeMapper.readBoolean(__in, _lookupTypeInfo("active", "urn:metadata.tooling.soap.sforce.com","active","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), boolean.class));
      }
    }

    private void writeFieldActive(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("active", "urn:metadata.tooling.soap.sforce.com","active","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), active, active__is_set);
    }

    /**
     * element : entityName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean entityName__is_set = false;

    private java.lang.String entityName;

    public java.lang.String getEntityName() {
      return entityName;
    }

    public void setEntityName(java.lang.String entityName) {
      this.entityName = entityName;
      entityName__is_set = true;
    }

    protected void setEntityName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("entityName", "urn:metadata.tooling.soap.sforce.com","entityName","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setEntityName(__typeMapper.readString(__in, _lookupTypeInfo("entityName", "urn:metadata.tooling.soap.sforce.com","entityName","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldEntityName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("entityName", "urn:metadata.tooling.soap.sforce.com","entityName","http://www.w3.org/2001/XMLSchema","string",1,1,true), entityName, entityName__is_set);
    }

    /**
     * element : fieldName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean fieldName__is_set = false;

    private java.lang.String fieldName;

    public java.lang.String getFieldName() {
      return fieldName;
    }

    public void setFieldName(java.lang.String fieldName) {
      this.fieldName = fieldName;
      fieldName__is_set = true;
    }

    protected void setFieldName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("fieldName", "urn:metadata.tooling.soap.sforce.com","fieldName","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setFieldName(__typeMapper.readString(__in, _lookupTypeInfo("fieldName", "urn:metadata.tooling.soap.sforce.com","fieldName","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldFieldName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("fieldName", "urn:metadata.tooling.soap.sforce.com","fieldName","http://www.w3.org/2001/XMLSchema","string",1,1,true), fieldName, fieldName__is_set);
    }

    /**
     * element : masterLabel of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean masterLabel__is_set = false;

    private java.lang.String masterLabel;

    public java.lang.String getMasterLabel() {
      return masterLabel;
    }

    public void setMasterLabel(java.lang.String masterLabel) {
      this.masterLabel = masterLabel;
      masterLabel__is_set = true;
    }

    protected void setMasterLabel(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("masterLabel", "urn:metadata.tooling.soap.sforce.com","masterLabel","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setMasterLabel(__typeMapper.readString(__in, _lookupTypeInfo("masterLabel", "urn:metadata.tooling.soap.sforce.com","masterLabel","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldMasterLabel(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("masterLabel", "urn:metadata.tooling.soap.sforce.com","masterLabel","http://www.w3.org/2001/XMLSchema","string",1,1,true), masterLabel, masterLabel__is_set);
    }

    /**
     * element : recordTypeName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean recordTypeName__is_set = false;

    private java.lang.String recordTypeName;

    public java.lang.String getRecordTypeName() {
      return recordTypeName;
    }

    public void setRecordTypeName(java.lang.String recordTypeName) {
      this.recordTypeName = recordTypeName;
      recordTypeName__is_set = true;
    }

    protected void setRecordTypeName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("recordTypeName", "urn:metadata.tooling.soap.sforce.com","recordTypeName","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setRecordTypeName(__typeMapper.readString(__in, _lookupTypeInfo("recordTypeName", "urn:metadata.tooling.soap.sforce.com","recordTypeName","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldRecordTypeName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("recordTypeName", "urn:metadata.tooling.soap.sforce.com","recordTypeName","http://www.w3.org/2001/XMLSchema","string",1,1,true), recordTypeName, recordTypeName__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "PathAssistant");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       writeFields1(__out, __typeMapper);
    }

    @Override
    public void load(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __typeMapper.consumeStartTag(__in);
      loadFields(__in, __typeMapper);
      __typeMapper.consumeEndTag(__in);
    }

    protected void loadFields(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
        super.loadFields(__in, __typeMapper);
        loadFields1(__in, __typeMapper);
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[PathAssistant ");
      sb.append(super.toString());
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldActive(__out, __typeMapper);
      writeFieldEntityName(__out, __typeMapper);
      writeFieldFieldName(__out, __typeMapper);
      writeFieldMasterLabel(__out, __typeMapper);
      writeFieldRecordTypeName(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setActive(__in, __typeMapper);
      setEntityName(__in, __typeMapper);
      setFieldName(__in, __typeMapper);
      setMasterLabel(__in, __typeMapper);
      setRecordTypeName(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "active", active);
      toStringHelper(sb, "entityName", entityName);
      toStringHelper(sb, "fieldName", fieldName);
      toStringHelper(sb, "masterLabel", masterLabel);
      toStringHelper(sb, "recordTypeName", recordTypeName);
    }


}
