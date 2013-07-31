<?php
include'config.php';

//แสดงค่าในตารางยศ โดยการตรวจสอบไอดียศ
$query = mysql_query('SELECT idStatus, nameStatus, exp, authority FROM status WHERE idStatus = "'.$_GET['idStatus'].'"') or die(mysql_error());

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