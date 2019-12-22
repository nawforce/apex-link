/*
    Copyright (c) 2016, Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Salesforce.org nor the names of
      its contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.
*/
/**
* @author Salesforce.org
* @group Utilities
* @description Controller for SLDS styled page messages component
*/
public class UTIL_PageMessages_CTRL {
    /**
     * @description Get the current context's page messages in the form of a
     * container class that can be used within Visualforce
     *
     * @return List<Message>
     */
    public List<Message> getPageMessages() {
        List<Message> pageMessages = new List<Message>();

        for (ApexPages.Message m : ApexPages.getMessages()) {
            pageMessages.add(new Message(m));
        }

        return pageMessages;
    }

    /**
     * @author Salesforce.org
     * @group Utilities
     * @description A container class that maps fields from ApexPages.Message.
     * This allows this data to be accessed within a Visualforce page.
     */
    public class Message {
        public MessageSeverity severity {get; set;}
        public String summary {get; set;}
        public String detail {get; set;}
        public String componentLabel {get; set;}

        /**
         * @description Construct a Message data container object from a given
         * ApexPages.Message object.  This will extract out the data from the
         * ApexPages.Message object and store it in the new Message object
         * isntance.
         *
         * @param m The ApexPages.Message object to extract data from
         */
        public Message(ApexPages.Message m) {
            this.summary = m.getSummary();
            this.detail = m.getDetail();
            this.componentLabel = m.getComponentLabel();

            ApexPages.Severity s = m.getSeverity();

            if (ApexPages.Severity.CONFIRM == s) {
                this.severity = MessageSeverity.CONFIRM;
            } else if (ApexPages.Severity.ERROR == s) {
                this.severity = MessageSeverity.ERROR;
            } else if (ApexPages.Severity.FATAL == s) {
                this.severity = MessageSeverity.FATAL;
            } else if (ApexPages.Severity.INFO == s) {
                this.severity = MessageSeverity.INFO;
            } else if (ApexPages.Severity.WARNING == s) {
                this.severity = MessageSeverity.WARNING;
            }
        }

        /**
         * @description A helper method for use in Visualforce pages that
         * translates MessageSeverity enums into SLDS theme names.
         *
         * @return string
         */
        public String getSeverityTheme() {
            if (MessageSeverity.CONFIRM == severity) {
                return 'success';
            } else if (MessageSeverity.ERROR == severity) {
                return 'error';
            } else if (MessageSeverity.FATAL == severity) {
                return 'error';
            } else if (MessageSeverity.INFO == severity) {
                return 'info';
            } else if (MessageSeverity.WARNING == severity) {
                return 'warning';
            } else {
                return 'info';
            }
        }

        /**
         * @description A helper method for use in Visualforce pages that
         * translates MessageSeverity enums into localized text labels
         *
         * @return string
         */
        public String getSeverityLabel() {
            if (MessageSeverity.CONFIRM == severity) {
                return Label.PageMessagesConfirm;
            } else if (MessageSeverity.ERROR == severity) {
                return Label.PageMessagesError;
            } else if (MessageSeverity.FATAL == severity) {
                return Label.PageMessagesFatal;
            } else if (MessageSeverity.INFO == severity) {
                return '';
            } else if (MessageSeverity.WARNING == severity) {
                return Label.PageMessagesWarning;
            } else {
                return '';
            }
        }
    }

    /**
     * @author Salesforce.org
     * @description A native Apex enum representing values that correspond to
     * the ApexPages.MessageSeverity enum.
     */
    public enum MessageSeverity {
        CONFIRM,
        ERROR,
        FATAL,
        INFO,
        WARNING
    }
}