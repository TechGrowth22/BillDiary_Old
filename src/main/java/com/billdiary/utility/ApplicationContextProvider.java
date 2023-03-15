package com.billdiary.utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

@Component
public class ApplicationContextProvider {

	@Autowired
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



 public ApplicationContext getContext() {
        return applicationContext;
    }
 
 public Object load(String url) {

		try (InputStream fxmlStream = getClass().getResourceAsStream(url)) {
			System.err.println(getClass().getResourceAsStream(url));
			FXMLLoader loader = new FXMLLoader();
			URL location = getClass().getResource(url);
			loader.setLocation(location);
			//loader.setResources(bundle);
			//loader.setResources(resources);
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> clazz) {
					return getContext().getBean(clazz);
				}
			});
			/**
			 * Here we set the Locale (For language change)
			 */
			
			/*if(loc==null)
			{
				loc = new Locale("en");
			}
			System.out.println(loc.getDisplayLanguage());
			setLoc(loc);
			MessageSourceResourceBundle bundle=getBundle();
			setBundle(bundle);
			loader.setResources(getBundle());*/
			return loader.load(fxmlStream);
		} catch (IOException ioException) {
			ioException.printStackTrace();
			throw new RuntimeException(ioException);
		}
	}
}
