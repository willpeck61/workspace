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
			<div class="col-md-8 col-md-offset-2 text-center">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2>Create a message!</h2>
						<hr>
						<form action="/sendInitMessage" method="post"
							modelAttribute="user" id="sendInitMessage" name="sendInitMessage">
							<div class="form-part">
								<div class="form-group shadow">
									<div class="input-group">
										<span class="input-group-addon no-radius"><i
											class="fa fa-user"></i>To</span> <input type="text"
											class="form-control no-radius"
											aria-describedby="inputGroupSuccess1Status" name="userToSend"
											id="userToSend" placeholder="Recipient username"
											value="<%=request.getParameter("usr")%>" required="required">
									</div>
								</div>
							</div>
							<textarea placeholder="Write your message here" rows="5"
								class="form-control no-radius shadow" name="messageContent"
								id="messageContent"></textarea>

							<div class="col-md-12" style="margin-top:10px">
								<div class="col-md-1">
									<a href="/message" class="btn btn-danger" role="button">Cancel</a>
								</div>
								<div class="col-md-1 col-md-offset-8">
									<input type="submit" class="btn btn-success"
										value="Send Message"> <input type="hidden"
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