<?php
include "db.class.php";
//OBJECT ORIENTATED DATABASE CONNECTION
//Create Database Object
	$sqli = new config("localhost","Will","wp642120","willproject","mysqli");  
	$db = new db($sqli);

//Open the database connection.
	$db->openConnection();
	$testdb = $db->pingServer();
	echo "Are We Connected: " . $testdb;
	
//Has the customer form been submitted? If TRUE then CustomerInsertTable;
/*
if (isset($_POST['customerForm'])){
	CustomerInsertTable();
};
//Has the retrieve button been clicked? If TRUE then RetrieveData;
if (isset($_POST['retrieve'])){
	include "select-customer.php";
	RetrieveData();
};

//Has the retrieve button been clicked? If TRUE then RetrieveData;
$count = 0;
if (isset($_POST['custsel'])){
	CustomerSelect();
}*/
?>