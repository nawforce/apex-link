public class Contestants extends fflib_SObjectDomain
{
	public Contestants(List<Contestant__c> contestants) 
	{
		super(contestants);	
	}

	public override void onValidate()
	{
		// Bulk load the associated races
		Set<Id> raceIds = new Set<Id>();
		for(Contestant__c contestant : (List<Contestant__c>) Records)
			raceIds.add(contestant.Race__c);
		Map<Id, Race__c> associatedRaces = 
			new Map<Id, Race__c>(new RacesSelector().selectById(raceIds));

		// Only permit new contestants to be added to Scheduled races
		for(Contestant__c contestant : (List<Contestant__c>) Records)
		{
			Race__c race = associatedRaces.get(contestant.Race__c);
			if(race.Status__c != 'Scheduled')
				contestant.addError( 'Contestants can only be added to scheduled races');
		}
	}

	/**
	 * Performs validation on update of existing Contestants
	 **/
	public override void onValidate(Map<Id,SObject> existingRecords)
	{
		// Bulk load the associated races
		Map<Id, Race__c> associatedRaces = queryAssociatedRaces();

		// Can only change drivers in scheduled races
		for(Contestant__c contestant : (List<Contestant__c>) Records)
		{			
			Race__c contestantRace = associatedRaces.get(contestant.Race__c);
			Contestant__c oldContestant  = (Contestant__c) existingRecords.get(contestant.Id);
			if(contestantRace.Status__c != 'Scheduled' && 
			   contestant.Driver__c !=  oldContestant.Driver__c)
				contestant.Driver__c.addError( 'You can only change drivers for scheduled races' );
		}
	}

	public void awardChampionshipPoints(fflib_SObjectUnitOfWork uow)
	{
		// Custom implementation configured by Developer X?
		String customImplementationClassName =
			Application__c.getInstance().IAwardChampionshipPointsApexClass__c;
		if(customImplementationClassName!=null)
		{
			// Attempt to resolve the class?
			Type customImplementationClass = 
				// Namespace (managed) qualified e.g. devx.CustomCalc
				Type.forName(customImplementationClassName); 
			if(customImplementationClass==null)
				customImplementationClass = 
					// Local (unmanaged) namespace e.g. CustomCalc
					Type.forName('',customImplementationClassName);
			if(customImplementationClass!=null)
			{
				// Create instance of Developer X's class
				ContestantService.IAwardChampionshipPoints customImplementation = 
					(ContestantService.IAwardChampionshipPoints) 
						customImplementationClass.newInstance(); 
				// Invoke the custom method
				customImplementation.calculate(Records);
				// Mark dirty on behalf of Developer X
				for(Contestant__c contestant : (List<Contestant__c>) Records)
					uow.registerDirty(contestant);
			}
			return;
		}

		// Apply championship points to given contestants		
		for(Contestant__c contestant : (List<Contestant__c>) Records)
		{
			// Determine points to award for the given position
			ChampionshipPoints__c pointsForPosition = 
				ChampionshipPoints__c.getInstance(String.valueOf(contestant.RacePosition__c));
			if(pointsForPosition!=null)
			{
				// Apply points and register for udpate with uow
				contestant.ChampionshipPoints__c = pointsForPosition.PointsAwarded__c;
				uow.registerDirty(contestant);
			}
		}		
	}

	public override void onBeforeInsert()
	{
		// Query Drivers related to Contestant records
		Map<Id, Driver__c> driversById = 
			new Map<Id, Driver__c>((List<Driver__c>)
				Application.Selector.selectByRelationship(
					Records, Contestant__c.Driver__c));

		// Query Races related to Contestant records
		Map<Id, Race__c> racesById = queryAssociatedRaces();

		// Calculate each unique Contestants Race Data Id
		for(Contestant__c contestant : (List<Contestant__c>) Records)
		{		
			Race__c race = racesById.get(contestant.Race__c);
			Driver__c driver = driversById.get(contestant.Driver__c);
			contestant.RaceDataId__c = makeRaceDataId(race.Year__c, race.Name, driver.DriverId__c); 
		}		
	}

	public static String makeRaceDataId(Decimal year, String raceName, String driverId)
	{
		return makeRaceDataId(String.valueOf(year), raceName, driverId);
	}

	public static String makeRaceDataId(String year, String raceName, String driverId)
	{
		return year+'-'+raceName+'-'+driverId;
	}

	private Map<Id, Race__c> queryAssociatedRaces()
	{
		// Bulk load the associated races
		Set<Id> raceIds = new Set<Id>();
		for(Contestant__c contestant : (List<Contestant__c>) Records)
			raceIds.add(contestant.Race__c);
		return new Map<Id, Race__c>(new RacesSelector().selectById(raceIds));		
	}

	public class Constructor implements fflib_SObjectDomain.IConstructable
	{
		public fflib_SObjectDomain construct(List<SObject> sObjectList)
		{
			return new Contestants(sObjectList);
		}
	}	
}