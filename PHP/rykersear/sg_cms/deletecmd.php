<?php
 session_start();
 include('verify_login.php');
 if(!verify_login()){ exit(); }
 
 include('timestamps.php');
 
 function stripFormSlashes($arr)
 {
 	if(!is_array($arr))
 	{
 		return stripslashes($arr);
 	}
 	else
 	{
 		return array_map('stripFormSlashes', $arr);
 	}
 }
 
 function switch_file_name($p, $f)
{
  $a = explode('/', $p);
  $i = count($a) - 1;
  $a[$i] = $f;
  return implode('/', $a);
}

function find_matching_item($nodeList, $attrName, $target)
 {
   $x =0;
   $item = $nodeList->item($x);
   while($item)
   {
      if(strcmp($item->getAttribute($attrName), $target) ==0)
      {
      	return $item;
      }
      $x++;
      $item = $nodeList->item($x);
   }
   return null;
 }


 



 function ts_delete_file($f)
 {
     unlink('../' . $f);
     log_timestamp($f, ":del", "cms");
 }
 function ts_delete_directory_completely($dir)
 {
   if(substr($dir, strlen($dir)-1) !== '/'){ $dir = $dir . '/'; } //make sure dir ends with /
   $d = '../' . $dir;
   if(is_dir($d))
   {
     $handler = opendir($d);
     while($file = readdir($handler))
     {
       if ($file != '.' && $file != '..')
       {
         if(is_dir($d . $file))
         {
           ts_delete_directory_completely($dir . $file);
         }else{ ts_delete_file($dir  . $file); }
       }
     }
     closedir($handler);
   
     
     rmdir($d);
     log_timestamp($dir, ":deldir", "cms");
   }
 }
 
 
 
 function delete_pages_xml($id, $isfolder)
 {
   $pagesXML = new DOMDocument();
   if(file_exists('../sg_info/pages.xml'))
   {
     $pagesXML->load('../sg_info/pages.xml');
   }
 
   $nodeList = $pagesXML->getElementsByTagname( $isfolder ? 'folder' : 'item');
   $entry = find_matching_item($nodeList, "id", $id);
   
   if($entry)
   {
     
     log_page_timestamp($entry, ":delete", "page");
     log_del_page_timestamp($entry, ":delete", "page");
     $entry->parentNode->removeChild($entry);
     $pagesXML->save('../sg_info/pages.xml');
   }
 }
 
 function delete_page_or_folder($xmlString)
 {
    $dom = new DomDocument();
    $dom->loadXML($xmlString);
    $folder = $dom->getElementsByTagname('folder')->item(0);
    $id = 0;
    if($folder)
    {
      $path = $folder->getAttribute('path');
      $id = $folder->getAttribute('id');
      ts_delete_directory_completely( $path);
    }
    else
    {
      $page = $dom->getElementsByTagname('item')->item(0);
      if($page)
      {
        $path = $page->getAttribute('path');
        $id = $page->getAttribute('id');
        ts_delete_file( $path);
        $name = $page->getAttribute('name');
        $aname = $page->getAttribute('aname');
        $design = $page->getAttribute('design');
        if($name !== $design)
        {
          $contentDir = switch_file_name( $path, "sg_" . $aname . "_content");
          if(is_dir('../' . $contentDir))
          {
            ts_delete_directory_completely($contentDir);
          }
        }
      }
     }
     if($id)
     { 
     	delete_pages_xml($id, $folder);
     }
 }
 
 
 
 //MAIN ENTRY
 header('Content-type: text/xml');
 
 $lzpostbodyvar = $_POST['lzpostbody'];

if($lzpostbodyvar)
{
	if(get_magic_quotes_gpc())
	{
		$lzpostbodyvar = stripFormSlashes($lzpostbodyvar);
	}
		
	delete_page_or_folder($lzpostbodyvar);
}


 echo "<nothing />";
 ?>
