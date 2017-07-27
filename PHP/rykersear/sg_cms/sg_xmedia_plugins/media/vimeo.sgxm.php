<?php
 //Vimeo player Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=vimeo_xmc
 //Easily place your Vimeo videos on your page. Player works in all browsers, including iPhone.

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("VIMEO_ID"))
{
 function VIMEO_ID($URL)
{
return REGEX_MATCH("vimeo.com/(.*)", $URL);
 }
}
if(!function_exists("LOOP"))
{
 function LOOP($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? loop : JOIN_STRING("", "");;
 }
}
if(!function_exists("AUTOPLAY_BOOL"))
{
 function AUTOPLAY_BOOL($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? true : false;;
 }
}
if(!function_exists("AUTOPLAY_NUM"))
{
 function AUTOPLAY_NUM($VAL)
{
return BOOL($VAL) ? 1 : 0;;
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
'); /*literal*/ fwrite($file, 'echo \'   <iframe id="vimeo-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" src="http://player.vimeo.com/video/\';');  /*expression*/ fwrite($file, 'echo \'' . VIMEO_ID(PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) . '\';');  /*literal*/ fwrite($file, 'echo \'?api=1&player_id=vimeo-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'&autoplay=\';');  /*expression*/ fwrite($file, 'echo \'' . AUTOPLAY_NUM(PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay")) . '\';');  /*literal*/ fwrite($file, 'echo \'" width="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'" height="\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'" frameborder="0" \';');  /*expression*/ fwrite($file, 'echo \'' . LOOP(PLUGIN_OPTION_VAL($OPTIONSDOM, "loop")) . '\';');  /*literal*/ fwrite($file, 'echo \' webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>
   <script>
      // autoplay
      if(\';');  /*expression*/ fwrite($file, 'echo \'' . AUTOPLAY_BOOL(PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay")) . '\';');  /*literal*/ fwrite($file, 'echo \')
      {
         var vimeo_iframe_\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \' = document.getElementById("vimeo-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'");
         var url_\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \' = "http://player.vimeo.com/video/\';');  /*expression*/ fwrite($file, 'echo \'' . VIMEO_ID(PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) . '\';');  /*literal*/ fwrite($file, 'echo \'";
         document.addEventListener("\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" + ":slideenter", function(){ vimeo_iframe_\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'.contentWindow.postMessage(JSON.stringify({method:"play"}), url_\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'); });
         document.addEventListener("\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" + ":slideleave", function(){ vimeo_iframe_\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'.contentWindow.postMessage(JSON.stringify({method:"pause"}), url_\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'); });
      }
   </script>\';');   fwrite($file, ' echo \' <!--\';
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
    echo '<plugin name="Vimeo player" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=vimeo_xmc" type="media/vimeo.sgxm.php" >
 <info>Easily place your Vimeo videos on your page. Player works in all browsers, including iPhone.</info>

<option name="url" type="online-url" value="" />
<option name="autoplay" type="boolean" value="true" />
<option name="loop" type="boolean" value="true" />
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