package com.gcs.ophwc.services.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContext implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext=arg0;
	}
	
	/**
	 * @return the context
	 */
	public static ApplicationContext getApplicationContext() {		
		return applicationContext;
	}
}
