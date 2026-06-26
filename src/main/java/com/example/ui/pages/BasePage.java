package com.example.ui.pages;

import com.example.ui.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    protected void navigateTo(String url) {
        log.info("Navigating to {}", url);
        driver.get(url);
    }

    protected void click(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();
    }

    protected void type(WebElement el, String text) {
        wait.until(ExpectedConditions.visibilityOf(el));
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(WebElement el) {
        return wait.until(ExpectedConditions.visibilityOf(el)).getText();
    }

    protected boolean isPresent(By locator) {
        try { return driver.findElement(locator).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    protected void waitForUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public String getCurrentUrl() { return driver.getCurrentUrl(); }
}
