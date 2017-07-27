<?php

	/* get-dir-sizes.php?path=somdir&md5=1&recurse=2&maxmd5=10000 */
	
 include('verify_authorization.php');
 if(!verify_auth()){ exit(); }
 
  
 function output_directory_sizes($path, $md5, $recurse, $maxmd5)
 {
 	 
         if(basename($path) === '_MACOSX'){ return; }
 	 
 	 if(file_exists($path))
 	 {
 	 	$sz = filesize($path);
		$rem_path = substr($path, 3);
		$op = is_dir($path) ? ":mkdir" : ":new";
		echo "(:file \"$rem_path\"  :op $op  :size $sz  ";
		if($md5 && $sz > 0 && $sz < $maxmd5)
		{ 
			$md5str = md5_file($path);
			echo ":md5 \"$md5str\"  ";
		}
		echo ")\n";
		if(is_dir($path) && $recurse)
		{
			if(is_int($recurse)){ if($recurse > 0){ $recurse--; }}
			
			$d = dir($path);
			while(false !== ($entry = $d->read()))
			{
				if($entry != "." && $entry != ".." )
				{
					$new_path = $path . '/' . $entry;
				     if($path[strlen($path)-1] === '/'){ $new_path = $path . $entry; }
					
					output_directory_sizes($new_path, $md5, $recurse, $maxmd5);
				}
			}
			$d->close();
		}
	}
	
 }
 
 //ENTRY POINT
 
$path = ''; $md5 = false; $recurse='infinite'; $maxmd5=10000;
if(isset($_GET['path'])){ $path = $_GET['path']; }
if(isset($_GET['md5'])){ $md5 = $_GET['md5']; }
if(isset($_GET['recurse'])){ $recurse = $_GET['recurse']; if(is_numeric($recurse)){ $recurse = intval($recurse);  }}
if(isset($_GET['maxmd5'])){ $maxmd5 = $_GET['maxmd5']; if(is_numeric($maxmd5)){ $maxmd5 = intval($maxmd5); }else{ $maxmd5 = 10000; }}

$error_r = error_reporting();
error_reporting(0);
output_directory_sizes('../' . $path, $md5, $recurse, $maxmd5);
error_reporting($error_r); 
 
?>