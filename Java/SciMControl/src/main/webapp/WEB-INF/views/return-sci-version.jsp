<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<datalist id="scis">
   	<option sci="0" class="sciopt" disabled selected>[select SCI]</option>
	<c:forEach items="${scis}" var="sci">
		<option sci="${sci.id}" class="sciopt" value="${sci.name}">${sci.name}</option>
	</c:forEach>
</datalist>
<datalist id="versions">
	<option class="veropt" disabled selected>[select Version]</option>
	<c:forEach items="${versions}" var="ver">
		<option ver="${ver.sciId}" class="veropt" value="${ver.name}">${ver.name}</option>
	</c:forEach>
</datalist>