/**
 *    Copyright (c) 2009-2010, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */
/* Things to do for CSS3
	- alignInPicturebox needed? 
 
 	Features to finish
 	- deal with interrupting gotoImage requests
 	- place with wrong sized images within picturebox
 	- test with viewsheet
 	- limit queries to picturebox div name to avoid problems with multiple pboxes on same page
 	- use external css file instead of dynamically adding classes?
*/
/*global Modernizr,alert,$ml, Image */

var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.gallery1) {
	com.medialab.sg.gallery1 = {};
}

var exhibitArray = [];
com.medialab.sg.gallery1.pictureboxSiteGrinderCSSAnim = function(pictureboxXMLIn, docentIn){
	var that = this;
	var debug = false;
	var sgalert = function(msg){
		if (debug) {
			alert(msg);
		}
	};
	var docent = docentIn;
	var xml = pictureboxXMLIn;
	
	var myWidth = 0;
	var myHeight = 0;
	var myDivID = "not set yet";
	var myClickAction = "not set yet";
	var myHoverAction = "not set yet";
	var rightClickBlock = false;
	var curImageUrl = "not set yet";
	var halfPreloader = 15;  // hardwired for now so we don't have to preload the preloader to find it.  will have to change that if we allow pluggable preload images.
	var preloadImgHTML = null;
	var exhibitArray = [];
	var fadeTimeMs = 500;
	var fadeTimeS = 0.5;
	var delayMs = 5000;
	var delayS = 5;
	var goNextTimerID = null;
	var imageDaemonTimerID = null;
	var checkLoadTimerID = null;
	var autoPlay = false;
	var endAction = 'loop';
	var endUrl = 'index.html';
	var viewDiv = null;
	var pboxAlign = "not set yet";
	var autoPlayed = "not set yet";
	var preloaderimageUrl = "not set yet";
	var d = new Date(); // for constructing a unique name
	var picContainerDivId = "css3pictureboxcontainer"+ d.getTime();
	d=null;
	var numExhibits = docent.getExhibitCount();
	
// ****	
// **** Begin CSS3 Stuff
// ****
//
//	NOTE: "cssanimations" are currently required for all CSS3 transitions, and so it is checked in browserSucks()
//
	var transitions = {};
	var selectedTransition = null;
	var css3PictureboxContainerDiv = null;
	var exLastShown = 'not set yet';
	var viewLastShown = "not set yet";
	
	function getVendorCSSBlock(transitionString){
		var myCss = "-webkit-transition:" + transitionString + "; "+
		"-moz-transition:" + transitionString + "; "+
		"-o-transition:" + transitionString + "; "+
		"-ms-transition:" + transitionString + "; "+
		"transition:" + transitionString + "; ";
		return myCss;
	}	
// set up transitions

//////////////////////////////////////////////////
// V1Crossfade

	function setUpV1CrossFade() {
		var ptop = Math.floor(myHeight/2) - halfPreloader;
		var pleft = Math.floor(myWidth/2) - halfPreloader;
		preloadImgHTML= $ml("<img style='position:absolute;left:"+pleft+"px;top:"+ptop+"px;display:block' src='" + preloaderimageUrl +"'/>");
		preloadImgHTML.appendTo(css3PictureboxContainerDiv);	
	}
	
	function getV1CrossfadePictureDiv(urlIn) {
		var left = 0;
		var top = 0;
		var cursorString = "";
		if(myClickAction!="none") { cursorString="cursor:pointer;"; }
		return "<img style='"+cursorString+"position:absolute;left:0px;top:0px;display:none' src='" + urlIn + "'>";
	}

    var firstCall = true;
	function V1CrossfadeGotoPicNum(exhibitNum, viewNum) {
		docent.sgconsole('V1CrossfadeGotoPicNum');
		clearTimeout(goNextTimerID);
		if(typeof viewNum == 'undefined') { viewNum = 0; }
		if (exhibitNum <= exhibitArray.length - 1) {
			var imageHero = exhibitArray[exhibitNum].images[viewNum];
			if(typeof imageHero == 'undefined') {
				//if(typeof imageHero == 'undefined') {
					exhibitArray[exhibitNum].images[viewNum] = {};
					exhibitArray[exhibitNum].images[viewNum].img = new Image();
					exhibitArray[exhibitNum].images[viewNum].img.src = getUrlFromNum(exhibitNum,viewNum);
					exhibitArray[exhibitNum].images[viewNum].aligned = false;
					exhibitArray[exhibitNum].images[viewNum].html = $ml(getV1CrossfadePictureDiv(exhibitArray[exhibitNum].images[viewNum].img.src));
					imageHero = exhibitArray[exhibitNum].images[viewNum];
				//}
			}
						
			if (typeof imageHero.img.width == 'undefined' || imageHero.img.width===0) {
				goNextTimerID = setTimeout(function(){
					V1CrossfadeGotoPicNum(exhibitNum,viewNum);
					exhibitNum = null;
				}, 10);
			}
			else {
				if (!imageHero.aligned) { alignInPicturebox(imageHero); }
				setNextImgHTML(imageHero.html);
//				$ml(getDivContainerString() + ' :first-child')
				$ml('#'+ picContainerDivId +' :first-child')
					.fadeOut(fadeTimeMs, function(){fadeDone(exhibitNum);})
					.next('img').fadeIn(fadeTimeMs)
					.end().appendTo(getDivContainerString());
				if (autoPlayed) {
					docent.pictureboxSelectByNum(exhibitNum, false);
				}
			}
		}
//			viewDiv = $ml(getPictureDiv(getUrlFromNum(nextNum,nextView)));
	}
/*		docent.sgconsole('V1CrossfadeGotoPicNum ' + exhibitNum + ' ' + viewNum);
		clearTimeout(goNextTimerID);
		if(typeof viewNum == 'undefined') { viewNum = 0; }
		if (exhibitNum <= exhibitArray.length - 1) {
			var imageHero = exhibitArray[exhibitNum].images[viewNum];
			if(typeof imageHero == 'undefined') {
					exhibitArray[exhibitNum].images[viewNum] = {};
					exhibitArray[exhibitNum].images[viewNum].img = new Image();
					exhibitArray[exhibitNum].images[viewNum].img.src = getUrlFromNum(exhibitNum,viewNum);
					exhibitArray[exhibitNum].images[viewNum].aligned = false;
					exhibitArray[exhibitNum].images[viewNum].html = $ml(getV1CrossfadePictureDiv(exhibitArray[exhibitNum].images[viewNum].img.src));
					imageHero = exhibitArray[exhibitNum].images[viewNum];
			}
						
			if (typeof imageHero.img.width == 'undefined' || imageHero.img.width===0) {  // kludge in case img not loaded yet?
				goNextTimerID = setTimeout(function(){
					V1CrossfadeGotoPicNum(exhibitNum,viewNum);
					exhibitNum = null;
				}, 10);
			}
			else {
				if(firstCall){
					firstCall = false;
					setNextImgHTML(imageHero.html);
					exhibitArray[exhibitNum].images[viewNum].html.fadeIn(fadeTimeMs);
				}
				else {
					if (!imageHero.aligned) { alignInPicturebox(imageHero); }
					setNextImgHTML(imageHero.html);
					$ml(getDivContainerString() + ' :first-child')
						.fadeOut(fadeTimeMs, function(){fadeDone(exhibitNum);})
						.next('img').fadeIn(fadeTimeMs)
						.end().appendTo(getDivContainerString());
					if (autoPlayed) {
						docent.pictureboxSelectByNum(exhibitNum, false);
					}
				}
			}
		}
//			viewDiv = $ml(getPictureDiv(getUrlFromNum(nextNum,nextView)));
	}*/
	
	
	function v1CrossFadeAddDivs() {
		var startImgNum = docent.getCurrentExhibit();
		var nextImgNum = docent.getCurrentExhibit()+1;
		if(nextImgNum == docent.getExhibitCount()) { nextImgNum = 0; }
		alignInPicturebox(exhibitArray[startImgNum].images[0]);
		preloadImgHTML.remove();
		exhibitArray[startImgNum].images[0].html.appendTo(getDivContainerString());
		exhibitArray[nextImgNum].images[0].html.appendTo(getDivContainerString());
		exhibitArray[startImgNum].images[0].html.fadeIn(fadeTimeMs);
	}
	
	transitions.v1CrossFade = {
		optionName:		"v1CrossFade",
		requirements:	[],
		minLoadToStart:	2,
		initFunc:		setUpV1CrossFade,
		makeImgDivFunc:	getV1CrossfadePictureDiv,
		addImageDivs:	v1CrossFadeAddDivs,
		gotoFunc:		V1CrossfadeGotoPicNum		
	};
	
	// v1CrossFade

////////////////////////////////////////////////////////////////////////////////////////
// CSS3CrossFade

	function getCSS3CrossFadePictureDiv(urlIn) {
		var left = 0;
		var top = 0;
		var cursorString = "";
		if(myClickAction!="none") { cursorString="cursor:pointer;"; }
		return "<img class='hide' style='"+cursorString+"position:absolute;left:0px;top:0px;' src='" + urlIn + "' />";
	}


/* 	#css3pictureboxcontainer img {
    	-moz-transition: opacity 2s ease-in-out 0s;
   }
	#css3pictureboxcontainer img.hide {
	    opacity: 0;
	}
*/

	function setUpCSS3CrossFade() {
		docent.sgconsole('setting up styles');
		var ptop = Math.floor(myHeight/2) - halfPreloader;
		var pleft = Math.floor(myWidth/2) - halfPreloader;
		preloadImgHTML= $ml("<img style='position:absolute;left:"+pleft+"px;top:"+ptop+"px;display:block' src='" + preloaderimageUrl +"'/>");
		preloadImgHTML.appendTo(css3PictureboxContainerDiv);
		 $ml("<style type='text/css'>" +
			"#"+picContainerDivId+" img {"+getVendorCSSBlock("opacity "+fadeTimeS+"s ease-in-out")+"}" +
			"#"+picContainerDivId+" img.hide {opacity:0;}"+
			"</style>").appendTo("head");
	}
	
	function CSS3CrossFadeGotoPicNum(exhibitNum, viewNum) {
		docent.sgconsole('CSS3CrossFadeGotoPicNum');
		clearTimeout(goNextTimerID);
		if(typeof viewNum == 'undefined') { viewNum = 0; }
		if (exhibitNum <= exhibitArray.length - 1) {
			var imageHero = exhibitArray[exhibitNum].images[viewNum];
			if(typeof imageHero == 'undefined') {  // image data object isn't created yet yet, so initialize one for it
					exhibitArray[exhibitNum].images[viewNum] = {};
					exhibitArray[exhibitNum].images[viewNum].img = new Image();
					exhibitArray[exhibitNum].images[viewNum].img.src = getUrlFromNum(exhibitNum,viewNum);
					exhibitArray[exhibitNum].images[viewNum].aligned = false;
					exhibitArray[exhibitNum].images[viewNum].html = $ml(getCSS3CrossFadePictureDiv(exhibitArray[exhibitNum].images[viewNum].img.src));
					imageHero = exhibitArray[exhibitNum].images[viewNum];
			}
						
			if (typeof imageHero.img.width == 'undefined' || imageHero.img.width===0) {  // if image file not loaded then try again in 10ms
				docent.sgconsole('image not loaded yet');
				goNextTimerID = setTimeout(function(){
					CSS3CrossFadeGotoPicNum(exhibitNum,viewNum);
					exhibitNum = null;
				}, 10);
			}
			else {  // image is loaded so go!
				//docent.sgconsole('showing image ' + exhibitNum + ' width ' + imageHero.img.width + ' opacity ' + $ml('#'+picContainerDivId+' > img:eq('+exhibitNum+')').css('opacity'));
				var o = $ml('#'+picContainerDivId+' > img:eq('+exhibitNum+')').css('opacity');  // kludge to force the style to "take", otherwise image doesn't animate the first time on load
				//if (!imageHero.aligned) { alignInPicturebox(imageHero); }
				// New way
                //var nextImgUrl = exhibitArray[exhibitNum].images[viewNum].img.src;
                //var nextElement = signUpForClasses(nextImgUrl); 
                var nextElement = signUpForClasses(imageHero); 

/*
 //             The old way: hide all of them and then show the selected exhibit.  Didn't support views.
 				$ml('#'+picContainerDivId+' > img').addClass('hide');
				$ml('#'+picContainerDivId+' > img:eq('+exhibitNum+')').removeClass('hide');  */
				if (autoPlayed) {
					docent.pictureboxSelectByNum(exhibitNum, false);
				} 
				continueAutoPlay();
			}
		}  
//			viewDiv = $ml(getPictureDiv(getUrlFromNum(nextNum,nextView)));
	}
	
	function signUpForClasses(imageHero) {
	    var imgUrl = imageHero.img.src;
        var nextElementToShow = $ml('#'+picContainerDivId+' > img:eq(0)');
        var elementShowing = $ml('#'+picContainerDivId+' > img:eq(1)');
	    if(!nextElementToShow.hasClass("hide")) { 
            nextElementToShow = $ml('#'+picContainerDivId+' > img:eq(1)');
            elementShowing = $ml('#'+picContainerDivId+' > img:eq(0)');
	    }
	    nextElementToShow.attr('src',imgUrl);
	    alignInPicturebox2($ml(nextElementToShow), imageHero.img);
	    nextElementToShow.removeClass('hide');
	    elementShowing.addClass('hide');
	}
	
    function addCSS3CrossFadeImageDivs() {
        preloadImgHTML.remove();
        var frontStart = exhibitArray[docent.getCurrentExhibit()].images[0].img.src;
//        $ml('<div class="hide"><img src='+frontStart+'/></div>').appendTo(getDivContainerString());
        $ml(getCSS3CrossFadePictureDiv("")).appendTo(getDivContainerString());
        $ml(getCSS3CrossFadePictureDiv("")).appendTo(getDivContainerString());
    }

    transitions.CSS3CrossFade = {
        optionName:     "CSS3CrossFade",
        requirements:   ["csstransitions"],
        minLoadToStart: 2,
        initFunc:       setUpCSS3CrossFade,
        makeImgDivFunc: getCSS3CrossFadePictureDiv,
        addImageDivs:   addCSS3CrossFadeImageDivs, //addAllImageDivs, // addAllImageDivs doesn't include views so we aren't using it anymore
        gotoFunc:       CSS3CrossFadeGotoPicNum     
    };
	
//////////////////////////////////////////////////
// CSS3Flip

	function getCSS3FlipPictureDiv(urlIn) {
		var left = 0;
		var top = 0;
		var cursorString = "";
		if(myClickAction!="none") { cursorString="cursor:pointer;"; }
		return "<div><img class='hide' style='"+cursorString+"position:absolute;left:0px;top:0px;' src='" + urlIn + "' /></div>";
	}

	function addFlipImageDivs() {
		preloadImgHTML.remove();
		var frontStart = exhibitArray[docent.getCurrentExhibit()].images[0].img.src;
		$ml('<div id="card"><figure class="front"><img src="'+frontStart+'"/></figure><figure class="back"><img src=""/></figure></div>').appendTo(getDivContainerString());
	}
	
	function setUpCSS3Flip() {
		docent.sgconsole('setting up styles');
		var ptop = Math.floor(myHeight/2) - halfPreloader;
		var pleft = Math.floor(myWidth/2) - halfPreloader;
		preloadImgHTML= $ml("<img style='position:absolute;left:"+pleft+"px;top:"+ptop+"px;display:block' src='" + preloaderimageUrl +"'/>");
		preloadImgHTML.appendTo(css3PictureboxContainerDiv);
		 $ml("<style type='text/css'>" +
			"#"+ picContainerDivId +" {position: relative;-webkit-perspective: 800;}" +
			"#card {width: 100%;height: 100%;position: absolute;-webkit-transition: -webkit-transform "+ fadeTimeS +"s;-webkit-transform-style: preserve-3d;-webkit-transform-origin: right center;}" +
			"#card.flipped {-webkit-transform:  translateX( -100% ) rotateY( -180deg );}" +
			"#card figure {display: block;height: 100%;width: 100%;position: absolute;-webkit-backface-visibility: hidden;-webkit-margin-before: 0em;-webkit-margin-after: 0em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;}" +
			"#card .back {-webkit-transform: rotateY( 180deg );}" +
			"</style>").appendTo("head");	
	}
	
	function setHiddenImage(url) {
		var switcheroo = null;
		var flipped = $ml('#' + 'card').hasClass('flipped');
		if(flipped) {
			switcheroo = $ml('#card figure.front img');
		}
		else {
			switcheroo = $ml('#card figure.back img');
		}
		switcheroo.attr('src',url);
	}
	
	function CSS3FlipGotoPicNum(exhibitNum, viewNum) {
		docent.sgconsole('CSS3CrossFlipPicNum');
		clearTimeout(goNextTimerID);
		if(typeof viewNum == 'undefined') { viewNum = 0; }
		if (exhibitNum <= exhibitArray.length - 1) {
			var imageHero = exhibitArray[exhibitNum].images[viewNum];
			if(typeof imageHero == 'undefined') {  // image data object isn't created yet yet, so initialize one for it
					exhibitArray[exhibitNum].images[viewNum] = {};
					exhibitArray[exhibitNum].images[viewNum].img = new Image();
					exhibitArray[exhibitNum].images[viewNum].img.src = getUrlFromNum(exhibitNum,viewNum);
					exhibitArray[exhibitNum].images[viewNum].aligned = false;
					exhibitArray[exhibitNum].images[viewNum].html = $ml(getCSS3FlipPictureDiv(exhibitArray[exhibitNum].images[viewNum].img.src));
					imageHero = exhibitArray[exhibitNum].images[viewNum];
			}
						
			if (typeof imageHero.img.width == 'undefined' || imageHero.img.width===0) {  // if image file not loaded then try again in 10ms
				docent.sgconsole('image not loaded yet');
				goNextTimerID = setTimeout(function(){
					CSS3FlipGotoPicNum(exhibitNum,viewNum);
					exhibitNum = null;
				}, 10);
			}
			else {  // image is loaded so go!
				//docent.sgconsole('showing image ' + exhibitNum + ' width ' + imageHero.img.width + ' opacity ' + $ml('#'+picContainerDivId+' > img:eq('+exhibitNum+')').css('opacity'));
				var o = $ml('#'+picContainerDivId+' > img:eq('+exhibitNum+')').css('opacity');  // kludge to force the style to "take", otherwise image doesn't animate the first time on load
//				if (!imageHero.aligned) { alignInPicturebox(imageHero); }
				var nextImgUrl = exhibitArray[exhibitNum].images[viewNum].img.src;
				setHiddenImage(nextImgUrl);
				$ml('#card').toggleClass('flipped');
//				$ml('#'+picContainerDivId+' > div').addClass('hide');
//				$ml('#'+picContainerDivId+' > div:eq('+exhibitNum+')').removeClass('hide'); 
				if (autoPlayed) {
					docent.pictureboxSelectByNum(exhibitNum, false);
				} 
				continueAutoPlay();

/*				setNextImgHTML(imageHero.html);
				$ml(getDivContainerString() + ' :first-child')
					.fadeOut(fadeTimeMs, function(){fadeDone(exhibitNum);})
					.next('img').fadeIn(fadeTimeMs)
					.end().appendTo(getDivContainerString());
				if (autoPlayed) {
					docent.pictureboxSelectByNum(exhibitNum, false);
				} */
			}
		}  
//			viewDiv = $ml(getPictureDiv(getUrlFromNum(nextNum,nextView)));
	}
	
    transitions.CSS3Flip = {
        optionName:     "CSS3Flip",
        requirements:   ["csstransitions","csstransforms","csstransforms3d"],
        minLoadToStart: 2,
        initFunc:       setUpCSS3Flip,
        makeImgDivFunc: getCSS3FlipPictureDiv,
        addImageDivs:   addFlipImageDivs,
        gotoFunc:       CSS3FlipGotoPicNum      
    };
    

///////////////////////////////////////////////
// Utility Functions

	function continueAutoPlay(exhibitNum) {
		targetNum = getNextImgNum();
		if (autoPlay && docent.getCurrentView() === 0) {  // we don't support autoplay for viewsheets
			if (targetNum == docent.getExhibitCount() || targetNum === 0) {
				if (endAction == 'loop') {
					targetNum = 0;
				} else {
					if(endAction=='gotourl') {
						if(endUrl!==null && endUrl!=='') {
							docent.gotoUrl(endUrl);
						}
					}
				}
			}
			autoPlayNext(targetNum);
		}		
	}
	
	function unknownTransition(transName) {
		if(transitions.contructor == Array) {
			for(var i = 0; i<transitions.length; i++) {
				if(transitions[i].optionName == transName) {
					return false;
				}
			}
		}
		else {
			if ( typeof transitions[transName] != 'undefined') {
				return false;
			}
		}
		return true;
	}
	
	function findTransition(selectedTransitionName) {
		if(transitions.contructor == Array) {
			for(var curTrans = 0; curTrans < transitions.length; curTrans++) {
				if(transitions[curTrans].optionName == selectedTransitionName) {
					return transitions[curTrans];
				}
			}
			return null;
		}
		else {
			return transitions[selectedTransitionName];
		}
	}

	// determines if current browser supports selected transition type
	function browserSucks() {
		if(Modernizr && Modernizr.csstransitions) {
			var selectedTransitionNeeds = selectedTransition.requirements;
			if(selectedTransitionNeeds) {
				if(selectedTransitionNeeds.length > 0) {
					for(var curNeed = 0; curNeed < selectedTransitionNeeds.length; curNeed++) {
						if(!Modernizr[selectedTransitionNeeds[curNeed]]) {
							return true;  // missing some required feature
						}
					}
					// if we made it here then all features were found
					return false;
				}
				else {
					return false; // empty requirements, so we should be good
				}
			}
			else {
				alert('The transition ' + getSelectedTransitionObject().optionName + ' has no compatibility table.  Please add one in sg_animcss_picturebox.js');
				return true;  // no compatibility array!  Panic.
			}
		}
		return true;  // no Modernizr or no csstransitions so panic and declare browser sucks to use old code
	}

// ****
// **** End CSS3 Stuff
// ****

		
	function getPictureboxQuery() {
		// sample : '#divname img'
		return '#'+myDivID+' img';
	}
	
	this.getRightClickBlock = function () { return rightClickBlock; };
	
	this.displayByUrl = function(imageUrl) {  // disabled right now
		alert('displayByUrl() not supported by CSS3 animated picturebox');
		var pbq = getPictureboxQuery();
		//$ml(pbq).attr('src', imageUrl);
	};
	
	this.displayByNum = function(exhibitNum, viewNum) {
		docent.sgconsole('CSS PICTUREBOX displayByNum()');
		autoPlayed = false;
		if (viewNum !== 0) {
			autoPlay = false;
		}
		selectedTransition.gotoFunc(exhibitNum,viewNum);
		//this.displayByUrl(getUrlFromNum(exhibitNum,viewNum));
	};
	
	function getUrlFromNum(exhibitNum, viewNum){
		return $ml('exhibits exhibit:eq('+ exhibitNum +') views view:eq('+ viewNum +') picturebox url',xml).text();
	}
	
	
	
/*	function startShow() {
		$ml('#'+myDivID+' img:eq(0)').css('display','block');
		setInterval(function(){
			$ml('#'+myDivID+' :first-child').fadeOut()
			.next('img').fadeIn()
			.end().appendTo('#'+myDivID);}, 
		6000);	
	}
*/	




/*  // This one works well without views
	function gotoPicNum(nextNum) {
		clearTimeout(goNextTimerID);
		if (nextNum <= imgArray.length - 1) {
			if (!imgArray[nextNum].img.complete) {
				goNextTimerID = setTimeout(function(){
					gotoPicNum(nextNum);
					nextNum = null;
				}, 10);
			}
			else {
				if (!imgArray[nextNum].centered) { centerInPicturebox(nextNum); }
				setNextImgHTML(imgArray[nextNum].html);
				$ml('#' + myDivID + ' :first-child')
					.fadeOut(fadeTimeMs, function(){fadeDone(nextNum);nextNum=null;})
					.next('img').fadeIn(fadeTimeMs)
					.end().appendTo('#' + myDivID);
				if (autoPlayed) {
					docent.pictureboxSelectByNum(nextNum, false);
				}
			}
		}
//			viewDiv = $ml(getPictureDiv(getUrlFromNum(nextNum,nextView)));
	}
	*/
	// this version of align is used in CSS3 functions and takes the prepared img element rather than our custom image holder object.
    function alignInPicturebox2(imgElementToAlign, imgToAlign) {
        var pboxWidth = $ml('#'+myDivID).width();
        var pboxHeight = $ml('#'+myDivID).height();
        var imgHeight = imgToAlign.height;
        var imgWidth = imgToAlign.width;
        // assume center h & v
        var top =  Math.floor(pboxHeight/2) - Math.floor(imgHeight/2);
        var left = Math.floor(pboxWidth/2) - Math.floor(imgWidth/2);
        // check for non centered
        if(pboxAlign.indexOf("left") != -1) {
            left = 0;
        }
        if(pboxAlign.indexOf("right") != -1) {
            left = pboxWidth-imgWidth;
        }
        if(pboxAlign.indexOf("top") != -1) {
            top = 0;
        }
        if(pboxAlign.indexOf("bottom") != -1) {
            top = pboxHeight-imgHeight;
        }
        
        imgElementToAlign.css('top',top+'px');
        imgElementToAlign.css('left',left+'px');
        //imageObjToAlign.aligned = true;
    }

	
	function alignInPicturebox(imageObjToAlign) {
		var pboxWidth = $ml('#'+myDivID).width();
		var pboxHeight = $ml('#'+myDivID).height();
		// assume center h & v
		var top =  Math.floor(pboxHeight/2) - Math.floor(imageObjToAlign.img.height/2);
		var left = Math.floor(pboxWidth/2) - Math.floor(imageObjToAlign.img.width/2);
		// check for non centered
		if(pboxAlign.indexOf("left") != -1) {
			left = 0;
		}
		if(pboxAlign.indexOf("right") != -1) {
			left = pboxWidth-imageObjToAlign.img.width;
		}
		if(pboxAlign.indexOf("top") != -1) {
			top = 0;
		}
		if(pboxAlign.indexOf("bottom") != -1) {
			top = pboxHeight-imageObjToAlign.img.height;
		}
		
		imageObjToAlign.html.css('top',top+'px');
		imageObjToAlign.html.css('left',left+'px');
		imageObjToAlign.aligned = true;
	}
	
	function setNextImgHTML(htmlIn) {
		$ml(getDivContainerString() + ' :eq(1)').remove();
		htmlIn.appendTo(getDivContainerString());
	}
	
	function fadeDone(replacedByNum) { 
		var targetNum = replacedByNum + 1;
		if (autoPlay) {
			if (targetNum == docent.getExhibitCount()) {
				if (endAction == 'loop') {
					targetNum = 0;
				} else {
					if(endAction=='gotourl') {
						if(endUrl!==null && endUrl!=='') {
							docent.gotoUrl(endUrl);
						}
					}
				}
			}
			autoPlayNext(targetNum);
		}
	}

	function autoPlayNext(nextToPlay){
		autoPlayed = true;
		clearTimeout(goNextTimerID);
		if (nextToPlay === 0 && endAction == "stop") {
			return;
		}
		goNextTimerID = setTimeout(function(){
			selectedTransition.gotoFunc(nextToPlay);
			nextToPlay = null;
		}, delayMs);
	}

	function imageIsLoaded(imageNum) {  // returns true if image file has been successfully loaded
		if (typeof exhibitArray[imageNum] != "undefined" && 
		    typeof exhibitArray[imageNum].images[0].img.width != "undefined" &&
		    exhibitArray[imageNum].images[0].img.width > 0) {
				return true;
		    }
		else {
		    return false;
		}
	}
	

		
	function addAllImageDivs() {
		preloadImgHTML.remove();
		for(var curSlide = 0; curSlide < docent.getExhibitCount(); curSlide++) {
			exhibitArray[curSlide].images[0].html.appendTo(getDivContainerString());
		}
	}
	
	function getNextImgNum() {
		var nextImgNum = docent.getCurrentExhibit()+1;
		if(nextImgNum == docent.getExhibitCount()) { nextImgNum = 0; }
		return nextImgNum;
	}
	
	// checkLoad waits until the desired number of images in order starting with the
	//           current exhibit have loaded and then calls the callback.
	//			 right now it also calls the function that adds the images into the html
	//			 this may need to change or be optional depending on future transition types
	function checkLoad() {
		var checkImgNum = docent.getCurrentExhibit();
		for(var i = 0; i < selectedTransition.minLoadToStart; i++) {
			if(!imageIsLoaded(checkImgNum)) {
				checkLoadTimerID = setTimeout(checkLoad, 10);
				return;
			}
			if(checkImgNum == (docent.getExhibitCount() - 1)) {
				checkImgNum=0;
			}
			else {
				checkImgNum++;
			}
		}
		selectedTransition.addImageDivs();
		if(selectedTransition!= transitions.v1CrossFade) { selectedTransition.gotoFunc(docent.getCurrentExhibit()); }
		if (autoPlay) {
			autoPlayNext(getNextImgNum());
		}
	}
	
	function preload() {
		for(var curSlide = 0; curSlide < docent.getExhibitCount(); curSlide++) {
			var victim = {};
			victim.images = [];
			victim.images[0] = {};
			victim.images[0].img = new Image();
			var imgSrc = getUrlFromNum(curSlide,0);
			victim.images[0].img.src = imgSrc;
			victim.images[0].html = $ml(selectedTransition.makeImgDivFunc(imgSrc));
			victim.images[0].positioned = false;
			exhibitArray[curSlide]  = victim;
		}
		checkLoad(selectedTransition.minLoadToStart, selectedTransition.gotoFunc);
	}
	
	function oldcheckLoad() {
		var startImgNum = docent.getCurrentExhibit();
		var nextImgNum = docent.getCurrentExhibit()+1;
		if(nextImgNum == docent.getExhibitCount()) { nextImgNum = 0; }
		if (typeof exhibitArray[startImgNum] != "undefined" && typeof exhibitArray[nextImgNum] != "undefined") {
			if (exhibitArray[startImgNum].images[0].img.width !== 0 && exhibitArray[nextImgNum].images[0].img.width !== 0) {  // first one loaded, so show
				//alignInPicturebox(exhibitArray[startImgNum].images[0]);
				preloadImgHTML.remove();
				exhibitArray[startImgNum].images[0].html.appendTo(getDivContainerString());
				exhibitArray[nextImgNum].images[0].html.appendTo(getDivContainerString());
				//exhibitArray[startImgNum].images[0].html.fadeIn(fadeTimeMs);
				if (autoPlay) {
					autoPlayNext(nextImgNum);
				}
				else {
					selectedTransition.gotoFunc(startImgNum);
				}
			}
			else {
				checkLoadTimerID = setTimeout(checkLoad, 10);
			}
		}
	}
	
	function getDivContainerString() {
		return '#' + myDivID + ' > div';
	}
		
	function initPictureBox() {
		css3PictureboxContainerDiv = $ml('<div style="position:relative" id="'+picContainerDivId+'"></div>');
		css3PictureboxContainerDiv.appendTo('#'+myDivID);

		selectedTransition.initFunc();
		preload();
		
		if (myClickAction != "none") {
			$ml(getDivContainerString()).bind('click',function(event){myClick(event);});  // we may have to allow transitions to customize this

		}		
		if(rightClickBlock) { 
			docent.sgconsole('3 disabling right click');
			css3PictureboxContainerDiv.bind("contextmenu",function(e){ alert('Context menu disabled for this image.'); return false; } ); 
		}
		
/*		var ptop = Math.floor(myHeight/2) - halfPreloader;
		var pleft = Math.floor(myWidth/2) - halfPreloader;
		preloadImgHTML= $ml("<img style='position:absolute;left:"+pleft+"px;top:"+ptop+"px;display:block' src='" + preloaderimageUrl +"'/>");
		var container = $ml('<div style="position:relative"></div>');
		preloadImgHTML.appendTo(container);
		container.appendTo('#'+myDivID);

		preload();

		docent.sgconsole('made thumbs... ');
		
		if(rightClickBlock) { 
			docent.sgconsole('3 disabling right click');
			$ml(getDivContainerString()).bind("contextmenu",function(e){ alert('Context menu disabled for this image.'); return false; } ); 
		}
		
		if (myClickAction != "none") {
			$ml(getDivContainerString()).bind('click',function(event){myClick(event);});

		} */
	}
	

	
	function myClick(e) { 
		docent.sgconsole("picturebox clicked");
		docent.pictureboxClick(true,null);
		return false;
	}
	
	function getOption(opt) {
		return docent.getPictureboxOption(opt);
		//var w = $ml('options > picturebox > option[varname='+opt+']', xml).attr("value");
		
	}
	
	
			
	 function processXML () {
		sgalert("SG Standard Picturebox: processXML", this);
		
		myWidth = getOption("width");
		myHeight = getOption("height");
		myDivID = getOption("divname");
		pboxAlign = getOption("alignment");
		rightClickBlock = getOption("rightclickblock");
		myClickAction = getOption("clickaction");
		myHoverAction = getOption("hoveraction");
		fadeTimeS = getOption("autoadvancedtransitiontimeseconds");
		fadeTimeMs = fadeTimeS * 1000;
		delayS = getOption("autoadvancedelayseconds");
		delayMs = delayS * 1000;
		autoPlay = getOption("autoadvance");
		
		endAction = getOption("endaction");
		endUrl = getOption("endurl");
		preloaderimageUrl= getOption("preloaderimage");

		// New stuff for CSS3
		var selectedTransitionName = getOption("transition");
		selectedTransition = findTransition(selectedTransitionName);
		if (typeof selectedTransition != "object"	 ||								// if we don't have a valid transition, perhaps because of old xml or unknown transition name
			selectedTransition === null ||
			selectedTransition.minLoadToStart > docent.getExhibitCount() ||			// or there aren't enough images to support the transition
			browserSucks()) {								// or browser doesn't support required feature
				docent.sgconsole('A problem is forcing me to choose crossfade transition');
				selectedTransition = transitions.v1CrossFade;   // use basic crossfade 
				return;
		}
	}
	
	function init() {
		sgalert("SG AnimCSS Picturebox: Init", this);
		if(getOption("type")!="animatedcss") { alert("Type mismatch between picturebox javascript and data!"); return;}
		if(getOption("version") > 2.0) { alert("Picturebox javascript version older than data version!"); return;}
		processXML();
		if(typeof selectedTransition == 'object') {
			initPictureBox();
		}
	}
		
	init();
};