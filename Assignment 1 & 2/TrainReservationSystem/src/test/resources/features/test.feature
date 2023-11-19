Feature: Add City
  Scenario: Add a new city to cities list
    Given the system is on
    When the new city object is added
    Then the new city must be added to the cities list
