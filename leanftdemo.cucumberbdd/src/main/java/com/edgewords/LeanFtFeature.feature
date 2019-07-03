#Feature File cannot be run directly
#You must run LeanFTTest.java test runner

Feature: Edgewords Shop Search

  Scenario: Search for cap
    Given I am on the Edgewords Shop Demo homepage
    When I search for "cap"
    Then a "cap" shown shown in the results