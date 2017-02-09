@isTest
private class SeasonControllerTest 
{	
	@isTest static void testNewsletter()
	{
		try {
			new SeasonController(null).sendNewsletter();
			System.assert(false);
		} catch (Exception e) {
			System.assertEquals('Not implemented', e.getMessage());
		}		
	}
}