<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Place Vote</title>
<script type="text/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/css/bootstrap.min.css" />
<script type="text/javascript">
	$(document).ready(
	  function() {
		  $(".expires, .expires1, #lastvote").hide();
			$("#questsel").change(
			function() {
			  var id = $(this).val();
				$.ajax({
					url : "/return-options",
					type : "POST",
					data : {
						"questionid" : id,
				  	"${_csrf.parameterName}" : "${_csrf.token}"
					},
					success : function(result) {
						var json = $.parseJSON(result);
						var resultarr = [];
						$.each(json,
							function(key, value) {
								resultarr.push(key);
								resultarr.push(value);
							});
						for (var i = 0; i < resultarr.length; i++) {
							$("#questions").append("<div class='radio'><label><input type='radio' class='optionsRadios' name='optionid' value='" 
									  + resultarr[i] + "'/>"
									  + resultarr[i + 1]
									  + "</label></div>");
							$("input").each(function(){
                if ($(this).attr("qid") == resultarr[i]) {
                  $(".expires").html("<b>This question expires on " + $(this).attr("date"));
                  $(".expires").show();
                }
              })
							i++;
						}
					}
				})
			})

			$("#vote").click(
			function(e) {
				e.preventDefault();
				var validate = 1;
				$(".required").each(function() {
					if ($(this).val === "") {
						validate = 0;
					}
				});

				var formdata = $("#votingform").serialize();
				if (validate = 1) {
					$.ajax({
						url : "/place-vote",
						type : "POST",
						data : formdata,
						success : function(result) {
							var voted = $("#questsel").val();
							$(".vote").each(function() {
								if ($(this).val() == voted) {
			            $(this).remove();
								}
							});
							$("#result").html(result);
						}
					});
				} else {
					alert("You have not selected a question or voting option. Try again.");
				}
			})
				
		  $("#questsel2").change(
      function() {
	      var id = $(this).val();
	      $.ajax({
	        url : "/return-options",
	        type : "POST",
	        data : {
	          "questionid" : id,
	          "${_csrf.parameterName}" : "${_csrf.token}"
	        },
	        success : function(result) {
	          var json = $.parseJSON(result);
	          var resultarr = [];
	          $.each(json,
	            function(key, value) {
	              resultarr.push(key);
	              resultarr.push(value);
	            });
	          $("#prequestions .radio").html("");
	          for (var i = 0; i < resultarr.length; i++) {
	            $("#prequestions").append("<div class='radio'><label><input type='radio' class='optionsRadios' name='optionid' value='" 
	                  + resultarr[i] + "'/>"
	                  + resultarr[i + 1]
	                  + "</label></div>");
	            $("input").each(function(){
	            	if ($(this).attr("qid") == resultarr[i]) {
	            		console.log("this");
	                $("#lastvote").html("<h3>You last voted: " + $(this).val() + "</h3>");
	                $(".expires1").html("<b>This question expires on " + $(this).attr("date"));
	                $("#lastvote").show();
	                $(".expires1").show();
	              }
	            })
	            
	            i++;
	          }
	        }
	      })
      })
      
      $("#changevote").click(
      function(e) {
        e.preventDefault();
        var validate = 1;
        $(".required").each(function() {
          if ($(this).val === "") {
            validate = 0;
          }
        });

        var formdata = $("#changevoteform").serialize();
        if (validate = 1) {
          $.ajax({
            url : "/place-vote",
            type : "POST",
            data : formdata,
            success : function(result) {
              var voted = $("#questsel2").val();
              $("#result2").html(result);
              alert("Successfully changed vote.");
              location.reload();
            }
          });
        } else {
          alert("You have not selected a question or voting option. Try again.");
        }
      })
        
		});
</script>
</head>
<body>
	<div class="col-md-6">
		<a href="/logout">Logout</a>
		<h1>
			Voting Page - Welcome
			<sec:authentication property="principal.username" var="username" />
		</h1>
	  <div class="panel panel-default">
	  <div class="panel-heading"><h2>Place your vote</h2></div>
	  <div class="panel-body">
	  <div class="form-group">
    <div class="expires">Expires</div>
    </div>
		<form id="votingform">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
				<input type="hidden" name="change" value="false" />
			<div class="form-group" id="questions">
				<label for="questionid">Select Question</label> 
				<select name="questionid" class="form-control" id="questsel"
					class="required">
					<option value="" disabled selected>[voting options]</option>
					<c:forEach items="${questions}" var="question">
						<option class="vote" value="${question.id}">${question.question}</option>
					</c:forEach>
				</select>
			</div>
		</form>
		<button type="button" class="btn btn-default" id="vote">Submit
			Vote</button>
		<div class="form-group">
		<p></p>
		<div id="result"></div>
		</div>
		</div>
		</div>
		    <div class="panel panel-default">
    <div class="panel-heading"><h2>Change your vote</h2></div>
    <div class="panel-body">
    <div class="form-group">
    <div id="lastvote">Last Voted</div>
    <div class="expires1">Expires</div>
    </div>
    <form id="changevoteform">
      <input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />
        <input type="hidden" name="change" value="true" />
      <div class="form-group" id="prequestions">
        <label for="questionid">Select Question</label> 
        <select
          name="questionid" class="form-control" id="questsel2"
          class="required">
          <option value="" disabled selected>[voting options]</option>
          <c:forEach items="${questions}" var="question">
            <c:forEach items="${votes}" var="vote">
              <c:if test="${question == vote.question}">
                <option class="voted" value="${question.id}">${question.question}
                </option>
              </c:if>
            </c:forEach>
          </c:forEach>
        </select>
      </div>
    </form>
    <div id="process">
    <c:forEach items="${votes}" var="vote">
      <c:forEach items="${questions}" var="question">
        <c:if test="${vote.question == question}">
           <input type="hidden" value="${vote.option.option}" 
           qid="${question.id}" date="${question.expires}"/>
        </c:if> 
      </c:forEach>
    </c:forEach>
    </div>
    <button type="button" class="btn btn-default" id="changevote">Submit
      Vote</button>
    <div class="form-group">
    <p></p>
    <div id="result2"></div>
    </div>
    </div>
    </div>
	</div>
</body>
</html>