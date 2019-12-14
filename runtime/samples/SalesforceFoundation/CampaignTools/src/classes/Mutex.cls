/*
    Copyright (c) 2015 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.com Foundation nor the names of
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
 * @group Mutex
 * @description Allows obtaining a mutex on a particular record, as long as that record has a mutex datetime field.  This relies on SELECT ... FOR UPDATE for concurrency control.
 */
public with sharing class Mutex implements MutexInterface {
    /**
     * @description The recordId of the record to be locked
     */
    private Id recordId;

    /**
     * @description The field on the record to be used to store the acquire time of the lock.  If this field is null, the record is not locked.
     */
    private Schema.SObjectField mutexField;

    /**
     * @description Construct a Mutex for a specific record and mutexField.  Note this does not lock the record-- you must call acquireLock() to attempt to lock the record.  mutexField is a DateTime field on the record to be locked.
     *
     * @param recordId The id of the record to be locked.
     * @param mutexField A DateTime field on the record to be locked-- this will store the time the lock was acquired, or null if not currently locked
     */
    public Mutex(Id recordId, Schema.SObjectField mutexField) {

        Schema.DescribeFieldResult fieldDescribe = mutexField.getDescribe();
        Schema.DescribeSObjectResult objectDescribe = recordId.getSObjectType().getDescribe();

        Set<Schema.SObjectField> objectFields = new Set<Schema.SObjectField>(
            objectDescribe.fields.getMap().values()
        );

        Boolean mutexFieldIsDateTime = (Schema.DisplayType.DATETIME == fieldDescribe.getType());
        Boolean mutexFieldExists = objectFields.contains(mutexField);

        if (!mutexFieldExists || !mutexFieldIsDateTime) {
            throw new InvalidMutexFieldException(
                String.format(
                    Label.MutexFieldInvalidDateTimeException,
                    new List<String>{
                        fieldDescribe.getLabel(),
                        objectDescribe.getLabel()
                    }
                )
            );
        }

        if (!fieldDescribe.isUpdateable()) {
            throw new InvalidMutexFieldException(
                String.format(
                    Label.MutexFieldInvalidUpdateableException,
                    new List<String>{fieldDescribe.getLabel()}
                )
            );
        }

        this.recordId = recordId;
        this.mutexField = mutexField;
    }

    /**
     * @description Construct a query for the record to be locked.
     *
     * @param forUpdate True if this query is to include the 'FOR UPDATE' clause-- this should be true if trying to perform an exclusive operation on the record.
     * @return String The generated SOQL query.
     */
    private String getLockQuery(Boolean forUpdate) {
        String sObjectTypeName = recordId.getSobjectType().getDescribe().getName();
        String mutexFieldName = mutexField.getDescribe().getName();
        String forUpdateClause = (forUpdate ? 'FOR UPDATE': '');

        String queryFormat = 'SELECT {0} FROM {1} WHERE Id = \'\'{2}\'\' LIMIT 1 {3}';

        String query = String.format(
            queryFormat,
            new List<String>{
                mutexFieldName,
                sObjectTypeName,
                recordId,
                forUpdateClause
            }
        );

        return query;
    }

    /**
     * @description Queries the database for the record to be locked.  This will return null if the record was unable to be queried (i.e. if it doesn't exist, OR if the FOR UPDATE clause prevented the record from being queried).
     *
     * @param forUpdate True if this query is to include the 'FOR UPDATE' clause-- this should be true if trying to perform an exclusive operation on the record.
     * @return sObject The sObject corresponding to the record to be locked.
     */
    @TestVisible
    private sObject getLockRecord(Boolean forUpdate) {
        List<sObject> results;
        try {
            results = Database.query(getLockQuery(true));
        } catch (Exception e) {
            return null;
        }

        if (results.isEmpty()) {
            return null;
        }

        sObject lockRecord = results.get(0);
        return lockRecord;
    }

    /**
     * @description Attempt to acquire a lock.  This will attempt to update the lock record in the database.  This returns true if the lock was able to be acquired, false otherwise.
     *
     * @return Boolean
     */
    public Boolean acquireLock() {
        return acquireLock(
            getLockRecord(true),
            DateTime.now()
        );
    }

    /**
     * @description Attempt to acquire a lock.  This will attempt to update the lock record in the database.  This returns true if the lock was able to be acquired, false otherwise.
     *
     * @param lockRecord The sObject corresponding to the record to be locked (must be queried with the mutexField)
     * @param lockTime The DateTime of the lock acquire time.  This will be written to the mutexField on the record to be locked.
     * @return Boolean
     */
    @TestVisible
    private Boolean acquireLock(sObject lockRecord, DateTime lockTime) {
        if (null == lockRecord) {
            // can't get a lock on a record that doesn't exist
            return false;
        }

        if (null != getLockTime(lockRecord)) {
            // can't get a lock on a record that is already locked
            return false;
        }

        lockRecord.put(mutexField, lockTime);
        update lockRecord;
        return true;
    }

    /**
     * @description Release the lock that has currently been acquired (if any) on the lock record.  Returns true to indicate that the record is not locked, or false if unable to release lock.
     *
     * @return Boolean
     */
    public Boolean releaseLock() {
        return releaseLock(getLockRecord(true));
    }

    /**
     * @description Release the lock that has currently been acquired (if any) on the lock record.  Returns true to indicate that the record is not locked, or false if unable to release lock.
     *
     * @return Boolean
     */
    @TestVisible
    public Boolean releaseLock(sObject lockRecord) {
        if (null == lockRecord) {
            return false;
        }

        lockRecord.put(mutexField, null);
        update lockRecord;
        return true;
    }

    /**
     * @description Get the DateTime that a lock was acquired on the record.  Returns null if the record is not locked.
     *
     * @return DateTime
     */
    public DateTime getLockTime() {
        return getLockTime(getLockRecord(false));
    }

    /**
     * @description Get the DateTime that a lock was acquired on the record.  Returns null if the record is not locked.
     *
     * @param lockRecord The sObject corresponding to the record to be locked (must be queried with the mutexField)
     * @return DateTime
     */
    @TestVisible
    private DateTime getLockTime(sObject lockRecord) {
        if (null == lockRecord) {
            return null;
        }
        return (DateTime) lockRecord.get(mutexField);
    }

    public interface MutexInterface {
        Boolean acquireLock();
        Boolean releaseLock();
        DateTime getLockTime();
    }

    public interface Factory {
        MutexInterface create(Id recordId, Schema.SObjectField mutexField);
    }

    public with sharing class DefaultFactory implements Factory {
        public MutexInterface create(Id recordId, Schema.SObjectField mutexField) {
            return new Mutex(recordId, mutexField);
        }
    }

    /**
     * @description Any exceptions thrown by the Mutex class will extend this class
     */
    public virtual class CustomException extends Exception {}

    /**
     * @description Exception thrown when a valid mutex field is not found on the sObjectType of this Mutex's recordId.
     */
    public class InvalidMutexFieldException extends CustomException {}
}