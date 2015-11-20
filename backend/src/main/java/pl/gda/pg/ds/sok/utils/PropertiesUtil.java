package pl.gda.pg.ds.sok.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	private static final String FILE = "config.properties";
	
	public static String getProperty(String propertyName) {
	
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = loader.getResourceAsStream(FILE);
		if (inputStream != null) {
			Properties properties = new Properties();
			try {
	            properties.load(inputStream);
	            return new String(properties.getProperty(propertyName).getBytes("ISO-8859-1"), "UTF-8");
	        } catch (IOException e) {
	        	logger.fatal("Problem reading properties file", e);
	        }
		} else {
			logger.fatal("Properties file not found");
		}
		return null;
	}
}
