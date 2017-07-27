<?php
session_start();
include 'process.php';
include 'securepassword.php';
if (isset($_GET['login'])) {  //is login set?
$password = $_POST['password'];
$username = $_POST['username'];
$result = $mysqli->query("SELECT Password FROM tblemployee WHERE Username = '{$username}' AND Activate = 1") or die("NOT MATCHED");
$norows = mysqli_num_rows($result);
		while ($row = $result->fetch_array()){
			$pword = $row['Password'];
}
$validatePw = validate_password($password, $pword);
echo $validatePw;
if ($validatePw == true){
		$_SESSION['loggedin'] = 1;
		header("Location: index.php");
		exit;
	} else echo "NOT MATCHED";
}
?>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>EE Services :: Workshop Management System</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="top.css"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://onlinehtmltools.com/tab-generator/skinable_tabs.min.js"></script>
<script type="text/javascript" src="script.js"></script>
</head>

<div id="maincont">
<header>
<img id="logo" alt="EE Services Logo" src="images/logo-demo-green.gif.jpg" width="577" height="230">
<h1 id="headtitle">EE Services</h1>
<div id="tblcontainer">
<form action="?login=1" method="post">
	<label for="username">Username:</label><input type="text" name="username" />
	<label for="password">Password:</label><input type="password" name="password" />
	<input type="submit" />
</form>
<a href="register.php">Click Here to Request Account</a>
</div>
</header>

<ul class="inputform">
<form id="newuserform" action="process.php" method="post">
   	<li class="formline"><label for="UserFirstName">First Name</label></li>
    <li class="formlineb"><input class="formfield" type="text" id="UserFirstName" name="UserFirstName" placeholder="First Name" required></li>
   	<li class="formlinealt"><label for="UserLastName">Last Name</label></li>
    <li class="formlinealtb"><input class="formfield" type="text" id="UserLastName" name="UserLastName" placeholder="Last Name" required></li>
    <li class="formline"><label for="Username">Create Username</label></li>
    <li class="formlineb"><input class="formfield" type="text" id="Username" name="Username" placeholder="Username" required /></li>
    <li class="formlinealt"><label for="Password">Create Password</label></li>
    <li class="formlinealtb"><input class="formfield" type="Password" id="Password" name="Password" placeholder="Password" required /></li>
    <li class="formline"><label for="Password1">Verify Password</label></li>
    <li class="formlineb"><input class="formfield" type="Password" id="Password1" name="Password1" placeholder="Verify Password" required /></li>
    <li class="formlinebut"><a href="#" id="newuser" class="Buttons">SUBMIT</a></li>
</form>
</ul>