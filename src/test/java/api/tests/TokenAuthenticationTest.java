package api.tests;

import api.utils.AuthManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TokenAuthenticationTest {

    @Test
    public void tokenAcquisitionAndReuseTest() {

        // Step 1: Simulate authentication request
        String requestBody =
                """
                {
                    "username": "testuser",
                    "password": "testpassword"
                }
                """;

        Response authResponse =
                RestAssured
                        .given()
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .body(requestBody)
                        .when()
                        .post(
                                "https://httpbin.org/anything"
                        );

        System.out.println(
                "Authentication Status: "
                        + authResponse.getStatusCode()
        );

        Assert.assertEquals(
                authResponse.getStatusCode(),
                200,
                "Authentication request failed"
        );

        // Step 2: Simulate token returned by authentication server
        String accessToken =
                AuthManager.getAccessToken();

        System.out.println(
                "Access token obtained successfully."
        );

        Assert.assertNotNull(
                accessToken,
                "Access token should not be null"
        );

        // Step 3: Reuse token for protected API
        Response protectedResponse =
                AuthManager
                        .authenticatedRequest()
                        .when()
                        .get(
                                "https://httpbin.org/bearer"
                        );
        System.out.println(
                "Protected API Status: "
                        + protectedResponse.getStatusCode()
        );

        System.out.println(
                "Protected API Response:\n"
                        + protectedResponse
                        .getBody()
                        .asPrettyString()
        );

        // Step 4: Validate protected API
        Assert.assertEquals(
                protectedResponse.getStatusCode(),
                200,
                "Expected protected API status 200"
        );

        boolean authenticated =
                protectedResponse
                        .jsonPath()
                        .getBoolean("authenticated");

        Assert.assertTrue(
                authenticated,
                "Expected successful Bearer authentication"
        );

        System.out.println(
                "Token successfully reused for protected API."
        );
    }
    @Test
    public void extractAccessTokenTest() {

        String mockTokenResponse =
                """
                {
                    "access_token": "test-access-token-123",
                    "token_type": "Bearer",
                    "expires_in": 3600
                }
                """;

        Response response =
                RestAssured
                        .given()
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .body(mockTokenResponse)
                        .when()
                        .post(
                                "https://httpbin.org/anything"
                        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        String accessToken =
                response.jsonPath()
                        .getString("json.access_token");

        String tokenType =
                response.jsonPath()
                        .getString("json.token_type");

        int expiresIn =
                response.jsonPath()
                        .getInt("json.expires_in");

        System.out.println(
                "Access token extracted successfully."
        );

        System.out.println(
                "Token Type: "
                        + tokenType
        );

        System.out.println(
                "Expires In: "
                        + expiresIn
                        + " seconds"
        );

        Assert.assertEquals(
                accessToken,
                "test-access-token-123",
                "Incorrect access token"
        );

        Assert.assertEquals(
                tokenType,
                "Bearer",
                "Incorrect token type"
        );

        Assert.assertEquals(
                expiresIn,
                3600,
                "Incorrect token expiration"
        );
    }
}