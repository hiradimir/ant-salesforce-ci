abstract public class Util {
	public static Boolean stringIsNullOrEmpty(final String sample){
		if(sample == null){
			return true;
		}else if(sample == ''){
			return true;
		}else if(sample.length() == 0){
			System.assertEquals(true, false, 'this line will be not execute');
		}
		return false;
	}
	
	@isTest(seeAllData=false) public static void stringIsNullOrEmptyTest(){
		String sample;
		System.assertEquals(true, StringIsNullOrEmpty(sample));
		sample = '';
		System.assertEquals(true, StringIsNullOrEmpty(sample));
		sample = 'sample';
		System.assertEquals(false, StringIsNullOrEmpty(sample));
	}
	
	/**
	 * @description not support Latin-1 Supplement
	 */
	public static String htmlEncode(final String sample){
		if(stringIsNullOrEmpty(sample)) return '';
		return sample
		.replace('&','&amp;')
		.replace('"','&quot;')
		.replace('<','&lt;')
		.replace('>','&gt;');
	}
	
	@isTest(seeAllData=false) public static void htmlEncodeTest(){
		String sample;
		try{
			System.assertEquals('', htmlEncode(sample));
		}catch(Exception ex){
			System.assertEquals(null, ex, 'htmlEncode can call when string is null');
		}
		sample = '<';
		System.assertEquals('&lt;', htmlEncode(sample));
		sample = '&';
		System.assertEquals('&amp;', htmlEncode(sample));
		sample = '>';
		System.assertEquals('&gt;', htmlEncode(sample));
		sample = '"';
		System.assertEquals('&quot;', htmlEncode(sample));
		sample = '<&">';
		System.assertEquals('&lt;&amp;&quot;&gt;', htmlEncode(sample));
		sample = 'sample';
		System.assertEquals(sample, htmlEncode(sample));
	}
	
	public static String stringJoin(final String[] samples){
		return stringJoin(samples, ',');
	}
	
	@isTest(seeAllData=false) public static void stringJoinTest(){
		String[] samples;
		System.assertEquals('', stringJoin(samples));
		samples = new String[]{};
		System.assertEquals('', stringJoin(samples));
		samples.add('');
		System.assertEquals('', stringJoin(samples));
		samples = new String[]{'sample'};
		System.assertEquals('sample', stringJoin(samples));
		samples.add('');
		System.assertEquals('sample,', stringJoin(samples));
		samples.add(0, '');
		System.assertEquals(',sample,', stringJoin(samples));
	}
	
	public static String stringJoin(final String[] samples, final String separator){
		String result = '';
		if(samples != null){
			String currentSeparator = '';
			for(String sample : samples){
				result += currentSeparator + sample;
				currentSeparator = separator;
			}
		}
		return result;
	}
	
	@isTest(seeAllData=false) public static void stringJoinTestWithSeparate(){
		String[] samples;
		System.assertEquals('', stringJoin(samples, ':'));
		samples = new String[]{};
		System.assertEquals('', stringJoin(samples, ':'));
		samples.add('');
		System.assertEquals('', stringJoin(samples, ':'));
		samples = new String[]{'sample'};
		System.assertEquals('sample', stringJoin(samples, ':'));
		samples.add('');
		System.assertEquals('sample:', stringJoin(samples, ':'));
		samples.add(0, '');
		System.assertEquals(':sample:', stringJoin(samples, ':'));
	}
	
	@isTest(seeAllData=false) public static void stringJoinTestWithSeparatePattern(){
		final String sampleString = 'text1;:,@><text2';
		final String[] splitters = ';:,@><'.split('');
		for(string splitter : splitters){
			System.assertEquals(sampleString, stringJoin(sampleString.split(splitter), splitter));
		}
	}
	
	public static List<Id> getIdList(final List<SObject> records, final Boolean onlyIdExist){
		final List<Id> ids = new List<Id>();
		if(records != null){
			for(SObject record : records){
				if(!onlyIdExist || record.id != null){
					ids.add(record.id);
				}
			}
		}
		return ids;
	}
	
	public static List<Id> getIdList(final List<SObject> records){
		return getIdList(records, false);
	}
	@isTest(seeAllData=false) public static void getIdList(){
		// tested at LogTriggerTest
	}
	
	public static String getRandomString(final Integer LengthRequired){
		final String CharList = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_';
		String Res = '';
		integer position;
		for(Integer i = 0; i <= LengthRequired; i++) {
			position = Math.mod(Math.round(Math.random() * CharList.length()), CharList.length());
			Res += stringCharAt(CharList, position);
		}
		return Res;
	}
	
	@isTest(seeAllData=false) public static void getRandomStringTest(){
		System.assertNotEquals(getRandomString(18), getRandomString(18));
		System.assertNotEquals(getRandomString(18), getRandomString(18));
		System.assertNotEquals(getRandomString(18), getRandomString(18));
		System.assertNotEquals(getRandomString(18), getRandomString(18));
		System.assertNotEquals(getRandomString(18), getRandomString(18));
		System.assertNotEquals(getRandomString(100), getRandomString(100));
		System.assertNotEquals(getRandomString(100), getRandomString(100));
		System.assertNotEquals(getRandomString(100), getRandomString(100));
		System.assertNotEquals(getRandomString(100), getRandomString(100));
		System.assertNotEquals(getRandomString(100), getRandomString(100));
	}
	
	public class IllegalArgumentException extends Exception{}
	
	public static String stringCharAt(final String sample, final Integer charAt){
		if(sample == null) throw new IllegalArgumentException();
		if(charAt == null) throw new IllegalArgumentException();
		if(stringIsNullOrEmpty(sample)) throw new IllegalArgumentException();
		if(charAt < 0) throw new IllegalArgumentException();
		if(charAt >= sample.length()) throw new IllegalArgumentException();
		return sample.substring(charAt, charAt + 1);
	}
	
	@isTest(seeAllData=false) public static void stringCharAtTest(){
		try{stringCharAt(null,0);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('',null);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('',-1);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('',-2);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('',1); }catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('',0); }catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('a',-1);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt('b', 1);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		
		final String sample = 'abcde';
		try{stringCharAt(sample, -1);}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		try{stringCharAt(sample, sample.length());}catch(IllegalArgumentException ex){System.assertNotEquals(null, ex);}
		
		System.assertEquals('a', stringCharAt(sample,0));
		System.assertEquals('b', stringCharAt(sample,1));
		System.assertEquals('c', stringCharAt(sample,2));
		System.assertEquals('d', stringCharAt(sample,3));
		System.assertEquals('e', stringCharAt(sample,4));
	}
	
	public class InnerUtilClass {
		public InnerUtilClass(){
			System.assertEquals(true, false, 'this line will be not instance');
		}
		
		public void test(){
			Integer i = 0;
			String sample = '';
			sample = i++ + '';
			sample = i++ + '' + sample;
		}
	}
	
}