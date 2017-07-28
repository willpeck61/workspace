<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>FlatFinder</title>
	
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="resources/font-awesome/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="resources/css/animate.min.css" type="text/css">
    <link rel="stylesheet" href="resources/css/creative.css" type="text/css">

</head>

<body id="page-top">

    <header>
        <div class="header-content">
            <div class="header-content-inner">
                <h1>Welcome to FlatFinder</h1>
                <hr>
                <p>FlatFinder is a web application that aims to connect people looking for accomodation to Landlords in an easy to 
				use environment. Landlords are able to "upload" their properties with detailed profiles, users can then find these 
				properties through the in-depth search system that will find the property you desire!</p>
                <a href="login-form.jsp" class="btn btn-primary btn-xl page-scroll">Login</a>
				<a href="register-form.jsp" class="btn btn-primary btn-xl page-scroll">Sign Up</a>
            </div>
        </div>
    </header>
	
	<section id="services">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Features of FlatFinder</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-diamond wow bounceIn text-primary"></i>
                        <h3>Property Search</h3>
                        <p class="text-muted">Search through a vast amount of property listings.</p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-paper-plane wow bounceIn text-primary" data-wow-delay=".1s"></i>
                        <h3>Buddy Up System</h3>
                        <p class="text-muted">Meet other people looking for similar accomodation.</p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-newspaper-o wow bounceIn text-primary" data-wow-delay=".2s"></i>
                        <h3>Instant Messaging</h3>
                        <p class="text-muted">Instantly message other users and landlords on the website!</p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-heart wow bounceIn text-primary" data-wow-delay=".3s"></i>
                        <h3>Express Interest</h3>
                        <p class="text-muted">Express your interest in properties that take your fancy!</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
	
    <section class="bg-primary" id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">We've got everything you need!</h2>
                    <hr class="light">
                    <p class="text-faded">FlatFinder has a large amount of features available to its users, sign up today and start looking for your future accomodation!</p>
	                <c:if test="${error == true}">
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
	                <c:url value="/user-registration" var="userReg" />
	                <div class="regFont">
	                	<a href="/test-users">Populate Test Users</a><br>
	               		<a href="${userReg}">Click here to register.</a>
	               	</div>
	                <a href="#services" class="page-scroll btn btn-default btn-xl">Sign Up!</a>
                </div>
            </div>
        </div>
    </section>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/jquery.fittext.js"></script>
    <script src="js/wow.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/creative.js"></script>

</body>

</html>
