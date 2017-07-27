<?php
//DATABASE CONNECTION
//Create Database Object
	$sqli = new mysqli("localhost","Will","wp642120","willproject");  //$GLOBALS required to call within functions.
	if (mysqli_connect_errno())
  	{
  		printf("Could not connect: %s. \n" . mysqli_connect_error());
		exit();
  	}
	
//Return Database Name
	if ($result = $sqli->query("SELECT DATABASE()")) {
		$row = $result->fetch_row();
		echo "<p>Connected to Database: " . $row[0] . "</p>";
		$result->close();
	}
	
//Has the customer form been submitted? If TRUE then CustomerInsertTable;
if (isset($_POST['customerForm'])){
	CustomerInsertTable();
};
//Has the retrieve button been clicked? If TRUE then RetrieveData;
if (isset($_POST['retrieve'])){
	RetrieveData();
};

//Use Object to insert data to table.
function CustomerInsertTable(){	
	$GLOBALS['sqli']->select_db("willproject");
	$into="INSERT INTO tblcustomer (CustomerTitle,CustomerFirstName,CustomerLastName,CustomerAddress1,
		CustomerAddress2,CustomerAddress3,CustomerPostcode,CustomerPhone1,CustomerPhone2,CustomerEmail) VALUES	
		('$_POST[custTitle]','$_POST[firstname]','$_POST[lastname]','$_POST[street]','$_POST[town]','$_POST[city]',
			'$_POST[postcode]','$_POST[phone1]','$_POST[phone2]','$_POST[email]')";
	if($GLOBALS['sqli']->query($into) === true){
		echo "<script type='text/javascript'>alert('New Record Added Successfully')</script>";
	}
	else
	{
		echo "<script type='text/javascript'>alert('Error: " . $into . " " . $GLOBALS['sqli']->error . "')</script>";
	}
};

//Get Stored Data
function RetrieveData(){
	$GLOBALS['sqli']->select_db("willproject");
	$select="SELECT * FROM tblcustomer";
	$result = $GLOBALS['sqli']->query($select);
	
	while ($row = $result->fetch_array())
	{
		$rows[] = $row;	
	};
	
	foreach ($rows as $row){
	echo "<ul>";
		echo "<li>ID: " . $row["CustomerID"] . "</li>";
		echo "<li>Title: " . $row["CustomerTitle"] . "</li>";
		echo "<li>First Name: " . $row["CustomerFirstName"] . "</li>";
		echo "<li>Last Name: " . $row["CustomerLastName"] . "</li>";
		echo "<li>Street: " . $row["CustomerAddress1"] . "</li>";
		echo "<li>Town: " . $row["CustomerAddress2"] . "</li>";
		echo "<li>City: " . $row["CustomerAddress3"] . "</li>";
		echo "<li>Postcode: " . $row["CustomerPostcode"] . "</li>";
		echo "<li>Mobile: " . $row["CustomerPhone1"] . "</li>";
		echo "<li>Alternate Phone: " . $row["CustomerPhone2"] . "</li>";
		echo "<li>Email: " . $row["CustomerEmail"] . "</li>";
	echo "</ul>";
	};
	
};
?>