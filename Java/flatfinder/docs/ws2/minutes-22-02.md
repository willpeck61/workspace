# Software Engineering Project (CO2015):  Group 3 Meeting Minutes

**Date and time of meeting:**

18:00 22/02/2016

**Participants:**

Phoebe, Will, Connor, Ross, Ihtasham

**Agenda and records of the meeting:**

**Approval of minutes (of previous meeting):**

- Fine (see minutes-18-02)
	
**Matters arising (from previous minutes):**

* In general, how is development progressing?

**Progress during the last period:**

* Each person should break down their planned tasks into smaller steps so they can be better represented on the Gantt chart.
* User login - Completed
* Account creation/registration - Completed
* Distinguishing between account types - Nothing visual, but can select landlord/searcher when signing up to an account
* Adapt current code to allow searcher/landlord/admin [see above]
* Draw object diagram - in progress. Currently have class diagrams of domain entities; discuessed creating 'feature' class diagrams.
(I.e. the create new user feature will have a class diagram containing the user and userRepo classes)
* OpenStreetMap integration - Implemented (using Javascript)
* Local amenities/transport on OpenMaps - Close to completion
* The backend of property and rooms is finished.

Nothing from below has started implementation:
* Property search - Connor
* Basic messaging - Ihtasham
* Real-time messaging - Ihtasham
* Buddy system - Will
* Create new property + upload info and images - Ross
* Express interest in property [depends on create new property being implemented] - Phoebe
* User provided comments/ratings on properties [depends on create new property being implemented] - Gavin
* Delete accounts - Will?
* Suspend account - Will?
* Admin messaging all users - Ihtasham
* Notifications - Undecided (potential SPIKE)

**Tasks and roles during the next period:**

* By Friday 26th we want to have a working webapp (search; select property; make properties.)
* OpenStreetMap will display markers when properties are searched for
* Buddy up/express interest will be applied later (tentatively next week)
* POJOs must be completed ASAP. Users and roles have been completed, along with repositories and controllers.
* Once create new property has been completed, express interest and user ratings can also be completed.

**AOB (Any Other Business):**

* Discussed issue surrounding DBConfig files (for individually working on the code); 
each time someone pushed to the repo the config file would be overwritten, breaking the build for the next person to pull.
* Solution: Ihtasham will set up a remote database that we can all connect to. 
There will then be a single DBConfig file that we all use.

**Role assignment for next week:**


|   Name   |     Role/task performed     |  Comments |
|----------|-----------------------------|-----------|
| Will     |Write controller for user registration. Work on POJOs.||
| Ihtasham |Work on OpenStreetMap implementation.|Searching brings up results on map; markers are shown.|
| Kalin    |-|-|
| Connor   |Complete risk assessment adjustments. Start property search development.||
| Ross     |Continue work on plan. Start development on property upload.||
| Gavin    |-|-|
| Phoebe   |Work on POJOs with Will. Continue Object Design.|Alter class diagrams as discussed.|
	
	
**Date and time of next meeting:**

14:00 Thursday 25th: Check progress on development for features mentioned here.
