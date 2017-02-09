global class ContestantService 
{
	public static void awardChampionshipPoints(Set<Id> contestantIds)
	{
		fflib_SObjectUnitOfWork uow = Application.UnitOfWork.newInstance();

		// Apply championship points to given contestants		
		Contestants contestants = 
			new Contestants(
				Application.Selector.selectById(contestantIds));
		contestants.awardChampionshipPoints(uow);

		uow.commitWork();
	}

	public static void retireFromRace(List<RaceRetirement> retirements)
	{
		throw new ContestantServiceException('Not implemented');
	}

	public class ContestantServiceException extends Exception {} 

	public class RaceRetirement
	{
		public Id contestantId;
		public String reason;
	}	

	global interface IAwardChampionshipPoints
	{
		void calculate(List<Contestant__c> contestants);
	}
}