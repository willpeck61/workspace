<?php
//START_OPTIONS
 $options='
<plugin/>';
 //END_OPTIONS
 
if(isset($_GET['options']))
{
  header('Content-type: text/xml');
  echo($options);
}
?>