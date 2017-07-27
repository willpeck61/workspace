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

function find_matching_node($tag, $attr, $val, $dom)
{
  $nodeList = $dom->getElementsByTagname($tag);
  $x=0;
  $cnode = $nodeList->item($x);
  while($cnode)
  {
    if($cnode->getAttribute($attr) === $val)
    {
      return($cnode);
    }
    $x++;
    $cnode = $nodeList->item($x);
  }
  return(0);
}

function cmd_set_link($setlinkdata)
{
  $dom = new DomDocument();
  $dom->loadXML($setlinkdata);
  $btnNode =  $dom->getElementsByTagname('btn')->item(0);
  $btnid = $btnNode->getAttribute("id");
  $linktype = $btnNode->getAttribute("linktype");
  $linkval  = $btnNode->getAttribute("linkval");
  $aux      = $btnNode->getAttribute("aux");
  $newwin   = $btnNode->getAttribute("newwin");
  
  $buttonLinksDom = new DomDocument();
  if(file_exists("../sg_info/buttonlinks.xml"))
  {
    $buttonLinksDom->load("../sg_info/buttonlinks.xml");
  }
  $matchingButton = find_matching_node('btn', 'id', $btnid, $buttonLinksDom);
  
  $display = $linktype . " " . $linkval; 
  if($linktype === "UDF"){ $display = "-undefined-"; }
  if($linktype === "NONE"){ $display = "-none-"; }
  if($linktype === "PAGE")
  { 
    $pageDom = new DomDocument(); 
    if(file_exists("../sg_info/pages.xml"))
    {
      $pageDom->load("../sg_info/pages.xml"); 
    }
    $pageNode = find_matching_node('item', 'id', $linkval, $pageDom);
    if($pageNode){ $display = "PAGE " . $pageNode->getAttribute("name"); }
    else{  $btnNode->setAttribute("linkval", "#");
      $btnNode->setAttribute("linktype", "UDF"); $display = "-undefined-"; }
  }

  if($matchingButton)
  {
    $matchingButton->setAttribute("linktype", $linktype);
    $matchingButton->setAttribute("linkval", $linkval);
    $matchingButton->setAttribute("display", $display);
    $matchingButton->setAttribute("aux", $aux);
    $matchingButton->setAttribute("newwin", $newwin);
    $buttonLinksDom->save("../sg_info/buttonlinks.xml");
    log_timestamp('sg_info/buttonlinks.xml', ':new', 'cms');
  }
}
  
  
  

  //MAIN ENTRY
header('Content-type: text/xml');

$setlinkdata = $_POST['lzpostbody'];
if($setlinkdata)
{
     if(get_magic_quotes_gpc())
     {
	    $setlinkdata = stripFormSlashes($setlinkdata);
     }
     cmd_set_link($setlinkdata);
}
echo "<nothing />";

?>