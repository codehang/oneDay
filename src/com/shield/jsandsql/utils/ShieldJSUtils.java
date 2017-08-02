package com.shield.jsandsql.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;


/**
 * 此类专门用于过滤js注入 , * 工具类
 * 
 * This class is specifically designed to filter JS injection
 * 
 * @author lenovo
 *
 */
public class ShieldJSUtils {

	/**
	 * 过滤传递的数据，防止js注入 * Filtered data to prevent JS injection
	 * 
	 * @param data	传递的数据 * Transmitted data
	 * 
	 * @return	如果存在非法返回true，否则返回false，程序结束 * If there is an illegal return to true, or return to false, the end of the program
	 */
	public static boolean shieldJSAll(String data,String shieldPath,String symoblPath,String codePath) {
		
		//过滤字符串的非法格式 * Illegal format of filter string
		
		for (int p = 0; p < data.length(); p++) {
			
			String dataCharValue = data.substring(p, p+1);
			
			if(dataCharValue.equals("/")) {
				
				data = data.replace("/", "");
				
			}
			
			else if(dataCharValue.equals(" ")){
				
				data = data.replace(" ", "");
				
			}
			
			else if(dataCharValue.equals("'")){
				
				data = data.replace("'", "");
				
			}
			
			else if(dataCharValue.equals("&")){
				
				data = data.replace("&", "");
				
			}
			
		}
		
		if(isShield(data, shieldPath, symoblPath, codePath)){
			
			return true;
			
		}
		
		return false;
		
	}

	
	
	/**
	 * 本方法根据传递的properties路径获取到文件中所有的值 * This method gets all the values in the file based on the passed properties path
	 * 
	 * @param path	文件路径 * File path
	 * 
	 * @return	返回一个arraylist集合 *Returns a “ ArrayList ” collection
	 * 
	 */
	private static ArrayList<String> getSymbol(String path){
		
		ArrayList<String> symbols = new ArrayList<String>();
		
		try {
			
			Properties props = new Properties();

			FileInputStream fins = new FileInputStream(path);
			
			props.load(fins);
			
			for (int i = 1; i <= props.size(); i++) {
				
				String value = props.getProperty(i+"");
				
				symbols.add(value);
				
			}
			
			fins.close();
			
			return symbols;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
	}
	
	
	
	/**
	 * 对传递进来的数据进行各种过滤 * Filter the data that is passed in.
	 * 
	 * @param data	传递的数据 * Transmitted data
	 * 
	 * @param shieldPath	传递的关键词配置文件的路径 ，默认为src/shield.properties *  Pass the key word configuration file path, the default is src/shield.properties
	 * 
	 * @param symoblPath	传递的特殊符号配置文件的路径，默认为src/shield_symbol.properties * The path to the special symbol configuration file, the default is src/shield_symbol.properties
	 * 
	 * @param codePath		传递的特殊编码配置文件的路径，默认为 src/shield_code.properties * Pass the path of the special encoding profile, by default to src/shield_code.properties
	 * 
	 * @return	如果非法返回一个true，否则返回false，程序结束 * If you return to a true, or return to false, the end of the program
	 */
	private static boolean isShield(String data,String shieldPath,String symoblPath,String codePath){
		
		try {
			
			if(shieldPath == null){
				
				shieldPath = "src/shield.properties";
				
			}
			
			ArrayList<String> keywords = getSymbol(shieldPath);
			
			if(keywords != null){
				
				for (int i = 0; i < keywords.size(); i++) {
					
					String value = keywords.get(i);
					
					if(!value.equals("")){
						
						//过滤关键字 * Filter key
						
						if(data.contains(value)){
							
							return true;
							
						}else{
							
							//过滤特殊符号 * Filter special symbols
							
							if(symoblPath == null){
								
								symoblPath = "src/shield_symbol.properties";
								
							}
							
							ArrayList<String> symbols = getSymbol(symoblPath);
							
							if(symbols != null){
								
								for (int j = 0; j < symbols.size(); j++) {
									
									String symbolValue = symbols.get(j);
									
									if(!symbolValue.equals("")){
										
										if(data.contains(symbolValue)){
											
											return true ;
										
										}else{
											
											//过滤特殊编码 * Filter special code
											
											if(codePath == null){
												
												codePath = "src/shield_code.properties";
												
											}
											
											ArrayList<String> codes = getSymbol(codePath);
											
											if(codes != null){
												
												for (int k = 0; k < codes.size(); k++) {
													
													String codeValue = codes.get(k);
													
													if(data.contains(codeValue)){
													
														return true;
													
													}else{
													
														continue;
													
													}
												
												}
												
												return false;
											
											}else{
											
												throw new NullPointerException("特殊编码配置文件不能为空  * Special code profile cannot be empty");											
											
											}
										
										}
									
									}else{
									
										continue;
									
									}
								
								}
							
							}else{
								
								throw new NullPointerException("特殊符号配置文件不能为空  * Special symbol profile cannot be empty");

							
							}
						
						}
					
					}else{
						
						continue;
					
					}
				
				}
			
			}else{
				
				throw new NullPointerException("关键词配置文件不能为空  * Keywords profile cannot be empty");
			
			}
			
			
		
		} catch (Exception e) {
			
			throw new RuntimeException("数据处理异常   * Data Execution");
			
		}
		
		return false;
		
	}
	
}
