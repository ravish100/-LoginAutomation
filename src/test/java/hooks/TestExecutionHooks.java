package hooks;

import io.cucumber.java.BeforeAll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;

public class TestExecutionHooks {

    private static final Logger logger =
            LogManager.getLogger(TestExecutionHooks.class);

    @BeforeAll
    public static void logTestConfiguration() {

        logger.info("==================================================");
        logger.info("        TEST EXECUTION CONFIGURATION");
        logger.info("==================================================");

        logger.info(
                "Environment     : {}",
                ConfigReader.getProperty("environment")
        );

        logger.info(
                "Browser         : {}",
                ConfigReader.getProperty("browser")
        );

        logger.info(
                "Parallel Threads: {}",
                ConfigReader.getParallelThreads()
        );

        logger.info("==================================================");
    }
}