<article id="newVehicle" class="item2">
    <h3>New / Edit Vehicle</h3>
    <form class="vehform" action="#" method="post" name="formdata" >
    	<input type="hidden" name="formdata" value="1" />
        <input type="hidden" name="tbl" value="editveh" />
        <input type="hidden" name="id" id="id" value="" />
        <input type="hidden" name="updating" id="update" value="" />
    	<div class="searchbox">
    	<ul class="vehicleform">
        	<li class="formlinealt"><label class="retrievetext" for="retrieve2">Search</label></li>
        	<li class="formlinealtc"><input class="searchfield" id="retrieve2" type="text" list="editveh" name="retrieve" placeholder="Make or Model"/>
            <datalist id="editveh"></datalist></li>
        </ul>
        </div>
        <div class="formback">
        <ul class="inputform">
    		<li class="formline"><label for="VehicleMake">Manufacturer</label></li>
            <li class="formlineb"><input class="formfield" type="text" id="VehicleMake" name="VehicleMake" placeholder="Make" required /></li>
    		<li class="formlinealt"><label for="VehicleModel">Model</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" id="VehicleModel" name="VehicleModel" placeholder="Model e.g. Mondeo" required /></li>
    		<li class="formline"><label for="VehicleType">Type</label></li>
            <li class="formlineb"><input type="text" class="formfield" id="VehicleType" name="VehicleType" placeholder="Type e.g. Ghia X" required /></li>
            <li class="formlinealt"><label for="VehicleEngineSize">Engine Size</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" name="VehicleEngineSize" id="VehicleEngineSize" placeholder="Engine Size e.g. 2.5" required /></li>
            <li class="formline"><label for="VehicleEngineType">Engine Type</label></li>
            <li class="formlineb"><input class="formfield" type="text" name="VehicleEngineType" placeholder="Engine Type e.g. V6" id="VehicleEngineType" required /></li>
            <li class="formlinealt"><label for="VehicleFuelType">Fuel Type</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" name="VehicleFuelType" id="VehicleFuelType" placeholder="Fuel Type e.g. Petrol" required /></li>
        </form>
        	<li class="formlinealt"></li>
            <li class="formlinealtb"></li>
        <form id="ownerform" action="#" method="post" name="formdata"><!-- -->
        	<input type="hidden" name="formdata" value="1" />
            <input type="hidden" name="tbl" value="addowner" />
            <input type="hidden" name="vehid" value="" />
        	<li class="formline"><label for="VehicleLogCustomerID">Vehicle Owner</label></li>
            <li class="formlineb"><input class="formfield" type="text" list="addowner" name="VehicleLogCustomerID" placeholder="Select Customer" id="VehicleLogCustomerID" />
            	<datalist id="addowner"></datalist></li>
            <li class="formlinealt"><label for="VehicleLogRegistration">Registration</label></li>
            <li class="formlinealtb"><input class="formfield" type="text" name="VehicleLogRegistration" id="VehicleLogRegistration" placeholder="Reg No" /></li>
            <li class="formline"><label for="VehicleLogColour">Colour</label></li>
            <li class="formlineb"><input class="formfield" type="text" name="VehicleLogColour" placeholder="Colour" id="VehicleLogColour" /></li>
        </form>
            <li class="formline"><a href="#" id="vehreset" class="Buttons">RESET</a></li>
        	<li class="formlined"><a href="#" id="vehdelete" class="Buttons">DELETE</a></li>
       		<li class="formlinebut"><a href="#" id="vehsubmit" class="Buttons">ADD RECORD</a></li>
        
        </ul>
    
    		
	</div>
    </article>