/**
 *    Copyright (c) 2009-2010, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

/*global $ml, $, Tween, fc_tb_init */

/*
 * 	Notes:
 * 	1. Nice HTML thumbsheet here: http://plt-scheme.org/screenshots/
 */

var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.gallery1) { com.medialab.sg.gallery1 = {};}


com.medialab.sg.gallery1.scrollPanel =
function (thumbSheetXMLIn, modeIn, docentIn) {
	var debug = false;
	var sgalert = function(msg) { if (debug) {
		alert(msg);
	} };
	var docent= docentIn;

    docent.sgconsole("panel docent: " + docentIn);
	var xml = thumbSheetXMLIn;
    var cssInitialized=false;
	var that=this;
	var thumbWidth = 0;
	var thumbHeight = 0;
	var sheetWidth = 0;
	var sheetHeight = 0;
	var sheetMode = modeIn;
	if(typeof(sheetMode)=='undefined') { sheetMode = 'exhibit'; }
	var panelPadding = 0;
	var panelBorderWidth = 0;
	var loopBack = true;
	var curPos = 0;
	var currentPanel=0;
	var tweenTime=1;
	var panelsContainer = "not set yet";
	var tweenFunc = Tween.regularEaseInOut;
	var tween = "not set yet";  // will hold the actual tween object
	var numAtOnce = "not set yet";  // how many panels are visible at a time
	var numPanels = "not set yet";
	var sheetDivID = "not set yet";
	var panelClassName = "not set yet";
	var thumbDivID = "not set yet";
	var linkClass = "not set yet";
	var changeimageonhover = "false";
	var clickAction = "not set yet";
	var sheetOrientation = "horizontal";
	var backgroundHoverChange = "not set yet";
	var backgroundClickable = "not set yet";
	var backgroundHoverColor = "not set yet";
	var dataUrl = '';
	var dataType ='gallery';
	var useHtmlMetadata = false;
	var panelSetupComplete = false;
	var thumbClassName = 'not set yet';
	var panelHeight = 'not set yet';
	var panelWidth = 'not set yet';
	var playDelay = 3000;
	var autoPlay = true;
	var feedItems = null;
	var player = null;

// BEGIN FUNCTIONS IDENTICAL IN ALL PANELSHEETS

// finds delimited metadata keys in a str and replaces them with the actual metadata for that exhibit number
// shopuld go in docent?
	function replaceMetaData(str, exNum) {
	    if(typeof str != 'string') {return;}
	    var mdStartDelim = "[[";
	    var mdEndDelim = "]]";
	    var mdataStart = str.indexOf(mdStartDelim);
	    var mdataEnd = str.indexOf(mdEndDelim);
	    while (mdataStart > -1 && mdataEnd > mdataStart) {
	    	var mdReplace = str.substring(mdataStart,mdataEnd+2);
	    	var mdKeyword = str.substring(mdataStart+2,mdataEnd);
	    	var mdata = docent.getMetadata(mdKeyword, exNum);
			if (mdata !== "") {
				str = str.replace(mdReplace, mdata);
			}
			else if(mdKeyword=="feeditemlink"){
				str = str.replace(mdReplace, getLinkUrlFromJson(feedItems, exNum));  // I special cased this to allow a literal link button with [[feeditemlink]] as the link text to link to the feed item's link.  Link.
			}
			mdataStart = str.indexOf(mdStartDelim, mdataEnd+1);
			mdataEnd = str.indexOf(mdEndDelim, mdataEnd+1);
	    }
	    return str;
	}


// Finds all <a> elements in the panelsheet and translates them from SG's link table and replaces metadata
	function convertHrefs() {
		var curEntry = -1;
		var panelsElement = $ml('#' + sheetDivID + ' a');
		var numEntriesPerPanel = panelsElement.length/numPanels;
		panelsElement.each(function(n) {
			if(n % numEntriesPerPanel === 0) { curEntry++; }
	        var initHref = $ml(this).attr('href');
			var intermediateHref = docent.translate_link(initHref);
	        var finalHref = replaceMetaData(intermediateHref,curEntry);
	        finalHref = encodeURI(finalHref);
	        $ml(this).attr('href',finalHref);
		});
	}

	function setGalleryMetaData(element, curEntry, metaDataName){
		var metaContents = $ml.trim($ml('exhibits exhibit:eq('+curEntry+') views view:eq(0) metadata[name='+metaDataName+']', xml).text());
		$ml(element).html(metaContents);
	}

	function getPropIgnoreCase(propToFindMixedCase, objectToLookIn) {
		if(typeof propToFindMixedCase != 'string' || typeof objectToLookIn!='object') { return ""; }
		var propToFind = propToFindMixedCase.toLowerCase();
		for (var tryProp in objectToLookIn) {
			var lowProp = tryProp.toLowerCase();
			if(lowProp == propToFind) { return objectToLookIn[tryProp]; }
		}
	}

	function excavateObject(ob,propArr) {
		if(typeof(ob) == 'undefined' || ob===null) { return ""; }
		if (propArr.length == 1) {
			return getPropIgnoreCase(propArr[0], ob);  //ob[propArr[0]]; }  changed this to fix mixed case problem
		}
		var curProp = propArr[0];
		propArr.splice(0,1);
		return excavateObject(ob[curProp],propArr);
	}

	function setFeedMetaData(element, itemNum, metaDataName) {
		docent.sgconsole('setFeedMetaData item ' + itemNum + ' feedItems ' + feedItems[itemNum]);
		var mdItem = feedItems[itemNum];
		var mdContent = "";
		// Check a deeper query.  Use metaDataName = "author.name"; for debugging
		if (metaDataName.indexOf('.') > -1) {
			mdContent=excavateObject(mdItem, metaDataName.split('.'));
		}
		else {
			if (metaDataName == 'link') {
				mdContent = getLinkUrlFromJson(feedItems, itemNum);
			}
			else {
				//mdContent = mdItem[metaDataName];  // replaced this with below to fix metadata label capitalization problem
				mdContent = getPropIgnoreCase(metaDataName, mdItem);
				// handle funky entries that embed text in subobjects with identifiers, like blogspot's ATOM feed
				if(typeof mdContent == 'object' && typeof mdContent != 'string') {
					if(typeof mdContent.content == 'string') {
						mdContent = mdContent.content;
					} else {
						mdContent = "";
					}
				}
			}
		}
		if (typeof(mdContent) != 'undefined' && mdContent !== null) {
			var feedText = null;
			// The data is a link
			if (mdContent.indexOf('http://') === 0) {
				//function isURL(s) { var regexp = /http:\/\/[A-Za....-]{3,}\.[A-Za-z]{3}/; return regexp.test(s); }
				//$ml(element).html('<a href="' + mdContent + '">' + mdContent + '</a>');
				$ml(element).html('<a href="' + mdContent + '">' + 'link' + '</a>');
			}
			// The data is a html (starts with a < )
			else if (mdContent.indexOf('<')===0) {
				if(useHtmlMetadata){
					$ml(element).html(mdContent);
				} else {
					////var fixedContent = mdContent.replace(/&(lt|gt);/g, function (strMatch, p1){ return (p1 == "lt")? "<" : ">"; });
					//$ml(element).text( $ml(mdContent).text() );

					// Clean HTML tags out of text
					feedText = mdContent.replace(/<\/?[^>]+(>|$)/g, "");
					// convert html entities (like &quot;)
					$ml(element).text( cleanHtmlEntities(feedText) );
				}
			}
			// The data is something else
			else {
				//$ml(element).text( $ml(mdContent).text() );

				// Clean HTML tags out of text
				feedText = mdContent.replace(/<\/?[^>]+(>|$)/g, "");
				// convert html entities (like &quot;)
				$ml(element).text( cleanHtmlEntities(feedText) );
			}
		}
	}

	function cleanHtmlEntities(dirtyString) {
	  var ta=document.createElement("textarea");
	  ta.innerHTML=dirtyString.replace(/</g,"&lt;").replace(/>/g,"&gt;");
	  return ta.value;
	}


	function setMetaData(element,num,name) {
		if(dataType!='feed') { setGalleryMetaData(element,num,name); }
		else { setFeedMetaData(element,num,name);}
	}

	function getThumbPath(thumbNum){
		if(dataType=='feed') { return getImageUrlFromJson(feedItems, thumbNum); }
		else {
			if(sheetMode == 'exhibit') {
				return $ml.trim($ml('exhibits exhibit:eq('+ thumbNum +') view:eq(0) thumbnail url',xml).text());
			}
			else {
				return $ml.trim($ml('exhibits exhibit:eq('+ docent.getCurrentExhibit() +') view:eq('+ thumbNum +') thumbnail url',xml).text());
			}
		}

	}

	function setUpPanelsData() {
		// hrefs
		convertHrefs();
		if (typeof fc_tb_init == 'function') {  // pre version .7 of foxycart needs this for dynamically added elements
			fc_tb_init('a.foxycart');
		}

		// content metadata
		var searchString = 'div[class="' + panelClassName + '"]:eq(0) span[class*="'+ docent.getName() +'"]';
		var numEntriesPerPanel = $ml(searchString).length;
		var curEntry = -1;
		//$ml('div[class="'+ panelClassName + '"] span[class*="'+ docent.getName() +'"]').each(function (n) {	// same as below?
		$ml('#' + sheetDivID +  ' span[class*="'+ docent.getName() +'"]').each(function (n) {
			if(n % numEntriesPerPanel === 0) { curEntry++; }
			var fullClassName = $ml(this).attr('class');
			var metaDataName = fullClassName.split(' ')[1];
			setMetaData(this, curEntry, metaDataName);
		});


		// thumb images & links
		if(typeof thumbClassName =='string' && thumbClassName !== '' && thumbWidth>0 && thumbHeight>0) {
			searchString = 'div[class="'+ panelClassName + '"] div[class*="'+ thumbClassName +'"]';
			docent.sgconsole(searchString);
			$ml(searchString).each(function(n) {
				var link = null;
				var tHtml = "";
				var thumbPath = '#';
				var tPath = getThumbPath(n);
				var styleString = 'position:inherit; width:'+ thumbWidth +'px; height:'+ thumbHeight +'px; background-image:url('+ docent.url2CssUrl(tPath) + '); background-position: 50% 50%; background-repeat: no-repeat;';
				switch (clickAction) {
					case 'picturebox':
					case 'bigbox':
					case 'picturebox':
						tHtml += '<a href="#"';
						break;
					case 'url':
						link = $ml.trim($ml('exhibits exhibit:eq('+ n +') view:eq(0) link',xml).text());
						tHtml += '<a href="' + link + '" ';
						break;
					case 'feedlink':
						link = "#";
						if (dataType == 'feed') {
							link = getLinkUrlFromJson(feedItems, n);
						}
						tHtml += '<a href="' + link + '" ';
						break;
					default:
						$ml(this).attr('style',styleString);
						break;
				}
				if(tHtml!== '') {
					tHtml += '><div style="'+styleString+'"></div></a>';

					$ml(this).append(tHtml);
				}
			});
		}
	}

	function initPanelSheet() {
		// generate generic HTML for all panel sheets
		var containerText = getPanelSheetContainerStartHTML();
		containerText += getEmptyPanelsHTML();
		containerText += getPanelSheetContainerEndHTML();
		containerText += '<div style="clear:left"></div>';

		// add generic HTML to page html
		$ml("#"+sheetDivID).html(containerText);

		setUpPanelsData();
		setUpPanelsEvents();

		// now finish
		initCustomPanelSheetStuff();
		that.panelSetupComplete();
	}


	function getEmptyPanelsHTML() {
		var panelsHTML = "";
		//var onePanelHTML = $ml('panels > panel > div', xml).toXML();  // pre kludge
		var onePanelHTML = $ml('panels > panel > div:eq(0)', xml).toXML();  // kludge to avoid <divider> that shouldn't be there
		for(var curPanelCounter = 0; curPanelCounter < numPanels; curPanelCounter++) {
			panelsHTML += onePanelHTML;
		}
		return panelsHTML;
	}

	function getOption(opt) {
		var w = docent.getPanelOption(sheetMode,opt);
		//var w = $ml('option[varname='+opt+']', options).attr("value");
		return w;
	}

	function getFirstPropertyFromObject(ob) {
		for(var i in ob) {
			return ob[i]; }
	}



	function getImageUrlFromJson(dataArr,itemNum) {
		var img="";
		if(dataArr.length > itemNum) {
			var item = dataArr[itemNum];
			if (typeof(item.content) != 'undefined') {
				if(typeof item.thumbnail == 'object' && typeof item.thumbnail.url == "string") {  // check for an existing thumbnail entry as in blogspot ATOM
					return item.thumbnail.url;
				}
				else if (typeof item.content.url != "undefined") { // assume image in content poperty 1st as in http://rss.news.yahoo.com/rss/topstories
					return item.content.url;
				} else if(typeof item.content.content != 'undefined') {
					img=getImageFromContent(item.content);  // try finding it in description HTML
					if(img!=="") { return img; }
				}
			}
			img=getImageFromDescription(item);  // try finding it in description HTML
			if(img!=="") { return img; }
			img = getImageFromLink(item);  // Flickr-style, as in http://api.flickr.com/services/feeds/photoset.gne?set=72157623884410853&nsid=25976997@N03&lang=en-us
			if(img!=="") { return img; }
		}
		return img;
	}

	function getImageFromLink(item) {
		var img = "";
		if(typeof (item.link) != "undefined") {
			var linkObj = findObjectInArrayWith('type','image/jpeg',item.link);
			if(typeof linkObj == 'object' && linkObj!==null) {
				if(typeof linkObj.href == "string") {
					return linkObj.href;
				}
			}
		}
		return "";
	}

	function findObjectInArrayWith(propName,propVal,objArr) {
		if(typeof objArr == 'object' && objArr!==null) {
			for(var i=0; i < objArr.length; i++) {
				var testObj = objArr[i];
				if (typeof testObj == 'object' && testObj!==null) {
					if(typeof testObj[propName] != 'undefined') {
						if (testObj[propName] == propVal) {
							return testObj;
						}
					}
				}
			}
		}
		return null;
	}

	function getImageFromContent(item) {
		var img = "";
		if(typeof (item.content) != "undefined") {
			var h = "<div>" + item.content + "</div>";
			var imgArr = $ml('img',h);
			if (typeof imgArr != "undefined" && typeof imgArr[0] != "undefined" && typeof imgArr[0].src != "undefined"){
				img = imgArr[0].src;
			}
		}
		return img;
	}

	function getImageFromDescription(item) {
		var img = "";
		if(typeof (item.description) != "undefined") {
			var h = "<div>" + item.description + "</div>";
			var imgArr = $ml('img',h);
			if (typeof imgArr != "undefined" && typeof imgArr[0] != "undefined" && typeof imgArr[0].src != "undefined"){
				img = imgArr[0].src;
			}
		}
		return img;
	}

	function getLinkUrlFromJson(dataArr, itemNum){
		if(dataArr.length > itemNum) {
			var item = dataArr[itemNum];
			var vType = typeof(item.link);
			switch(vType) {
				case 'string':
					return item.link;
				case 'object':
					var it = item.link;
					if(typeof it=='object' && typeof it.href == 'string') { return it.href; }
					else{
						if (typeof it == 'object') {
							var linkObj = findObjectInArrayWith('type','text/html',it);
							if(typeof linkObj.href == 'string' && linkObj.href.substring(0,4) == 'http') {
								return linkObj.href;
							}
						}
					}
					return it.href;
				//case 'undefined':
				default:
					return "";
				//return item.content.url;
			}
		}
	}

	this.select = function (thumbnum) {
		// called whenever a new image is selected to sync
		// could be called becuase a thumb (including this one) is clicked
		// or for any other reason the docent wants, like next/prev buttons.

		// don't do anything if already synced

		// If not synced then hilite apropriate thumb if necessary and dehilite others if necessary
		docent.sgconsole("thubsheet select called");
		initSheet();
	};

	function jqClick(e) {
		var thumbNum = e.data.thumbNum;
		//alert('thumb ' + thumbNum);
		docent.sgconsole('css '+ sheetMode +' thumb '+ thumbNum +' clicked');
		switch (clickAction) {
			case 'picturebox':
				docent.selectThumb(thumbNum,sheetMode,true);
				break;
			case 'bigbox':
				docent.selectThumb(thumbNum,sheetMode,true);
				docent.openBigbox(sheetMode);
				break;
			case 'url' :
				docent.gotoCustomUrl(thumbNum);
				break;
			case 'feedlink' :
				docent.gotoUrl(e.data.feedLink);
				break;
			default:
				docent.sgconsole('css thumbs passed unknwn click action '+ clickAction);
				break;
		}
		return false;
	}

	function setUpPanelsEvents() {
		var numPanelsAdded = $ml('#'+sheetDivID + ' [class="' + panelClassName + '"]').length;
		// 	Set up click events

		for (var curPanel = 0; curPanel < numPanelsAdded; curPanel++) {
			var eventInfo = {};
			eventInfo.thumbNum=curPanel;
			if (dataType =='feed') {
				eventInfo.feedLink = getLinkUrlFromJson(feedItems, curPanel);
			}
			if (clickAction && clickAction != 'none') {
                if (backgroundClickable || thumbClassName=='') { // if there's no thumb then ignore the click setting and use the background
                    $ml('div[class="' + panelClassName + '"]:eq('+ curPanel +')').bind('click', eventInfo, jqClick);
				}
				else { // only the thumb is clickable
					$ml('div[class="' + thumbClassName + '"] a:eq('+ curPanel + ')').bind('click', eventInfo, jqClick);
				}
			}
		}

		// 	Set up style events

		if (clickAction && clickAction != 'none') {
			if (backgroundClickable) {
				$ml('div[class="' + panelClassName + '"]').css('cursor', 'pointer');
				if(backgroundHoverChange) {
				$ml('div[class="' + panelClassName + '"]').hover(
					function(e) { $ml(this).css('background-color',backgroundHoverColor);},
					function(e) { $ml(this).css('background-color',''); }); // or do we need to capture initial color?
				}
			}
			else { // only the thumb is clickable
				$ml('div[class="' + thumbClassName + '"] a').css('cursor', 'pointer');
			}
		}
	}

// END FUNCTIONS IDENTICAL IN ALL PANELSHEETS
// ********************************************
// BEGIN FUNCTIONS IMPLEMENTED IN ALL PANELSHEETS BUT THAT ARE NOT THE SAME

	// a good test url for json is http://rss.news.yahoo.com/rss/topstories
	this.gotJSON = function(jsonData) {
		if (typeof jsonData == 'object' && typeof jsonData.query == 'object' && typeof jsonData.query.results == 'object') {
			feedItems = getFirstPropertyFromObject(jsonData.query.results);
			if (typeof(feedItems) == 'undefined' || feedItems === null) {
				$ml("#" + sheetDivID).html('<p>No data found at feed:</p><p>' + dataUrl + '</p>');
				return;
			}
			numPanels = feedItems.length;
			docent.sgconsole('numPanels from feed: ' + numPanels);
			initPanelSheet();
		}
		else {
			$ml("#" + sheetDivID).html('<p>Problem with feed data:</p><p>' + dataUrl + '</p>');
		}
	};

	function getPanelSheetContainerEndHTML() {
		return '</div></div>';
	}

	function initCustomPanelSheetStuff() {  // will be custom depending on panel type
		$ml('div[class="' + panelClassName + '"]').css('overflow','hidden');  // necessary?
		  // set up tween
		var axis = sheetOrientation=='horizontal' ? 'left' : 'top';
		panelsContainer = $ml("#"+sheetDivID + " > div > div")[0];
		tween = new Tween(panelsContainer.style,axis,tweenFunc,0,0,1,'px');
		  // set up margins
		var marg = sheetOrientation == 'horizontal' ? 'margin-left' : 'margin-top';
		panelPadding = parseInt($ml('div[class="' + panelClassName + '"]').css(marg),10);
		if (isNaN(panelPadding)) { panelPadding = 0; }
	}

	function getPanelSheetContainerStartHTML() { // will be custom depending on panel type
		// Basic scroll panel
		var containerText = '<div style="width:' + sheetWidth + 'px;height:' + sheetHeight + 'px;overflow:hidden;position:relative;">'; // position relative fixes IE bug with overlfow hidden
		var extent = that.getPanelsExtent();  // axis size based on h/v orientation of sheet
		if(sheetOrientation=='horizontal') {
			containerText += '<div style="height:'+panelHeight+'px; width:'+extent+'px; left:0; top:0;position:relative;">';
		} else {
			containerText += '<div style="height:'+extent+'px; width:'+panelWidth+'px; left:0; top:0;position:relative;">';
		}
		return containerText;
	}

	 function processXML () {
		sgalert("SG Standard Thumbsheet: processXML", this);

		dataUrl = getOption('dataurl');
		dataType = getOption('datatype');
		//panelBorderWidth = getOption('panelborderwidth');
		clickAction = getOption('clickaction');
		//panelPadding = getOption("spacing");
		//if(typeof(panelPadding)!='number') { panelPadding = getOption("hspacing"); }
		sheetWidth = getOption("sheetwidth");
		sheetHeight = getOption("sheetheight");
		panelWidth = getOption("panelwidth");
		panelHeight = getOption("panelheight");
		thumbWidth = getOption("thumbwidth");
		thumbHeight = getOption("thumbheight");
		thumbClassName = getOption("panelthumbclass");
		sheetDivID = getOption("sheetdivname");
		thumbDivID = getOption("thumbdivname");
		useHtmlMetadata = getOption('usehtmlmetadata');
		changeimageonhover = getOption("changeimageonhover");
		loopBack =  getOption("loopback");
		backgroundHoverChange = getOption("backgroundohoverchange");
		backgroundClickable = getOption("backgroundclickable");
		backgroundHoverColor = docent.toCSSColor(getOption("backgroundhovercolor"));
		tweenTime = getOption('tweenduration');
		playDelay = getOption('playdelay') * 1000 + tweenTime*1000;
		autoPlay = getOption('autoplay');

		var tweenFuncName = getOption("tweenfunction");
		if(typeof(Tween[tweenFuncName]) == 'function') { tweenFunc = Tween[tweenFuncName]; }

		sheetOrientation = getOption('sheetorientation');
		if(sheetOrientation=='auto' || sheetOrientation === '') {
			sheetOrientation = (sheetWidth-panelWidth >= sheetHeight-panelHeight) ? 'horizontal' : 'vertical';
		}

		if(sheetMode=='exhibit') { numPanels = docent.getExhibitCount(); }
		else { numPanels = docent.getNumViews();}
		docent.sgconsole('numpanels: ' + numPanels);
		panelClassName = $ml.trim($ml('panels > panel > div',xml).attr('class'));
		panelClassName = panelClassName.replace('  ',' ');

		var size = sheetOrientation == 'horizontal' ? sheetWidth : sheetHeight;
		numAtOnce = Math.floor(size/that.getPanelExtent());
		docent.sgconsole('panel showing ' + numAtOnce + ' at once out of ' + numPanels + ' total');
		if (numAtOnce === 0) {
			numAtOnce = 1;
		}
		return;
	}

	this.panelSetupComplete = function() {
		if(autoPlay) { startPlay(); }
	};

	function init() {
		docent.sgconsole("SG Basic Scroll Panelsheet: Init", this);
		if(getOption("type")!="scrollPanel") { docent.sgconsole("Type mismatch between thumbsheet javascript(scrollPanel) and data ("+getOption("type")+")"); return;}
		if(getOption("version") > 1.0) { docent.sgconsole("Panel javascript version older than data version!"); return;}
		processXML();
		if(dataType=='feed') {
			docent.getJSON(dataUrl, that.gotJSON);
		} else {
			initPanelSheet();
		}
	}



	this.updateSheet = function() {
		initSheet();
	};

// END FUNCTIONS IMPLEMENTED IN ALL PANELSHEETS BUT THAT ARE NOT THE SAME
// ********************************************
// BEGIN FUNCTIONS TOTALLY UNIQUE TO THIS PANELSHEET
	this.getPanelsExtent = function() {  // get the total width/height of all panels with borders and spacing
		var extent = 0;
		extent += panelPadding * 2 * (numPanels-1);
		extent += panelBorderWidth * 2 * (numPanels-1);
		var size = sheetOrientation=="horizontal" ? panelWidth : panelHeight;
		extent += size * numPanels;
		return extent;
	};

	this.getPanelExtent = function() { // get the total width/height of a single panel with borders and spacing
		var extent = 0;
		extent += panelPadding;
		extent += panelBorderWidth * 2;
		var size = sheetOrientation=="horizontal" ? panelWidth : panelHeight;
		docent.sgconsole(panelPadding + " - " + panelBorderWidth + " - " + size);
		extent += size;
		docent.sgconsole('panelsheet getPanelExtent returning ' + extent);
		return extent;
	};

	function startPlay() {
		player = setInterval(playNext,playDelay);
	}

	function stopPlay() {
		if (player) {
			player.stop();
		}
	}

	function playNext() {
		that.nextPanelSet();
	}

	function resetTweenTime() {
		if(player) {
			clearInterval(player);
			startPlay();
		}
	}

	this.goToPanel = function (destPanel) {
		var targetLoc = -destPanel * this.getPanelExtent();
		currentPanel = destPanel;
		if(window.Tween) {
			if(autoPlay) { resetTweenTime(); }
			tween.continueTo(targetLoc, tweenTime);
		}
		else {
			docent.sgconsole("JSTween not found!");
		}
	};

	this.nextPanel = function() {
		var nextPanel = currentPanel;
		if (currentPanel >= numPanels - numAtOnce) {
			if (loopBack) {
				nextPanel = 0;
			}
		}
		else {
			nextPanel = currentPanel + 1;
		}
		if(nextPanel != currentPanel) { this.goToPanel(nextPanel); }
		return;
	};

	this.previousPanel = this.prevPanel = function() {
		var saveNum = numAtOnce;
		if(currentPanel!==0) { numAtOnce = 1; }  // if at beginning and looping we need to preserve numAtOnce so the loop works
		this.previousPanelSet();
		numAtOnce=saveNum;
	};

	this.nextPanelSet = function() {
		var nextPanel = currentPanel;
		var numToSlide = numAtOnce;
		docent.sgconsole('nextPanelSet currentPanel >= numPanels - numAtOnce ' + numPanels + ' ' + numAtOnce + ' ' + currentPanel);
		if(currentPanel >= numPanels - numAtOnce) {
			if (loopBack) {
				nextPanel = 0;
			}
		}
		else {
			var numLeft = numPanels-(currentPanel + (numAtOnce));
			if (numLeft < numAtOnce) { numToSlide = numLeft; }
			nextPanel = currentPanel + numToSlide;
		}

		if(nextPanel != currentPanel) { this.goToPanel(nextPanel); }
		return;
	};


	this.previousPanelSet = this.prevPanelSet = function() {
		var nextPanel = currentPanel;
		var numToSlide = numAtOnce;
		if(currentPanel === 0) {
			if (loopBack) {
				nextPanel = numPanels - numAtOnce;
			}
		}
		else {
			var numLeft = numPanels-(currentPanel + (numAtOnce));
			if(currentPanel < numAtOnce) { numToSlide = currentPanel; }
			nextPanel = currentPanel - numToSlide;
		}
		if(nextPanel != currentPanel) { this.goToPanel(nextPanel); }
	};



// END FUNCTIONS TOTALLY UNIQUE TO THIS PANELSHEET

	init();
};
