package com.filter.ipandthread.utils;

import com.filter.ipandthread.IpAndThread;

/**

 *	本类为单例模式
 *
 *		提供两个方法，懒汉式和恶汉式
 *
 *	其中恶汉式--
 *
 *	同步方式：synchronized-锁为静态class锁
 *
 *	单例类型：饿汉模式
 *
 *	目的是获得唯一并且安全的IpAndThread对象
 *
 *	。。。。。
 *
 *	懒汉式--
 *
 *	运行速度快
 *
 *	占用资源少
 *
 *	。。。。。
 *
 *	请使用者适当的选择适合的模式
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
	 * 懒汉式--适用于需要急速并且资源=过少的情况		*		Slacker - applies to situations that require haste and resources = too little
	 * 
	 * @return
	 */
	public static IpAndThread getLazyIpAndThread(){
		
			return ipAndThread;
			
	}
	
	
	/**
	 * 单例模式获取IpAndThread类对象		*		Singleton pattern gets IpAndThread class object
	 * 
	 * @return IpAndThread对象		*		IpAndThread is Object
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
