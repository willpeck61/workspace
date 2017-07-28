(function($) {

$(document)
    .on( 'hidden.bs.modal', '.modal', function() {
        $('nav').removeClass('add-padding-15');
    })
    .on( 'show.bs.modal', '.modal', function() {
        // Bootstrap's .modal-open class hides any scrollbar on the body,
        // so if there IS a scrollbar on the body at modal open time, then
        // add a right margin to take its place.
        if ( $(window).height() < $(document).height() ) {
			$('nav').addClass('add-padding-15').addClass('stop-transition');
        }
    });

})(window.jQuery);
