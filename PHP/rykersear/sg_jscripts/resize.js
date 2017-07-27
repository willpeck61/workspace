
function handleOverflowX(minw)
{
  var windowWidth = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.clientWidth;
  
  var top = document.body;
  if(windowWidth < minw)
  {
    
    top.style.overflowX = 'visible';
  }
  else
  {
    top.style.overflowX = 'hidden';
  }

}
function sizeSomething(pheight, fspace, id)
{
 var obj = document.getElementById(id);
 if(obj)
 {
  var contentFooter = document.getElementById('clear_footer');
  var contentBottom = contentFooter ? contentFooter.offsetTop : 0;
  var windowBottom = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.clientHeight;
  var totalHeightNow = contentBottom + fspace;
	  		
  if(totalHeightNow < windowBottom || totalHeightNow > pheight) //>
  {
    var h = obj.clientHeight; 
	  			
    obj.style.height = Math.max(0, h + (windowBottom - totalHeightNow)) + 'px';
  }
 }
}
