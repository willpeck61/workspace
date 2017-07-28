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
								href="/admin-dashboard/listings" class="list-group-item"
								role="button">Edit existing listing</a> <a
								href="/admin-dashboard/reports" class="list-group-item active"
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

			<div class="col-md-9 text-center">
				<div class="panel panel-default rounded">
					<div class="panel-heading roundtop">
						<h2 class="col-md-12" style="text-align: left; float: none;">
							Reports</h2>
					</div>
					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">

								<div class="col-md-12 tableHeading">
									<div class="col-md-1">
										<h4>Id</h4>
									</div>
									<div class="col-md-2">
										<h4>Date</h4>
									</div>
									<div class="col-md-4">
										<h4>Subject</h4>
									</div>
									<div class="col-md-3">
										<h4>Type</h4>
									</div>
									<div class="col-md-2">
										<h4>Reporter</h4>
									</div>
								</div>

								<div id="scrollBox">
									<c:if test="${reports.size() == 0}">
										<div class="col-md-12">
											<div class="col-md-1">None</div>
											<div class="col-md-2"></div>
											<div class="col-md-4"></div>
											<div class="col-md-3"></div>
											<div class="col-md-2"></div>
										</div>
									</c:if>

									<c:forEach var="reps" items="${reports}">
										<div class="col-md-12" style="padding-top: 2%">
											<div class="col-md-1">${reps.id}</div>
											<div class="col-md-2">${reps.date}</div>
											<div class="col-md-4">${reps.reportsub}</div>
											<div class="col-md-3">${reps.reportType}</div>
											<div class="col-md-2">${reps.reporter}</div>
										</div>
										<div class="col-md-12"
											style="padding: 2% 0; border-bottom: 1px solid #ddd">
											"<i>${reps.description}</i>"
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>