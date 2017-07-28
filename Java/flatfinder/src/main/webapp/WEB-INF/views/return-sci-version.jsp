<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div>
<h1>My Profile</h1>
	<section id="userdetails">
	<h2>Your Details</h2>
		<ul>
			<li><img height=100 width=100 src="#" /></li>
			<li>Name:</li><li><span id="firstname">${user}</span>&nbsp;<span id="lastname"></span></li>
			<li>Address:</li>
			<li><span id="address1"></span></li>
			<li><span id="address2"></span></li>
			<li><span id="address3"></span></li>
			<li><span id="postcode"></span></li>
			<li>Phone:</li>
			<li><span id="phone"></span></li>
			<li>Email:</li>
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