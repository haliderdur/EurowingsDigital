@FlightStatus
Feature: Flight Status Check

  Background: User is on the flight status check page
    Given User navigates to flight status check page

  @TC01
  Scenario: Switch between Flight route and Flight number options
    When User clicks on the "Flight route" radio button
    Then Departure and destination airport fields and departure date field should be displayed
    When User clicks on the "Flight number" radio button
    Then Departure date and flight number input fields should be displayed


  @TC02
  Scenario: Populate departure and destination airport dropdowns in Flight route
    When User clicks on the "departure airport" dropdown
    Then List of available departure airports should be displayed
    When User clicks on the "destination airport" dropdown
    Then List of available destination airports should be displayed

  Scenario: Date picker allows selection of valid dates
    When User clicks on the date picker
    Then User should be able to select dates from "today" up to 3 days in the future
    And Dates 4 days in the future should be disabled

 # Scenario: Show flight status button activation
 #   When User has not filled in all required fields
 #   Then The Show flight status button should be disabled
 #   When User has filled in all required fields
 #   Then The Show flight status button should be enabled

  Scenario Outline: Check flight status by route
    When User clicks on the "Flight route" radio button
    And User clicks on the "departure airport" dropdown
    And User selects on the "<departure>" as the airport
    Then User should see "Cologne-Bonn" in departure airport dropdown
    When User clicks on the "destination airport" dropdown
    And User selects on the "<destination>" as the airport
    Then User should see "Berlin Brandenburg" in destination airport dropdown
    When User clicks on the date picker
    And User selects "<day>" as the departure date
    And User clicks the "Show flight status" button
    Then System should display correct "<departure>" and "<destination>" flights

    Examples:
      | departure | destination | day       |
      | CGN       | BER         | today     |
 #     | CGN       | BER         | tomorrow  |
 #     | CGN       | BER         | 6         |
 #     | DUS       | BCN         | yesterday |
 #     | DUS       | BCN         | tomorrow  |
 #     | DUS       | BCN         | 6         |


#  Scenario Outline: Check flight status by flight number
#    And User has selected "Flight number"
#    When User enters "<flight_number>" as the flight number
#    And User selects "<date>" as the departure date
#    And User clicks the "Show flight status" button
#    Then System should display the correct flight status for the flight
#
#    Examples:
#      | flight_number | date       |
#      | AA100         | 2024-08-03 |
#      | BA2490        | 2024-08-04 |
#
#  Scenario: No flights found
#    When User enters valid criteria for a non-existent flight
#    And User clicks the "Show flight status" button
#    Then System should display a "No flights found" message

# Scenario Outline: Handle invalid inputs
#   When User selects "<departure>" as the departure airport
#   And User selects "<destination>" as the destination airport
#   And User selects "<date>" as the departure date
#   And User clicks the "Show flight status" button
#   Then System should display an appropriate error message

#   Examples:
#     | departure | destination | date       | error_message                                |
#     | JFK       | JFK         | 2024-08-03 | Departure and destination cannot be the same |
#     | LAX       | LHR         | 2024-07-01 | Selected date is in the past                 |

# Scenario Outline: Responsive design
#   Given User is accessing the flight status check page
#   When User views the page on a "<device>" with screen size "<screen_size>"
#   Then All elements should be properly aligned and visible
#   And User should be able to interact with all form elements

#   Examples:
#     | device     | screen_size |
#     | Desktop    | 1920x1080   |
#     | Tablet     | 1024x768    |
#     | Smartphone | 375x667     |

# Scenario: Display correct time zone
#   When User checks the status of an international flight
#   Then System should display departure and arrival times in the correct local time zones
#   And Time zone abbreviations should be clearly indicated