<?php

	 include_once("config.php");
	 mysql_query('SELECT * FROM location WHERE idLoca = "'.'1'.'" LIMIT 1');
	 
	 echo $_GET['idLoca'] ;
?>