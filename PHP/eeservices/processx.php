<?php
//OBJECT ORIENTATED DATABASE CONNECTION
//Create Database Object
	$sqli = new mysqli("localhost","Will","wp642120","willproject");  //$GLOBALS required to call within functions.
	if (mysqli_connect_errno())
  	{
  		printf("Could not connect: %s. \n" . mysqli_connect_error());
		exit();
  	}
	
//Has the customer form been submitted? If TRUE then CustomerInsertTable;
if (isset($_POST['customerForm'])){
	CustomerInsertTable();
};
//Has the retrieve button been clicked? If TRUE then RetrieveData;
if (isset($_POST['retrieve'])){
	RetrieveData();
};

//Has the retrieve button been clicked? If TRUE then RetrieveData;
$count = 0;
if (isset($_POST['custsel'])){
	CustomerSelect();
}
?>