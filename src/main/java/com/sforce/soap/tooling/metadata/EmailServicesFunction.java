package com.sforce.soap.tooling.metadata;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class EmailServicesFunction extends com.sforce.soap.tooling.metadata.Metadata {

    /**
     * Constructor
     */
    public EmailServicesFunction() {}

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
     * element : apexClass of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean apexClass__is_set = false;

    private java.lang.String apexClass;

    public java.lang.String getApexClass() {
      return apexClass;
    }

    public void setApexClass(java.lang.String apexClass) {
      this.apexClass = apexClass;
      apexClass__is_set = true;
    }

    protected void setApexClass(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("apexClass", "urn:metadata.tooling.soap.sforce.com","apexClass","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setApexClass(__typeMapper.readString(__in, _lookupTypeInfo("apexClass", "urn:metadata.tooling.soap.sforce.com","apexClass","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldApexClass(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("apexClass", "urn:metadata.tooling.soap.sforce.com","apexClass","http://www.w3.org/2001/XMLSchema","string",1,1,true), apexClass, apexClass__is_set);
    }

    /**
     * element : attachmentOption of type {urn:tooling.soap.sforce.com}EmailServicesAttOptions
     * java type: com.sforce.soap.tooling.EmailServicesAttOptions
     */
    private boolean attachmentOption__is_set = false;

    private com.sforce.soap.tooling.EmailServicesAttOptions attachmentOption;

    public com.sforce.soap.tooling.EmailServicesAttOptions getAttachmentOption() {
      return attachmentOption;
    }

    public void setAttachmentOption(com.sforce.soap.tooling.EmailServicesAttOptions attachmentOption) {
      this.attachmentOption = attachmentOption;
      attachmentOption__is_set = true;
    }

    protected void setAttachmentOption(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("attachmentOption", "urn:metadata.tooling.soap.sforce.com","attachmentOption","urn:tooling.soap.sforce.com","EmailServicesAttOptions",1,1,true))) {
        setAttachmentOption((com.sforce.soap.tooling.EmailServicesAttOptions)__typeMapper.readObject(__in, _lookupTypeInfo("attachmentOption", "urn:metadata.tooling.soap.sforce.com","attachmentOption","urn:tooling.soap.sforce.com","EmailServicesAttOptions",1,1,true), com.sforce.soap.tooling.EmailServicesAttOptions.class));
      }
    }

    private void writeFieldAttachmentOption(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("attachmentOption", "urn:metadata.tooling.soap.sforce.com","attachmentOption","urn:tooling.soap.sforce.com","EmailServicesAttOptions",1,1,true), attachmentOption, attachmentOption__is_set);
    }

    /**
     * element : authenticationFailureAction of type {urn:tooling.soap.sforce.com}EmailServicesErrorAction
     * java type: com.sforce.soap.tooling.EmailServicesErrorAction
     */
    private boolean authenticationFailureAction__is_set = false;

    private com.sforce.soap.tooling.EmailServicesErrorAction authenticationFailureAction;

    public com.sforce.soap.tooling.EmailServicesErrorAction getAuthenticationFailureAction() {
      return authenticationFailureAction;
    }

    public void setAuthenticationFailureAction(com.sforce.soap.tooling.EmailServicesErrorAction authenticationFailureAction) {
      this.authenticationFailureAction = authenticationFailureAction;
      authenticationFailureAction__is_set = true;
    }

    protected void setAuthenticationFailureAction(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("authenticationFailureAction", "urn:metadata.tooling.soap.sforce.com","authenticationFailureAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true))) {
        setAuthenticationFailureAction((com.sforce.soap.tooling.EmailServicesErrorAction)__typeMapper.readObject(__in, _lookupTypeInfo("authenticationFailureAction", "urn:metadata.tooling.soap.sforce.com","authenticationFailureAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), com.sforce.soap.tooling.EmailServicesErrorAction.class));
      }
    }

    private void writeFieldAuthenticationFailureAction(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("authenticationFailureAction", "urn:metadata.tooling.soap.sforce.com","authenticationFailureAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), authenticationFailureAction, authenticationFailureAction__is_set);
    }

    /**
     * element : authorizationFailureAction of type {urn:tooling.soap.sforce.com}EmailServicesErrorAction
     * java type: com.sforce.soap.tooling.EmailServicesErrorAction
     */
    private boolean authorizationFailureAction__is_set = false;

    private com.sforce.soap.tooling.EmailServicesErrorAction authorizationFailureAction;

    public com.sforce.soap.tooling.EmailServicesErrorAction getAuthorizationFailureAction() {
      return authorizationFailureAction;
    }

    public void setAuthorizationFailureAction(com.sforce.soap.tooling.EmailServicesErrorAction authorizationFailureAction) {
      this.authorizationFailureAction = authorizationFailureAction;
      authorizationFailureAction__is_set = true;
    }

    protected void setAuthorizationFailureAction(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("authorizationFailureAction", "urn:metadata.tooling.soap.sforce.com","authorizationFailureAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true))) {
        setAuthorizationFailureAction((com.sforce.soap.tooling.EmailServicesErrorAction)__typeMapper.readObject(__in, _lookupTypeInfo("authorizationFailureAction", "urn:metadata.tooling.soap.sforce.com","authorizationFailureAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), com.sforce.soap.tooling.EmailServicesErrorAction.class));
      }
    }

    private void writeFieldAuthorizationFailureAction(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("authorizationFailureAction", "urn:metadata.tooling.soap.sforce.com","authorizationFailureAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), authorizationFailureAction, authorizationFailureAction__is_set);
    }

    /**
     * element : authorizedSenders of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean authorizedSenders__is_set = false;

    private java.lang.String authorizedSenders;

    public java.lang.String getAuthorizedSenders() {
      return authorizedSenders;
    }

    public void setAuthorizedSenders(java.lang.String authorizedSenders) {
      this.authorizedSenders = authorizedSenders;
      authorizedSenders__is_set = true;
    }

    protected void setAuthorizedSenders(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("authorizedSenders", "urn:metadata.tooling.soap.sforce.com","authorizedSenders","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setAuthorizedSenders(__typeMapper.readString(__in, _lookupTypeInfo("authorizedSenders", "urn:metadata.tooling.soap.sforce.com","authorizedSenders","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldAuthorizedSenders(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("authorizedSenders", "urn:metadata.tooling.soap.sforce.com","authorizedSenders","http://www.w3.org/2001/XMLSchema","string",0,1,true), authorizedSenders, authorizedSenders__is_set);
    }

    /**
     * element : emailServicesAddresses of type {urn:metadata.tooling.soap.sforce.com}EmailServicesAddress
     * java type: com.sforce.soap.tooling.metadata.EmailServicesAddress[]
     */
    private boolean emailServicesAddresses__is_set = false;

    private com.sforce.soap.tooling.metadata.EmailServicesAddress[] emailServicesAddresses = new com.sforce.soap.tooling.metadata.EmailServicesAddress[0];

    public com.sforce.soap.tooling.metadata.EmailServicesAddress[] getEmailServicesAddresses() {
      return emailServicesAddresses;
    }

    public void setEmailServicesAddresses(com.sforce.soap.tooling.metadata.EmailServicesAddress[] emailServicesAddresses) {
      this.emailServicesAddresses = emailServicesAddresses;
      emailServicesAddresses__is_set = true;
    }

    protected void setEmailServicesAddresses(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("emailServicesAddresses", "urn:metadata.tooling.soap.sforce.com","emailServicesAddresses","urn:metadata.tooling.soap.sforce.com","EmailServicesAddress",0,-1,true))) {
        setEmailServicesAddresses((com.sforce.soap.tooling.metadata.EmailServicesAddress[])__typeMapper.readObject(__in, _lookupTypeInfo("emailServicesAddresses", "urn:metadata.tooling.soap.sforce.com","emailServicesAddresses","urn:metadata.tooling.soap.sforce.com","EmailServicesAddress",0,-1,true), com.sforce.soap.tooling.metadata.EmailServicesAddress[].class));
      }
    }

    private void writeFieldEmailServicesAddresses(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("emailServicesAddresses", "urn:metadata.tooling.soap.sforce.com","emailServicesAddresses","urn:metadata.tooling.soap.sforce.com","EmailServicesAddress",0,-1,true), emailServicesAddresses, emailServicesAddresses__is_set);
    }

    /**
     * element : errorRoutingAddress of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean errorRoutingAddress__is_set = false;

    private java.lang.String errorRoutingAddress;

    public java.lang.String getErrorRoutingAddress() {
      return errorRoutingAddress;
    }

    public void setErrorRoutingAddress(java.lang.String errorRoutingAddress) {
      this.errorRoutingAddress = errorRoutingAddress;
      errorRoutingAddress__is_set = true;
    }

    protected void setErrorRoutingAddress(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("errorRoutingAddress", "urn:metadata.tooling.soap.sforce.com","errorRoutingAddress","http://www.w3.org/2001/XMLSchema","string",0,1,true))) {
        setErrorRoutingAddress(__typeMapper.readString(__in, _lookupTypeInfo("errorRoutingAddress", "urn:metadata.tooling.soap.sforce.com","errorRoutingAddress","http://www.w3.org/2001/XMLSchema","string",0,1,true), java.lang.String.class));
      }
    }

    private void writeFieldErrorRoutingAddress(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("errorRoutingAddress", "urn:metadata.tooling.soap.sforce.com","errorRoutingAddress","http://www.w3.org/2001/XMLSchema","string",0,1,true), errorRoutingAddress, errorRoutingAddress__is_set);
    }

    /**
     * element : functionInactiveAction of type {urn:tooling.soap.sforce.com}EmailServicesErrorAction
     * java type: com.sforce.soap.tooling.EmailServicesErrorAction
     */
    private boolean functionInactiveAction__is_set = false;

    private com.sforce.soap.tooling.EmailServicesErrorAction functionInactiveAction;

    public com.sforce.soap.tooling.EmailServicesErrorAction getFunctionInactiveAction() {
      return functionInactiveAction;
    }

    public void setFunctionInactiveAction(com.sforce.soap.tooling.EmailServicesErrorAction functionInactiveAction) {
      this.functionInactiveAction = functionInactiveAction;
      functionInactiveAction__is_set = true;
    }

    protected void setFunctionInactiveAction(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("functionInactiveAction", "urn:metadata.tooling.soap.sforce.com","functionInactiveAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true))) {
        setFunctionInactiveAction((com.sforce.soap.tooling.EmailServicesErrorAction)__typeMapper.readObject(__in, _lookupTypeInfo("functionInactiveAction", "urn:metadata.tooling.soap.sforce.com","functionInactiveAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), com.sforce.soap.tooling.EmailServicesErrorAction.class));
      }
    }

    private void writeFieldFunctionInactiveAction(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("functionInactiveAction", "urn:metadata.tooling.soap.sforce.com","functionInactiveAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), functionInactiveAction, functionInactiveAction__is_set);
    }

    /**
     * element : functionName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean functionName__is_set = false;

    private java.lang.String functionName;

    public java.lang.String getFunctionName() {
      return functionName;
    }

    public void setFunctionName(java.lang.String functionName) {
      this.functionName = functionName;
      functionName__is_set = true;
    }

    protected void setFunctionName(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("functionName", "urn:metadata.tooling.soap.sforce.com","functionName","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setFunctionName(__typeMapper.readString(__in, _lookupTypeInfo("functionName", "urn:metadata.tooling.soap.sforce.com","functionName","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldFunctionName(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("functionName", "urn:metadata.tooling.soap.sforce.com","functionName","http://www.w3.org/2001/XMLSchema","string",1,1,true), functionName, functionName__is_set);
    }

    /**
     * element : isActive of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean isActive__is_set = false;

    private boolean isActive;

    public boolean getIsActive() {
      return isActive;
    }

    public boolean isIsActive() {
      return isActive;
    }

    public void setIsActive(boolean isActive) {
      this.isActive = isActive;
      isActive__is_set = true;
    }

    protected void setIsActive(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("isActive", "urn:metadata.tooling.soap.sforce.com","isActive","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsActive(__typeMapper.readBoolean(__in, _lookupTypeInfo("isActive", "urn:metadata.tooling.soap.sforce.com","isActive","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldIsActive(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("isActive", "urn:metadata.tooling.soap.sforce.com","isActive","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), isActive, isActive__is_set);
    }

    /**
     * element : isAuthenticationRequired of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean isAuthenticationRequired__is_set = false;

    private boolean isAuthenticationRequired;

    public boolean getIsAuthenticationRequired() {
      return isAuthenticationRequired;
    }

    public boolean isIsAuthenticationRequired() {
      return isAuthenticationRequired;
    }

    public void setIsAuthenticationRequired(boolean isAuthenticationRequired) {
      this.isAuthenticationRequired = isAuthenticationRequired;
      isAuthenticationRequired__is_set = true;
    }

    protected void setIsAuthenticationRequired(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("isAuthenticationRequired", "urn:metadata.tooling.soap.sforce.com","isAuthenticationRequired","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsAuthenticationRequired(__typeMapper.readBoolean(__in, _lookupTypeInfo("isAuthenticationRequired", "urn:metadata.tooling.soap.sforce.com","isAuthenticationRequired","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldIsAuthenticationRequired(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("isAuthenticationRequired", "urn:metadata.tooling.soap.sforce.com","isAuthenticationRequired","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), isAuthenticationRequired, isAuthenticationRequired__is_set);
    }

    /**
     * element : isErrorRoutingEnabled of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean isErrorRoutingEnabled__is_set = false;

    private boolean isErrorRoutingEnabled;

    public boolean getIsErrorRoutingEnabled() {
      return isErrorRoutingEnabled;
    }

    public boolean isIsErrorRoutingEnabled() {
      return isErrorRoutingEnabled;
    }

    public void setIsErrorRoutingEnabled(boolean isErrorRoutingEnabled) {
      this.isErrorRoutingEnabled = isErrorRoutingEnabled;
      isErrorRoutingEnabled__is_set = true;
    }

    protected void setIsErrorRoutingEnabled(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("isErrorRoutingEnabled", "urn:metadata.tooling.soap.sforce.com","isErrorRoutingEnabled","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsErrorRoutingEnabled(__typeMapper.readBoolean(__in, _lookupTypeInfo("isErrorRoutingEnabled", "urn:metadata.tooling.soap.sforce.com","isErrorRoutingEnabled","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldIsErrorRoutingEnabled(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("isErrorRoutingEnabled", "urn:metadata.tooling.soap.sforce.com","isErrorRoutingEnabled","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), isErrorRoutingEnabled, isErrorRoutingEnabled__is_set);
    }

    /**
     * element : isTextAttachmentsAsBinary of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean isTextAttachmentsAsBinary__is_set = false;

    private boolean isTextAttachmentsAsBinary;

    public boolean getIsTextAttachmentsAsBinary() {
      return isTextAttachmentsAsBinary;
    }

    public boolean isIsTextAttachmentsAsBinary() {
      return isTextAttachmentsAsBinary;
    }

    public void setIsTextAttachmentsAsBinary(boolean isTextAttachmentsAsBinary) {
      this.isTextAttachmentsAsBinary = isTextAttachmentsAsBinary;
      isTextAttachmentsAsBinary__is_set = true;
    }

    protected void setIsTextAttachmentsAsBinary(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("isTextAttachmentsAsBinary", "urn:metadata.tooling.soap.sforce.com","isTextAttachmentsAsBinary","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsTextAttachmentsAsBinary(__typeMapper.readBoolean(__in, _lookupTypeInfo("isTextAttachmentsAsBinary", "urn:metadata.tooling.soap.sforce.com","isTextAttachmentsAsBinary","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldIsTextAttachmentsAsBinary(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("isTextAttachmentsAsBinary", "urn:metadata.tooling.soap.sforce.com","isTextAttachmentsAsBinary","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), isTextAttachmentsAsBinary, isTextAttachmentsAsBinary__is_set);
    }

    /**
     * element : isTlsRequired of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean isTlsRequired__is_set = false;

    private boolean isTlsRequired;

    public boolean getIsTlsRequired() {
      return isTlsRequired;
    }

    public boolean isIsTlsRequired() {
      return isTlsRequired;
    }

    public void setIsTlsRequired(boolean isTlsRequired) {
      this.isTlsRequired = isTlsRequired;
      isTlsRequired__is_set = true;
    }

    protected void setIsTlsRequired(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("isTlsRequired", "urn:metadata.tooling.soap.sforce.com","isTlsRequired","http://www.w3.org/2001/XMLSchema","boolean",0,1,true))) {
        setIsTlsRequired(__typeMapper.readBoolean(__in, _lookupTypeInfo("isTlsRequired", "urn:metadata.tooling.soap.sforce.com","isTlsRequired","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), boolean.class));
      }
    }

    private void writeFieldIsTlsRequired(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("isTlsRequired", "urn:metadata.tooling.soap.sforce.com","isTlsRequired","http://www.w3.org/2001/XMLSchema","boolean",0,1,true), isTlsRequired, isTlsRequired__is_set);
    }

    /**
     * element : overLimitAction of type {urn:tooling.soap.sforce.com}EmailServicesErrorAction
     * java type: com.sforce.soap.tooling.EmailServicesErrorAction
     */
    private boolean overLimitAction__is_set = false;

    private com.sforce.soap.tooling.EmailServicesErrorAction overLimitAction;

    public com.sforce.soap.tooling.EmailServicesErrorAction getOverLimitAction() {
      return overLimitAction;
    }

    public void setOverLimitAction(com.sforce.soap.tooling.EmailServicesErrorAction overLimitAction) {
      this.overLimitAction = overLimitAction;
      overLimitAction__is_set = true;
    }

    protected void setOverLimitAction(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("overLimitAction", "urn:metadata.tooling.soap.sforce.com","overLimitAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true))) {
        setOverLimitAction((com.sforce.soap.tooling.EmailServicesErrorAction)__typeMapper.readObject(__in, _lookupTypeInfo("overLimitAction", "urn:metadata.tooling.soap.sforce.com","overLimitAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), com.sforce.soap.tooling.EmailServicesErrorAction.class));
      }
    }

    private void writeFieldOverLimitAction(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("overLimitAction", "urn:metadata.tooling.soap.sforce.com","overLimitAction","urn:tooling.soap.sforce.com","EmailServicesErrorAction",1,1,true), overLimitAction, overLimitAction__is_set);
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:metadata.tooling.soap.sforce.com", "EmailServicesFunction");
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
      sb.append("[EmailServicesFunction ");
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
      writeFieldApexClass(__out, __typeMapper);
      writeFieldAttachmentOption(__out, __typeMapper);
      writeFieldAuthenticationFailureAction(__out, __typeMapper);
      writeFieldAuthorizationFailureAction(__out, __typeMapper);
      writeFieldAuthorizedSenders(__out, __typeMapper);
      writeFieldEmailServicesAddresses(__out, __typeMapper);
      writeFieldErrorRoutingAddress(__out, __typeMapper);
      writeFieldFunctionInactiveAction(__out, __typeMapper);
      writeFieldFunctionName(__out, __typeMapper);
      writeFieldIsActive(__out, __typeMapper);
      writeFieldIsAuthenticationRequired(__out, __typeMapper);
      writeFieldIsErrorRoutingEnabled(__out, __typeMapper);
      writeFieldIsTextAttachmentsAsBinary(__out, __typeMapper);
      writeFieldIsTlsRequired(__out, __typeMapper);
      writeFieldOverLimitAction(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setApexClass(__in, __typeMapper);
      setAttachmentOption(__in, __typeMapper);
      setAuthenticationFailureAction(__in, __typeMapper);
      setAuthorizationFailureAction(__in, __typeMapper);
      setAuthorizedSenders(__in, __typeMapper);
      setEmailServicesAddresses(__in, __typeMapper);
      setErrorRoutingAddress(__in, __typeMapper);
      setFunctionInactiveAction(__in, __typeMapper);
      setFunctionName(__in, __typeMapper);
      setIsActive(__in, __typeMapper);
      setIsAuthenticationRequired(__in, __typeMapper);
      setIsErrorRoutingEnabled(__in, __typeMapper);
      setIsTextAttachmentsAsBinary(__in, __typeMapper);
      setIsTlsRequired(__in, __typeMapper);
      setOverLimitAction(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "apexClass", apexClass);
      toStringHelper(sb, "attachmentOption", attachmentOption);
      toStringHelper(sb, "authenticationFailureAction", authenticationFailureAction);
      toStringHelper(sb, "authorizationFailureAction", authorizationFailureAction);
      toStringHelper(sb, "authorizedSenders", authorizedSenders);
      toStringHelper(sb, "emailServicesAddresses", emailServicesAddresses);
      toStringHelper(sb, "errorRoutingAddress", errorRoutingAddress);
      toStringHelper(sb, "functionInactiveAction", functionInactiveAction);
      toStringHelper(sb, "functionName", functionName);
      toStringHelper(sb, "isActive", isActive);
      toStringHelper(sb, "isAuthenticationRequired", isAuthenticationRequired);
      toStringHelper(sb, "isErrorRoutingEnabled", isErrorRoutingEnabled);
      toStringHelper(sb, "isTextAttachmentsAsBinary", isTextAttachmentsAsBinary);
      toStringHelper(sb, "isTlsRequired", isTlsRequired);
      toStringHelper(sb, "overLimitAction", overLimitAction);
    }


}
