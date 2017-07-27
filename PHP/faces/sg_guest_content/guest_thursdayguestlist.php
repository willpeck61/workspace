<?php

   /*  This PHP form mailing script created by SiteGrinder 3.6.2 s_340  http://www.medialab.com/sitegrinder3  */

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
  $message = "";
  $message .= "FIRST Name: " . htmlspecialchars($_POST['first_name'], ENT_QUOTES) . "<br>\n";
  $message .= "Last NAme: " . htmlspecialchars($_POST['last_name'], ENT_QUOTES) . "<br>\n";
  $message .= "EMail: " . htmlspecialchars($_POST['email'], ENT_QUOTES) . "<br>\n";
  $message .= "Company: " . htmlspecialchars($_POST['company'], ENT_QUOTES) . "<br>\n";
  $message .= "PHONE: " . htmlspecialchars($_POST['phone'], ENT_QUOTES) . "<br>\n";
  $message .= "Date ReQuIRED: " . htmlspecialchars($_POST['date_required'], ENT_QUOTES) . "<br>\n";
  $message .= "Party Size: " . htmlspecialchars($_POST['party_size'], ENT_QUOTES) . "<br>\n";
  $message .= "OPTIONAL MESSAGE: " . htmlspecialchars($_POST['optional_message'], ENT_QUOTES) . "<br>\n";
  $lowmsg = strtolower($message);
  $injection_strings = array ( "content-type:","charset=","mime-version:","multipart/mixed","bcc:","cc:");
  foreach($injection_strings as $suspect)
  {
   if((stristr($lowmsg, $suspect)) || (stristr(strtolower($_POST['email']), $suspect)))
   {
     die ( 'Illegal Input.  Go back and try again.  Your message has not been sent.' );
   }
  }
 $headers = "MIME-Version: 1.0\r\nContent-type: text/html; charset=utf-8\r\n";
 $headers .= "Content-Transfer-Encoding: 8bit\r\n";
 $headers .= "From: \"" . "" . "\" <" . $_POST['email'] . ">\r\n";
 $headers .= "Reply-To: " . $_POST['email'] . "\r\n";
  mail("", "", $message, $headers);
  header("Location: ../calendar.php");
?>