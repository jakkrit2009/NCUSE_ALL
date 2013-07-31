<?php

include'config.php';
//แสดงตารางของสถานที่
$query = mysql_query('SELECT idLoca, nameLoca, latitude, longitude, historyLoca, eagle, landgon FROM location') or die(mysql_error());

$data_array = array();
$i=0;
while($arr = mysql_fetch_object($query))
{
	$data_array[$i]['idLoca'] = $arr->idLoca;
	$data_array[$i]['nameLoca'] = $arr->nameLoca;
	$data_array[$i]['latitude'] = $arr->latitude;
	$data_array[$i]['longitude'] = $arr->longitude;
	$data_array[$i]['historyLoca'] = $arr->historyLoca;
	$data_array[$i]['eagle'] = $arr->eagle;
	$data_array[$i]['landgon'] = $arr->landgon;
	$i++;
}

echo json_encode($data_array);



?>


