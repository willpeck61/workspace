##FLATFINDER
###Group Three

#Project Backlog

##Project Goal

The goal as taken from the case study is as follows: to help students and professionals across the country find accommodation near their universities and workplaces respectively. FlatFinder will cater to users who wish to find accommodation for both long-term and short-term/overnight stays. The company has decided to develop a trial for a responsive site for use within the Leicester area which can be used on Desktop and mobile devices.

Our personal project goal is to develop a web application whose function is to provide a simplistic, easy-to-use, interface whereby users can search for nearby properties, connect with other users, and give feedback on the content shown. Various features will include a search engine, a user-to-user system of interaction, a mapping system with to scale navigation and realistic distance control. The system will support numerous account types allowing different levels of access to various functions. The overall system will be created using Java, HTML, MySQL, Javascript, and servlet technology.

###Features

*  Messaging service

*  Help feature

*  About Us

*  Privacy

*  Terms of Use

*  OpenStreeMap with some visualised features

*  Display nearby properties

*  Route feature

*  Local amenities and transport links

*  Communication mechanism

*  Register

*  Security

*  Report feature



###Landlord and Agent

*  Property information management

*  Personal property statistics

###Administrator

*  Expire accounts

*  Message all users

*  Account suspension

*  Confidential statistics (overall users on the site, active users)

####Searcher

*  Profile Update

*  Property statistics (popular houses, popular areas)

*  Express interest

*  View statistics

*  Buddy-up feature

*  Different account handling(Landlords, Searchers and Administrators,Agents)

*  Express interest

*  Search features

*  Advertisement type

*  Switch views (long-term stay and short-term)

*  Local search

*  Length of stay search

*  Price search (pw/pcm)

*  Smoking/non-smoking

*  Degree level

*  Atmosphere

*  Room type or whole properties

*  Bills

*  Gender preferences

*  Size

*  Availability

*  Occupation

*  Age range

*  Size of household

*  Live with landlord

*  Pets allowed

*  Parking Available

*  En suite

*  Photo ads only

*  Vegetarians proffered

*  DSS

*  Disabled accessibility

###Gherkin notations

Feature: **Help**

>**As a** site visitor of  FlatFinder

>**I want** to find help

> **In order to** find answers on common questions or usability support

Scenario: Open FAQ's

> **Given** I'm on  FlatFinder's homepage

> **When** click on FAQ's link

> **Then** system should provide me with the list of frequently asked questions

> **And** corresponding answer

Feature: **Report**

> **As a** site member

> **I want to** report advertisement

> **In order to** keep community free of scam

Scenario: open report submission page for advertisement

 >**Given** I'm logged in as a 'Searcher'

> **When** I open advertisement for room to rent name "TestReport"

> **And** click on 'Report' link

> **Then** system should lead me to the report submission page

> **And** advertisement reference number is display for user

Scenario: provide topic, subject, details and priority

> **Given** ticket submission form with fields for subject, details

> **When** I provide report ticket subject 'Scam'

> **And** details 'Using FlatFinder website for scam'

> **Then** system must store the information as  'High' priority

> **And** shows a summary of the report ticket

> **And** send a confirmation email

Scenario: open report submission page for a member

> **Given** I have opened user profile with name 'Test1'

> **When** press on 'Report' link

> **Then** system should lead me to the report submission page

> **And** User ID  is displayed for user

Scenario: report a member

> **Given** report submission form for user with name 'Test1'

> **When** I provide support ticket subject 'Bullying'

> **And** details 'I'm being bullied'

> **Then** system must store the information with 'High' priority

 >**And** show a summary of the report ticket

> **And** send a confirmation email

Feature: **Contact us**

> **As a** site visitor

> **I want to** find contact details

> **In order to** get in touch with developing team

Scenario: Open Contact us

> **Given** I'm on FlatFinder's homepage

> **When** click on 'Contact us'

> **Then** system should provide me with the possible ways to contact developing team

Feature: **Privacy**

> **As a** site visitor

> **I want to** find privacy information

> **In order to** make sure of personal data protecting information

Scenario: Open Privacy

> **Given** I'm on FlatFinder's homepage

> **When** I click on  'Privacy' link

> **Then** system should provide me with the 'Privacy' information

Feature: **Terms of Use**

> **As a** site visitor

> **I want to** find terms of use

> **In order to** find out about restrictions and terms of use

Scenario: Open Terms of Use

> **Given** I'm on FlatFinder's homepage

> **When** I click on 'Terms of use' link

> **Then** system should provide me with the 'Term of use' information

Feature: **About Us**

> **As a** site visitor

> **I want to** find out about the company

Scenario: Open About Us

 >**Given** I'm on FlatFinder's homepage

> **When** I click on  'About Us' link

> **Then** system must provide me with the 'About Us' information

Feature: **Profile Update**

>**As a** 'Searcher'

>**I want to** describe myself on my own page in a semi-structured way

>**In order to** make sure other member would like to 'buddy up'

Scenario: profile update after successfully log in

> **Given** I'm logged in as 'Searcher'

> **And** viewing my account page

> **When** I click  on 'Edit my profile'

>**Then** system should provide me with predefined fields with personal information that can be changed

> **And** 'About me' free-text field

Feature: **View statistics**

> **As** a site member

> **I want to** see statistics allowed for my account

Background:

> **Given** access for 'My account'

> **And** I have 'My account' page opened

Scenario: open statistics view as a 'Searcher'

> **Given** I can see account tool bar

> **When** I click on 'My statistics'

> **Then** system should provide me with number for account views

> **And** 'Property view history'

Scenario: open statistics view as a 'Landlord'

> **Given** account tool bar

> **When** I click on 'My statistics'

> **Then** system should provide me with a list of my properties

> **And** corresponding property views

Scenario: open confidential statistics view as 'Administrator'

> **Given** account tool bar

> **When** I click on 'View statistics'

> **Then** system must provide me with overall users on the site information

> **And** overall registered members

> **And** people visited my profile

> **And** 'Property view history'

Feature: **Profile security**

> **As a** site member

> **I can** mark my profile as private in which case only my name and picture will appear

> **In order** to keep my information private

Scenario: set account as 'Private profile'

> **Given** access to 'My account' system feature

> **When** I click on 'Profile Settings'

> **And** select 'Private account' in the list of settings

> **And** click 'Save' button

> **Then** system must set information to be invisible for other users

Feature: **Password recovery**

> **As a** site member

> **I want to** recover my password

> **In order to** log in to the system

Scenario: find password recovery feature

> **Given** login page

> **When** I click on 'Forgotten Password'

> **Then** system should provide me with fields to be field for password recovery

Scenario: submit password recovery form

> **Given** 'Forgotten Password' page

> **When** I finished filling field with valid information for my account

> **Then** system must my credential in a database

> **And** send me an email with corresponding password

> **And** prompt a message with successful recovery

Feature: **Messenger**

> **As a** site member

> **I can** send message to any member who are currently 'buddied up' with me

> **In order to** stay in touch with other possible house members

Scenario: message a current 'Buddy up' member

> **Given** I'm viewing  'My account' page

> **When** I go to my 'Buddy ups'

> **And** select at least one of them

> **Then** system should show instant message input box

Feature: **Register**

> **As a** site visitor

> **I want to** become part of a community

> **In order to** find a room for rent

Scenario: find registration form

> **Given** I'm on FlatFinder homepage

> **When** I click on 'Registration' link

> **Then** system should lead me to Registration page

Scenario: application submission as a 'Searcher'

> **Given** I'm on the registration page

> **And** I have defined myself as a 'Searcher'

> **When** I filled all mandatory field (Name, Surname, Email, Password)

> **And** submit my application by clicking 'Register'

> **Then** system should store the information provided information

 >**And** encrypt password

> **And** notify new member by confirmation email of successful registration

> **And** prompt a message of successful registration with potential buddy ups according to interest provided in the application

Scenario: application submission as a 'Landlord'

> **Given** I'm on the registration page

> **And** I have defined myself as a 'Landlord'

> **When** I filled all mandatory field (Name, Surname, Email, Password)

> **And** submit my application by clicking 'Register'

> **Then** system should store the information provided information

> **And** encrypt password

> **And** notify new member by confirmation email of successful registration

> **And** prompt a message of successful registration

Feature: **Administration**

> **As a** site administrator

>**I can** suspend or edit any member of the system

>**So that** I can I protect community

Scenario: find account managing tool

> **Given** I'm logged in 'My account'

> **And** I have 'My account' page opened

>**And** I have administrator tool bar

> **When** I click on 'Account search'

> **Then** system must provide me with a list of all member registered recently

> **And** a search box for direct account search

Scenario: find a member for suspension

> **Given** list of all member registered recently

 >**And** account search box

> **When** I type name 'Test' in the search box

> **Then** system must provide me with a list member containing name 'Test'

Scenario: single account selection

 >**Given** list of members with name containing 'Test'

 >**When** I select member with name 'Test1'

> **Then** system must open 'Test1' profile

Scenario: account suspension

> **Given** opened 'Test1' profile

> **When** I click on 'Suspend account'

> **Then** system amend account as a 'Suspended'

> **And** prompt me a message with successfully suspension

> **And** send me back to list of all members

Scenario: account editing

> **Given** opened 'Test1' profile

> **When** I click on 'Edit profile'

>**Then** system must provide me with corresponding field that can be chaged

Scenario: successful editing

> **When** I change name 'Test1' to 'Test3'

> **And** submit my charges by clicking on 'Submit'

> **Then** system must store new name 'Test3' in the system

>**And** prompt me a message with successfully editing

> **And** send me back to 'Test3' profile

Feature: **Message all users**

>**As a** site administrator

>**I can** message all users

>**So that** I can inform everybody in case of emergency

Background:

> **Given** all members has email address

> **And** all members had in site inbox

Scenario: find 'Message to all users' function

> **Given** access for 'My account'

> **And** I have 'My account' page opened

> **And** I have administrator tool bar

> **When** I click on 'Message to all users' link

> **Then** system must provide me a page with email form submission

Scenario: send message to all users

> **Given** a page with email form submission

> **When** I provide subject into 'Subject' text box

> **And** message into 'Message' text box

> **And** I press 'Send' button

> **And** System must notify with me successful operation

> **And** send message with a subject to all registered members

Scenario: cancel send message to all users

> **Given** a page with email form submission

> **When** I press on 'Cancel' button

> **Then** system must redirect me back to 'My account' page

Feature: **Property advertisement**

>**As a** site 'Landlord'

>**I can** advertise property for rent or sale

>**So that** I can let people find my advertisement

Scenario: open advertisement form

>**Given** I'm logged in as a 'Landlord'

>**When** I select 'Place an ad'

>**Then** system show me an advertisement submission form

Scenario: advertisement submission

>**Given** I'm viewing the  advertisement form with the mandatory fields

>**When** I fill "Type" with "Shared"

>**And** I fill "Postcode" with "LE2 6BF"

>**And** I fill "Price" with "65" per "pw"

>**And** I fill "Occupation" with "Students"

>**And** I fill "Gender preferences" with "Males Only"

>**And** I choose "Availability" to be "12-02-2016" to "01-01-2017"

>**Then** system must store the information

>**And** send me next part of the submission

Examples:

|**Type**|**Postcode**|**Price**|**Occupation**|**Room type**|**Gender**|**Sizes**|**Availability**|
|:---|:---|:---|:---|:---|:---|:---|:---|
|Shared|LE2 6BF|65  pw|Students|Single room|Males Only|12|12-02-2016 to 01-01-2017|

Scenario: advertisement submission 2st step

> **Given** I have completed 1st submission part

> **And** next part of the submission from is displayed

>**When** I choose "Atmosphere" to be "Quite"

>**And** I choose "Smoking" to be "No"

>**And** I choose "Age range" to be "20" to "25"

>**And** I choose "Degree level" to be "Phd"

>**And** I choose "Size of household" to be "4 bedroom"

>**And** I choose "Live with landlord" to be "No"

>**And** I choose "Pets" to be "No"

>**And** I choose "Parking Available" to be "No"

>**And** I choose "Vegetarians" to be "Vegan"

>**And** I choose "DSS" to be "Yes"

>**And** I choose "Disabled accessibility" to be "No"

>**And** press on 'Submit' button

>**Then** system must store all submission form data

>**And** send to the upload main image page

Examples:

|**Atmosphere**|**Smoking**|**Age range**|**Degree level**|**Size of household**|**Live with landlord**|**Pets**|
|:---|:---|:---|:---|:---|:---|:---|
|Quite|No|20 to 25|Phd|4 bedroom|No|No|

|**Parking Available**|**En-suit**|**Vegetarians**|**DSS**|**Disabled accessibility**|
|:---|:---|:---|:---|:---|
|No|Yes|Vegans|Yes|No|



Scenario: main image uploading stage

>**Given** property image uploading function

 >**When** I click on 'Browse'

> **And** I select 'house.jpg'

 >**And** click 'Upload' button

 >**Then** system must store uploaded picture

 >**And** store all form data in to database with the image URL

>**And** prompt a message of successful advertisement

Feature: **Property management**

> **As a** landlord

 >**I can** delete one of my advertisements

> **So that** I can keep only fresh once in the community

>Scenario: find advertisement to be deleted

>**Given** access for 'My account'

> **And** I have 'My account' page opened

> **And** I have member tool bar

> **When** click on 'My Ads'

>**Then** system must send me to the page with current advertisements online

Scenario: select an advertisement

> **Given** list of active advertisements

> **When** I click on advertisement 'Test1'

> **Then** system must send me to the page of advisement 'Test1'

Scenario: edit advertisement

> **Given** advertisement 'Test1'

> **When** I click on 'Edit advertisement'

> **Then** system should provide me with advertising form

Scenario: editing submission

> **Given** advertising form which is currently under editing

> **When** I set "Smoking" to be "Yes"

> **And** press on 'Save' button

> **Then** system must store the information

> **And** prompt a massage of successful editing

> **And** send me back to the list of active advertisement

Feature: **Property search**

> **As a** site member

> **I can** use search box tool

> **So that** I can find accommodation to rent

Scenario: basic search usage

> **Given** I'm logged as a 'Searcher'

> **And** I'm on the home page

> **When** I use search box typing 'LE2' into 'Postcode' field

> **And** click on 'Quick search' button

>**Then** system must send me to the page with result for 'LE2' postcode property advertisements

Scenario: open advance search option

> **Given** access to 'My account'

> **And** home page opened

 >**When** I click on 'Search' link

> **Then** system must take me to page with advance searching options

Scenario: advance search usage

>**Given** I'm on the advance search page

> **And** I have completed search parameters

> **When** I click on 'Search' link

> **Then** system returns the matching properties

Feature: **Express interest**

> **As a** site member

> **I want** to express interest in advertisement

> **In order** to get in touch  with 'Landlord'

Scenario: send an express interest notification to 'Landlord'

> **Given** I'm logged in as a 'Searcher'

> **When** I open advertisement for room to rent name 'TestReport'

> **And** click on 'Express Interest' link

> **Then** system send a notification to corresponding 'Landlord'

> **And** a  successful notification is displayed with email confirmation

Feature: **Notification**

>**As a** site 'Landlord'

> **I want to** know who has expressed interest in my property

> **In to** order to get in touch with them

Scenario: receiving an notification

> **Given**  I'm logged in as a 'Landlord'

>**When** I have received  'Express interest' notification for 'TestReport' property

>**Then** system must shows the that I have non-read notification

Feature: **Buddy-up**

> **As a** site member

> **I can** see for profiles based on buddy up system

 >**In order to** rent a house in a group of people

Scenario: send an 'buddy-up' request to another user

> **Given** I'm logged in as a 'Searcher'

> **When** I open suggested profile 'Test1'

 >**And** click on 'Buddy-up' link

> **Then** system send a notification to 'Test1' for buddy-up approval

Scenario: buddy-up acceptance

> **Given** notification 'Test1' for buddy-up approval

>**When**  'Test1' accepts the request

> **Then** system stores 2 people as a buddy-ups

Feature: **Login feature**

>**As a** site member

>**I can** log in at any time

>**So that** I can use site system feature

Background:

>**Given** credentials are valid

Scenario: get to the login page

>**Given** I'm on FlatFinder home page

> **When** I click on 'Log in' link

> **Then** system must take me to the login page

Scenario: log in procedure

> **Given** I'm on 'Login in' page

> **And** I'm registered

> **When** I type 'Test1' in account field

> **And** 'password1' into password field

> **Then** system take me to the 'My account' page

Feature: **Log out**

>**As a** site member

>**I can** log out at any time

>**So that** I keep my account safe

Scenario: log out procedure

>**Given** access to 'My account'

> **And** home page opened

> **When** I click on Log out

> **Then** system must delete all session files on my computer

> **And** prompt me a message of successfully log out

 >**And** redirect me back to Homepage

**Optional Features**

Feature: **Support Ticket**

 >**As a** site visitor

 >**I want** to ask support team for a help

 >**In order** to resolve a current doubts or problems which comes with FlatFinder

Scenario: open support ticket submission page

 >**Given** FAQ's page

 >**When** I click on 'Create new support ticket' button

> **Then** system should lead me to the ticked submission page

Scenario: provide topic, subject, details and priority

>**Given** ticket submission form with fields for subject, details, priority, contact email, name

>**When** I provide support ticket subject 'General'

>**And** details 'Can't find register form' with 'High' priority

>**And** provide my email address, name

>**Then** system must store the information

> **And** shows a summary of the support ticket

>(Optional) **And** send a confirmation email


Feature: **Group message**

>**As a** site member

>**I can** send message to a group of users who are currently 'buddied up' with me

>**In order** to organise viewings

Scenario: message group of 'buddy ups'

> **Given** a dialog box with a current 'buddy up'

> **When** I click on 'add member'

>**And** select one more from the list

> **Then** system should add the next person to the chat

> (optional) **And** notify new person that he was added to the chat



Scenario: multiple account suspension

>**Given** list of all member registered recently

> **And** account search box

> **When** I select account names 'Test1'

> **And** 'Test2' by click on ticking box

> **And** press 'Suspend selected'

> **Then** system must delete all 'Test1'

>**And** 'Test2' account records from the database

>**And** prompt me a message with successfully suspension


*  As a site visitor, I want to see a list of the most popular items on the site.

*  As a site visitor, I want to be able to get back to the home page quickly and easily.

*  As a site member, I want to have full access to all articles.

*  As someone who wants to hire, I can post a "help wanted ad".

*  As a member, I can update one of my existing courses or events.

*  As a site member, I can subscribe to an RSS feed of news (and events? Or are they separate?).

*  As a site editor, I can maintain an FAQ section

*  As a site member, I can do a full-text search of the FAQs. (Maybe we want this for any site visitor?)

*  As a site member, I can mark my email address as private even if the rest of my profile is not.

*  As a site developer I want to be able to indicate whether an adv is publicly available or for members only.

*  As someone who has posted an ad that is about to expire, seven days before it expires I want to be emailed a reminder so that I can go extend the ad.

*  As a member, I can copy one of my courses or events so that I can create a new one.
