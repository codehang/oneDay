package com.filter.ipandthread;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.management.RuntimeErrorException;

/**
 * 
 * @author jq-01
 * @version 1.1
 * @Time 2017.7.21
 *
 *	�˰����ṩ������д�ӿڣ�ʹ��������ʹ���Զ���ķ�����ֱ�ӽ��з����Ķ��弴��    *   This package does not provide an external overwrite interface, and the user can directly define the method if you want to use the custom method
 *	
 *	ע�⣺�����Ƚ��б��ݡ�	*	Note: you can make a backup first.
 *
 *	������õ���ģʽ	*	The singleton model is used in this class
 *
 */
public class IpAndThread {

//--------------------���Ͷ�����  *  Large object area----------------------------------------
	
	private List<ThreadLocal<String>> threadList = null;
	
	private HashMap<String,String> whiteHashMap = null;
	
	private HashMap<String,String> blackHashMap = null;
	
	private ThreadLocal<String> threadLocalStart = null;
	
//----------------------������  *  Variable region---------------------------------
	private static int max = 0;
	
	private static int time = 0;
	
	private static int index = 0;

	
	/**
	 * �û�������г�ʼ�����ſ��Խ�����������		*		The user must initialize before other operations can be performed
	 * 
	 * @param max	�趨�û����ʹ涨ʱ���ڵķ��ʴ�ʱ����		* 		Sets access restrictions for user access within the specified time
	 * 
	 * @param time	�趨IP���ʼ������		*		Set IP access interval restrictions
	 */
	public void init(int max, int time, int loging){
		
		try{
			
			this.max = max;
			
			this.time = time;
			
			this.threadList = new ArrayList<ThreadLocal<String>>();
			
			for(int i = 0;i<max;i++){
				
				ThreadLocal<String> threadLocal = new ThreadLocal<String>();
				
				threadList.add(threadLocal);
				
			}
			
			this.whiteHashMap = new HashMap<String, String>();
			
			this.blackHashMap = new HashMap<String, String>();
			
			this.threadLocalStart = new ThreadLocal<String>();
			
		}catch(Exception e){
			
			throw new RuntimeException("Start Exception !");
			
		}
		
	}
	
	
	/**
	 * ���ط��������ٴ��õ���ip��threadlocal�����жϣ�ȡ��threadlocal��ֵ���ж��Ƿ���ͬ���Ƿ�Ƿ�����		*		The weights are used to determine the IP and ThreadLocal that are received again, and the values of the ThreadLocal are removed to determine if the same and illegal access is made
	 * 
	 * @param ip	�û�ip		*		user ip
	 *  
	 * @param id	�û�id		*		user id	
	 * 
	 * @param threadLocal	���ݴ洢�û�ʱ���ص��߳�		*		Threads that are returned when stored by the user
	 * 
	 * @return	������������顣����true�����򷵻�false--ip�쳣�Զ����ص��������л��Ѿ��ں�������		*		If there is no objection to the operation. Returns true, otherwise returns false--ip, the exception is automatically loaded into the blacklist, or it is already in the blacklis
	 */
	
	public boolean isRepeat(String ip, String id, boolean isSaveThread, ThreadLocal<String> threadLocal){
		
		String[] ipThreads = threadLocal.get().split(",");
		
		long time = System.currentTimeMillis() - (long)Integer.parseInt(ipThreads[1]);
		
		int max = Integer.parseInt(ipThreads[0]);
		
		//����ȣ���У��IP���� 		*		If equal, check the number of IP
		
		if(time >= this.time){
			
			if(max >= this.max){
				
				whiteHashMap.remove(ip);
				
				blackHashMap.put(ip, id);
				
				return false;
				
			}
			
			if(id.equals(whiteHashMap.get(ip))){
				
				return true;
				
			}else if(id.equals(blackHashMap.get(ip))){
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	
	/**
	 * �����û�		*		Black subscriber
	 * 
	 * @param ip	��Ҫ���ڵ��û�		*		user ip
	 * 
	 * @param whyBlcak	��������		*		Black excuse
	 * 
	 * @return	������ɷ���true�����򷵻�false		*		The operation completes, returns true, otherwise returns false
	 */
	public boolean isPullBlackByIP(String ip, String whyBlcak){
		
		try{
			
			blackHashMap.put(ip, "");
			
			return true;
			
		}catch(Exception e){
			
			return false;
			
		}
		
	}
	
	
	/**
	 * ����Ӧ��ip�������		*		Remove the corresponding IP mask
	 * 
	 * @param ip	�û�ip		*		user ip
	 * 
	 * @return �����ɹ�����true�����򷵻�false		*		The operation completes, returns true, otherwise returns false
	 * 
	 */
	public boolean isWhiteUserByIP(String ip){
		
		try{
			
			blackHashMap.remove(ip);
			
			return true;
			
		}catch(Exception e){
			
			return false;
			
		}
		
	}
	
	
	
	
	/**
	 * ����boolean��ȡ��Ӧ���������ݣ�����Ϊtrue�����������������ݣ�������ȫ��Ϊfalse		*		According to Boolean access to the corresponding list of data, if all true, return all list data, but not all of false
	 * 
	 * @param white	true�򷵻ذ���������		*		True returns the whitelist data
	 * 
	 * @param black	true�򷵻غ���������		*		True returns the blacklist data
	 * 
	 * @return	�������������		*		Cached list data
	 * 
	 */
	public HashMap<String,String> getUserAll(boolean white, boolean black){
		
		HashMap<String,String> newALLMap = new HashMap<String, String>();
		
		if(white = true){
			
			newALLMap = whiteHashMap;
			
		}else if(black = true){
			
			newALLMap = blackHashMap;
			
		}else{
			
			throw new RuntimeException("�����Ƿ�...	*	Illegal operation...");
			
		}
		
		return newALLMap;
		
	}
	
	
	/**
	 * ������������û�		*		clear all user
	 * 
	 * @return
	 * 
	 */
	public boolean clearBlackAll(){
		
		try{
			
			blackHashMap = null;
			
			whiteHashMap = null;
			
			return true;
			
		}catch(Exception e){
			
			return false;
			
		}
		
	}

	
	/**
	 * �洢�û�ip���߳�������max,����������ѡ���Ƿ�洢�����û��߻�����		*		Store user IP, the number of threads in the max, developers choose whether to store to the configuration or Huan Cunzhong
	 * 
	 * @param ip	�û�ip		*		user ip
	 * 
	 * @param id	�����������ɵ�����id�����鶨��淶		*		The specific ID generated by developers suggests defining specifications
	 * 
	 * @return	����һ���û���ǰ��ʱ�洢���ݵ�ֵ		*		Returns the value of a temporary temporary storage of a user's content
	 * 
	 */
	public ThreadLocal<String> SaveWhiteUser(String ip, String id){
		
		if(threadList != null && threadList.size() > 0){
			
			if(threadList.size()==max){
				
				throw new RuntimeException("��ʱ�߳���Ŀ�Ѿ��ﵽ���趨�����ֵ...��ȴ��п��е�λ�û�������������	*	At this point the number of threads has reached your maximum value. Please wait for the free location or increase the number of restrictions");
				
			}else{
				
				ThreadLocal<String> threadLocal = new ThreadLocal<String>();
				
				threadLocal.set(ip+","+id);
				
				whiteHashMap.put(ip, id);
				
				index +=1;
				
				threadLocalStart.set(index+","+System.currentTimeMillis());
				
				return threadLocal;
				
			}
			
		}
		
		return null;
		
	}
	
}
