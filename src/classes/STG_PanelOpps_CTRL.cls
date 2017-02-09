/*
    Copyright (c) 2014, Salesforce.org
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
* @date 2014
* @group Settings
* @description Controller for the Donor Statistics panel.
*/
public with sharing class STG_PanelOpps_CTRL extends STG_Panel { 

    /*********************************************************************************************************
    * @description Returns the string Id of the Donor Statistics panel. 
    */
    public override string idPanel() { return 'idPanelOpp'; }

    /**
     * @description Get a list of the active closed/won opportunity stages
     * defined in this org.  This is for populating a select list in the
     * settings UI.
     * @return List<SelectOption>
     */
    public List<SelectOption> getOpportunityClosedWonStages() {

        // We can query OpportunityStage to find closed/won stages, but the
        // MasterLabel field is not translated in multilanguage orgs.  We need
        // to compare these labels to the described picklist values to get
        // translated closed/won stages.
        Set<String> activeClosedWonStages = getOpportunityClosedWonStageNames();

        List<SelectOption> stageOptions = new List<SelectOption>();
        stageOptions.add(new SelectOption('', '-- no selection --'));

        List<Schema.PicklistEntry> stagePicklistValues = Opportunity.StageName.getDescribe().getPicklistValues();

        for (Schema.PickListEntry entry : stagePicklistValues) {
            if (activeClosedWonStages.contains(entry.getValue())) {
                stageOptions.add(new SelectOption(
                    entry.getValue(),
                    entry.getLabel()
                ));
            }
        }

        return stageOptions;
    }

    /**
     * @description Get a set of the internal labels for the active closed/won
     * opportunity stages defined in this org.
     * @return Set<String>
     */
    public static Set<String> getOpportunityClosedWonStageNames() {
        List<OpportunityStage> opportunityStages = [
            SELECT MasterLabel
            FROM OpportunityStage
            WHERE IsActive = true
            AND IsClosed = true
            AND IsWon = true
        ];

        Set<String> activeClosedWonStages = new Set<String>();

        for (OpportunityStage opportunityStage : opportunityStages) {
            activeClosedWonStages.add(opportunityStage.MasterLabel);
        }

        return activeClosedWonStages;
    }

    /**
     * @description Verifies that the selected opportunity stage for auto
     * transition when all payments received is an active closed/won stage (or
     * empty) - called by HealthCheck
     * @param ctrl The Health Check panel controller
     * @return void
     */
    public static void verifyOpportunityStageForPaymentAutoCloseIsActiveClosedWon(STG_PanelHealthCheck_CTRL ctrl) {
        String settingLabel = npe01__Contacts_And_Orgs_Settings__c.Payments_Auto_Close_Stage_Name__c.getDescribe().getLabel();
        Set<String> activeClosedWonStages = getOpportunityClosedWonStageNames();
        String paymentAutoCloseStageName = UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c;

        Boolean isBlank = String.isBlank(paymentAutoCloseStageName);
        Boolean isActiveClosedWonStage = activeClosedWonStages.contains(paymentAutoCloseStageName);

        if (!isBlank && !isActiveClosedWonStage) {
            ctrl.createDR(
                settingLabel,
                STG_PanelHealthCheck_CTRL.statusError,
                Label.healthPaymentAutoCloseStageInvalid,
                Label.healthPaymentAutoCloseStageMustBeActiveClosedWonOrBlank
            );
        } else {
            ctrl.createDR(
                settingLabel,
                STG_PanelHealthCheck_CTRL.statusSuccess,
                null,
                Label.healthPaymentAutoCloseStageValid
            );
        }
    }

    /**
     * @description A user friendly rendering of record type ids by record type
     * name for Opportunity Record Types Excluded for Payments
     */
    static public String strOppRecTypesExcludedForPaymentsReadOnly {
        get {
            if (strOppRecTypesExcludedForPaymentsReadOnly == null) {
                strOppRecTypesExcludedForPaymentsReadOnly = STG_Panel.getRecordTypeNamesFromPicklistString(
                    Opportunity.sObjectType,
                    STG_Panel.stgService.stgCon.Opp_RecTypes_Excluded_for_Payments__c
                );
            }
            return strOppRecTypesExcludedForPaymentsReadOnly;
        }
        private set;
    }
}