<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Alien Kraft</title> <!-- Website Title for Browser display and SEO.-->
<meta name="description" content="Alien Kraft :: Producer based in Milton Keynes">  <!-- Basic SEO for Name and Location. -->
<meta name="viewport" content="width=device-width"> <!--Inserted to help display on mobile phones -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>  <!-- Jquery loaded from content delivery network. -->

<script type="text/javascript" src="js/cycle.js"></script> <!-- transitional effects plugin. -->
<script type="text/javascript" src="js/maximage.js"></script> <!-- Plugin for background Video-->
<script type="text/javascript" src="js/jquery.plugin.js"></script> <!-- Jquery Extensions -->
<script type="text/javascript" src="js/jquery.countdown.js"></script> <!-- Time counter script. Was going to use this when building custom audio player.-->
<script type="text/javascript" src="js/akscript.js"></script>  <!-- Self written code for page transistions and HTML5 audio/video control -->
<link href="CSS/main.css" rel="stylesheet" type="text/css">
<link href="CSS/jquery.countdown.css" rel="stylesheet" type="text/css">
</head>

<body>
<video id="homevid" autoplay width="1920" height="1080" loop><!-- Modification to original design.  Video background.-->
	<source src="video/visual.mp4" type="video/mp4" />
</video>

<div id="container">
    <audio id="hometrack"> <!--HTML5 Audio Player. Controlled through Jquery akscript.js-->
 		<source src="music/nocturnal.mp3" type="audio/mp3">
    </audio>   

<header class="aktext">
<h1>Alien Kraft</h1>
</header>

<article id="about" class="aktext">
<h1>About</h1>
<p>Alien Kraft is an amateur music producer from Milton Keynes.  He has been producing various underground styles since the early 1990s after being inspired by tracks produced by artists like The Prodigy and The Criminal Minds.  Recently, Alien Kraft has focussed on producing Dubstep and Drum and Bass tracks.</p>
<video id="aboutvid" width="420" height="315" controls>
	<source src="music/alienmk.mp4" type="video/mp4" />
</video>
</article>

<article id="music" class="aktext"> <!-- planning to replace soundcloud embed with a custom audio player in the future. -->
<!--<div class="audio-player">--> 
	<iframe width="100%" height="130" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/103972426%3Fsecret_token%3Ds-5GYVl&amp;color=5fbd85&amp;auto_play=false&amp;hide_related=true&amp;show_artwork=true&amp;show_comments=false&amp;show_user=false&amp;show_reposts=false"></iframe>
	<iframe width="100%" height="130" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/117552177&amp;color=5fbd85&amp;auto_play=false&amp;hide_related=true&amp;show_artwork=true&amp;show_comments=false&amp;show_user=false&amp;show_reposts=false"></iframe>
	<iframe width="100%" height="130" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/109690435%3Fsecret_token%3Ds-RzdnY&amp;color=5fbd85&amp;auto_play=false&amp;hide_related=true&amp;show_artwork=true&amp;show_comments=false&amp;show_user=false&amp;show_reposts=false"></iframe>
	<iframe width="100%" height="130" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/97004274&amp;color=5fbd85&amp;auto_play=false&amp;hide_related=true&amp;show_artwork=true&amp;show_comments=false&amp;show_user=false&amp;show_reposts=false"></iframe>
	<iframe width="100%" height="130" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/96922372&amp;color=5faf7f&amp;auto_play=false&amp;hide_related=true&amp;show_artwork=true&amp;show_comments=false&amp;show_user=false&amp;show_reposts=false"></iframe>
<!--<h1>Demo - Nocturnal</h1>
	<img class="cover" src="images/alienkraftthumb.jpg" alt="Alien Kraft Track Thumb" height="120" width="120">
    <ul id="player">
    	<li><a href="#" id="msplaybutton"><span>Play</span></a></li>
    	<li><a href="#" id="mspausebutton"><span>Pause</span></a></li>
    </ul>
    <span id="countertext">Counter</span>
    <span id="progresscounter">00:00</span>
    <a href="#" id="msmutebutton"><span>Mute</span></a>
	<audio id="audio-player" src="music/nocturnal.mp3" type="audio/mp3" controls="controls"></audio>
</div>-->
</article>
<article id="contact">
<div id="contact-form" class="clearfix">
    <h1>Contact</h1>
    <form method="POST" action="processform.php"> <!-- processform.php self written mailing script -->
        <label for="name">Name: <span class="required">*</span></label>
        <input type="text" id="name" name="name" value="" placeholder="John Doe" required autofocus />
         
        <label for="email">Email Address: <span class="required">*</span></label>
        <input type="email" id="email" name="email" value="" placeholder="johndoe@example.com" required />
         
        <label for="telephone">Telephone: </label>
        <input type="tel" id="telephone" name="telephone" value="" />
         
        <label for="message">Message: <span class="required">*</span></label>
        <textarea id="message" name="message" placeholder="Minimum 20 characters" required data-minlength="20"></textarea>
         
        <span id="loading"></span>
        <input type="submit" value="submit" name="submit" id="submit-button" />
        <p id="req-field-desc"><span class="required">*</span> indicates a required field</p>
    </form>
</div>
</article>
<figure id="logo">
	<img src="images/skull.png" alt="Alien Kraft Logo">
    <p id="playbutton" class="aktext">PLAY</p>
	<a href="#" id="toggleplay"><img id="jaw" src="images/jaw.png" alt="Alien Kraft Logo"></a> <!-- Jaw animated in Jquery akscript.js -->
</figure>

<footer>
<h2 id="trackname" class="aktext"></h2>
<nav>
<ul>
	<li><a href="#" id="homebutton">Home</a></li>
	<li><a href="#" id="aboutbutton">About</a></li>
	<li><a href="#" id="musicbutton">Music</a></li>
	<li><a href="#" id="contactbutton">Contact</a></li>
</ul>
<span id="siteby"><a href="#">Site by RGS Digital</a></span>
</nav>
</footer>

</div>

</body>
</html>
