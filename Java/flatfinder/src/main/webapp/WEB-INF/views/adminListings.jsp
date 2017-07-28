<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script src="/resources/js/bootstrap.js"></script>

<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">

</head>

<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
		<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-body">
						<p class="lead">Navigate</p>
						<div class="list-group">
							<a href="/admin-dashboard/users" class="list-group-item"
								role="button">Manage users</a> <a
								href="/admin-dashboard/listings" class="list-group-item active"
								role="button">Edit existing listing</a> <a
								href="/admin-dashboard/reports" class="list-group-item"
								role="button">View reports</a><br />
							<a onclick="goBack()" class="list-group-item"
								style="cursor: pointer;">Go Back</a>

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
					<div class="panel-heading">
						<h2 style="text-align:center">Mass manage listings</h2>
					</div>
					<img width="100%" src="/resources/img/mapbanner.jpg" />
					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">

								<div class="col-md-12 tableHeading">
									<div class="col-md-1">Id</div>
									<div class="col-md-2">Address</div>
									<div class="col-md-2">Postcode</div>
									<div class="col-md-1">Listed</div>
									<div class="col-md-1">PPW</div>
									<div class="col-md-5">Actions</div>
								</div>


								<c:forEach var="props" items="${allprops}">
									<div class="col-md-12"
										style="padding-top: 2%; border-bottom: 1px solid #ddd">
										<div class="col-md-1">${props.id}</div>
										<div class="col-md-2">${props.houseNum} ${props.address1}</div>
										<div class="col-md-2">${props.postcode}</div>
										<div class="col-md-1">${props.listed}</div>
										<div class="col-md-1">&pound;${props.pricePerWeek}</div>
										<div class="col-md-5">
											<a href="/admin-dashboard/listings/editRoom?id=${props.id}"
												class="btn btn-info btn-sm" role="button">Edit Rooms</a> <a
												href="/admin-dashboard/listings/editProperty?id=${props.id}"
												class="btn btn-info btn-sm" role="button">Edit Property</a>
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