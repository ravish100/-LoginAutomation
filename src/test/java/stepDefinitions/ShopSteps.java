package stepDefinitions;

import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import driverFactory.DriverFactory;
import io.cucumber.java.en.Then;
import pageObjects.CartPage;
import pageObjects.ShopPage;

public class ShopSteps {

    private static final Logger logger =
            LogManager.getLogger(ShopSteps.class);

    private ShopPage shopPage;

    @Then("Shop page should display products")
    public void shop_page_should_display_products() {

        logger.info(
                "Verifying products are displayed on Shop page."
        );

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        int productCount =
                shopPage.getProductCount();

        logger.info(
                "Number of products displayed: {}",
                productCount
        );

        Assert.assertTrue(
                "Shop page should display at least one product.",
                productCount > 0
        );

        logger.info(
                "Products are displayed successfully."
        );
    }
    @Then("Shop page should display product names")
    public void shop_page_should_display_product_names() {

        logger.info(
                "Verifying product names are displayed."
        );

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        var productNames =
                shopPage.getProductNames();

        Assert.assertFalse(
                "Product names should be displayed.",
                productNames.isEmpty()
        );

        for (var productName : productNames) {

            String name =
                    productName.getText().trim();

            logger.info(
                    "Product name found: {}",
                    name
            );

            Assert.assertFalse(
                    "Product name should not be empty.",
                    name.isEmpty()
            );
        }

        logger.info(
                "All {} product names are displayed successfully.",
                productNames.size()
        );
    }
    @Then("Shop page should display product prices")
    public void shop_page_should_display_product_prices() {

        logger.info(
                "Verifying product prices are displayed."
        );

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        var productPrices =
                shopPage.getProductPrices();

        Assert.assertFalse(
                "Product prices should be displayed.",
                productPrices.isEmpty()
        );

        for (var productPrice : productPrices) {

            String price =
                    productPrice.getText().trim();

            logger.info(
                    "Product price found: {}",
                    price
            );

            Assert.assertFalse(
                    "Product price should not be empty.",
                    price.isEmpty()
            );
        }

        logger.info(
                "All {} product prices are displayed successfully.",
                productPrices.size()
        );
    }
    @When("User adds product {string} to the cart")
    public void user_adds_product_to_the_cart(String productName) {

        logger.info(
                "Adding product to cart: {}",
                productName
        );

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        shopPage.addProduct(productName);

        logger.info(
                "Product added to cart: {}",
                productName
        );
    }


    @Then("Cart count should be {string}")
    public void cart_count_should_be(String expectedCount) {

        logger.info(
                "Verifying cart count. Expected: {}",
                expectedCount
        );

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        String actualCount =
                shopPage.getCartCount();

        logger.info(
                "Actual cart count: {}",
                actualCount
        );

        Assert.assertEquals(
                "Cart count does not match expected value.",
                expectedCount,
                actualCount
        );

        logger.info(
                "Cart count verified successfully."
        );
    }
    @When("User opens the cart")
    public void user_opens_the_cart() {

        logger.info("Opening cart.");

        shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        shopPage.clickCheckout();

        logger.info("Cart opened successfully.");
    }


    @Then("Cart page should be displayed")
    public void cart_page_should_be_displayed() {

        logger.info(
                "Verifying Cart page is displayed."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Cart page should be displayed.",
                cartPage.isCartPageDisplayed()
        );

        logger.info(
                "Cart page displayed successfully."
        );
    }
    @Then("Cart should contain {string} products")
    public void cart_should_contain_products(String expectedCount) {

        logger.info(
                "Verifying number of products in cart. Expected: {}",
                expectedCount
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        int actualCount =
                cartPage.getCartProductCount();

        int expected =
                Integer.parseInt(expectedCount);

        logger.info(
                "Actual cart product count: {}",
                actualCount
        );

        Assert.assertEquals(
                "Cart product count does not match expected value.",
                expected,
                actualCount
        );

        logger.info(
                "Cart product count verified successfully."
        );
    }
    @Then("Cart should contain product {string}")
    public void cart_should_contain_product(String productName) {

        logger.info(
                "Verifying product in cart: {}",
                productName
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Cart should contain product: "
                        + productName,
                cartPage.containsProduct(productName)
        );

        logger.info(
                "Product found in cart: {}",
                productName
        );
    }
    @When("User removes product {string} from the cart")
    public void user_removes_product_from_the_cart(
            String productName) {

        logger.info(
                "Removing product from cart: {}",
                productName
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.removeProduct(productName);

        logger.info(
                "Product removed from cart: {}",
                productName
        );
    }
    @Then("Cart should contain {string} product")
    public void cart_should_contain_product_count(
            String expectedCount) {

        logger.info(
                "Verifying cart product count after removal. Expected: {}",
                expectedCount
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        int actualCount =
                cartPage.getCartProductCount();

        int expected =
                Integer.parseInt(expectedCount);

        logger.info(
                "Actual cart product count: {}",
                actualCount
        );

        Assert.assertEquals(
                "Cart product count does not match expected value.",
                expected,
                actualCount
        );

        logger.info(
                "Cart product count verified successfully."
        );
    }
    @Then("Cart total should be {string}")
    public void cart_total_should_be(
            String expectedTotal) {

        logger.info(
                "Verifying cart total. Expected: {}",
                expectedTotal
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        String actualTotal =
                cartPage.getCartTotal();

        logger.info(
                "Actual cart total: {}",
                actualTotal
        );

        Assert.assertTrue(
                "Cart total should contain expected value.",
                actualTotal.contains(expectedTotal)
        );

        logger.info(
                "Cart total verified successfully."
        );
    }
    @When("User clicks Continue Shopping")
    public void user_clicks_continue_shopping() {

        logger.info("Clicking Continue Shopping.");

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.clickContinueShopping();

        logger.info(
                "Continue Shopping clicked successfully."
        );
    }
    @Then("User should return to the Shop page")
    public void user_should_return_to_the_shop_page() {

        logger.info(
                "Verifying user returned to Shop page."
        );

        ShopPage shopPage =
                new ShopPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "User should be returned to Shop page.",
                shopPage.isShopPageDisplayed()
        );

        logger.info(
                "User returned to Shop page successfully."
        );
    }
    @When("User clicks Checkout")
    public void user_clicks_checkout() {

        logger.info("Clicking Checkout.");

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.clickCheckout();

        logger.info(
                "Checkout clicked successfully."
        );
    }
    @Then("Checkout page should be displayed")
    public void checkout_page_should_be_displayed() {

        logger.info(
                "Verifying Checkout section is displayed."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Checkout section should be displayed.",
                cartPage.isCheckoutSectionDisplayed()
        );

        logger.info(
                "Checkout section displayed successfully."
        );
    }
    @Then("Checkout should display delivery location message")
    public void checkout_should_display_delivery_location_message() {

        logger.info(
                "Verifying checkout delivery location message."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Checkout should display delivery location message.",
                cartPage.isCheckoutDeliveryMessageDisplayed()
        );

        logger.info(
                "Checkout delivery location message displayed."
        );
    }
    @Then("Checkout should display purchase instruction")
    public void checkout_should_display_purchase_instruction() {

        logger.info(
                "Verifying checkout purchase instruction."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Checkout should display purchase instruction.",
                cartPage.isCheckoutPurchaseInstructionDisplayed()
        );

        logger.info(
                "Checkout purchase instruction displayed."
        );
    }
    @Then("Checkout should display terms and conditions")
    public void checkout_should_display_terms_and_conditions() {

        logger.info(
                "Verifying checkout terms and conditions."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Checkout should display terms and conditions.",
                cartPage.isCheckoutTermsDisplayed()
        );

        logger.info(
                "Checkout terms and conditions displayed."
        );
    }
    @Then("Debug checkout input fields")
    public void debug_checkout_input_fields() {

        logger.info(
                "Inspecting checkout input fields."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.debugCheckoutInputs();
    }
    @When("User enters delivery location {string}")
    public void user_enters_delivery_location(
            String country) {

        logger.info(
                "Entering delivery location: {}",
                country
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.enterDeliveryLocation(country);

        logger.info(
                "Delivery location entered successfully: {}",
                country
        );
    }
    @Then("Delivery location should be {string}")
    public void delivery_location_should_be(
            String expectedCountry) {

        logger.info(
                "Verifying delivery location. Expected: {}",
                expectedCountry
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        String actualCountry =
                cartPage.getDeliveryLocation();

        logger.info(
                "Actual delivery location: {}",
                actualCountry
        );

        Assert.assertEquals(
                "Delivery location does not match.",
                expectedCountry,
                actualCountry
        );

        logger.info(
                "Delivery location verified successfully."
        );
    }

    @When("User selects the terms and conditions checkbox")
    public void user_selects_terms_and_conditions_checkbox() {

        logger.info(
                "Selecting Terms and Conditions checkbox."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.selectTermsAndConditions();

        logger.info(
                "Terms and Conditions checkbox selected."
        );
    }
    @Then("Terms and conditions checkbox should be selected")
    public void terms_and_conditions_checkbox_should_be_selected() {

        logger.info(
                "Verifying Terms and Conditions checkbox."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Terms and Conditions checkbox should be selected.",
                cartPage.isTermsAndConditionsSelected()
        );

        logger.info(
                "Terms and Conditions checkbox is selected."
        );
    }


    @Then("Checkout should display Purchase button")
    public void checkout_should_display_purchase_button() {

        logger.info(
                "Verifying Purchase button is displayed."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertTrue(
                "Purchase button should be displayed on Checkout.",
                cartPage.isPurchaseButtonDisplayed()
        );

        logger.info(
                "Purchase button is displayed successfully."
        );
    }
    @When("User clicks Purchase without accepting terms")
    public void user_clicks_purchase_without_accepting_terms() {

        logger.info(
                "Clicking Purchase without accepting Terms and Conditions."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.clickPurchase();

        logger.info(
                "Purchase button clicked without accepting Terms and Conditions."
        );
    }
    @Then("User should remain on the Checkout page")
    public void user_should_remain_on_the_checkout_page() {

        logger.info(
                "Verifying user remains on Checkout page."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        String currentUrl =
                cartPage.getCurrentUrl();

        logger.info(
                "Current URL after Purchase attempt: {}",
                currentUrl
        );

        Assert.assertTrue(
                "User should remain on the Checkout page.",
                currentUrl.contains("/shop")
        );

        logger.info(
                "User remained on the Checkout page."
        );
    }
    @When("User clicks Purchase")
    public void user_clicks_purchase() {

        logger.info(
                "Clicking Purchase button."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.clickPurchase();

        logger.info(
                "Purchase button clicked successfully."
        );


    }





    @When("User unselects the terms and conditions checkbox")
    public void user_unselects_terms_and_conditions_checkbox() {

        logger.info(
                "Unselecting Terms and Conditions checkbox."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        cartPage.unselectTermsAndConditions();

        logger.info(
                "Terms and Conditions checkbox unselected."
        );
    }


    @Then("Terms and conditions checkbox should not be selected")
    public void terms_and_conditions_checkbox_should_not_be_selected() {

        logger.info(
                "Verifying Terms and Conditions checkbox is not selected."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        Assert.assertFalse(
                "Terms and Conditions checkbox should not be selected.",
                cartPage.isTermsAndConditionsSelected()
        );

        logger.info(
                "Terms and Conditions checkbox is not selected."
        );
    }

    @Then("Purchase confirmation message should be displayed")
    public void purchase_confirmation_message_should_be_displayed() {

        logger.info(
                "Verifying purchase confirmation message."
        );

        CartPage cartPage =
                new CartPage(
                        DriverFactory.getDriver()
                );

        String actualMessage =
                cartPage.getPurchaseSuccessMessage();

        logger.info(
                "Purchase confirmation message: {}",
                actualMessage
        );

        Assert.assertTrue(
                "Purchase confirmation message should be displayed.",
                actualMessage.contains(
                        "Success! Thank you! Your order will be delivered in next few weeks"
                )
        );

        logger.info(
                "Purchase confirmation verified successfully."
        );
    }

}