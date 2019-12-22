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
private with sharing class CampaignListSegment_TEST2 {
    private static testMethod void testCriteriaWithSingleSource() {
        /*
         * Criteria tree as s-expression:
         * (SOURCE A)
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createSourceSegment(A);

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
    }

    private static testMethod void testCriteriaWithSingleSourceExclusion() {
        /*
         * Criteria tree as s-expression:
         * (NOT (SOURCE A))
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createExcludedSourceSegment(A);

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
    }

    private static testMethod void testCriteriaWithAndRootNodeAndTwoSources() {
        /*
         * Criteria tree as s-expression:
         * (AND
         *     (SOURCE A)
         *     (SOURCE B)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id C = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createAndSegment();
        rootSegment.addChild(createSourceSegment(A));
        rootSegment.addChild(createSourceSegment(B));

        /*
         * Test members:
         * (), (A), (B), (C), (A, B), (A, C), (B, C), (A, B, C)
         *
         * Expected to meet criteria:
         * (A, B), (A, B, C)
         *
         * Expected to not meet criteria:
         * (), (A), (B), (C), (A, C), (B, C)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B, C);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, C)')));

    }

    private static testMethod void testCriteriaWithOrRootNodeAndTwoSources() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (SOURCE A)
         *     (SOURCE B)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id C = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createOrSegment();
        rootSegment.addChild(createSourceSegment(A));
        rootSegment.addChild(createSourceSegment(B));

        /*
         * Test members:
         * (), (A), (B), (C), (A, B), (A, C), (B, C), (A, B, C)
         *
         * Expected to meet criteria:
         * (A), (B), (A, B), (A, C), (B, C), (A, B, C)
         *
         * Expected to not meet criteria:
         * (), (C)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B, C);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B, C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(C)')));
    }

    private static testMethod void testCriteriaWithAndRootNodeAndTwoSourcesWithOneExclusion() {
        /*
         * Criteria tree as s-expression:
         * (AND
         *     (NOT (SOURCE A))
         *     (SOURCE B)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id C = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createAndSegment();
        rootSegment.addChild(createExcludedSourceSegment(A));
        rootSegment.addChild(createSourceSegment(B));

        /*
         * Test members:
         * (), (A), (B), (C), (A, B), (A, C), (B, C), (A, B, C)
         *
         * Expected to meet criteria:
         * (B), (B, C)
         *
         * Expected to not meet criteria:
         * (), (A), (C), (A, B), (A, C), (A, B, C)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B, C);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B, C)')));
    }

    private static testMethod void testCriteriaWithOrRootNodeAndTwoSourcesWithOneExclusion() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (NOT (SOURCE A))
         *     (SOURCE B)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id C = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createOrSegment();
        rootSegment.addChild(createExcludedSourceSegment(A));
        rootSegment.addChild(createSourceSegment(B));

        /*
         * Test members:
         * (), (A), (B), (C), (A, B), (A, C), (B, C), (A, B, C)
         *
         * Expected to meet criteria:
         * (), (B), (C), (A, B), (B, C), (A, B, C)
         *
         * Expected to not meet criteria:
         * (A), (A, C)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B, C);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B, C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, C)')));
    }

    private static testMethod void testCriteriaWithComplexCriteriaTree() {
        /*
         * Criteria tree as s-expression:
         * (AND
         *     (OR
         *         (SOURCE A)
         *         (NOT (SOURCE B))
         *     )
         *     (OR
         *         (SOURCE C)
         *         (SOURCE D)
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id C = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id D = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id E = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createAndSegment();
        CampaignListSegment orSegment1 = createOrSegment();
        orSegment1.addChild(createSourceSegment(A));
        orSegment1.addChild(createExcludedSourceSegment(B));
        CampaignListSegment orSegment2 = createOrSegment();
        orSegment2.addChild(createSourceSegment(C));
        orSegment2.addChild(createSourceSegment(D));
        rootSegment.addChild(orSegment1);
        rootSegment.addChild(orSegment2);

        /*
         * Test members:
         * (), (A), (B), (A, B), (C), (A, C), (B, C), (A, B, C),
         * (D), (A, D), (B, D), (A, B, D), (C, D), (A, C, D), (B, C, D), (A, B, C, D),
         * (E), (A, E), (B, E), (A, B, E), (C, E), (A, C, E), (B, C, E), (A, B, C, E),
         * (D, E), (A, D, E), (B, D, E), (A, B, D, E), (C, D, E), (A, C, D, E), (B, C, D, E), (A, B, C, D, E)
         *
         * Expected to meet criteria:
         * (C), (A, C), (A, B, C),
         * (D), (A, D), (A, B, D), (C, D), (A, C, D), (A, B, C, D),
         * (C, E), (A, C, E), (A, B, C, E),
         * (D, E), (A, D, E), (A, B, D, E), (C, D, E), (A, C, D, E), (A, B, C, D, E)
         *
         * Expected to not meet criteria:
         * (), (A), (B), (A, B), (B, C),
         * (B, D), (B, C, D),
         * (E), (A, E), (B, E), (A, B, E), (B, C, E),
         * (B, D, E), (B, C, D, E)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B, C, D, E);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(D)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, D)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, D)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(C, D)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, C, D)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C, D)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(C, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, C, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(D, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, D, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, D, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(C, D, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, C, D, E)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B, C, D, E)')));

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, C)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, D)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, C, D)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(E)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, E)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, E)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B, E)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, C, E)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, D, E)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B, C, D, E)')));
    }

    private static testMethod void testNegatedOrWithNegatedEmptyAndAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (NOT AND)
         *         (NOT SOURCE A)
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (AND)
         *     (SOURCE A)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createNegatedAndSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createAndSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testNegatedOrWithNegatedEmptyAndAndSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (NOT (AND))
         *         (SOURCE A)
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (AND)
         *     (NOT (SOURCE A))
         * )
         */


        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createNegatedAndSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createAndSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testNegatedOrWithNegatedEmptyOrAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (NOT (OR))
         *         (NOT (SOURCE A))
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (OR)
         *     (SOURCE A)
         * )
         */


        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createNegatedOrSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createOrSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testNegatedOrWithNegatedEmptyOrAndSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (NOT (OR))
         *         (SOURCE A)
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (OR)
         *     (NOT (SOURCE A))
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createNegatedOrSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createOrSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }


    private static testMethod void testNegatedOrWithEmptyAndAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (AND)
         *         (NOT (SOURCE A))
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (NOT (AND))
         *     (SOURCE A)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createAndSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createNegatedAndSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }


    private static testMethod void testNegatedOrWithEmptyAndAndSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (AND)
         *         (SOURCE A)
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (NOT (AND))
         *     (NOT (SOURCE A))
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createAndSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createNegatedAndSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testNegatedOrWithEmptyOrAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (OR)
         *         (NOT (SOURCE A))
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (NOT (OR))
         *     (SOURCE A)
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createOrSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createNegatedOrSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testNegatedOrWithEmptyOrAndSource() {
        /*
         * Criteria tree as s-expression:
         * (NOT
         *     (OR
         *         (OR)
         *         (SOURCE A)
         *     )
         * )
         *
         * Equivalent to:
         * (AND
         *     (NOT (OR))
         *     (NOT (SOURCE A))
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createNegatedOrSegment();
        rootSegment.addChild(createOrSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createAndSegment();
        rootSegment2.addChild(createNegatedOrSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithNegatedEmptyAndAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (NOT (AND))
         *     (NOT (SOURCE A))
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (AND)
         *         (SOURCE A)
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createOrSegment();
        rootSegment.addChild(createNegatedAndSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createAndSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithNegatedEmptyAndAndSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (NOT (AND))
         *     (SOURCE A)
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (AND)
         *         (NOT (SOURCE A))
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment rootSegment = createOrSegment();
        rootSegment.addChild(createNegatedAndSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createAndSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithNegatedEmptyOrAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (NOT (OR))
         *     (NOT (SOURCE A))
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (OR)
         *         (SOURCE A)
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList.Segment rootSegment = createOrSegment();
        rootSegment.addChild(createNegatedOrSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createOrSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithNegatedEmptyOrAndSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (NOT (OR))
         *     (SOURCE A)
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (OR)
         *         (NOT (SOURCE A))
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList.Segment rootSegment = createOrSegment();
        rootSegment.addChild(createNegatedOrSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createOrSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithEmptyAndAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (AND)
         *     (NOT (SOURCE A))
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (NOT (AND))
         *         (SOURCE A)
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList.Segment rootSegment = createOrSegment();
        rootSegment.addChild(createAndSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createNegatedAndSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithEmptyAndAndSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (AND)
         *     (SOURCE A)
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (NOT (AND))
         *         (NOT (SOURCE A))
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList.Segment rootSegment = createOrSegment();
        rootSegment.addChild(createAndSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createNegatedAndSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static testMethod void testOrWithEmptyOrAndExcludedSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (OR)
         *     (NOT (SOURCE A))
         * )
         *
         * (NOT
         *     (AND
         *         (NOT (OR))
         *         (SOURCE A)
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList.Segment rootSegment = createOrSegment();
        rootSegment.addChild(createOrSegment());
        rootSegment.addChild(createExcludedSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createNegatedOrSegment());
        rootSegment2.addChild(createSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (), (B)
         *
         * Expected to not meet criteria:
         * (A), (A, B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('()')));

    }

    private static testMethod void testOrWithEmptyOrAndSource() {
        /*
         * Criteria tree as s-expression:
         * (OR
         *     (OR)
         *     (SOURCE A)
         * )
         *
         * Equivalent to:
         * (NOT
         *     (AND
         *         (NOT (OR))
         *         (NOT (SOURCE A))
         *     )
         * )
         */

        Id A = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id B = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList.Segment rootSegment = createOrSegment();
        rootSegment.addChild(createOrSegment());
        rootSegment.addChild(createSourceSegment(A));

        CampaignListSegment rootSegment2 = createNegatedAndSegment();
        rootSegment2.addChild(createNegatedOrSegment());
        rootSegment2.addChild(createExcludedSourceSegment(A));

        /*
         * Test members:
         * (), (A), (B), (A, B)
         *
         * Expected to meet criteria:
         * (A), (A, B)
         *
         * Expected to not meet criteria:
         * (), (B)
         */

        Map<String, CampaignList.Member> members = getTestMembersWithSources(A, B);

        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment.meetsCriteria(members.get('()')));

        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('(B)')));
        System.assertEquals(true, rootSegment2.meetsCriteria(members.get('(A, B)')));
        System.assertEquals(false, rootSegment2.meetsCriteria(members.get('()')));
    }

    private static CampaignListSegment createOrSegment() {
        return createSegment('OR', false);
    }

    private static CampaignListSegment createNegatedOrSegment() {
        return createSegment('OR', true);
    }

    private static CampaignListSegment createAndSegment() {
        return createSegment('AND', false);
    }

    private static CampaignListSegment createNegatedAndSegment() {
        return createSegment('AND', true);
    }

    private static CampaignListSegment createSourceSegment(Id sourceId) {
        return createSegment('SOURCE', false, sourceId);
    }

    private static CampaignListSegment createExcludedSourceSegment(Id sourceId) {
        return createSegment('SOURCE', true, sourceId);
    }

    private static CampaignListSegment createSegment(String operator, Boolean isExclusion) {
        return createSegment(operator, isExclusion, null);
    }

    private static CampaignListSegment createSegment(String operator, Boolean isExclusion, Id sourceId) {
        if ('OR' == operator) {
            return new CampaignListSegment.OrSegment(null, null, null, isExclusion);
        } else if ('AND' == operator) {
            return new CampaignListSegment.AndSegment(null, null, null, isExclusion);
        } else if ('SOURCE' == operator) {
            return new CampaignListSegment.CampaignSourceSegment(null, null, null, isExclusion, sourceId, '');
        }
        return null;
    }

    private static Map<String, CampaignList.Member> getTestMembersWithSources(Id A, Id B) {
        return getTestMembersWithSources(A, B, null, null, null);
    }

    private static Map<String, CampaignList.Member> getTestMembersWithSources(Id A, Id B, Id C) {
        return getTestMembersWithSources(A, B, C, null, null);
    }

    private static Map<String, CampaignList.Member> getTestMembersWithSources(Id A, Id B, Id C, Id D) {
        return getTestMembersWithSources(A, B, C, D, null);
    }

    private static Map<String, CampaignList.Member> getTestMembersWithSources(Id A, Id B, Id C, Id D, Id E) {
        Map<String, CampaignList.Member> members = new Map<String, CampaignList.Member>();

        members.put('()', getTestMemberWithSources(null, null, null, null, null));

        if (null != A) {
            members.put('(A)', getTestMemberWithSources(A, null, null, null, null));
            if (null != B) {
                members.put('(B)',    getTestMemberWithSources(null, B, null, null, null));
                members.put('(A, B)', getTestMemberWithSources(   A, B, null, null, null));
                if (null != C) {
                    members.put('(C)',       getTestMemberWithSources(null, null, C, null, null));
                    members.put('(A, C)',    getTestMemberWithSources(   A, null, C, null, null));
                    members.put('(B, C)',    getTestMemberWithSources(null,    B, C, null, null));
                    members.put('(A, B, C)', getTestMemberWithSources(   A,    B, C, null, null));
                }
                if (null != D) {
                    members.put('(D)',          getTestMemberWithSources(null, null, null, D, null));
                    members.put('(A, D)',       getTestMemberWithSources(   A, null, null, D, null));
                    members.put('(B, D)',       getTestMemberWithSources(null,    B, null, D, null));
                    members.put('(A, B, D)',    getTestMemberWithSources(   A,    B, null, D, null));
                    members.put('(C, D)',       getTestMemberWithSources(null, null,    C, D, null));
                    members.put('(A, C, D)',    getTestMemberWithSources(   A, null,    C, D, null));
                    members.put('(B, C, D)',    getTestMemberWithSources(null,    B,    C, D, null));
                    members.put('(A, B, C, D)', getTestMemberWithSources(   A,    B,    C, D, null));
                }
                if (null != E) {
                    members.put('(E)',             getTestMemberWithSources(null, null, null, null, E));
                    members.put('(A, E)',          getTestMemberWithSources(   A, null, null, null, E));
                    members.put('(B, E)',          getTestMemberWithSources(null,    B, null, null, E));
                    members.put('(A, B, E)',       getTestMemberWithSources(   A,    B, null, null, E));
                    members.put('(C, E)',          getTestMemberWithSources(null, null,    C, null, E));
                    members.put('(A, C, E)',       getTestMemberWithSources(   A, null,    C, null, E));
                    members.put('(B, C, E)',       getTestMemberWithSources(null,    B,    C, null, E));
                    members.put('(A, B, C, E)',    getTestMemberWithSources(   A,    B,    C, null, E));
                    members.put('(D, E)',          getTestMemberWithSources(null, null, null,    D, E));
                    members.put('(A, D, E)',       getTestMemberWithSources(   A, null, null,    D, E));
                    members.put('(B, D, E)',       getTestMemberWithSources(null,    B, null,    D, E));
                    members.put('(A, B, D, E)',    getTestMemberWithSources(   A,    B, null,    D, E));
                    members.put('(C, D, E)',       getTestMemberWithSources(null, null,    C,    D, E));
                    members.put('(A, C, D, E)',    getTestMemberWithSources(   A, null,    C,    D, E));
                    members.put('(B, C, D, E)',    getTestMemberWithSources(null,    B,    C,    D, E));
                    members.put('(A, B, C, D, E)', getTestMemberWithSources(   A,    B,    C,    D, E));
                }
            }
        }
        return members;
    }

    private static CampaignList.Member getTestMemberWithSources(Id A, Id B, Id C, Id D, Id E) {
        CampaignListMember member = new CampaignListMember(null, null);
        if (null != A) {
            member.addSource(A, 'A');
        }
        if (null != B) {
            member.addSource(B, 'B');
        }
        if (null != C) {
            member.addSource(C, 'C');
        }
        if (null != D) {
            member.addSource(D, 'D');
        }
        if (null != E) {
            member.addSource(E, 'E');
        }
        return member;
    }
}