<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
			<div class="col-md-8 col-md-offset-2 text-center">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2>Message all users</h2>
						<hr>
						<form action="/admin-dashboard/users/message-all/message"
							method="post">
							<div class="form-part"></div>
							<textarea placeholder="Message" rows="3"
								class="form-control no-radius shadow" name="message"
								id="message"></textarea>

							<div class="col-md-12" style="margin-top: 10px">
								<div class="col-md-1">
									<a href="/admin-dashboard/users" class="btn btn-danger"
										role="button">Cancel</a>
								</div>
								<div class="col-md-1 col-md-offset-8">
									<input type="submit" class="btn btn-success"
										value="Send Message" /><input type="hidden"
										name="${_csrf.parameterName}" value="${_csrf.token}" />
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>