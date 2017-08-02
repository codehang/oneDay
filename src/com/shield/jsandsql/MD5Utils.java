package com.shield.jsandsql;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ����Ϊר������md5���ܵ��࣬һ������
 * 
 * ʹ���߿��Զ����������Զ���Ĳ���
 * 
 * @author lenovo
 *
 *
 *This class for the MD5 encryption for the class, the first level encryption
 *
 *Users can customize the operation of this class
 *
 */
public class MD5Utils {
	
	public static String encryptionMD5(String data) {
		
		MessageDigest digest;
		
		try {
			
			digest = MessageDigest.getInstance("md5");
			
			byte buff[] = digest.digest(data.getBytes());
			
			StringBuffer stringBuffer = new StringBuffer();
			
			for(byte b : buff){
				
				int index = b & 0xff ;
				
				String newData = Integer.toHexString(index);
				
				if(newData.length() == 1){
					
					stringBuffer.append("0");
					
				}
				
				stringBuffer.append(newData);
				
			}
			
			return stringBuffer.toString();

		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			
			return "";
			
		}
	}

}
