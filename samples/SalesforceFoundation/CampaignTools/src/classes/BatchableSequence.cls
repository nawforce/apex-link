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
 * @group BatchableSequence
 * @description A BatchableSequence is a sequence of Batchable classes that
 * when run will cause each Batchable in the sequence to execute the next
 * Batchable in the sequence upon finishing.  You can use this class to cause a
 * series of Batchable classes to execute in serial order.
 */
public with sharing class BatchableSequence implements Interface_x {
    public interface Interface_x {
        Batch add(Batch b);
        Batch add(Batch b, Integer scope);
        Id run();
        Integer count();
    }

    public interface Factory {
        Interface_x create();
    }

    public with sharing class DefaultFactory implements Factory {
        public Interface_x create() {
            return new BatchableSequence();
        }
    }

    /** @description A list of the Batchables in this sequence, in order */
    @TestVisible
    private List<Batch> batches = new List<Batch>();

    /**
     * @description Add a Batch to this sequence.
     * @param b The batch to add.  If the batch does not already have a scope size specified, it will be defaulted to 200.
     * @return Batch (the batch that was added)
     */
    public Batch add(Batch b) {
        if (null == b.getScope()) {
            b.setScope(200);
        }
        if (!batches.isEmpty()) {
            batches.get(batches.size() - 1).setNext(b);
        }
        batches.add(b);
        return b;
    }

    /**
     * @description Add a batch to this sequence while also specifying the
     * scope size.
     *
     * @param b The batch to add.  If the batch has a scope size specified, it will be overwritten by the scope parameter.
     * @param scope The size of the scope that the batch should be executed with (i.e., the value passed along to Database.executeBatch())
     * @return Batch (the batch that was added)
     */
    public Batch add(Batch b, Integer scope) {
        b.setScope(scope);
        return add(b);
    }

    /**
     * @description Initiate this batchable sequence by executing the first
     * batch in the sequence.
     *
     * @return Id (the id of the AsyncApexJob corresponding to the first batch in the sequence, or null if this sequence is empty)
     */
    public Id run() {
        if (!batches.isEmpty()) {
            return batches.get(0).executeBatch();
        }
        return null; // @todo throw exception instead?
    }

    /**
     * @description return the count of jobs in this batchable
     *
     * @return Integer 
     */
    public Integer count() {
        return batches.size();
    }

    /**
     * @author Salesforce.org
     * @group BatchableSequence
     * @description An individual batch that can be added to a batchable
     * sequence.
     */
    public abstract with sharing class Batch {

        /** @description The next batch to run after this one finishes */
        @TestVisible
        private Batch next;

        /** @description The scope size with which to run this batch */
        private Integer scope;

        /**
         * @description Virtual implementation of the finish() method from the
         * Database.Batchable interface.  This will execute the next batch, if
         * there is one.
         *
         * @return void
         */
        public virtual void finish(Database.BatchableContext bc) {
            if (null != next) {
                next.executeBatch();
            }
        }

        /**
         * @description Set the scope size for this batch.
         * @param scope The scope size to pass to Database.executeBatch()
         * @return void
         */
        public void setScope(Integer scope) {
            this.scope = scope;
        }

        /**
         * @description Get the scope size for this batch (if set).
         * @return Integer (the scope size this batch will run with, or null if not set)
         */
        public Integer getScope() {
            return scope;
        }

        /**
         * @description Set the next batch to be run after this batch finishes.
         * @param next The next batch to be run.
         * @return void
         */
        public void setNext(Batch next) {
            this.next = next;
        }

        /**
         * @description This will cause this batch to be executed, i.e.,
         * Database.executeBatch() will be called with this batch as the batch
         * to execute and this batch's scope.
         *
         * @return Id The id of the AsyncApexJob returned by Database.executeBatch()
         */
        public abstract Id executeBatch();
    }
}