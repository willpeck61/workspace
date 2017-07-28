<!-- © Coding and Design By William R Peck 2015 -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<article id="menu0">
<div id="scidata"></div>
    <form action="" method="get" id="newChange" name="newChange" modelAttribute="request">
    <ul>
    	<li><label for="scilist" class="formlabel">Select SCI:&nbsp;</label><select name="sci" required="required" id="scilist" onchange="versionList()"/></select></li>
    	<li><label for="verlist" class="formlabel">Select Version:&nbsp;</label><select name="version" required="required" id="verlist" disabled="true"/></select></li>
        <li><label for="description">Problem:&nbsp;</label></li>
      	<li><textarea name="description" rows="3" cols="50" maxlength="100" required="required"></textarea></li>
        <li><label for="solution">Solution:&nbsp;</label></li>
        <li><textarea name="solution" rows="3" cols="50" maxlength="100" required="required"></textarea></li>
        <li><label for="type">Choose Type:&nbsp;</label></li>
        <li>Feature&nbsp;<input type="radio" name="type" value="0" placeholder="Feature"/>&nbsp; Fault&nbsp;<input type="radio" name="type" value="1" placeholder="Fault"/><button type="button" name="send0" onclick="createNewRequest()">Submit</button></li>
    </ul>
	</form>
	<div id="returned"></div>
</article>