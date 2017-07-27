<?php
 session_start();
 include('verify_login.php');
 if(!verify_login()){ exit(); }
 
 include('timestamps.php');
 
 header('Content-type: text/xml');
 
 function stripFormSlashes($arr)
 {
 	if(!is_array($arr))
 	{
 		return stripslashes($arr);
 	}
 	else
 	{
 		return array_map('stripFormSlashes', $arr);
 	}
 }
 
 if(get_magic_quotes_gpc())
 {
 	$_POST = stripFormSlashes($_POST);
 }

 //MAIN ENTRY
 $lzpostbodyvar = $_POST['lzpostbody'];
 
 if($lzpostbodyvar)
 {
	if(get_magic_quotes_gpc())
 	{
    	  $lzpostbodyvar = stripFormSlashes($lzpostbodyvar);
 	}
 	$lzpostbodyvar = str_replace('<sgsettings_dataset>', '', $lzpostbodyvar);
 	$lzpostbodyvar = str_replace('</sgsettings_dataset>', '', $lzpostbodyvar);
 	file_put_contents('../sg_info/site_settings.xml', $lzpostbodyvar);
 	log_timestamp('sg_info/site_settings.xml', ':new', 'cms');
 }
 echo '<nothing />';

?>