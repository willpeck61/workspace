/**
 *    Copyright (c) 2011, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

/*global dojo, dijit, console, $ml, $, path_to_sgcms, sg_stopUpload */

var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.cm) { com.medialab.sg.cm = {};}

if(typeof dojo != 'object'){ throw new Error("can't initialize...linkeditor requires dojo!"); }

dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.dijit"); // necessary?
//dojo.require("dojox.form.FileInput");  // experimental

com.medialab.sg.cm.linkEditor = function(managerIn, pageListPathIn){
	if (typeof($ml) == 'undefined' && typeof $ != 'undefined') {
		$ml = $;
	}
	var that = this;
	var manager = managerIn;
	var dijitContainer = null;
	var linkSelectorContentPane = null;
	var linkEditorContentPane = null;
	var linkEditors = {};
	var pageListPath = pageListPathIn;
	var pagesObject = null;
	var xjutils = new com.medialab.sg.cm.xjutils();
	var pagesXml = "";
	
	function sg_menu_editor_replace_char(str, c, r)
	{
		var arr = str.split(c);
		
		return(arr.join(r));
	}
	
	function sg_menu_editor_remove_chars(str, cArr)
	{
		var finalStr = str;
		var i=0;
		for(i=0; i < cArr.length; i++)
		{   
		  finalStr = sg_menu_editor_replace_char(finalStr, cArr[i], '');
		}
		return(finalStr);
	}	
	
	// sg_menu_editor_simple_munge()
	//
	// This is here to mimic the php/lisp filename munging that happens to uploaded files
	// Since php does also return the munged name it would be better to just use that
	// but it's very hard to reach it from the iframe that contains the "upload finished" 
	// javascript called by the php/lisp uploader. Having yet another spot for munging is bad for maintainablility if the munging code changes.
	
	function sg_menu_editor_simple_munge(filename)
	{
		var mungedFileName = sg_menu_editor_replace_char(filename, " ", "_");
	    mungedFileName = sg_menu_editor_remove_chars(mungedFileName, ['*', '&', '?', ';']);
		return(mungedFileName);
	}
	
	com.medialab.sg.cm.linkEditor.sg_stopUpload = function(success, newFilename){  // GLOBAL for generated iframe script to call
		  //alert("upload finished, result = " + success);
	      var result = '';
		  var fname = '';
	      if (success == 1){
	         result = '<span class="msg">The file was uploaded successfully!<\/span><br/><br/>';
			 fname = newFilename; //dojo.byId('Filedata').value;
	      }
	      else {
	         result = '<span class="emsg">There was an error during file upload!<\/span><br/><br/>';
			 alert('The upload failed.  The file may be too large or the server may have experienced an error.');
	      }
	      document.getElementById('sg_upload_indicator').style.visibility = 'hidden';
	      //document.getElementById('f1_upload_form').innerHTML = result + '<label>File: <input name="myfile" type="file" size="30" /><\/label><label><input type="submit" name="submitBtn" class="sbtn" value="Upload" /><\/label>';
	      document.getElementById('Filedata').style.visibility = 'visible';
		  com.medialab.sg.cm.linkEditor.updateLinkedFileDisplay(fname);     
	      return true;   
	};

	com.medialab.sg.cm.linkEditor.sg_menuEditor_startUpload = function () {
	  //window.console.log('starting upload');
	  com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();
      document.getElementById('sg_upload_indicator').style.visibility = 'visible';
      document.getElementById('Filedata').style.visibility = 'hidden';
		//window.console.log('file changed');
		//window.console.log("New file selected: " + window.document.getElementById("Filedata").value);
		
		window.document.getElementById('sgLinkeditorHiddenFilename').value = window.document.getElementById("Filedata").value;
		window.document.getElementById('SG_File_Callback').value = "window.parent.com.medialab.sg.cm.linkEditor.sg_stopUpload";
		document.getElementById('fileLinkEditor').submit();
	};

	function setUpLinkEditors() {
		// set up page editor
		linkEditors.PAGE = {};
		linkEditors.PAGE.content = 
		'Select an existing site page to link to:<br /><br />' +
//		'<form id="pageLinkEditor">' +
			'<label for="sg_linkeditor_pageSelectorMenu">Page: </label>' +
			'<select name="sg_linkeditor_pageSelectorMenu" id="sg_linkeditor_pageSelectorMenu" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor()">' +
			pagesXml + '</select>' +
			'<p><input id="pageNewWin" name="pageNewWin" dojoType="dijit.form.CheckBox" value="" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" />' +
			'<label for="pageNewWin">Open in New Window</label></p>';
//		'</form>';
		// set up url editor
		linkEditors.URL = {};
		linkEditors.URL.content = 
		'Type or paste in a url:<br /><br />' +
		'<form id="urlLinkEditor">' +
			'<label for="urlTextBox">URL: </label>' +
			'<input dojoType="dijit.form.TextBox" intermediateChanges="true" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" name="urlText" value="" placeHolder="Type in a relative or absolute url" trim="true" id="urlText" propercase="false" /><br />' +
			'<p><input id="urlNewWin" name="urlNewWin" dojoType="dijit.form.CheckBox" value="" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" />' +
			'<label for="urlNewWin">Open in New Window</label></p>' +
		'</form>';
		// set up file editor
		linkEditors.FILE = {};
		linkEditors.FILE.content = 
 		  '<form id="fileLinkEditor" target = "sg_upload_target" method="POST" enctype="multipart/form-data" action="'+ path_to_sgcms('filelinkcmd2.php') +'">'
		+   '<input id="sgLinkeditorHiddenFilename" type="hidden" value="nofilechosen" name="Filename" />'  // this is for compatibility with old link editor
		+	'<input id="SG_File_Callback" type="hidden" value="noCallbackSet" name="SG_File_Callback" />' 		// this is to tell the php what js to call on finish.  we'll set both in js above.
		+   '<div id="sg_filelink_current"><p style="color:red">No file currently linked.</p></div><br />'
		+	'<p style="padding-top:0px; padding-button:0px; margin-top:0px;margin-bottom:0px;">Click Browse to upload and link to a file:</p>'
		+   '<div style="visibility:hidden;padding-top:2px; margin-top:2px;" id="sg_upload_indicator"><img src="' + path_to_sgcms('menu_editor/progindicator.gif') + '" alt="uploading"/>Uploading</div>'
		+   '<input type="file" id="Filedata" name="Filedata" onchange="com.medialab.sg.cm.linkEditor.sg_menuEditor_startUpload();" style="padding-top:0px; margin-top:0px;" /><br /><br />'
		+   '<input id="fileNewWin" name="mycheck" dojoType="dijit.form.CheckBox" value="" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" />'
		+   '<label for="fileNewWin">Open in New Window</label>'
		+	'<br /><br /><p style="padding-top:0px; padding-button:0px; margin-top:0px;margin-bottom:0px;">SiteGrinder may change or remove some of the characters in the name of your file when it uploads based on web server file name rules.  The modified name will be used in the link for this menu item.</p>'
		+ '</form>'
		+ '<iframe name="sg_upload_target" id="upload_target" style="width:0px; height:0px;visibility:hidden;border:0px;"></iframe>';
		// set up literal editor
		linkEditors.LITERAL = {};
		linkEditors.LITERAL.content = 
		'Type in anything you want SiteGrinder to use as the link:<br /><br />' +
		'<form id="literalLinkEditor">' +
			'<label for="literalText">Link text: </label>' +
			'<input dojoType="dijit.form.TextBox" intermediateChanges="true" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" name="literalText" value="" placeHolder="Type in a url" trim="true" id="literalText" propercase="false" /><br /><br />' +
			'<p><input id="literalNewWin" name="literalNewWin" dojoType="dijit.form.CheckBox" value="" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" />' +
			'<label for="literalNewWin">Open in New Window</label>' +
		'</form>';
		// set up none editor
		linkEditors.NONE = {};
		linkEditors.NONE.content = 
		'<div>You can use the "none" link when you don\'t know what the link will be yet.  On your web pages clicking a button or menu item which has a "none" link won\'t do anything.</div>';
		// set up email editor
		linkEditors.EMAIL = {};
		linkEditors.EMAIL.content = 
		'Type in an email address:<br /><br />' +
		'<form id="emailLinkEditor">' +
			'<label for="emailText">Email address: </label>' +
			'<input intermediateChanges="true" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" name="emailText" value="" placeHolder="Address to send email to" dojoType="dijit.form.TextBox" trim="true" id="emailText" propercase="false" /><br /><br />' +
			'<label for="emailSubject">Subject: </label>' +
			'<input intermediateChanges="true" onChange="com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor();" name="emailSubject" value="" placeHolder="Subject line for email" dojoType="dijit.form.TextBox" trim="true" id="emailSubject" propercase="false" /><br /><br />' +
		'</form>';
		// set up noitem "editor"  (right now this should never happen)
		linkEditors.NOITEM = {};
		linkEditors.NOITEM.content = 
		'<div>No menu item is selected.  Please select an item from the list on the left to edit it\'s link information.</div>';
		linkEditors.UDF = {};
		linkEditors.UDF.content = 
		"<div>This item's link has not been selected.  <br /><br />This means no link type has been selected for it and clicking on it where it appears in your web pages will have no effect.  To choose a link type, select one of the radio buttons above and then enter your link settings.</div>";

		
		//linkEditorContentPane.setAttribute('content', linkEditors.page.content);
	}

	
	function digestPages(data, ioArgs) {
		pagesObject = xjutils.xmlToJson(data);
		pagesXml = "";
		var pageItems = pagesObject.items[0].children;
		pageItems.sort(function(a,b){var aa = a.name.toLowerCase(); var bb = b.name.toLowerCase(); if(aa<bb){return -1;} if(aa>bb){return 1;} return 0;});  // alphabetize page list
		for(var i = 0; i < pageItems.length; i++) {
			if (typeof pageItems[i] != 'undefined' && typeof pageItems[i].id != 'undefined' && typeof pageItems[i].name != 'undefined') {
				var pageName = pageItems[i].name;
				if(pageName.substr(pageName.length-5, 5) == '-page') { pageName = pageName.substr(0,pageName.length-5); }
				pagesXml += '<option value="' + pageItems[i].id + '">' + pageName + '</option>';
			}
			else {
				pagesXml += '<option value="'+ i +'">Page not found</option>';
			}
		}
		setUpLinkEditors();
	}
		
	function init() {
		// Set up container
		dijitContainer = new dijit.layout.BorderContainer({region:'center',style:'padding:2px;'});
//		dijitContainer.startup();
		linkSelectorContentPane = new dijit.layout.ContentPane({region:'top',style:'background-color: #ffffff;'});
		linkEditorContentPane = new dijit.layout.ContentPane({region:'center',style:'background-color: #ffffff;'});
		
		linkSelectorContentPane.placeAt(dijitContainer);
		linkEditorContentPane.placeAt(dijitContainer);
		// Set up link type selector
		// the ids below MUST match the link types used by SG, ie "URL" and "selectURL".  "url" and "selectUrl" won't work.
		linkSelectorContentPane.setAttribute('content',
		'Select the type of link:<br /><br /><form id="sgLinkTypeSelector">'+
		'<input dojoType="dijit.form.RadioButton" name="linkselector" id="selectPAGE" value="page" onClick="com.medialab.sg.cm.linkEditor.selectNewLinkType('+"'PAGE'"+');"/><label for="selectPage">Page</label>'+
		'<input dojoType="dijit.form.RadioButton" name="linkselector" id="selectURL" value="url" onClick="com.medialab.sg.cm.linkEditor.selectNewLinkType('+"'URL'"+');"/><label for="selectPage">Url</label>' +
		'<input dojoType="dijit.form.RadioButton" name="linkselector" id="selectFILE" value="file" onClick="com.medialab.sg.cm.linkEditor.selectNewLinkType('+"'FILE'"+');"/><label for="selectPage">File</label>'+
		'<input dojoType="dijit.form.RadioButton" name="linkselector" id="selectLITERAL" value="literal" onClick="com.medialab.sg.cm.linkEditor.selectNewLinkType('+"'LITERAL'"+');"/><label for="selectPage">Literal</label>'+
		'<input dojoType="dijit.form.RadioButton" name="linkselector" id="selectNONE" value="none" onClick="com.medialab.sg.cm.linkEditor.selectNewLinkType('+"'NONE'"+');"/><label for="selectPage">None</label>'+
		'<input dojoType="dijit.form.RadioButton" name="linkselector" id="selectEMAIL" value="email" onClick="com.medialab.sg.cm.linkEditor.selectNewLinkType('+"'EMAIL'"+');"/><label for="selectPage">Email</label></form>'
		);		
		//console.log (dojo.query("#selectPage").length); //onclick(function(){this.selectLinkType('page');});
		
		// Asynch retrieve page list xml
		
		dojo.xhr("Get",
		{
			url:pageListPath,
			preventCache: true,
			handleAs: "text",
			load: digestPages,
			error: function(e, ioArgs)
					{
						linkEditorContentPane.set('content',"Unable to pagelist xml. " + e);
					}			
		});
		
	}
	
	function getSelectedType() {
		if(dijit.byId('selectPAGE').checked) { return 'PAGE'; }
		if(dijit.byId('selectURL').checked) { return 'URL'; }
		if(dijit.byId('selectFILE').checked) { return 'FILE'; }
		if(dijit.byId('selectLITERAL').checked) { return 'LITERAL'; }
		if(dijit.byId('selectNONE').checked) { return 'NONE'; }
		if(dijit.byId('selectEMAIL').checked) { return 'EMAIL'; }
	}
	
	this.ensurePrefix = function(thingToCheck, prefixToCheckFor) {
		if(typeof thingToCheck == 'string' && typeof prefixToCheckFor == 'string') {
			if(thingToCheck.indexOf(prefixToCheckFor)===0) {
				return thingToCheck;
			}
			else {
				return prefixToCheckFor + thingToCheck;
			}
		}
	};	
	//
	// updateModelFromLinkEditor updates the data model based on the state of the currently selected item's
	// ui controls
	//
	
	com.medialab.sg.cm.linkEditor.updateModelFromLinkEditor = this.updateModelFromLinkEditor = function() {
		//console.log('updating model');
		var item = manager.getSelectedTreeItem();
		if(typeof item != 'object') { return; }
		item.linktype[0]=getSelectedType();
		switch(item.linktype[0]){
			case 'PAGE':
				//item.linkval[0] = dijit.byId('sg_linkeditor_pageSelectorMenu').get('value');  // not using dijit for this for the moment due to bug
				item.linkval[0] = dojo.byId('sg_linkeditor_pageSelectorMenu').value;
				if(dijit.byId('pageNewWin').get('value') == "on")
				{ item.newwin[0] = "true"; }
				else { item.newwin[0] = "false"; }
				break;
			case 'UDF':
				break;
			case 'URL':  // treat undefined as URL
				item.linkval[0]=dijit.byId('urlText').get('value');
				if(dijit.byId('urlNewWin').get('value') == "on")
				{ item.newwin[0] = "true"; }
				else { item.newwin[0] = "false"; }
				break;
			case 'FILE':
				//console.log('updating file link in model with ' + dojo.byId('Filedata').value); // dijit.byId is only for dijit controls and sadly this isn't one
				var fname = dojo.byId('Filedata').value;
				if (typeof fname == "string" && fname !== "") {
					item.linkval[0] = 'sg_userfiles/' + sg_menu_editor_simple_munge(fname);
				}
				if(dijit.byId('fileNewWin').get('value') == "on")
				{ item.newwin[0] = "true"; }
				else { item.newwin[0] = "false"; }
				break;

			case 'LITERAL':
				item.linkval[0]=dijit.byId('literalText').get('value');
				if(dijit.byId('literalNewWin').get('value') == "on")
				{ item.newwin[0] = "true"; }
				else { item.newwin[0] = "false"; }
				break;
			case 'EMAIL':
				var emailWithURI = that.ensurePrefix(dijit.byId('emailText').get('value'), 'mailto:');
				item.linkval[0] = emailWithURI;
				item.aux[0] = dijit.byId('emailSubject').get('value');
				break;
			default:
				break;
		}
					
	};
	

	
	this.removePrefix = function(thingToCheck, prefixToCheckFor){
		if (typeof thingToCheck == 'string' && typeof prefixToCheckFor == 'string') {
			if(thingToCheck.indexOf(prefixToCheckFor)===0) {
				var cleanedThing = thingToCheck.substring(prefixToCheckFor.length);	
                return cleanedThing;
			}
			else {
				return thingToCheck;
			}
		}
	};
	
	// show appropriate link controls changes the set of controls displayed for a given link type
	this.showAppropriateLinkControls = function(type) {
		var typeObject = linkEditors[type];
		if (typeof typeObject == 'object' && typeof typeObject.content == 'string') {
			linkEditorContentPane.setAttribute('content', typeObject.content);
		}
	};
	
	// selectLinkType calls showAppropriateLinkControls and updates the model for the new link type
	
	com.medialab.sg.cm.linkEditor.selectNewLinkType = 
	this.selectNewLinkType = function(type) {
		var item = manager.getSelectedTreeItem();
		if(typeof item == 'object') {
			item.linktype[0] = type;
		}
		that.showAppropriateLinkControls(type);
	};
	
	com.medialab.sg.cm.linkEditor.updateLinkedFileDisplay =
	this.updateLinkedFileDisplay = function(newFileName){
		if (typeof newFileName != 'string' || newFileName === "" || newFileName == "#") {
			dojo.byId('sg_filelink_current').innerHTML = '<p style="color:red;">No file currently linked.</p>';
		}
		else {
			dojo.byId('sg_filelink_current').innerHTML = '<p>Current file link: <strong>' + newFileName + '</strong></p>';
		}
	};
	
	
	this.getContainer = function () {
		return dijitContainer;
	};
	
	// updateLinkSettingsPanel simply applies the values from "item" to the
	// appropriate controls for a given link type.
	
	this.updateLinkSettingsPanel = function(item){
		switch(item.linktype[0]){
			case 'UDF':
				break;
			case 'NONE':
				this.setLinkTypeRadioSelection(item.linktype[0],true);
				break;
			case 'PAGE':
				var pageId = item.linkval[0];				
				//dijit.byId('sg_linkeditor_pageSelectorMenu').set('value',item.linkval[0]);
				dojo.byId('sg_linkeditor_pageSelectorMenu').value = item.linkval[0];
				var pageCheckValue = false;
				if(item.newwin[0] != 'false') { pageCheckValue = "on"; }
				dijit.byId('pageNewWin').set('value',pageCheckValue);
				this.setLinkTypeRadioSelection(item.linktype[0],true);
				break;
			case 'URL':
				dijit.byId('urlText').set('value',item.linkval[0]);
				var urlCheckValue = false;
				if(item.newwin[0] != 'false') { urlCheckValue = "on"; }
				dijit.byId('urlNewWin').set('value',urlCheckValue);
				this.setLinkTypeRadioSelection(item.linktype[0],true);
				break;
			case 'FILE':
				this.updateLinkedFileDisplay(item.linkval[0]);
				var fileCheckValue = false;
				if(item.newwin[0] != 'false') { fileCheckValue = "on"; }
				dijit.byId('fileNewWin').set('value',fileCheckValue);
				this.setLinkTypeRadioSelection(item.linktype[0],true);
				break;
			case 'LITERAL':
				dijit.byId('literalText').set('value',item.linkval[0]);
				var literalCheckValue = false;
				if(item.newwin[0] != 'false') { literalCheckValue = "on"; }
				dijit.byId('literalNewWin').set('value',literalCheckValue);
				this.setLinkTypeRadioSelection(item.linktype[0],true);
				break;
			case 'EMAIL':
				dijit.byId('emailText').set('value',this.removePrefix(item.linkval[0],'mailto:'));
				dijit.byId('emailSubject').set('value',item.aux[0]);
				this.setLinkTypeRadioSelection(item.linktype[0],true);
				break;
			default:
				break;
		}
	};
	
	this.setLinkTypeRadioSelection = function(typeName, setting) {
		typeName = this.ensurePrefix(typeName,'select');
		var box = dijit.byId(typeName);
		if (box && typeof box.set == 'function') {
			box.set('value', setting);
		}
	};
	
	this.deselectAllLinkTypeRadios = function() {
		this.setLinkTypeRadioSelection('selectPAGE', false);
		this.setLinkTypeRadioSelection('selectURL', false);
		this.setLinkTypeRadioSelection('selectNONE', false);
		this.setLinkTypeRadioSelection('selectEMAIL', false);
		this.setLinkTypeRadioSelection('selectLITERAL', false);
		this.setLinkTypeRadioSelection('selectFILE', false);
	};
	
	// updateSettings is resonsible for changing the UI to reflect the options for the
	// paramters given in the "newSettings" object.
	// 
	// It does the following:
	//
	// 1. updates the radio group to reflect the type of link
	//
	// 2. calls selectLinkType() to draw the UI controls appropriate for the link type
	//
	// 3. calls updateLinkSettingsPanel() to actually set the settings values of the UI controls
	//
	
	this.updateSettings = function(newSettings) {
		if(typeof newSettings != 'object') { com.medialab.sg.cm.linkEditor.selectLinkType('NOITEM'); return;}
		// select appropriate radio
		var newType = newSettings.linktype[0];
		if (typeof newType == 'string') {
			// 1. update the radio group to reflect the type of link
			if (newType == "UDF") {
				this.deselectAllLinkTypeRadios();
			}
			else {
				var checkId = 'select' + newSettings.linktype[0];
				this.setLinkTypeRadioSelection(checkId, true);
			}
			
			// 2. Draw the UI controls appropriate for the link type
			that.showAppropriateLinkControls(newSettings.linktype[0]);
			
			// 3. Set the settings values of the UI controls
			this.updateLinkSettingsPanel(newSettings);
		} 
		else {
			alert('ERROR: bad settings sent to link_editor.updateSettings().  newSettings.linktype: ' + typeof newSettings.linktype[0]);
		}
	};
	
	this.destroy = function() {
		dijitContainer.destroyRecursive();
	};
	
	init();
};