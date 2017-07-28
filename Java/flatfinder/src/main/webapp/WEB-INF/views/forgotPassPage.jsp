<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>FlatFinder</title>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
	<script src="/resources/js/bootstrap.js"></script>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js" ></script>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #eee;
	overflow: auto;
	background-size: cover;
	background-image: url('http://i.imgur.com/PGVau2S.jpg');
}

.centered-form {
	margin-top: 60px;
}

.centered-form .panel {
	background: rgba(255, 255, 255, 0.8);
	box-shadow: rgba(0, 0, 0, 0.3) 20px 20px 20px;
}

label.label-floatlabel {
	font-weight: bold;
	color: #46b8da;
	font-size: 11px;
}

.separate {
	margin-bottom: 15px;
}
.centered-form {
	margin-top: 0;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row centered-form">
			<div class="panel panel-default">
				<div class="panel-body">
					<div id="success">
						<c:forEach var="suc" items="${success}">
							<p style="font-size: 20px;">${suc}</p>
						</c:forEach>
					</div>
					<a class="btn btn-info" href="/user-login">Back to login</a>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1 class="form-signin-heading text-muted">
						Flat<b>Finder</b>
					</h1>
					<h2>Forgotten Password</h2>
				</div>
				<div class="panel-body">
					<c:if test="${error == true}">
						<br>
						<b class="err">Invalid Credentials</b>
					</c:if>
					<c:if test="${logout == true}">
						<b class="err">You have been logged out.</b>
					</c:if>
					<c:url value="/submitForgotPassword" var="forgotpasswordurl" />
					<form class="form-horizontal" role="form"
						action="${forgotpasswordurl}" method="post">
						<div class="panel panel-default">
							<p class="panel-heading">Enter your details to reset your
								password</p>
							<div class="form-group">
								<label for="email" class="col-sm-2 control-label">Email</label>
								<div class="col-sm-10">
									<input placeholder="email" class="form-control" type="email"
										name="email" />
								</div>
							</div>

							<div class="form-group">
								<label for="p2" class="col-sm-2 control-label">Secret
									question</label>
								<div class="col-sm-10">
									<input placeholder="Enter a secret question"
										class="form-control" type="text" id="secret" name="secret" />
								</div>
							</div>
							<div class="form-group">
								<label for="p2" class="col-sm-2 control-label">Answer</label>
								<div class="col-sm-10">
									<input placeholder="Enter the answer to the question"
										class="form-control" type="text" id="answer" name="answer" />
								</div>
							</div>
							<div class="form-group">
								<label for="p1" class="col-sm-2 control-label">Password</label>
								<div class="col-sm-10">
									<input placeholder="Password" class="form-control"
										oninput="check(this)" type="password" name="p1" id="p1"
										required />
								</div>
							</div>
							<div class="form-group">
								<label for="p2" class="col-sm-2 control-label">Password
									Again</label>
								<div class="col-sm-10">
									<input placeholder="Password Again" class="form-control"
										oninput="check(this)" type="password" id="p2" name="p2" />
								</div>
							</div>
						</div>
						<button class="btn btn-success pull-right" type="submit"
							name="submit">Submit</button>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
			</div>
			<script language='javascript' type='text/javascript'>
				function check(input) {
					if (input.value != document.getElementById('p1').value) {
						input
								.setCustomValidity('The two passwords must match.');
					} else {
						// input is valid -- reset the error message
						input.setCustomValidity('');
					}
				}
			</script>
		</div>
	</div>
</body>
</html>