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
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default" style="border-radius: 20px">
					<div class="panel-heading roundtop" >
						<h2 class="form-signin-heading" style="text-align:center;">Manage your listings!</h2>
					</div>
					
					<div class="panel-body">
					<img src="/resources/img/mapbanner.jpg" style="width:100%"/>
						<h3>Navigate</h3>
						<div class="form-group" style="text-align: center">
							<a href="/landlord-dashboard/create"
								class="btn btn-md btn-primary btn-block" role="button">Create
								New Listing</a> <a href="/landlord-dashboard/manage"
								class="btn btn-md btn-primary btn-block" role="button">Edit
								existing Listing</a> <a href="/landlord-dashboard/statistics"
								class="btn btn-md btn-primary btn-block" role="button">View
								statistics</a>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>