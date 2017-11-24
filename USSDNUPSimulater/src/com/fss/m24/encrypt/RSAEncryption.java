package com.fss.m24.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import Decoder.BASE64Encoder;
public class RSAEncryption {

	private static final String PUBLIC_KEY_FILE = "D:/ATEMP/USSDNUPSimulater/bin/Public.key";
	private static String data = "1234";

	public static void main(String[] args) {

		try {
			RSAEncryption rsaObj = new RSAEncryption();

			// Encrypt Data using Public Key
			String encryptedData = rsaObj.encryptData(data);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Encrypt Data
	 * 
	 * @param data
	 * @throws IOException
	 */
	public String encryptData(String data) throws IOException {
		System.out.println("\n----------------ENCRYPTION STARTED------------");

		
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = null;
		try {
			PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
			Cipher cipher = Cipher.getInstance("RSA");
			
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			encryptedData = cipher.doFinal(dataToEncrypt);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("----------------ENCRYPTION COMPLETED------------");
		// System.out.println("BASE64Encoder().encode(encryptedData)========"+new
		// BASE64Encoder().encode(encryptedData));
		return new BASE64Encoder().encode(encryptedData);
	}

	/**
	 * read Public Key From File
	 * 
	 * @param fileName
	 * @return PublicKey
	 * @throws IOException
	 */
	public PublicKey readPublicKeyFromFile(String fileName) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(new File(fileName));
			ois = new ObjectInputStream(fis);

			BigInteger modulus = (BigInteger) ois.readObject();
			BigInteger exponent = (BigInteger) ois.readObject();

			// Get Public Key
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);
			
			return publicKey;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return null;
	}

}
