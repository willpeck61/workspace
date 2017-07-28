Feature: Feedback on properties
		
Scenario: Searcher "Alice" rates a property 
  Given Im logged in as "Alice"
  And want to rate property "1"
  When submitting the rating "5" 
  Then the rating is stored for property "1"
