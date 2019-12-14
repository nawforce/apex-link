public class RaceService 
{
	public static Map<Id, List<ProvisionalResult>> calculateProvisionResults(Set<Id> raceIds)
	{
		return service().calculateProvisionResults_x(raceIds);
	}

	public static void applyRaceResults(Map<Id, List<ProvisionalResult>> provisionalResultsByRaceId)
	{
		service().applyRaceResults_x(provisionalResultsByRaceId);
	}

	public static void awardChampionshipPoints(Set<Id> raceIds)
	{
		service().awardChampionshipPoints_x(raceIds);
	}

	public static String generateSummaryAsCSV(Set<Id> raceIds)
	{
		return service().generateSummaryAsCSV_x(raceIds);
	}

	public static List<AnalysisResult> analizeData(Set<String> raceNames)
	{
		return service().analizeData_x(raceNames);
	}

	public static void processData(Set<Id> raceDataIds)
	{
		service().processData_x(raceDataIds);
	}

	public static void runProcessDataJob()
	{
		service().runProcessDataJob_x();	
	}

	private static IRaceService service()
	{
		if(ApexPages.currentPage()!=null)
			if(ApexPages.currentPage().getParameters().containsKey('dummy'))
				return (IRaceService) new DummyImplementation();
		return (IRaceService) new ProductionImplementation();
	}

	private interface IRaceService
	{
		Map<Id, List<ProvisionalResult>> calculateProvisionResults_x(Set<Id> raceIds);

		void applyRaceResults_x(Map<Id, List<ProvisionalResult>> provisionalResultsByRaceId);

		void awardChampionshipPoints_x(Set<Id> raceIds);

		String generateSummaryAsCSV_x(Set<Id> raceIds);

		List<AnalysisResult> analizeData_x(Set<String> raceNames);

		void processData_x(Set<Id> raceDataIds);

		Id runProcessDataJob_x();
	}	

	private class DummyImplementation implements IRaceService
	{
		public Map<Id, List<ProvisionalResult>> calculateProvisionResults_x(Set<Id> raceIds)
		{
			// Dummy behaviour to allow the client to be developed 
			//    independent of the main service implementation 
			Id raceId = new List<Id>(raceIds)[0];
			ProvisionalResult hamilton = new ProvisionalResult();
			hamilton.racePosition = 1;
			hamilton.contestantName = 'Lewis Hamilton';
			hamilton.contestantId = 'a03b0000006WVph';
			ProvisionalResult rubens = new ProvisionalResult();
			rubens.racePosition = 2;
			rubens.contestantName = 'Rubens Barrichello';
			rubens.contestantId = 'a03b00000072xx9';
			return new Map<Id, List<ProvisionalResult>> {
					new List<Id>(raceIds)[0] => 
						new List<ProvisionalResult> { hamilton, rubens } };
		}

		public void applyRaceResults_x(Map<Id, List<ProvisionalResult>> provisionalResultsByRaceId)
		{
		}

		public void awardChampionshipPoints_x(Set<Id> raceIds)
		{
		}

		public String generateSummaryAsCSV_x(Set<Id> raceIds)
		{
			return null;
		}

		public List<AnalysisResult> analizeData_x(Set<String> raceNames)
		{
			return null;
		}

		public void processData_x(Set<Id> raceDataIds)
		{
		}

		public Id runProcessDataJob_x()
		{
			return null;
		}
	}

	private class ProductionImplementation implements IRaceService
	{
		public Map<Id, List<ProvisionalResult>> calculateProvisionResults_x(Set<Id> raceIds)
		{			
			return null;
		}

		public void applyRaceResults_x(Map<Id, List<ProvisionalResult>> provisionalResultsByRaceId)
		{
			fflib_SObjectUnitOfWork uow = Application.UnitOfWork.newInstance();
			uow.commitWork();
		}

		public void awardChampionshipPoints_x(Set<Id> raceIds)
		{
			fflib_SObjectUnitOfWork uow = Application.UnitOfWork.newInstance();

			// Query Races and contestants and bulkify list of all contestants
			List<Contestant__c> contestants = new List<Contestant__c>();
			for(Race__c race : new RacesSelector().selectByIdWithContestants(raceIds))
				contestants.addAll(race.Contestants__r);

			// Delegate to Contestant Domain class
			new Contestants(contestants).awardChampionshipPoints(uow);

			// Commit work
			uow.commitWork();
		}

		public String generateSummaryAsCSV_x(Set<Id> raceIds)
		{
			// Query summaries via custom selector method
			Map<Id, List<ContestantsSelector.Summary>> summariesByRaceId = 
				new ContestantsSelector().selectByRaceIdWithContestantSummary(raceIds);
			// Construct CSV output (note this is not a formal implementation of the CSV spec)
			String csvOutput = '';
			for(Id raceId : summariesByRaceId.keySet())
				for(ContestantsSelector.Summary raceContestantSummary : summariesByRaceId.get(raceId))
					csvOutput+= String.join(
						new Object[] { 
								raceContestantSummary.Season,
								raceContestantSummary.Race,
								raceContestantSummary.Position,
								raceContestantSummary.Driver,
								raceContestantSummary.Team,
								raceContestantSummary.Car
							} , ',') + '\r\n';
			return csvOutput;
		}		

		public List<AnalysisResult> analizeData_x(Set<String> raceNames)
		{
			List<RaceData__c> raceData = 
				RaceDataSelector.newInstance().selectByRaceName(raceNames);			
			return null;
		}	

		public void processData_x(Set<Id> raceDataIds)
		{
			// Query Race Data records and update with Contestant relationship
			fflib_SObjectUnitOfWork uow = Application.UnitOfWork.newInstance();
			for(RaceData__c raceData : 
					(List<RaceData__c>)
						Application.Selector.selectById(raceDataIds))
			{
				// Associate Race Data record with a Contestant via Unique Id
				raceData.Contestant__r = 
					new Contestant__c(
						RaceDataId__c = 
							Contestants.makeRaceDataId(
								raceData.Year__c, 
								raceData.RaceName__c, 
								raceData.DriverId__c));
				uow.registerDirty(raceData);
			}
			// Update records
			uow.commitWork();
		}

		public Id runProcessDataJob_x()
		{
			// TODO: Implement concurrency check and scope config (via custom setting)
			return Database.executeBatch(new ProcessRaceDataJob(), 2000);	
		}		
	}

	public class AnalysisResult 
	{

	}

	public class ProvisionalResult
	{
		public Integer racePosition {get; set;}
		public Id contestantId {get; set;}
		public String contestantName {get; private set;}		
	}
}