<?php
include'config.php';

//แสดงข้อมูลจอยกันของตารางยศและตารางผู้ใช้ โดยแสดงค่าที่มากที่สุด 10 ค่าแรก
$query = mysql_query('SELECT * FROM status ,users WHERE status.idStatus = users. idStatus  ORDER BY  arrogate  DESC  LIMIT 0 , 10') or die(mysql_error());

$data_array = array();
$i=0;
while($arr = mysql_fetch_object($query))
{
	$data_array[$i]['nameUsers'] = $arr->nameUsers;
	$data_array[$i]['arrogate'] = $arr->arrogate;
	$data_array[$i]['tema'] = $arr->tema;
	$data_array[$i]['nameStatus'] = $arr->nameStatus;

	$i++;
}

echo json_encode($data_array);



?>