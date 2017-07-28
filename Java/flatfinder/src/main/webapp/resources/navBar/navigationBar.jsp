<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- naviate -->
<style>
.navbar-custom {
    background-color: #f05f40;
    font-color: #000000;
	opacity: 0.8;
}
</style>

<nav class="navbar navbar-default navbar-fixed-top navbar-custom" role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">Flat<b>Finder</b></a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="/">Home</a></li>
				
				<li><a href="/message">Messenger</a></li>
				<li><a href="/privacy">Privacy Policy</a></li>
				<li><a href="/terms">Terms of Service</a></li>
				<sec:authorize access="hasRole('LANDLORD')">
					<li><a href="/landlord-dashboard">Landlord Dashboard</a></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ADMIN')">
					<li><a href="/admin-dashboard">Administrator Dashboard</a></li>
				</sec:authorize>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
          		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${pageContext.request.userPrincipal.name} <span class="caret"></span></a>
          		<ul class="dropdown-menu">
          		<sec:authorize access="hasRole('SEARCHER')">
           			 <li><a href="/view-profile">My Profile</a></li>
           		</sec:authorize>
        			<li><a href="/notifications">My Notifications <span class="badge"><jsp:include page="/notificationcount" /></span> </a></li>
        			<li><a href="/inbox">My Inbox</a></li>
            		<li role="separator" class="divider"></li>
            		<li><a href="/logout">Logout</a></li>
          		</ul>
        </li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container -->
</nav>
