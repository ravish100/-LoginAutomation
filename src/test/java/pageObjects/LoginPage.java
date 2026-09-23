package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By usernameField =
            By.id("username");

    private final By passwordField =
            By.id("password");

    private final By loginButton =
            By.id("signInBtn");

    private final By invalidCredentialsMessage =
            By.cssSelector("div.alert.alert-danger");


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public void enterUsername(String username) {

        enterText(usernameField, username);
    }


    public void enterPassword(String password) {

        enterText(passwordField, password);
    }


    public void clickLogin() {

        click(loginButton);
    }


    public String getInvalidCredentialsMessage() {

        return wait.until(driver -> {
            WebElement element =
                    driver.findElement(invalidCredentialsMessage);

            if (!element.isDisplayed()) {
                return null;
            }

            String text = element.getText().trim();

            if (text.toLowerCase().contains("incorrect")) {
                return text;
            }

            return null;
        });
    }

    public boolean isUsernameFieldEmpty() {

        String value =
                getAttribute(usernameField, "value");

        return value == null || value.trim().isEmpty();
    }


    public boolean isPasswordFieldEmpty() {

        String value =
                getAttribute(passwordField, "value");

        return value == null || value.trim().isEmpty();
    }


    public boolean isOnPage(String urlPart) {

        return isCurrentUrlContaining(urlPart);
    }
    public boolean isNotOnPage(String urlPart) {

        return !isCurrentUrlContaining(urlPart);
    }
    public void openLoginPage(String url) {

        driver.get(url);
    }
    public boolean waitForShopPage() {

        return wait.until(driver ->
                driver.getCurrentUrl().contains("shop")
        );
    }
}