package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class FlowStage extends com.sforce.soap.tooling.metadata.FlowElement {

    /**
     * Constructor
     */
    public FlowStage() {}

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
     * element : isActive of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean isActive__is_set = false;

    private boolean isActive;

    public boolean getIsActive() {
      return isActive;
    }

    public boolean isIsActive() {
      return isActive;
    }

    public void setIsActive(boolean isActive) {
      this.isActive = isActive;
      isActive__is_set = true;
    }

    protected void setIsActive(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("isActive", "urn:metadata.tooling.soap.sforce.com","isActive","http://www.w3.org/2001/XMLSchema","boolean",1,1,true))) {
        setIsActive(__typeMapper.readBoolean(__in, _lookupTypeInfo("isActive", "urn:metadata.tooling.soap.sforce.com","isActive","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), boolean.class));
      }
    }

    private void writeFieldIsActive(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("isActive", "urn:metadata.tooling.soap.sforce.com","isActive","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), isActive, isActive__is_set);
    }

    /**
     * element : label of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean label__is_set = false;

    private java.lang.String label;

    public java.lang.String getLabel() {
      return label;
    }

    public void setLabel(java.lang.String label) {
      this.label = label;
      label__is_set = true;
    }

    protected void setLabel(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("label", "urn:metadata.tooling.soap.sforce.com","label","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setLabel(__typeMapper.readString(__in, _lookupTypeInfo("label", "urn:metadata.tooling.soap.sforce.com","label","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldLabel(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("label", "urn:metadata.tooling.soap.sforce.com","label","http://www.w3.org/2001/XMLSchema","string",1,1,true), label, label__is_set);
    }

    /**
     * element : stageOrder of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean stageOrder__is_set = false;

    private int stageOrder;

    public int getStageOrder() {
      return stageOrder;
    }

    public void setStageOrder(int stageOrder) {
      this.stageOrder = stageOrder;
      stageOrder__is_set = true;
    }

    protected void setStageOrder(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("stageOrder", "urn:metadata.tooling.soap.sforce.com","stageOrder","http://www.w3.org/2001/XMLSchema","int",1,1,true))) {
        setStageOrder((int)__typeMapper.readInt(__in, _lookupTypeInfo("stageOrder", "urn:metadata.tooling.soap.sforce.com","stageOrder","http://www.w3.org/2001/XMLSchema","int",1,1,true), int.class));
      }
    }

    private void writeFieldStageOrder(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("stageOrder", "urn:metadata.tooling.soap.sforce.com","stageOrder","http://www.w3.org/2001/XMLSchema","int",1,1,true), stageOrder, stageOrder__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "FlowStage");
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
      sb.append("[FlowStage ");
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
      writeFieldIsActive(__out, __typeMapper);
      writeFieldLabel(__out, __typeMapper);
      writeFieldStageOrder(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setIsActive(__in, __typeMapper);
      setLabel(__in, __typeMapper);
      setStageOrder(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "isActive", isActive);
      toStringHelper(sb, "label", label);
      toStringHelper(sb, "stageOrder", stageOrder);
    }


}
