Feature: web authorization

@security
  Scenario Outline: authentication
    Given I am a <ROLE> with username <USR> and password <PWD>
    When I access <URL> 
    Then My authentication is <isAuth> with role <ROLE>
    Examples: 
      | URL           | USR       | PWD        | ROLE          | isAuth |
      | "/login-form" | "Alice"   | "password" | "SEARCHER"    | true   |
      | "/login-form" | "Alice"   | "invalid"  | "SEARCHER"    | false  |
      | "/login-form" | "Bob"     | "admin"    | "ADMIN"       | true   |
      | "/login-form" | "Bob"     | "invalid"  | "ADMIN"    | false  |
      | "/login-form" | "invalid" | "invalid"  | "LANDLORD"    | false  |
	  | "/login-form" | "John"	  | "password" | "LANDLORD"	   | true   |
	  | "/login-form" | "John"    | "invalid"  | "LANDLORD"    | false  |
	  

@security
  Scenario: secure password
     Given a developer "Alice"
     When I retrieve the password from the user credentials stored in the repository    
     Then I should get the encrypted password "password"

@security
  Scenario Outline: authorization   
     Given I am a <ROLES> role with username <USER> and password <PW>
     When I access a <Service>
     Then My authentication is then <isAuthorized> with role <ROLES>
     Examples: 
	  | Service                    | USER    | PW			| ROLES       | isAuthorized |
	  | "/admin-home"              | "Alice" | "password"	| "SEARCHER"  | false        |
	  | "/admin-home"              | "Bob"   | "admin"		| "ADMIN"     | true         |
	  | "/admin-home"              | "John"  | "password"   | "LANDLORD"  | false        |
	  | "/user-account"            | "Alice" | "password"   | "SEARCHER"  | true         |
	  | "/user-account"            | "Bob"   | "admin"      | "ADMIN"     | true         |
	  | "/user-account"            | "John"  | "password"   | "LANDLORD"  | true         |
	  | "/report-express-interest" | "Alice" | "password"   | "SEARCHER"  | true         |
	  | "/report-express-interest" | "Bob"   | "admin"      | "ADMIN"     | true         |
	  | "/report-express-interest" | "John"  | "password" 	| "LANDLORD"  | true         |
	  | "/buddy-up"				   | "Alice" | "password"   | "SEARCHER"  | true         |
	  | "/buddy-up" 			   | "Bob"   | "admin"      | "ADMIN"     | false        |
	  | "/buddy-up"                | "John"  | "password" 	| "LANDLORD"  | false        |
	  | "/file-report"			   | "Alice" | "password"   | "SEARCHER"  | true         |
	  | "/file-report" 			   | "Bob"   | "admin"      | "ADMIN"     | false        |
	  | "/file-report"             | "John"  | "password" 	| "LANDLORD"  | true         |  