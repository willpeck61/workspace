# Software Engineering Project (CO2015):  Group 3 Meeting Minutes

**Date and time of meeting:**

14:00 13/04/2016

**Participants:**

Phoebe, Will, Connor, Ihtasham, Ross

**Agenda and records of the meeting:**

**Approval of minutes (of previous meeting):**

- Fine (see minutes-30-03)
	
**Matters arising (from previous minutes):**

* In general, how is development progressing?

**Progress during the last period:**

##### Connor
- Fixed 'Express Interest' [created a new table in the database]. Landlords will receive a notification when a user clicks "Express Interest" on their property.
- Added a parameter to the search engine that prevents properties marked as "unlisted" from being returned.
- Landlords can now see which user is occupying their room/property. This includes a link to the user's profile.
  
##### Will
- Implemented Buddy Up feature:
  - Can search for buddies by username, interest, gender etc
  - A user can request to 'Buddy up' with another user
  - A user will receive a notification if another user requests to Buddy up with them
  - A user can accept or reject a Buddy up request, and view the profile of the user that requested.
  - A user's profile contains a list of current buddies, and a list of pending buddies (requests that user has made).
  - Buddies can be removed or messaged.
- Pushing to the GitHub repository also pushes the changes to the live version of the website.
- Fixed issue where two people requesting the each other as a Buddy created duplicate BuddyUps

##### Phoebe
- Updated supervised meeting/minutes files

##### Ihtasham
- Notifications are now sent when a property is added
- Clicking on a notification will take you to the relevant page
- Admin dashboard was implemented:
  - Admins can manage users and listings
  - View reports on properties.
  - Managing users allows the admin to edit the profile of, message, (un)suspend, or delete a particular user.
  - Managing a listing allows the property or rooms profiles to be edited.
  - Admins can also hide, unlist, or delete properties, and delete rooms.
  - Admins have the option to send a message to all users simultaneously.
- Fixed error whereby users could view other user's messages
- Landlords can view analytical data about properties (such as profile views).
- Users can leave comments and ratings on properties.
- Landlords will receive notifications when users leave a comment on their property.
- Properties can be reported.

##### Ross
- Updated the Gantt chart to reflect recent changes
- Updated the configuration management plan to include Sprint 2

**Tasks and roles during the next period:**

##### Connor
* Add property profile page (showing static property/room images)
* Implement checkstyle on code. Add comments.
* Add Bootstrap carousel to rooms pages to dynamically display images.
* Add missing search parameters that were specified in the requirements.
* Add ability to upload a floor plan to a property/room

##### Will
* Be able to upload multiple pictures to properties/rooms
* Add a Bootstrap carousel to property profiles so the images are displayed dynamically.
* Allow user to view analytical information about their profile
* Fix issues concerning Buddy Up (reported in Issues)

##### Phoebe
* Write up minutes of meeting
* Plan retrospective for Sprint 2 [this will be done most easily in person].
* Start editing the style/CSS of the website
  * Remove the navigate side bars
  * Add a carousel on the search page; images have yet to be decided
  * The most developed style is on the profile pages; use view-profile.jsp as a template.
  * Match the profile style to the other pages [mainly html tags].
  * Looking at the templates supplied by Ross; use template 1 for the login page primarily, then the style could be applied across the entire site.
  * Add a description of the website to the login page

##### Ihtasham
* Allow users to specify a home/uni/work location when they sign up
* Show the distance from the user's home/uni/work and a specified property on the map
* Implement a system whereby a user will be sent an email after setting up their account
* Allow the users to reset their password; again this will be done via email [see details below].

##### Ross
* Implement CSS for login page
* Start work on overall site design?

**AOB (Any Other Business):**
* The deadline for the worksheet is 9am on the 28th of April, however we want to have everything submitted on the 27th.
* Restarting bootrun keeps the cookie active, so you aren't logged out of the site.
* Refreshing the page should stop security config from keeping you logged in.
* [I] Email users when setting up account/changing password (issues with spring security). Must generate token; send via email; then verify token when reactivating account. (CSRF tokens can't be sent via email). Create new table with token and userid; after verification delete the token so it cant be re-used. Java mail centre for sending messages.

* Reread requirements to ensure we've covered all aspects:
  * [C] Searching properties; need to implement specified search parameters (e.g. start/end date).
  * [I/W] View site on desktop and mobile (will be implemented with Bootstrap/client side code).
  * [I] Return distances to (current) home/work/uni. On sign up a user should set these things, then on viewing a property they'll be displayed (with distances).
  * [C] Be able to specify postgraduate in the property search
  * We won't implement optional search parameters; this will narrow search down too much.
  * [W] Fix BuddyUp issues
  * [C] Allow uploading floor plan images to properties and rooms
  * [C] Add 'Information about local area' section? [Could have]
  * [W] Add analytic information to a user's profile (such as profile views).
  * Track visitors through page views recording profiles?
  
* Design
  * One person will implement all CSS code in order to keep the design consistent and smooth (instead of each person designing a specific page).
  * Use a basic template and extend this across the entire site; this way code can be copied and pasted.
  * Add collapsable panels to areas of heavy information.
  * [W] Add carousel to property page so the images are displayed dynamically.
  * Ensure navigating between pages is clean and meets HCI standards.

* Testing
  * Testing must be completed before Design
  * [W/C/I] will start testing this week.
  * Could create a table to record client side issues (similar to GitHub issues).
  * [W] Ensure you can't access anything when logged out.

* We will set a time/date to collectively work on the design of the website.
* By next Wednesday we want all features to be completed and testing to have begun. We can then further plan design/optional features.

**Role assignment for next week:**


|   Name   |     Role/task performed     |  Comments |
|----------|-----------------------------|-----------|
| Will     |System developer||
| Ihtasham |System developer||
| Kalin    |-|-|
| Connor   |System developer||
| Ross     |UI designer||
| Gavin    |-|-|
| Phoebe   |Coordinator/UI designer||
	
	
**Date and time of next meeting:**

14:00 Wednesday 20th: General meeting (over Skype?)
