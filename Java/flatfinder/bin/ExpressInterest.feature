Feature: Show interest on property
	As a registered user
	I want to search for a property
	In order to express interest in it
  
	@domain  
	Scenario: Express interest in a property
		Given im logged in as "Alice"
		When the show interest button is clicked with the property id 1
		Then a notification is sent to the owner id 1 with the property id 1