package com.shield.jsandsql.inter;

/**
 * 利用策略模式对其方法进行抽象
 * @author jq-01
 * @Time 2017.7.14 9:39
 * @version 1.1
 * 更新内容，优化策略模式，改变类调用，采用多态进行调用
 */
public interface ShieldJSAndSQLInter {

	boolean shieldJSAll(String data,String shieldPath,String symoblPath,String codePath);
	
	void addKeyWord(String key,String value);
	
	void delete(String key);
	
	String encryptionMD5(String data);
	
	boolean shieldSQL(String text);
	
}
