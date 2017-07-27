<?php 

header('Content-type: application/javascript');

function REGEX_MATCH($reg, $text)
{
   preg_match('(' . $reg . ')ms', $text, $matches);
   return $matches[1];
}

function lift_ecommerce_href($path)
{
	$str = file_get_contents($path);
	$href = REGEX_MATCH('href="([^"]*)"', $str);
	
	return htmlspecialchars_decode($href);
	
}

function string_begins_with($str, $prefix)
{
  if (strstr($str, $prefix) === $str){ return true; }else{ return false; }
}

function is_protocoled_string($str)
{
  if(strpos($str, '://') || strpos($str, 'javascript:') === 0 || strpos($str, '//') === 0) { return true; } else { return false; }
}

global $pathToRoot;
$buttonLinkXML = new DOMDocument();
$buttonlinksPath = '../../' .  $pathToRoot . 'sg_info/buttonlinks.xml';
if(file_exists($buttonlinksPath))
{
   $buttonLinkXML->load($buttonlinksPath);
}
$buttonList = $buttonLinkXML->getElementsByTagname('btn');



$pageNodeList = null;

function load_pages_xml()
{
  global $pageNodeList;
  global $pathToRoot;

  $pagesXML = new DOMDocument();
  $pagesxmlPath = '../../' .  $pathToRoot . 'sg_info/pages.xml';
  if(file_exists($pagesxmlPath))
  {
    $pagesXML->load($pagesxmlPath);
    $pageNodeList =  $pagesXML->getElementsByTagname('*');
  }
}

function matching_link($btnid, $nodeList)
{
    $x = 0;
    $element = $nodeList->item($x);
    while($element)
    {
      if($element->getAttribute('id') == $btnid)
      {
        return $element;
      }
      $x++;
      $element = $nodeList->item($x);
    } 
    return false;
}


function transform_href($element)
{
  	
  	global $pathToRoot;
  	global $pageNodeList;
  	$linktype = $element->getAttribute('linktype');
  	$linkval = $element->getAttribute('linkval');
  	if($linktype === "PAGE")
  	{
  		if($pageNodeList == null){ load_pages_xml(); }
  		if($pageNodeList)
  		{
  			$x = 0;
  			$e = $pageNodeList->item($x);
  			while($element)
  			{
  				if(strcmp($e->getAttribute('id'), $linkval) ==0)
  				{
  					return $pathToRoot . $e->getAttribute('path');
  				}
  				$x++;
  				$e = $pageNodeList->item($x);
  			}
  		}
  		return "#";
  	}
  	else if($linktype == "EMAIL")
  	{
  		 if(strpos($linkval, "mailto:") === 0)
      	{
			return $linkval;
      	}
      	else
      	{
      		$aux = $element->getAttribute('aux');
			$subj = ($aux === "") ? "" : "?subject=" . $aux;
			return "mailto:" . $linkval . $subj;
      	}
     }
     else if($linktype == "URL")
     {
     	if(is_protocoled_string($linkval))
      	{
			return $linkval;
      	}else{ return 'http://' . $linkval; }
    	}
	else if($linktype == "FILE")
        {
      	  return $pathToRoot . str_replace("[upload]", "sg_userfiles", $linkval) ;
    	}
    	else if($linktype == "EDIT")
    	{
    	  return $pathToRoot . "sg_admin.php";
    	}
    	else { return $linkval; }
}

function local_link($id)
{
  global $buttonList;
  $element = matching_link($id, $buttonList);
  if($element)
  {
  	$href = htmlspecialchars_decode(transform_href($element));
  	$newwin =  $element->getAttribute('newwin');
  	if($newwin === "true")
  	{
  	    return "javascript:open_new_window('$href');";
  	}
  	else
  	{
  	    return $href;
  	}
  }
  return $id;
}


?>

function open_new_window(href)
{
	window.open(href);
	return; //return nothing
}

function translate_photos_d_link(href)
{
	switch(href)
	{
	}
	return(href);
}