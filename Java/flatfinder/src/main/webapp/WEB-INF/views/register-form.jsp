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
<style type="text/css">
	body{
	    padding-top: 40px;
	    padding-bottom: 40px;
	    background-color: #eee;
	    overflow: auto;
	    background-size: cover;
	    background-image: url('http://i.imgur.com/PGVau2S.jpg');
	}
	
	.centered-form{
		margin-top: 60px;
	}
	
	.centered-form .panel{
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
</style>
</head>
<body>
<div class="container">
	<div class="row centered-form">
        <div>
            <div class="panel panel-default">
          	    <div class="panel-body">
            	    <div id="success">
            		    <c:forEach var="suc" items="${success}"> 
            			   <p style="font-size: 20px;">${suc}</p>
            		    </c:forEach>
            	</div>
            	<a href="/user-login">Already a member? Click here to login.</a>
            </div>
            </div>
        	<div class="panel panel-default">
        		<div class="panel-heading">
			    	<h1 class="form-signin-heading text-muted">Flat<b>Finder</b></h1>
			    	<h2>Registration Form</h2>
			    </div>
				<div class="panel-body">
                		<c:if test="${error == true}">
                		<br>
    						<b class="err">Invalid Credentials</b>
						</c:if>
						<c:if test="${logout == true}">
    						<b class="err">You have been logged out.</b>
						</c:if>
                		<c:url value="/addUser" var="addUserUrl"/>
							<form class="form-horizontal" role="form" action="/addUser" method="POST" modelAttribute="user" id="adduser" name="adduser">	
	    						<div class="panel panel-default">
	    							<p class="panel-heading">About You</p>
	    							<div class="panel-body">
			    						<div class="form-group">
		    					    		<label for="role" class="col-sm-2 control-label">Role</label>
		    					    		<div class="col-sm-10">
		                    				<select class="form-control" name="role">                    			
		                    					<option value="3">Searcher</option>
		                    					<option value="2">Landlord</option>
		                    				</select>
		                    				</div>
		                    			</div>
		                    			<div class="form-group">
		                    				<label for="gender" class="col-sm-2 control-label">Gender</label>
		                    				<div class="col-sm-10">
		                        			<select class="form-control" name="gender">
		                        				<option value="0">Male</option>
		                        				<option value="1">Female</option>
		                        			</select>
		                        			</div>
		                        		</div>
		                        		<div class="form-group">
		                        			<label for="status" class="col-sm-2 control-label">Status</label>
		                        			<div class="col-sm-10">
		                        			<select class="form-control" name="status">
		                        				<option value="0">Full-Time Employed</option>
		                        				<option value="1">Post-Graduate Student</option>
		                        				<option value="2">Under-Graduate Student</option>
		                        			</select>
		                        			</div>
		                        		</div>
		                        		<div class="form-group">
		                        			<label for="agegroup" class="col-sm-2 control-label">Age Group</label>
		                        			<div class="col-sm-10">
		                        			<select class="form-control separate" name="agegroup">
		                        				<option value="0">18 - 22 years</option>
		                        				<option value="1">23 - 29 years</option>
		                        				<option value="2">30+</option>
		                        			</select>
		                        			</div>
		                        		</div>
                        			</div>
                        		</div>
                        		<div class="panel panel-default">
	                        		<p class="panel-heading">Your Name</p>
	                        		<div class="panel-body">
		                        		<div class="form-group">
		                        			<label for="title" class="col-sm-2 control-label">Title</label>
		                        			<div class="col-sm-10">
		                        			<select class="form-control" name="title">
		                        				<option>Mr</option>
		                        				<option>Mrs</option>
		                        				<option>Miss</option>
		                        				<option>Dr</option>
		                        			</select>
		                        			</div>
		                        		</div>
		                        		<div class="form-group">
		                        			<label for="firstname" class="col-sm-2 control-label">First Name</label>
		                        			<div class="col-sm-10">
		                        				<input placeholder="First Name" class="form-control" type="text" name="firstname" required />
		                        			</div>
		                        		</div>
		                        		<div class="form-group">
		                        			<label for="lastname" class="col-sm-2 control-label">Last Name</label>
		                        			<div class="col-sm-10">
		                        				<input placeholder="Last Name" class="form-control separate" type="text" name="lastname" required />
		                        			</div>
		                        		</div>
		                        	</div>
		                        </div>
		                        <div class="panel panel-default">		
		                        	<p class="panel-heading">Your Address</p>
		                        	<div class="panel-body">
		                        		<div class="form-group" >
		                        			<label for="address1" class="col-sm-2 control-label">House Name/No</label>
		                        			<div class="col-sm-10">
		                        				<input placeholder="House Name or Number" class="form-control" type="text" name="address1" />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="address2" class="col-sm-2 control-label">Street</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Street Address" class="form-control" type="text" name="address2" />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="address3" class="col-sm-2 control-label">Town</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Town" class="form-control" type="text" name="address3" />
					                    	</div>
					                    </div>
					                    <div class="form-group">
					                        <label for="postcode" class="col-sm-2 control-label">Postcode</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Postcode" class="form-control" type="text" name="postcode" />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="email" class="col-sm-2 control-label">Email</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="email" class="form-control separate" type="email" name="email" />
					                    	</div>
					                    </div>
					                 </div>
					            </div>
					            <div class="panel panel-default">		
		                        	<p class="panel-heading">Workplace / Place of Study</p>
		                        	<div class="panel-body">
		                        		<div class="form-group" >
		                        			<label for="studyplace" class="col-sm-2 control-label">Place of Study</label>
		                        			<div class="col-sm-10">
		                        				<input placeholder="Establishment Name" class="form-control" type="text" name="studyplace" />
					                    	</div>
					                    </div>
					                    <div class="form-group">
					                        <label for="sp-postcode" class="col-sm-2 control-label">Postcode</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Postcode" class="form-control" type="text" name="sp-postcode" />
					                    	</div>
					                    </div>
					                    <div class="form-group" >
		                        			<label for="workplace" class="col-sm-2 control-label">Workplace Name</label>
		                        			<div class="col-sm-10">
		                        				<input placeholder="Establishment Name" class="form-control" type="text" name="workplace" />
					                    	</div>
					                    </div>
					                    <div class="form-group">
					                        <label for="wp-postcode" class="col-sm-2 control-label">Postcode</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Postcode" class="form-control" type="text" name="wp-postcode" />
					                    	</div>
					                    </div>
					                 </div>
					            </div>
					            <div class="panel panel-default">
	                        		<p class="panel-heading">Set Your Login Details</p>
	                        		<div class="panel-body">
					                    <div class="form-group">
					                        <label for="username" class="col-sm-2 control-label">User-name</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Username" title="No special characters allowed" pattern="[a-zA-Z0-9]+" class="form-control" type="text" name="username" />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="p1" class="col-sm-2 control-label">Password</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Password" class="form-control" type="password" name="p1" id="p1" required />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="p2" class="col-sm-2 control-label">Password Again</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Password Again" class="form-control" oninput="check(this)" type="password" id="p2" name="p2" />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="p2" class="col-sm-2 control-label">Secret question</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Enter a secret question" class="form-control" type="text" id="secret" name="secret" />
					                    	</div>
					                    </div>
					                    <div class="form-group">    
					                        <label for="p2" class="col-sm-2 control-label">Answer</label>
					                        <div class="col-sm-10">
					                        	<input placeholder="Enter the answer to the question" class="form-control" type="text" id="answer" name="answer" />
					                    	</div>
					                    </div>
					                    <div class="form-group">
					                    	<div class="col-sm-offset-2 col-sm-10">
					                    		<div class="checkbox">
					                        	<label>
					                      		<input type="checkbox" value="1" name="buddyup" />
					                      		Join <a href="/privacy">Buddy</a> Program? 
					                      		</label>
					                      		</div>	
		                					</div>
		                				</div>
		                				<div class="well">
		                				<h5>Please read our <a href="/terms">Terms of Service&nbsp;</a> and our <a href="/privacy">Privacy Policy&nbsp;</a> before submitting this form.</h5>
		                				</div>
		                			</div>
		                		</div>
                				 <button class="btn btn-success pull-right" type="submit" name="submit">Submit</button>
                        		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                				</form>
                        	</div>
			                <script language='javascript' type='text/javascript'>
								function check(input) {
								    if (input.value != document.getElementById('p1').value) {
								        input.setCustomValidity('The two passwords must match.');
								    } else {
								        // input is valid -- reset the error message
								        input.setCustomValidity('');
								   }
								}
							</script>
            			</div>
            		</div>
        	</div>
		</div>
</body>
</html>