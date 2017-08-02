package com.shield.jsandsql;


import com.shield.jsandsql.inter.ShieldJSAndSQLInter;
import com.shield.jsandsql.utils.KVAllUtils;
import com.shield.jsandsql.utils.MD5Utils;
import com.shield.jsandsql.utils.ShieldJSUtils;
import com.shield.jsandsql.utils.ShieldSQLUtils;

/**
 * 
 * ------------��������ʲô���ʻ��ߺõĽ��飬��ӭ������΢�ź�x235527��лл��------------------
 * 
 * --����Դ�����GBK���룬����Դ�����ʲô���㣬�����ֶ�ת�壬���������
 * 
 * ���� ��
 * 
 * 	����Ϊ����jsע���sqlע��Ĺ�����
 * 	 
 * 	  *�����ṩ�������
 * 		
 * 		--boolean  shieldJSAll(String data,String shieldPath,String symoblPath,String codePath) : �������е�jsע�룬�û�������properties��������ӹؼ��ֵ�
 * 		
 * 		--void addKeyWord(String keyWord) : ��������properties���Զ����һ����ֵ��
 * 		
 * 		--void delete(String key) : �����Ը��ݼ�ɾ����Ӧ��ֵ����Ҫע����������ɾ���ǽ���ֵ����Ϊ""
 * 		
 * 		--String encryptionMD5(String data)�����������ѡ����������ͨ������������м��ܣ����ܲ���md5��ʽ��һ����ܣ�
 * 		
 * 		--boolean shieldSQL(String text) ���˷���Ŀ���ǶԻ�ȡ�����ݽ���sql��ʽ���ж�
 * 
 * English :
 * 
 * This class is a tool for shielding JS injection and SQL injection.
 * 	 
 * 	  *This class provides five methods
 * 		
 * 		--boolean  shieldJSAll(String data) : Shield known as the JS injection, the user can add the keyword in the properties, etc.
 * 		
 * 		--void addKeyWord(String keyWord) : You can automatically add a key to properties
 *  	
 *  	--void delete(String key) : You can delete the corresponding value according to the key, need to pay attention to is that the "value" is changed to""
 * 		
 * 		--String encryptionMD5(String data)��You can choose to use this method to encrypt your data, encryption using MD5 (a layer of encryption)
 * 		
 * 		--boolean shieldSQL(String text) ��The purpose of this method is to judge the data obtained by SQL method.
 * 
 * @author lenovo
 * @version 1.0
 */
public class ShieldJSAndSQL implements ShieldJSAndSQLInter {

	/*
	 * 	����ԭ�� �����Ȼ�ȡ���û����ݽ��������ݣ��ֱ����ĸ�����
	 * 	
	 * 	һ ���ؼ��ʳ������� ��
	 * 		
	 * 		��properties�еĹؼ���ͨ��
	 * 			
	 * 			Properties pps = new Properties(); 
	 * 			
	 * 			pps.load(new FileInputStream("Test.properties")); 
	 * 			
	 * 			String value = pps.getProperty(String key)
	 * 	  	
	 * 		���value����value����forѭ�����õ�ÿһ���ؼ��ʣ�����string�з����жϽ��յ������Ƿ�������������Ĺؼ��֣������ֱ�ӷ���true������������û�м�������ִ��
	 * 	
	 * 	�� �� ����һЩ������ţ����� < : ; "" '�ȣ�
	 * 		
	 * 		��ͬһ���ж��Ƿ������Щ������ַ����ͣ����ڷ���true�����������������ڳ�������ִ��
	 * 	
	 * 	�� �� ����һЩ������루�˽��ƣ�ʮ�����Ƶȣ�
	 * 		
	 * 		����ȡ�����ݽ��и���ת�壬ÿ��ת���ֱַ���ؼ��ֺ������ַ�����ƥ�䣬ƥ�������ô�ͷ���true���������������򷽷���������ִ��
	 * 	
	 * 	�� �� ��һЩ������䣨��ո񡢴�Сд�����������Լ�ƥ��
	 * 		
	 * 		�����ݽ��������ݽ���ɸѡ���ж��Ƿ���ڲ�������䣬��������޸ģ���ɺ��������жϣ��жϲ�ͨ������true���������������򷵻�false����������
	 * 
	 * ����Ŀ�� �� ��ֹ���ĳ����⵽js��xss���Ƿ�������
	 * 
	 * ���� ���κη������䶼�п��ܱ��л��ɳˣ�������Ҫ����ע������дϰ�ߣ�ͬʱ�����ｨ����ʹ�ô˷������������htmlת�塣
	 * 
	 * 
	 * Method principle: first to get the user to pass in the data, there are four steps

	* 	One: preliminary filtration:
	
	* 	pass the key words in properties
	
	* 		Properties pps = new Properties(); 
	 * 			
	 * 			pps.load(new FileInputStream("Test.properties")); 
	 * 			
	 * 			String value = pps.getProperty(String key)
	
	* 		value, the value for cycle, get every one of the words, to determine whether the received data contains the key word out traversal using the string method, if there is a direct return to the true, the method completes, no further down the execution
	
	* 	Two: filter some special symbols (such as <: "")
	
	* 		as one, to determine if this is a special type of character, there is a return to true. End of the method, there is no program to execute down
	
	* 	Three: filtering some special encoding (octal, hexadecimal sixteen etc.)
	
	* 		will obtain the data of various each kind of escape, escape respectively with keywords and special characters, and match then return true, otherwise the method completes, continue execution method
	
	* 	Four: the integration of a number of special statements (such as spaces, case), and matching
	
	* 		the data passed in screening, to determine whether there is not a statement of rules will be modified, after the completion of its judgment, judgment by return true method over, otherwise it returns false, end method
	 * 
	 * Method purpose: to prevent your program from being attacked by JS (XSS).
	 * 
	 * Reminder: any wrong defense are likely to be used, so you need to pay more attention to the code writing habits, at the same time you recommend using this method here to continue HTML escape. .
	 * 
	 * */
	
	
	 /**
	 * ���ݽ��յ�������ͨ�����������й���  * According to the received data through the method of filtering
	 * 
	 * @param data	���յ������� * Received data
	 * 
	 * @param shieldPath	���ݵĹؼ��������ļ���·�� ��Ĭ��Ϊsrc/shield.properties * Pass the key word configuration file path, the default is src/shield.properties
	 * 
	 * @param symoblPath	���ݵ�������������ļ���·����Ĭ��Ϊsrc/shield_symbol.properties * The path to the special symbol configuration file, the default is src/shield_symbol.properties
	 * 
	 * @param codePath		���ݵ�������������ļ���·����Ĭ��Ϊ src/shield_code.properties * Pass the path of the special encoding profile, by default to src/shield_code.properties
	 * 
	 * @return	������ڷǷ���ô�ͷ���true�����򷵻�false * Returns "true" if there is an illegal, otherwise returns "false"
	 */
	public boolean shieldJSAll(String data,String shieldPath,String symoblPath,String codePath){
		
		return ShieldJSUtils.shieldJSAll(data, shieldPath, symoblPath, codePath);
	}
	
	
	/*
	 * ����ԭ�� �� ����properties��load��������Ҫ����ļ�ֵ��д��
	 * 
	 * ����Ŀ�� �� ���Ի�������Լ�����Ҫ���˵Ĺؼ���
	 * 
	 * Methods: using load method, principle of properties will need to save the key to writing
	 * 
	 * Method purpose: you can add the keyword you want to filter
	 */
	
	/**
	 * ���ݼ���propertiesͨ��������Ӽ�ֵ�ԣ�ע��������ǰ��������ֲ��Ұ���properties�е�����˳�� * According to the key to properties through the method of adding keys, note that the key must be the number of Arabia and in accordance with the order of the properties
	 * 
	 * @param value	��Ҫ��ӵ�ֵ * Need to add the value
	 * 
	 * @param key ��ѽ��ӵļ� * Need to add the key
	 */
	public void addKeyWord(String key,String value){
		KVAllUtils.addKeyWord(key,value);
	}
	
	
	/*
	 * ����ԭ�� �� �����Ը��ݼ�ɾ����Ӧ��ֵ��ͨ��properties�еķ�����������Ҫע����������ɾ����
	 * 		
	 * 		��˼���ǽ�ֵ��Ϊ���ַ�������������Ŀ���Ǹ��õ�������Դ���������ڴ�ʹ��ʱֱ�ӽ��ϴ�ɾ���ļ���ӽ�ȥ����
	 * 
	 * ����Ŀ�� �� �����Ը��Ի���ȥ���Լ���������˵Ĺؼ���
	 * 
	 * Method of principle: you can delete the corresponding value according to the key, through the method in properties, but need to pay attention to is the delete here,
	 * 
	 * 		Meaning is to change the value of an empty string, so that the purpose is to better use of resources, you can use the time to add the last delete key can be added
	 * 
	 * Method purpose: you can personalize the key words that you don't want to filter.
	 * 
	 */
	
	/**
	 * ���ݼ�ɾ����Ӧ��ֵ * According to the key to delete the corresponding value
	 * 
	 * @param key	������ * Index key
	 */
	public void delete(String key){
		KVAllUtils.delete(key);
	}
	
	
	/*
	 * ����ԭ�� �� �Դ��ݵ����ݣ��ײ����MD5���ܣ�����һ����ܣ����������������ѡ�����м��ܻ�����MD5Utils���������޸�Դ��
	 * 
	 * ����Ŀ�� �� ���ڷ�ֹsqlע������⣬���Բ��ö����ݼ��ܵķ��������ڴ˴��ṩһ�����ڼ��ܵķ���
	 * 
	 * Method principle: the transmission of the data, the bottom of the use of MD5 encryption, a layer of encryption, if there is another demand, You can choose to encrypt their own or in the MD5Utils class to modify the source
	 * 
	 * Method objective: to prevent the problem of SQL injection, we can use the method of data encryption, especially in here to provide a method for encryption
	 * 
	 * */
	
	/**
	 * �����ݽ��������ݽ���md5���� * The data will be transmitted to MD5 encryption
	 * 
	 * @param data	���ݵ����� * Transmitted data
	 * 
	 * @return	����һ�����ܹ����ַ��� * Returns an encrypted string
	 */
	public String encryptionMD5(String data){
		return MD5Utils.encryptionMD5(data);
	}
	
	/*
	 * ����ԭ�� �� �Խ��յ����ݽ��и��ֹ��˺��жϣ��жϴ��ݽ������������Ƿ���ڵ����ţ�--�ȣ�����ת��
	 * 
	 * ����Ŀ�� �� ��ֹ���ĳ����ܵ�sqlע�룬
	 * 
	 * ���� ���κη������䶼�п��ܱ��л��ɳˣ�������Ҫ����ע������дϰ�ߣ�ͬʱ�����ｨ�������ò����������ݣ�������ʹ��ƴ��sql���
	 * 
	 * Methods: various filtering and judging principle of the received data, the existence of single quotes, determine the data passed in, etc., to escape
	 * 
	 * Method purpose: to prevent your program from being injected with sql,
	 * 
	 * Reminder: any wrong defense are likely to be used, so you need to pay more attention to the code writing habits, and here it is recommended that you use the parameters to insert data, try not to use mosaic SQL statement
	 * 
	 * */
	
	/**
	 * �Խ��յ����ݽ���sql������ * Shielding the received data for SQL
	 * 
	 * @param text	���յ������� * Receive the amount of data
	 * 
	 * @return	���˷���true�����з���false * Filtered return true, released to return to false
	 */
	public boolean shieldSQL(String text){
		return ShieldSQLUtils.shieldSQL(text);
	}
}
