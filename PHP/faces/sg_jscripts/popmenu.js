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

function ClearMenuArray(level, array)
{
    while(array.length > level)
    {
        var strct = array.pop();
        var cname = strct[0];
        SG_EndByClass(cname);
        SG_StartByClass(cname+"hide");
        restore_nm_class(strct[1], false);
    }
}

function SG_ClearMenu(level)
{
  if(window.sg_popmenuarray)
  {
    ClearMenuArray(level, window.sg_popmenuarray);
  }
  if(level==0){ SG_StartByClass("hidey"); }
}




function SG_SetMenu(menuClassName, obj, level)
{
  if(! window.sg_popmenuarray)
  {
    window.sg_popmenuarray = new Array();
    if(!window.sg_popmenuarray.push){window.sg_popmenuarray.push=p2h_push;}
    if(!window.sg_popmenuarray.pop){window.sg_popmenuarray.pop=p2h_pop;}
  }
  SG_ClearMenu(level);
  window.sg_popmenuarray.push([menuClassName, obj]);
}


function SG_ToggleMenu(menuClassName, level, obj)
{
  if( window.sg_popmenuarray && sub_array_contains(window.sg_popmenuarray, menuClassName))
  {
    
    removeClass(obj, 'cmh'); removeClass(obj, 'pmh');
    SG_ClearMenu(level);
  }
  else
  {
    removeClass(obj, 'nmh'); removeClass(obj, 'pmh');
    addClass(obj, 'cmh');
    SG_SetMenu(menuClassName, obj, level);
    SG_StartByClass(menuClassName);
    SG_EndByClass(menuClassName+"hide");
    if(level==0){SG_EndByClass("hidey");}
  }
}

function SG_CloseClickStay(group_name)
{
    var group = window.sg_clickstay_hashtable[group_name];
    if(group)
    {
        removeClass(group.obj, 'cmh');
        SG_EndByClass(group.cname);
        for(i=0; i< group.others.length; i++)
        {
            var subgroup = group.others[i];
            SG_EndByClass(subgroup.cname);
            removeClass(subgroup.obj);
            delete window.sg_clickstay_hashtable[subgroup.group_name];
        }
        delete window.sg_clickstay_hashtable[group_name];
    }
    return group;
}


function SG_ToggleClickStay(menuClassName, obj, my_group, other_group_array)
{
    if(!window.sg_clickstay_hashtable){ window.sg_clickstay_hashtable = {}; }
    var i;
    var group = SG_CloseClickStay(my_group);
    if(group && group.cname === menuClassName){ return; /* hiding only*/ }
    //showing new
    removeClass(obj, 'nmh'); addClass(obj, 'cmh');
    SG_StartByClass(menuClassName);
    window.sg_clickstay_hashtable[my_group] = {cname:menuClassName, obj:obj, others:[]};

    if(other_group_array)
    {
        for(i=0; i < other_group_array.length; i++)
        {
            var group_name = other_group_array[i];
            var group = window.sg_clickstay_hashtable[group_name];
            if(group)
            {
                group.others.push({cname:menuClassName, obj:obj, group_name:my_group});
            }
        }
    }
    
}


function P2H_Menu(menuClassName, delay, level, obj)
{
  P2H_StopClock();
  window.p2h_delay=delay;
  P2H_SetMenu(menuClassName, obj, level);
  removeClass(obj, 'nmh');
  addClass(obj, 'pmh');
  SG_StartByClass(menuClassName);
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
    ClearMenuArray(level, window.p2h_popmenuarray);
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
