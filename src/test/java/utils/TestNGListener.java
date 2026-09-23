package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TestNGListener implements ITestListener {

    private static final Logger logger =
            LogManager.getLogger(TestNGListener.class);

    private final ConcurrentMap<String, Long> startTimes =
            new ConcurrentHashMap<>();

    @Override
    public void onTestStart(ITestResult result) {

        String testName = getTestName(result);

        startTimes.put(
                getKey(result),
                System.currentTimeMillis()
        );

        logger.info("--------------------------------------------------");
        logger.info(
                "TEST STARTED | Test={} | Thread={}",
                testName,
                Thread.currentThread().getName()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        logResult(
                result,
                "PASSED"
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        logger.error(
                "TEST FAILED | Test={} | Thread={}",
                getTestName(result),
                Thread.currentThread().getName()
        );

        Throwable throwable = result.getThrowable();

        if (throwable != null) {
            logger.error(
                    "Failure Exception: {}",
                    throwable.getMessage()
            );
        }

        logResult(
                result,
                "FAILED"
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logResult(
                result,
                "SKIPPED"
        );
    }

    @Override
    public void onStart(ITestContext context) {

        logger.info(
                "TESTNG TEST STARTED | Context={}",
                context.getName()
        );
    }

    @Override
    public void onFinish(ITestContext context) {

        logger.info(
                "TESTNG TEST FINISHED | Context={}",
                context.getName()
        );

        logger.info(
                "TESTNG SUMMARY | Passed={} | Failed={} | Skipped={}",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size()
        );
    }

    private void logResult(
            ITestResult result,
            String status) {

        String key = getKey(result);

        Long startTime = startTimes.remove(key);

        long duration = 0;

        if (startTime != null) {
            duration =
                    System.currentTimeMillis() - startTime;
        }

        logger.info(
                "TEST {} | Test={} | Thread={} | Duration={} ms",
                status,
                getTestName(result),
                Thread.currentThread().getName(),
                duration
        );
    }

    private String getTestName(ITestResult result) {

        if (result.getTestName() != null) {
            return result.getTestName();
        }

        return result.getMethod().getMethodName();
    }

    private String getKey(ITestResult result) {

        return result.getTestClass().getName()
                + "."
                + result.getMethod().getMethodName()
                + "-"
                + result.getStartMillis();
    }
}