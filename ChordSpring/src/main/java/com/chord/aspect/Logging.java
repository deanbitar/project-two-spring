package com.chord.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component("aspect")
@Aspect
public class Logging {

	static final Logger LOGGER = Logger.getLogger(Logging.class);
	
	@Before("execution(* createNewUser(..))")
	public void logRequests() {
		LOGGER.info("New user signed up for new account at Chordination!");
	}
}
