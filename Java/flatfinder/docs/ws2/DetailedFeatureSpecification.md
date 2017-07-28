# Detailed Feature Specification
--------------

## Software Architecture

The software architecture of the system will adapt to the backlog which outlines the features that will be included as well as describe the system requirements for the features to be implemented successfully. 

The languages, frameworks, APIs and plug-ins will remain the same as outlines previously with minor changes to how the information is passed between different services, views, people and features.
Spring MVC will be the main framework used for development and testing along with Gradle for build automation. 

###### Messaging service

The messaging service will require to use JavaScript (AJAX and JSON) in which we will use a dynamic page to allow the sending of messages in real-time which would be written to the database, there would be a continuous query to the database to grab any new messages between people and for this the ideal method of receiving the information would be JSON objects. This will not only allow good formatting of the data but will offer a flexible way of showing data.

###### Maps

OpenStreetMap would be the ideal way of plotting and showing information due to the open-source aspect it provides. There are also many implementation that can be used, in JavaScript we can use `OpenLayers` to implement this.

``` javascript
map = new OpenLayers.Map("demoMap");
map.addLayer(new OpenLayers.Layer.OSM());
map.zoomToMaxExtent();
```
Using `Leaflet`, a JavaScript API for OpenLayers, it is fairly simple to implement maps:

``` javascript
var map = L.map('map').setView([51.505, -0.09], 13); // [Lat,Lon], zoom
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);
```
###### Route Feature

The best way of routing would be to use Google Maps API which allows freedom of not adding in any bloated code and just giving an external personalized link to the destination. This can be achieved by simple HTTP requests:

	https://maps.google.com?q={destination_here}

###### Security 

Encryption is key in protecting passwords and other sensitive data, for passwords the best way to go about encrypting would be to use *Spring Security* which introduces a function, `BCryptPasswordEncoder`. This allows for a password to be stored in the format of `[hash:salt]` allowing a good level of security. 

###### Notifications

The goal for this feature is to implement push notifications that provide full interaction and give the user the best overall experience by seeing the relevant information whenever required. In order to implement this feature it would be important to use AJAX/JSON/jQuery to be able to send asynchronous messages to the domain and controllers, receive the information back and have it act onto the frong end for the user. 

###### Report

Reports can be sent through a simple service which includes sending a message to the administrator, this can either be tied in with the messenger aspect itself but would better work with a separate aspect. 
