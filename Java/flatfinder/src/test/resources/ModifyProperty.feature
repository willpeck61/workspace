Feature:  Create and edit a property
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
   Examples:
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
   Examples:
      | ID | PRICE | HOUSENUM | ADDRESS1       | ADDRESS2       | ADDRESS3    | POSTCODE | FROMDD | FROMMM | FROMYYYY | TODD | TOMM | TOYYYY | PROPERTYTYPE | ADVERTTITLE       | DESCRIPTION                        | LOCALAREA              | NO_OF_ROOMS | OCCUPIED_ROOMS | OCCUPANTTYPE | BILLSINCLUDED | PETSALLOWED | PARKING | DISABLEDACCESS | SMOKING | QUIETAREA | SHORTTERM | SINGLEGENGER |
      | 1  | 115   | 14       | "Cecilia Lane" | "Changed road" | "Leicester" | "LE21TA" | 12     | 05     | 2016     | 05   | 09   | 2016   | 1            | "A changed value" | "A nice house near the university" | "Very nice neighbours" | 1           | 1              | "Student"    | 1             | 0           | 0       | 1              | 1       | 1         | 0         | 1            |
             
  @domain  
  Scenario Outline: Delete property
   Given im completing the "landlord-dashboard/manage/editProperty?id=1" form 
   When click the "landlord-dashboard/manage/deleteProperty?id=1" link
   Then the property should be deleted
        
   