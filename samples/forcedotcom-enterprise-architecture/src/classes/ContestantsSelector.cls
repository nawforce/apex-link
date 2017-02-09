public virtual class ContestantsSelector extends fflib_SObjectSelector
{
	public static ContestantsSelector newInstance() 
	{ 
		return (ContestantsSelector) Application.Selector.newInstance(Contestant__c.SObjectType); 
	}

	public List<Schema.SObjectField> getSObjectFieldList()
	{
		return new List<Schema.SObjectField> {
			Contestant__c.Id,
			Contestant__c.Name,
			Contestant__c.ChampionshipPoints__c,
			Contestant__c.DNF__c,
			Contestant__c.Driver__c,
			Contestant__c.DriverRace__c,
			Contestant__c.GridPosition__c,
			Contestant__c.Qualification1LapTime__c,
			Contestant__c.Qualification2LapTime__c,
			Contestant__c.Qualification3LapTime__c,
			Contestant__c.Race__c,
			Contestant__c.RacePosition__c,
			Contestant__c.RaceTime__c,
			Contestant__c.RetirementReason__c
		};
	}

	public Schema.SObjectType getSObjectType()
	{
		return Contestant__c.sObjectType;
	}

	public override String getOrderBy()
	{
		return 'Race__r.Season__r.Name, Race__r.Name, RacePosition__c';
	}

	public List<Contestant__c> selectById(Set<Id> ids)
	{
		return (List<Contestant__c>) super.selectSObjectsById(ids);
	}

	public List<Contestant__c> selectByIdWithDriver(Set<Id> driverIds)
	{
		assertIsAccessible();

		DriversSelector driversSelector = new DriversSelector();
		driversSelector.assertIsAccessible();

		return Database.query(
			String.format('select {0},{1} from {2} where Id in :driverIds order by {3}', 
			new List<String>{
				getFieldListString(),
				driversSelector.getRelatedFieldListString('Driver__r'),
				getSObjectName(),
				getOrderBy()}));		
	}	

	public virtual Map<Id, List<Summary>> selectByRaceIdWithContestantSummary(Set<Id> raceIds)
	{
		assertIsAccessible();
		new DriversSelector().assertIsAccessible();
		new CarsSelector().assertIsAccessible();
		new TeamsSelector().assertIsAccessible();
		Map<Id, List<Summary>> summariesByRaceId = new Map<Id, List<Summary>>();		
		for(Contestant__c contestant : 
				Database.query(
					String.format(
						'select {0} from {1} where Race__c in :raceIds order by {2}', 
						new List<String>{
							String.join(
								new List<String> {
									'Race__r.Season__r.Name',
									'Race__r.Name',
									'RacePosition__c',
									'Driver__r.Name',
									'Driver__r.Team__r.Name',
									'Car__r.Name' }, ','),
							getSObjectName(),
							getOrderBy()})))
		{
			List<Summary> summaries = summariesByRaceId.get(contestant.Race__c);
			if(summaries==null)
				summariesByRaceId.put(contestant.Race__c, summaries = new List<Summary>());
			summaries.add(new Summary(contestant));
		}
		return summariesByRaceId;
	}

	public class Summary 
	{
		private Contestant__c contestant;				
		public String Season { 
			get { return contestant.Race__r.Season__r.Name; }}		
		public String Race { 
			get { return contestant.Race__r.Name; } }	
		public Decimal Position { 
			get { return contestant.RacePosition__c; } }				
		public String Driver { 
			get { return contestant.Driver__r.Name; } }		
		public String Team { 
			get { return contestant.Driver__r.Team__r.Name; } }		
		public String Car { 
			get { return contestant.Car__r.Name; } }		
		@TestVisible			
		private Summary(Contestant__c contestant) { 
			this.contestant = contestant; }
	}
}