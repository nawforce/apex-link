@isTest
private class ContestantServiceTest 
{	
	@isTest static void testAwardPoints() 
	{
		SeasonService.importSeasons(TestData.asJSON());
		insert new ChampionshipPoints__c(Name = '1', PointsAwarded__c = 25);			
		ContestantService.awardChampionshipPoints(
			new Map<Id, SObject>([select Id from Contestant__c]).keySet());
	}

	@isTest static void testTemporary()
	{
		try {
			ContestantService.retireFromRace(null);
			System.assert(false);
		} catch (Exception e) {
			System.assertEquals('Not implemented', e.getMessage());
		}
	}
}