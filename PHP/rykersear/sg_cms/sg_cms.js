// border and selection
var currentSGEditSelection = null;
var currentSGEditHilite = null
function doborder(theid)
{
   var thediv = theid ? document.getElementById(theid) : null;
   
   if(currentSGEditSelection)
   {
     removeClass(currentSGEditSelection, 'sg_cms_selected');
   }
   if(thediv)
   { 
     removeClass(thediv, 'sg_cms_hilite'); 
     addClass(thediv, 'sg_cms_selected'); 
   }
   currentSGEditSelection = thediv;
}

function dohilite(theid)
{
   var thediv = theid ? document.getElementById(theid) : null;
   
   if(currentSGEditHilite)
   {
     removeClass(currentSGEditHilite, 'sg_cms_hilite');
   }
   if(thediv && thediv != currentSGEditSelection){ addClass(thediv, 'sg_cms_hilite'); }
   currentSGEditHilite = thediv;
}

function addClass(target, classValue)
{
   var pattern = new RegExp("(^| )" + classValue + "( |$)");
   if(!pattern.test(target.className))
   {
   	if(target.className == "")
   	{
   		target.className = classValue;
   	}
   	else
   	{
   		target.className += " " + classValue;
   	}
   }
}

function removeClass(target, classValue)
{
 var removedClass = target.className;
 var pattern = new RegExp("(^| )" + classValue + "( |$)");
 removedClass = removedClass.replace(pattern, "$1");
 removedClass = removedClass.replace(/ $/, "");
 
 target.className = removedClass;
}

//edit handling
function edit_entry(fpath, divid, ctype) //main entry point
  {
    window.suspend_impresto = true;
    if(ctype == 'menu')
    {
      show_menu_ui(fpath, divid, ctype);
    }
    else if(ctype == 'gallery')
    {
    	 show_gallery_ui(fpath, divid, ctype);
    }
    else if(ctype == 'title')
    {
      show_title_ui(fpath, ctype);
    }
    else if(ctype == 'data feed')
    {
		show_datafeed_ui(fpath, divid, ctype);
    }
    else if(ctype == 'menutree')
    {
       show_menutree_ui(fpath, divid);
    }
    else
    {
      
    //some browsers (Safari, Opera, FF < 2.0.0.12) resolve the final path to be relative to this .js file. Others (IE, FF >= 2.0.0.13) use a path relative to the .html page.  
  	var request = newXMLHttpRequest();
	request.onreadystatechange = function(){ openCMSUI(request, fpath, divid, ctype, true); };
	request.open("GET", path_to_site(fpath)  + '?r=' +  Math.floor(Math.random() * 10000)); //relative to sg_cms/sg_cms.js
	request.send(null);
    }
  }
function end_edit()
{
	var pos = document.location.search.length;
	if(pos)
	{
		var url = document.location.href.substring(0, document.location.href.length-pos);
		document.location = url;
	}
}
  function saveEntry(fpath, divid)
  {
  	var inst = tinyMCE.getInstanceById('sg_tinymce_ta');
  	var request = newXMLHttpRequest();
	request.open("POST", path_to_sgcms("sg_saveentry.php") , true);
	request.onreadystatechange = function(){ saveCompleted(request, divid, /* tinyMCE 2: inst.getHTML() */  inst.getContent(), fpath );};
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	request.send("fpath=" +encodeURIComponent(fpath)+ "&mpath=" +encodeURIComponent(path_from_sgcms())+ "&ppath=" +encodeURIComponent(document.location)+ "&entry=" + encodeURIComponent(/*tinyMCE 2 inst.getHTML()*/ inst.getContent() ));
  }
  function saveCompleted(request, divid, newhtml, fpath)
  {
  	if(request.readyState ==4)
	{
			if(request.status == 200 || request.status == 304)
			{
				var elm = document.getElementById(divid);
				if(elm){ elm.innerHTML = newhtml; }
				
				var anticache = newXMLHttpRequest();
				var r = Math.floor(Math.random() * 10000);
				anticache.open("GET", '../' + fpath + '?r=' + r);
				anticache.send(null);
				
				
				closeCMSUI();
			}
	}
  }
function openCMSUI(request, fpath, divid, ctype, goOn)
  {
  	if(request.readyState ==4)
	{
		if(request.status == 200 || request.status == 304)
		{
		    var btn = document.getElementById('saveEntryBtn');
		    btn.onclick = function(){ saveEntry(fpath, divid); };
		    showoverlay();
		    var elm = document.getElementById('sg_tinymce_div');
		    elm.style.visibility = 'visible';
		    var v = (typeof(self.pageYOffset) == 'number') ? self.pageYOffset : 0;
		    elm.style.marginTop = (100 + v) + 'px';
		    var inst = tinyMCE.getInstanceById('sg_tinymce_ta');

		    //tinyMCE 2
		    //inst.setHTML(request.responseText);

		    //tinyMCE 3
		    inst.setContent(request.responseText);

		}
		else if(request.status == 404 && goOn)
		{
		  var request = newXMLHttpRequest();
		  request.onreadystatechange = function(){ openCMSUI(request, fpath, divid, ctype, false); };
		  request.open("GET", '../' +  fpath + '?r=' +  Math.floor(Math.random() * 10000)); //relative to page
		  request.send(null);
		}
	}
  }
  function closeMenuUI(r)
  {
     window.suspend_impresto = false;
     var menudiv = document.getElementById('sg_menu_editor_div');
     menudiv.style.visibility = 'hidden';
     var overlay = document.getElementById('sg_overlay');
     overlay.style.visibility = 'hidden';
     if(r)
     {     
           var search = '?reload=true&edit=1';
           document.location.search = search; 
     	  var hash    = document.location.hash ? document.location.hash : '';
           var newhref = document.location.protocol + '//' + document.location.host + document.location.pathname + hash + '?reload=true&edit=1';
          
           document.location.href = newhref;
     }
  }
  
  function closeMenuTreeUI(r)
  {
     window.suspend_impresto = false;
     var menudiv = document.getElementById('sg_menutree_editor_div');
     menudiv.style.visibility = 'hidden';
     var overlay = document.getElementById('sg_overlay');
     overlay.style.visibility = 'hidden';
     if(r)
     {     
           var search = '?reload=true&edit=1';
           document.location.search = search; 
     	  var hash    = document.location.hash ? document.location.hash : '';
           var newhref = document.location.protocol + '//' + document.location.host + document.location.pathname + hash + '?reload=true&edit=1';
          
           document.location.href = newhref;
     }
  }


  function closeCMSUI()
  {
     window.suspend_impresto = false;
  	var btn = document.getElementById('saveEntryBtn');
	btn.onclick = function(){ ; };
  	var elm = document.getElementById('sg_tinymce_div');
	elm.style.visibility = 'hidden';
        var overlay = document.getElementById('sg_overlay');
        overlay.style.visibility = 'hidden';
  }
	 
  function newXMLHttpRequest()
  {
  	var req = null;
	try{ req = new XMLHttpRequest();}
	catch(error){ try{ req = new ActiveXObject("Microsoft.XMLHTTP");}
	              catch(er){req=null;} 
				}
	return(req);
  }
  function showoverlay()
  {
    var overlay = document.getElementById('sg_overlay');
    overlay.style.visibility = 'visible';
    var dim = getPageDim();
    if(dim[0] && dim[1]){ overlay.style.width = dim[0]+'px'; overlay.style.height = dim[1]+'px'; }
  }

function show_menu_ui(fpath, divid, ctype)
{
   var menudiv = document.getElementById('sg_menu_editor_div');
   var v = (typeof(self.pageYOffset) == 'number') ? self.pageYOffset : 0;
   menudiv.innerHTML = '<div style="margin:' + (100 + v) + 'px auto 0 auto; width:500px; height:500px; border:10px solid black;"><!--[if IE]><object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="500" height="500"> <param name="quality" value="best" /><param name="movie" value="' + path_to_sgcms('menueditor.lzx.swf')  + '" /><param name="menu" value="false" /><param name="FlashVars" value="pagelisturl=sg_pageslist.php&menuurl=' + fpath   + '&submitcmdurl=sg_saveentry.php&filelinkcmdurl=filelinkcmd.php&localarg=' + sg_local_arg() + '" /></object><![endif]--><!--[if !IE]> <--> <object type="application/x-shockwave-flash" data="' + path_to_sgcms('menueditor.lzx.swf') + '" width="500" height="500"> <param name="quality" value="best" /><param name="menu" value="false" /><param name="FlashVars" value="pagelisturl=sg_pageslist.php&menuurl=' +fpath + '&submitcmdurl=sg_saveentry.php&filelinkcmdurl=filelinkcmd.php&localarg=' + sg_local_arg() + '" /></object><!-- <![endif]--></div>';
			    showoverlay();
			    menudiv.style.visibility = 'visible';
}

function show_menutree_ui(fpath, divid)
{
    var menudiv = document.getElementById('sg_menutree_editor_div');
    var v = (typeof(self.pageYOffset) == 'number') ? self.pageYOffset : 0;
    var menutreeEditor = new com.medialab.sg.cm.menuEditor('sg_menutree_editor_inner_div', fpath, path_to_sgcms('sg_pageslist.php'));
    showoverlay();
    menudiv.style.visibility = 'visible';
    return(menutreeEditor);
}

function show_datafeed_ui(fpath, divid, ctype)
{
   var menudiv = document.getElementById('sg_menu_editor_div');
   var v = (typeof(self.pageYOffset) == 'number') ? self.pageYOffset : 0;
   menudiv.innerHTML = '<div style="margin:' + (100 + v) + 'px auto 0 auto; width:400px; height:140px; border:10px solid black;"><!--[if IE]><object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="400" height="140"> <param name="quality" value="best" /><param name="movie" value="' + path_to_sgcms('feed_editor.lzx.swf')  + '" /><param name="menu" value="false" /><param name="FlashVars" value="feedurl=' + fpath   + '&submitcmdurl=sg_saveentry.php&localarg=' + sg_local_arg() + '" /></object><![endif]--><!--[if !IE]> <--> <object type="application/x-shockwave-flash" data="' + path_to_sgcms('feed_editor.lzx.swf') + '" width="400" height="140"> <param name="quality" value="best" /><param name="menu" value="false" /><param name="FlashVars" value="feedurl=' +fpath + '&submitcmdurl=sg_saveentry.php&localarg=' + sg_local_arg() + '" /></object><!-- <![endif]--></div>';
			    showoverlay();
			    menudiv.style.visibility = 'visible';
}

function show_title_ui(fpath, ctype)
{
   var titlediv = document.getElementById('sg_menu_editor_div');
   var v = (typeof(self.pageYOffset) == 'number') ? self.pageYOffset : 0;
   titlediv.innerHTML = '<div style="margin:' + (100 + v) + 'px auto 0 auto; width:400px; height:140px; border:10px solid black;"><!--[if IE]><object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="400" height="140"> <param name="quality" value="best" /><param name="movie" value="' + path_to_sgcms('title_editor.lzx.swf')  + '" /><param name="menu" value="false" /><param name="FlashVars" value="titleurl=' + fpath   + '&submitcmdurl=sg_saveentry.php&localarg=' + sg_local_arg() + '" /></object><![endif]--><!--[if !IE]> <--> <object type="application/x-shockwave-flash" data="' + path_to_sgcms('title_editor.lzx.swf') + '" width="400" height="140"> <param name="quality" value="best" /><param name="menu" value="false" /><param name="FlashVars" value="titleurl=' +fpath + '&submitcmdurl=sg_saveentry.php&localarg=' + sg_local_arg() + '" /></object><!-- <![endif]--></div>';
			    showoverlay();
			    titlediv.style.visibility = 'visible';
}

function show_gallery_ui(fpath, divid, ctype)
{
  var gallerydiv = document.getElementById('sg_menu_editor_div');
  var v = (typeof(self.pageYOffset) == 'number') ? self.pageYOffset : 0;
  gallerydiv.innerHTML = '<div style="margin:' + (20 + v) + 'px auto 0 auto; width:830px; height:685px; border:10px solid black;"><!--[if IE]><object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" width="830" height="685"> <param name="quality" value="best" /><param name="movie" value="' + path_to_sgcms('gallery_cms.lzx.swf')  + '" /><param name="menu" value="false" /><param name="FlashVars" value="gxmlurl=' + fpath   + '&submitcmdurl=sg_saveentry.php&filelinkcmdurl=sg_cms/sg_galleryupload.php&localarg=' + sg_local_arg() + '" /></object><![endif]--><!--[if !IE]> <--> <object type="application/x-shockwave-flash" data="' + path_to_sgcms('gallery_cms.lzx.swf') + '" width="830" height="685"> <param name="quality" value="best" /><param name="menu" value="false" /><param name="FlashVars" value="gxmlurl=' +fpath + '&submitcmdurl=sg_saveentry.php&filelinkcmdurl=sg_galleryupload.php&localarg=' + sg_local_arg() + '" /></object><!-- <![endif]--></div>';
  showoverlay();
  gallerydiv.style.visibility = 'visible';
  //submitcmdurl=http://127.0.0.1:1702/site/eve/sg_cms/sg_saveentry.php&filelinkcmdurl=http://127.0.0.1:1702/site/eve/sg_image-gallery_content/gallery_images/sg_galleryupload.php&lzr=swf8&debug=false&gxmlurl=sg_image-gallery_content/gallery_images/alternate_5.gxml
}

  function getPageDim()
  {
  	var body =  document.getElementsByTagName("body")[0];
  	var pdim = [0,0];
  	if(document.documentElement && document.documentElement.scrollWidth)
  	{
  	   pdim[0] = document.documentElement.scrollWidth;
  	   pdim[1] = document.documentElement.scrollHeight;
     }
     if(body)
     {
        pdim[0] = Math.max(pdim[0], body.offsetWidth, body.scrollWidth);
        pdim[1] = Math.max(pdim[1], body.offsetHeight, body.scrollHeight);
     }
     return(pdim);
  }






