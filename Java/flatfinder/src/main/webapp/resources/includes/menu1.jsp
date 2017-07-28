<!-- © Coding and Design By William R Peck 2015 -->
<article id="menu1">
    <form action="#" method="post" name="edit">
    <ul>
    	<li><label for="version">Select Request:&nbsp;</label><input type="text" name="requestid" /></li>
        <datalist id="requests">
        	<option value="Item 1">
            <option value="Item 2">
        </datalist>
        <li><label for="problem">Problem:&nbsp;</label></li>
      	<li><textarea name="problem" rows="3" cols="50" maxlength="100" required="required"></textarea></li>
        <li><label for="problem">Solution:&nbsp;</label></li>
        <li><textarea name="solution" rows="3" cols="50" maxlength="100" required="required"></textarea></li>
        <li><label for="type">Choose Type:&nbsp;</label></li>
        <li>Feature&nbsp;<input type="radio" name="type" value="feature" placeholder="Feature"/>&nbsp Fault&nbsp;<input type="radio" name="type" value="fault" placeholder="Fault"/><button type="button" name="send0">Submit</button></li>
    </ul>
	</form>
</article>