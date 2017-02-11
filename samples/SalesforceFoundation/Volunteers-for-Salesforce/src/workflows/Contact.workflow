<?xml version="1.0" encoding="UTF-8"?>
<Workflow xmlns="http://soap.sforce.com/2006/04/metadata">
    <alerts>
        <fullName>Volunteer_Signup_Notification_Email_Alert_Contact</fullName>
        <description>Volunteer Signup Notification Email Alert - Contact</description>
        <protected>false</protected>
        <recipients>
            <type>owner</type>
        </recipients>
        <senderType>CurrentUser</senderType>
        <template>Volunteers_Email_Templates/Volunteer_Signup_Notification</template>
    </alerts>
    <alerts>
        <fullName>Volunteer_Signup_Thank_You_Email_Alert_Contact</fullName>
        <description>Volunteer Signup Thank You Email Alert - Contact</description>
        <protected>false</protected>
        <recipients>
            <field>Email</field>
            <type>email</type>
        </recipients>
        <senderType>CurrentUser</senderType>
        <template>Volunteers_Email_Templates/Volunteer_Signup_Thank_You</template>
    </alerts>
    <rules>
        <fullName>Volunteer Signup - Contact</fullName>
        <actions>
            <name>Volunteer_Signup_Notification_Email_Alert_Contact</name>
            <type>Alert</type>
        </actions>
        <actions>
            <name>Volunteer_Signup_Thank_You_Email_Alert_Contact</name>
            <type>Alert</type>
        </actions>
        <actions>
            <name>Volunteer_Signup_Thank_You_Sent_Contact</name>
            <type>Task</type>
        </actions>
        <active>true</active>
        <description>When a contact is updated or created from VolunteersSignup(FS) or VolunteersJobListing(FS), thank them and notify the volunteer manager.  Note if you are not using VolunteersSignup(FS), you can de-activate this rule to avoid multiple emails being sent.</description>
        <formula>Volunteer_Last_Web_Signup_Date__c =  TODAY()</formula>
        <triggerType>onCreateOrTriggeringUpdate</triggerType>
    </rules>
    <tasks>
        <fullName>Volunteer_Signup_Thank_You_Sent_Contact</fullName>
        <assignedToType>owner</assignedToType>
        <description>An automatic thank you email has been sent to the contact for signing up to be a volunteer.</description>
        <dueDateOffset>0</dueDateOffset>
        <notifyAssignee>false</notifyAssignee>
        <priority>Normal</priority>
        <protected>false</protected>
        <status>Completed</status>
        <subject>Volunteer Signup Thank You Sent - Contact</subject>
    </tasks>
</Workflow>
