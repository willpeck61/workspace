##1. Introduction##

**1.1 Purpose**

The purpose of this test plan is to describe the way our group is going to test our system to         ensure that once released there is a minimal amount of bugs and issues with the system. We         will be testing our system through a variety of ways, (e.g. Functional Tests, Unit Tests, UI         Tests etc.). This document will detail the following :

**Test Objectives / Milestones –** describes what the objectives are of the tests and what         milestones we have during the project that we need to consider.

**Scopes and Levels of testing** – details the purpose of each type of testing, the scope of each         type of testing, who will be doing the testing, what methods will be used to test and timings         for the         testing.

**Tests to be carried out –** describes what tests we are going to be carrying out and details how exactly we are going to carry out each test, will also describe predicted outcomes of tests.

**1.2 Project Overview**

In this project our goal is to design and develop an application that enables students and professionals across the country be able to find accommodation near their universities and workplaces. The application must be able to allow users to communicate through the application to one another and must also have a built in GPS feature that allows users to get directions to various properties across the country. The application will be initially trialled in Leicester and rolled out to the rest of the UK and a later date.

##2. Objectives / Milestones##

**2.1 Test Objectives**

The objectives of the testing is to ensure that all the features specified by FDM are properly         implemented and are working as they are supposed to be. The tests carried out will         thoroughly test all aspects of the application, from the UI to the data management, the tests         were cover all aspects of the application.

**2.2 Test Milestones**

**       **

| **Milestone** | **Pass / Fail ?** | **Notes** |
| --- | --- | --- |
| 95% of Test Scripts Passed | Pass | 100% Passed |
| All Test Scripts Executed | Pass | All test scripts are executed on startup of the application |
| UI Testing Complete | Pass | UI is working as intended |
| No Security Issues | Pass | No known issues with security |

**2.3 Features to be tested / Features not to be tested**

**Features to be tested :**

- User registration

- User login

- Landlord registration

- Creating and maintaining a property

- Admin account suspension/deletion

- Messaging system

- User Interface

- Express interest

- Property searching

- Editing user profiles

- Storing data

- The buddy up system

- Analytical data

**Features not to be tested :**

- Turn by turn navigation system (Not implemented)

##3. Scopes and Level of Testing##

**3.1 Functional Tests**

PURPOSE:        End users test the system, displays any problems from a user perspective rather than a developer perspective. Tests are based on the requirements of the project.

TESTERS:        Users of the system.

METHOD:        Give a series of tests to users for them to complete.

TIMING:        Will be conducted once the application has several features implemented.

**3.2 Unit Tests**

PURPOSE:        Makes sure the system is working as intended according to the step definitions of the application.

TESTERS:        Cucumber / Step Definitions.

METHOD:        Automatically ran when the application is booted.

TIMING:        Will be conducted throughout development of the application.





**3.3 UI Tests**

PURPOSE:        Checks to make sure that there are no faults with the UI (for example all colouring schemes must be in place and all fonts must be consistent throughout the application).

TESTERS:        Front End Developers

METHOD:        Multiple tests executed by the front end developing, highlighting any faults                         with the UI of the application.

TIMING:        Once the application has a functional user-interface the UI will be tested.

##4. Tests##

**4.1 – Functional /UI Tests**

| **Test Number** | **Test Description** | **Test Type** | Test Result |
| --- | --- | --- | --- |
| 1 | Entering incorrect data types into fields doesn't cause errors | UI | No data type errors occured. |
| 2 | Users are not able to bypass required fields on a form | UI | When a user misses a required field and they submit the form, the form is not submitted and they are told which field they missed. |
| 3 | There is a maximum field entry on fields | UI | Test failed, there is no maximum field entry. |
| 4 | Grammar is correct | UI | Grammar is correct as of Tuesday 26th April. |
| 5 | Font is consistent throughout | UI | Font is consistent throughout. |
| 6 | Design is consistent throughout | UI | Design is consistent throughout. |
| 7 | Instructions are clear and concise | UI | Instructions are clear and concise. |
| 8 | Error messages are clear | UI | Errors are given to the user in simple english rather than using complex technical terms to ensure they are clear to the user. |
| 9 | Errors are documented | UI | ?? |
| 10 | On-screen instructions are available | UI | Instructions are available on all pages. |
| 11 | Users cannot see landlord functionality | UI | Users cannot see the Landlord dashboard,  |
| 12 | Terms and Conditions are clearly visible | UI | Terms/Conditions are located on the top panel, very easy to see. |
| 13 | Privacy Policy is clearly possible | UI | The Privacy Policy is located on the top panel, very easy to see. |
| 14 | Certain panels on the profile page can be collapsed to simplify the page | UI | The buddy list and several other features of the profile page are able to be collpasedmto make the page simpler to view. |
| 15 | Font type/colour/size is easy to read | UI | Very easy to read. |
| 16 | Can users locate houses by searching by postcode, start/end date and price? | Functional | Yes. |
| 17 | Can users register based on their needs? (e.g. Landlord/Searcher) | Functional | Yes, there are two signup options searcher and landlord. |
| 18 | Is a property created succesfully when a landlord creates a property? | Functional | Yes, a property is added to the database and can be found by users. |
| 19 | After successfully creating an account, can a user log in to the application? |  Functional | Yes, no issues. |
| 20 | After being searched are houses located on a customised OpenStreetMap? | Functional | Yes, when viewing a house page there is a map for each property. |
| 21 | When a user searches by preference (e.g. Smoking etc) do only properties with that preference show up? | Functional | Yes, searching with a preference filters out properties correctly. |
| 22 | Can users 'Buddy Up' with other users? | Functional | Yes, users can send requests to other users to buddy up with them. |
| 23 | Are users presented with a 'Buddy Up' option when registering? | Functional | Yes, at the bottom of the signup page there is an option for joining the Buddy program. |
| 24 | Are users notified when another user expressed interest in 'Buddying Up' with them? | Functional | Yes, a user will receieve a notification as a 'buddy request'. |
| 25 | Are users matched with each other based on their requirements? | Functional | ?? |
| 26 | Are users able to message each other? | Functional | Yes. |
| 27 | Are users able to express an interest in a property? | Functional | Yes, they are able to click the express interest button on the property page that sends a notifcation to the landlord. |
| 28 | Are landlords notified when a user expresses interest in a property? | Functional | Yes, Landlords recieve a notifcation that can be found on the notification section. |
| 29 | Are landlords able to upload information and photos of their property? | Functional | Yes, there are a lot of details that landlords can fill in about their property, image upload is included in this. |
| 30 | Can you see other properties on the map? | Functional | No, other properties do not appear on the map. |
| 31 | When a property is clicked on the map is the user taken to the properties page? | Functional | No, currently other properties aren't able to be seen on the map. |
| 32 | If a user leaves feedback on a property is it sucessfully submitted and able to be viewed by other users? | Functional | Yes, onced feedback is submitted it is posted to the property page. |
| 33 | Are local amenities displayed on the map? | Functional | Yes, the application used OpenStreetMaps which displays local amenities. |
| 34 | After a user reports another user/property do admins get a notifcation? |  Functional | Yes, a report appears in the report tab of the admin panel. |
| 36 | Can admins message all user accounts? | Functional | ?? |
| 37 | When an admin suspends a user account, can the user still login? | Functional | No, users are presented with an error saying they don't have access to the website. |
| 38 | Is a users profile succesfully updated when they submit an update profile request? | Functional | Yes, all requested update details are updated immediately. |
| 39 | Can users recover their passwords if lost? | Functional | ?? |
| 40 | Do landlords, searchers and admins have different access rights? | Functional | Yes, users/landlords do not have access to the admin panel and users/admins don't have access to the landlord dashboard. |
| 41 | Can users create properties? | Functional | No, users are unable to access the landlord dashboard so are unable to create a property. |
| 42 | Can landlords buddy up? | Functional | Yes, landlords can buddy up. |
| 43 | Can landlords access the admin panel? | Functional | No landlords are unable to see the tab that enables access to the admin panel. |
| 44 | Can users access the admin panel? | Functional | No, users are unable to see the tab that enables access to the admin panel. |
| 45 | Can users delete their own account? | Functional | No, users cannot delete their own account. |
| 46 | Once a landlord creates a prperty, can they edit the property listing and update it with new details? | Functional | Yes, landlords are able to edit existing listings. |
| 47 | Can landlords delete their own accounts? | Functional | No. |
| 48 | Once a landlord creates a property listing can they then choose to remove this listing? | Functional | Yes, landlords can remove property listings. |
| 49 | Is the propertyy page views statistic updating correctly? | Functional | Yes, after every time a property page is loaded the views statistic increments by one. |
| 50 | Can users buddy up with other users? | Functional | Yes, users can send each other 'buddy requests'. |
| 51 | Are users given a list of suggested buddies? | Functional | Yes, on the profile page users are given a list of suggested buddies. |
| 52 | Can users search for buddies? | Functional | Yes, there is a search box that can search through names of other buddies on the application. |
| 53 | Do users recieve a notification when another user requests to be a buddy with them? | Functional | Yes. |
| 54 | Do users receieve a notifcation when somebody tries to add them as a buddy? | Functional | Yes, they recieve a notification that is shown in the notification panel. |
| 55 | Do users receieve a notification when they recieve a message? | Functional | Yes. |
| 56 | Are users passwords sucessfully changed when they fill out the change password form? | Functional | Yes, users can fill out the change password form and their password is instantly changed. |
| 57 | Can landlords change their passwords? | Functional | Yes, landlords can fill out the change password form on their profile page and their password is then changed instantly. |
| 58 | Can admins change their passwords? | Functional | Yes, admins can fll out the change password form on their profiel page and their password is then changed instantly. |
| 59 | Can users view other users profiles? | Functional | Yes, on the profile page (where buddies and other users are listed) there is a 'click here to view my profile' link that takes the user to the profile of the user. |
| 60 | Do landlords recieve notifications when a user expresses interest in a property? | Functional | Yes, they recieve a notifcation. |
| 61 | Are landlords notified when a user submits feedback on a property? | Functional | Yes, they recieve a notification. |
| 62 | Are users logged out when they press the 'logout' button? | Functional | Yes, users are completely logged out and sent back to the landing page. |
| 63 | Can users upload a profile picture? | Functional | Yes, there is an option on the update profile section to change the profile picture and replace it with a new one. |
| 64 | Can users replace an existing profile picture with a new one? | Functional | Yes. |
| 65 | Is the profile views statistic updating correctly? | Functional | Yes, it is incremented by 1 for every time that the profile is viewed. |




**4.2 – Unit Tests**

The following tests are all ran every time the application is booted up. This ensures that the application is always doing "the things that it should be doing" when it starts up.

**Authorisation**

- Users can only go to certain URLS based on their role

- When a password is retrieved from the database it should be encrypted

**Express Interest**

- Appropriate users are notified when the express notification button is pressed

**Feedback**

- Admins are able to receive ratings from users who have submitted them

- Admins have the ability to approve requests

- Once approved the rating appears on the property page

**Profile**

- Users are taken to their homepage when they click the Profile tab

- Users are able to upload profile pictures successfully

**Registration**

- Users cannot register their user-name to be a name that already exists in the system

- Users data is stored in the database after they register

**Search**

- Properties are able to be successfully searched by postcode

- Users can search properties using multiple specifications (e.g. number of rooms + postcode etc)

- Searching with a postcode containing no properties results in an error message



##5. Key Goals and Deadlines##

| **Goal** | **Completion Date** | **Complete (Yes/No)** |
| --- | --- | --- |
| Unit Testing Complete | Friday 22nd April | Yes |
| UI Testing Complete | Tuesday 26th April | Yes |
| Functional Testing Complete | Tuesday 26th April | Yes |
