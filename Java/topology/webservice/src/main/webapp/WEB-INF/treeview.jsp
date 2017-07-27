<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Taxonomy Tree View</title>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/jqtree/tree.jquery.js"></script>
<script type="text/javascript" src="/resources/js/taxonomy.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/resources/css/jqtree.css">
<link rel="stylesheet" href="/resources/css/taxonomy.css">
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1>Taxonomy Builder</h1>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-3">

						<div class="panel-group">
							<form id="classbuildform" action="/rest/class/create"
								method="GET">
								<input type="hidden" id="edit" name="edit" value="" />
								<div class="panel-default panel">
									<div class="panel-body">
										<div class="panel-title">
											<h4>Class Editor</h4>
										</div>
										<div class="form-group">
											<label for="selectclass">Select Existing Class:</label> <select
												id="classes" name="selectclass" class="form-control">
												<option value="" disabled selected>Select an
													existing class..</option>
											</select>
										</div>
										<div class="form-group">
											<label for="classname">New or Edit Class name:</label> <input type="text"
												name="classname" id="class" class="form-control"
												placeholder="Classname" />
										</div>
										<div class="form-group">
											<label for="superclass">Select New or Change Superclass:</label> <select
												id="superclass" name="superclass" class="form-control">
												<option value="" disabled selected>Select an
													existing superclass..</option>
											</select>
										</div>
										<div class="checkbox">
											<label> <input type="checkbox" id="delete"
												name="delete" aria-label="Delete?" /> Delete Class?
											</label>
										</div>
										<div class="form-group">
											<a href="#" onclick="buildClass()" class="btn btn-default">Submit</a>
										</div>
									</div>
								</div>
							</form>
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="panel-title">
										<h4>Property Editor</h4>
									</div>
									<form id="properties">
										<div class="form-group">
											<label for="selectclass">Select Existing Property:</label> 
											<select id="propertysel" name="selectproperty" class="form-control">
												<option value="" disabled selected>Select an
													existing property..</option>
											</select>
										</div>
										<div class="form-group">
											<label for="propertyname">New or Edit Property Name:</label> <input
												type="text" name="propertyname" id="propertyname" class="form-control" />
										</div>
										<div class="form-group">
											<label for="datatype">Select / Change Data-type:</label> <select
												id="datatype" name="datatype" class="form-control">
												<option value="" disabled selected>Select a
													datatype..</option>
											</select>
										</div>
										<div class="form-group">
											<label for="classname">Assign to an Existing Class:</label> <select
												id="assignclass" name="classname" class="form-control">
												<option value="" disabled selected>Select an
													existing class..</option>
											</select>
										</div>
										<div class="checkbox">
											<label> <input type="checkbox" id="deleteprop"
												name="delete" aria-label="Delete?" /> Delete Property? (only for String, int, boolean or double)
											</label>
										</div>
									   <div class="form-group">
											<a href="#" onclick="addProperty()" class="btn btn-default">Submit</a>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div id="buildresult"></div>
					</div>
					<div class="col-md-4">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="panel-title">
									<h4>Object Repository</h4>
								</div>
								<div class="panel-group">
									<div class="panel panel-default">
										<div class="panel-body">
											<form id="createinstance">
												<div class="form-group">
													<label for="classforinst">Selected Class</label> <input
														type="text" id="classforinst" class="form-control"
														name="classforinst" placeholder="No Class Selected"
														disabled />
												</div>
											</form>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-body">
											<form id="instances">
												<div class="form-group">
													<label for="instance">Select Instance</label> <select
														class="form-control" name="instance" id="instancesel">
														<option value="" disabled selected>Select an
															existing instance..</option>
													</select>
												</div>
											<ul id="nodedata">
												<li>Select a class to display data..</li>
											</ul>
											<input type="hidden" name="classname" id="instclass" value="" />
											<div id="submitinst"></div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-5">
					<div class="panel panel-default">
					<div class="panel-body">
					<div class="panel-title">
						<h4>Current Tree</h4></div>
						<div id="tree"></div>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>