global abstract class Secret {
	
	static{
		System.assertEquals(true, false, 'Not Access this class');
	}

}