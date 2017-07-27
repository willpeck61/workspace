 <article id="newSupplier" class="item4">
    <h3>New / Edit Supplier</h3>
    <form id="supform" action="#" method="post" name="formdata">
    	<input type="hidden" name="formdata" value="1" />
        <input type="hidden" name="tbl" value="editsup" />
        <div class="searchbox">
        <ul class="inputform">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve4">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" id="retrieve4" type="text" list="editsup" name="retrieve" placeholder="Supplier Name or Contact"/>
            <datalist id="editsup"></datalist></li>
        </ul>
        </div>
        <div class="formback">
        <ul class="inputform">
    		<li class="formline"><label for="SupplierName">Supplier Name</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierName" name="SupplierName" Placeholder="Supplier Name" required /></li>
    		<li class="formlinealt"><label for="SupplierContactName">Contact Name</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierContactName" placeholder="Contact Name" name="SupplierContactName" required /></li>
    		<li class="formline"><label for="SupplierEmail">Email</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierEmail" name="SupplierEmail" placeholder="Email" required /></li>
            <li class="formlinealt"><label for="SupplierPhone1">Phone 1</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierPhone1" name="SupplierPhone1" placeholder="Main Phone" required /></li>
            <li class="formline"><label for="SupplierPhone2">Phone 2</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierPhone2" name="SupplierPhone2" placeholder="Alternative" required /></li>
            <li class="formlinealt"><label for="SupplierWebsite">Website</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierWebsite" name="SupplierWebsite" placeholder="Website Address" required /></li>
            <li class="formline"><label for="SupplierAddress1">Street Address</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierAddress1" name="SupplierAddress1" placeholder="Street Address" required /></li>
            <li class="formlinealt"><label for="SupplierAddress2">Town</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierAddress2" name="SupplierAddress2" placeholder="Town" required /></li>
            <li class="formline"><label for="SupplierAddress3">City</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierAddress3" name="SupplierAddress3" placeholder="City" required /></li>
            <li class="formlinealt"><label for="SupplierPostcode">Postcode</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierPostcode" name="SupplierPostcode" placeholder="Postcode" required /></li>
            <li class="formline"><label for="SupplierAccountNo">Acc. Number</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierAccountNo" name="SupplierAccountNo" placeholder="Account Number" required /></li>
            <li class="formline"><a href="#" id="supreset" class="Buttons">RESET</a></li>
        	<li class="formlinebut"><a href="#" id="supsubmit" class="Buttons">ADD RECORD</a></li>
        </ul>
        </div>
    </form>
	</article>