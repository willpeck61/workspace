Feature: Report a user
	As a registered user
	I want to submit a report
	In order to complain about a user
  
	@domain  
	Scenario: Express interest in a property
		Given im logged in as "Alice"
		When the user "Alice" submits a report on "Adam"
		Then the report on "Adam" exists made by "Alice"