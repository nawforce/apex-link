public with sharing class ContestantController 
{
	private ApexPages.StandardController standardController;

    public ContestantController(ApexPages.StandardController standardController) 
    {
    	this.standardController = standardController;
    }

    public PageReference awardPoints()
    {
    	try {
        	ContestantService.awardChampionshipPoints(
        		new Set<Id> { standardController.getId() });
        }
        catch (Exception e) {
        	ApexPages.addMessages(e);
        }        
        return null;
    }
}