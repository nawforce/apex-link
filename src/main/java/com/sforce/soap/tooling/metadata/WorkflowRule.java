package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class WorkflowRule extends com.sforce.soap.tooling.metadata.Metadata {

    /**
     * Constructor
     */
    public WorkflowRule() {}

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
     * element : actions of type {urn:metadata.tooling.soap.sforce.com}WorkflowActionReference
     * java type: com.sforce.soap.tooling.metadata.WorkflowActionReference[]
     */
    private boolean actions__is_set = false;

    private com.sforce.soap.tooling.metadata.WorkflowActionReference[] actions = new com.sforce.soap.tooling.metadata.WorkflowActionReference[0];

    public com.sforce.soap.tooling.metadata.WorkflowActionReference[] getActions() {
      return actions;
    }

    public void setActions(com.sforce.soap.tooling.metadata.WorkflowActionReference[] actions) {
      this.actions = actions;
      actions__is_set = true;
    }

    protected void setActions(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("actions", "urn:metadata.tooling.soap.sforce.com","actions","urn:metadata.tooling.soap.sforce.com","WorkflowActionReference",0,-1,true))) {
        setActions((com.sforce.soap.tooling.metadata.WorkflowActionReference[])__typeMapper.readObject(__in, _lookupTypeInfo("actions", "urn:metadata.tooling.soap.sforce.com","actions","urn:metadata.tooling.soap.sforce.com","WorkflowActionReference",0,-1,true), com.sforce.soap.tooling.metadata.WorkflowActionReference[].class));
      }
    }

    private void writeFieldActions(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("actions", "urn:metadata.tooling.soap.sforce.com","actions","urn:metadata.tooling.soap.sforce.com","WorkflowActionReference",0,-1,true), actions, actions__is_set);
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
     * element : booleanFilter of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean booleanFilter__is_set = false;

    private java.lang.String booleanFilter;

    public java.lang.String getBooleanFilter() {
      return booleanFilter;
    }

    public void setBooleanFilter(java.lang.String booleanFilter) {
      this.booleanFilter = booleanFilter;
      booleanFilter__is_set = true;
    }

    protected void setBooleanFilter(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("booleanFilter", "urn:metadata.tooling.soap.sforce.com","booleanFilter","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setBooleanFilter(__typeMapper.readString(__in, _lookupTypeInfo("booleanFilter", "urn:metadata.tooling.soap.sforce.com","booleanFilter","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldBooleanFilter(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("booleanFilter", "urn:metadata.tooling.soap.sforce.com","booleanFilter","http://www.w3.org/2001/XMLSchema","string",0,1,true), booleanFilter, booleanFilter__is_set);
    }

    /**
     * element : criteriaItems of type {urn:metadata.tooling.soap.sforce.com}FilterItem
     * java type: com.sforce.soap.tooling.metadata.FilterItem[]
     */
    private boolean criteriaItems__is_set = false;

    private com.sforce.soap.tooling.metadata.FilterItem[] criteriaItems = new com.sforce.soap.tooling.metadata.FilterItem[0];

    public com.sforce.soap.tooling.metadata.FilterItem[] getCriteriaItems() {
      return criteriaItems;
    }

    public void setCriteriaItems(com.sforce.soap.tooling.metadata.FilterItem[] criteriaItems) {
      this.criteriaItems = criteriaItems;
      criteriaItems__is_set = true;
    }

    protected void setCriteriaItems(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("criteriaItems", "urn:metadata.tooling.soap.sforce.com","criteriaItems","urn:metadata.tooling.soap.sforce.com","FilterItem",0,-1,true))) {
        setCriteriaItems((com.sforce.soap.tooling.metadata.FilterItem[])__typeMapper.readObject(__in, _lookupTypeInfo("criteriaItems", "urn:metadata.tooling.soap.sforce.com","criteriaItems","urn:metadata.tooling.soap.sforce.com","FilterItem",0,-1,true), com.sforce.soap.tooling.metadata.FilterItem[].class));
      }
    }

    private void writeFieldCriteriaItems(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("criteriaItems", "urn:metadata.tooling.soap.sforce.com","criteriaItems","urn:metadata.tooling.soap.sforce.com","FilterItem",0,-1,true), criteriaItems, criteriaItems__is_set);
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
     * element : formula of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean formula__is_set = false;

    private java.lang.String formula;

    public java.lang.String getFormula() {
      return formula;
    }

    public void setFormula(java.lang.String formula) {
      this.formula = formula;
      formula__is_set = true;
    }

    protected void setFormula(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("formula", "urn:metadata.tooling.soap.sforce.com","formula","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setFormula(__typeMapper.readString(__in, _lookupTypeInfo("formula", "urn:metadata.tooling.soap.sforce.com","formula","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldFormula(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("formula", "urn:metadata.tooling.soap.sforce.com","formula","http://www.w3.org/2001/XMLSchema","string",0,1,true), formula, formula__is_set);
    }

    /**
     * element : triggerType of type {urn:tooling.soap.sforce.com}WorkflowTriggerTypes
     * java type: com.sforce.soap.tooling.WorkflowTriggerTypes
     */
    private boolean triggerType__is_set = false;

    private com.sforce.soap.tooling.WorkflowTriggerTypes triggerType;

    public com.sforce.soap.tooling.WorkflowTriggerTypes getTriggerType() {
      return triggerType;
    }

    public void setTriggerType(com.sforce.soap.tooling.WorkflowTriggerTypes triggerType) {
      this.triggerType = triggerType;
      triggerType__is_set = true;
    }

    protected void setTriggerType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("triggerType", "urn:metadata.tooling.soap.sforce.com","triggerType","urn:tooling.soap.sforce.com","WorkflowTriggerTypes",1,1,true))) {
        setTriggerType((com.sforce.soap.tooling.WorkflowTriggerTypes)__typeMapper.readObject(__in, _lookupTypeInfo("triggerType", "urn:metadata.tooling.soap.sforce.com","triggerType","urn:tooling.soap.sforce.com","WorkflowTriggerTypes",1,1,true), com.sforce.soap.tooling.WorkflowTriggerTypes.class));
      }
    }

    private void writeFieldTriggerType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("triggerType", "urn:metadata.tooling.soap.sforce.com","triggerType","urn:tooling.soap.sforce.com","WorkflowTriggerTypes",1,1,true), triggerType, triggerType__is_set);
    }

    /**
     * element : workflowTimeTriggers of type {urn:metadata.tooling.soap.sforce.com}WorkflowTimeTrigger
     * java type: com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[]
     */
    private boolean workflowTimeTriggers__is_set = false;

    private com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[] workflowTimeTriggers = new com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[0];

    public com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[] getWorkflowTimeTriggers() {
      return workflowTimeTriggers;
    }

    public void setWorkflowTimeTriggers(com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[] workflowTimeTriggers) {
      this.workflowTimeTriggers = workflowTimeTriggers;
      workflowTimeTriggers__is_set = true;
    }

    protected void setWorkflowTimeTriggers(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("workflowTimeTriggers", "urn:metadata.tooling.soap.sforce.com","workflowTimeTriggers","urn:metadata.tooling.soap.sforce.com","WorkflowTimeTrigger",0,-1,true))) {
        setWorkflowTimeTriggers((com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[])__typeMapper.readObject(__in, _lookupTypeInfo("workflowTimeTriggers", "urn:metadata.tooling.soap.sforce.com","workflowTimeTriggers","urn:metadata.tooling.soap.sforce.com","WorkflowTimeTrigger",0,-1,true), com.sforce.soap.tooling.metadata.WorkflowTimeTrigger[].class));
      }
    }

    private void writeFieldWorkflowTimeTriggers(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("workflowTimeTriggers", "urn:metadata.tooling.soap.sforce.com","workflowTimeTriggers","urn:metadata.tooling.soap.sforce.com","WorkflowTimeTrigger",0,-1,true), workflowTimeTriggers, workflowTimeTriggers__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "WorkflowRule");
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
      sb.append("[WorkflowRule ");
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
      writeFieldActions(__out, __typeMapper);
      writeFieldActive(__out, __typeMapper);
      writeFieldBooleanFilter(__out, __typeMapper);
      writeFieldCriteriaItems(__out, __typeMapper);
      writeFieldDescription(__out, __typeMapper);
      writeFieldFormula(__out, __typeMapper);
      writeFieldTriggerType(__out, __typeMapper);
      writeFieldWorkflowTimeTriggers(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setActions(__in, __typeMapper);
      setActive(__in, __typeMapper);
      setBooleanFilter(__in, __typeMapper);
      setCriteriaItems(__in, __typeMapper);
      setDescription(__in, __typeMapper);
      setFormula(__in, __typeMapper);
      setTriggerType(__in, __typeMapper);
      setWorkflowTimeTriggers(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "actions", actions);
      toStringHelper(sb, "active", active);
      toStringHelper(sb, "booleanFilter", booleanFilter);
      toStringHelper(sb, "criteriaItems", criteriaItems);
      toStringHelper(sb, "description", description);
      toStringHelper(sb, "formula", formula);
      toStringHelper(sb, "triggerType", triggerType);
      toStringHelper(sb, "workflowTimeTriggers", workflowTimeTriggers);
    }


}
