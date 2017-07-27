<?php
 //flv Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=flv_xmc
 //Place an .flv file on your page

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
'); /*literal*/ fwrite($file, 'echo \'<object type="application/x-shockwave-flash" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" data="\';');  /*expression*/$pp = '\' . PLUGIN_PATH("' . 'sg_cms/sg_xmedia_plugins/media/flvplayer.swf' . '", 0) . \'';
 fwrite($file, 'echo \'' . $pp . '\';');  /*literal*/ fwrite($file, 'echo \'" ><param name="quality" value="high" /><param name="wmode" value="transparent" /><param name="movie" value="\';');  /*expression*/$pp = '\' . PLUGIN_PATH("' . 'sg_cms/sg_xmedia_plugins/media/flvplayer.swf' . '", 0) . \'';
 fwrite($file, 'echo \'' . $pp . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="flashvars" value="autoplay=\';');  /*expression*/ fwrite($file, 'echo \'' . BOOL_NUMBER(PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay")) . '\';');  /*literal*/ fwrite($file, 'echo \'&amp;loop=\';');  /*expression*/ fwrite($file, 'echo \'' . BOOL_NUMBER(PLUGIN_OPTION_VAL($OPTIONSDOM, "loop")) . '\';');  /*literal*/ fwrite($file, 'echo \'&amp;showcontroller=\';');  /*expression*/ fwrite($file, 'echo \'' . BOOL_NUMBER(PLUGIN_OPTION_VAL($OPTIONSDOM, "show controller")) . '\';');  /*literal*/ fwrite($file, 'echo \'&amp;flvpath=../../../\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url") . '\';');  /*literal*/ fwrite($file, 'echo \'" /></object>\';');   fwrite($file, ' echo \' <!--\';
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
    echo '<plugin name="flv" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=flv_xmc" type="media/flv.sgxm.php" >
 <info>Place an .flv file on your page</info>

<option name="url" type="upload" value="" extensions="flv f4v" />
<option name="autoplay" type="boolean" value="true" />
<option name="loop" type="boolean" value="true" />
<option name="show controller" type="boolean" value="true" />
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