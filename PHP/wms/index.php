<?php
    session_start();
    if ($_SESSION['loggedin'] != 1) {
        header("Location: login.php");
        exit;
    }
error_reporting(E_ALL);
ini_set('display_errors', 1);
include 'process.php';
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
<?php include "dashboard.php" ?>
</div>
</header>

<section id="leftmenu">
<h2>Main Menu</h2>
<nav>
	<ul class="mainmenu">
    	<li><a href="#" id="item0">>>Planner</a></li>
		<li><a href="#" id="item1">>>New / Edit Customer</a></li>
    	<li><a href="#" id="item2">>>New Vehicle</a></li>
    	<li><a href="#" id="item3">>>New Employee</a></li>
    	<li><a href="#" id="item4">>>New Supplier</a></li>
        <li><a href="#" id="item5">>>New Order</a></li>
        <li><a href="#" id="item6">>>Order Enquiry</a></li>
    </ul>
</nav>

</section>
<div id="background">
<section id="main">
<h2>Application Window</h2>
	<article id="welcome" class="item0">
	<h2>DAY PLANNER</h2>
	<?php include "bays.php" ?>
	</article>
	<?php include "customer-form.php";?>
	<?php include "vehicle-form.php";?>
    <?php include "employee-form.php";?>
   	<?php include "supplier-form.php";?>
    <?php include "new-order.php";?>
</section>
</div>
</div>

<footer>
<?php include "current-selection.php";?>
<h1><i><b>W</b>orkshop <b>M</b>anagement <b>S</b>ystem 1.0</i></h1>
<h4>Coded by Will Peck</h4>
<?php  
/*CHECK CONNECTION TO DATABASE*/ 
if(!$result = $mysqli->query($test)){
echo "Connection Status: INACTIVE";
} else {
echo "Connection Status: ACTIVE";
};
?>  
</footer>
</body>
</html>
