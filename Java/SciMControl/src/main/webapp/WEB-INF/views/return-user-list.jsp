<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<option id="dummyusr" value="0" selected>[select]</option>
<c:forEach items="${users}" var="user">
    <option value="<c:out value='${user.getId()}'></c:out>"><c:out value="${user.getLogin()}"></c:out>
</c:forEach> 