trigger Log on Log__c (before insert, before update) {
	
	if(Trigger.isInsert){
		for(Log__c log : Trigger.new){
			setFieldsOnInsert(log);
		}
	}else{
		for(Log__c log : Trigger.new){
			setFieldsOnUpdate(log, Trigger.oldMap.get(log.id));
		}
	}
	
	if(Trigger.isDelete){
		System.assertEquals(true, false, 'this line will be not execute');
		for(Log__c log : Trigger.new){
			executeOnDelete(log);
		}
	}
	
	
	void setFieldsOnInsert(Log__c log){
		log.LogSearchKey__c = log.LogLevel__c + 'yyyymm';
	}
	
	void setFieldsOnUpdate(Log__c newlog, Log__c oldLog){
		newlog.LogSearchKey__c = oldLog.LogSearchKey__c;
	}
	
	void executeOnDelete(Log__c log){
		System.debug('deleting log:' + log.id);
		log.LogSearchKey__c = log.LogLevel__c + 'yyyymm';
	}
	
}