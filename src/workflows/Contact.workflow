<?xml version="1.0" encoding="UTF-8"?>
<Workflow xmlns="http://soap.sforce.com/2006/04/metadata">
    <fieldUpdates>
        <fullName>ContactAlternateEmailUpdate</fullName>
        <field>AlternateEmail__c</field>
        <formula>Email</formula>
        <name>Contact.AlternateEmail.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactHomePhoneUpdate</fullName>
        <field>HomePhone</field>
        <formula>Phone</formula>
        <name>Contact.HomePhone.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactMobilePhoneUpdate</fullName>
        <field>MobilePhone</field>
        <formula>Phone</formula>
        <name>Contact.MobilePhone.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactOtherEmailUpdate</fullName>
        <field>OtherPhone</field>
        <formula>Phone</formula>
        <name>Contact.OtherEmail.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactPersonalEmailUpdate</fullName>
        <field>HomeEmail__c</field>
        <formula>Email</formula>
        <name>Contact.PersonalEmail.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactPreferredEmail</fullName>
        <field>Email</field>
        <formula>CASE( 
Preferred_Email__c , 

&quot;Work&quot;, 
if(len(WorkEmail__c)&gt;0, WorkEmail__c, 
if(len(HomeEmail__c)&gt;0, HomeEmail__c, 
AlternateEmail__c)), 

&quot;Personal&quot;, 
if(len(HomeEmail__c)&gt;0, HomeEmail__c, 
if(len(WorkEmail__c)&gt;0, WorkEmail__c, 
AlternateEmail__c)), 

&quot;Home&quot;, 
if(len(HomeEmail__c)&gt;0, HomeEmail__c, 
if(len(WorkEmail__c)&gt;0, WorkEmail__c, 
AlternateEmail__c)), 

&quot;Alternate&quot;, 
if(len(AlternateEmail__c)&gt;0, AlternateEmail__c, 
if(len(WorkEmail__c)&gt;0, WorkEmail__c, 
HomeEmail__c)), 

If(LEN(WorkEmail__c)&gt;0 , WorkEmail__c , 
if(LEN( HomeEmail__c)&gt;0, HomeEmail__c, 
AlternateEmail__c 
)))</formula>
        <name>Contact.PreferredEmail</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactPreferredPhone</fullName>
        <description>Populates the standard Phone field displayed on activities based on the Preferred Phone field value.</description>
        <field>Phone</field>
        <formula>CASE(
  PreferredPhone__c ,
&quot;Work&quot;,
  WorkPhone__c  ,
&quot;Home&quot;,
 HomePhone,
&quot;Mobile&quot;,
 MobilePhone,
&quot;Other&quot;,
 OtherPhone,
If(LEN( WorkPhone__c )&gt;0 , WorkPhone__c  ,
if(LEN(  HomePhone)&gt;0,  HomePhone,
if(LEN( MobilePhone)&gt;0, MobilePhone,
OtherPhone
))))</formula>
        <name>Contact.PreferredPhone</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactWorkEmailUpdate</fullName>
        <field>WorkEmail__c</field>
        <formula>Email</formula>
        <name>Contact.WorkEmail.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>ContactWorkPhoneUpdate</fullName>
        <field>WorkPhone__c</field>
        <formula>Phone</formula>
        <name>Contact.WorkPhone.Update</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>PreferredPhonetoWork</fullName>
        <field>PreferredPhone__c</field>
        <literalValue>Work</literalValue>
        <name>Preferred Phone to Work</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Literal</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>SetPrefEmailtoWork</fullName>
        <field>Preferred_Email__c</field>
        <literalValue>Work</literalValue>
        <name>Set Pref Email to Work</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Literal</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>SetWorkEmailtoEmail</fullName>
        <field>WorkEmail__c</field>
        <formula>Email</formula>
        <name>Set Work Email to Email</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <fieldUpdates>
        <fullName>WorkPhonetoPhone</fullName>
        <field>WorkPhone__c</field>
        <formula>Phone</formula>
        <name>Work Phone to Phone</name>
        <notifyAssignee>false</notifyAssignee>
        <operation>Formula</operation>
        <protected>false</protected>
    </fieldUpdates>
    <rules>
        <fullName>Contact%2EEmailChanged_Alternate</fullName>
        <actions>
            <name>ContactAlternateEmailUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(      ISPICKVAL( Preferred_Email__c ,&quot;Alternate&quot;),      OR(           AND(                ISNEW(),                LEN(Email)&gt;0           ),           ISCHANGED( Email )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EEmailChanged_Personal</fullName>
        <actions>
            <name>ContactPersonalEmailUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(     OR( ISPICKVAL( Preferred_Email__c ,&quot;Personal&quot;),ISPICKVAL( Preferred_Email__c ,&quot;Home&quot;)),      OR(           AND(                ISNEW(),                LEN(Email)&gt;0           ),           ISCHANGED( Email )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EEmailChanged_Work</fullName>
        <actions>
            <name>ContactWorkEmailUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(      ISPICKVAL( Preferred_Email__c ,&quot;Work&quot;),      OR(           AND(                ISNEW(),                LEN(Email)&gt;0           ),           ISCHANGED( Email )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EPhoneChanged_Home</fullName>
        <actions>
            <name>ContactHomePhoneUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(      ISPICKVAL( PreferredPhone__c ,&quot;Home&quot;),      OR(           AND(                ISNEW(),                LEN(Phone)&gt;0           ),           ISCHANGED( Phone )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EPhoneChanged_Mobile</fullName>
        <actions>
            <name>ContactMobilePhoneUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(      ISPICKVAL( PreferredPhone__c ,&quot;Mobile&quot;),      OR(           AND(                ISNEW(),                LEN(Phone)&gt;0           ),           ISCHANGED( Phone )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EPhoneChanged_Other</fullName>
        <actions>
            <name>ContactOtherEmailUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(      ISPICKVAL( PreferredPhone__c ,&quot;Other&quot;),      OR(           AND(                ISNEW(),                LEN(Phone)&gt;0           ),           ISCHANGED( Phone )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EPhoneChanged_Work</fullName>
        <actions>
            <name>ContactWorkPhoneUpdate</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <formula>AND(      ISPICKVAL( PreferredPhone__c ,&quot;Work&quot;),      OR(           AND(                ISNEW(),                LEN(Phone)&gt;0           ),           ISCHANGED( Phone )      ) )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EPreferred_Email%5F%5Fc</fullName>
        <actions>
            <name>ContactPreferredEmail</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <description>This workflow OVERWRITES the existing value in the standard Email field based on the Preferred Email field value.  This rule needs to be turned on manually after an Upgrade to this package.</description>
        <formula>OR( LEN(Email)=0, ISCHANGED(Preferred_Email__c) , ISCHANGED(WorkEmail__c) , ISCHANGED(HomeEmail__c) , ISCHANGED(AlternateEmail__c)  )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Contact%2EPreferred_Phone%5F%5Fc</fullName>
        <actions>
            <name>ContactPreferredPhone</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <description>This workflow OVERWRITES the existing value in the standard Phone field based on the Preferred Phone field value.  This rule needs to be turned on manually after an Upgrade to this package.</description>
        <formula>OR(  LEN(Phone)=0, ISCHANGED(PreferredPhone__c) ,  ISCHANGED(WorkPhone__c) ,  ISCHANGED(HomePhone) ,  ISCHANGED(MobilePhone) , ISCHANGED(OtherPhone)  )</formula>
        <triggerType>onAllChanges</triggerType>
    </rules>
    <rules>
        <fullName>Email only%3A Paste to Work</fullName>
        <actions>
            <name>SetPrefEmailtoWork</name>
            <type>FieldUpdate</type>
        </actions>
        <actions>
            <name>SetWorkEmailtoEmail</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <criteriaItems>
            <field>Contact.Email</field>
            <operation>notEqual</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.Preferred_Email__c</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.HomeEmail__c</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.AlternateEmail__c</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.WorkEmail__c</field>
            <operation>equals</operation>
        </criteriaItems>
        <description>If there is a value in Email but no values in any email fields or Preferred Email</description>
        <triggerType>onCreateOrTriggeringUpdate</triggerType>
    </rules>
    <rules>
        <fullName>Phone only%3A Paste to Work</fullName>
        <actions>
            <name>PreferredPhonetoWork</name>
            <type>FieldUpdate</type>
        </actions>
        <actions>
            <name>WorkPhonetoPhone</name>
            <type>FieldUpdate</type>
        </actions>
        <active>false</active>
        <booleanFilter>1 AND 2 AND 3 AND 4 AND 5 AND 6</booleanFilter>
        <criteriaItems>
            <field>Contact.Phone</field>
            <operation>notEqual</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.PreferredPhone__c</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.WorkPhone__c</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.MobilePhone</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.HomePhone</field>
            <operation>equals</operation>
        </criteriaItems>
        <criteriaItems>
            <field>Contact.OtherPhone</field>
            <operation>equals</operation>
        </criteriaItems>
        <description>A new phone value is added to the Phone field and there is no preferred Phone</description>
        <triggerType>onCreateOrTriggeringUpdate</triggerType>
    </rules>
</Workflow>
