package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class ProfileFieldLevelSecurity implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public ProfileFieldLevelSecurity() {}

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
     * element : editable of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean editable__is_set = false;

    private boolean editable;

    public boolean getEditable() {
      return editable;
    }

    public boolean isEditable() {
      return editable;
    }

    public void setEditable(boolean editable) {
      this.editable = editable;
      editable__is_set = true;
    }

    protected void setEditable(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("editable", "urn:metadata.tooling.soap.sforce.com","editable","http://www.w3.org/2001/XMLSchema","boolean",1,1,true))) {
        setEditable(__typeMapper.readBoolean(__in, _lookupTypeInfo("editable", "urn:metadata.tooling.soap.sforce.com","editable","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), boolean.class));
      }
    }

    private void writeFieldEditable(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("editable", "urn:metadata.tooling.soap.sforce.com","editable","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), editable, editable__is_set);
    }

    /**
     * element : field of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean field__is_set = false;

    private java.lang.String field;

    public java.lang.String getField() {
      return field;
    }

    public void setField(java.lang.String field) {
      this.field = field;
      field__is_set = true;
    }

    protected void setField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("field", "urn:metadata.tooling.soap.sforce.com","field","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setField(__typeMapper.readString(__in, _lookupTypeInfo("field", "urn:metadata.tooling.soap.sforce.com","field","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("field", "urn:metadata.tooling.soap.sforce.com","field","http://www.w3.org/2001/XMLSchema","string",1,1,true), field, field__is_set);
    }

    /**
     * element : readable of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean readable__is_set = false;

    private boolean readable;

    public boolean getReadable() {
      return readable;
    }

    public boolean isReadable() {
      return readable;
    }

    public void setReadable(boolean readable) {
      this.readable = readable;
      readable__is_set = true;
    }

    protected void setReadable(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("readable", "urn:metadata.tooling.soap.sforce.com","readable","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setReadable(__typeMapper.readBoolean(__in, _lookupTypeInfo("readable", "urn:metadata.tooling.soap.sforce.com","readable","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldReadable(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("readable", "urn:metadata.tooling.soap.sforce.com","readable","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), readable, readable__is_set);
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
      sb.append("[ProfileFieldLevelSecurity ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldEditable(__out, __typeMapper);
      writeFieldField(__out, __typeMapper);
      writeFieldReadable(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setEditable(__in, __typeMapper);
      setField(__in, __typeMapper);
      setReadable(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "editable", editable);
      toStringHelper(sb, "field", field);
      toStringHelper(sb, "readable", readable);
    }


}
