public class DriversSelector extends fflib_SObjectSelector
{
	public static DriversSelector newInstance() 
	{ 
		return (DriversSelector) Application.Selector.newInstance(Driver__c.SObjectType); 
	}
    
	public List<Schema.SObjectField> getSObjectFieldList()
	{
		return new List<Schema.SObjectField> {
			Driver__c.Id,
			Driver__c.Name,
			Driver__c.FIASuperLicense__c,
			Driver__c.Team__c,
            Driver__c.DriverId__c
		};
	}

	public Schema.SObjectType getSObjectType()
	{
		return Driver__c.sObjectType;
	}

	public List<Driver__c> selectById(Set<Id> ids)
	{
		return (List<Driver__c>) super.selectSObjectsById(ids);
	}

	public List<Driver__c> selectByTeam(Set<Id> teamIds)
	{
		assertIsAccessible();
		return Database.query(
			String.format('SELECT {0} FROM {1} WHERE Team__c in :teamIds ORDER BY {2}', 
			new List<String>{getFieldListString(),getSObjectName(),getOrderBy()}));
	}
}