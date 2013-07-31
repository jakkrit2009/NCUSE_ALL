<?php

	include'config.php';

	//เพิ่มข้อมูลของผู้ใช้
	mysql_query('INSERT INTO users(idUsers, nameUsers, tema,idStatus) VALUES("'.$_POST['idUsers'] .'","'.$_POST['nameUsers'] .'","'.$_POST['tema'] .'","'.$_POST['idStatus'] .'")')or die(mysql_error());
	

echo 'Add data complete.';
?>