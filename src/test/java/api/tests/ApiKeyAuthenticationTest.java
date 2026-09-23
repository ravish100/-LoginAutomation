package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiKeyAuthenticationTest {

    @Test
    public void apiKeyAuthenticationTest() {

        String apiKey = "test-api-key";

        Response response =
                RestAssured
                        .given()
                        .header(
                                "X-API-Key",
                                apiKey
                        )
                        .when()
                        .get(
                                "https://httpbin.org/anything"
                        );

        System.out.println(
                "Status Code: "
                        + response.getStatusCode()
        );

        System.out.println(
                "Response Body:\n"
                        + response.getBody().asPrettyString()
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        String returnedApiKey =
                response.jsonPath()
                        .getString("headers.X-Api-Key");

        System.out.println(
                "Returned API Key: "
                        + returnedApiKey
        );

        Assert.assertEquals(
                returnedApiKey,
                apiKey,
                "API key was not sent correctly"
        );
    }
    @Test
    public void apiKeyQueryParameterTest() {

        String apiKey = "test-api-key";

        Response response =
                RestAssured
                        .given()
                        .queryParam(
                                "api_key",
                                apiKey
                        )
                        .when()
                        .get(
                                "https://httpbin.org/anything"
                        );

        System.out.println(
                "Status Code: "
                        + response.getStatusCode()
        );

        System.out.println(
                "Response Body:\n"
                        + response.getBody().asPrettyString()
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        String returnedApiKey =
                response.jsonPath()
                        .getString("args.api_key");

        System.out.println(
                "Returned API Key: "
                        + returnedApiKey
        );

        Assert.assertEquals(
                returnedApiKey,
                apiKey,
                "API key query parameter was not sent correctly"
        );
    }
}