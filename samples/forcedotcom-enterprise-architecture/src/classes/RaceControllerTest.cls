@isTest
private class RaceControllerTest 
{	
	@isTest static void testAwardPoints() 
	{
		SeasonService.importSeasons(TestData.asJSON());
		new RaceController(
			new ApexPages.StandardController(
				[select Id from Race__c LIMIT 1])).awardPoints();
	}
}