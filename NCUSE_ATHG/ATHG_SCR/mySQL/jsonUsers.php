<?php
include'config.php';

//แสดงค่าในตารางผู้ใช้ ด้วยแสดงตามไอดี
$query = mysql_query('SELECT idUsers, nameUsers, tema, sumExp, arrogate, idStatus FROM users WHERE idUsers = "'.$_GET['idUsers'].'"') or die(mysql_error());

$data_array = array();
$i=0;

while($arr = mysql_fetch_object($query))
{
	$data_array[$i]['idUsers'] = $arr->idUsers;
	$data_array[$i]['nameUsers'] = $arr->nameUsers;
	$data_array[$i]['tema'] = $arr->tema;
	$data_array[$i]['sumExp'] = $arr->sumExp;
	$data_array[$i]['arrogate'] = $arr->arrogate;
	$data_array[$i]['idStatus'] = $arr->idStatus;
	$i++;
}

echo json_encode($data_array);

?>