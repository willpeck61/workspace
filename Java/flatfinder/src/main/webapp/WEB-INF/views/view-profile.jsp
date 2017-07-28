<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="x" />
<sec:csrfMetaTags />
<!-- Adds csrf token for to headers. Sent with AJAX requests. -->
<!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
<title>FlatFinder ${USER.login} Profile Page</title>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/script/script.js"></script>
<script type="text/javascript" src="/resources/js/jssor.slider.min.js"></script>

<!-- Property Slider -->
<script>
        jssor_1_slider_init = function() {
            
            var jssor_1_SlideshowTransitions = [
              {$Duration:1200,$Opacity:2}
            ];
            
            var jssor_1_options = {
              $AutoPlay: false,
              $SlideshowOptions: {
                $Class: $JssorSlideshowRunner$,
                $Transitions: jssor_1_SlideshowTransitions,
                $TransitionsOrder: 1
              },
              $ArrowNavigatorOptions: {
                $Class: $JssorArrowNavigator$
              },
              $BulletNavigatorOptions: {
                $Class: $JssorBulletNavigator$
              }
            };
            
            var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);         
            
        };
    </script>
	<link
		href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
		rel='stylesheet' type='text/css' />
	<link
		href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
		rel='stylesheet' type='text/css' />
<!-- BOOTSTRAP STYLE SHEET -->
<link href="resources/css/bootstrap.css" rel="stylesheet" />
<!-- FONT-AWESOME STYLE SHEET FOR BEAUTIFUL ICONS -->
<link href="resources/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="/resources/css/creative.css"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/flatfinder.css" />
<!-- CUSTOM STYLE CSS -->
<style type="text/css">

/* Property Slider */
.jssorb05 {
	position: absolute;
}

.jssorb05 div, .jssorb05 div:hover, .jssorb05 .av {
	position: absolute;
	/* size of bullet elment */
	width: 16px;
	height: 16px;
	background: url('/resources/img/b05.png') no-repeat;
	overflow: hidden;
	cursor: pointer;
}

.jssorb05 div {
	background-position: -7px -7px;
}

.jssorb05 div:hover, .jssorb05 .av:hover {
	background-position: -37px -7px;
}

.jssorb05 .av {
	background-position: -67px -7px;
}

.jssorb05 .dn, .jssorb05 .dn:hover {
	background-position: -97px -7px;
}

.jssora12l, .jssora12r {
	display: block;
	position: absolute;
	/* size of arrow element */
	width: 30px;
	height: 46px;
	cursor: pointer;
	background: url('/resources/img/a12.png') no-repeat;
	overflow: hidden;
}

.jssora12l {
	background-position: -16px -37px;
}

.jssora12r {
	background-position: -75px -37px;
}

.jssora12l:hover {
	background-position: -136px -37px;
}

.jssora12r:hover {
	background-position: -195px -37px;
}

.jssora12l.jssora12ldn {
	background-position: -256px -37px;
}

.jssora12r.jssora12rdn {
	background-position: -315px -37px;
}

.btn-social {
	color: white;
	opacity: 0.8;
}

.btn-social:hover {
	color: white;
	opacity: 1;
	text-decoration: none;
}

.btn-facebook {
	background-color: #3b5998;
}

.btn-twitter {
	background-color: #00aced;
}

.btn-linkedin {
	background-color: #0e76a8;
}

.btn-google {
	background-color: #c32f10;
}

.textContainer {
	vertical-align: middle;
}

.scrollable {
	max-height: 600px;
	overflow-y: auto;
}

.clear-floats {
	clear: both;
}

.vertical-space {
	margin-top: 5%;
}

.btn-file {
	position: relative;
	overflow: hidden;
}

.btn-file input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	min-width: 100%;
	min-height: 100%;
	font-size: 100px;
	text-align: right;
	filter: alpha(opacity = 0);
	opacity: 0;
	outline: none;
	background: white;
	cursor: inherit;
	display: block;
}

.conf {
	display: none;
}

.vcenter {
	display: inline-block;
	width: 100%;
	vertical-align: middle;
	float: none;
}

.scrollable {
	height: auto;
	overflow-y: auto;
}
</style>
</head>
<body>
	<div class="container">
		<jsp:include page="/resources/navBar/navigationBar.jsp" />
		<!-- Includes the navigationBar -->
		<div class="row vertical-space">
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<em>Upload Profile Picture</em>
					</div>
					<div class="panel-body">
						<div class="form-group" id="imgsrc">
							<img
								src="/resources/users/${USER.login}/${USER.login}_profile.jpg"
								class="img-rounded img-responsive center-block" id="profileimg"
								width=250 height=300 />
						</div>
						<form id="uploadform" method="POST" enctype="multipart/form-data"
							action="/upload">
							<input type="hidden" name="name"
								value="users/${USER.login}/${USER.login}_profile.jpg" /> <input
								type="hidden" name="type" value="profile" /> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="form-group pull-left">
										<input type="text" class="form-control" id="filename"
											placeholder="Select a photo..." disabled="disabled" />
									</div>
									<div class="btn-group pull-right">
										<span class="btn btn-default btn-file btn-warning">
											Choose File <input type="file" id="filechooser" name="file">
										</span> <input type="submit" class="btn btn-warning" value="Upload"
											id="upload" disabled="disabled" />
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<form id="updateprofile" method="POST" action="/amend-user">
					<h2>Address Details</h2>
					<div class="panel panel-default">
						<div class="panel-heading">
							<em>Private Details</em>
						</div>
						<div class="panel-body">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="form-group">
								<label class="control-label">My Buddy Status</label>
								<div class="radio">
									<c:if test="${USER.buddyUp == 1}">
										<label class="radio-inline"> <input type="radio"
											class="" name="buddyup" value="0" />In-active
										</label>
										<label class="radio-inline"> <input type="radio"
											name="buddyup" value="1" checked="checked" />Active
										</label>
									</c:if>
									<c:if test="${USER.buddyUp == 0}">
										<label class="radio-inline"> <input type="radio"
											class="" name="buddyup" value="0" />In-active
										</label>
										<label class="radio-inline"> <input type="radio"
											name="buddyup" value="1" checked="checked" />Active
										</label>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">Registered First Name</label> <input
									type="text" class="form-control" name="firstname"
									placeholder="${USER.firstName}">
							</div>
							<div class="form-group">
								<label class="control-label">Registered Last Name</label> <input
									type="text" class="form-control" name="lastname"
									placeholder="${USER.lastName}">
							</div>
							<div class="form-group">
								<label class="control-label">House Name/Number</label> <input
									type="text" class="form-control" name="address1"
									placeholder="${USER.address1}">
							</div>
							<div class="form-group">
								<label class="control-label">Street Address</label> <input
									type="text" class="form-control" name="address2"
									placeholder="${USER.address2}">
							</div>
							<div class="form-group">
								<label class="control-label">Town</label> <input type="text"
									class="form-control" name="address3"
									placeholder="${USER.address3}">
							</div>
							<div class="form-group">
								<label class="control-label">Postcode</label> <input type="text"
									class="form-control" name="postcode"
									placeholder="${USER.postcode}">
							</div>
							<div class="form-group">
								<label class="control-label">Registered Email</label> <input
									type="text" class="form-control" placeholder="${USER.email}">
							</div>
							<button type="button" id="amend"
								class="btn pull-right btn-warning">Amend Details</button>
						</div>
					</div>
				</form>
				<h2>Change Password</h2>
				<form method="POST" id="changepw" action="amend-user">
					<div class="panel panel-default">
						<div class="panel-heading">Change Your Password</div>
						<div class="panel-body">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="form-group">
								<label for="p1" class="control-label">Enter New Password</label>
								<input type="password" name="p1" class="form-control" />
							</div>
							<div class="form-group">
								<label for="p2" class="control-label">Confirm New
									Password</label> <input type="password" name="p2" class="form-control" />
							</div>
							<div class="form-group">
								<a href="#" id="pwchange" class="btn pull-right btn-warning"
									onClick="$('#changepw').submit()">Change Password</a>
							</div>
						</div>
					</div>
				</form>
				<h2>My Property Shortlist</h2>
				<div class="panel panel-default">
					<div class="panel-heading">
						<em>Properties</em>
					</div>
					<c:if test="${fn:length(PROPERTY) == 0}">
						<div class="panel-body">
							<p>You have not expressed interest in any properties.</p>
						</div>
					</c:if>
					<c:if test="${fn:length(PROPERTY) > 0}">
						<div class="panel-body scrollable">
							<c:forEach items="${PROPERTY}" var="props">
								<div class="well">
									<div class="panel panel-default">
										<div class="panel-heading">
											<a href="/property?id=${props.id}"><em>${props.postcode}</em></a>
										</div>
									</div>
									<div class="panel-body">
										<a href="/property?id=${props.id}"><img
											src="/resources/properties/${props.id}/${props.id}_main.jpg"
											alt="Property Image" class="img-responsive" /></a>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>
				<h2>My Feedback</h2>
				<div class="panel panel-default">
					<div class="panel-heading">
						<em>Feedback and Ratings</em>
					</div>
					<c:if test="${fn:length(FEEDBACK) == 0}">
						<div class="panel-body">
							<p>You have not left any feedback.</p>
						</div>
					</c:if>
					<c:if test="${fn:length(FEEDBACK) > 0}">
						<div class="panel-body scrollable">
							<c:forEach items="${RATEDPROPS}" var="rated">
								<c:forEach items="${FEEDBACK}" var="feedback">
									<c:if test="${feedback.propertyId == rated.id}">
										<div class="well">
											<div class="panel panel-default">
												<div class="panel-heading">
													<a href="/property?id=${rated.id}"><em>${rated.postcode}</em></a>
												</div>
											</div>
											<div class="panel-body text-center">
												<a href="/property?id=${rated.id}"><img
													src="/resources/properties/${rated.id}/${rated.id}_main.jpg"
													alt="Property Image" class="img-responsive" /></a>
												<div class="well">
													<p>You rated this property</p>
													<p>${feedback.rating} out of 5</p>
													<p class="text-left">
														<em>"${feedback.comment}"</em>
													</p>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</c:forEach>
						</div>
					</c:if>
				</div>
				<h2>Suggested Buddies</h2>
				<div class="panel panel-default">
					<div class="panel-heading">
						<em>Matched Interests</em>
					</div>
					<div class="panel-body">
						<c:set var="count" value="0" scope="page" />
						<c:forEach items="${MATCHEDUSER}" var="matchedusr">
							<c:if test="${matchedusr.id != USER.id}">
								<c:set var="count" value="${count + 1}" scope="page" />
								<div class="well">
									<div class="panel-body text-center">
										<div class="well text-center">
											<a href="/view-buddy-profile?username=${matchedusr.login}"><img
												alt="${matchedusr.login} Profile Picture"
												src="/resources/users/${matchedusr.login}/${matchedusr.login}_profile.jpg"
												height=100 width=80 /></a>
										</div>
										<div class="form-group text-center">
											<h2>
												<a href="/view-buddy-profile?username=${matchedusr.login}">${matchedusr.login}</a>
											</h2>
											<p>has similar interests..</p>
											<c:forEach items="${MATCHEDINTEREST}" var="matchedint">
												<c:if
													test="${matchedusr.id == matchedint.userId && matchedusr.id != USER.id }">
													<em>${matchedint.interest}</em>
													<br>
												</c:if>
											</c:forEach>
										</div>
										<c:forEach items="${MATCHEDPROFILE}" var="matchedprof">
											<c:if test="${matchedprof.userid == matchedint.userId}">
												<div class="">
													<h3>
														<a href="/view-buddy-profile?username=${matchedusr.login}">${matchedusr.login}</a>
													</h3>
													<c:if test="${matchedprof.firstEdit == 0}">
														<p>
															<a
																href="/view-buddy-profile?username=${matchedusr.login}">Click
																to see my profile.</a>
														</p>
													</c:if>
													<c:if test="${p.firstEdit == 1}">
														<p>
															<a
																href="/view-buddy-profile?username=${matchedusr.login}"><em>"${matchedprof.headline}"</em></a>
														</p>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
										<input type="hidden" value="${matchedusr.login}"
											id="budlogin${matchedusr.login}" />
										<button onClick="sendBuddyReq('${matchedusr.login}')"
											class="btn btn-warning">Send Buddy Request</button>
									</div>
									<div class="clear-floats"></div>
								</div>
							</c:if>
						</c:forEach>
						<c:if test="${fn:length(MATCHEDUSER) == 0 || count == 0}">
							<p>There are no suggested buddies..</p>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<!-- Second Column -->
				<div class="alert alert-info">
					<div class="bg-warning well text-center">
						<h2 class="vcenter">
							You have ${notifCount} <a href="notifications">notification(s)</a>.
						</h2>
						<h3 class="vcenter">Your profile has been viewed
							${PROFILE.hits} time(s).</h3>
					</div>
					<c:if test="${USER.buddyUp == 1}">
						<h4>Your profile is searchable</h4>
						<h5>
							Set Buddy Status to In-active to make profile private.
							</h3>
					</c:if>
					<c:if test="${USER.buddyUp == 0}">
						<h4>Your profile is set to private</h4>
						<h5>
							Set Buddy Status to Active to make profile searchable.
							</h3>
					</c:if>
				</div>
				<div class="form-group col-md-12">
					<a href="${PROFILE.facebookurl}" class="btn btn-social btn-facebook" target="_blank">
						<i class="fa fa-facebook"></i>&nbsp; Facebook
					</a> <a href="${PROFILE.googleplusurl}" class="btn btn-social btn-google" target="_blank">
						<i class="fa fa-google-plus"></i>&nbsp; Google
					</a> <a href="${PROFILE.twitterurl}" class="btn btn-social btn-twitter" target="_blank">
						<i class="fa fa-twitter"></i>&nbsp; Twitter
					</a> <a href="${PROFILE.linkedinurl}" class="btn btn-social btn-linkedin" target="_blank">
						<i class="fa fa-linkedin"></i>&nbsp; Linkedin
					</a>
				</div>
				<div class="clearfix"></div>
				<div class="form-group">
					<div class="panel panel-default">
						<div class="panel-body">
							<h2>My Visible Information</h2>
							<div class="form-group col-md-13">
								<form class="form-horizontal" role="form"
									action="/amend-profile" method="POST" name="amendprofile">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<div class="panel panel-default">
										<div class="panel-heading vcenter">
											<em>Edit Bio</em>
											<button type="button" class="pull-right"
												data-toggle="collapse" data-target="#bio">Show /
												Hide</button>
										</div>
										<div class="panel-body collapse" id="bio">
											<div class="form-group col-md-12">
												<label for="headline" class="col-sm-3 control-label">Headline</label>
												<div class="col-sm-9">
													<textarea class="form-control" name="headline"
														placeholder="${PROFILE.headline}"></textarea>
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="about" class="col-sm-3 control-label">About</label>
												<div class="col-sm-9">
													<textarea class="form-control" name="about"
														placeholder="${PROFILE.about}"></textarea>
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="interests" class="col-sm-3 control-label">Interests</label>
												<div class="col-sm-9">
													<!-- <textarea class="form-control" name="interests" placeholder="${PROFILE.interests}"></textarea> -->
													<a href="#" class="list-group-item disabled"><em>Added
															Interests Appear Here</em></a> <select id="interests" multiple
														class="form-control" id="sel2">
														<c:forEach items="${INTEREST}" var="interest">
															<option value="${interest.interest}">${interest.interest}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="add-interest" class="col-sm-3 control-label">Add
													Interest</label>
												<div class="col-sm-9">
													<input type="text" name="add-interest" id="addInter"
														class="form-control" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<div class="btn-group pull-right">
													<button type="button" id="removeInt"
														class="btn btn-warning" onClick="removeInterest()"
														disabled="disabled">Remove</button>
													<button type="button" class="btn btn-warning"
														onClick="addInterest()">Add</button>
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="studying" class="col-sm-3 control-label">Studying</label>
												<div class="col-sm-9">
													<input class="form-control" type="text" name="studying"
														placeholder="${PROFILE.studying}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="workplace" class="col-sm-3 control-label">Workplace</label>
												<div class="col-sm-9">
													<input class="form-control" type="text" name="workplace"
														placeholder="${PROFILE.workplace}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="wp-postcode" class="col-sm-3 control-label">Workplace
													Postcode</label>
												<div class="col-sm-9">
													<input class="form-control" type="text" name="wp-postcode"
														placeholder="${PROFILE.wpostcode}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="studyplace" class="col-sm-3 control-label">Place
													of Study</label>
												<div class="col-sm-9">
													<input class="form-control" type="text" name="studyplace"
														placeholder="${PROFILE.studyplace}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="sp-postcode" class="col-sm-3 control-label">Study
													Place Postcode</label>
												<div class="col-sm-9">
													<input class="form-control" type="text" name="sp-postcode"
														placeholder="${PROFILE.spostcode}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="studyyear" class="col-sm-3 control-label">Year
													of Study</label>
												<div class="col-sm-9">
													<input class="form-control" type="number" min="0" max="20"
														name="studyyear" placeholder="${PROFILE.studyyear}" />
												</div>
											</div>
											<input type="hidden" id="env"
												value="${PROFILE.quietorlively}" />
											<div class="form-group col-md-12">
												<div class="controls-row">
													<label class="col-sm-3 control-label">Environment</label>
													<div class="radio">
														<label class="col-sm-offset-1 radio-inline"> <input
															id="quiet" type="radio" name="quietorlively" value="0" />Quiet
														</label> <label class="radio-inline"> <input id="lively"
															type="radio" name="quietorlively" value="1" />Lively
														</label> <label class="radio-inline"> <input id="anyenv"
															type="radio" name="quietorlively" value="2" />Any
														</label>
													</div>
												</div>
											</div>
											<input type="hidden" id="same"
												value="${PROFILE.samesexormixed}" />
											<div class="form-group col-md-12">
												<div class="controls-row">
													<label for="samesexormixed" class="col-sm-3 control-label">Share
														With</label>
													<div class="radio">
														<label class="col-sm-offset-1 radio-inline"> <input
															id="samesex" type="radio" name="samesexormixed" value="0" />Same
															Sex
														</label> <label class="radio-inline"> <input id="mixed"
															type="radio" name="samesexormixed" value="1" />Mixed
														</label> <label class="radio-inline"> <input id="anysame"
															type="radio" name="samesexormixed" value="2" />Any
														</label>
													</div>
												</div>
											</div>
											<input type="hidden" id="smok" value="${PROFILE.smoking}" />
											<div class="form-group col-md-12">
												<div class="controls-row">
													<label for="smoking" class="col-sm-3 control-label">Smoking</label>
													<div class="radio">
														<label class="col-sm-offset-1 radio-inline"> <input
															id="smoky" type="radio" name="smoking" value="1" />Yes
														</label> <label class="radio-inline"> <input id="smokn"
															type="radio" name="smoking" value="0" />No
														</label>
													</div>
												</div>
											</div>
											<input type="hidden" id="pets" value="${PROFILE.pets}" />
											<div class="form-group col-md-12">
												<div class="controls-row">
													<label for="pets" class="col-sm-3 control-label">Pets</label>
													<div class="radio">
														<label class="col-sm-offset-1 radio-inline"> <input
															id="pety" type="radio" name="pets" value="1" />Yes
														</label> <label class="radio-inline"> <input id="petn"
															type="radio" name="pets" value="0" />No
														</label>
													</div>
												</div>
											</div>
											<input type="hidden" id="ens" value="${PROFILE.ensuite}" />
											<div class="form-group col-md-12">
												<div class="controls-row">
													<label for="ensuite" class="col-sm-3 control-label">Ensuite</label>
													<div class="radio">
														<label class="col-sm-offset-1 radio-inline"> <input
															id="ensy" type="radio" name="ensuite" value="1" />Yes
														</label> <label class="radio-inline"> <input id="ensn"
															type="radio" name="ensuite" value="0" />No
														</label>
													</div>
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="maxsharers" class="col-sm-3 control-label">Max
													Number of House Mates</label>
												<div class="col-sm-9">
													<input class="form-control" type="number" name="maxsharers"
														min="0" max="10" placeholder="${PROFILE.maxsharers}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="facebookurl" class="col-sm-3 control-label">Facebook
													URL</label>
												<div class="col-sm-9">
													<input class="form-control" type="url" name="facebookurl"
														placeholder="${PROFILE.facebookurl}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="twitterurl" class="col-sm-3 control-label">Twitter
													URL</label>
												<div class="col-sm-9">
													<input class="form-control" type="url" name="twitterurl"
														placeholder="${PROFILE.twitterurl}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="googleplusurl" class="col-sm-3 control-label">Google
													Plus URL</label>
												<div class="col-sm-9">
													<input class="form-control" type="url" name="googleplusurl"
														placeholder="${PROFILE.googleplusurl}" />
												</div>
											</div>
											<div class="form-group col-md-12">
												<label for="linkedinurl" class="col-sm-3 control-label">LinkedIn
													URL</label>
												<div class="col-sm-9">
													<input class="form-control" type="url" name="linkedinurl"
														placeholder="${PROFILE.linkedinurl}" />
												</div>
											</div>
											<button id="update-profile" type="submit"
												class="btn pull-right btn-warning">Submit Updates</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="form-group">
					<div class="panel panel-default">
						<div class="panel-body">
							<h2>Find Buddies</h2>
							<div id="userlst" class="form-group">
								<div class="panel panel-default">
									<div class="panel-heading">
										<em>Find by user-name or interest...</em>
									</div>
									<div class="panel-body">
										<input list="userlist" name="userlist" id="inputusrlst"
											class="search-query form-control" placeholder="Search..." />
										<datalist id="userlist"></datalist>
										<br>
										<button class="btn btn-danger pull-right" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
										<c:if test="${fn:length(BUDS) == 0}">
											<p>No Buddies Are Currently Registered</p>
										</c:if>
										<div id="noresult" class="conf">
											<c:if test="${fn:length(BUDDIES) == 0}">
												<p>No results found.</p>
											</c:if>
										</div>
										<div class="col-md-13">
											<div id="selected-buddy"></div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading vcenter">
										<em>List of all potential buddies...</em>
										<button type="button" class="pull-right"
											data-toggle="collapse" data-target="#potential">Show
											/ Hide</button>
									</div>
									<div class="collapse in" id="potential">
										<c:forEach items="${BUDS}" var="bud">
											<c:if
												test="${(fn:length(BUDS) == 0) || (bud.login == USER.login && fn:length(BUDS) == 1)}">
												<div class="col-md-13 panel-body collapse" id="potential">
													<p>No buddies are currently registered</p>
												</div>
											</c:if>
											<!-- Check that a pending request does not exist-->
											<c:set var="flag" value="0" scope="page" />
											<c:forEach items="${NOTCONFIRM}" var="req">
												<c:if
													test="${(req.initialiserId == USER.id || req.responderId == USER.id) && (req.initialiserId == bud.id || req.responderId == bud.id)}">
													<c:set var="flag" value="1" scope="page" />
												</c:if>
											</c:forEach>
											<c:if test="${bud.login != USER.login && flag == 0}">
												<div class="col-md-13 panel-body">
													<div class="col-md-3">
														<a href="/view-buddy-profile?username=${bud.login}"><img
															src="./resources/users/${bud.login}/${bud.login}_profile.jpg"
															height=100 width=80 /></a>
													</div>
													<div class="col-md-8 textContainer">
														<h3>
															<a href="/view-buddy-profile?username=${bud.login}">${bud.login}</a>
														</h3>
														<c:forEach items="${PROFILELIST}" var="p">
															<c:if test="${(p.userid == bud.id)}">
																<c:if test="${p.firstEdit == 0}">
																	<p>
																		<a href="/view-buddy-profile?username=${bud.login}">Click
																			to see my profile.</a>
																	</p>
																</c:if>
																<c:if test="${p.firstEdit == 1}">
																	<p>
																		<a href="/view-buddy-profile?username=${bud.login}"><em>"${p.headline}"</em></a>
																	</p>
																</c:if>
															</c:if>
														</c:forEach>
													</div>
													<input type="hidden" value="${bud.login}"
														id="budlogin${bud.login}" />
													<button onClick="sendBuddyReq('${bud.login}')"
														class="btn pull-right btn-warning">Send Buddy
														Request</button>
												</div>
												<div class="clear-floats"></div>
											</c:if>
										</c:forEach>
									</div>
									<fmt:parseNumber var="end" type="number" value="${PAGES}" />
									<ul class="pagination pull-right">
										<c:forEach var="i" begin="1" end="${end}">
											<c:if test="${CURRENT == i}">
												<li class="active"><a href="view-profile?page=${i}"
													id="page${i}">${i}</a></li>
											</c:if>
											<c:if test="${CURRENT != i}">
												<li><a href="view-profile?page=${i}" id="page${i}">${i}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="panel panel-default">
						<div class="panel-body">
							<h2>My Buddies</h2>
							<div class="form-group">
								<div class="panel panel-default">
									<div class="panel-heading vcenter">
										<em>List of my buddies...</em>
										<button type="button" class="pull-right"
											data-toggle="collapse" data-target="#mybuddies">Show
											/ Hide</button>
									</div>
									<c:set var="count" value="0" scope="page" />

									<c:if test="${fn:length(MYBUDS) > 0}">
										<div class="col-md-13 panel-body collapse" id="mybuddies">
											<c:forEach items="${MYBUDS}" var="mybud">
												<c:forEach items="${ALLMYBUDS}" var="all">
													<c:if
														test="${(all.initialiserId == mybud.id || all.responderId == mybud.id) && (all.confirmed == 1)}">
														<c:set var="count" value="${count + 1}" scope="page" />

														<div class="col-md-3">
															<a href="/view-buddy-profile?username=${mybud.login}"><img
																src="./resources/users/${mybud.login}/${mybud.login}_profile.jpg"
																height=100 width=80 /></a>
														</div>
														<div class="col-md-8 textContainer">
															<h3>
																<a href="/view-buddy-profile?username=${mybud.login}">${mybud.login}</a>
															</h3>
															<c:forEach items="${PROFILELIST}" var="p">
																<c:if
																	test="${(p.firstEdit == 0) && (mybud.id == p.userid)}">
																	<p>
																		<a href="/view-buddy-profile?username=${mybud.login}">Click
																			to see my profile.</a>
																	</p>
																</c:if>
																<c:if
																	test="${(p.firstEdit == 1) && (mybud.id == p.userid)}">
																	<p>
																		<a href="/view-buddy-profile?username=${mybud.login}"><em>"${p.headline}"</em></a>
																	</p>
																</c:if>
															</c:forEach>
														</div>
														<div class="btn-group pull-right">
															<button type="button"
																onClick="cremoveBuddy('${mybud.login}')"
																value="${mybud.login}" class="btn btn-warning">Remove
																Buddy</button>
															<button type="button"
																onClick="cshowMsg('${mybud.login}')"
																class="btn btn-warning">Send Message</button>
														</div>
														<div class="clearfix"></div>
														<div id="cconfirm-${mybud.login}"
															class="conf panel panel-default">
															<div class="panel-body">
																<p class="text-center">Are you sure?</p>
																<div class="clearfix"></div>
																<a href="/remove-buddy?username=${mybud.login}"
																	class="btn btn-warning pull-right">Ok</a>
																<button type="button"
																	onClick="hideConf('${mybud.login}')"
																	class="btn btn-warning pull-left">Cancel</button>
															</div>
														</div>
														<div id="cmessage-${mybud.login}" class="conf well">
															<form action="/sendInitMessage" class="form-horizontal"
																method="post" modelattribute="user"
																id="csend${mylogin.login}" name="sendInitMessage">
																<div class="form-group">
																	<label for="userToSend" class="control-label">Send
																		To</label> <input id="cusr${mybud.login}" type="text"
																		name="userToSend" value="${mybud.login}"
																		class="form-control" disabled="disabled"
																		placeholder="${mybud.login}" />
																</div>
																<div class="form-group">
																	<label for="messageContent" class="control-label">Message</label>
																	<textarea id="ctxt${mybud.login}" name="messageContent"
																		placeholder="your message" class="form-control"></textarea>
																</div>
																<div class="form-group">
																	<button type="button"
																		onClick="csendMessage('${mybud.login}','csend${mylogin.login}')"
																		class="btn btn-warning pull-right">Send</button>
																	<button type="button"
																		onClick="chideMsg('${mybud.login}')"
																		class="btn btn-warning pull-left">Cancel</button>
																</div>
																<div class="form-group">
																	<p id="cmsg-${mybud.login}"></p>
																</div>

															</form>
														</div>
													</c:if>
												</c:forEach>
												<div class="clear-floats"></div>
											</c:forEach>
										</div>
									</c:if>

									<c:if test="${count == 0  || fn:length(MYBUDS) == 0}">
										<div class="col-md-13 panel-body collapse" id="mybuddies">
											<p>:( You don't have any buddies..</p>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="form-group">
					<div class="panel panel-default">
						<div class="panel-body">
							<h2>Pending Requests</h2>
							<div class="form-group">
								<div class="panel panel-default">
									<c:set var="count" value="0" scope="page" />
									<div class="panel-heading vcenter">
										<em>List of my buddy requests...</em>
										<button type="button" class="pull-right"
											data-toggle="collapse" data-target="#requests">Show
											/ Hide</button>
									</div>
									<c:if test="${fn:length(MYBUDS) > 0}">
										<div class="col-md-13 panel-body collapse in" id="requests">
											<c:forEach items="${MYBUDS}" var="mybud">
												<c:forEach items="${ALLMYBUDS}" var="all">
													<c:if
														test="${(all.initialiserId == mybud.id || all.responderId == mybud.id) && (all.confirmed == 0)}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<div class="col-md-3">
															<a href="/view-buddy-profile?username=${mybud.login}"><img
																src="./resources/users/${mybud.login}/${mybud.login}_profile.jpg"
																height=100 width=80 /></a>
														</div>
														<div class="col-md-8 textContainer">
															<h3>
																<a href="/view-buddy-profile?username=${mybud.login}">${mybud.login}</a>
															</h3>
															<c:forEach items="${PROFILELIST}" var="p">
																<c:if test="${p.userid == mybud.id}">
																	<c:if test="${p.firstEdit == 0}">
																		<p>
																			<a href="/view-buddy-profile?username=${mybud.login}">Click
																				to see my profile.</a>
																		</p>
																	</c:if>
																	<c:if test="${p.firstEdit == 1}">
																		<p>
																			<a href="/view-buddy-profile?username=${mybud.login}"><em>"${p.headline}"</em></a>
																		</p>
																	</c:if>
																</c:if>
															</c:forEach>
														</div>
														<div class="btn-group pull-right">
															<button type="button"
																onClick="removeBuddy('${mybud.login}')"
																value="${mybud.login}" class="btn btn-warning">Remove
																Buddy</button>
															<button type="button" onClick="showMsg('${mybud.login}')"
																class="btn btn-warning">Send Message</button>
															<c:forEach items="${NOTIF}" var="notif">
																<c:if
																	test="${(notif.forUser == USER.login && notif.requestId == all.id)}">
																	<a
																		href="/action-buddy-request?notifyid=${notif.id}&requestid=${notif.requestId}&response=accept"
																		class="btn btn-warning">Accept</a>
																</c:if>
															</c:forEach>
														</div>
														<div class="clearfix"></div>
														<div id="confirm-${mybud.login}"
															class="conf panel panel-default">
															<div class="panel-body">
																<p class="text-center">Are you sure?</p>
																<div class="clearfix"></div>
																<c:forEach items="${NOTIF}" var="notif">
																	<c:if test="${notif.forUser == mybud.login}">
																		<a
																			href="/remove-buddy?username=${mybud.login}&notifyid=${notif.id}"
																			class="btn btn-warning pull-right">Ok</a>
																	</c:if>
																</c:forEach>
																<button type="button"
																	onClick="hideConf('${mybud.login}')"
																	class="btn btn-warning pull-left">Cancel</button>
															</div>
														</div>
														<div id="message-${mybud.login}" class="conf well">
															<form action="/sendInitMessage" class="form-horizontal"
																method="post" modelattribute="user"
																id="send${mylogin.login}" name="sendInitMessage">
																<div class="form-group">
																	<label for="userToSend" class="control-label">Send
																		To</label> <input id="usr${mybud.login}" type="text"
																		name="userToSend" value="${mybud.login}"
																		class="form-control" disabled="disabled"
																		placeholder="${mybud.login}" />
																</div>
																<div class="form-group">
																	<label for="messageContent" class="control-label">Message</label>
																	<textarea id="txt${mybud.login}" name="messageContent"
																		placeholder="your message" class="form-control"></textarea>
																</div>
																<div class="form-group">
																	<button type="button"
																		onClick="sendMessage('${mybud.login}','send${mylogin.login}')"
																		class="btn btn-warning pull-right">Send</button>
																	<button type="button"
																		onClick="hideMsg('${mybud.login}')"
																		class="btn btn-warning pull-left">Cancel</button>
																</div>
																<div class="form-group">
																	<p id="msg-${mybud.login}"></p>
																</div>

															</form>
														</div>

													</c:if>
													<div class="clear-floats"></div>
												</c:forEach>
												<div class="clear-floats"></div>
											</c:forEach>
										</div>
									</c:if>
									<c:if test="${fn:length(MYBUDS) == 0 || count == 0}">
										<div class="col-md-13 panel-body collapse" id="requests">
											<p>You don't have any pending requests..</p>
										</div>
									</c:if>
									<input type="hidden" value="" id="remlog" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-body">
						<h2>History</h2>
						<div class="form-group">
							<div class="panel panel-default">
								<c:set var="count" value="0" scope="page" />
								<div class="panel-heading vcenter">
									<em>List of my recently viewed properties ...</em>
									<button type="button" class="pull-right" data-toggle="collapse"
										data-target="#propertyHistory">Show / Hide</button>
								</div>

								<div class="col-md-12 panel-body collapse" id="propertyHistory">
									<c:if test="${fn:length(USERHISTORY) == 0}">
										<p>You haven't viewed any properties yet.</p>
									</c:if>
									<div id="jssor_1"
										style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 600px; height: 300%; overflow: hidden; visibility: hidden;">
										<!-- Loading Screen -->
										<div data-u="loading"
											style="position: absolute; top: 0px; left: 0px;">
											<div
												style="filter: alpha(opacity = 70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
											<div
												style="position: absolute; display: block; background: url('/resources/img/loading.gif') no-repeat center center; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
										</div>
										<div data-u="slides"
											style="cursor: default; position: relative; top: 0px; left: 0px; width: 600px; height: 300px; overflow: hidden;">
											<c:if test="${fn:length(USERHISTORY) > 0}">
												<c:forEach items="${USERHISTORY}" var="userHistory">
													<div class="">
														<div class="col-md-12" style="margin-top: 5%;">
															<div class="col-md-5">
																<img
																	src=./resources/properties/${userHistory.id}/${userHistory.id}_main.jpg
																	width="150px" height="150px"> <a
																	href='/property?id=${userHistory.id}'
																	style="margin: 3% 0 0 5%;" class='btn btn-info '
																	role='button'> View Property</a>
															</div>
															<div class="col-md-7">
																<h3 class="row">${userHistory.headline}</h3>

																<h4 class="row">${userHistory.houseNum}
																	${userHistory.address1},
																	<c:if test="${userHistory.address2 != 'NULL'}">${userHistory.address2},</c:if>
																	${userHistory.address3}, ${userHistory.postcode}
																</h4>
																<h4 class="row">
																	&pound;${userHistory.pricePerWeek} <small>per
																		week.</small> ${userHistory.numberOfRooms} room(s) <small>(${userHistory.numberOfRooms - userHistory.numberOfOccupiedRooms}
																		available)</small>
																</h4>


															</div>
														</div>
													</div>
												</c:forEach>
											</c:if>
										</div>
										<!-- Bullet Navigator -->
										<div data-u="navigator" class="jssorb05"
											style="bottom: 16px; right: 16px;" data-autocenter="1">
											<!-- bullet navigator item prototype -->
											<div data-u="prototype" style="width: 16px; height: 16px;"></div>
										</div>
										<!-- Arrow Navigator -->
										<span data-u="arrowleft" class="jssora12l"
											style="top: 0px; left: 0px; width: 30px; height: 46px;"
											data-autocenter="2"></span> <span data-u="arrowright"
											class="jssora12r"
											style="top: 0px; right: 0px; width: 30px; height: 46px;"
											data-autocenter="2"></span>
									</div>
									<script>
        jssor_1_slider_init();
    </script>

								</div>

							</div>
							<input type="hidden" value="" id="remlog" />
						</div>

					</div>
				</div>
				<!-- ROW END -->
			</div>
			<!-- CONATINER END -->


			<!-- REQUIRED SCRIPTS FILES -->
			<!-- CORE JQUERY FILE -->
			<script src="resources/js/jquery-1.11.1.js"></script>
			<!-- REQUIRED BOOTSTRAP SCRIPTS -->
			<script src="resources/js/bootstrap.js"></script>
</body>

</html>
