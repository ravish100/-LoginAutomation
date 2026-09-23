package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;


import java.time.LocalDateTime;

public class TestExecutionListener implements IExecutionListener {

    private static final Logger logger =
            LogManager.getLogger(TestExecutionListener.class);

    private LocalDateTime startTime;

    @Override
    public void onExecutionStart() {

        startTime = LocalDateTime.now();


        logger.info("==================================================");
        logger.info("TEST EXECUTION STARTED");
        logger.info("Start Time: {}", startTime);
        logger.info("==================================================");
    }

    @Override
    public void onExecutionFinish() {

        LocalDateTime endTime = LocalDateTime.now();

        logger.info("==================================================");
        logger.info("TEST EXECUTION FINISHED");
        logger.info("End Time: {}", endTime);

        if (startTime != null) {
            long durationSeconds =
                    java.time.Duration
                            .between(startTime, endTime)
                            .getSeconds();

            logger.info(
                    "Total Execution Time: {} seconds",
                    durationSeconds
            );
        }

        logger.info("==================================================");
    }
}