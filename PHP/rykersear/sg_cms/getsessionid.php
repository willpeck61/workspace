<?php
 session_start();
 //include('verify_login.php');
 //if(!verify_login()){ exit(); }
 
 header('Content-type: text/xml');
 echo "<session>" . session_name() . "=" . session_id() . "</session>";
 
 ?>
