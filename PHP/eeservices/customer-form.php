<article id="newCustomer" class="item1">
	<h3 class="h2style">New / Edit Customer Details</h3>
	<form id="custform" action="#" method="post">
    	<input type="hidden" name="formdata" value="1">
        <input type="hidden" name="tbl" value="editcust">
		<div class="searchbox">
        <ul class="customerform">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve1">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" type="text" id="retrieve1" list="editcust" name="retrieve" placeholder="Name or Postcode"/>
        	<datalist id="editcust"></datalist></li>
        </ul>    
        </div>
        <div class="formback">
        <ul class="customerform">
			<li class="formline"><label for="CustomerTitle">Title</label></li>
        	<li class="formlineb"><input id="CustomerTitle" type="text" list="custtitlelist" placeholder="Title" name="CustomerTitle" required />
            <datalist id="custtitlelist">
            	<option value="Mr">Mr</option>
                <option value="Mrs">Mrs</option>
                <option value="Miss">Miss</option>
                <option value="Dr">Dr</option>
                <option value="Prof">Prof</option>
            </datalist></li>
    	<li class="formlinealt"><label for="CustomerFirstName">First Name</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="CustomerFirstName" name="CustomerFirstName" placeholder="First Name" required /></li>
    	<li class="formline"><label for="CustomerLastName">Last Name</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="CustomerLastName" name="CustomerLastName" placeholder="Last Name" required /></li>
    	<li class="formlinealt"><label for="CustomerAddress1">Street Address</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="CustomerAddress1" name="CustomerAddress1" placeholder="Street Address" required /></li>
    	<li class="formline"><label for="CustomerAddress2">Town</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="CustomerAddress2" name="CustomerAddress2" placeholder="Town" required /></li>
    	<li class="formlinealt"><label for="CustomerAddress3">City</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="CustomerAddress3" name="CustomerAddress3" placeholder="City" required /></li>
    	<li class="formline"><label for="CustomerPostcode">Postcode</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="CustomerPostcode" name="CustomerPostcode" placeholder="Postcode" required /></li>
        <li class="formlinealt"><label for="CustomerPhone1">Mobile Phone</label></li>
        <li class="formlinealtb"><input class="formfield" type="text" id="CustomerPhone1" name="CustomerPhone1" placeholder="Mobile" required /></li>
    	<li class="formline"><label for="CustomerPhone2">Other Phone</label></li>
        <li class="formlineb"><input class="formfield" type="text" id="CustomerPhone2" name="CustomerPhone2" placeholder="Alternate" required /></li>
        <li class="formlinealt"><label for="CustomerEmail">Email</label></li>
        <li class="formlinealtb"><input class="formfield" type="email" id="CustomerEmail" name="CustomerEmail" placeholder="Email Address" required /></li>
        <li class="formline"><a href="#" id="custreset" class="Buttons">RESET</a></li>
        <li class="formlinebut"><a href="#" id="custsubmit" class="Buttons">ADD RECORD</a></li>
        </ul>
        </div>
    </form>
	</article>