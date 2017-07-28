Feature:  ExpiredUser
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
		
		
		
		