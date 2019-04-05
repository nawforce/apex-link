global class Application 
{
	// Configure and create the UnitOfWorkFactory for this Application
	public static final UnitOfWorkFactory UnitOfWork = 
		new UnitOfWorkFactory(
				new List<SObjectType> { 
					Driver__c.SObjectType, 
					Season__c.SObjectType, 
				  	Race__c.SObjectType, 
				  	Contestant__c.SObjectType,
				  	RaceData__c.SObjectType });

	// Configure and create the SelectorFactory for this Application
	public static final SelectorFactory Selector = 
		new SelectorFactory(
			new Map<SObjectType, Type> {
					Team__c.SObjectType => TeamsSelector.class,
					Race__c.SObjectType => RacesSelector.class,
					Car__c.SObjectType => CarsSelector.class,
					Driver__c.SObjectType => DriversSelector.class,
					Contestant__c.SObjectType => ContestantsSelector.class,
					RaceData__c.SObjectType => RaceDataSelector.class });

	// Configure and create the DomainFactory for this Application
	public static final DomainFactory Domain = 
		new DomainFactory(
			new Map<SObjectType, Type> {
					Team__c.SObjectType => Teams.Constructor.class,
					Race__c.SObjectType => Races.Constructor.class,
					Car__c.SObjectType => Cars.Constructor.class,
					Driver__c.SObjectType => Drivers.Constructor.class,
					Contestant__c.SObjectType => Contestants.Constructor.class });

	/**
	 * Class implements a Unit of Work factory
	 **/
	public class UnitOfWorkFactory
	{
		private List<SObjectType> objectTypes;

		private UnitOfWorkFactory(List<SObjectType> objectTypes)
		{
			this.objectTypes = objectTypes;
		}

		public fflib_SObjectUnitOfWork newInstance()
		{
			return new fflib_SObjectUnitOfWork(objectTypes);
		}
	}

	/**
	 * Class implements a Selector class factory
	 **/
	public class SelectorFactory
	{
		private Map<SObjectType, Type> sObjectBySelectorType;
		private Map<SObjectType, fflib_SObjectSelector> sObjectByMockSelector;

		private SelectorFactory(Map<SObjectType, Type> sObjectBySelectorType)
		{
			this.sObjectBySelectorType = sObjectBySelectorType;	
			this.sObjectByMockSelector = new Map<SObjectType, fflib_SObjectSelector>();		
		}

		public fflib_SObjectSelector newInstance(SObjectType sObjectType)
		{
			// Mock implementation?
			if(Test.isRunningTest() && sObjectByMockSelector.containsKey(sObjectType))
				return sObjectByMockSelector.get(sObjectType);

			// Determine Apex class for Selector class			
			Type selectorClass = sObjectBySelectorType.get(sObjectType);
			if(selectorClass==null)
				throw new ApplicationException('Selector class not found for SObjectType ' + sObjectType);

			// Construct Selector class and query by Id for the records
			return (fflib_SObjectSelector) selectorClass.newInstance();			
		}

		public List<SObject> selectById(Set<Id> recordIds)
		{
			// Determine SObjectType
			SObjectType domainSObjectType = new List<Id>(recordIds)[0].getSObjectType();

			// Construct Selector class and query by Id for the records
			return newInstance(domainSObjectType).selectSObjectsById(recordIds);
		}

		public List<SObject> selectByRelationship(List<SObject> relatedRecords, SObjectField relationshipField)
		{
			Set<Id> relatedIds = new Set<Id>();
			for(SObject relatedRecord : relatedRecords)
				relatedIds.add((Id) relatedRecord.get(relationshipField));
			return selectById(relatedIds);
		}

		@TestVisible
		private void setMock(fflib_SObjectSelector selectorInstance)
		{
			sObjectByMockSelector.put(selectorInstance.getSObjectType2(), selectorInstance);
		} 
	}

	/**
	 * Class implements a Domain class factory
	 **/
	public class DomainFactory 
	{
		private Map<SObjectType, Type> sObjectByDomainConstructorType;

		private DomainFactory(
			Map<SObjectType, Type> sObjectByDomainConstructorType)
		{
			this.sObjectByDomainConstructorType = sObjectByDomainConstructorType;
		}			

		/**
		 * Dynamically constructs an instance of a Domain class for the given record Ids
		 **/
		public fflib_SObjectDomain newInstance(Set<Id> recordIds)
		{
			// No point creating an empty Domain class
			if(recordIds.size()==0)
				return null;

			List<SObject> records = Application.Selector.selectById(recordIds);

			// Determine SObjectType and Apex classes for Domain class
			SObjectType domainSObjectType = new List<Id>(recordIds)[0].getSObjectType();
			Type domainConstructorClass = sObjectByDomainConstructorType.get(domainSObjectType);
			if(domainConstructorClass==null)
				throw new ApplicationException('Domain constructor class not found for SObjectType ' + domainSObjectType);

			// Construct Domain class passing in the queried records
			fflib_SObjectDomain.IConstructable domainConstructor = 
				(fflib_SObjectDomain.IConstructable) domainConstructorClass.newInstance();		
			return domainConstructor.construct(records);
		}		
	}

	global enum ExceptionCode
	{
		NotImplemented,
		ComplianceFailure,
		UnableToVerifyCompliance
	}		

	global virtual class ApplicationException extends Exception 
	{
		global ExceptionCode ExceptionCode {get; private set;}

		public ApplicationException(ExceptionCode exceptionCode, String message)
		{
			this(message);
			this.exceptionCode = exceptionCode;
		}
	}	
}