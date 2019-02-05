package com.sforce.soap.tooling.sobject;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class SetupNode extends com.sforce.soap.tooling.sobject.SObject {

    /**
     * Constructor
     */
    public SetupNode() {}

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
     * element : ExternalId of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean ExternalId__is_set = false;

    private java.lang.String ExternalId;

    public java.lang.String getExternalId() {
      return ExternalId;
    }

    public void setExternalId(java.lang.String ExternalId) {
      this.ExternalId = ExternalId;
      ExternalId__is_set = true;
    }

    protected void setExternalId(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("ExternalId", "urn:sobject.tooling.soap.sforce.com","ExternalId","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setExternalId(__typeMapper.readString(__in, _lookupTypeInfo("ExternalId", "urn:sobject.tooling.soap.sforce.com","ExternalId","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldExternalId(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("ExternalId", "urn:sobject.tooling.soap.sforce.com","ExternalId","http://www.w3.org/2001/XMLSchema","string",0,1,true), ExternalId, ExternalId__is_set);
    }

    /**
     * element : FullName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean FullName__is_set = false;

    private java.lang.String FullName;

    public java.lang.String getFullName() {
      return FullName;
    }

    public void setFullName(java.lang.String FullName) {
      this.FullName = FullName;
      FullName__is_set = true;
    }

    protected void setFullName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("FullName", "urn:sobject.tooling.soap.sforce.com","FullName","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setFullName(__typeMapper.readString(__in, _lookupTypeInfo("FullName", "urn:sobject.tooling.soap.sforce.com","FullName","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldFullName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("FullName", "urn:sobject.tooling.soap.sforce.com","FullName","http://www.w3.org/2001/XMLSchema","string",0,1,true), FullName, FullName__is_set);
    }

    /**
     * element : IconUrl of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean IconUrl__is_set = false;

    private java.lang.String IconUrl;

    public java.lang.String getIconUrl() {
      return IconUrl;
    }

    public void setIconUrl(java.lang.String IconUrl) {
      this.IconUrl = IconUrl;
      IconUrl__is_set = true;
    }

    protected void setIconUrl(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("IconUrl", "urn:sobject.tooling.soap.sforce.com","IconUrl","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setIconUrl(__typeMapper.readString(__in, _lookupTypeInfo("IconUrl", "urn:sobject.tooling.soap.sforce.com","IconUrl","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldIconUrl(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("IconUrl", "urn:sobject.tooling.soap.sforce.com","IconUrl","http://www.w3.org/2001/XMLSchema","string",0,1,true), IconUrl, IconUrl__is_set);
    }

    /**
     * element : IsFullPage of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: java.lang.Boolean
     */
    private boolean IsFullPage__is_set = false;

    private java.lang.Boolean IsFullPage;

    public java.lang.Boolean getIsFullPage() {
      return IsFullPage;
    }

    public void setIsFullPage(java.lang.Boolean IsFullPage) {
      this.IsFullPage = IsFullPage;
      IsFullPage__is_set = true;
    }

    protected void setIsFullPage(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("IsFullPage", "urn:sobject.tooling.soap.sforce.com","IsFullPage","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsFullPage((java.lang.Boolean)__typeMapper.readObject(__in, _lookupTypeInfo("IsFullPage", "urn:sobject.tooling.soap.sforce.com","IsFullPage","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), java.lang.Boolean.class));
      }
    }

    private void writeFieldIsFullPage(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("IsFullPage", "urn:sobject.tooling.soap.sforce.com","IsFullPage","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), IsFullPage, IsFullPage__is_set);
    }

    /**
     * element : IsNew of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: java.lang.Boolean
     */
    private boolean IsNew__is_set = false;

    private java.lang.Boolean IsNew;

    public java.lang.Boolean getIsNew() {
      return IsNew;
    }

    public void setIsNew(java.lang.Boolean IsNew) {
      this.IsNew = IsNew;
      IsNew__is_set = true;
    }

    protected void setIsNew(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("IsNew", "urn:sobject.tooling.soap.sforce.com","IsNew","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsNew((java.lang.Boolean)__typeMapper.readObject(__in, _lookupTypeInfo("IsNew", "urn:sobject.tooling.soap.sforce.com","IsNew","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), java.lang.Boolean.class));
      }
    }

    private void writeFieldIsNew(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("IsNew", "urn:sobject.tooling.soap.sforce.com","IsNew","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), IsNew, IsNew__is_set);
    }

    /**
     * element : Label of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean Label__is_set = false;

    private java.lang.String Label;

    public java.lang.String getLabel() {
      return Label;
    }

    public void setLabel(java.lang.String Label) {
      this.Label = Label;
      Label__is_set = true;
    }

    protected void setLabel(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("Label", "urn:sobject.tooling.soap.sforce.com","Label","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setLabel(__typeMapper.readString(__in, _lookupTypeInfo("Label", "urn:sobject.tooling.soap.sforce.com","Label","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldLabel(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("Label", "urn:sobject.tooling.soap.sforce.com","Label","http://www.w3.org/2001/XMLSchema","string",0,1,true), Label, Label__is_set);
    }

    /**
     * element : NodeType of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean NodeType__is_set = false;

    private java.lang.String NodeType;

    public java.lang.String getNodeType() {
      return NodeType;
    }

    public void setNodeType(java.lang.String NodeType) {
      this.NodeType = NodeType;
      NodeType__is_set = true;
    }

    protected void setNodeType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("NodeType", "urn:sobject.tooling.soap.sforce.com","NodeType","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setNodeType(__typeMapper.readString(__in, _lookupTypeInfo("NodeType", "urn:sobject.tooling.soap.sforce.com","NodeType","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldNodeType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("NodeType", "urn:sobject.tooling.soap.sforce.com","NodeType","http://www.w3.org/2001/XMLSchema","string",0,1,true), NodeType, NodeType__is_set);
    }

    /**
     * element : RelatedEntities of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean RelatedEntities__is_set = false;

    private java.lang.String RelatedEntities;

    public java.lang.String getRelatedEntities() {
      return RelatedEntities;
    }

    public void setRelatedEntities(java.lang.String RelatedEntities) {
      this.RelatedEntities = RelatedEntities;
      RelatedEntities__is_set = true;
    }

    protected void setRelatedEntities(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("RelatedEntities", "urn:sobject.tooling.soap.sforce.com","RelatedEntities","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setRelatedEntities(__typeMapper.readString(__in, _lookupTypeInfo("RelatedEntities", "urn:sobject.tooling.soap.sforce.com","RelatedEntities","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldRelatedEntities(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("RelatedEntities", "urn:sobject.tooling.soap.sforce.com","RelatedEntities","http://www.w3.org/2001/XMLSchema","string",0,1,true), RelatedEntities, RelatedEntities__is_set);
    }

    /**
     * element : Tags of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean Tags__is_set = false;

    private java.lang.String Tags;

    public java.lang.String getTags() {
      return Tags;
    }

    public void setTags(java.lang.String Tags) {
      this.Tags = Tags;
      Tags__is_set = true;
    }

    protected void setTags(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("Tags", "urn:sobject.tooling.soap.sforce.com","Tags","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setTags(__typeMapper.readString(__in, _lookupTypeInfo("Tags", "urn:sobject.tooling.soap.sforce.com","Tags","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldTags(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("Tags", "urn:sobject.tooling.soap.sforce.com","Tags","http://www.w3.org/2001/XMLSchema","string",0,1,true), Tags, Tags__is_set);
    }

    /**
     * element : Url of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean Url__is_set = false;

    private java.lang.String Url;

    public java.lang.String getUrl() {
      return Url;
    }

    public void setUrl(java.lang.String Url) {
      this.Url = Url;
      Url__is_set = true;
    }

    protected void setUrl(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("Url", "urn:sobject.tooling.soap.sforce.com","Url","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setUrl(__typeMapper.readString(__in, _lookupTypeInfo("Url", "urn:sobject.tooling.soap.sforce.com","Url","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldUrl(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("Url", "urn:sobject.tooling.soap.sforce.com","Url","http://www.w3.org/2001/XMLSchema","string",0,1,true), Url, Url__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:sobject.tooling.soap.sforce.com", "SetupNode");
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
      sb.append("[SetupNode ");
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
      writeFieldExternalId(__out, __typeMapper);
      writeFieldFullName(__out, __typeMapper);
      writeFieldIconUrl(__out, __typeMapper);
      writeFieldIsFullPage(__out, __typeMapper);
      writeFieldIsNew(__out, __typeMapper);
      writeFieldLabel(__out, __typeMapper);
      writeFieldNodeType(__out, __typeMapper);
      writeFieldRelatedEntities(__out, __typeMapper);
      writeFieldTags(__out, __typeMapper);
      writeFieldUrl(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setExternalId(__in, __typeMapper);
      setFullName(__in, __typeMapper);
      setIconUrl(__in, __typeMapper);
      setIsFullPage(__in, __typeMapper);
      setIsNew(__in, __typeMapper);
      setLabel(__in, __typeMapper);
      setNodeType(__in, __typeMapper);
      setRelatedEntities(__in, __typeMapper);
      setTags(__in, __typeMapper);
      setUrl(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "ExternalId", ExternalId);
      toStringHelper(sb, "FullName", FullName);
      toStringHelper(sb, "IconUrl", IconUrl);
      toStringHelper(sb, "IsFullPage", IsFullPage);
      toStringHelper(sb, "IsNew", IsNew);
      toStringHelper(sb, "Label", Label);
      toStringHelper(sb, "NodeType", NodeType);
      toStringHelper(sb, "RelatedEntities", RelatedEntities);
      toStringHelper(sb, "Tags", Tags);
      toStringHelper(sb, "Url", Url);
    }


}
