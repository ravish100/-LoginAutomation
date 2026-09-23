package api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiTestDataReader {

    private static final Properties properties =
            new Properties();

    static {

        try (FileInputStream input =
                     new FileInputStream(
                             "src/test/resources/testdata/api-user-data.properties")) {

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load API test data file.",
                    e
            );
        }
    }

    private ApiTestDataReader() {
        // Utility class
    }

    public static String getProperty(String key) {

        String value =
                properties.getProperty(key);

        if (value == null) {

            throw new RuntimeException(
                    "API test data not found for key: "
                            + key
            );
        }

        return value.trim();
    }
}