### Group 3

### Sprint 1 Retrospective

#### Start
*	Use sprint_2 branch solo
*	Checking commits before updating Gantt
*	Communicating better with the project planner to ensure accurate plan
*	Asking for more support; commit earlier; communicate better if struggling.
*	More comprehensive testing; there is a lot more we could test. I.e. incorrect form filling, test all URL requests. All points of navigation need to be tested.
*	For each meeting Kalin should update us on what features are outstanding, so we know how much progress has been made. Does the functionality match the feature?
*	Fix minor issues:
  *	Change “Naviate” to view; category 1 becomes map; category 2 becomes property image; category 3 becomes rooms.
  *	Limit profile image to specific size
  *	Clicking on notifications navigates to relevant page
  *	Fix “available to - available to” on cerate property page
  *	Add “property added” notification
  *	Can’t express interest on or edit new property
  *	Fix changing password
  *	Add DbConfig to gitIgnore

#### Stop

*	Committing to the to the wrong branch
*	Committing incorrect database information

#### Continue

*	Implementing Feedback feature [currently 10%]
*	Implementing Buddy Up feature [currently 40%]
*	Finish Express Interest

#### Sprint 1 General Reflection

#### Bad

Will:

*	Security issues; spent 10 days fixing an issue. If you sent a request via mock MVC to the controller URL (behaving as the browser) the function itself wasn’t working; approaching from the wrong direction. Trying to authenticate a user then go to the page, instead of passing details to the page.
*	Getting tests to pass on Travis; due to Travis needing JUnit controller tests implemented to know what to do.
*	Manager: almost lost track of everyone’s progress. Quickly recovered, but had a lapse.

Ross:

*	Could have updated Gantt more regularly/accurately but didn’t have enough detail from other members.
*	Struggled with test plan format; didn’t know how best to represent testing/which format would best benefit project

Connor:

*	Struggled with step defs.

Kalin:

*	Miscommunication with Ross about task completion. Turned to Will to find out what and when tasks were completed, though he should have asked Ross.
*	Lacked programming involvement in order to update backlog and prioritize creating feature file.
*	Missed meetings.

Gavin:

*	Struggled with allocated tasks; needed more support, which wasn’t offered soon enough.
*	Wasn’t allocated tasks suitable to skillset.

Phoebe:

*	Could have started task [object design] earlier; rushed towards end. Could have elaborated on some detail to make file more user-friendly and useful towards the project [i.e. explaining Read/Write terms]

Etty:

*	Changing postcode to coordinates

#### Good

Will:

*	Overcame all issues

Ross:

*	Regularly updated Gantt
*	Successfully Googled test plan format issue and overcame.

Connor:

*	More confident with step defs; has better understanding

Kalin:

*	Nothing of note

Gavin:

*	Made a small amount of progress on writing feedback scenario

Phoebe:

*	Kept track of what people were doing; ensured everyone was aware of the others movements. Kept the project up to date in terms of when meetings are, and what was discussed. Well informed.


#### Other points [for reference]

* Missed two features due to delays in implementing other sections (such as step defs).
* Sprint 1.5 leading up to Easter; 2 afterwards. Everyone needs to be absolutely clear on what will be implemented over Easter.
* Our scope: everything planned was implemented bar the mentioned features. This is represented on the Gantt.
* Time management was fairly good; lots of regular commits. Regular meetings/good structure. Started introducing an agenda for the meeting; stuck to the same ‘what has been done’/’what needs doing’ format.

#### Plan for demonstration

1.	Create user [landlord]
2.	Login
3.	Search for property
4.	Click icon on map to view postcode
5.	Click view property to see full details
6.	Talk about express interest [doesn’t inform searcher that they’ve clicked it, but does inform landlord]; 90% implemented
7.	Demonstrate sending a message; send to other user, log in and view
8.	Show profile view; update buddy status; change name
9.	Show notifications; can be received and viewed, but by everyone [global notifications]
10.	Show landlord dashboard [logged in as previously created user]
11.	Make new listing
12.	Edit existing listing
13.	Search for new listing
14.	View statistics
15.	Talk about change made to PropertyController.java and FlatFinder.java
