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
						<h2 class="col-md-12" style="text-align: left; float: none;">
							Inbox</h2>
					</div>
					<div class="panel-body">

						<div class="panel panel-default">
							<div class="panel-body">

								<div class="col-md-12 tableHeading">
									<div class="col-md-2">
										<h4>From</h4>
									</div>
									<div class="col-md-2">
										<h4>To</h4>
									</div>
									<div class="col-md-5">
										<h4>Received</h4>
									</div>
									<div class="col-md-3"></div>
								</div>

								<div id="scrollBox">
									<c:if test="${msgs.size() == 0}">
										<div class="col-md-12">
											<div class="col-md-2">None</div>
											<div class="col-md-2"></div>
											<div class="col-md-5"></div>
											<div class="col-md-3"></div>
										</div>
									</c:if>
									<c:forEach var="message" items="${msgs}">
										<div class="col-md-12" style="padding-bottom: 1%">
											<div class="col-md-2">${message.sender}</div>
											<div class="col-md-2">${message.receiver}</div>
											<div class="col-md-5">${message.creation}</div>
											<a href="/inbox/view?id=${message.id}"
												class="btn btn-info btn-sm col-md-3" role="button">View
												Message</a>
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
