public class RaceDataSelector extends fflib_SObjectSelector
{
	public static RaceDataSelector newInstance() 
	{ 
		return (RaceDataSelector) Application.Selector.newInstance(RaceData__c.SObjectType); 
	}

	public List<Schema.SObjectField> getSObjectFieldList()
	{
		return new List<Schema.SObjectField> {
				RaceData__c.Id,
				RaceData__c.Name,
				RaceData__c.Year__c,
				RaceData__c.RaceName__c,
				RaceData__c.Lap__c,
				RaceData__c.Sector__c,
				RaceData__c.Type__c,
				RaceData__c.Value__c,
				RaceData__c.DriverId__c
			};
	}

	public Schema.SObjectType getSObjectType()
	{
		return RaceData__c.sObjectType;
	}

	public List<RaceData__c> selectById(Set<ID> idSet)
	{
		return (List<RaceData__c>) selectSObjectsById(idSet);
	}	

	public List<RaceData__c> selectByRaceName(Set<String> raceNames)
	{
		assertIsAccessible();
		return Database.query(
			String.format('SELECT {0} FROM {1} WHERE RaceName__c in :raceNames ORDER BY {2}', 
			new List<String>{getFieldListString(),getSObjectName(),getOrderBy()}));
	}	

	public Database.QueryLocator selectAllQueryLocator()
	{
		assertIsAccessible();
		return Database.getQueryLocator(
			String.format('SELECT {0} FROM {1} ORDER BY {2}', 
				new List<String>{getFieldListString(),getSObjectName(),getOrderBy()}));
	}

	public List<Analysis> selectAnaysisGroupByRaceName()
	{
		assertIsAccessible();
		List<Analysis> raceAnalysis = new List<Analysis>();
		for(AggregateResult result : 
			[select 
				RaceName__c raceName, Type__c type,
				AVG(Value__c) averageValue,
				MAX(Value__c) maxValue,
				MIN(Value__c) minValue
			 from RaceData__c
			 group by RaceName__c, Type__c ])
			raceAnalysis.add(new Analysis(result));
		return raceAnalysis;
	}

	public class Analysis
	{
		private AggregateResult result;				
		public String Race { 
			get { return (String) result.get('raceName'); } }	
		public String Type { 
			get { return (String) result.get('type'); } }	
		public Decimal AverageValue { 
			get { return (Decimal) result.get('averageValue'); } }	
		public Decimal MaxValue { 
			get { return (Decimal) result.get('maxValue'); } }	
		public Decimal MinValue { 
			get { return (Decimal) result.get('minValue'); } }	
		private Analysis(AggregateResult result) { 
			this.result = result; }		
	}
}