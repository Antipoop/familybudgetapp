package info.stepanoff.test.config;

import java.io.InputStream;
import java.util.Properties;

public class Props {
    private static Properties properties;

    public static Properties loadProperties() {

        if (properties != null){
            return properties;
        }

        Properties tempProperties = new Properties();
        tempProperties.setProperty("db.url","jdbc:postgresql://localhost:5432/postgres");
        tempProperties.setProperty("db.user","postgres");
        tempProperties.setProperty("db.password","test");

        properties = tempProperties;

        return properties;
    }
}
