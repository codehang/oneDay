package com.shield.jsandsql;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 此类专门用于properties配置的各种擦做
 * @author lenovo
 *
 *Such a variety of specialized for properties configuration to do
 *
 */
public class KVAllUtils {
	
	private static String path = "src/shield.properties";
	/**
	 * 添加键值对 * Add value
	 * 
	 * @param key 传递的键 * Transitive key
	 * 
	 * @param value 值 * value
	 */
	public static void addKeyWord(String key,String value) {
		
		try {
			
			Properties pps = new Properties();
			
			FileOutputStream out = new FileOutputStream(path,true);
			
			pps.setProperty(key, value);
			
	        pps.store(out, "shield :  " +value+ "  .......");	
	        
	        out.close();
	        
		} catch (Exception e) {
			
			System.out.println("执行有误 * Execution error");
			
			e.printStackTrace();
			
		}
	}
	
	/**
	 *根据传递的键将对应的值变为空 * Changes the value of the corresponding value to the null based on the passed key
	 *
	 * @param key	传递的键 * Transitive key
	 */
	public static void delete(String key) {
		
		addKeyWord(key,"");
		
	}

	
}
