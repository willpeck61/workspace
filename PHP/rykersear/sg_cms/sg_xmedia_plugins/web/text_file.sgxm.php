<?php
 //text file Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=text_file_xmc
 //import text from an external file.

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("EXTRACT_TEXT"))
{
 function EXTRACT_TEXT($URL, $ENVIRO)
{
return XML_ESCAPE(FETCH_URL($URL, "body", $ENVIRO));
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
fwrite($file, 'if(!function_exists("EXTRACT_TEXT"))
{
 function EXTRACT_TEXT($URL, $ENVIRO)
{
return XML_ESCAPE(FETCH_URL($URL, "body", $ENVIRO));
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
'); /*literal*/ fwrite($file, 'echo \'\';');  /*expression*/ fwrite($file, 'echo EXTRACT_TEXT(');fwrite($file, '"' .  PLUGIN_OPTION_VAL($OPTIONSDOM, "file") . '"'); fwrite($file, ', '); fwrite($file,  '$ENVIRODOM' ); fwrite($file, ');');  fwrite($file, 'echo \'\';');  /*literal*/ fwrite($file, 'echo \'\';');   fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
');  fwrite($file, 'echo \'<!-- \';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="text file" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=text_file_xmc" type="web/text_file.sgxm.php" >
 <info>import text from an external file.</info>

 <option name="file" type="upload" value="" extensions="txt" />
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