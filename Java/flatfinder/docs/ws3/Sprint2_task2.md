# Task 2
---------------------------------------
## Key design decisions made (sprint 1)

###Using bootstrap as the UI  

Regarding the aesthetic design, we have decided to use Bootstrap as the front-end framework due 
to the importance of user interaction with the software. Also, Bootstrap was originally used for mobile 
websites which means they focuses on responsive designs that can be very useful in order to create a 
feasible website for our client as we want the end user to be able to access and operates the website 
on multi-platforms. Consequently, creating a larger target audience for future development. We picked a
 brief application outlook that has a menu on the left which allows easy learning by the users. Moreover,
 we created a landing page because one requirement from the client was that every user must have an account
 before being able to use the application. The landing page will have the options of either login or register
 to become a member of the community. We tried to keep the design as simple as possible because this will be 
more efficient to deliver essential information. Therefore users will be able to get to the pages or use the
 website quicker without wasting time on learning the website and figuring how features work. Not only the 
landing page but the main pages we have come to the conclusion of keeping it simple and tidy with just the 
essential information on it.

###Data structures

The architecture approach we used to create the website is MVC (model-view-controller). This allows 
fluent interaction between the user and the database. User inputs will be passed onto the controller 
and then receive a complete and updated web page from the view. The process of interaction is the 
following:  Users use controller then it manipulates the model and updates the view which is the
feedback given to the user as a result. The model component only exists on the server.

###The Model

Data objects are stored in the model and the model doesn't know anything about views and controllers.
If the model changes it is the controller's job to update the view. For example, when a landlord adds a 
property to the website, it is stored in an instance of the model.

###The view

View is the visual page that is presented to the end-user so they can interact with the website i.e. 
login, search properties, send messages to buddies etc. The view is made with HTML, CSS and JavaScript.

###The Controller

This component is the decision maker and the main connection between the model and view. The 
controller updates the view when the model changes. The user manipulates the view and the controller
will update the model creating this connection within the three components. For example, the user make 
a change to their name on their profile page, this is forwarded to the controller. The controller modifies
the model to update the name field in the database.

###Relational database

Edgar F.Codd is the inventor of relational database and we used this to store the data required for the
website. Details of the user accounts for all different target audience are stored in the database i.e.
Students, Landlords and Administrators. Additionally, feedback on the website from the end users is
also directly linked to the database so it can be stored/ archived for future review.

###Local Host to preview software

In order to preview and test the functions we implemented we used localhost to launch the program. 
Each member uses an individual database configuration to access the software. This method is not
efficient enough and we always had problems with the database configuration making it more time 
consuming in order to preview of the prototype. We have reviewed this in more details in the retrospective
sessions and suggested another way to implement the previewing of the prototype; this will be
mentioned later in this document.

###Authentication

In order to give tailored and personalised application for the users, there are accounts for each user 
and they are secured by username and password. In order to have eligible authentication in place, 
we will encrypt the password with BCryptPasswordEncoder. Using this method, we can prevent
characters from the password section appearing when it is being entered in. This feature is essential 
as it meets the security standard expected from users.

## Design completed or should have completed (sprint 1)

###Algorithms

The searching of the properties is where the algorithm takes place. We used a linear search approach 
to find the appropriate properties from the database. Although in theory it is not the fastest algorithm
that can be implemented however considering the size of the database the duration is still feasible and
more sensible to implement. One significant drawback is that it will become unacceptably slow when 
the database grows overtime which will require another algorithm to be implemented to replace the
current one.

###APIs

The open API we used is OpenStreetMap, this allows the user to view the exact location of the properties 
based on their searched area and personal preferences. Another reason to use this API is because it is an 
open API meaning it is completely free with a high amount of support on the internet and this is suitable
for our project as it is likely we need a lot of help to implement exactly what we desired. However, there is 
one drawback which is the limitation on customisation. As the website evolves in the future there is a 
possibility of the client wanting additional tailored features to be implemented on the navigation map. 
Using open API will limit the ability to add and modify the map making it costly to implement a new 
tailored API.

###Security

SSL protocol is what we used for securing passwords. Normal data transfer in plain text between web 
server and the browser. With the use of SSL, it encrypts the data to prevent confidential information
being intercepted by attackers.

Another security measure we implemented in conjunction with SSL protocol is S-HTTP (secure HTTP). 
This allows the user to know exactly that they are connected to the desired server also this will ensure
the integrity of the data between the server and client.

One security measure we implemented is the timeout feature on the website. If the end-user is inactive
for a certain amount of time, the website will logout the user. This will prevent unauthorised individual 
using an irrelevant account performing undesired actions.  

##Key design decisions made (sprint 2)  

###User Interface
In terms of UI, we have decided to change the original landing page and the overall aesthetic of the website 
for better ease of use. We selected a different bootstrap template for the landing page with a cleaner and 
professional outlook. (Image)  

With the change in UI, end users will be more likely to have a positive experience because it require less 
learning input from the users which will allow them to navigate and operate the application quicker. 

###Setting up remote server  
Instead of continue using the previous configuration to preview our application which was using localhost 
and personal database configuration. We decided to set up a server so all members can access the preview
from different locations, we thought this would be very useful especially during the university breaks when 
every member is likely to be off-campus with limited communication.  

##Design completed or should have completed (sprint 2)  

###Adding express interest button  
This additional feature we implemented will allow the end user to show interest towards a specific property 
and this will give a notification to the landlord which enhances the interaction between the two users.  

###Security
Regarding security further implementation for sprint 2, we added Terms of Use policy to the website which 
increases the users' security by protecting them with policies.  

Also, we have extended tests on the URLs making sure the security measure is in place to prevent 
unauthorised access from modification to the URL. For example, making sure if it is not directed to
the correct location it will prevent the user continue to gain access to the directed path.

Encryption is key in protecting passwords and other sensitive data, for passwords the best way to go about encrypting would be to use *Spring Security* which introduces a function, `BCryptPasswordEncoder`. This allows for a password to be stored in the format of `[hash:salt]` allowing a good level of security. 

###Administrator  
We added specialist permission for the administrator so they will have the ability to oversee the whole 
website and maintain the website to achieve optimal performance. Also they can suspend and expire
accounts if there are any inappropriate members on the platform. Moreover, the administrator can remove 
accounts that are either fake or inactivity for a long period of time which will improve the performance of 
the system with the optimal number of accounts on the database.   


##Summary of the key design decisions

#### Technologies Used

######Languages and Frameworks to be used in this software:

- Java for backend implementation
- JSP/HTML/CSS for front-end implementation
- MySQL for data handling and saving
- JavaScript/jQuery for using real-time update (messenger)
- Gradle framework for automated builds
- Cucumber for black-box testing
- Spring MVC to handle HTTP requests
- 
###### Use of real-time data updates in the system

One of the most key components of any user-interface and system is to be able to transmit information in real-time. An example of where we would use this is in the "Messenger" and "Notification" aspect of the system. The use of AJAX and JSON objects would be key due to the real-time HTML features it provides. The SpringMVC servlet provides a very useful JavaScript interface which can be used to pass information between controllers and views, by using this we can manipulate information without the need to switch views and produces a functional and professional interface.

## Usage of API's

###### HTML 5 and Location services

Using HTML5, the system can use client GPS location which would allow the application to suggest nearby places as well as provide a more concise function of using maps to navigate to locations.

	navigator.geolocation.getCurrentPosition(showPosition);


###### OpenStreetMap

As an alternative to Google Maps, OpenStreetMap provides an easy implementation to embed into an application. This allows developers to implement on top of the API completely which enables the full embedding into the system.

###### JavaMail

The development of a CMS system would require the sending of emails which would be important for users, landlords and administrators. By using the JavaMail (`org.springframework.mail.javamail`) library, we can achieve this in a very practical way. The Spring framework also supports JavaMail and this would allow us to form an integration of the services that the application uses. 

###### Spring MVC Framework

The Spring MVC framework is very useful for the project as it provides the structure and tools that can be used to handle user-requests, security and the different layers of the web-application.

###### The Spring Framework is formed by the following:

![spring-mvc](http://i.imgur.com/CKdiXxS.png)

The Spring MVC framework allows us to take incoming HTTP requests from a user, and allows us to delegate these requests to a controller. The controller can then handle the requests and give any responses back to the user. This is very useful as all the data is being handled in the back-end of the web-application and provides further efficiency and security.

The Spring MVC framework uses the following file-structure for implementation of the software:

	└───src
	    ├───main
	    │   ├───java
	    │   │   └───Application
	    │   │       ├───controller
	    │   │       ├───domain
	    │   │       └───persistence
	    │   │           ├───repository
	    │   │           └───services
	    │   ├───resources
	    │   └───webapp
	    │       └───WEB-INF
	    │           └───views
	    └───test
	        ├───java
	        │   └───Application
	        └───resources

The controller corresponds to the HTTP handler for requests made by users through the views. And the domain allows us to write data-objects to the models. The tests will be implemented after the application is made and throughout to test each component.

## Summary

Project is fully functional web-application that can fulfill the needs and requirements of the targeted user (students & landlords) and also takes into consideration the requirements of the system that we will be developing on (operating system). The system  provide a web-interface, backend controllers for handling requests and a fully passing black-box testing method which automatically test each feature of the web application.

##Project Diagrams
###User interface desing

####Main page
Easy to navigate.
![fileupload](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws3/easy.png?token=AOe3XIJUyfFygc3yWwpf8XVTyz8d0YYOks5XKoAbwA%3D%3D)


####Mobile version
Fully supports mobile version.
![mobilefriend](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws3/mobile%20friendly.bmp?token=AOe3XGAig9bRBVCmSEih1IKC7e-JaZHEks5XKnUEwA%3D%3D)

####Search 
![search](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/searchController.png?token=AOe3XBP1HZeOiqxbCWoZUCGJiDaSbWaeks5XKnyIwA%3D%3D)

####File Upload

![fileupload](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/fileUploadController.png?token=AOe3XIIVLqbbRLjJlhupa05gayEuA8DSks5XKn0RwA%3D%3D)


##UML Activity diagrams
In this section, activity diagrams for each feature is visible to demonstrate how the system function.

###Register/Login  
The diagram shows when both user types (Searcher and Landlord) will go through when they want to 
gain access to the application. The landing page should be the first view they access before passing 
through any authentication. On this page there are two options of either register or login depending 
on whether the user already has an account.  

![registerloginAD](https://cloud.githubusercontent.com/assets/14927027/14725943/bd83156c-0816-11e6-9006-6d2b6782bf86.png)

###Property  
This diagram shows the process of the searcher searching through the properties avaliable within the 
searched area. When the user input the desire postcode the controller fetches the property database 
so the view of property list will be available to the end user. Then the searcher can select a specific 
property to see more details of it. Once it is selected, data from the property is fetched again to
display it in a different view. At last, the searcher has the ability to either express interest or give 
property feedback.

![propertyAD](https://cloud.githubusercontent.com/assets/14927027/14725965/dc5714fc-0816-11e6-9b8f-293dbb22387b.png)

###Message
All user types are eligible to send messages to other uesr, the diagram below shows the process. 
The user click on the message tab then click new message to construct their personalised message,
when the user sends the message it connects with the database to write the message into the database
then this is directed to the specific user that was specified in the message form.  

![messageAD](https://cloud.githubusercontent.com/assets/14927027/14725944/bd9c14ea-0816-11e6-8931-d834f80d3cca.png)

###Data Model

The model accurately picturely describe the software. It was recenlty simplifield in order to be less specific in detail to give more of an overview of the system. 

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws3/dataModel.png?token=AOe3XNJiEqQwWVhOsgSMSx0O0AYFhWI9ks5XKlG-wA%3D%3D)

###Roles Definition


![messageAD](https://cloud.githubusercontent.com/assets/14922982/12902946/10a85a0c-cebc-11e5-8dfe-56d17a1f1b16.png)

###Use Case Diagram

![messageAD](https://cloud.githubusercontent.com/assets/14922982/12885589/026b756c-ce5f-11e5-951a-0e0390fcdfc2.png)

###Domain Model

The domain model consists of class diagrams for the BuddyUp, Conversation, Message, Notification, Property, PropertyFeedback, PropertyImage, Role, Room, and User entities. We've also included class diagrams representing the various features of the system (below). The relationships between the entities is also shown. When developing these entities we ensured any many-to-many relationships were broken down into many-to-one/one-to-many relationships instead; this ensured there were no 'hidden' entities. All relevant 'getter' and 'setter' methods were included as needed, but they are not represented here.

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/domainModel.png?token=AOe3XP6AZ9PgkT3Xw3Fxd-eZbgesqMGFks5XKl02wA%3D%3D)


###Class Diagrams

Create Property

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/CreatePropertyClassDiagram.png?token=AOe3XPcfU-jlVo_BJXJg-kBXBxbLX-_uks5XKl2fwA%3D%3D)

Express Interest

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/ExpressInterestClassDiagram.1.png?token=AOe3XEhz7JhjqOvc8ab5DHYKgLI8xts5ks5XKl2swA%3D%3D)

Register User

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/RegisterUserClassDiagram.png?token=AOe3XMpQqqMFQPD2iycqtzjMxVr0FGRbks5XKl2-wA%3D%3D)

Property Search

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/SearchClassDiagram.1.png?token=AOe3XBV0897rA5rpqTaQckbe7pUmHwEmks5XKl3NwA%3D%3D)

###Repository Layer

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/repositoryLayer.png?token=AOe3XPkNQbsN6DE8xrqwX3ifTi1sLyg2ks5XKl3kwA%3D%3D)

###Controller Layer

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/controllerLayer.png?token=AOe3XOocxG3ZKQfteZ2VkMcw2lyl4fceks5XKl3ywA%3D%3D)

###Security Layer

![messageAD](https://raw.githubusercontent.com/UOL-CS/co2015-group-03-repo/master/docs/ws2/securityLayer.png?token=AOe3XK3kAcW2pLWETUzxTts_LGcJ6mGzks5XKl4YwA%3D%3D)

###Component Diagram

![messageAD](https://camo.githubusercontent.com/330a583aaed0fbb515c3967f5f41dcad16eb97d7/687474703a2f2f692e696d6775722e636f6d2f486448563239352e706e67)

