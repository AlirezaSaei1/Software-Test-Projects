Feature: Exchange a ticket successfully
  Someone wants to exchange his/her ticket, the exchanging process must be correct

  Scenario: Exchange Ticket
    Given Exchanging requirements are met
    When Request to exchange a ticket from a trip
    Then Exchange must be done for a trip
