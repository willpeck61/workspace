Feature: request change
  As a developer
  I want to create a change request
  So that issues or request new features can be reported

  Background: 
    Given a SCI "SCI_1"
    And a head version "version_2"
  
  @domain
  Scenario: report a fault
    Given a problem description "Fault" 
    And a proposed solution "Missing dependency"
    When I request a change request for reporting a fault
    Then the system stores the fault request "Fault" for the head version "version_2" of "SCI_1" with solution "Missing dependency"
	
  @controller
  Scenario: report a fault (controller)
    Given a problem description "Fault" 
    And a proposed solution "Missing dependency"
    When I request a change request for reporting a fault
    And shows a summary of the change request 
    
  @domain  
  Scenario: request a new feature
    Given a problem description "New Feature" 
    And a proposed solution "Extension of Module A"
    When I request a new feature
    Then the system stores the new feature request "New Feature" for the head version "version_2" of "SCI_1" with solution "Extension of Module A"

  @controller 
  Scenario: request a new feature (controller)
    Given a problem description "New Feature" 
    And a proposed solution "Extension of Module A"
    When I request a new feature
    Then The system shows a summary of the change request 
    
