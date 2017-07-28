Feature: web authorization

@security
  Scenario Outline: authentication
    Given Im am a <ROLE> with username <USR> and password <PWD>
    When Im access <URL> 
    Then My authentication is <isAuth> with role <ROLE>
    Examples: 
      | URL      | USR       | PWD        | ROLE          | isAuth |
      | "/login" | "Alice"   | "password" | "SEARCHER"    | true   |
      | "/login" | "Alice"   | "invalid"  | "SEARCHER"    | false  |
      | "/login" | "Bob"     | "admin"    | "ADMIN"       | true   |
      | "/login" | "Bob"     | "invalid"  | "ADMIN"       | false  |
      | "/login" | "invalid" | "invalid"  | "LANDLORD"    | false  |
	  | "/login" | "John" 	 | "password" | "LANDLORD"	  | true   |
	  | "/login" | "John"    | "invalid"  | "LANDLORD"    | false  |
	  
@security
  Scenario: secure password
     Given an developer "Alice"
     When Im retrieve the password from the user credentials stored in the repository    
     Then Im should get the encrypted password "password"

@security
  Scenario Outline: authorization   
     Given Im am a <ROLES> role with username <USER> and password <PW>
     When Im access a <Service> 
     Then My authentication is then <isAuthorized> with role <ROLES> to return <Result>
     Examples: 
	  | Service                    	  | USER    | PW			| ROLES            | isAuthorized | Result	|
	  | "/"              		   	  | "Baddy" | "password" 	| "ROLE_SUSPENDED" | false        | 403  	|
	  | "/landlord-dashboard"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | false        | 403		|
	  | "/landlord-dashboard"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | false        | 403		|
	  | "/landlord-dashboard"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/landlord-dashboard"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/landlord-dashboard"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/view-profile"            	  | "Alice" | "password"    | "ROLE_SEARCHER"  | true         | 200		|
	  | "/view-profile"            	  | "Bob"   | "admin"       | "ROLE_ADMIN"     | true         | 200 	|
	  | "/view-profile"            	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200	 	|
	  | "/view-profile"            	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/view-profile"            	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
 	  | "/amend-user"              	  | "Alice" | "password"    | "ROLE_SEARCHER"  | true         | 302		|
	  | "/amend-user"              	  | "Bob"   | "admin"       | "ROLE_ADMIN"     | true         | 302		|
	  | "/amend-user"              	  | "John"  | "password" 	| "ROLE_LANDLORD"  | true         | 302		|
	  | "/amend-user"              	  | "anon"  | "password" 	| "anon"           | false        | 403		|
	  | "/amend-user"              	  | "Baddy" | "password" 	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/upload"				   	  | "Alice" | "password"    | "ROLE_SEARCHER"  | true         | 200		|
	  | "/upload" 			       	  | "Bob"   | "admin"       | "ROLE_ADMIN"     | true         | 200		|
	  | "/upload"                  	  | "John"  | "password" 	| "ROLE_LANDLORD"  | true         | 200		|
	  | "/upload"                  	  | "anon"  | "password" 	| "anon"           | false        | 403		|
	  | "/upload"                  	  | "Baddy" | "password" 	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/addUser"                 	  | "Alice" | "password"	| "ROLE_SEARCHER"  | false        | 405		|
	  | "/addUser"                 	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | false        | 405		|
	  | "/addUser"                 	  | "John"  | "password"    | "ROLE_LANDLORD"  | false        | 405		|
	  | "/addUser"                 	  | "anon"  | "password"    | "anon"           | false        | 405		|
	  | "/addUser"                 	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 405		|
	  | "/get-users?type=buds"     	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/get-users?type=buds"     	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/get-users?type=buds"     	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/get-users?type=buds"     	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/get-users?type=buds"     	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|	
	  | "/add-interest?addint=x"   	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 302		|
	  | "/add-interest?addint=x"   	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 302		|
	  | "/add-interest?addint=x"   	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 302		|	
	  | "/add-interest?addint=x"   	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/add-interest?addint=x"   	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/remove-interest?remint=x"   | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 302		|
	  | "/remove-interest?remint=x"   | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 302		|
	  | "/remove-interest?remint=x"   | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 302		|	
	  | "/remove-interest?remint=x"   | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/remove-interest?remint=x"   | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/users"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/users"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/suspend?id=1"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/users/suspend?id=1"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 302		|
	  | "/admin-dashboard/users/suspend?id=1"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/suspend?id=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/suspend?id=1"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/unsuspend?id=1"      | "Alice" | "password"		| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/users/unsuspend?id=1"      | "Bob"   | "admin"			| "ROLE_ADMIN"     | true         | 302		|
	  | "/admin-dashboard/users/unsuspend?id=1"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/unsuspend?id=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/unsuspend?id=1"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/delete?id=1"      | "Alice" | "password"		| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/users/delete?id=1"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 302		|
	  | "/admin-dashboard/users/delete?id=1"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/delete?id=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/delete?id=1"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/edit?id=1"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/users/edit?id=1"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/users/edit?id=1"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/edit?id=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/edit?id=1"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/buddy-search?querystr=x"      | "Alice" | "password"		| "ROLE_SEARCHER"  | true         | 200		|
	  | "/buddy-search?querystr=x"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/buddy-search?querystr=x"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 200		|	
	  | "/buddy-search?querystr=x"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/buddy-search?querystr=x"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/buddy-request?responder=x"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/buddy-request?responder=x"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/buddy-request?responder=x"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 200		|	
	  | "/buddy-request?responder=x"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/buddy-request?responder=x"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/view-buddy-request?notifyid=1"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/view-buddy-request?notifyid=1"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/view-buddy-request?notifyid=1"     | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 200		|	
	  | "/view-buddy-request?notifyid=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/view-buddy-request?notifyid=1"     | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|	  
	  | "/landlord-dashboard"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/landlord-dashboard"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | false         | 403		|
	  | "/landlord-dashboard"     | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 200		|	
	  | "/landlord-dashboard"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/landlord-dashboard"     | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard"     | "John"  | "password"   	| "ROLE_LANDLORD"  | false         | 403		|	
	  | "/admin-dashboard"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard"     | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/message-all"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard/users/message-all"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/users/message-all"     | "John"  | "password"   	| "ROLE_LANDLORD"  | false        | 403		|	
	  | "/admin-dashboard/users/message-all"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/message-all"     | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/listings/editProperty?id=0"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard/listings/editProperty?id=0"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/listings/editProperty?id=0"     | "John"  | "password"   	| "ROLE_LANDLORD"  | false        | 403		|	
	  | "/admin-dashboard/listings/editProperty?id=0"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/listings/editProperty?id=0"     | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/listings/editRoom?id=0"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard/listings/editRoom?id=0"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/listings/editRoom?id=0"     | "John"  | "password"   	| "ROLE_LANDLORD"  | false         | 403		|	
	  | "/admin-dashboard/listings/editRoom?id=0"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/listings/editRoom?id=0"     | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/message"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/message"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200  	|
	  | "/message"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/message"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/message"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/message/create"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/message/create"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200  	|
	  | "/message/create"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/message/create"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/message/create"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/notifications"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/notifications"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200  	|
	  | "/notifications"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/notifications"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/notifications"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/property?id=0"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/property?id=0"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200  	|
	  | "/property?id=0"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/property?id=0"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/property?id=0"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/reports"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/reports"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200  	|
	  | "/admin-dashboard/reports"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 403		|
	  | "/admin-dashboard/reports"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/admin-dashboard/reports"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/user-registration"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 403		|
	  | "/user-registration"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 403  	|
	  | "/user-registration"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 403	|
	  | "/user-registration"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/user-registration"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/error-login"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/error-login"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200  	|
	  | "/error-login"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
	  | "/error-login"      	  | "anon"  | "password"    | "anon"           | true        | 200		|
	  | "/error-login"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | true        | 200		|