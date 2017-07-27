<?php
require_once "process.php"; //CALL BACK TO CREATED ARRAYS AND GLOBALS
include "alerts.php";


//New or update customer
function CustomerInsertTable($tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli, $updating){
	if ($updating == "true"){
		$a = 0;
		$f = 0;
		$pq = $arrp + $arrq;
		for ($i = $arrp + 1; $i < $pq; $i++){
			$ins[$a] = $insertstr[$i];
			$val[$a] = $_POST["" . $ins[$a] . ""];
			if($mysqli->query("UPDATE {$tbl} SET {$ins[$a]} = '{$val[$a]}' WHERE {$insertstr[$arrp]} = {$id}") === true)
			{
				$update[$i] = true;
			}
			else 
			{
				$update[$i] = false;
			}	
		};
		$flag = false;
		foreach ($update as $boolean){
			if ($boolean == false){
				$flag = true;
			}
		}
		if ($flag == true){
			AlertFailed();
		}
		else
		{
			AlertSuccess();
		}
	} 
	else {
		$a = 0;
		$f = 0;
		$pq = $arrp + $arrq;
		for ($i = $arrp + 1; $i < $pq; $i++){
			$ins[$a] = $insertstr[$i];
			$val[$a] = "'" . $_POST["" . $ins[$a] . ""] . "'";
			$a++;
		}
		$datains = implode(",", $ins);
		$datavals = implode(",",$val);
		if($mysqli->query("INSERT INTO {$tbl} ({$datains}) VALUES ({$datavals})") === true)
		{
			$insert = true;
			AlertSuccess();
		}
		elseif ($mysqli->query("INSERT INTO {$tbl} ({$datains}) VALUES ({$datavals})") === false)
		{
			die('There was an error running the query [' . $mysqli->error . ']');
			$insert = false;
			AlertFailed();
		}
		else
		{
			AlertWarn();
		};
	};
};	

//Retrieve all data from given table for combo boxes 
function RetrieveData($tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli){
	$result = $mysqli->query("SELECT * FROM {$tbl} ORDER BY {$tblorder} ASC");
	$norows = mysqli_num_rows($result);
	while ($row = $result->fetch_array())
	{
		$rows[] = $row;	
	};
	$a = $count + 2;
	$b = $count + 1;
	$c = $count + 4;
	$d = $count + 3;
	$e = $count;
	$i = 0;
	foreach ($rows as $row){
		$i++;
		$rowdata = $row["" . $querystr[$a] . ""] . ", " . $row["" . $querystr[$b] . ""] . " " . $row["" . $querystr[$c] . ""] . ", " . $row["" . $querystr[$d] . ""];
		echo "<option id='cust" . $i . "' value='" . $rowdata . "' data-value='" . $row["" . $querystr[$e] . ""] . "'></option>\n";
	};	
};

//Turn array in to a list of values.
function arraytoValues($ins) {
	$arrayValues = array();
	foreach ($ins as $value){
		if (is_scalar($value) OR is_resource($value)) {
			$arrayValues[] = $value;
		} elseif (is_array($value)) {
			$arrayValues = array_merge($arrayValues, arraytoValues($value));
		}
	}
	return $arrayValues;
}
		
//Bubble sort - just for fun.
function bubbleSort($array){
	$exchanges = TRUE;
	$noofrows = $GLOBALS['norows'] - 1;
	while ($noofrows > 0 && $exchanges == TRUE) {
		$exchanges = FALSE;
		for ($i = 0; $i <= $noofrows; $i++){
			if($array[$i]>$array[$i+1]){
				$exchanges = TRUE;
				$tmp = $array[$i];
				$array[$i] = $array[$i+1];
				$array[$i+1] = $tmp;
				$noofrows = $noofrows - 1;
			}
		}
	} 
}

//Customer selected
function CustomerSelect($tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli){
	$select = $_POST['selection'];
	$result = $mysqli->query("SELECT * FROM {$tbl} WHERE {$querystr[$count]} = {$select}");  
	$rowselect = $result->fetch_array();
	$a = 0;
	$b = $arrp + $arrq;
	$ins = array();
	for ($i = $arrp; $i < $b; $i++){
		$ins[$a] = $insertstr[$i];  
		$a++;	
	};
	$i = 0;
	foreach ($ins as $rows){
		$jsdata[$i] = $rowselect["" . $ins[$i] . ""];
		$i++;
	}
	?>
	<script type="text/javascript">
		$(document).ready(function(){
			<?php 
			$i = 0;
			foreach ($jsdata as $rows) {;
			?>
			var jqins = JSON.parse('<?php echo json_encode($ins[$i]);?>');  
			$("#"+jqins).val(<?php echo json_encode($jsdata[$i])?>);
			<?php 
			$i++;
			}; 
			?>
		});
    </script><?php
};

//Delete Record
function DeleteRecord ($deleteid, $tbl, $tblorder, $querystr, $count, $arrp, $arrq, $insertstr, $id, $mysqli, $update){
	$mysqli->query("DELETE FROM {$tbl} WHERE {$insertstr[$arrp]} = {$deleteid}") or die('There was an error running the query [' . $mysqli->error . ']');
}
?>