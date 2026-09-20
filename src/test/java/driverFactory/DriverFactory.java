package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    // Temporary diagnostics for measuring actual concurrency
    private static final AtomicInteger activeBrowsers =
            new AtomicInteger(0);

    private static final AtomicInteger maxConcurrentBrowsers =
            new AtomicInteger(0);


    public static WebDriver initializeDriver() {

        String browser = ConfigReader.getProperty("browser");
        String executionMode = ConfigReader.getProperty("executionMode");

        if (executionMode == null || executionMode.isBlank()) {
            executionMode = "local";
        }

        WebDriver webDriver;

        try {

            // =========================================================
            // CHROME
            // =========================================================

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();

                options.addArguments("--disable-notifications");

                // Disable Chrome password manager UI
                options.addArguments(
                        "--disable-features=PasswordLeakDetection"
                );

                options.setExperimentalOption(
                        "prefs",
                        Map.of(
                                "credentials_enable_service", false,
                                "profile.password_manager_leak_detection", false,
                                "profile.password_manager_enabled", false
                        )
                );

                if (executionMode.equalsIgnoreCase("grid")) {

                    String gridUrl =
                            ConfigReader.getProperty("gridUrl");

                    if (gridUrl == null || gridUrl.isBlank()) {
                        throw new RuntimeException(
                                "gridUrl is not configured in config.properties"
                        );
                    }

                    webDriver = new RemoteWebDriver(
                            new URL(gridUrl),
                            options
                    );

                } else {

                    WebDriverManager.chromedriver().setup();

                    webDriver = new ChromeDriver(options);
                }


                // =========================================================
                // FIREFOX
                // =========================================================

            } else if (browser.equalsIgnoreCase("firefox")) {

                FirefoxOptions options =
                        new FirefoxOptions();

                if (executionMode.equalsIgnoreCase("grid")) {

                    String gridUrl =
                            ConfigReader.getProperty("gridUrl");

                    if (gridUrl == null || gridUrl.isBlank()) {
                        throw new RuntimeException(
                                "gridUrl is not configured in config.properties"
                        );
                    }

                    webDriver = new RemoteWebDriver(
                            new URL(gridUrl),
                            options
                    );

                } else {

                    WebDriverManager.firefoxdriver().setup();

                    webDriver = new FirefoxDriver(options);
                }


                // =========================================================
                // EDGE
                // =========================================================

            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options =
                        new EdgeOptions();

                if (executionMode.equalsIgnoreCase("grid")) {

                    String gridUrl =
                            ConfigReader.getProperty("gridUrl");

                    if (gridUrl == null || gridUrl.isBlank()) {
                        throw new RuntimeException(
                                "gridUrl is not configured in config.properties"
                        );
                    }

                    webDriver = new RemoteWebDriver(
                            new URL(gridUrl),
                            options
                    );

                } else {

                    WebDriverManager.edgedriver().setup();

                    webDriver = new EdgeDriver(options);
                }


                // =========================================================
                // UNSUPPORTED BROWSER
                // =========================================================

            } else {

                throw new RuntimeException(
                        "Unsupported browser: " + browser
                );
            }


        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Invalid Selenium Grid URL: "
                            + ConfigReader.getProperty("gridUrl"),
                    e
            );
        }


        // =============================================================
        // STORE DRIVER IN THREADLOCAL
        // =============================================================

        driver.set(webDriver);


        // =============================================================
        // CONCURRENCY DIAGNOSTICS
        // =============================================================

        int current =
                activeBrowsers.incrementAndGet();

        updateMaximum(current);

        System.out.println(
                "BROWSER CREATED | Thread="
                        + Thread.currentThread().getName()
                        + " | Browser="
                        + browser
                        + " | Mode="
                        + executionMode
                        + " | Active browsers="
                        + current
                        + " | Max concurrent="
                        + maxConcurrentBrowsers.get()
        );

        return webDriver;
    }


    // =============================================================
    // UPDATE MAXIMUM CONCURRENT BROWSERS
    // =============================================================

    private static void updateMaximum(int current) {

        int previousMaximum;

        do {

            previousMaximum =
                    maxConcurrentBrowsers.get();

            if (current <= previousMaximum) {
                return;
            }

        } while (!maxConcurrentBrowsers.compareAndSet(
                previousMaximum,
                current
        ));
    }


    // =============================================================
    // GET DRIVER
    // =============================================================

    public static WebDriver getDriver() {

        return driver.get();
    }


    // =============================================================
    // QUIT DRIVER
    // =============================================================

    public static void quitDriver() {

        WebDriver webDriver =
                driver.get();

        if (webDriver != null) {

            try {

                System.out.println(
                        "Closing browser..."
                );

                webDriver.quit();

            } finally {

                // Remove first so the same ThreadLocal
                // driver cannot accidentally be closed twice.
                driver.remove();

                int current =
                        activeBrowsers.decrementAndGet();

                System.out.println(
                        "BROWSER CLOSED | Thread="
                                + Thread.currentThread().getName()
                                + " | Active browsers="
                                + current
                                + " | Max concurrent="
                                + maxConcurrentBrowsers.get()
                );
            }
        }
    }
}