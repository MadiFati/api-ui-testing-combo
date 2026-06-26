package com.example;

import com.example.ui.utils.DriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for tests that need a browser (UI or E2E).
 * Pure API tests should NOT extend this — they don't need a driver.
 */
public abstract class BaseUiTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    void setUp(TestInfo info) {
        log.info("▶ Starting: {}", info.getDisplayName());
        DriverManager.initDriver();
    }

    @AfterEach
    void tearDown(TestInfo info) {
        try {
            takeScreenshot();
        } finally {
            DriverManager.quitDriver();
        }
        log.info("■ Finished: {}", info.getDisplayName());
    }

    @Attachment(value = "Screenshot", type = "image/png")
    protected byte[] takeScreenshot() {
        try {
            return ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
