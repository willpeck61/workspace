<?php

function verify_auth()
{

  if(isset($_POST['user']) &&
     isset($_POST['pw']) &&
     $_POST['user'] === '3df1de355383830cbb7e545df76761c6' &&
    $_POST['pw'] === '913bdde1ac7c1805a72b0d2caf8985c3')
    {
	 return true;
    }
    return false;
}
?>