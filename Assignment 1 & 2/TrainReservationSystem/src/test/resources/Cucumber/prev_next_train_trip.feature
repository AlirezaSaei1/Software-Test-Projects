Feature: Checking previous and next trip of a train
  Someone has a trip of a train and wants to see previous and next trip of that train

  Scenario: Show previous and next trip of a train
    Given Train has more that three trips and has a previous and next trip
    When Request to see available earlier and later trips of a train
    Then The correct successor and predecessor trips are shown
