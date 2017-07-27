<?php
require 'PHPMailerAutoload.php';

// Check for empty fields
if(empty($_POST['name'])  		||
   empty($_POST['email']) 		||
   empty($_POST['phone']) 		||
   empty($_POST['message'])	||
   !filter_var($_POST['email'],FILTER_VALIDATE_EMAIL))
   {
	echo "No arguments Provided!";
	return false;
   }

$mail = new PHPMailer(true);

try {
	$name = strip_tags(htmlspecialchars($_POST['name']));
	$email_address = strip_tags(htmlspecialchars($_POST['email']));
	$phone = strip_tags(htmlspecialchars($_POST['phone']));
	$message = strip_tags(htmlspecialchars($_POST['message']));
	
	// Create the email and send the message
	$mail->setFrom('noreply@willpeck.info','Finnish Coatings'); 
	$mail->addAddress('willpeck61@gmail.com');
	$mail->Subject = "Website Contact Form:  $name";
	$mail->Body = "You have received a new message from your website contact form.\n\n"."Here are the details:\n\nName: $name\n\nEmail: $email_address\n\nPhone: $phone\n\nMessage:\n$message";
	if (!$mail->send()) {
		echo "Failed to send message.";
		echo "Mailer error: " . $mail->ErrorInfo;
		return false;
	} else {
		return True;
	}
}	catch (phpmailerException $e) {
	echo $e->errorMessage();
}	catch (Exception $e) {
	echo $e->getMessage();
}
	
?>
