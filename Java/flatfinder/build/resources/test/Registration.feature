Feature: Register as a member
  As a site visitor
  I want to join the website
  In order to find property to rent

  @controller
  Scenario: open registration page
    Given im on the flatfinder home page
    When the registration link is clicked
    Then the registration page should open

  @domain  
  Scenario: register as any role
	Given im completing the "Register" form
	And the form is completed with
		| FIRSTNAME  | LASTNAME  | ADDRESS1 | ADDRESS2       | ADDRESS3    | POSTCODE  | PHONE        | ROLE       | USERNAME   | PASSWORD   |	
		| "Alice"    | "Smith"	 | "86"     | "West Road"    | "Leicester" | "LE2 7TG" | "0778542125" | "searcher" | "alice"    | "password" |
		| "John"     | "Wick"    | "22a"	| "North Street" | "Leicester" | "LE1 1NY" | "0116225124" | "landlord" | "john"     | "password" |
    When the Register form is submitted
    Then I should see that <FIRSTNAME> with <POSTCODE> and <ROLE> has been registered
    
    