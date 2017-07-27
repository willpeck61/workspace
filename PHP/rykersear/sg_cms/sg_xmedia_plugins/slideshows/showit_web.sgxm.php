<?php
 //showit_web Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=showitweb_xmc
 //Set the URL to the  &lt;b&gt;index.html&lt;/b&gt; file created by ShowIt Web

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
fwrite($file, '');
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
');$p = 'PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0)';
if( REGEX_MATCH("(iframe\\.html)", ' . $p . ') )
{
  /*literal*/ fwrite($file, 'echo \'<iframe src="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" frameborder="0" scrolling="no" />\';'); }
else
{
  /*literal*/ fwrite($file, 'echo \'<object type="application/x-shockwave-flash" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" data="\';');  /*expression*/ fwrite($file, 'echo \'' . JOIN_STRING(REGEX_MATCH("(.*/)", PLUGIN_OPTION_VAL($OPTIONSDOM, "url")), "showit.swf") . '\';');  /*literal*/ fwrite($file, 'echo \'" ><param name="movie" value="\';');  /*expression*/ fwrite($file, 'echo \'' . JOIN_STRING(REGEX_MATCH("(.*/)", PLUGIN_OPTION_VAL($OPTIONSDOM, "url")), "showit.swf") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="flashvars" value="showit_path=\';');  /*expression*/ fwrite($file, 'echo \'' . REGEX_MATCH("(.*/)", PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) . '\';');  /*literal*/ fwrite($file, 'echo \'&amp;showit_embed=\';');  /*expression*/ fwrite($file, 'echo \'' . REGEX_MATCH("(.*/)", PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) . '\';');  /*literal*/ fwrite($file, 'echo \'|\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'|\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'|\';');  /*expression*/ fwrite($file, 'echo \'' . BOOL_NUMBER(PLUGIN_OPTION_VAL($OPTIONSDOM, "embed")) . '\';');  /*literal*/ fwrite($file, 'echo \'|\';');  /*expression*/ fwrite($file, 'echo \'' . BOOL_NUMBER(PLUGIN_OPTION_VAL($OPTIONSDOM, "ask to play")) . '\';');  /*literal*/ fwrite($file, 'echo \'|\';');  /*expression*/ fwrite($file, 'echo \'' . BOOL_NUMBER(PLUGIN_OPTION_VAL($OPTIONSDOM, "disable net")) . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="wmode" value="transparent" /><param name="salign" value="LT" /><param name="menu" value="false" /><param name="loop" value="false" /><param name="quality" value="best" /><param name="bgcolor" value="\';');  /*expression*/ fwrite($file, 'echo \'' . REGEX_MATCH("body {[^}]*background-color:([^;]*)", FETCH_URL(PLUGIN_OPTION_VAL($OPTIONSDOM, "url"), "obj", $ENVIRODOM)) . '\';');  /*literal*/ fwrite($file, 'echo \'" /></object>\';'); }  fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
'); /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">#\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'{ height:\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'px; border:none; background-color:\';');  /*expression*/ fwrite($file, 'echo \'' . REGEX_MATCH("body {[^}]*background-color:([^;]*)", FETCH_URL(PLUGIN_OPTION_VAL($OPTIONSDOM, "url"), "css", $ENVIRODOM)) . '\';');  /*literal*/ fwrite($file, 'echo \'}</style>\';');   fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="showit_web" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=showitweb_xmc" type="slideshows/showit_web.sgxm.php" >
 <info>Set the URL to the  &lt;b&gt;index.html&lt;/b&gt; file created by ShowIt Web</info>

 <option name="url" type="url" value="" extensions="html" />
 <option name="embed" type="boolean" value="true" />
 <option name="ask to play" type="boolean" value="false" />
 <option name="disable net" type="boolean" value="false" />
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