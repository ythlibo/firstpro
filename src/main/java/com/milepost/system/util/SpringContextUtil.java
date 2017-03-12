package com.milepost.system.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 实现了 ApplicationContextAware 接口的类，加上@Component后，应用启动时候被加载到spring的ioc容器中，
 * 并被调用 setApplicationContext 方法，初始化 applicationContext
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
	
	public SpringContextUtil() {
		if (logger.isDebugEnabled()) {
			logger.debug("SpringContextUtil'constructor...");
		}
	}
	
	//Spring应用上下文环境
	private static ApplicationContext applicationContext; 
	
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
    	return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }
}
