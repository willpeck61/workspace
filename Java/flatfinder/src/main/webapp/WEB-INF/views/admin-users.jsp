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
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/flatfinder.css" />

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
							<a href="/admin-dashboard/users" class="list-group-item active"
								role="button">Manage users</a> <a
								href="/admin-dashboard/listings" class="list-group-item"
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
						<h4>
							<a href="/admin-dashboard/users/message-all" class="btn btn-info"
								role="button">Message All Users</a>
						</h4>
					</div>
					<div class="panel-body">
						<div class="col-md-12 tableHeading">
							<div class="col-md-1"><h4>Id</h4></div>
							<div class="col-md-2"><h4>Username</h4></div>
							<div class="col-md-2"><h4>Type</h4></div>
							<div class="col-md-2"><h4>Status</h4></div>
							<div class="col-md-5"><h4>Actions</h4></div>
						</div>

						<c:forEach var="users" items="${userlist}">
							<div class="col-md-12 rowseparator">
								<div class="col-md-1">${users.id}</div>
								<div class="col-md-2">${users.login}</div>
								<div class="col-md-2">${users.role.role}</div>
								<div class="col-md-2">${users.accountStatus}</div>
								<div class="col-md-5">
									<a href="/admin-dashboard/users/edit?id=${users.id}">Edit</a> -
									<a href="/message/create?usr=${users.login}">Message</a> -
									<c:if test="${users.accountStatus == 'active'}">
										<a href="/admin-dashboard/users/suspend?id=${users.id}">Suspend</a> - </c:if>
									<c:if test="${users.accountStatus == 'suspended'}">
										<a href="/admin-dashboard/users/unsuspend?id=${users.id}">Unsuspend</a> - </c:if>
									<c:if test="${users.accountStatus == 'deleted'}"> Deleted </c:if>
									<c:if test="${users.accountStatus == 'active'}">
										<a href="/admin-dashboard/users/delete?id=${users.id}">Delete</a>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
