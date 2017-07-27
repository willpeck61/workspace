<?php 


$pagesXML = new DOMDocument();
if(file_exists($pathToRoot . 'sg_info/pages.xml'))
{
  $pagesXML->load($pathToRoot . 'sg_info/pages.xml');
}
$pageNodeList =  $pagesXML->getElementsByTagname('*');


$buttonLinkXML = new DOMDocument();
if(file_exists($pathToRoot . 'sg_info/buttonlinks.xml'))
{
   $buttonLinkXML->load($pathToRoot . 'sg_info/buttonlinks.xml');
}
$buttonList = $buttonLinkXML->getElementsByTagname('btn');
$bList      = $buttonLinkXML->getElementsByTagname('b');

function is_protocoled_string($str)
{
  if(strpos($str, '://') || strpos($str, 'javascript:') === 0 || strpos($str, '//') === 0) { return true; } else { return false; }
}


function get_menumatchclass_txt($pathTR)
{
  if(file_exists($pathTR . "sg_info/menumatchclass.txt"))
  {
    return file_get_contents($pathTR . "sg_info/menumatchclass.txt");
  }else{ return ''; }
}

function transform_href($linktype, $linkval, $aux, $nw)
{
    global $pageNodeList;
    global $pathToRoot;
   
    $newwin = ($nw === "true") ? "\" onclick=\"target='_blank';" : "";
    $nolink = "#";
    $classStr = substr($linkval, 0, 1) == '#' ? "\" class=\"smooth_scrolling" : "";

    if($linktype === "PAGE")
    {
      $x = 0;
      $element = $pageNodeList->item($x);
      while($element)
      {
	if(strcmp($element->getAttribute('id'), $linkval) ==0)
	{
	  return  $pathToRoot . $element->getAttribute('path') . $newwin;
	}
	$x++;
	$element = $pageNodeList->item($x);
      }
      return $nolink;
    }
    else if($linktype === "EMAIL")
    {
      if(strpos($linkval, "mailto:") === 0)
      {
	return $linkval;
      }
      else
      {
	$subj = ($aux === "") ? "" : "?subject=" . $aux;
	return "mailto:" . $linkval . $subj;
      }
    }
    else if($linktype == "URL")
    {
      if(is_protocoled_string($linkval))
      {
	return $linkval . $newwin;
      }else{ return 'http://' . $linkval . $newwin; }
    }
    else if($linktype == "FILE")
    {
      return $pathToRoot . str_replace("[upload]", "sg_userfiles", $linkval) . $newwin;
    }
    else if($linktype == "EDIT")
    {
      return $pathToRoot . "sg_admin.php";
    }
    else if($linktype == "NONE")
    {
    	 return "#";
    }
    else { return $linkval . $newwin . $classStr; }
}

 function regex_match2($reg, $text)
 {
   preg_match('(' . $reg . ')ms', $text, $matches);
   return $matches[1];
 }

 function regex_remove2($reg, $text)
 {
   return preg_replace('(' . $reg . ')ms', '', $text);
 }

function transform_link_regex($matches)
{
    global $pathToRoot;
    global $pageid;
    
    $a_txt = $matches[0];
    $linktype = regex_match2("linktype=\"([^\"]*)\"", $a_txt);
    $linkval = regex_match2("linkval=\"([^\"]*)\"", $a_txt);
    $aux = regex_match2("aux=\"([^\"]*)\"", $a_txt);
    $nw = regex_match2("newwin=\"([^\"]*)\"", $a_txt);

    $a_txt = regex_remove2("linktype=\"[^\"]*\"", $a_txt);
    $a_txt = regex_remove2("linkval=\"[^\"]*\"", $a_txt);
    $a_txt = regex_remove2("aux=\"[^\"]*\"", $a_txt);
    $a_txt = regex_remove2("newwin=\"([^\"]*)\"", $a_txt);
    $a_txt = regex_remove2("<a", $a_txt);
    $a_txt = regex_remove2(">", $a_txt);
    
    
    $pageidstr = $pageid . "";
    
    $classString = ($linkval === $pageidstr) ? get_menumatchclass_txt($pathToRoot) : "";
    

    return '<a ' . sprintf('href="%s"', transform_href($linktype, $linkval, $aux, $nw)) . $classString . $a_txt . '>';
}



function transform_links($txt)
{
	return preg_replace_callback('(<a[^>]*>)', 'transform_link_regex', $txt);
}

function sg_menu($f)
{
  echo " -->";
  $txt = file_get_contents($f); 
  echo transform_links($txt); 
  echo "<!-- ";
} 

function sg_emit($f)
{
  echo " -->";
  $txt = file_get_contents($f); 
  echo $txt; 
  echo "<!-- ";
} 

function sg_xmedia($url, $sg_mode, $SG_DIVNAME, $SG_DIVWIDTH, $SG_DIVHEIGHT, $sg_root)
{
   global $pathToRoot;
   include($url); 
}

function sg_blog($url, $sg_mode, $SG_DIVNAME, $SG_DIVWIDTH, $SG_DIVHEIGHT, $sg_root)
{
   global $pathToRoot;
   include($url); 
}

function static_picturebox($path)
{
    global $pathToRoot;
    $idxvar = 0;
    if(isset($_GET['idx']))
    {
		$idxvar = $_GET['idx'];
	      if(!$idxvar){ $idxvar = 0; }
    }
	$dom = new DOMDocument();
	if(file_exists($pathToRoot . $path))
	{
	  $dom->load($pathToRoot . $path);
	}
	$exhibitNodeList = $dom->getElementsByTagname('exhibit');
	if( $exhibitNodeList->length)
	{
		$picturebox =  $exhibitNodeList->item($idxvar)->getElementsByTagname('picturebox')->item(0);
		$url = $picturebox->getElementsByTagname('url')->item(0)->firstChild->nodeValue;
		$nextidx = ($idxvar + 1) % $exhibitNodeList->length;
		echo "--><noscript><a href=\"?idx=$nextidx\"><img src=\"$url\" alt=\"\" /></a>"; //</noscript><!-- ";
                $metadata = $exhibitNodeList->item($idxvar)->getElementsByTagname('metadata');
                for($curMeta = 0; $curMeta < $metadata->length; $curMeta++)
                {
                                
				echo('<p>');
				echo($metadata->item($curMeta)->firstChild->nodeValue);
				echo("</p>\n");
                }
               echo "</noscript><!-- ";
	}
} 

function static_panelsheet($path) 
{
        global $pathToRoot;
	$dom = new DOMDocument();
	if(file_exists($pathToRoot . $path))
	{
	  $dom->load($pathToRoot . $path);
	}
	$exhibitNodeList = $dom->getElementsByTagname('exhibit');
	$thumbList = $dom->getElementsByTagname('thumbnail');
	$exhibitCount = $exhibitNodeList->length;
	if( $exhibitCount)
	{
                echo("--><noscript>\n");
		$metaCount = $exhibitNodeList->item(0)->getElementsByTagname('metadata')->length;
		for($curExhibit = 0; $curExhibit < $exhibitCount; $curExhibit++) {
			echo('<h2>');
			echo($exhibitNodeList->item($curExhibit)->getElementsByTagname('name')->item(0)->firstChild->nodeValue);
			echo('</h2>');	
			$thumbUrl = $thumbList->item($curExhibit)->getElementsByTagname('url')->item(0)->firstChild->nodeValue;
			echo("<img src=\"$thumbUrl\" alt=\"\" />");
                        $curExhibitMetaList =  $exhibitNodeList->item($curExhibit)->getElementsByTagname('metadata');
			for($curMeta = 0; $curMeta < $metaCount; $curMeta++) {
				
				echo('<p>');
				echo($curExhibitMetaList->item($curMeta)->firstChild->nodeValue);
				echo("</p>\n");
			}		
		}
		echo('</noscript><!-- ');
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

function sglink($btnid)
{
    global $buttonList;
    global $bList;

    $nolink = "#";
    $element = matching_link($btnid, $buttonList);
    if(!$element){ $element = matching_link($btnid, $bList); }
    if($element)
    {
       echo transform_href($element->getAttribute('linktype'), $element->getAttribute('linkval'), $element->getAttribute('aux'), $element->getAttribute('newwin'));
    }
    else
    {
    	 echo $nolink;
    }
}

function sg_d_link($linktype, $linkval, $aux, $nw)
{
  echo transform_href($linktype, $linkval, $aux, $nw);
}

function sg_control_panel_scripts($scriptPath)
{
	$editvar = (isset($_GET['edit'])) ? $_GET['edit'] : 0;
	if($editvar)
	{
                echo " -->";
                printf("<script src=\"%s\" type=\"text/javascript\"></script>", $scriptPath);
                echo "<!-- ";
        }
}

function sg_path_scripts($sg_cmsPath, $sg_revcmsPath, $sg_sitePath)
{
   	$editvar = (isset($_GET['edit'])) ? $_GET['edit'] : 0;
	if($editvar)
	{
                echo " -->";
                printf(" <script type=\"text/javascript\">
  function path_to_sgcms(f)
  {
    return( '%s' + (f ? f : ''));
  }
  function path_from_sgcms(f)
  {
    return( '%s' + (f ? f : ''));
  }
  function path_to_site(f)
  {
    return( '%s' + (f ? f : ''));
  }
  function sg_local_arg()
  {
    return 0;
  }
 </script>", $sg_cmsPath, $sg_revcmsPath, $sg_sitePath);
                echo "<!-- ";
        }  
}

function sg_control_panel_styles()
{
  $editvar = (isset($_GET['edit'])) ? $_GET['edit'] : 0;
  if($editvar)
  {
    echo " -->
  <style type=\"text/css\" media=\"all\">
   .sg_cms_selected { border-color: blue; border-width:1px; border-style:solid; margin-top:-1px; margin-left:-1px; }
   .sg_cms_hilite { border-color: #FFAAAA; border-width:1px; border-style:solid; margin-top:-1px; margin-left:-1px; }
   #sg_overlay { z-index:1000; position:absolute; display:block; visibility:hidden; width:100%; top:0px; height:100%; background-color:black; display:block; -moz-opacity:0.6; opacity:.60; filter:alpha(opacity=60); }
   #sg_tinymce_div { z-index:1001;  position:absolute; display:block; visibility:hidden; width:100%; top:0px; height:100%;  display:block; }
   #sg_tinymce_inner_div { width:620px;  background-color:#DFDFDF;  display:block; position:relative; margin:0px auto 0px auto; border:10px solid black;  }
   #sg_tinymce_ta { margin:0; display:block; width:610px; height:350px; background-color:white; }
   #sg_menu_editor_div{ z-index:1001; position:absolute; display:block; visibility:hidden; width:100%; top:0px; height:100%; }
   #sg_menutree_editor_div{ z-index:1001; position:absolute; display:block; visibility:hidden; width:100%; top:0px; height:100%; }
   #sg_menutree_editor_inner_div{ width:500px; background-color:#FFF;  display:block; position:relative; margin:0px auto 0px auto; border:10px solid black;  }
   .editor_button{ width: 85px; border: 1px solid black; margin:5px 10px; background-color:#DDD; color:black; border-radius: 3px;  }
   .editor_button:hover{ background-color:#AAF; color:#F22; }
  </style><!-- ";
  }
}

function sg_menutree_editor_inclusions($sg_cmsPath)
{
  $editvar = (isset($_GET['edit'])) ? $_GET['edit'] : 0;
  if($editvar)
  {
    echo " -->
    <link rel=\"stylesheet\" type=\"text/css\" href=\"$sg_cmsPath/dojo_support/themes/claro/claro.css\"/>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/dojo_support/dojo/dojo.js\" djConfig=\"parseOnLoad: true\"></script>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/dojo_support/dijit/dijit.js\" djConfig=\"parseOnLoad: true\"></script>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/dojo_support/dojo/sgdojo.js\" djConfig=\"parseOnLoad: true\"></script>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/menu_editor/sg_xml_json_utils.js\" ></script>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/menu_editor/sg_link_editor.js\"></script>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/menu_editor/sg_menu_tree_editor.js\"></script>
    <script type=\"text/javascript\" src=\"$sg_cmsPath/menu_editor/sg_menu_editor.js\"></script> <!-- ";
  }
}


function sg_show_control_panel()
{	
	$editvar = (isset($_GET['edit'])) ? $_GET['edit'] : 0;
	if($editvar)
	{
		echo " -->";
            echo "    <div id=\"sg_overlay\"></div>
    <div id=\"sg_menu_editor_div\"></div>
    <div id=\"sg_menutree_editor_div\">
       <div id=\"sg_menutree_editor_inner_div\"></div>
    </div>
    <div id=\"sg_tinymce_div\" >
    <div id=\"sg_tinymce_inner_div\">
	<textarea id=\"sg_tinymce_ta\" class=\"mce-editor\" ></textarea>
    	<p style=\"text-align:right;\">
	 <input class=\"editor_button\" type=\"button\" value=\"Cancel\" onclick=\"closeCMSUI();\" />
	 <input class=\"editor_button\" type=\"button\" value=\"OK\" id=\"saveEntryBtn\" />
		
         </p>	
	
    </div>
</div>";
	    echo "<!-- ";
	}
}

?>
