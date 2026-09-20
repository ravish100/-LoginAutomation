package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static WebDriver initializeDriver() {

        String browser = ConfigReader.getProperty("browser");

        WebDriver webDriver;

        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--disable-notifications");

            // Disable Chrome password manager UI
            options.addArguments("--disable-features=PasswordLeakDetection");

            options.setExperimentalOption(
                    "prefs",
                    Map.of(
                            "credentials_enable_service", false,
                            "profile.password_manager_leak_detection", false,
                            "profile.password_manager_enabled", false
                    )
            );

            webDriver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            webDriver = new EdgeDriver();

        } else {

            throw new RuntimeException(
                    "Unsupported browser: " + browser
            );
        }

        // Do not maximize during parallel startup.
        // Chrome 153 + parallel sessions can occasionally
        // fail with CDP Runtime.evaluate during maximize.

        driver.set(webDriver);

        return webDriver;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        WebDriver webDriver = driver.get();

        if (webDriver != null) {
            try {
                webDriver.quit();
            } finally {
                driver.remove();
            }
        }
    }
}