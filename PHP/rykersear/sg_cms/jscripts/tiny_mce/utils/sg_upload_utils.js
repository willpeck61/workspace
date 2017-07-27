/*global tinyMCEPopup */

// Utility Functions 
function sg_utils_endsWidth (str, lookfor) {
  return str.length >= lookfor.length && str.substr(str.length - lookfor.length) == lookfor;
}

// The next two routines are here because at the moment the SG-supplied functions for this are broken and
// because they only work from site pages, not from, for example, tinymce scopes like the link or image editor html files in their iframes
// but I have to be able to build paths from there (or absolute) to call the fileupload php file

function sg_uploader_path_to_site() {
	var p1 = window.location.toString();		// these two must differ at the site root for this to work, so must be called from subdir or iframe context
	var p2 = window.top.location.toString();
	var shared = getSharedPortionOfPaths(p1,p2);
	if( !sg_utils_endsWidth(shared,'/') ) {
		shared += '/';
	}
	return shared;
	
}

function sg_uploader_path_to_uploads() {
	var site = sg_uploader_path_to_site();
	if( !sg_utils_endsWidth(site, "sg_userfiles/") ) { site += 'sg_userfiles/';}	
	return site;
}

function dropPathLevel(s,d,n) { var r=s; for(var i=0;i<n;i++){ r = r.substring(0,r.lastIndexOf(d)); } return r; }

function sg_uploader_path_to_cms() {
	var site = sg_uploader_path_to_site();
	if( !sg_utils_endsWidth(site, "sg_cms/") ) { site += 'sg_cms/';}	
	return site;
}

/*function getPathToSgUploads(f){
	var p = tinyMCEPopup.getWin().location.toString();
	p = dropPathLevel(p, "/", 1);
    p+="/sg_uploads/";
	if(typeof f == "string") { p += f; }
	return p;
}*/

function getSharedPortionOfPaths(path1,path2) {
	for(var curChar = 0; curChar<= path1.length; curChar++) {
		if(path1[curChar] != path2[curChar]) {
			path1 = path1.substring(0,curChar);
			path2 = path2.substring(0,curChar);
			// the ending is nicely on a path end for both
			if(path1[path1.length-1] == '/' && path2[path2.length-1] == '/') {
				return path1;
			}			
			// ok, back up to the most recent path end
			return path1.substr(0,path1.lastIndexOf('/'));
		}
	}
	return path1;
}

function sg_Uploader_findElement(id) {
	var tryMe = window.document.getElementById(id);
	if(tryMe) { return tryMe; }
	tryMe = window.parent.document.getElementById(id);
	return tryMe;
}

function sg_Uploader_setStyle(id,styleName,styleVal) {
	var el = sg_Uploader_findElement(id);
	if(typeof el == "object") {
		if(typeof el.style == 'object') {
			var styleArray = el.style;
			if(typeof styleArray[styleName] != 'undefined') {
				styleArray[styleName] = styleVal;
			}
		}
	}
}

// Functions used by multiple other files, including image.htm and link.htm

function sg_showFileFinderUI(uploadUiId,url) { 
   /* if(typeof tinyMCEPopup.getWin().com == 'undefined') { tinyMCEPopup.getWin().com = {}; };
    if(typeof tinyMCEPopup.getWin().com.medialab == 'undefined') { tinyMCEPopup.getWin().com.medialab = {}; };
    if(typeof tinyMCEPopup.getWin().com.medialab.sg == 'undefined') { tinyMCEPopup.getWin().com.medialab.sg  = {}; };
    if(typeof tinyMCEPopup.getWin().com.medialab.sg.cm == 'undefined') { tinyMCEPopup.getWin().com.medialab.sg.cm = {}; };
    if(typeof tinyMCEPopup.getWin().com.medialab.sg.cm.linkEditor == 'undefined') { tinyMCEPopup.getWin().com.medialab.sg.cm.linkEditor = {}; };
	tinyMCEPopup.getWin().com.medialab.sg.cm.linkEditor.sg_stopUpload = sg_img_endImageUpload;*/
	
	sg_Uploader_findElement(uploadUiId).style.display = 'block';
	//document.getElementById(id).value=url; 
	//ImageDialog.updateImageData(this); 
	return; 
}

// Functions used by image.htm

function sg_img_startImageUpload() {
	sg_Uploader_findElement('sg_upload_indicator').style.visibility = 'visible';
	sg_Uploader_findElement('Filedata').style.visibility = 'hidden';		
	sg_Uploader_findElement('sgLinkeditorHiddenFilename').value = sg_Uploader_findElement("Filedata").value;
	sg_Uploader_findElement('SG_File_Callback').value = 'window.parent.sg_img_endImageUpload';
	var actionPath = sg_uploader_path_to_cms();
	sg_Uploader_findElement('fileLinkEditor').action = actionPath + 'filelinkcmd2.php';
	sg_Uploader_findElement('fileLinkEditor').submit();
}

function sg_img_endImageUpload(success, newFileName) {
	// We'll be in the hidden iframe context here
	//alert('sg_img_endImageUpload called with ' + success);
	
	sg_Uploader_setStyle('sg_upload_indicator','visibility','hidden');
	sg_Uploader_setStyle('Filedata','visibility', 'visible');
	sg_Uploader_setStyle('sguploaderform','display', 'none');
	if (success) {
		var url = sg_uploader_path_to_uploads() + newFileName;
		sg_Uploader_findElement('prev').innerHTML = '<img id="previewImg" src="' + '../../../../../' + "sg_userfiles/" + newFileName + '" onload="ImageDialog.updateImageData(this, 1);" border="0">';
		sg_Uploader_findElement('src').value=url; 
	}
}

// functions used by link.htm 

function sg_link_startFileUpload() {
	sg_Uploader_findElement('sg_upload_indicator').style.visibility = 'visible';
	sg_Uploader_findElement('Filedata').style.visibility = 'hidden';		
	sg_Uploader_findElement('sgLinkeditorHiddenFilename').value = sg_Uploader_findElement("Filedata").value;
	sg_Uploader_findElement('SG_File_Callback').value = 'window.parent.sg_link_endFileUpload';
	var actionPath = sg_uploader_path_to_cms();
	sg_Uploader_findElement('fileLinkEditor').action = actionPath + 'filelinkcmd2.php';
	sg_Uploader_findElement('fileLinkEditor').submit();
}

function sg_link_endFileUpload(success, newFileName) {
	// We'll be in the hidden iframe context here
	//alert('sg_img_endImageUpload called with ' + success);
	sg_Uploader_setStyle('sg_upload_indicator','visibility','hidden');
	sg_Uploader_setStyle('Filedata','visibility', 'visible');
	sg_Uploader_setStyle('sguploaderform','display', 'none');
	if (success) {
		var url = sg_uploader_path_to_uploads() + newFileName;
		sg_Uploader_findElement('href').value=url;
	}
}