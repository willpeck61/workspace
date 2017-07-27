<article id="newVehicle" class="item2">
    <h3>New / Edit Vehicle</h3>
    <form id="vehform" action="#" method="post" name="formdata" >
    	<input type="hidden" name="formdata" value="1" />
        <input type="hidden" name="tbl" value="editveh" />
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
            <li class="formline"><a href="#" id="vehreset" class="Buttons">RESET</a></li>
        	<li class="formlinebut"><a href="#" id="vehsubmit" class="Buttons">ADD RECORD</a></li>
        </ul>
        </div>
    </form>
	</article>