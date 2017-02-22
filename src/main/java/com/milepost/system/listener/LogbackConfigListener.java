package com.milepost.system.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * 启动slf4j的监听器
 * @author HRF
 */
public class LogbackConfigListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(LogbackConfigListener.class);

	public void contextInitialized(ServletContextEvent event) {
		String logbackConfigLocation = event.getServletContext().getInitParameter("logbackConfigLocation");
		String fileRealPath = event.getServletContext().getRealPath(logbackConfigLocation);
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			loggerContext.reset();
			JoranConfigurator joranConfigurator = new JoranConfigurator();
			joranConfigurator.setContext(loggerContext);
			joranConfigurator.doConfigure(fileRealPath);
			log.debug("loaded slf4j configure file from {}", fileRealPath);
		} catch (JoranException e) { 
			e.printStackTrace();
			log.error("can't loading slf4j configure file from {}" + fileRealPath, e);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}