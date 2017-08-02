package com.shield.jsandsql.utils;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * 
 * 本类是一个工具类   主要是对传入的数据防sql的一些操作
 * 
 * This class is a tool class is mainly on the incoming data to prevent some of the operation of SQL
 * @author lenovo
 *
 */
public class ShieldSQLUtils {

	
	/**
	 * 本方法目的是对传递的数据进行过滤，防止sql注入 * The purpose of this method is to filter the transmitted data to prevent SQL injection.
	 * 
	 * @param text	传递的数据 * Transmitted data
	 * 
	 * @return	如果非法返回true，否则返回false，程序结束 * If the illegal return to true, or return to false, the end of the program
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
	 * 设置需要过滤的非法字符 ，可以在此添加新的非法字符 * Sets the illegal characters needed to filter, you can add new illegal characters
	 * 
	 * @return	返回arraylist * Return ArrayList
	 */
	public static ArrayList<String> getArrayList(){
		
		 ArrayList<String> alist = new ArrayList<String>();
		 
		 alist.add("‘");
		 
		 alist.add("\\");
		 
		 alist.add("/");
		 
		return alist;
		
	}

	
	/**
	 * 此方法用于正则表达式的判断 * This method is used to determine the regular expression.
	 * 
	 * @param text	传递的数据 * Transmitted data
	 * 
	 * @return	如果相同返回true，否则false * If the same returns false, otherwise true
	 */
	public static boolean isSQL(String text){
		
		String CHECKSQL = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
		
		return Pattern.matches(CHECKSQL,text);
		
	}
	
}
