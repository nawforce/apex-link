/*
Copyright (c) 2011 salesforce.com, inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

By: Chris Kemp <ckemp@salesforce.com>
*/

public with sharing class GamificationTestUtility {

    /*
        A random user is useful for testing in order to be 
        sure that you are able to see data across posts regardless
        of follow status.
    */
    public static User createTestUser(String profileName) {
        Profile p = [Select Id, Name from Profile 
            where Name = :profileName];
        return GamificationTestUtility.createTestUser(p);
    }
    
    public static User createTestUser(Profile p) {
        String namePrefix = createRandomWord();
        
        AggregateResult[] userCount = [Select count(id) userCount 
            From user where username like :namePrefix];
        
        Object users = userCount[0].get('userCount');
        
        User testUser = new User();
        testUser.Email = 'test@test.com';
        testUser.Username = namePrefix+users+'@testuser.test';

        testUser.LastName = 'test';
        testUser.Alias = 'test';
        testUser.ProfileId = p.Id;
        testUser.LanguageLocaleKey = 'en_US';
        testUser.LocaleSidKey = 'en_US';
        testUser.TimeZoneSidKey = 'America/New_York';
        testUser.EmailEncodingKey = 'UTF-8';
        insert testUser;
        return testUser;
    }

    /*
        Random words are required for testing 
        as you will likely run into any word I can insert
        during a test class and produce false test results.
    */
    public static String createRandomWord() {
        String ret = 'word' + math.rint(math.random() * 100000);
        return ret;
    }
    
    //TEST METHODS
    
    static TestMethod void testRandomWord() {
        System.assert(GamificationTestUtility.createRandomWord() != 
            GamificationTestUtility.createRandomWord());
    }
    
    static TestMethod void testCreateUser() {
        User u = GamificationTestUtility.createTestUser('Standard User');
        System.assert(u.id != null);
    }   
}