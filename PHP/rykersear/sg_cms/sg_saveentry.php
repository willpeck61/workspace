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
 
 if(isset($_POST['fpath']) &&
    //isset($_POST['mpath']) &&
    isset($_POST['entry']))
 {
   //file_put_contents($_POST['mpath'] . $_POST['fpath'], $_POST['entry']);
   file_put_contents('../' . $_POST['fpath'], $_POST['entry']);
   log_timestamp($_POST['fpath'], ":new", "cms");
   echo "<status>ok</status>";
 }
 else
 {
   echo "<status>fail</status>";
 }
?>