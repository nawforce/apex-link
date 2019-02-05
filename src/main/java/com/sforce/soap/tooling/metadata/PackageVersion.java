package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class PackageVersion implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public PackageVersion() {}

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
     * element : majorNumber of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean majorNumber__is_set = false;

    private int majorNumber;

    public int getMajorNumber() {
      return majorNumber;
    }

    public void setMajorNumber(int majorNumber) {
      this.majorNumber = majorNumber;
      majorNumber__is_set = true;
    }

    protected void setMajorNumber(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("majorNumber", "urn:metadata.tooling.soap.sforce.com","majorNumber","http://www.w3.org/2001/XMLSchema","int",1,1,true))) {
        setMajorNumber((int)__typeMapper.readInt(__in, _lookupTypeInfo("majorNumber", "urn:metadata.tooling.soap.sforce.com","majorNumber","http://www.w3.org/2001/XMLSchema","int",1,1,true), int.class));
      }
    }

    private void writeFieldMajorNumber(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("majorNumber", "urn:metadata.tooling.soap.sforce.com","majorNumber","http://www.w3.org/2001/XMLSchema","int",1,1,true), majorNumber, majorNumber__is_set);
    }

    /**
     * element : minorNumber of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean minorNumber__is_set = false;

    private int minorNumber;

    public int getMinorNumber() {
      return minorNumber;
    }

    public void setMinorNumber(int minorNumber) {
      this.minorNumber = minorNumber;
      minorNumber__is_set = true;
    }

    protected void setMinorNumber(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("minorNumber", "urn:metadata.tooling.soap.sforce.com","minorNumber","http://www.w3.org/2001/XMLSchema","int",1,1,true))) {
        setMinorNumber((int)__typeMapper.readInt(__in, _lookupTypeInfo("minorNumber", "urn:metadata.tooling.soap.sforce.com","minorNumber","http://www.w3.org/2001/XMLSchema","int",1,1,true), int.class));
      }
    }

    private void writeFieldMinorNumber(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("minorNumber", "urn:metadata.tooling.soap.sforce.com","minorNumber","http://www.w3.org/2001/XMLSchema","int",1,1,true), minorNumber, minorNumber__is_set);
    }

    /**
     * element : namespace of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean namespace__is_set = false;

    private java.lang.String namespace;

    public java.lang.String getNamespace() {
      return namespace;
    }

    public void setNamespace(java.lang.String namespace) {
      this.namespace = namespace;
      namespace__is_set = true;
    }

    protected void setNamespace(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("namespace", "urn:metadata.tooling.soap.sforce.com","namespace","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setNamespace(__typeMapper.readString(__in, _lookupTypeInfo("namespace", "urn:metadata.tooling.soap.sforce.com","namespace","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldNamespace(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("namespace", "urn:metadata.tooling.soap.sforce.com","namespace","http://www.w3.org/2001/XMLSchema","string",1,1,true), namespace, namespace__is_set);
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
      sb.append("[PackageVersion ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldMajorNumber(__out, __typeMapper);
      writeFieldMinorNumber(__out, __typeMapper);
      writeFieldNamespace(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setMajorNumber(__in, __typeMapper);
      setMinorNumber(__in, __typeMapper);
      setNamespace(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "majorNumber", majorNumber);
      toStringHelper(sb, "minorNumber", minorNumber);
      toStringHelper(sb, "namespace", namespace);
    }


}
