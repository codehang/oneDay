package com.shield.jsandsql;


import com.shield.jsandsql.inter.ShieldJSAndSQLInter;
import com.shield.jsandsql.utils.KVAllUtils;
import com.shield.jsandsql.utils.MD5Utils;
import com.shield.jsandsql.utils.ShieldJSUtils;
import com.shield.jsandsql.utils.ShieldSQLUtils;

/**
 * 
 * ------------如果大家有什么疑问或者好的建议，欢迎交流，微信号x235527，谢谢！------------------
 * 
 * --本类源码采用GBK编码，如果对大家造成什么不便，可以手动转义，请多多包涵！
 * 
 * 汉语 ：
 * 
 * 	此类为屏蔽js注入和sql注入的工具类
 * 	 
 * 	  *本类提供五个方法
 * 		
 * 		--boolean  shieldJSAll(String data,String shieldPath,String symoblPath,String codePath) : 屏蔽素有的js注入，用户可以在properties中自行添加关键字等
 * 		
 * 		--void addKeyWord(String keyWord) : 您可以向properties中自动添加一个键值对
 * 		
 * 		--void delete(String key) : 您可以根据键删除对应的值，需要注意的是这里的删除是将“值”变为""
 * 		
 * 		--String encryptionMD5(String data)：你可以自行选择将您的数据通过这个方法进行加密，加密采用md5方式（一层加密）
 * 		
 * 		--boolean shieldSQL(String text) ：此方法目的是对获取的数据进行sql方式的判断
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
 * 		--String encryptionMD5(String data)：You can choose to use this method to encrypt your data, encryption using MD5 (a layer of encryption)
 * 		
 * 		--boolean shieldSQL(String text) ：The purpose of this method is to judge the data obtained by SQL method.
 * 
 * @author lenovo
 * @version 1.0
 */
public class ShieldJSAndSQL implements ShieldJSAndSQLInter {

	/*
	 * 	方法原理 ：首先获取到用户传递进来的数据，分别有四个步骤
	 * 	
	 * 	一 ：关键词初步过滤 ：
	 * 		
	 * 		将properties中的关键词通过
	 * 			
	 * 			Properties pps = new Properties(); 
	 * 			
	 * 			pps.load(new FileInputStream("Test.properties")); 
	 * 			
	 * 			String value = pps.getProperty(String key)
	 * 	  	
	 * 		获得value，对value进行for循环，拿到每一个关键词，利用string中方法判断接收的数据是否包含遍历出来的关键字，如果有直接返回true，方法结束，没有继续向下执行
	 * 	
	 * 	二 ： 过滤一些特殊符号（比如 < : ; "" '等）
	 * 		
	 * 		如同一，判断是否包含这些特殊的字符类型，存在返回true。方法结束，不存在程序向下执行
	 * 	
	 * 	三 ： 过滤一些特殊编码（八进制，十六进制等）
	 * 		
	 * 		将获取的数据进行各种转义，每种转义又分别与关键字和特殊字符进行匹配，匹配存在那么就返回true，方法结束，否则方法继续向下执行
	 * 	
	 * 	四 ： 对一些特殊语句（如空格、大小写）进行整合以及匹配
	 * 		
	 * 		将传递进来的数据进行筛选，判断是否存在不规则语句，将其进行修改，完成后对其进行判断，判断不通过返回true，方法结束，否则返回false，方法结束
	 * 
	 * 方法目的 ： 防止您的程序遭到js（xss）非法攻击。
	 * 
	 * 提醒 ：任何防御错输都有可能被有机可乘，所以需要您多注意代码编写习惯，同时在这里建议您使用此方法后继续进行html转义。
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
	 * 根据接收到的数据通过本方法进行过滤  * According to the received data through the method of filtering
	 * 
	 * @param data	接收到的数据 * Received data
	 * 
	 * @param shieldPath	传递的关键词配置文件的路径 ，默认为src/shield.properties * Pass the key word configuration file path, the default is src/shield.properties
	 * 
	 * @param symoblPath	传递的特殊符号配置文件的路径，默认为src/shield_symbol.properties * The path to the special symbol configuration file, the default is src/shield_symbol.properties
	 * 
	 * @param codePath		传递的特殊编码配置文件的路径，默认为 src/shield_code.properties * Pass the path of the special encoding profile, by default to src/shield_code.properties
	 * 
	 * @return	如果存在非法那么就返回true，否则返回false * Returns "true" if there is an illegal, otherwise returns "false"
	 */
	public boolean shieldJSAll(String data,String shieldPath,String symoblPath,String codePath){
		
		return ShieldJSUtils.shieldJSAll(data, shieldPath, symoblPath, codePath);
	}
	
	
	/*
	 * 方法原理 ： 利用properties的load方法将需要保存的键值对写入
	 * 
	 * 方法目的 ： 个性化的添加自己所想要过滤的关键字
	 * 
	 * Methods: using load method, principle of properties will need to save the key to writing
	 * 
	 * Method purpose: you can add the keyword you want to filter
	 */
	
	/**
	 * 根据键给properties通过方法添加键值对，注意键必须是阿拉伯数字并且按照properties中的排列顺序 * According to the key to properties through the method of adding keys, note that the key must be the number of Arabia and in accordance with the order of the properties
	 * 
	 * @param value	需要添加的值 * Need to add the value
	 * 
	 * @param key 需呀添加的键 * Need to add the key
	 */
	public void addKeyWord(String key,String value){
		KVAllUtils.addKeyWord(key,value);
	}
	
	
	/*
	 * 方法原理 ： 您可以根据键删除对应的值，通过properties中的方法，但是需要注意的是这里的删除，
	 * 		
	 * 		意思上是将值变为空字符串，这样做的目的是更好的利用资源，您可以在次使用时直接将上次删除的键添加进去即可
	 * 
	 * 方法目的 ： 您可以个性化的去掉自己所不想过滤的关键字
	 * 
	 * Method of principle: you can delete the corresponding value according to the key, through the method in properties, but need to pay attention to is the delete here,
	 * 
	 * 		Meaning is to change the value of an empty string, so that the purpose is to better use of resources, you can use the time to add the last delete key can be added
	 * 
	 * Method purpose: you can personalize the key words that you don't want to filter.
	 * 
	 */
	
	/**
	 * 根据键删除对应的值 * According to the key to delete the corresponding value
	 * 
	 * @param key	索引键 * Index key
	 */
	public void delete(String key){
		KVAllUtils.delete(key);
	}
	
	
	/*
	 * 方法原理 ： 对传递的数据，底层采用MD5加密，进行一层加密，如果另有需求，您可选择自行加密或者在MD5Utils类中自行修改源码
	 * 
	 * 方法目的 ： 对于防止sql注入的问题，可以采用对数据加密的方法，特在此处提供一个用于加密的方法
	 * 
	 * Method principle: the transmission of the data, the bottom of the use of MD5 encryption, a layer of encryption, if there is another demand, You can choose to encrypt their own or in the MD5Utils class to modify the source
	 * 
	 * Method objective: to prevent the problem of SQL injection, we can use the method of data encryption, especially in here to provide a method for encryption
	 * 
	 * */
	
	/**
	 * 将传递进来的数据进行md5加密 * The data will be transmitted to MD5 encryption
	 * 
	 * @param data	传递的数据 * Transmitted data
	 * 
	 * @return	返回一个加密过的字符串 * Returns an encrypted string
	 */
	public String encryptionMD5(String data){
		return MD5Utils.encryptionMD5(data);
	}
	
	/*
	 * 方法原理 ： 对接收的数据进行各种过滤和判断，判断传递进来的数据中是否存在单引号，--等，将其转义
	 * 
	 * 方法目的 ： 防止您的程序受到sql注入，
	 * 
	 * 提醒 ：任何防御错输都有可能被有机可乘，所以需要您多注意代码编写习惯，同时在这里建议您采用参数插入数据，尽量不使用拼接sql语句
	 * 
	 * Methods: various filtering and judging principle of the received data, the existence of single quotes, determine the data passed in, etc., to escape
	 * 
	 * Method purpose: to prevent your program from being injected with sql,
	 * 
	 * Reminder: any wrong defense are likely to be used, so you need to pay more attention to the code writing habits, and here it is recommended that you use the parameters to insert data, try not to use mosaic SQL statement
	 * 
	 * */
	
	/**
	 * 对接收的数据进行sql的屏蔽 * Shielding the received data for SQL
	 * 
	 * @param text	接收到额数据 * Receive the amount of data
	 * 
	 * @return	过滤返回true，放行返回false * Filtered return true, released to return to false
	 */
	public boolean shieldSQL(String text){
		return ShieldSQLUtils.shieldSQL(text);
	}
}
