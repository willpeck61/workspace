<?php
//TIMESTAMPS

include("permission_levels.php");

function log_timestamp($file, $operation, $tstype)
{
  global $raise_permission_levels;
  $time = time();
  
  $fsize = 1000;
  if($operation === ":mkdir"){ $fsize = 2000;}
  else if($operation === ":new" || $operation == ":onew" || $operation == ":link"){ $fsize = sprintf("%u", filesize('../' . $file)); }
  
  $tspath = timestamp_path($tstype);
  $exists = file_exists($tspath);
  $fh = fopen($tspath, 'a');
  fprintf($fh, "(:time $time :file \"$file\"  :op $operation  :size $fsize)\n");
  fclose($fh);
  
  if($operation !== ":mkdir" && $operation !== ":deldir" && $operation !== ":del")
  {
     $info = pathinfo($file);
     $extension = $info['extension'];
     if($raise_permission_levels && $extension !== "php" && $extension !== "html")
     {
       chmod('../' . $file, 0666);
     }
  }
  if($raise_permission_levels && $operation == ":mkdir")
  {
     $oldumask = umask(0);
     chmod('../' . $file, 0777);
     umask($oldumask);
  }
  if(!$exists && $raise_permission_levels)//we just created it
  {
  	chmod($tspath, 0666);
  }
}

function log_page_timestamp($node, $operation, $tstype)
{
  global $raise_permission_levels;
  $time = time();
  $tspath = timestamp_path($tstype);
  $name = $node->getAttribute("name");
  $aname = $node->getAttribute("aname");
  $id = $node->getAttribute("id");
  $path = $node->getAttribute("path");
  $design = $node->getAttribute("design");
  $designfname = $node->getAttribute("designfname");
  $icon = $node->getAttribute("icon");
  $isfile = $node->getAttribute("isfile");
  $ishome = $node->getAttribute("ishome");
  $exists = file_exists($tspath);
  $tag = ($operation === ":new-folder") ? ":|folder|" : ":|item|";
 
  
  
  $fh = fopen($tspath, 'a');
  
  if($ishome)
  {
     fprintf($fh, "(:time $time :page-sxml #S(XMLENTRY :TAG $tag :ATTRIBUTES (:|ishome| \"true\" :|name| \"$name\" :|aname| \"$aname\" :|id| \"$id\" :|path| \"$path\" :|design| \"$design\" :|designfname| \"$designfname\" :|icon| \"$icon\" :|isfile| \"$isfile\") :CONTENT NIL) :op $operation)\n");
  }
  else
  {
     fprintf($fh, "(:time $time :page-sxml #S(XMLENTRY :TAG $tag :ATTRIBUTES (:|name| \"$name\" :|aname| \"$aname\" :|id| \"$id\" :|path| \"$path\" :|design| \"$design\" :|designfname| \"$designfname\" :|icon| \"$icon\" :|isfile| \"$isfile\") :CONTENT NIL) :op $operation)\n");
  }
  fclose($fh);
  if(!$exists && $raise_permission_levels)//we just created it
  {
  	chmod($tspath, 0666);
  }
}

function log_del_page_timestamp($node, $operation, $tstype)
{
  $time = time();
  $tspath = timestamp_path('page_del');
  $name = $node->getAttribute("name");
  $aname = $node->getAttribute("aname");
  $id = $node->getAttribute("id");
  $path = $node->getAttribute("path");
  $design = $node->getAttribute("design");
  $designfname = $node->getAttribute("designfname");
  $icon = $node->getAttribute("icon");
  $isfile = $node->getAttribute("isfile");
  $ishome = $node->getAttribute("ishome");
  $exists = file_exists($tspath);
  //$tag = ($operation === ":new-folder") ? ":|folder|" : ":|item|";
  $tag = ':|item|';
 
  
  
  $fh = fopen($tspath, 'a');
  
  if($ishome)
  {
     fprintf($fh, "(:time $time :page-sxml #S(XMLENTRY :TAG $tag :ATTRIBUTES (:|ishome| \"true\" :|name| \"$name\" :|aname| \"$aname\" :|id| \"$id\" :|path| \"$path\" :|design| \"$design\" :|designfname| \"$designfname\" :|icon| \"$icon\" :|isfile| \"$isfile\") :CONTENT NIL) :op $operation)\n");
  }
  else
  {
     fprintf($fh, "(:time $time :page-sxml #S(XMLENTRY :TAG $tag :ATTRIBUTES (:|name| \"$name\" :|aname| \"$aname\" :|id| \"$id\" :|path| \"$path\" :|design| \"$design\" :|designfname| \"$designfname\" :|icon| \"$icon\" :|isfile| \"$isfile\") :CONTENT NIL) :op $operation)\n");
  }
  fclose($fh);
}



function timestamp_path($tstype)
{
  if($tstype === "cms")
  {
    return "../sg_info/cms_timestamps.ts";
  }
  else if($tstype === "page")
  {
    return "../sg_info/page_timestamps.ts";
  }
  else if($tstype === "menu")
  {
    return "../sg_info/menu_timestamps.ts";
  }
  else if($tstype === "page_del")
  {
    return "../sg_info/page_del_timestamps.txt";
  }
}
?>