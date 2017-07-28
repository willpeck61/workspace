#FlatFinder - Group Three
##**Glossary of Terms**

#List of Abbreviations

| Abv. | Stands for.. |
| :--- | :---------- |
| AJAX | [Asynchronous JavaScript and Extensible Markup Language](#ajax) |
| API | [Application Programming Interface](#api) |
| BDD | [Behaviour Driven Development](#bdd) |
| BLL | [Business Logic Layer](#bll) |
| HTTP | Hyper Text Trasfer Protocol |
| MVC | [Model View Controller](#mvc) |
| GPS | [Global Positioning Satellite](#gps) |
| JSON | JavaScript Object Notation |
| JSP | [Java Server Pages](#jsp) |
| JSTL | [Java Standard Tag Library](JSTL) |
| MVC | [Model View Controller](#mvc) |
| OS | [Operating System](#os) |
| POJO | [Plain Old Java Object](#pojo) |
| RDBMS | [Relational Database Management System](#mysql) |
| SQL | [Structured Query Language](#mysql) |
| UML | [Unified Modeling Language](#uml) |
 
#a

| Term | Description |
| :------ | :------------- |
| <a id="actor">Actor</a> | An actor is an entity in the form of a human or other external system (often defined by a [role](#role)) that interacts with a system defined by a [use-case](#usecase) or in other [UML](#uml) diagram. 
|| Actors are external to the system but can send to and receive messages from it.|
| <a id="administrator">Administrator</a>| An administrator is an [actor](#actor) with a [role](#role) defined as those users with special [privileges](#privileges) who will administer, maintain and control [content](#content) of the [application](#application) and [user accounts](#useraccount).
| <a id="ajax">Asynchronous Javascript & Extensible Markup Language (AJAX)</a>| AJAX is a method of passing information between the [client-side](#clientside) and [server-side](#serverside) asynchronously.  This means web page content can be updated with server returned data in real time without reloading thus improving the user experience. |
| <a id="application">Application (app, webapp)</a> | An application is a term used to describe the software element of the system which provides the required functionality to users so that they can complete tasks. |
| <a id="api">Application Programming Interface (API)</a> | An API is collection of prewritten libraries or [classes](#class) that allow for the developer to integrate functionality provided by another application.  For example, APIs are used extensively in web application development especially for integrating e-commerce solutions such as secure checkout systems. |
#b
| Term | Description |
| :------ | :------------- |
| <a id="bdd">Behaviour Driven Development (BDD)</a> | BDD is a development methodology where software is created through satisfying test scenarios. Each test scenario is created from the expected interaction between the system and the [user](#user) from the perspective of the user.|
| <a id="blackbox">Blackbox Testing</a> | Blackbox testing is the process of feeding user defined tests into the system and expecting to see the desired output. |
| <a id="bll">Business Logic Layer</a> | Business Logic is programmed realisation of business rules to control the data sent and received between the user and the data layer.  For example,  in [Spring](#spring) [MVC](#mvc) the controller classes are programmed in accordance with business rules to control dataflow between the [view](#view) and [model](#mvc) layers.|

#c
| Term | Description |
| :------ | :------------- |
| <a id="class">Class</a> | A class is the Java implementation of an object as described by the [object orientated programming](#oop) paradigm. Classes contain methods and attributes or fields which can be accessed once instantiated in other classes.| 
| <a id="clientside">Client-side</a> | Client-side refers to any operation that takes place on the user's system or device when engaged in a client-server relationship.  For example, web form data is input and sent from the client-side whereas the data is received and processed on the [server-side](#serverside).
| <a id="content">Content</a> | The term content is used to generally describe the information displayed to users by the application through different [views](#view). |
| <a id="cucumber">Cucumber</a> | Cucumber is programming tool for automating tests throughout the [BDD](#bdd) development process. Different tests are defined through [Gherkin Scenarios](#gherkin) where scenarios combine to represent features of the application. |

#d
| Term | Description |
| :------ | :------------- |
| <a id="designpatterns">Design Pattern</a> | A design pattern is a reuseable solution to commonly occuring problems in [object orientated](#oop) software design and engineering.  There are three different types of pattern. The first of which is creational which covers problems related to instantiating [classes](#class). The second is behavioral which is concerned with communications between objects.  The third is structural which provides methodology to solve problems relating to integrating objects to work together where they may not have done so otherwise. |
| <a id="database">Database</a> | A database is an application designed to store and [persist](#persistence) data in tables. There are a number of different types including flat file and relational databases.  Relational databases, such as [MySQL](#mysql),  are used to store complex structures where entities are defined along with the relationships between them. For example, a bank, a bank account and an account holder are all entities with relationships between them:  One bank can have many bank accounts, one account holder can have many bank accounts, but one bank account can only have one account holder etc. 

#f
| Term | Description |
| :------ | :------------- |
| <a id="framework">Framework</a> | A framework provides a structure for a system to be built on with the aim of improving efficiency.  Software development frameworks take the form of prewritten libraries or [classes](#class) which are designed to facilitate the creation of [applications](#application) often aimed providing solutions to simplify [business logic](#bll) processes.  Another benefit of using a framework is better adherence to a relevent architecture where, for example, a desired structural [design pattern](#designpatterns) solution is included.|    

#g
| Term | Description |
| :------ | :------------- |
| <a id="gherkin">Gherkin Scenarios</a> | Test scenerios defined in [Cucumber](#cucumber) are known as Gherkins.  In [Behaviour Driven Development](#bdd) each scenario tests a particular function of the system. When a test scenario passes then that particular function is complete.|
| <a id="gps">Global Positioning Satellite (GPS)</a> | GPS is an existing network of satellites use for tracking the position of GPS enabled devices.  It is commonly used for [satellite navigation](#satnav).   
| <a id="gradle">Gradle</a> | Gradle is an automation tool used for building [applications](#application) coded using the [Java](#java) programming language and chosen [framework](#framework).|

#j
| Term | Description |
| :------ | :------------- |
| <a id="java">Java</a> | Java is a popular [object orientated](#oop) programming language. Java utilises a [virtual machine](#vm) environment design which means that it can run on different [platforms](#platform) with out modifying the [source code](#sourcecode). In Java, every function used is an hierarchal object which is represented as a [class](#class).  The application is built by creating and importing [classes](#class) which provide the required functionality. |
| <a id="jsp">Java Server Pages (JSP)</a> | Java Server Pages are [HTML](#html) web pages that can be dynamically changed [server-side](#serverside) using [Java](#java) through embedding [JSTL](#jstl) tags. |
| <a id="js">JavaScript (JS)</a> | JavaScript is the standard [client-side](#clientside) scripting language used within web sites. It is used to manipulate the web page content **after** it is received by the browser. |
| <a id="jquery">jQuery</a> | jQuery is a large event driven library for [JavaScript](#js) which incorporates [AJAX](#ajax). It also is designed to reduce the verbosity present in [JavaScript](#js)|
| <a id="jstl">Java Standard Tag Library (JSTL)</a> | JSTL is a set of additional [HTML](#html) style tags used for manipulating [Java](#java) web page content ([JSP](#jsp)) **before** the page is received by the browser.  |
#l

| Term | Description |
| :------ | :------------- |
| <a id="Layer">Layer</a> | In software engineering, abstract layers are used to define which functionality or services different components of the application provide. For example, a data access layer contains components that can access [persistent](#persistence) storage. 
| <a id="landlord">Landlord</a> | A landlord is an actor with a role defined as those users who have the requirement to advertise [accommodation](#accommodation).

#m
| Term | Description |
| :------ | :------------- |
| <a id="mvc">Model View Controller (MVC)</a> | MVC is an extension to the [Spring](#spring) [framework](#framework) which is designed to facilitate the creation of web [applications](#application). The Model element refers to the data access layer of the application which defines the use of databases and other related objects such as file storage. [View](#view) refers to the way the data is to be displayed and Controllers are created to handle the movement of data between the view and model layers.|
| <a id="mysql">MySQL</a> | MySQL is a commonly used relational database management system which is programmed using structured query language (SQL). It is primarliy used to [persist](#persistence) data where entity relationships exist between tables. |

#o
| Term | Description |
| :------ | :------------- |
| <a id="object">Object</a> | An object is an entity that has describable attributes and is defined within the [object orientated programming paradigm](#oop). Objects contain both methods and associated variables. |
| <a id="oop">Object Orientated Programming</a> | Object orientated programming is a method of developing software through the classification of [objects](#object) and then defining the relationships between them.  Unlike procedural style where procedures are separated from variables, objects contain both methods and associated variables.  |
| <a id="os">Operating System</a> | An operating system is software that provides control over the integrated and connected hardware through its own [user interface](#userinterface) and any other [applications](#application) that are run on the operating system.   

#p
| Term | Description |
| :------ | :------------- |
| <a id="persistence">Persistence</a> | Persistence is a term used to describe the saving the state or storing data beyond the life of the current instance of the application.  This means when the application is reopened previously modified data can be retrieved. 
| <a id="platform">Platform</a> | Platform is a term used to describe different computer system implementations. Most often, platforms are differentiated by their [operating system](#os).
| <a id="pojo">Plain Old Java Object</a> | A POJO is a [class](#class) used in [Java](#java) to create objects that describe an entity with attributes.  POJOs do not have methods other than those that set or get attribute values. For example, a POJO could be used to represent a person with the attributes such as name, address, postcode and telephone number.|
| <a id="privileges">Privileges</a> | The term privileges is used to describe which functions within the application each user [role](#role) is allowed access.  Privileges are usually controlled by a system [administrator](#administrator).|

#r
| Term | Description |
| :------ | :------------- |
| <a id="role">Role</a> | The term role is used to define the type of user. Each user role might have different access [privileges](#privileges) where they can only access role relevant functions. |
| <a id="response">Response</a> | A response is created by the system in return to a received [request](#request) from the [client-side](#clientside). | 
| <a id="request">Request</a> | A request is created by the [user](#user) when interacting with the system which handled by the [server-side](#serverside). | 

#s
| Term | Description |
| :------ | :------------- |
| <a id="satnav">Satellite Navigation</a> | Satellite navigation is commonly used to provide live turn-by-turn instructions that guide the [user](#user) to a destination via a [GPS](#gps) enabled device in real time. |  
| <a id="searcher">Searcher</a> | A searcher is an [actor](#actor) with a [role](#role) defined as those users with the requirement to find [accommodation](#accommodation).|
| <a id="sequencediagram">Sequence Diagram</a> | A [UML](#uml) sequence diagram is often used to illustrate the way the application will behave throughout user interaction with it.|
| <a id="serverside">Server-side</a> | Server-side is a term used to describe operations that take place on the server. These could include activities such as processing a submitted form, database queries or sending emails. | 
| <a id="spring">Spring</a> | Spring is a [framework](#framework) for creating applications in the [Java](#java) programming language. By using [POJOs](#pojo) and the Inversion of Control [pattern](#designpatterns) Spring allows the developer use dependancy injection in construction which simplifies testing and prevents individual [classes](#class) from handling too many operations. |
| <a id="stepdef">Step Definition (StepDef)</a> | A StepDef is the Java code used to test the [application](#application) with the aim of satisfying different the conditions defined in [gherkin scenarios](#gherkin). |
| <a id="systemboundary">System Boundary</a> | The system boundary contains the functional parts or components which in combination forms the defined system being illustrated by the [use-case](#usecase).|

#u
| Term | Description |
| :------ | :------------- |
| <a id="uml">Unified Modeling Language (UML)</a> | UML is a method for communicating and modeling system functionality and design.  Common UML diagrams that are useful for customers include [use-cases](#usecase) and [sequence](#sequence) diagrams.|
| <a id="usecase">Use-case</a> | A use-case is a method used to visualise and help describe the functionality of a defined system either in total or in part.
| | The most common presentation method is through a collection of scenarios illustrated by [UML](#uml) use-case diagrams which attempt to show the transmission of messages between actors and entities within the [system boundary](#systemboundary).
| <a id="user">User</a> | Users are defined as human [actors](#actor) who use the functionallity provided by the application. A user may have a [role](#role) assigned.
| <a id="useraccount">User Account</a> | A user account is defined as the stored data related to a specific [user](#user). This could include details such as user name and access [privileges](#privileges).|
| <a id="userinterface">User Interface</a> | A user interface is a term used to describe the functionality provided by the application that allows users to interact with it.  See also [view](#view). |

#v
| Term | Description |
| :------ | :------------- |
| <a id="view">View</a> | A view is a term used to describe different [user interfaces](#userinterface) which allows the user to display or send data via the controller as defined within the [MVC](#mvc) architecture. For example, web pages.|
