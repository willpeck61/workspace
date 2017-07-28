<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="review">
<form>
    <table id="row">
        <thead>
            <th>Sci</th>
            <th>Version</th>
            <th>Dev</th>
            <th>Type</th>
            <th>Description</th>
            <th>Solution</th>
            <th>Due</th>
            <th>Complete</th>
        </thead>
        <tbody>
        <c:forEach items="${assigned}" var="assigned">
            <tr id="${assigned.id}" class="review">
                <td id="sci">
        		<c:set var="i" value="0" scope="page" />
        		<c:forEach items="${scilst}" var="scilst">
            		<c:if test="${scilst.id == assigned.sciId && i<1}">
	            		${scilst.name}
	            		<c:set var="i" value="${i+1}" scope="page" />
            		</c:if>
            	</c:forEach></td>
            	<td id="version">
            	<c:set var="i" value="0" scope="page" />
            	<c:forEach items="${verlst}" var="verlst">
            		<c:if test="${verlst.id == assigned.versionId && i<1}">
            			${verlst.name}
            			<c:set var="i" value="${i+1}" scope="page" />
            		</c:if>
            	</c:forEach></td>
                <td id="dev">${assigned.assignTo}</td>
                <td id="type">${assigned.type}</td>
                <td id="description">${assigned.reqDesc}</td>
                <td id="solution">${assigned.reqSolution}</td>
                <td id="duedate">${assigned.dueDate}</td>
                <td id="complete"><input id="completed" type="radio" name="completed" value="${assigned.id}" data-value="${assigned.assignTo}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p id="setdate">
    	<label for="setdate">Date Completed (dd-mm-yy):&nbsp;</label>
    	<input id="datecompleted" type="text" name="setdate" />&nbsp;
    	<button id="btn" type="button" onclick="completeRequest()">Submit</button>
    </p>
</form>
</div>
