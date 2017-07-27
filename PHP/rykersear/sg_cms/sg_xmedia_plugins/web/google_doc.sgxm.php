<?php
 //google doc Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=google_doc_xmc
 //Place Google Documents onto your page. If using the Remote CMS then the page will display the latest version whenever the google document is updated.

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("EXTRACT_G_H_D"))
{
 function EXTRACT_G_H_D($URL, $DIVNAME, $ENVIRO)
{
return REGEX_REMOVE("background-color:[^;]*;", REGEX_REMOVE("\\.editor[^{]*{[^}]*}", REGEX_REMOVE("\\* html[^{]*{[^}]*}", REGEX_REMOVE("body[^}]*}", REGEX_MATCH("<style[^>]*>([^<]*)", FETCH_URL($URL, "head", $ENVIRO))))));
 }
}
if(!function_exists("EXTRACT_ALT_G_H_D"))
{
 function EXTRACT_ALT_G_H_D($URL, $DIVNAME, $ENVIRO)
{
return REGEX_REMOVE("background-color:[^;]*;", REGEX_REMOVE("#footer[^{]*{[^}]*}", REGEX_REMOVE("iframe[^{]*{[^}]*}", REGEX_REMOVE("\\* html[^{]*{[^}]*}", REGEX_REMOVE("body[^}]*}", REGEX_MATCH("<style[^>]*>([^<]*)", FETCH_URL($URL, "head", $ENVIRO)))))));
 }
}
if(!function_exists("EXTRACT_G_B_D"))
{
 function EXTRACT_G_B_D($URL, $ENVIRO)
{
return REGEX_REPLACE_LAMBDA($ENVIRO, "\"(file?[^\"]*)\"", REGEX_REMOVE(" id=[^ ^>]*", REGEX_REMOVE(" id=\"[^\"]*\"", REGEX_REPLACE_LAMBDA($ENVIRO, "(<[^>]*>)", REGEX_MATCH("<div id=\"doc-contents\">(.*)</div>\\s*<div id=\"google-view-footer\">", FETCH_URL($URL, "body", $ENVIRO)), 0, "string_downcase"))), 0, "remap_link");
 }
}
if(!function_exists("REMAP_LINK"))
{
 function REMAP_LINK($FILEID)
{
return JOIN_STRING(JOIN_STRING("\"http://docs.google.com/", $FILEID), "\"");
 }
}
if(!function_exists("EXTRACT_ALT_G_B_D"))
{
 function EXTRACT_ALT_G_B_D($URL, $ENVIRO)
{
return REGEX_REPLACE("<style ", REGEX_REPLACE_LAMBDA($ENVIRO, "\"(pubimage?[^\"]*)\"", REGEX_REPLACE_LAMBDA($ENVIRO, "href=\"#bookmark=([^\"]*)", REGEX_REMOVE(" id=[^ ^>]*", REGEX_REMOVE(" id=\"[^\"]*\"", REGEX_MATCH("<div id=\"contents\">(.*)</div>\\s*<div id=\"footer\">", FETCH_URL($URL, "body", $ENVIRO)))), 0, "remap_alt_bookmark"), 0, "remap_alt_link"), "<style scoped=\"scoped\" ");
 }
}
if(!function_exists("REMAP_ALT_BOOKMARK"))
{
 function REMAP_ALT_BOOKMARK($ID)
{
return JOIN_STRING("href=\"#", $ID);
 }
}
if(!function_exists("REMAP_ALT_LINK"))
{
 function REMAP_ALT_LINK($FILEID)
{
return JOIN_STRING(JOIN_STRING("\"http://docs.google.com/document/", $FILEID), "\"");
 }
}
 function output_xmc($OPTIONSDOM, $ENVIRODOM)
 {
   global $pathToRoot;
   
   $pathvar = $_POST['path'];
   $plugNode = $OPTIONSDOM->getElementsByTagname('plugin')->item(0);
   $plugNode->setAttribute('type', $_POST['plugtype']);

   $pageInfoNode = $ENVIRODOM->getElementsByTagname('page_info')->item(0);
   $dpath = $pageInfoNode->getAttribute('path_to_page_dir');
   $pathToRoot = path_to_root($dpath);
   
   $file = fopen('../../../' . $pathvar, 'w');
   fwrite($file, '<?php
');fwrite($file, 'include_once( $sg_root . \'sg_cms/sg_xmedia_plugins/plugin_utilities.php\');
');
fwrite($file, 'if(!function_exists("EXTRACT_G_H_D"))
{
 function EXTRACT_G_H_D($URL, $DIVNAME, $ENVIRO)
{
return REGEX_REMOVE("background-color:[^;]*;", REGEX_REMOVE("\\.editor[^{]*{[^}]*}", REGEX_REMOVE("\\* html[^{]*{[^}]*}", REGEX_REMOVE("body[^}]*}", REGEX_MATCH("<style[^>]*>([^<]*)", FETCH_URL($URL, "head", $ENVIRO))))));
 }
}
if(!function_exists("EXTRACT_ALT_G_H_D"))
{
 function EXTRACT_ALT_G_H_D($URL, $DIVNAME, $ENVIRO)
{
return REGEX_REMOVE("background-color:[^;]*;", REGEX_REMOVE("#footer[^{]*{[^}]*}", REGEX_REMOVE("iframe[^{]*{[^}]*}", REGEX_REMOVE("\\* html[^{]*{[^}]*}", REGEX_REMOVE("body[^}]*}", REGEX_MATCH("<style[^>]*>([^<]*)", FETCH_URL($URL, "head", $ENVIRO)))))));
 }
}
if(!function_exists("EXTRACT_G_B_D"))
{
 function EXTRACT_G_B_D($URL, $ENVIRO)
{
return REGEX_REPLACE_LAMBDA($ENVIRO, "\"(file?[^\"]*)\"", REGEX_REMOVE(" id=[^ ^>]*", REGEX_REMOVE(" id=\"[^\"]*\"", REGEX_REPLACE_LAMBDA($ENVIRO, "(<[^>]*>)", REGEX_MATCH("<div id=\"doc-contents\">(.*)</div>\\s*<div id=\"google-view-footer\">", FETCH_URL($URL, "body", $ENVIRO)), 0, "string_downcase"))), 0, "remap_link");
 }
}
if(!function_exists("REMAP_LINK"))
{
 function REMAP_LINK($FILEID)
{
return JOIN_STRING(JOIN_STRING("\"http://docs.google.com/", $FILEID), "\"");
 }
}
if(!function_exists("EXTRACT_ALT_G_B_D"))
{
 function EXTRACT_ALT_G_B_D($URL, $ENVIRO)
{
return REGEX_REPLACE("<style ", REGEX_REPLACE_LAMBDA($ENVIRO, "\"(pubimage?[^\"]*)\"", REGEX_REPLACE_LAMBDA($ENVIRO, "href=\"#bookmark=([^\"]*)", REGEX_REMOVE(" id=[^ ^>]*", REGEX_REMOVE(" id=\"[^\"]*\"", REGEX_MATCH("<div id=\"contents\">(.*)</div>\\s*<div id=\"footer\">", FETCH_URL($URL, "body", $ENVIRO)))), 0, "remap_alt_bookmark"), 0, "remap_alt_link"), "<style scoped=\"scoped\" ");
 }
}
if(!function_exists("REMAP_ALT_BOOKMARK"))
{
 function REMAP_ALT_BOOKMARK($ID)
{
return JOIN_STRING("href=\"#", $ID);
 }
}
if(!function_exists("REMAP_ALT_LINK"))
{
 function REMAP_ALT_LINK($FILEID)
{
return JOIN_STRING(JOIN_STRING("\"http://docs.google.com/document/", $FILEID), "\"");
 }
}
');
fwrite($file, ' //START_OPTIONS
    $options=\'');
   fwrite($file, $OPTIONSDOM->saveXML());
   fwrite($file, '\';
 //END_OPTIONS
 ');
fwrite($file, 'if(isset($_GET[\'options\']))
{
  header(\'Content-type: text/xml\');
  echo($options);
}
else if($sg_mode == \'body\')
{
  echo \'--> \';
');if( REGEX_MATCH("(/pub\\?)", PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) )
{
  /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo EXTRACT_ALT_G_B_D(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '"'); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'\';'); }
else
{
  /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo EXTRACT_G_B_D(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '"'); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'\';'); }  fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
');if( REGEX_MATCH("(/pub\\?)", PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) )
{
  /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">\';');  /*expression*/ fwrite($file, 'echo EXTRACT_ALT_G_H_D(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '"'); fwrite($file, ', '); fwrite($file,  '$SG_DIVNAME' ); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'</style>\';'); }
else
{
  /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">\';');  /*expression*/ fwrite($file, 'echo EXTRACT_G_H_D(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '"'); fwrite($file, ', '); fwrite($file,  '$SG_DIVNAME' ); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'</style>\';'); }  fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="google doc" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=google_doc_xmc" type="web/google_doc.sgxm.php" >
 <info>Place Google Documents onto your page. If using the Remote CMS then the page will display the latest version whenever the google document is updated.</info>

<option name="url"  type="online-url"  value="" extensions="html"  />
</plugin>';
 }
 else if(check_post_values() && verify_login())
 { 
 
   if(get_magic_quotes_gpc())
   {
 	$_POST = stripFormSlashes($_POST);
   }
   

   $OPTIONSDOM = new DomDocument();
   $OPTIONSDOM->loadXML($_POST['options']);
   $ENVIRODOM  = new DomDocument();
   $ENVIRODOM->loadXML($_POST['page_info']);
   
   output_xmc($OPTIONSDOM, $ENVIRODOM);

   update_media_xml($OPTIONSDOM);

   log_xmc_timestamp( $_POST['path'] );
   
   echo '<nothing />';  //the reply
 }

?>