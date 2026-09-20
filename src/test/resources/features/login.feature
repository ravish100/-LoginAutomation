
Feature: Login functionality

  Background:
    Given User is on the Login Page

  @regression @login @positive
  Scenario: Valid login credentials
    When User enters credentials for "validUser"
    And User clicks on login button
    Then User should see "successful login"

  @regression @login @negative
  Scenario: Invalid username with valid password
    When User enters credentials for "invalidUser"
    And User clicks on login button
    Then User should see "Invalid credentials"

  @regression @login @negative
  Scenario: Valid username with invalid password
    When User enters credentials for "invalidPassword"
    And User clicks on login button
    Then User should see "Invalid credentials"

  @regression @login @negative
  Scenario: Invalid username with invalid password
    When User enters credentials for "invalidUserPassword"
    And User clicks on login button
    Then User should see "Invalid credentials"

  @regression @login @validation
  Scenario: Empty username with valid password
    When User enters credentials for "emptyUsername"
    And User clicks on login button
    Then User should see "Empty username/password"

  @regression @login @validation
  Scenario: Valid username with empty password
    When User enters credentials for "emptyPassword"
    And User clicks on login button
    Then User should see "Empty username/password"

  @regression @login @validation
  Scenario: Empty username and empty password
    When User enters credentials for "emptyCredentials"
    And User clicks on login button
    Then User should see "Empty username/password"

  @regression @login @validation
  Scenario: Username with leading and trailing spaces
    When User enters credentials for "usernameWithSpaces"
    And User clicks on login button
    Then User should see "Invalid credentials"

  @regression @login @negative
  Scenario: Password containing special characters
    When User enters credentials for "specialCharacterPassword"
    And User clicks on login button
    Then User should see "Invalid credentials"

  @regression @login @security
  Scenario: Invalid credentials should not allow access to Shop
    When User enters credentials for "invalidUserPassword"
    And User clicks on login button
    Then User should remain on the Login Page

