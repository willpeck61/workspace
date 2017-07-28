Feature: Administer user accounts
	As administrator user
	I want be able to make changes to user accounts
	In order to secure the use of the website
  
	@controller 
	Scenario Outline: Delete user
		Given im logged in as "Bob"
		When the user "Bob" chooses to <ACTION> on <USER>
		Then the <USER> is set <ACTION> to <STATUS> and role is <ROLE>
   Examples: 
      | USER     | ACTION      | STATUS         | ROLE          | 
      | 10       | "suspend"   | "suspended"    | "SUSPENDED"   | 
	  | 10       | "unsuspend" | "active"       | "SEARCHER"    |
      | 10       | "delete"    | "deleted"      | "SEARCHER"    | 


	@controller	
	Scenario Outline: Searcher edits profile 
		Given Im logged in as "Bob"
		When submit edits to <USER> profile <FIELD> with <EDIT> success is <RESULT>
		Then the edit <FIELD> with the data <EDIT> is stored in <USER> profile <METHOD> is <RESULT>
	Examples:
	  | USER     | FIELD               | EDIT          | METHOD                 | RESULT        | 
      | "Alice"  | "title"             | "test data"   | "findByTitle"          | true          | 
      | "Alice"  | "firstname"         | "test data"   | "findByFirstname"      | true          | 
      | "Alice"  | "lastname"          | "test data"   | "findByLastname"       | true          | 
      | "Alice"  | "status"            | "0"           | "findByStatus"         | true          | 
      | "Alice"  | "address1"          | "test data"   | "findByAddress1"       | true          |
	  | "Alice"  | "address2"          | "test data"   | "findByAddress2"       | true          | 
	  | "Alice"  | "address3" 	       | "test data"   | "findByAddress3"       | true          |
	  | "Alice"  | "postcode"   	   | "test data"   | "findByPostcode"       | true          |
	  | "Alice"  | "email"             | "test data"   | "findByEmail"          | true          |
	  | "Alice"  | "buddyup"           | "0"		   | "findByBuddyup"        | true          |
      | "Alice"  | "username"          | "test data"   | "findByLogin"          | true          | 
	  