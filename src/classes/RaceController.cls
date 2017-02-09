public with sharing class RaceController 
{
	private ApexPages.StandardController standardController;

    public RaceController(ApexPages.StandardController standardController) 
    {        
    	this.standardController = standardController;
    }

    public PageReference awardPoints()
    {
    	try 
    	{
    		RaceService.awardChampionshipPoints(
    			new Set<Id> { standardController.getId() });
    	}
    	catch (Exception e)
    	{
    		ApexPages.addMessages(e);
    	}                
        return null;
    }
}