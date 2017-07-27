<?php
 //YouTube player Plugin
 //http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=youtube_xmc
 //Easily place your YouTube videos on your page. Player works in all browsers, including iPhone.

session_start();
include('../plugin_utilities.php');
include('../../verify_login.php');

if(!function_exists("VIDEO_ID"))
{
 function VIDEO_ID($URL)
{
return REGEX_MATCH("v=([^&]*)", $URL);
 }
}
if(!function_exists("AUTOPLAY_BOOL"))
{
 function AUTOPLAY_BOOL($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? true : false;;
 }
}
if(!function_exists("CONTROLS_NUM"))
{
 function CONTROLS_NUM($CONTROL_VAL)
{
return BOOL($CONTROL_VAL) ? 1 : 0;;
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
'); /*literal*/ fwrite($file, 'echo \'<div id="youtube-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'"></div>
     <script>
      // 2. This code loads the IFrame Player API code asynchronously.
      var tag = document.createElement("script");
      tag.src = "http://www.youtube.com/player_api";
      var firstScriptTag = document.getElementsByTagName("script")[0];
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

      // 3. This function creates an <iframe> (and YouTube player)
      //    after the API code downloads.
      var \';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_player;
      var \';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_autoplay = \';');  /*expression*/ fwrite($file, 'echo \'' . AUTOPLAY_BOOL(PLUGIN_OPTION_VAL($OPTIONSDOM, "autoplay")) . '\';');  /*literal*/ fwrite($file, 'echo \';
      function onYouTubePlayerAPIReady() {
        \';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_player = new YT.Player("youtube-\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'", {
          height: "\';');  /*expression*/ fwrite($file, 'echo $SG_DIVHEIGHT;'); /*literal*/ fwrite($file, 'echo \'",
          width: "\';');  /*expression*/ fwrite($file, 'echo $SG_DIVWIDTH;'); /*literal*/ fwrite($file, 'echo \'",
          videoId: "\';');  /*expression*/ fwrite($file, 'echo \'' . VIDEO_ID(PLUGIN_OPTION_VAL($OPTIONSDOM, "url")) . '\';');  /*literal*/ fwrite($file, 'echo \'",
          controls: "\';');  /*expression*/ fwrite($file, 'echo \'' . CONTROLS_NUM(PLUGIN_OPTION_VAL($OPTIONSDOM, "show controller")) . '\';');  /*literal*/ fwrite($file, 'echo \'",
          loop: "\';');  /*expression*/ fwrite($file, 'echo \'' . CONTROLS_NUM(PLUGIN_OPTION_VAL($OPTIONSDOM, "loop")) . '\';');  /*literal*/ fwrite($file, 'echo \'",
          wmode: "transparent",
          events: {
            "onReady": onPlayerReady
          }
        });
      }

      // autoplay
      function onPlayerReady(event) {
        if(\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_autoplay){ event.target.playVideo(); }
      }
      if(\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_autoplay)
      {
       document.addEventListener("\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" + ":slideenter", function(){ \';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_player.playVideo();});
       document.addEventListener("\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'" + ":slideleave", function(){ \';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_autoplay = false; if(\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_player){\';');  /*expression*/ fwrite($file, 'echo $SG_DIVNAME;'); /*literal*/ fwrite($file, 'echo \'_player.stopVideo();}});
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
    echo '<plugin name="YouTube player" url="http://www.medialab.com/sitegrinder3/support/SG3_HelpJump.html?helpsym=youtube_xmc" type="media/youtube.sgxm.php" >
 <info>Easily place your YouTube videos on your page. Player works in all browsers, including iPhone.</info>

<option name="url" type="online-url" value="" />
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