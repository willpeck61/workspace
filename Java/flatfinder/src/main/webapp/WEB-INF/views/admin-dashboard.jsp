<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
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
		<div class="row centered-form">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default" style="border-radius: 20px">
					<div class="panel-heading roundtop" >
						<h2 class="form-signin-heading" style="text-align:center;">Administrator Options</h2>
					</div>
					
					<div class="panel-body">
					<img src="/resources/img/mapbanner.jpg" style="width:100%"/>
						<h3>Navigate</h3>
						<div class="form-group" style="text-align: center">
							<a href="/admin-dashboard/users" class="btn btn-md btn-primary btn-block" role="button">Manage Users</a> 
							<a href="/admin-dashboard/listings" class="btn btn-md btn-primary btn-block" role="button">Manage Listings</a>
							<a href="/admin-dashboard/reports" class="btn btn-md btn-primary btn-block" role="button">View Reports</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
