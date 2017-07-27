#!/bin/sh

curl "http://localhost:8090/rest/class/create?classname=Root"
curl "http://localhost:8090/rest/class/create?classname=Person&superclass=Root"
#curl "http://localhost:8090/rest/class/create?classname=Staff&superclass=Person"
#curl "http://localhost:8090/rest/class/create?classname=Student&superclass=Person"
#curl "http://localhost:8090/rest/class/create?classname=Lectuer&superclass=Staff"
#curl "http://localhost:8090/rest/class/create?classname=TeachingAssistant&superclass=Staff"
#curl "http://localhost:8090/rest/class/create?classname=Undergraduate&superclass=Student"
#curl "http://localhost:8090/rest/class/create?classname=Postgraduate&superclass=Student"
#curl "http://localhost:8090/rest/class/create?classname=TeachingAssistant&superclass=Postgraduate"
curl "http://localhost:8090/rest/class/create?classname=Module&superclass=Root"
curl "http://localhost:8090/rest/class/create?classname=MScModule&superclass=Module"
curl "http://localhost:8090/rest/class/create?classname=BScModule&superclass=Module"

# create hierarchy
curl "http://localhost:8090/rest/class/createHierarchy?construct=\{Staff\{Lecturer,TeachingAssistant\},Student\{Undergraduate,Postgraduate\{TeachingAssistant\}\}\}&root=Person"
#curl "http://localhost:8090/rest/class/createHierarchy?construct=\{testing\}&root=Root"

# tests for delete
# curl "http://localhost:8090/rest/class/delete?classname=Person"

# added tests that should fail
#curl "http://localhost:8090/rest/class/create?classname=Module&superclass=Module";
#curl "http://localhost:8090/rest/class/create?classname=Person&superclass=Person";
#curl "http://localhost:8090/rest/class/create?classname=Module&superclass=BScModule";

# add property to class
curl "http://localhost:8090/rest/class/createProperty?classname=Student&propertyname=FirstName&datatype=String";

