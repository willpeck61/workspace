<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<link href="/resources/css/bootstrap.min.css" rel="stylesheet" />
	<!-- Includes the navigationBar -->
	<link
		href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
		rel='stylesheet' type='text/css' />
	<link
		href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
		rel='stylesheet' type='text/css' />
	<link rel="stylesheet" href="/resources/font-awesome.min.css"
		type="text/css" />
	
	<link rel="stylesheet" href="/resources/css/creative.css"
		type="text/css" />
	
	<link rel="stylesheet" href="/resources/css/flatfinder.css" />
	
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script src="/resources/js/bootstrap.js"></script>
	<script>
		$(function() {
			$("#startDatePicker").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#endDatePicker").datepicker({
				dateFormat : 'yy-mm-dd'
			});
		});
	
		$(document)
				.ready(
						function() {
							$("#AdvanceSearchOptions")
									.click(
											function() {
												$(".fullscreen_bg").css({
													'position' : 'absolute'
												});
												$("#AdvancedSearchDiv")
														.slideToggle();
												$(this)
														.text(
																$(this).text() == 'Advanced search options' ? "Fewer Options"
																		: "Advanced search options");
											});
						});
	</script>
	</head>
<body id="background">
	<div class="container">
		<jsp:include page="/resources/navBar/navigationBar.jsp" />
	
		<!-- Includes the navigationBar -->
		<div class="row centered-form">

			<div class="col-md-8 col-md-offset-2 text-center">
				<div class="panel panel-default rounded"
					style="margin-bottom: 2%;">
					<div class="panel-heading roundtop">
						<h2>Hey ${login}, welcome to FlatFinder</h2>
					</div>
					<div class="panel-body">

						<div id="myCarousel" class="carousel slide" data-ride="carousel">
							<!-- Indicators -->
							<ol class="carousel-indicators">
								<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
								<li data-target="#myCarousel" data-slide-to="1"></li>
								<li data-target="#myCarousel" data-slide-to="2"></li>
							</ol>

							<!-- Wrapper for slides -->
							<div class="carousel-inner" role="listbox"
								style="margin-bottom: 10px; max-height: 185px !important;">
								<div class="item active">
									<img src="/resources/img/clocktower.jpg" alt="Map">
								</div>

								<div class="item">
									<img src="/resources/img/trainstation.jpg" alt="Train Station">
								</div>

								<div class="item">
									<img src="/resources/img/header.jpg" alt="Doge">
								</div>
							</div>
						</div>


						<form class="form-signin" action="/search" method="GET"
							modelAttribute="user">

							<div class="panel panel-default">
								<div class="panel-body">
									<div class="col-lg-12">
										<div class="form-group leftAlign">
											<label for="CAP" style="text-align: left">Please
												enter the relevant information</label> <input
												class="form-control input-sm floatlabel"
												placeholder="City / Postcode / Street Name" type="text"
												name="CAP" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label for="minPrice">Min price per week</label> <select
												class="form-control" aria-labelledby="minPriceMenu"
												name="minPrice" id="minPrice">
												<option value="0">No min</option>
												<option value="50">&pound;50</option>
												<option value="55">&pound;55</option>
												<option value="60">&pound;60</option>
												<option value="65">&pound;65</option>
												<option value="70">&pound;70</option>
												<option value="75">&pound;75</option>
												<option value="80">&pound;80</option>
												<option value="85">&pound;85</option>
												<option value="90">&pound;90</option>
												<option value="95">&pound;95</option>
												<option value="100">&pound;100</option>
												<option value="105">&pound;105</option>
												<option value="110">&pound;110</option>
												<option value="115">&pound;115</option>
												<option value="120">&pound;120</option>
												<option value="125">&pound;125</option>
												<option value="130">&pound;130</option>
												<option value="135">&pound;135</option>
												<option value="140">&pound;140</option>
												<option value="145">&pound;145</option>
												<option value="150">&pound;150</option>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label for="maxPrice">Max price per week</label> <select
												class="form-control" name="maxPrice" id="maxPrice">
												<option value="0">No max</option>
												<option value="50">&pound;50</option>
            <option value="55">&pound;55</option>
            <option value="60">&pound;60</option>
            <option value="65">&pound;65</option>
            <option value="70">&pound;70</option>
            <option value="75">&pound;75</option>
            <option value="80">&pound;80</option>
            <option value="85">&pound;85</option>
            <option value="90">&pound;90</option>
            <option value="95">&pound;95</option>
            <option value="100">&pound;100</option>
            <option value="105">&pound;105</option>
            <option value="110">&pound;110</option>
            <option value="115">&pound;115</option>
            <option value="120">&pound;120</option>
            <option value="125">&pound;125</option>
            <option value="130">&pound;130</option>
            <option value="135">&pound;135</option>
            <option value="140">&pound;140</option>
            <option value="145">&pound;145</option>
            <option value="150">&pound;150</option>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label for="minNumRooms">Min bedrooms</label> <select
												class="form-control" name="minNumRooms" id="minNumRooms">
												<option value="0">No min</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
            <option value="8">8</option>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label for="maxNumRooms">Max bedrooms</label> <select
												class="form-control" name="maxNumRooms" id="maxNumRooms">
												<option value="0">No max</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
											</select>
										</div>
									</div>
								</div>
							</div>

							<div id="AdvancedSearchDiv" style="display: none;">
								<hr>
								<div class="panel panel-default">
									<div class="panel-heading leftAlign">Address details</div>
									<div class="panel-body">
										<div class="form-group">
											<input class="form-control input-sm floatlabel"
												placeholder="City" type="text" name="city" />
										</div>
										<div class="form-group">
											<input class="form-control input-sm floatlabel"
												placeholder="Postcode" type="text" name="postcode" />
										</div>
										<div class="form-group">
											<input class="form-control input-sm floatlabel"
												placeholder="Street name" type="text" name="address" />
										</div>
									</div>
								</div>
								<hr>

								<div class="panel panel-default">
									<div class="panel-heading leftAlign">Further Information</div>
									<div class="panel-body">
										<div class="form-group leftAlign">
											<label for="propertyType">Property type</label> <select
												class="form-control" name="propertyType">
												<option value="-1">Any</option>
												<option value="0">House</option>
												<option value="1">Flat</option>
												<option value="2">Bungalow</option>
											</select>
										</div>

										<div class="form-group leftAlign">
											<label for="occupantType">Occupant Type</label> <select
												class="form-control" name="occupantType">
												<option value="null">Any</option>
												<option value="Student">Student</option>
												<option value="Postgraduate">Postgraduate</option>
												<option value="Undergraduate">Undergraduate</option>
												<option value="Professional">Professional</option>
											</select>
										</div>

										<div class="form-inline">
											<div class="input-group form-group">
												<span class="input-group-addon" id="startDate">Start
													Date</span> <input class="form-control" type="text"
													name="startDate" id="startDatePicker"
													placeholder="yyyy-mm-dd" aria-describedby="startDate">
											</div>
											<div class="input-group form-group">
												<span class="input-group-addon" id="endDate">End Date</span>
												<input class="form-control" type="text" name="endDate"
													id="endDatePicker" placeholder="yyyy-mm-dd"
													aria-describedby="endDate">
											</div>
										</div>


									</div>
								</div>
								<hr>
								<div class="panel panel-default">
									<div class="panel-heading leftAlign">Other details</div>
									<div class="panel-body">

										<div class="form-group leftAlign">
											<div class="controls-row">
												<label class="checkbox-inline col-sm-3"><input
													type="checkbox" name="bills_included" value="true">
													Bills Included </label> <label class="checkbox-inline col-sm-3"><input
													type="checkbox" name="pets_allowed" value="true">Pets
													Allowed </label> <label class="checkbox-inline col-sm-3"> <input
													type="checkbox" name="parking" value="true">Parking
												</label> <label class="checkbox-inline col-sm-3"> <input
													type="checkbox" name="disabled_access" value="true">Disabled
													Access
												</label>
											</div>
										</div>

										<div class="form-group leftAlign">
											<div class="controls-row">
												<label class="checkbox-inline col-sm-3"> <input
													type="checkbox" name="smoking" value="true">Smoking
												</label> <label class="checkbox-inline col-sm-3"> <input
													type="checkbox" name="quiet_area" value="true">Quiet
													Area
												</label> <label class="checkbox-inline col-sm-3"> <input
													type="checkbox" name="short_term" value="true">Short
													Term
												</label> <label class="checkbox-inline col-sm-3"> <input
													type="checkbox" name="singleGender" value="true">Single
													Gender
												</label>
											</div>
										</div>


									</div>
								</div>
							</div>
							<a id="AdvanceSearchOptions">Advanced search options</a> <input
								type="hidden" name="page" value="0" /> <input type="submit"
								class="btn btn-lg btn-primary btn-block type" value="Search" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>