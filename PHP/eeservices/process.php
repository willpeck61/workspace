<?php
//OBJECT ORIENTATED DATABASE CONNECTION
//Create Database Object
	global $mysqli;
	$mysqli = new mysqli("localhost","Will","wp642120","willproject");  
	if($mysqli->connect_errno > 0){
    	die('Unable to connect to database [' . $mysqli->connect_error . ']');
	}
	$test = 'SELECT * FROM `tblcustomer` WHERE `CustomerID` = 1';
	if(!$result = $mysqli->query($test)){
    die('There was an error running the query [' . $mysqli->error . ']');
	}
//set table
if (isset($_POST['tbl'])){	
	global $id;
	if (!isset($_POST['selection'])){
		$id = $_POST['id'];
	} else {
		$id = $_POST['selection'];
	}

	if ($_POST['tbl'] == "editcust") {
		$count = 0;
		$arrp = 0;
		$arrq = 10;
		$tbl = "tblcustomer";
		settype($tbl, "string");
		$tblorder = "CustomerLastName";
		$querystr[0] = "CustomerID";
		$querystr[1] = "CustomerFirstName";
		$querystr[2] = "CustomerLastName";
		$querystr[3] = "CustomerPostcode";
		$querystr[4] = "CustomerTitle";
		
		$insertstr[0] = "CustomerID";
		$insertstr[1] = "CustomerTitle";
		$insertstr[2] = "CustomerFirstName";
		$insertstr[3] = "CustomerLastName";
		$insertstr[4] = "CustomerAddress1";
		$insertstr[5] = "CustomerAddress2";
		$insertstr[6] = "CustomerAddress3";
		$insertstr[7] = "CustomerPostcode";
		$insertstr[8] = "CustomerPhone1";
		$insertstr[9] = "CustomerPhone2";
		$insertstr[10] = "CustomerEmail";
	} 
	elseif ($_POST['tbl'] == "editveh") {
		$count = 5;
		$arrp = 11;
		$arrq = 7;
		$tbl = "tblvehicle";
		settype($tbl, "string");
		$tblorder = "VehicleMake";
		$querystr[5] = "VehicleID";
		$querystr[6] = "VehicleModel";
		$querystr[7] = "VehicleMake";
		$querystr[8] = "VehicleType";
		$querystr[9] = "VehicleEngineType";
		
		$insertstr[11] = "VehicleID";
		$insertstr[12] = "VehicleMake";
		$insertstr[13] = "VehicleModel";
		$insertstr[14] = "VehicleType";
		$insertstr[15] = "VehicleEngineSize";
		$insertstr[16] = "VehicleEngineType";
		$insertstr[17] = "VehicleFuelType";
	}
	elseif ($_POST['tbl'] == "editemp") {
		$count = 10;
		$arrp = 18;
		$arrq = 11;
		$tbl = "tblemployee";
		settype($tbl, "string");
		$tblorder = "EmployeeLastName";
		$querystr[10] = "EmployeeID";
		$querystr[11] = "EmployeeFirstName";
		$querystr[12] = "EmployeeLastName";
		$querystr[13] = "EmployeePostcode";
		$querystr[14] = "EmployeeTitle";
		
		$insertstr[18] = "EmployeeID";
		$insertstr[19] = "EmployeeTitle";
		$insertstr[20] = "EmployeeFirstName";
		$insertstr[21] = "EmployeeLastName";
		$insertstr[22] = "EmployeeAddress1";		
		$insertstr[23] = "EmployeeAddress2";
		$insertstr[24] = "EmployeeAddress3";
		$insertstr[25] = "EmployeePostcode";
		$insertstr[26] = "EmployeeEmail";
		$insertstr[27] = "EmployeePhone1";
		$insertstr[28] = "EmployeePhone2";
	}
	elseif ($_POST['tbl'] == "editsup") {
		$count = 15;
		$arrp = 29;
		$arrq = 12;
		$tbl = "tblsupplier";
		settype($tbl, "string");
		$tblorder = "SupplierName";
		$querystr[15] = "SupplierID";
		$querystr[16] = "SupplierEmail";
		$querystr[17] = "SupplierName";
		$querystr[18] = "SupplierAccountNo";
		$querystr[19] = "SupplierPhone1";
		
		$insertstr[29] = "SupplierID";
		$insertstr[30] = "SupplierName";
		$insertstr[31] = "SupplierContactName";
		$insertstr[32] = "SupplierEmail";
		$insertstr[33] = "SupplierPhone1";
		$insertstr[34] = "SupplierPhone2";
		$insertstr[35] = "SupplierWebsite";
		$insertstr[36] = "SupplierAddress1";
		$insertstr[37] = "SupplierAddress2";
		$insertstr[38] = "SupplierAddress3";
		$insertstr[39] = "SupplierPostcode";
		$insertstr[40] = "SupplierAccountNo";
		
	}
	include_once "select-customer.php";
	if (isset($_POST['formdata'])){
		CustomerInsertTable($tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli);
	};
	if (isset($_POST['retrieve'])){
		RetrieveData($tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli);
	};
	if (isset($_POST['selection'])){
		CustomerSelect($tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli);
	}
}
?>