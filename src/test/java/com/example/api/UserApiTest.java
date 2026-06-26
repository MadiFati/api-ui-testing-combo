package com.example.api;

import com.example.api.clients.UserApiClient;
import com.example.api.models.User;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pure API tests — no browser needed, ultra fast.
 * Covers: GET all, GET by id, POST, PUT, DELETE + negative cases.
 */
@Epic("API")
@Feature("User API")
@Tag("api")
class UserApiTest {

    private UserApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient = new UserApiClient();
    }

    @Test
    @DisplayName("GET /users — should return 10 users")
    @Story("Get all users")
    @Severity(SeverityLevel.CRITICAL)
    void getAllUsersShouldReturnTenUsers() {
        List<User> users = apiClient.getAllUsers();

        assertNotNull(users, "Users list should not be null");
        assertEquals(10, users.size(), "Should return exactly 10 users");
        assertTrue(users.stream().allMatch(u -> u.getId() > 0),
            "All users should have a valid id");
    }

    @Test
    @DisplayName("GET /users/1 — should return Leanne Graham")
    @Story("Get user by id")
    @Severity(SeverityLevel.CRITICAL)
    void getUserByIdShouldReturnCorrectUser() {
        User user = apiClient.getUserById(1);

        assertAll("User fields",
            () -> assertEquals(1, user.getId()),
            () -> assertEquals("Leanne Graham", user.getName()),
            () -> assertEquals("Sincere@april.biz", user.getEmail()),
            () -> assertNotNull(user.getUsername())
        );
    }

    @Test
    @DisplayName("POST /users — should create user and return id")
    @Story("Create user")
    @Severity(SeverityLevel.BLOCKER)
    void createUserShouldReturnNewId() {
        User newUser = new User("Fatima El Madini", "fati.dev", "fati@example.com");

        User created = apiClient.createUser(newUser);

        assertNotNull(created, "Created user should not be null");
        assertTrue(created.getId() > 0, "Created user should have an id");
        assertEquals("Fatima El Madini", created.getName());
        assertEquals("fati@example.com", created.getEmail());
    }

    @Test
    @DisplayName("PUT /users/1 — should update user name")
    @Story("Update user")
    @Severity(SeverityLevel.NORMAL)
    void updateUserShouldReturnUpdatedData() {
        User updated = new User("Updated Name", "updated.user", "updated@example.com");

        User result = apiClient.updateUser(1, updated);

        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    @DisplayName("DELETE /users/1 — should return 200")
    @Story("Delete user")
    @Severity(SeverityLevel.NORMAL)
    void deleteUserShouldReturn200() {
        // JSONPlaceholder simulates deletion (always returns 200 with empty body)
        var response = apiClient.deleteUser(1);
        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("GET /users/999 — non-existent user should return 404")
    @Story("Error handling")
    @Severity(SeverityLevel.NORMAL)
    void nonExistentUserShouldReturn404() {
        var response = apiClient.getUserRaw(999);
        assertEquals(404, response.statusCode(),
            "Non-existent user should return 404");
    }
}
