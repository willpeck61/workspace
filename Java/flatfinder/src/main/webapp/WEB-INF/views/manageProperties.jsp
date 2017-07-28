<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">
	<script src="/resources/js/bootstrap.js"></script>

</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-3">
				<div id="navCol" class="panel panel-default">
					<div class="panel-body">
						<p class="lead">Navigate</p>
						<div class="list-group">
							<a href="/landlord-dashboard/create" class="list-group-item"
								role="button">Create new listing</a> <a
								href="/landlord-dashboard/manage" class="list-group-item active"
								role="button">Edit existing listing</a> <a
								href="/landlord-dashboard/statistics" class="list-group-item"
								role="button">View statistics</a><br /> <a onclick="goBack()"
								class="list-group-item" style="cursor: pointer;">Go Back</a>

							<script>
								function goBack() {
									window.history.back();
								}
							</script>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2 style="text-align: center;">Edit your existing listings</h2>
						<img width="100%" src="/resources/img/mapbanner.jpg" />

						<div class="panel panel-default" style="margin-top: 10px">
							<div class="panel-body">
								<div class="col-md-12 tableHeading">
									<div class="col-md-3">Address</div>
									<div class="col-md-2">Postcode</div>
									<div class="col-md-1">Rooms</div>
									<div class="col-md-1">Lister</div>
									<div class="col-md-2"></div>
									<div class="col-md-2"></div>
								</div>
								<c:forEach var="list" items="${listing}">
									<div class="col-md-12 propertyRow">
										<div class="col-md-3">${list.houseNum} ${list.address1}</div>
										<div class="col-md-2">${list.postcode}</div>
										<div class="col-md-1">${list.numberOfRooms}</div>
										<div class="col-md-1">You</div>
										<div class="col-md-2">
											<a href="/landlord-dashboard/manage/editRoom?id=${list.id}"
												class="btn btn-info" role="button"
												style="margin-bottom: 8px;">Edit Rooms</a>
										</div>
										<div class="col-md-2">
											<a
												href="/landlord-dashboard/manage/editProperty?id=${list.id}"
												class="btn btn-info" role="button"
												style="margin-bottom: 8px;">Edit Property</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>