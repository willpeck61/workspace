<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Form</title>
<script type="text/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" />
<script type="text/javascript">
$(document).ready(function(){
	
	$("#code").keyup(function(){
		var strlen = this.value.length;
		var code = this.value;
		console.log(strlen);
		if (strlen == 8) {
			$.ajax({
				url : "/validate-code",
				type : "POST",
				data : { 
					"code" : code,
					"${_csrf.parameterName}" : "${_csrf.token}"
				},
				success : function(result){
					$("#codemsg").html(result);
					if ($("#valid").length) {
						$("#code").attr("disabled","true");
					}
				}
			});
		}
	});
	
	$("#submitform").click(function(event){
		event.preventDefault();
		var validate = 1;
        $(".form-control").each(function() {
          if ($(this).val() === "") {
            validate = 0;
          }
        });
    if (validate == 1) {
    	  var formdata = $("#regform").serialize();
    	  $.ajax({
    		  	url : "/process-registration",
    		    type : "POST",
    		    data : formdata,
    		    success : function(result){
    		    	$("#regmsg").html("<p>" + result + "</p><p><a href='/' id='goto'>Click here to login.</a></p>");
    		    	$("html, body").animate({
    	           scrollTop: $("#regmsg").offset().top
    	        }, 2000);
    		    }
    	  });
    } else {
    	 alert("Please complete all form fields.");
    }
	});
});
</script>
</head>
<body>
<div class="col-md-6">
  <h1>Registration Form</h1>
  <div class="panel panel-default">
  <div class="panel-heading">
  <h2>Enter Details</h2>
  <a href="/">Back to Login Page</a>
  </div>
  <div class="panel-body">
  <div id="codemsg"></div>
  <form action="/process-registration" method="POST" id="regform">
  <div class="form-group">
  <label for="code">Enter security code</label>
	  <input type="text" name="securecode" class="form-control" placeholder="Secure Code" id="code"/>
	  </div>
	<div class="form-group">
  <label for="title">Title</label>
	  <select id="title" class="form-control" name="title">
	         <option>Mr</option>
	         <option>Mrs</option>
	         <option>Miss</option>
	       </select>
	       </div>
	<div class="form-group">
  <label for="firstname">First name</label>
	  <input type="text" id="firstname" class="form-control" name="firstname" placeholder="First Name" />
	  </div>
	<div class="form-group">
  <label for="lastname">Last name</label>  
	  <input type="text" class="form-control" name="lastname" placeholder="Last Name" />
	  </div>
	<div class="form-group">
  <label for="addr1">House name or number</label>  
	  <input type="text" id="addr1" class="form-control" name="address1" placeholder="House Name / Number" />
	  </div>
	<div class="form-group">
  <label for="addr2">Street address</label>  
	  <input type="text" id="addr2" class="form-control" name="address2" placeholder="Street Address" />
	</div>
	<div class="form-group">
  <label for="addr3">Town</label>  
	  <input type="text" id="addr3" class="form-control" name="address3" placeholder="Postal Town" />
	</div>
	<div class="form-group">
  <label for="postcode">Postcode</label>  
	  <input type="text" id="postcode" class="form-control"  name="postcode" placeholder="Postcode" />
	  </div>
	<div class="form-group">
  <label for="email">Email address</label>  
	  <input type="email" id="email" class="form-control" name="email" placeholder="Email Address" />
	  </div>
	<div class="form-group">
  <label for="username">Username</label>    
	  <input type="text" class="form-control" name="username" placeholder="Choose a Username" id="username" />
	  </div>
	<div class="form-group">
  <label for="password">Password</label>  
	  <input type="password" class="form-control" name="password" placeholder="Choose a Password" id="password" />
	</div>
	<div class="form-group">
  <label for="validatepw">Re-enter password</label>  
	  <input type="password" class="form-control" placeholder="Choose a Password" id="validatepw" />
	  </div>
	<div class="form-group">  
	  <button class="btn btn-default" id="submitform">Register Now</button>
	</div>
	<div class="form-group">  
  <div id="regmsg"></div>
  </div>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  </form>
  </div>
  </div>
</div>
</body>
</html>