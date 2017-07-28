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
	<script src="/resources/js/bootstrap.js"></script>

</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2 style="text-align: center">Submit a property report</h2>
						<hr>
						<form action="/submitPropertyReport" method="post">
						<input name="subject" type="hidden" value="Reporting property <%= request.getParameter("id") %>" />
							<div class="alert alert-danger">
								Reporting <b><a
									href="/property?id=<%=request.getParameter("id")%>">property
										<%=request.getParameter("id")%></a></b>
							</div>
							<textarea placeholder="Write your report here" rows="5"
								class="form-control no-radius shadow" name="description"></textarea>

							<input class="btn btn-primary" style="margin-top: 10px"
								type="submit" value="Submit Report" /> <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" /> <input
								type="hidden" name="propId"
								value="<%=request.getParameter("id")%>" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>