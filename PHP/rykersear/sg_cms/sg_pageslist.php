<?php
  //lists all the pages and folders in a flat list.
header('Content-type: text/xml');

$dom = new DOMDocument();
if(file_exists('../sg_info/pages.xml'))
{
  $dom->load('../sg_info/pages.xml');
}
echo('<pages>');
foreach($dom->getElementsByTagname('item') as $item)
{
  $name  = $item->getAttribute('name');
  $id    = $item->getAttribute('id');
  printf('<item name="%s" id="%s" />', $name, $id);
}
foreach($dom->getElementsByTagname('folder') as $item)
{
  $name  = $item->getAttribute('name');
  $id    = $item->getAttribute('id');
  printf('<item name="%s" id="%s" />', $name, $id);
}
echo('</pages>');

?>