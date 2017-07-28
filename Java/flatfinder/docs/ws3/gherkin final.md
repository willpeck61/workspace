##FLATFINDER
###Group Three

#Project Backlog

##Project Goal

The goal as taken from the case study is as follows: to help students and professionals across the country find accommodation near their universities and workplaces respectively. FlatFinder will cater to users who wish to find accommodation for both long-term and short-term/overnight stays. The company has decided to develop a trial for a responsive site for use within the Leicester area which can be used on Desktop and mobile devices.

Our personal project goal is to develop a web application whose function is to provide a simplistic, easy-to-use, interface whereby users can search for nearby properties, connect with other users, and give feedback on the content shown. Various features will include a search engine, a user-to-user system of interaction, a mapping system with to scale navigation and realistic distance control. The system will support numerous account types allowing different levels of access to various functions. The overall system will be created using Java, HTML, MySQL, Javascript, and servlet technology.

### Marking guidelines

Levels of academic performance used:
*  **Compeleted:**  corresponds to a top mark;
*  **in progress:**  corresponds to a low mark and is a clear an indicator of an area where improvement is required.
*  **not implamented** -  correspond to piece of work which haven't been started

### Priority guidelines
*  **HIGH**
*  **MEDIUM**
*  **LOW**
*  **OPTIONAL**

### List of Gherking features

###Compeleted
Priority Completion Scenario
* HIGH 100% **Log out** *As a site member I can log out at any time So that I keep my account safe*
* HIGH 100% **Navigation** *As a site visitor, I want to be able to get back to the home page quickly and easily*
* HIGH 100% **Local amenities and transport links** *As a website user, I can see local aminities and transport links, In know where is advertised property is*
* HIGH 100% **Profile Update** *As a 'Searcher' I want to describe myself on my own page in a semi-structured way In order to make sure other member would like to 'buddy up'*
* HIGH 100% **Property advertisement** *As a site 'Landlord' I can advertise property for rent or sale So that I can let people find my advertisement*
* HIGH 100% **Property search** *As a site member I can use search box tool So that I can find accommodation to rent*
* HIGH 100% *Express interest *As a site member I want to express interest in advertisement In order to get in touch  with 'Landlord'*
* HIGH 100% **Notification** *As a site 'Landlord' I want to know who has expressed interest in my property In to order to get in touch with them*
* HIGH 100% **Buddy-up** *As a site member I can see for profiles based on buddy up system In order to rent a house in a group of people*
* HIGH 100% **View statistics** *As a site member  I want to see statistics allowed for my account*
* HIGH 100% **Profile security** *As a site member I can mark my profile as private in which case only my name and picture will appear In order to keep my information private*
* HIGH 100% **Messenger** *As a site member I can send message to any member who are currently 'buddied up' with me In order to stay in touch with other possible house members*
* HIGH 100% **Register** *As a site visitor I want to become part of a community In order to find a room for rent*
* HIGH 100% **StreetMapView** *As a site member, I want to see StreetMapView when I search for property*
* HIGH 100% **Route feature** *As a searcher, I can see updated route infomation according to my post code, In order to see how far is it to University*
* MEDIUM 100% **Administration** *As a site administrator I can suspend or edit any member of the system So that I can I protect community*
* MEDIUM 100% **Property management** *As a landlord I can delete one of my advertisements So that I can keep only fresh once in the community*
* MEDIUM 100% **Report** *As a site member I want to report advertisement In order to keep community free of scam*
* MEDIUM 100% **Boddy management** *As a member, I can update one of my existing buddies In order to keep only the most recent ones*
* MEDIUM 100% **Login feature** *As a site member I can log in at any time So that I can use site system feature*
* LOW 100% **Message all users** *As a site administrator I can message all users So that I can inform everybody in case of emergency*
* LOW 100% **Privacy** *As a site visitor I want to find privacy information In order to make sure of personal data protecting information*
* LOW 100% **Privacy** *As a site visitor I want to find privacy information In order to make sure of personal data protecting information*
* LOW 100% **Terms of Use** *As a site visitor I want to find terms of use In order to find out about restrictions and terms of use*
* LOW 100% **Password recovery** *As a site member I want to recover my password In order to log in to the system*
* LOW 100% **Popular property** *As a site user, I want to see a list of the most popular popular properties*

###Gherkin notations (Detailed feature specification)

**Feature:  BuddyUp**
priority: HIGH
completion:finished

	As a searcher who is loooking for a buddy
	I want to search for a buddy
	In order to send them a request to be buddies
	
	@controller
	Scenario: send a buddy request
		Given im the user "Alice" on the profile page
		When im sending the buddy request to "Adam"
		Then the buddy request for "Alice" to "Adam" should exist
		
	@controller
	Scenario: notification for buddy request
		Given im the user "Adam" on the notification page
		When click the notification from "Alice" to buddyup
		Then the notification for "Adam" is diaplayed
	
	@controller
	Scenario: accept buddy request
		Given im the user "Adam" on the notification page
		When ive clicked to accept the buddy request from "Adam"
		Then the user "Adam" is a confirmed buddy
		
	@controller
	Scenario: reject the buddy request
		Given im the user "Adam" on the notification page
		When ive clicked to reject the buddy request from "Adam"
		Then the user "Adam" is not a confirmed buddy
		
	@controller	
	Scenario Outline: making multiple buddy requests 
		Given Im logged in as user id <INITID>
		When made buddy request to user id <RESPID> from <INITID>
		Then after the <RESPID> and <INITID> the total buddy requests are <TOTAL> when the response is <RESPONSE>
**Examples:**

      | INITID | RESPID    | TOTAL  | RESPONSE        |
      | 1      | 10        | 1  	| true            | 
      | 1      | 11        | 1  	| true            | 
      | 1      | 12        | 1 	    | true            | 
      | 1      | 13        | 1 	    | true            | 
      | 1      | 14        | 1  	| true            | 
      | 1      | 15        | 1  	| true            | 
      | 1      | 10        | 1  	| false           | 
      | 1      | 11        | 1  	| false           | 
      | 1      | 12        | 1 	    | false           | 
      | 1      | 13        | 1 	    | false           | 
      | 1      | 14        | 1      | false           | 
      | 1      | 15        | 1  	| false           |
	  
	@controller	
	Scenario Outline: multiple buddy responses 
		Given Im logged in as user id <INITID>
		When made buddy request to user id <RESPID> from <INITID>
		Then after the <RESPID> and <INITID> the total buddy requests are <TOTAL> when the response is <RESPONSE>
**Examples:**

      | RESPID | INITID    | TOTAL  | RESPONSE        |
      | 1      | 10        | 7   	| true            | 
      | 1      | 11        | 8   	| true            | 
      | 1      | 12        | 9  	| true            | 
      | 1      | 13        | 10 	| true            | 
      | 1      | 14        | 11  	| true            | 
      | 1      | 15        | 12  	| true            |
      | 1      | 10        | 13 	| false           | 
      | 1      | 11        | 13  	| false           | 
      | 1      | 12        | 13     | false           | 
      | 1      | 13        | 13	    | false           | 
      | 1      | 14        | 13 	| false           | 
      | 1      | 15        | 13	    | false           |
 
	@controller
	Scenario: remove buddy
		Given Im logged in as "Alice"
		And user "Alice" has a buddy "Adam"
		When user "Alice" removes "Adam" as a buddy
		Then user "Alice" is no longer buddies with "Adam"
	
	@controller	
	Scenario Outline: multiple buddy rejection
		Given Im logged in as user id <USERID>
		When user <USERID> removes buddy up with <BUDDYID>
		Then after <USERID> and <BUDDYID> are no longer buddies
**Examples:**

      | USERID | BUDDYID   |
      | 1      | 10        | 
      | 1      | 11        |  
      | 1      | 12        | 
      | 1      | 13        | 
      | 1      | 14        |  
      | 1      | 15        |
      
	   
      


**Feature: Web authorization**
priority: HIGH
completion: finished

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
    
      | Service                    	  | USER    | PW			| ROLES            | isAuthorized | Result	|
      | "/"              		   	  | "Baddy" | "password" 	| "ROLE_SUSPENDED" | false         | 403  	|
      | "/landlord-dashboard"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | false     | 403		|
      | "/landlord-dashboard"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | false       | 403		|
      | "/landlord-dashboard"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
      | "/landlord-dashboard"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
      | "/landlord-dashboard"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
      | "/view-profile"            	  | "Alice" | "password"    | "ROLE_SEARCHER"  | true         | 200		|
      | "/view-profile"            	  | "Bob"   | "admin"       | "ROLE_ADMIN"     | true         | 200 	|
      | "/view-profile"            	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200	 	|
      | "/view-profile"            	  | "anon"  | "password"    | "anon"           | false        | 403		|
      | "/view-profile"            	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
      | "/amend-user"              	  | "Alice" | "password"    | "ROLE_SEARCHER"  | true         | 200		|
      | "/amend-user"              	  | "Bob"   | "admin"       | "ROLE_ADMIN"     | true         | 200		|
      | "/amend-user"              	  | "John"  | "password" 	| "ROLE_LANDLORD"  | true           | 200		|
      | "/amend-user"              	  | "anon"  | "password" 	| "anon"           | false          | 403		|
      | "/amend-user"              	  | "Baddy" | "password" 	| "ROLE_SUSPENDED" | false          | 403		|
      | "/upload"   	                  | "Alice" | "password"    | "ROLE_SEARCHER"  | true         | 200		|
      | "/upload" 			       	      | "Bob"   | "admin"       | "ROLE_ADMIN"     | true         | 200		|
      | "/upload"                  	  | "John"  | "password" 	| "ROLE_LANDLORD"  | true         | 200		|
      | "/upload"                  	  | "anon"  | "password" 	| "anon"           | false        | 403		|
      | "/upload"                  	  | "Baddy" | "password" 	| "ROLE_SUSPENDED" | false        | 403		|
      | "/addUser"                 	  | "Alice" | "password"	| "ROLE_SEARCHER"  | false        | 405		|
      | "/addUser"                 	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | false          | 405		|
      | "/addUser"                 	  | "John"  | "password"    | "ROLE_LANDLORD"  | false        | 405		|
      | "/addUser"                 	  | "anon"  | "password"    | "anon"           | false        | 405		|
      | "/addUser"                 	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 405		|
      | "/get-users?type=buds"     	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true           | 200		|
      | "/get-users?type=buds"     	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true             | 200		|
      | "/get-users?type=buds"     	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 200		|
      | "/get-users?type=buds"     	  | "anon"  | "password"    | "anon"           | false        | 403		|
      | "/get-users?type=buds"     	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|	
      | "/add-interest?addint=x"   	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true           | 302		|
      | "/add-interest?addint=x"   	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true             | 302		|
      | "/add-interest?addint=x"   	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 302		|	
      | "/add-interest?addint=x"   	  | "anon"  | "password"    | "anon"           | false        | 403		|
      | "/add-interest?addint=x"   	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
      | "/remove-interest?remint=x"   | "Alice" | "password"	| "ROLE_SEARCHER"  | true           | 302		|
      | "/remove-interest?remint=x"   | "Bob"   | "admin"		| "ROLE_ADMIN"     | true             | 302		|
      | "/remove-interest?remint=x"   | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 302		|	
      | "/remove-interest?remint=x"   | "anon"  | "password"   	| "anon"           | false        | 403		|
      | "/remove-interest?remint=x"   | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
      | "/admin-dashboard/users"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true           | 403		|
      | "/admin-dashboard/users"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true             | 200		|
      | "/admin-dashboard/users"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
      | "/admin-dashboard/users"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users"        | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/suspend?id=1"      | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 403		|
	  | "/admin-dashboard/users/suspend?id=1"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 302		|
	  | "/admin-dashboard/users/suspend?id=1"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/suspend?id=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/suspend?id=1"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/unsuspend?id=1"    | "Alice" | "password"		| "ROLE_SEARCHER"  | true           | 403		|
	  | "/admin-dashboard/users/unsuspend?id=1"    | "Bob"   | "admin"			| "ROLE_ADMIN"     | true           | 302		|
	  | "/admin-dashboard/users/unsuspend?id=1"    | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/unsuspend?id=1"    | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/unsuspend?id=1"    | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/delete?id=1"       | "Alice" | "password"		| "ROLE_SEARCHER"  | true           | 403		|
	  | "/admin-dashboard/users/delete?id=1"       | "Bob"   | "admin"		| "ROLE_ADMIN"     | true             | 302		|
	  | "/admin-dashboard/users/delete?id=1"       | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 403		|	
	  | "/admin-dashboard/users/delete?id=1"       | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/delete?id=1"       | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/edit?id=1"         | "Alice" | "password"	| "ROLE_SEARCHER"  | true             | 403		|
	  | "/admin-dashboard/users/edit?id=1"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true                 | 200		|
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
	  | "/view-buddy-request?notifyid=1"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 200		|	
	  | "/view-buddy-request?notifyid=1"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/view-buddy-request?notifyid=1"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|	  
	  | "/landlord-dashboard"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/landlord-dashboard"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | false         | 403		|
	  | "/landlord-dashboard"      | "John"  | "password"   	| "ROLE_LANDLORD"  | true         | 200		|	
	  | "/landlord-dashboard"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/landlord-dashboard"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard"         | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard"         | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard"         | "John"  | "password"   	| "ROLE_LANDLORD"  | false         | 403		|	
	  | "/admin-dashboard"         | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard"         | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/users/message-all"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard/users/message-all"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/users/message-all"      | "John"  | "password"   	| "ROLE_LANDLORD"  | false        | 403		|	
	  | "/admin-dashboard/users/message-all"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/users/message-all"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/listings/editProperty?id=0"      | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard/listings/editProperty?id=0"      | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/listings/editProperty?id=0"      | "John"  | "password"   	| "ROLE_LANDLORD"  | false        | 403		|	
	  | "/admin-dashboard/listings/editProperty?id=0"      | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/listings/editProperty?id=0"      | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
	  | "/admin-dashboard/listings/editRoom?id=0"          | "Alice" | "password"	| "ROLE_SEARCHER"  | false         | 403		|
	  | "/admin-dashboard/listings/editRoom?id=0"          | "Bob"   | "admin"		| "ROLE_ADMIN"     | true         | 200		|
	  | "/admin-dashboard/listings/editRoom?id=0"          | "John"  | "password"   	| "ROLE_LANDLORD"  | false         | 403		|	
	  | "/admin-dashboard/listings/editRoom?id=0"          | "anon"  | "password"   	| "anon"           | false        | 403		|
	  | "/admin-dashboard/listings/editRoom?id=0"          | "Baddy" | "password"   	| "ROLE_SUSPENDED" | false        | 403		|
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
	  | "/user-registration"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"     | true           | 403  	|
	  | "/user-registration"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true         | 403	  |
	  | "/user-registration"      	  | "anon"  | "password"    | "anon"           | false        | 403		|
	  | "/user-registration"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | false        | 403		|
	  | "/error-login"      	  | "Alice" | "password"	| "ROLE_SEARCHER"  | true         | 200		|
	  | "/error-login"      	  | "Bob"   | "admin"		| "ROLE_ADMIN"       | true         | 200  	|
	  | "/error-login"      	  | "John"  | "password"    | "ROLE_LANDLORD"  | true       | 200		|
	  | "/error-login"      	  | "anon"  | "password"    | "anon"           | true       | 200		|
	  | "/error-login"      	  | "Baddy" | "password"    | "ROLE_SUSPENDED" | true       | 200		|


**Feature:  ExpiredUser**
priority: HIGH
completion:finished

	As a user who has not logged in for 30 days
	My account will be suspended
	
	@controller
	Scenario: user not logged in for 31 days
		Given the registered user "Adam" not logged in for 31 days
		When the landing page is accessed
		Then the user "Adam" is suspended
		
	@controller
	Scenario: admin user who has not logged in for 31 days
		Given the registered user "Bob" has not logged in for 31 daya
		When the landing page is accessed
		Then the user "Bob" is not suspended
		
	@controller
	Scenario: landlord user who has not logged in for 31 days
		Given the registered user "John" not logged in for 31 days
		When the lsnding page is accessed
		Then the user "John" is suspended

**Feature: Show interest on property**
priority: HIGH
completion:finished

	As a registered user
	I want to search for a property
	In order to express interest in it
  
	@domain  
	Scenario: Express interest in a property
		Given im logged in as "Alice"
		When the show interest button is clicked with the property id 1
		Then a notification is sent to the owner id 1 with the property id 1


**Feature: Feedback on properties**
priority: HIGH
completion:finished

	Scenario: Searcher "Alice" rates a property 
		Given Im logged in as "Alice"
		And want to rate property "1"
		When submitting the rating "5"
		Then the rating is stored for property "1"

**Feature:  Create and edit a property**
priority: HIGH
completion:finished


	As a landlord
	I want to create and edit a property
	In order to be able to rent it out to other searchers
	
	@controller
	Scenario: View new property page
		Given im the user "John" on the landlord dashboard
		When click the access "landlord-dashboard/create" link
		Then the form to create a new property is displayed
	
	@domain
	Scenario Outline: Create new property
		Given im the user "John" completing the "landlord-dashboard/create" form
		When the create property form is submitted with <PRICE>, <HOUSENUM>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <FROMDD>, <FROMMM>, <FROMYYYY>, <TODD>, <TOMM>, <TOYYYY>, <PROPERTYTYPE>, <ADVERTTITLE>, <DESCRIPTION>, <LOCALAREA>, <NO_OF_ROOMS>, <OCCUPANTTYPE>, <BILLSINCLUDED>, <PETSALLOWED>, <PARKING>, <DISABLEDACCESS>, <SMOKING>, <QUIETAREA>, <SHORTTERM> and <SINGLEGENGER>
		Then we should see <PRICE>, <HOUSENUM>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <FROMDD>, <FROMMM>, <FROMYYYY>, <TODD>, <TOMM>, <TOYYYY>, <PROPERTYTYPE>, <ADVERTTITLE>, <DESCRIPTION>, <LOCALAREA>, <NO_OF_ROOMS>, <OCCUPANTTYPE>, <BILLSINCLUDED>, <PETSALLOWED>, <PARKING>, <DISABLEDACCESS>, <SMOKING>, <QUIETAREA>, <SHORTTERM> and <SINGLEGENGER>  has been created
	
**Examples:**

      | PRICE | HOUSENUM | ADDRESS1       | ADDRESS2      | ADDRESS3    | POSTCODE | FROMDD | FROMMM | FROMYYYY | TODD | TOMM | TOYYYY | PROPERTYTYPE | ADVERTTITLE            | DESCRIPTION                                                    | LOCALAREA                                                              | NO_OF_ROOMS | OCCUPANTTYPE    | BILLSINCLUDED | PETSALLOWED | PARKING | DISABLEDACCESS | SMOKING | QUIETAREA | SHORTTERM | SINGLEGENGER |
      | 105   | 13       | "Cecilia Road" | "Poppy lane"  | "Leicester" | "LE21TA" | 12     | 05     | 2016     | 05   | 09   | 2016   | 1            | "House near uni"       | "A nice house near the university, about a 5 minute walk"      | "Very nice neighbours"                                                 | 0           | "Student"       | 1             | 1           | 1       | 1              | 1       | 0         | 1         | 0            |
      | 75    | 300      | "Hot Gates"    | "Thermopylae" | "Greece"    | "LE01DS" | 01     | 03     | 2016     | 04   | 12   | 2016   | 1            | "Deceptively Spacious" | "Narrow, but open air residents that affords good protection." | "Okay neighbours but a few million Persian with the odd megalomaniac." | 0           | "Undergraduate" | 1             | 0           | 1       | 1              | 1       | 0         | 1         | 0            |
        
 	@domain  
	 Scenario Outline: "John" edits property with id "1"
		Given im the user "john" on the landlord dashboard
		When click the access "landlord-dashboard/manage/editProperty?id=1" link 
		Then the property is displayed
		
	@domain
	Scenario Outline: Save changes
		Given im completing the "landlord-dashboard/manage/editProperty?id=1" form
		When the save form is submitted with <ID>, <PRICE>, <HOUSENUM>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <FROMDD>, <FROMMM>, <FROMYYYY>, <TODD>, <TOMM>, <TOYYYY>, <PROPERTYTYPE>, <ADVERTTITLE>, <DESCRIPTION>, <LOCALAREA>, <NO_OF_ROOMS>, <OCCUPIED_ROOMS>, <OCCUPANTTYPE>, <BILLSINCLUDED>, <PETSALLOWED>, <PARKING>, <DISABLEDACCESS>, <SMOKING>, <QUIETAREA>, <SHORTTERM> and <SINGLEGENGER>
		Then we should see <ID>, <PRICE>, <HOUSENUM>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <FROMDD>, <FROMMM>, <FROMYYYY>, <TODD>, <TOMM>, <TOYYYY>, <PROPERTYTYPE>, <ADVERTTITLE>, <DESCRIPTION>, <LOCALAREA>, <NO_OF_ROOMS>, <OCCUPIED_ROOMS>, <OCCUPANTTYPE>, <BILLSINCLUDED>, <PETSALLOWED>, <PARKING>, <DISABLEDACCESS>, <SMOKING>, <QUIETAREA>, <SHORTTERM> and <SINGLEGENGER> has been updated
**Examples:** 

      | ID | PRICE | HOUSENUM | ADDRESS1       | ADDRESS2       | ADDRESS3    | POSTCODE | FROMDD | FROMMM | FROMYYYY | TODD | TOMM | TOYYYY | PROPERTYTYPE | ADVERTTITLE       | DESCRIPTION                        | LOCALAREA              | NO_OF_ROOMS | OCCUPIED_ROOMS | OCCUPANTTYPE | BILLSINCLUDED | PETSALLOWED | PARKING | DISABLEDACCESS | SMOKING | QUIETAREA | SHORTTERM | SINGLEGENGER |
      | 1  | 115   | 14       | "Cecilia Lane" | "Changed road" | "Leicester" | "LE21TA" | 12     | 05     | 2016     | 05   | 09   | 2016   | 1            | "A changed value" | "A nice house near the university" | "Very nice neighbours" | 1           | 1              | "Student"    | 1             | 0           | 0       | 1              | 1       | 1         | 0         | 1            |
             
	@domain
	Scenario Outline: Delete property
		Given im completing the "landlord-dashboard/manage/editProperty?id=1" form 
		When click the "landlord-dashboard/manage/deleteProperty?id=1" link
		Then the property should be deleted

**Feature:  Create and edit rooms**
priority: HIGH
completion:finished

	 As a landlord
	 I want to create, edit and delete rooms
	 In order to be able to rent it out to other searcher
	 
	@controller
	Scenario: View room page
		Given im the user "john" on the landlord dashboard
		When click the access "/landlord-dashboard/manage/editRoom?id=1" link
		Then the rooms associated with the property id "1" should be displayed
	
	@domain  
	Scenario Outline: Create new room
		Given im the user "John" completing the form for property id "1"
		When the create room form is submitted with <PROPERTYID>, <HEADLINE>, <DESCRIPTION>, <WIDTH>, <HEIGHT>, <FURNISHED>, <ENSUITE> and <DOUBLEROOM>
		Then we should see  <PROPERTYID>, <HEADLINE>, <DESCRIPTION>, <WIDTH>, <HEIGHT>, <FURNISHED>, <ENSUITE> and <DOUBLEROOM> has been created.
 
**Examples:** 

    | PROPERTYID | HEADLINE          | DESCRIPTION                       | WIDTH | HEIGHT | FURNISHED | ENSUITE | DOUBLEROOM |
    | 1          | "Nice room"       | "A good sized room"               | 12    | 20     | 0         | 0       | 1          |
    | 1          | "An ensuite room" | "A room with an ensuite bathroom" | 20    | 15     | 0         | 1       | 1          |
    
 	@domain
 	Scenario Outline: "John" edits room with id "1"
		Given im the user "john" on the landlord dashboard
		When click the access "landlord-dashboard/manage/editRoom?id=1" link
		Then the room page is displayed
 	
 	@domain
 	Scenario Outline: Save changes
		Given im completing the "landlord-dashboard/manage/editRoom?id=1&roomid=1" form
		When the save form is submitted with <ID>, <PROPERTYID>, <HEADLINE>, <DESCRIPTION>, <WIDTH>, <HEIGHT>, <FURNISHED>, <ENSUITE> and <DOUBLEROOM>
		Then we should see <ID>, <PROPERTYID>, <HEADLINE>, <DESCRIPTION>, <WIDTH>, <HEIGHT>, <FURNISHED>, <ENSUITE> and <DOUBLEROOM> has been updated
 	
 	
**Examples:** 

    | ID | PROPERTYID | HEADLINE                  | DESCRIPTION                          | WIDTH | HEIGHT | FURNISHED | ENSUITE | DOUBLEROOM |
    | 1  | 1          | "Nice room - with a view" | "A the room has changed size"        | 12    | 20     | 1         | 0       | 0          |
    | 2  | 1          | "An ensuite room"         | "A room with an interesting ensuite" | 25    | 18     | 1         | 1       | 1          |
   
 	@domain
 	Scenario Outline: Delete room
		Given im completing the "landlord-dashboard/manage/editRoom?id=1" form 
		When click the "landlord-dashboard/deleteRoom?id=1&roomid=1" link
		Then the room should be deleted
   

**Feature: Access my profile**
priority: HIGH
completion:finished
	
	As a searcher
	I want to access my profile
	In order to amend my details
	
	@controller
	Scenario: access my profile page
		Given im the user "Alice" on the home page
		When click the access "/view-profile" link
		Then my profile page for "Alice" is displayed
		
	@controller
	Scenario: upload profile picture
		Given im the user "alice" on my profile page
		When click to submit "testprofle" image file
		Then the "testprofile" exists in the "alice" folder
		
	@controller	
	Scenario Outline: Searcher edits profile 
		Given Im logged in as <USR>
		When submit edits to my profile <FIELD> <EDIT> success is <RESULT>
		Then the edit <FIELD> with data <EDIT> is stored in <USR> profile <METHOD> is <RESULT>
**Examples:**

      | USR      | FIELD               | EDIT          | METHOD                 | RESULT        |
      | "Alice"  | "headline"          | "test data"   | "findByHeadline"       | true          | 
      | "Alice"  | "about"             | "test data"   | "findByAbout"          | true          | 
      | "Alice"  | "studying"          | "test data"   | "findByStudying"       | true          | 
      | "Alice"  | "workplace"         | "test data"   | "findByWorkplace"      | true          | 
      | "Alice"  | "studyyear"         | "test data"   | "findByStudyyear"      | false         |
      | "Alice"  | "studyyear"         | "1"           | "findByStudyyear"      | true          | 
      | "Alice"  | "quietorlively" 	   | "test data"   | "findByQuietorlively"  | false         |
      | "Alice"  | "quietorlively" 	   | "1"           | "findByQuietorlively"  | true          |
      | "Alice"  | "samesexormixed"    | "test data"   | "findBySamesexormixed" | false         |
      | "Alice"  | "samesexormixed"    | "1"		   | "findBySamesexormixed" | true          |
      | "Alice"  | "smoking"           | "test data"   | "findBySmoking"        | false         | 
      | "Alice"  | "smoking"           | "1"           | "findBySmoking"	    | true          | 
      | "Alice"  | "pets"              | "test data"   | "findByPets"		    | false         | 
      | "Alice"  | "pets"              | "1"           | "findByPets"		    | true          | 
      | "Alice"  | "ensuite"           | "test data"   | "findByEnsuite"	    | false         | 
      | "Alice"  | "ensuite"           | "1"           | "findByEnsuite"        | true          | 
      | "Alice"  | "facebookurl" 	   | "test data"   | "findByFacebookurl"    | true          |
      | "Alice"  | "twitterurl"        | "test data"   | "findByTwitterurl"     | true          |
      | "Alice"  | "googleplusurl"     | "test data"   | "findByGoogleplusurl"  | true          |
      | "Alice"  | "linkedinurl"       | "test data"   | "findByLinkedinurl"    | true          |
      | "Alice"  | "studyplace"        | "test data"   | "findByStudyplace"     | true          | 
      | "Alice"  | "wp-postcode"       | "test data"   | "findByWpostcode"      | true          | 
      | "Alice"  | "sp-postcode"       | "test data"   | "findBySpostcode"      | true          |   
		
		
**Feature: Search properties**
priority: HIGH
completion: finished

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
		
		
**Feature: Register as a member**
priority: HIGH
completion: finished

   	  As a site visitor
   	  I want to join the website
   	  In order to find property to rent
   	  
   	  @domain
   	  Scenario Outline: register as any role
		Given im completing the "register-form" form
		When the Register form is submitted with <FIRSTNAME>, <LASTNAME>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <ROLE>, <USERNAME> and <PASSWORD>
		Then we should see that <FIRSTNAME>, <LASTNAME>, <ADDRESS1>, <ADDRESS2>, <ADDRESS3>, <POSTCODE>, <ROLE>, <USERNAME> and <PASSWORD> has been registered
		And the folder structure for <USERNAME> has been created
   	  
**Examples:** 

        | FIRSTNAME  | LASTNAME  | ADDRESS1 | ADDRESS2       | ADDRESS3    | POSTCODE | ROLE    | USERNAME   | PASSWORD   |
        | "Will"     | "Smith"	 | "86"     | "West Road"    | "Leicester" | "LE27TG" | "2" 	| "will"     | "password" |
        | "James"    | "Wick"    | "22a"	| "North Street" | "Leicester" | "LE11NY" | "3" 	| "james"    | "password" |

   	 @controller
   	 Scenario: attempt to register twice same username
		Given im completing the registration form with the username "alice"
		When the Register form is submitted with "Alice", "Smith", "address1", "address2", "address3", "postcode", "SEARCHER", "alice" and "password"
		Then an error message "error" is returned


**Feature: Report a user**
priority: HIGH
completion: finished

   	As a registered user
   	I want to submit a report
   	In order to complain about a user
   	  
   	@domain  
	Scenario: Express interest in a property
		Given im logged in as "Alice"
		When the user "Alice" submits a report on "Adam"
		Then the report on "Adam" exists made by "Alice"

**Feature: Searching for properties**
priority: HIGH
completion: finished

	As a registered user
	I want to search for a property
	In order to view properties
	
	@domain  
	Scenario Outline: search for properties by postcode
		Given im searching for <POSTCODE>
		When ive submitted the search form
		Then the search page is displayed with <POSTCODE> as the result
**Examples:**

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
  
	@domain  
	Scenario Outline: search for properties by postcode with max 4 bedrooms
		Given im searching for "LE21TA" with 4 bedrooms max
		When ive submitted the search form
		Then the search page is displayed with "LE21TA" and max 4 bedrooms
**Examples:**

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
 
 	@domain
 	Scenario Outline: search for properties by postcode with 2 bedrooms min, 6 bedrooms max
		Given im searching for <POSTCODE> with 2 bedrooms min and 6 bedrooms max
		When ive submitted the search form
		Then the search page is displayed with <POSTCODE> and min 2 bedrooms and max 6 bedrooms
**Examples:** 

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
     
 	@domain
	Scenario Outline: search for properties by postcode with 65 min price, 125 max price
		Given im searching for <POSTCODE> with 65 min price and 125 max price
		When ive submitted the search form
		Then the search page is displayed with <POSTCODE> and 65 min price and 125 max price
**Examples:**

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
     
  	@domain
	Scenario Outline: search for properties by postcode, address and city
		Given im searching for <POSTCODE>, <ADDRESS_1> and <ADDRESS_3>
		When ive submitted the search form
		Then the search page is displayed with <POSTCODE>, <ADDRESS_1> and <ADDRESS_3>
**Examples:**

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
  	
  	@domain
	Scenario Outline: search for properties by city, bills_included, disabled_access, parking, quiet_area, short_term, single_gender, and smoking
		Given im searching for <ADDRESS_3> with <BILLS_INCLUDED>, <DISABLED_ACCESS>, <PARKING>, <QUIET_AREA>, <SHORT_TERM>, <SINGLE_GENDER> and <SMOKING>
		When ive submitted the search form
		Then the search page is displayed with <ADDRESS_3> with <BILLS_INCLUDED>, <DISABLED_ACCESS>, <PARKING>, <QUIET_AREA>, <SHORT_TERM>, <SINGLE_GENDER> and <SMOKING>
**Examples:**

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
    
  	@domain
	Scenario Outline: search for properties by city in between "2016-04-01" and "2017-01-01"
		Given im searching for <ADDRESS_3> that is in between "2016-04-01" and "2017-01-01"
		When ive submitted the search form
		Then the search page is displayed with <ADDRESS_3> that is in between "2016-04-01" and "2017-01-01"
**Examples:** 

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
 
	@domain  
	Scenario Outline: search for properties by postcode with no results
		Given im searching for "QE21EB"
		When ive submitted the search form
		Then the search page is displayed with no results with postcode "QE21EB"
**Examples:**

      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
  
**Feature: **Privacy**
priority: HIGH
completion: finished

   	As a site visitor
   	I want to find privacy information
   	In order to make sure of personal data protecting information
   	  
	Scenario: Open Privacy
		Given I'm on FlatFinder's homepage
		When I click on  'Privacy' link
		Then system should provide me with the 'Privacy' information

**Feature: **Terms of Use**
priority: HIGH
completion: finished

   	As a** site visitor
   	I want to find terms of use
   	In order to find out about restrictions and terms of use
   	  
	Scenario: Open Terms of Use
		Given I'm on FlatFinder's homepage
		When I click on 'Terms of use' link
		Then system should provide me with the 'Term of use' information
	
	Scenario: open statistics view as a 'Landlord'
		Given account tool bar
		When I click on 'My statistics'
		Then system should provide me with a list of my properties
		And corresponding property views
	
	Scenario: open confidential statistics view as 'Administrator'
		Given account tool bar
		When I click on 'View statistics'
		Then system must provide me with overall users on the site information
		And overall registered members
		And people visited my profile
		And 'Property view history'


**Feature: **Password recovery**
priority: HIGH
completion: finished

   	As a** site member
   	I want to** recover my password
   	In order to** log in to the system
   	  
	Scenario: find password recovery feature
		Given** login page
		When** I click on 'Forgotten Password'
		Then** system should provide me with fields to be field for password recovery
		
	Scenario: submit password recovery form
		Given 'Forgotten Password' page
		When I finished filling field with valid information for my account
		Then system must my credential in a database
		And send me an email with corresponding password
		And prompt a message with successful recovery

**Feature: **Messenger**
priority: HIGH
completion: finished

   	As a site member
   	I can send message to any member who are currently 'buddied up' with me
   	In order to stay in touch with other possible house members
   	  
	Scenario: message a current 'Buddy up' member
		Given I'm viewing  'My account' page
		When I go to my 'Buddy ups'
		And select at least one of them
		Then system should show instant message input box

**Feature: Log out**
priority: HIGH
completion: finished

   	As a site member
   	I can log out at any time
   	So that I keep my account safe
   	  
	Scenario: Scenario: log out procedure
		Given access to 'My account'
		And** home page opened
		When I click on Log out
		Then system must delete all session files on my computer
		And prompt me a message of successfully log out
		And redirect me back to Homepage
