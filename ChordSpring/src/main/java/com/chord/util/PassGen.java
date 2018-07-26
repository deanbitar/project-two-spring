package com.chord.util;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * This class is used to generate a temporary password of random characters.
 * 
 * @author Ezzdean Bietar
 *
 */
public class PassGen {
	
	/**
	 * Generates and returns a String with randomly generated characters.
	 * 
	 * @return String - the randomly generated string
	 */
	public static String generateTempPass() {
		
		byte[] array = new byte[50];
        new Random().nextBytes(array);
        
        return new String(array, Charset.forName("UTF-8")).replaceAll("[^A-Za-z0-9]", "");
	}
}
