<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<table id="newrequest">
<thead>
	<tr>
		<th>Field</th>
		<th>Value</th>
	</tr>
</thead>
<tbody>
	<tr>
		<td class="field">Request ID</td>
		<td class="value">${req.id}</td>
	</tr>
	<tr>
		<td class="field">SCI Artifact</td>
		<td class="value">${sciname}</td>
	</tr>
	<tr>
		<td class="field">Version</td>
		<td class="value">${vername}</td>
	</tr>
	<tr>
		<td class="field">Description</td>
		<td class="value">${description}</td>
	</tr>
	<tr>
		<td class="field">Solution</td>
		<td class="value">${solution}</td>
	</tr>
</tbody>
</table>