<?php
/* -------------------------------------*/
/*
    Get Upload Data.   
        
*/
/* -------------------------------------*/

/* --------- UTILITY FUNCTIONS ------------- */
function group_by($imagefiles, $transformF)
{
	$groups = array();
	foreach($imagefiles as $f)
	{
		$ename = $transformF($f);
		if(isset($groups[$ename]))
		{ 
			$group = $groups[$ename]; 
			$group[] = $f; 
			$groups[$ename] = array_unique($group);
		}
		else{  $group = array($f); 
			  $groups[$ename] = $group; }
		
	}
	ksort($groups, SORT_STRING);
	return $groups;
}

/* _________________________________________ */
global $gxml_url; //global.  Set by side effect.

function get_stored_upload_files($uploadidvar) //has side effect, sets $gxml_url and cleans up the  upload log file (m/id.txt)
{
	global $gxml_url;
	$upload_log_path = 'm/' . $uploadidvar . '.txt';
	if(is_file($upload_log_path) && file_exists($upload_log_path))
	{
		$lines = file($upload_log_path, FILE_IGNORE_NEW_LINES);
		$gxml_url = array_shift($lines);
		unlink($upload_log_path);
		return $lines;
	}
}

function get_metadata($gxmlpath)
{
	//returns an array [$dom, $node].
	$dom = new DomDocument();
	if(is_file($gxmlpath) && file_exists($gxmlpath))
	{
		$dom->load($gxmlpath);
		$toplevelnodes = $dom->firstChild->childNodes;
		$x =0;
		$node = $toplevelnodes->item($x);
		while($node)
		{
			if(($node->nodeType != XML_TEXT_NODE) && ($node->tagName === "metadataentries"))
			{
				return Array($dom, $node); 
			}
			$x++;
			$node = $toplevelnodes->item($x);
		}
	}
	return null;
}

function echo_song_upload_data($uploadidvar)
{
	global $gxml_url;
	$songfiles = get_stored_upload_files($uploadidvar); //global $gxml_url is now set 
	
	preg_match( "(sg_[^_]*_content)", $gxml_url, $matches, PREG_OFFSET_CAPTURE);
	$start = $matches[0][1];
	
	echo "<songs>\n";
	foreach($songfiles as $song)
	{
		$sub = encode_path_for_url(substr($song, $start));
		
		echo "<song><url>$sub</url></song>\n";
	}
	echo "</songs>";
}
function exhibit_name($f)
{
	$fname = basename($f);
	$arr = preg_split("/--/", $fname);
	return $arr[0];
}
function view_name($f)
{
	$fname = basename($f);
	$arr = preg_split("/--/", $fname);
	if(count($arr)>1){ return $arr[1]; }
	return $arr[0];
}

function encode_path_for_url($f)
{
  $arr = explode('/', $f);
  $arr = array_map('rawurlencode', $arr);
  return implode('/', $arr);
}


function echo_subview($subview)
{
	
        preg_match( "(sg_[^_]*_content)", $subview, $matches, PREG_OFFSET_CAPTURE);
	$content_dir_start = $matches[0][1];
	$path = encode_path_for_url(substr($subview, $content_dir_start));

	if(preg_match("/\/bigbox\//", $subview)){ echo_log("<bigbox><url>$path</url></bigbox>"); }
	else if(preg_match("/\/picturebox\//", $subview)){ echo_log("<picturebox><url>$path</url></picturebox>"); }
	else if(preg_match("/\/thumbnail\//", $subview)){ echo_log("<thumbnail><url>$path</url></thumbnail>"); }
	else if(preg_match("/\/viewthumb\//", $subview)){ echo_log("<viewthumb><url>$path</url></viewthumb>"); }
}

function echo_metadata($metadata)
{
	if($metadata)
	{
		$dom = $metadata[0];
		$node = $metadata[1];
		echo $dom->saveXML($node);
	}
} 



function echo_views($group, $metadata)
{
	$subgroups = group_by($group, 'view_name');
	echo( "<views>\n" );
	foreach($subgroups as $viewname => $subviews)
	{
		echo("<view><name>$viewname</name>\n");
		echo_metadata($metadata);
		foreach($subviews as $subview)
		{
			echo_subview($subview);
		}
		echo("</view>\n");
	}
	echo( "</views>\n" );
}

function echo_image_upload_data($uploadidvar, $dmodevar)
{
	global $gxml_url;
	$imagefiles = get_stored_upload_files($uploadidvar);  //global $gxml_url is now set
	$metadata = get_metadata('../' . $gxml_url);
	if($dmodevar == 'view')
	{
		echo_views($imagefiles, $metadata);
	}
	else
	{
		$groups = group_by($imagefiles, 'exhibit_name');
		echo "<exhibits>\n";
		foreach($groups as $ename => $group)
		{
			echo( "<exhibit><name>$ename</name>\n");
			echo_views($group, $metadata);
			echo( "</exhibit>\n");
		}
		echo("</exhibits>");
	}
}

function log_msg($str)
 {
   global $logf;
   if($logf){ fwrite($logf, $str); fwrite($logf, '
'); }
 }

function echo_log($str)
{
	log_msg($str);
	echo $str;
}



//MAIN ENTRY

header('Content-type: text/xml');

global $logf;
//$logf = fopen('glog.txt', 'w');

$uploadidvar = isset($_GET['uploadid']) ?  $_GET['uploadid'] : null;
$dmodevar = isset($_GET['dmode']) ?  $_GET['dmode'] : null;

log_msg("uploadid: $uploadidvar   dmode: $dmodevar");

if($dmodevar == "song")
{
	echo_song_upload_data($uploadidvar);
}
else if($uploadidvar)
{
	echo_image_upload_data($uploadidvar, $dmodevar);
}

if($logf){ fclose($logf); }

?>