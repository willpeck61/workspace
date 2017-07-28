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
<script src="/resources/script/leaflet.js"></script>
<link rel="stylesheet" href="/resources/css/leaflet.css" />

<script type="text/javascript">
	var form = document.getElementById('addproperty');
	form.noValidate = true;
	form
			.addEventListener(
					'submit',
					function(event) {
						if (!event.target.checkValidity()) {
							event.preventDefault();
							alert('Please, fill the form'); // error message
							//document.getElementById('errorMessageDiv').style.display = 'block';
						}

						var propertyImageUploadCheck = $('#fileUpload').val();
						if (propertyImageUploadCheck != '') {
							var fileUpload = document
									.getElementById("fileUpload");
							var size = parseFloat(
									fileUpload.files[0].size / 1024).toFixed(2);
							if (size > 1024) {
								event.preventDefault();
								alert("The property image you have chosen is over the 1mb limit");
							}
						}

						var propertyFloorPlanUploadCheck = $('#floorPlan')
								.val();
						if (propertyFloorPlanUploadCheck != '') {
							var floorPlanFileUpload = document
									.getElementById("floorPlan");
							var size1 = parseFloat(
									floorPlanFileUpload.files[0].size / 1024)
									.toFixed(2);
							if (size1 > 1024) {
								event.preventDefault();
								alert("The floor plan image you have chosen is over the 1mb limit");
							}
						}

					}, false);
</script>

<style>
.btn-info.active.focus, 
.btn-info.active:focus, 
.btn-info.active:hover, 
.btn-info:active.focus, 
.btn-info:active:focus, 
.btn-info:active:hover,
.btn-info:active,
.btn-info.focus, 
.btn-info:focus,
.btn-info:hover {
	box-shadow:none;
    border:0;
	background-color: #5bc0de;
}
#map {
	height: 350px;
	width: 100%;
}

</style>

</head>
<body id="background">
	<div class="container">
	<jsp:include page="/resources/navBar/navigationBar.jsp" />
	<!-- Includes the navigationBar -->
		<div class="row centered-form">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-body">
						<p class="lead">Navigate</p>
						<div class="list-group">
							
							<sec:authorize access="hasRole('LANDLORD')">
							<a href="/landlord-dashboard/create" class="list-group-item"
								role="button">Create new listing</a> <a
								href="/landlord-dashboard/manage" class="list-group-item active"
								role="button">Edit existing listing</a> <a
								href="/landlord-dashboard/statistics" class="list-group-item"
								role="button">View statistics</a><br />
								<a href="/landlord-dashboard/manage/" class="list-group-item">Go
									Back</a>
							</sec:authorize>
							<sec:authorize access="hasRole('ADMIN')">
							<a href="/admin-dashboard/users" class="list-group-item"
								role="button">Manage users</a> <a
								href="/admin-dashboard/listings" class="list-group-item active"
								role="button">Edit existing listing</a> <a
								href="/admin-dashboard/reports" class="list-group-item"
								role="button">View reports</a><br />
								<a href="/admin-dashboard/listings/" class="list-group-item">Go
									Back</a>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-body">

						<h2 style="text-align: center">
							Edit property <small>${currentProperty.houseNum}
								${currentProperty.address1}</small>
						</h2>
						
						<div id="map">
							<script>
							var loc = [${lat}, ${lng}];
							var map = L.map('map').setView(loc, 16);
							
							L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
							}).addTo(map);
							
							L.marker(loc).addTo(map)
							    .bindPopup("${information.postcode}")
							</script>
						</div>


						<script>
							var fromDate = ${startDateString};
							var toDate = ${endDateString};

							var fromDD = fromDate.substring(0, 2);
							var fromMM = fromDate.substring(3, 5);
							var fromYYYY = fromDate.substring(6, 10);

							var toDD = toDate.substring(0, 2);
							var toMM = toDate.substring(3, 5);
							var toYYYY = toDate.substring(6, 10);
						</script>

						<form role="form" action="/submitEditedProperty" method="post"
							enctype="multipart/form-data" id="addproperty" name="addproperty">

							<div class="col-md-12" style="margin: 20px 0 20px 0;">
								<div class="col-md-4">
									<a class="btn btn-info" style="cursor:default;"> This property is: <c:if
											test="${currentProperty.listed==false}">
											<b><i>unlisted</i></b>
										</c:if> <c:if test="${currentProperty.listed==true}">
											<b><i>listed</i></b>
										</c:if></a>
								</div>

								<sec:authorize access="hasRole('LANDLORD')">

									<!-- <td width="33%"><a href="/" class="btn btn-info"
											style="width: 90%;" role="button">Hide Property ~ does
												nothing</a></td> -->

									<div class="col-md-4">
										<c:choose>
											<c:when test="${currentProperty.listed==true}">
												<a
													href="/landlord-dashboard/manage/unlistProperty?id=${currentProperty.id}&&listed=false"
													class="btn btn-warning"
													onclick="return confirm('Are you sure you wish to unlist this property?')"
													role="button">Unlist Property</a>
											</c:when>
											<c:otherwise>
												<a
													href="/landlord-dashboard/manage/unlistProperty?id=${currentProperty.id}&&listed=true"
													class="btn btn-warning"
													onclick="return confirm('Are you sure you wish to unlist this property?')"
													role="button">Re-List Property</a>
											</c:otherwise>
										</c:choose>
									</div>

									<div class="col-md-4">
										<a
											href="/landlord-dashboard/manage/deleteProperty?id=${currentProperty.id}"
											class="btn btn-danger"
											onclick="return confirm('Are you sure you wish to delete this property?')"
											role="button">Delete Property</a>
									</div>
								</sec:authorize>

								<sec:authorize access="hasRole('ADMIN')">

									<!-- <td width="33%"><a href="/" class="btn btn-info"
											style="width: 90%;" role="button">Hide Property ~ does
												nothing</a></td> -->

									<div class="col-md-4">
										<c:choose>
											<c:when test="${currentProperty.listed==true}">
												<a
													href="/admin-dashboard/listings/unlistProperty?id=${currentProperty.id}&&listed=false"
													class="btn btn-warning"
													onclick="return confirm('Are you sure you wish to unlist this property?')"
													role="button">Unlist Property</a>
											</c:when>
											<c:otherwise>
												<a
													href="/admin-dashboard/listings/unlistProperty?id=${currentProperty.id}&&listed=true"
													class="btn btn-warning"
													onclick="return confirm('Are you sure you wish to unlist this property?')"
													role="button">Re-List Property</a>
											</c:otherwise>
										</c:choose>
									</div>

									<div class="col-md-4">
										<a
											href="/admin-dashboard/listings/deleteProperty?id=${currentProperty.id}"
											class="btn btn-danger"
											onclick="return confirm('Are you sure you wish to delete this property?')"
											role="button">Delete Property</a>

									</div>
								</sec:authorize>
							</div>

							<div class="col-md-6">
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="form-group">
											<label for="price">Price per week</label> <input
												value="${currentProperty.pricePerWeek}"
												class="form-control input-sm floatlabel" type="text"
												name="price" placeholder="Price Per Week"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												required />
										</div>

										<div class="form-group">
											<label for="houseNum">House number</label> <input
												value="${currentProperty.houseNum}"
												class="form-control input-sm floatlabel" type="text"
												name="houseNum" placeholder="House Number" required />
										</div>

										<div class="form-group">
											<label for="address1">Address 1</label> <input
												value="${currentProperty.address1}"
												class="form-control input-sm floatlabel" type="text"
												name="address1" placeholder="Address 1" required />
										</div>

										<div class="form-group">
											<label for="address2">Address 2</label> <input
												value="${currentProperty.address2}"
												class="form-control input-sm floatlabel" type="text"
												name="address2" placeholder="Address 2" />
										</div>

										<div class="form-group">
											<label for="address3">City</label> <input
												value="${currentProperty.address3}"
												class="form-control input-sm floatlabel" type="text"
												name="address3" placeholder="City" required />
										</div>

										<div class="form-group">
											<label for="postcode">Postcode</label> <input
												value="${currentProperty.postcode}"
												class="form-control input-sm floatlabel" type="text"
												name="postcode" placeholder="Postcode" required />
										</div>
									</div>
								</div>
							</div>

							<div class="col-md-6">
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="fakelabel">Available From</div>
										<div class="col-md-4" style="padding-left: 0">
											<input class="form-control input-sm" type="text"
												name="fromDD" placeholder="dd" maxlength="2"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												id="fromDD" required />
										</div>
										<div class="col-md-4" style="padding-left: 0">
											<input class="form-control input-sm" type="text"
												name="fromMM" placeholder="mm" maxlength="2"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												id="fromMM" required />
										</div>
										<div class="col-md-4" style="padding-left: 0">
											<input class="form-control input-sm" type="text"
												name="fromYYYY" placeholder="yyyy" maxlength="4"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												id="fromYYYY" required />
										</div>

										<script>
											document.getElementById('fromDD').value = fromDD;
											document.getElementById('fromMM').value = fromMM;
											document.getElementById('fromYYYY').value = fromYYYY;
										</script>

										<div class="fakelabel" style="margin-top: 5px">Available
											to</div>
										<div class="col-md-4" style="padding-left: 0">
											<input class="form-control input-sm" type="text" name="toDD"
												placeholder="dd" maxlength="2"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												id="toDD" required />
										</div>
										<div class="col-md-4" style="padding-left: 0">
											<input class="form-control input-sm" type="text" name="toMM"
												placeholder="mm" maxlength="2"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												id="toMM" required />
										</div>
										<div class="col-md-4" style="padding-left: 0">
											<input class="form-control input-sm" type="text"
												name="toYYYY" placeholder="yyyy" maxlength="4"
												onkeypress='return event.charCode >= 48 && event.charCode <= 57'
												id="toYYYY" required />
										</div>

										<script>
											document.getElementById('toDD').value = toDD;
											document.getElementById('toMM').value = toMM;
											document.getElementById('toYYYY').value = toYYYY;
										</script>
									</div>
								</div>

								<div class="panel panel-default">
									<div class="panel-body">

										<div class="form-group">
											<label for="advertTitle">Advert Title</label> <input
												value="${currentProperty.headline}"
												class="form-control input-sm floatlabel" type="text"
												name="advertTitle" placeholder="Advert Title" required />
										</div>



										<div class="form-group">
											<label for="description">Description</label>
											<textarea class="form-control input-sm floatlabel"
												name="description" placeholder="Property Description"
												style="height: 100px" required>${currentProperty.description}</textarea>
										</div>


										<div class="form-group">
											<label for="localArea">Local Area</label>
											<textarea class="form-control input-sm floatlabel"
												name="localArea"
												placeholder="Description of the local area (optional)">${currentProperty.localArea}</textarea>
										</div>

									</div>
								</div>
							</div>

							<div class="col-md-12">
								<div class="panel panel-default">
									<div class="panel-body">

										<div class="col-md-6">
											<div class="form-group">
												<label for="fileUpload">Property Picture</label> <input
													type="file" name="file" id="fileUpload" value="undefined" />
												<input type="hidden" name="type" value="property" />
											</div>

											<div class="form-group">
												<label for="floorPlan">Floor Plan (optional)</label> <input
													type="file" name="floorPlan" id="floorPlan"
													value="undefined" />
											</div>

											<div class="form-group">
												<label for="propertytype">Property Type</label> <select
													class="form-control" class="form-control"
													name="propertytype" required>
													<option
														${currentProperty.type == 0 ? 'selected="selected"' : ''}
														value="0">House</option>
													<option
														${currentProperty.type == 1 ? 'selected="selected"' : ''}
														value="1">Flat</option>
													<option
														${currentProperty.type == 2 ? 'selected="selected"' : ''}
														value="2">Bungalow</option>
												</select>
											</div>

											<div class="form-group">
												<label for="occupantType">Occupant Type</label> <select
													class="form-control" name="occupantType" required>
													<option
														${currentProperty.occupantType == "Student" ? 'selected="selected"' : ''}
														value="Student">Student</option>
													<option
														${currentProperty.occupantType == "Postgraduate" ? 'selected="selected"' : ''}
														value="Postgraduate">Postgraduate</option>
													<option
														${currentProperty.occupantType == "Undergraduate" ? 'selected="selected"' : ''}
														value="Undergraduate">Undergraduate</option>
													<option
														${currentProperty.occupantType == "Professional" ? 'selected="selected"' : ''}
														value="Professional">Professional</option>
												</select>
											</div>
											<div class="form-group">
												<label for="billsincluded">Bills Included</label> <select
													class="form-control" name="billsincluded" required>
													<option
														${currentProperty.billsIncluded == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.billsIncluded == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
											<div class="form-group">
												<label for="petsallowed">Pets Allowed</label> <select
													class="form-control" name="petsallowed" required>
													<option
														${currentProperty.petsAllowed == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.petsAllowed == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label for="parking">Parking Spaces</label> <select
													class="form-control" name="parking" required>
													<option
														${currentProperty.parking == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.parking == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
											<div class="form-group">
												<label for="disabledaccess">Disabled Access</label> <select
													class="form-control" name="disabledaccess" required>
													<option
														${currentProperty.disabledAccess == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.disabledAccess == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
											<div class="form-group">
												<label for="smoking">Smoking Allowed</label> <select
													class="form-control" name="smoking" required>
													<option
														${currentProperty.smoking == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.smoking == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
											<div class="form-group">
												<label for="quietarea">Quiet Area</label> <select
													class="form-control" name="quietarea" required>
													<option
														${currentProperty.quietArea == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.quietArea == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
											<div class="form-group">
												<label for="shortterm">Short Term</label> <select
													class="form-control" name="shortterm" required>
													<option
														${currentProperty.shortTerm == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.shortTerm == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
											<div class="form-group">
												<label for="singlegender">Single Gender</label> <select
													class="form-control" name="singlegender" required>
													<option
														${currentProperty.singleGender == true ? 'selected="selected"' : ''}
														value="true">Yes</option>
													<option
														${currentProperty.singleGender == false ? 'selected="selected"' : ''}
														value="false">No</option>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>

							<!-- 
                  						Both, admins and landlords will use this form, as a result
                  						the code below tells the form where to redirect the current user.
                  					 -->
							<sec:authorize access="hasRole('LANDLORD')">
								<input type="hidden" name="location"
									value="landlord-dashboard/manage" />
							</sec:authorize>
							<sec:authorize access="hasRole('ADMIN')">
								<input type="hidden" name="location"
									value="admin-dashboard/listings" />
							</sec:authorize>

							<input type="submit" class="btn btn-success"
								value="Submit Changes" /> <input type="hidden" name="id"
								value="${currentProperty.id}" /> <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<aside id="rhs"></aside>
	</div>
</body>
</html>