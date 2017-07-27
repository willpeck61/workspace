<?php
function AlertSuccess(){
	?>
			<script type='text/javascript'>
			alert('New Record Added / Updated Successfully')
			$(window).load( function(){
				$('#item1').click();
			});
			</script>
			<?php
};

function AlertFailed(){	
	?>
			<script type='text/javascript'>
			alert('No Submission');
			$(window).load( function(){
				$('#item1').click();
			});
			</script>
            <?php
};

function AlertWarn(){
	?>
			<script type='text/javascript'>
			alert('Duplicate Entry Detected - Please Select Customer From Edit Menu')
			$(window).load( function(){
				$('#item1').click();
			});
			</script>
            <?php
};
?>