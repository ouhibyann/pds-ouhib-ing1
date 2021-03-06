package properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	
	public static Properties load(String configFile) {

		Properties properties = new Properties();

		FileInputStream input;
		try {
			input = new FileInputStream(configFile);

			properties.load(input);
			return properties;

		} catch (FileNotFoundException e) {
			
			Properties prop = new Properties() ;
			prop.setProperty("driver", "org.postgresql.Driver");
			prop.setProperty("url", "jdbc:postgresql://10.10.10.220:5432/phygitmall");
			prop.setProperty("user", "postgres");
			prop.setProperty("password", "123");
			prop.setProperty("port",Integer.toString(5432)) ;
			prop.setProperty("max", Integer.toString(20));
			
			return prop;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;

	}
}
