Feature: Feedback on properties

Background:
  Given an searcher named <sname>
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
