<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div>
<h1>My Profile</h1>
	<section id="userdetails">
	<h2>Your Details</h2>
		<ul>
			<li><img height=100 width=100 src="#" /></li>
			<li>Name:</li><li>${USER.firstName}&nbsp;${USER.lastName}</li>
			<li>Address:</li>
			<li><span id="address1">${USER.address1}</span></li>
			<li><span id="address2">${USER.address2}</span></li>
			<li><span id="address3">${USER.address3}</span></li>
			<li><span id="postcode">${USER.postcode}</span></li>
			<li><span id="phone"></span></li>
			<li>Email:&nbsp;${USER.email}</li>
			<li><span id="email"></span></li>
		</ul>
	</section>
	<section id="buddylist">
	<h2>My Buddy Ups</h2>
		<ul>
			<li><span></span></li>
		</ul>
	</section>
</div>