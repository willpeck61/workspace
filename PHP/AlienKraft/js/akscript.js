// JavaScript Document

//Animate Skull Jaw
$(document).ready(function (){
	$("#jaw").mouseover(function (){
			$(this).animate({ marginTop: "+=20" },1000)
		});
	$("#jaw").mouseleave(function (){
			$(this).animate({ marginTop: "-=20" },1000)
		});	
    }); 

//Video Background	
$(document).ready(function() {
	$('#homevid, object').maximage('maxcover');
	//$('h2').hide();

//Set global variables for element visibility
	$('nav a').click(function (){
		if($('#logo:visible').length == 0) //skull logo is not visible?
		{
			chksk = 0;
		} else {chksk = 1;};
		 
		if($('#about:visible').length == 0) //about page is not visible?
		{
			chkab = 0;
		} else {chkab = 1;};
		
		if($('#music:visible').length == 0) //music page is not visible?
		{
			chkmu = 0;
		} else {chkmu = 1;};
		
		if($('#contact:visible').length == 0) //contact page is not visible?
		{
			chkco = 0;
		} else {chkco = 1;};
	});
	
//About Page
	$('#about').hide();
	$('#aboutbutton').click(function(){
		//check for element visibility and execute correct transitions.
				if (chksk == 1){  //if skull is visible
					$('#logo').fadeOut(1000);
					$('#about').fadeIn(1000);
					}
				if (chksk == 0 && chkab == 1) //if skull is not visible and about page is visible
					{		
					$('#about').fadeOut(1000);
					$('#logo').fadeIn(1000);
					}
				if (chkmu == 1) //if skull is not visible and about page is visible
					{		
					$('#music').hide();
					$('#about').fadeIn(1000);
					}
				if (chkco == 1) //if skull is not visible and about page is visible
					{		
					$('#contact').hide();
					$('#about').fadeIn(1000);
					}
				});

//Music Page
	$('#music').hide();
	$('#musicbutton').click(function(){
		//check for element visibility and execute correct transitions.
				if (chksk == 1){  //if skull is visible
					$('#logo').fadeOut(1000);
					$('#music').fadeIn(1000);
					}
				if (chksk == 0 && chkmu == 1) //if skull is not visible and music page is visible
					{		
					$('#music').fadeOut(1000);
					$('#logo').fadeIn(1000);
					}
				if (chkab == 1) //if about page is visible
					{		
					$('#about').hide();
					$('#music').fadeIn(1000);
					}
				if (chkco == 1) //if contact page is visible
					{		
					$('#contact').hide();
					$('#music').fadeIn(1000);
					}
				});

//Contact Page		
	$('#contact').hide();
	$('#contactbutton').click(function(){
		console.log(chksk);
		//check for element visibility and execute correct transitions.
				if (chksk == 1){  //if skull is visible
					$('#logo').fadeOut(1000);
					$('#contact').fadeIn(1000);
					}
				if (chksk == 0 && chkco == 1) //if skull is not visible and contact page is visible
					{		
					$('#contact').fadeOut(1000);
					$('#logo').fadeIn(1000);
					}
				if (chkab == 1) //if about page is visible
					{		
					$('#about').hide();
					$('#contact').fadeIn(1000);
					}
				if (chkmu == 1) //if music page is visible
					{		
					$('#music').hide();
					$('#contact').fadeIn(1000);
					}
				});
//Home Page		
	$('#homebutton').click(function(){
		$('#music').hide();
		$('#about').hide();
		$('#contact').hide();
		$('#logo').fadeIn(1000);	
	});
});

//Audio Player Under Construction

$(document).ready(function() {
	
	//home page
	$('#toggleplay').click(function(){
		var myAudio = document.getElementById("hometrack"); //had to use document.getElementById. Jquery selector does not return DOM audio values?
		if (myAudio.paused){
			$('#playbutton').html('Playing');
			$('h2').fadeIn();
			$('#trackname').html('Nocturnal :: PLAYING');
			$('#hometrack').trigger('play');  				//jquery can pass DOM audio instructions through trigger.
		}
		else
		{
			$('#trackname').html('Nocturnal :: PAUSED');
			$('#playbutton').html('Paused');
			$('#hometrack').trigger('pause');
		};
	});
	
	//Main Player
	//had to add this due to chrome not waiting for audio metadata before loading page (returns NaN without this code).
//	$('#msplaybutton').click(function(){
//		var trackOne = document.getElementById("audio-player");
		//trackOne.addEventListener('loadedmetadata', function() {  
    	//console.log(trackOne.duration); 
//		var timeind = trackOne.duration;
//		$('#progresscounter').countdown({until: (timeind), format: 'MS', compact: true});
//	});
});




