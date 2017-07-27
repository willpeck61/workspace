/**
 *    Copyright (c) 2009-2010, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

/*global $ml, $, fc_CookieFix, Image, XMLSerializer, console */

/*
 *
 *  Initialization process:
 *  	Init all docent stuff
 *  	As necessry, based on xml:
 *  		New picturebox
 *  		New exhibit sheet
 *  		New view sheet
 *  		New panel sheet
 *  		New bigbox
 *
 *  	Each picbox and sheet will initially select the current
 *  	view/exb as passed in.  That way only the jscript for that
 *  	component needs to know about its possibly asynchronous
 *  	initialization requirements.
 *
 */
var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.gallery1) { com.medialab.sg.gallery1 = {};}


com.medialab.sg.gallery1.docent =
function (xmlPathIn, myNameIn) {
	if (typeof($ml)=='undefined') { $ml = $; }
	var that=this;
	var myName = myNameIn ? myNameIn : "docent";
	var curExhibit = 0;
	var prevExhibit = -1;
	var exhibitChange = true;
	var curView = 0;
	var prevView = -1;
	var exhibitCount = 0;
	var thumbsheetCount = 0;
	var galleryXMLpath = xmlPathIn;
	var thumbsheet = null;
	var viewsheet = null;
	var viewsheetOptions = null;
	var exhibitsheetOptions = null;
	var pictureboxOptions = null;
	var bigboxOptions = null;
	var panelOptions = null;
	var bigbox = null;
	var picturebox = null;
	var panel = null;
	var xml = null;
	var thumbsheetDirty = false;
	var viewsheetDirty = false;
	var pictureboxDirty = false;
	var urlViewParamName = "view";
	var urlExhibitParamName = "exb";
	var preload = false;  // preload or not
	var preloads = [];  // array of images to preload
	var preloadMax = 50;
    var sheetDivName = "notsetyet";

    var metaHrefs = null;
	var metaChunks = null;
	var metaDelimOpen = "[[";
	var metaDelimClose = "]]";
	var formMetaData = null;

	var useConsole = false;
	var debug = false;

 	this.sgalert = function(msg) { if (debug) {	alert(msg);	} };
	function sgalert(msg) { that.sgalert(msg); 	}

	this.getCurrentExhibit = function () { return curExhibit; };
	this.getCurrentView = function () { return curView; };

	this.getExhibitCount = function () { return exhibitCount; };

	this.getName = function () { return myName; };

	this.getxml = function() {
		return xml;
	};



	this.selectThumb = function (thumbnum, mode, updateThumbsheet) {
		//alert('hi');
		//this.sgalert("Selected thumb (v1)" + thumbnum);
		this.sgconsole("selectthumb. mode: " + mode + " thumbnum: " + thumbnum + ' curexhibit ' + curExhibit);
		if (mode == "exhibit") {
			if (thumbnum != curExhibit) {
				exhibitChange = true;
				prevExhibit = curExhibit;
				curExhibit = thumbnum;
				curView=0;
				prevView=0;
				thumbsheetDirty = updateThumbsheet;
				viewsheetDirty = true;
				pictureboxDirty=true;
				this.sgconsole("About to sync");
				this.sync();
			} else {
				if(panel && panel.goToPanel) {
					panel.goToPanel(thumbnum);
				}
			}
		}
		else {
			if (thumbnum != curView) {
				exhibitChange = false;
				viewsheetDirty=updateThumbsheet;
				pictureboxDirty=true;
				prevView = curView;
				curView = thumbnum;
				this.sync();
			}
		}
//		if(this.getThumbSheetOption(mode,'selectionaction')=='bigbox') {
//			this.openBigbox();
//		}
		return false;
	};

//	this.sgalert = function(msg) {	alert("sgalert public"); alert(msg);  	}
	function checkEnvironment () {
		sgalert("DOCENT: checkEnvironment()");
		// check for any required libs, like jquery
		if (!$ml) { return false; }
		return true;
	}


	this.init = function() {
		/*var urlExbNum = this.getUrlParam(urlExhibitParamName);  // this was the old way of allowing ehxhibit number in the url
		var urlViewNum = this.getUrlParam(urlViewParamName);	  // it didn't allow for targeting a specific gallery
		if(urlExbNum) {											 //  see getExhibitFromURL() below for the new way.
			curExhibit = urlExbNum;
			if(urlViewNum) {
				curView = urlViewNum;
			}
		}*/

		if(!checkEnvironment()) {
			alert("SiteGrinder gallery missing components!");
			return;
		}

		if (!loadGalleryXML()) {
			alert("SiteGrinder gallery can't find XML file!");
			return;
		}
	};

	function sync() { that.sgconsole('wrong sync called'); that.sync();}

	function getExhibitFromURL() {
		var queryExhibit = getUrlParameter(myName+'_exhibit');  // this will be, for example, "weddings_d_exhibit=8", the value being zero-based.
		if(queryExhibit !== "" && !isNaN(queryExhibit) && Number(queryExhibit) < exhibitCount) {
			return Number(queryExhibit);
		} else {
			return 0;
		}
	}

	function getUrlParameter(name){
		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regexS = "[\\?&]" + name + "=([^&#]*)";
		var regex = new RegExp(regexS);
		var results = regex.exec(window.location.href);
		if (results === null) {
			return "";
		}
		else {
			return results[1];
		}
	}


	this.getThumbsheetOption = function (sheetType,optionIn) { // these are dopplegangered just to avoid constant camelcaps typo bugs
		return this.getThumbSheetOption(sheetType,optionIn);
	};

	this.getThumbSheetOption = function (sheetType,optionIn) {
		var options = null;
		if(sheetType == "view") { options = viewsheetOptions; }
		else { options = exhibitsheetOptions; }
		return this.getAnOption(options,optionIn);
	};

	this.getPictureboxOption = function (optionIn) {
		return this.getPictureBoxOption(optionIn);
	};

	this.getPictureBoxOption = function (optionIn) {
		return this.getAnOption(pictureboxOptions,optionIn);
	};

	this.getBigboxOption = function (optionIn) {
		return this.getBigBoxOption(optionIn);
	};

	this.getPanelOption = function (modeIn, optionIn) {
		return this.getAnOption(panelOptions,optionIn);
	};

	this.getBigboxOption = function (optionIn) {
		return this.getBigBoxOption(optionIn);
	};
	this.getBigBoxOption = function (optionIn) {
		return this.getAnOption(bigboxOptions,optionIn);
	};

	this.getAnOption = function (options,option) {
		var type = $ml('option[varname='+option+']', options).attr("type");
		var valMix = $ml('option[varname='+option+']', options).attr("value");
		if (typeof valMix == 'undefined') {
			this.sgconsole('Docent asked for a non existent option: ' + option);
			return "";
		}
	    var valLower = valMix.toLowerCase();
		if (type == 'undefined') {
			this.sgconsole('option type not found in options for option ' + option);
			if (isNaN(valLower)) {
				if (valLower == "true") {
					return true;
				}
				else
					if (valLower == "false") {
						return false;
					}
					else {
						return valMix;
					}
			}
			else {
				if (valLower.substring(0, 2) == '0x') {
					return valLower;
				} // color
				else {
					return parseFloat(valMix);
				}
			}
		}
		else {
			var typeLower =type.toLowerCase();
			switch (typeLower) {
				case 'boolean' :
					return (valLower == 'true');
				case 'number' :
					return parseFloat(valLower);
				case 'color':
					return valMix;
				default:
				    return valMix;
			}
		}
	};

	this.getCSSFormattedColor = function(col)  {
		if(col.substring(0,1) == '#') { return col; }
		else if (col.substring(0,2) == '0x') { return '#' + col.substring(2,col.length); }
		else { return '#' + col; }
	};

	this.getFlashMovie = function(movieName) {
	  // IE and Netscape refer to the movie object differently.
	  // This function returns the appropriate syntax depending on the browser.
	  this.sgconsole("finding movie " + movieName);
	  if (navigator.appName.indexOf ("Microsoft") !=-1) {
	    return window[movieName];
	  } else {
	    return document[movieName];
	  }
	};

	// thanks to FF windows when the movie isn't visible on screen this routine now has to check for initWithXML...
	// this means all docent-checked flash components must export an initWithXML() with that exact name
	this.flashMovieIsLoaded = function(theMovie) {
	  // First make sure the movie's defined.
	  if (typeof(theMovie) != "undefined" && theMovie!==null) {
	  	this.sgconsole("movie " + theMovie);
	    // If it is, check how much of it is loaded.
		// incredibly IE returns 'unknown' as the type of PercentLoaded() but 'function' for initWithXML()
	    if (typeof theMovie.initWithXML != 'undefined') {
			try {
				if (typeof theMovie.PercentLoaded != 'undefined' && typeof theMovie.PercentLoaded != 'unknown') { // just checking for PercentLoaded causes IE to error with Flash 10.1
					return theMovie.PercentLoaded() == 100; // check percentloaded just in case if the func is available
				}
			}
			catch(err) {
			}
			return true; // assume loaded since initWithXML is there
		}
		else {
			return false;
		}
	  } else {
	  	  this.sgconsole("movie undefined");
	    // If the movie isn't defined, it's not loaded.
	    return false;
	  }
	};


	this.processXML = function (fullGalleryXMLIn){
		// **  Called when ajax has successfully loaded our XML  **
		that.sgconsole("about to process xml");
		xml=$ml(fullGalleryXMLIn);
		that.sgconsole("Docent: processXML (xml loaded)");
		that.postXMLInit();
	};

	//
	// setUpPreloads()
	//
	function preloadNext(){
		if(preloads.length > 0) {
			var curSrc = $ml.trim($ml(preloads.shift()).text());
			that.sgconsole('PRELOADING next picturebox image '+ curSrc +'('+ preloads.length +' left)');
			var img = new Image();
			img.onerror = preloadErr;
			img.onload = preloadNext;
			img.src = curSrc;
		}
	}

	function setUpPreloads(){
		if (preload) {
			var qString = "";
			var result = null;
			if (picturebox) { // current picturebox
				qString = 'exhibits exhibit:eq(' + curExhibit + ') view:eq('+curView+') picturebox url';
				result = $ml(qString, xml).get();
				if (result) { preloads = preloads.concat(result);	}
			}
			if (thumbsheet) { // current thumbnail
				qString = 'exhibits exhibit:eq(' + curExhibit + ') view thumbnail url';
				result = $ml(qString, xml).get();
				if (result) { preloads = preloads.concat(result);	}
			}
			if (viewsheet) { // add current view thumbnails first
				qString = 'exhibits exhibit:eq(' + curExhibit + ') view viewthumb url';
				result = $ml(qString, xml).get();
				if (result) { preloads = preloads.concat(result);	}
			}
			if(thumbsheet){  // now load all the exhibit thumbs
				for (var curEx = 0; curEx < exhibitCount; curEx++) {
					qString = 'exhibits exhibit:eq('+ curEx +') view:eq(0) thumbnail url';
					result = $ml(qString, xml).get();
					if (result) { preloads = preloads.concat(result);	}
				}
			}
			for (curEx = 0; curEx < exhibitCount; curEx++) {
				if (curEx != curExhibit) {  // now add the main pictureboxes and viewthumbs in order from zero
					if (picturebox) {
						qString = 'exhibits exhibit:eq('+curEx+') view:eq(0) picturebox url';
						result = $ml(qString, xml).get();
						if (result) { preloads = preloads.concat(result);	}
					}
					if(viewsheet) {
						qString = 'exhibits exhibit:eq('+curEx+') view viewthumb url';
						result = $ml(qString, xml).get();
						if (result) { preloads = preloads.concat(result);	}
					}
				}
			}
			that.sgconsole('preload list has ' + preloads.length + 'items');
			if(preloads.length > preloadMax) { preloads.length = preloadMax; }
			preloadNext();
		}
	}


	function preloadErr() {
		this.sgconsole('PRELOADING err ' + this.src);
		preloadNext();
	}



	this.postXMLInit = function() {
		// **  Called to init whatever is necessary after XML loaded
		// the options are stored as an optimization so the whole tree isn't parsed each time an option is looked for
		this.sgconsole("Docent: postXMLInit");

		exhibitCount = $ml('exhibit',xml).length;
		curExhibit = getExhibitFromURL();

		this.sgconsole("binding preloader");
		//$ml(window).bind('load', pictureboxPreload);

		var thumbsheetCount = $ml('thumbsheet',xml).length;
		$ml('options thumbsheet',xml).each(function(n){
			//console.log("docent making thumbsheet");
			var sheetConstructor = $ml('option[varname="constructor"]',this).attr("value");
			if(!sheetConstructor) { sgalert('No thumbsheet contructor provided in xml!'); }
			if (!com.medialab.sg.gallery1[sheetConstructor]) {sgalert('Thumbsheet constructor "' + sheetConstructor + '" not available!');}
			var sheetmode = $ml('option[varname="mode"]',this).attr("value");
			that.sgconsole("Found a "+ sheetmode +" sheet with constructor " + sheetConstructor);
			if(sheetmode == 'exhibit') {
				exhibitsheetOptions = this;
				thumbsheet = new com.medialab.sg.gallery1[sheetConstructor](xml,sheetmode,that);
				//thumbsheet = new window[sheetConstructor](xml,sheetmode,that);
			}
			else {
				viewsheetOptions = this;
				viewsheet = new com.medialab.sg.gallery1[sheetConstructor](xml,sheetmode,that);
	//				viewsheet = new window[sheetConstructor](xml,sheetmode,that);
			}
		});
		var pictureboxCount = $ml('options picturebox',xml).length;
		if (pictureboxCount > 0) {
			var pboxConstructor = $ml('options picturebox option[varname="constructor"]',xml).attr("value");
			that.sgconsole("Found a picturebox with constructor " + pboxConstructor);
			if(!pboxConstructor) { sgalert('No picturebox contructor provided in xml!'); }
			if (!window[pboxConstructor]) {sgalert('Picturebox constructor "' + pboxConstructor + '" not available!');}
			pictureboxOptions = $ml('options picturebox',xml);
			picturebox = new com.medialab.sg.gallery1[pboxConstructor](xml, this);
		}



		var bigboxCount = $ml('options bigbox',xml).length;
		if (bigboxCount > 0) {
			var bboxConstructor = $ml('options bigbox option[varname="constructor"]',xml).attr("value");
			that.sgconsole("Found a bigbox with constructor " + bboxConstructor);
			if(!bboxConstructor) { sgalert('No bigbox contructor provided in xml!'); }
			if (!window[pboxConstructor]) {sgalert('Bigbox constructor "' + bboxConstructor + '" not available!');}
			bigboxOptions = $ml('options bigbox',xml);
			this.sgconsole("Found a BIGBOX: " + bboxConstructor);
			bigbox = new com.medialab.sg.gallery1[bboxConstructor](xml, this);
		}

		var panelCount = $ml('options panel',xml).length;
		if (panelCount > 0) {
			var panelMode = $ml('options panel option[varname="mode"]',this).attr("value");
			if(typeof(panelMode) == 'undefined') { panelMode = 'exhibit'; }
            sheetDivName = $ml('options panel option[varname="sheetdivname"]',xml).attr("value");
			var panelConstructor = $ml('options panel option[varname="constructor"]',xml).attr("value");
			that.sgconsole("Found a panel with constructor " + panelConstructor);
			if(!panelConstructor) { sgalert('No panel contructor provided in xml!'); }
			if (!window[panelConstructor]) {sgalert('panel constructor "' + panelConstructor + '" not available!');}
			panelOptions = $ml('options panel',xml);
			this.sgconsole("Found a panel!!");
			panel = new com.medialab.sg.gallery1[panelConstructor](xml, panelMode, this);
		}
        this.sgconsole("About to update metadata");
        this.updateMetadata();


        setUpPreloads();


		//this.sync();
	};

	this.openBigbox = function(mode) {
		if(!mode) {
			if(!viewsheet || curView === 0)  {mode='exhibit'; }
			else { mode='view';}
		}
		this.sgconsole('js docent opening '+ mode +' mode bigbox with exhibit ' + curExhibit + ', view ' + curView);
		bigbox.openByView(mode, curExhibit, curView);
	};

	this.getLargestImageUrl = function() {
		var foundUrl = $ml('exhibits exhibit:eq(' + curExhibit + ') views view:eq(' + curView + ') bigbox url', xml).text();
		if (foundUrl) {
			return foundUrl;
		}
		else {
			foundUrl = $ml('exhibits exhibit:eq(' + curExhibit + ') views view:eq(' + curView + ') picturebox url', xml).text(); // not found so check picturebox
			if (foundUrl) {
				return foundUrl;
			}
			else {
				foundUrl = $ml('exhibits exhibit:eq(' + curExhibit + ') views view:eq(' + curView + ') thumbnail url', xml).text(); // not found so check thumbnail
				if (foundUrl) {
					return foundUrl;
				}
			}
		}
		return "";
	};

	this.printCurrentExhibit = function() {
		var imgUrl = this.getLargestImageUrl();
		if (typeof imgUrl == 'string' && imgUrl.length > 0) {
			var printPageContents = "<html>\n" +
			"<head>\n" +
			"<title>Temporary Printing Window</title>\n" +
			"<script>\n" +
			"function step1() {\n" +
			"  setTimeout('step2()', 10);\n" +
			"}\n" +
			"function step2() {\n" +
			"  window.print();\n" +
			"  window.close();\n" +
			"}\n" +
			"</scr" +
			"ipt>\n" +
			"</head>\n" +
			"<body onLoad='step1()'>\n" +
			"<img src='" +
			imgUrl +
			"'/>\n" +
			"</body>\n" +
			"</html>\n";

			var link = "about:blank";
			var pw = window.open(link, "_new");
			pw.document.open();
			pw.document.write(printPageContents);
			pw.document.close();
		}
	};

	// gets image url of picturebox or, if no pbox, bigbox
	this.num2url = function(exhibitNum, viewNum){
		var foundUrl = $ml('exhibits exhibit:eq(' + exhibitNum + ') views view:eq(' + viewNum + ') picturebox url', xml).text(); // check picturebox first
		if (foundUrl) {
			return foundUrl;
		}
		else {
			return $ml('exhibits exhibit:eq(' + exhibitNum + ') views view:eq(' + viewNum + ') bigbox url', xml).text(); // not found so check bigbox
		}
	};

	this.getSpecialMetadata = function(mdName) {
		switch (mdName) {
			case "c":
				return (curExhibit+1)+"";  // adjust from zero-based to one-based for silly humans
			case "t":
				return exhibitCount+"";
			default:
				return null;
		}
	};

	this.updateMetadata = function () {
		this.sgconsole('NOW IN updateMetadata - ' + $ml($ml('form[class*="'+that.getName()+'"] input[value*="[["]')[0]).attr('value'));

		// Create an array to preserve the original metadata hrefs along with their nodes for later replacement
		if(metaHrefs === null) {
			metaHrefs = [];
			$ml('a[class*="' + that.getName() + '"]').each(function (n) {
				var origHref = $ml(this).attr('href');
				var node = this;
				metaHrefs[n] = { origHref: origHref, node:node };
			});
		}

		// Create array to collect metadata nodes in forms along with their metadata terms
		if(formMetaData===null) {
			formMetaData = [];
			var searchStr = 'form[class*="'+ that.getName() +'"] input[value*="[["]';
			//this.sgconsole('looking for form nodes with ' + searchStr);
			$ml(searchStr).each(function() {
				var mdWithDelims = $ml(this).attr('value');
				var mdataName = mdWithDelims.substring(2, mdWithDelims.length-2);
				//that.sgconsole('adding form metadata node: ' + mdataName);
				formMetaData.push({node:this, meta:mdataName});
			});
		}

		//  first do spans that aren't in panels
		// (panels are done all at once when they are created)
		$ml('exhibit:eq('+ curExhibit +') view:eq(0) metadata',xml).each(function (n) {
			var metadataName = $ml(this).attr('name');
			var mdContent = that.getSpecialMetadata(metadataName);
			if(!mdContent) { mdContent = $ml.trim($ml(this).text()); }
//			var targetClass = '"' + that.getName() + ' ' + metadataName + '"';  // should be ie 'docent1 description'
			var targetClass = that.getName() + ' ' + metadataName;  // should be ie 'docent1 description'
			// The two class= below were class*= but were getting false positive between "description" and "description writer"
			if($ml('span.[class="'+targetClass+'"]:not(div[id="'+ sheetDivName  +'"] span)').length > 0) {
				$ml('span.[class="'+targetClass+'"]:not(div[id="'+ sheetDivName  +'"] span)').html(mdContent);
			}
		});

		// Now do hrefs
		// as in
		// $ml('a[class*=docentname]').attr('href', $ml('a').attr('href').replace('45.00','555.00'))
		for(var curHref = 0; curHref < metaHrefs.length; curHref++) {
			var newHref = metaHrefs[curHref].origHref;
			$ml('exhibit:eq('+ curExhibit +') view:eq(0) metadata',xml).each(function (n) {
				var mdContent = $ml.trim($ml(this).text());
				var metadataChunk = '[[' + $ml(this).attr('name') + ']]';
				newHref = newHref.replace(metadataChunk, escape(mdContent));
			});
			//newHref = escape(newHref);
			//console.log("New href with metadata : " + newHref);
			$ml(metaHrefs[curHref].node).attr('href',newHref);
		}

		// now do forms, as in
		//
		// <form class="main foxycart">
		//  <input type="hidden" name="price" value="[[price]]" />
		for (var curFormFieldNum = 0; curFormFieldNum < formMetaData.length; curFormFieldNum++) {
			var mdContent = $ml.trim($ml('exhibit:eq('+ curExhibit +') view:eq(0) metadata[name="'+ formMetaData[curFormFieldNum].meta +'"]', xml).text());
			//that.sgconsole('replacing form content for: ' + mdContent);
			$ml(formMetaData[curFormFieldNum].node).attr('value',mdContent);
		}

		//update foxycart
		if (typeof(fc_CookieFix) == "function") {
			fc_CookieFix();
		}

	};


	 this.sync = function () {
		this.sgconsole('in sync picturebox ' + picturebox + ' dirty ' + pictureboxDirty);
		if (thumbsheet  && thumbsheetDirty) {
			this.sgconsole('sync thumbsheet');
			thumbsheet.select(curExhibit);
			thumbsheetDirty = false;
		}

		if (viewsheet && viewsheetDirty) {
			this.sgconsole('sync viewsheet');
			if (exhibitChange) {
				this.sgconsole('exhibitChange, resetting curView to 0');
				curView = 0;
				viewsheet.updateSheet();
			}
			else {
				viewsheet.select(curView);
			}
			viewsheetDirty = false;

		}
		if (panel && panel.goToPanel) {
			this.sgconsole('sync panelsheet');
			panel.goToPanel(curExhibit);
			//panelsheetDirty = false;
		}
		this.sgconsole("*Syncing: " + curExhibit + "," + curView);
		this.updateMetadata();
		if (picturebox && pictureboxDirty) {
			this.sgconsole('docent calling picturebox.displaybynum with ' + curExhibit);
			picturebox.displayByNum(curExhibit, curView);
		}
	};

	this.hasViewSheet = function() {
		if(viewsheet) { return true; }
		return false;
	};

/* Currently unused	*/
	this.pictureboxReady = function() {
		//picturebox.displayByNum(curExhibit,curView);
	};

	this.thumbSheetReady = function() {

	};
	function errorXML(xhr,errStr,exceptionObj) {
		sgalert("SiteGrinder's Gallery data could not be loaded.  Error: " + errStr);
		//document.write("DOCENT XML READING ERROR: " + errStr + ".  Please report this to <a href='mailto:help@medialab.com'>help@medialab.com</a>");
	}

	function initJQUERYAccessors () {
		this.altThumbQuery = "exhibits > exhibit > views > view > thumbnail > src";
		this.altPictureboxQuery = "exhibits > exhibit > views > view > picturebox > src";
	}


	function setUpGalleryParts () {
		this.thumbsheet = new com.medialab.sg.gallery1.thumbsheet(this.thumbsheetXML);
	}

	function useUncachedXML() {
		return( document.location.href.indexOf('reload=true') != -1);
	}

	function loadGalleryXML () {
		var loadMethod = "GET";
		var ie_pain = '';
		if(useUncachedXML()) { loadMethod = "POST"; ie_pain = '?r=' + Math.floor(Math.random() * 10000);}
		// load up the xml file into an xml parseable something or other
		$ml.ajax({
			type: loadMethod,
			url: galleryXMLpath + ie_pain,
			dataType: "xml",
			//dataType: "html",
			success: that.processXML,
			error: errorXML
		});
		return(true);
	}


(function( $ ){
  $.cssRule = function (Selector, Property, Value) {
    // Selector == {}
    if(typeof Selector == "object"){
      $.each(Selector, function(NewSelector, NewProperty){
        $.cssRule(NewSelector, NewProperty);
      });
      return;
    }

    // Selector == "body:background:#F99"
    if((typeof Selector == "string") && (Selector.indexOf(":") > -1)
      && (Property === undefined) && (Value === undefined)){
      var Data = Selector.split("{");
      Data[1] = Data[1].replace(/\}/, "");
      $.cssRule($.trim(Data[0]), $.trim(Data[1]));
      return;
    }

    // Check for multi-selector, [ IE don't accept multi-selector on this way, we need to split ]
    if((typeof Selector == "string") && (Selector.indexOf(",") > -1)){
      var Multi = Selector.split(",");
      for(var x = 0; x < Multi.length; x++){
        Multi[x] = $.trim(Multi[x]);
        if (Multi[x] !== "") {
          $.cssRule(Multi[x], Property, Value);
      }
      }

      return;
    }

    // Property == {} or []
    if(typeof Property == "object"){

      // Is {}
      if(typeof Property.length == 'undefined'){

        // Selector, {}
        $.each(Property, function(NewProperty, NewValue){
          $.cssRule(Selector + " " + NewProperty, NewValue);
        });

      // Is [Prop, Value]
      }else if((Property.length == 2) && (typeof Property[0] == "string") &&
        (typeof Property[1] == "string")){
        $.cssRule(Selector, Property[0], Property[1]);

      // Is array of settings
      }else{
        for(var x1 = 0; x1 < Property.length; x1++){
          $.cssRule(Selector, Property[x1], Value);
        }
      }

      return;
    }

    // Parse for property at CSS Style "{property:value}"
    if((typeof Property == "string") && (Property.indexOf("{") > -1)
       && (Property.indexOf("}") > -1)){
      Property = Property.replace(/\{/, "").replace(/\}/, "");
    }

    // Check for multiple properties
    if((typeof Property == "string") && (Property.indexOf(";") > -1)){
      var Multi1 = Property.split(";");
      for(var x2 = 0; x2 < Multi1.length; x2++){
        $.cssRule(Selector, Multi1[x2], undefined);
      }
      return;
    }

    // Check for property:value
    if((typeof Property == "string") && (Property.indexOf(":") > -1)){
      var Multi3 = Property.split(":");
      $.cssRule(Selector, Multi3[0], Multi3[1]);
      return;
    }

    //********************************************
    // Logical CssRule additions
    // Check for multiple logical properties [ "padding,margin,border:0px" ]
    if((typeof Property == "string") && (Property.indexOf(",") > -1)){
      var Multi2 = Property.split(",");
      for(var x3 = 0; x3 < Multi2.length; x3++){
        $.cssRule(Selector, Multi2[x3], Value);
      }
      return;
    }

    //********************************************
    // Check for Most One Style Sheet
    // jQuery.CssRule need at last one Style Sheet enabled on the page.
    var styleSheetsLength = document.styleSheets.length;
    if(styleSheetsLength <= 1){
      // Append for no IE browsers
	  var styleSheet = null;
      if(!document.createStyleSheet){
        styleSheet = (typeof document.createElementNS != 'undefined') ?
          document.createElementNS("http://www.w3.org/1999/xhtml", "style") :
          document.createElement("style");
        styleSheet.setAttribute("type", "text/css");
        styleSheet.setAttribute("media", "screen");
        if(styleSheetsLength === 0){
          $($("html")[0]).prepend(styleSheet);
        }
      // Append for IE
      }else{
        var BaseStyle = document.getElementsByTagName("style");
        if (BaseStyle.length > 0) {
          document.getElementsByTagName("style")[0].disabled = false;
		}
        styleSheet = document.createElement("style");
        styleSheet.setAttribute("type", "text/css");
        styleSheet.setAttribute("media", "screen");
        styleSheet.disabled = false;
        $($("html")[0]).prepend(styleSheet);
      }
    }

    if ((Property === undefined) || (Value === undefined)) {
      return;
	}

    Selector = $.trim(Selector);
    Property = $.trim(Property);
    Value = $.trim(Value);

    if ((Property === "") || (Value === "")) {
      return;
	}

    // adjusts on property
    if($.browser.msie && $.browser.version < 9) {
      // for IE (@.@)^^^
      switch(Property){
        case "float": Property = "style-float"; break;
      }
    }else{
      // CSS rights
      switch(Property){
        case "float": Property = "css-float"; break;
      }
    }

    var CssProperty = (Property || "").replace(/\-(\w)/g, function(m, c){ return (c.toUpperCase()); });

  	var Rules = null;
    for(var i = 0; i < document.styleSheets.length; i++){
	  var CurrentStyleSheet = document.styleSheets[i];
	  if (typeof(CurrentStyleSheet) != 'undefined' && CurrentStyleSheet !== null && typeof(CurrentStyleSheet.href) == 'string'
	      && CurrentStyleSheet.href.indexOf('foxycart') < 0)
	  {
  		//console.log('checking ' + CurrentStyleSheet.href);
		try {
			if (CurrentStyleSheet.cssRules !== null && typeof(CurrentStyleSheet.cssRules) != 'undefined') {
				Rules = CurrentStyleSheet.cssRules;
			}
			if (CurrentStyleSheet.rules !== null && typeof(CurrentStyleSheet.rules) != 'undefined' && !($.browser.msie && $.browser.version >= 9)) {
				Rules = CurrentStyleSheet.rules;
			}
		}
		catch(ex) {
			continue;
		}
		//Rules = (CurrentStyleSheet.cssRules || CurrentStyleSheet.rules);
		var LowerSelector = Selector.toLowerCase();

		if (Rules !== null && typeof Rules == 'object') {
			if (Rules.length !== 0) {
			for (var i2 = 0, len = Rules.length; i2 < len; i2++) {
				if (Rules[i2].selectorText && (Rules[i2].selectorText.toLowerCase() == LowerSelector)) {
						if (Value !== null) {
						Rules[i2].style[CssProperty] = Value;
						return;
					}
					else {
						if (CurrentStyleSheet.deleteRule) {
							CurrentStyleSheet.deleteRule(i2);
						}
						else
							if (CurrentStyleSheet.removeRule) {
								CurrentStyleSheet.removeRule(i2);
							}
							else {
								Rules[i2].style.cssText = "";
							}
					}
				}
			}
		}
	}
	}
	else {
			//this.sgconsole('encountered null stylesheet in cssRule()');
	}
   }

    if(Property && Value){
	  Rules = null;

      for(i = 0; i < document.styleSheets.length; i++){
	    var WorkerStyleSheet = document.styleSheets[i];
		try {  // avoid Firefox security err in case css is remote

	    if(typeof(WorkerStyleSheet) != 'undefined' && WorkerStyleSheet !== null && typeof(WorkerStyleSheet.href) == 'string' && WorkerStyleSheet.href.indexOf('foxycart') < 0
		 && (WorkerStyleSheet.cssRules !== null || WorkerStyleSheet.rules !== null)) {
	        if(WorkerStyleSheet.insertRule){
			  try {
			  	if (WorkerStyleSheet.cssRules !== null && typeof(WorkerStyleSheet.cssRules) != 'undefined') {
			  		Rules = WorkerStyleSheet.cssRules;
			  	}
			  	if (WorkerStyleSheet.rules !==null && typeof(WorkerStyleSheet.rules) != 'undefined' && !($.browser.msie && $.browser.version >= 9)) {
			  		Rules = WorkerStyleSheet.rules;
			  	}
			  }
			  catch(ex) {
			  	continue;
			  }
	          //Rules = (WorkerStyleSheet.cssRules || WorkerStyleSheet.rules);
	          WorkerStyleSheet.insertRule(Selector + "{ " + Property + ":" + Value + "; }", Rules.length);
	        }else if(WorkerStyleSheet.addRule){
	          WorkerStyleSheet.addRule(Selector, Property + ":" + Value + ";", 0);
	        }else{
	          throw new Error("Add/insert not enabled.");
	        }
		}
		else { }
		}
		catch(ex){
			continue;
		}
      }
    }
  };

  $.tocssRule = function(cssText){
    var matchRes = cssText.match(/(.*?)\{(.*?)\}/);
    while(matchRes){
      cssText = cssText.replace(/(.*?)\{(.*?)\}/, "");
      $.cssRule(matchRes[1], matchRes[2]);
      matchRes = cssText.match(/(.*?)\{(.*?)\}/);
    }
  };
})( $ml );

(function( $ ){
           $.fn.toXML = function(){
               var toXML = function(node){
                    var out = '';
                    var attributes = '';
                    var content = '';
                    out += '<' + node.nodeName;
                    if (node.childNodes) {
                        for (var i = 0; i < node.childNodes.length; i++) {
                            switch (node.childNodes[i].nodeType) {
                                case 1: // ELEMENT_NODE
                                    content += toXML(node.childNodes[i]);
                                    break;
                                case 2: // ATTRIBUTE_NODE
                                    attributes += ' ' + node.childNodes[i].nodeName + '="' +
                                    node.childNodes[i].nodeValue +
                                    '"';
                                    break;
                                case 3: // TEXT_NODE
                                case 4: // CDATA_SECTION_NODE
                                case 5: // ENTITY_REFERENCE_NODE
                                case 6: // ENTITY_NODE
                                case 7: // PROCESSING_INSTRUCTION_NODE
                                case 8: // COMMENT_NODE
                                case 9: // DOCUMENT_NODE
                                case 10: // DOCUMENT_TYPE_NODE
                                case 11: // DOCUMENT_FRAGMENT_NODE
                                case 12: // NOTATION_NODE
                                    content += node.childNodes[i].nodeValue;
                                    break;
                            }
                        }
                    }
                    out += attributes;
                    if (content.length > 0) {
                        out += '>' + content;
                        out += '<\/' + node.tagName + '>';
                    }
                    else {
                        out += '/>';
                    }
                    return out;
                };
                var out = '';
                if (this.length > 0) {
                    if ( (typeof XMLSerializer == 'function' || typeof XMLSerializer == 'object')
					 && !($.browser.msie === true && Math.floor(parseFloat($.browser.version)) == 9)) {  // ie9 beta fix
                        var xs = new XMLSerializer();
                        this.each(function(){
                            out += xs.serializeToString(this);
                        });
                    }
                    else
                        if (this[0].xml !== undefined) {
                            this.each(function(){
                                out += this.xml;
                            });
                        }
                        else {
                            if (this.length > 0) {
                                this.each(function(){
                                    out += toXML(this);
                                });
                            }
                        }
                }
                return out;
            };
})( $ml );

	this.getUrlParam = function(param) {
	    var regex = '[?&]' + param + '=([^&#]*)';
	    var results = (new RegExp(regex)).exec(window.location.href);
	    if (results) {
			return results[1];
		}
	    return null;
	};

	this.sgconsole = function(text) {
		if(useConsole) {
			var msg = "DOCENT : " + text;
			if (window.console && console.log) {
				console.log(msg);
			}
			else
				if (window.opera && window.opera.postError) {
					window.opera.postError(msg);
				}
		}
	};

	this.sendMessage = function(where,what) {
		switch (where) {
			case "thumbsheet":
				if(thumbsheet) { return thumbsheet.message(what); }
				else { this.agalert ("There's no thumbsheet to send the message " + what + " to"); }
				break;
			case "viewsheet":
				if(viewsheet) { return viewsheet.message(what); }
				else { this.agalert ("There's no viewsheet to send the message " + what + " to"); }
				break;
			case "picturebox":
				if(picturebox) { return picturebox.message(what); }
				else { this.agalert ("There's no picturebox to send the message " + what + " to"); }
				break;
			case "bigbox":
				if (bigbox) { return bigbox.message(what);	}
				else { this.agalert ("There's no bigbox to send the message " + what + " to"); }
				break;
			default:
				this.agalert("Can't send message '"+ what + "' because I don't recognize '"+ where + "' as a message target.");
				break;
		}
		return null;
	};

	// for now this only checks the first view, as all views have the same metadata
	this.getMetadata = function(dataLabel, exhibitNum) {
		//this.sgconsole('Getting metadata for exhibit ' + exhibitNum);
		if(typeof(exhibitNum) == 'undefined') { exhibitNum = curExhibit; }
		var mdata="";
		if (typeof(dataLabel) == 'undefined' || dataLabel === null || dataLabel === "") {
			return "";
		}
		mdata = $ml('exhibits > exhibit:eq('+exhibitNum+') > views > view:eq(0) > metadataentries > metadata[name="'+dataLabel+'"]',xml).text();
		return mdata;
	};





	this.getThumbImage = function(thumbNum, mode) {
		if(typeof mode =='undefined' || mode == 'exhibit') {
			return  $ml('exhibit:eq('+thumbNum+') > views > view:eq(0) > thumbnail > url', xml).text();
		} else {
			return $ml('exhibit:eq(' + curExhibit + ') view > viewthumb > url', xml).eq(thumbNum).text();
		}
	};

	this.iterateExhibitThumbImageUrls = function (iterator,data) {
		var iiterator = iterator;
		var idata = data;
		$ml('exhibit', xml).each(function(n){
			iiterator(n, idata, $ml('views > view > thumbnail > url', this).eq(0).text());
		});
	};


	// assumes current exhibit unless specified
	this.iterateViewThumbImageUrls = function(iterator, data, exhibitNumIn) {
		var iiterator = iterator;
		var idata = data;

		var curExhibit = exhibitNumIn ? exhibitNumIn : this.getCurrentExhibit();

//		if ($ml('exhibit:eq(' + curExhibit + ') view', xml).length > 1) {
			$ml('exhibit:eq(' + curExhibit + ') view', xml).each(function(n){
				iiterator(n, idata, $ml('viewthumb > url', this).eq(0).text());
			});
//		}
	};

	this.getNumViews = function(exNumIn) {
		var exNum = exNumIn ? exNumIn : curExhibit;
		var numViews = $ml('exhibit:eq(' + curExhibit + ') view', xml).length;
		this.sgconsole("num views for exhibit "+curExhibit+ " : " + numViews);
		return numViews;
	};

	this.pictureboxSelectByNum = function(imageNum, updatePbox) {
		this.sgconsole("Picturebox pictureboxSelectByNum exhibit: " + imageNum);
		if (typeof updatePbox == 'undefined') {
			pictureboxDirty = false;
		} else {
			pictureboxDirty = updatePbox;
		}
		if (imageNum != curExhibit) {
			this.sgconsole("image not current so about to sync");
			prevExhibit = curExhibit;
			curExhibit = imageNum;
			thumbsheetDirty=true;
			viewsheetDirty=true;
			//thumbsheet.select(curExhibit);
			this.sync();
		}
	};

	this.sgconsole("docent initializing");
	this.init();


	// EVENTS:
	this.thumbHover = function (e) { };
	this.thumbClick = function (e) { };
    this.alt_thumbHover = function (e) { };
    this.alt_thumbClick = function (e) { };

/*
 *
 *  Navigation functions:
 *
 * 	next/previous
 *  nextview/previousview
 *  nextpanelset/previouspanelset
 *  nextthumbset/previousthumbset
 *  nextviewset/previousviewset
 *
 */

	// NEXT / PREVIOUS
	this.next = function() {  // advance to next exhibit
		this.nextClick(null);
	};
	this.prev = this.previous = function() {  // go to previous exhibit
		this.previousClick(null);
	};

	// NEXTVIEW/PREVIOUSVIEW
	this.nextView = function() {  // advance to next view
		if (curView < this.getNumViews()-1) {
			curView++;
			pictureboxDirty = true;
			viewsheetDirty = true;
			thumbsheetDirty = false;
			exhibitChange = false;
			this.sync();
		}
	};

	this.prevView = this.previousView = function() {  // go to previous view
		if (curView > 0) {
			curView--;
			pictureboxDirty = true;
			viewsheetDirty = true;
			thumbsheetDirty = false;
			exhibitChange = false;
			this.sync();
		}
	};

	// NEXTTHUMBSET/PREVIOUSTHUMBSET
	this.nextThumbSet = function() {  // advance to next view
		 this.sgconsole('next Thumb set ');
		 if (thumbsheet && thumbsheet.nextThumbSet) {
		 	thumbsheet.nextThumbSet();
		 }
	};

	this.prevThumbSet = this.previousThumbSet = function() {  // go to previous view
		 this.sgconsole('prev Thumb set ');
		 if (thumbsheet && thumbsheet.previousThumbSet) {
		 	thumbsheet.previousThumbSet();
		 }
	};

	// NEXTVIEWSET/PREVIOUSVIEWSET   --   ARE THESE SUPPORTED YET?
	this.nextViewSet = function() {  // advance to next view
		 this.sgconsole('next View set ');
		 if (viewsheet && viewsheet.nextThumbSet) {
		 	viewsheet.nextThumbSet();
		 }
	};

	this.prevViewSet = this.previousViewSet = function() {  // go to previous view
		 this.sgconsole('prev View set ');
		 if (viewsheet && viewsheet.previousThumbSet) {
		 	viewsheet.previousThumbSet();
		 }
	};

	// NEXTPANEL/PREVIOUSPANEL/GOTOPANEL
	this.nextPanel = function () {
		 this.sgconsole('next panel set ' + panel);
		 if (panel && panel.nextPanel) {
		 	panel.nextPanel();
		 }
	};

	this.prevPanel = this.previousPanel = function () {
		 this.sgconsole('prev panel set ' + panel);
		 if (panel && panel.previousPanel) {
		 	panel.previousPanel();
		 }
	};

	this.goToPanel = function(panelNum) {
		if(typeof panel != 'undefined' && typeof panelNum != 'undefined' && typeof panel.goToPanel == 'function') {
			panel.goToPanel(panelNum);
		}
	};

	// NEXTPANELSET/PREVIOUSPANELSET
	this.nextPanelSet = function () {
		 this.sgconsole('next panel set ' + panel);
		 if (panel && panel.nextPanelSet) {
		 	panel.nextPanelSet();
		 }
	};

	this.prevPanelSet = this.previousPanelSet = function () {
		 this.sgconsole('prev panel set ' + panel);
		 if (panel && panel.previousPanelSet) {
		 	panel.previousPanelSet();
		 }
	};



    this.pictureboxNextClick = function(updatePbox, e, loop){
		this.sgconsole("Picturebox Next click received.");
		if (typeof loop == 'undefined') {
			loop = false;
		}
		if (curExhibit < exhibitCount - 1) {
			curExhibit++;
		}
		else {
			if (loop) {
				curExhibit = 0;
			}
			else {
				return;
			}
		}
		if (updatePbox) {
			pictureboxDirty = true;
		} else {
			pictureboxDirty = false;
		}
		thumbsheetDirty = true;
		viewsheetDirty = true;
		exhibitChange = true;
		this.sync();
	};


   this.pictureboxPreviousClick = function (updatePbox, e) {
		if (curExhibit > 0) {
			curExhibit--;
			thumbsheetDirty = true;
			viewsheetDirty = true;
			if (updatePbox) {
				pictureboxDirty = true;
			} else {
				pictureboxDirty = false;
			}
			exhibitChange = true;
			this.sync();
		}
	};

    this.toCSSColor = function(hexColor) {
		if (typeof hexColor != 'undefined') {
			if(typeof(hexColor)=='number') { return '#' + hexColor;}
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
	};

    this.thumbNextClick = function (mode,e) {
		this.sgconsole("thumb Next click received. mode:" + mode);
		if (curExhibit < exhibitCount-1) {
			curExhibit++;
			if(mode=='view') { thumbsheetDirty = false; /*viewsheetDirty=false;*/exhibitChange = false;} else {thumbsheetDirty = true; /*viewsheetDirty=true;*/exhibitChange = true;}
			viewsheetDirty = true;
			pictureboxDirty=true;
			this.sync();
		}
	};

   this.thumbPreviousClick = function (mode,e) {
		this.sgconsole("thumb prev click received. mode:" + mode);
		if (curExhibit > 0) {
			curExhibit--;
			if(mode=='view') { thumbsheetDirty = false; /*viewsheetDirty=false;*/exhibitChange = false;} else {thumbsheetDirty = true; /*viewsheetDirty=true;*/exhibitChange = true;}
			viewsheetDirty = true;
			pictureboxDirty = true;
			this.sync();
		}
	};
  	// Next exhibit
    this.nextClick = function (e) {
		this.sgconsole("Next click received.");
		if (curExhibit < exhibitCount - 1) {
			curExhibit++;
		} else {
			curExhibit=0;
		}
		thumbsheetDirty = true;
		viewsheetDirty = true;
		pictureboxDirty = true;
		exhibitChange = true;
		this.sync();
	};

    this.previousClick = function(e){
		if (curExhibit > 0) {
			curExhibit--;
		}
		else {
			curExhibit = exhibitCount-1;
		}
		thumbsheetDirty = true;
		viewsheetDirty = true;
		pictureboxDirty = true;
		exhibitChange = true;
		this.sync();
	};

    this.nextHover = function (e) { };
	this.previousHover = function (e) { };
    this.pictureboxHover = function (e) { };

	this.pictureboxIsFlashBased = function() {
		var flashBased = false;
		if (pictureboxOptions) {
			$ml('option', pictureboxOptions).each(function(n){
				var val  = String($ml(this).attr('value'));
				var lVal = val.toLowerCase();
				if(lVal.substring(lVal.length-4) == '.swf') { flashBased = true; }
			});
		}
		return flashBased;
	};

	this.gotoCustomUrl = function(exhibitNum, viewNum, windowName, props) {
		if(typeof(exhibitNum)=='undefined') { exhibitNum = curExhibit ; }
		if(typeof(viewNum)=='undefined') { viewNum = curView; }
		var url = $ml.trim($ml('exhibits exhibit:eq('+ exhibitNum  +') views view:eq('+ viewNum +') link',xml).text());
		this.gotoUrl(url, windowName, props);
	};

	this.gotoUrl = function(url, newWindowName, props) {
		if (typeof(url) != 'string') {
			return;
		}
		if(typeof(newWindowName)=='undefined') {
			location=url;
		}
		else {
			if (typeof props == 'undefined') { props = ""; }
			window.open(url,newWindowName,props);
		}
	};

	this.getJSON = function(url, callback){
//		this.getYQL('select * from rss where url="' + url + '"', callback);
		this.getYQL('select * from feed where url="' + url + '"', callback);
	};

	this.getYQL = function(yqlQuery, callback) {
 		var eQuery = escape(yqlQuery);
		eQuery = eQuery.replace(/\//g,'%2F');
		var finalQuery = 'http://query.yahooapis.com/v1/public/yql?q=' + eQuery + '&format=json&callback=?';
		$ml.getJSON(finalQuery,callback);
	};

	// Escapes parentheses, commas, white space characters, single quotes (') and double quotes (")
	// This way we can use an unquoted URI in the url() for the css background property.
	// Daniel Paul Searles - 9/8/10
	this.url2CssUrl = function(url) {
		if (typeof url == 'string') {
			var escapeChars = [ ')', '(', ',', ' ', '\'', '"', '0' ];
			for (var key in escapeChars) {
				if (typeof escapeChars[key] == 'string') {
					url = url.replace(new RegExp('\\' + escapeChars[key], 'g'), '\\' + escapeChars[key]);
				}
			}
		}
		return url;
	};
/* experimental, not used

	this.json2GalleryXML = function(jIn, labelsIn) {
		var labels = labelsIn;

		var myXML = "<exhibits>";
		$ml.each(data.query.results.item, function(index, item){
			myXML += '<exhibit><views><view><metadataentries>';
			for (var curLabelNum = 0; curLabelNum < labels.length; curLabelNum++) {
				var content = item[labels[curLabelNum]];
				//if (index == 0) {alert(item[labels[curLabelNum]]);}
				if (typeof(content) != 'undefined' && content !== "") {
					myXML += '<metadata name="' + labels[curLabelNum] + '">';
					//alert( $ml('p',item.description).text());
					myXML += content;
					//alert(item.description);
					myXML += '</metadata></metadataentries>';
					myXML += '</view></views></exhibit>';
				}
			}
		});
		myXML += '</exhibits>';
		return myXML;
	};
*/
	this.getClickUrl = function(mode) {
		if(typeof mode == 'undefined') { mode='exhibit'; }
		if(mode=='exhibit') {
			return $ml.trim($ml('exhibits exhibit:eq('+ curExhibit +') view:eq(0) link',xml).text());
		}
		else if (mode=='view') { // are separate urls for views supported?
			return $ml.trim($ml('exhibits exhibit:eq('+ curExhibit +') view:eq('+ curView +') link',xml).text());
		}
		else { return ""; }
	};

	this.pictureboxClick = function(updatePbox,clickAction) {

		// maybe some code here to assume updatePbox false if a flash pbox?
		if(typeof(updatePbox)=="undefined"  && this.pictureboxIsFlashBased()) { updatePbox=false; }

		if (!clickAction) {
			this.sgconsole("docent finding clickaction itself since picturebox did not supply it");
			clickAction = this.getPictureBoxOption('clickaction');
		}

		if (typeof(clickAction) == "string") {
			this.sgconsole("docent: picBoxClick: " + clickAction + " updatePbox: " + updatePbox);
			switch (clickAction.toLowerCase()) {
				case 'advance':
					var loopOption = true;
					var endOption = this.getPictureBoxOption('endaction');
					if(endOption!=="loop") { loopOption = false;}
					this.pictureboxNextClick(updatePbox, null, loopOption);
					break;
				case 'url':
					//var url = this.getPictureBoxOption('clickurl');  // Initially this was a DM option but now it's set in the CMS
					var url = this.getClickUrl();
					if(typeof url != 'string' || url==="") { return; }
					if (!this.getPictureBoxOption('newwindow')) {
						this.gotoUrl(url);
					}
					else {
						var newWindowName = this.getPictureBoxOption('newwindowname') || "Untitled";
						var props = "";
						var h = this.getPictureBoxOption('newwindowheight');
						if (h !== "" && h !== 0) {
							props += "height=" + h;
						}

						if (props.length > 0) {	props += ","; }
						var w = this.getPictureBoxOption('newwindowwidth');
						if (w !== "" && w !== 0) {
							props += "width=" + w;
						}

						if (props.length > 0) { props += ","; }
						if (w === "" || h === "" || w === 0 || h === 0) {
							props += "resizable=yes";
						}
						else {
							props += "resizable=no";
						}
						this.gotoUrl(url, newWindowName, props);
					}
					break;
				case 'bigbox':
					this.sgconsole("about to call openBigBox");
					this.openBigbox();
					break;
				default:
					this.sgconsole("unknown picturebox clickaction: " + clickAction);
					break;
			}
		}
		else {
			this.sgconsole("picturebox clicked, but clickaction is null or undefined ");
		}
	};
};

if(!com.medialab.parseUri) { com.medialab.parseUri = {}; }
com.medialab.parseUri.options = {
	strictMode: false,
	key: ["source","protocol","authority","userInfo","user","password","host","port","relative","path","directory","file","query","anchor"],
	q:   {
		name:   "queryKey",
		parser: /(?:^|&)([^&=]*)=?([^&]*)/g
	},
	parser: {
		strict: /^(?:([^:\/?#]+):)?(?:\/\/((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?))?((((?:[^?#\/]*\/)*)([^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
		loose:  /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/
	}
};
com.medialab.parseUri.parse = function(str) {
	var	o   = com.medialab.parseUri.options,
		m   = o.parser[o.strictMode ? "strict" : "loose"].exec(str),
		uri = {},
		i   = 14;

	while (i--) {
		uri[o.key[i]] = m[i] || "";
	}

	uri[o.q.name] = {};
	uri[o.key[12]].replace(o.q.parser, function ($0, $1, $2) {
		if ($1) {
			uri[o.q.name][$1] = $2;
		}
	});

	return uri;
};
