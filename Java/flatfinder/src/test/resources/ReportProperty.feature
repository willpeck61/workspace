Feature: Report a property
	As a registered user
	I want to submit a report
	In order to complain about a property
  
	@domain  
	Scenario: Report a property
		Given im logged in as "Alice"
		When the user "Alice" submits report on property with id "1" and subject "Reporting property 1" and description "Property Report"
		Then the report on property "1" exists made by "Alice"