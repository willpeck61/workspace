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
  $message .= "Company (optional): " . htmlspecialchars($_POST['company_optional'], ENT_QUOTES) . "<br>\n";
  $message .= "CONTACT PHONE: " . htmlspecialchars($_POST['contact_phone'], ENT_QUOTES) . "<br>\n";
  $message .= "DAY: " . htmlspecialchars($_POST['day'], ENT_QUOTES) . "<br>\n";
  $message .= "MONTH: " . htmlspecialchars($_POST['month'], ENT_QUOTES) . "<br>\n";
  $message .= "YEAR: " . htmlspecialchars($_POST['year'], ENT_QUOTES) . "<br>\n";
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
 $headers .= "From: \"" . "Private Hire Form" . "\" <" . $_POST['email'] . ">\r\n";
 $headers .= "Reply-To: " . $_POST['email'] . "\r\n";
  mail("info@faceswolverhampton.co.uk", "VIP Parties or Private Hire Enquiry", $message, $headers);
  echo "<script type='text/javascript'>alert('Thank you for your enquiry. We will be in contact shortly.')</script>";
 echo "<script type='text/javascript' >window.location = 'http://www.faceswolverhampton.co.uk/index.php'</script>";
?>