//tinyMCE
		  tinyMCE.init({
			theme : "advanced",
			mode: "exact",
			elements: "sg_tinymce_ta",
			//skin : "cirkuit",
              
			language: "en",
			button_tile_map: true,
			gecko_spellcheck: true,
			external_link_list_url: path_to_sgcms('sg_linkslist.php?pts=' + encodeURIComponent(path_to_site())),
			editor_selector: "mce-editor",
			plugins : "pdw,spellchecker,safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,media,searchreplace,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras", 

			theme_advanced_buttons1 : "formatselect,fontselect,fontsizeselect,forecolor,backcolor,|,bold,italic,underline,strikethrough,sup,sub,|,removeformat,|,fullscreen", 
 
			theme_advanced_buttons2 : "bullist,numlist,|,justifyleft,justifycenter,justifyright,justifyfull,|, outdent,indent,|,iespell,|,link,unlink,anchor,|,advhr,charmap,|,image,media,|,search,replace,|,|,pdw_toggle",
 
			theme_advanced_buttons3 : "tablecontrols,|,styleprops,code,pastetext,pasteword,|,visualaid", 
			theme_advanced_buttons4 : "cite,abbr,acronym,del,ins,|,nonbreaking,help",
			pdw_toggle_on : 1,
			pdw_toggle_toolbars : "3,4",
			
			content_css:path_to_sgcms("tinymce-styles.css"),
			        
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_path_location : "bottom",
			theme_advanced_resizing : true,
			extended_valid_elements : "a[name|href|rel|target|title|onclick|class|style],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name|style],hr[class|width|size|noshade|style],font[face|size|color|style],span[class|align|style],iframe[align|frameborder|height|longdesc|marginheight|marginwidth|name|scrolling|src|width|class|allowtransparency|style]"
			
			
	/*  // Drop lists for link/image/media/template dialogs
		external_image_list_url : "lists/image_list.js",
		media_external_list_url : "lists/media_list.js",
		
			*/
				}); 