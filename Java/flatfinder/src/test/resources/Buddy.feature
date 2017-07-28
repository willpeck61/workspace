Feature:  BuddyUp
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
	Examples:
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
	Examples:
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
	Examples:
	  | USERID | BUDDYID   | 
      | 1      | 10        | 
      | 1      | 11        |  
      | 1      | 12        | 
      | 1      | 13        | 
      | 1      | 14        |  
      | 1      | 15        |
		
		
		