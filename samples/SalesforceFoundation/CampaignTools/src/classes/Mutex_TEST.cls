@isTest
private with sharing class Mutex_TEST {
    private static testMethod void testConstructorValidField() {
        Contact c = new Contact(LastName = 'Test Contact');
        insert c;

        Boolean exceptionThrown = false;

        try {
            Mutex m = new Mutex(c.Id, Contact.EmailBouncedDate);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        System.assert(!exceptionThrown);
    }

    private static testMethod void testConstructorNonModifiableField() {
        Contact c = new Contact(LastName = 'Test Contact');
        insert c;

        Boolean exceptionThrown = false;

        try {
            Mutex m = new Mutex(c.Id, Contact.SystemModstamp);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        System.assert(exceptionThrown);
    }

    private static testMethod void testConstructorNotDateTimeField() {
        Contact c = new Contact(LastName = 'Test Contact');
        insert c;

        Boolean exceptionThrown = false;

        try {
            Mutex m = new Mutex(c.Id, Contact.Name);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        System.assert(exceptionThrown);
    }

    private static testMethod void testConstructorFieldDoesNotBelongToRecord() {
        Contact c = new Contact(LastName = 'Test Contact');
        insert c;

        Boolean exceptionThrown = false;

        try {
            Mutex m = new Mutex(c.Id, Opportunity.Amount);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        System.assert(exceptionThrown);
    }

    public static testMethod void testGetLockRecordReturnsNullWhenNoRecordFound() {
        Id recordId = Contact.sObjectType.getDescribe().getKeyPrefix() + '000000000000';

        Mutex m = new Mutex(recordId, Contact.EmailBouncedDate);

        System.assertEquals(null, m.getLockRecord(false));
    }

    public static testMethod void testGetLockRecordReturnsCorrectRecord() {
        Schema.SObjectField mutexField = Contact.EmailBouncedDate;

        Contact c = new Contact(LastName = 'Test Contact');
        insert c;

        Mutex m = new Mutex(c.Id, mutexField);

        sObject so = m.getLockRecord(false);

        System.assert(so instanceof Contact);
        System.assertEquals(c.Id, so.Id);

        Boolean mutexFieldWasQueried = true;

        try {
            so.get(mutexField);
        } catch (SObjectException e) {
            mutexFieldWasQueried = false;
        }

        System.assert(mutexFieldWasQueried);
    }

    public static testMethod void testAcquireLockFailsIfMissingLockRecord() {
        Id recordId = Contact.sObjectType.getDescribe().getKeyPrefix() + '000000000000';

        Mutex m = new Mutex(recordId, Contact.EmailBouncedDate);

        System.assertEquals(false, m.acquireLock(null, null));
    }

    public static testMethod void testAcquireLockFailsIfRecordAlreadyLocked() {
        Schema.SObjectField mutexField = Contact.EmailBouncedDate;
        DateTime lockTime = DateTime.now();

        Contact c = new Contact(
            LastName = 'Test Contact',
            EmailBouncedDate = lockTime
        );
        insert c;

        Mutex m = new Mutex(c.Id, mutexField);

        Boolean lockAcquired = m.acquireLock(c, lockTime);

        System.assertEquals(false, lockAcquired);
    }

    public static testMethod void testAcquireLockUpdatesLockField() {
        Schema.SObjectField mutexField = Contact.EmailBouncedDate;

        Contact c = new Contact(LastName = 'Test Contact');
        insert c;

        DateTime lockTime = DateTime.now();

        Mutex m = new Mutex(c.Id, mutexField);

        Boolean lockAcquired = m.acquireLock(c, lockTime);

        c = [
            SELECT EmailBouncedDate
            FROM Contact
            WHERE Id = :c.Id
        ];

        System.assert(lockAcquired);
        System.assertEquals(lockTime, c.get(mutexField));
    }

    public static testMethod void testReleaseLockReturnsNullIfNoRecord() {
        Id recordId = Contact.sObjectType.getDescribe().getKeyPrefix() + '000000000000';

        Mutex m = new Mutex(recordId, Contact.EmailBouncedDate);

        System.assertEquals(false, m.releaseLock(null));
    }

    public static testMethod void testReleaseLockClearsLockField() {
        Schema.SObjectField mutexField = Contact.EmailBouncedDate;

        DateTime lockTime = DateTime.now();

        Contact c = new Contact(
            LastName = 'Test Contact',
            EmailBouncedDate = lockTime
        );
        insert c;

        Mutex m = new Mutex(c.Id, mutexField);

        Boolean lockReleased = m.releaseLock(c);

        c = [
            SELECT EmailBouncedDate
            FROM Contact
            WHERE Id = :c.Id
        ];

        System.assert(lockReleased);
        System.assertEquals(null, c.get(mutexField));
    }

    public static testMethod void testGetLockTimeReturnsNullIfMissingLockRecord() {
        Schema.SObjectField mutexField = Contact.EmailBouncedDate;
        DateTime lockTime = DateTime.now();

        Contact c = new Contact(
            LastName = 'Test Contact',
            EmailBouncedDate = lockTime
        );
        insert c;

        Mutex m = new Mutex(c.Id, mutexField);

        Boolean lockReleased = m.releaseLock(c);

        c = [
            SELECT EmailBouncedDate
            FROM Contact
            WHERE Id = :c.Id
        ];

        System.assert(lockReleased);
        System.assertEquals(null, c.get(mutexField));
    }

    public static testMethod void testGetLockTimeReturnsLockTime() {
        Id recordId = Contact.sObjectType.getDescribe().getKeyPrefix() + '000000000000';
        DateTime lockTime = DateTime.now();
        Contact c = new Contact(EmailBouncedDate = lockTime);

        Mutex m = new Mutex(recordId, Contact.EmailBouncedDate);

        System.assertEquals(lockTime, m.getLockTime(c));
    }
}