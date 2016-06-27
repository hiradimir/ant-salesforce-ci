/**
 * This class contains unit tests for validating the behavior of Apex classes
 * and triggers.
 *
 * Unit tests are class methods that verify whether a particular piece
 * of code is working properly. Unit test methods take no arguments,
 * commit no data to the database, and are flagged with the testMethod
 * keyword in the method definition.
 *
 * All test methods in an organization are executed whenever Apex code is deployed
 * to a production organization to confirm correctness, ensure code
 * coverage, and prevent regressions. All Apex classes are
 * required to have at least 75% code coverage in order to be deployed
 * to a production organization. In addition, all triggers must have some code coverage.
 * 
 * The @isTest class annotation indicates this class only contains test
 * methods. Classes defined with the @isTest annotation do not count against
 * the organization size limit for all Apex scripts.
 *
 * See the Apex Language Reference for more information about Testing and Code Coverage.
 */
@isTest(seeAllData=false)
private class LogTriggerTest {

	static Log__c newTestLogRecord(){
		return new Log__c(LogLevel__c = 'error', LogTitle__c = 'test log', LogResult__c = 'test');
	}
	
	static List<Log__c> selectLogRecords(Id[] ids){
		return [select id, Name, LogLevel__c, LogTitle__c, LogResult__c, LogSearchKey__c from Log__c where id in :ids];
	}

	static testMethod void insertAndUpdate() {
		Log__c newLog = newTestLogRecord();
		insert newLog;
		System.assertNotEquals(null, newLog.id);
		
		Log__c insertedLog = selectLogRecords(new Id[]{newLog.id})[0];
		System.assertNotEquals(null, insertedLog.Name);
		System.assertNotEquals(null, insertedLog.LogSearchKey__c);

		insertedLog.LogTitle__c = 'updated log';
		insertedLog.LogSearchKey__c = 'key modified';
		update insertedLog;
		
		Log__c updatedLog = selectLogRecords(new Id[]{newLog.id})[0];
		System.assertEquals(newLog.id, updatedLog.id);
		System.assertEquals(insertedLog.Name, updatedLog.Name);
		System.assertEquals(insertedLog.LogTitle__c, updatedLog.LogTitle__c);
		System.assertNotEquals(insertedLog.LogSearchKey__c, updatedLog.LogSearchKey__c);
	}
	
	static testMethod void maxTriggerGoverner() {
		Integer MAX_RECORD = 200;
		
		List<Log__c> newLogs = new List<Log__c>();
		for(Integer i=0; i < MAX_RECORD; i++){
			newLogs.add(newTestLogRecord());
		}
		insert newLogs;
		
		for(Log__c log : newLogs){
			System.assertNotEquals(null, log.id);
		}
		List<Id> ids = Util.getIdList(newLogs);
		System.assertEquals(MAX_RECORD, ids.size());
		
		List<Log__c> insertLogs = selectLogRecords(ids);
		
		System.assertEquals(MAX_RECORD, insertLogs.size());
		update insertLogs;
		
		List<Log__c> updateLogs = selectLogRecords(ids);
		System.assertEquals(MAX_RECORD, updateLogs.size());
		
	}
	
	
}