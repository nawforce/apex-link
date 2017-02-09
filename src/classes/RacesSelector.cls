public class RacesSelector extends fflib_SObjectSelector
{
	public RacesSelector()
	{
		super();
	}

	public RacesSelector(Boolean includeFieldSetFields)
	{
		super(includeFieldSetFields);
	}

    public override List<Schema.FieldSet> getSObjectFieldSetList()
    {
        return new List<Schema.FieldSet> { 
        	SObjectType.Race__c.FieldSets.SeasonOverview 
        };
    }

	public List<Schema.SObjectField> getSObjectFieldList()
	{
		return new List<Schema.SObjectField> {
			Race__c.Id,
			Race__c.Name,
			Race__c.Year__c,
			Race__c.Status__c,
			Race__c.Season__c,
			Race__c.FastestLapBy__c,
			Race__c.PollPositionLapTime__c,
			Race__c.TotalDNFs__c
		};
	}

	public Schema.SObjectType getSObjectType()
	{
		return Race__c.sObjectType;
	}

	public List<Race__c> selectById(Set<Id> raceIds)
	{
		return (List<Race__c>) selectSObjectsById(raceIds);
	}

	public List<Race__c> selectByIdWithContestants(Set<Id> raceIds)
	{
		assertIsAccessible();

		ContestantsSelector contestantSelector = new ContestantsSelector();
		contestantSelector.assertIsAccessible();

		return (List<Race__c>) Database.query(
			String.format(
					'select {0}, ' +
					  '(select {1} ' +
					     'from Contestants__r order by {2})' + 
					  'from {3} ' +
					  'where id in :raceIds ' + 
					  'order by {4}', 
				new List<String>{
					getFieldListString(),
					contestantSelector.getFieldListString(),
					contestantSelector.getOrderBy(),
					getSObjectName(),
					getOrderBy()
				}));		
	}
}