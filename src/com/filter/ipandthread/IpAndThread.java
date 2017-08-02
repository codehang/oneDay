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
 *	此包不提供对外重写接口，使用者若想使用自定义的方法，直接进行方法的定义即可    *   This package does not provide an external overwrite interface, and the user can directly define the method if you want to use the custom method
 *	
 *	注意：可以先进行备份。	*	Note: you can make a backup first.
 *
 *	本类采用单例模式	*	The singleton model is used in this class
 *
 */
public class IpAndThread {

//--------------------大型对象区  *  Large object area----------------------------------------
	
	private List<ThreadLocal<String>> threadList = null;
	
	private HashMap<String,String> whiteHashMap = null;
	
	private HashMap<String,String> blackHashMap = null;
	
	private ThreadLocal<String> threadLocalStart = null;
	
//----------------------变量区  *  Variable region---------------------------------
	private static int max = 0;
	
	private static int time = 0;
	
	private static int index = 0;

	
	/**
	 * 用户必须进行初始化，才可以进行其他操作		*		The user must initialize before other operations can be performed
	 * 
	 * @param max	设定用户访问规定时间内的访问此时限制		* 		Sets access restrictions for user access within the specified time
	 * 
	 * @param time	设定IP访问间隔限制		*		Set IP access interval restrictions
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
	 * 排重方法，对再次拿到的ip和threadlocal进行判断，取出threadlocal的值，判断是否相同和是否非法访问		*		The weights are used to determine the IP and ThreadLocal that are received again, and the values of the ThreadLocal are removed to determine if the same and illegal access is made
	 * 
	 * @param ip	用户ip		*		user ip
	 *  
	 * @param id	用户id		*		user id	
	 * 
	 * @param threadLocal	根据存储用户时返回的线程		*		Threads that are returned when stored by the user
	 * 
	 * @return	如果操作无异议。返回true，否则返回false--ip异常自动加载到黑名单中或已经在黑名单中		*		If there is no objection to the operation. Returns true, otherwise returns false--ip, the exception is automatically loaded into the blacklist, or it is already in the blacklis
	 */
	
	public boolean isRepeat(String ip, String id, boolean isSaveThread, ThreadLocal<String> threadLocal){
		
		String[] ipThreads = threadLocal.get().split(",");
		
		long time = System.currentTimeMillis() - (long)Integer.parseInt(ipThreads[1]);
		
		int max = Integer.parseInt(ipThreads[0]);
		
		//若相等，则校验IP次数 		*		If equal, check the number of IP
		
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
	 * 拉黑用户		*		Black subscriber
	 * 
	 * @param ip	需要拉黑的用户		*		user ip
	 * 
	 * @param whyBlcak	拉黑理由		*		Black excuse
	 * 
	 * @return	操作完成返回true，否则返回false		*		The operation completes, returns true, otherwise returns false
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
	 * 将对应的ip解除屏蔽		*		Remove the corresponding IP mask
	 * 
	 * @param ip	用户ip		*		user ip
	 * 
	 * @return 操作成功返回true，否则返回false		*		The operation completes, returns true, otherwise returns false
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
	 * 根据boolean获取相应的名单数据，若都为true，返回所有名单数据，但不能全部为false		*		According to Boolean access to the corresponding list of data, if all true, return all list data, but not all of false
	 * 
	 * @param white	true则返回白名单数据		*		True returns the whitelist data
	 * 
	 * @param black	true则返回黑名单数据		*		True returns the blacklist data
	 * 
	 * @return	缓存的名单数据		*		Cached list data
	 * 
	 */
	public HashMap<String,String> getUserAll(boolean white, boolean black){
		
		HashMap<String,String> newALLMap = new HashMap<String, String>();
		
		if(white = true){
			
			newALLMap = whiteHashMap;
			
		}else if(black = true){
			
			newALLMap = blackHashMap;
			
		}else{
			
			throw new RuntimeException("操作非法...	*	Illegal operation...");
			
		}
		
		return newALLMap;
		
	}
	
	
	/**
	 * 清除所有名单用户		*		clear all user
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
	 * 存储用户ip，线程数量在max,开发者自行选择是否存储到配置或者缓存中		*		Store user IP, the number of threads in the max, developers choose whether to store to the configuration or Huan Cunzhong
	 * 
	 * @param ip	用户ip		*		user ip
	 * 
	 * @param id	开发者所生成的特有id，建议定义规范		*		The specific ID generated by developers suggests defining specifications
	 * 
	 * @return	返回一个用户当前临时存储内容的值		*		Returns the value of a temporary temporary storage of a user's content
	 * 
	 */
	public ThreadLocal<String> SaveWhiteUser(String ip, String id){
		
		if(threadList != null && threadList.size() > 0){
			
			if(threadList.size()==max){
				
				throw new RuntimeException("此时线程数目已经达到你设定的最大值...请等待有空闲的位置或者增大限制数	*	At this point the number of threads has reached your maximum value. Please wait for the free location or increase the number of restrictions");
				
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
