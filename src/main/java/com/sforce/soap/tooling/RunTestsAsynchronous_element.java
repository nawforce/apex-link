package com.sforce.soap.tooling;

/**
 * This is a generated class for the SObject Enterprise API.
 * Do not edit this file, as your changes will be lost.
 */
public class RunTestsAsynchronous_element implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public RunTestsAsynchronous_element() {}

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
     * element : classids of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean classids__is_set = false;

    private java.lang.String classids;

    public java.lang.String getClassids() {
      return classids;
    }

    public void setClassids(java.lang.String classids) {
      this.classids = classids;
      classids__is_set = true;
    }

    protected void setClassids(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("classids", "urn:tooling.soap.sforce.com","classids","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setClassids(__typeMapper.readString(__in, _lookupTypeInfo("classids", "urn:tooling.soap.sforce.com","classids","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldClassids(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("classids", "urn:tooling.soap.sforce.com","classids","http://www.w3.org/2001/XMLSchema","string",1,1,true), classids, classids__is_set);
    }

    /**
     * element : suiteids of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean suiteids__is_set = false;

    private java.lang.String suiteids;

    public java.lang.String getSuiteids() {
      return suiteids;
    }

    public void setSuiteids(java.lang.String suiteids) {
      this.suiteids = suiteids;
      suiteids__is_set = true;
    }

    protected void setSuiteids(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("suiteids", "urn:tooling.soap.sforce.com","suiteids","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setSuiteids(__typeMapper.readString(__in, _lookupTypeInfo("suiteids", "urn:tooling.soap.sforce.com","suiteids","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldSuiteids(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("suiteids", "urn:tooling.soap.sforce.com","suiteids","http://www.w3.org/2001/XMLSchema","string",1,1,true), suiteids, suiteids__is_set);
    }

    /**
     * element : maxFailedTests of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private boolean maxFailedTests__is_set = false;

    private int maxFailedTests;

    public int getMaxFailedTests() {
      return maxFailedTests;
    }

    public void setMaxFailedTests(int maxFailedTests) {
      this.maxFailedTests = maxFailedTests;
      maxFailedTests__is_set = true;
    }

    protected void setMaxFailedTests(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("maxFailedTests", "urn:tooling.soap.sforce.com","maxFailedTests","http://www.w3.org/2001/XMLSchema","int",1,1,true))) {
        setMaxFailedTests((int)__typeMapper.readInt(__in, _lookupTypeInfo("maxFailedTests", "urn:tooling.soap.sforce.com","maxFailedTests","http://www.w3.org/2001/XMLSchema","int",1,1,true), int.class));
      }
    }

    private void writeFieldMaxFailedTests(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("maxFailedTests", "urn:tooling.soap.sforce.com","maxFailedTests","http://www.w3.org/2001/XMLSchema","int",1,1,true), maxFailedTests, maxFailedTests__is_set);
    }

    /**
     * element : testLevel of type {urn:tooling.soap.sforce.com}TestLevel
     * java type: com.sforce.soap.tooling.TestLevel
     */
    private boolean testLevel__is_set = false;

    private com.sforce.soap.tooling.TestLevel testLevel;

    public com.sforce.soap.tooling.TestLevel getTestLevel() {
      return testLevel;
    }

    public void setTestLevel(com.sforce.soap.tooling.TestLevel testLevel) {
      this.testLevel = testLevel;
      testLevel__is_set = true;
    }

    protected void setTestLevel(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("testLevel", "urn:tooling.soap.sforce.com","testLevel","urn:tooling.soap.sforce.com","TestLevel",1,1,true))) {
        setTestLevel((com.sforce.soap.tooling.TestLevel)__typeMapper.readObject(__in, _lookupTypeInfo("testLevel", "urn:tooling.soap.sforce.com","testLevel","urn:tooling.soap.sforce.com","TestLevel",1,1,true), com.sforce.soap.tooling.TestLevel.class));
      }
    }

    private void writeFieldTestLevel(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("testLevel", "urn:tooling.soap.sforce.com","testLevel","urn:tooling.soap.sforce.com","TestLevel",1,1,true), testLevel, testLevel__is_set);
    }

    /**
     * element : classNames of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean classNames__is_set = false;

    private java.lang.String classNames;

    public java.lang.String getClassNames() {
      return classNames;
    }

    public void setClassNames(java.lang.String classNames) {
      this.classNames = classNames;
      classNames__is_set = true;
    }

    protected void setClassNames(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("classNames", "urn:tooling.soap.sforce.com","classNames","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setClassNames(__typeMapper.readString(__in, _lookupTypeInfo("classNames", "urn:tooling.soap.sforce.com","classNames","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldClassNames(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("classNames", "urn:tooling.soap.sforce.com","classNames","http://www.w3.org/2001/XMLSchema","string",1,1,true), classNames, classNames__is_set);
    }

    /**
     * element : suiteNames of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private boolean suiteNames__is_set = false;

    private java.lang.String suiteNames;

    public java.lang.String getSuiteNames() {
      return suiteNames;
    }

    public void setSuiteNames(java.lang.String suiteNames) {
      this.suiteNames = suiteNames;
      suiteNames__is_set = true;
    }

    protected void setSuiteNames(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("suiteNames", "urn:tooling.soap.sforce.com","suiteNames","http://www.w3.org/2001/XMLSchema","string",1,1,true))) {
        setSuiteNames(__typeMapper.readString(__in, _lookupTypeInfo("suiteNames", "urn:tooling.soap.sforce.com","suiteNames","http://www.w3.org/2001/XMLSchema","string",1,1,true), java.lang.String.class));
      }
    }

    private void writeFieldSuiteNames(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("suiteNames", "urn:tooling.soap.sforce.com","suiteNames","http://www.w3.org/2001/XMLSchema","string",1,1,true), suiteNames, suiteNames__is_set);
    }

    /**
     * element : tests of type {urn:tooling.soap.sforce.com}TestsNode
     * java type: com.sforce.soap.tooling.TestsNode[]
     */
    private boolean tests__is_set = false;

    private com.sforce.soap.tooling.TestsNode[] tests = new com.sforce.soap.tooling.TestsNode[0];

    public com.sforce.soap.tooling.TestsNode[] getTests() {
      return tests;
    }

    public void setTests(com.sforce.soap.tooling.TestsNode[] tests) {
      this.tests = tests;
      tests__is_set = true;
    }

    protected void setTests(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.isElement(__in, _lookupTypeInfo("tests", "urn:tooling.soap.sforce.com","tests","urn:tooling.soap.sforce.com","TestsNode",0,-1,true))) {
        setTests((com.sforce.soap.tooling.TestsNode[])__typeMapper.readObject(__in, _lookupTypeInfo("tests", "urn:tooling.soap.sforce.com","tests","urn:tooling.soap.sforce.com","TestsNode",0,-1,true), com.sforce.soap.tooling.TestsNode[].class));
      }
    }

    private void writeFieldTests(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("tests", "urn:tooling.soap.sforce.com","tests","urn:tooling.soap.sforce.com","TestsNode",0,-1,true), tests, tests__is_set);
    }

    /**
     * element : skipCodeCoverage of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private boolean skipCodeCoverage__is_set = false;

    private boolean skipCodeCoverage;

    public boolean getSkipCodeCoverage() {
      return skipCodeCoverage;
    }

    public boolean isSkipCodeCoverage() {
      return skipCodeCoverage;
    }

    public void setSkipCodeCoverage(boolean skipCodeCoverage) {
      this.skipCodeCoverage = skipCodeCoverage;
      skipCodeCoverage__is_set = true;
    }

    protected void setSkipCodeCoverage(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __in.peekTag();
      if (__typeMapper.verifyElement(__in, _lookupTypeInfo("skipCodeCoverage", "urn:tooling.soap.sforce.com","skipCodeCoverage","http://www.w3.org/2001/XMLSchema","boolean",1,1,true))) {
        setSkipCodeCoverage(__typeMapper.readBoolean(__in, _lookupTypeInfo("skipCodeCoverage", "urn:tooling.soap.sforce.com","skipCodeCoverage","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), boolean.class));
      }
    }

    private void writeFieldSkipCodeCoverage(com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      __typeMapper.writeObject(__out, _lookupTypeInfo("skipCodeCoverage", "urn:tooling.soap.sforce.com","skipCodeCoverage","http://www.w3.org/2001/XMLSchema","boolean",1,1,true), skipCodeCoverage, skipCodeCoverage__is_set);
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
      sb.append("[RunTestsAsynchronous_element ");
      toString1(sb);

      sb.append("]\n");
      return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, String name, Object value) {
      sb.append(' ').append(name).append("='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
    }

    private void writeFields1(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
      writeFieldClassids(__out, __typeMapper);
      writeFieldSuiteids(__out, __typeMapper);
      writeFieldMaxFailedTests(__out, __typeMapper);
      writeFieldTestLevel(__out, __typeMapper);
      writeFieldClassNames(__out, __typeMapper);
      writeFieldSuiteNames(__out, __typeMapper);
      writeFieldTests(__out, __typeMapper);
      writeFieldSkipCodeCoverage(__out, __typeMapper);
    }

    private void loadFields1(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      setClassids(__in, __typeMapper);
      setSuiteids(__in, __typeMapper);
      setMaxFailedTests(__in, __typeMapper);
      setTestLevel(__in, __typeMapper);
      setClassNames(__in, __typeMapper);
      setSuiteNames(__in, __typeMapper);
      setTests(__in, __typeMapper);
      setSkipCodeCoverage(__in, __typeMapper);
    }

    private void toString1(StringBuilder sb) {
      toStringHelper(sb, "classids", classids);
      toStringHelper(sb, "suiteids", suiteids);
      toStringHelper(sb, "maxFailedTests", maxFailedTests);
      toStringHelper(sb, "testLevel", testLevel);
      toStringHelper(sb, "classNames", classNames);
      toStringHelper(sb, "suiteNames", suiteNames);
      toStringHelper(sb, "tests", tests);
      toStringHelper(sb, "skipCodeCoverage", skipCodeCoverage);
    }


}