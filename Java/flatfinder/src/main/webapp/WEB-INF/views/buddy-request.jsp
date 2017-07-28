<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />

<!-- CORE JQUERY FILE -->
<script src="resources/js/jquery-1.11.1.js"></script>
<!-- REQUIRED BOOTSTRAP SCRIPTS -->
<script src="resources/js/bootstrap.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js"></script>

<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<!-- BOOTSTRAP STYLE SHEET -->
<link href="resources/css/bootstrap.css" rel="stylesheet" />
<!-- FONT-AWESOME STYLE SHEET FOR BEAUTIFUL ICONS -->
<link href="resources/css/font-awesome.css" rel="stylesheet" />
<!-- CUSTOM STYLE CSS -->
<link href="resources/css/flatfinder.css" rel="stylesheet" />

<style type="text/css">
a:hover {
	text-decoration: none;
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
						<div class="panel panel-default">
							<div class="panel-heading">
								<h2>New Buddy Request</h2>
							</div>
							<div class="panel-body">
								<div class="col-md-3">
									<a><img
										src="./resources/users/${BUDDY.login}/${BUDDY.login}_profile.jpg"
										height=100 width=80 /></a>
								</div>
								<div class="col-md-8">
									<h3>
										<a href="view-buddy-profile?username=${BUDDY.login}">${BUDDY.login}</a>
									</h3>
									<c:if test="${PROFILE.firstEdit == 1}">
										<p>
											<a href="view-buddy-profile?username=${BUDDY.login}"><em>"${PROFILE.headline}"</em></a>
										</p>
									</c:if>
									<c:if test="${PROFILE.firstEdit == 0}">
										<p>
											<a href="view-buddy-profile?username=${BUDDY.login}"><em>No
													profile data has been added.</em></a>
										</p>
									</c:if>
								</div>
							</div>
							<div class="panel-footer" style="padding: 10px 15px 0px 15px;">
							<div class="col-md-12" style="float: none;">
								<div class="col-md-1">
									<form action="action-buddy-request" method="GET">
										<input type="hidden" name="notifyid" value="${NOTIFY.id}" />
										<input type="hidden" name="requestid" value="${BUDDYUP.id}" />
										<input type="hidden" name="response" value="accept" />
										<button type="submit" id="accept" class="btn btn-warning">ACCEPT</button>
									</form>
									</div>
								<div class="col-md-1 col-md-offset-10" style="float: none;">
									<form action="action-buddy-request" method="GET">
										<input type="hidden" name="notifyid" value="${NOTIFY.id}" />
										<input type="hidden" name="requestid" value="${BUDDYUP.id}" />
										<input type="hidden" name="response" value="reject" />
										<button type="submit" id="reject" class="btn btn-warning">REJECT</button>
									</form>
								</div>
								</div>
							</div>
						</div>
						<script>
							document
									.write('<button type="button" class="btn btn-default"><a href="' + document.referrer + '">Go Back</a></button>');
						</script>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
