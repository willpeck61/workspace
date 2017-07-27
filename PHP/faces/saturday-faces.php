<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>saturday-faces</title>
	<link rel="stylesheet" type="text/css" href="./saturday-faces.css" media="all" />
    <link href="styles-guest.css" rel="stylesheet" type="text/css">
	<!--[if IE]>
	<style type="text/css" media="all">.borderitem {border-style: solid;}</style>
	<![endif]-->
</head>

<body>

<div id="Div">
<p>Join the guestlist to gain free entry and Q-jump before 11pm! After 11pm normal entrance fee applies.</p>
</div>
<div id="Div2">
<?php
include_once "counter.php";
date_default_timezone_set('Europe/London');
if (isset($_POST['submit'])) {
 	$con=mysqli_connect("db488137745.db.1and1.com","dbo488137745","WPzx379007","db488137745");
    if (mysqli_connect_errno($con))
{
	echo "Failed to Connect to MySQL: " . mysqli_connect_error();
}
    $firstname = $_POST['firstname'];
    $surname = $_POST['surname'];
    $postcode = $_POST['postcode'];
    $emailaddress = $_POST['emailaddress'];
    $mobilenumber = $_POST['mobilenumber'];
	$date = date("d.m.Y");
	$visitors = $_SESSION['views'];
   	mysqli_query($con,"INSERT INTO `saturday-guestlist` (firstname, surname, postcode, mobilenumber, emailaddress, date) VALUES ('$firstname', '$surname', '$postcode', '$mobilenumber', '$emailaddress', '$date')");
	echo "<script type='text/javascript'>alert('Thanks " . $firstname . "! You have been added to the guestlist for Saturday.')</script>";
	echo "<script type='text/javascript' >window.location = 'http://www.faceswolverhampton.co.uk/index.php'</script>";
    mysqli_close($con);
  }
?>

  <form class="contact_form" action="" method="post" name="contact_form">
  <fieldset>
  <legend>Your Details - (Strictly Over 18s Only)</legend>
  <p>
    <legend><b>COMPLETE FOR FREE ENTRY &amp; Q-JUMP BEFORE 11PM!</b></legend>
  </p>
  <legend></legend>
  <ul> 
  	<li>
    	<label for="firstname">First Name:</label>
        <input type="text" name="firstname" required placeholder="Required" maxlength="20" pattern="[a-zA-Z_-]{3,15}">
    </li>
    <li>
        <label for="surname">Last Name:</label>
        <input type="text" name="surname" required placeholder="Required" maxlength="20" pattern="[a-zA-Z_-]{2,15}">
    </li>
    <li>    
        <label for="emailaddress">Email Address:</label>
        <input type="email" name="emailaddress" required placeholder="Required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
    </li>
    <li>  	
        <label for="postcode">Postcode:</label>
        <input type="text" name="postcode" placeholder="Optional" maxlength="7" pattern="[A-Za-z]{1,2}[0-9Rr][0-9A-Za-z]?[0-9][ABD-HJLNP-UW-Zabd-hjlnp-uw-z]{2}">
    </li>
    <li>    
        <label for="mobilenumber">Mobile Phone:</label>
        <input type="tel" name="mobilenumber" placeholder="Optional" maxlength="11" pattern="^\s*\(?(020[7,8]{1}\)?[ ]?[1-9]{1}[0-9{2}[ ]?[0-9]{4})|(0[1-8]{1}[0-9]{3}\)?[ ]?[1-9]{1}[0-9]{2}[ ]?[0-9]{3})\s*$" > 
    </li>
    <li>
      <button class="submit" type="submit" name="submit">Submit Form</button>
    </li>
    <li>
    	<a href="privacypolicy.php" target="_blank" style="color:#333">Privacy Policy</a>
    </li>
  </ul>
    </fieldset>
  </form>
</div>
<div id="saturday-faces_r1_c1">
</div>
<div id="saturday-faces_r2_c1">
</div>
<a href="www.faceswolverhampton.co.uk">
	<img src="images/saturday-faces_r2_c5.jpg" id="saturday-faces_r2_c5" alt="Faces Wolverhampton Home" />
	</a>
<div id="saturday-faces_r2_c7">
</div>
<div id="saturday-faces_r3_c1">
</div>
<img src="images/saturday-faces_r3_c2.jpg" id="saturday-faces_r3_c2" alt="" />
<div id="saturday-faces_r3_c3">
</div>
<div id="saturday-faces_r4_c5">
</div>
<div id="saturday-faces_r5_c3">
</div>
<div id="saturday-faces_r5_c6">
</div>
<div id="saturday-faces_r6_c2">
</div>
<div id="saturday-faces_r6_c4">
</div>
</body>
</html>
