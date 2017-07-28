Feature:  Create and edit rooms
 As a landlord
 I want to create, edit and delete rooms 
 In order to be able to rent it out to other searchers
 
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
   Examples:
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
   Examples:
    | ID | PROPERTYID | HEADLINE                  | DESCRIPTION                          | WIDTH | HEIGHT | FURNISHED | ENSUITE | DOUBLEROOM |
    | 1  | 1          | "Nice room - with a view" | "A the room has changed size"        | 12    | 20     | 1         | 0       | 0          |
    | 2  | 1          | "An ensuite room"         | "A room with an interesting ensuite" | 25    | 18     | 1         | 1       | 1          |
   
  @domain  
  Scenario Outline: Delete room
   Given im completing the "landlord-dashboard/manage/editRoom?id=1" form 
   When click the "landlord-dashboard/deleteRoom?id=1&roomid=1" link
   Then the room should be deleted
   
   
   