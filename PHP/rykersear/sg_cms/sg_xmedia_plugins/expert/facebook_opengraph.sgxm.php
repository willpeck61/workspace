<?php
 //Facebook OpenGraph Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=facebook_opengraph_xmc
 //Place a Facebook OpenGraph descriptor into your page. You should understand what the Graph API is before using. http://developers.facebook.com/docs/opengraph/

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("CHECK_OUTPUT_ITEM"))
{
 function CHECK_OUTPUT_ITEM($OPTIONSSXML, $SYM)
{
return BOOL_STRING(PLUGIN_OPTION_VAL($OPTIONSSXML, $SYM)) ? FORMAT_S("<meta property=\"~A\" content=\"~A\" />", $SYM, PLUGIN_OPTION_VAL($OPTIONSSXML, $SYM)) : FORMAT_S("");;
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
');  fwrite($file, 'echo \'<!-- \';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
'); /*literal*/ fwrite($file, 'echo \'<meta property="og:title" content="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "og:title") . '\';');  /*literal*/ fwrite($file, 'echo \'" />
<meta property="og:type" content="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "og:type") . '\';');  /*literal*/ fwrite($file, 'echo \'" /> 
<meta property="og:image" content="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "og:image") . '\';');  /*literal*/ fwrite($file, 'echo \'" />
<meta property="og:url" content="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "og:url") . '\';');  /*literal*/ fwrite($file, 'echo \'" />\';');  /*expression*/ fwrite($file, 'echo \'' . CHECK_OUTPUT_ITEM($OPTIONSDOM, "og:description") . '\';');  /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo \'' . CHECK_OUTPUT_ITEM($OPTIONSDOM, "og:site-name") . '\';');  /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo \'' . CHECK_OUTPUT_ITEM($OPTIONSDOM, "fb:admins") . '\';');  /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo \'' . CHECK_OUTPUT_ITEM($OPTIONSDOM, "fb:app_id") . '\';');  /*literal*/ fwrite($file, 'echo \'\';');   fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="Facebook OpenGraph" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=facebook_opengraph_xmc" type="expert/facebook_opengraph.sgxm.php" >
 <info>Place a Facebook OpenGraph descriptor into your page. You should understand what the Graph API is before using. http://developers.facebook.com/docs/opengraph/</info>

<option name="required" type="static-text" value="" />
<option name="og:title" type="string" value="" />
<option name="og:type" type="string" value="" />
<option name="og:image" type="online-url" value="" />
<option name="og:url" type="online-url" value="" />
<option name="optional" type="static-text" value="" />
<option name="og:description" type="text" value="" />
<option name="og:site-name" type="string" value="" />
<option name="fb:admins" type="text" value="" />
<option name="fb:app_id" type="string" value="" />
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