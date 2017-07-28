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

<script>
	/* Scrolls to bottom of page is the edit button is clicked*/
	var boolRoomId = $
	{
		roomid
	}

	if (boolRoomId != null) {
		$(function() {
			$('html, body').animate({
				scrollTop : $('#bottomDiv').position().top
			}, 'slow');
		});

	}
</script>
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
						<h2 style="text-align: center">Edit rooms <small>at ${currentProperty.houseNum} ${currentProperty.address1}</small></h2>
						<img width="100%" src="/resources/img/mapbanner.jpg" />

						<div class="panel panel-default" style="margin-top: 10px;">
							<div class="panel-body">
								<div class="col-md-12 tableHeading">
									<div class="col-md-3">Room name</div>
									<div class="col-md-3">Occupied</div>
									<div class="col-md-3"></div>
									<div class="col-md-3"></div>
								</div>

								<c:forEach var="list" items="${listing}">
									<div class="col-md-12 propertyRow">
										<div class="col-md-3">${list.headline}</div>
										<div class="col-md-3">
											<c:choose>
												<c:when test="${list.occupiedByUsername == 'null'}">
										         Available
													</c:when>
												<c:otherwise>
													<a
														href="/view-buddy-profile?username=${list.occupiedByUsername}">${list.occupiedByUsername}</a>
													<br />
												</c:otherwise>
											</c:choose>
										</div>
										<sec:authorize access="hasRole('ADMIN')">
											<div class="col-md-3">
												<a
													href="/admin-dashboard/listings/editRoom?id=${list.propertyId}&&roomid=${list.id}"
													class="btn btn-info" role="button">Edit Room</a>
											</div>
											<div class="col-md-3">
												<a
													href="/admin-dashboard/listings/deleteRoom?id=${list.propertyId}&&roomid=${list.id}"
													class="btn btn-info" role="button"
													onclick="return confirm('Are you sure?')">Delete Room</a>
											</div>
										</sec:authorize>
										<sec:authorize access="hasRole('LANDLORD')">
											<div class="col-md-3">
												<a
													href="/landlord-dashboard/manage/editRoom?id=${list.propertyId}&&roomid=${list.id}"
													class="btn btn-info" role="button"
													style="margin-bottom: 8px;">Edit Room</a>
											</div>
											<div class="col-md-3">
												<a
													href="/landlord-dashboard/deleteRoom?id=${list.propertyId}&&roomid=${list.id}"
													class="btn btn-info" role="button"
													onclick="return confirm('Are you sure?')"
													style="margin-bottom: 8px;">Delete Room</a>
											</div>
										</sec:authorize>
									</div>
								</c:forEach>
							</div>
						</div>

						<hr />

						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-body">

									<div class="form-group">
										<c:choose>
											<c:when test="${roomid != null}">
												<h4>
													<u>Currently editing: <i>${currentRoom.headline}</i></u>
												</h4>
												<form role="form" action="/submitUpdateRoom"
													enctype="multipart/form-data" method="post" id="addroom"
													name="addroom">
													<div class="row">
														<div class="col-md-8">
															<input type="hidden" name="propertyid"
																value="${currentProperty.id}" /> <input type="hidden"
																name="roomid" value="${currentRoom.id}" /> <input
																class="form-control input-sm floatlabel" type="text"
																name="headline" placeholder="Headline"
																value="${currentRoom.headline}" required /><br>
															<textarea class="form-control input-sm floatlabel"
																name="description" placeholder="Room Description"
																required>${currentRoom.description}</textarea>
															<br /> <input class="form-control input-sm floatlabel"
																type="text" name="width" placeholder="Width in feet"
																onkeypress='return event.charCode >= 48 && event.charCode <= 57'
																value="${currentRoom.width}" required /><br> <input
																class="form-control input-sm floatlabel" type="text"
																name="height" placeholder="Height in feet"
																onkeypress='return event.charCode >= 48 && event.charCode <= 57'
																value="${currentRoom.height}" required /><br>
														</div>

														<div class="col-md-4">
															<div class="form-group">
																<label for="file">Room Picture</label> <input
																	type="file" name="file" id="fileUpload"
																	value="undefined" /> <input type="hidden" name="type"
																	value="room" /> <br />
															</div>
															<div class="form-group">
																<label class="form-group">Floor Plan (not
																	required)</label> <input type="file" name="floorPlan"
																	id="floorPlan" value="undefined" /> <br /> <br />
															</div>
														</div>
													</div>

													<div class="row">
														<div class="col-md-4">
															<div class="input-group">
																<label for="furnished">Furnished</label> <select
																	name="furnished" required class="form-control">
																	<option value="">Default</option>
																	<option value="true"
																		${currentRoom.furnished == true ? 'selected="selected"' : ''}>Yes</option>
																	<option value="false"
																		${currentRoom.furnished == false ? 'selected="selected"' : ''}>No</option>
																</select>
															</div>
														</div>

														<div class="col-md-4">
															<div class="input-group">
																<label for="ensuite">Ensuite</label> <select
																	name="ensuite" required class="form-control">
																	<option value="">Default</option>
																	<option value="true"
																		${currentRoom.ensuite == true ? 'selected="selected"' : ''}>Yes</option>
																	<option value="false"
																		${currentRoom.ensuite == false ? 'selected="selected"' : ''}>No</option>
																</select>
															</div>
														</div>

														<div class="col-md-4">
															<div class="input-group">
																<label for="doubleRoom">Double Room</label> <select
																	name="doubleRoom" required class="form-control">
																	<option value="">Default</option>
																	<option value="true"
																		${currentRoom.doubleRoom == true ? 'selected="selected"' : ''}>Yes</option>
																	<option value="false"
																		${currentRoom.doubleRoom == false ? 'selected="selected"' : ''}>No</option>
																</select>
															</div>
														</div>

													</div>


													<div class="col-md-12" style="margin-top: 2%">
														<input type="submit"
															class="btn btn-success col-md-3 col-md-offset-2"
															value="Update room" />

														<!-- 
                  						Both, admins and landlords will use this form, as a result
                  						the code below tells the form where to redirect the current user.
                  					 -->
														<sec:authorize access="hasRole('LANDLORD')">
															<a
																href="/landlord-dashboard/manage/editRoom?id=${currentProperty.id}"
																class="btn btn-info col-md-3 col-md-offset-2"
																role="button">Back</a>
														</sec:authorize>
														<sec:authorize access="hasRole('ADMIN')">
															<a
																href="/admin-dashboard/listings/editRoom?id=${currentProperty.id}"
																class="btn btn-info col-md-3 col-md-offset-2"
																role="button">Back</a>
														</sec:authorize>
														<input type="hidden" name="${_csrf.parameterName}"
															value="${_csrf.token}" />
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

												</form>
												<script>
													var form = document
															.getElementById('addroom');
													form.noValidate = true;
													form
															.addEventListener(
																	'submit',
																	function(
																			event) {
																		if (!event.target
																				.checkValidity()) {
																			event
																					.preventDefault();
																			alert('Please, fill the form'); // error message
																			//document.getElementById('errorMessageDiv').style.display = 'block';
																		}

																		var propertyImageUploadCheck = $(
																				'#fileUpload')
																				.val();
																		if (propertyImageUploadCheck != '') {
																			var fileUpload = document
																					.getElementById("fileUpload");
																			var size = parseFloat(
																					fileUpload.files[0].size / 1024)
																					.toFixed(
																							2);
																			if (size > 1024) {
																				event
																						.preventDefault();
																				alert("The property image you have chosen is over the 1mb limit");
																			}
																		}

																		var propertyFloorPlanUploadCheck = $(
																				'#floorPlan')
																				.val();
																		if (propertyFloorPlanUploadCheck != '') {
																			var floorPlanFileUpload = document
																					.getElementById("floorPlan");
																			var size1 = parseFloat(
																					floorPlanFileUpload.files[0].size / 1024)
																					.toFixed(
																							2);
																			if (size1 > 1024) {
																				event
																						.preventDefault();
																				alert("The floor plan image you have chosen is over the 1mb limit");
																			}
																		}

																	}, false);
												</script>
											</c:when>
											<c:otherwise>
												<h4 style="text-decoration: underline; margin-left: 5px;">Add
													a new room</h4>
												<div class="row">
													<form role="form" action="/submitAddedRoom"
														enctype="multipart/form-data" method="post" id="addroom"
														name="addroom">
														<div class="col-md-8">
															<input type="hidden" name="propertyid"
																value="${currentProperty.id}" /> <input
																class="form-control input-sm floatlabel" type="text"
																name="headline" placeholder="Headline" required /><br>

															<textarea class="form-control input-sm floatlabel"
																name="description" placeholder="Room Description"
																required></textarea>

															<br /> <input class="form-control input-sm floatlabel"
																type="text" name="width" placeholder="Width in feet"
																onkeypress='return event.charCode >= 48 && event.charCode <= 57'
																required /><br> <input
																class="form-control input-sm floatlabel" type="text"
																name="height" placeholder="Height in feet"
																onkeypress='return event.charCode >= 48 && event.charCode <= 57'
																required /><br>
														</div>

														<div class="col-md-4">
															<div class="form-group">
																<label for="file">Room Picture</label> <input
																	type="file" name="file" id="fileUpload"
																	value="undefined" required /> <input type="hidden"
																	name="type" value="room" />
															</div>

															<div class="form-group">
																<label for="floorPlan">Floor Plan (not required)</label>
																<input type="file" name="floorPlan" id="floorPlan"
																	value="undefined" />
															</div>
														</div>
												</div>

												<div class="row">
													<div class="col-md-4">
														<div class="form-group">
															<label for="furnished">Furnished</label> <select
																name="furnished" class="form-control" required>
																<option value="">Default</option>
																<option value="true">Yes</option>
																<option value="false">No</option>
															</select>
														</div>
													</div>

													<div class="col-md-4">
														<div class="form-group">
															<label for="ensuite">Ensuite</label> <select
																name="ensuite" class="form-control" required>
																<option value="">Default</option>
																<option value="true">Yes</option>
																<option value="false">No</option>
															</select>
														</div>
													</div>

													<div class="col-md-4">
														<div class="form-group">
															<label for="doubleRoom">Double Room</label> <select
																name="doubleRoom" class="form-control" required>
																<option value="">Default</option>
																<option value="true">Yes</option>
																<option value="false">No</option>
															</select> <input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
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
													value="Add room" />
												</form>
												<script>
													var form = document
															.getElementById('addroom');
													form.noValidate = true;
													form
															.addEventListener(
																	'submit',
																	function(
																			event) {
																		if (!event.target
																				.checkValidity()) {
																			event
																					.preventDefault();
																			alert('Please, fill the form'); // error message
																			//document.getElementById('errorMessageDiv').style.display = 'block';
																		}

																		var propertyImageUploadCheck = $(
																				'#fileUpload')
																				.val();
																		if (propertyImageUploadCheck != '') {
																			var fileUpload = document
																					.getElementById("fileUpload");
																			var size = parseFloat(
																					fileUpload.files[0].size / 1024)
																					.toFixed(
																							2);
																			if (size > 1024) {
																				event
																						.preventDefault();
																				alert("The property image you have chosen is over the 1mb limit");
																			}
																		}

																		var propertyFloorPlanUploadCheck = $(
																				'#floorPlan')
																				.val();
																		if (propertyFloorPlanUploadCheck != '') {
																			var floorPlanFileUpload = document
																					.getElementById("floorPlan");
																			var size1 = parseFloat(
																					floorPlanFileUpload.files[0].size / 1024)
																					.toFixed(
																							2);
																			if (size1 > 1024) {
																				event
																						.preventDefault();
																				alert("The floor plan image you have chosen is over the 1mb limit");
																			}
																		}

																	}, false);
												</script>
												<br />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<aside id="rhs"></aside>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>