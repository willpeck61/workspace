<?php
 //iframe Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=iframe_xmc
 //Import the content into an iframe

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
');if( BOOL(REGEX_MATCH("(override)", PLUGIN_OPTION_VAL($OPTIONSDOM, "height"))) )
{
  /*literal*/ fwrite($file, 'echo \'<iframe src="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "override height") . '\';');  /*literal*/ fwrite($file, 'echo \'" scrolling="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "scrolling") . '\';');  /*literal*/ fwrite($file, 'echo \'" allowTransparency="true" frameborder="0"></iframe>\';'); }
else
{
  /*literal*/ fwrite($file, 'echo \'<iframe src="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" scrolling="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "scrolling") . '\';');  /*literal*/ fwrite($file, 'echo \'" allowTransparency="true" frameborder="0"></iframe>\';'); }  fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
');if( BOOL(REGEX_MATCH("(override)", PLUGIN_OPTION_VAL($OPTIONSDOM, "height"))) )
{
  /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">#\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'{ height:\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "override height") . '\';');  /*literal*/ fwrite($file, 'echo \'px; border:none; }</style>\';'); }
else
{
  /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">#\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'{ height:\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'px; border:none; }</style>\';'); }  fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="iframe" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=iframe_xmc" type="web/iframe.sgxm.php" >
 <info>Import the content into an iframe</info>

 <option name="url" type="url" value="" extensions="*" />
 <option name="height"  type="choice" value="use layer">
  <item value="use layer" /><item value="override" />
 </option>
 <option name="scrolling" type="choice" value="auto">
   <item value="auto" /><item value="yes" /><item value="no" />
 </option>
 <option name="override height" type="number" value="" />
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