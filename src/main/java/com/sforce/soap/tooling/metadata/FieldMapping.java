package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class FieldMapping implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public FieldMapping() {}

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
     * element : SObjectType of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean SObjectType__is_set = false;

    private java.lang.String SObjectType;

    public java.lang.String getSObjectType() {
      return SObjectType;
    }

    public void setSObjectType(java.lang.String SObjectType) {
      this.SObjectType = SObjectType;
      SObjectType__is_set = true;
    }

    protected void setSObjectType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("SObjectType", "urn:metadata.tooling.soap.sforce.com","SObjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setSObjectType(__typeMapper.readString(__in, _lookupTypeInfo("SObjectType", "urn:metadata.tooling.soap.sforce.com","SObjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldSObjectType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("SObjectType", "urn:metadata.tooling.soap.sforce.com","SObjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true), SObjectType, SObjectType__is_set);
    }

    /**
     * element : developerName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean developerName__is_set = false;

    private java.lang.String developerName;

    public java.lang.String getDeveloperName() {
      return developerName;
    }

    public void setDeveloperName(java.lang.String developerName) {
      this.developerName = developerName;
      developerName__is_set = true;
    }

    protected void setDeveloperName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("developerName", "urn:metadata.tooling.soap.sforce.com","developerName","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setDeveloperName(__typeMapper.readString(__in, _lookupTypeInfo("developerName", "urn:metadata.tooling.soap.sforce.com","developerName","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDeveloperName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("developerName", "urn:metadata.tooling.soap.sforce.com","developerName","http://www.w3.org/2001/XMLSchema","string",1,1,true), developerName, developerName__is_set);
    }

    /**
     * element : fieldMappingRows of type {urn:metadata.tooling.soap.sforce.com}FieldMappingRow
     * java type: com.sforce.soap.tooling.metadata.FieldMappingRow[]
     */
    private boolean fieldMappingRows__is_set = false;

    private com.sforce.soap.tooling.metadata.FieldMappingRow[] fieldMappingRows = new com.sforce.soap.tooling.metadata.FieldMappingRow[0];

    public com.sforce.soap.tooling.metadata.FieldMappingRow[] getFieldMappingRows() {
      return fieldMappingRows;
    }

    public void setFieldMappingRows(com.sforce.soap.tooling.metadata.FieldMappingRow[] fieldMappingRows) {
      this.fieldMappingRows = fieldMappingRows;
      fieldMappingRows__is_set = true;
    }

    protected void setFieldMappingRows(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("fieldMappingRows", "urn:metadata.tooling.soap.sforce.com","fieldMappingRows","urn:metadata.tooling.soap.sforce.com","FieldMappingRow",0,-1,true))) {
        setFieldMappingRows((com.sforce.soap.tooling.metadata.FieldMappingRow[])__typeMapper.readObject(__in, _lookupTypeInfo("fieldMappingRows", "urn:metadata.tooling.soap.sforce.com","fieldMappingRows","urn:metadata.tooling.soap.sforce.com","FieldMappingRow",0,-1,true), com.sforce.soap.tooling.metadata.FieldMappingRow[].class));
      }
    }

    private void writeFieldFieldMappingRows(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("fieldMappingRows", "urn:metadata.tooling.soap.sforce.com","fieldMappingRows","urn:metadata.tooling.soap.sforce.com","FieldMappingRow",0,-1,true), fieldMappingRows, fieldMappingRows__is_set);
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
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
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
        loadFields1(__in, __typeMapper);
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FieldMapping ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldSObjectType(__out, __typeMapper);
      writeFieldDeveloperName(__out, __typeMapper);
      writeFieldFieldMappingRows(__out, __typeMapper);
      writeFieldMasterLabel(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setSObjectType(__in, __typeMapper);
      setDeveloperName(__in, __typeMapper);
      setFieldMappingRows(__in, __typeMapper);
      setMasterLabel(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "SObjectType", SObjectType);
      toStringHelper(sb, "developerName", developerName);
      toStringHelper(sb, "fieldMappingRows", fieldMappingRows);
      toStringHelper(sb, "masterLabel", masterLabel);
    }


}
