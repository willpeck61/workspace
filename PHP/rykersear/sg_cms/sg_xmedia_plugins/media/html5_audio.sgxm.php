<?php
 //html5 audio Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=html5_audio_xmc
 //Audio importer for HTML 5. MP3 for most browsers. OGG for Firefox

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("AUDIO_TYPE"))
{
 function AUDIO_TYPE($URL)
{
return BOOL_STRING(REGEX_MATCH("(\\.mp3$)", $URL)) ? mpeg : STRING_DOWNCASE(REGEX_MATCH("\\.([^.]*)$", $URL));;
 }
}
if(!function_exists("LOOP"))
{
 function LOOP($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? loop : JOIN_STRING("", "");;
 }
}
if(!function_exists("AUTOPLAY"))
{
 function AUTOPLAY($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? autoplay : JOIN_STRING("", "");;
 }
}
if(!function_exists("SHOW_CONTROLLER"))
{
 function SHOW_CONTROLLER($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? controls : JOIN_STRING("", "");;
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
'); /*literal*/ fwrite($file, 'echo \'  <audio id="audio-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" \';');  /*expression*/ fwrite($file, 'echo \'' . SHOW_CONTROLLER(PLUGIN_OPTION_VAL($OPTIONSDOM, "show controller")) . '\';');  /*literal*/ fwrite($file, 'echo \' \';');  /*expression*/ fwrite($file, 'echo \'' . AUTOPLAY(PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay")) . '\';');  /*literal*/ fwrite($file, 'echo \' \';');  /*expression*/ fwrite($file, 'echo \'' . LOOP(PLUGIN_OPTION_VAL($OPTIONSDOM, "loop")) . '\';');  /*literal*/ fwrite($file, 'echo \' preload="auto" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'">
    <source src="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "audio file", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" type="audio/\';');  /*expression*/ fwrite($file, 'echo \'' . AUDIO_TYPE(PLUGIN_OPTION_VAL($OPTIONSDOM, "audio file")) . '\';');  /*literal*/ fwrite($file, 'echo \'">
    <source src="\';');  /*expression*/$p = '\' . PLUGIN_PATH("' . PLUGIN_OPTION_VAL($OPTIONSDOM, "ogg file", $ENVIRODOM) . '", 0) . \'';
 fwrite($file, 'echo \'' . $p . '\';');  /*literal*/ fwrite($file, 'echo \'" type="audio/\';');  /*expression*/ fwrite($file, 'echo \'' . AUDIO_TYPE(PLUGIN_OPTION_VAL($OPTIONSDOM, "ogg file")) . '\';');  /*literal*/ fwrite($file, 'echo \'">
  </audio>\';');   fwrite($file, ' echo \' <!--\';
}');fwrite($file, 'else if($sg_mode == \'head\')
  {
  
  echo \'--> \';
');if( BOOL(PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay")) )
{
  /*literal*/ fwrite($file, 'echo \'
 <script>document.addEventListener("\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" + ":slideenter", function(){ document.getElementById("audio-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'").play();}); document.addEventListener("\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" + ":slideleave", function(){ document.getElementById("audio-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'").pause();});</script>\';'); }  fwrite($file, ' echo \' <!--\';
}');  fwrite($file, '?>');
  fclose($file);
 }

// main entry
 
 header('Content-type: text/xml');
   
 if(isset($_GET['options']))
 {
    echo '<plugin name="html5 audio" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=html5_audio_xmc" type="media/html5_audio.sgxm.php" >
 <info>Audio importer for HTML 5. MP3 for most browsers. OGG for Firefox</info>

<option name="audio file" type="upload" value="" extensions="*" />
<option name="ogg file" type="upload" value="" extensions="ogg" />
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