package com.chord.util;

import java.nio.charset.Charset;
import java.util.Random;

public class PassGen {
	
	public static String generateTempPass() {
		
		byte[] array = new byte[50];
        new Random().nextBytes(array);
        
        return new String(array, Charset.forName("UTF-8")).replaceAll("[^A-Za-z0-9]", "");
	}
}
