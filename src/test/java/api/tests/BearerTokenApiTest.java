package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BearerTokenApiTest {

    @Test
    public void bearerTokenAuthenticationTest() {

        String token = "test-token";

        Response response =
                RestAssured
                        .given()
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .when()
                        .get(
                                "https://httpbin.org/bearer"
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

        boolean authenticated =
                response.jsonPath()
                        .getBoolean("authenticated");

        System.out.println(
                "Authenticated: "
                        + authenticated
        );

        Assert.assertTrue(
                authenticated,
                "Expected successful Bearer Token authentication"
        );
    }
}