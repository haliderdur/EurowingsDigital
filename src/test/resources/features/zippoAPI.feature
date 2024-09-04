@TEST @API
Feature: Zippopotam API test


  @API01
  Scenario: Verify API response for Stuttgart
    Given I make a GET request to zippopotam API
    Then the status code should be 200 and content type should be "application/json"
    And the country should be "Germany"
    And the state should be "Baden-Württemberg"
    And the place name for the post code "70597" should be "Stuttgart Degerloch"
    And the response time should be less than 2 second

  @API02
  Scenario Outline: Verify place name for given country and postal code
    Given I make an GET request for country "<Country>" and postal code "<Postal Code>"
    Then the status code should be 200 and content type should be "application/json"
    And the place name for post code "<Postal Code>" should be "<Place Name>"
    And the response time should be less than 2 second

    Examples:
      | Country | Postal Code | Place Name    |
      | us      | 90210       | Beverly Hills |
      | de      | 74072       | Heilbronn     |
      | es      | 28001       | Madrid        |
      | ca      | B2R         | Waverley      |
      | us      | 90230       | Culver City   |


