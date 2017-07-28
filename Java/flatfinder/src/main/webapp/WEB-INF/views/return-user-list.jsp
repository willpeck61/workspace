<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${buds}" var="bud">
   <option value="${bud.getLogin()}" userid="${bud.getId()}">
</c:forEach>
