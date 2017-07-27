<?php
 session_start();
 if(isset($_POST['cms_user']) && 
    isset($_POST['cms_pwd']))
 {
    $_SESSION['cms_user'] = $_POST['cms_user'];
    $_SESSION['cms_pwd']  = $_POST['cms_pwd'];
 }
 header('Location: ../sg_admin.php');

?>