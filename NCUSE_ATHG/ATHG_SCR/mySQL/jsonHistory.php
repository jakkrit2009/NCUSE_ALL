<?php
include'config.php';


//แสดงประวัติสถานที่ตามไอดีสถานที่ ที่รับมา
$query = mysql_query('SELECT idLoca, nameLoca, latitude, longitude, historyLoca, imageLoca, eagle, landgon, score FROM location WHERE idLoca ="'.$_GET['idLoca'].'"') or die(mysql_error());

$data_array = array();
$i=0;
while($arr = mysql_fetch_object($query))
{
	$data_array[$i]['idLoca'] = $arr->idLoca;
	$data_array[$i]['nameLoca'] = $arr->nameLoca;
	$data_array[$i]['latitude'] = $arr->latitude;
	$data_array[$i]['longitude'] = $arr->longitude;
	$data_array[$i]['historyLoca'] = $arr->historyLoca;
	$data_array[$i]['imageLoca'] = $arr->imageLoca;
	$data_array[$i]['score '] = $arr->score ;
	$data_array[$i]['eagle'] = $arr->eagle;
	$data_array[$i]['landgon'] = $arr->landgon;
	$data_array[$i]['score'] = $arr->score;
	$i++;
}

echo json_encode($data_array);



?>