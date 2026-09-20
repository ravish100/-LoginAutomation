package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {

    private static final Properties properties =
            new Properties();

    static {

        try (InputStream input =
                     TestDataReader.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "testdata/login-data.properties"
                             )) {

            if (input == null) {

                throw new RuntimeException(
                        "Unable to find login-data.properties "
                                + "in testdata folder."
                );
            }

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load login test data.",
                    e
            );
        }
    }


    public static String getValue(String key) {

        String value =
                properties.getProperty(key);

        if (value == null) {

            throw new RuntimeException(
                    "Test data not found for key: "
                            + key
            );
        }


        if (value.equals("${VALID_PASSWORD}")) {

            String password =
                    System.getenv("VALID_PASSWORD");

            if (password == null || password.isBlank()) {

                throw new RuntimeException(
                        "Environment variable "
                                + "VALID_PASSWORD is not set."
                );
            }

            return password;
        }

        return value;
    }
}