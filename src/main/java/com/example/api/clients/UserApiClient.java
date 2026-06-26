package com.example.api.clients;

import com.example.api.models.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * API client for user operations.
 * Uses JSONPlaceholder (https://jsonplaceholder.typicode.com) as public test API.
 *
 * Pattern : each method returns the domain object, not the raw Response.
 * Low-level assertions (status codes) stay here; business assertions go in tests.
 */
public class UserApiClient {

    private static final Logger log = LoggerFactory.getLogger(UserApiClient.class);
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public UserApiClient() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // ── CRUD operations ───────────────────────────────────────────────────────

    @Step("API — Get all users")
    public List<User> getAllUsers() {
        log.info("GET /users");
        return given()
            .contentType(ContentType.JSON)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList(".", User.class);
    }

    @Step("API — Get user by id: {id}")
    public User getUserById(int id) {
        log.info("GET /users/{}", id);
        return given()
            .contentType(ContentType.JSON)
            .pathParam("id", id)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .extract()
            .as(User.class);
    }

    @Step("API — Create user: {user.name}")
    public User createUser(User user) {
        log.info("POST /users — {}", user);
        // JSONPlaceholder simulates creation (returns 201 with generated id)
        return given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .extract()
            .as(User.class);
    }

    @Step("API — Update user id: {id}")
    public User updateUser(int id, User user) {
        log.info("PUT /users/{}", id);
        return given()
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .body(user)
        .when()
            .put("/users/{id}")
        .then()
            .statusCode(200)
            .extract()
            .as(User.class);
    }

    @Step("API — Delete user id: {id}")
    public Response deleteUser(int id) {
        log.info("DELETE /users/{}", id);
        return given()
            .pathParam("id", id)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(200)
            .extract()
            .response();
    }

    // ── Raw response (for negative tests) ────────────────────────────────────

    @Step("API — Get user raw response id: {id}")
    public Response getUserRaw(int id) {
        return given()
            .contentType(ContentType.JSON)
            .pathParam("id", id)
        .when()
            .get("/users/{id}")
        .then()
            .extract()
            .response();
    }
}
