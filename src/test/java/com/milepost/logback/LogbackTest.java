package com.milepost.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
	private static Logger log = LoggerFactory.getLogger(LogbackTest.class);

	@Test
	public void test() {
		for(int i=0; i<10; i++){
			log.trace(i + "======tracetracetracetracetracetracetracetracetracetracetracetracetracetracetrace");
			log.debug(i + "======debugdebugdebugdebugdebugdebugdebugdebugdebugdebugdebugdebugdebugdebugdebug");
			log.info(i + "======infoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfoinfo");
			log.warn(i + "======warnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarnwarn");
			log.error(i + "======中国errorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerror");
		}
	}
	
}
