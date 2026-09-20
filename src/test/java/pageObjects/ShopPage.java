package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShopPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ShopPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        Integer.parseInt(
                                utils.ConfigReader
                                        .getProperty("explicitWait")
                        )
                )
        );
    }

    // --------------------------------------------------
    // Page elements
    // --------------------------------------------------

    private By shopPageTitle =
            By.xpath("//h1[contains(normalize-space(),'Shop Name')]");

    private By productCards =
            By.cssSelector(".card");

    private By productNames =
            By.cssSelector(".card-title");

    private By productPrices =
            By.cssSelector(".card-body h5");

    private By addButtons =
            By.cssSelector(".card-footer button");

    private By checkoutButton =
            By.cssSelector("a.nav-link.btn.btn-primary");

    private By cartCount =
            By.cssSelector("a.nav-link.btn.btn-primary");


    // --------------------------------------------------
    // Page validation
    // --------------------------------------------------

    public boolean isShopPageDisplayed() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(shopPageTitle)
        ).isDisplayed();
    }


    // --------------------------------------------------
    // Product methods
    // --------------------------------------------------

    public int getProductCount() {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productCards)
        );

        return driver.findElements(productCards).size();
    }


    public List<WebElement> getProducts() {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productCards)
        );

        return driver.findElements(productCards);
    }


    public List<WebElement> getProductNames() {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productNames)
        );

        return driver.findElements(productNames);
    }


    public List<WebElement> getProductPrices() {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productPrices)
        );

        return driver.findElements(productPrices);
    }


    // --------------------------------------------------
    // Add single product
    // --------------------------------------------------

    public void addProduct(String productName) {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(productCards)
        );

        List<WebElement> products =
                driver.findElements(productCards);

        for (WebElement product : products) {

            String name =
                    product.findElement(productNames)
                            .getText()
                            .trim();

            if (name.equalsIgnoreCase(productName)) {

                WebElement addButton =
                        product.findElement(addButtons);

                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(addButton)
                );

                addButton.click();

                return;
            }
        }

        throw new RuntimeException(
                "Product not found: " + productName
        );
    }


    // --------------------------------------------------
    // Add multiple products
    // --------------------------------------------------

    public void addProducts(List<String> productNames) {

        for (String productName : productNames) {

            addProduct(productName);
        }
    }


    // --------------------------------------------------
    // Cart / Checkout
    // --------------------------------------------------

    public void clickCheckout() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(checkoutButton)
        ).click();

        // Wait until Cart page is actually loaded
        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                By.cssSelector("table.table")
                        )
        );
    }


    public String getCartCount() {

        WebElement cart =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(cartCount)
                );

        return cart.getText()
                .trim()
                .replaceAll("[^0-9]", "");
    }





    private String extractCartCount() {

        String cartText =
                wait.until(
                                ExpectedConditions
                                        .visibilityOfElementLocated(cartCount)
                        )
                        .getText()
                        .trim();

        String count =
                cartText.replaceAll(
                        "[^0-9]",
                        ""
                );

        return count;
    }


    private int getCartCountAsInt() {

        String count = extractCartCount();

        if (count.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(count);
    }
}