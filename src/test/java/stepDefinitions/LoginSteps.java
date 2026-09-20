package stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import driverFactory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoginPage;
import utils.ConfigReader;
import utils.TestDataReader;
import pageObjects.ShopPage;

public class LoginSteps {

   // private WebDriver driver;
    private LoginPage loginPage;
    private ShopPage shopPage;

    private static final Logger logger =
            LogManager.getLogger(LoginSteps.class);

    private void verifySuccessfulLogin() {

        logger.info(
                "Current URL after login: "
                        + DriverFactory.getDriver().getCurrentUrl()
        );

        Assert.assertTrue(
                "Expected successful login to redirect to Shop page.",
                loginPage.waitForShopPage()
        );
//        Assert.assertTrue(
//                "Retry test - intentional failure",
//                false
//        );
        logger.info(
                "Login successful. Shop page displayed."
        );
    }

    private void verifyInvalidCredentials() {

        String error =
                loginPage.getInvalidCredentialsMessage();

        logger.info(
                "Application error message: " + error
        );

        Assert.assertTrue(
                "Expected error message to contain "
                        + "'Incorrect username/password.' "
                        + "but actual message was: " + error,

                error.toLowerCase().contains(
                        "incorrect username/password"
                )
        );

        Assert.assertTrue(
                "Invalid credentials should not redirect "
                        + "the user to the Shop page.",

                loginPage.isNotOnPage("shop")
        );

        logger.info(
                "Invalid credentials correctly handled."
        );
    }
    private void verifyEmptyCredentials() {

        Assert.assertFalse(
                "Empty username/password should not "
                        + "result in successful login.",

                loginPage.isOnPage("shop")
        );

        logger.info(
                "Empty username/password correctly "
                        + "prevented successful login."
        );
    }
    @Given("User is on the Login Page")
    public void user_is_on_the_login_page() {

        logger.info("Navigating to login page.");

        WebDriver driver = DriverFactory.getDriver();

        String url = ConfigReader.getEnvironmentUrl();

        logger.info("Navigating to {} environment URL: {}",
                ConfigReader.getProperty("environment"),
                url);

        driver.get(url);

        loginPage = new LoginPage(driver);

        logger.info("Navigating to URL: " + url);

        loginPage.openLoginPage(url);

        logger.info("Login page loaded successfully.");
    }

    @When("User enters credentials for {string}")
    public void user_enters_credentials(String testData) {

        String username =
                TestDataReader.getValue(
                        testData + ".username"
                );

        String password =
                TestDataReader.getValue(
                        testData + ".password"
                );

        logger.info("Test data key: " + testData);
        logger.info("Username entered: " + username);
        logger.info(
                "Password retrieved: "
                        + (password != null && !password.isBlank())
        );

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }


    @When("User clicks on login button")
    public void user_clicks_on_login_button() {

        logger.info("Clicking login button.");

        loginPage.clickLogin();
    }


    @Then("User should see {string}")
    public void user_should_see(String outcome) {

        logger.info("Verifying login outcome: " + outcome);

        if (outcome.equalsIgnoreCase("successful login")) {

            verifySuccessfulLogin();

        } else if (outcome.equalsIgnoreCase("Invalid credentials")) {

            verifyInvalidCredentials();

        } else if (outcome.equalsIgnoreCase("Empty username/password")) {

            verifyEmptyCredentials();

        } else {

            Assert.fail(
                    "Unsupported expected outcome: '"
                            + outcome
                            + "'."
            );
        }
    }
    @Then("User should remain on the Login Page")
    public void user_should_remain_on_login_page() {

        logger.info(
                "Verifying user remains on Login page after invalid login."
        );

        Assert.assertTrue(
                "User should remain on the Login page after invalid credentials.",
                loginPage.isOnPage("login")
        );

        logger.info(
                "User correctly remained on Login page."
        );
    }
    @Then("User should see the Shop page")
    public void user_should_see_the_shop_page() {

        logger.info(
                "Verifying Shop page is displayed."
        );

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Shop page should be displayed after successful login.",
                shopPage.isShopPageDisplayed()
        );

        logger.info(
                "Shop page displayed successfully."
        );
    }
}