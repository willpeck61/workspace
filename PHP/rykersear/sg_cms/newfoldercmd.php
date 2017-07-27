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
 
function calc_max_id($nodeList, $maxid)
{
  $x=0;
  $n = $nodeList->item($x);
  while($n)
  {
    $id = (int)($n->getAttribute('id'));
    if($id > $maxid){ $maxid = $id; }
    $x++;
    $n = $nodeList->item($x);
  }
  return $maxid;
}

function get_max_id($pageDom)
{
  $nodeList = $pageDom->getElementsByTagname('item');
  $folderList = $pageDom->getElementsByTagname('folder');
  $maxid = calc_max_id($nodeList, 0);
  $maxid = calc_max_id($folderList, $maxid);
  return $maxid;
}

function is_even($x)
{
  if($x % 2 == 0)
  { 
    return true;
  }
  return false;
}
 
 function append_page_xml($fname, $folderPath)
 {
 	$parentNode = null;
 	$pageDom = new DomDocument();
 	if(file_exists('../sg_info/pages.xml'))
 	{
 	  $pageDom->load('../sg_info/pages.xml');
 	}
 	$x =0;
 	$nodeList = $pageDom->getElementsByTagname( ($folderPath != "") ? 'folder' : 'pages' );
 	$maxid = get_max_id($pageDom);
 	$nextid = is_even($maxid) ? $maxid + 2 : $maxid + 1; //ALWAYS EVEN PAGE IDs FOR REMOTELY CREATED PAGES
 	$parentNode = $nodeList->item($x);
 	if($folderPath !== "")
	{
		while($parentNode)
		{
		   if($parentNode->getAttribute('path') === $folderPath)
		   {
		      break;
		   }
		   $x++;
		   $parentNode = $nodeList->item($x);
		}
	}
	if($parentNode)
	{
		$newNode = $pageDom->createElement('folder');
		$newNode->setAttribute('name', $fname);
		$newNode->setAttribute('path', $folderPath . $fname . '/');
		$newNode->setAttribute('id', $nextid);
		$parentNode->appendChild($newNode);
		
		log_page_timestamp($newNode, ":new-folder", "page");
		
		$pageDom->save('../sg_info/pages.xml');
	}
}
 
 function new_folder($xmlString)
 {
 	$dom = new DomDocument();
 	$dom->loadXML($xmlString);
 	$nf = $dom->getElementsByTagname('newfolder')->item(0);
 	
 	$fname = $nf->getAttribute('name');
 	$parentPath  = $nf->getAttribute('path');
 	mkdir('../' . $parentPath . $fname);
 	log_timestamp($parentPath . $fname, ":mkdir", "cms");
 	
 	append_page_xml($fname, $parentPath);
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
		
	new_folder($lzpostbodyvar);
}

echo "<nothing />";
?>