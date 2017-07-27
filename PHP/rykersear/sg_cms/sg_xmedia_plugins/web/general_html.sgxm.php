<?php
 //general html Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=general_html_xmc
 //This importer will import simple html files. They should be well formed valid html files.

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

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
fwrite($file, 'if(!function_exists("EXTRACT_HTML_H_D"))
{
 function EXTRACT_HTML_H_D($URL, $ENVIRO, $DIVNAME)
{
return REGEX_REPLACE_LAMBDA($ENVIRO, "src=\"([^\"]*)\"", REGEX_REPLACE_LAMBDA($ENVIRO, "href=\"([^\"]*)\"", REGEX_REPLACE("body", REGEX_REMOVE("html", REGEX_REMOVE("<title[^>]*>.*</title>", REGEX_REMOVE("<meta http-equiv[^>]*>", REGEX_MATCH("<head>(.*)</head>", FETCH_URL($URL, "head", $ENVIRO))))), JOIN_STRING("#", $DIVNAME)), 0, "remap_link", "href=", REGEX_MATCH("(.*/)", $URL)), 0, "remap_link", "src=", REGEX_MATCH("(.*/)", $URL));
 }
}
if(!function_exists("EXTRACT_HTML_B_D"))
{
 function EXTRACT_HTML_B_D($URL, $ENVIRO)
{
return REGEX_REPLACE_LAMBDA($ENVIRO, "data=\"([^\"]*)\"", REGEX_REPLACE_LAMBDA($ENVIRO, "href=\"([^\"]*)\"", REGEX_REPLACE_LAMBDA($ENVIRO, "src=\"([^\"]*)\"", REGEX_MATCH("<body[^>]*>(.*)</body>", FETCH_URL($URL, "body", $ENVIRO)), 0, "remap_link", "src=", REGEX_MATCH("(.*/)", $URL)), 0, "remap_link", "href=", REGEX_MATCH("(.*/)", $URL)), 0, "remap_link", "data=", REGEX_MATCH("(.*/)", $URL));
 }
}
if(!function_exists("REMAP_LINK"))
{
 function REMAP_LINK($STR, $LINKSTR, $DURL)
{
if( PROTOCOLEDP($STR) )
{
 return FORMAT_S("~A\"~A\"", $LINKSTR, $STR);
}
else
{
 return REGEX_MATCH("^(\\#)", $STR) ? FORMAT_S("~A\"~A\"", $LINKSTR, $STR) : FORMAT_S("~A\"~A~A\"", $LINKSTR, $DURL, $STR);};
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
'); /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo EXTRACT_HTML_B_D(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '"'); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'\';');   fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
'); /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo EXTRACT_HTML_H_D(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '"'); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ', '); fwrite($file,  '$SG_DIVNAME' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'\';');   fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="general html" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=general_html_xmc" type="web/general_html.sgxm.php" >
 <info>This importer will import simple html files. They should be well formed valid html files.</info>

<option name="url"  type="url"  value="" extensions="html htm php asp shtml jsp"  />
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