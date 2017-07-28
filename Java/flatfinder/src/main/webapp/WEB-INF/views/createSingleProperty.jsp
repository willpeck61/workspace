<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/css/creativeNoAnimation.css"
	type="text/css">
<link href="/resources/css/flatfinder.css" rel="stylesheet">
	<script src="/resources/js/bootstrap.js"></script>

</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-3">
				<div id="navCol" class="panel panel-default">
					<div class="panel-body">
						<p class="lead">Navigate</p>
						<div class="list-group">
							<a href="/landlord-dashboard/create"
								class="list-group-item active" role="button">Create new
								listing</a> <a href="/landlord-dashboard/manage"
								class="list-group-item" role="button">Edit existing listing</a>
							<a href="/landlord-dashboard/statistics" class="list-group-item"
								role="button">View statistics</a><br /> <a onclick="goBack()"
								class="list-group-item" style="cursor: pointer;">Go Back</a>

							<script>
								function goBack() {
									window.history.back();
								}
							</script>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default" style="text-align: center">
					<div class="panel-body">
						<h2>Create new listing</h2>
						<img width="100%" src="/resources/img/mapbanner.jpg" />

						<form role="form" action="/submitAddedProperty"
							enctype="multipart/form-data" method="post" id="addproperty"
							name="addproperty">
							<div class="col-md-12" style="margin: 10px 0 0 0">
								<h4>
									<b>Information for adding rooms to a property</b>
								</h4>
								<p>In order to add a room, you must first create the
									property and then go to 'manage properties'. Click on the
									property and click 'add room'.</p>
							</div>

							<div class="col-md-5">
								<div class="row">
									<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-group">
												<label for="advertTitle">General Information</label> <input
													class="form-control input-sm floatlabel" type="text"
													name="advertTitle" placeholder="Advert Title" required />
											</div>
											<div class="form-group">
												<textarea class="form-control input-sm floatlabel"
													name="description" placeholder="Property Description"
													required></textarea>
											</div>
											<div class="form-group">
												<textarea class="form-control input-sm floatlabel"
													name="localArea"
													placeholder="Description of the local area (optional)"></textarea>
											</div>
											<div class="form-group">
												<input class="form-control input-sm floatlabel" type="text"
													name="price" placeholder="Price Per Week"
													onkeypress='return event.charCode >= 48 && event.charCode <= 57'
													required />
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-group">
												<label for="houseNum">Property Address</label> <input
													class="form-control input-sm floatlabel" type="text"
													name="houseNum" placeholder="House Number" required />
											</div>
											<div class="form-group">
												<input class="form-control input-sm floatlabel" type="text"
													name="address1" placeholder="Address 1" required />
											</div>
											<div class="form-group">
												<input class="form-control input-sm floatlabel" type="text"
													name="address2" placeholder="Address 2" />
											</div>
											<div class="form-group">
												<input class="form-control input-sm floatlabel" type="text"
													name="address3" placeholder="City" required />
											</div>
											<div class="form-group">
												<input class="form-control input-sm floatlabel" type="text"
													name="postcode" placeholder="Postcode" required />
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="row">

											<div class="fakelabel">Available From</div>
											<div class="col-md-4">
												<div class="form-group">
													<input class="form-control input-sm" type="text"
														name="fromDD" placeholder="dd" maxlength="2"
														onkeypress='return event.charCode >= 48 && event.charCode <= 57'
														required />
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<input class="form-control input-sm" type="text"
														name="fromMM" placeholder="mm" maxlength="2"
														onkeypress='return event.charCode >= 48 && event.charCode <= 57'
														required />
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<input class="form-control input-sm" type="text"
														name="fromYYYY" placeholder="yyyy" maxlength="4"
														onkeypress='return event.charCode >= 48 && event.charCode <= 57'
														required />
												</div>
											</div>
										</div>

										<div class="row">
											<div class="fakelabel">Available To</div>
											<div class="col-md-4">
												<div class="form-group">
													<input class="form-control input-sm" type="text"
														name="toDD" placeholder="dd" maxlength="2"
														onkeypress='return event.charCode >= 48 && event.charCode <= 57'
														required />
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<input class="form-control input-sm" type="text"
														name="toMM" placeholder="mm" maxlength="2"
														onkeypress='return event.charCode >= 48 && event.charCode <= 57'
														required />
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<input class="form-control input-sm" type="text"
														name="toYYYY" placeholder="yyyy" maxlength="4"
														onkeypress='return event.charCode >= 48 && event.charCode <= 57'
														required />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-md-7">
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="col-md-6">

											<div class="form-group">
												<label for="file">Property Picture</label> <input
													type="file" name="file" id="fileUpload" required /> <input
													type="hidden" name="type" value="property" />
											</div>

											<div class="form-group">
												<label for="propertytype">Property Type</label> <select
													class="form-control" name="propertytype" required>
													<option value="">Choose</option>
													<option value="0">House</option>
													<option value="1">Flat</option>
													<option value="2">Bungalow</option>
												</select>
											</div>

											<div class="form-group">
												<label for="occupantType">Occupant Type</label> <select
													class="form-control" class="form-control"
													name="occupantType" required>
													<option value="">Choose</option>
													<option value="Student">Student</option>
													<option value="Postgraduate">Postgraduate</option>
													<option value="Undergraduate">Undergraduate</option>
													<option value="Professional">Professional</option>
												</select>
											</div>

											<div class="form-group">
												<label for="billsincluded">Bills Included</label> <select
													class="form-control" class="form-control"
													name="billsincluded" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>

											<div class="form-group">
												<label for="petsallowed">Pets Allowed</label> <select
													class="form-control" name="petsallowed" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>

											<div class="form-group">
												<label for="parking">Parking Spaces</label> <select
													class="form-control" name="parking" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<label for="floorPlan">Floor Plan (optional)</label> <input
													type="file" name="floorPlan" id="fileUploadFloorPlan" />
											</div>

											<div class="form-group">
												<label for="disabledaccess">Disabled Access</label> <select
													class="form-control" name="disabledaccess" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>

											<div class="form-group">
												<label for="smoking">Smoking Allowed</label> <select
													class="form-control" name="smoking" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>

											<div class="form-group">
												<label for="quietarea">Quiet Area</label> <select
													class="form-control" name="quietarea" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>

											<div class="form-group">
												<label for="shortterm">Short Term</label> <select
													class="form-control" name="shortterm" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>

											<div class="form-group">
												<label for="singlegender">Single Gender</label> <select
													class="form-control" name="singlegender" required>
													<option value="">Choose</option>
													<option value="true">Yes</option>
													<option value="false">No</option>
												</select>
											</div>
											<input type="hidden" name="id" value="${currentProperty.id}" />
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" /><br> <br> <input
												type="submit" class="btn btn-success" value="Submit Changes" />
										</div>
									</div>
								</div>
							</div>
						</form>

						<script>
							var form = document.getElementById('addproperty');
							form.noValidate = true;
							form
									.addEventListener(
											'submit',
											function(event) {
												if (!event.target
														.checkValidity()) {
													event.preventDefault();
													alert('Please fill the form'); // error message
													//document.getElementById('errorMessageDiv').style.display = 'block';
												}

												var fileUpload = document
														.getElementById("fileUpload");
												if (typeof (fileUpload.files) != "undefined") {
													var size = parseFloat(
															fileUpload.files[0].size / 1024)
															.toFixed(2);
													if (size > 1024) {
														event.preventDefault();
														alert("The property image you have chosen is over the 1MB limit.");
													}
												} else {
													alert("This browser does not support HTML5.");
												}

												var fileUpload = document
														.getElementById("fileUploadFloorPlan");
												if (typeof (fileUpload.files) != "undefined") {
													var size = parseFloat(
															fileUpload.files[0].size / 1024)
															.toFixed(2);
													if (size > 1024) {
														event.preventDefault();
														alert("The floor plan image you have chosen is over the 1MB limit.");
													}
												} else {
													alert("This browser does not support HTML5.");
												}

											}, false);
						</script>
					</div>
				</div>
			</div>
			<aside id="rhs"></aside>
		</div>
	</div>
</body>
</html>
