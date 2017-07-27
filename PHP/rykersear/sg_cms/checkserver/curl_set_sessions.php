<?php
session_id($_REQUEST['id']);
session_start();
$_SESSION['value'] = $_REQUEST['value'];
echo 'success';
?>