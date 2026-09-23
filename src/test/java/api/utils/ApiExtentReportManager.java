package api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ApiExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static synchronized ExtentReports getExtentReports() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            "test-output/api-extent-report.html"
                    );

            spark.config().setReportName("API Automation Report");
            spark.config().setDocumentTitle("API Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Framework", "TestNG + REST Assured");
            extent.setSystemInfo("Project", "LoginAutomation");
        }

        return extent;
    }

    public static void startTest(String testName) {

        ExtentTest extentTest =
                getExtentReports().createTest(testName);

        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void pass(String message) {
        if (test.get() != null) {
            test.get().pass(message);
        }
    }

    public static void fail(String message) {
        if (test.get() != null) {
            test.get().fail(message);
        }
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}