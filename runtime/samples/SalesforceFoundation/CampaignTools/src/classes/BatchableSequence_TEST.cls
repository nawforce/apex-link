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
@isTest
private with sharing class BatchableSequence_TEST {
    private static testMethod void testAddBatchWithoutScopeAddsDefaultScope() {
        BatchableSequence bs = new BatchableSequence();
        ConcreteBatch batch = new ConcreteBatch();

        bs.add(batch);

        System.assertEquals(200, batch.getScope());
    }

    private static testMethod void testAddBatchWithoutScopeHonorsExistingScope() {
        BatchableSequence bs = new BatchableSequence();
        ConcreteBatch batch = new ConcreteBatch();
        batch.setScope(999);

        bs.add(batch);

        System.assertEquals(999, batch.getScope());
    }

    private static testMethod void testAddBatchWithScopeOverridesExistingScope() {
        BatchableSequence bs = new BatchableSequence();
        ConcreteBatch batch = new ConcreteBatch();
        batch.setScope(999);

        bs.add(batch, 222);

        System.assertEquals(222, batch.getScope());
    }

    private static testMethod void testAddSecondBatchLinksToPreviousBatch() {
        BatchableSequence bs = new BatchableSequence();
        ConcreteBatch firstBatch = new ConcreteBatch();
        ConcreteBatch secondBatch = new ConcreteBatch();

        bs.add(firstBatch);
        bs.add(secondBatch);

        System.assertEquals(secondBatch, firstBatch.next);
    }

    private static testMethod void testGetScopeReturnsNullIfNoScopeSet() {
        ConcreteBatch batch = new ConcreteBatch();
        System.assertEquals(null, batch.getScope());
    }

    private static testMethod void testGetScopeReturnsScopeIfScopeSet() {
        ConcreteBatch batch = new ConcreteBatch();
        batch.setScope(999);
        System.assertEquals(999, batch.getScope());
    }

    private static testMethod void testFinishWithNoNextBatchIsNoOp() {
        ConcreteBatch batch = new ConcreteBatch();

        try {
            batch.finish(null);
        } catch (Exception e) {
            System.assert(
                false,
                'Finish with no next batch threw an exception: ' + e.getMessage()
            );
        }
    }

    private static testMethod void testFinishWillRunNextBatch() {
        ConcreteBatch firstBatch = new ConcreteBatch();
        ConcreteBatch secondBatch = new ConcreteBatch();

        firstBatch.setNext(secondBatch);

        firstBatch.finish(null);

        System.assertEquals(0, firstBatch.executionCount);
        System.assertEquals(1, secondBatch.executionCount);
    }

    private static testMethod void testExecuteBatchReturnsAsyncApexJobId() {
        ConcreteBatch batch = new ConcreteBatch();
        Id actualId = batch.executeBatch();
        System.assertEquals(batch.asyncApexJobId, actualId);
    }

    private static testMethod void testBatchableSequenceRunExecutesFirstBatch() {
        BatchableSequence bs = new BatchableSequence();
        ConcreteBatch batch = new ConcreteBatch();

        bs.add(batch);
        bs.run();

        System.assertEquals(1, batch.executionCount);
    }

    private static testMethod void testBatchableSequenceReturnsAsyncApexJobIdFromRun() {
        BatchableSequence bs = new BatchableSequence();
        ConcreteBatch batch = new ConcreteBatch();

        bs.add(batch);
        Id actualId = bs.run();

        System.assertEquals(batch.asyncApexJobId, actualId);
    }

    private static testMethod void testBatchableSequenceReturnsNullFromRunWhenEmpty() {
        BatchableSequence bs = new BatchableSequence();
        System.assertEquals(null, bs.run());
    }

    private class ConcreteBatch extends BatchableSequence.Batch {
        public Integer executionCount = 0;
        public Id asyncApexJobId = AsyncApexJob.sObjectType.getDescribe().getKeyPrefix() + '000000000001';

        public override Id executeBatch() {
            executionCount++;
            return asyncApexJobId;
        }
    }
}