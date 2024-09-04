@TEST @UI
Feature: Flight Status Check
  Agile story: As a user, I want to check the flight status by flight route or flight number
  so that I can plan my travel accordingly.

  Background: User is on the flight status check page
    Given User navigates to flight status check page
    And User verifies page title is "Flight status - Information - Eurowings"
    And User accepts privacy settings

  @UI01
  Scenario Outline: Check flight status by flight route
    Given The Show flight status button should be disabled by default
    When User clicks on the "FLIGHT_ROUTE" radio button
    And User clicks on the "departure airport" dropdown
    And User selects on the "<departure>" as the airport name
    Then User should see "<departure airport name>" in departure airport dropdown
    When User clicks on the "destination airport" dropdown
    And User selects on the "<destination>" as the airport name
    Then User should see "<destination airport name>" in destination airport dropdown
    When User clicks on the date picker
    Then User should be able to select dates from today up to 3 days in the future and past
    And User selects "<day>" as the departure date
    And User clicks the show flight status button
    Then User should see "<day>" in result
    And  System should display correct "<departure>" and "<destination>"

    Examples:
      | departure | destination | day | departure airport name | destination airport name |
      | CGN       | BER         | 17   | Cologne-Bonn           | Berlin Brandenburg       |
   #  | BER       | CGN         | 14   | Berlin Brandenburg     | Cologne-Bonn             |
   #  | DUS       | LHR         | 16   | Dusseldorf             | London Heathrow          |
   #  | BCN       | HAM         | 13   | Barcelona              | Hamburg                  |


  @UI02
  Scenario Outline: Check flight status by flight number
    Given The Show flight status button should be disabled by default
    When User clicks on the "FLIGHT_NUMBER" radio button
    And User enters "<flight number>" as the flight number
    And User clicks on the date picker
    Then User should be able to select dates from today up to 3 days in the future and past
    And User selects "<day>" as the departure date
    And User clicks the show flight status button
    Then User should see "<day>" in result
    And  System should display correct "<departure>" and "<destination>" and "<flight number>"

    Examples:
      | flight number | day | departure | destination |
      | EW12          | 16   | CGN       | BER         |
      | EW13          | 14   | BER       | CGN         |
      | EW9468        | 18   | DUS       | LHR         |
      | EW7521        | 15   | BCN       | HAM         |