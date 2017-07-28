# Software Engineering Project (CO2015):  Group 3 Meeting Minutes

**Date and time of meeting:**

14:00 25/02/2016

**Participants:**

Phoebe, Will, Connor, Ross, Gavin

**Agenda and records of the meeting:**

**Approval of minutes (of previous meeting):**

- Fine (see minutes-22-02)
	
**Matters arising (from previous minutes):**

* In general, how is development progressing?

**Progress during the last period:**

Will:
* Reformatted project; removed old content and references from back end, but not front end.
* Looked at implementing google checkstyles; changed files to match the checkstyle.
* Checkstyle not integrated in gradle; instead using a plugin (but not capable on lab machines).
* Created main POJOs (property, user [ammened to add address details, status of user etc], room, role, buddyup, propertyimage, propertyfeedback)
* Created repos for all POJOs.
* Created registration form and login functions, and their respective and step defs (currently passing).
* Created rudimentary view profile page.

Ihtasham:
* Created remote database to allow uniform DbConfig file.
* Completed implementation for maps
  * The map is scrollable
  *  Properties can be added
  * Clicking on a postcode makes the map center on the property
  * Rolling over will show information on that property (this is not 100% implemented yet)

Ross:
* The test plan is in progress.
* Completed configuration managment plan
* Property upload function: the code for the controller, property POJO, and JSP page are implemented
* Added detail to the project plan, meetings, and minutes sections of the Gantt chart

Gavin:
* Worked on user feedback function
* Created class diagrams 
* Finished feature file/step defs.

Connor:
* Created search form.

Phoebe:
* Created POJOs and repositories for PropertyImage, PropertyFeedback, and BuddyUp.
* Updated POJOs for User, Property, and Room [added various attributes to all, such as address for User].
* Updated domain model to reflect new POJOs and state of existing POJOs.

**Tasks and roles during the next period:**

* By Sunday we must all send Ross a breakdown of the tasks we've completed/will complete so he can update the Gantt chart

Will:
* Implement method to allow travis to pass step defs (currently runs gradle check, not bootrun).
* Formatting of profile page
* Continue supporting other team members in their tasks.
  * By the end of the day (25/02) Will should be done with the first two tasks to allow maximum support

Ihtasham:
* Start implementation of messaging function.
* Create default view for admin/landlord/searcher; these will be visually different to differentiate between account types

Ross:
* Include functional tests and UI in the test plan
* Start risk assessment [this will be done by revising the old assessment]
* Create feature file and step definitions for the property upload function
* Add object design and software architecture tasks to the Gantt chart
* Split development of source code into ~25 tasks
* Add resources to Gantt chart
* Implement express interest [this may be done by Gavin or Phoebe, depending on who completes their other tasks first]

Gavin: 
* Implement step defs for user feedback function

Connor:
* Finish search function
* Create gherkin scenarios
* Draw function class diagram.

Phoebe:
* Ammend repository layer class diagram to include new repositories
* Implement express interest [this may be done by Gavin or Ross, depending on who completes their other tasks first]

**AOB (Any Other Business):**

On Thursday 3rd all of the team will meet to do a final check of all files and ensure everything is as it should be before submitting.

**Role assignment for next week:**


|   Name   |     Role/task performed     |  Comments |
|----------|-----------------------------|-----------|
| Will     |Code developer/support provider|Front end|
| Ihtasham |Code developer|Front end and back end|
| Kalin    |-|-|
| Connor   |Code developer/test implementer/object designer||
| Ross     |Project planner/code developer/test implementer||
| Gavin    |Code developer/test implementer||
| Phoebe   |Continue work on object design/some code development||
	
	
**Date and time of next meeting:**

15:00 Monday 29th: General check-up on progress
