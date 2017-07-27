<?php
session_start();
unset($_SESSION['cms_user']);
unset($_SESSION['cms_pwd']);
echo "<done />";
?>