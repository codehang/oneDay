package com.shield.jsandsql.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;


/**
 * ����ר�����ڹ���jsע�� , * ������
 * 
 * This class is specifically designed to filter JS injection
 * 
 * @author lenovo
 *
 */
public class ShieldJSUtils {

	/**
	 * ���˴��ݵ����ݣ���ֹjsע�� * Filtered data to prevent JS injection
	 * 
	 * @param data	���ݵ����� * Transmitted data
	 * 
	 * @return	������ڷǷ�����true�����򷵻�false��������� * If there is an illegal return to true, or return to false, the end of the program
	 */
	public static boolean shieldJSAll(String data,String shieldPath,String symoblPath,String codePath) {
		
		//�����ַ����ķǷ���ʽ * Illegal format of filter string
		
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
	 * ���������ݴ��ݵ�properties·����ȡ���ļ������е�ֵ * This method gets all the values in the file based on the passed properties path
	 * 
	 * @param path	�ļ�·�� * File path
	 * 
	 * @return	����һ��arraylist���� *Returns a �� ArrayList �� collection
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
	 * �Դ��ݽ��������ݽ��и��ֹ��� * Filter the data that is passed in.
	 * 
	 * @param data	���ݵ����� * Transmitted data
	 * 
	 * @param shieldPath	���ݵĹؼ��������ļ���·�� ��Ĭ��Ϊsrc/shield.properties *  Pass the key word configuration file path, the default is src/shield.properties
	 * 
	 * @param symoblPath	���ݵ�������������ļ���·����Ĭ��Ϊsrc/shield_symbol.properties * The path to the special symbol configuration file, the default is src/shield_symbol.properties
	 * 
	 * @param codePath		���ݵ�������������ļ���·����Ĭ��Ϊ src/shield_code.properties * Pass the path of the special encoding profile, by default to src/shield_code.properties
	 * 
	 * @return	����Ƿ�����һ��true�����򷵻�false��������� * If you return to a true, or return to false, the end of the program
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
						
						//���˹ؼ��� * Filter key
						
						if(data.contains(value)){
							
							return true;
							
						}else{
							
							//����������� * Filter special symbols
							
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
											
											//����������� * Filter special code
											
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
											
												throw new NullPointerException("������������ļ�����Ϊ��  * Special code profile cannot be empty");											
											
											}
										
										}
									
									}else{
									
										continue;
									
									}
								
								}
							
							}else{
								
								throw new NullPointerException("������������ļ�����Ϊ��  * Special symbol profile cannot be empty");

							
							}
						
						}
					
					}else{
						
						continue;
					
					}
				
				}
			
			}else{
				
				throw new NullPointerException("�ؼ��������ļ�����Ϊ��  * Keywords profile cannot be empty");
			
			}
			
			
		
		} catch (Exception e) {
			
			throw new RuntimeException("���ݴ����쳣   * Data Execution");
			
		}
		
		return false;
		
	}
	
}
