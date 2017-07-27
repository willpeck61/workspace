<?php
 //flash Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=flash_xmc
 //Place .swf files on your web page.

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("SCREEN_BACKGROUND"))
{
 function SCREEN_BACKGROUND($OPTIONSSXML)
{
return BOOL(PLUGIN_OPTION_VAL($OPTIONSSXML, "transparent background")) ? transparent : opaque;;
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
'); /*literal*/ fwrite($file, 'echo \'<object type="application/x-shockwave-flash" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" data="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" ><param name="quality" value="high" /><param name="wmode" value="\';');  /*expression*/ fwrite($file, 'echo \'' . SCREEN_BACKGROUND($OPTIONSDOM) . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="movie" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="play" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="loop" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "loop") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="flashvars" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "flashvars") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="base" value="." /><param name="allowfullscreen" value="true" /></object>\';');   fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
'); /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">#\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'{ height:\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'px;}</style>\';');   fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="flash" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=flash_xmc" type="media/flash.sgxm.php" >
 <info>Place .swf files on your web page.</info>

<option name="url" type="url" value="" extensions="swf" />
<option name="autoplay" type="boolean" value="true" />
<option name="loop" type="boolean" value="true" />
<option name="transparent background" type="boolean" value="true" />
<option name="flashvars" type="text" value="" />
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