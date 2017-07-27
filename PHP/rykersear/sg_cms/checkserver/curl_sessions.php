<?php
session_id($_REQUEST['id']);
session_start();
if($_REQUEST['value'] == $_SESSION['value']) {
	echo 'success';
} else {
	echo 'fail';
}
?>