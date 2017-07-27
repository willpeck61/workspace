<?php
 //video Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=video_xmc
 //General video importer. Use it for a variety of video formats

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
'); /*literal*/ fwrite($file, 'echo \'<!--[if  IE]> <object type="video/x-mpeg" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B"  codebase="http://www.apple.com/qtactivex/qtplugin.cab" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" class="slide-active-only"><param name="scale" value="aspect" /><param name="src" value="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="autoplay" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="loop" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "loop") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="controller" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "show controller") . '\';');  /*literal*/ fwrite($file, 'echo \'" /></object> <![endif]-->
<!--[if !IE]><!--> <object type="video/x-mpeg" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" data="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" class="slide-active-only"><param name="scale" value="aspect" /><param name="src" value="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="autoplay" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="loop" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "loop") . '\';');  /*literal*/ fwrite($file, 'echo \'" /><param name="controller" value="\';');  /*expression*/ fwrite($file, 'echo \'' . PLUGIN_OPTION_VAL($OPTIONSDOM, "show controller") . '\';');  /*literal*/ fwrite($file, 'echo \'" /></object> <!--<![endif]-->\';');   fwrite($file, ' echo \' <!--\';
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
    echo '<plugin name="video" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=video_xmc" type="media/video.sgxm.php" >
 <info>General video importer. Use it for a variety of video formats</info>

<option name="url" type="upload" value="" extensions="*" />
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