<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link rel="stylesheet" href="/resources/css/leaflet.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="/resources/script/leaflet.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<link rel="stylesheet" href="/resources/css/bjqs.css">
<link rel="stylesheet" href="/resources/css/demo.css">

<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">

<!-- load jQuery and the plugin -->
<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script src="/resources/js/bjqs-1.3.js"></script>
<script src="/resources/js/bootstrap.js"></script>

<script>
	$(document).ready(function(){
		$("#map").hide();
		$("#rooms").slideUp(1);
		
	    $("#showMapButton").click(function(){
	        $("#map").slideDown();
	        $("#property").slideUp();
	        $("#rooms").slideUp();
	        
	        $("#showMapButton").removeClass('list-group-item').addClass('list-group-item active');
	        $("#showPropertyButton").removeClass('list-group-item active').addClass('list-group-item');
	        $("#showRoomsButton").removeClass('list-group-item active').addClass('list-group-item');
	    });
	    $("#showPropertyButton").click(function(){
	        $("#property").slideDown();
	        $("#map").slideUp();
	        $("#rooms").slideUp();
	        
	        $("#showMapButton").removeClass('list-group-item active').addClass('list-group-item');
	        $("#showPropertyButton").removeClass('list-group-item').addClass('list-group-item active');
	        $("#showRoomsButton").removeClass('list-group-item active').addClass('list-group-item');
	    });
	    $("#showRoomsButton").click(function(){
	        $("#rooms").slideDown();
	        $("#map").slideUp();
	        $("#property").slideUp();
	        
	        $("#showMapButton").removeClass('list-group-item active').addClass('list-group-item');
	        $("#showPropertyButton").removeClass('list-group-item active').addClass('list-group-item');
	        $("#showRoomsButton").removeClass('list-group-item').addClass('list-group-item active');
	    });
	});
	
	
</script>

<style>
#map {
	height: 500px;
	width: 100%;
}

#rooms {
	height: 500px;
	width: 100%;
}

#property {
	height: 500px;
	width: 100%;
}

@media screen and (max-width: 991px) {
	.mobileMargin {
		margin-top: 2%;
	}
}

.borderStyle {
	border-width: 1px;
	border-style: solid;
	border-color: #ddd;
	border-radius: 4px;
	border-style: solid;
}

.feedbackComment {
	margin-top: 2%;
	padding: 3% 0 3% 0;
	border-top: 1px solid #cdcdcd;
	border-bottom: 1px solid #cdcdcd;
}

.interestIcon {
	width: 20px;
	height: 20px;
	padding-bottom: 2px;
	padding-left: 3px;
}

.topPadding {
	padding: 2% 0 2% 0;
	margin-top: 1%;
}
</style>


</head>
<body id="background">

	<div class="container">
		<jsp:include page="/resources/navBar/navigationBar.jsp" />
		<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="lead" style="margin-bottom: 0">Navigation</h5>
					</div>
					<div class="panel-body">
						<div class="list-group" style="width: 90%">
							<a href="#" class="list-group-item active"
								id="showPropertyButton">Property</a> <a href="#"
								class="list-group-item" id="showRoomsButton">Rooms</a> <a
								href="#" class="list-group-item" id="showMapButton">Map</a> <br />
							<script>
    					document.write('<a href="' + document.referrer + '" class="list-group-item" style-"cursor: pointer;">Go Back</a>');
					</script>
						</div>

						<a href="/property/report?id=${information.id}"
							class="btn btn-danger">Report property</a>
						<hr>
						<h4>Property Feedback</h4>
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="col-md-12 tableHeading">
									<div class="col-md-3">User</div>
									<div class="col-md-6">Date posted</div>									
									<div class="col-md-3">Rating</div>
								</div>

								<c:set var="contains" value="false" />

								<c:if test="${feedback.size() == 0}">
									<div class="col-md-3">None</div>
									<div class="col-md-3"></div>
									<div class="col-md-6"></div>
								</c:if>
								<c:if test="${feedback.size() != 0}">
									<div id="scrollBox" class="fixHeight">
										<c:forEach var="fbItem" items="${feedback}">
											<c:if test="${fbItem.userName eq username}">
												<c:set var="contains" value="true" />
											</c:if>
											<div class="col-md-12" style="padding: 3% 0 3% 0;">
												<div class="panel panel-default" style="margin-bottom: 0px;">
													<div class="panel-body">
														<div style="display: inline-flex;">
															<div class="col-md-3" style="padding-left: 0">${fbItem.userName}</div>
															<div class="col-md-6">${fbItem.created}</div>
														</div>
															<div class="col-md-12">

															</div>
																<c:forEach var="i" begin="1" end="${fbItem.rating}">
																	<img style="height:15px;width:15px" src="resources/img/ratingStar.png" />
																</c:forEach>
														<div class="col-md-12 feedbackComment">${fbItem.comment}</div>
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-8">
				<div class="panel panel-default"
					style="padding: 3% 0 0 0; background: rgba(255, 255, 255, 1);">
					<div class="panel-body">
						<div class="col-md-12" style="text-align: center;">
							<h4 class="col-md-6 mobileMargin">
								<b>${information.address1}</b>, ${information.postcode}
							</h4>
							<h4 class="col-md-6">
								&pound;${information.pricePerWeek} <small>per week</small>
							</h4>
						</div>
						<div id="property">
							<!-- DO NOT REMOVE. Needed for slide effect -->
							<div id="container">
								<div id="banner-fade">
									<ul class="bjqs">
										<li><img
											src="./resources/properties/${information.id}/${information.id}_main.jpg"
											width="100%" height="100%"></li>
										<c:if test="${hasFloorPlan eq 'yes'}">
											<li><img
												src="./resources/properties/${information.id}/${information.id}_floorPlan.jpg"
												width="100%" height="100%"></li>
										</c:if>
									</ul>
								</div>
							</div>

							<script class="secret-source">
					        jQuery(document).ready(function($) {
					
					          $('#banner-fade').bjqs({
					            height      : 800,
					            width       : 1200,
					            responsive  : true
					          });
					
					        });
					      </script>

						</div>


						<div id="rooms">
							<div id="container1">
								<br />
								<div id="banner-fade1">
									<ul class="bjqs">
										<c:forEach var="propertyRooms" items="${propertyRooms}">
											<li><img
												src="./resources/properties/${information.id}/rooms/room_${propertyRooms.id}.jpg"
												width="100%" height="100%"></li>
										</c:forEach>
									</ul>
								</div>
							</div>

							<script class="secret-source">
					        jQuery(document).ready(function($) {
					
					          $('#banner-fade1').bjqs({
					            height      : 800,
					            width       : 1200,
					            responsive  : true
					          });
					
					        });
					      </script>
						</div>


						<div id="map">
							<script>
							var loc = [${lat}, ${lng}];
							var map = L.map('map').setView(loc, 16);
							
							L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
							}).addTo(map);
							
							L.marker(loc).addTo(map)
							    .bindPopup("${information.postcode}")
							</script>
						</div>

						<div class="col-md-12 borderStyle topPadding">
							<div class="col-md-6">
								<div class="col-md-12">
									<div class="col-md-6 mobileMargin">
										<b>Rooms:</b>
									</div>
									<div class="col-md-3">${information.numberOfRooms}</div>
								</div>
								<div class="col-md-12" style="margin-top: 3%">
									<div class="col-md-6 mobileMargin">
										<b>Available rooms:</b>
									</div>
									<div class="col-md-3">${information.numberOfRooms - numberOfOccupiedRooms}</div>
								</div>
								<div class="col-md-12" style="margin-top: 3%">
									<div class="col-md-6 mobileMargin">
										<b>Occupant type:</b>
									</div>
									<div class="col-md-3">${information.occupantType}</div>
								</div>
								<div class="col-md-12" style="margin-top: 3%">
									<div class="col-md-6 mobileMargin">
										<b>Start date:</b>
									</div>
									<div class="col-md-6">
										<fmt:formatDate value="${information.startDate}"
											pattern="dd/MM/yyyy" />
									</div>
								</div>
								<div class="col-md-12" style="margin-top: 3%">
									<div class="col-md-6 mobileMargin">
										<b>Date posted:</b>
									</div>
									<div class="col-md-6">
										<fmt:formatDate value="${information.date}"
											pattern="dd/MM/yyyy" />
									</div>

								</div>
							</div>

							<div class="col-md-6 mobileMargin"
								style="border-left: 1px solid #a6a6a6">
								<div>
									<em>${information.description}</em>
									<hr />
									<p><em>Would you like to visit this property? If so, click the icon to start routing!</em></p>
									<p align="center">
									<a target='_blank' href="http://maps.google.com/?daddr=${information.address1} ${information.postcode}"><img style="height:100px;width:100px" src="/resources/img/routeme.png" /></a>
									</p>
								<hr />
								</div>
							</div>

							<sec:authorize access="hasRole('SEARCHER')">
							<div class="col-md-3 col-md-offset-9 mobileMargin">
							<c:choose>
								<c:when test="${interested == true}">
									<a href='/removeInterestedProperty?id=${information.id}'
										class="btn btn-warning">Remove Interest</a>
								</c:when>
								<c:otherwise>
									<a href='/InterestedProperty?id=${information.id}&username=<%= request.getUserPrincipal().getName() %>'
										class="btn btn-warning">Express Interest</a>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
						</sec:authorize>


						<div class="col-md-12">
							<h3>Submit feedback</h3>
							<div class="panel panel-default">
								<form action="/submitFeedback" method="post">
									<div class="panel-body">
										<c:if test="${contains == false}">
											<div id="ratingChart">
												Rating: <input type="radio" name="rating" value="1" checked>
												1 <input type="radio" name="rating" value="2"> 2 <input
													type="radio" name="rating" value="3"> 3 <input
													type="radio" name="rating" value="4"> 4 <input
													type="radio" name="rating" value="5"> 5
											</div>
											<br>
											<input type="hidden" name="id" value="${information.id}" />
											<br>
											<textarea style="width: 100%; height: 30%;" name="comment"
												placeholder=" Write your feedback here"></textarea>
											<input class="btn btn-success" type="submit"
												style="margin: 15px 0 0 15px" value="Submit feedback" />
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</c:if>

										<c:if test="${contains != false}">
											<h5>Thanks for leaving feedback!</h5>
										</c:if>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>