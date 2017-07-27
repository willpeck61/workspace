/**
 *    Copyright (c) 2009-2010, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

/*
 * 	Notes:
 * 	1. Nice HTML thumbsheet here: http://plt-scheme.org/screenshots/
 */

var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.gallery1) { com.medialab.sg.gallery1 = {};}


com.medialab.sg.gallery1.thumbsheetSiteGrinderPagedCSS =
function (thumbSheetXMLIn, modeIn, docentIn) { 
	var that=this;
	var debug = false;
	var sgalert = function(msg) { if (debug) {
		alert(msg);
	} };
	var docent= docentIn;
	var docentName = docent.getName();

	//alert(docentIn);
	var xml = thumbSheetXMLIn;
	var clickAction = "picturebox";
	var hoverAction = "none";
	var thumbWidth = 0;
	var totalThumbWidth = 0;
	var totalThumbHeight = 0;
	var thumbHeight = 0;
	var sheetWidth = 0;
	var sheetHeight = 0;
	var borderWidth = 1;  
	var sheetMode = modeIn;
	var sheetDivID = "not set yet";
	var thumbDivID = "not set yet";
	var linkClass = "not set yet";
	var d = new Date(); // for constructing a unique name
	var previewStyleName = "imagePreview" + d.getTime();
	var autoSetNextPrevVisibility = true;

	var cssInitialized=false;

// To adapt this:
// 1. Alter consturction of thumbsheet DIVs
// 2. Alter numToSubsheetElement based on thumbsheet DIV structure, then test
// 3. Adapt variables to plugin xml
// 4. Load variables in processXML then test

// Subset animation vars

	var visibleSet=0; 
	var setToCome = -1;
	var animating = false;
	
	var numSubSheetThumbs = 0;
	var numSubSheets = 0;
	var subSheetWidth = 0;
	var subSheetsWidth = 0;
	
	var numThumbs = getNumThumbs();
	var curThumb = getCurThumb();
	
	var transitionTime = 1;
	var transitionEffect = 'fade';

// subset animation funcs

	function numToSubsheetElement(num) {
		return $ml('div#' + sheetDivID + ' > div > div > div')[num];
	}
	
	function getSlidingDiv() {
		return $ml('div#' + sheetDivID + ' > div > div')[0];
	}
	
	 this.nextThumbSet = function() {
	 	docent.sgconsole('thumbsheet nextSet()');
		if (!animating && visibleSet < (numSubSheets - 1)) {
			setToCome = visibleSet+1;
			startSetTransition();
		}
	}
	
	this.prevThumbSet = this.previousThumbSet =function() {
	 	docent.sgconsole('thumbsheet prevSet()');
		if (!animating && visibleSet > 0) {
			setToCome = visibleSet-1;
			startSetTransition();
		}
	}
	
	function transitionThumbsSlide(destDivNum) {
		animating=true;
		var slideStart = parseInt(getSlidingDiv().style.left);
		if(typeof(slideStart) == 'undefined' || isNaN(slideStart)) { docent.sgconsole('bad slidestart ' + slideStart); return; }
		var slideTarget = destDivNum * subSheetWidth * -1;
		var t = new Tween(getSlidingDiv().style,'left',Tween.regularEaseIn,slideStart,slideTarget,transitionTime,'px');
		t.onMotionFinished = function(e) { 
			visibleSet = destDivNum;
			handleNextPrevSetDisplay();
			animating=false; 
		}
		t.start();
	}
	
	function transitionThumbsFade(curDiv, nextDiv){
		docent.sgconsole('transitionThumbsFade');
		//var tt = new OpacityTween(nextDiv,Tween.regularEaseIn,0,0,0).start();
		animating=true;
		var t = new OpacityTween(curDiv,Tween.regularEaseIn,100,0,transitionTime);
		t.onMotionFinished = function(e){
			curDiv.style.display='none';
			nextDiv.style.display='block';
			showThumbsFade(nextDiv);
		}
		t.start();
	}
	
	function showThumbsFade(divvie) {
	
		var s = new OpacityTween(divvie,Tween.regularEaseIn,0,100,transitionTime);
		s.onMotionFinished = function () { animating = false; }
		visibleSet = setToCome;
		handleNextPrevSetDisplay();
		
		docent.sgconsole('visible set now ' + visibleSet);
		s.start();
	}
	
	function startSetTransition() {
		docent.sgconsole('startSetTransition');
		switch(transitionEffect) {
			case 'fade':
				transitionThumbsFade(numToSubsheetElement(visibleSet),numToSubsheetElement(setToCome));
				break;
			case 'slide':
				transitionThumbsSlide(setToCome);
				break;
			default:
				docent.sgconsole('UNKNOWN THUMBSHEET TRANSITION ENCOUTERED: ' + transitionEffect);

		}		
	}
	
	function handleNextPrevSetDisplay() {
		if (autoSetNextPrevVisibility) {
			if (sheetMode == 'exhibit') {
				$ml('a:[onClick*="'+docentName+'.nextThumbSet"]').css('display', 'block');
				$ml('a:[onClick*="'+docentName+'.previousThumbSet"]').css('display', 'block');
				if (visibleSet == numSubSheets - 1) {
					$ml('a:[onClick*="'+docentName+'.nextThumbSet"]').css('display', 'none');
					docent.sgconsole('turning off nextset');
				}
				if (visibleSet == 0) {
					$ml('a:[onClick*="'+docentName+'.previousThumbSet"]').css('display', 'none');
					docent.sgconsole('turning off prevset');
				}
			} else {
				$ml('a:[onClick*="'+docentName+'.nextViewSet"]').css('display', 'block');
				$ml('a:[onClick*="'+docentName+'.previousViewSet"]').css('display', 'block');
				if (visibleSet == numSubSheets - 1) {
					$ml('a:[onClick*="'+docentName+'.nextViewSet"]').css('display', 'none');
					docent.sgconsole('turning off nextset');
				}
				if (visibleSet == 0) {
					$ml('a:[onClick*="'+docentName+'.previousViewSet"]').css('display', 'none');
					docent.sgconsole('turning off prevset');
				}
			}
		} else {
			docent.sgconsole('not turning off next/prev');
		}
	}


// END SUBSET ANIMATION FUNCTIONS
	

	this.select = function (thumbnum) {		
		$ml('#' + sheetDivID + ' .it').removeClass('it');
		$ml('#' + sheetDivID + ' a:eq('+ thumbnum +')').addClass('it');
	}
	

	
	
	function init() {
		sgalert("SG Standard Thumbsheet: Init", this);
		if(getOption("type")!="sitegrinderpagedcss") { alert("Type mismatch between thumbsheet javascript and data!"); return;}
		if(getOption("version") > 1.0) { alert("Thumbsheet javascript version older than data version!"); return;}
		processXML();
		initSheet();
		$ml("div#"+sheetDivID).css("height","");

	}
	
	 function processXML () {
		sgalert("SG Standard Thumbsheet: processXML", this);
		/*
		 * we'll need to know:
		 * 
		 * 		- thumb div name prefix
		 * 		- options (scroll? change on hover?)
		 * 
		 *     1. count the thumbs (to figure out how to go page to page
		 */
		
		numExhibits = docent.getExhibitCount();
		sgalert("ne:"+numExhibits);
		sheetWidth = getOption('sheetwidth');
		sheetHeight = getOption("sheetheight");
		thumbWidth = getOption("thumbwidth");
		thumbHeight = getOption("thumbheight");
		sheetDivID = getOption("sheetdivname");
		thumbDivID = getOption("thumbdivname");
		linkClass = getOption("linkclassname");
		borderWidth = getOption('borderwidth', borderWidth);
		hoverAction = getOption("hoveraction", hoverAction);
		clickAction = getOption("clickaction", clickAction);
		transitionTime = getOption("transitiontime", transitionTime);
		transitionEffect = getOption("transitioneffect", transitionEffect);
		autoSetNextPrevVisibility = getOption('autosetnextprevvisibility',autoSetNextPrevVisibility);
		docent.sgconsole('autoSetNextPrevVisibility is ' + autoSetNextPrevVisibility);
		return;
	}
		
	this.updateSheet = function() {
		docent.sgconsole(sheetMode + " : updateSheet");
		initSheet();
	}
	
	
	function jqClick(e) {
		var thisPanel = $ml(this).parent();
		var panelNum = $ml(this).parent().parent().children().index(thisPanel);
		docent.sgconsole('jqClick() thisPanel '+ thisPanel +' panelNum '+ panelNum);
		var numInThisPanel = $ml(thisPanel).children().length;
		var internalThumbNum = $ml(this).parent().children().index(this); 
		var thumbNum = numSubSheetThumbs * panelNum + internalThumbNum;
		docent.sgconsole('jqClick() css '+ sheetMode + ' internalthumbnum ' + internalThumbNum + ' numSubSheetThumbs ' + numSubSheetThumbs + ' thumb '+ thumbNum +' clicked');
		switch (clickAction) {
			case 'url':
				var link = $ml.trim($ml('exhibits exhibit:eq('+ thumbNum +') view:eq(0) link',xml).text());
				docent.gotoUrl(link);
				break;
			case 'picturebox':
				docent.selectThumb(thumbNum,sheetMode,true);
				break;
			case 'bigbox':
				docent.selectThumb(thumbNum,sheetMode,true);
				docent.openBigbox(sheetMode);
				break;
			default:
				docent.sgconsole('css thumbs passed unknwn click action '+ clickAction);
				break;
		}
		return false;
	}
	
	function jqHover(e) {
		var thisPanel = $ml(this).parent();
		var panelNum = $ml(this).parent().parent().children().index(thisPanel);
		//console.log('jqHover() thisPanel '+ thisPanel +' panelNum '+ panelNum);
		var numInThisPanel = $ml(thisPanel).children().length;
		var internalThumbNum = $ml(this).parent().children().index(this);
		var thumbNum = numSubSheetThumbs * panelNum + internalThumbNum;
		docent.sgconsole('jqHover() css '+ sheetMode + ' internalthumbnum ' + internalThumbNum + ' numSubSheetThumbs ' + numSubSheetThumbs + ' thumb '+ thumbNum +' clicked');
		switch (hoverAction) {
			case 'picturebox':
				docent.selectThumb(thumbNum,sheetMode,true);
				break;
			default:
				docent.sgconsole('css thumbs passed unknwn hover action '+ clickAction);
				break;
		}
	}

	this.getAThumbIterator = function(n,data,url){
		data.containerText += getAThumb(n,url);
	}
	
	function getAThumb(thumbnum, bkgdURL) {
		var tdiv = "";
		var curThumbToMake=0;
		if(sheetMode=='exhibit') { curThumbToMake = docent.getCurrentExhibit(); }
		else { curThumbToMake = docent.getCurrentView(); }
		var className = "notit";
		if(curThumbToMake==thumbnum) { className='it'; }
		
		// Thumb div
		tdiv += '<a href="#" class="'+ className + '" ';
		tdiv += 'style="background-image:url('+ docent.url2CssUrl(bkgdURL) + '); background-position: 50% 50%; background-repeat: no-repeat;" ';
		if (hoverAction == 'floatingpreview') {
			var previewUrl = "";
			if (sheetMode == 'exhibit') {
				previewUrl = docent.num2url(thumbnum, 0);
			}
			else {
				previewUrl = docent.num2url(docent.getCurrentExhibit(), thumbnum);
			}
			tdiv += ' rel="' + previewUrl + '" ';
		}
		tdiv += ' ></a>';		
		
		docent.sgconsole ("Adding thumb div: " + tdiv);
		return tdiv;
	}
	
// New version of initsheet using docent iterator

	
/*	function maxCanFit(containerExtent, containeeExtent, padding) {
		docent.sgconsole('maxCanFit ' + containerExtent + ' ' + containeeExtent + ' ' + padding);
		var containees = 0;
		var containeeTotal = padding;
		while (containeeTotal < containerExtent) {
			containees++;
			containeeTotal += containeeExtent;
			containeeTotal += 2 * padding;
		}
		return containees;
	}
	*/
	function maxCanFit(containerExtent, containeeExtent, padding){
		docent.sgconsole('maxCanFit ' + containerExtent + ' ' + containeeExtent + ' ' + padding);
		var maxFittable = Math.floor(containerExtent/(containeeExtent+padding));
		if(maxFittable==0) { maxFittable = 1; }
		return maxFittable;
	}
	
    function measureSheet() {
		docent.sgconsole('measureSheet');
		totalThumbWidth = thumbWidth + 2 * getOption('hinset') + 2 * borderWidth;
		totalThumbHeight = thumbHeight + 2 * getOption('vinset') + 2 * borderWidth;
		var maxCanFitH = maxCanFit(sheetWidth, totalThumbWidth, getOption('hspacing'));
		var maxCanFitV = maxCanFit(sheetHeight, totalThumbHeight, getOption('vspacing'));
		docent.sgconsole('maxCanFitH: ' + maxCanFitH);
		docent.sgconsole('maxCanFitV: ' + maxCanFitV);
		
		subSheetWidth = maxCanFitH * (totalThumbWidth + getOption('hspacing') +  + getOption('hspacing'));
		docent.sgconsole('subsheetwidth: ' + subSheetWidth);
		
		numSubSheetThumbs = maxCanFitH * maxCanFitV;
		docent.sgconsole('numSubSheetThumbs: ' + numSubSheetThumbs);
		
		if(numThumbs <= numSubSheetThumbs) { numSubSheets = 1; }
		else {
			numSubSheets = Math.floor(numThumbs/numSubSheetThumbs);
			if(numThumbs % numSubSheetThumbs > 0) { numSubSheets++; }
		}
		docent.sgconsole('numSubSheets: ' + numSubSheets);
		
		subSheetsWidth = numSubSheets * subSheetWidth;
	}
	
	function getCurThumb() {
		if(sheetMode=='exhibit') { return docent.getCurrentExhibit(); }
		else { return docent.getCurrentView() }
	}
	
	function getNumThumbs() {
		if(sheetMode=='exhibit') { return docent.getExhibitCount(); }
		else { return docent.getNumViews() }
	}
	
	function getFadingSubSheetDisplay(curSubSheet) {
		if(transitionEffect != 'fade') { return 'block'; }
		
		if(curThumb >= (curSubSheet * numSubSheetThumbs) && curThumb < ((curSubSheet * numSubSheetThumbs) + numSubSheetThumbs)) {
			visibleSet = curSubSheet;
			return 'block';
		} else {
			return 'none';
		}
	}
	
	function doThumbDivs(){
		var curHtml = "";
		var curThumbToAdd = 0;
		for (var curSubSheet = 0; curSubSheet < numSubSheets; curSubSheet++) {
			curHtml += '<div style="width:' + subSheetWidth + 'px;float:left;display:' + getFadingSubSheetDisplay(curSubSheet) + ';filter:Alpha(opacity=100);">';
			for (var curSubSheetThumb = 0; curSubSheetThumb < numSubSheetThumbs; curSubSheetThumb++) {
				if ((curSubSheet * numSubSheetThumbs) + curSubSheetThumb >= numThumbs) {
					break;
				}
				curHtml += getAThumb(curThumbToAdd, docent.getThumbImage(curThumbToAdd, sheetMode));
				curThumbToAdd++;
			}
			curHtml += "</div>"
		}
		return curHtml
	}
	
	function initSheet() {
		docent.sgconsole('THUMBSHEET: initSheet 1');
		numThumbs = getNumThumbs();
		curThumb = getCurThumb();

		initSheetCSS();
		measureSheet();
		
		var sheetHTML = "";
		sheetHTML += '<div style="width:'+sheetWidth+'px;height:'+sheetHeight+'px;overflow:hidden;position:relative;">';  // position relative fixes IE bug with overlfow hidden
		sheetHTML += '<div style="height:'+sheetHeight+'px; width:'+subSheetsWidth+'px; left:0; top:0;position:relative;">';
		sheetHTML += doThumbDivs(sheetHTML);
		sheetHTML += '</div></div>';		
		//docent.sgconsole(sheetHTML);

		var targetDiv = $ml("#"+sheetDivID);
		targetDiv.html(sheetHTML);
		
		// 	Set up click events
		
		if(clickAction && clickAction!='none') {
			$ml("#" + sheetDivID + ' a').bind('click',jqClick);
		}
		
		if(hoverAction && hoverAction!='none') {
			if (hoverAction == 'floatingpreview') {
				$ml('#' + sheetDivID + ' a').imgPreview({
					srcAttr: 'rel',
					containerID: previewStyleName,
					onShow: function(link){
						$ml('img', this).stop().css({
							opacity: 0
						});
					},
					onLoad: function(){
						// Animate image
						$ml(this).animate({
							opacity: 1
						}, 300);
					}
				});
			}
			else {
				docent.sgconsole('binding '+ sheetDivID +' a mouseover to ' + hoverAction);
				$ml("#" + sheetDivID + ' a').bind('mouseover', jqHover);
			}
		}
		
		handleNextPrevSetDisplay();
		$ml('#'+sheetDivID + ' a').css('outline','none');
	}
/*
	function initSheetOrig () { 
		initSheetCSS();
		var data = new Object();
		data.containerText = "";
		// Create thumbs
		if (sheetMode == "exhibit") {
			docent.iterateExhibitThumbImageUrls(that.getAThumbIterator, data);
		}
		else {
			docent.iterateViewThumbImageUrls(that.getAThumbIterator, data, null);	// null means use current exhibit	
		}
		data.containerText += '<div style="clear:left"></div>';
		var targetDiv = $ml("#"+sheetDivID);
		targetDiv.html(data.containerText);
		
		if(clickAction && clickAction!='none') {
			$ml("#" + sheetDivID + ' a').bind('click',jqClick);
		}
		
		if(hoverAction && hoverAction!='none') {
			if (hoverAction == 'floatingpreview') {
				$ml('#' + sheetDivID + ' a').imgPreview({
					srcAttr: 'rel',
					containerID: previewStyleName,
					onShow: function(link){
						$ml('img', this).stop().css({
							opacity: 0
						});
					},
					onLoad: function(){
						// Animate image
						$ml(this).animate({
							opacity: 1
						}, 300);
					}
				});
			}
			else {
				docent.sgconsole('binding mouseover to ' + hoverAction);
				$ml("#" + sheetDivID + ' a').bind('mouseover', jqHover);
			}
		}
	}
*/

	//
	// defaultVal is to be used if option not found
	// docent expected to return options correctly typed as string, boolean etc.
	//
	// probably on next refactoring should stop using '' as undefined val and actually use undefined, but will have to change every js file
	//
	function getOption(opt, defaultVal) {
		var val = docent.getThumbSheetOption(sheetMode,opt);  // equivalent to $ml('option[varname='+opt+']', options).attr("value");
		
		// Option recognized and value returned, so return it
		if (val === '') {
			docent.sgconsole('ERROR: paged thumbsheet option not found ' + opt);
			if (typeof defaultVal != 'undefined') {
				return defaultVal;
			}
			else {	
				return val;
			}
		}
		// Option not found or returned with bad val, so use default if we have one
		else {
			return val;
		}
	}
	
	
	function initSheetCSS() {
		if(!cssInitialized) {
			var ruleObj = {};

			ruleObj["#"+sheetDivID+" a"]=[getAnchorNormalStyle() ];
			ruleObj["#"+sheetDivID+" a:hover"]=[getAnchorHoverStyle()];
			ruleObj["#"+sheetDivID+" a.it"]=[getAnchorCurrentPageStyle()];
			
			ruleObj["#"+previewStyleName] = ['z-index:1000;'];  // used for floating preview option
			$ml.cssRule(ruleObj);
			cssInitialized = true;
		}		
	}
	
	function getAnchorNormalStyle() {
	    var s='';
		s+='border: '+ getBorderOptions('normal');
		s+='background:'+ getColorOption('normalbackgroundcolor') +';';
		s+=' display:block; float:left;';
//		s+='margin: '+Math.round(getOption('vspacing')/2)+'px '+Math.round(getOption('hspacing')/2)+'px '+Math.round(getOption('vspacing')/2)+'px '+Math.round(getOption('hspacing')/2)+'px;'
		s+='margin: '+Math.ceil(getOption('vspacing')/2)+'px '+Math.ceil(getOption('hspacing')/2)+'px '+Math.floor(getOption('vspacing')/2)+'px '+Math.floor(getOption('hspacing')/2)+'px;'

		width = getOption('thumbwidth') + 2* getOption('hinset');
		height = getOption('thumbheight') + 2* getOption('vinset');
		s+=' width: '+ width +'px; height: '+ height +'px;';
		docent.sgconsole(s);
		return s;
	}
	function getAnchorHoverStyle() {
	    var s='';
		s+='border: '+ getBorderOptions('hover');
		s+='background:'+ getColorOption('hoverbackgroundcolor') +';';
		docent.sgconsole(s);
		return s;
	}
	
	function getAnchorCurrentPageStyle() {
	    var s='';
		s+='border: '+ getBorderOptions('current');
		s+='background:'+ getColorOption('currentbackgroundcolor') +';';
		s+=' display:block; float:left;';
//		s+='margin: '+Math.round(getOption('vspacing')/2)+'px '+Math.round(getOption('hspacing')/2)+'px '+Math.round(getOption('vspacing')/2)+'px '+Math.round(getOption('hspacing')/2)+'px;'
		s+='margin: '+Math.ceil(getOption('vspacing')/2)+'px '+Math.ceil(getOption('hspacing')/2)+'px '+Math.floor(getOption('vspacing')/2)+'px '+Math.floor(getOption('hspacing')/2)+'px;'
		
		width = getOption('thumbwidth') + 2* getOption('hinset');
		height = getOption('thumbheight') + 2* getOption('vinset');
		s+=' width: '+ width +'px; height: '+ height +'px;';
		docent.sgconsole(s);
		return s;
	}
	 
	function getPXOption(optName) {
		var pxOpt = getOption(optName);
		if (pxOpt==undefined) { docent.sgconsole("no image thumb: px option not found!"); return "10px"; }
		else if (typeof(s)=='string' && pxOpt.substring(pxOpt.length-2)=='px') { return pxOpt; }
		else if (isNaN(pxOpt)) { docent.sgconsole("no image thumb: px option not a number!"); return "10px";}
		else { return pxOpt + 'px'; }
	}
	
    function getBorderOptions(borderType){
		var borderWidth = getOption('borderwidth');
		if (borderWidth == 0) {
			return 'none;';
		}
		else 
			return borderWidth + 'px ' + 'solid ' + getColorOption(borderType + 'bordercolor') + ';';
	}

	
    function getColorOption(optName) {
		var hexColor = getOption(optName);
		if (hexColor != undefined) {
			if (hexColor.substring(0, 1) == '#') {
				return hexColor;
			} // already css style for some reason
			else 
				if (hexColor.substring(0, 2) == '0x') {
					return '#' + hexColor.substring(2);
				}
				else 
					if (!isNaN('0x' + hexColor)) {
						return '#' + hexColor;
					}
		}
		return null;
	}
		
		
	init();
}