// JavaScript Document
$(document).ready(function(){
$('article').not('.item0').hide();
$('nav a').click(function(){
	var chkitem = $(this).attr('id');
	$('article').not('.'+chkitem).hide();
	$('.'+chkitem).fadeIn();
	});
});

