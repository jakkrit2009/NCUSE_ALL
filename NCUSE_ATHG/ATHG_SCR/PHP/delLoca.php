<?php

		//ทำการส่งข้อมูลไปคลาสและลบข้อมูล
		include_once("admin.php");
		$delLoca = new admin();
		
		$delId = $_GET["idLoca"];
		$delLoca->setIdLoca($delId);
		$delLoca->delLoca();

?>