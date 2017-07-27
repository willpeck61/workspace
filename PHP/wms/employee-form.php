<article id="newEmployee" class="item3">
    <h3>New / Edit Employee</h3>
    <form class="empform" action="#" method="post" name="formdata">
    	<input type="hidden" name="formdata" value="1" />
        <input type="hidden" name="tbl" value="editemp" />
        <input type="hidden" name="id" id="id" value="" />
        <input type="hidden" name="updating" id="update" value="" />
        <div class="searchbox">
        <ul class="inputform">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve3">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" id="retrieve3" type="text" list="editemp" name="retrieve" placeholder="Name or Postcode"/>
            <datalist id="editemp"></datalist></li>
        </ul>
        </div>
        <div class="formback">
        <ul class="inputform">
		<li class="formline"><label for="EmployeeTitle">Title</label></li>
        <li class="formlineb"><input type="text" list="titlelist" placeholder="Title" id="EmployeeTitle" name="EmployeeTitle" required />
            <datalist id="titlelist">
        		<option value="Mr">Mr</option>
                <option value="Mrs">Mrs</option>
                <option value="Miss">Miss</option>
                <option value="Dr">Dr</option>
                <option value="Prof">Prof</option>
            </datalist></li>
    	<li class="formlinealt"><label for="EmployeeFirstName">First Name</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="EmployeeFirstName" name="EmployeeFirstName" placeholder="First Name" required></li>
    	<li class="formline"><label for="EmployeeLastName">Last Name</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="EmployeeLastName" name="EmployeeLastName" placeholder="Last Name" required></li>
    	<li class="formlinealt"><label for="EmployeeAddress1">Street Address</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="EmployeeAddress1" name="EmployeeAddress1" placeholder="Street Address" required></li>
    	<li class="formline"><label for="EmployeeAddress2">Town</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="EmployeeAddress2" name="EmployeeAddress2" placeholder="Town" required></li>
    	<li class="formlinealt"><label for="EmployeeAddress3">City</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="EmployeeAddress3" name="EmployeeAddress3" placeholder="City" required></li>
    	<li class="formline"><label for="EmployeePostcode">Postcode</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="EmployeePostcode" name="EmployeePostcode" placeholder="Postcode" required></li>
        <li class="formlinealt"><label for="EmployeePhone1">Mobile Phone</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="EmployeePhone1" name="EmployeePhone1" placeholder="Mobile" required /></li>
    	<li class="formline"><label for="EmployeePhone2">Other Phone</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="EmployeePhone2" name="EmployeePhone2" placeholder="Alternate" required /></li>
        <li class="formlinealt"><label for="EmployeeEmail">Email</label></li>
        <li class="formlinealtb"><input class="formfield" type="email" id="EmployeeEmail" name="EmployeeEmail" placeholder="Email Address" required /></li>
    	<li class="formline"><a href="#" id="empreset" class="Buttons">RESET</a></li>
        <li class="formlined"><a href="#" id="empdelete" class="Buttons">DELETE</a></li>
        <li class="formlinebut"><a href="#" id="empsubmit" class="Buttons">ADD RECORD</a></li>
        </ul>
        </div>
	</form>
	</article>