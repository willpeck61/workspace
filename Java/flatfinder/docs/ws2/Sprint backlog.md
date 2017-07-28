##FLATFINDER
###Group Three

#Project Backlog v.2

##Project Goal

The goal as taken from the case study is as follows: to help students and professionals across the country find accommodation near their universities and workplaces respectively. FlatFinder will cater to users who wish to find accommodation for both long-term and short-term/overnight stays. The company has decided to develop a trial for a responsive site for use within the Leicester area which can be used on Desktop and mobile devices.

Our personal project goal is to develop a web application whose function is to provide a simplistic, easy-to-use, interface whereby users can search for nearby properties, connect with other users, and give feedback on the content shown. Various features will include a search engine, a user-to-user system of interaction, a mapping system with to scale navigation and realistic distance control. The system will support numerous account types allowing different levels of access to various functions. The overall system will be created using Java, HTML, MySQL, Javascript, and servlet technology.

### Marking guidelines

Levels of academic performance used:
*  **finished:**  corresponds to a top mark;
*  **in progress:**  corresponds to a low mark and is a clear an indicator of an area where improvement is required.
*  **not implamented** -  correspond to piece of work which haven't been started

### Priority guidelines
*  **HIGH:**
*  **MEDIUM**
*  **LOW**
*  **OPTIONAL**



### List of Gherking features

*  Web authorization
*  Express interest on property
*  Feedback on properties
*  Access my profile
*  Register as a member
*  Search properties


###Gherkin notations (Detailed feature specification)

**Feature: Web authorization**
priority: HIGH
completion:finished

	@security
	Scenario Outline: Authentication
		Given Im am a <ROLE> with username <USR> and password <PWD>
		When Im access <URL> 
		Then My authentication is <isAuth> with role <ROLE>

**Examples:** 
    
      | URL      | USR       | PWD        | ROLE          | isAuth |
      | "/login" | "Alice"   | "password" | "SEARCHER"    | true   |
      | "/login" | "Alice"   | "invalid"  | "SEARCHER"    | false  |
      | "/login" | "Bob"     | "admin"    | "ADMIN"       | true   |
      | "/login" | "Bob"     | "invalid"  | "ADMIN"       | false  |
      | "/login" | "invalid" | "invalid"  | "LANDLORD"    | false  |
      | "/login" | "John"    | "password" | "LANDLORD"	  | true   |
      | "/login" | "John"    | "invalid"  | "LANDLORD"    | false  |

	@security
	 Scenario: secure password
		Given an developer "Alice"
		When Im retrieve the password from the user credentials stored in the repository 
		Then Im should get the encrypted password "password"

	@security
	Scenario Outline: Authorization
		Given Im am a <ROLES> role with username <USER> and password <PW>
		When Im access a <Service>
		Then My authentication is then <isAuthorized> with role <ROLES>
		
**Examples:** 
    
      | Service                    | USER    | PW			| ROLES            | isAuthorized |
      | "/landlord-dashboard"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false        |
      | "/landlord-dashboard"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | false        |
      | "/landlord-dashboard"      | "John"  | "password"   | "ROLE_LANDLORD"  | true         |
      | "/landlord-dashboard"      | "anon"  | "password"   | "anon"           | false        |
      | "/view-profile"            | "Alice" | "password"   | "ROLE_SEARCHER"  | true         |
      | "/view-profile"            | "Bob"   | "admin"      | "ROLE_ADMIN"     | true         |
      | "/view-profile"            | "John"  | "password"   | "ROLE_LANDLORD"  | true         |
      | "/view-profile"            | "anon"  | "password"   | "anon"           | false        |
      | "/amend-user"              | "Alice" | "password"   | "ROLE_SEARCHER"  | true         |
      | "/amend-user"              | "Bob"   | "admin"      | "ROLE_ADMIN"     | true         |
      | "/amend-user"              | "John"  | "password" 	| "ROLE_LANDLORD"  | true         |
      | "/amend-user"              | "anon"  | "password" 	| "anon"           | false        |
      | "/upload"				   | "Alice" | "password"   | "ROLE_SEARCHER"  | true         |
      | "/upload" 			       | "Bob"   | "admin"      | "ROLE_ADMIN"     | true         |
      | "/upload"                  | "John"  | "password" 	| "ROLE_LANDLORD"  | true         |
      | "/upload"                  | "anon"  | "password" 	| "anon"           | false        |
	
**Feature: Access my profile**
priority: HIGH
completion:finished
	
	As a searcher
	I want to access my profile
	In order to amend my details
	
	@controller
	Scenario: access my profile page
		Given im the user "alice" on the home page
		When click the access "profile" link
		Then my profile page is displayed
		
	@controller
	Scenario: upload profile picture
		Given im the user "alice" on my profile page
		When click to submit "testprofle" image file
		Then the "testprofile" exists in the "alice" folder


**Feature: Register as a member**
priority: HIGH
completion:finished

  	As a site visitor
	I want to join the website
	In order to find property to rent

  	@domain 
	Scenario Outline: register as any role
		Given im completing the "register-form" form
		When the Register form is submitted with <FIRSTNAME>, <LASTNAME>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <ROLE>, <USERNAME>
		And <PASSWORD>
		Then we should see that <FIRSTNAME>, <LASTNAME>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <ROLE>, <USERNAME>
		And<PASSWORD> has been registered
		And the folder structure for <USERNAME> has been created

**Examples:**

	  | FIRSTNAME  | LASTNAME  | ADDRESS1 | ADDRESS2       | ADDRESS3    | POSTCODE  | ROLE      | USERNAME   | PASSWORD   |
	  | "Will"     | "Smith"   | "86"     | "West Road"    | "Leicester" | "LE27TG" | "SEARCHER" | "will"     | "password" |
	  | "James"    | "Wick"    | "22a"    | "North Street" | "Leicester" | "LE11NY" | "LANDLORD" | "james"    | "password" |
  
	@controller
	Scenario: attempt to register twice same username
		Given im completing the registration form with the username "alice"
		When the Register form is submitted with "Alice", "Smith", "address1", "address2", "address3", "postcode", "SEARCHER", 		"alice" and "password"
		Then an error message "error" is returned
		
		
**Feature: Search properties**
priority: HIGH
completion:finished

	As a registered user
	I want to search for a property
	In order to view properties
	
	Scenario: search for properties by postcode
		Given im searching for "LE2 1TA"
		When ive submitted the search form
	@domain  
	Scenario: search for properties by postcode
		Given im searching for "LE2 1TA"
		When ive submitted the search form
		Then the search page is displayed with "LE2 1TA" as the result
		
	@domain  
	Scenario: search for properties by postcode with max 4 bedrooms
		Given im searching for "LE2 1TA" with "4" bedrooms max
		When ive submitted the search form
		Then the search page is displayed with "LE2 1TA" and max "4" bedrooms 
	
	@domain  
	Scenario: search for properties by postcode
		Given im searching for "QW1 1ES"
		When ive submitted the search form
		Then the search page is displayed with no results with postcode "QW1 1ES"


**Feature: Show interest on property** 
priority: MEDIUM
completion:finished

 	As a registered user
 	I want to search for a property
 	In order to express interest in it
 	
	@domain  
	Scenario: register as any role
		Given im interested in "LE11HA"
		When the show interest button is clicked
		Then the notification "LE11HA" is sent to the owner id "1"
		
IN PROGRESS	

**Feature: Feedback on properties**
priority: MEDIUM
completion: in progress

   	Background:
		Given Im logged in as <sname>
		And has selected <pid> on the property page
		And an admin approves the rating request
		
 	Scenario: Searcher <sname> rates a property 
		Given Im logged in as <sname>
		And want to rate property <pid>
		When submitting the rating "6" 
		Then the rating request is sent to the admin for approval
  
   	Scenario: Admin approves <sname> rating request
		Given Im logged in as admin
		And want to approve a rating request
		When clicking the approve on the request 
		Then the rating should appear on property page <pid>


