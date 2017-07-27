/**
 *    Copyright 2009-2010, As Is Software Inc., dba Media Lab Inc.
 *    http://www.medialab.com
 */

var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.gallery1) { com.medialab.sg.gallery1 = {};}

com.medialab.sg.gallery1.fancybox = function(thumbSheetXMLIn, docentIn){
	var that = this;
	var debug = false;
	var fancyboxGallery = {};
	var sgalert = function(msg){
		if (debug) {
			alert(msg);
		}
	};
	var docent = docentIn;
	var useAllViews = true;
	var titleMetaField = "";

	//alert(docentIn);
	var xml = thumbSheetXMLIn;
	
	function init(){
		docent.sgconsole("Bigbox shadowbox Init()");
		var ruleObj = {};
		ruleObj["#fancy_wrap"]=["position:static"];
		$ml.cssRule(ruleObj);		//fancyboxGallery = makeFancyboxGallery();
	}
	
	
	function makeFancyboxGallery(mode,curExhibit) {
		var fancyItems = [];
		var fancyOptions = {};
		if (!curExhibit) { curExhibit=0; }
		if (!mode) { mode = 'exhibit'; }

		if (mode == 'exhibit') {
			$ml('exhibit', xml).each(function(n){
				var myOrig = null;
				var myThumbUrl = $ml.trim($ml('views > view > thumb > url', this).eq(0).text());
				
				//var mySrc = $ml('div[background-image*='+myThumbUrl+']');
				$ml('div').each(function(n) {
					var s = $ml(this).attr('style');
					if(typeof(s)=="string" && s.indexOf(myThumbUrl)>-1) { myOrig=$ml(this); };
				});
				myItem = new Object();
				var myUrl = $ml('views > view > bigbox > url', this).eq(0).text();
				if (!myUrl || myUrl == "") {
					myUrl = $ml('views > view > picturebox > url', this).eq(0).text();
				}
				if (myUrl && myUrl != "") {
					var myItem = new Object();
					myItem.href = myUrl;
					
					if(myOrig) {myItem.orig = myOrig;}
					fancyItems.push(myItem);
				}
			})
		}
		else {
			$ml('exhibit:eq('+curExhibit+') view', xml).each(function(n){
				var myOrig = null;
				var myThumbUrl = $ml.trim($ml('viewthumb > url', this).text());
				
				$ml('div').each(function(n) {
					var s = $ml(this).attr('style');
					if(typeof(s)=="string" && s.indexOf(myThumbUrl)>-1) { myOrig=$ml(this); };
				});
				var myUrl = $ml('bigbox > url', this).text();
				if (!myUrl || myUrl == "") {
					myUrl = $ml('picturebox > url', this).text();
				}
				if (myUrl && myUrl != "") {
					var myItem = new Object();
					myItem.href = myUrl;
					fancyItems.push(myItem);
				}
			})
		}
		fancyOptions.itemArray = fancyItems;
		fancyOptions.zoomOpacity= true;
		fancyOptions.overlayShow= false;
		fancyOptions.zoomSpeedIn= 500;
		fancyOptions.zoomSpeedOut= 500;
		return fancyOptions;
	}
	

	function setGalleryOptions(gal) {
		gal.zoomOpacity= getOption('zoomopacity');
		gal.overlayShow= getOption('overlayshow');
		gal.overlayColor= getColorOption('overlaycolor');
		gal.overlayOpacity= getOption('overlayopacity');
		gal.zoomSpeedIn= getOption('zoomspeedin');
		gal.zoomSpeedOut= getOption('zoomspeedout');
	}
	
	function tryToFindOrig(mode,curExhibit,curView) {
		var myOrig=null;
		if(mode == 'exhibit') {
			var myThumbUrl = $ml.trim($ml('exhibits > exhibit:eq('+curExhibit+') > views > view > thumb > url:eq(0)', xml).text());
		} else {
			myThumbUrl = $ml.trim($ml('exhibits > exhibit:eq('+curExhibit+') > views > view > viewthumb > url:eq('+curView+')', xml).text());
		}
		$ml('div').each(function(n) {
			var s = $ml(this).attr('style');
			if(typeof(s)=="string" && s.indexOf(myThumbUrl)>-1) { myOrig=$ml(this); };
		});
		return myOrig;
	}
	
	function getBigboxURL(mode, exhibitnum, viewnum) {
		if(mode == 'exhibit') {
			var myUrl = $ml('exhibits > exhibit:eq('+exhibitnum+') > views > view > bigbox > url', xml).eq(0).text();
			if (!myUrl || myUrl == "") {
				myUrl = $ml('exhibits > exhibit:eq('+exhibitnum+') > views > view > picturebox > url', xml).eq(0).text();
			}
		} else {
			var myUrl = $ml('exhibits > exhibit:eq('+exhibitnum+') > views > view > bigbox > url:eq('+viewnum+')', xml).text();
			if (!myUrl || myUrl == "") {
				myUrl = $ml('exhibits > exhibit:eq('+exhibitnum+') > views > view > picturebox > url:eq('+viewnum+')', xml).text();
			}
		}
		myUrl = $ml.trim(myUrl);
		return myUrl;
	}
	
	function getBigboxMetadataTitle(mode, exhibitnum, viewnum) {
		var title="";
		if (typeof(titleMetaField) == 'undefined' || titleMetaField == null || titleMetaField == "") {
			return "";
		}
		
		/*if(mode == 'exhibit') {
			title = $ml('exhibits > exhibit:eq('+exhibitnum+') > views > view:eq(0) > metadataentries > metadata[name="'+titleMetaField+'"]',xml).text();
		} else {
			title = $ml('exhibits > exhibit:eq('+exhibitnum+') > views > view:eq('+viewNum+') > metadataentries > metadata[name="'+titleMetaField+'"]',xml).text();
		}*/
		return docent.getMetadata(titleMetaField,exhibitnum);
	}
	
	
    function makeSingleItemGallery(url, orig) {
		fancyboxGallery = {};
		setGalleryOptions(fancyboxGallery);
		
		var item = { href:url };
		if(typeof('orig') != 'undefined' && orig!=null) { item.orig = orig; };
		fancyboxGallery.itemArray = [item];
				
		return fancyboxGallery;
	}
	
	function getOption(optName) {
		var w = docent.getBigBoxOption(optName);
		return w;
	}
	
	this.openByURL = function (url) {
		docent.sgconsole("fancybox openByURL() with " + url);
		$ml('nothingtoseehere').fancybox(makeSingleItemGallery(url));
	}
	
	this.getGallery = function() {
		return fancyboxGallery;
	}
	
    function getColorOption(optName) {
		var hexColor = getOption(optName);
		//console.log('hexColor: ' + hexColor);
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
	
	this.openByView = function(mode, exhibitnum, viewnum) {
		docent.sgconsole("fancybox: openByView trying to start... " + exhibitnum + ", " + viewnum);
		titleMetaField = docent.getBigboxOption('titlemetafield');
		var orig = tryToFindOrig(mode, exhibitnum, viewnum);
		var url = getBigboxURL(mode, exhibitnum, viewnum);	
		var gal = makeSingleItemGallery(url,orig);
		var title = getBigboxMetadataTitle(mode,exhibitnum,viewnum);
		docent.sgconsole('found title '+title);
		if (typeof(title) != 'undefined' && title != null && title != "") {
			gal.itemArray[0].title = title;
		}
				
		$ml('nothingtoseehere').fancybox(gal);

	}
	
	init();
}