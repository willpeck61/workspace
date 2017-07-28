# Software Engineering Project (CO2015):  Group 3 Meeting Minutes

**Date and time of meeting:**

11:00 11/03/2016

**Participants:**

Phoebe, Will, Ross, Connor, Gavin

**Agenda and records of the meeting:**

**Approval of minutes (of previous meeting):**

- Fine (see minutes-09-03)
	
**Matters arising (from previous minutes):**

* Does everyone know what their assigned tasks are/how to go about completing them?

**Progress during the last period:**

* Assigned tasks for Sprint 2

**Tasks and roles during the next period:**

###### Connor [System Tasks]
* FlatFinder Java; adding loads of test properties is inefficient using the current code [they're coded manually]; instead have properties in a file and load them from there into the database.
* Redo search engine; currently returns all properties to page THEN filters; inefficient. Fix by editing repo.
* Add a property properly; currently not checking if filtered properly. Also some issues with database (i.e. didnt insert ownerId)
* Finish Express Interest feature; works visually but doesnt inform user
* Fix back button; using POST form during search, so pressing back prompts resubmitting form. Could change to GET to prevent this.
* Property/Room images upload needs implementing. Every property has many rooms; each room could or could not be occupied; each Room has assigned pictures.
* Extend Gherkin tests
* Landlords need to be able to delete rooms/properties

###### Ross [Project Planning tasks]
* Looking at Bootstrap degins
* Update Gantt
* Everyone needs to keep primarily Ross, but also each other, updated on current work in progress and which tasks they're completed
* Revise risk assessment
* Revise test plan
* Revise configuration management plan
* Consider retrospective when revising

###### Will [Review backlog]
* Implement BuddyUp feature
* Finish Feedback feature
* Implement Report feature
* Terms of Use
* Privacy page
* Help page/search [can be created automatically as a wiki using another service] 
* FAQ
* Profile page needs to have a list of Buddies
* Implement chat on profile pages [messages list?]
* Admin abilities (suspend/delete). Need Admin page/interface
* Password recovery
* Figure out a way to host non-locally for testing purposes

###### Ihtasham [System tasks]
* Message all users as admin
* Webpage statistics (users and properties)
* SPIKE: Routing on maps
* Look at notifications issue; any user can currently see any notification. They should only be able to see their own.
* Redesign CSS
* Revamp messaging system [can currently see others messages by changing ID]

###### Phoebe [Coordinate what needs to be done]
* Convert task list into issue list
* Finish write up of retrospective

###### Gavin [System Design alongside Kalin]
* User Manual: Describe what each feature it is and how it functions
* Installation Guide: This will be worked on once the system is nearer completion
* Object Design: 

**AOB (Any Other Business):**

* All code needs to be supported with Gherkin
* Restyle everything to a common theme
* Go through site with a fine toothed comb and fix all and any minor spelling mistakes/layout errors etc.
* If a new feature/issue is thought of that's not written here, it should be raised as an issue through GitHub.

**Role assignment for next week:**


|   Name   |     Role/task performed     |  Comments |
|----------|-----------------------------|-----------|
| Will     |System developer||
| Ihtasham |System developer||
| Kalin    |-|-|
| Connor   |System developer/tester||
| Ross     |Project planner||
| Gavin    |Object design||
| Phoebe   |Coordinator||
	
	
**Date and time of next meeting:**

12:00 Tuesday 15th: General meeting
