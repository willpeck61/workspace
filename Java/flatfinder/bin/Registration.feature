Feature: Register as a member
  As a site visitor
  I want to join the website
  In order to find property to rent

  @domain  
  Scenario Outline: register as any role
	Given im completing the "register-form" form
    When the Register form is submitted with <FIRSTNAME>, <LASTNAME>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <ROLE>, <USERNAME> and <PASSWORD>  
    Then we should see that <FIRSTNAME>, <LASTNAME>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <ROLE>, <USERNAME> and <PASSWORD> has been registered
	And the folder structure for <USERNAME> has been created
    Examples:
        | FIRSTNAME  | LASTNAME  | ADDRESS1 | ADDRESS2       | ADDRESS3    | POSTCODE | ROLE    | USERNAME   | PASSWORD   |	
		| "Will"     | "Smith"	 | "86"     | "West Road"    | "Leicester" | "LE27TG" | "2" 	| "will"     | "password" |
		| "James"    | "Wick"    | "22a"	| "North Street" | "Leicester" | "LE11NY" | "3" 	| "james"    | "password" |
  
  @controller
  Scenario: attempt to register twice same username
	Given im completing the registration form with the username "alice"
	When the Register form is submitted with "Alice", "Smith", "address1", "address2", "address3", "postcode", "SEARCHER", "alice" and "password"
	Then an error message "error" is returned