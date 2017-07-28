<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>SciM Management Console - Login</title>
<link rel="stylesheet" type="text/css" href="/resources/css/styles.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js" ></script>
</head>
<body>
	<div class="main">
        <header>
            <div id="title">
                <h1 class="fit">SCI Management<br>Console</h1>
            </div>
        </header>
        <span id="ui">
            <aside id="lhs">
            
            </aside>
            <section>
                <div id="content">
                <c:if test="${error == true}">
                <br>
    				<b class="err">Invalid Credentials</b>
				</c:if>
				<c:if test="${logout == true}">
    				<b class="err">You have been logged out.</b>
				</c:if>
                <h2>Login</h2>
                <c:url value="/login" var="loginUrl"/>
                <form action="${loginUrl}" method="post" modelAttribute="user" id="login" name="login">
                    <ul>
                        <li><label for="username">Username:&nbsp;</label></li>
                        <li><input type="text" name="username" required></li>
                        <li><label for="password">Password:&nbsp;</label></li>
                        <li><input type="password" name="password" required></li>
                        <li><button type="submit" name="submit">Login</button></li>
                    </ul>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
                </div>
                
            </section>    
            <aside id="rhs">
            </aside>
        </span>
    </div>
    <footer>
        <p>Will Peck - Sci Management System</p>
    </footer>
    <script type="text/javascript" src="/resources/script/jquery.fittext.js" ></script>
</body>
</html>