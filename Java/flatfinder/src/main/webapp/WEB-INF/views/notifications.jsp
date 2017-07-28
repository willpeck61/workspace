<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js"></script>

<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creative.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">
	<script src="/resources/js/bootstrap.js"></script>


<style>
.tableHeading {
	border: 1px solid #ddd;
	border-radius: 3px;
	color: #333;
	background-color: #f5f5f5;
	border-color: #ddd;
	margin-bottom: 1%;
}
</style>
</head>
<body id="background">
	<div class="container">
		<jsp:include page="/resources/navBar/navigationBar.jsp" />
		<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-8 col-md-offset-2 text-center">
				<div class="panel panel-default rounded">
					<div class="panel-heading roundtop">
						<h2 style="text-align: left;">
							Notifications<span class="label label-default"
								style="float: right">Unread: ${numUnreadNotifs}</span>
						</h2>
					</div>
					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="col-md-12 tableHeading" style="padding-left: 0">
									<div class="col-md-3">
										<h4>Notification</h4>
									</div>
									<div class="col-md-3">
										<h4>Type</h4>
									</div>
									<div class="col-md-4">
										<h4>Time</h4>
									</div>
									<div class="col-md-2">
										<h4>Seen</h4>
									</div>
								</div>
								<div id="scrollBox">
									<c:if test="${notifications.size() == 0}">
										<div class="col-md-12">
											<div class="col-md-3">None</div>
											<div class="col-md-3"></div>
											<div class="col-md-4"></div>
											<div class="col-md-2"></div>
										</div>
									</c:if>

									<c:forEach var="notif" items="${notifications}">
										<div class="col-md-12 separator">
											<c:if test="${notif.notificationType == 'BuddyUp'}">
												<div class="col-md-3">
													<a href="${notif.redirUrl}?notifyid=${notif.id}">${notif.notificationText}</a>
												</div>
											</c:if>
											<c:if test="${notif.notificationType != 'BuddyUp'}">
												<div class="col-md-3">
													<a href="${notif.redirUrl}">${notif.notificationText}</a>
												</div>
											</c:if>
											<div class="col-md-3">${notif.notificationType}</div>
											<div class="col-md-4">${notif.whenCreated}</div>
											<div class="col-md-2"><c:if test="${notif.isSeen}"><img src="/resources/img/tick.png" style="height:25px;width:25px" /></c:if></div>
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