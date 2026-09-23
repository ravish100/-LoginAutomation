package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationApiTest {

    @Test
    public void basicAuthenticationTest() {

        Response response =
                RestAssured
                        .given()
                        .auth()
                        .basic("postman", "password")
                        .when()
                        .get("https://postman-echo.com/basic-auth");

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
                "Expected successful Basic Authentication"
        );
    }
}