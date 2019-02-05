package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class EventDelivery extends com.sforce.soap.tooling.metadata.Metadata {

    /**
     * Constructor
     */
    public EventDelivery() {}

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
     * element : eventParameters of type {urn:metadata.tooling.soap.sforce.com}EventParameterMap
     * java type: com.sforce.soap.tooling.metadata.EventParameterMap[]
     */
    private boolean eventParameters__is_set = false;

    private com.sforce.soap.tooling.metadata.EventParameterMap[] eventParameters = new com.sforce.soap.tooling.metadata.EventParameterMap[0];

    public com.sforce.soap.tooling.metadata.EventParameterMap[] getEventParameters() {
      return eventParameters;
    }

    public void setEventParameters(com.sforce.soap.tooling.metadata.EventParameterMap[] eventParameters) {
      this.eventParameters = eventParameters;
      eventParameters__is_set = true;
    }

    protected void setEventParameters(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("eventParameters", "urn:metadata.tooling.soap.sforce.com","eventParameters","urn:metadata.tooling.soap.sforce.com","EventParameterMap",0,-1,true))) {
        setEventParameters((com.sforce.soap.tooling.metadata.EventParameterMap[])__typeMapper.readObject(__in, _lookupTypeInfo("eventParameters", "urn:metadata.tooling.soap.sforce.com","eventParameters","urn:metadata.tooling.soap.sforce.com","EventParameterMap",0,-1,true), com.sforce.soap.tooling.metadata.EventParameterMap[].class));
      }
    }

    private void writeFieldEventParameters(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("eventParameters", "urn:metadata.tooling.soap.sforce.com","eventParameters","urn:metadata.tooling.soap.sforce.com","EventParameterMap",0,-1,true), eventParameters, eventParameters__is_set);
    }

    /**
     * element : eventSubscription of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean eventSubscription__is_set = false;

    private java.lang.String eventSubscription;

    public java.lang.String getEventSubscription() {
      return eventSubscription;
    }

    public void setEventSubscription(java.lang.String eventSubscription) {
      this.eventSubscription = eventSubscription;
      eventSubscription__is_set = true;
    }

    protected void setEventSubscription(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("eventSubscription", "urn:metadata.tooling.soap.sforce.com","eventSubscription","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setEventSubscription(__typeMapper.readString(__in, _lookupTypeInfo("eventSubscription", "urn:metadata.tooling.soap.sforce.com","eventSubscription","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldEventSubscription(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("eventSubscription", "urn:metadata.tooling.soap.sforce.com","eventSubscription","http://www.w3.org/2001/XMLSchema","string",1,1,true), eventSubscription, eventSubscription__is_set);
    }

    /**
     * element : referenceData of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean referenceData__is_set = false;

    private java.lang.String referenceData;

    public java.lang.String getReferenceData() {
      return referenceData;
    }

    public void setReferenceData(java.lang.String referenceData) {
      this.referenceData = referenceData;
      referenceData__is_set = true;
    }

    protected void setReferenceData(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("referenceData", "urn:metadata.tooling.soap.sforce.com","referenceData","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setReferenceData(__typeMapper.readString(__in, _lookupTypeInfo("referenceData", "urn:metadata.tooling.soap.sforce.com","referenceData","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldReferenceData(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("referenceData", "urn:metadata.tooling.soap.sforce.com","referenceData","http://www.w3.org/2001/XMLSchema","string",0,1,true), referenceData, referenceData__is_set);
    }

    /**
     * element : type of type {urn:tooling.soap.sforce.com}EventDeliveryType
     * java type: com.sforce.soap.tooling.EventDeliveryType
     */
    private boolean type__is_set = false;

    private com.sforce.soap.tooling.EventDeliveryType type;

    public com.sforce.soap.tooling.EventDeliveryType getType() {
      return type;
    }

    public void setType(com.sforce.soap.tooling.EventDeliveryType type) {
      this.type = type;
      type__is_set = true;
    }

    protected void setType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("type", "urn:metadata.tooling.soap.sforce.com","type","urn:tooling.soap.sforce.com","EventDeliveryType",1,1,true))) {
        setType((com.sforce.soap.tooling.EventDeliveryType)__typeMapper.readObject(__in, _lookupTypeInfo("type", "urn:metadata.tooling.soap.sforce.com","type","urn:tooling.soap.sforce.com","EventDeliveryType",1,1,true), com.sforce.soap.tooling.EventDeliveryType.class));
      }
    }

    private void writeFieldType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("type", "urn:metadata.tooling.soap.sforce.com","type","urn:tooling.soap.sforce.com","EventDeliveryType",1,1,true), type, type__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "EventDelivery");
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
      sb.append("[EventDelivery ");
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
      writeFieldEventParameters(__out, __typeMapper);
      writeFieldEventSubscription(__out, __typeMapper);
      writeFieldReferenceData(__out, __typeMapper);
      writeFieldType(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setEventParameters(__in, __typeMapper);
      setEventSubscription(__in, __typeMapper);
      setReferenceData(__in, __typeMapper);
      setType(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "eventParameters", eventParameters);
      toStringHelper(sb, "eventSubscription", eventSubscription);
      toStringHelper(sb, "referenceData", referenceData);
      toStringHelper(sb, "type", type);
    }


}
