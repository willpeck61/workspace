// JavaScript Document
//
// Coding by Will Peck 2015

//ONLY SHOW THE CONTENT FROM CLICKED ITEMS
$(window).load(function(){
$('article').not('.item0').hide();
$('nav a').click(function(){
	var chkitem = $(this).attr('id');
	$('article').not('.'+chkitem).hide();
	$('.'+chkitem).fadeIn();
});
$('.custform').children("#update").val('false');
$('.empform').children("#update").val('false');
$('.vehform').children("#update").val('false');
$('.supform').children("#update").val('false');

//UPDATE TIME EVERY 1s
function rightNow(){
	new Date($.now())
	$(".tbldate").html(Date);
}
rightNow();
setInterval(rightNow,1000);

//AJAX TO RETRIEVE DATA ASYNCRONOUSLY ON FOCUS
$("#VehicleLogCustomerID").focus( function() {
	var formopt = ($(this).attr('list'));
	$.ajax({
		type : 'post',
		url : "process.php",
		data : { "retrieve" : "", "tbl" : formopt },
		dataType : "html",
		success : function( data ) {
			$('#'+formopt).html(data)
		}
	});
});

//AJAX TO RETRIEVE DATA ASYNCRONOUSLY ON FOCUS
$("input[name='retrieve']").focus( function() {
	var formopt = ($(this).attr('list'));
	$.ajax({
		type : 'post',
		url : "process.php",
		data : { "retrieve" : "", "tbl" : formopt },
		dataType : "html",
		success : function( data ) {
			$('#'+formopt).html(data)
		}
	});
});

//AJAX POST DATA TO PROCESS
$("input[name='retrieve']").change( function() {
	var thisvalue = $(this).val();
	var getdata = $("option[value='"+thisvalue+"']").data("value");
	$(this).closest('form').children("#id").html(getdata);
	var toVal = $(this).closest('form').children("#id").html();
	$(this).closest('form').children("#id").val(toVal);
	$(this).closest('form').children("#update").val('true');
	var whatForm = $(this).closest('form').attr('class');
	console.log(getdata);
	var formsrc = $(this).attr('list');
	window.num = $("option[value='"+thisvalue+"']").data("value");
	$.ajax({ 
		type : 'post',
		url : "process.php", 
		data : { "tbl" : formsrc, "selection" : getdata },
		dataType : "html", 
		success : function( data ){
			$('footer').after("<span class='"+formsrc+"'>"+data+"</span>");
			$("input[required='']").each( function () {
				$(this).parent().children("span").html("&#10004;").css("color","#6C3");
			});
			console.log(whatForm);
			if (whatForm == "ordcustform"){
				var CustomerID = getdata;
			}
			if (whatForm == "ordempform"){
				var EmployeeID = getdata;
			}
			if (whatForm == "ordvehform"){
				var VehicleID = getdata;
			}
			console.log(CustomerID);
			console.log(EmployeeID);
			console.log(VehicleID);
			var CustomerTitle = $("#CustomerTitle").val();
			var CustomerFirstName = $("#CustomerFirstName").val();
			var CustomerLastName = $("#CustomerLastName").val();
			var CustomerAddress1 = $("#CustomerAddress1").val();
			var CustomerAddress2 = $("#CustomerAddress2").val();
			var CustomerAddress3 = $("#CustomerAddress3").val();
			var CustomerPostcode = $("#CustomerPostcode").val();
			var CustomerPhone1 = $("#CustomerPhone1").val();
			var CustomerEmail = $("#CustomerEmail").val();
			
			var VehicleMake = $("#VehicleMake").val();
			var VehicleModel = $("#VehicleModel").val();
			var VehicleType = $("#VehicleType").val();
			var VehicleEngineType = $("#VehicleEngineType").val();
			var VehicleFuelType = $("#VehicleFuelType").val();
		
			$("#dataname").html(CustomerTitle+" "+CustomerFirstName+" "+CustomerLastName);
			$("#dataveh").html(VehicleMake+" "+VehicleModel+" "+VehicleType);
			$("#datareg").html(VehicleEngineType);
			$("#ordtitle").html(CustomerTitle);
			$("#ordfirst").html(CustomerFirstName);
			$("#ordstreet").html(CustomerAddress1);
			$("#ordtown").html(CustomerAddress2);
			$("#ordcity").html(CustomerAddress3);
			$("#ordpostcode").html(CustomerPostcode);
			$("#ordphone").html(CustomerPhone1);
			$("#ordemail").html(CustomerEmail);
			$("#vehmake").html(VehicleMake);
			$("#vehmodel").html(VehicleModel);
			$("#vehtype").html(VehicleType);
			$("#vehfuel").html(VehicleFuelType);
			
			$("#OrderCustomerID").html(CustomerID);
			$("#OrderEmployeeID").html(EmployeeID);
			$("#OrderVehicleID").html(VehicleID);
			var cust = $("#OrderCustomerID").html();
			var emp = $("#OrderEmployeeID").html();
			var veh = $("#OrderVehicleID").html();
			$("#OrderCustomerID").val(cust);
			$("#OrderEmployeeID").val(emp);
			$("#OrderVehicleID").val(veh);
			$("#vehid").val(veh);			
		}
	});
	$("#custdelete, #empdelete, #vehdelete, #supdelete").click( function() {
		if(confirm('CAUTION: This action will delete the current record. Are you sure you wish to continue?')){
			$.ajax ({
				type : 'post',
				url : 'process.php',
				data : {'tbl' : formsrc, 'delete' : getdata},
				dataType : "html",
				success : function(){
					alert("RECORD DELETED");
				}
			});
			$(this).closest('form')[0].reset();
		}
	});
});

//BIND BUTTONS TO FORM SUBMIT 
$("#custsubmit").click( function(){
	$('.custform').submit();
});


//Add required *s to form.
$("#custreset").click( function(){
	$(".custform")[0].reset();
	$('.custform').children("#update").val('false');
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});

//Use Ajax to post 2 forms from one button.
$("#vehsubmit").click( function(){
	if(!$('#VehicleLogCustomerID').val()){
	$(".vehform").submit();
	} else {
	$("#ownerform").submit(function(e){
		e.preventDefault();
		var thisform = $(this).serialize();
		$.post('process.php', thisform, function(){
			$(".vehform").submit();
		});
	});	
	}
});

$("#vehreset").click( function(){
	$(".vehform")[0].reset();
	$('.vehform').children("#update").val('false');
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});
$("#empsubmit").click( function(){
	$(".empform").submit();
});
$("#empreset").click( function(){
	$(".empform")[0].reset();
	$('.empform').children("#update").val('false');
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});
$("#supsubmit").click( function(){
	$(".supform").submit();
});
$("#supreset").click( function(){
	$(".supform")[0].reset();
	$('.supform').children("#update").val('false');
	$("input[required='']").parent().children("span").html("*").css("color","#C00");
});

//Handle form validation icons.
$(".searchfield").after("<span class='required'>&#128269;</span>");
$("input[required='']").after("<span class='required'>*</span>");
$("input[required='']").blur(function() {
	if ($(this).is(":valid")) {
	$(this).parent().children("span").html("&#10004;").css("color","#6C3");

	} else {
	$(this).parent().children("span").html("*").css("color","#C00");
	}
});

//DRAG AND DROP PLANNER

//ONLY MAKE DRAGGABLE ON MOUSEDOWN
$('.grab').mousedown(function(){ 
	$(this).attr('id','cust'),
	makeDraggable()
});

$('.grab').mouseup(function(){
	$(this).removeAttr('id','cust')
});

function makeDraggable(){
$('#cust').draggable({
	snap: ".timeslot",
	snapTolerance: "15",
	drag: function(){
		$('.timeslot, .timeslot .sub').children('span').html("");
	}
	
});
}

//When dropped then add something to span elements.
$('.timeslot, .timeslot .sub').droppable({
	accept: ".grab",
	hoverClass: "timeslotshover",
	tolerance: "touch",
	over: function() {
		$('#cust').draggable({
			snapMode: "inner"
			});
	},
    drop: function(event,ui) {
		console.log(ui.offset);
		var x = 0;
		$(this).each(function(event,ui){
			x++;
			var times = $(this).attr('id');
			$(this).children('span').html($('.grab').children('p').html())
		})
	}
});

$('.tabs_holder').skinableTabs({
    effect: 'fade_slide_up',
    skin: 'skin7',
    position: 'top'
});
  
$('input[type="date"]').datepicker({
	dateFormat : 'dd-mm-yy',
	minDate : 0
});

//CREATE NEW ORDER IN tblorder
$("#TimeSlotDate").change(function(){
	var thisform = $("#tblorder").serialize()
	$.ajax({
		type : "post",
		data :  thisform,
		url : "process.php",
		dataType : "html",
		success : function(data){
		
		}
	})
	return false;
});


//POST NEW USER FORM
$('#newuser').click(function(){
	var thisform = $("#newuserform").serialize();
	var pw = $('#Password').val();
	var pw1 = $('#Password1').val();
	if (pw != pw1){
		alert('Passwords do not Match');
	} 
	else {
		alert("this happened first");
		$('#newuserform').submit();		
	}
});
});




