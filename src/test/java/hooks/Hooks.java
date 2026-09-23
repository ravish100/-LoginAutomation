package hooks;

import driverFactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.HasCapabilities;
import utils.ConfigReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    private static final Logger logger =
            LogManager.getLogger(Hooks.class);

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @Before
    public void setUp() {

        logger.info(
                "Starting browser | Thread={}",
                Thread.currentThread().getName()
        );

        DriverFactory.initializeDriver();

        logger.info(
                "Browser started successfully | Thread={}",
                Thread.currentThread().getName()
        );
    }

    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

        try {

            if (scenario.isFailed()) {

                logger.error(
                        "Scenario failed | Scenario={} | Thread={}",
                        scenario.getName(),
                        Thread.currentThread().getName()
                );

                captureFailureScreenshot(
                        driver,
                        scenario
                );

            } else {

                logger.info(
                        "Scenario passed | Scenario={} | Thread={}",
                        scenario.getName(),
                        Thread.currentThread().getName()
                );
            }

        } finally {

            logger.info(
                    "Closing browser | Scenario={} | Thread={}",
                    scenario.getName(),
                    Thread.currentThread().getName()
            );

            DriverFactory.quitDriver();

            logger.info(
                    "Browser cleanup completed | Scenario={} | Thread={}",
                    scenario.getName(),
                    Thread.currentThread().getName()
            );
        }
    }

    private void captureFailureScreenshot(
            WebDriver driver,
            Scenario scenario) {

        if (driver == null) {

            logger.warn(
                    "WebDriver is null. Screenshot cannot be captured."
            );

            return;
        }

        try {

            /*
             * First check whether the browser window is still available.
             */
            String currentUrl = driver.getCurrentUrl();

            logger.info(
                    "Capturing failure screenshot | URL={} | Thread={}",
                    currentUrl,
                    Thread.currentThread().getName()
            );

            /*
             * Capture original screenshot.
             */
            byte[] originalScreenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            /*
             * Attach original screenshot to Cucumber report.
             */
            scenario.attach(
                    originalScreenshot,
                    "image/png",
                    "Failure Screenshot"
            );

            /*
             * Create enhanced diagnostic screenshot.
             */
            byte[] enhancedScreenshot =
                    createDiagnosticScreenshot(
                            originalScreenshot,
                            scenario,
                            driver,
                            currentUrl
                    );

            /*
             * Save enhanced screenshot.
             */
            saveScreenshot(
                    enhancedScreenshot,
                    scenario.getName()
            );

            logger.info(
                    "Failure screenshot attached and saved successfully."
            );

        } catch (Exception e) {

            /*
             * Screenshot failure must NOT replace
             * the original scenario failure.
             */
            logger.warn(
                    "Unable to capture failure screenshot. "
                            + "Browser may already be closed.",
                    e
            );
        }
    }

    private byte[] createDiagnosticScreenshot(
            byte[] screenshotBytes,
            Scenario scenario,
            WebDriver driver,
            String currentUrl) throws IOException {

        BufferedImage originalImage =
                ImageIO.read(
                        new ByteArrayInputStream(screenshotBytes)
                );

        String browser =
                ((HasCapabilities) driver)
                        .getCapabilities()
                        .getBrowserName();

        String executionMode =
                ConfigReader.getProperty("executionMode");

        String gridUrl =
                ConfigReader.getProperty("gridUrl");

        String threadName =
                Thread.currentThread().getName();

        String timestamp =
                LocalDateTime.now()
                        .format(TIMESTAMP_FORMAT);

        int headerHeight = 170;

        BufferedImage finalImage =
                new BufferedImage(
                        originalImage.getWidth(),
                        originalImage.getHeight() + headerHeight,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D graphics =
                finalImage.createGraphics();

        try {

            /*
             * Header background
             */
            graphics.setColor(Color.WHITE);

            graphics.fillRect(
                    0,
                    0,
                    finalImage.getWidth(),
                    headerHeight
            );

            /*
             * Header title
             */
            graphics.setColor(Color.BLACK);

            graphics.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            16
                    )
            );

            graphics.drawString(
                    "FAILURE SCREENSHOT",
                    20,
                    25
            );

            /*
             * Scenario
             */
            graphics.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            13
                    )
            );

            graphics.drawString(
                    "Scenario: " + scenario.getName(),
                    20,
                    50
            );

            /*
             * URL
             */
            graphics.drawString(
                    "URL: " + currentUrl,
                    20,
                    70
            );

            /*
             * Browser
             */
            graphics.drawString(
                    "Browser: " + browser,
                    20,
                    90
            );

            /*
             * Execution mode
             */
            graphics.drawString(
                    "Execution Mode: " + executionMode,
                    20,
                    110
            );

            /*
             * Thread
             */
            graphics.drawString(
                    "Thread: " + threadName,
                    20,
                    130
            );

            /*
             * Grid URL
             */
            if ("grid".equalsIgnoreCase(executionMode)
                    && gridUrl != null
                    && !gridUrl.isBlank()) {

                graphics.drawString(
                        "Grid URL: " + gridUrl,
                        20,
                        150
                );
            } else {

                graphics.drawString(
                        "Grid URL: N/A",
                        20,
                        150
                );
            }

            /*
             * Original screenshot
             */
            graphics.drawImage(
                    originalImage,
                    0,
                    headerHeight,
                    null
            );

        } finally {

            graphics.dispose();
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        ImageIO.write(
                finalImage,
                "png",
                outputStream
        );

        return outputStream.toByteArray();
    }

    private void saveScreenshot(
            byte[] screenshotBytes,
            String scenarioName) {

        try {

            Path screenshotDirectory =
                    Paths.get(
                            "target",
                            "screenshots"
                    );

            Files.createDirectories(
                    screenshotDirectory
            );

            String safeScenarioName =
                    scenarioName.replaceAll(
                            "[^a-zA-Z0-9-_]",
                            "_"
                    );

            String timestamp =
                    LocalDateTime.now()
                            .format(TIMESTAMP_FORMAT);

            String threadName =
                    Thread.currentThread()
                            .getName()
                            .replaceAll(
                                    "[^a-zA-Z0-9-_]",
                                    "_"
                            );

            String fileName =
                    safeScenarioName
                            + "_"
                            + timestamp
                            + "_"
                            + threadName
                            + ".png";

            Path screenshotPath =
                    screenshotDirectory.resolve(
                            fileName
                    );

            Files.write(
                    screenshotPath,
                    screenshotBytes
            );

            logger.info(
                    "Screenshot saved to: {}",
                    screenshotPath.toAbsolutePath()
            );

        } catch (IOException e) {

            logger.error(
                    "Failed to save screenshot to disk.",
                    e
            );
        }
    }
}
