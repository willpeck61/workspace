Feature: complete change request
  As a developer 
  I want to process the change requests allocated to me
  So that we can make progress on project development

  @domain
  Scenario: process a change request
    Given I am logged in as "Alice"
    And an approved change request "Fault"
    When I complete the task with data "15-11-2015"
    Then the change request "Fault" is removed from my list

  @controller
  Scenario: process a change request (controller)
    Given I am logged in as "Alice"
    And an approved change request "Fault"
    When I complete the task with data "15-11-2015"
    Then I should get the next approved request allocated to me
    