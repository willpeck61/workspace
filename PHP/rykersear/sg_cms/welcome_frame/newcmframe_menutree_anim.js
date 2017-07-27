function init_menutree_anim()
{
$ml('.menutree_child .menutreetop li').hover(
	function(){
		var ul = $ml(this).find('ul:first');
		if (ul) {
			ul.stop();
			ul.css('opacity', 0);
			ul.css('display', 'block');
			ul.animate({
				opacity: 1
			}, 400);
		}
	},
	function(){ 
		var ul = $ml(this).find('ul:first');
		if (ul) {
			ul.stop();
			ul.animate({
				opacity: 0
			}, 400, 'linear', function(){
				$ml(this).css('display', 'none');
			});
		}
	});
}