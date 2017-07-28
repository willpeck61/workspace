Feature:  Access my profile
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
	Examples:
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