package com.example.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for JSONPlaceholder users page.
 * Used to verify that data created via API is correctly rendered in the UI.
 *
 * Target: https://jsonplaceholder.typicode.com — the /users endpoint
 * rendered as JSON in browser (we verify raw JSON content as a UI validation).
 *
 * In a real project, this would point to your app's user profile page.
 */
public class UserProfilePage extends BasePage {

    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";

    @FindBy(tagName = "pre")
    private WebElement jsonContent;

    @Step("UI — Open users page")
    public UserProfilePage open() {
        navigateTo(USERS_URL);
        return this;
    }

    @Step("UI — Open user by id: {id}")
    public UserProfilePage openUser(int id) {
        navigateTo(USERS_URL + "/" + id);
        return this;
    }

    @Step("UI — Get page content")
    public String getPageContent() {
        return getText(jsonContent);
    }

    @Step("UI — Check user name is visible: {name}")
    public boolean isNameVisible(String name) {
        return getPageContent().contains(name);
    }

    @Step("UI — Check email is visible: {email}")
    public boolean isEmailVisible(String email) {
        return getPageContent().contains(email);
    }

    @Step("UI — Check username is visible: {username}")
    public boolean isUsernameVisible(String username) {
        return getPageContent().contains(username);
    }

    public boolean isOnUserPage() {
        return getCurrentUrl().contains("jsonplaceholder.typicode.com/users");
    }
}
