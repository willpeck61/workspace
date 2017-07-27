<?php 
$raise_permission_levels = false; 
/*
if true, then select folders will have their permission levels set to 777 and certain files 666.
if the server has PHP operating as one user and your FTP login as another, then $raise_permission_levels should be true.
otherwise, if the server is configured such that PHP and your FTP login are both the same user, then $raise_permission_levels should be false.
*/
?>