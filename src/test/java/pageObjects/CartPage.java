package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(
                                Integer.parseInt(
                                        ConfigReader.getProperty(
                                                "explicitWait"
                                        )
                                )
                        )
                );
    }

    // --------------------------------------------------
    // Locators
    // --------------------------------------------------

    private By cartTable =
            By.cssSelector("table.table");

    private By termsCheckbox =
            By.id("checkbox2");

    private By countryInput =
            By.id("country");

    private By cartRows =
            By.cssSelector(
                    "table.table tbody tr"
            );

    private By checkoutSection =
            By.cssSelector(
                    ".checkout"
            );

    private By productRows =
            By.xpath(
                    "//table[contains(@class,'table')]"
                            + "//tbody/tr"
                            + "[.//button[contains(@class,'btn-danger')]]"
            );

    private By purchaseButton =
            By.cssSelector(
                    "input[type='submit'][value='Purchase']"
            );

    private By productNames =
            By.xpath(
                    "//table[contains(@class,'table')]"
                            + "//tbody/tr"
                            + "[.//button[contains(@class,'btn-danger')]]"
                            + "/td[2]"
            );

    private By removeButtons =
            By.cssSelector(
                    "table.table tbody tr button.btn-danger"
            );

    private By cartTotal =
            By.xpath(
                    "//tr[td[normalize-space()='Total']]"
                            + "/td[last()]"
            );

    private By continueShoppingButton =
            By.cssSelector(
                    "button.btn.btn-default"
            );

    private By cartCheckoutButton =
            By.cssSelector(
                    "button.btn.btn-success"
            );

    private By purchaseSuccessMessage =
            By.xpath(
                    "//*[contains(normalize-space(),"
                            + "'Success! Thank you! Your order will be delivered in next few weeks')]"
            );

    private By checkoutDeliveryMessage =
            By.xpath(
                    "//*[contains(normalize-space(),"
                            + "'Please choose your delivery location.')]"
            );

    private By checkoutPurchaseMessage =
            By.xpath(
                    "//*[contains(normalize-space(),"
                            + "'Then click on purchase button')]"
            );

    private By checkoutTermsText =
            By.xpath(
                    "//*[contains(normalize-space(),"
                            + "'I agree with the term & Conditions')]"
            );


    // --------------------------------------------------
    // Cart page
    // --------------------------------------------------

    public boolean isCartPageDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTable)
        ).isDisplayed();
    }


    // --------------------------------------------------
    // Cart product count
    // --------------------------------------------------

    public int getCartProductCount() {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTable)
        );

        List<WebElement> rows =
                driver.findElements(productRows);

        int productCount = 0;

        for (WebElement row : rows) {

            String rowText =
                    row.getText()
                            .trim();

            if (!rowText.isEmpty()
                    && !rowText.equalsIgnoreCase("Total")) {

                productCount++;
            }
        }

        return productCount;
    }


    // --------------------------------------------------
    // Product names
    // --------------------------------------------------

    public List<WebElement> getProductNames() {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productNames)
        );

        return driver.findElements(productNames);
    }


    // --------------------------------------------------
    // Verify product exists
    // --------------------------------------------------

    public boolean containsProduct(String productName) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTable)
        );

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTotal)
        );

        List<WebElement> rows =
                driver.findElements(productRows);

        for (WebElement row : rows) {

            String rowText =
                    row.getText()
                            .trim();

            if (rowText
                    .toLowerCase()
                    .contains(productName.toLowerCase())) {

                return true;
            }
        }

        return false;
    }


    // --------------------------------------------------
    // Remove product
    // --------------------------------------------------

    public void removeProduct(String productName) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTable)
        );

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productRows)
        );

        List<WebElement> rows =
                driver.findElements(productRows);

        for (WebElement row : rows) {

            String rowText =
                    row.getText()
                            .trim();

            if (rowText
                    .toLowerCase()
                    .contains(productName.toLowerCase())) {

                WebElement removeButton =
                        row.findElement(
                                By.cssSelector("button.btn-danger")
                        );

                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(removeButton)
                );

                removeButton.click();

                /*
                 * Wait until this product disappears
                 * from the cart.
                 */
                wait.until(driver -> {

                    List<WebElement> remainingRows =
                            driver.findElements(productRows);

                    for (WebElement remainingRow :
                            remainingRows) {

                        String text =
                                remainingRow.getText()
                                        .trim()
                                        .toLowerCase();

                        if (text.contains(
                                productName.toLowerCase())) {

                            return false;
                        }
                    }

                    return true;
                });

                return;
            }
        }

        throw new RuntimeException(
                "Product not found in cart: "
                        + productName
        );
    }


    // --------------------------------------------------
    // Cart total
    // --------------------------------------------------

    public String getCartTotal() {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTable)
        );

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTotal)
        ).getText().trim();
    }


    // --------------------------------------------------
    // Continue shopping
    // --------------------------------------------------

    public void clickContinueShopping() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                continueShoppingButton
                        )
        ).click();

        /*
         * Confirm navigation back to Shop page.
         */
        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                By.xpath(
                                        "//h1[contains(normalize-space(),'Shop Name')]"
                                )
                        )
        );
    }


    // --------------------------------------------------
    // Checkout
    // --------------------------------------------------

    public void clickCheckout() {

        /*
         * Make sure cart page is loaded first.
         */
        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(cartTable)
        );

        WebElement checkout =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        cartCheckoutButton
                                )
                );

        checkout.click();

        /*
         * Confirm checkout section has loaded.
         */
        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutDeliveryMessage
                        )
        );
    }


    // --------------------------------------------------
    // Checkout validation
    // --------------------------------------------------

    public boolean isCheckoutDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutSection
                        )
        ).isDisplayed();
    }


    public void debugCheckoutPage() {

        System.out.println(
                "========== CHECKOUT DEBUG =========="
        );

        System.out.println(
                "URL: " +
                        driver.getCurrentUrl()
        );

        System.out.println(
                "PAGE TITLE: " +
                        driver.getTitle()
        );

        System.out.println(
                "PAGE TEXT:"
        );

        System.out.println(
                driver.findElement(
                        By.tagName("body")
                ).getText()
        );

        System.out.println(
                "===================================="
        );
    }


    public boolean isCheckoutSectionDisplayed() {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutDeliveryMessage
                        )
        );

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutPurchaseMessage
                        )
        );

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutTermsText
                        )
        );

        return true;
    }


    public boolean isCheckoutDeliveryMessageDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutDeliveryMessage
                        )
        ).isDisplayed();
    }


    public boolean isCheckoutPurchaseInstructionDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutPurchaseMessage
                        )
        ).isDisplayed();
    }


    public boolean isCheckoutTermsDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                checkoutTermsText
                        )
        ).isDisplayed();
    }


    // --------------------------------------------------
    // Debug checkout inputs
    // --------------------------------------------------

    public void debugCheckoutInputs() {

        System.out.println(
                "========== CHECKOUT INPUT DEBUG =========="
        );

        List<WebElement> inputs =
                driver.findElements(
                        By.cssSelector("input")
                );

        System.out.println(
                "INPUT COUNT: " + inputs.size()
        );

        for (int i = 0; i < inputs.size(); i++) {

            WebElement input = inputs.get(i);

            System.out.println(
                    "INPUT " + i
                            + " | type=" + input.getAttribute("type")
                            + " | name=" + input.getAttribute("name")
                            + " | id=" + input.getAttribute("id")
                            + " | class=" + input.getAttribute("class")
                            + " | placeholder="
                            + input.getAttribute("placeholder")
            );
        }

        System.out.println(
                "=========================================="
        );
    }


    // --------------------------------------------------
    // Delivery location
    // --------------------------------------------------

    public void enterDeliveryLocation(String country) {

        WebElement countryField =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        countryInput
                                )
                );

        countryField.clear();

        countryField.sendKeys(country);
    }


    public String getDeliveryLocation() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                countryInput
                        )
        ).getAttribute("value");
    }


    // --------------------------------------------------
    // Terms and Conditions
    // --------------------------------------------------

    public void selectTermsAndConditions() {

        WebElement checkbox =
                wait.until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        termsCheckbox
                                )
                );

        if (!checkbox.isSelected()) {

            WebElement label =
                    wait.until(
                            ExpectedConditions
                                    .presenceOfElementLocated(
                                            By.cssSelector(
                                                    "label[for='checkbox2']"
                                            )
                                    )
                    );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            label
                    );

            wait.until(
                    ExpectedConditions
                            .visibilityOf(label)
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            label
                    );

            wait.until(
                    ExpectedConditions
                            .elementSelectionStateToBe(
                                    termsCheckbox,
                                    true
                            )
            );
        }
    }


    public boolean isTermsAndConditionsSelected() {

        return wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(
                                termsCheckbox
                        )
        ).isSelected();
    }


    public void debugTermsCheckbox() {

        System.out.println(
                "========== TERMS CHECKBOX DEBUG =========="
        );

        WebElement checkbox =
                driver.findElement(
                        By.id("checkbox2")
                );

        System.out.println(
                "CHECKBOX:"
        );

        System.out.println(
                "tag=" + checkbox.getTagName()
                        + " | type="
                        + checkbox.getAttribute("type")
                        + " | id="
                        + checkbox.getAttribute("id")
                        + " | class="
                        + checkbox.getAttribute("class")
                        + " | displayed="
                        + checkbox.isDisplayed()
                        + " | enabled="
                        + checkbox.isEnabled()
                        + " | selected="
                        + checkbox.isSelected()
        );

        System.out.println(
                "PARENT HTML:"
        );

        System.out.println(
                checkbox.findElement(
                        By.xpath("..")
                ).getAttribute("outerHTML")
        );

        System.out.println(
                "=========================================="
        );
    }


    // --------------------------------------------------
    // Purchase
    // --------------------------------------------------

    public boolean isPurchaseButtonDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                purchaseButton
                        )
        ).isDisplayed();
    }


    public void clickPurchase() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                purchaseButton
                        )
        ).click();
    }


    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }


    public void unselectTermsAndConditions() {

        WebElement checkbox =
                wait.until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        termsCheckbox
                                )
                );

        if (checkbox.isSelected()) {

            WebElement label =
                    wait.until(
                            ExpectedConditions
                                    .presenceOfElementLocated(
                                            By.cssSelector(
                                                    "label[for='checkbox2']"
                                            )
                                    )
                    );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            label
                    );

            wait.until(
                    ExpectedConditions
                            .visibilityOf(label)
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            label
                    );

            wait.until(
                    ExpectedConditions
                            .elementSelectionStateToBe(
                                    termsCheckbox,
                                    false
                            )
            );
        }
    }


    public String getPurchaseSuccessMessage() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                purchaseSuccessMessage
                        )
        ).getText().trim();
    }
}