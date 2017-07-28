<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>FlatFinder</title>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js" ></script>
<style>
  body {
    padding-top: 40px;
    padding-bottom: 40px;
    background-color: #eee;
  }
  .fullscreen_bg {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-size: cover;
    background-position: 50% 50%;
    background-image: url('http://i.imgur.com/PGVau2S.jpg');
  }
  .form-signin {
    max-width: 280px;
    padding: 15px;
    margin: 0 auto;
  }
  .form-signin .form-signin-heading, .form-signin {
    margin-bottom: 10px;
  }
  .form-signin .form-control {
    position: relative;
    font-size: 16px;
    height: auto;
    padding: 10px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
  }
  .form-signin .form-control:focus {
    z-index: 2;
  }
  .form-signin input[type="text"] {
    margin-bottom: -1px;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
    border-top-style: solid;
    border-right-style: solid;
    border-bottom-style: none;
    border-left-style: solid;
    border-color: #000;
  }
  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    border-top-style: none;
    border-right-style: solid;
    border-bottom-style: solid;
    border-left-style: solid;
    border-color: #000;
  }
  .form-signin-heading {
    color: #fff;
    text-align: center;
    text-shadow: 0 2px 2px rgba(0,0,0,0.5);
  }
  .regFont {
  	font-size: 20px;
  	font-color: #000000;
  }
  .well {
   background-color: rgba(245, 245, 245, 0.4);
  }
</style>
</head>
<body>


<div id="fullscreen_bg" class="fullscreen_bg"/>

<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
        <span id="ui">
            <aside id="lhs"></aside>
            <section>
                <div id="content">
                <c:if test="${error == true}">
                <br>
    				<b class="err">Invalid Credentials</b>
				</c:if>
				<c:if test="${logout == true}">
    				<b class="err">You have been logged out.</b>
				</c:if>
                <h1 class="form-signin-heading text-muted">Flat<b>Finder</b></h1>
                <c:url value="/login" var="loginUrl"/>
                <form class="form-signin" action="${loginUrl}" method="post" modelAttribute="user" id="login" name="login">
                        <input placeholder="Username" class="form-control" type="text" name="username" required>
                        <input placeholder="Password" class="form-control" type="password" name="password" required>
                        <button class="btn btn-lg btn-primary btn-block type="submit" name="submit">Login</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
                </div>
                <center><c:url value="/user-registration" var="userReg" />
                	<div class="regFont">
                		<a href="/test-users">Populate Test Users</a><br>
                		<a href="${userReg}">Click here to register.</a>
                	</div>
                </center>
            </section>    
            <aside id="rhs"></aside>
        </span>
    </div>
    </div>
    <script type="text/javascript" src="/resources/script/jquery.fittext.js" ></script>
</body>
</html>