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
			<div class="col-md-8 col-md-offset-2 text-center">
				<div class="panel panel-default rounded">
					<div class="panel-heading roundtop">
						<h2 class="col-md-12 textleft">
							Inbox Message<a href="/inbox" class="btn btn-md btn-primary type"
								role="button" style="float: right">Go back</a>
						</h2>
					</div>

					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row tableHeading">
								<h4 class="col-md-4"><small>From:</small> ${msg.sender}</h4>
								<h4 class="col-md-4"><small>To: </small>${msg.receiver}</h4>
								<h4 class="col-md-4"><small>Date:</small> ${msg.creation}</h4>
							</div>
							<div class="col-md-12" style="text-align: left">${msg.message}</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
