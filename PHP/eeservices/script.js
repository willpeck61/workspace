// JavaScript Document
$(window).load(function(){
$('article').not('.item0').hide();
$('nav a').click(function(){
	var chkitem = $(this).attr('id');
	$('article').not('.'+chkitem).hide();
	$('.'+chkitem).fadeIn();
});
	
$("input[name='retrieve']").focus( function() {
	var formopt = ($(this).attr('list'));
	$.ajax({ 
		type : 'post',
		url : "process.php", 
		data : { "retrieve" : "", "tbl" : formopt },
		dataType : "html", 
		success : function( data ){
			$('#'+formopt).html( data )
		}
	});
});

$("input[name='retrieve']").change( function() {
	var thisvalue = $(this).val();
	var getdata = $("option[value='"+thisvalue+"']").data("value");
	var formsrc = $(this).attr('list');
	window.num = $("option[value='"+thisvalue+"']").data("value");
	$.ajax({ 
		type : 'post',
		url : "process.php", 
		data : { "tbl" : formsrc, "selection" : getdata },
		dataType : "html", 
		success : function( data ){
			$('footer').after("<span class='"+formsrc+"'>"+data+"</span>");
			if( $("#id").val() > 0 ){
				
			} else {
				$(this).closest('form').append("<input type='hidden' id='id' name='id' val='' />");
			}
			$("input[required='']").each( function () {
				$(this).parent().children("span").html("&#10004;").css("color","#6C3");
			});
			var CustomerTitle = $("#CustomerTitle").val();
			var CustomerFirstName = $("#CustomerFirstName").val();
			var CustomerLastName = $("#CustomerLastName").val();
			var VehicleMake = $("#VehicleMake").val();
			var VehicleModel = $("#VehicleModel").val();
			var VehicleType = $("#VehicleType").val();
			var VehicleEngineType = $("#VehicleEngineType").val();
			$("#dataname").html(CustomerTitle+" "+CustomerFirstName+" "+CustomerLastName);
			$("#dataveh").html(VehicleMake+" "+VehicleModel+" "+VehicleType);
			$("#datareg").html(VehicleEngineType);
		}
	});
	
	
});

$("#custsubmit").click( function(){
	$('#custform').submit();
	//alert($("#custform").serialize());
	/*$.ajax({
		type : "POST",
		url : 'process.php',
		data : $("#custform").serialize(),
		dataType : "html"
	});*/
});

$("#custreset").click( function(){
	$("#custform")[0].reset();
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});
$("#vehsubmit").click( function(){
	$("#vehform").submit();
});
$("#vehreset").click( function(){
	$("#vehform")[0].reset();
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});
$("#empsubmit").click( function(){
	$("#empform").submit();
});
$("#empreset").click( function(){
	$("#empform")[0].reset();
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});
$("#supsubmit").click( function(){
	$("#supform").submit();
});
$("#supreset").click( function(){
	$("#supform")[0].reset();
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});

$(".searchfield").after("<span class='required'>&#128269;</span>");
$("input[required='']").after("<span class='required'>*</span>");
$("input[required='']").blur(function() {
if ($(this).is(":valid")) {
	$(this).parent().children("span").html("&#10004;").css("color","#6C3");

} else {
	$(this).parent().children("span").html("*").css("color","#C00");
}
});
new Date($.now())
$(".tbldate").html(Date);

$('.grab').mousedown(function(){ 
	$(this).attr('id','cust'),
	makeDraggable()
});

$('.grab').mouseup(function(){
	$(this).removeAttr('id','cust')
});

function makeDraggable(){
$('#cust').draggable({
	addClasses: false,
	snap: true,
	snapMode: "inner"
});
}

$('.timeslot .timeslots').droppable({
    drop: function(ev, ui) {
		$(this).each(function(){
        $(ui.draggable).detach()
		var times = $(this).attr('id');
		$(this).children('span').removeAttr('id').attr('id','slot'+times),
		$(this).children('span').html($('.grab').children('p').html())
		});
		}
});
/*
$('#bayone').hover(
function(){
   	$(this).droppable({
		accept: ".grab",
		hoverClass: "timeslotshover",
		tolerance: "touch",
		drop: function(){
			$(this).each(function(){
				var times = $(this).attr('id');
				$(this).children('span').removeAttr('id').attr('id','slot'+times),
				$(this).children('span').html($('.grab').children('p').html())
		});
	}
});
},
function(){
	$(this).droppable('disable')
});
*/
});


