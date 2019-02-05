package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class ProfileApplicationVisibility implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public ProfileApplicationVisibility() {}

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
     * element : application of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean application__is_set = false;

    private java.lang.String application;

    public java.lang.String getApplication() {
      return application;
    }

    public void setApplication(java.lang.String application) {
      this.application = application;
      application__is_set = true;
    }

    protected void setApplication(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("application", "urn:metadata.tooling.soap.sforce.com","application","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setApplication(__typeMapper.readString(__in, _lookupTypeInfo("application", "urn:metadata.tooling.soap.sforce.com","application","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldApplication(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("application", "urn:metadata.tooling.soap.sforce.com","application","http://www.w3.org/2001/XMLSchema","string",1,1,true), application, application__is_set);
    }

    /**
     * element : default of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean _default__is_set = false;

    private boolean _default;

    public boolean getDefault() {
      return _default;
    }

    public boolean isDefault() {
      return _default;
    }

    public void setDefault(boolean _default) {
      this._default = _default;
      _default__is_set = true;
    }

    protected void setDefault(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("_default", "urn:metadata.tooling.soap.sforce.com","default","http://www.w3.org/2001/XMLSchema","boolean",1,1,true))) {
        setDefault(__typeMapper.readBoolean(__in, _lookupTypeInfo("_default", "urn:metadata.tooling.soap.sforce.com","default","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), boolean.class));
      }
    }

    private void writeFieldDefault(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("_default", "urn:metadata.tooling.soap.sforce.com","default","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), _default, _default__is_set);
    }

    /**
     * element : visible of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean visible__is_set = false;

    private boolean visible;

    public boolean getVisible() {
      return visible;
    }

    public boolean isVisible() {
      return visible;
    }

    public void setVisible(boolean visible) {
      this.visible = visible;
      visible__is_set = true;
    }

    protected void setVisible(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("visible", "urn:metadata.tooling.soap.sforce.com","visible","http://www.w3.org/2001/XMLSchema","boolean",1,1,true))) {
        setVisible(__typeMapper.readBoolean(__in, _lookupTypeInfo("visible", "urn:metadata.tooling.soap.sforce.com","visible","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), boolean.class));
      }
    }

    private void writeFieldVisible(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("visible", "urn:metadata.tooling.soap.sforce.com","visible","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), visible, visible__is_set);
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
      sb.append("[ProfileApplicationVisibility ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldApplication(__out, __typeMapper);
      writeFieldDefault(__out, __typeMapper);
      writeFieldVisible(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setApplication(__in, __typeMapper);
      setDefault(__in, __typeMapper);
      setVisible(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "application", application);
      toStringHelper(sb, "_default", _default);
      toStringHelper(sb, "visible", visible);
    }


}
