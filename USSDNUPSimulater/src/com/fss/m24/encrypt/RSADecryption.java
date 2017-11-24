package com.fss.m24.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.Cipher;

import Decoder.BASE64Decoder;


public class RSADecryption {
	
	 private static final String PRIVATE_KEY_FILE = "Private.key"; 
	 private static String encrypted_Data = "W9QTf8nCsf4t1EIeTgpE0rMv5Ltc9h8RQT7SalHmFnZ5r2ufCkZhUb4lnwOYCcBekSkdaqT0yhO4I9btkDtV3dES02qJ0Ngimgi9l/gupl/hP0CPWW3SS+apOa/Nyg3ZONVPn/QN7OI9MMRUnxHuGG/eaJRYvlB6E/OelntUyoQ=";
	 
	public static void main(String[] args) {
		
		//byte[] encryptedData = encrypted_Data.getBytes(); 
		//byte[] encryptedData = DatatypeConverter.parseHexBinary(encrypted_Data);
		
		try {
			
			byte[] encryptedData = new BASE64Decoder().decodeBuffer(encrypted_Data);
				   
			RSADecryption rsaDecryption = new RSADecryption();

			//Descypt Data using Private Key  
			rsaDecryption.decryptData(encryptedData);  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /** 
	  * Encrypt Data 
	  * @param data 
	  * @throws IOException 
	  */  
	 public void decryptData(byte[] data) throws IOException {  
	  System.out.println("\n----------------DECRYPTION STARTED------------");  
	  byte[] descryptedData = null;  
	    
	  try {  
	   PrivateKey privateKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);  
	   Cipher cipher = Cipher.getInstance("RSA");  
	   cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	   descryptedData = cipher.doFinal(data);  
	   System.out.println("Decrypted Data: " + new String(descryptedData));  
	     
	  } catch (Exception e) {  
	   e.printStackTrace();  
	  }   
	    
	  System.out.println("----------------DECRYPTION COMPLETED------------");    
	 }  

	 /** 
	  * read Public Key From File 
	  * @param fileName 
	  * @return 
	  * @throws IOException 
	  */  
	 public PrivateKey readPrivateKeyFromFile(String fileName) throws IOException{  
	  FileInputStream fis = null;  
	  ObjectInputStream ois = null;  
	  try {  
	   fis = new FileInputStream(new File(fileName));  
	   ois = new ObjectInputStream(fis);  
	     
	   BigInteger modulus = (BigInteger) ois.readObject();  
	      BigInteger exponent = (BigInteger) ois.readObject();  
	     
	      //Get Private Key  
	      RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);  
	      KeyFactory fact = KeyFactory.getInstance("RSA");  
	      PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);  
	              System.out.println("privateKey "+privateKey );
	      return privateKey;  
	        
	  } catch (Exception e) {  
	   e.printStackTrace();  
	  }  
	  finally{  
	   if(ois != null){  
	    ois.close();  
	    if(fis != null){  
	     fis.close();  
	    }  
	   }  
	  }  
	  return null;  
	 }  
	
}
