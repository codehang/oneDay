package com.shield.jsandsql.inter;

/**
 * ���ò���ģʽ���䷽�����г���
 * @author jq-01
 * @Time 2017.7.14 9:39
 * @version 1.1
 * �������ݣ��Ż�����ģʽ���ı�����ã����ö�̬���е���
 */
public interface ShieldJSAndSQLInter {

	boolean shieldJSAll(String data,String shieldPath,String symoblPath,String codePath);
	
	void addKeyWord(String key,String value);
	
	void delete(String key);
	
	String encryptionMD5(String data);
	
	boolean shieldSQL(String text);
	
}
