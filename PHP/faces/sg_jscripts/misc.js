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

function array_contains(arr, v)
{
  var len = arr.length;
  for (var i = 0; i < len; i++)
  {
      if(arr[i]===v){return true;}
  }
  return false;
}

function sub_array_contains(arr, v)
{
  var len = arr.length;
  for (var i = 0; i < len; i++)
  {
      if(arr[i][0]===v){return true;}
  }
  return false;
}