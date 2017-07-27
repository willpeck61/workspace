<?php
/* -------------------------------------*/
/*
    SiteGrinder 3 Gallery Image Upload 
   
        
*/
/* -------------------------------------*/
 
 session_id( $_GET['PHPSESSID'] );
 session_start();
 include('verify_login.php');
 if(!verify_login()){ echo "fail"; exit(); }
 
 include('timestamps.php');
 
 
function simple_munge($fname)
{
  $str = str_replace(array(' '), '_', $fname);
  return str_replace(array('*', '?', '&', ';', '(', ')', '"', "'"), '', $str);
}

function get_fname()
{
  return basename( simple_munge( $_FILES['Filedata']['name'])); 
}

function get_dname()
{
  $target_d =  dirname($_GET['gxmlurl']);
  $target_d .= '/'; 
  //if(isset($_GET['mode'])){ $target_d .= $_GET['mode'] . '/'; }
  //if(!is_dir($target_d )){ mkdir($target_d ); }
  
  return $target_d;
}

function upload_image($fname, $target_path)
{
  $im = null;
  $modevar = '';
  if(isset($_GET['mode'])){ $modevar = $_GET['mode'] . '/'; }
  if(!file_exists('../' . $target_path . $modevar)){ $oldumask = umask(0); mkdir('../' . $target_path . $modevar); chmod('../' . $target_path . $modevar, 0777); umask($oldumask); }
  
  $shortpath = $target_path . $modevar .  $fname;
  $fullpath = '../' . $shortpath;  log_msg('upload filename: ' . $fullpath);
  
  $errors = array(1 => 'php.ini max file size exceeded', 
                2 => 'html form max file size exceeded', 
                3 => 'file upload was only partial', 
                4 => 'no file was attached');
  
  if($_FILES['Filedata']['error'] !== 0)
  {
   log_err("Error in Uploaded File Data " . $errors[$_FILES['Filedata']['error']]);
  }
  if(!is_uploaded_file($_FILES['Filedata']['tmp_name']))
  {
    log_err("Not an HTTP upload ");
  }  
  if($modevar === 'song/')
  {
    if(move_uploaded_file($_FILES['Filedata']['tmp_name'], $fullpath))
    {
	  log_upload($target_path, $shortpath);
	  echo "<ok path=\"$target_path\" />
";
	}else{ log_err( "There was an error uploading the file");
	    echo "<error />
";
	     }
  }
  else if(move_uploaded_file($_FILES['Filedata']['tmp_name'], $fullpath)) 
  { 
	  if(getimagesize($fullpath))
	  {
	    echo "<ok path=\"$target_path\" />
	    ";
	    if(preg_match('/[.](jpe?g)$/i', $fullpath)) {   
	         $im = imagecreatefromjpeg( $fullpath);   
	    } else if (preg_match('/[.](gif)$/i', $fullpath)) {   
	        $im = imagecreatefromgif( $fullpath);   
	    } else if (preg_match('/[.](png)$/i', $fullpath)) {   
	        $im = imagecreatefrompng( $fullpath);   
	    }   
	
	    if($modevar == "bigbox/"){ log_upload($target_path, $shortpath); log_timestamp( $shortpath, ":new", "cms");}
	  
	  } else{
	    log_err( "Error: getimagesize failed.  $fullpath ");
	    echo "<error />
	    ";
	  }
  }else{ log_err("Error: There was an error uploading the file. " .  $_FILES['Filedata']['tmp_name']  . " --- " . $_FILES['Filedata']['name'] );  }
  return $im;
}

function debug_image()
{
  $im = imagecreatefromjpeg('../sg_all-the-cakes_content/cakes/debug/debug2.jpg');
  return $im;
}




function output_image($im, $path)
{
   if(preg_match('/[.](jpe?g)$/i', $path)) {   
         imagejpeg($im, '../' .  $path, 100);  
    } else if (preg_match('/[.](gif)$/i', $path)) {   
         imagegif($im, '../' . $path);  
    } else if (preg_match('/[.](png)$/i', $path)) {   
         imagepng($im, '../' . $path, 9);   
    }     
}

function crop_save_image($im, $path, $cropW, $cropH)
{
   crop_resize_image($im, $path, $cropW, $cropH, $cropW, $cropH);
}

function crop_resize_image($im, $path, $cropW, $cropH, $width, $height)
{
	$newimg = imagecreatetruecolor($width, $height);
	$im_w = imagesx($im);
	$im_h = imagesy($im);
	$xcoord = ($im_w  - $cropW) / 2;
	$ycoord = ($im_h  - $cropH) / 2;
	
	imagecopyresampled($newimg, $im, 0, 0, $xcoord, $ycoord, $width, $height, $cropW, $cropH);
	
	output_image($newimg, $path);
}

function inset_resize_image($im, $path, $width, $height)
{
   $newimg = imagecreatetruecolor($width, $height);
   imagecopyresampled($newimg, $im, 0, 0, 0, 0, $width, $height, imagesx($im), imagesy($im));
   
   output_image($newimg, $path);
}

function resize_gallery_image($im, $path,  $imgw, $imgh, $targetW, $targetH, $fit)
{
   $targetRatio = $targetW / ($targetH + 0.0);
   $imgRatio = $imgw / ($imgh + 0.0);
   if( ($imgw > $targetW) && ($imgh > $targetH) && ($imgRatio > $targetRatio) ) //completely too big. image "wider" than target
   {
      if($fit == 0){
        crop_resize_image($im, $path, round( ($imgh * $targetW) / $targetH), $imgh, $targetW, $targetH);
      }else{
        inset_resize_image($im, $path, $targetW, round( ($imgh * $targetW) / $imgw));   }
   }
   else if( ($imgw > $targetW) && ($imgh > $targetH) ) //completely too big. image "taller" than target
   {
   	 if($fit == 0){
   	 	crop_resize_image($im, $path, $imgw, round( ($imgw * $targetH) / $targetW), $targetW, $targetH);
   	 }else{
   	    inset_resize_image($im, $path, round( ($imgw * $targetH) / $imgh), $targetH);   }
   }
   else if($imgw > $targetW) //too wide only
   {
   	 if($fit == 0){
   	 	crop_save_image($im, $path, $targetW, $imgh);
   	 }else{
   	 	inset_resize_image($im, $path, $targetW, round(($imgh * $targetW) / $imgw));   }
   }
   else if($imgh > $targetH) //too tall only
   {
   	 if($fit == 0){
   	 	crop_save_image($im, $path, $imgw, $targetH);
   	 }else{
   	 	inset_resize_image($im, $path, round(($imgw * $targetH) / $imgh), $targetH);   }
   }
}

function handle_uploaded_image($im, $fname, $target_d, $targetW, $targetH, $fitflag, $dir)
{
   $path =  $dir . $fname;
   $im_width  = imagesx($im);
   $im_height = imagesy($im);
   $fit = 1;
   if($fitflag === "fill"){ $fit = 0; }

   log_upload($target_d, $path);
   
   if(($im_width <= $targetW) && ($im_height <= $targetH)) // it fits
   {
      $modevar = '';
      if(isset($_GET['mode'])){ $modevar = $_GET['mode'] . '/'; }
      copy('../' . $target_d . $modevar .  $fname, '../' . $path);
   }
   else 
   {
      resize_gallery_image($im, $path, $im_width, $im_height, $targetW, $targetH, $fit);
   }
   
   log_timestamp($path, ":new", "cms");
}

function process_image($im, $fname, $target_d, $args)
{
   foreach($args as $process)
   {
     $targetW = isset($_GET[ $process[0] ]) ?  $_GET[ $process[0] ] : false;
     $targetH = isset($_GET[ $process[1] ]) ?  $_GET[ $process[1] ] : false;
     $fit     = isset($_GET[ $process[2] ]) ?  $_GET[ $process[2] ] : false;
     $dirname =  $process[3]; 
     
     log_msg('targetW: ' . $targetW . '  targetH: ' . $targetH . '  fit: ' . $fit . '  dirname: ' . $dirname);
	//always make a thumbnail.
	 if($dirname == "thumbnail" 
		&& !$targetW
		&& !file_exists('../' . $target_d . $dirname . '/'. $fname))
	 { $targetW = 100; $targetH = 100; $fit = "fill";}
     
     if($targetW)
     {
       $dir = $target_d . $dirname . '/';              log_msg('dir: ' . $dir);
       if(!file_exists('../' . $dir)){ $oldumask = umask(0); mkdir('../' . $dir); chmod('../' . $dir, 0777); umask($oldumask);}
       handle_uploaded_image($im, $fname, $target_d, $targetW, $targetH, $fit, $dir);
     }
     else if( file_exists('../' . $target_d . $dirname . '/'. $fname) && $dirname == 'thumbnail' )
     { 
        $dir = $target_d . $dirname . '/'; 
     	log_upload($target_d, $dir . $fname);
     }
   }
}

 function log_err($str)
 {
    global $logf;
    if($logf)
    {
      fwrite($logf, $str); fwrite($logf, '
');
    }
    trigger_error($str, E_USER_NOTICE);
 }
 
 function log_msg($str)
 {
   global $logf;
   if($logf){ fwrite($logf, $str); fwrite($logf, '
'); }
 }

 function log_upload($target_d, $path)
 {
	$uploadidvar = $_GET['uploadid'];
	$dir = 'm';
	if(!file_exists($dir)){ $oldumask = umask(0); mkdir($dir); chmod($dir, 0777); umask($oldumask); }
	$upload_log_path = $dir . '/' . $uploadidvar . '.txt';
	$existed_before = file_exists($upload_log_path);
	$uploadlogf  = fopen($upload_log_path, 'a');
	if($uploadlogf)
	{
		if(!$existed_before)
		{
			$gxmlurlvar = $_GET['gxmlurl'];
			 fwrite($uploadlogf, $gxmlurlvar); fwrite($uploadlogf, "\n"); 
		}
		fwrite($uploadlogf, $path); fwrite($uploadlogf, "\n");
                fclose($uploadlogf);
	}
	
 }

  //MAIN ENTRY
 header('Content-type: text/xml');
 global $logf;
 //$logf = fopen('log.txt', 'w');
 
 
 $fname = get_fname();       
 
 $target_d = get_dname();    
 $im = upload_image($fname,  $target_d);       //responds

 
 if($im)
 {
   process_image($im, $fname,  $target_d, array(array("pbox_w", "pbox_h", "pbox_f", "picturebox"),
 	                       	                    array("ex_w", "ex_h", "ex_f", "thumbnail"),
 	                     	                    array("view_w", "view_h", "view_f", "viewthumb")));
 }
 
 echo "
 ";
 

 if($logf){ fclose($logf); }

?>