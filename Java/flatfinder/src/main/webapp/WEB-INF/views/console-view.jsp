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
			            <li><a href="view-profile" id="profile">view my profile</a></li>
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
			    <h2 id="task">welcome world</h2>
			    <div id="content">
			    	<h3>Search</h3>
			    	
			   
			    	
			    	<form class="form-signin" action="/test" method="POST" modelAttribute="user" id="login" name="login">
				    	<table width=100%>
				    		<tr>
				    			<td colspan=4> <!-- For now only searching for postcode -->
				    				<input placeholder="Postcode" type="text" name="postcode" style="width:90%;">
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan=4></td>
				    		</tr>
				    		<tr>
				    			<td>
				    				Min price
				    			</td>
				    			<td>
				    				Max price
				    			</td>
				    			<td>
				    				Property type
				    			</td>
				    			<td>
				    				Bedrooms
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    				<select name="minPrice" id="minPrice">
									  <option value="0">No min</option>
									  <option value="75">£75</option>
									  <option value="80">£80</option>
									  <option value="85">£85</option>
									</select>
				    			</td>
				    			<td>
				    				<select name="maxPrice" id="maxPrice">
									  <option value="10000">No max</option> <!-- No Max is set to 10,000   change later -->
									  <option value="90">£90</option>
									  <option value="95">£95</option>
									  <option value="105">£105</option>
									</select>
				    			</td>
				    			<td>
				    				<select name="propType" id="propType">
									  <option value="showAll">Show all</option>
									  <option value="flat">Flat</option>
									  <option value="house">House</option>
									</select>
				    			</td>
				    			<td>
				    				<select name="numRooms" id="numRooms">
									  <option value="0">No min</option>
									  <option value="1">1</option>
									  <option value="2">2</option>
									  <option value="3">3</option>
									</select>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td></td>
				    			<td></td>
				    			<td></td>
				    			<td>
				    				<button class="btn btn-lg btn-primary btn-block type="submit" name="submit">Search</button>
				    			</td>
				    		</tr>
				    	</table>
			    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />	
               		</form>
			    	
			    </div>
			</section>
        </span>
    </div>
    <footer>
        <p>Coding By Will Peck - Sci Management System</p>
    </footer>
    <form><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></form>
    <script type="text/javascript" src="/resources/script/jquery.fittext.js" ></script>
</body>
</html>