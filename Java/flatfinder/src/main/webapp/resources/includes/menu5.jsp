<!-- © Coding and Design By William R Peck 2015 -->
<article id="menu5">
    <form action="" method="get" name="user" modelAttribute="user" id="form5">
    <ul>
    	<li><label for="user">Username:&nbsp;</label><input id="user" placeholder="user name" type="text" name="user" list="users" required="required" /></li>
    </ul>
    <ul>
        <li><label for="password">Password:&nbsp;</label><input id="p1" type="password" placeholder="password" name="password" required="required" /></li>
        <li><label for="confpass">Confirm Password:&nbsp;</label><input id="p2" type="password" placeholder="password" name="confpass" required="required" onchange="checkPword(form5)"/></li>
    </ul>   
    <ul>
        <li><label for="type">Choose Role:&nbsp;</label></li>
        <li>User&nbsp;<input type="radio" name="role" value="USER" placeholder="Feature" />&nbsp;Admin&nbsp;<input type="radio" name="role" value="ADMIN" placeholder="Fault"/><button id="sub5" type="button" onclick="createNewUser()"  name="send0">Submit</button></li>
        <li><p id="message"></p></li>
    </ul>
	</form>
	<form action="" method="get" name="selectusr" modelAttribute="user" id="form5b">
		<ul>
    		<li><label for="usrselect">Existing User:</label></li>
    		<li>
    			<select class="usrlist" name="userid" required></select>
    		</li>
    		<li><p id="testdata"></p></li>
    		<li><button id="usrdelete" type="button" onclick="deleteUser()">Delete</button>&nbsp;<button id="updusr" type="button" onclick="updateUserList()">Refresh</button></li>
    	</ul>
	</form>
</article>