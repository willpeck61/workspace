// JavaScript Document

// Confirm Password
function checkPword(){
	var has_empty = false;
	console.log("Yep");
   $("#adduser").find( 'input' ).each(function () {
	      if ( ! $(this).val() ) { has_empty = true; return false; }
   });
   if ( has_empty ) { alert("Fill in all the fields."); } else {
	var p1 = $("input#p1").val();
	var p2 = $("input#p2").val();
	if (p1 != p2){
		$("#message").html("Passwords do not match.");
		$("#sub5").attr("disabled", true);
	} else if (p1 == p2) {
		$("#message").html("Passwords match.");
		$("#sub5").attr("disabled", false);
		createNewUser();
	}
   }
}

// Upload image file
function uploadImg(){
	$.ajax({
		url: "upload",
		method: "post",
		data: $("#uploadform").serialize(),
		dataType: "multipart/form-data",
		success: function(data){
			$("#profileimg").html(data);
			alert("New Image Uploaded");
		}
	})
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

function sendBuddyReq(e){
	var usrname = $("#budlogin" + e).val()
	$.ajax({
		url: "buddy-request",
		data: "responder=" + usrname,
		method: "get",
		success: function(data) {
			alert("Your Request Was Sent Successfully");
			location.reload();
		}
	});
}

function showMsg(e){
	$("#message-" + e).slideDown();
}

function removeBuddy(e){
	$("#confirm-" + e).slideDown();
}

function hideConf(e){
	$("#confirm-" + e).slideUp();
}

function hideMsg(e){
	$("#message-" + e).slideUp();
}


function cshowMsg(e){
	$("#cmessage-" + e).slideDown();
}

function cremoveBuddy(e){
	$("#cconfirm-" + e).slideDown();
}

function chideConf(e){
	$("#cconfirm-" + e).slideUp();
}

function chideMsg(e){
	$("#cmessage-" + e).slideUp();
}


function sendMessage(e, f){
	var result = $.ajax({
		url: "sendInitMessage",
		data: { userToSend: e, messageContent: $("#txt" + e).val() },
		method: "post",
		success: function(data) {
			$("#txt" + e).val("");
			$("#msg-" + e).html("Message Sent");
		}
	})
	
	 
	result.fail(function( jqXHR, textStatus ) {
	  alert( "Request failed: " + textStatus );
	  console.log(jqXHR);
	});
}

function csendMessage(e, f){
	var result = $.ajax({
		url: "sendInitMessage",
		data: { userToSend: e, messageContent: $("#ctxt" + e).val() },
		method: "post",
		success: function(data) {
			$("#ctxt" + e).val("");
			$("#cmsg-" + e).html("Message Sent");
		}
	})

	result.fail(function( jqXHR, textStatus ) {
	  alert( "Request failed: " + textStatus );
	  console.log(jqXHR);
	});
}

function addInterest(){
	var inputBox = $("#addInter").val();
	$("#interests").append("<option>" + inputBox + "</option>");
	$.ajax({
		url: "add-interest",
		method: "get",
		data: {addint: inputBox},
		success: function(data) {
			location.reload();
		}
	})
}

function removeInterest(){
	var inputBox = $("#addInter").val();
	$.ajax({
		url: "remove-interest",
		method: "get",
		data: {remint: inputBox},
		success: function(data) {
			location.reload();
		}
	})
}

//DOM ready functions with event triggers.
$(document).ready(function(){	
	$(function () {
	    var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});
	
	$("#interests").change(function(){
		var inter = $(this).val();
		$("#addInter").val(inter);
		$("#removeInt").prop("disabled", false);
	})
	
	var env = $("#env").val();
	if (env != null) {
		if (env == 0) {
			$("#quiet").prop("checked", true);
		} else if (env == 1) {
			$("#lively").prop("checked", true);
		} else if (env == 2){
			$("#anyenv").prop("checked", true);
		};
	};
	
	var same = $("#same").val();
	if (same != null) {
		if (same == 0) {
			$("#samesex").prop("checked", true);
		} else if (same == 1) {
			$("#mixed").prop("checked", true);
		} else if (same == 2) {
			$("#anysame").prop("checked", true);
		};
	};
	
	var smok = $("#smok").val();
	if (smok != null) {
		if (smok == 0) {
			$("#smokn").prop("checked", true);
		} else if (smok == 1) {
			$("#smoky").prop("checked", true);
		} 
	};
	
	var pets = $("#pets").val();
	if (pets != null) {
		if (pets == 0) {
			$("#petn").prop("checked", true);
		} else if (pets == 1) {
			$("#pety").prop("checked", true);
		}
	};
	
	var ens = $("#ens").val();
	if (ens != null) {
		if (ens == 0) {
			$("#ensn").prop("checked", true);
		} else if (ens == 1) {
			$("#ensy").prop("checked", true);
		} 
	};
	
	var remlog = $("#remlog").val();
	$("#remove-buddy").click(function(){
		var login = $(this).val();
		$("#remlog").val(login);
		remlog = $("#remlog").val();
		$("#confirm-" + remlog).slideDown();
	});
	

	
//listen for amend button click and submit form
	$("#amend").click(function(){
		$("#updateprofile").submit();
	});
	
	$("#pwchange").click(function(){
		if ( has_empty ) { alert("Fill in all the fields."); } else {
			var p1 = $("input#p1").val();
			var p2 = $("input#p2").val();
			if (p1 != p2){
				alert("Passwords do not match.");
			} else if (p1 == p2) {
				alert("Password successfully changed.");
				$("#changepw").submit();
			}
		}
	});

//Reload user list in buddy search box
	$("#inputusrlst").focus(function(){		
		$.ajax({
			url: "get-users",
			data: "type=buds",
			method: "get",
			success: function(data) {
				$("#userlist").html(data);
			}
		});
	 });

//Return selected user in buddy search box
	$("#inputusrlst").change(function(){
		var querystr = $("#inputusrlst").val();
		$.ajax({
			url: "buddy-search",
			data: "querystr=" + querystr,
			method: "get",
			success: function(data) {
				$("#selected-buddy").html(data);
				if (data.indexOf("<div") >= 0) {
					$("#noresult").hide();
				} else {
					$("#noresult").show();
				}
			}
		});
		
	});

//View buddy request
	$('.btn-file :file').on('fileselect', function(event, numFiles, label) {
	    $('#filename').val(label);
	    $('#upload').prop('disabled',false);
	});
	
	$('#upload').click(function(){
		$(this).prop('disabled', true);
		var filesize = $("#filechooser")[0].files[0].size;
		if (filesize < 3072000) {
			$('#uploadform').submit();
		} else {
			alert("Filesize is too large. Must be less than 3 MB");
		}
	});
});



$(document).on('change', '.btn-file :file', function() {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
});
