package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class FieldMappingField implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public FieldMappingField() {}

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
     * element : dataServiceField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean dataServiceField__is_set = false;

    private java.lang.String dataServiceField;

    public java.lang.String getDataServiceField() {
      return dataServiceField;
    }

    public void setDataServiceField(java.lang.String dataServiceField) {
      this.dataServiceField = dataServiceField;
      dataServiceField__is_set = true;
    }

    protected void setDataServiceField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("dataServiceField", "urn:metadata.tooling.soap.sforce.com","dataServiceField","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setDataServiceField(__typeMapper.readString(__in, _lookupTypeInfo("dataServiceField", "urn:metadata.tooling.soap.sforce.com","dataServiceField","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDataServiceField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("dataServiceField", "urn:metadata.tooling.soap.sforce.com","dataServiceField","http://www.w3.org/2001/XMLSchema","string",1,1,true), dataServiceField, dataServiceField__is_set);
    }

    /**
     * element : dataServiceObjectName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean dataServiceObjectName__is_set = false;

    private java.lang.String dataServiceObjectName;

    public java.lang.String getDataServiceObjectName() {
      return dataServiceObjectName;
    }

    public void setDataServiceObjectName(java.lang.String dataServiceObjectName) {
      this.dataServiceObjectName = dataServiceObjectName;
      dataServiceObjectName__is_set = true;
    }

    protected void setDataServiceObjectName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("dataServiceObjectName", "urn:metadata.tooling.soap.sforce.com","dataServiceObjectName","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setDataServiceObjectName(__typeMapper.readString(__in, _lookupTypeInfo("dataServiceObjectName", "urn:metadata.tooling.soap.sforce.com","dataServiceObjectName","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDataServiceObjectName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("dataServiceObjectName", "urn:metadata.tooling.soap.sforce.com","dataServiceObjectName","http://www.w3.org/2001/XMLSchema","string",1,1,true), dataServiceObjectName, dataServiceObjectName__is_set);
    }

    /**
     * element : priority of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean priority__is_set = false;

    private int priority;

    public int getPriority() {
      return priority;
    }

    public void setPriority(int priority) {
      this.priority = priority;
      priority__is_set = true;
    }

    protected void setPriority(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("priority", "urn:metadata.tooling.soap.sforce.com","priority","http://www.w3.org/2001/XMLSchema","int",1,1,true))) {
        setPriority((int)__typeMapper.readInt(__in, _lookupTypeInfo("priority", "urn:metadata.tooling.soap.sforce.com","priority","http://www.w3.org/2001/XMLSchema","int",1,1,true), int.class));
      }
    }

    private void writeFieldPriority(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("priority", "urn:metadata.tooling.soap.sforce.com","priority","http://www.w3.org/2001/XMLSchema","int",1,1,true), priority, priority__is_set);
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
      sb.append("[FieldMappingField ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldDataServiceField(__out, __typeMapper);
      writeFieldDataServiceObjectName(__out, __typeMapper);
      writeFieldPriority(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setDataServiceField(__in, __typeMapper);
      setDataServiceObjectName(__in, __typeMapper);
      setPriority(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "dataServiceField", dataServiceField);
      toStringHelper(sb, "dataServiceObjectName", dataServiceObjectName);
      toStringHelper(sb, "priority", priority);
    }


}
