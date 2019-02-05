package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class CaseSubjectParticle extends com.sforce.soap.tooling.metadata.Metadata {

    /**
     * Constructor
     */
    public CaseSubjectParticle() {}

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
     * element : index of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean index__is_set = false;

    private int index;

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
      index__is_set = true;
    }

    protected void setIndex(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("index", "urn:metadata.tooling.soap.sforce.com","index","http://www.w3.org/2001/XMLSchema","int",1,1,true))) {
        setIndex((int)__typeMapper.readInt(__in, _lookupTypeInfo("index", "urn:metadata.tooling.soap.sforce.com","index","http://www.w3.org/2001/XMLSchema","int",1,1,true), int.class));
      }
    }

    private void writeFieldIndex(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("index", "urn:metadata.tooling.soap.sforce.com","index","http://www.w3.org/2001/XMLSchema","int",1,1,true), index, index__is_set);
    }

    /**
     * element : textField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean textField__is_set = false;

    private java.lang.String textField;

    public java.lang.String getTextField() {
      return textField;
    }

    public void setTextField(java.lang.String textField) {
      this.textField = textField;
      textField__is_set = true;
    }

    protected void setTextField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("textField", "urn:metadata.tooling.soap.sforce.com","textField","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setTextField(__typeMapper.readString(__in, _lookupTypeInfo("textField", "urn:metadata.tooling.soap.sforce.com","textField","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldTextField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("textField", "urn:metadata.tooling.soap.sforce.com","textField","http://www.w3.org/2001/XMLSchema","string",0,1,true), textField, textField__is_set);
    }

    /**
     * element : type of type {urn:tooling.soap.sforce.com}CaseSubjectParticleType
     * java type: com.sforce.soap.tooling.CaseSubjectParticleType
     */
    private boolean type__is_set = false;

    private com.sforce.soap.tooling.CaseSubjectParticleType type;

    public com.sforce.soap.tooling.CaseSubjectParticleType getType() {
      return type;
    }

    public void setType(com.sforce.soap.tooling.CaseSubjectParticleType type) {
      this.type = type;
      type__is_set = true;
    }

    protected void setType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("type", "urn:metadata.tooling.soap.sforce.com","type","urn:tooling.soap.sforce.com","CaseSubjectParticleType",1,1,true))) {
        setType((com.sforce.soap.tooling.CaseSubjectParticleType)__typeMapper.readObject(__in, _lookupTypeInfo("type", "urn:metadata.tooling.soap.sforce.com","type","urn:tooling.soap.sforce.com","CaseSubjectParticleType",1,1,true), com.sforce.soap.tooling.CaseSubjectParticleType.class));
      }
    }

    private void writeFieldType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("type", "urn:metadata.tooling.soap.sforce.com","type","urn:tooling.soap.sforce.com","CaseSubjectParticleType",1,1,true), type, type__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "CaseSubjectParticle");
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
      sb.append("[CaseSubjectParticle ");
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
      writeFieldIndex(__out, __typeMapper);
      writeFieldTextField(__out, __typeMapper);
      writeFieldType(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setIndex(__in, __typeMapper);
      setTextField(__in, __typeMapper);
      setType(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "index", index);
      toStringHelper(sb, "textField", textField);
      toStringHelper(sb, "type", type);
    }


}
