<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<article id="art4">
<form action="" method="get" name="updateRequests">
	<table>
    	<thead>
        	<th>SCI</th>
            <th>Version</th>
            <th>Dev</th>
            <th>Type</th>
            <th>Description</th>
            <th>Solution</th>
            <th>Assign</th>
            <th>Priority</th>
            <th>Pending</th>
            <th>Approve</th>
            <th>Reject</th>
        </thead>
        <tbody id="row">
        <c:forEach items="${pending}" var="pending">
        	<tr class="${pending.id}">
        		<td id="rreqno">
        		<c:set var="i" value="0" scope="page" />
        		<c:forEach items="${scilist}" var="scilist">
            		<c:if test="${scilist.id == pending.sciId && i<1}">
	            		${scilist.name}
	            		<c:set var="i" value="${i+1}" scope="page" />
            		</c:if>
            	</c:forEach></td>
            	<td id="rverno">
            	<c:set var="i" value="0" scope="page" />
            	<c:forEach items="${verlist}" var="verlist">
            		<c:if test="${verlist.id == pending.versionId && i<1}">
            			${verlist.name}
            			<c:set var="i" value="${i+1}" scope="page" />
            		</c:if>
            	</c:forEach></td>
            	<td id="rsubmitby">${pending.createdBy}</td>
            	<td id="rtype">${pending.type}</td>
            	<td id="rdescription">${pending.reqDesc}</td>
            	<td id="rsolution">${pending.reqSolution}</td>
                <td id="assignto"><select data-value="${pending.id}" id="usr_${pending.id}" class="usrlist"></select></td>
                <td id="priority"><select id="prior" class="prior_${pending.id}" name="priority" disabled>
                	<option id="dummyprior" value="0" selected>[select]</option>
                	<option value="high">High</option>
                	<option value="medium">Medium</option>
                	<option value="low">Low</option>
                </select>
            	<td id="pending"><input data-value="${pending.id}" id="pend" type="radio" name="status_${pending.id}" value="0" /></td>
            	<td id="approve"><input data-value="${pending.id}" id="accept" type="radio" name="status_${pending.id}" value="1" disabled/></td>
            	<td id="reject"><input data-value="${pending.id}" id="rejected" type="radio" name="status_${pending.id}" value="4" /></td>
        	</tr>
        </c:forEach>
        </tbody>
    </table>
    </form>
    <p class="reason"><label for="reason">Reject Reason:&nbsp;</label><textarea id="reason" name="reason"></textarea>&nbsp;
    	<button id="rejectcancel" type="button" >Cancel</button>&nbsp;
    	<button id="rejectbtn" type="button" onclick="rejectRequest()">Submit</button>
    </p>
    <p class="duedate"><label for="duedate">Deadline (dd-mm-yy):&nbsp;</label><input id="duedate" type="text" name="duedate" />
    	<button id="acceptcancel" type="button" >Cancel</button>&nbsp;
    	<button id="acceptbtn" type="button" onclick="acceptRequest()">Submit</button>
    </p>
    <button id="hideshow" type="button" onclick="hideRejected()">Hide Rejected</button>
	</article>