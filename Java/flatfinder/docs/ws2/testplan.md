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
| UI Testing Complete | Pass / Fail | Some elements of the UI need to be addressed. |
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

**Features not to be tested :**

- The buddy up system

- Analytical data

- Turn by turn navigation system

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

| **Test Number** | **Test Description** | **Test Type** |
| --- | --- | --- |
| 1 | No data type errors occur | UI |
| 2 | Users are not able to bypass required fields on a form | UI |
| 3 | There is a maximum field entry on fields | UI |
| 4 | Grammar is correct | UI |
| 5 | Font is consistent throughout | UI |
| 6 | Design is consistent throughout | UI |
| 7 | Instructions are clear and concise | UI |
| 8 | Error messages are clear | UI |
| 9 | Errors are documented | UI |
| 10 | On-screen instructions are available | UI |
| 11 | Users cannot see landlord functionality | UI |
| 12 | Can users locate houses by searching by postcode, start/end date and price? | Functional |
| 13 | Can users register based on their needs? (e.g. Landlord/Searcher) | Functional |
| 14 | Can landlords create a property? | Functional |
|  15 |  Can users login successfully after signing up? |  Functional |
| 16 | After being searched are houses located on a customised OpenStreetMap? | Functional |
| 17 | Can users search by preference? (e.g. Smoking etc) | Functional |
| 18 | Can users 'Buddy Up' with other users? | Functional |
| 19 | Are users presented with a 'Buddy Up' option when registering? | Functional |
| 20 | Are users notified when another user expressed interest in 'Buddying Up' with them? | Functional |
| 21 | Are users matched with each other based on their requirements? | Functional |
| 22 | Are users able to message each other? | Functional |
| 23 | Are users able to express an interest in a property? | Functional |
| 24 | Are landlords notified when a user expresses interest in a property? | Functional |
| 25 | Are users able to upload information and photos of their property? | Functional |
| 26 | Are nearby properties displayed on the map? | Functional |
| 27 | When a property is clicked on the map is the user taken to the properties page? | Functional |
| 28 | Are users able to provide feedback and ratings on properties? | Functional |
|  29 | Are local amenities displayed on the map? | Functional |
|  30 |  Do admins receive report notifications from searchers and landlords? |  Functional |
| 31 | Can admins message all user accounts? | Functional |
| 32 | Can admins suspend/delete accounts? | Functional |
| 33 | Can users update their profile successfully? | Functional |
| 34 | Can users recover their passwords if lost? | Functional |
| 35 | Do landlords, searchers and admins have different access rights? | Functional |
| 36 | Can users create properties? | Functional |
| 37 | Can landlords buddy up? | Functional |
| 38 | Can landlords access the admin panel? | Functional |
| 39 | Can users access the admin panel? | Functional |
| 40 | Can users delete their own account? | Functional |









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

**-** Properties are able to be successfully searched by postcode

- Users can search properties using multiple specifications (e.g. number of rooms + postcode etc)

- Searching with a postcode containing no properties results in an error message



##5. Key Goals and Deadlines##

| **Goal** | **Completion Date** | **Complete (Yes/No)** |
| --- | --- | --- |
| Unit Testing Complete | Friday 26th February 2016 | Yes |
| UI Testing Complete | Wednesday 2nd March 2016 | No |
| Functional Testing Complete | Wednesday 2nd March 2016 | No |
