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
 
 function find_matching_xml_item($dom, $tagName, $attrName, $target)
 {
   $nodeList = $dom->getElementsByTagname($tagName);
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
 
 function parse_dir_path($path)
 {
 	$arr = explode('/', $path); 
 	$fname = array_pop($arr);
 	$dpath = implode('/', $arr);
 	return $dpath == '' ? $dpath : $dpath . '/';
 }
 
 function home_cmd($xmlString)
 {
 	$dom = new DomDocument();
 	$dom->loadXML($xmlString);
 	$homeDataItem = $dom->getElementsByTagname('item')->item(0);
 	$pagePath = $homeDataItem->getAttribute('path');
 	$dirPath = parse_dir_path($pagePath);
 	$extension = "php"; //<- extension
 	$indexPath = $dirPath . "index." . $extension;
 	$homeOn = $dom->getElementsByTagname('v')->item(0)->textContent === "true";
 	
 	$pageDom = new DomDocument();
 	if(file_exists('../sg_info/pages.xml'))
 	{
 	  $pageDom->load('../sg_info/pages.xml');
 	}
 	$pageNode = find_matching_xml_item($pageDom, 'item', 'path', $pagePath); 
 	$oldHomeNode = find_matching_xml_item($pageDom, 'item', 'path', $indexPath);
 	 	
 	if($oldHomeNode && is_file('../' . $indexPath) && file_exists('../' . $indexPath))
 	{
 		$oldHomePath = $dirPath . $oldHomeNode->getAttribute('aname') . '.' . $extension;
 		$success = rename('../' . $indexPath, '../' . $oldHomePath);
                if(!success){ return; } //BAIL
 		$oldHomeNode->setAttribute('path', $oldHomePath);
 		$oldHomeNode->removeAttribute('ishome');
 		log_timestamp($oldHomePath, ":new", "cms");
 		log_timestamp($indexPath, ":del", "cms");
 		log_page_timestamp($oldHomeNode, ":update", "page");
 	}
 	if($homeOn && is_file('../' . $pagePath) && file_exists('../' . $pagePath))
 	{
 		$success = rename('../' . $pagePath, '../' . $indexPath);
                if($success)
                {
 		  $pageNode->setAttribute('path', $indexPath);
 		  $pageNode->setAttribute('ishome', "true");
 		  log_timestamp($indexPath, ":new", "cms");
 		  log_timestamp($pagePath, ":del", "cms");
 		  log_page_timestamp($pageNode, ":update", "page");
                }
 	}
 	
 	$pageDom->save('../sg_info/pages.xml');		
 }

 
 // MAIN ENTRY
 header('Content-type: text/xml');
 
 $lzpostbodyvar = $_POST['lzpostbody'];
 
 if($lzpostbodyvar)
 {
	if(get_magic_quotes_gpc())
 	{
    	  $lzpostbodyvar = stripFormSlashes($lzpostbodyvar);
 	}
 	//trigger_error("lzpostbody: " . $lzpostbodyvar, E_USER_NOTICE);
 	home_cmd($lzpostbodyvar);
 }
 echo "<nothing />";
?>