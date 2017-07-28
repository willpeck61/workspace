#Website Requirements Specification

##**FlatFinder**

###Created by Group Three

###Version 1.0 - Issued January 28, 2016

This requirements specification is used to record the user requirements for the FlatFinder website.

**BACKGROUND**

The purpose of this project is to create a responsive web application named "FlatFinder" which provides a property finding service to students and professionals within the Leicester area.  The search results will be refined to return long term and short-term / overnight stays alongside distance to workplace or university.  By implementing a responsive design, the website should be usable on both desktop and mobile devices.

![group3usecasediagram-1](https://cloud.githubusercontent.com/assets/14922982/12885589/026b756c-ce5f-11e5-951a-0e0390fcdfc2.png)
[Link to Usecase Diagram](Group3UseCaseDiagram.pdf)

**1.  Visitor Interaction**

| **Req** | **Description** | **Comments** |
| --- | :--- | :--- |
| 1.1 |  [Must have] the ability for a searcher to find properties by postcode, start date and end date and price range.   | Price range can be indicated by price per week or per calendar month  |
| 1.2  | [Must have] the ability to view the site on both desktop and mobile | This can be broken down into further requirements dependent on what is visible through the desktop and mobile interfaces |
| 1.3 | [Must have] the ability for visitors to register for an account based on their needs e.g. is the visitor a student / professional or landlord. |   |
| 1.4 | [Must have] the ability for a registered landlord to add a new property to the system. |   |
| 1.5 | [Must have] the ability for visitors to be able to log in to their account. |   |
| 1.6 | [Must have] different views dependent on role. | The appearance of the site should make it explicitly clear as to the role/access rights of the current signed in user |
| 1.7 | [Must have] returned results on a customized OpenStreetMap map along with distances to work or university. | This could also include distances to preset 'home' locations  |
| 1.8 | [Should have] public transport and local amenities displayed on the OpenStreetMap map. | |
| 1.9 | [Must have] other selectable search preferences such smoking / non-smoking, undergraduate / postgraduate, quiet / lively atmosphere, room type etc. | Another search preference to consider is occupation of other residents  |
| 1.10 | [Could have] other search preferences such as occupation(s) of other resident(s), bills included, gender, dimensions, age range of residents, number of bedrooms, pets allowance, parking, en suite, vegetarian/vegan, disabled accessibility, DSS, etc |
| 1.11 | [Must have] "buddy up" option with other searchers that can be selected when initially registering with the site. | |
| 1.12  | [Must have] "buddy up" button available on user's profile page, to be clicked by other users |   |
| 1.13  | [Must have] the ability to notify users when another user has expressed interest in "buddying up" with them | This notification will be viewable in the mail interface. [All user accounts must have their own individual inboxes displaying received mail, sent mail and notifications through button-presses (buddy-up, interest in property, report).] |
| 1.14 | [Must have] the ability to allow searchers to message each other. | Again, viewable in the mail interface. |
| 1.15 | [Must have] the ability to match potential buddies based on requirements and preferences. | This implies the need for a "buddy" search function. |
| 1.16 | [Must have] the ability for searchers to express an interest in a property without messaging a landlord directly. | Searchers should be able to express an interest in a property without messaging a landlord directly. For this, an 'express interest' button must be implemented when a Searcher is viewing a property. This will alert the landlord that a Searcher is interested in their property. |
| 1.17  | [Must have] an 'express interest' button available on each property page when logged in as a searcher | For this, an 'express interest' button must be implemented when a Searcher is viewing a property. |
| 1.18 | [Must have] the ability to notify landlords that a searcher has expressed an interest in their property. |   |
| 1.19 | [Must have] the ability for landlords to upload both information and images about their property. | Information includes: Price per month/week, number of rooms, room dimensions, floor plan, dates available, address, information about local area, bills included etc. |
| 1.20 | [Must have] the ability for searchers to see analytic information on their profile including profile views | Implies that user profiles will need to be created and viewable by other users.  Important for risk analysis to consider the Data Protect Act consequences of this. |
| 1.21 | [Must have] the ability for landlords to see analytic information on their properties including number of views and similar historical data. |   |
| 1.22 | [Should have] the ability to give turn-by-turn directions to properties selected by searchers via GPS tracking. | Can this be delivered by a web application? |
| 1.23 | [Should have] the ability to display nearby properties within an adjustable radius which match their search requirements, as well as their distances from an entered university or workplace. | Could we use IP location? |
| 1.24 | [Should have] the ability to display all local amenities and transport links on the map in the local area. |   |
| 1.25 | [Should have] the ability to generate statistics such as popular houses (most looked at), popular areas and whether an area is mainly students or working professionals. |   |
| 1.26 | [Should have] the ability to allow searchers to click on a property on the map and view the property page. |   |
| 1.27 | [Should have] the ability to switch properties listed on the map based on long term or short-term stay properties. |   |
| 1.28 | [Should have] the ability for users to submit time taken to get between houses and university or workplace. |   |
| 1.29 | [Should have] the ability for users to inject information onto maps such as local amenities and other facilities. |   |
| 1.30 | [Should have] the ability for users to provide feedback/ratings on both accommodation and amenities. |   |


**2.  Editing, Updates and Administration**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 2.1 | [Must have] the ability for an administrator to receive report notifications from searchers and landlords. |   |
| 2.2 | [Must have] the ability for administrators to message all user accounts. |   |
| 2.3 | [Must have] the ability for administrators to suspend or delete user accounts. |   |
| 2.4 | [Must have] the ability for administrators to expire accounts after a predetermined period of time. |   |
| 2.5 | [Must have] the ability for all user accounts to have their own individual inboxes displaying received mail, sent mail and notifications. |   |
| 2.6 | [Must have] the facility for administrators to view confidential statistics such as overall users on the site, active users, etc. |   |
| 2.7 | [Must have] the option to "view all" when logged in as an administrator, in order to see all currently uploaded properties |  |
| 2.8 | [Must have] the option for a user to update their profile | |


**3.  Sitemap and Navigation**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 3.1 | [Could have] a 'Contact Us' page available  |This will include a general email address that  all admins have access to  |
| 3.2 | [Could have] an FAQ page with a list of the most important information  |   |
| 3.3 | [Could have] a Privacy page stating the privacy policy  |   |
| 3.4 | [Could have] a Help page with links to various support pages |  Support pages including Contact Us and FAQ |
| 3.5 | [Could have] a 'Terms of Use' page with the Terms & Conditions stated | |
| 3.6 | [Could have] an 'About Us' page that informs the user what the purpose of the website is | |


**4.  Content Management**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 4.1 | [Could have] external libraries used to implement map rendering | Must be used in accordance with copyright regulations. |
| 4.2 | [Must have] in-house software for generating and managing current location-to-house routes | Essentially: we make the path finding algorithm and provide all locations/amenities/info to be mapped, but the actual drawing of the map can be done with OpenMap. |


**5.  Web Interface**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 5.1 | [Must have] two distinct interfaces for web and app | The system should provide two main interfaces, each of which provides different services        |
| 5.2 | [Must have] option for users to create an account and upload information |   |
| 5.3 | [Must have] ability for users to view statistical information about their account | Users can view statistics of their account such as profile views, property views, properties 'looked at' history etc |


**6.  Styling and Design**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 6.1 | [Should have] styling provided with CSS and HTML |   |


**7.  Security**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 7.1 | [Must have] password encryption and recovery. |   |
| 7.2 | [Must have] security measures in-place for login and site navigation | SSL |
| 7.3 | [Must have] three separate user account types: landlords, searchers and administrators with different access rights. |   |


**8.  Hosting**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 8.1 | [Must have] Servlet technnology |   |


**9.  Other Requirements**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 9.1 | [Must have] Java as the main implementation language. |   |
| 9.2 | [Must have] database technologies to store persistent user data. E.g. MySQL. |   |
| 9.3 | [Must have] OpenStreetMap support to generate maps and their data. |   |
| 9.4 | [Must have] Javascript support for webpages, alongside JSP. |   |


**10.  Tracking (website statistics)**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 10.1 | [Must] track visitors by role through recording page views. |   |
| 10.2 |   |   |
| 10.3 |   |   |


**11.  Search Engine Optimisation (SEO)**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 11.1 | [Should] have standard meta-tags implemented as described by Google.  eg. Every page has a title and short description. | http://static.googleusercontent.com/media/www.google.co.uk/en/uk/webmasters/docs/search-engine-optimization-starter-guide.pdf  |
| 11.2 |   |   |
| 11.3 |   |   |


**12.  Accessibility**

| **Req** | **Description** | **Comments** |
| --- | --- | --- |
| 12.1 | [Must] conform to accessibility standards outlined by W3 |  https://www.w3.org/standards/webdesign/accessibility |
| 12.2 |   |   |
| 12.3 |   |   |
