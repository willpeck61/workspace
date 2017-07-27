/**
 *    Copyright (c) 2011, As Is Software, dba Media Lab Inc.
 *    http://www.medialab.com
 */

/*global dojo, dojox */

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

dojo.require("dojox.xml.DomParser");

com.medialab.sg.cm.xjutils = function(){
		this.myCounter = (function (){var c=0; function nextCounter() {c++; return c+"";} return nextCounter;})();
			
		//
		//
		//   INPUT PROCESSING
		//
		//   BEGIN Functions to convert xml from server into tree json data
		//
			function isXjBranch(node) {
			   if(typeof(node) != 'object' || typeof(node.childNodes) != 'object') { return false; }
			   var kidz = node.childNodes;
			   for(var i = 0; i < kidz.length; i++) {
			        if(kidz[i].localName == 'ul') { return(kidz[i]); }
			   }
			   return false;
			}
			
			function getAnchorOrItemAttributeFromXmlJsonNode(attr,node) {
				if(typeof node.localName == 'string' && (node.localName == 'a' || node.localName == 'item')) { return node.getAttribute(attr);  }
				if(typeof node.childNodes != 'object' || typeof node.childNodes[0] != 'object') { return false; }
				if(node.childNodes[0].localName == 'a' || node.childNodes[0] == 'item') {
					return node.childNodes[0].getAttribute(attr);
				}
				if(typeof node.childNodes[0].childNodes != 'object' || typeof node.childNodes[0].childNodes[0] != 'object') { return false; }
				if(node.childNodes[0].childNodes[0].localName == 'a' || node.childNodes[0].childNodes[0].localName == 'item') {
					return node.childNodes[0].childNodes[0].getAttribute(attr);
				}
				
				return false;
			}
			
			function getTextFromXmlJsonNode(node) {
				if(typeof node.childNodes != 'object') { return false; }
				if(typeof node.childNodes[0] == 'undefined' || typeof node.childNodes[0].nodeName == 'undefined') { return false; }
				if(node.childNodes[0].nodeName == '#text') {
					return node.childNodes[0].nodeValue;
				}
				if(typeof node.childNodes[0].childNodes != 'object') { return false; }
				if(node.childNodes[0].childNodes[0].nodeName == '#text') {
					return node.childNodes[0].childNodes[0].nodeValue;
				}
				return false;
			}
			
			function addPropertyIfExists(obj,prop,val) {
//				console.log('addproperty '+prop+'val type ' + typeof val);
				if(typeof val != 'undefined' && val !== null) {
					obj[prop] = val;
				}
			}
			
			function makeTreeJsonChildFromXmlJson(jdata, id) {
				var jChild = {};
				jChild.id = id;
				var n = getTextFromXmlJsonNode(jdata);
				if (n) {
					jChild.name = n;
				}
				
				// for menus
				addPropertyIfExists(jChild,'href',getAnchorOrItemAttributeFromXmlJsonNode('href',jdata));
				addPropertyIfExists(jChild,'linktype',getAnchorOrItemAttributeFromXmlJsonNode('linktype',jdata));
				addPropertyIfExists(jChild,'linkval',getAnchorOrItemAttributeFromXmlJsonNode('linkval',jdata));
				addPropertyIfExists(jChild,'aux',getAnchorOrItemAttributeFromXmlJsonNode('aux',jdata));
				addPropertyIfExists(jChild,'newwin',getAnchorOrItemAttributeFromXmlJsonNode('newwin',jdata));
				
				// for page list
				addPropertyIfExists(jChild,'name',getAnchorOrItemAttributeFromXmlJsonNode('name',jdata));
				addPropertyIfExists(jChild,'id',getAnchorOrItemAttributeFromXmlJsonNode('id',jdata));

				return jChild;
			}	

			function addTreeJsonChildrenFromXmlJson(xjdata, j, getId) {
				var kidz = xjdata.childNodes;
				var nextDeeper = null;
				for(var i = 0; i < kidz.length; i++) {	
					nextDeeper = isXjBranch(kidz[i]);	
					if(nextDeeper) // we've got a submenu with either ul or li contents
					{ 
						j[i] = makeTreeJsonChildFromXmlJson(kidz[i],getId());
						j[i].children = [];
						addAttributes(j[i].children, xjdata);
						addTreeJsonChildrenFromXmlJson(nextDeeper,j[i].children, getId);
					}
					else {  // we're a lone button
						j[i] = makeTreeJsonChildFromXmlJson(kidz[i],getId());
					}
				}
				return j;
			}
			
			function addAttributes(objToReceiveAttrs, xmlObjThatHasAttrs) {
				if(typeof xmlObjThatHasAttrs.attributes == 'object') {
					for(var curAttr = 0; curAttr < xmlObjThatHasAttrs.attributes.length; curAttr++) {
						if(typeof xmlObjThatHasAttrs.attributes[curAttr].localName == 'string' && typeof xmlObjThatHasAttrs.attributes[0].nodeValue != 'undefined') {
							var attName = xmlObjThatHasAttrs.attributes[curAttr].localName;
							objToReceiveAttrs[attName] = xmlObjThatHasAttrs.attributes[0].nodeValue;
						}
	                }
				}
			}
			
			
			
			this.xmlToJson = function(xml) { 
				if (typeof xml == 'string') {						// Not sure why, but DomParser fails if there are spaces between xml tags, 
					var cleanXml = xml.replace( />\s+</g, "><" ); 	// so delete them and all other whitespace for good measure.  What if '>  <' appears in the attributes or contents?  Can it ever?
					
					if(cleanXml.indexOf('<?xml') === 0) {  			// get rid of '<?xml version="1.0"?>' which will sometimes appear
						var endXmlTag = cleanXml.indexOf('>') + 1;
						if (endXmlTag > 1) {
							cleanXml = cleanXml.substring(endXmlTag,cleanXml.length);
						}
					}
					var xjdata = dojox.xml.DomParser.parse(cleanXml);
					var rootItem = {
						name: 'root',
						id: '0',
						href: 'none',
						children: []
					};
					var j = {
						label: "name",
						identifier: "id",
						items: [rootItem]
					};
					addTreeJsonChildrenFromXmlJson(xjdata.childNodes[0], j.items[0].children, this.myCounter);
					return j;
				}
			};
			
			
		//			
		//   END Functions to convert xml from server to tree json data
		//
		//
			
//==================================================
			
		//
		//
		//   OUTPUT PROCESSING
		//
		//   BEGIN Functions to convert tree json data to xml to send back to the server
		//
			function makeXmlChildFromJson(jdata) {
				if(jdata.name === "") { jdata.name = "Untitled"; }
				var aux = "";
				if(typeof jdata.aux[0]  == 'string' && jdata.aux[0].length > 0) { aux = convert_to_html_entities(jdata.aux); }
				var childXml = 
					'<a linktype="' + jdata.linktype + '" ' +
					'linkval="' + encodeURI(jdata.linkval) + '" ' +
					'aux="' + aux + '" ' +
					'newwin="'  + jdata.newwin + '"' +
					'>' + convert_to_html_entities(jdata.name) + '</a>';
				return childXml;
			}

			function addChildrenFromJson(jdata, x) {
				var kidz = jdata.children;
				if (x === "") {
					x += '<ul class="menutreetop">';
				} else {
					x += '<ul class="children">';
				}
				for(var i = 0; i < kidz.length; i++) {					
					if(kidz[i].children) // we've got a submenu
					{ 
						x+='<li class="page_item">';
						x+=makeXmlChildFromJson(kidz[i]);
						x=addChildrenFromJson(kidz[i],x);
						x+='</li>';
					}
					else {  // we're a lone button
						x+='<li class="page_item">';
						x+=makeXmlChildFromJson(kidz[i]);
						x+='</li>';
					}
				}
				x+='</ul>';
				return x;
			}
			
			this.jsonToXml = function(jdata) {
				var x = "";
				x=addChildrenFromJson(jdata.root,x);
				//console.log(x);
				return x;
			};

		//			
		//   END Functions to convert tree json data to xml to send back to the server
 		//
		//

};




function convert_to_html_entities(str){
	if(typeof str == 'object') { str = str[0]; }  // dojo likes to wrap its model data in arrays, so we gots to get it coh-rect.
	if (typeof str == 'string') {
		var chars = ["&", "<", ">", '"'];// "'", "©", "Û", "®", "ž", "Ü", "Ÿ", "Ý", "$", "Þ", "%", "¡", "ß", "¢", "à", "£", "á", "À", "¤", "â", "Á", "¥", "ã", "Â", "¦", "ä", "Ã", "§", "å", "Ä", "¨", "æ", "Å", "©", "ç", "Æ", "ª", "è", "Ç", "«", "é", "È", "¬", "ê", "É", "­", "ë", "Ê", "®", "ì", "Ë", "¯", "í", "Ì", "°", "î", "Í", "±", "ï", "Î", "²", "ð", "Ï", "³", "ñ", "Ð", "´", "ò", "Ñ", "µ", "ó", "Õ", "¶", "ô", "Ö", "·", "õ", "Ø", "¸", "ö", "Ù", "¹", "÷", "Ú", "º", "ø", "Û", "»", "ù", "Ü", "@", "¼", "ú", "Ý", "½", "û", "Þ", "€", "¾", "ü", "ß", "¿", "ý", "à", "‚", "À", "þ", "á", "ƒ", "Á", "ÿ", "å", "„", "Â", "æ", "…", "Ã", "ç", "†", "Ä", "è", "‡", "Å", "é", "ˆ", "Æ", "ê", "‰", "Ç", "ë", "Š", "È", "ì", "‹", "É", "í", "Œ", "Ê", "î", "Ë", "ï", "Ž", "Ì", "ð", "Í", "ñ", "Î", "ò", "‘", "Ï", "ó", "’", "Ð", "ô", "“", "Ñ", "õ", "”", "Ò", "ö", "•", "Ó", "ø", "–", "Ô", "ù", "—", "Õ", "ú", "˜", "Ö", "û", "™", "×", "ý", "š", "Ø", "þ", "›", "Ù", "ÿ", "œ", "Ú"];
		var codes = ["&amp;", "&lt;", "&gt;", "&quot;"];// "&#039;", "&copy;", "&#219;", "&reg;", "&#158;", "&#220;", "&#159;", "&#221;", "&#36;", "&#222;", "&#37;", "&#161;", "&#223;", "&#162;", "&#224;", "&#163;", "&#225;", "&Agrave;", "&#164;", "&#226;", "&Aacute;", "&#165;", "&#227;", "&Acirc;", "&#166;", "&#228;", "&Atilde;", "&#167;", "&#229;", "&Auml;", "&#168;", "&#230;", "&Aring;", "&#169;", "&#231;", "&AElig;", "&#170;", "&#232;", "&Ccedil;", "&#171;", "&#233;", "&Egrave;", "&#172;", "&#234;", "&Eacute;", "&#173;", "&#235;", "&Ecirc;", "&#174;", "&#236;", "&Euml;", "&#175;", "&#237;", "&Igrave;", "&#176;", "&#238;", "&Iacute;", "&#177;", "&#239;", "&Icirc;", "&#178;", "&#240;", "&Iuml;", "&#179;", "&#241;", "&ETH;", "&#180;", "&#242;", "&Ntilde;", "&#181;", "&#243;", "&Otilde;", "&#182;", "&#244;", "&Ouml;", "&#183;", "&#245;", "&Oslash;", "&#184;", "&#246;", "&Ugrave;", "&#185;", "&#247;", "&Uacute;", "&#186;", "&#248;", "&Ucirc;", "&#187;", "&#249;", "&Uuml;", "&#64;", "&#188;", "&#250;", "&Yacute;", "&#189;", "&#251;", "&THORN;", "&#128;", "&#190;", "&#252", "&szlig;", "&#191;", "&#253;", "&agrave;", "&#130;", "&#192;", "&#254;", "&aacute;", "&#131;", "&#193;", "&#255;", "&aring;", "&#132;", "&#194;", "&aelig;", "&#133;", "&#195;", "&ccedil;", "&#134;", "&#196;", "&egrave;", "&#135;", "&#197;", "&eacute;", "&#136;", "&#198;", "&ecirc;", "&#137;", "&#199;", "&euml;", "&#138;", "&#200;", "&igrave;", "&#139;", "&#201;", "&iacute;", "&#140;", "&#202;", "&icirc;", "&#203;", "&iuml;", "&#142;", "&#204;", "&eth;", "&#205;", "&ntilde;", "&#206;", "&ograve;", "&#145;", "&#207;", "&oacute;", "&#146;", "&#208;", "&ocirc;", "&#147;", "&#209;", "&otilde;", "&#148;", "&#210;", "&ouml;", "&#149;", "&#211;", "&oslash;", "&#150;", "&#212;", "&ugrave;", "&#151;", "&#213;", "&uacute;", "&#152;", "&#214;", "&ucirc;", "&#153;", "&#215;", "&yacute;", "&#154;", "&#216;", "&thorn;", "&#155;", "&#217;", "&yuml;", "&#156;", "&#218;"];
		for (var x = 0; x < chars.length; x++) {
			var c = chars[x];
			var r = codes[x];
			var ss = str.split(c);
			str=ss.join(r);
		}
	}
	return str;
 }


/*
 * 1. For each node add 
 */