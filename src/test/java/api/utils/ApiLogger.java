package api.utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiLogger {

    private static final Logger logger =
            LogManager.getLogger(ApiLogger.class);

    private ApiLogger() {
        // Utility class
    }

    public static void logResult(
            String method,
            String endpoint,
            Response response,
            long responseTime) {

        int statusCode =
                response.getStatusCode();

        String result =
                (statusCode >= 200 && statusCode < 400)
                        ? "PASSED"
                        : "FAILED";

        if ("PASSED".equals(result)) {

            logger.info(
                    "API RESULT | {} | Method={} | Endpoint={} | Status={} | ResponseTime={} ms",
                    result,
                    method,
                    endpoint,
                    statusCode,
                    responseTime
            );

        } else {

            logger.error(
                    "API RESULT | {} | Method={} | Endpoint={} | Status={} | ResponseTime={} ms",
                    result,
                    method,
                    endpoint,
                    statusCode,
                    responseTime
            );
        }
    }
}