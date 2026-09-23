package api.utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiExtentReportListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        String className =
                result.getTestClass()
                        .getRealClass()
                        .getSimpleName();

        String methodName =
                result.getMethod()
                        .getMethodName();

        String businessName;

        if ("DataDrivenUserApiTest".equals(className)
                && "getUserDataDrivenTest".equals(methodName)) {

            Object[] parameters = result.getParameters();

            if (parameters.length >= 2) {

                int userId = (Integer) parameters[0];
                String expectedName = (String) parameters[1];

                businessName =
                        "Verify User Data - ID "
                                + userId
                                + " - "
                                + expectedName;

            } else {
                businessName = "Verify User Data";
            }

        } else {

            businessName =
                    getBusinessName(className, methodName);
        }

        ApiExtentReportManager.startTest(businessName);

        ApiExtentReportManager.pass(
                "Test Class: " + className
                        + " | Test Method: " + methodName
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ApiExtentReportManager.pass(
                "Test Status: PASSED"
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String message =
                result.getThrowable() != null
                        ? result.getThrowable().getMessage()
                        : "Test failed";

        ApiExtentReportManager.fail(
                "Test Status: FAILED | Reason: " + message
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ApiExtentReportManager.fail(
                "Test Status: SKIPPED"
        );
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        ApiExtentReportManager.flush();
    }

    private String getBusinessName(
            String className,
            String methodName) {

        if ("ApiKeyAuthenticationTest".equals(className)) {
            return "Verify API access using API key authentication";
        }

        if ("AuthenticationApiTest".equals(className)) {
            return "Verify API authentication flow";
        }

        if ("BearerTokenApiTest".equals(className)) {
            return "Verify API access using bearer token";
        }

        if ("TokenAuthenticationTest".equals(className)) {
            return "Verify API access using token authentication";
        }

        if ("DataDrivenUserApiTest".equals(className)) {
            return "Verify user data for multiple users";
        }

        if ("UserApiTest".equals(className)) {
            return getUserApiBusinessName(methodName);
        }

        return className + " - " + methodName;
    }

    private String getUserApiBusinessName(String methodName) {

        if (methodName.toLowerCase().contains("get")) {
            return "Verify user details can be retrieved successfully";
        }

        if (methodName.toLowerCase().contains("post")) {
            return "Verify a new user can be created successfully";
        }

        if (methodName.toLowerCase().contains("put")) {
            return "Verify user details can be updated successfully";
        }

        if (methodName.toLowerCase().contains("patch")) {
            return "Verify user details can be partially updated successfully";
        }

        if (methodName.toLowerCase().contains("delete")) {
            return "Verify a user can be deleted successfully";
        }

        return "Verify user API operation: " + methodName;
    }
}

