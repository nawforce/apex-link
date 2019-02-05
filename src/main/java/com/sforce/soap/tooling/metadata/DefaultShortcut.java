package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class DefaultShortcut implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public DefaultShortcut() {}

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
     * element : action of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean action__is_set = false;

    private java.lang.String action;

    public java.lang.String getAction() {
      return action;
    }

    public void setAction(java.lang.String action) {
      this.action = action;
      action__is_set = true;
    }

    protected void setAction(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("action", "urn:metadata.tooling.soap.sforce.com","action","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setAction(__typeMapper.readString(__in, _lookupTypeInfo("action", "urn:metadata.tooling.soap.sforce.com","action","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldAction(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("action", "urn:metadata.tooling.soap.sforce.com","action","http://www.w3.org/2001/XMLSchema","string",1,1,true), action, action__is_set);
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
     * element : keyCommand of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean keyCommand__is_set = false;

    private java.lang.String keyCommand;

    public java.lang.String getKeyCommand() {
      return keyCommand;
    }

    public void setKeyCommand(java.lang.String keyCommand) {
      this.keyCommand = keyCommand;
      keyCommand__is_set = true;
    }

    protected void setKeyCommand(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("keyCommand", "urn:metadata.tooling.soap.sforce.com","keyCommand","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setKeyCommand(__typeMapper.readString(__in, _lookupTypeInfo("keyCommand", "urn:metadata.tooling.soap.sforce.com","keyCommand","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldKeyCommand(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("keyCommand", "urn:metadata.tooling.soap.sforce.com","keyCommand","http://www.w3.org/2001/XMLSchema","string",1,1,true), keyCommand, keyCommand__is_set);
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
      sb.append("[DefaultShortcut ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldAction(__out, __typeMapper);
      writeFieldActive(__out, __typeMapper);
      writeFieldKeyCommand(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setAction(__in, __typeMapper);
      setActive(__in, __typeMapper);
      setKeyCommand(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "action", action);
      toStringHelper(sb, "active", active);
      toStringHelper(sb, "keyCommand", keyCommand);
    }


}
