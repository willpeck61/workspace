<%@page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <div id="status">
			    <table>
			        <thead>
			            <th class="fit1">Pending Requests</th>
			            <th class="fit2">Active Requests</th>
			            <th class="fit3">Completed Requests</th>
			        </thead>
			        <tbody>
			        <tr>
			            <td class="fit4" id="pending">0</td>
			            <td class="fit5" id="active">0</td>
			            <td class="fit6" id="complete">0</td>
			        </tr>
			        </tbody>
			    </table>
			</div>
        </header>
        <span id="ui">
            <aside id="lhs">
			    <h2>navigation</h2>
			    <nav>
			        <ul>
			            <li><a href="#" id="menu0">create new request</a></li>
			            <li><a href="#" id="menu1">assigned requests</a></li>
			            <li><a href="#" id="menu2">view active requests</a></li>
			            <sec:authorize access="hasRole('ADMIN')" >
			            <li><a href="#" id="menu3">view task management</a></li>
			            <li><a href="#" id="menu4">review pending requests</a></li>
			            <li><a href="#" id="menu5">user administration</a>
 			            </sec:authorize>
			        </ul>
			        <ul>
			        	<c:url value="/logout" var="logoutUrl"/>
			        	<form action="${logoutUrl}" method="get" id="logout">
			        	<!--  input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/ -->
			        	</form>
			        	<li><a href="#" id="logout" onclick="document.getElementById('logout').submit()">>>Logout</a></li>
			        </ul>    
			    </nav>
			</aside>
			<section>
			    <h2 id="task">welcome</h2>
			    <div id="content"></div>
			    
			</section>
			<aside id="rhs">
			    <h2>log</h2>
			    <div id="log"></div>
			</aside>
        </span>
    </div>
    <footer>
        <p>Coding By Will Peck - Sci Management System</p>
    </footer>
    <form><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></form>
    <script type="text/javascript" src="/resources/script/jquery.fittext.js" ></script>
</body>
</html>