<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty BUDDY.login}">
	<div class="clear-floats"></div>
	<div class="col-md-3">
		<a><img src="./resources/users/${BUDDY.login}/${BUDDY.login}_profile.jpg" height=100 width=80 /></a>
	</div>
	<div class="col-md-8 textContainer">
		<h3><a href="/view-buddy-profile?username=${BUDDY.login}"><em>User:</em>&nbsp;${BUDDY.login}</a></h3>
		<p><a href="/view-buddy-profile?username=${BUDDY.login}"><em>${PROFILE.headline}</em></a></p>
	</div>
	<button id="buddy-request" onClick="sendBuddyReq('${BUDDY.login}')" class="btn pull-right btn-warning">Send Buddy Request</button>
</c:if>
	<c:forEach items="${BUDDIES}" var="buds">
		<div class="clear-floats"></div>
		<div class="col-md-3">
			<a><img src="./resources/users/${buds.login}/${buds.login}_profile.jpg" height=100 width=80 /></a>
		</div>
		<div class="col-md-8 textContainer">
			<h3><a href="/view-buddy-profile?username=${buds.login}"><em>User:</em>&nbsp;${buds.login}</a></h3>
			<c:forEach items="${PROFILES}" var="profs">
				<c:if test="${profs.userid == buds.id}">
					<p><a href="/view-buddy-profile?username=${buds.login}"><em>${profs.headline}</em></a></p>
				</c:if>
			</c:forEach>
		</div>
		<button id="buddy-request" onClick="sendBuddyReq('${buds.login}')" class="btn pull-right btn-warning">Send Buddy Request</button>
		<div class="clear-floats"></div>
	</c:forEach>
