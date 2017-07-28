<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />

<title>FlatFinder User Profile Page</title>

<script src="/resources/js/jquery-1.11.1.js"></script>
<script src="/resources/js/bootstrap.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js"></script>

<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">


<style type="text/css">
.btn-social {
	color: white;
	opacity: 0.8;
}

.btn-social:hover {
	color: white;
	opacity: 1;
	text-decoration: none;
}

.btn-facebook {
	background-color: #3b5998;
}

.btn-twitter {
	background-color: #00aced;
}

.btn-linkedin {
	background-color: #0e76a8;
}

.btn-google {
	background-color: #c32f10;
}
</style>
</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2 style="text-align: center; margin: 10px 0 20px 0;">
							Admin profile edit <small>Editing ${USER.login}'s profile</small>
						</h2>
						<div class="col-md-4">
							<div id="imgsrc">
								<img
									src="/resources/users/${USER.login}/${USER.login}_profile.jpg"
									class="img-rounded img-responsive" id="profileimg" />
							</div>
						</div>
						<div class="col-md-8">
							<div class="panel panel-default">
								<div class="panel-body">
									<form id="updateprofile" method="POST"
										action="/admin-dashboard/users/submitAdminEdit">

										<div class="form-group">
											<div class="fakelabel">My Buddy Status</div>
									
									  <c:if test="${USER.buddyUp == '1'}">
                <label class="radio-inline">
                      <input type="radio" name="buddyup" value="1" checked="checked">&nbsp;Active&nbsp;&nbsp;
                </label>
                <label class="radio-inline">
                      <input type="radio" name="buddyup" value="0">&nbsp;In-active&nbsp;&nbsp;
                </label>
           </c:if>
           <c:if test="${USER.buddyUp == '0'}">
                <label class="radio-inline">
                      <input type="radio" name="buddyup" value="1">&nbsp;Active&nbsp;&nbsp;
                </label>
                <label class="radio-inline">
                      <input type="radio" name="buddyup" value="0" checked="checked">&nbsp;In-active&nbsp;&nbsp;
                </label>
           </c:if>
									
									
										</div>

										<div class="form-group">
											<label for="username">Registered Username</label> <input
												type="text" class="form-control" name="username"
												placeholder="${USER.login}">
										</div>

										<div class="form-group">
											<label for="firstname">Registered First Name</label> <input
												type="text" class="form-control" name="firstname"
												placeholder="${USER.firstName}">
										</div>

										<div class="form-group">
											<label for="lastname">Registered Last Name</label> <input
												type="text" class="form-control" name="lastname"
												placeholder="${USER.lastName}">
										</div>

										<div class="form-group">
											<label for="address1">House Name/Number</label> <input
												type="text" class="form-control" name="address1"
												placeholder="${USER.address1}">
										</div>

										<div class="form-group">
											<label for="address2">Street Address</label> <input
												type="text" class="form-control" name="address2"
												placeholder="${USER.address2}">
										</div>

										<div class="form-group">
											<label for="address3">Town</label> <input type="text"
												class="form-control" name="address3"
												placeholder="${USER.address3}">
										</div>

										<div class="form-group">
											<label for="postcode">Postcode</label> <input type="text"
												class="form-control" name="postcode"
												placeholder="${USER.postcode}">
										</div>

										<div class="form-group">
											<label for="email">Registered Email</label> <input
												type="text" class="form-control" name="email"
												placeholder="${USER.email}">
										</div>


										<input type="hidden" name="id" value="${USER.id}" /> <input
											type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" /> <input type="hidden"
											name="${_csrf.parameterName}" value="${_csrf.token}" /> <input
											type="submit" class="btn btn-warning" value="Amend Details" />

									</form>
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