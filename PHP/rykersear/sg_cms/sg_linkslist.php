var tinyMCELinkList = new Array(
  <?php 
    // Name, URL
    $ptsvar = $_GET['pts'];
    if(get_magic_quotes_gpc()){ $ptsvar = stripslashes($ptsvar); }

    $dom = new DOMDocument();
    if(file_exists('../sg_info/pages.xml'))
    {
      $dom->load('../sg_info/pages.xml');
    }
    foreach($dom->getElementsByTagname('item') as $item)
    {
      $name = $item->getAttribute('name');
      $path = $item->getAttribute('path');
      printf('["%s", "%s"],', $name, $ptsvar . $path);
    }
    printf('["SiteGrinder", "http://www.medialab.com/sitegrinder3"]');
  ?>
 );