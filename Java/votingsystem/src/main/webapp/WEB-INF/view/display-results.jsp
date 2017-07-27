<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Display Results</title>
<script type="text/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/Chart.bundle.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
  href="/resources/css/bootstrap.min.css" />
<script type="text/javascript">
$(document).ready(function(){
	var jsonreturn = null;
	var resultarr = [];
	var labelarr = [];
	$.ajax({
		  url : "/check-votes",
		  context: document.body,
		  type : "POST",
		  data : {
			  "questionid": "${question.id}",
			  "${_csrf.parameterName}" : "${_csrf.token}"
		  },
		  success : function(result){
			  
				  var json = $.parseJSON(result);
				  var totalvotes = 0;
					$.each(json, function(key, value){
					  resultarr.push(value);
					  totalvotes += value;
					  labelarr.push(key);
					});
					$("#regmsg").html("<b>Total Votes: " + totalvotes + "</b>");
				  var ctx = $("#myChart");
				  var myChart = new Chart(ctx, {
					    type: 'bar',
					    data: {
					        labels: labelarr,
					        datasets: [{
					            label: '# of Votes',
					            data: resultarr,
					            borderWidth: 1
					        }]
					    },
					    options: {
					        scales: {
					            yAxes: [{
					                ticks: {
					                    beginAtZero:true
					                }
					            }]
					        }
					    }
					});
		  }
	})	
});
</script>
</head>
<body>
<div class="col-md-6">
  <a href="/process-login"><< back</a>
  <div class="panel panel-default">
  <div class="panel-heading"><h1>${question.question}</h1></div>
  <div class="panel-body">
  <canvas id="myChart" width="400" height="400"></canvas>
  <div id="regmsg"></div>
  </div>
  </div>
</div>
</body>
</html>