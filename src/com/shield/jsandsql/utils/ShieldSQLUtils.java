package com.shield.jsandsql.utils;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * 
 * ������һ��������   ��Ҫ�ǶԴ�������ݷ�sql��һЩ����
 * 
 * This class is a tool class is mainly on the incoming data to prevent some of the operation of SQL
 * @author lenovo
 *
 */
public class ShieldSQLUtils {

	
	/**
	 * ������Ŀ���ǶԴ��ݵ����ݽ��й��ˣ���ֹsqlע�� * The purpose of this method is to filter the transmitted data to prevent SQL injection.
	 * 
	 * @param text	���ݵ����� * Transmitted data
	 * 
	 * @return	����Ƿ�����true�����򷵻�false��������� * If the illegal return to true, or return to false, the end of the program
	 * 
	 * 
	 */
	
	public static boolean shieldSQL(String text) {
		
		if(isSQL(text)){
			
			return true;
			
		}
		
		ArrayList<String> alist = getArrayList();
		
		for (int i = 0; i < alist.size(); i++) {
			
			if(text.contains(alist.get(i))){
				
				return true;
				
			}
			
		} 
		
		return false;
		
	}
	
	/**
	 * ������Ҫ���˵ķǷ��ַ� �������ڴ�����µķǷ��ַ� * Sets the illegal characters needed to filter, you can add new illegal characters
	 * 
	 * @return	����arraylist * Return ArrayList
	 */
	public static ArrayList<String> getArrayList(){
		
		 ArrayList<String> alist = new ArrayList<String>();
		 
		 alist.add("��");
		 
		 alist.add("\\");
		 
		 alist.add("/");
		 
		return alist;
		
	}

	
	/**
	 * �˷�������������ʽ���ж� * This method is used to determine the regular expression.
	 * 
	 * @param text	���ݵ����� * Transmitted data
	 * 
	 * @return	�����ͬ����true������false * If the same returns false, otherwise true
	 */
	public static boolean isSQL(String text){
		
		String CHECKSQL = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
		
		return Pattern.matches(CHECKSQL,text);
		
	}
	
}
