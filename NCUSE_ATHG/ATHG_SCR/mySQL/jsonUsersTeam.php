<?php
include'config.php';

//แสดงค่าสถานะและผู้ใช้ โดยแสดงแสดงแค่ทีมเดียวกันเท่านั้น เรียงค่ามากไปหาน้อย
$query = mysql_query('SELECT * FROM status , users WHERE status.idStatus = users.idStatus AND users.tema = "'.$_GET['tema'].'" ORDER BY arrogate DESC ') or die(mysql_error());

$data_array = array();
$i=0;
while($arr = mysql_fetch_object($query))
{
	$data_array[$i]['nameUsers'] = $arr->nameUsers;
	$data_array[$i]['arrogate'] = $arr->arrogate;
	$data_array[$i]['nameStatus'] = $arr->nameStatus;

	$i++;
}

echo json_encode($data_array);


