package com.billdiary.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

@Component
public class SpringFxmlLoader {

	@Autowired
	private ConfigurableApplicationContext applicationcontext;

	/**
	 * This method is used for injecting the fxml bean into the spring bean context
	 * @param url
	 * @return
	 */
	public Object load(String url) {

		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
	        loader.setControllerFactory(applicationcontext::getBean);
			return loader.load();
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
	
	public ConfigurableApplicationContext getApplicationcontext() {
		return applicationcontext;
	}

	public void setApplicationcontext(ConfigurableApplicationContext applicationcontext) {
		this.applicationcontext = applicationcontext;
	}
}
