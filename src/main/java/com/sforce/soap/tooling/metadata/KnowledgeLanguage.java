package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class KnowledgeLanguage implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public KnowledgeLanguage() {}

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
      if (__typeMapper.isElement(__in, _lookupTypeInfo("active", "urn:metadata.tooling.soap.sforce.com","active","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setActive(__typeMapper.readBoolean(__in, _lookupTypeInfo("active", "urn:metadata.tooling.soap.sforce.com","active","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldActive(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("active", "urn:metadata.tooling.soap.sforce.com","active","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), active, active__is_set);
    }

    /**
     * element : defaultAssignee of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean defaultAssignee__is_set = false;

    private java.lang.String defaultAssignee;

    public java.lang.String getDefaultAssignee() {
      return defaultAssignee;
    }

    public void setDefaultAssignee(java.lang.String defaultAssignee) {
      this.defaultAssignee = defaultAssignee;
      defaultAssignee__is_set = true;
    }

    protected void setDefaultAssignee(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("defaultAssignee", "urn:metadata.tooling.soap.sforce.com","defaultAssignee","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setDefaultAssignee(__typeMapper.readString(__in, _lookupTypeInfo("defaultAssignee", "urn:metadata.tooling.soap.sforce.com","defaultAssignee","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDefaultAssignee(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("defaultAssignee", "urn:metadata.tooling.soap.sforce.com","defaultAssignee","http://www.w3.org/2001/XMLSchema","string",0,1,true), defaultAssignee, defaultAssignee__is_set);
    }

    /**
     * element : defaultAssigneeType of type {urn:metadata.tooling.soap.sforce.com}KnowledgeLanguageLookupValueType
     * java type: com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType
     */
    private boolean defaultAssigneeType__is_set = false;

    private com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType defaultAssigneeType;

    public com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType getDefaultAssigneeType() {
      return defaultAssigneeType;
    }

    public void setDefaultAssigneeType(com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType defaultAssigneeType) {
      this.defaultAssigneeType = defaultAssigneeType;
      defaultAssigneeType__is_set = true;
    }

    protected void setDefaultAssigneeType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("defaultAssigneeType", "urn:metadata.tooling.soap.sforce.com","defaultAssigneeType","urn:metadata.tooling.soap.sforce.com","KnowledgeLanguageLookupValueType",0,1,true))) {
        setDefaultAssigneeType((com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType)__typeMapper.readObject(__in, _lookupTypeInfo("defaultAssigneeType", "urn:metadata.tooling.soap.sforce.com","defaultAssigneeType","urn:metadata.tooling.soap.sforce.com","KnowledgeLanguageLookupValueType",0,1,true), com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType.class));
      }
    }

    private void writeFieldDefaultAssigneeType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("defaultAssigneeType", "urn:metadata.tooling.soap.sforce.com","defaultAssigneeType","urn:metadata.tooling.soap.sforce.com","KnowledgeLanguageLookupValueType",0,1,true), defaultAssigneeType, defaultAssigneeType__is_set);
    }

    /**
     * element : defaultReviewer of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean defaultReviewer__is_set = false;

    private java.lang.String defaultReviewer;

    public java.lang.String getDefaultReviewer() {
      return defaultReviewer;
    }

    public void setDefaultReviewer(java.lang.String defaultReviewer) {
      this.defaultReviewer = defaultReviewer;
      defaultReviewer__is_set = true;
    }

    protected void setDefaultReviewer(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("defaultReviewer", "urn:metadata.tooling.soap.sforce.com","defaultReviewer","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setDefaultReviewer(__typeMapper.readString(__in, _lookupTypeInfo("defaultReviewer", "urn:metadata.tooling.soap.sforce.com","defaultReviewer","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDefaultReviewer(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("defaultReviewer", "urn:metadata.tooling.soap.sforce.com","defaultReviewer","http://www.w3.org/2001/XMLSchema","string",0,1,true), defaultReviewer, defaultReviewer__is_set);
    }

    /**
     * element : defaultReviewerType of type {urn:metadata.tooling.soap.sforce.com}KnowledgeLanguageLookupValueType
     * java type: com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType
     */
    private boolean defaultReviewerType__is_set = false;

    private com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType defaultReviewerType;

    public com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType getDefaultReviewerType() {
      return defaultReviewerType;
    }

    public void setDefaultReviewerType(com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType defaultReviewerType) {
      this.defaultReviewerType = defaultReviewerType;
      defaultReviewerType__is_set = true;
    }

    protected void setDefaultReviewerType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("defaultReviewerType", "urn:metadata.tooling.soap.sforce.com","defaultReviewerType","urn:metadata.tooling.soap.sforce.com","KnowledgeLanguageLookupValueType",0,1,true))) {
        setDefaultReviewerType((com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType)__typeMapper.readObject(__in, _lookupTypeInfo("defaultReviewerType", "urn:metadata.tooling.soap.sforce.com","defaultReviewerType","urn:metadata.tooling.soap.sforce.com","KnowledgeLanguageLookupValueType",0,1,true), com.sforce.soap.tooling.metadata.KnowledgeLanguageLookupValueType.class));
      }
    }

    private void writeFieldDefaultReviewerType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("defaultReviewerType", "urn:metadata.tooling.soap.sforce.com","defaultReviewerType","urn:metadata.tooling.soap.sforce.com","KnowledgeLanguageLookupValueType",0,1,true), defaultReviewerType, defaultReviewerType__is_set);
    }

    /**
     * element : name of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean name__is_set = false;

    private java.lang.String name;

    public java.lang.String getName() {
      return name;
    }

    public void setName(java.lang.String name) {
      this.name = name;
      name__is_set = true;
    }

    protected void setName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("name", "urn:metadata.tooling.soap.sforce.com","name","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setName(__typeMapper.readString(__in, _lookupTypeInfo("name", "urn:metadata.tooling.soap.sforce.com","name","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("name", "urn:metadata.tooling.soap.sforce.com","name","http://www.w3.org/2001/XMLSchema","string",1,1,true), name, name__is_set);
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
      sb.append("[KnowledgeLanguage ");
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
      writeFieldDefaultAssignee(__out, __typeMapper);
      writeFieldDefaultAssigneeType(__out, __typeMapper);
      writeFieldDefaultReviewer(__out, __typeMapper);
      writeFieldDefaultReviewerType(__out, __typeMapper);
      writeFieldName(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setActive(__in, __typeMapper);
      setDefaultAssignee(__in, __typeMapper);
      setDefaultAssigneeType(__in, __typeMapper);
      setDefaultReviewer(__in, __typeMapper);
      setDefaultReviewerType(__in, __typeMapper);
      setName(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "active", active);
      toStringHelper(sb, "defaultAssignee", defaultAssignee);
      toStringHelper(sb, "defaultAssigneeType", defaultAssigneeType);
      toStringHelper(sb, "defaultReviewer", defaultReviewer);
      toStringHelper(sb, "defaultReviewerType", defaultReviewerType);
      toStringHelper(sb, "name", name);
    }


}
