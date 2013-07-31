<?php
include'config.php';
//แสดงข้อมูลของตารางยศ

$query = mysql_query('SELECT * FROM status ') or die(mysql_error());

$data_array = array();
$i=0;
while($arr = mysql_fetch_object($query))
{
	$data_array[$i]['idStatus'] = $arr->idStatus;
	$data_array[$i]['nameStatus'] = $arr->nameStatus;
	$data_array[$i]['exp'] = $arr->exp;
	$data_array[$i]['authority'] = $arr->authority;
	$i++;
}

echo json_encode($data_array);

?>