<?php
/* -------------------------------------*/
/*
    SiteGrinder 3 PayPal Standard Support
    v 0.4
        
*/
/* -------------------------------------*/


 session_start();
 include('verify_login.php');
 if(!verify_login()){ exit(); }
 
 
 include('timestamps.php');
 
 /*-------- utility functions ----------*/
 function stripFormSlashes($arr)
 {
 	if(!is_array($arr))
 	{
 		return stripslashes($arr);
 	}
 	else
 	{
 		return array_map('stripFormSlashes', $arr);
 	}
 }
 

 
 function find_matching_item($nodeList, $attrName, $target)
 {
   $x =0;
   $item = $nodeList->item($x);
   while($item)
   {
      if(strcmp($item->getAttribute($attrName), $target) ==0)
      {
      	return $item;
      }
      $x++;
      $item = $nodeList->item($x);
   }
   return null;
 }
 
 
 function str_has_suffix($str, $sfx)
 {
    $reg = "(-" . $sfx . "($|[-|\\s]))";
	return preg_match($reg, $str);
 }
 
 function sans_suffix($str)
 {
   return preg_replace('(-.*)', '', $str);
 }
 
 function strict_name($str)
{ 
  	return  str_replace(array(' ', '-',  '.', ',', ':', ';', '!', '*', '?', '/', '&', '@', '#', '%', '\"', '\'', '<', '>', '_'), 
  					 '',
  					  strtolower($str)); 					 						
 }
 
 function docent_obj_name($str)
 {
   return strict_name( sans_suffix( $str )) . '_d';
 }
 
 
 
 
 /*--------- main functions ------------*/
 function calc_paypal_display($op)
 {
   $sym = $op->getAttribute('sym');
   if($sym === "view"){ return $sym; }
   else
   {
      $options = $op->getElementsByTagname('option');
      $nameOption = find_matching_item($options, 'name', 'name');
      $priceOption = find_matching_item($options, 'name', 'amount');
      
      return $sym . ': ' . $nameOption->getAttribute('value') 
             . '  $' . $priceOption->getAttribute('value');
   }
 }
 
 function update_ecommerce_xml($dom, $pathToPageDir, $aname)
 {
    $ecommerce_xml_path =  $pathToPageDir . 'sg_' . $aname . '_content/ecommerce.xml';
    $submittedEntry = $dom->getElementsByTagname('entry')->item(0);
    $divid = $submittedEntry->getAttribute('divid');
    $submittedOp = $dom->getElementsByTagname('op')->item(0);
    if(is_file('../' . $ecommerce_xml_path))
    {
       $ecommerceDOM = new DomDocument();
       $ecommerceDOM->load('../' . $ecommerce_xml_path);
       $nodeList = $ecommerceDOM->getElementsByTagname("entry");
       $entry = find_matching_item($nodeList, "divid", $divid);
       $newOp = $ecommerceDOM->importNode($submittedOp, true);
       $newOp->setAttribute('display', calc_paypal_display($newOp));
       while($entry->hasChildNodes()){ $entry->removeChild($entry->firstChild); }
       $entry->appendChild($newOp);
       
       $ecommerceDOM->save('../' . $ecommerce_xml_path);  
       log_timestamp($ecommerce_xml_path, ":new", "cms");
       return($entry);
    }
 }
 /* ---------------------------------------------- */
 
 function calc_ecommerce_link($entry, $classString)
 {
   $op = $entry->getElementsByTagName('op')->item(0);
   $sym = $op->getAttribute('sym');
   
   $paypaldomain = "https://www.paypal.com/cgi-bin/webscr?business=payments@rykersear.com&amp;" ; //
   if($sym === "buy"){    $paypaldomain .= "cmd=_xclick&amp;"; }
   if($sym === "donate"){ $paypaldomain .= "cmd=_donations&amp;"; }
   if($sym === "add" || $sym === "view")
   { 
   	 $paypaldomain .= "cmd=_cart&amp;"; 
   	 if($sym === "add"){  $paypaldomain .= "add=1&amp;"; }
   	 if($sym === "view"){ $paypaldomain .= "display=1"; }
   }
   if($sym !== "view")
   {
     $options = $op->getElementsByTagname('option');
     $x =0;
     $option = $options->item($x);
     while($option)
     {
       if($x != 0){ $paypaldomain .= "&amp;";}
       $name = $option->getAttribute('sym');
       if(!$name){ $name = $option->getAttribute('name'); }
       $val = $option->getAttribute('value');
       if(strpos($val, '[[') === 0){ ; }else{ $val = rawurlencode($val); }
       $paypaldomain .=  rawurlencode($name) . '=' . $val;
       $x++;
       $option = $options->item($x);
     }
   }
   return "href=\"$paypaldomain\" class=\"$classString\"";
  
 }
 
 function calc_eform_action()
 {
   return "https://www.paypal.com/cgi-bin/webscr"; 
 }
 
 function calc_eform_params($entry)
 {
    $op = $entry->getElementsByTagName('op')->item(0);
    $sym = $op->getAttribute('sym');
    
    $params = " <input type=\"hidden\" name=\"business\" value=\"payments@rykersear.com\" />\n"; //
    
    $val = "_cart";
    if($sym === "buy"){    $val = "_xclick"; }
    if($sym === "donate"){ $val = "_donations"; }
    $params .= " <input type=\"hidden\" name=\"cmd\" value=\"$val\" />\n";
    
    if($sym === "add"){ $params .= " <input type=\"hidden\" name=\"add\" value=\"1\" />\n"; }
    if($sym === "view"){$params .= " <input type=\"hidden\" name=\"display\" value=\"1\" />\n"; }
    
    
    if($sym != "view")
    {
       $options = $op->getElementsByTagname('option');
       $x =0;
       $option = $options->item($x);
       while($option)
       {
         $name = $option->getAttribute('sym');
         if(!$name){ $name = $option->getAttribute('name'); }
         $value=$option->getAttribute('value');
         $params .= " <input type=\"hidden\" name=\"$name\" value=\"$value\" />\n";
         $x++;
         $option = $options->item($x);
       }
    }
    return $params;
 }
 
 class labels_and_ids { public $labelsArray;
                        public $idsArray;
                        public $namesArray;
                        public function labels_lambda($matches)
                        {
                        	  array_push($this->idsArray, $matches[1]);
                        	  array_push($this->labelsArray, $matches[2]);
                        	  array_push($this->namesArray, $matches[3]);
                        	  return $matches[0]; //just return the match, no replacement
                        }
                      }
 
 function remaining_paypal_transform($content)
 {
    $remapKeys = array("on0", "on1", "on2", "on3", "on4", "on5", "on6", "on7", "on8", "on9",
                       "os0", "os1", "os2", "os3", "os4", "os5", "os6", "os7", "os8", "os9");
 	$reserved = array("option_index", "option_select0", "option_select1", "option_select2", "option_select3", "option_select4", "option_select5", "option_select6", "option_select7", "option_select8", "option_select9", "option_amount0", "option_amount1", "option_amount2", "option_amount3", "option_amount4", "option_amount5", "option_amount6", "option_amount7", "option_amount8", "option_amount9", "discount_amount", "discount_amount2", "discount_amount3", "discount_amount4", "discount_amount5", "discount_amount6", "discount_amount7", "discount_amount8", "discount_amount9", "discount_rate", "discount_rate2", "discount_rate3", "discount_rate4", "discount_rate5", "discount_rate6", "discount_rate7", "discount_rate8", "discount_rate9", "business", "item_name", "currency_code", "quantity", "amount", "undefined_quantity", "discount_num", "address1", "address2", "city", "country", "email", "first_name", "last_name", "lc", "charset", "night_phone_a", "night_phone_b", "night_phone_c", "state", "zip", "page_style", "image_url", "cpp_header_image", "cpp_headerback_color", "cpp_headerborder_color", "cpp_payflow_color", "cs", "lc", "no_note", "rm", "cbt", "cancel_return", "a1", "p1", "t1", "a2", "p2", "t2", "a3", "p3", "t3", "src", "sra", "custom", "invoice", "modify", "urs_manage", "add", "discount_amount_cart", "display", "handling_cart", "paymentaction", "shopping_url", "upload", "address_override", "custom", "handling", "shipping", "shipping2", "tax_cart", "weight_cart", "weight_unit", "iten_number");
                       
     $obj = new labels_and_ids(); $obj->labelsArray = array(); $obj->idsArray = array(); $obj->namesArray = array();
     preg_replace_callback( "(for=\"([^\"]*)\"[^>]*>([^<]*).*?name=\"([^\"]*))ms", array($obj, 'labels_lambda'), $content);
     
     
     $hiddenInputs = ""; $x = 0;
     for($i=0; $i < count($obj->idsArray); $i++)
     {
     	$label = $obj->labelsArray[$i];
     	$name = $obj->namesArray[$i];
     	$value = ( $label === $name || in_array($name, $remapKeys)) ? $label : $name;
     	
 		if(!in_array($name, $reserved))
     	{
     		$hiddenInputs .= "  <input type=\"hidden\" name=\"on$x\" value=\"$value\" />\n";
     		$x++;
     	}
     }
     $content = str_replace("<!-- /eform -->", $hiddenInputs . "<!-- /eform -->", $content);
     
        $x = 0;
 	for($i=0; $i < count($obj->idsArray); $i++)
 	{
 		$id = $obj->idsArray[$i];
                $name = $obj->namesArray[$i];
 		if(!in_array($name, $reserved))
 		{
 			$content = preg_replace("(id=\"$id\" name=\"[^\"]*\")", "id=\"$id\" name=\"os$x\"", $content);
                        $x++;
 		}
 	}
 	
 	return($content);
 	
 }
 
 function calc_ecommerce_form_transformation($content, $entry, $classString)
 {
    $divid = $entry->getAttribute('divid');
    $formid = 'frm_' . $divid;
    $action = calc_eform_action();
    //<form>
    $content = preg_replace( "(<form action=\"[^\"]*\"[^>]*id=\"$formid\")ms",
    	                        "<form action=\"$action\" method=\"post\" class=\"$classString\" id=\"$formid\"",
    	                         $content);
    //<!-- eform -->
    $params = calc_eform_params($entry);
    $content = preg_replace("(<!-- eform -->.*<!-- /eform -->)ms",
    	                       "<!-- eform --> $params <!-- /eform -->",
    	                       $content);
    	                       
    	$content = remaining_paypal_transform($content);
    	
    return $content;
    	                                 
 }
 
 function update_ecommerce_content_file($dom, $pathToPageDir, $aname, $entry, $providername)
 {
   $content_path =  $pathToPageDir . 'sg_' . $aname . '_content/' . $entry->getAttribute('path');
   if(is_file('../' . $content_path))
   {
     $classString = $providername ?  docent_obj_name($providername) : "";
     $content = "";
     $name = $entry->getAttribute('name');
     if( str_has_suffix($name, "form") )
     {
       $content = calc_ecommerce_form_transformation(file_get_contents('../' . $content_path), $entry,  $classString);
     }
     else
     {
       $content = calc_ecommerce_link($entry, $classString);
     }
     
     file_put_contents('../' . $content_path, $content);
     log_timestamp($content_path, ":new", "cms");
   }
 }
 
 /* __________________________________________________*/
 
 function set_ecommerce_operation($xmlString)
 { 
    //1. load XML
    $dom = new DomDocument();
    $dom->loadXML($xmlString);
    //2. extract page info
    $pageinfo = $dom->getElementsByTagname('page_info')->item(0);
    $pathToPageDir = $pageinfo->getAttribute('path_to_page_dir');
    $aname = $pageinfo->getAttribute('aname');
    if($pathToPageDir !== ''){ $pathToPageDir .= '/'; }
    //2.
    $entry = update_ecommerce_xml($dom, $pathToPageDir, $aname);
    //3. 
    $providername = null;
    $providerlist = $dom->getElementsByTagname('provider');
    if($providerlist){ $providername = $providerlist->item(0)->textContent; }
    update_ecommerce_content_file($dom, $pathToPageDir, $aname, $entry, $providername);
 }
 
 /*---------------------------------------------------*/
 // MAIN ENTRY
 header('Content-type: text/xml');
 
 $lzpostbodyvar = $_POST['lzpostbody'];
 
 
 if($lzpostbodyvar)
 {
	if(get_magic_quotes_gpc())
 	{
    	  $lzpostbodyvar = stripFormSlashes($lzpostbodyvar);
 	}
 	set_ecommerce_operation($lzpostbodyvar);
 }

echo "<nothing />";  

?>