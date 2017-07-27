<!doctype html>
<html>
<head>

<meta charset="utf-8">
<title>EE Services :: Workshop Management System</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="script.js"></script>
</head>
<header>
<img id="logo" alt="EE Services Logo" src="logo.png" width="184" height="77">
<h1>EE Services</h1>
</header>
<section id="leftmenu">
<nav>
	<ul>
		<li><a href="#" id="item1">>>New / Edit Customer</a></li>
    	<li><a href="#" id="item2">>>New Vehicle</a></li>
    	<li><a href="#" id="item3">>>New Employee</a></li>
    	<li><a href="#" id="item4">>>New Supplier</a></li>
    	<li><a href="#" id="item5">>>Order System</a></li>
        <ul>
        	<li><a href="#" id="item6">>>New Order</a></li>
            <li><a href="#" id="item7">>>Order Enquiry</a></li>
        </ul>
    </ul>
</nav>
</section>
<section id="main">
	<article id="welcome" class="item0">
	<h2>WELCOME!!</h2>
	</article>
	<article id="newCustomer" class="item1">
	<h2 class="h2style">New / Edit Customer Details</h2>
	<form id="custform" action="" method="post">
    	<input type="hidden" name="formdata">
		<ul class="customerform">
        <div class="searchbox">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" type="text" list="editcustomer" name="retrieve" placeholder="Name or Postcode"/>
        	<datalist id="editcustomer"></datalist></li>
        </div>
        <div class="formback">
			<li class="formline"><label for="custTitle">Title</label></li>
        	<li class="formlineb"><input id="customertitle" type="text" list="custtitlelist" placeholder="Title" name="CustomerTitle" required />
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
        </div>
        </ul>
    </form>
	<span id="custscript"></span>
	</article>
	<article id="newVehicle" class="item2">
    <h2>New Vehicle</h2>
    <form id="vehform" action="" method="post" name="formdata" >
    	<ul class="vehicleform">
        <div class="searchbox">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" type="text" list="editveh" name="retrieve" placeholder="Make or Model"/>
            <datalist id="editveh"></datalist></li>
        </div>
        <div class="formback">
    		<li class="formline"><label for="VehicleMake">Manufacturer</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="VehicleMake" name="VehicleMake" placeholder="Make" required /></li>
    		<li class="formlinealt"><label for="VehicleModel">Model</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="VehicleModel" name="VehicleModel" placeholder="Model e.g. Mondeo" required /></li>
    		<li class="formline"><label for "VehicleType">Type</label></li>
            <li class="formlineb"><input type="text" class="formfield" id="VehicleType" name="VehicleType" placeholder="Type e.g. Ghia X" required /></li>
            <li class="formlinealt"><label for "VehicleEngineSize">Engine Size</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" name="VehicleEngineSize" id="VehicleEngineSize" placeholder="Engine Size e.g. 2.5" required /></li>
            <li class="formline"><label for "VehicleEngineType">Engine Type</label></li>
            <li class="formlineb"><input class="formfield" type="text" name="VehicleEngineType" placeholder="Engine Type e.g. V6" id="VehicleEngineType" required /></li>
            <li class="formlinealt"><label for "VehicleFuelType">Fuel Type</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" name="VehicleFuelType" id="VehicleFuelType" placeholder="Fuel Type e.g. Petrol" required /></li>
            <li class="formline"><a href="#" id="vehreset" class="Buttons">RESET</a></li>
        	<li class="formlinebut"><a href="#" id="vehsubmit" class="Buttons">ADD RECORD</a></li>
        </ul>
        </div>
    </form>
	</article>
    <article id="newEmployee" class="item3">
    <h2>New Employee</h2>
    <form id="empform" action="" method="post" name="formdata">
		<ul class="inputform">
        <div class="searchbox">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" type="text" list="editveh" name="retrieve" placeholder="Name or Postcode"/>
            <datalist id="editemp"></datalist></li>
        </div>
        <div class="formback">
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
        <li class="formlinebut"><a href="#" id="empsubmit" class="Buttons">ADD RECORD</a></li>
        </div>
		</ul>
	</form>
	</article>
    <article id="newSupplier" class="item4">
    <h2>New Supplier</h2>
    <form id="supform" action="" method="post" name="formdata">
    	<ul class="inputform">
        <div class="searchbox">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" type="text" list="editveh" name="retrieve" placeholder="Supplier Name or Contact"/>
            <datalist id="editsup"></datalist></li>
        </div>
        <div class="formback">
    		<li class="formline"><label for="SupplierName">Supplier Name</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierName" name="SupplierName" Placeholder="Supplier Name" required /></li>
    		<li class="formlinealt"><label for="SupplierContactName">Contact Name</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierContactName" placeholder="Contact Name" name="SupplierContactName" required /></li>
    		<li class="formline"><label for "SupplierEmail">Email</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierEmail" name="SupplierEmail" placeholder="Email" required /></li>
            <li class="formlinealt"><label for "SupplierPhone1">Phone 1</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierPhone1" name="SupplierPhone1" placeholder="Main Phone" required /></li>
            <li class="formline"><label for "SupplierPhone2">Phone 2</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierPhone2" name="SupplierPhone2" placeholder="Alternative" required /></li>
            <li class="formlinealt"><label for "SupplierWebsite">Website</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierWebsite" name="SupplierWebsite" placeholder="Website Address" required /></li>
            <li class="formline"><label for "SupplierAddress1">Street Address</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierAddress1" name="SupplierAddress1" placeholder="Street Address" required /></li>
            <li class="formlinealt"><label for "SupplierAddress2">Town</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierAddress2" name="SupplierAddress2" placeholder="Town" required /></li>
            <li class="formline"><label for "SupplierAddress3">City</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierAddress3" name="SupplierAddress3" placeholder="City" required /></li>
            <li class="formlinealt"><label for "SupplierPostcode">Postcode</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="SupplierPostcode" name="SupplierPostcode" placeholder="Postcode" required /></li>
            <li class="formline"><label for "SupplierAccountNo">Acc. Number</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="SupplierAccountNo" name="SupplierAccountNo" placeholder="Account Number" required /></li>
            <li class="formline"><a href="#" id="supreset" class="Buttons">RESET</a></li>
        	<li class="formlinebut"><a href="#" id="supsubmit" class="Buttons">ADD RECORD</a></li>
        </div>
        </ul>
    </form>
	</article>
</section>
<footer>
<h1><i><b>W</b>orkshop <b>M</b>anagement <b>S</b>ystem 1.0</i></h1>
<h4>Coded by Will Peck</h4>

</footer>
</body>
</html>
