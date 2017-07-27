function input_focus(obj)
{
   removeClass(obj, 'hinted');
   if(obj.value == obj.alt)
   {
     obj.value='';
   }
}
	
function input_blur(obj)
{
   if((obj.value == obj.alt)||(obj.value == ''))
   {
     addClass(obj, 'hinted');
     obj.value = obj.alt;
   }
}