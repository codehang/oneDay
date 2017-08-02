package com.shield.jsandsql;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * ����ר������properties���õĸ��ֲ���
 * @author lenovo
 *
 *Such a variety of specialized for properties configuration to do
 *
 */
public class KVAllUtils {
	
	private static String path = "src/shield.properties";
	/**
	 * ��Ӽ�ֵ�� * Add value
	 * 
	 * @param key ���ݵļ� * Transitive key
	 * 
	 * @param value ֵ * value
	 */
	public static void addKeyWord(String key,String value) {
		
		try {
			
			Properties pps = new Properties();
			
			FileOutputStream out = new FileOutputStream(path,true);
			
			pps.setProperty(key, value);
			
	        pps.store(out, "shield :  " +value+ "  .......");	
	        
	        out.close();
	        
		} catch (Exception e) {
			
			System.out.println("ִ������ * Execution error");
			
			e.printStackTrace();
			
		}
	}
	
	/**
	 *���ݴ��ݵļ�����Ӧ��ֵ��Ϊ�� * Changes the value of the corresponding value to the null based on the passed key
	 *
	 * @param key	���ݵļ� * Transitive key
	 */
	public static void delete(String key) {
		
		addKeyWord(key,"");
		
	}

	
}
