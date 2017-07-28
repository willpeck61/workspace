<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<link rel="stylesheet" href="/resources/css/leaflet.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="/resources/script/leaflet.js"></script>

<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">
	<script src="/resources/js/bootstrap.js"></script>


<style>
#map {
	height: 300px;
	width: 100% px;
}
</style>
</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->	
		<div class="row centered-form">
			<div class="col-md-8 col-md-offset-2">
				<c:if test="${listProperty != '[]'}">
					<div id="map">
						<script>
			var loc = [${lats}[0], ${lngs}[0]];
			var map = L.map('map').setView(loc, 12);
			for(var i=0; i<${lngs}.length; i++){
				var loc = [${lats}[i], ${lngs}[i]];
				L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
				}).addTo(map);
				L.marker(loc).addTo(map)
				    .bindPopup("${postcodes.get(i)}")
		    }
		</script>
					</div>
				</c:if>
				<div class="panel panel-default">
					<div class="panel-body">
						<h3 class="col-md-12" style="text-align: left;">
							Search results <a href="/" class="btn btn-md btn-primary"
								style="cursor: pointer; float: right">Back to search</a>
						</h3>
						<c:if test="${listProperty == '[]'}">
							<div class='col-md-12'>

								<h4 style="text-align: center;">Sorry, no results were
									found. Please try again.</h4>
							</div>
						</c:if>

						<div class="panel panel-default">
							<div class="panel-body">

								<c:forEach var="listProperty" items="${listProperty}">
									<div class="col-md-12" style="margin-top: 1%;">
										<div class="col-md-5">
													<a href='/property?id=${listProperty.id}'>
													   <img src=./resources/properties/${listProperty.id}/${listProperty.id}_main.jpg
														    class="img-responsive">
												</a>
										</div>

										<div class="col-md-7">
											<h3 class="row">${listProperty.headline}</h3>


											<h4 class="row">${listProperty.houseNum}
												${listProperty.address1},
												<c:if test="${listProperty.address2 != 'NULL'}">${listProperty.address2},</c:if>
												${listProperty.address3}, ${listProperty.postcode}
											</h4>

											<h4 class="row">
												<div class="col-md-6">
													&pound;${listProperty.pricePerWeek} <small>per week</small>
												</div>
												<div class="col-md-6">
													${listProperty.numberOfRooms} room(s) <br /><small>(${listProperty.numberOfRooms - listProperty.numberOfOccupiedRooms}
														available)</small>
												</div>
											</h4>

											<div class="row">
												<div class="panel panel-default">
													<div class="panel-body">
														<i>${listProperty.description}</i>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-12 rowseparator">
										<div class="col-md-4 col-md-offset-2"
											style="padding: 3px 0 0 0">
											<span><font color="A8A8A8">
													${listProperty.numViews} views </font> </span>
										</div>
										<div class="col-md-2 col-md-offset-3">
											<span> <a href='/property?id=${listProperty.id}'
												class='btn btn-info' role='button'> View Property </a>
											</span>
										</div>
									</div>

								</c:forEach>


								<c:set var="currentURL"
									value="${pageContext.request.queryString}" />
								<c:set var="currentURLsubString"
									value="${fn:substring(currentURL, 0, currentURL.length()-1)}" />

								<c:if test="${listProperty != '[]'}">
									<fmt:parseNumber var="end" type="number" value="${pages}" />
									<ul class="pagination pull-right">
										<c:forEach var="i" begin="1" end="${end}">
											<c:if test="${currentPage == i}">
												<li class="active"><a
													href="/search?${currentURLsubString}${i-1}" id="page${i}">${i}</a></li>
											</c:if>
											<c:if test="${currentPage != i}">
												<li><a href="/search?${currentURLsubString}${i-1}"
													id="page${i}">${i}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>