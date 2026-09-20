Feature: Shop functionality

  @regression @shop @positive
  Scenario: Verify Shop page is displayed after successful login
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page

  @regression @shop @positive
  Scenario: Verify products are displayed on Shop page
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    And Shop page should display products

  @regression @shop @validation
  Scenario: Verify product names are displayed on Shop page
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    And Shop page should display product names

  @regression @shop @validation
  Scenario: Verify product prices are displayed on Shop page
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    And Shop page should display product prices

  @regression @shop @cart @positive
  Scenario: Add a single product to the cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    Then Cart count should be "1"

  @regression @shop @cart @positive
  Scenario: Add two products to the cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"

  @regression @shop @cart @validation
  Scenario: Verify cart count after adding products
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    Then Cart count should be "1"

  @regression @shop @cart @navigation
  Scenario: Open cart after adding a product
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    Then Cart page should be displayed

  @regression @shop @cart @validation
  Scenario: Verify number of products in cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    Then Cart should contain "2" products

  @regression @shop @cart @negative
  Scenario: Remove a product from the cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    When User removes product "iphone X" from the cart
    Then Cart should contain "1" product

  @regression @shop @cart @validation
  Scenario: Verify cart count after removing a product
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    When User removes product "Samsung Note 8" from the cart
    Then Cart should contain "1" product
    And Cart should contain product "iphone X"

  @regression @shop @cart @validation
  Scenario: Verify total price of products in cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    Then Cart total should be "185000"

  @regression @shop @cart @navigation
  Scenario: Continue shopping from cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Continue Shopping
    Then User should return to the Shop page

  @regression @shop @cart @checkout
  Scenario: Proceed to checkout from cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed

  @regression @checkout @validation
  Scenario: Verify checkout section elements
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    And Checkout should display delivery location message
    And Checkout should display purchase instruction
    And Checkout should display terms and conditions



  @regression @checkout @positive
  Scenario: Enter delivery location during checkout
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    Then Delivery location should be "United States"



  @regression @checkout @positive
  Scenario: Select terms and conditions during checkout
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User selects the terms and conditions checkbox
    Then Terms and conditions checkbox should be selected

  @regression @checkout @validation
  Scenario: Verify Purchase button is displayed during checkout
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    And Checkout should display Purchase button

  @regression @checkout @negative
  Scenario: Attempt purchase without accepting terms and conditions
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User clicks Purchase without accepting terms
    Then User should remain on the Checkout page

  @regression @checkout @purchase @positive
  Scenario: Successfully purchase a product
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    And User selects the terms and conditions checkbox
    When User clicks Purchase
    Then Purchase confirmation message should be displayed


  @regression @checkout @purchase @validation
  Scenario: Verify purchase confirmation message
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    And User selects the terms and conditions checkbox
    When User clicks Purchase
    Then Purchase confirmation message should be displayed


  @regression @checkout @purchase @validation
  Scenario: Verify order delivery confirmation
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    And User selects the terms and conditions checkbox
    When User clicks Purchase
    Then Purchase confirmation message should be displayed


  @regression @checkout @purchase @positive
  Scenario: Purchase product with Canada delivery location
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "Canada"
    Then Delivery location should be "Canada"
    When User selects the terms and conditions checkbox
    When User clicks Purchase
    Then Purchase confirmation message should be displayed


  @regression @checkout @cart @purchase @positive
  Scenario: Purchase multiple products
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"
    When User opens the cart
    Then Cart should contain "2" products
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    And User selects the terms and conditions checkbox
    When User clicks Purchase
    Then Purchase confirmation message should be displayed


  @regression @checkout @cart @validation
  Scenario: Verify cart total before purchase
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"
    When User opens the cart
    Then Cart total should be "185000"


  @regression @checkout @cart @validation
  Scenario: Verify cart count before checkout
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"
    When User opens the cart
    Then Cart should contain "2" products
    When User clicks Checkout
    Then Checkout page should be displayed


  @regression @checkout @negative
  Scenario: Attempt purchase without accepting terms and conditions
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User clicks Purchase without accepting terms
    Then User should remain on the Checkout page


  @regression @checkout @validation
  Scenario: Unselect terms and conditions checkbox
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User selects the terms and conditions checkbox
    Then Terms and conditions checkbox should be selected
    When User unselects the terms and conditions checkbox
    Then Terms and conditions checkbox should not be selected


  @regression @checkout @purchase @e2e
  Scenario: Complete purchase flow with multiple products
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"
    When User opens the cart
    Then Cart should contain "2" products
    Then Cart total should be "185000"
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    Then Delivery location should be "United States"
    When User selects the terms and conditions checkbox
    Then Terms and conditions checkbox should be selected
    When User clicks Purchase
    Then Purchase confirmation message should be displayed

  @regression @cart @validation
  Scenario: Add the same product twice
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    Then Cart count should be "1"
    When User adds product "iphone X" to the cart
    Then Cart count should be "2"


  @regression @cart @validation
  Scenario: Verify same product quantity increases when added again
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    Then Cart count should be "1"
    When User adds product "iphone X" to the cart
    Then Cart count should be "2"
    And User opens the cart
    Then Cart should contain "1" product
    And Cart should contain product "iphone X"


  @regression @cart @validation
  Scenario: Verify cart contains multiple selected products
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    Then Cart should contain "2" products
    And Cart should contain product "iphone X"
    And Cart should contain product "Samsung Note 8"


  @regression @cart @negative
  Scenario: Remove all products from cart
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    When User removes product "iphone X" from the cart
    Then Cart should contain "1" product
    When User removes product "Samsung Note 8" from the cart
    Then Cart should contain "0" product


  @regression @cart @validation
  Scenario: Verify cart is empty after removing all products
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    When User removes product "iphone X" from the cart
    When User removes product "Samsung Note 8" from the cart
    Then Cart should contain "0" product

  @regression @cart @navigation
  Scenario: Continue shopping returns user to Shop page
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"
    When User opens the cart
    When User clicks Continue Shopping
    Then User should return to the Shop page


  @regression @checkout @cart @validation
  Scenario: Checkout should preserve selected products
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    And User opens the cart
    Then Cart should contain "2" products
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    And User selects the terms and conditions checkbox
    Then Terms and conditions checkbox should be selected


  @regression @checkout @validation
  Scenario: Change delivery location during checkout
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "Canada"
    Then Delivery location should be "Canada"
    When User enters delivery location "United States"
    Then Delivery location should be "United States"


  @regression @checkout @purchase @validation
  Scenario: Purchase button remains available after changing delivery location
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User opens the cart
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "Canada"
    Then Delivery location should be "Canada"
    When User enters delivery location "United States"
    Then Delivery location should be "United States"
    Then Checkout should display Purchase button


  @regression @e2e @purchase @positive
  Scenario: Complete end to end purchase with cart validation
    Given User is on the Login Page
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see the Shop page
    When User adds product "iphone X" to the cart
    And User adds product "Samsung Note 8" to the cart
    Then Cart count should be "2"
    And User opens the cart
    Then Cart should contain "2" products
    And Cart should contain product "iphone X"
    And Cart should contain product "Samsung Note 8"
    Then Cart total should be "185000"
    When User clicks Checkout
    Then Checkout page should be displayed
    When User enters delivery location "United States"
    Then Delivery location should be "United States"
    When User selects the terms and conditions checkbox
    Then Terms and conditions checkbox should be selected
    When User clicks Purchase
    Then Purchase confirmation message should be displayed