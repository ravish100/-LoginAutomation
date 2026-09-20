package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {

        try (FileInputStream fileInputStream =
                     new FileInputStream(
                             "src/test/resources/config/config.properties")) {

            properties.load(fileInputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load config.properties", e
            );
        }
    }

    public static String getProperty(String key) {

        // Check Maven/system property first
        String systemProperty = System.getProperty(key);

        if (systemProperty != null &&
                !systemProperty.trim().isEmpty()) {

            return systemProperty.trim();
        }

        // Otherwise use config.properties
        String value = properties.getProperty(key);

        if (value == null) {

            throw new RuntimeException(
                    "Configuration property not found: " + key
            );
        }

        return value.trim();
    }
    public static String getEnvironmentUrl() {

        String environment = getProperty("environment")
                .toLowerCase();

        String urlKey = environment + ".url";

        if (!properties.containsKey(urlKey)
                && System.getProperty(urlKey) == null) {

            throw new RuntimeException(
                    "Invalid environment: " + environment
                            + ". Expected one of: qa, staging, prod"
            );
        }

        return getProperty(urlKey);
    }
    public static int getParallelThreads() {

        String threads = System.getProperty("parallelThreads");

        if (threads == null || threads.trim().isEmpty()) {
            threads = properties.getProperty("parallelThreads", "2");
        }

        try {

            int threadCount = Integer.parseInt(threads.trim());

            if (threadCount < 1) {
                throw new RuntimeException(
                        "parallelThreads must be greater than 0. Current value: "
                                + threadCount
                );
            }

            return threadCount;

        } catch (NumberFormatException e) {

            throw new RuntimeException(
                    "Invalid parallelThreads value: " + threads
                            + ". Expected a positive integer.",
                    e
            );
        }
    }
}