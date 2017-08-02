package com.filter.ipandthread.utils;

import com.filter.ipandthread.IpAndThread;

/**

 *	����Ϊ����ģʽ
 *
 *		�ṩ��������������ʽ�Ͷ�ʽ
 *
 *	���ж�ʽ--
 *
 *	ͬ����ʽ��synchronized-��Ϊ��̬class��
 *
 *	�������ͣ�����ģʽ
 *
 *	Ŀ���ǻ��Ψһ���Ұ�ȫ��IpAndThread����
 *
 *	����������
 *
 *	����ʽ--
 *
 *	�����ٶȿ�
 *
 *	ռ����Դ��
 *
 *	����������
 *
 *	��ʹ�����ʵ���ѡ���ʺϵ�ģʽ
 *
 *This class is a singleton pattern
*
* provide two methods, lazy and evil
*
* the evil Chinese style -
*
* synchronization mode: synchronized- lock for static class lock
*
* singleton type: hunger mode
*
* the purpose is to obtain unique and secure IpAndThread objects
*
*.....
*
* lazy - -
*
* running fast
*
* less resources available
*
*.....
*
* ask the user to select the appropriate pattern appropriately
 *
 * @time 2017.7.26
 * @author jq-01
 * @version 1.1
 *
 *
 */
public class SingletonIpAndThread {
	
	private SingletonIpAndThread(){}
	
	private static IpAndThread ipAndThread = new IpAndThread();
	
	
	/**
	 * ����ʽ--��������Ҫ���ٲ�����Դ=���ٵ����		*		Slacker - applies to situations that require haste and resources = too little
	 * 
	 * @return
	 */
	public static IpAndThread getLazyIpAndThread(){
		
			return ipAndThread;
			
	}
	
	
	/**
	 * ����ģʽ��ȡIpAndThread�����		*		Singleton pattern gets IpAndThread class object
	 * 
	 * @return IpAndThread����		*		IpAndThread is Object
	 */
	public static IpAndThread getBadmashIpAndThread(){
		
		if(ipAndThread == null){
			
			synchronized (SingletonIpAndThread.class) {
				
				if(ipAndThread == null){
					
				ipAndThread = new IpAndThread();
				
				}
				
			}
			
		}
		
		return ipAndThread;
		
	}
	
}
