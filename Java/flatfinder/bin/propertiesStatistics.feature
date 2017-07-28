Feature: statistics on properties should change
  As a landlord
  I want to see the number of views and interests on each property
  In order to monitor my properties success and interest
  
  @controller
  Scenario: Visit a property, the number of views should go up
  Given Im logged in as "Alice"
  When the user "Alice" visits a property with id "2"
  Then the number of views on property with id "2" should increase by one
  
  @controller
  Scenario: Express interest in a property
  Given Im logged in as "Alice"
  When the user "Alice" express' interest in a property with id "2"
  Then "John" should be send a notification from "Alice"

 