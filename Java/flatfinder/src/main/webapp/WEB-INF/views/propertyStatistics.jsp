<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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

<html>
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
								href="/landlord-dashboard/manage" class="list-group-item"
								role="button">Edit existing listing</a> <a
								href="/landlord-dashboard/statistics"
								class="list-group-item active" role="button">View statistics</a><br />
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
					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<table class="table">
									<thead>
										<tr>
											<th>Address</th>
											<th>Postcode</th>
											<th>Views</th>
											<th>Interests</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="statistics" items="${statistics}">

											<tr>
												<td>${statistics.address1}</td>
												<td>${statistics.postcode}</td>
												<td>${statistics.numViews}</td>
												<td>${statistics.numInterests}</td>
											</tr>
										</c:forEach>
										</form>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
