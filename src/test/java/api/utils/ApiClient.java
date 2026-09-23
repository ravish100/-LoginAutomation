package api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class ApiClient {

    private ApiClient() {
        // Utility class
    }

    public static Response get(String endpoint) {

        return RestAssured
                .given()
                .when()
                .get(endpoint);
    }

    public static Response post(
            String endpoint,
            String requestBody) {

        return jsonRequest()
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public static Response put(
            String endpoint,
            String requestBody) {

        return jsonRequest()
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public static Response patch(
            String endpoint,
            String requestBody) {

        return jsonRequest()
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    public static Response delete(String endpoint) {

        return RestAssured
                .given()
                .when()
                .delete(endpoint);
    }
    public static io.restassured.specification.RequestSpecification
    jsonRequest() {

        return RestAssured
                .given()
                .header(
                        "Content-Type",
                        "application/json"
                )
                .header(
                        "Accept",
                        "application/json"
                );
    }
}