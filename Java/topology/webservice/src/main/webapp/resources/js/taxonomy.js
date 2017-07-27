/**
 * 
 */

$(document).ready(function() {
	//populate class select box with existing classes.
	getClassList();
	getProperties();
	getDatatypes();
	getInstances();
	
	//listens for change in select box value and updates form fields. 
	$("#classes").change(function(){
		var val = $(this).val();
		$("#class").val(val);
		$("#classforinst").val(val);
		$("#edit").val(val);
		console.log(val);
		getNodedata(val);
	})
	
	$("#propertysel").change(function(){
		var val = $(this).val();
		$("#propertyname").val(val);
		
	})
	
	$("#instancesel").change(function(){
		var val = $(this).val();
		$("#submitinst").html("<input type='hidden' name='instancename' id='inst' />")
		$("#inst").val(val);
		getNodedata(val);
	})
	
	// bind 'tree.click' event
	$('#tree1').bind(
	    'tree.click',
	    function(event) {
	        this.reload();
	    }
	);
});

//get available properties
function getProperties() {
	$.get("/rest/class/getProperties", function(data){
		var json = JSON.parse(data);
		console.log(json);
		$("#propertysel").html("<option value='' disabled selected>Select an existing property..</option>");
		$.each(json, function(name, type){
			$("#propertysel").append("<option value='" + name + "'>" + name + " :: " + type + "</option>")
		})
	})
	
}

//get available datatypes
function getDatatypes() {
	$.get("/rest/class/getDatatypes", function(data){
		var json = JSON.parse(data);
		console.log(json);
		$("#datatype").html("<option value='' disabled selected>Select an existing datatype..</option>");
		$.each(json, function(name, type){
			$("#datatype").append("<option value='" + name + "'>" + name + " :: " + type + "</option>")
		})
	})
}

function getNodedata(val){
	var instance = false;
	var instname = $("#inst").val();
	  $.ajax({
		  type : "GET",
		  url : "/rest/class/nodeData",
		  data : {"classname" : val, "instancename" : instname},
		  success : function(data){
				getClassList();
				getDatatypes();
		        $("#tree").tree("reload", function() {
		        	console.log("Reloaded");
		        });
		        console.log("node data : " + data);
		        var json = JSON.parse(data);
		        $("#nodedata").html("");
				$.each(json, function(label, value){
					console.log(label + " " + value);
					if (label == "classname") {
						$("#instclass").val(value);
					}
					if (typeof value == "object" && typeof label == "string") {
						$("#nodedata").append("<li>" + label);
					}
					if (typeof value == "object" && typeof label == "string") {
						$.each(value, function(label, value) {
							if (label == "propertyname") { 
								$.each(value, function (n, m) {
									console.log(n + m);
								})
							}
							if (typeof label == "number") {
							    $("#nodedata").append("<ul><li>" +  value + "</li></ul>");
							} else {
							    if (label == "propertyname"){
							    	$.each(value, function(n, m) {
							    		$("#nodedata").append("<ul><li>PropertyName :: "  + m + " = <input class='values' name='"+ m +"' type='text' placeholder='' /></li></ul>");
							    	})
								} else if (label == "datatype"){
									var datatypes = [];
									$.each(value, function(n, m) {
										datatypes[n] = m;
									})
									console.log(datatypes);
									for (var i = 0; i < datatypes.length; i++) {
										$(".values").each(function (x, y){
											if (x == i) {
												$(this).attr("placeholder", "Enter a(n) " + datatypes[i]);
											}
										})
									}
								} else if (label == "value") {
									$.each(value, function(n, m){
										var dat = eval(m);
										console.log(m);
										$("input[name=" + dat[0] + "]").val(dat[1]);
										$("input[name=" + dat[0] + "]").attr("disabled", "disabled");
										instance = true;
									})	
								}
							}
							
						})
					} else {
						
						$("#nodedata").append("<li>" + label + " :: "  + value + "</li>");
					}	
				})
				if (instance == false) {
					$("#submitinst").html("");
					$("#submitinst").append("<input name='instancename' type='text' placeholder='Instance Name'>");
					$("#submitinst").append("<a href='#' onclick='createInstance()' class='btn btn-default'>Create Instance</a>");
				}
		  }
	  });
}

//get treeview
function treeView() {
	//gets current tree structure.
    $.get("/rest/class/hierarchy?classname=Root",
	    function(data) {
    		var json = JSON.parse(data);
    		var modstr = JSON.stringify(json);
    		var len = modstr.length;
    		for (var i=0; i < len; i++) {
    			if (modstr.charAt(i) == '"') {
    				if (modstr.substr(i+1, 4) === "name") {
    					modstr = modstr.replace(modstr.substr(i, 6), "name");
    				} else if (modstr.substr(i+1,8) === "subclass") {
    					modstr = modstr.replace(modstr.substr(i, 10), "children");
    				}
    			}
    		}
    		var tree = eval('[' + modstr + ']');
	        $("#tree").tree({
	            data: tree,
	            autoOpen : true,
	        });
	        
	        $(document).ready(function(){
	        	$("#tree").tree('loadData', tree)
	        })
	    }
   );
}

//get class list
function getClassList() {
	$.get("/rest/class/getSubclassList?classname=all", 
		    function(data) {
				arr = eval(data);
				$("#classes").html("<option value='' disabled selected>Select an existing class..</option>");
				$("#superclass").html("<option value='' disabled selected>Select an existing class..</option>");
				$("#assignclass").html("<option value='' disabled selected>Select an existing class..</option>");
				$.each(arr, function(e){
					$("#classes").append("<option>" + arr[e] + "</option>");								
					$("#superclass").append("<option>" + arr[e] + "</option>");
					$("#assignclass").append("<option>" + arr[e] + "</option>");
				});
				treeView();
			}
		);
}

//create instance from selected class
function createInstance() {
	var formdata = JSON.stringify($("#instances").serializeArray());
	$.ajax({
		type : "GET",
		url : "/rest/class/createInstance",
		data : { "formdata" : formdata },
		success : function(data) {
			alert(data);
		}
	})
}

//get active instances
function getInstances() {
	$.get("/rest/class/getInstances", 
		    function(data) {
				var json = JSON.parse(data);
				console.log(data);
				$("#instancesel").html("<option value='' disabled selected>Select an existing class..</option>");
				$.each(json, function(label, value){
					$.each(value, function(x, y){
						$.each(y, function(n, m) {
							$("#instancesel").append("<option value='" + m + "'>" + n + " :: " + m + "</option>");
						})
					})
				});
			}
		);
}



//handle build class form
function buildClass() {
	if (document.getElementById("delete").checked) {
	  var formdata = $("#classbuildform").serialize();
		  $.ajax({
			  type : "GET",
			  url : "/rest/class/delete",
			  data : formdata,
			  success : function(data){
					getClassList();
					getDatatypes();
			        $("#tree").tree("reload", function() {
			        	console.log("Reloaded");
			        });
					alert(data);
			  }
		  });
	} else {
		var formdata = $("#classbuildform").serialize();
		$.ajax({
			type : "GET",
			url : "/rest/class/create",
			data : formdata,
			success : function(data) {
				getClassList();
				getDatatypes();
				alert(data);
			}
		})
	}
}

//add or change a property
function addProperty() {
	if (document.getElementById("deleteprop").checked) {
		  var formdata = $("#properties").serialize();
			  $.ajax({
				  type : "GET",
				  url : "/rest/class/deleteProperty",
				  data : formdata,
				  success : function(data){
						getClassList();
						getProperties();
						getDatatypes();
				        $("#tree").tree("reload", function() {
				        	console.log("Reloaded");
				        });
						alert(data);
				  }
			  });
		} else {
		var formdata = $("#properties").serialize();
		$.ajax({
			type : "GET",
			url : "/rest/class/createProperty",
			data : formdata,
			success : function(data) {
				getClassList();
				getProperties();
				getDatatypes();
				alert(data);
			}
		});
	}
}