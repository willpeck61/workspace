/**
 *    Copyright (c) 2011, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */
/*global dojo, dijit, $ml, $, path_to_sgcms, path_to_site, closeMenuTreeUI */

//
// immediate to do:
// - file link button
// - clean up layout, add title bar
// - scrollbar if tree gets too wide or tall
// - make font size and other layout stuff independent of body mark-up
// - get rid of console logs, accidental globals
// - Customize tree icons
//
// final integration to do:
// - what will contain the editor on the page? draggable window?
//
// bugs:
// - the dijit page selector menu in the link editor stopped working so I had to switch to standard HTML <select>
// - drag and drop sometimes not restarted after inline editing an item name. long pause after editing or clicking another item seems to fix
// - tricky reproduce bug where a drag/drop fails to complete and leaves things in an ugly state.  probably a dijit bug
// - tree drag and drop fails to properly reset selection hilighting (this is a dijit bug, probably not easily fixable)
// - keep a close eye on dnd after editing a node.  I had to add code to the editable tree to start and stop dnd so it doesn't inerfere with editing, but it may screw up dnd after the edit.
// - unnecessary horiz scroll bars in opera
// - tree editor a little too narrow for deep trees
// - selection highlight never wider than container, looks odd for deep trees when scrolled to view.  Probably the container hierarchy needs to be tweaked more, with maybe no width set for the tree and the parent set to overflow scroll.
//
// little things:
// - add "Rename" button to New/Delete panel?
// - make ids more unique (prepend "sg_cms_menu_editor__" to avoid conflicts)
// - sequester sg_cms calls in com.medialab object?
// - verify everything that needs to be is getting destroyed
// - investigate why the dojo domparser fail in our xmlutils.xmlToJson() if there are spaces between the tags?
// - better behavior when user sets menu name to ""?  Kludge right now is to pass "Untitled" back at end of edit session for such items.  Probably should change name right after edit to "Untitled" or to what the name was before, but not sure how.
// - better way to deal with global itemContext in sg_menu_tree_editor.js
// - make the url fields larger
// - give a max width to the pages popup menu (it gets weird if a page name is too long)
//


var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.cm) { com.medialab.sg.cm = {};}

dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.BorderContainer");

com.medialab.sg.cm.menuEditor = function(divIdIn, menuDataPathIn, pageListPathIn, saveCommandIn){

	var that = this;
	var myDivId = 'sgmenueditorworkdiv';
	var selectedItemId = null;
	var menuTreeEditor = null;
	var linkEditor = null;
	var menuDataPathOrig = menuDataPathIn;
	var menuDataPathRel = path_to_site(menuDataPathIn);
	var pageListPath = pageListPathIn;
	var dijitContainer = null;
	var xjutils = new com.medialab.sg.cm.xjutils();
	var buttonsContentPane = null;
	var saveCommandUrl = saveCommandIn ? path_to_sgcms(saveCommandIn) :  path_to_sgcms("sg_saveentry.php");
	
	this.init = function() {
		dojo.html.set(dojo.byId(divIdIn),'<div class="claro" id="'+ myDivId +'"></div>');  // add another inner div because dijit.destroy also kills the div
		//dojo.query("#sgmenueditorworkdiv").addClass("claro");  // add dojo skin class
		
		// set up container
 		dijitContainer = new dijit.layout.BorderContainer({style:'border: 1px solid black;width:500px;height:500px;padding:2px;'}, myDivId);
		
		// Set up containees
		linkEditor = new com.medialab.sg.cm.linkEditor(this, pageListPath);
		dijitContainer.addChild(linkEditor.getContainer());

		menuTreeEditor = new com.medialab.sg.cm.menuTreeEditor(this, menuDataPathRel);
		dijitContainer.addChild(menuTreeEditor.getContainer());
		
		buttonsContentPane = new dijit.layout.ContentPane({region:'bottom',style:'padding-left:370px;background-color: #ffffff;'});		
		buttonsContentPane.set('content',
		'<button dojoType="dijit.form.Button" onClick="com.medialab.sg.cm.menuEditor.finish(true);">Okay</button>' +
		'<button dojoType="dijit.form.Button" onClick="com.medialab.sg.cm.menuEditor.finish(false);">Cancel</button>');
		dijitContainer.addChild(buttonsContentPane);
		
		dijitContainer.startup();  // IE hates this if startup() is called for the included compoenents as well since this also calls those.
	};
	
	//
	// getRelativePathFromUrl()
	//    strips the path info to the xml to only the filename so it can be passed to saveentry.php which won't take the full path 
	//
	function getRelativePathFromUrl(fullUrl){
		var myLoc = window.location.href;  // is this cross browser?
		var relPath = "";
		var curChar = 0;
		for(; curChar<= fullUrl.length; curChar++) {
			if(myLoc[curChar] != fullUrl[curChar]) {
				break;
			}
		}
		
		for(; curChar < fullUrl.length; curChar++) {
			if(fullUrl[curChar] == "?") {
				break;
			}
			else {
				relPath += fullUrl[curChar];
			}
		}
		return relPath;
	}

	function returnData() {
		var data = menuTreeEditor.getModel();
		var xmlToReturn = xjutils.jsonToXml(data);
		//console.log(xmlToReturn);
		var myContent = {fpath:menuDataPathOrig, // saveentry command wants original path, not relative path
						 entry:xmlToReturn};

		var xhrArgs = { url: saveCommandUrl,
                content: myContent,
                handleAs: "text" ,
                handle: function(data,args){
                			destroy();
							if (typeof closeMenuTreeUI == 'function') {
								closeMenuTreeUI(true);
							}
						}
		};

		dojo.xhrPost(xhrArgs);
	}
	
	function destroy() {
		dijitContainer.removeChild(menuTreeEditor.getContainer());
		menuTreeEditor.destroy();
		
		dijitContainer.removeChild(linkEditor.getContainer());
		linkEditor.destroy();
		
		dijitContainer.removeChild(buttonsContentPane);
		buttonsContentPane.destroy();
		
		dijitContainer.destroyRecursive();
		
	}
	
	com.medialab.sg.cm.menuEditor.finish = 
	this.finish = function(okay) {
		if (okay) {
			returnData();
		}
		else {
			// clean up and destroy
			destroy();
			if (typeof closeMenuTreeUI == 'function') {
				closeMenuTreeUI(false);
			}
		}
	};
	
	
	this.getSelectedTreeItem = function() {
		return menuTreeEditor.getSelectedItem();
	};
	
	this.newMenuItemSelected = function(newItem) {
		linkEditor.updateSettings(newItem);
	};
	
	this.menuTreeLoaded = function() {			
		// Update containees
		var curItemSettings = menuTreeEditor.getSelectedItem();
		linkEditor.updateSettings(curItemSettings);
	};
		
	this.init();
};
