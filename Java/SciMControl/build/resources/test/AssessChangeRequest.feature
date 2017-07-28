Feature: Assess change request
  In order to either approve or reject change requests
  As a project manager
  I want to revise change requests

  @domain
  Scenario: accept a change request
    Given the pending change request "Fault" in a list of pending requests
    When I accept it with priority "high" for developer "Alice" with deadline "17-11-2015"
    Then the change request "Fault" is accepted
    And removed from the list of pending requests

  @controller  
  Scenario: accept a change request (controller)
    Given the pending change request "Fault" in a list of pending requests
    When I accept it with priority "high" for developer "Alice" with deadline "17-11-2015"
    Then I should see the next pending request (if any)

  @domain
  Scenario: reject a change request
    Given the pending change request "New Feature" in a list of pending requests
    When I reject it with explanation "feature already considered in the new release candidate"
    Then the change request "New Feature" is rejected
    And removed from the list of pending requests

  @controller
  Scenario: reject a change request (controller)
    Given the pending change request "New Feature" in a list of pending requests
    When I reject it with explanation "feature already considered in the new release candidate"
    Then I should see the next pending request (if any)

  @controller
  Scenario: cancel assessment
    Given I am assessing change requests
    When I request to cancel the assessment
    Then I stop seeing change requests
	# This could be completed by indicating information 
    # about the web page that should be displayed
    # (not mentioned in the worksheet)  
    
    