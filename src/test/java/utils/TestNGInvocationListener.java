package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestNGInvocationListener
        implements IInvokedMethodListener {

    private static final Logger logger =
            LogManager.getLogger(TestNGInvocationListener.class);

    @Override
    public void beforeInvocation(
            IInvokedMethod method,
            ITestResult testResult) {

        logger.debug(
                "TESTNG INVOCATION START | Method={} | Configuration={} | Thread={}",
                method.getTestMethod().getMethodName(),
                method.isConfigurationMethod(),
                Thread.currentThread().getName()
        );
    }

    @Override
    public void afterInvocation(
            IInvokedMethod method,
            ITestResult testResult) {

        logger.debug(
                "TESTNG INVOCATION END | Method={} | Configuration={} | Status={} | Thread={}",
                method.getTestMethod().getMethodName(),
                method.isConfigurationMethod(),
                getStatus(testResult),
                Thread.currentThread().getName()
        );
    }

    private String getStatus(ITestResult result) {

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:
                return "PASSED";

            case ITestResult.FAILURE:
                return "FAILED";

            case ITestResult.SKIP:
                return "SKIPPED";

            default:
                return "UNKNOWN";
        }
    }
}