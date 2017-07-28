<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <sec:csrfMetaTags />
    <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->

    
    <title>FlatFinder User Profile Page</title>
    <script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	<script src="/resources/js/bootstrap.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/creative.css"
	type="text/css" />
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css' />
<link
	href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
	rel='stylesheet' type='text/css' />
	<script type="text/javascript" src="/resources/script/script.js" ></script>
    <script src="/resources/js/bootstrap.js"></script>
    <!-- BOOTSTRAP STYLE SHEET -->
    <link href="resources/css/bootstrap.css" rel="stylesheet" />
    <!-- FONT-AWESOME STYLE SHEET FOR BEAUTIFUL ICONS -->
    <link href="resources/css/font-awesome.css" rel="stylesheet" />
     <!-- CUSTOM STYLE CSS -->
     <link rel="stylesheet" href="/resources/css/creative.css"
	type="text/css" />
     <link rel="stylesheet" href="/resources/css/flatfinder.css" />
    <style type="text/css">
    .conf {
		display: none;
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
		    filter: alpha(opacity=0);
		    opacity: 0;
		    outline: none;
		    background: white;
		    cursor: inherit;
		    display: block;
		}
		.vertical-space {
        	margin-top: 70px;
        }
        .vertical-space-sm {
        	margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
    <jsp:include page="/resources/navBar/navigationBar.jsp" /> <!-- Includes the navigationBar -->   
         <div class="row vertical-space">
         	<div class="col-md-12 vertical-space-sm">
         			<script>
    					document.write('<a class="btn btn-info" href="' + document.referrer + '">Go Back </a>');
					</script> 
			</div>
             <div class="col-md-4">
                	<div class="panel panel-default">
                	<c:if test="${BUDDY.status == 2}">
                		<div class="panel-heading text-center">${BUDDY.login} :: Undergraduate</div>
                	</c:if>
                	<c:if test="${BUDDY.status == 1}">
                		<div class="panel-heading text-center">${BUDDY.login} :: Postgraduate</div>
                	</c:if>
                	<c:if test="${BUDDY.status == 0}">
                		<div class="panel-heading text-center">${BUDDY.login} :: Full-time Employed</div>
                	</c:if>
                		<div class="panel-body">
	               			<div class="form-group" id="imgsrc">
	                    		<img src="/resources/users/${BUDDY.login}/${BUDDY.login}_profile.jpg" class="img-rounded img-responsiveclass center-block" id="profileimg" width=250 height=300/>
	                		</div>
	                		<div class="form-group">
	                			<p class="text-center"><em>${BUDDY.firstName} ${BUDDY.lastName}</em></p>
                   			</div>
                    	</div>
                    </div>
                    <a href="/user/report?username=${BUDDY.login}" class="btn btn-danger">Report User</a>
                    <div class="pull-right">
						<button type="button" onClick="showMsg('${BUDDY.login}')"
							class="btn btn-warning">Send Message</button>

					</div>
                    <div id="message-${BUDDY.login}" class="conf well">
						<form action="/sendInitMessage" class="form-horizontal"
							method="post" modelattribute="user"
							id="send${BUDDY.login}" name="sendInitMessage">
							<div class="form-group">
								<label for="userToSend" class="control-label">Send
									To</label> <input id="usr${BUDDY.login}" type="text"
									name="userToSend" value="${BUDDY.login}"
									class="form-control" disabled="disabled"
									placeholder="${BUDDY.login}" />
							</div>
							<div class="form-group">
								<label for="messageContent" class="control-label">Message</label>
								<textarea id="txt${BUDDY.login}" name="messageContent"
									placeholder="your message" class="form-control"></textarea>
							</div>
							<div class="form-group">
								<button type="button"
									onClick="sendMessage('${BUDDY.login}','send${BUDDY.login}')"
									class="btn btn-warning pull-right">Send</button>
								<button type="button"
									onClick="hideMsg('${BUDDY.login}')"
									class="btn btn-warning pull-left">Cancel</button>
							</div>
							<div class="form-group">
								<p id="msg-${BUDDY.login}"></p>
							</div>

						</form>
					</div>
                </div>
                <div class="col-md-8">
                    <div class="alert alert-info">
                        <h2>${BUDDY.login}'s Profile</h2>
                        <c:if test="${PROFILE.firstEdit == 1}"> 
                        	<p><em>"${PROFILE.headline}"</em></p>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <a href="${PROFILE.facebookurl}" class="btn btn-social btn-facebook">
                            <i class="fa fa-facebook"></i>&nbsp; Facebook</a>
                        <a href="${PROFILE.googleplusurl}" class="btn btn-social btn-google">
                            <i class="fa fa-google-plus"></i>&nbsp; Google</a>
                        <a href="${PROFILE.twitterurl}" class="btn btn-social btn-twitter">
                            <i class="fa fa-twitter"></i>&nbsp; Twitter </a>
                        <a href="${PROFILE.linkedinurl}" class="btn btn-social btn-linkedin">
                            <i class="fa fa-linkedin"></i>&nbsp; Linkedin </a>
                    </div>
                    <div class="clearfix"></div>
                    <div class="form-group">
                    	<div class="panel panel-default">
                    		<div class="panel-heading">About</div>
                    		<div class="panel-body">
                    			<c:if test="${PROFILE.firstEdit == 0}">
                    				<h3>About</h3>
	                    			<p>No data added yet</p>
	                    			<h3>Interests</h3>
	                    			<p>No data added yet</p>
	                    			<h3>Subject Studying or Occupation</h3>
	                    			<p>No data added yet</p>
	                    			<h3>Place of Work</h3>
	                    			<p>No data added yet</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.firstEdit == 1}">
		                    		<h3>About</h3>
		                    		<p>${PROFILE.about}</p>
		                    		<h3>Interests</h3>
		                    		<p>${PROFILE.interests}</p>
		                    		<h3>Subject Studying or Occupation</h3>
		                    		<p>${PROFILE.studying}</p>
		                    		<h3>Place of Work</h3>
		                    		<p>${PROFILE.workplace}</p>
	                    		</c:if>
	                    		<h3>Year of Study</h3>
	                    		<c:if test="${PROFILE.studyyear == 0}">
	                    			<p>No data or not applicable</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.studyyear > 0}">
	                    			<p>Studying in year ${PROFILE.studyyear}</p>
	                    		</c:if>
	                    	</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">Living Requirements</div>	
							<div class="panel-body">                    
	                    		<h3>Location / Atmosphere</h3>
	                    		<c:if test="${PROFILE.quietorlively == 0}">
	                    			<p>Property with a quiet atmosphere</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.quietorlively == 1}">
	                    			<p>Property with a lively atmosphere</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.quietorlively == 2}">
	                    			<p>No preference</p>
	                    		</c:if>
	                    		<h3>Sharing With</h3>
	                    		<c:if test="${PROFILE.samesexormixed == 0}">
	                    			<p>Same-sex sharing property is required</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.samesexormixed == 1}">
	                    			<p>Mixed sex sharing property is required</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.samesexormixed== 2}">
	                    			<p>No preference</p>
	                    		</c:if>
	                    		<h3>Smoking</h3>
	                    		<c:if test="${PROFILE.smoking == 0}">
	                    			<p>Non-Smoking property is required</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.smoking == 1}">
	                    			<p>Smoking allowed property is required</p>
	                    		</c:if>
	                    		<h3>Pets</h3>
	                    		<c:if test="${PROFILE.pets == 0}">
	                    			<p>No pets allowed property is required</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.smoking == 1}">
	                    			<p>Pets allowed property is required</p>
	                    		</c:if>
	                    		<h3>Ensuite</h3>
	                    		<c:if test="${PROFILE.ensuite == 0}">
	                    			<p>Ensuite property is not required</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.ensuite == 1}">
	                    			<p>Ensuite allowed property is required</p>
	                    		</c:if>
	                    		<h3>Max No. of Sharers</h3>
	                    		<c:if test="${PROFILE.maxsharers == 0}">
	                    			<p>No data or not applicable</p>
	                    		</c:if>
	                    		<c:if test="${PROFILE.maxsharers > 0}">
	                    			<p>Looking to share with a maximum of ${PROFILE.maxsharers} people.</p>
	                    		</c:if>
                    		</div>
                    	</div>
                    </div>
                    <h2>${BUDDY.login}'s Buddies</h2>
                   	  <div class="panel panel-default">
						<div class="panel-heading"><em>list of my buddies..</em></div>
						<c:if test="${fn:length(MYBUDS) == 0}">
							<div class="col-md-13 panel-body scrollable">
								<p>:( ${BUDDY.login} doesn't have any buddies..</p>
							</div>
						</c:if>
						<c:if test="${fn:length(MYBUDS) > 0}">
							<c:forEach items="${MYBUDS}" var="mybud">
								<div class="col-md-13 panel-body scrollable">
									<div class="col-md-3">
										<a><img src="./resources/users/${mybud.login}/${mybud.login}_profile.jpg" height=100 width=80 /></a>
									</div>
									<div class="col-md-8 textContainer">
										<h3><a href="#"><em>User:</em>&nbsp;${mybud.login}</a></h3>
										<c:forEach items="${PROFILELIST}" var="p">
											<c:if test="${p.userid == mybud.id}">
												<c:if test="${p.firstEdit == 0}">
													<p><a href="/view-buddy-profile?username=${mybud.login}">Click to see my profile.</a></p>
												</c:if>
												<c:if test="${p.firstEdit == 1}">
													<p><a href="/view-buddy-profile?username=${mybud.login}"><em>"${p.headline}"</em></a></p>
												</c:if>
											</c:if>
										</c:forEach>
									</div>
								</div>
								<div class="clear-floats"></div>
							</c:forEach>
						</c:if>
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
