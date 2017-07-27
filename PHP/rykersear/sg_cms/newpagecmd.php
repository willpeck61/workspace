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

 
 function find_design_by_name($designName)
 {
   return find_matching_xml_item('../sg_info/designs.xml', 'design', 'name', $designName);
 }
 
 function find_matching_xml_item($path, $tagName, $attrName, $target)
 {
   $dom = new DomDocument();
   if(file_exists($path))
   {
     $dom->load($path);
   }
   $nodeList = $dom->getElementsByTagname($tagName);
   
   return find_matching_item($nodeList, $attrName, $target);
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
 
  function path_to_top($path)
 { //simple
 	$arr = preg_split("(/)", $path);
 	$str = '';
 	for($i = 1; $i< count($arr); $i++) //yes, _1_ is correct.
 	{
		$str = $str . '../';
	}
	return $str;
}
 
function base_name($str)
{ //simplified
  	return str_replace(' ', '-', str_replace(array( '.', ',', ':', ';', '!', '*', '?', '/', '&', '@', '#', '%', '\"', '\'', '<', '>', '_'), 
  								'',
  								strtolower(trim($str))));
  					 
  							
 }
 
function insure_dir_exists($p) //this function will add the '../'    accepts _file_ paths only.
{
  $a = explode('/', $p);
  $dp = '..';
  $max = count($a) -1;
  for($i=0; $i < $max; $i++)
  {
    $dp = $dp . '/' . $a[$i];
    if(! is_dir($dp) )
    {
      mkdir($dp);
    }
  }
}

function switch_file_name($p, $f)
{
  $a = explode('/', $p);
  $i = count($a) - 1;
  $a[$i] = $f;
  return implode('/', $a);
}
 
 function sans_page_suffix($str)
 {
   return preg_replace('(-page.*)', '', $str);
 }
 
 
 function collect_entries($design, $status)
 {
 	$allEntries = $design->getElementsByTagname('item');
 	$arr = array();
 	$x = 0;
 	$entry = $allEntries->item($x);
 	while($entry)
 	{
 	   if(strcmp($entry->getAttribute('status'), $status) == 0)
 	   {
 	   	 array_push($arr, $entry);
 	   }
 	   $x++;
 	   $entry = $allEntries->item($x);
 	}
 	return($arr);
 }
 
function string_begins_with($str, $prefix)
{
  if (strstr($str, $prefix) === $str){ return true; }else{ return false; }
}	

function is_dynamic_link_token($str)
{
  if(strpos($str, '?php')){ return true; } else { return false; }
}

function is_protocoled_string($str)
{
 if(strpos($str, '://') || strpos($str, 'javascript:') === 0 || strpos($str, '//') === 0) { return true; } else { return false; }
}


class sgclosure { public  $i; 
	           public  $relChange;
	           public  $unique;
	           public  $linkageFormat;
	           public  $oldcontentstr;
	           public  $newcontentstr;
	           public  function adjust_media_link($matches)
	           {
	              $href = $matches[1];
	              if(is_protocoled_string($href) || is_dynamic_link_token($href) || string_begins_with($href, "#"))
	              {
	                return sprintf($this->linkageFormat[$this->i],  $href);
	              }
	              else
	              {
	           	   $item = find_matching_item($this->unique, "path", substr($href, strlen($this->oldcontentstr)+1) );
	           	   if($item)
	           	   {
	           	     return sprintf($this->linkageFormat[$this->i], $this->newcontentstr . '/' . $item->getAttribute('path'));
	           	   }
	           	   else
	           	   {
	           	     return sprintf($this->linkageFormat[$this->i], $this->relChange . $href);
	           	   }
	           	}
	           }
	          }//end sgclosure
	           	
	           


function adjust_all_relative_links($designStr, $relativeChange, $oldContentStr, $oldContentDir, $newContentStr, $newContentDir)
{
	$unique_xml_path = $oldContentDir . '/unique.xml';
	if(is_file($unique_xml_path))
	{
		$unique_xml = new DomDocument();
		if(file_exists($unique_xml_path))
		{
	 	   $unique_xml->load($unique_xml_path);
	 	}
	 	$nodeList = $unique_xml->getElementsByTagname('item');
	 	$linkageRegex = array('(href="([^"]*)")', '(src="([^"]*)")', '(data="([^"]*)")', '(action="([^"]*)")', '(sg_emit\("([^"]*)")', '(<\?php include\("([^"]*)")', '(sg_menu\("([^"]*)")', '(sg_xmedia\("([^"]*)")', '(sg_blog\("([^"]*)")', '(docent\("([^"]*)")', '(static_picturebox\("([^"]*)")', '(static_panelsheet\("([^"]*)")', '(<param\s*name="src"\s*value="([^"]*)")', '(<param\s*value="([^"]*)"\s*name="src")' );
	 	$cl = new sgclosure(); 
	 	$cl->relChange = $relativeChange; 
	 	$cl->unique = $nodeList; 
	 	$cl->oldcontentstr = $oldContentStr;
	 	$cl->newcontentstr = $newContentStr;
	 	$cl->linkageFormat =  array('href="%s"', 'src="%s"', 'data="%s"', 'action="%s"', 'sg_emit("%s"', '<?php include("%s"', 'sg_menu("%s"', 'sg_xmedia("%s"', 'sg_blog("%s"', 'docent("%s"', 'static_picturebox("%s"', 'static_panelsheet("%s"', '<param name="src" value="%s"', '<param name="src" value="%s"');
	 	
		for($x =0; $x < count($linkageRegex); $x++)
		{
			$cl->i = $x;
			$designStr = preg_replace_callback($linkageRegex[$x], 
				                              array($cl, 'adjust_media_link'),
				                              $designStr);     
		}
	}
	return $designStr;
}

function copy_unique_items_for_new_page($oldContentDir, $newContentDir, $relativeChange, $oldContentStr,  $newContentStr)
{
	$unique_xml_path = $oldContentDir . '/unique.xml';
	if(is_file($unique_xml_path))
	{
		$unique_xml = new DomDocument();
		if(file_exists($unique_xml_path))
		{
	 	  $unique_xml->load($unique_xml_path);
	 	}
	 	$nodeList = $unique_xml->getElementsByTagname('item');
	 	$x = 0;
	 	$uniqueItem = $nodeList->item($x);
	 	while($uniqueItem)
	 	{
	 		$path = $uniqueItem->getAttribute('path');
	 		$newPath =  $newContentDir . '/' . $path;
	 		insure_dir_exists($newPath);
	 		if(basename($path) === "gxml.xml")
	 		{
	 		  $mpath = switch_file_name($newPath, 'gallery.xml');
	 		  
	 		  copy($oldContentDir . '/' . $path, '../' . $mpath);
	  	       log_timestamp($mpath, ":new", "cms");
	  	     }
	  	     else if(basename($path) !== "gallery.xml")
	  	     {
	  	       copy($oldContentDir . '/' . $path, '../' . $newPath);
	  	       log_timestamp($newPath, ":new", "cms");
	  	     }
	  	     else if(strpos(basename($path), ".htm")  ||
	  	             strpos(basename($path), ".blog.") )
	  	     {
	  	        file_put_contents('../' . $newPath,
	  	        	                 adjust_all_relative_links(file_get_contents('../' . $newPath), 
	  	        	                 	                        $relativeChange, $oldContentStr, $oldContentDir, $newContentStr, $newContentDir));
	  	     }
	  	     
	 		$x++;
	 		$uniqueItem = $nodeList->item($x);
	 	}
	}
 }
 
 function adjust_xml_links($newContentDir, $oldContentStr, $f, $tag, $attr)
 {
   $path = '../' . $newContentDir . '/' . $f;
   if(is_file($path))
   {
	   $dom = new DomDocument();
	   $success = null;
	   if(file_exists($path))
	   {
	     $success = $dom->load($path);
	   }
	   if($success)
	   {
	     $nodeList = $dom->getElementsByTagname($tag);
	     $x = 0;
	     $item = $nodeList->item($x);
	     while($item)
	     {
	        
	         $status = $item->getAttribute('status');
	         if($status !== "shared")
	         {
	         		$str = $item->getAttribute($attr);
	         		
	     		if(strpos($str, $oldContentStr) !== false)
	     		{
	     			
	     			$item->setAttribute($attr, str_replace($oldContentStr, $newContentDir, $str)); 
	     			
	     		}
	     	}
	     	$x++;
	     	$item = $nodeList->item($x);
	     }
	     $dom->save('../' . $newContentDir . '/' . $f);
		log_timestamp($newContentDir . '/' . $f, ":new", "cms");
	   }
   }
}

 function adjust_gallery_option_links($newContentDir, $relativeChange)
 {
 	$path = '../' . $newContentDir . '/editable.xml';
 	if(is_file($path) && file_exists($path))
 	{
 		$editableDom = new DomDocument();
 		$success = $editableDom->load($path);
 		if($success)
 		{
 			$nodeList = $editableDom->getElementsByTagname('item');
 			$x=0;
 			$item = $nodeList->item($x);
 			while($item)
 			{
 				$type = $item->getAttribute('type');
 				if($type === 'gallery')
 				{
 					$galleryxml_path = '../' . $item->getAttribute('file');
 					$galleryDom = new DomDocument();
 					$success = $galleryDom->load($galleryxml_path);
 					if($success)
 					{
 						$optionsNodeList = $galleryDom->getElementsByTagname('option');
 						$y = 0;
 						$option = $optionsNodeList->item($y);
 						while($option)
 						{	
 							$optiontype = $option->getAttribute('type');
 							if($optiontype === 'file')
 							{
 								$option->setAttribute('value', $relativeChange . $option->getAttribute('value'));
 							}
 							$y++;
 							$option = $optionsNodeList->item($y);
 						}
 						$galleryDom->save($galleryxml_path);
						
 					}
 				}
 				$x++;
 				$item = $nodeList->item($x);
 			}
 		}
 	}		
 }

function adjust_blog_links($newContentDir, $oldContentStr, $newContentStr)
{
	$newCD = opendir('../' . $newContentDir);
	while(($file = readdir($newCD))!== false)
	{
		if(strpos($file, ".blog")) 
      	{	
      	    $text = file_get_contents('../' . $newContentDir . '/' . $file);  
      	    file_put_contents('../' . $newContentDir . '/' . $file, str_replace($oldContentStr, $newContentStr, $text));
      	}
     }
}

function adjust_blog_object_path($designStr, $oldContentStr, $newContentStr)
{
	$str = str_replace("'blog_dir' => '" . $oldContentStr, "'blog_dir' => '" . $newContentStr, $designStr);
	return $str;
}
function adjust_sgblog_links($pageFName, $relativeChange, $newContentDir, $newContentStr)
{
	//feed.php
	$feed_path =  '../' . $newContentDir . '/blog/feed.php';
	if(file_exists($feed_path))
	{
		$feed_str = file_get_contents($feed_path);
		if($feed_str)
		{
			$feed_str = str_replace("require_once('", "require_once('" . $relativeChange, $feed_str);
			file_put_contents($feed_path, $feed_str);
		}
	}
	//blog.xml
	$blog_xml_path = '../' . $newContentDir . '/blog/blog.xml';
	if(file_exists($blog_xml_path))
	{
		$dom = new DomDocument();
		$success = $dom->load($blog_xml_path);
		if($success)
		{
			$nodeList = $dom->getElementsByTagname('base_url');
			$node = $nodeList->item(0);
			switch_text_content($node, '');
			
			$nodeList = $dom->getElementsByTagname('url');
			$node = $nodeList->item(0);
			switch_text_content($node, $pageFName);
			
			$nodeList = $dom->getElementsByTagname('feed_url');
			$node = $nodeList->item(0);
			switch_text_content($node, $newContentStr . '/feed.php'); 
			
			$dom->save($blog_xml_path);
		}
	}			
}

function switch_text_content($node, $str)
{
	$nodeList = $node->childNodes;
	$x =0;
  	$n = $nodeList->item($x);
  	while($n)
  	{
	   if(get_class($n) == 'DOMText')
	   {
	   	$node->removeChild($n);
	   	$node->appendChild(new DOMText($str));
	   	return;
	   }
	}
}	

function cms_maintenance_in_new_page($htmlStr, $relativeChange, $id)
{
	$str =  preg_replace('(pathToRoot\s*=\s*"[^"]*";)',
		        	      'pathToRoot = "' . $relativeChange . '";',
		       	       $htmlStr);
	return preg_replace('(pageid\s*=[^;]*;)',
		               'pageid='. $id . ';',
		               $str);
}


function insert_site_settings($htmlStr)
{
  $path = '../sg_info/site_settings.xml';
  $dom = new DomDocument();
  if(file_exists($path))
  {
    $dom->load($path);
  }
  $nodeList = $dom->getElementsByTagname("ENTRY");
  $x =0;
  $n = $nodeList->item($x);
  while($n)
  {
    $type = $n->getElementsByTagname("TYPE")->item(0)->nodeValue;
    $id   = $n->getElementsByTagname("ID")->item(0)->nodeValue;
    $val = ($type === "string") ? $n->getElementsByTagname("STRING")->item(0)->nodeValue
    	                           : $n->getElementsByTagname("BIGSTRING")->item(0)->nodeValue;
    if($val !== "")
    {
    	  if($id == "head"){ $htmlStr = str_replace('</head>', $val . '</head>', $htmlStr); }
    	  else if($id == "body"){ $htmlStr = str_replace('</body>', $val . '</body>', $htmlStr); }
    	  else{ $htmlStr = insert_meta_setting($htmlStr, $id, $val); }
    }
    
    $x++;
    $n = $nodeList->item($x);
  }
  return $htmlStr;
}

function insert_meta_setting($htmlStr, $id, $val)
{
  $metaname = substr($id, 5);
  $regex  = "(<meta[^>]*name=\"$metaname\"[^>]*>)ms";
  $ok = preg_match($regex, $htmlStr);
  if($ok > 0)
  {
    $htmlStr = preg_replace($regex, sprintf('<meta name="%s" content="%s" />', $metaname, $val), $htmlStr);
  }
  else{ $htmlStr = str_replace("</head>", sprintf('<meta name="%s" content="%s" />
</head>', $metaname, $val), $htmlStr); }
  return $htmlStr;
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
 
function append_page_xml($pageTitle, $pagePath, $folderPath, $design)
{
	$parentNode = null;
	$pageDom = new DomDocument();
	if(file_exists('../sg_info/pages.xml'))
	{
	  $pageDom->load('../sg_info/pages.xml');
	}
	$x = 0;
	$nodeList = $pageDom->getElementsByTagname( ($folderPath !== "") ? 'folder' : 'pages');
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
		$newNode = $pageDom->createElement('item');
		$newNode->setAttribute('name', $pageTitle);
		$newNode->setAttribute('id', $nextid);
		$newNode->setAttribute('aname', base_name($pageTitle));
		$newNode->setAttribute('path', $pagePath);
		$newNode->setAttribute('design', $design->getAttribute('name'));
		$newNode->setAttribute('designfname', basename($design->getAttribute('path'), '.data'));
		$newNode->setAttribute('icon', $design->getAttribute('icon'));
		$newNode->setAttribute('isfile', "true");
		$parentNode->appendChild($newNode);
		
		log_page_timestamp($newNode, ":new-page", "page");
		
		$pageDom->save('../sg_info/pages.xml');

		return($nextid);
	}
	return(-1);
}

function new_page_media_xml($mediaXml, $contentDir, $oldContentStr, $newContentStr)
{
	$xmediaDom = new DomDocument();
	if(is_file($mediaXml) && file_exists($mediaXml))
	{
		$xmediaDom->load($mediaXml);
		$entries = $xmediaDom->getElementsByTagname('entry');
		$x=0;
		$entry = $entries->item($x);
		while($entry)
		{
		  $path = $entry->getAttribute('path');
		  $path  = str_replace($oldContentStr, $newContentStr, $path);
		  $entry->setAttribute('path', $path);
		  $x++;
		  $entry = $entries->item($x);
		}
		$xmediaDom->save($contentDir . '/media.xml');
		log_timestamp(substr($contentDir . '/media.xml', 3), ":new", "cms");
	}
}

function update_editable_xml($contentDir, $newEditable)
{
  $editableDom = new DomDocument();
  $topNode = $editableDom->createElement('entries');
  $editableDom->appendchild($topNode);
  foreach($newEditable as $entry)
  {
 	$ecopy = $editableDom->importNode($entry, true); //second arg?
 	$topNode->appendChild($ecopy);
  }
  $editableDom->save('../' . $contentDir . '/editable.xml');
  log_timestamp($contentDir . '/editable.xml', ":new", "cms");
}

function copy_over_attributes($oldelement, $newelement)
{
  if($oldelement)
  {
  	$attributeArray = array('style', 'onmouseover', 'onmouseout', 'onmousedown', 'onmouseup', 'onclick');
  	foreach($attributeArray as $attr)
  	{
  		if($oldelement->hasAttribute($attr))
  		{
  			$newelement->setAttribute($attr, $oldelement->getAttribute($attr));
  		}
  	}
  }
  return $newelement;
}

function append_menu($menuName, $id, $pagePath, $pageTitle)
{
 	if($menuName && $menuName !== "null" && $menuName !== "")
 	{
 		$menuEntry = find_matching_xml_item('../sg_info/menus.xml', 'menu', 'name', $menuName);
 		$mfile = $menuEntry->getAttribute('file');

		$menuDom = new DomDocument();
		if(file_exists('../' . $mfile))
		{
		  $menuDom->load('../' . $mfile);
		}
		
		$auxfile = dirname($mfile) . '/' .  basename($mfile, '.xml') . '_aux.xml';
		$auxDom = new DomDocument(); $auxLI = null; $auxA = null; $separator = null;
		if(file_exists ('../' . $auxfile))
		{
		  $auxDom->load('../'. $auxfile);
		  $auxLI = $auxDom->getElementsByTagname('li')->item(0);
		  $auxA  = $auxDom->getElementsByTagname('a')->item(0);
		  $separator = $auxDom->getElementsByTagname('separator')->item(0);
		}
		
		
		$nodeList = $menuDom->getElementsByTagname('ul');
		$topNode = $nodeList->item(0);
		$newLI = $menuDom->createElement('li');
		$newLI = copy_over_attributes($auxLI, $newLI);
		$newA =  $menuDom->createElement('a', $pageTitle);
		$newA = copy_over_attributes($auxA, $newA);
		$newA->setAttribute('linktype', 'PAGE');
		$newA->setAttribute('linkval', $id);
		$newA->setAttribute('aux', '');
		$newA->setAttribute('newwin', 'false');
		if($separator)
		{
			$sepLI = $menuDom->createElement('li', $separator);
			$topNode->appendChild($sepLI);
		}
		$topNode->appendChild($newLI);
		$newLI->appendChild($newA);
		$menuDom->save('../' . $mfile, LIBXML_NOXMLDECL);
		log_timestamp($mfile, ":new", "cms");
 	}
}



 
 function new_page($xmlString)
 {
 //0. load xml
 //1. find design
 //2 path to new page?
 //3. make a new content directory
 //4 read in design data
 //5. remap all external paths (if necessary)
 //   handling any unique entries
 //6. update editable.xml entries
 //7. adjust blog links
 //8  update pages.xml with new page
 //9  $pathToRoot 
 //10  save out design data as new file
 
 //11 append page to menu
 

 	//0.
 	$dom = new DomDocument();
 	$dom->loadXML($xmlString);
 	$np = $dom->getElementsByTagname('np')->item(0);
 	//1.
 	$design = find_design_by_name($np->getAttribute('design'));
 	//2.
 	$pageTitle = $np->getAttribute('title');
 	$pageFName = base_name($pageTitle);
 	$pagePath = $np->getAttribute('path') . $pageFName . '.' . 'php'; //<- extension
 	//3.
 	$newContentDir  =  $np->getAttribute('path') . 'sg_' . $pageFName . '_content';
 	mkdir('../' . $newContentDir);
 	//4.
 	$designPath =  '../sg_info/' . $design->getAttribute('path');
 	
 	$designStr = '';
 	if(file_exists($designPath))
 	{
 	  $designStr = file_get_contents($designPath);
 	}
 	$relativeChange = path_to_top($pagePath); 
	//5
	$designFName = basename($designPath, '.data');
  	$oldContentStr = "sg_" . $designFName . "_content"; 
  	$newContentStr = 'sg_' . $pageFName . '_content';
  	$oldContentDir =  "../" . $oldContentStr;
	$designStr = adjust_all_relative_links($designStr, $relativeChange, $oldContentStr, $oldContentDir, $newContentStr, $newContentDir);
 	//5.1
 	copy_unique_items_for_new_page($oldContentDir, $newContentDir, $relativeChange, $oldContentStr,  $newContentStr);
  	
  	//6.
  	adjust_xml_links($newContentDir, $oldContentStr, "editable.xml", "item", "file");
  	adjust_xml_links($newContentDir, $oldContentStr, "media.xml", "entry", "path");
     adjust_gallery_option_links($newContentDir, $relativeChange);
  	//7.
  	adjust_blog_links($newContentDir, $oldContentStr, $newContentStr);
  	$designStr = adjust_blog_object_path($designStr, $oldContentStr, $newContentStr);
  	adjust_sgblog_links($pageFName . '.php', $relativeChange, $newContentDir, $newContentStr);
  	//8.
 	$id = append_page_xml($pageTitle, $pagePath, $np->getAttribute('path'), $design);
  	//9
 	$designStr = cms_maintenance_in_new_page($designStr, $relativeChange, $id);
 	$designStr = insert_site_settings($designStr);
 	
 	//10
 	file_put_contents( '../' . $pagePath, $designStr); 	
 	log_timestamp($pagePath, ":new", "cms");
 	
 	//11.
 	$appendM = $np->getAttribute('append');
 	if($appendM === "true")
 	{
 		append_menu($np->getAttribute('menu'), $id, $pagePath, $pageTitle);
 	}
 	
 	log_timestamp($newContentDir, ":mkdir", "cms"); 		
 }
 



	
 
 // MAIN ENTRY
 header('Content-type: text/xml');
 
 
 $lzpostbodyvar = $_POST['lzpostbody'];
 //$lzpostbodyvar = '<npbase><np title=\"shazbot\" design=\"Menus-basic\" append=\"true\" menu=\"simple vertical-menu\" path=\"\"/></npbase>'; 
 

 
 if($lzpostbodyvar)
 {
	if(get_magic_quotes_gpc())
 	{
    	  $lzpostbodyvar = stripFormSlashes($lzpostbodyvar);
 	}
 	//trigger_error("lzpostbody: " . $lzpostbodyvar, E_USER_NOTICE);
 
	$pagesXML = new DOMDocument();
	$pagesXML->load('../sg_info/pages.xml');
	$pageNodeList =  $pagesXML->getElementsByTagname('item');
	$rc = null;
 
 	new_page($lzpostbodyvar);
 }
 

 echo "<nothing />";

 //echo $lzpostbodyvar;
 //file_put_contents('log.txt', $_POST['lzpostbody']);
?>