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
* @group Utilities
* @group-content ../../ApexDocContent/Utilities.htm
* @description This class calculates summary totals from detail records and saves them in parent records.
*
* To use it:
*  1) create a subclass that extends this class
*  2) the subclass must:
*     a) override the setRollupSpecs method: create one or more rollupSpec objects and set all their
*           non-optional properties, return them in a list
*     b) override the setParentObjName to return.. well.. duh
*  3) write one or more tests in the subclass
*  4) instantiate the subclass as part of triggers, schedulable classes, and VF controllers
*
*-----------------------------------------------------------------------------------------------*/
public abstract class UTIL_AbstractRollup_BATCH implements database.batchable<SObject> {

    // the object into which aggregate values get saved, e.g. account
    public string parentObjName;
    private set<id> parentRecIds;
    private list<sobject> parentRecsInitial;
    private list<RollupSpec> specs = new list<RollupSpec>();
    private set<string> parentRollupFields = new set<string>();
    private set<string> parentFieldsToZeroBeforeRollup = new set<string>();

    // for batch
    public string customBatchWhereClause {get;set;}
    protected String batchQuery { get; set; }
    private integer batchSize;
    private boolean isRunningBatch = false;

    // constructors - pass in one or more parent object ids
    public UTIL_AbstractRollup_BATCH ( id parentRecId ) {
        // this.parentObjName = parentObjName;
        this.parentRecIds = new set<id> { parentRecId };
        setupRollup();
    }
    public UTIL_AbstractRollup_BATCH ( set<id> parentRecIds ) {
        this.parentRecIds = parentRecIds;
        setupRollup();
    }
    // or the list of objects - so they won't need to be queried again for current values
    // note: best practice: use this constructor only in a trigger on the parent object
    //  (since it will contain all fields, including those we're writing rollup info to)
    public UTIL_AbstractRollup_BATCH ( list<sobject> parentRecs ) {
        for ( sobject obj : parentRecs ) this.parentRecIds.add( obj.id );
        this.parentRecsInitial = parentRecs;
        setupRollup();
    }

    // or, for BATCH processing, pass in the batch size as an integer <=200
    public UTIL_AbstractRollup_BATCH (integer batchSz) {
        system.assert( batchSz <= 200 && batchSz >= 1, 'For batch processing, your batch size must be between 1 and 200.');
        this.batchSize = batchSz;
        setupRollup();
    }

    private void setupRollup() {
        this.parentObjName = setParentObjName();
        this.specs = setRollupSpecs();
        setParentFieldList();
    }

    private void runBatchRollup() {
        string soql = 'SELECT ' + getQuerySelectClause() + ' FROM ' + parentObjName;
        if (customBatchWhereClause != null) soql += ' ' + customBatchWhereClause;
        batchQuery = soql;
        id batchProcessId = database.executeBatch(this, 20);
    }

    // subclass must implement these to set the objects and fields we're working with, and the rules for what gets rolled up to where
    public abstract string setParentObjName();
    public abstract list<RollupSpec> setRollupSpecs();

    public database.Querylocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(batchQuery);
    }

    public void execute(Database.BatchableContext bc, Sobject[] result) {
        this.parentRecIds = new set<id>();
        for ( sobject obj : result ) this.parentRecIds.add( obj.id );
        this.parentRecsInitial = result;
        isRunningBatch = true;
        runRollups();
    }

    public void finish(Database.BatchableContext bc) {
    }

    // this data structure defines all the parameters of a single rollup
    public class RollupSpec {

        // the object that has the details to be rolled up, e.g. opportunity
        string childObjectName;

        // relationship field in child table, e.g. accountId
        string FKFieldnameInChild;

        public RollupSpec( string NameOfChildObjectToRollFrom, string ForeignKeyFieldnameInChildObject ) {
            childObjectName = NameOfChildObjectToRollFrom;
            FKFieldnameInChild = ForeignKeyFieldnameInChildObject;
        }

        // where clause elements, if any (not including IN or NOT IN)
        public string filter;

        // these let you pass in sets of filter strings to include or leave out
        public map<string, set<string>> setFiltersIn = new map<string, set<string>>();
        public map<string, set<string>> setFiltersNotIn = new map<string, set<string>>();

        // this maps aggregate functions to the rollup field names that summarize them
        public map<string, string> aggregateToParentFieldnameMap;

        // an expression to subgroup by - example: 'Fiscal_Year(CloseDate)'
        // if you set this, you must also set the map below
        public string subgroupExpression;

        // this maps each aggregate field to a map of subgroup values and the fields they summarize
        public map<string, map<object, string>> subgroupValueToParentFieldnameMap;

        // optional having clause, to limit the subgroups
        public string subgroupFilter;

/* CAN SUPPORT THIS FEATURE USING THE CUSTOM OVERRIDES INSTEAD
        // these support saving a value for the highest total of all groups, such as best year
        public string topSubgroupAggregate;
        public string topSubgroupRollupGroupField;
        public string topSubgroupRollupValueField;
*/

        // if you set this, each total row will include the aggregate of this special field
        // you must override the processRollupFieldset method to process the result
        // deprecated, use set of strings instead: public string specialRollupAggregate;
        public set<string> specialRollupAggregates;

        // need the list of special fields we are rolling into, even though the function processes them
        public set<string> specialRollupFields;

        // need the list of special fields we want to query and might want to change, but do NOT want to zero or null out
        public set<string> additionalQueryFields;

        // this set has ALL the fieldnames
        private set<string> parentRollupFieldnames;
        public set<string> getAllFields() {
            if (parentRollupFieldNames == null) setAllFields();
            return parentRollupFieldnames;
        }

        // this set has all but the "additional" fieldnames, which we won't zero out
        private set<string> fieldNamesToZeroBeforeRollup;
        public set<string> getAllRollupFields() {
            if (parentRollupFieldNames == null) setAllFields();
            return fieldNamesToZeroBeforeRollup;
        }

        private string rollupFieldList;

        // method to get list of rollup fields for soql
        public string getRollupFieldList() {
            return rollupFieldList;
        }

        private void setAllFields() {
            // the method formerly known as "compileMe"
            string flist = 'id';
            parentRollupFieldnames = new set<string>{  };
            fieldNamesToZeroBeforeRollup = new set<string>{ };

            // regular fields
            if (aggregateToParentFieldnameMap != null)
                for (string s : aggregateToParentFieldnameMap.values())
                    addToFieldlist(flist, s);

            // subgroup fields
            if (subgroupValueToParentFieldnameMap != null) {
                for ( map<object, string> smap : subgroupValueToParentFieldnameMap.values() ) {
                    for ( string s : smap.values() )
                        addToFieldlist(flist, s);
                }
            }

            // special rollup fields
            if (specialRollupFields != null) {
                for (string s : specialRollupFields)
                    addToFieldlist(flist, s);
            }

            // additional query fields
            if (additionalQueryFields != null) {
                for (string s : additionalQueryFields) {
                    // add this one to the "all fields" list and query string, but not the rollup list
                    if (s != null && !parentRollupFieldnames.contains(s)) {
                        parentRollupFieldnames.add(s);
                        flist += ', ' + s;
                    }
                }
            }

            rollupFieldList = flist;

        }

        private void addToFieldlist(string flist, string s) {
            if (s != null && !parentRollupFieldnames.contains(s)) {
                parentRollupFieldnames.add(s);
                fieldNamesToZeroBeforeRollup.add(s);
                flist += ', ' + s;
            }
        }

        // map of aggregate field labels to the aggregates
        private map<string, string> aggregateLabelMap;
        public map<string, string> getAggregateLabelMap() { return aggregateLabelMap; }

        // method to get list of aggregate fields for soql
        public string getAggregateFieldList() {
            string flist = '';
            aggregateLabelMap = new map<string, string>();
            allAggregates = new set<string>();

            // subgrouping field
            flist = addToAggregateList(flist, subgroupExpression);

            // regular aggregates
            if (aggregateToParentFieldnameMap != null)
                for (string s : aggregateToParentFieldnameMap.keyset())
                    flist = addToAggregateList(flist, s);

            // subgroup aggregates
            if (subgroupValueToParentFieldnameMap != null)
                for ( string s : subgroupValueToParentFieldnameMap.keyset() )
                    flist = addToAggregateList(flist, s);

            // special rollup fields
            if (specialRollupAggregates != null) {
                for (string s:specialRollupAggregates) {
                    flist = addToAggregateList(flist, s);
                }
            }

            system.assert((flist != ''), 'Your rollup specification has no aggregate fields to roll up.');
            return flist.substring(1);
        }

        private set<string> allAggregates;

        private string addToAggregatelist(string flist, string s) {
            if (s != null && !allAggregates.contains(s)) {
                string label = 'AggregateField' + string.valueOf(aggregateLabelMap.size());
                aggregateLabelMap.put(s, label);
                allAggregates.add(s);
                flist += ', ' + s + ' ' + label;
            }
            return flist;
        }
    }


    public string getQuerySelectClause() {
        // build the full field list for all rollup specs
        string flist = 'id';
        for ( string s : parentRollupFields )
            flist += ', ' + s;

        return flist;
    }


    public void setParentFieldList() {
        // get parentRollupFields list from specs
        for (RollupSpec spec : this.specs) {
            parentRollupFields.addAll( spec.getAllFields() );
            parentFieldsToZeroBeforeRollup.addAll( spec.getAllRollupFields() );
        }
    }


    public void runRollups() {
        system.assert( parentObjName != null, 'You must specify objects to roll up.' );
        system.assert( !specs.isEmpty(), 'You must add one or more rollup specifications before running rollups.' );

        if (batchSize != null && !isRunningBatch) {
            // run batch if we have the batch params, otherwise run a regular rollup
            //  if we're already in the batch process (came from the execute method) don't do this again, or we get unwanted recursion
            runBatchRollup();
        } else {
            system.assert( parentRecIds != null, 'You must specify objects to roll up.' );

            // get the state of the rollup set at the beginning
            if (parentRecsInitial == null) {
                // build the full field list for all rollup specs
                string flist = getQuerySelectClause();
                parentRecsInitial = database.query(
                    'SELECT ' + flist + ' FROM ' + parentObjName + ' WHERE id IN : parentRecIds');
            }

            // get the blank starting list to which we will add rollups
            map<id, sobject> objMap = new map<id, sobject>();

            for (sobject obj : parentRecsInitial) {
                sobject newObj = obj.clone(true, true);
                for ( string fld : parentFieldsToZeroBeforeRollup ) {
                    string ft = UTIL_Describe.getFieldType(parentObjName, fld);
                    boolean isNum = (ft == 'INTEGER' || ft == 'CURRENCY' || ft == 'PERCENT' || ft == 'DOUBLE');
                    //system.debug (logginglevel.warn, 'the field is ' + fld);
                    //system.debug (logginglevel.warn, 'the type is ' + ft);

                    if (isNum) {
                        newObj.put(fld, 0);
                    } else {
                        newObj.put(fld, null);
                    }
                }
                objMap.put( obj.id, newObj );
            }

            for (RollupSpec spec : specs) {

                // build the soql for the rollup
                string soql = 'SELECT ' + spec.FKFieldnameInChild + ', ' + spec.getAggregateFieldList() +
                    ' FROM ' + spec.childObjectName + ' WHERE ' + spec.FKFieldnameInChild + ' IN : parentRecIds';
                if (spec.filter != null)
                    soql += ' AND ' + spec.filter;

                if (spec.setFiltersIn != null) {
                    for (string s : spec.setFiltersIn.keyset()) {
                        set<string> IsIn = spec.setFiltersIn.get(s);
                        string inClause = getInClause(IsIn);
                        if (inClause != '') soql += ' AND ' + s + ' IN (' + inClause + ') ';
                    }
                }
                if (spec.setFiltersNotIn != null) {
                    for (string s : spec.setFiltersNotIn.keyset()) {
                        set<string> notIn = spec.setFiltersNotIn.get(s);
                        string inClause = getInClause(notIn);
                        if (inClause != '') soql += ' AND ' + s + ' NOT IN (' + inClause + ') ';
                    }
                }
                soql += ' GROUP BY ' + ((spec.subgroupExpression == null) ? spec.FKFieldnameInChild :
                    ('ROLLUP (' + spec.FKFieldnameInChild + ', ' + spec.subgroupExpression + ')'));
                soql += ' HAVING ' + spec.FKFieldnameInChild    + ' != null ';
                if (spec.subgroupFilter != null)
                    soql += 'AND (' + spec.subgroupFilter + ') ';

                system.debug(logginglevel.warn, 'SOQL for rollup: ' + soql);

                // query and process the rows
                for (sobject rollupRow : database.query(soql)) {

                    //system.debug(logginglevel.warn,'row: ' + rollupRow);

                    // get the id for this result row
                    id oid = (id)(rollupRow.get(spec.FKFieldnameInChild));

                    // get an object to roll into
                    sObject obj = objMap.get(oid);

                    // is this a subgroup or a total row?
                    string subgroupLabel = (spec.subgroupExpression == null) ? null :
                         spec.aggregateLabelMap.get(spec.subgroupExpression);

                    if (subgroupLabel != null && rollupRow.get(subgroupLabel) != null) {

                        //system.debug(logginglevel.warn,spec.subgroupExpression);
                        //system.debug(logginglevel.warn,spec.aggregateLabelMap);

                        object subgroupValue = rollupRow.get(subgroupLabel);
                        if (subgroupValue != null && spec.subgroupValueToParentFieldnameMap != null) {

                            // get each subgroup rollup aggregate
                            for (string agg : spec.subgroupValueToParentFieldnameMap.keyset()) {
                                string label = spec.aggregateLabelMap.get(agg);
                                map<object, string> subgroupMap = spec.subgroupValueToParentFieldnameMap.get(agg);

                                // do we have this subgroup value?
                                if (subgroupMap.containsKey(subgroupValue)) {
                                    string targetField = subgroupMap.get(subgroupValue);

                                    // set the field to the aggregate value, casting to the proper type
                                    setValue(parentObjName, targetField, obj, rollupRow, label);
                                }
                            }
                        }

                        customResultRowProcessing(rollupRow,spec,obj);
                    } else {

                        // get each main rollup field
                        for (string agg : spec.aggregateToParentFieldnameMap.keyset()) {
                            string label = spec.aggregateLabelMap.get(agg);
                            string targetField = spec.aggregateToParentFieldnameMap.get(agg);

                            // set the field to the aggregate value, casting to the proper type
                            setValue(parentObjName, targetField, obj, rollupRow, label);
                        }

                        customResultRowProcessing(rollupRow,spec,obj);
                    }
                }
            }

            // allow for an override function that can process parent records before the update
            customFinalProcessing(objMap);

            // remove unchanged records
            for (sobject obj : parentRecsInitial) {
                sobject compare = objMap.get(obj.id);
                boolean diff = false;
                for ( string fld : parentRollupFields ) {
                    if ( obj.get(fld) != compare.get(fld) ) {
                        diff = true;
                        break;
                    }
                }
                if (!diff) objMap.remove(obj.id);
            }

            // update the rollups
            if (!objMap.isEmpty()) {
                update objMap.values();
            }
        }
    }


    private string getInClause (set<string> stringSet) {
        string inClause = '';
        if (stringSet.size() > 0) {
            for (string t:stringSet) {
                inClause += ',\'' + t + '\'';
            }
            inClause = inClause.substring(1);  // lop off first comma
        }

        return inClause;
    }

    // helper function that sets aggregate values after casting to proper type
    void setValue(string parentObjName, string targetField, sObject obj, sObject r, string lbl) {
        string ft = UTIL_Describe.getFieldType(parentObjName, targetField);
        boolean nv = (r.get(lbl) == null);

        if (ft == 'DATE')
            obj.put( targetField, ((date)(r.get(lbl))));
        else if (ft == 'DATETIME')
            obj.put( targetField, ((datetime)(r.get(lbl))));
        else if (ft == 'INTEGER')
            obj.put( targetField, (nv ? 0 : ((integer)(r.get(lbl)))));
        else if (ft == 'CURRENCY' || ft == 'PERCENT' || ft == 'DOUBLE')
            obj.put( targetField, (nv ? 0 :((decimal)(r.get(lbl)))));
        else if (ft == 'REFERENCE')
            obj.put( targetField, ((id)(r.get(lbl))));
        else if (ft == 'BOOLEAN')
            obj.put( targetField, ((boolean)(r.get(lbl))));
        else obj.put( targetField, ((string)(r.get(lbl))));
    }


    public virtual void customResultRowProcessing(SObject rollupRow,RollupSpec spec,SObject parentRec) {
        // this is called for each non-subgroup row of our aggregate result to allow for custom calculation of rollup values
        // you should write code here to insert values into the parent obj in parentRec
        // you must also make sure that any fields you're writing to are contained in specialRollupFields property of the spec
    }

    public virtual void customFinalProcessing (map<id, sobject> objMap) {
        // this is called at the end, just before the runRollup method determines which parent objects
        //  have changed and need to be written to
        // before the write to database happens, you can make your last minute changes here
    }

}