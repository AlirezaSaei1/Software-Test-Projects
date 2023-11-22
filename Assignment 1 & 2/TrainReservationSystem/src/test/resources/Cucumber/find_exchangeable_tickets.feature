Feature: Exchangeable Tickets are shown
  Someone wants to exchange his/her ticket, available tickets for exchanges will be shown

  Scenario: Show Exchangeable Tickets
    Given Exchange requirements are met
    When Request to see available tickets for exchange
    Then A list of all available tickets are shown
