<?php echo "Hello World!!" ?>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>EE Services :: Workshop Management System</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="script.js"></script>
</head>
<header>
</header>
<section id="leftmenu">
<nav>
	<ul>
		<li><a href="#" id="item1">>>New Customer</a></li>
    	<li><a href="#" id="item2">>>New Vehicle</a></li>
    	<li><a href="#" id="item3">>>New Employee</a></li>
    	<li><a href="#" id="item4">>>New Supplier</a></li>
    	<li><a href="#" id="item5">>>Order System</a></li>
        <ul>
        	<li><a href="#" id="item6">>>New Order</a></li>
            <li><a href="#" id="item7">>>Order Enquiry</a></li>
        </ul>
    </ul>
</nav>
</section>
<section id="main">
	<article id="newCustomer">
	<h1>New Customer Details</h1>
	<form action="#">
		<ul class="inputform">
		<li class="formline"><label for="custTitle">Title</label></li>
        	<li class="formlineb"><select name="custTitle" required>
            							<option value="mr">Mr</option>
                                        <option value="mrs">Mrs</option>
                                        <option value="miss">Miss</option>
                                        <option value="dr">Dr</option>
                                        <option value="prof">Prof</option>
                                  </select></li>
    	<li class="formlinealt"><label for="custFirstName">First Name</label></li><li class="formlinealtb"><input type="text" id="custFirstName" required></li>
    	<li class="formline"><label for="custLastName">Last Name</label></li><li class="formlineb"><input type="text" id="custLastName" required></li>
    	<li class="formlinealt"><label for="custAddress1">Street Address</label></li><li class="formlinealtb"><input type="text" id="custAddress1" required></li>
    	<li class="formline"><label for="custAddress2">Town</label></li><li class="formlineb"><input type="text" id="custAddress2" required></li>
    	<li class="formlinealt"><label for="custAddress3">City</label></li><li class="formlinealtb"><input type="text" id="custAddress3" required></li>
    	<li class="formline"><label for="custPostcode">Postcode</label></li><li class="formlineb"><input type="text" id="Postcode" required></li>
    	<li class="formlinealt"><button type="submit">Submit</button></li>
		</ul>
	</form>
	</article>
	<article id="newVehicle">
	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed auctor sit amet nunc ac dictum. Donec sagittis mi ligula, suscipit maximus urna fermentum non. Proin sit amet efficitur massa. Nam non quam mauris. Fusce condimentum lectus quis felis gravida, ac ornare diam egestas. Sed malesuada sapien nisi, non fringilla lacus pulvinar mattis. Mauris enim enim, mattis et fermentum nec, aliquam non arcu. Morbi blandit ultricies metus non pellentesque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aenean purus quam, mattis vitae malesuada nec, volutpat ac libero. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Maecenas eget mollis ipsum. Morbi ex mi, tincidunt rhoncus lacus non, bibendum pulvinar lacus. Morbi lobortis, tortor eu posuere gravida, est enim tristique neque, vel lobortis magna turpis a elit.

Nulla in faucibus massa, in sagittis erat. Phasellus dictum eu dui in tristique. Pellentesque fringilla arcu eget dapibus suscipit. Nam dapibus est magna, nec interdum massa mattis et. Sed ut pretium ante. Etiam interdum sit amet eros ac ultrices. Nullam pharetra blandit sodales. In sollicitudin nunc velit, eget tincidunt purus gravida eget. Sed a accumsan eros. Praesent feugiat commodo lacus sit amet rutrum. Vivamus luctus lectus nec finibus suscipit.

Integer sagittis, arcu vel fermentum scelerisque, eros sapien sagittis enim, sit amet fermentum urna nulla in ipsum. Proin sit amet egestas ante. Vestibulum in vehicula ex. Integer in pellentesque ipsum, eget placerat elit. Integer sit amet lectus augue. Pellentesque pulvinar ac ipsum at euismod. Etiam ac lacus ut turpis ultricies ullamcorper ut vehicula mi. Morbi ullamcorper diam fermentum consectetur sagittis. Curabitur volutpat, ipsum id molestie porttitor, erat tortor condimentum ipsum, at facilisis elit ex ac lectus. Mauris ut malesuada erat, id efficitur lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nam elementum est eget augue ultricies, id varius lorem sollicitudin. Quisque malesuada pharetra diam quis laoreet.

In cursus ex sit amet mauris consequat vestibulum. Mauris purus massa, ullamcorper eu pellentesque nec, dictum ut odio. Ut purus elit, eleifend in risus at, rutrum iaculis orci. Vivamus non malesuada tortor. Integer aliquet, lectus eu elementum scelerisque, enim turpis dictum nisl, ut imperdiet nulla dolor ac lectus. In tempor tincidunt condimentum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed imperdiet accumsan mauris non ornare. Suspendisse rutrum aliquet eros sed feugiat.

Maecenas commodo neque sem, tempor feugiat massa maximus a. Nulla tincidunt metus suscipit nisl imperdiet pulvinar. Etiam lobortis massa at mi venenatis, quis ornare lorem congue. Nullam fermentum finibus elit, sed facilisis libero dignissim id. Suspendisse potenti. Morbi arcu augue, dapibus vitae pharetra ut, aliquam vitae dui. Maecenas a tellus et arcu vehicula efficitur posuere vitae mauris. Integer eget felis et quam varius commodo. Praesent sit amet ligula euismod, gravida purus quis, aliquam lectus. Phasellus sit amet velit eget velit dapibus sagittis pellentesque sed lorem. Integer vel vulputate velit, vel mattis lorem. Nam a leo viverra, vehicula felis quis, rhoncus justo. Nulla nunc dolor, tincidunt nec sagittis at, faucibus vel magna. Morbi finibus sagittis nisl vel tempor. Etiam imperdiet, lorem nec elementum consequat, risus tortor imperdiet arcu, vitae porta lacus lorem semper mauris.</p>
	</article>
</section>
<footer>
</footer>
</body>
</html>
