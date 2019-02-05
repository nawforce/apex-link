package com.sforce.soap.tooling.sobject;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class CustomTab extends com.sforce.soap.tooling.sobject.SObject {

    /**
     * Constructor
     */
    public CustomTab() {}

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
     * element : ContentId of type {urn:tooling.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private boolean ContentId__is_set = false;

    private java.lang.String ContentId;

    public java.lang.String getContentId() {
      return ContentId;
    }

    public void setContentId(java.lang.String ContentId) {
      this.ContentId = ContentId;
      ContentId__is_set = true;
    }

    protected void setContentId(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("ContentId", "urn:sobject.tooling.soap.sforce.com","ContentId","urn:tooling.soap.sforce.com","ID",0,1,true))) {
        setContentId(__typeMapper.readString(__in, _lookupTypeInfo("ContentId", "urn:sobject.tooling.soap.sforce.com","ContentId","urn:tooling.soap.sforce.com","ID",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldContentId(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("ContentId", "urn:sobject.tooling.soap.sforce.com","ContentId","urn:tooling.soap.sforce.com","ID",0,1,true), ContentId, ContentId__is_set);
    }

    /**
     * element : CreatedBy of type {urn:sobject.tooling.soap.sforce.com}User
     * java type: com.sforce.soap.tooling.sobject.User
     */
    private boolean CreatedBy__is_set = false;

    private com.sforce.soap.tooling.sobject.User CreatedBy;

    public com.sforce.soap.tooling.sobject.User getCreatedBy() {
      return CreatedBy;
    }

    public void setCreatedBy(com.sforce.soap.tooling.sobject.User CreatedBy) {
      this.CreatedBy = CreatedBy;
      CreatedBy__is_set = true;
    }

    protected void setCreatedBy(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("CreatedBy", "urn:sobject.tooling.soap.sforce.com","CreatedBy","urn:sobject.tooling.soap.sforce.com","User",0,1,true))) {
        setCreatedBy((com.sforce.soap.tooling.sobject.User)__typeMapper.readObject(__in, _lookupTypeInfo("CreatedBy", "urn:sobject.tooling.soap.sforce.com","CreatedBy","urn:sobject.tooling.soap.sforce.com","User",0,1,true), com.sforce.soap.tooling.sobject.User.class));
      }
    }

    private void writeFieldCreatedBy(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("CreatedBy", "urn:sobject.tooling.soap.sforce.com","CreatedBy","urn:sobject.tooling.soap.sforce.com","User",0,1,true), CreatedBy, CreatedBy__is_set);
    }

    /**
     * element : CreatedById of type {urn:tooling.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private boolean CreatedById__is_set = false;

    private java.lang.String CreatedById;

    public java.lang.String getCreatedById() {
      return CreatedById;
    }

    public void setCreatedById(java.lang.String CreatedById) {
      this.CreatedById = CreatedById;
      CreatedById__is_set = true;
    }

    protected void setCreatedById(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("CreatedById", "urn:sobject.tooling.soap.sforce.com","CreatedById","urn:tooling.soap.sforce.com","ID",0,1,true))) {
        setCreatedById(__typeMapper.readString(__in, _lookupTypeInfo("CreatedById", "urn:sobject.tooling.soap.sforce.com","CreatedById","urn:tooling.soap.sforce.com","ID",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldCreatedById(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("CreatedById", "urn:sobject.tooling.soap.sforce.com","CreatedById","urn:tooling.soap.sforce.com","ID",0,1,true), CreatedById, CreatedById__is_set);
    }

    /**
     * element : CreatedDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private boolean CreatedDate__is_set = false;

    private java.util.Calendar CreatedDate;

    public java.util.Calendar getCreatedDate() {
      return CreatedDate;
    }

    public void setCreatedDate(java.util.Calendar CreatedDate) {
      this.CreatedDate = CreatedDate;
      CreatedDate__is_set = true;
    }

    protected void setCreatedDate(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("CreatedDate", "urn:sobject.tooling.soap.sforce.com","CreatedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true))) {
        setCreatedDate((java.util.Calendar)__typeMapper.readObject(__in, _lookupTypeInfo("CreatedDate", "urn:sobject.tooling.soap.sforce.com","CreatedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true), java.util.Calendar.class));
      }
    }

    private void writeFieldCreatedDate(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("CreatedDate", "urn:sobject.tooling.soap.sforce.com","CreatedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true), CreatedDate, CreatedDate__is_set);
    }

    /**
     * element : Description of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean Description__is_set = false;

    private java.lang.String Description;

    public java.lang.String getDescription() {
      return Description;
    }

    public void setDescription(java.lang.String Description) {
      this.Description = Description;
      Description__is_set = true;
    }

    protected void setDescription(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("Description", "urn:sobject.tooling.soap.sforce.com","Description","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setDescription(__typeMapper.readString(__in, _lookupTypeInfo("Description", "urn:sobject.tooling.soap.sforce.com","Description","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDescription(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("Description", "urn:sobject.tooling.soap.sforce.com","Description","http://www.w3.org/2001/XMLSchema","string",0,1,true), Description, Description__is_set);
    }

    /**
     * element : DeveloperName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean DeveloperName__is_set = false;

    private java.lang.String DeveloperName;

    public java.lang.String getDeveloperName() {
      return DeveloperName;
    }

    public void setDeveloperName(java.lang.String DeveloperName) {
      this.DeveloperName = DeveloperName;
      DeveloperName__is_set = true;
    }

    protected void setDeveloperName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("DeveloperName", "urn:sobject.tooling.soap.sforce.com","DeveloperName","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setDeveloperName(__typeMapper.readString(__in, _lookupTypeInfo("DeveloperName", "urn:sobject.tooling.soap.sforce.com","DeveloperName","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldDeveloperName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("DeveloperName", "urn:sobject.tooling.soap.sforce.com","DeveloperName","http://www.w3.org/2001/XMLSchema","string",0,1,true), DeveloperName, DeveloperName__is_set);
    }

    /**
     * element : EncodingKey of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean EncodingKey__is_set = false;

    private java.lang.String EncodingKey;

    public java.lang.String getEncodingKey() {
      return EncodingKey;
    }

    public void setEncodingKey(java.lang.String EncodingKey) {
      this.EncodingKey = EncodingKey;
      EncodingKey__is_set = true;
    }

    protected void setEncodingKey(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("EncodingKey", "urn:sobject.tooling.soap.sforce.com","EncodingKey","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setEncodingKey(__typeMapper.readString(__in, _lookupTypeInfo("EncodingKey", "urn:sobject.tooling.soap.sforce.com","EncodingKey","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldEncodingKey(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("EncodingKey", "urn:sobject.tooling.soap.sforce.com","EncodingKey","http://www.w3.org/2001/XMLSchema","string",0,1,true), EncodingKey, EncodingKey__is_set);
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
     * element : HasSidebar of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: java.lang.Boolean
     */
    private boolean HasSidebar__is_set = false;

    private java.lang.Boolean HasSidebar;

    public java.lang.Boolean getHasSidebar() {
      return HasSidebar;
    }

    public void setHasSidebar(java.lang.Boolean HasSidebar) {
      this.HasSidebar = HasSidebar;
      HasSidebar__is_set = true;
    }

    protected void setHasSidebar(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("HasSidebar", "urn:sobject.tooling.soap.sforce.com","HasSidebar","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setHasSidebar((java.lang.Boolean)__typeMapper.readObject(__in, _lookupTypeInfo("HasSidebar", "urn:sobject.tooling.soap.sforce.com","HasSidebar","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), java.lang.Boolean.class));
      }
    }

    private void writeFieldHasSidebar(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("HasSidebar", "urn:sobject.tooling.soap.sforce.com","HasSidebar","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), HasSidebar, HasSidebar__is_set);
    }

    /**
     * element : IconId of type {urn:tooling.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private boolean IconId__is_set = false;

    private java.lang.String IconId;

    public java.lang.String getIconId() {
      return IconId;
    }

    public void setIconId(java.lang.String IconId) {
      this.IconId = IconId;
      IconId__is_set = true;
    }

    protected void setIconId(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("IconId", "urn:sobject.tooling.soap.sforce.com","IconId","urn:tooling.soap.sforce.com","ID",0,1,true))) {
        setIconId(__typeMapper.readString(__in, _lookupTypeInfo("IconId", "urn:sobject.tooling.soap.sforce.com","IconId","urn:tooling.soap.sforce.com","ID",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldIconId(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("IconId", "urn:sobject.tooling.soap.sforce.com","IconId","urn:tooling.soap.sforce.com","ID",0,1,true), IconId, IconId__is_set);
    }

    /**
     * element : LastModifiedBy of type {urn:sobject.tooling.soap.sforce.com}User
     * java type: com.sforce.soap.tooling.sobject.User
     */
    private boolean LastModifiedBy__is_set = false;

    private com.sforce.soap.tooling.sobject.User LastModifiedBy;

    public com.sforce.soap.tooling.sobject.User getLastModifiedBy() {
      return LastModifiedBy;
    }

    public void setLastModifiedBy(com.sforce.soap.tooling.sobject.User LastModifiedBy) {
      this.LastModifiedBy = LastModifiedBy;
      LastModifiedBy__is_set = true;
    }

    protected void setLastModifiedBy(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("LastModifiedBy", "urn:sobject.tooling.soap.sforce.com","LastModifiedBy","urn:sobject.tooling.soap.sforce.com","User",0,1,true))) {
        setLastModifiedBy((com.sforce.soap.tooling.sobject.User)__typeMapper.readObject(__in, _lookupTypeInfo("LastModifiedBy", "urn:sobject.tooling.soap.sforce.com","LastModifiedBy","urn:sobject.tooling.soap.sforce.com","User",0,1,true), com.sforce.soap.tooling.sobject.User.class));
      }
    }

    private void writeFieldLastModifiedBy(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("LastModifiedBy", "urn:sobject.tooling.soap.sforce.com","LastModifiedBy","urn:sobject.tooling.soap.sforce.com","User",0,1,true), LastModifiedBy, LastModifiedBy__is_set);
    }

    /**
     * element : LastModifiedById of type {urn:tooling.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private boolean LastModifiedById__is_set = false;

    private java.lang.String LastModifiedById;

    public java.lang.String getLastModifiedById() {
      return LastModifiedById;
    }

    public void setLastModifiedById(java.lang.String LastModifiedById) {
      this.LastModifiedById = LastModifiedById;
      LastModifiedById__is_set = true;
    }

    protected void setLastModifiedById(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("LastModifiedById", "urn:sobject.tooling.soap.sforce.com","LastModifiedById","urn:tooling.soap.sforce.com","ID",0,1,true))) {
        setLastModifiedById(__typeMapper.readString(__in, _lookupTypeInfo("LastModifiedById", "urn:sobject.tooling.soap.sforce.com","LastModifiedById","urn:tooling.soap.sforce.com","ID",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldLastModifiedById(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("LastModifiedById", "urn:sobject.tooling.soap.sforce.com","LastModifiedById","urn:tooling.soap.sforce.com","ID",0,1,true), LastModifiedById, LastModifiedById__is_set);
    }

    /**
     * element : LastModifiedDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private boolean LastModifiedDate__is_set = false;

    private java.util.Calendar LastModifiedDate;

    public java.util.Calendar getLastModifiedDate() {
      return LastModifiedDate;
    }

    public void setLastModifiedDate(java.util.Calendar LastModifiedDate) {
      this.LastModifiedDate = LastModifiedDate;
      LastModifiedDate__is_set = true;
    }

    protected void setLastModifiedDate(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("LastModifiedDate", "urn:sobject.tooling.soap.sforce.com","LastModifiedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true))) {
        setLastModifiedDate((java.util.Calendar)__typeMapper.readObject(__in, _lookupTypeInfo("LastModifiedDate", "urn:sobject.tooling.soap.sforce.com","LastModifiedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true), java.util.Calendar.class));
      }
    }

    private void writeFieldLastModifiedDate(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("LastModifiedDate", "urn:sobject.tooling.soap.sforce.com","LastModifiedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true), LastModifiedDate, LastModifiedDate__is_set);
    }

    /**
     * element : ManageableState of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean ManageableState__is_set = false;

    private java.lang.String ManageableState;

    public java.lang.String getManageableState() {
      return ManageableState;
    }

    public void setManageableState(java.lang.String ManageableState) {
      this.ManageableState = ManageableState;
      ManageableState__is_set = true;
    }

    protected void setManageableState(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("ManageableState", "urn:sobject.tooling.soap.sforce.com","ManageableState","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setManageableState(__typeMapper.readString(__in, _lookupTypeInfo("ManageableState", "urn:sobject.tooling.soap.sforce.com","ManageableState","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldManageableState(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("ManageableState", "urn:sobject.tooling.soap.sforce.com","ManageableState","http://www.w3.org/2001/XMLSchema","string",0,1,true), ManageableState, ManageableState__is_set);
    }

    /**
     * element : MasterLabel of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean MasterLabel__is_set = false;

    private java.lang.String MasterLabel;

    public java.lang.String getMasterLabel() {
      return MasterLabel;
    }

    public void setMasterLabel(java.lang.String MasterLabel) {
      this.MasterLabel = MasterLabel;
      MasterLabel__is_set = true;
    }

    protected void setMasterLabel(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("MasterLabel", "urn:sobject.tooling.soap.sforce.com","MasterLabel","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setMasterLabel(__typeMapper.readString(__in, _lookupTypeInfo("MasterLabel", "urn:sobject.tooling.soap.sforce.com","MasterLabel","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldMasterLabel(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("MasterLabel", "urn:sobject.tooling.soap.sforce.com","MasterLabel","http://www.w3.org/2001/XMLSchema","string",0,1,true), MasterLabel, MasterLabel__is_set);
    }

    /**
     * element : Metadata of type {urn:metadata.tooling.soap.sforce.com}CustomTab
     * java type: com.sforce.soap.tooling.metadata.CustomTab
     */
    private boolean Metadata__is_set = false;

    private com.sforce.soap.tooling.metadata.CustomTab Metadata;

    public com.sforce.soap.tooling.metadata.CustomTab getMetadata() {
      return Metadata;
    }

    public void setMetadata(com.sforce.soap.tooling.metadata.CustomTab Metadata) {
      this.Metadata = Metadata;
      Metadata__is_set = true;
    }

    protected void setMetadata(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("Metadata", "urn:sobject.tooling.soap.sforce.com","Metadata","urn:metadata.tooling.soap.sforce.com","CustomTab",0,1,true))) {
        setMetadata((com.sforce.soap.tooling.metadata.CustomTab)__typeMapper.readObject(__in, _lookupTypeInfo("Metadata", "urn:sobject.tooling.soap.sforce.com","Metadata","urn:metadata.tooling.soap.sforce.com","CustomTab",0,1,true), com.sforce.soap.tooling.metadata.CustomTab.class));
      }
    }

    private void writeFieldMetadata(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("Metadata", "urn:sobject.tooling.soap.sforce.com","Metadata","urn:metadata.tooling.soap.sforce.com","CustomTab",0,1,true), Metadata, Metadata__is_set);
    }

    /**
     * element : MotifName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean MotifName__is_set = false;

    private java.lang.String MotifName;

    public java.lang.String getMotifName() {
      return MotifName;
    }

    public void setMotifName(java.lang.String MotifName) {
      this.MotifName = MotifName;
      MotifName__is_set = true;
    }

    protected void setMotifName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("MotifName", "urn:sobject.tooling.soap.sforce.com","MotifName","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setMotifName(__typeMapper.readString(__in, _lookupTypeInfo("MotifName", "urn:sobject.tooling.soap.sforce.com","MotifName","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldMotifName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("MotifName", "urn:sobject.tooling.soap.sforce.com","MotifName","http://www.w3.org/2001/XMLSchema","string",0,1,true), MotifName, MotifName__is_set);
    }

    /**
     * element : NamespacePrefix of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean NamespacePrefix__is_set = false;

    private java.lang.String NamespacePrefix;

    public java.lang.String getNamespacePrefix() {
      return NamespacePrefix;
    }

    public void setNamespacePrefix(java.lang.String NamespacePrefix) {
      this.NamespacePrefix = NamespacePrefix;
      NamespacePrefix__is_set = true;
    }

    protected void setNamespacePrefix(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("NamespacePrefix", "urn:sobject.tooling.soap.sforce.com","NamespacePrefix","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setNamespacePrefix(__typeMapper.readString(__in, _lookupTypeInfo("NamespacePrefix", "urn:sobject.tooling.soap.sforce.com","NamespacePrefix","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldNamespacePrefix(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("NamespacePrefix", "urn:sobject.tooling.soap.sforce.com","NamespacePrefix","http://www.w3.org/2001/XMLSchema","string",0,1,true), NamespacePrefix, NamespacePrefix__is_set);
    }

    /**
     * element : Type of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean Type__is_set = false;

    private java.lang.String Type;

    public java.lang.String getType() {
      return Type;
    }

    public void setType(java.lang.String Type) {
      this.Type = Type;
      Type__is_set = true;
    }

    protected void setType(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("Type", "urn:sobject.tooling.soap.sforce.com","Type","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setType(__typeMapper.readString(__in, _lookupTypeInfo("Type", "urn:sobject.tooling.soap.sforce.com","Type","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldType(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("Type", "urn:sobject.tooling.soap.sforce.com","Type","http://www.w3.org/2001/XMLSchema","string",0,1,true), Type, Type__is_set);
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
      __typeMapper.writeXsiType(__out, "urn:sobject.tooling.soap.sforce.com", "CustomTab");
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
      sb.append("[CustomTab ");
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
      writeFieldContentId(__out, __typeMapper);
      writeFieldCreatedBy(__out, __typeMapper);
      writeFieldCreatedById(__out, __typeMapper);
      writeFieldCreatedDate(__out, __typeMapper);
      writeFieldDescription(__out, __typeMapper);
      writeFieldDeveloperName(__out, __typeMapper);
      writeFieldEncodingKey(__out, __typeMapper);
      writeFieldFullName(__out, __typeMapper);
      writeFieldHasSidebar(__out, __typeMapper);
      writeFieldIconId(__out, __typeMapper);
      writeFieldLastModifiedBy(__out, __typeMapper);
      writeFieldLastModifiedById(__out, __typeMapper);
      writeFieldLastModifiedDate(__out, __typeMapper);
      writeFieldManageableState(__out, __typeMapper);
      writeFieldMasterLabel(__out, __typeMapper);
      writeFieldMetadata(__out, __typeMapper);
      writeFieldMotifName(__out, __typeMapper);
      writeFieldNamespacePrefix(__out, __typeMapper);
      writeFieldType(__out, __typeMapper);
      writeFieldUrl(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setContentId(__in, __typeMapper);
      setCreatedBy(__in, __typeMapper);
      setCreatedById(__in, __typeMapper);
      setCreatedDate(__in, __typeMapper);
      setDescription(__in, __typeMapper);
      setDeveloperName(__in, __typeMapper);
      setEncodingKey(__in, __typeMapper);
      setFullName(__in, __typeMapper);
      setHasSidebar(__in, __typeMapper);
      setIconId(__in, __typeMapper);
      setLastModifiedBy(__in, __typeMapper);
      setLastModifiedById(__in, __typeMapper);
      setLastModifiedDate(__in, __typeMapper);
      setManageableState(__in, __typeMapper);
      setMasterLabel(__in, __typeMapper);
      setMetadata(__in, __typeMapper);
      setMotifName(__in, __typeMapper);
      setNamespacePrefix(__in, __typeMapper);
      setType(__in, __typeMapper);
      setUrl(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "ContentId", ContentId);
      toStringHelper(sb, "CreatedBy", CreatedBy);
      toStringHelper(sb, "CreatedById", CreatedById);
      toStringHelper(sb, "CreatedDate", CreatedDate);
      toStringHelper(sb, "Description", Description);
      toStringHelper(sb, "DeveloperName", DeveloperName);
      toStringHelper(sb, "EncodingKey", EncodingKey);
      toStringHelper(sb, "FullName", FullName);
      toStringHelper(sb, "HasSidebar", HasSidebar);
      toStringHelper(sb, "IconId", IconId);
      toStringHelper(sb, "LastModifiedBy", LastModifiedBy);
      toStringHelper(sb, "LastModifiedById", LastModifiedById);
      toStringHelper(sb, "LastModifiedDate", LastModifiedDate);
      toStringHelper(sb, "ManageableState", ManageableState);
      toStringHelper(sb, "MasterLabel", MasterLabel);
      toStringHelper(sb, "Metadata", Metadata);
      toStringHelper(sb, "MotifName", MotifName);
      toStringHelper(sb, "NamespacePrefix", NamespacePrefix);
      toStringHelper(sb, "Type", Type);
      toStringHelper(sb, "Url", Url);
    }


}
