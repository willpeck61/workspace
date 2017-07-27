<?php

function verify_login()
{
  if(isset($_SESSION['cms_user']) &&
     isset($_SESSION['cms_pwd']) &&
     md5($_SESSION['cms_user']) === '3df1de355383830cbb7e545df76761c6' &&
     md5($_SESSION['cms_pwd']) === '913bdde1ac7c1805a72b0d2caf8985c3')
    {
	 return true;
    }
    return false;
}
?>