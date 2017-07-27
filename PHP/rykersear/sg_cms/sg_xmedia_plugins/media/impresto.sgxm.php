<?php
 //Impresto Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=impresto_xmc
 //Place the Impresto presentation onto a page

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
'); /*literal*/ fwrite($file, 'echo \'<iframe src="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "url", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" scrolling="no" onload="this.contentWindow.focus()" allowTransparency="true"></iframe>\';');   fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
'); /*literal*/ fwrite($file, 'echo \'<style type="text/css" media="screen,print,projection">#\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'{ height:\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'px; border:none;}</style>\';');   fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="Impresto" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=impresto_xmc" type="media/impresto.sgxm.php" >
 <info>Place the Impresto presentation onto a page</info>

 <option name="url" type="url" value="" extensions="php html" />
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