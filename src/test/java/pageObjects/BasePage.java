package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {

        this.driver = driver;

        int waitTime = Integer.parseInt(
                ConfigReader.getProperty("explicitWait")
        );

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(waitTime)
        );
    }

    protected void enterText(By locator, String text) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        element.click();
    }

    protected String getText(By locator) {

        return wait.until(driver -> {

            try {

                String text =
                        driver.findElement(locator).getText();

                if (text != null && !text.trim().isEmpty()) {
                    return text.trim();
                }

                return null;

            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    protected String getAttribute(
            By locator,
            String attribute) {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator)
        ).getAttribute(attribute);
    }

    protected boolean isDisplayed(By locator) {

        try {

            return wait.until(
                    ExpectedConditions
                            .visibilityOfElementLocated(locator)
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
    protected boolean isCurrentUrlContaining(String urlPart) {

        return driver.getCurrentUrl().contains(urlPart);
    }
}