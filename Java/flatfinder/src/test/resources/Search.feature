Feature: Searching for properties
	As a registered user
	I want to search for a property
	In order to view properties
	
	@domain  
	Scenario Outline: search for properties by postcode
		Given im searching for <POSTCODE>
		When ive submitted the search form
		Then the search page is displayed with <POSTCODE> as the result
		Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
		    | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
		    | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
		    
		    
	@domain  
	Scenario Outline: search for properties by postcode with max 4 bedrooms
		Given im searching for "LE21TA" with 4 bedrooms max
		When ive submitted the search form
		Then the search page is displayed with "LE21TA" and max 4 bedrooms
		Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
  
 @domain  
 Scenario Outline: search for properties by postcode with 2 bedrooms min, 6 bedrooms max
  Given im searching for <POSTCODE> with 2 bedrooms min and 6 bedrooms max
  When ive submitted the search form
  Then the search page is displayed with <POSTCODE> and min 2 bedrooms and max 6 bedrooms
  Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
     
     
 @domain  
 Scenario Outline: search for properties by postcode with 65 min price, 125 max price
  Given im searching for <POSTCODE> with 65 min price and 125 max price
  When ive submitted the search form
  Then the search page is displayed with <POSTCODE> and 65 min price and 125 max price
  Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
     
     
 @domain  
 Scenario Outline: search for properties by postcode, address and city
  Given im searching for <POSTCODE>, <ADDRESS_1> and <ADDRESS_3>
  When ive submitted the search form
  Then the search page is displayed with <POSTCODE>, <ADDRESS_1> and <ADDRESS_3>
  Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
    
 @domain  
 Scenario Outline: search for properties by city, bills_included, disabled_access, parking, quiet_area, short_term, single_gender, and smoking
  Given im searching for <ADDRESS_3> with <BILLS_INCLUDED>, <DISABLED_ACCESS>, <PARKING>, <QUIET_AREA>, <SHORT_TERM>, <SINGLE_GENDER> and <SMOKING>
  When ive submitted the search form
  Then the search page is displayed with <ADDRESS_3> with <BILLS_INCLUDED>, <DISABLED_ACCESS>, <PARKING>, <QUIET_AREA>, <SHORT_TERM>, <SINGLE_GENDER> and <SMOKING>
  Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
    
    
 @domain  
 Scenario Outline: search for properties by city in between "2016-04-01" and "2017-01-01"
  Given im searching for <ADDRESS_3> that is in between "2016-04-01" and "2017-01-01"
  When ive submitted the search form
  Then the search page is displayed with <ADDRESS_3> that is in between "2016-04-01" and "2017-01-01"
  Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
    
    
	@domain  
	Scenario Outline: search for properties by postcode with no results
		Given im searching for "QE21EB"
		When ive submitted the search form
		Then the search page is displayed with no results with postcode "QE21EB"
		Examples: 
      | ID | ADDRESS_1       | ADDRESS_2    | ADDRESS_3   | BILLS_INCLUDED | DATE_CREATED               | DESCRIPTION                                             | DISABLED_ACCESS | AVAILABLE_TO        | HEADLINE_TEXT            | HOUSE_NUM | LISTED | LOCAL_AREA                                   | NUM_INTERESTS | NO_OF_VIEWS | OCCUPITED_ROOMS | NO_OF_ROOMS | PARKING   | PETS_ALLOWED | POSTCODE  | PRICE_PER_WEEK | QUIET_AREA | SHORT_TERM | SINGLE_GENDER | SMOKING | AVAILABLE_FROM      | PROPERTY_TYPE | OWNER_ID |  
      | 1  | "Cecilia Road"  | NULL         | "Leicester" | 1              | 2016-04-15 18:08:33        | "Really nice place to live, this line has to be on ..." | 1               | 2017-08-03 00:00:00 | "Nice house near Uni"    | 12        | 1      | "The local area is very nice"                | 22            | 222         | 0               | 3           | 0         | 1            | "LE21TA"  | 75             | 1          | 0          | 1             | 1       | 2016-01-01 00:00:00 | 1             | 2        | 
      | 2  | "Cecilia Road"  | "Green Road" | "Leicester" | 1              | 2016-04-15 18:08:33        | "I saw Susie sitting in a shoe shine shop. Where..."    | 1               | 2017-02-01 00:00:00 | "Another house near uni" | 13        | 1      | "This is the description of the local area." | 6             | 7           | 1               | 5           | 1         | 0            | "LE21QA"  | 105            | 1          | 1          | 1             | 0       | 2016-01-05 00:00:00 | 1             | 2        |
     
		
		