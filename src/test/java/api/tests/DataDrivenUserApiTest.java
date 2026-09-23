package api.tests;

import api.utils.ApiClient;
import api.utils.ApiLogger;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.utils.ApiTestDataReader;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import api.utils.ApiExtentReportManager;

public class DataDrivenUserApiTest {

//    @DataProvider(name = "userData")
//    public Object[][] userData() {
//
//        return new Object[][]{
//                {1, "Leanne Graham"},
//                {2, "Ervin Howell"},
//                {3, "Clementine Bauch"}
//        };
//    }

    @DataProvider(name = "userData")
    public Object[][] userData() {

        return new Object[][]{
                {
                        Integer.parseInt(
                                ApiTestDataReader.getProperty("user1.id")
                        ),
                        ApiTestDataReader.getProperty("user1.name")
                },
                {
                        Integer.parseInt(
                                ApiTestDataReader.getProperty("user2.id")
                        ),
                        ApiTestDataReader.getProperty("user2.name")
                },
                {
                        Integer.parseInt(
                                ApiTestDataReader.getProperty("user3.id")
                        ),
                        ApiTestDataReader.getProperty("user3.name")
                }
        };
    }
    @BeforeMethod
    public void startApiTest(ITestResult result) {
        ApiExtentReportManager.startTest(
                result.getMethod().getMethodName()
        );
    }

    @AfterSuite
    public void flushApiReport() {
        ApiExtentReportManager.flush();
    }
    @Test(dataProvider = "userData")
    public void getUserDataDrivenTest(
            int userId,
            String expectedName) {

        String endpoint =
                "https://jsonplaceholder.typicode.com/users/"
                        + userId;

        long startTime =
                System.currentTimeMillis();

        Response response =
                ApiClient.get(endpoint);

        long responseTime =
                System.currentTimeMillis()
                        - startTime;

        ApiLogger.logResult(
                "GET",
                endpoint,
                response,
                responseTime
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        ApiExtentReportManager.pass(
                "HTTP Status: " + response.getStatusCode()
        );

        String actualName =
                response.jsonPath()
                        .getString("name");

        Assert.assertEquals(
                actualName,
                expectedName,
                "User name does not match expected value"
        );
        ApiExtentReportManager.pass(
                "User ID: " + userId
                        + " | Expected Name: " + expectedName
                        + " | Actual Name: " + actualName
        );

        System.out.println(
                "User ID: " + userId
                        + " | Name: " + actualName
        );
    }
}