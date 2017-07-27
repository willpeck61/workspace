//array push/pop - IE5Mac
function p2h_push( v )
{
  this[this.length]=v;
}

function p2h_pop()
{
   if(this.length > 0)
   {
  var v = this[this.length -1];
  this.length--;
  return( v );
   }
}



function restore_nm_class(obj)
{ 	
  removeClass(obj, 'cmh'); removeClass(obj, 'pmh');
}





function P2H_SetVisibilityByC(className, show)
{
  var arg = show ? 'block' : 'none';
  $ml("."+className).css('display',arg);  
}


function SG_StartByClass(className)
{
  $ml("."+className).each( function(){ if(this.startf){  this.startf(); }else{ this.style.display='block'; }} );
}

function SG_EndByClass(className)
{
  $ml("."+className).each( function(){ if(this.endf){  this.endf(); }else{ this.style.display='none'; }} );
}



function SG_ToggleMenu(menuClassName, level, obj)
{
  if( window.p2h_popmenuarray && sub_array_contains(window.p2h_popmenuarray, menuClassName))
  {
    
    removeClass(obj, 'cmh'); removeClass(obj, 'pmh');
    P2H_ClearMenu(level);
  }
  else
  {
    removeClass(obj, 'nmh'); removeClass(obj, 'pmh');
    addClass(obj, 'cmh');
    P2H_SetMenu(menuClassName, obj, level);
    SG_StartByClass(menuClassName)
    SG_EndByClass(menuClassName+"hide");
    if(level==0){SG_EndByClass("hidey");}
  }
}


function P2H_Menu(menuClassName, delay, level, obj)
{
  P2H_StopClock();
  window.p2h_delay=delay;
  P2H_SetMenu(menuClassName, obj, level);
  removeClass(obj, 'nmh');
  addClass(obj, 'pmh');
  SG_StartByClass(menuClassName)
  SG_EndByClass(menuClassName+"hide");
  if(level==0){SG_EndByClass("hidey");}
  
}

function P2H_MenuItem(level)
{
  P2H_StopClock();
  P2H_ClearMenu(level);
}

function P2H_ClearMenu(level)
{
  if(window.p2h_popmenuarray)
  {
    while(window.p2h_popmenuarray.length > level)
    {
      var strct = p2h_popmenuarray.pop();
      var cname = strct[0];
      SG_EndByClass(cname);
      SG_StartByClass(cname+"hide");
      restore_nm_class(strct[1], false);     
    }
  }
  if(level==0){ SG_StartByClass("hidey"); }
}

function P2H_SetMenu(menuClassName, obj, level)
{
  if(! window.p2h_popmenuarray)
  { 
    window.p2h_popmenuarray = new Array(); 
    if(!window.p2h_popmenuarray.push){window.p2h_popmenuarray.push=p2h_push;}
    if(!window.p2h_popmenuarray.pop){window.p2h_popmenuarray.pop=p2h_pop;}
  }
  P2H_ClearMenu(level);
  window.p2h_popmenuarray.push([menuClassName, obj]);
}

function P2H_StopClock()
{
  if(window.p2h_timeoutid)
  {
    clearTimeout(window.p2h_timeoutid);
    window.p2h_timeoutid = null;
  }
}

function P2H_StartClock()
{
  if(p2h_delay == 0){ P2H_CloseMenu(); return;}
  P2H_StopClock();
  window.p2h_timeoutid = setTimeout('P2H_CloseMenu()', window.p2h_delay);
}

function P2H_CloseMenu()
{
  P2H_StopClock();
  P2H_ClearMenu(0);
}
