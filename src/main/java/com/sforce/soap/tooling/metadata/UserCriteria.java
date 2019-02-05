package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class UserCriteria extends com.sforce.soap.tooling.metadata.Metadata {

    /**
     * Constructor
     */
    public UserCriteria() {}

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
     * element : creationAgeInSeconds of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean creationAgeInSeconds__is_set = false;

    private int creationAgeInSeconds;

    public int getCreationAgeInSeconds() {
      return creationAgeInSeconds;
    }

    public void setCreationAgeInSeconds(int creationAgeInSeconds) {
      this.creationAgeInSeconds = creationAgeInSeconds;
      creationAgeInSeconds__is_set = true;
    }

    protected void setCreationAgeInSeconds(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("creationAgeInSeconds", "urn:metadata.tooling.soap.sforce.com","creationAgeInSeconds","http://www.w3.org/2001/XMLSchema","int",0,1,true))) {
        setCreationAgeInSeconds((int)__typeMapper.readInt(__in, _lookupTypeInfo("creationAgeInSeconds", "urn:metadata.tooling.soap.sforce.com","creationAgeInSeconds","http://www.w3.org/2001/XMLSchema","int",0,1,true), int.class));
      }
    }

    private void writeFieldCreationAgeInSeconds(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("creationAgeInSeconds", "urn:metadata.tooling.soap.sforce.com","creationAgeInSeconds","http://www.w3.org/2001/XMLSchema","int",0,1,true), creationAgeInSeconds, creationAgeInSeconds__is_set);
    }

    /**
     * element : description of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean description__is_set = false;

    private java.lang.String description;

    public java.lang.String getDescription() {
      return description;
    }

    public void setDescription(java.lang.String description) {
      this.description = description;
      description__is_set = true;
    }

    protected void setDescription(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("description", "urn:metadata.tooling.soap.sforce.com","description","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setDescription(__typeMapper.readString(__in, _lookupTypeInfo("description", "urn:metadata.tooling.soap.sforce.com","description","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDescription(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("description", "urn:metadata.tooling.soap.sforce.com","description","http://www.w3.org/2001/XMLSchema","string",0,1,true), description, description__is_set);
    }

    /**
     * element : lastChatterActivityAgeInSeconds of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean lastChatterActivityAgeInSeconds__is_set = false;

    private int lastChatterActivityAgeInSeconds;

    public int getLastChatterActivityAgeInSeconds() {
      return lastChatterActivityAgeInSeconds;
    }

    public void setLastChatterActivityAgeInSeconds(int lastChatterActivityAgeInSeconds) {
      this.lastChatterActivityAgeInSeconds = lastChatterActivityAgeInSeconds;
      lastChatterActivityAgeInSeconds__is_set = true;
    }

    protected void setLastChatterActivityAgeInSeconds(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("lastChatterActivityAgeInSeconds", "urn:metadata.tooling.soap.sforce.com","lastChatterActivityAgeInSeconds","http://www.w3.org/2001/XMLSchema","int",0,1,true))) {
        setLastChatterActivityAgeInSeconds((int)__typeMapper.readInt(__in, _lookupTypeInfo("lastChatterActivityAgeInSeconds", "urn:metadata.tooling.soap.sforce.com","lastChatterActivityAgeInSeconds","http://www.w3.org/2001/XMLSchema","int",0,1,true), int.class));
      }
    }

    private void writeFieldLastChatterActivityAgeInSeconds(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("lastChatterActivityAgeInSeconds", "urn:metadata.tooling.soap.sforce.com","lastChatterActivityAgeInSeconds","http://www.w3.org/2001/XMLSchema","int",0,1,true), lastChatterActivityAgeInSeconds, lastChatterActivityAgeInSeconds__is_set);
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
     * element : profiles of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private boolean profiles__is_set = false;

    private java.lang.String[] profiles = new java.lang.String[0];

    public java.lang.String[] getProfiles() {
      return profiles;
    }

    public void setProfiles(java.lang.String[] profiles) {
      this.profiles = profiles;
      profiles__is_set = true;
    }

    protected void setProfiles(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("profiles", "urn:metadata.tooling.soap.sforce.com","profiles","http://www.w3.org/2001/XMLSchema","string",0,-1,true))) {
        setProfiles((java.lang.String[])__typeMapper.readObject(__in, _lookupTypeInfo("profiles", "urn:metadata.tooling.soap.sforce.com","profiles","http://www.w3.org/2001/XMLSchema","string",0,-1,true), java.lang.String[].class));
      }
    }

    private void writeFieldProfiles(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("profiles", "urn:metadata.tooling.soap.sforce.com","profiles","http://www.w3.org/2001/XMLSchema","string",0,-1,true), profiles, profiles__is_set);
    }

    /**
     * element : userTypes of type {urn:tooling.soap.sforce.com}NetworkUserType
     * java type: com.sforce.soap.tooling.NetworkUserType[]
     */
    private boolean userTypes__is_set = false;

    private com.sforce.soap.tooling.NetworkUserType[] userTypes = new com.sforce.soap.tooling.NetworkUserType[0];

    public com.sforce.soap.tooling.NetworkUserType[] getUserTypes() {
      return userTypes;
    }

    public void setUserTypes(com.sforce.soap.tooling.NetworkUserType[] userTypes) {
      this.userTypes = userTypes;
      userTypes__is_set = true;
    }

    protected void setUserTypes(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("userTypes", "urn:metadata.tooling.soap.sforce.com","userTypes","urn:tooling.soap.sforce.com","NetworkUserType",0,-1,true))) {
        setUserTypes((com.sforce.soap.tooling.NetworkUserType[])__typeMapper.readObject(__in, _lookupTypeInfo("userTypes", "urn:metadata.tooling.soap.sforce.com","userTypes","urn:tooling.soap.sforce.com","NetworkUserType",0,-1,true), com.sforce.soap.tooling.NetworkUserType[].class));
      }
    }

    private void writeFieldUserTypes(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("userTypes", "urn:metadata.tooling.soap.sforce.com","userTypes","urn:tooling.soap.sforce.com","NetworkUserType",0,-1,true), userTypes, userTypes__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "UserCriteria");
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
      sb.append("[UserCriteria ");
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
      writeFieldCreationAgeInSeconds(__out, __typeMapper);
      writeFieldDescription(__out, __typeMapper);
      writeFieldLastChatterActivityAgeInSeconds(__out, __typeMapper);
      writeFieldMasterLabel(__out, __typeMapper);
      writeFieldProfiles(__out, __typeMapper);
      writeFieldUserTypes(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setCreationAgeInSeconds(__in, __typeMapper);
      setDescription(__in, __typeMapper);
      setLastChatterActivityAgeInSeconds(__in, __typeMapper);
      setMasterLabel(__in, __typeMapper);
      setProfiles(__in, __typeMapper);
      setUserTypes(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "creationAgeInSeconds", creationAgeInSeconds);
      toStringHelper(sb, "description", description);
      toStringHelper(sb, "lastChatterActivityAgeInSeconds", lastChatterActivityAgeInSeconds);
      toStringHelper(sb, "masterLabel", masterLabel);
      toStringHelper(sb, "profiles", profiles);
      toStringHelper(sb, "userTypes", userTypes);
    }


}
