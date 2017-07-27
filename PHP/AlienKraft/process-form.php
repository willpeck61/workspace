
<?php
//PHP Script coded for contact forms - includes notification email, mailchimp integration, MySQL updates
//Written by Will Peck   

//Custom Vars
$processemail = 'willpeck61@gmail.com';    								//email address for application 
$customeralert = 'Thank you for your enquiry. We will get back to you shortly.';				//Alert message.
$returnpage = 'http://www.alienkraft.com/index.php';						//Return to this page after finished
$subject = 'AlienKraft';
//Edit above to customise.

date_default_timezone_set('Europe/London');

if (isset($_POST['submit'])) {
	$firstname = $_POST['name'];
	$emailaddress = $_POST['email'];
	$mobilenumber = $_POST['telephone'];
	$formmessage = $_POST['message'];
	
	//send enquiry to designated email
	$message .= "Name: " . htmlspecialchars($firstname, ENT_QUOTES) . "<br>\n";
  	$message .= "Email Address: " . htmlspecialchars($emailaddress, ENT_QUOTES) . "<br>\n";
  	$message .= "Mobile Phone: " . htmlspecialchars($mobilenumber, ENT_QUOTES) . "<br>\n";
  	$message .= "Message: " . htmlspecialchars($formmessage, ENT_QUOTES) . "<br>\n";
   
  	$lowmsg = strtolower($message);  //combine message array to lowmsg
	//secure php mailer against injection hacks
  	$injection_strings = array ( "content-type:","charset=","mime-version:","multipart/mixed","bcc:","cc:");
	foreach($injection_strings as $suspect)  
  	{
   		if((stristr($lowmsg, $suspect)) || (stristr(strtolower($_POST['email_address']), $suspect)))
   		{
     		die ( 'Illegal Input.  Go back and try again.  Your message has not been sent.' );
   		}
  	}	
 	$headers = "MIME-Version: 1.0\r\nContent-type: text/html; charset=utf-8\r\n";
 	$headers .= "Content-Transfer-Encoding: 8bit\r\n";
 	$headers .= "From: messages@alienkraft.com\r\n";
 	$headers .= "Reply-To: messages@alienkraft.com\r\n";
  	mail($processemail, $subject, $message, $headers);
	
  	//mail end
	
	echo "<script type='text/javascript'>alert('".$customeralert."')</script>";
	echo "<script type='text/javascript'>window.location = '".$returnpage."';</script>";      
}

?>