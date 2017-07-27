<article class="item5">
<h3>Order Form</h3>
	<section class="orderforms">
		<section class="neworderemployee">
    		<form class="ordempform" action="#" method="post" name="formdata">
    			<input type="hidden" name="formdata" value="1" />
        		<input type="hidden" name="tbl" value="editemp" />
    		<div class="ordercombos">
        	<ul class="inputform">
        		<li class="formlinealt"><label class="ordercombotxt" for="retrieve3">Employee</label></li>
        		<li class="formlinealtc"><input class="ordersearch" id="retrieve3" type="text" list="editemp" name="retrieve" placeholder="Name or Postcode"/>
            	<datalist id="editemp"></datalist></li>
        	</ul>
    		</div>
    		</form>
    	</section>
		<section class="newordercustomer">
    		<div class="ordercombos">
    			<form class="ordcustform" action="#" method="post">
    				<input type="hidden" name="formdata" value="1">
    				<input type="hidden" name="tbl" value="editcust">
        	<ul class="customerform">
        		<li class="formlinealt"><label class="ordercombotxt" for="retrieve1">Customer</label></li>
        		<li class="formlinealtc"><input class="ordersearch" type="text" id="retrieve1" list="editcust" name="retrieve" placeholder="Name or Postcode"/>
        		<datalist id="editcust"></datalist></li>
        	</ul>
        		</form>
        	<ul class="ordcustomer">
    			<li class="ordtitle" id="ordtitle"></li>
        		<li class="ordname" id="ordfirst"></li>
        		<li class="ordname" id="ordlast"></li>
            	<li class="ordaddress" id="ordstreet"></li>
            	<li class="ordaddress" id="ordtown"></li>
            	<li class="ordaddress" id="ordcity"></li>
            	<li class="ordaddress" id="ordpostcode"></li>
            	<li class="ordaddress" id="ordphone"></li>
           	 	<li class="ordaddress" id="ordemail"></li>
   			</ul>
    		</div>
    	</section>
        <section class="newordervehicle">
        <form class="ordvehform" action="#" method="post" name="formdata" >
            <input type="hidden" name="formdata" value="1" />
            <input type="hidden" name="tbl" value="editveh" />
        <div class="ordercombos">
            <ul class="vehicleform">
                <li class="formlinealt"><label class="ordercombotxt" for="retrieve2">Vehicle</label></li>
                <li class="formlinealtc"><input class="ordersearch" id="retrieve2" type="text" list="editveh" name="retrieve" placeholder="Make or Model"/>
                <datalist id="editveh"></datalist></li>
                <li class="formlinealt"><label class="ordercombotxt" for="retrieve3">Registration</label></li>
                <li class="formlinealtc"><input class="ordersearch" id="retrieve3" type="text" list="editreg" name="retrieve" placeholder="Registration"/>
                <datalist id="editreg"></datalist></li>
            </ul>
            <ul class="ordvehicle">
                <li class="ordtitle"></li>
                <li class="ordname" id="vehmake"></li>
                <li class="ordname" id="vehmodel"></li>
                <li class="ordname" id="vehtype"></li>
                <li class="ordaddress" id="vehfuel"></li>
            </ul>
        </div>
        </form>
        </section>
        </section>
       	<form action="#" method="post" id="tblorder">
        <input type="hidden" value="" id="OrderCustomerID" name="OrderCustomerID" />
        <input type="hidden" value="" id="OrderEmployeeID" name="OrderEmployeeID" />
        <input type="hidden" value="" id="OrderVehicleID" name="OrderVehicleID" />
        <input type="hidden" value="tblorder" name="tbl"  />
        <input type="hidden" value="tblorder" name="formdata"  />
        </form>
        <section class="orderlines">
        <h3>Jobs</h3>
    	<table id="orderjobs">
                <thead>
                <tr>
                <th><b>Date</b></td>
                <th><b>Job Type</b></td>
                <th><b>Time Slot</b></td>
                <th><b>Bay No</b></td>
                <th><b>Mechanic</b></td>
                <th><b>Price</b></td>
                </tr>
                </thead>
                <tbody>
                <tr>
  				<td id="orderjobdate"></td>
                <td id="orderjobtype"></td>
          		<td id="orderjobtime"></td>
               	<td id="orderjobbay"></td>
             	<td id="orderjobmech"></td>
                <td id="orderjobprice"></td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                	<td>TOTAL</td><td colspan="5" id="orderjobtot"></td>
                </tr>
                </tfoot>
        </table>
        <h3>Parts</h3>
        <table id="orderparts">
                <thead>
                <tr>
                <th><b>Category</b></td>
                <th><b>Part</b></td>
                <th><b>Quantity</b></td>
                <th><b>Price</b></td>
                <th><b>Total</b></td>
                </tr>
                </thead>
                <tbody>
                <tr>
  				<td id="orderpartcat"></td>		
                <td id="orderparttype"></td>
          		<td id="orderpartqty"></td>
               	<td id="orderpartprice"></td>
             	<td id="orderparttotal"></td>
                </tr>
                </tbody>
                <tfoot>
                	<tr>
                    	<td>TOTAL</td><td colspan="4" id="orderparttot"></td>
                    </tr>
                </tfoot>
        </table>
    </section>
    <section class="orderjobs">
            <div class="tabs_holder">
            <ul>
                <li><a href="#jobs">Jobs</a></li>
                <li class="tab_selected"><a href="#parts">Parts</a></li>
            </ul>
                <div class="content_holder">
                    <div id="jobs">
                    <form id="jobform" action="#" method="post">
                    <table class="inputtbl">
                    <thead>
                    <tr>
                    <th><label for="TimeSlotDate">Date</label></td>
                    <th><label for="JobTypeName">Job Type</label></td>
                    <th><label for="OperatingHoursTime">Time Slot</label></td>
                    <th><label for="BayType">Bay No</label></td>
                    <th><label for="Mechanic">Mechanic</label></td>
                    <th><label for="JobPrice">Price</label></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                    <td><input type="date" id="TimeSlotDate" name="TimeSlotDate" required /></td>
                    <td><input type="text" id="JobTypeName" list="JobTypes" name="JobTypes" required /></td>
                            <datalist id="JobTypes"></datalist>
                    <td><input type="text" list="TimeSlots" id="OperatingHoursTime" name="OperatingHoursTime" required /></td>
                            <datalist id="TimeSlots"></datalist>
                    <td><input type="text" list="BayNo" id="BayType" name="BayType" required /></td>
                            <datalist id="BayNo"></datalist>
                    <td><input type="text" list="EmpList" id="Mechanic" name="Mechanic" required /></td>
                            <datalist id="EmpList"></datalist>
                    <td><input type="text" id="JobPrice" name"JobPrice" required /></td>
                    </tr>
                    </tbody>
                    </table>
                    <button type="submit" id="jobformbut">ADD JOB</button>
                    </form>
                    </div>
                    <div id="parts">
                    <form id="partform" action="#" method="post">
                    <table class="inputtbl">
                    <thead>
                    <tr>
                    <th><label for="Category">Category</label></td>
                    <th><label for="PartName">Part</label></td>
                    <th><label for="PartQuantity">Quantity</label></td>
                    <th><label for="PartPrice">Price</label></td>
                    <th><label for="PartTotalPrice">Total</label></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                    <td><input type="text" list="CatList" id="Category" name="Category" required /></td>
                            <datalist id="CatList"></datalist> 
                    <td><input type="text" id="PartName" list="PartList" name="PartName" required /></td>
                            <datalist id="PartList"></datalist>
                    <td><input type="text" id="PartQuantity" name="PartQuantity" required /></td>
                    <td><input type="text" id="BayType" list="PriceList" name="PartPrice" required /></td>
                            <datalist id="PriceList"></datalist>
                    <td><input type="text" id="PartTotalPrice" name="PartTotalPrice" required /></td>
                    </tr>
                    </tbody>
                    </table>
                    <button type="submit" id="partformbut">ADD PART</button>
                    </form>
                    </div>
                </div><!-- /.content_holder -->
            </div><!-- /.tabs_holder -->
        </section>
</article>