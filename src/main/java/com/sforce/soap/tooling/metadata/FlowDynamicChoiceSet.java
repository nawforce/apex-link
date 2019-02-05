package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class FlowDynamicChoiceSet extends com.sforce.soap.tooling.metadata.FlowElement {

    /**
     * Constructor
     */
    public FlowDynamicChoiceSet() {}

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
     * element : dataType of type {urn:tooling.soap.sforce.com}FlowDataType
     * java type: com.sforce.soap.tooling.FlowDataType
     */
    private boolean dataType__is_set = false;

    private com.sforce.soap.tooling.FlowDataType dataType;

    public com.sforce.soap.tooling.FlowDataType getDataType() {
      return dataType;
    }

    public void setDataType(com.sforce.soap.tooling.FlowDataType dataType) {
      this.dataType = dataType;
      dataType__is_set = true;
    }

    protected void setDataType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("dataType", "urn:metadata.tooling.soap.sforce.com","dataType","urn:tooling.soap.sforce.com","FlowDataType",1,1,true))) {
        setDataType((com.sforce.soap.tooling.FlowDataType)__typeMapper.readObject(__in, _lookupTypeInfo("dataType", "urn:metadata.tooling.soap.sforce.com","dataType","urn:tooling.soap.sforce.com","FlowDataType",1,1,true), com.sforce.soap.tooling.FlowDataType.class));
      }
    }

    private void writeFieldDataType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("dataType", "urn:metadata.tooling.soap.sforce.com","dataType","urn:tooling.soap.sforce.com","FlowDataType",1,1,true), dataType, dataType__is_set);
    }

    /**
     * element : displayField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean displayField__is_set = false;

    private java.lang.String displayField;

    public java.lang.String getDisplayField() {
      return displayField;
    }

    public void setDisplayField(java.lang.String displayField) {
      this.displayField = displayField;
      displayField__is_set = true;
    }

    protected void setDisplayField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("displayField", "urn:metadata.tooling.soap.sforce.com","displayField","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setDisplayField(__typeMapper.readString(__in, _lookupTypeInfo("displayField", "urn:metadata.tooling.soap.sforce.com","displayField","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDisplayField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("displayField", "urn:metadata.tooling.soap.sforce.com","displayField","http://www.w3.org/2001/XMLSchema","string",1,1,true), displayField, displayField__is_set);
    }

    /**
     * element : filters of type {urn:metadata.tooling.soap.sforce.com}FlowRecordFilter
     * java type: com.sforce.soap.tooling.metadata.FlowRecordFilter[]
     */
    private boolean filters__is_set = false;

    private com.sforce.soap.tooling.metadata.FlowRecordFilter[] filters = new com.sforce.soap.tooling.metadata.FlowRecordFilter[0];

    public com.sforce.soap.tooling.metadata.FlowRecordFilter[] getFilters() {
      return filters;
    }

    public void setFilters(com.sforce.soap.tooling.metadata.FlowRecordFilter[] filters) {
      this.filters = filters;
      filters__is_set = true;
    }

    protected void setFilters(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("filters", "urn:metadata.tooling.soap.sforce.com","filters","urn:metadata.tooling.soap.sforce.com","FlowRecordFilter",0,-1,true))) {
        setFilters((com.sforce.soap.tooling.metadata.FlowRecordFilter[])__typeMapper.readObject(__in, _lookupTypeInfo("filters", "urn:metadata.tooling.soap.sforce.com","filters","urn:metadata.tooling.soap.sforce.com","FlowRecordFilter",0,-1,true), com.sforce.soap.tooling.metadata.FlowRecordFilter[].class));
      }
    }

    private void writeFieldFilters(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("filters", "urn:metadata.tooling.soap.sforce.com","filters","urn:metadata.tooling.soap.sforce.com","FlowRecordFilter",0,-1,true), filters, filters__is_set);
    }

    /**
     * element : limit of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean limit__is_set = false;

    private int limit;

    public int getLimit() {
      return limit;
    }

    public void setLimit(int limit) {
      this.limit = limit;
      limit__is_set = true;
    }

    protected void setLimit(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("limit", "urn:metadata.tooling.soap.sforce.com","limit","http://www.w3.org/2001/XMLSchema","int",0,1,true))) {
        setLimit((int)__typeMapper.readInt(__in, _lookupTypeInfo("limit", "urn:metadata.tooling.soap.sforce.com","limit","http://www.w3.org/2001/XMLSchema","int",0,1,true), int.class));
      }
    }

    private void writeFieldLimit(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("limit", "urn:metadata.tooling.soap.sforce.com","limit","http://www.w3.org/2001/XMLSchema","int",0,1,true), limit, limit__is_set);
    }

    /**
     * element : object of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean object__is_set = false;

    private java.lang.String object;

    public java.lang.String getObject() {
      return object;
    }

    public void setObject(java.lang.String object) {
      this.object = object;
      object__is_set = true;
    }

    protected void setObject(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("object", "urn:metadata.tooling.soap.sforce.com","object","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setObject(__typeMapper.readString(__in, _lookupTypeInfo("object", "urn:metadata.tooling.soap.sforce.com","object","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldObject(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("object", "urn:metadata.tooling.soap.sforce.com","object","http://www.w3.org/2001/XMLSchema","string",1,1,true), object, object__is_set);
    }

    /**
     * element : outputAssignments of type {urn:metadata.tooling.soap.sforce.com}FlowOutputFieldAssignment
     * java type: com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[]
     */
    private boolean outputAssignments__is_set = false;

    private com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[] outputAssignments = new com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[0];

    public com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[] getOutputAssignments() {
      return outputAssignments;
    }

    public void setOutputAssignments(com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[] outputAssignments) {
      this.outputAssignments = outputAssignments;
      outputAssignments__is_set = true;
    }

    protected void setOutputAssignments(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("outputAssignments", "urn:metadata.tooling.soap.sforce.com","outputAssignments","urn:metadata.tooling.soap.sforce.com","FlowOutputFieldAssignment",0,-1,true))) {
        setOutputAssignments((com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[])__typeMapper.readObject(__in, _lookupTypeInfo("outputAssignments", "urn:metadata.tooling.soap.sforce.com","outputAssignments","urn:metadata.tooling.soap.sforce.com","FlowOutputFieldAssignment",0,-1,true), com.sforce.soap.tooling.metadata.FlowOutputFieldAssignment[].class));
      }
    }

    private void writeFieldOutputAssignments(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("outputAssignments", "urn:metadata.tooling.soap.sforce.com","outputAssignments","urn:metadata.tooling.soap.sforce.com","FlowOutputFieldAssignment",0,-1,true), outputAssignments, outputAssignments__is_set);
    }

    /**
     * element : picklistField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean picklistField__is_set = false;

    private java.lang.String picklistField;

    public java.lang.String getPicklistField() {
      return picklistField;
    }

    public void setPicklistField(java.lang.String picklistField) {
      this.picklistField = picklistField;
      picklistField__is_set = true;
    }

    protected void setPicklistField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("picklistField", "urn:metadata.tooling.soap.sforce.com","picklistField","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setPicklistField(__typeMapper.readString(__in, _lookupTypeInfo("picklistField", "urn:metadata.tooling.soap.sforce.com","picklistField","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldPicklistField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("picklistField", "urn:metadata.tooling.soap.sforce.com","picklistField","http://www.w3.org/2001/XMLSchema","string",0,1,true), picklistField, picklistField__is_set);
    }

    /**
     * element : picklistObject of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean picklistObject__is_set = false;

    private java.lang.String picklistObject;

    public java.lang.String getPicklistObject() {
      return picklistObject;
    }

    public void setPicklistObject(java.lang.String picklistObject) {
      this.picklistObject = picklistObject;
      picklistObject__is_set = true;
    }

    protected void setPicklistObject(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("picklistObject", "urn:metadata.tooling.soap.sforce.com","picklistObject","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setPicklistObject(__typeMapper.readString(__in, _lookupTypeInfo("picklistObject", "urn:metadata.tooling.soap.sforce.com","picklistObject","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldPicklistObject(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("picklistObject", "urn:metadata.tooling.soap.sforce.com","picklistObject","http://www.w3.org/2001/XMLSchema","string",0,1,true), picklistObject, picklistObject__is_set);
    }

    /**
     * element : sortField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean sortField__is_set = false;

    private java.lang.String sortField;

    public java.lang.String getSortField() {
      return sortField;
    }

    public void setSortField(java.lang.String sortField) {
      this.sortField = sortField;
      sortField__is_set = true;
    }

    protected void setSortField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("sortField", "urn:metadata.tooling.soap.sforce.com","sortField","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setSortField(__typeMapper.readString(__in, _lookupTypeInfo("sortField", "urn:metadata.tooling.soap.sforce.com","sortField","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldSortField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("sortField", "urn:metadata.tooling.soap.sforce.com","sortField","http://www.w3.org/2001/XMLSchema","string",0,1,true), sortField, sortField__is_set);
    }

    /**
     * element : sortOrder of type {urn:tooling.soap.sforce.com}SortOrder
     * java type: com.sforce.soap.tooling.SortOrder
     */
    private boolean sortOrder__is_set = false;

    private com.sforce.soap.tooling.SortOrder sortOrder;

    public com.sforce.soap.tooling.SortOrder getSortOrder() {
      return sortOrder;
    }

    public void setSortOrder(com.sforce.soap.tooling.SortOrder sortOrder) {
      this.sortOrder = sortOrder;
      sortOrder__is_set = true;
    }

    protected void setSortOrder(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("sortOrder", "urn:metadata.tooling.soap.sforce.com","sortOrder","urn:tooling.soap.sforce.com","SortOrder",0,1,true))) {
        setSortOrder((com.sforce.soap.tooling.SortOrder)__typeMapper.readObject(__in, _lookupTypeInfo("sortOrder", "urn:metadata.tooling.soap.sforce.com","sortOrder","urn:tooling.soap.sforce.com","SortOrder",0,1,true), com.sforce.soap.tooling.SortOrder.class));
      }
    }

    private void writeFieldSortOrder(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("sortOrder", "urn:metadata.tooling.soap.sforce.com","sortOrder","urn:tooling.soap.sforce.com","SortOrder",0,1,true), sortOrder, sortOrder__is_set);
    }

    /**
     * element : valueField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean valueField__is_set = false;

    private java.lang.String valueField;

    public java.lang.String getValueField() {
      return valueField;
    }

    public void setValueField(java.lang.String valueField) {
      this.valueField = valueField;
      valueField__is_set = true;
    }

    protected void setValueField(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("valueField", "urn:metadata.tooling.soap.sforce.com","valueField","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setValueField(__typeMapper.readString(__in, _lookupTypeInfo("valueField", "urn:metadata.tooling.soap.sforce.com","valueField","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldValueField(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("valueField", "urn:metadata.tooling.soap.sforce.com","valueField","http://www.w3.org/2001/XMLSchema","string",0,1,true), valueField, valueField__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "FlowDynamicChoiceSet");
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
      sb.append("[FlowDynamicChoiceSet ");
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
      writeFieldDataType(__out, __typeMapper);
      writeFieldDisplayField(__out, __typeMapper);
      writeFieldFilters(__out, __typeMapper);
      writeFieldLimit(__out, __typeMapper);
      writeFieldObject(__out, __typeMapper);
      writeFieldOutputAssignments(__out, __typeMapper);
      writeFieldPicklistField(__out, __typeMapper);
      writeFieldPicklistObject(__out, __typeMapper);
      writeFieldSortField(__out, __typeMapper);
      writeFieldSortOrder(__out, __typeMapper);
      writeFieldValueField(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setDataType(__in, __typeMapper);
      setDisplayField(__in, __typeMapper);
      setFilters(__in, __typeMapper);
      setLimit(__in, __typeMapper);
      setObject(__in, __typeMapper);
      setOutputAssignments(__in, __typeMapper);
      setPicklistField(__in, __typeMapper);
      setPicklistObject(__in, __typeMapper);
      setSortField(__in, __typeMapper);
      setSortOrder(__in, __typeMapper);
      setValueField(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "dataType", dataType);
      toStringHelper(sb, "displayField", displayField);
      toStringHelper(sb, "filters", filters);
      toStringHelper(sb, "limit", limit);
      toStringHelper(sb, "object", object);
      toStringHelper(sb, "outputAssignments", outputAssignments);
      toStringHelper(sb, "picklistField", picklistField);
      toStringHelper(sb, "picklistObject", picklistObject);
      toStringHelper(sb, "sortField", sortField);
      toStringHelper(sb, "sortOrder", sortOrder);
      toStringHelper(sb, "valueField", valueField);
    }


}
