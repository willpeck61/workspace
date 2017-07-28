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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creative.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">
	<script src="/resources/js/bootstrap.js"></script>

<script type="text/javascript">
	function sendMsg() {
		var formData = $("#msgform").serialize();
		$.ajax({
			url : "/message/nextMsg",
			method : "post",
			data : formData,
			dataType : "html",
			success : function(e) {
				location.reload();
				scrollDown();
			}
		})
	}
</script>

</head>
<body onload="scrollDown()" id="background">
	<div class="container">
		<jsp:include page="/resources/navBar/navigationBar.jsp" />
		<!-- Includes the navigationBar -->
		<div class="row centered-form">

			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-body">
						<h2 class="text-center col-md-12" style="float: none;">
							Conversation <small>with ${conversation[0].from}</small> <a
								href="/message/create" style="float: right;"
								class="btn btn-md btn-primary type" role="button" id="newMsg">New
								Message</a>
						</h2>
						<hr>
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="scrollBox">
									<c:forEach var="message" items="${conversation}">
										<div class="col-md-12 separator">
											[<u>${message.time}</u> ]<b> ${message.from}</b>: ${message.message}
										</div>
									</c:forEach>
								</div>
								<div class="panel-footer">
									<form action="/message/nextMsg" method="post" name="msgform"
										id="msgform" style="margin: 5px 0 5px 0">
										<!--  modelAttribute="message"-->
										<input size="65%" type="text" name="msg"
											placeholder=" Reply to this conversation" autocomplete="off" /> <input
											type="hidden" value="1" name="user" /> <input type="hidden"
											value="1" name="conversation" /> <input type="hidden"
											name="${_csrf.parameterName}" value="${_csrf.token}" />

										<button type="button" class="btn btn-success"
											onClick="sendMsg()">Send Message</button>
									</form>
								</div>
								<div id="inject"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
			function scrollDown() {
				var objDiv = document.getElementById("scrollBox");
				objDiv.scrollTop = objDiv.scrollHeight;
			}
		</script>
	</div>
</body>
</html>
