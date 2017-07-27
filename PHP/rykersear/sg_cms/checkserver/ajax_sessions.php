<?php
session_start();
if($_SESSION['check'] == $_REQUEST['id']) {
	unset($_SESSION['check']);
	echo 'success';
} else {
	echo 'fail';
}
?>