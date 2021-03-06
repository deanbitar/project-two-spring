package com.chord.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used to hash a string using the SHA-256 algorithm.
 * 
 * @author deanb
 *
 */
public class Hash {
	
	/**
	 * This method will return the SHA-256 hashed string of the parameter.
	 * 
	 * @param message - the string to be hashed
	 * @return String - the hashed string
	 */
	public static String sha256(String message) {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(message.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return message;
	}
}
