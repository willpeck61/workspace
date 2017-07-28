# Software Architecture & Technology Rationale

--------------------------------------

## Technologies Used

######Languages and Frameworks to be used in this software:

- Java for backend implementation
- JSP/HTML/CSS for front-end implementation
- MySQL for data handling and saving
- JavaScript/jQuery for using real-time update (messenger)
- Gradle framework for automated builds
- Cucumber for black-box testing
- Spring MVC to handle HTTP requests

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

## Goals

The goal of the project is to create a fully functional web-application that can fulfill the needs and requirements of the targeted user (students & landlords) and can also take into consideration the requirements of the system that we will be developing on (operating system). The system should provide a web-interface, backend controllers for handling requests and a fully passing black-box testing method which should automatically test each feature of the web application.

## System Qualities

The software architecture will contribute to many things outside of functionality including portability, reliability, extensibility and integration. The software will be designed in the Java language using the Java Servlet SE 8 and JSP. This will allow the system to run standalone without the need to install any frameworks outside of which are included with Java JDK, JRE, Spring and Gradle. 

The security of the system will be a vital part and it will be controlled using Spring Security, which offers an efficient way of securing views and requests in the application.

## Usage of API's

###### HTML 5 and Location services

Using HTML5, the system can use client GPS location which would allow the application to suggest nearby places as well as provide a more concise function of using maps to navigate to locations.

	navigator.geolocation.getCurrentPosition(showPosition);

###### Google Maps Services

Google Maps supports many map services, like navigation which would be very useful towards this project. It can provide maps, that we can use to place-hold properties as well as provide navigation services.

###### OpenStreetMap

As an alternatice to Google Maps, OpenStreetMap provides an easy implementation to embed into an application. This allows developers to implement on top of the API completely which enables the full embedding into the system.

###### JavaMail

The development of a CMS system would require the sending of emails which would be important for users, landlords and administrators. By using the JavaMail (`org.springframework.mail.javamail`) library, we can achieve this in a very practical way. The Spring framework also supports JavaMail and this would allow us to form an integration of the services that the application uses. 

## Implementation

###### Use of real-time data updates in the system

One of the most key components of any user-interface and system is to be able to transmit information in real-time. An example of where we would use this is in the "Messenger" and "Notification" aspect of the system. The use of AJAX and JSON objects would be key due to the real-time HTML features it provides. The SpringMVC servlet provides a very useful JavaScript interface which can be used to pass information between controllers and views, by using this we can manipulate information without the need to switch views and it allows for a functional and professional interface.

Example of Spring-JavaScript

	<form:input id="searchString" path="searchString"/>
	<script type="text/javascript">
		Spring.addDecoration(new Spring.ElementDecoration({
			elementId: "searchString",
			widgetType: "dijit.form.ValidationTextBox",
			widgetAttrs: { promptMessage : "Search hotels by name, address, city, or zip." }}));
	</script>

###### Running the system

The system can be ran using Gradle. To run the JSP server we can use `gradle bootRun` and to run the cucumber black-box testing we can use `gradle cucumber`. 

## Component Diagram

![component-diagram](http://i.imgur.com/HdHV295.png)
