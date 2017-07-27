/**
 *    Copyright (c) 2009-2010, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.gallery1) { com.medialab.sg.gallery1 = {};}


com.medialab.sg.gallery1.pictureboxSiteGrinderCSSBasic = function(pictureboxXMLIn, docentIn){
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
		
	function getPictureboxQuery() {
		// sample : '#divname img'
		return '#'+myDivID+' img';
	}
	
	this.getRightClickBlock = function () { return rightClickBlock; }
	
	this.displayByUrl = function(imageUrl) {
		var pbq = getPictureboxQuery();
		$ml(pbq).attr('src', imageUrl);
		//$ml(pbq).attr('alt', "A very expensive-to-create picture of some space crap");
	}
	
	this.displayByNum = function(exhibitNum, viewNum) {
		docent.sgconsole('CSS PICTUREBOX displayByNum()');
		this.displayByUrl(getUrlFromNum(exhibitNum,viewNum));
	}
	
	function getUrlFromNum(exhibitNum, viewNum){
		return $ml('exhibits exhibit:eq('+ exhibitNum +') views view:eq('+ viewNum +') picturebox url',xml).text();

	}
	
	
 	function initPictureBox() {
		var containerText = '';
		containerText+= '<table cellpadding="0" cellspacing="0"><tr><td align="center" valign="middle">';
		containerText+= '<img id="testimg" />';
		containerText+= '</td></tr></table>';
		
		var container = $ml(containerText);
		container.appendTo('#'+myDivID);
		//that.displayByNum(0,0);
		var exhibitToShow = docent.getCurrentExhibit();
		if (getOption('startrandom')) {
			exhibitToShow = Math.floor(Math.random() * docent.getExhibitCount());
			docent.pictureboxSelectByNum(exhibitToShow,false);
			that.displayByNum(exhibitToShow, 0);
		}
		else {
			that.displayByNum(exhibitToShow, 0);
		}
		
		if(rightClickBlock) { 
			docent.sgconsole('2disabling right click');
			$ml('#'+myDivID).bind("contextmenu",function(e){ alert('Context menu disabled for this image.'); return false; } ); 
		}
		
		if (myClickAction != "none") {
			$ml('#'+myDivID).bind('click',function(event){myClick(event);});
			$ml('#'+myDivID).css('cursor','pointer');

		}
		//$ml('#'+myDivID + ' a').css('outline','none'); // turns off ff picturebox.  Doesn't need it for some reason.
		
	}
	
	function myClick(e) { 
		docent.sgconsole("picturebox clicked");
		
/*		if(rightClickBlock) {
			var msg = "Image protected from saving.";
			if (navigator.appName == 'Netscape' && e.which == 3) {
				alert(msg);
				return false;
			}
			if (navigator.appName == 'Microsoft Internet Explorer' && event.button==2) {
				alert(msg);
				return false;
			}
		}*/
		
		docent.pictureboxClick(true,null);
		return false;
	}
	
	function init() {
		docent.sgconsole("SG Standard Picturebox: Init");
		if(getOption("type")!="sitegrindercss") { alert("Type mismatch between picturebox javascript and data!"); return;}
		if(getOption("version") > 1.0) { alert("Picturebox javascript version older than data version!"); return;}
		processXML();
		initPictureBox();
	}
	
	 function processXML () {
		sgalert("SG Standard Picturebox: processXML", this);
		
		numExhibits = docent.getExhibitCount();
		myWidth = getOption("width");
		myHeight = getOption("height");
		myDivID = getOption("divname");
		rightClickBlock = getOption("rightclickblock");
		myClickAction = getOption("clickaction");
		myHoverAction = getOption("hoveraction");
	
		return;
	}
		
	function getOption(opt) {
		return docent.getPictureboxOption(opt);
		//var w = $ml('options > picturebox > option[varname='+opt+']', xml).attr("value");
		
	}
		
		
	init();
}

