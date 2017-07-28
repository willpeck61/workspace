# Software Engineering Project (CO2015):  Group 3 Meeting Minutes

**Date and time of meeting:**

11:00 18/03/2016

**Participants:**

Phoebe, Will, Ross, Connor, Gavin, Kalin

**Agenda and records of the meeting:**

**Approval of minutes (of previous meeting):**

- Fine (see minutes-15-03)
	
**Matters arising (from previous minutes):**

* In general, how is development progressing?

**Progress during the last period:**

* Made progress on assigned tasks

##### Connor
- Test properties and users are now read from a file
- Started reimplementation of search engine using POJOs

##### Ross
- Updated Gantt chart; added issues.
  
##### Will
- Automatic committing to the server; usernames + passwords need creating for each group member, then when pushing to 'live master' a script will run on the server that updates itself and our repo. Alternatively could push to both 'live master' and 'master' (LM is a branch specifically linked to the server).
- [Integrated continuous integration into git, as well as travis. Restarts gradle build; updates gradle bootrun.]
- Fixed change password function
- Changed from GET to POST with change profile function
- Fixed tests in regards to the above [GET to POST]
- Upgraded gradle to 2.12; running two instances of gradle on the same system simultaneously caused conflicts (due to a shared cache)

##### Phoebe
- Updated previous minutes files
- Created current minutes file

##### Gavin
- Fixed Naviate typo
- Made progress on task 2; working on Sprint 1

##### Kalin
- Made progress on task 2; working on Sprint 2. Looked at feedback given for last worksheet and applying this to the current Object Model.

**Tasks and roles during the next period:**

##### Discussion of Feedback
* Project plan: Good. Happy with feedback.
* Object Design: Claims EER was exported from MySQL; intially true, but optional relationships were then manually added. This needs challenging.
* Testing: Some minor issues that have no effect on the testing itself. Some methodology needs changing, and the way objects are retrieved from the database needs making more explicit [dummy properties].
* Software: Must implement visual difference for each user type. Clean up commented out code.
* Minutes: Feedback minutes WERE taken; maybe not linked to?

##### Connor
* Redo search engine using POJOs
* Add a property function needs refining (currently form deosnt check if all fields are populated)
* Finish Express Interest button to send a notification
* Convert search engine to GET method; this will fix Back button issue
* Upload image for properties function [RequestParam name could be changed; POST file and URL]
* Add another field in Property.java file to store image
* Currently add a property, then add rooms. Want to create all in one go, and integrate adding images.
* Landlord needs to be able to edit/delete rooms and properties
* Test everything

##### Ross
* Continue to update Gantt
* Test plan/risk assessment/config management

##### Will
* Add BuddyUp function [implement by 25th]
* Need to test uploading by each logging on and exchanging messages/adding buddies etc
* Upload new Bootstrap template for Landing page
* Registration page: users should only need to submit email, password, and name to create an account. Extra profile detail can be added later.
* Could add 'profile is X% complete' notice on profile.

##### Phoebe
* Write up minutes of meeting
* Assign users to issues

##### Gavin
* Carry on with task 2; will commit current progress by Friday

##### Kalin
* Refine task 2 with Gavin; allocate tasks between themselves. Will commit progress by Friday also.
* Restyle main page to look like a homepage

**AOB (Any Other Business):**
* Develop default Bootstrap style into our own
* Admin functions (Ihtasham)

**Role assignment for next week:**


|   Name   |     Role/task performed     |  Comments |
|----------|-----------------------------|-----------|
| Will     |System developer||
| Ihtasham |-|-|
| Kalin    |Object designer/Website designer||
| Connor   |System developer/tester||
| Ross     |Project planner||
| Gavin    |Object design||
| Phoebe   |Coordinator||
	
	
**Date and time of next meeting:**

14:00 Wednesday 23rd: General meeting (over Skype)
