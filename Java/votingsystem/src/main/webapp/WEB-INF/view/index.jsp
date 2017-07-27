<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
  uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Landing Page</title>
<script type="text/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/css/bootstrap.min.css" />
</head>
<body>
	<div class="col-md-6">
		<h1>Voting System Login</h1>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>Login</h2>
				<a href="/registration">Register</a>
			</div>
			<div class="panel-body">
				<form action="/login" method="POST">
					<div class="form-group">
						<label for="name">Username</label> <input type="text"
							class="form-control" id="name" name="username"
							placeholder="username" value="${cookie.lastUser.value}" />
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password"
							class="form-control" name="password" placeholder="password" />
					</div>
					<div class="form-group">
						<button class="btn btn-default" id="loginbtn">Login</button>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
				<div class="form-group">
				  <p><b>Demonstration</b></p>
				  <p>Login as "<em>ero</em>" and "<em>password</em>" for Electoral Registration Officer.<br>
				     Login as "<em>test1</em>" and "<em>password</em>" for Voter login or register.</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>