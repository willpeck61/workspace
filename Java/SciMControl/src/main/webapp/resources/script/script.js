// JavaScript Document

// Confirm Password
function checkPword(){
	var p1 = $("input#p1").val();
	var p2 = $("input#p2").val();
	if (p1 != p2){
		$("#message").html("Passwords do not match.");
		$("#sub5").attr("disabled", true);
	} else {
		$("#message").html("Passwords match.");
		$("#sub5").attr("disabled", false);
	}	
}

//Create new user
function createNewUser(){
	var now = new Date($.now());
	$("#message").html("");
	$.ajax({
			url: "addUser",
			method: "get",
			data: $("#form5").serialize(),
			dataType: "html",
			success: alert("New User Added"),
			done: $("#form5")[0].reset(),
			done: $("#log").append(now.toLocaleString()+": New User Added<br>")
	});
}

//Delete selected user
function deleteUser(){
	var now = new Date($.now());
	$.ajax({
		url: "delete-user",
		method: "get",
		data: $("#form5b").serialize(),
		datatype: "html",
		success: alert("User Deleted"),
		done: $("#log").append(now.toLocaleString()+": User Deleted<br>")
	});
	$.when(this).done(function(){
		updateUserList();
	});
}

//Update list of active users 
function updateUserList(){
	$.ajax({
		url: "get-users",
		method: "get",
		success: function(data){
			$(".usrlist").each(function(i, obj){
				$(this).html(data);
			});
		}
	});
}

//Create new change request.
function createNewRequest(){
	var now = new Date($.now()+14);
	$.ajax({
		url: "newChange",
		method: "get",
		data: $("#newChange").serialize(),
		success: function(data){
			$("#returned").html(data);
			$("#status").load("dashboard");
		},
		done: $("#newChange")[0].reset(),
		done: $("#log").append(now.toLocaleString()+": New Change Request<br>")
	});
}

//Remove all versions not applicable to selected SCI.
function versionList(){
	var sciid = $("select :selected").attr("sci");
	$("[ver]").each(function(i, obj){
		var versci = $(this).attr("ver");
		if (versci != sciid){
			$(this).remove();
		};
		$("#verlist").prop("disabled",false);
	});
}

//Toggle rejected requests in pending view
function hideRejected(){
	$("input[name=status]:checked").each(function(){
		var selected = $(this);
		var getval = $(selected).val();
		if (getval == 4){
			var i = $(selected).attr("data-value");
			$("."+i).toggle();
			$("#hideshow").html("Toggle Rejected");
		}
	});
}

//Reject requests from pending view
function rejectRequest(){
	var now = new Date($.now()+14);
	$(".reason").hide();
	$("#row tr:not(#"+reqid+")").hide();
	var reqid = $("#rejectbtn").attr("value");
	var reason = $("#reason").val();
	$.ajax({
		url: "update-pending-requests",
		method: "get",
		dataType: "html",
		data: {
			"status" : 4,
			"reqid" : reqid,
			"reason" : reason
		},
		success: function(data){
			alert("Request " +reqid+ " Rejected");
			$("#log").append(now.toLocaleString()+": Request " +reqid+ " Rejected<br>");
		}
	})
}

function acceptRequest(){
	var now = new Date($.now());
	$(".duedate").hide();
	var reqid = $("#acceptbtn").val(); 
	var usrid = $("#usr_"+reqid).val();
	var priority = $(".prior_"+reqid).val();
	var duedate = $("#duedate").val();
	
	$.ajax({
		url: "update-pending-requests",
		method: "get",
		dataType: "html",
		data: {
			"reqid": reqid,
			"status": 1,
			"assignto": usrid,
			"priority": priority,
			"duedate": duedate
		},
		success: function(data){
			$("#log").append(now.toLocaleString()+": Request " +reqid+ " Accepted<br>");
			$("#status").load("dashboard");
		}
	});
}

function completeRequest(){
	var now = new Date($.now());
	$("#setdate").hide();
	var reqid = $("#setdate").attr("value");
	var datecompleted = $("#datecompleted").val();
	$.ajax({
		url: "complete-request",
		method: "get",
		dataType: "html",
		data:	{
			"reqid": reqid,
			"complete" : datecompleted
		},
		success: function(data){
			$("#log").append(now.toLocaleString()+": Request " +reqid+ " Complete<br>");
			$("#status").load("dashboard");
			$("#menu1").click();
		}
	})
}

$(document).ready(function(){
	
//Set Status Panel	
	$("#status").load("dashboard");
	
//Menu Actions
	$("#menu0").click(function(){
		$("#content").load("resources/includes/menu0.jsp",function(){
			$("#scidata").load("return-sci-version", function(){
				$(".sciopt").each(function(i, obj){
					$(this).appendTo("select#scilist");
				})
				$(".veropt").each(function(i, obj){
					$(this).appendTo("select#verlist");
				})
				$("#status").load("dashboard");
			})
			$("#task").html("new request");
			
		});
		$("#task").html("new request");
		$("#versions").appendTo("#verlist");
	});
		
	$("#menu1").click(function(){
		$("#content").load("assigned-requests", function(){
			$("#setdate").hide();
			$("#completed").click(function(){
				var reqid = $(this).val();
				$("#row tr:not(#"+reqid+")").hide();
				$("#setdate").show();
				$("#setdate").attr("value",reqid);
			});
		});
		$("#task").html("assigned requests");
	});
	$("#menu2").click(function(){
		$("#content").load("resources/includes/menu2.jsp");
		$("#task").html("active requests");
	});
	$("#menu3").click(function(){
		$("#content").load("resources/includes/menu3.jsp");
		$("#task").html("task management");
	});
	$("#menu4").click(function(){
		$("#content").load("get-pending-requests", function(){
			$(".duedate").hide();
			$(".reason").hide();
			updateUserList();
			
			$("#rejectcancel").click(function(){
				$("#menu4").click();
			});
			
			$("#acceptcancel").click(function(){
				$("#menu4").click();
			});
			
			$("input[type=radio][name^=status_]").change(function(){
				var status = $(this).val();
				var reqid = $(this).attr("data-value");
				var now = new Date($.now());
				if (status == 4){
					$("#row tr:not(#"+reqid+")").hide();
					$("#rejectbtn").attr("value", reqid);
					$(".duedate").hide();
					$(".reason").show();
				} else if (status == 0){
					$("#acceptbtn").attr("value",reqid);
					$(".duedate").hide();
					$(".reason").hide();
				} else if (status == 1){
					$("#row tr:not(#"+reqid+")").hide();
					$("#acceptbtn").attr("value",reqid);
					$(".duedate").show();
					$(".reason").hide();
				}
			});
			
			$(".usrlist").change(function(){
				var usrval = $(this).val();
				var reqid = $(this).attr("data-value");
				if (usrval != 0){
					$(".prior_"+reqid).prop("disabled",false);
				} else {
					$(".prior_"+reqid).prop("disabled",true);
				}
			});
			
			$("#priority").change(function(){
				var prival = $("#prior").val();
				if (prival != 0){
					$("#accept").prop("disabled", false);
				} else {
					$("#accept").prop("disabled", true);
				}
			});
		});
		$("#task").html("pending requests");
	});
	
	$("#menu5").click(function(){
		$("#content").load("resources/includes/menu5.jsp", function(){
			updateUserList();
			$("#status").load("dashboard");
		});
		$("#task").html("user management");
	});
	
//Text Resize
	$(".fit").fitText(1.2,{minFontSize: "28px", maxFontSize: "36px"});
	$(".fit1").fitText(1.2,{minFontSize: "16px", maxFontSize: "24px"});
	$(".fit2").fitText(1.2,{minFontSize: "16px", maxFontSize: "24px"});
	$(".fit3").fitText(1.2,{minFontSize: "16px", maxFontSize: "24px"});
	$(".fit4").fitText(1.2,{minFontSize: "16px", maxFontSize: "24px"});
	$(".fit5").fitText(1.2,{minFontSize: "16px", maxFontSize: "24px"});
	$(".fit6").fitText(1.2,{minFontSize: "16px", maxFontSize: "24px"});
});
