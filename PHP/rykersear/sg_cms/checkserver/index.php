<?php session_start(); ?>
<?php hide_errors(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<!--
	Version 1.1 of this script.
	Last edited: 4/11/11
	-->

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>SiteGrinder 3 Remote CMS Checkserver</title>

	<style type="text/css" media="all">
		b, .error { display: block; color: red; }
	</style>
	<script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		function server_fail()
		{
			$("#server").html("Server Fail");
			$("#server").addClass("error");
			$("#server_msg").html("This server is not able to support the SiteGrinder 3 CMS system");
			$("#server_msg").addClass("error");
		}
	</script>
</head>
<body>
	<noscript>
		<h2 class="error">Javascript is disabled in your browser. The remote CMS requires Javascript. Please enable it in your browser options.</h2>
	</noscript>
	<?php checkserver(); ?>
	<br /><a style="background-color:#ffffff;" href="index.php?errors=TRUE">Check Again(Display PHP Errors)</a>
</body>
</html>

<?php
function checkserver()
{
	global $phpok, $serverok, $jquery;
	$serverok = TRUE;
	$jquery = '';

	set_base_url();

	$checks[] = new check('version');
	$checks[] = new check('post_data');
	$checks[] = new check('file_write');
	$checks[] = new check('ftp_user_perms');
	$checks[] = new check('php_folder_perms');
	$checks[] = new check('php_file_perms');
	$checks[] = new check('file_ext');
	$checks[] = new check('file_uploads');
	$checks[] = new check('curl');
	$checks[] = new check('session_save_path');
	$checks[] = new check('sessions_ajax');
	$checks[] = new check('sessions_lb');
	$checks[] = new check('sessions_ua');
	$checks[] = new check('libxml');
	$checks[] = new check('dom');
	$checks[] = new check('pcre');
	$checks[] = new check('simplexml');
	$checks[] = new check('date');
	$checks[] = new check('gd');
	$checks[] = new check('safe_mode_or_safe_mode_gid');
	$checks[] = new check('server_vars');
	
	if($serverok && $phpok) {
		echo '<h1 id="server">Server OK</h1><h2 id="server_msg">This server appears to be compatible with the SiteGrinder 3 CMS system.</h2>';
	} else { 
		echo '<h1 class="error">Server Fail</h1><h2 class="error">This server is not able to support the SiteGrinder 3 CMS system.</h2>';
	}

	if($phpok) {
		echo "<table cellpadding='5'>";
		foreach($checks as $check) {
			echo $check->display_results();
		}
		echo "</table><br /><br /><br />";
		echo <<<js
		<script type="text/javascript">
			$(document).ready(function() {
				$(".jscheck").html("Checking");
				$jquery
			});
		</script>
js;
	} else {
		echo '<h3 class="error">This server is running PHP version '.phpversion().', but PHP version 5 or later is required.</h3>';
	}

	phpinfo();
}

function hide_errors()
{
	ini_set('display_errors', 'On');
	ini_set('display_startup_errors', 'On');
	$reporting = 0;

	if(!empty($_REQUEST['errors'])) {
		if($_REQUEST['errors'] == 'true') {
			$reporting = E_ALL ^ E_NOTICE;
		}
	}

	error_reporting($reporting);
}

function set_base_url()
{
	global $base_url;
	// Get Current URL
	$page_url = 'http';
	if (isset($_SERVER["HTTPS"]) && $_SERVER["HTTPS"] == "on") {
		$page_url .= "s";
	}
	$page_url .= "://";
	if ($_SERVER["SERVER_PORT"] != "80") {
		$page_url .= $_SERVER["SERVER_NAME"].":".$_SERVER["SERVER_PORT"].$_SERVER["REQUEST_URI"];
	} else {
		$page_url .= $_SERVER["SERVER_NAME"].$_SERVER["REQUEST_URI"];
	}
	$pieces = explode('/', $page_url);
	for($count = 0; $count < count($pieces) - 1; $count++) {
		$base_url .= $pieces[$count].'/';
	}
}

class check
{
	function __construct($check)
	{
		$this->check_name = $check;
		$check = 'check_'.$check;
		if(method_exists($this, $check)) {
			$this->$check();
		}
	}
	
	function display_results()
	{
		$id = '';
		if(isset($this->id)) {
			$id = ' id="'.$this->id.'"';
		}
		$class='';
		if(isset($this->class)) {
			$class = ' class="'.$this->class.'"';
		}
		return '<tr><td align="right">'.$this->display_name.'</td><td'.$id.$class.'>'.$this->msg.'</td></tr>';
	}

	function jquery($js)
	{
		global $jquery;
		$jquery .= $js;
	}

	function fail_server()
	{
		global $serverok;
		$serverok = FALSE;
	}

	function curl_call($url, $ua)
	{
		global $base_url;
		$ch = curl_init($base_url.$url);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
		curl_setopt($ch, CURLOPT_USERAGENT, $ua);
		$return = curl_exec($ch);
		curl_close($ch);
		return $return;
	}

	function check_version()
	{
		global $phpok;
		$this->display_name = 'PHP Version Number';
		if(floatval(phpversion()) >= 5) {
			$this->msg = phpversion();
			$phpok = TRUE;
		} else {
			$this->msg = "<b>".phpversion()."</b>";
			$phpok = FALSE;
		}
	}

	function check_post_data()
	{
		$this->display_name = 'POST Data Check';
		$js = <<<js
			$.ajax({
				data: {'test': '><'},
				type: 'post',
				url: 'ajax_post_data.php',
				success: function(data) {
					$("#post_data").html("OK");
				},
				error: function() {
					$("#post_data").html("<b>POST data containing &gt;&lt; is not allowed.</b>");
					server_fail();
				}
			});
js;
		$this->jquery($js);
		$this->msg = '<b><noscript>Javascript is required for this check.</noscript></b>';
		$this->id = 'post_data';
		$this->class = 'jscheck';
	}

	function check_file_write()
	{
		$this->display_name = 'File Write';
		$filename = 'checkserver_check.php';
		$file_write = TRUE;
		if(!file_put_contents($filename, 'Create File')) {
			$file_write = FALSE;
		}

		$file = file_get_contents($filename);

		if($file != 'Create File') {
			$file_write = FALSE;
		}

		$file = 'Edit';

		if(!file_put_contents($filename, $file)) {
			$file_write = FALSE;
		}

		$file = file_get_contents($filename);

		if($file != 'Edit') {
			$file_write = FALSE;
		}

		if(!unlink($filename)) {
			$file_write = FALSE;
		}
		
		if($file_write === TRUE) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>PHP IS NOT ABLE TO WRITE FILES</b>";
		}
	}

	function check_ftp_user_perms()
	{
		$this->display_name = "FTP User Permissions";
		if(file_put_contents('ftp_user_edit_check.htm', time()) === FALSE) {
			$this->fail_server();
			$this->msg = "<b>PHP CAN&apos;T EDIT FTP USER FILES</b>";
		} else {
			$this->msg = "OK";
		}
	}

	function check_php_folder_perms()
	{
		$this->display_name = "PHP Folder Permissions";
		$this->id = "php_folder_perms";

		$perm_folder = TRUE;
		$perm_file = TRUE;
		if(file_exists('permissions')) {
			if(file_exists('permissions/permissions.php')) {
				if(!unlink('permissions/permissions.php')) {
					$perm_file = FALSE;
				}
			}
			if(!rmdir('permissions')) {
				$perm_folder = FALSE;
			}
		}

		if(mkdir('permissions') === FALSE) {
			$perm_folder = FALSE;
		} else {
			if(file_put_contents('permissions/permissions.php', '<?php print_r($_REQUEST); ?>') === FALSE) {
				$perm_file = FALSE;
			}
		}

		if($perm_folder && $perm_file) {
			$this->class = 'jscheck';
			$this->msg = "<b><noscript>Javascript is required for this check.</noscript></b>";
			$js = <<<js
			if($("#php_folder_perms").html() == 'Checking') {
				$.ajax({
					data: {'test': 'data'},
					type: 'post',
					url: 'permissions/permissions.php',
					success: function(data) {
						$("#php_folder_perms").html("OK");
					},
					error: function() {
						$("#php_folder_perms").html("<b>Folders created by PHP are created with the wrong permissions.</b>");
						server_fail();
					}
				});
			}
js;
			$this->jquery($js);
		} else {
			$this->fail_server();
			$this->msg = "<b>PHP CAN&apos;T WRITE/EDIT/DELETE FILES</b>";
		}
	}

	function check_php_file_perms()
	{
		$this->display_name = "PHP File Permissions";
		$this->id = "php_file_perms";

		$perm_file = TRUE;
		if(file_exists('permissions.php')) {
			if(!unlink('permissions.php')) {
				$perm_file = FALSE;
			}
		}
		if(file_put_contents('permissions.php', '<?php print_r($_REQUEST); ?>') === FALSE) {
			$perm_file = FALSE;
		}
		
		if($perm_file) {
			$this->class = 'jscheck';
			$this->msg = "<b><noscript>Javascript is required for this check.</noscript></b>";
			$js = <<<js
			if($("#php_file_perms").html() == 'Checking') {
				$.ajax({
					data: {'test': 'data'},
					type: 'post',
					url: 'permissions.php',
					success: function(data) {
						$("#php_file_perms").html("OK");
					},
					error: function() {
						$("#ext_check").html("<b>Files created by PHP are created with the wrong permissions. Once that is fixed then we'll be able to check files with custom extensions.</b>");
						$("#php_file_perms").html("<b>Files created by PHP are created with the wrong permissions.</b>");
						server_fail();
					}
				});
			}
js;
			$this->jquery($js);
		} else {
			$this->fail_server();
			$this->msg = "<b>PHP CAN&apos;T WRITE/EDIT/DELETE FILES</b>";
		}
	}

	function check_file_ext()
	{
		$this->display_name = 'File Extension Check';
		$this->id = 'ext_check';
		
		$file_create = TRUE;
		if(file_exists('test.post')) {
			if(!unlink('test.post')) {
				$file_create = FALSE;
			}
		}
		if(file_put_contents('test.post', 'test') === FALSE) {
			$file_create = FALSE;
		}
		if(file_exists('test.comment')) {
			if(!unlink('test.comment')) {
				$file_create = FALSE;
			}
		}
		if(file_put_contents('test.comment', 'test') === FALSE) {
			$file_create = FALSE;
		}
		
		if($file_create) {
			$this->class = 'jscheck';
			$js = <<<js
				var wait = setInterval(function(){
					if($("#php_file_perms") != 'Checking') {
						clearInterval(wait);
						if($("#ext_check").html() == 'Checking') {
							$.ajax({
								url: 'test.post',
								success: function(data) {
									$("#ext_check").html("OK");
									check_comment_ext();
								},
								error: function() {
									$("#ext_check").html("<b>The server won't serve files with the .post extension correctly. </b>")
									check_comment_ext();
								},
							});
						}
					}
				}, 100);
				function check_comment_ext()
				{
					$.ajax({
						url: 'test.comment',
						error: function() {
							var msg = "<b>The server won't serve files with the .comment extension correctly.</b>";
							if($("#ext_check").html() == "OK") {
								$("#ext_check").html(msg)
							} else {
								$("#ext_check").append(msg);
							}
						}
					});
				}
js;
			$this->jquery($js);
			$this->msg = "<b><noscript>Javascript is required for this check.</noscript></b>";
		} else {
			$this->fail_server();
			$this->msg = "<b>PHP CAN&apos;T WRITE/EDIT/DELETE FILES</b>";
		}
	}

	function check_file_uploads()
	{
		$this->display_name = 'PHP file_uploads ini option';
		if(ini_get('file_uploads')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>file_uploads MUST BE SET TO ON</b>";
		}
	}

	function check_curl()
	{
		// check_sessions_ua
		global $curl;
		$this->display_name = 'cURL';
		if(extension_loaded('curl')) {
			$this->msg = "OK";
			$curl = TRUE;
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
			$curl = FALSE;
		}
	}

	function check_session_save_path()
	{
		$this->display_name = 'session.save_path';
		$open_basedir = ini_get('open_basedir');
		if(is_dir(session_save_path())){ 
			$this->msg = "OK";
		} elseif(!empty($open_basedir)) {
			$this->msg = "<b>CAN&aposT CHECK DUE TO OPEN BASEDIR RESTRICTIONS</b>";
		} else {
			$this->fail_server();
			$this->msg = "<b>DIRECTORY DOES NOT EXIST</b>";
		}
	}

	function check_sessions_ajax()
	{
		$this->display_name = 'Sessions with AJAX';
		$this->id = "ajax_session_check";
		if(isset($_SESSION)) {
			$this->class = 'jscheck';
			$this->msg = "<b><noscript>Javascript is required for this check.</noscript></b>";
			$id = uniqid();
			$js = <<<js
			$.ajax({
				type: 'post',
				data: {'id': '$id'},
				url: 'ajax_set_sessions.php',
				success: function(d) {
					$.ajax({
						type: 'post',
						data: {'id': '$id'},
						url: 'ajax_sessions.php',
						success: function(status) {
							if(status == 'success') {
								$("#ajax_session_check").html("OK");
							} else {
								$("#ajax_session_check").html("<b>SESSIONS ARE NOT SET UP TO WORK WITH AJAX");
								server_fail();
							}
						},
						error: function() {
							$("#ajax_session_check").html("<b>THERE WAS AN ERROR WITH THE SESSION AJAX TEST</b>");
							server_fail();
						}
					});
				},
				error: function() {
					$("#ajax_session_check").html("<b>THERE WAS AN ERROR SETTING THE SESSION FOR THE SESSION AJAX TEST</b>");
					server_fail();
				}
			});
js;
			$this->jquery($js);
		} else {
			$this->fail_server();
			$this->msg = "<b>SESSIONS ARE NOT CONFIGURED CORRECTLY</b>";
		}
	}

	function check_sessions_lb()
	{
		// Check for session problems with load balancing
		global $curl, $sessions_lb;
		$this->display_name = 'Sessions &amp; Load Balancing';
		if(!$curl) {
			$this->fail_server();
			$this->msg = "<b>cURL is required for this check.</b>";
			$sessions_lb = FALSE;
		} else {
			$id = uniqid();
			$value = uniqid();
			
			$return = $this->curl_call('curl_set_sessions.php?value='.$value.'&id='.$id, 'lb_test');
			if($return === false) {
				$this->msg = 'Curl error: ' . curl_error($ch);
				$sessions_lb = FALSE;
			} elseif($return == 'success') {
				for($count = 0; $count < 10; $count++) {
					$return = $this->curl_call('curl_sessions.php?value='.$value.'&id='.$id, 'lb_test');
					if($return === false) {
						$count = 100;
						$this->msg = 'Curl error: ' . curl_error($ch);
						$sessions_lb = FALSE;
					} elseif($return == 'success') {
						$this->msg = "OK";
						$sessions_lb = TRUE;
					} else {
						$count = 100;
						$this->fail_server();
						$this->msg = "<b>SESSIONS ARE NOT CONFIGURED TO WORK WITH LOAD BALANCING</b>";
						$sessions_lb = FALSE;
					}
				}
			} else {
				$this->fail_server();
				$this->msg = "<b>WASN&apos;T ABLE TO SET SESSION FOR TEST</b>";
				$sessions_lb = FALSE;
			}
		}
	}

	function check_sessions_ua()
	{
		global $curl, $sessions_lb;
		$this->display_name = 'Sessions support for multiple User Agents';
		if(!$curl) {
			$this->fail_server();
			$this->msg = "<b>cURL IS REQUIRED FOR THIS CHECK</b>";
		} elseif(!$sessions_lb) {
			$this->fail_server();
			$this->msg = "<b>SESSIONS MUST WORK WITH LOAD BALANCING FOR THIS CHECK TO RUN</b>";
		} else {
			if(isset($_SESSION)) {
				$id = uniqid();
				$value = uniqid();
				
				$return = $this->curl_call('curl_set_sessions.php?value='.$value.'&id='.$id, 'test_one');
				if($return === false) {
					$this->msg = 'Curl error: ' . curl_error($ch);
				} elseif($return == 'success') {
					$return = $this->curl_call('curl_sessions.php?value='.$value.'&id='.$id, 'test_two');
					if($return === false) {
						$this->msg = 'Curl error: ' . curl_error($ch);
					} elseif($return == 'success') {
						$this->msg = "OK";
					} else {
						$this->fail_server();
						$this->msg = "<b>SESSIONS ARE NOT CONFIGURED TO WORK WITH MULTIPLE USER AGENTS</b>";
					}
				} else {
					$this->fail_server();
					$this->msg = "<b>WASN&apos;T ABLE TO SET SESSION FOR TEST</b>";
				}
			} else {
				$this->fail_server();
				$this->msg = "<b>SESSIONS ARE NOT CONFIGURED CORRECTLY</b>";
			}
		}
	}

	function check_libxml()
	{
		$this->display_name = 'libxml';
		if(extension_loaded('libxml')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_dom()
	{
		$this->display_name = 'dom';
		if(extension_loaded('dom')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_pcre()
	{
		$this->display_name = 'pcre';
		if(extension_loaded('pcre')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_simplexml()
	{
		$this->display_name = 'SimpleXML';
		if(extension_loaded('SimpleXML')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_date()
	{
		$this->display_name = 'date';
		if(extension_loaded('date')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_gd()
	{
		$this->display_name = 'GD';
		if(extension_loaded('gd')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_safe_mode_or_safe_mode_gid()
	{
		$this->display_name = 'safe_mode is Off <br /> -or- <br />safe_mode_gid is On';
		if(!ini_get('safe_mode') || ini_get('safe_mode_gid')) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
			$this->msg = "<b>MISSING</b>";
		}
	}

	function check_server_vars()
	{
		$this->display_name = '$_SERVER Vars Check';
		$required_server_vars = array('SCRIPT_FILENAME', 'SERVER_PORT', 'SERVER_NAME', 'REQUEST_URI');
		$check = TRUE;
		foreach($required_server_vars as $var) {
			if(!isset($_SERVER[$var])) {
				$this->msg .= '<b>$_SERVER[\''.$var.'\'] IS NOT AN ABSOLUTE PATH.</b>';
				$check = FALSE;
			} elseif($var == 'SCRIPT_FILENAME' && preg_match('/\.\.\//', $_SERVER[$var])) {
				$this->msg .= '<b>$_SERVER[\'SCRIPT_FILENAME\'] IS NOT AN ABSOLUTE PATH.</b>';
				$check = FALSE;
			}
		}
		
		if($check) {
			$this->msg = "OK";
		} else {
			$this->fail_server();
		}
	}
}
?>