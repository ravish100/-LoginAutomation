package api.tests;

import api.utils.ApiClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import api.models.User;
import api.utils.ApiLogger;

public class UserApiTest {

    @Test
    public void getUserTest() {

        String endpoint =
                "https://jsonplaceholder.typicode.com/users/1";

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
        System.out.println(
                "Status Code: "
                        + response.getStatusCode()
        );

        System.out.println(
                "Response Body:\n"
                        + response.getBody().asPrettyString()
        );

        // Status code validation
        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        // Nested JSONPath validations

        String city =
                response.jsonPath().getString("address.city");

        String latitude =
                response.jsonPath().getString("address.geo.lat");

        String longitude =
                response.jsonPath().getString("address.geo.lng");

        String companyName =
                response.jsonPath().getString("company.name");

        System.out.println("City: " + city);
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Company: " + companyName);

        Assert.assertEquals(
                city,
                "Gwenborough",
                "Incorrect city"
        );

        Assert.assertEquals(
                latitude,
                "-37.3159",
                "Incorrect latitude"
        );

        Assert.assertEquals(
                longitude,
                "81.1496",
                "Incorrect longitude"
        );

        Assert.assertEquals(
                companyName,
                "Romaguera-Crona",
                "Incorrect company name"
        );
    }
    @Test
    public void getUserByUsernameTest() {

        Response response =
                RestAssured
                        .given()
                        .queryParam("username", "Bret")
                        .when()
                        .get("https://jsonplaceholder.typicode.com/users");

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

        String username =
                response.jsonPath()
                        .getString("[0].username");

        String name =
                response.jsonPath()
                        .getString("[0].name");

        System.out.println("Username: " + username);
        System.out.println("Name: " + name);

        Assert.assertEquals(
                username,
                "Bret",
                "Incorrect username"
        );

        Assert.assertEquals(
                name,
                "Leanne Graham",
                "Incorrect user"
        );
    }
    @Test
    public void getUserWithHeadersTest() {

        Response response =
                RestAssured
                        .given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .when()
                        .get("https://jsonplaceholder.typicode.com/users/1");

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

        String contentType =
                response.getHeader("Content-Type");

        System.out.println(
                "Response Content-Type: "
                        + contentType
        );

        Assert.assertTrue(
                contentType.contains("application/json"),
                "Expected JSON response"
        );
    }
    @Test
    public void createUserTest() {

        String requestBody =
                """
                {
                    "name": "Ravish Malik",
                    "username": "ravish",
                    "email": "ravish@example.com"
                }
                """;

        String endpoint =
                "https://jsonplaceholder.typicode.com/users";

        long startTime =
                System.currentTimeMillis();

        Response response =
                ApiClient.post(
                        endpoint,
                        requestBody
                );

        long responseTime =
                System.currentTimeMillis()
                        - startTime;

        ApiLogger.logResult(
                "POST",
                endpoint,
                response,
                responseTime
        );

        Assert.assertEquals(
                response.getStatusCode(),
                201,
                "Expected HTTP status 201"
        );

        String name =
                response.jsonPath().getString("name");

        String username =
                response.jsonPath().getString("username");

        String email =
                response.jsonPath().getString("email");

        System.out.println("Created Name: " + name);
        System.out.println("Created Username: " + username);
        System.out.println("Created Email: " + email);

        Assert.assertEquals(
                name,
                "Ravish Malik",
                "Incorrect name"
        );

        Assert.assertEquals(
                username,
                "ravish",
                "Incorrect username"
        );

        Assert.assertEquals(
                email,
                "ravish@example.com",
                "Incorrect email"
        );
    }
    @Test
    public void updateUserTest() {

        String requestBody =
                """
                {
                    "name": "Ravish Updated",
                    "username": "ravishupdated",
                    "email": "ravish.updated@example.com"
                }
                """;

        String endpoint =
                "https://jsonplaceholder.typicode.com/users/1";

        long startTime =
                System.currentTimeMillis();

        Response response =
                ApiClient.put(
                        endpoint,
                        requestBody
                );

        long responseTime =
                System.currentTimeMillis()
                        - startTime;

        ApiLogger.logResult(
                "PUT",
                endpoint,
                response,
                responseTime
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        String name =
                response.jsonPath().getString("name");

        String username =
                response.jsonPath().getString("username");

        String email =
                response.jsonPath().getString("email");

        System.out.println("Updated Name: " + name);
        System.out.println("Updated Username: " + username);
        System.out.println("Updated Email: " + email);

        Assert.assertEquals(
                name,
                "Ravish Updated",
                "Incorrect updated name"
        );

        Assert.assertEquals(
                username,
                "ravishupdated",
                "Incorrect updated username"
        );

        Assert.assertEquals(
                email,
                "ravish.updated@example.com",
                "Incorrect updated email"
        );
    }
    @Test
    public void patchUserTest() {

        String requestBody =
                """
                {
                    "email": "ravish.patch@example.com"
                }
                """;

        String endpoint =
                "https://jsonplaceholder.typicode.com/users/1";

        long startTime =
                System.currentTimeMillis();

        Response response =
                ApiClient.patch(
                        endpoint,
                        requestBody
                );

        long responseTime =
                System.currentTimeMillis()
                        - startTime;

        ApiLogger.logResult(
                "PATCH",
                endpoint,
                response,
                responseTime
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        String email =
                response.jsonPath().getString("email");

        System.out.println(
                "Patched Email: " + email
        );

        Assert.assertEquals(
                email,
                "ravish.patch@example.com",
                "Incorrect patched email"
        );
    }
    @Test
    public void deleteUserTest() {

        String endpoint =
                "https://jsonplaceholder.typicode.com/users/1";

        long startTime =
                System.currentTimeMillis();

        Response response =
                ApiClient.delete(endpoint);

        long responseTime =
                System.currentTimeMillis()
                        - startTime;

        ApiLogger.logResult(
                "DELETE",
                endpoint,
                response,
                responseTime
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );
    }
    @Test
    public void createUserWithPojoTest() {

        User user =
                new User(
                        "Ravish Malik",
                        "ravish",
                        "ravish.pojo@example.com"
                );

        Response response =
                RestAssured
                        .given()
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .body(user)
                        .when()
                        .post(
                                "https://jsonplaceholder.typicode.com/users"
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
                201,
                "Expected HTTP status 201"
        );

        String name =
                response.jsonPath()
                        .getString("name");

        String username =
                response.jsonPath()
                        .getString("username");

        String email =
                response.jsonPath()
                        .getString("email");

        Assert.assertEquals(
                name,
                "Ravish Malik"
        );

        Assert.assertEquals(
                username,
                "ravish"
        );

        Assert.assertEquals(
                email,
                "ravish.pojo@example.com"
        );
    }

    @Test
    public void getUserWithPojoTest() {

        Response response =
                RestAssured
                        .given()
                        .when()
                        .get(
                                "https://jsonplaceholder.typicode.com/users/1"
                        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        User user =
                response.as(User.class);

        System.out.println(
                "User ID: " + user.getId()
        );

        System.out.println(
                "Name: " + user.getName()
        );

        System.out.println(
                "Username: " + user.getUsername()
        );

        System.out.println(
                "Email: " + user.getEmail()
        );

        Assert.assertEquals(
                user.getId(),
                1,
                "Incorrect user ID"
        );

        Assert.assertEquals(
                user.getName(),
                "Leanne Graham",
                "Incorrect name"
        );

        Assert.assertEquals(
                user.getUsername(),
                "Bret",
                "Incorrect username"
        );

        Assert.assertEquals(
                user.getEmail(),
                "Sincere@april.biz",
                "Incorrect email"
        );
        String city =
                user.getAddress().getCity();

        String latitude =
                user.getAddress()
                        .getGeo()
                        .getLat();

        String longitude =
                user.getAddress()
                        .getGeo()
                        .getLng();

        String companyName =
                user.getCompany().getName();

        System.out.println("City: " + city);
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Company: " + companyName);

        Assert.assertEquals(
                city,
                "Gwenborough",
                "Incorrect city"
        );

        Assert.assertEquals(
                latitude,
                "-37.3159",
                "Incorrect latitude"
        );

        Assert.assertEquals(
                longitude,
                "81.1496",
                "Incorrect longitude"
        );

        Assert.assertEquals(
                companyName,
                "Romaguera-Crona",
                "Incorrect company name"
        );
    }
    @Test
    public void validateUserSchemaTest() {

        Response response =
                RestAssured
                        .given()
                        .when()
                        .get(
                                "https://jsonplaceholder.typicode.com/users/1"
                        );

        System.out.println(
                "Status Code: "
                        + response.getStatusCode()
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status 200"
        );

        response.then()
                .assertThat()
                .body(
                        io.restassured.module.jsv.JsonSchemaValidator
                                .matchesJsonSchemaInClasspath(
                                        "schemas/user-schema.json"
                                )
                );

        System.out.println(
                "JSON Schema validation passed."
        );
    }
}