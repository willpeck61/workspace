/**
 *    Copyright (c) 2011, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

/*global dojo, dijit, console, $ml, $, InlineEditableTree */

// Tree stuff:
//
//  - to get the selected item use tree.get('selectedItem')
//



var com;
if(!com) { com = {}; } else if(typeof(com) != "object") { throw new Error("can't initialize...com is not an object."); }
if(!com.medialab) { com.medialab = {}; } else if(typeof(com.medialab) != "object") { throw new Error("can't initialize...com.medialab is not an object."); }
if(!com.medialab.sg) { com.medialab.sg = {}; }
if (!com.medialab.sg.cm) { com.medialab.sg.cm = {};}

if(typeof dojo != 'object'){ throw new Error("can't initialize...linkeditor requires dojo!"); }
dojo.require("dijit.dijit"); // necessary?

//dojo.require("dijit.form.CheckBox");
//dojo.require("dijit.form.TextBox");
//dojo.require("dijit.form.Select");
//dojo.require("dijit.form.Button");

dojo.require("dijit.Tree");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojox.xml.DomParser");
dojo.require("dijit.InlineEditBox");
dojo.require("dijit.tree.dndSource");

com.medialab.sg.cm.menuTreeEditor = function(managerIn, dataPathIn){
	if (typeof($ml) == 'undefined' && typeof $ != 'undefined') {
		$ml = $;
	}
	var that = this;
	var manager = managerIn;
	var dijitContainer = null;
//	var treeContainer = null; // currently unused
//	var treeContentPane = null; // currently unused
	var buttonsContentPane = null;
	var linkEditors = {};
	var dataPath = dataPathIn;
	var xjutils = new com.medialab.sg.cm.xjutils();
	var myTree  = null;
	var myStore = null;
	var myModel = null;
	var johnWayne = null;


		//treeContentPane = new dijit.layout.ContentPane({region:'center',style:'background-color: #acb386;'});
		//treeContentPane.setAttribute('content','Hi there!');
		//treeContentPane.placeAt(dijitContainer);
		
		// Set up tree debug test data:
		//var xml = '<ul><li><a href="(link 41)">Home</a></li><li><a href="(link 51)">Products</a><ul><li><a href="(link 42)">PhotoCaster</a></li><li><a href="(link 43)">Alphaania</a></li><li><a href="(link 44)">Effector Sets</a><ul><li><a href="(link 45)">Effector Set I</a></li><li><a href="(link 46)">Effector Set II</a></li></ul></li><li><a href="(link 47)">PSD2FLA</a></li><li><a href="(link 48)">WellRounded</a></li><li><a href="(link 49)">Web Meddler</a></li></ul></li><li><a href="(link 50)">Forums</a></li></ul>';
		//var xml = '<ul><li><a linktype="PAGE" linkval="3" aux="" newwin="false">My Page Item</a></li><li><a linktype="URL" linkval="http://testurl.com" aux="" newwin="false">My Url Item</a></li><li><a linktype="FILE" linkval="my media/paris hilton - the stars are blind.mp3" aux="" newwin="false">My File Item</a></li><li><a linktype="LITERAL" linkval="some_literal_stuff" aux="" newwin="false">My Literal Item</a></li><li><a linktype="NONE" linkval="#" aux="" newwin="false">My None Item</a></li><li><a linktype="EMAIL" linkval="mailto:test@menueditor.tom" aux="" newwin="false">My Email Item</a></li></ul>';

	this.init = function(){
		// Set up container
		dijitContainer = new dijit.layout.BorderContainer({
			region: 'left',
			style: 'width:190px;padding:2px;'
		});
//		treeContainer = new dijit.layout.BorderContainer({region:'center', style:'margin:0px;border:0px;padding:0px;overflow:scroll;'});
		//treeContentPane = new dijit.layout.ContentPane({region:'center',style:'background-color: #ffffff;overflow:scroll;'});
		buttonsContentPane = new dijit.layout.ContentPane({region:'bottom',style:'background-color: #ffffff;'});
		buttonsContentPane.set('content',
		'<button dojoType="dijit.form.Button" id="newItemButton">New Item</button>' +
		'<button dojoType="dijit.form.Button" id="deleteItemButton">Delete Item</button>');
		
		
//		dijitContainer.startup();
		dojo.xhr("Get", {
			url: dataPath+"",
			preventCache:true,
			handleAs: "text",
			load: this.gotXml,
			error: function(e, ioArgs){
				buttonsContentPane.set('content',"Unable to load menu xml. " + e);
			}
		});
	};
	
	this.getStore = function() {
		return myStore;
	};
	
	this.getModel = function() {
		return myModel;
	};
	
	this.getContainer = function () {
		return dijitContainer;
	};
	
	this.useTheseSettingsForSelectedItem = function() {
		
	};
	
	this.getSelectedItem = function() {
		return myTree.get('selectedItem');
	};
	
	
	this.destroy = function() {
		dijitContainer.destroyRecursive();
	};
	
	function findItemWithContext(searchIn, context){
		for (var i = 0; i < searchIn.length; i++) {
			context.siblingIndex = i;
			if (i > 0) {
				context.previousSibling = searchIn[i - 1];
			}
			if (i < (searchIn.length - 1)) {
				context.nextSibling = searchIn[i + 1];
			}
			else {
				context.nextSibling = null;
			}
			if (searchIn[i].id == context.id) {
				context.item = searchIn[i];
				return true;
			}
			if (typeof searchIn[i].children == 'object') {
				context.previousSibling = searchIn[i];
				context.parent = searchIn[i];
				var found = findItemWithContext(searchIn[i].children, context);
				if(found) { return true; }
			}
		}
		return false;
	}	
	
	this.getItemContext = function(itemIn){
		var item = itemIn;
		var that=this;
		var startSearchArray = myModel.root.children;
		var itemContext = {
			parent:myModel.root,
			previousSibling: null,
			id: item.id
		};
		findItemWithContext(startSearchArray, itemContext);
		return itemContext;
	};

// to get the root, use model.getRoot(sucessFunction(root), errorFunction())
		  
	      function tree_node_has_been_selected(item){
	            //myTree.curSelected = item;
				//console.log('tree selected item ' + item);
				manager.newMenuItemSelected(item);
	        }
	
			
			this.deleteSelectedItem = function() {
				// Make sure this isn't the last item in the tree - or have the delete button track this through enable/disable
				//console.log('delete item');
				// get selected item
				var selectedItem = this.getSelectedItem();
				if(selectedItem !== null) {
					this.deleteItem(selectedItem);
				}
			};
			
			this.deleteItem = function(item) {
				// Get item context
				var itemContext = this.getItemContext(item);
				if (itemContext.nextSibling || itemContext.previousSibling) {
					// delete item
					myStore.deleteItem(item);
					
					// determine item to select
					var newSelection = null;
					
					// - if there is item at same index get that item
					if (typeof itemContext.nextSibling == 'object' && itemContext.nextSibling) {
						newSelection = itemContext.nextSibling;
					}
					else 
						if (typeof itemContext.previousSibling == 'object' && itemContext.previousSibling) {
							newSelection = itemContext.previousSibling;
						}
					
					// select new item
					manager.newMenuItemSelected(newSelection);
					myTree.set('selectedItem', newSelection);
				}
			};
			
			function getParent(){
				
			}
			
			this.onNew = function(newItem) {
				//console.log('on new called');
				myModel.pasteItem(newItem, itemContext.parent, itemContext.parent, false, itemContext.siblingIndex + 1);
			};
			
			
            this.addItemAfterSelected = function(name){
				// get current selected item
				var selectedItem = myTree.get('selectedItem');
				
				// get context
				itemContext = this.getItemContext(selectedItem);  // MESSY GLOBAL!!
				
				// get parent item
				var parentOfNewItem = itemContext.parent;
				
				// get index of current selection
				var newItemIndex = itemContext.siblingIndex + 1;
				
				// make the new item
				// (should eventually call the manager to get this from the link editor so we don't have to know about it here)
				var newItem = { name:"New Item", id:xjutils.myCounter(), linktype:"PAGE", linkval:"1", newwin:"false", aux:""};
				
				// insert the new item
				myModel.newItem(newItem, parentOfNewItem);
				
			};
			/*
			 // Old proof of concept stuff
  
  				i = myStore.fetchItemByIdentity({
					identity: 1,
					onItem: function(i){
						myStore.newItem({
							id: 91,
							name: 'New Item!',
							link: "hi"
						}, {
							parent: i,
							attribute: 'children'
						});
					}
				}); 
			 */
			
			this.problemAddingItem = function(err) {
				//console.log('ERROR adding new item');
			};
			
            
	this.gotXml = function(data, ioArgs) {
		var digestedData = xjutils.xmlToJson(data);
			
        //Create a simple Store to hold the JSON test data.
        myStore = new dojo.data.ItemFileWriteStore({  // global for now for debugging.  add var when able
            //data: treedata
            data: digestedData
        });
        myModel = new dijit.tree.TreeStoreModel({
            store: myStore,
            query: {
                name: '*'
            },
            rootId: '999'
			,onNewItem:that.onNew
        });
		// create the tree
        myTree = new InlineEditableTree({
            id: "myTree",
            model: myModel,
            showRoot: false,
			dndController: "dijit.tree.dndSource",
			betweenThreshold:5,
			style:'border:0px;overflow:scroll;',
            childrenAttrs: ["children"]
        });
//		gtree = myTree;
		myTree.startup();
		dojo.connect(myTree, "onClick", this, tree_node_has_been_selected);
		var deferred = myTree.set('path',['0','1']);  // this selects item with path id0->id1.  Strange, I know.
		deferred.addCallback(that.initialTreeSelectionDone);
		myTree.region='center'; 
		myTree.placeAt(dijitContainer);
		//myTree.placeAt(treeContainer);
		//treeContainer.placeAt(dijitContainer);
		buttonsContentPane.placeAt(dijitContainer);
		
		dojo.connect(dojo.byId('newItemButton'), "onclick", that, "addItemAfterSelected");
		dojo.connect(dojo.byId('deleteItemButton'), "onclick", that, "deleteSelectedItem");
	};
	
	this.initialTreeSelectionDone = function() {
		manager.menuTreeLoaded();
	};
		
	 dojo.declare("InlineEditableTree", dijit.Tree, {  // custom tree for inline editing
	        editing: false,
	        postCreate: function(){
	                this.inherited("postCreate", arguments);
	                this.connect(this.domNode, "ondblclick", this._onDblClick);
	        },
	        _onKeyPress: function( e) {
	                if (this.tree.editing) {
						return;
					} // we are editing so we should ignore all keys
	                this.inherited(arguments);
	        },
	        _onClick: function( e) {
	                if (this.tree.editing) {
						//this.editor._onClick(e);
					} // attempt to grab clicks
					else {
						this.inherited(arguments);
					}
	        },
			enableDnd: function() {
					//console.log('restarting DND ' + this.dndController);
					this.dndController = "dijit.tree.dndSource";
					this.dndController = dojo.getObject(this.dndController);
					var params={};
					for(var i=0; i<this.dndParams.length;i++){
						if(this[this.dndParams[i]]){
							params[this.dndParams[i]] = this[this.dndParams[i]];
						}
					}
					this.dndController = new this.dndController(this, params);
			},
			disableDnd: function() {
					//console.log('ending DND ' + this.dndController);
					if(this.dndController && !dojo.isString(this.dndController)){
						this.dndController.destroy();
					}
					dojo.removeClass(this.domNode, "dojoDndContainer"); 
			},
	        _onDblClick: function( e){
	                var domElement = e.target;
	                var nodeWidget = dijit.getEnclosingWidget(domElement); 
	                if(!nodeWidget || !nodeWidget.isTreeNode){
	                        return;
	                }
	                this.editing = true; // turn off keys for the tree
	                
	                //experiment 
					this.disableDnd();
					//experiment
					
	                // first we put a span in to attach the editor to. This avoids errors
	                var labelNode = nodeWidget.labelNode;
	                var editSpan = document.createElement('span');
	                editSpan.innerHTML = labelNode.innerHTML;
	                labelNode.innerHTML = "";
	                labelNode.appendChild(editSpan);
	                this.editor = new dijit.InlineEditBox({
	                                        node: nodeWidget,
	                                        tree: this,
	                                        model: this.model,
	                                        autoSave: true,
											onCancel:function(val) { 
													//console.log('cancel');
	                                                this.tree.editing = false;
													this.tree.enableDnd();
													var saveInnerHTML = editSpan.innerHTML;
													labelNode.removeChild(editSpan);
													labelNode.innerHTML = saveInnerHTML;
													this.editor = null;
											},
	                                        onChange: function(val){
													//console.log('change');
													//val = convert_to_html_entities(val);
	                                                this.model.store.setValue(this.node.item,'name', [val]);
	                                                this.tree.editing = false;
													this.tree.enableDnd();
													//var saveInnerHTML = editSpan.innerHTML;
													//labelNode.removeChild(editSpan);
													//labelNode.innerHTML = saveInnerHTML;
													this.editor = null;
	                                        },
	                                        width: "100px"
	                                        }, editSpan);
	                this.editor.startup();
	                this.editor.edit();
	        }
	});	


		this.init();	
/*	
	treeSearcher = function(treeIn) {
		var myTree = treeIn;
		var foundCallback = null;
		var itemContext = {};
		var item = null;
		var root = null;
		
		function init(){
			myTree.model.getRoot(this.foundRoot);
		}
		
		this.getItemContext = function(itemIn,foundCallbackIn) {
			item = itemIn;
			foundCallback = foundCallbackIn;
			itemContext.root = root;
			var startSearchArray = root.children;
			findItemWithContext(startSearchArray);
		}
		
		function findItemWithContext(searchIn) {
			for(var i = 0; i<searchIn.length; i++) {
				if(typeof searchIn[i].children == 'object') {
					
				}
			}
		}
		
		foundRoot = function(rootFound) {
			root = rootFound;
		}
		
		this.getRoot = function() { return root; }
	}*/
	
};


