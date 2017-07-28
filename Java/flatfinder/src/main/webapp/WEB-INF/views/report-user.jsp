<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2 style="text-align: center">Report User</h2>
						<hr>
						<form action="/submitUserReport" method="post">
						<input name="subject" type="hidden" value="Reporting user <%= request.getParameter("username") %>" />
							<div class="alert alert-danger">
								Reporting <b><a
									href="/view-buddy-profile?username=<%=request.getParameter("username")%>">User
										<%=request.getParameter("username")%></a></b>
							</div>
							<textarea placeholder="Write your report here" rows="5"
								class="form-control no-radius shadow" name="description"></textarea>

							<input class="btn btn-primary" style="margin-top: 10px"
								type="submit" value="Submit Report" /> <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" /> <input
								type="hidden" name="username"
								value="<%=request.getParameter("username")%>" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>