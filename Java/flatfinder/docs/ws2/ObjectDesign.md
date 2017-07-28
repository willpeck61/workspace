# Group 3 Object Design

### Domain Model

[![alt text][2]][1]

  [1]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/domainModel.png
  [2]: domainModel.png (Class diagrams for the domain layer)
  
> The domain model consists of class diagrams for the BuddyUp, Conversation, Message, Notification, Property, PropertyFeedback, PropertyImage, Role, Room, and User entities. We've also included class diagrams representing the various features of the system (below).
> The relationships between the entities is also shown. When developing these entities we ensured any many-to-many relationships were broken down into many-to-one/one-to-many relationships instead; this ensured there were no 'hidden' entities.
> All relevant 'getter' and 'setter' methods were included as needed, but they are not represented here.

#### Feature Class Diagrams  
###### Create Property

[![alt text][28]][27]

  [27]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/CreatePropertyClassDiagram.png
  [28]: CreatePropertyClassDiagram.png (Class diagram for Creating a Property)
  
###### Express Interest

[![alt text][30]][29]

  [29]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/ExpressInterestClassDiagram.1.png
  [30]: ExpressInterestClassDiagram.1.png (Class diagram for Expressing Interest (in a property))

###### Register User

[![alt text][32]][31]

  [31]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/RegisterUserClassDiagram.png
  [32]: RegisterUserClassDiagram.png (Class diagram for Registering a new User)
  
###### Property Search

[![alt text][34]][33]

  [33]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/SearchClassDiagram.1.png
  [34]: SearchClassDiagram.1.png (Class diagram for Searching for a Property)

### Data Model

[![alt text][4]][3]

  [3]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/dataModel.png
  [4]: dataModel.png (EER diagram representing the data model)
  
> The data model represented by an EER diagram. The majority of the relationships were optional (denoted with a circle); if not, the relationship would have to be created when either of the objects involved was. Keeping relationsips optional makes coupling loose, leading to a system that is less likely to create unanticipated changes within elements. Limiting interconnections can help isolate problems when things go wrong and simplify testing, maintenance and troubleshooting procedures.

### Repository Layer

[![alt text][6]][5]

  [5]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/repositoryLayer.png
  [6]: repositoryLayer.png (Class diagrams for the repository layer)
  
### Controller Layer

[![alt text][8]][7]

  [7]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/controllerLayer.png
  [8]: controllerLayer.png (Class diagrams for the controller layer)

### Security Layer

[![alt text][10]][9]

  [9]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/securityLayer.png
  [10]: securityLayer.png (Class diagrams for the security layer)

#### Access Control Matrix
  
|Role    |URL access|User access|Property access|
|--------|----------|-----------|---------------|
|Searcher|"/", "/view-profile", "/message", "/notification", "/message/view/--", "/message/create/", "logout", "/upload"|R/W|R|
|Landlord|"/landlord-dashboard/--", "/landlord-dashboard/manage/--", "/landlord-dashboard/new/--", "/", "/view-profile", "/message", "/notification", "/message/view/--", "/message/create/", "logout", "/upload"|R|R/W|
|Admin   |"/", "/view-profile", "/message", "/notification", "/message/view/--", "/message/create/", "logout", "/upload"|R/W|R/W|

#### The security layer: ####

Specify access control by using an access control matrix and explain the security measures that are implemented.
> Each role provides a different level of access; if a user attempts to access a page with insufficient privileges they are redirected to an error page displaying an appropriate message.
> This was achieved using Spring security.

Does the access control strategy include how authentication is implemented?
> Authentication was implemented by storing user's usernames in plain text in the database, and encoding passwords using BCryptPasswordEncoder, which makes use of the BCrypt strong hashing function.
> This was also achieved with Spring security.

### Views

#### AccountController

[![alt text][12]][11]

  [11]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/accountController.png
  [12]: accountController.png (State machine for AccountController)
  
#### DashboardController

[![alt text][14]][13]

  [13]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/dashboardController.png
  [14]: dashboardController.png (State machine for DashboardController)
 

#### FileUploadController

[![alt text][16]][15]

  [15]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/fileUploadController.png
  [16]: fileUploadController.png (State machine for FileUploadController)
  
#### MessageController

[![alt text][18]][17]

  [17]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/messageController.png
  [18]: messageController.png (State machine for MessageController)
  
#### NotificationController

[![alt text][20]][19]

  [19]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/notificationsController.png
  [20]: notificationsController.png (State machine for NotificationController)
  
#### PropertyController

[![alt text][22]][21]

  [21]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/propertyController.png
  [22]: propertyController.png (State machine for PropertyController)
  
#### SearchController

[![alt text][24]][23]

  [23]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/searchController.png
  [24]: searchController.png (State machine for SearchController)

#### SessionController

[![alt text][26]][25]

  [25]: https://github.com/UOL-CS/co2015-group-03-repo/blob/master/docs/ws2/sessionController.png
  [26]: sessionController.png (State machine for SessionController)

> The controllers are depicted using state machines, showing naviagtion between the various web pages [views].

### Data Management Strategy

The end result of our data management strategy is shown by the data model.
* Are the relevant data formats explained?

> Numerical data was stored as ints within the database, and Integers within Java. Using the integer class allowed us to assign values as 'null' to denote the value had yet to be created, instead of using a different arbritaty value (such as 0 or -1) which could imply that is the value itself.

* Are persistent objects identified?

> The persistent objects are those in the domain layer [BuddyUp, Conversation, Message, Notification, Property, PropertyFeedback, PropertyImage, Role, Room, and User].

* Is the choice of database management system and persistence method justified?

> MySQL was chosen as the software is free to use, very fast, and works well with the Spring MVC framework.

##### SQL Script representing the Data Management Strategy

* CREATE TABLE `buddyup` (

  `buddyup_id` int(11) NOT NULL AUTO_INCREMENT,

  `confirmed` bit(1) DEFAULT NULL,

  `initialiser_id` int(11) DEFAULT NULL,

  `responder_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`buddyup_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

* CREATE TABLE `conversation` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `last_received` varchar(255) DEFAULT NULL,

  `number_of_messages` int(11) DEFAULT NULL,

  `recipient` varchar(255) DEFAULT NULL,

  `sender` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `message` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `conversation_id` int(11) DEFAULT NULL,

  `from_user` varchar(255) DEFAULT NULL,

  `message` varchar(255) DEFAULT NULL,

  `threadid` int(11) DEFAULT NULL,

  `time_sent` varchar(255) DEFAULT NULL,

  `to_receive` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `notification` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `for_user` varchar(255) DEFAULT NULL,

  `is_seen` bit(1) DEFAULT NULL,

  `notification_text` varchar(255) DEFAULT NULL,

  `notification_type` varchar(255) DEFAULT NULL,

  `when_created` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `property` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `address_1` varchar(255) DEFAULT NULL,

  `address_2` varchar(255) DEFAULT NULL,

  `address_3` varchar(255) DEFAULT NULL,

  `bills_included` bit(1) DEFAULT NULL,

  `date_created` datetime DEFAULT NULL,

  `description` varchar(255) DEFAULT NULL,

  `disabled_access` bit(1) DEFAULT NULL,

  `available_to` datetime DEFAULT NULL,

  `headline_text` varchar(255) DEFAULT NULL,

  `num_interests` int(11) DEFAULT NULL,

  `no_of_views` int(11) DEFAULT NULL,

  `no_of_rooms` int(11) DEFAULT NULL,

  `occupant_type` varchar(255) DEFAULT NULL,

  `parking` bit(1) DEFAULT NULL,

  `pets_allowed` bit(1) DEFAULT NULL,

  `postcode` varchar(255) DEFAULT NULL,

  `price_per_week` float DEFAULT NULL,

  `quiet_area` bit(1) DEFAULT NULL,

  `short_term` bit(1) DEFAULT NULL,

  `single_gender` bit(1) DEFAULT NULL,

  `smoking` bit(1) DEFAULT NULL,

  `available_from` datetime DEFAULT NULL,

  `property_type` int(11) DEFAULT NULL,

  `owner_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;



* CREATE TABLE `property_feedback` (

  `propertyfeedback_id` int(11) NOT NULL AUTO_INCREMENT,

  `approved` bit(1) DEFAULT NULL,

  `property_id` int(11) DEFAULT NULL,

  `rating` int(11) DEFAULT NULL,

  `text` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`propertyfeedback_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `property_image` (

  `propertyimage_id` int(11) NOT NULL AUTO_INCREMENT,

  `description` varchar(255) DEFAULT NULL,

  `image_link` varchar(255) DEFAULT NULL,

  `property_id` int(11) DEFAULT NULL,

  `title` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`propertyimage_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `roles` (

  `id` int(11) NOT NULL,

  `role` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `room` (

  `room_id` int(11) NOT NULL AUTO_INCREMENT,

  `date_created` datetime DEFAULT NULL,

  `description` varchar(255) DEFAULT NULL,

  `double_room` bit(1) DEFAULT NULL,

  `ensuite` bit(1) DEFAULT NULL,

  `furnished` bit(1) DEFAULT NULL,

  `headline_text` varchar(255) DEFAULT NULL,

  `height` int(11) DEFAULT NULL,

  `occupied` bit(1) DEFAULT NULL,

  `property_id` int(11) DEFAULT NULL,

  `width` int(11) DEFAULT NULL,

  PRIMARY KEY (`room_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `user_roles` (

  `role_id` int(11) NOT NULL,

  `user_id` int(11) NOT NULL,

  PRIMARY KEY (`role_id`,`user_id`),

  UNIQUE KEY `UK_g1uebn6mqk9qiaw45vnacmyo2` (`user_id`),

  CONSTRAINT `FK_5q4rc4fh1on6567qk69uesvyf` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),

  CONSTRAINT `FK_g1uebn6mqk9qiaw45vnacmyo2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



* CREATE TABLE `users` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `address_1` varchar(255) DEFAULT NULL,

  `address_2` varchar(255) DEFAULT NULL,

  `address_3` varchar(255) DEFAULT NULL,

  `age_group` int(11) DEFAULT NULL,

  `buddy_up` int(11) DEFAULT NULL,

  `email` varchar(255) DEFAULT NULL,

  `first_name` varchar(255) DEFAULT NULL,

  `gender` int(11) DEFAULT NULL,

  `last_name` varchar(255) DEFAULT NULL,

  `login` varchar(255) DEFAULT NULL,

  `password` varchar(255) DEFAULT NULL,

  `postcode` varchar(255) DEFAULT NULL,

  `employment_status` int(11) DEFAULT NULL,

  `title` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
