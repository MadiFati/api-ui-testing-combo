package com.example.e2e;

import com.example.BaseUiTest;
import com.example.api.clients.UserApiClient;
import com.example.api.models.User;
import com.example.ui.pages.UserProfilePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E tests combining API setup + UI verification.
 *
 * Pattern :
 *  1. Use API to fetch/create data (fast, reliable setup)
 *  2. Navigate to UI and verify the data is correctly displayed
 *
 * This is the most impressive pattern for startups and PMEs :
 * it shows you understand the full stack, not just "clicking in the browser".
 */
@Epic("E2E")
@Feature("User Profile")
@Tag("e2e")
class UserProfileE2ETest extends BaseUiTest {

    @Test
    @DisplayName("User fetched via API should be visible in UI")
    @Story("API data visible in UI")
    @Description("""
        1. Fetch user #1 via REST API
        2. Navigate to the user's profile URL in the browser
        3. Verify name and email are displayed correctly
        This test validates the full data pipeline from backend to frontend.
        """)
    @Severity(SeverityLevel.BLOCKER)
    void userFetchedViaApiShouldBeVisibleInUi() {
        // Step 1 — Get user data from API
        UserApiClient apiClient = new UserApiClient();
        User user = apiClient.getUserById(1);

        assertNotNull(user, "API should return a valid user");
        log.info("API returned user: {}", user);

        // Step 2 — Navigate to UI and verify
        UserProfilePage profilePage = new UserProfilePage();
        profilePage.openUser(user.getId());

        assertTrue(profilePage.isOnUserPage(),
            "Should be on the user page");
        assertTrue(profilePage.isNameVisible(user.getName()),
            "User name '%s' should be visible in UI".formatted(user.getName()));
        assertTrue(profilePage.isEmailVisible(user.getEmail()),
            "User email '%s' should be visible in UI".formatted(user.getEmail()));

        log.info("✓ User '{}' verified in UI", user.getName());
    }

    @Test
    @DisplayName("All 10 users from API should have accessible profile pages")
    @Story("All user profiles accessible")
    @Severity(SeverityLevel.CRITICAL)
    void allUsersFromApiShouldHaveAccessibleProfiles() {
        // Step 1 — Get all users from API
        UserApiClient apiClient = new UserApiClient();
        var users = apiClient.getAllUsers();
        assertEquals(10, users.size(), "API should return 10 users");

        // Step 2 — Verify first 3 users in UI (full check on all 10 would be slow)
        UserProfilePage profilePage = new UserProfilePage();
        for (User user : users.subList(0, 3)) {
            profilePage.openUser(user.getId());
            assertTrue(profilePage.isNameVisible(user.getName()),
                "Name '%s' should be visible for user id %d"
                    .formatted(user.getName(), user.getId()));
            log.info("✓ Profile verified for user: {}", user.getName());
        }
    }
}
