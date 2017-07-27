<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Officer Page</title>
<script type="text/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/css/bootstrap.min.css" />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#addquestion")
								.click(
										function(e) {
											e.preventDefault();
											var formdata = $("#newquestion")
													.serialize();
											var validate = 1;
											$(".required").each(function() {
												if ($(this).val() === "") {
													validate = 0;
												}
											});
											if (validate == 1) {
												$
														.ajax({
															url : "/add-question",
															type : "POST",
															data : formdata,
															success : function(
																	result) {
																$("#questions")
																		.html(
																				"");
																var json = $
																		.parseJSON(result);
																var resultarr = [];
																$
																		.each(
																				json.questions,
																				function(
																						key,
																						value) {
																					resultarr
																							.push(value.question);
																					resultarr
																							.push(value.id);
																				});
																for (var i = 0; i < resultarr.length; i++) {
																	$(
																			"#questions")
																			.append(
																					"<li><a href='/display-results?questionid="
																							+ resultarr[i + 1]
																							+ "'>"
																							+ resultarr[i]
																							+ "</a></li>");
																	i++;
																}
															}
														})
											} else {
												alert("Please complete required fields.");
											}
										})

					});
</script>
</head>
<body>
	<div class="col-md-6">
		<a href="/logout">Logout</a>
		<h1>
			Officer Page - Welcome
			<sec:authentication property="principal.username" />
		</h1>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>Current Questions</h2>
			</div>
			<div class="panel-body">
				<p>Click for results:</p>
				<ul id="questions">
					<c:forEach items="${questions}" var="question">
						<li><a href="/display-results?questionid=${question.id}">${question.question}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>New Question</h2>
			</div>
			<div class="panel-body">
				<form action="/add-question" method="POST" id="newquestion">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<div class="form-group">
						<textarea name="question" class="form-control"
							placeholder="Type your question.." class="required"></textarea>
					</div>
					<div class="form-group">
						<label for="date">Expires</label> <input name="date" id="date"
							class="form-control" type="date" class="required" />
					</div>
					<div class="form-group">
						<label>Set Voter Options (at least two)</label> <input
							name="option1" id="option" class="form-control" type="text"
							placeholder="Choice One.." class="required" />
					</div>
					<div class="form-group">
						<input name="option2" class="form-control" type="text"
							placeholder="Choice Two.." class="required" />
					</div>
					<div class="form-group">
						<input name="option3" class="form-control" type="text"
							placeholder="Choice Three.." />
					</div>
					<div class="form-group">
						<input name="option4" class="form-control" type="text"
							placeholder="Choice Four.." />
					</div>
					<div class="form-group">
						<input name="option5" class="form-control" type="text"
							placeholder="Choice Five.." />
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-default" id="addquestion">Add
							New Question</button>
					</div>
				</form>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>New Codes</h2>
			</div>
			<div class="panel-body">
				<form action="/add-codes" method="POST" id="newcodes">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<div class="form-group" id="questions">
						<label for="questionid">Select Question</label> <select
							name="questionid" class="form-control" id="questsel"
							class="required">
							<option value="" disabled selected>[select question]</option>
							<c:forEach items="${questions}" var="question">
								<option value="${question.id}">${question.question}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<textarea name="codes" class="form-control"
							placeholder="One code per line.."></textarea>
					</div>
					<div class="form-group">
						<button id="addcodes" class="btn btn-default">Add New
							Code(s)</button>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>