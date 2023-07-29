package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
	public static String readProperty(String key, String site) {
		
		String value="";
		try (InputStream input = new FileInputStream("./src/test/resources/data/bundle."+site+"properties.properties")) {

				Properties prop = new Properties();
			prop.load(input);
			value = prop.getProperty(key);
		} catch (Exception e) {}
		return value;
	}
}
