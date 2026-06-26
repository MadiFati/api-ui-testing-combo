package com.example.ui.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe WebDriver manager for JUnit 5 parallel execution.
 */
public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            initDriver();
        }
        return driverThread.get();
    }

    public static void initDriver() {
        boolean headless = Boolean.parseBoolean(
            System.getProperty("HEADLESS", System.getenv("HEADLESS") != null
                ? System.getenv("HEADLESS") : "false"));

        log.info("Initializing Chrome driver (headless={})", headless);
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
        if (headless) options.addArguments("--headless=new");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driverThread.set(driver);
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
            log.info("Driver closed");
        }
    }
}
