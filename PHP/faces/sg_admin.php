<?php
 if (!isset($_SESSION)) 
 {
   session_start(); 
 }

 include('sg_cms/verify_login.php');

 if(verify_login()) 
 {
?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
                <title>Content Manager: facesdeploy</title>
	</head>
	<frameset id="fs" cols="400px, *" onload="frames['content'].focus(); ">
		<frame name="nav" src="sg_cms/cms_frame.php" />
		<frame name="content" src="sg_cms/welcome_frame.html" />
	</frameset>
</html>
<?php
  }else
  {
  ?>
    <html><head><style type="text/css" media="all"/>body{ font-family:Verdana, sans-serif; font-size:.7em;} 
    label{ margin:0px; width:60px; display:block; float:left;  } 
    div{  margin:.8em; padding:0; display;block; height:1.6em;} 
    input{ display:block;  } a{ float:right;} </style><title>Login SiteGrinder CMS</title></head><body><form action="sg_cms/login.php" method="post"><fieldset><legend>Login</legend><img style="float:right" src="sg_cms/SGlogo_small.png" /><div><label  for="cms_user">User:</label><input type="text" name="cms_user"/></div><br />
    <div><label for="cms_pwd">Pwd:</label><input type="password" name="cms_pwd" /></div><br /><input type="submit" name="send"  value="submit" /><a href="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=sgadmin_login">Help</a></fieldset></form></body></html>
<?php
  }
  ?>