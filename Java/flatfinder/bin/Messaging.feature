Feature: Messaging users
  As a user
  I want to be able to message other users
  In order to communicate with them
  
  @controller
  Scenario: admin messaging all users
  Given im logged in as "bob"
  When I mass message all users with the message "this is a test"
  Then they should receive the message from "bob"
  And receive a notification
  
  @controller
  Scenario: user to user messaging
  Given im logged in as "alice"
  When I send "adam" a message with the message "this is a test"
  Then "adam" should receive the message from "alice"
  And receive a notification
  