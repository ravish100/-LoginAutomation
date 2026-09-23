package api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthManager {

    private AuthManager() {
        // Utility class
    }

    public static String getAccessToken() {

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

        if (response.getStatusCode() != 200) {

            throw new RuntimeException(
                    "Authentication request failed. "
                            + "Status code: "
                            + response.getStatusCode()
            );
        }

        String accessToken =
                response.jsonPath()
                        .getString("json.access_token");

        if (accessToken == null
                || accessToken.isBlank()) {

            throw new RuntimeException(
                    "Authentication succeeded but access token "
                            + "was missing from the response."
            );
        }

        return accessToken;
    }
    public static io.restassured.specification.RequestSpecification
    authenticatedRequest() {

        String accessToken = getAccessToken();

        return RestAssured
                .given()
                .header(
                        "Authorization",
                        "Bearer " + accessToken
                );
    }
}