Feature: web authorization

@security
  Scenario Outline: authentication
    Given I am a <ROLE> with username <USR> and password <PWD>
    When I access <URL> 
    Then My authentication is <isAuth> with role <ROLE>
    Examples: 
      | URL           | USR       | PWD        | ROLE         | isAuth |
      | "/login-form" | "Alice"   | "password" | "DEVELOPER"  | true   |
      | "/login-form" | "Alice"   | "invalid"  | "DEVELOPER"  | false  |
      | "/login-form" | "Bob"     | "admin"    | "MANAGER"    | true   |
      | "/login-form" | "Bob"     | "invalid"  | "MANAGER"    | false  |
      | "/login-form" | "invalid" | "invalid"  | "MANAGER"    | false  |

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
	  | "/assess-change-request"   | "Alice" | "password"	| "DEVELOPER" | false        |
	  | "/assess-change-request"   | "Bob"   | "admin"		| "MANAGER"   | true         |
	  | "/complete-change-request" | "Alice" | "password"   | "DEVELOPER" | true         |
	  | "/complete-change-request" | "Bob"   | "admin"      | "MANAGER"   | true         |
	  | "/request-change"          | "Alice" | "password"   | "DEVELOPER" | true         |
	  | "/request-change"          | "Bob"   | "admin" 	    | "MANAGER"   | true         |
