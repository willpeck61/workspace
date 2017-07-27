<?php
//OBJECT ORIENTATED DATABASE CONNECTION
//Create Database Object
	global $mysqli;
	$mysqli = new mysqli("localhost","will","wp642120","willproject");  
	if($mysqli->connect_errno > 0){
    	die('Unable to connect to database [' . $mysqli->connect_error . ']');
	}
	$test = 'SELECT * FROM `tblcustomer` WHERE `CustomerID` = 1';
	if(!$result = $mysqli->query($test)){
    die('There was an error running the query [' . $mysqli->error . ']');
	}
?>